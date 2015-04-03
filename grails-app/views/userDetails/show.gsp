<%@ page import="com.akiong.security.UserDetails" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="secure2"/>
    <g:set var="entityName" value="${message(code: 'userDetails.label')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div class="body">
    <div class="contentbar"><h1>${entityName}</h1></div>

    <div class="contentbox">

        <div class="transDetail"><g:message code="default.show.label" args="[entityName]"/></div>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${userDetailsInstance}">
            <div class="errors">
                <g:renderErrors bean="${userDetailsInstance}" as="list"/>
            </div>
        </g:hasErrors>
        
<script>
    $(function() {
        $("#tabs").tabs();
    });
</script>

        <table class="form" width="100%">
            <tbody>
            <tr>
                <td class="formLbl singgleTr"><g:message code="userDetails.name.label"/></td>
                <td class="value">${fieldValue(bean: userDetailsInstance, field: "firstName")} ${fieldValue(bean: userDetailsInstance, field: "lastName")}</td>
            </tr>
            <tr class="borderTop">
                <td class="formLbl singgleTr"><g:message code="userDetails.userID.label"/></td>
                <td class="value">${fieldValue(bean: userDetailsInstance, field: "user")}</td>
            </tr>
            <tr class="borderTop">
                <td class="formLbl singgleTr"><g:message code="userDetails.userAlias.label"/></td>
                <td class="value">${fieldValue(bean: userDetailsInstance, field: "userAlias")}</td>
            </tr>
            <tr class="borderTop">
                <td class="formLbl singgleTr"><g:message code="userDetails.email.label"/></td>
                <td class="value">${fieldValue(bean: userDetailsInstance, field: "email")}</td>
            </tr>
            <tr class="borderTop">
                <td class="formLbl singgleTr"><g:message code="userDetails.mobilePhoneNo.label"/></td>
                <td class="value">${fieldValue(bean: userDetailsInstance, field: "mobilePhoneNo")}</td>
            </tr>
            <tr class="borderTop">
                <td class="formLbl singgleTr"><g:message code="userDetails.status.label"/></td>
                <td class="value">
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
            </tr>
            </tbody>
            </table>
            <br/>
            <div id="tabs">
                	<ul>
			            <li><a href="#listRole"><g:message code="role.label"/></a></li>
			            <li><a href="#loginHistory"><g:message code="loginHistory.label"/></a></li>
			        </ul>
                    <div id="listRole">
                        <table class="table13" width="100%">
                            <thead>
                            <tr>
                                <th class="tblTitleLeft"><g:message code="role.label"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <g:each in="${listRole}" status="i" var="rrole">
                                <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                                    <td>${rrole}</td>
                                </tr>
                            </g:each>
                            </tbody>
                        </table>
                    </div>
                    <div id="loginHistory">
              	<table class="table13" width="100%" style="display:block;width:100%;max-height:500px;overflow:auto;">
              		<thead>
                      <tr>
                          <th class="tblTitle"><g:message code="login.time.label"/></th>
                          <th class="tblTitle"><g:message code="logout.time.label"/></th>
                      </tr>
                      </thead>
                      <tbody>
                      	<g:each in="${loginList}" status="i" var="login">
                      		<tr class="${((i + 0) % 2) == 0 ? 'odd' : 'even'}">
                       		<td><g:formatDate format="dd MMMM yyyy - HH:mm:ss" date="${login.loginTime}" /></td>
                       		<td><g:formatDate format="dd MMMM yyyy - HH:mm:ss" date="${login.logoutTime}" /></td>
                      		</tr>
                      	</g:each>
                      </tbody>
              	</table>
              </div>
        </div>
                
        <br/>
        <div class="buttons">
            <g:form action="delete" useToken="true">
                <g:hiddenField name="id" value="${userDetailsInstance?.id}"/>
                <g:hiddenField name="version" value="${userDetailsInstance?.version}"/>
                <g:hiddenField name="show" value="show"/>
                <span class="button"><input type="button" class="edit" value="${message(code: 'default.button.edit.label')}"
                                            onclick='location.href = "${createLink(url: [controller: 'userDetails', action: 'edit', id: userDetailsInstance?.id])}"'/></span>
                <span class="button"><input type="submit" class="delete redbtn" value="${message(code: 'default.button.delete.label')}"
                                                     onclick="return confirm('${message(code: 'default.button.delete.confirm.message')}');"/></span>
                <span class="button"><input type="button" class="back"
                                            value="${message(code: 'default.button.back.label')}"
                                            onclick='location.href = "${createLink(url: [controller: 'userDetails', action: 'index'])}"'/>
                </span>
            </g:form>
        </div>
    </div>

</div>
</body>
</html>
