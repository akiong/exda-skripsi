

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="secure2" />
        <g:set var="entityName" value="${message(code: 'user.label')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <div class="contentbar"><h1>${entityName}</h1></div>

		    <div class="contentbox">
		       <div class="transDetail"><g:message code="default.create.label" args="[entityName]"/></div>
	            <g:if test="${flash.message}">
	            <div class="message">${flash.message}</div>
	            </g:if>
	            <g:hasErrors model="${[userDetails: cifUserInstance?.userDetails, user: cifUserInstance?.userDetails?.user, cifUserInstance: cifUserInstance]}">
		            <div class="errors">
		                <g:hasErrors bean="${cifUserInstance?.userDetails}">
		                    <g:renderErrors bean="${cifUserInstance?.userDetails}" as="list"/>
		                </g:hasErrors>
		                <g:hasErrors bean="${cifUserInstance?.userDetails?.user}">
                            <g:renderErrors bean="${cifUserInstance?.userDetails?.user}" as="list"/>
                        </g:hasErrors>
		                <g:hasErrors bean="${cifUserInstance}">
		                    <g:renderErrors bean="${cifUserInstance}" as="list"/>
		                </g:hasErrors>
		            </div>
		        </g:hasErrors>
	            <g:form action="save" useToken="true">
	               <g:hiddenField name="id" value="${cifUserInstance?.cif?.id}"/>
	                <div class="dialog">
	                    <table class="form" width="100%">
	                        <tbody>
	                        	<tr>
					                <td class="formLbl">
					                    <label for="firstName"><g:message code="userDetails.firstName.label"/></label>
					                </td>
					                <td class="value ${hasErrors(bean: cifUserInstance, field: 'firstName', 'errors')}">
					                    <input type="text" maxlength="100" id="firstName" name="firstName"
					                           value="${cifUserInstance?.firstName}"/>
					                </td>
					            </tr>
					            
					            <tr class="borderTop">
					                <td class="formLbl">
					                    <label for="lastName"><g:message code="userDetails.lastName.label"/></label>
					                </td>
					                <td class="value ${hasErrors(bean: cifUserInstance, field: 'lastName', 'errors')}">
					                    <input type="text" maxlength="100" id="lastName" name="lastName"
					                           value="${cifUserInstance?.lastName}"/>
					                </td>
					            </tr>
					            	                        
	                            <tr class="borderTop">
					                <td class="formLbl">
					                    <label for="username"><g:message code="userDetails.userID.label"/></label>
					                </td>
					                <td class="value ${hasErrors(bean: cifUserInstance.userDetails, field: 'user.username', 'errors')}">
					                    <input type="text" maxlength="50" id="username" name="userDetails.user.username"
					                           value="${cifUserInstance?.userDetails?.user?.username}"/>
					                    <span id="idAvailable"></span>
					                </td>
					            </tr>
					            <tr class="borderTop">
					                <td class="formLbl">
					                    <label for="useralias"><g:message code="userDetails.userAlias.label"/></label>
					                </td>
					                <td class="value ${hasErrors(bean: cifUserInstance.userDetails, field: 'userAlias', 'errors')}">
					                    <input type="text" maxlength="100" id="useralias" name="userDetails.userAlias"
					                           value="${cifUserInstance?.userDetails?.userAlias}"/>
					                </td>
					            </tr>
					            
					            <tr class="borderTop">
					                <td class="formLbl">
					                    <label for="email"><g:message code="userDetails.email.label"/></label>
					                </td>
					                <td class="value ${hasErrors(bean: cifUserInstance.userDetails, field: 'email', 'errors')}">
					                    <input type="text" maxlength="100" id="email" name="userDetails.email"
					                           value="${cifUserInstance?.userDetails?.email}"/>
					                    <span id="emailAvailable"></span>
					                </td>
					            </tr>
								 
					            <tr class="borderTop">
					                <td class="formLbl">
					                    <label for="mobile"><g:message code="userDetails.mobilePhoneNo.label"/></label>
					                </td>
					                <td class="value ${hasErrors(bean: cifUserInstance.userDetails, field: 'mobilePhoneNo', 'errors')}">
					                    <g:textField id="mobile" name="userDetails.mobilePhoneNo" maxlength="15" class="numeric"
							                         value="${cifUserInstance?.userDetails?.mobilePhoneNo}"/>
					                </td>
					            </tr>
					            
					            <tr class="borderTop">
					                <td class="formLbl topPadding"><label for="role"><g:message code="role.label"/></label></td>
					                <td class="value">
					                	<div><g:checkBox name="bo" checked="${cifUserInstance?.bo.equals('Y')}"/> <g:message code="bo.label"/></div>
					                	<div><g:checkBox name="finance" checked="${cifUserInstance?.finance.equals('Y')}" /> <g:message code="finance.label"/></div>
					               	</td>
					            </tr>
	                        
	                        </tbody>
	                    </table>
	                </div>
	                <div class="buttons">
		                <span class="button"><input type="button" class="list redbtn" value="${message(code: 'default.button.back.label')}"
		                                            onclick='location.href = "${createLink(url: [controller: 'cifUser', action: 'list'])}"'/>
		                </span>
		                <span class="button"><input class="save" type="button" value="${message(code: 'default.button.create.label')}" onclick="checkValidation()"/>
		                </span>
		                
		            </div>
	            </g:form>
            </div>
        </div>
        <div id="firstNameBlank" style="display:none;" title="${message(code: 'userDetails.firstName.label')}">
            <p>${message(code: 'default.blank.message', args: [message(code: 'userDetails.firstName.label')])}</p>
        </div>
        <div id="lastNameBlank" style="display:none;" title="${message(code: 'userDetails.lastName.label')}">
            <p>${message(code: 'default.blank.message', args: [message(code: 'userDetails.lastName.label')])}</p>
        </div>
        <div id="usernameBlank" style="display:none;" title="${message(code: 'userDetails.userID.label')}">
            <p>${message(code: 'default.blank.message', args: [message(code: 'userDetails.userID.label')])}</p>
        </div>
        <div id="userAliasBlank" style="display:none;" title="${message(code: 'userDetails.userAlias.label')}">
            <p>${message(code: 'default.blank.message', args: [message(code: 'userDetails.userAlias.label')])}</p>
        </div>
        <div id="emailBlank" style="display:none;" title="${message(code: 'userDetails.email.label')}">
            <p>${message(code: 'default.blank.message', args: [message(code: 'userDetails.email.label')])}</p>
        </div>
        <div id="phoneBlank" style="display:none;" title="${message(code: 'userDetails.mobilePhoneNo2.label')}">
            <p>${message(code: 'default.blank.message', args: [message(code: 'userDetails.mobilePhoneNo2.label')])}</p>
        </div>
        <div id="phoneInvalid" style="display:none;" title="${message(code: 'userDetails.mobilePhoneNo2.label')}">
            <p>${message(code: 'cif.phoneNo2.matches.invalid')}</p>
        </div>
        <script type="text/javascript"/>
			var urlImgSpinner = "${resource(dir: 'images', file: 'spinner.gif')}";
			var url = "${createLink(url: [controller: 'userDetails', action: 'isUsernameAvailable'])}";
			var urlImgOk = "${resource(dir: 'images', file: 'ok.gif')}";
			var urlImgFalse = "${resource(dir: 'images', file: 'false.png')}";
			var username = "${cifUserInstance?.userDetails?.user?.username}";
		</script>
		<asset:javascript src='/perpage/corporateUserRegistration.js' />
    </body>
</html>
