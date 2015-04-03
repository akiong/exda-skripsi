<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="secure2" />
        <g:set var="entityName" value="${message(code: 'user.label')}" />
        <title><g:message code="default.list.label" args="[entityName]"/></title>
    </head>
    <body>
    <div class="body">
    	<div class="contentbar"><h1>${entityName}</h1></div>

   		<div class="leftbottombar"></div>

    	<div class="cuttedbluebartop"></div>

    	<div class="rightbottombar"></div>

    	<div class="contentbox">

        <div class="transDetail"><g:message code="default.list.label" args="[entityName]"/></div>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        
        <div class="listcontent">
        <table class="table13" width="100%">
                <thead>
                <tr>
					<g:sortableColumn property="username" title="${message(code: 'user.username.label')}"
                                      params='[userID:"${userID}", name:"${name}"]'/>
                    <g:sortableColumn property="cuFirstName" title="${message(code: 'user.name')}"
                                      params='[userID:"${userID}", name:"${name}"]'/>
                    <th class="tblTitle"><g:message code="role.label"/></th>
                    <th class="tblTitle"><g:message code="userDetails.status.label"/></th>

                                                          
                </tr>
                </thead>
                <tbody>
                <g:each in="${userList}" status="i" var="result001">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                        <td><g:link action="show"
                                    id="${result001.id}">${result001?.userDetails?.user?.username}</g:link></td>
                        
                        <td>${result001.firstName} ${result001.lastName} </td>
                        <td>
                        	<g:if test="${result001.bo.equals('Y') && result001.finance.equals('Y')}">
                        		<g:message code="bo.label"/>, <g:message code="finance.label"/>
                        	</g:if>
                        	<g:elseif test="${result001.bo.equals('Y')}">
                        		<g:message code="bo.label"/>
                        	</g:elseif>
                        	<g:elseif test="${result001.finance.equals('Y')}">
                        		<g:message code="finance.label"/>
                        	</g:elseif>
                        </td>
                        <td>
                        	<g:if test="${result001?.userDetails?.status.equals('1')}"><g:message
                                  code="userDetails.status.inactive.label"/></g:if>
                          <g:elseif test="${result001?.userDetails?.status.equals('2')}"><g:message
                                  code="userDetails.status.active.label"/></g:elseif>
                          <g:elseif test="${result001?.userDetails?.status.equals('3')}"><g:message
                                  code="userDetails.status.locked.label"/></g:elseif>
                          <g:else><g:message code="userDetails.status.blocked.label"/></g:else>
                       	</td>
                        
                    </tr>
                </g:each>
                </tbody>
            </table>
            
            	<div class="paginateButtons">
            		<g:paginate total="${userInstanceTotal}" params='[name:"${name}", userid:"${userid}"]'/>
        		</div>
        	</div>
        	
        	<div class="listfilter">        	
	    	<g:form action="index">
            	<div class="filterfont"><g:message code="default.filter.label"/></div>
	            <label for="userID"><g:message code="user.username.label"/></label>
	            <g:textField name="userID" value="${userID}"/>
				
				 <label for="name"><g:message code="user.name"/></label>
	            <g:textField name="name" value="${name}"/>
	            <div>
	                <span class="button">
                				<g:submitButton name="search" class="search" value="${message(code: 'default.button.search.label')}"/>
                			</span>
	                		<span class="button">
	                			<input type="button" class="add" value="${message(code: 'default.button.add.label')}"
	                                            onclick='location.href = "${createLink(url: [controller: 'cifUser', action: 'create'])}"'/>
	                		</span>
	            </div>
            	
	        </g:form>
	        </div>
	        <div class="clear"></div>
	        <br/>
            </div>
        </div>
    </body>
</html>
