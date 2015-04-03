/*
 * @copyright 2011 PT. Teravin Technovations. All rights reserverd.
 *
 * This source code is an unpublished work and the use of  a copyright  notice
 * does not imply otherwise. This source  code  contains  confidential,  trade
 * secret material of PT. Teravin Technovations.
 * Any attempt or participation in deciphering, decoding, reverse  engineering
 * or in any way altering the source code is strictly  prohibited, unless  the
 * prior  written consent of PT. Teravin Technovations is obtained.
 *
 * Unless  required  by  applicable  law  or  agreed  to  in writing, software
 * distributed under the License is distributed on an "AS IS"  BASIS,  WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or  implied.  See  the
 * License for the specific  language  governing  permissions  and limitations
 * under the License.
 */

package com.akiong

import org.codehaus.groovy.grails.commons.ConfigurationHolder

import com.akiong.security.Menu
import com.akiong.security.MenuMap
import com.akiong.security.RequestMap
import com.akiong.security.Role
import com.akiong.security.RoleMenu


class RoleService {

    static transactional = true

    private static final int MAX_SEARCH = ConfigurationHolder.config.max.autocomplete

    /**
     * Return role record
     * @param params
     * @return ArrayList containing List of role objects, and the total of roles
     */
    def list(def params) {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        params.properties = ["authority"]

        if (!params.sort && !params.order) {
            params.sort = "sortableAuthority"
            params.order = "asc"
        }

        def query = "-authority:BO_MASTER_TR"
        if (params.name) {
			def name = params.name.replaceAll("_", " ")
            query += " *${name}*"
        }
        else {
            query += " *"
        }

        def results = new ArrayList()
		def searchResult = Role.search(query, params)
		results.add(searchResult.results)
		results.add(searchResult.total)
        
		return results
    }

    /**
     * Create role
     * @param roleInstance role Object
     * @param menuIds Array of Menu id
     * @param username
     */
	def create(def roleInstance, def menuIds, def username) {
		if(roleInstance.authority.contains("'") || roleInstance.authority.contains(",")){
			def request = org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder.getRequest()
			def session = request.getSession(false)
			throw new SecurityException(messageSource.getMessage("role.mustnot.contain.msg", null, session[org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME]))
		}
		
        roleInstance.name = roleInstance.authority
        roleInstance.id = roleInstance.authority
		
		def menuArray = new ArrayList()
		if(menuIds){
			if(menuIds.class == String) {
				menuArray.add(menuIds)
			}
			else{
				menuArray.addAll(menuIds)
			}
		}

        def menu
        // add selected menu to role menu
		def menuMaps = new ArrayList()
        menuArray.each {
            menu = Menu.get(it as Long)
            roleInstance.addToRoleMenus(new RoleMenu(menu: menu, createdBy: username))
			menuMaps.addAll(MenuMap.findAllByMenuId(it as Long).collect { it.url })
        }

        roleInstance.save(failOnError: true)		
		addRequestMap(menuMaps, roleInstance.authority)
    }

    /**
     * Update role
     * @param roleInstance role Object
     * @param menuIds Array of Menu id
     * @param username
     */
	def update(def roleInstance, def menuIds, def username) {
		def menuArray = new ArrayList()
		if(menuIds){
			if(menuIds.class == String) {
				menuArray.add(menuIds)
			}
			else{
				menuArray.addAll(menuIds)
			}
		}

        def tempListRoleMenu = []
        def roleMenu
		def menuMapsRmv = new ArrayList()
        def id
        tempListRoleMenu += roleInstance.roleMenus
        tempListRoleMenu.each { x ->
            // check whether the existing details is selected otherwise remove from roleMenu
            id = x.menu.id
            if (menuArray.grep('' + id)) {
                x.updatedBy = username
                menuArray -= '' + id
            }
            else{
                roleInstance.removeFromRoleMenus(x)
				menuMapsRmv.addAll(MenuMap.findAllByMenuId(id).collect { it.url })
            }
        }

        def menu
		def menuMaps = new ArrayList()
        // add selected menu to role menu
        menuArray.each {
            menu = Menu.get(it as Long)
            roleInstance.addToRoleMenus(new RoleMenu(menu: menu, createdBy: username))
			menuMaps.addAll(MenuMap.findAllByMenuId(it as Long).collect { it.url })
        }

        roleInstance.save(failOnError: true)
		
		removeRequestMap(menuMapsRmv, roleInstance.authority)
		addRequestMap(menuMaps, roleInstance.authority)
		
        return roleInstance
    }
	
	private def removeRequestMap(def menuMaps, def authority){
		def reqMap
		def index = -1
		def indexComa = -1
		def reqConf
		def length
		menuMaps.unique().each {
			reqMap = RequestMap.findByUrl(it)
			if(reqMap){
				index = reqMap.configAttribute.indexOf("'" + authority + "'")
				if(index > -1){
					length = authority.length() + 2
					indexComa = reqMap.configAttribute.indexOf(",", index + length)
					if(indexComa > -1){
						reqConf = reqMap.configAttribute.substring(0,index) + reqMap.configAttribute.substring(indexComa + 1)
					}
					else{
						reqConf = reqMap.configAttribute.substring(0,index) + reqMap.configAttribute.substring(index + length)
						indexComa = reqConf.lastIndexOf(",")
						if(indexComa > -1){
							reqConf = reqConf.substring(0, indexComa) + reqConf.substring(indexComa + 1)
						}
					}
					reqMap.configAttribute = reqConf
				}
				reqMap.save(failOnError: true)
			}
		}
	}

	private def addRequestMap(def menuMaps, def authority){
		def reqMap
		def index = -1
		def reqConf
		menuMaps.unique().each {
			reqMap = RequestMap.findByUrl(it)
			if(reqMap){
				if(!reqMap.configAttribute.contains("'" + authority + "'")){
					index = reqMap.configAttribute.lastIndexOf(")")
					reqConf = reqMap.configAttribute.substring(0, index)
					if(reqConf.contains("'")){
						reqMap.configAttribute = reqConf + ",'" + authority + "')"
					}
					else{
						reqMap.configAttribute = reqConf + "'" + authority + "')"
					}
				}
			}
			else{
				reqMap = new RequestMap()
				reqMap.url = it
				reqMap.configAttribute = "hasAnyRole('" + authority + "')"
			}
			reqMap.save(failOnError: true)
		}
	}

    /**
     * Delete role
     * @param roleInstance role Object
     */
    def delete(def roleInstance) {		
		def menuMapsRmv = new ArrayList()
		def id
		roleInstance.roleMenus.each { x ->
			id = x.menu.id
			menuMapsRmv.addAll(MenuMap.findAllByMenuId(id).collect { it.url })
		}
		def authority = roleInstance.authority
		
		roleInstance.delete(failOnError: true, flush: true)
		removeRequestMap(menuMapsRmv, authority)
    }

    def getRoleLike(def name) {
        def params = [:]
        params.max = MAX_SEARCH
        params.properties = ["authority"]

		def namex = name.replaceAll("_", " ")
		def query = ""
        if (name) {
            query += " *${namex}*"
        }
        return Role.search(query, params).results
    }
}
