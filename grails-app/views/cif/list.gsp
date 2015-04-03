
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="secure2" />
        <g:set var="entityName" value="${message(code: 'cif.label')}" />
        <title><g:message code="default.management.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <div class="contentbar"><h1>${entityName}</h1></div>
            <div class="contentbox">
	            <div class="transDetail"><g:message code="default.management.label" args="[entityName]"/></div>
	            <g:if test="${flash.message}">
	            <div class="message">${flash.message}</div>
	            </g:if>
	            
	        <div class="listcontent">
	                <table class="table13" width="100%">
	                    <thead>
	                        <tr>
	                        
	                            <g:sortableColumn property="corpName" title="${message(code: 'cif.corpName.label')}" params='[corporate:"${corporate}"]'/>
	                        
	                        </tr>
	                    </thead>
	                    <tbody>
	                    <g:each in="${cifInstanceList}" status="i" var="cifInstance">
	                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	                        
	                            <td><g:link action="show" id="${cifInstance.id}">${fieldValue(bean: cifInstance, field: "corpName")}</g:link></td>
	                        
	                        </tr>
	                    </g:each>
	                    </tbody>
	                </table>
	            <div class="paginateButtons">
	                <g:paginate total="${cifInstanceTotal}" params='[corporate:"${corporate}"]'/>
	            </div>
            </div>
            <div class="listfilter">
            	<g:form action="list">
            		<div class="filterfont"><g:message code="default.filter.label"/></div>
		            <label for="corporate"><g:message code="cif.corpName.label"/></label>
		            <g:textField name="corporate" maxlength="100" value="${corporate}"/>
		
		            <div>
		                <g:submitButton name="search" class="search" value="${message(code: 'default.button.search.label')}"/>
		                <input type="button" class="add" value="${message(code: 'default.button.add.label')}"
		                                            onclick='location.href = "${createLink(url: [controller: 'cif', action: 'create'])}"'/>
		            </div>
		        </g:form>
		     </div>
		     <div class="clear"></div>
        </div>
        </div>
    </body>
</html>
