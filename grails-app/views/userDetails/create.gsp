
<%@ page import="com.akiong.security.UserDetails" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="secure2"/>
    <g:set var="entityName" value="${message(code: 'userDetails.label')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<div class="body">

    <div class="contentbar"><h1>${entityName}</h1></div>

    <div class="contentbox">

        <div class="transDetail"><g:message code="default.create.label" args="[entityName]"/></div>
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
        <g:form action="save" useToken="true">
            <table class="form" width="100%">
                <tbody>
                <tr>
                    <td class="formLbl">
                        <label for="firstName"><g:message code="userDetails.firstName.label"/></label>
                    </td>
                    <td class="value ${hasErrors(bean: userDetailsInstance, field: 'firstName', 'errors')}">
                        <g:textField name="firstName" maxlength="100" value="${userDetailsInstance?.firstName}"/>
                    </td>
                </tr>

                <tr class="borderTop">
                    <td class="formLbl">
                        <label for="lastName"><g:message code="userDetails.lastName.label"/></label>
                    </td>
                    <td class="value ${hasErrors(bean: userDetailsInstance, field: 'lastName', 'errors')}">
                        <g:textField name="lastName" maxlength="100" value="${userDetailsInstance?.lastName}"/>
                    </td>
                </tr>

                <tr class="borderTop">
                    <td class="formLbl">
                        <label for="username"><g:message code="userDetails.userID.label"/></label>
                    </td>

                    <td class="value ${hasErrors(bean: userDetailsInstance, field: 'user.username', 'errors')}">
                        <input type="text" maxlength="20" id="username" name="username"
                               value="${userDetailsInstance?.user?.username}"/>
                        <span id="usernameAvailable"></span>
                    </td>
                </tr>

                <tr class="borderTop">
                    <td class="formLbl">
                        <label for="userAlias"><g:message code="userDetails.userAlias.label"/></label>
                    </td>
                    <td class="value ${hasErrors(bean: userDetailsInstance, field: 'userAlias', 'errors')}">
                        <g:textField name="userAlias" maxlength="100" value="${userDetailsInstance?.userAlias}"/>
                    </td>
                </tr>

                <tr class="borderTop">
                    <td class="formLbl">
                        <label for="email"><g:message code="userDetails.email.label"/></label>
                    </td>
                    <td class="value ${hasErrors(bean: userDetailsInstance, field: 'email', 'errors')}">
                        <g:textField name="email" value="${userDetailsInstance?.email}"/>
                    </td>
                </tr>

                <tr class="borderTop">
                    <td class="formLbl">
                        <label for="mobilePhoneNo"><g:message code="userDetails.mobilePhoneNo.label"/></label>
                    </td>
                    <td class="value ${hasErrors(bean: userDetailsInstance, field: 'mobilePhoneNo', 'errors')}">
                        <g:textField name="mobilePhoneNo" value="${userDetailsInstance?.mobilePhoneNo}"/>
                    </td>
                </tr>

                <tr class="borderTop">
                    <td class="formLbl singgleTr" colspan="2">
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
			   <span class="button"><input type="button" class="back redbtn" value="${message(code: 'default.button.back.label')}"
							onclick='location.href = "${createLink(url: [controller: 'userDetails', action: 'index'])}"'/>
				</span>
                <span class="button"><input type="submit" class="save" value="${message(code: 'default.button.create.label')}"/></span>
                
            </div>
        </g:form>
    </div>
	
</div>
<script type="text/javascript">
    var roleCount = ${roleUserList?.size()} +0;
    var urlGetRoleLikeName = "${createLink(url: [controller: 'role', action: 'getRoleLikeName'])}";
    var urlIsUsernameAvailable = "${createLink(url: [controller: 'userDetails', action: 'isUsernameAvailable'])}";
    var imgSpinner = "${resource(dir:'images',file:'spinner.gif')}";
    var imgOk = "${resource(dir:'images',file:'ok.gif')}";
    var imgFalse = "${resource(dir:'images',file:'false.png')}";
    var delay = ${autocom.get("delay")};
    var minLength = ${autocom.get("minLength")};
    var btnremove = "${message(code: 'default.button.remove.label')}";
</script>
<asset:javascript src='/perpage/userDetailsCreate.js'/>
</body>
</html>

<%--
//<td width='90%'><g:select name='roleName' id='roleName"+(rowCount)+"' from='' value='' /></td><td><input type='button' class='ui-icon ui-icon-trash' value='' onclick='removeRole("+(rowCount)+")' style='text-align:left' /></td>
 --%>
                                    		