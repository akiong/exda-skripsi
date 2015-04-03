%{--
  - @copyright 2011 PT. Teravin Technovations. All rights reserverd.
  -
  - This source code is an unpublished work and the use of  a copyright  notice
  - does not imply otherwise. This source  code  contains  confidential,  trade
  - secret material of PT. Teravin Technovations.
  - Any attempt or participation in deciphering, decoding, reverse  engineering
  - or in any way altering the source code is strictly  prohibited, unless  the
  - prior  written consent of PT. Teravin Technovations is obtained.
  -
  - Unless  required  by  applicable  law  or  agreed  to  in writing, software
  - distributed under the License is distributed on an "AS IS"  BASIS,  WITHOUT
  - WARRANTIES OR CONDITIONS OF ANY KIND, either express or  implied.  See  the
  - License for the specific  language  governing  permissions  and limitations
  - under the License.
  --}%

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="secure2"/>
    <g:set var="entityName" value="${message(code: 'menu.label')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<div class="body">
    <div class="contentbar"><h1>${entityName}</h1></div>

    <div class="contentbox">
        <div class="transDetail"><g:message code="default.list.label" args="[entityName]"/></div>
        
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>

		<div class="listcontent">
	        <table class="table13" width="100%">
	            <thead>
	            <tr>
	
	                <g:sortableColumn property="name" title="${message(code: 'default.name.label')}"
	                                  params='[name:"${searchName}"]'/>
	
	                <g:sortableColumn property="description" title="${message(code: 'default.description.label')}"
	                                  params='[name:"${searchName}"]'/>
	
	                <g:sortableColumn property="url" title="${message(code: 'menu.url.label')}"
	                                  params='[name:"${searchName}"]'/>
	
	            </tr>
	            </thead>
	            <tbody>
	            <g:each in="${menuInstanceList}" status="i" var="menuInstance">
	                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	
	                    <td><g:link action="show"
	                                id="${menuInstance.id}">${fieldValue(bean: menuInstance, field: "name")}</g:link></td>
	
	                    <td>${fieldValue(bean: menuInstance, field: "description")}</td>
	
	                    <td>${fieldValue(bean: menuInstance, field: "url")}</td>
	
	                </tr>
	            </g:each>
	            </tbody>
	        </table>
	
	        <div class="paginateButtons">
	            <g:paginate total="${menuInstanceTotal}" params='[name:"${searchName}"]'/>
	        </div>
        </div>
        <div class="listfilter">
        	<g:form action="list">
        		<div class="filterfont"><g:message code="default.filter.label"/></div>
	            <label for="name"><g:message code="default.name.label"/></label>
	            <g:textField name="name" maxlength="100" value="${searchName}"/>
	
	            <div>
	                <g:submitButton name="search" class="search" value="${message(code: 'default.button.search.label')}"/>
	                <input type="button" class="create" value="${message(code: 'default.button.add.label')}"
	                                            onclick='location.href = "${createLink(url: [controller: 'menu', action: 'create'])}"'/>
	            </div>
	        </g:form>
        </div>
        <div class="clear"></div>
    </div>

</div>
</body>
</html>
