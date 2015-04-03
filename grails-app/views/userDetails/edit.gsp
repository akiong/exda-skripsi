
<%@ page import="com.akiong.security.UserDetails" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="secure2"/>
    <g:set var="entityName" value="${message(code: 'userDetails.label')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<div class="body">
    <div class="contentbar"><h1>${entityName}</h1></div>

    <div class="contentbox">
        <div class="transDetail"><g:message code="default.edit.label" args="[entityName]"/></div>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <g:if test="${flash.error}">
            <div class="errors"><ul><li>${flash.error}</li></ul></div>
        </g:if>
        <g:hasErrors bean="${userDetailsInstance}">
            <div class="errors">
                <g:renderErrors bean="${userDetailsInstance}" as="list"/>
            </div>
        </g:hasErrors>
        <g:form useToken="true">
            <g:hiddenField name="id" value="${userDetailsInstance?.id}"/>
            <g:hiddenField name="version" value="${userDetailsInstance?.version}"/>
            <table class="form" width="100%">
                <tbody>
                <tr>
                    <td class="formLbl">
                        <label for="firstName"><g:message code="userDetails.firstName.label"/></label>
                    </td>
                    <td colspan="2" class="value ${hasErrors(bean: userDetailsInstance, field: 'firstName', 'errors')}">
                        <g:textField name="firstName" maxlength="100" value="${userDetailsInstance?.firstName}"/>
                    </td>
                </tr>
                <tr class="borderTop">
                    <td class="formLbl">
                        <label for="lastName"><g:message code="userDetails.lastName.label"/></label>
                    </td>
                    <td colspan="2" class="value ${hasErrors(bean: userDetailsInstance, field: 'lastName', 'errors')}">
                        <g:textField name="lastName" maxlength="100" value="${userDetailsInstance?.lastName}"/>
                    </td>
                </tr>
                <tr class="borderTop">
                    <td class="formLbl singgleTr">
                        <label for="user"><g:message code="userDetails.userID.label"/></label>
                    </td>
                    <td colspan="2" class="value ${hasErrors(bean: userDetailsInstance, field: 'user', 'errors')}">
                        ${userDetailsInstance?.user}
                    </td>
                </tr>
                <tr class="borderTop">
                    <td class="formLbl">
                        <label for="userAlias"><g:message code="userDetails.userAlias.label"/></label>
                    </td>
                    <td colspan="2" class="value ${hasErrors(bean: userDetailsInstance, field: 'userAlias', 'errors')}">
                        <g:textField name="userAlias" maxlength="100" value="${userDetailsInstance?.userAlias}"/>
                    </td>
                </tr>
                <tr class="borderTop">
                    <td class="formLbl">
                        <label for="email"><g:message code="userDetails.email.label"/></label>
                    </td>
                    <td colspan="2" class="value ${hasErrors(bean: userDetailsInstance, field: 'email', 'errors')}">
                        <g:textField name="email" value="${userDetailsInstance?.email}"/>
                    </td>
                </tr>
                <tr class="borderTop">
                    <td class="formLbl">
                        <label for="mobilePhoneNo"><g:message code="userDetails.mobilePhoneNo.label"/></label>
                    </td>
                    <td colspan="2" class="value ${hasErrors(bean: userDetailsInstance, field: 'mobilePhoneNo', 'errors')}">
                        <g:textField name="mobilePhoneNo" value="${userDetailsInstance?.mobilePhoneNo}"/>
                    </td>
                </tr>
                <tr class="borderTop">
                    <td class="formLbl singgleTr"><g:message code="userDetails.status.label"/></td>
                    <td class="value" width="100px">
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
                    <td>    
                        <span><g:actionSubmit class="save" action="reset"
                                                     value="${message(code: 'default.button.reset.label')}"
                                                     onclick="return confirm('${message(code: 'default.button.delete.confirm.message')}');"/>
                                <g:actionSubmit class="redbtn" action="block"
                                                     value="${message(code: 'default.button.block.label')}"
                                                     onclick="return confirm('${message(code: 'default.button.delete.confirm.message')}');"/></span>
                    </td>
                </tr>
                <tr class="borderTop">
                    <td class="formLbl singgleTr" colspan="3">
                        <label for="role"><g:message code="role.label"/></label>
                    </td>
                </tr>                
                </tbody>
             </table>
             
             <table id="roleTbl" class="form" width="100%">
                <g:each in="${roleUserList}" status="i" var="roleUser">
                    <tr id="role${i}">
                        <td class="formLbl">
                            <g:textField class="roleName" name="roleName" id="roleName${i}"
                                         style='float: left;' value="${roleUser}" size="30"/>
							<g:if test="${i == 0}">
							     &nbsp;
							</g:if>
							<g:else>
								<input type="button" class="redbtn" value="Remove" 
								    style="float: left; margin: 5px 0px 0px 4px;" onclick="removeRole(${i })"/>
							</g:else>
                        </td>
                    </tr>
                </g:each>
               </table>
			   
			   <table class="form" width="100%">
				   <tr>
					   <td class="formLbl" colspan="2">
						   <input type="button" value="${message(code: 'default.button.add.label')}"
                               onclick="addRole()"/>
					   </td>
				   </tr>
				   </tbody>
			   </table>

            <div class="buttons">
                <span class="button"><g:actionSubmit class="save" action="update"
                                                     value="${message(code: 'default.button.update.label')}"/></span>
                <span class="button"><input type="button" class="back"
                                            value="${message(code: 'default.button.back.label')}"
                                            onclick='location.href = "${createLink(url: [controller: 'userDetails', action: 'index'])}"'/>
                </span>
            </div>
        </g:form>
    </div>

</div>
<script type="text/javascript">
    var urlGetRoleLikeName = "${createLink(url: [controller: 'role', action: 'getRoleLikeName'])}";
    var roleCount = ${roleUserList?.size()} +0;
    var delay = ${autocom.get("delay")};
    var minLength = ${autocom.get("minLength")};
</script>
<asset:javascript src='/perpage/userDetailsEdit.js'/>
</body>
</html>
