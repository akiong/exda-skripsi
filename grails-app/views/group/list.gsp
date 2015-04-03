
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="secure2" />
        <g:set var="entityName" value="${message(code: 'grup.label')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
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
	                            <g:sortableColumn property="grupId" title="${message(code: 'grup.grupId.label')}" params='[grupId:"${grupId}", name:"${name}"]'/>
	                        	<g:sortableColumn property="name" title="${message(code: 'grup.name.label')}" params='[grupId:"${grupId}", name:"${name}"]'/>
	                        	<g:sortableColumn property="dbaName" title="${message(code: 'grup.dbaName.label')}" params='[grupId:"${grupId}", name:"${name}"]'/>
	                        </tr>	                        
	                    </thead>
	                    <tbody>
	                    <g:each in="${groupInstanceList}" status="i" var="grupInstance">
	                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	                        
	                            <td><g:link action="show" id="${grupInstance.id}">${fieldValue(bean: grupInstance, field: "grupId")}</g:link></td>
	                            <td>${grupInstance.name}</td>
	                            <td>${grupInstance.dbaName}</td>
	                        
	                        </tr>
	                    </g:each>
	                    </tbody>
	                </table>
	            <div class="paginateButtons">
	                <g:paginate total="${groupInstanceTotal}" params='[grupId:"${grupId}", name:"${name}"]'/>
	            </div>
            </div>
            <div class="listfilter">
            	<g:form action="list">
            		<div class="filterfont"><g:message code="default.filter.label"/></div>
            		<label for="grupId"><g:message code="grup.grupId.label"/></label>
		            <g:textField name="grupId" maxlength="100" value="${grupId}"/>
		            <label for="name"><g:message code="grup.name.label"/></label>
		            <g:textField name="name" maxlength="100" value="${name}"/>           
		
		            <div>
		                <g:submitButton name="search" class="search" value="${message(code: 'default.button.search.label')}"/>
		                <input type="button" class="add" value="${message(code: 'default.button.add.label')}"
		                                            onclick='location.href = "${createLink(url: [controller: 'group', action: 'create'])}"'/>
		            </div>
		        </g:form>
		     </div>
		     <div class="clear"></div>
        </div>
        </div>
    </body>
</html>
