<%@ page import="com.akiong.security.UserDetails" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="secure2"/>
    <g:set var="entityName" value="${message(code: 'userDetails.label')}"/>
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
	                <g:sortableColumn width="13%" property="username" title="${message(code: 'userDetails.userID.label')}"
	                                  params='[userId:"${userId}", userName:"${name}"]'/>
	                <th class="tblTitle" width="20%"><g:message code="userDetails.name.label"/></th>
	                <th class="tblTitle"><g:message code="role.label"/></th>
	                <th class="tblTitle" width="100px"><g:message code="userDetails.status.label"/></th>
	                <sec:access expression="hasRole('${com.akiong.helper.Common.ROLE_SWTICH }')">
	                	<th class="tblTitle" width="100px"></th>
	                </sec:access>
	            </tr>
	            </thead>
	            <tbody>
	            <g:each in="${userDetailsInstanceList}" status="i" var="userDetailsInstance">
	
	                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	                    <td><g:link action="show"
	                                id="${userDetailsInstance.id}">${userDetailsInstance.user.username}</g:link></td>
	
	                    <td>${userDetailsInstance?.firstName} ${userDetailsInstance?.lastName}</td>
	
	                    <td>${auth[userDetailsInstance.id]}</td>
	
	                    <td>
	                        <g:if test="${userDetailsInstance?.status == '1'}">
	                            <g:message code="userDetails.status.inactive.label"/>
	                        </g:if>
	
	                        <g:elseif test="${userDetailsInstance?.status == '2'}">
	                            <g:message code="userDetails.status.active.label"/>
	                        </g:elseif>
	
	                        <g:elseif test="${userDetailsInstance?.status == '3'}">
	                            <g:message code="userDetails.status.locked.label"/>
	                        </g:elseif>
	
	                        <g:else>
	                            <g:message code="userDetails.status.blocked.label"/>
	                        </g:else>
	                    </td>
	                    <sec:access expression="hasRole('${com.akiong.helper.Common.ROLE_SWTICH }')">
                       	<td><form action='${request.contextPath}/j_spring_security_switch_user' method='POST' style="margin:0">
                       		<input type="hidden" name="j_username" value="${userDetailsInstance.user.username }"/>
                       		<input type='submit' value='Switch'/> 
                       	</form>
                       	</td>
                       	</sec:access>
	
	                </tr>
	            </g:each>
	            </tbody>
	        </table>
	        
	
	        <div class="paginateButtons">
	            <g:paginate total="${userDetailsInstanceTotal}" params='[userId:"${userId}", userName:"${name}"]'/>
	        </div>
        </div>
        
        <div class="listfilter">
        	<g:form action="list">
        		<div class="filterfont"><g:message code="default.filter.label"/></div>

	            <label for="userId"><g:message code="userDetails.userID.label"/></label>
	            <g:textField name="userId" maxlength="100" value="${userId}"/><br/>
	                </tr>
	
	             <label for="userName"><g:message code="userDetails.name.label"/></label>
	             <g:textField name="userName" maxlength="100" value="${name}"/>

	            <div class="buttons">
	                <span class="button"><g:submitButton name="search" class="search"
	                                                     value="${message(code: 'default.button.search.label')}"/></span>
	                <span class="button"><input type="button" class="add"
	                                            value="${message(code: 'default.button.add.label')}"
	                                            onclick='location.href = "${createLink(url: [controller: 'userDetails', action: 'create'])}"'/>
	                </span>
	            </div>
	
	        </g:form>
	    </div>
	    <div class="clear"></div>
    </div>

</div>
</body>
</html>
