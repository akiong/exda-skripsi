package com.akiong

import com.akiong.security.Menu

class MenuService {

    static transactional = true

    /**
     * Return menu record
     * @param params
     * @return ArrayList containing List of menu objects, and the total of menus
     */
    def list(def params) {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)

        if (!params.sort && !params.order) {
            params.sort = "name"
            params.order = "asc"
        }

        def results = new ArrayList()
        def result = Menu.createCriteria().list(params) {
            if (params.name) {
                ilike("name", '%' + params.name + '%')
            }
            order("idx", "asc")
        }

        results.add(result)
		results.add(result.totalCount)

        return results
    }

    /**
     * Create menu
     * @param menuInstance menu Object
     */
    def create(def menuInstance) {
        menuInstance.save(failOnError: true)
        return menuInstance
    }

    /**
     * Delete menu
     * @param menuInstance menu Object
     */
    def delete(def menuInstance) {
        menuInstance.delete(flush: true)
        return menuInstance
    }

    /**
     * Update menu
     * @param menuInstance menu Object
     */
    def update(def menuInstance) {
        menuInstance.save(failOnError: true)
        return menuInstance
    }

    /**
     * Save Menu Tree
     * @param menus Map containing parent menu id as the key and child menu id as the value
     */
    def saveTree(def menus) {
        def menusSeq = Menu.createCriteria().list { ne("sequence", 0) }

        def sequence
        menus.each { key, value ->
            sequence = 1
            if (value.class == String) {
                saveTreeNode(value, key, sequence, menusSeq)
            }
            else {
                value.each {
                    saveTreeNode(it, key, sequence, menusSeq)
                    sequence++;
                }
            }
        }

        menusSeq.each {
            it.sequence = 0
            it.parentMenu = null
            it.save(failOnError: true)
        }
    }

    /**
     * Save Menu Tree Node
     * @param id Menu id
     * @param parentId Parent Menu id
     * @param sequence
     * @param menusSeq List of Menu object which sequence is not 0
     */
    def saveTreeNode(def id, def parentId, def sequence, def menusSeq) {
        def menu
        menu = Menu.get(id as Integer)
        menu.sequence = sequence
        if (parentId != "0")
            menu.parentMenu = Menu.get(parentId as Integer)
        else
            menu.parentMenu = null
        menu.save(failOnError: true)
        menusSeq.remove(menu)
    }
}
