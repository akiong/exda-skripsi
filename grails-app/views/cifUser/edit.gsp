

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="secure2" />
        <g:set var="entityName" value="${message(code: 'user.label')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
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
		        <g:hasErrors model="${[userDetails: cifUserInstance?.userDetails, cifUserInstance: cifUserInstance]}">
		            <div class="errors">
				        <g:hasErrors bean="${cifUserInstance?.userDetails}">
				            <g:renderErrors bean="${cifUserInstance?.userDetails}" as="list"/>
				        </g:hasErrors>
				        <g:hasErrors bean="${cifUserInstance}">
				            <g:renderErrors bean="${cifUserInstance}" as="list"/>
				        </g:hasErrors>
				    </div>
		        </g:hasErrors>
	            <g:form action="update" useToken="true">
	                <g:hiddenField name="id" value="${cifUserInstance?.id}"/>
                    <g:hiddenField name="version" value="${cifUserInstance?.version}"/>
	                <div class="dialog">
	                    <table class="form" width="100%">
	                        <tbody>
	                        	<tr>
				                    <td class="formLbl">
				                        <label for="firstName"><g:message code="userDetails.firstName.label"/></label>
				                    </td>
				                    <td class="value ${hasErrors(bean: cifUserInstance, field: 'firstName', 'errors')}" colspan="2">
				                        <input type="text" maxlength="100" id="firstName" name="firstName"
				                               value="${cifUserInstance?.firstName}"/>
				                    </td>
				                </tr>
				                
				                <tr class="borderTop">
				                    <td class="formLbl">
				                        <label for="lastName"><g:message code="userDetails.lastName.label"/></label>
				                    </td>
				                    <td class="value ${hasErrors(bean: cifUserInstance, field: 'lastName', 'errors')}" colspan="2">
				                        <input type="text" maxlength="100" id="lastName" name="lastName"
				                               value="${cifUserInstance?.lastName}"/>
				                    </td>
				                </tr>
				                
	                            <tr>
				                    <td class="formLbl singgleTr">
				                        <label for="username"><g:message code="userDetails.userID.label"/></label>
				                    </td>
				                    <td class="value" colspan="2">${cifUserInstance?.userDetails?.user?.username}</td>
				                </tr>
				                
				                <tr class="borderTop">
				                    <td class="formLbl">
				                        <label for="useralias"><g:message code="userDetails.userAlias.label"/></label>
				                    </td>
				                    <td class="value ${hasErrors(bean: cifUserInstance.userDetails, field: 'userAlias', 'errors')}" colspan="2">
				                        <input type="text" maxlength="100" id="useralias" name="userDetails.userAlias"
				                               value="${cifUserInstance?.userDetails?.userAlias}"/>
				                    </td>
				                </tr>
				                
				                <tr class="borderTop">
				                    <td class="formLbl">
				                        <label for="email"><g:message code="userDetails.email.label"/></label>
				                    </td>
				                    <td class="value ${hasErrors(bean: cifUserInstance.userDetails, field: 'email', 'errors')}" colspan="2">
				                        <input type="text" maxlength="100" id="email" name="userDetails.email"
				                               value="${email ?: cifUserInstance?.userDetails?.email}"/>
				                        <span id="emailAvailable"></span>
				                    </td>
				                </tr>
				                
				                <tr class="borderTop">
				                    <td class="formLbl">
				                        <label for="mobile"><g:message code="userDetails.mobilePhoneNo.label"/></label>
				                    </td>
				                    <td class="value ${hasErrors(bean: cifUserInstance.userDetails, field: 'mobilePhoneNo', 'errors')}" colspan="2">
				                        <g:textField id="mobile" name="userDetails.mobilePhoneNo" class="numeric"
							                         value="${cifUserInstance?.userDetails?.mobilePhoneNo}"/>
				                    </td>
				                </tr>
	                            
	                            <tr class="borderTop">
					                <td class="formLbl topPadding"><label for="role"><g:message code="role.label"/></label></td>
					                <td class="value" colspan="2">
										 <div><g:checkBox name="bo" checked="${cifUserInstance?.bo.equals('Y')}"/> <g:message code="bo.label"/></div>
					                	<div><g:checkBox name="finance" checked="${cifUserInstance?.finance.equals('Y')}" /> <g:message code="finance.label"/></div>
					               	</td>
					            </tr>
				                
				                <tr class="borderTop">
				                    <td class="formLbl"><g:message code="userDetails.status.label"/></td>
				                    <td class="value" width="100px">
				                        <span id="userStatus">
				                             <g:if test="${cifUserInstance?.userDetails?.status.equals('1')}"><g:message
				                                     code="userDetails.status.inactive.label"/></g:if>
				                             <g:elseif test="${cifUserInstance?.userDetails?.status.equals('2')}"><g:message
				                                     code="userDetails.status.active.label"/></g:elseif>
				                             <g:elseif test="${cifUserInstance?.userDetails?.status.equals('3')}"><g:message
				                                     code="userDetails.status.locked.label"/></g:elseif>
				                             <g:else><g:message code="userDetails.status.blocked.label"/></g:else>
				                         </span>
				                    </td>
				                    <td>
				                        <span>
				                            <input id="resetPassBtn" type="button" class="reset"
				                                   value="${message(code: 'default.button.reset.label')}"/>
				                            <input id="blockStatusBtn" type="button" class="block redbtn"
				                                   value="${message(code: 'default.button.block.label')}"/>
				                        </span>
				                        <span id="loadingRequest"></span>
				                    </td>               
				                </tr>
	                        
	                        </tbody>
	                    </table>
	                </div>
	                <div class="buttons">
	                    <span class="button"><input class="update" type="button" value="${message(code: 'default.button.update.label')}" onclick="checkValidation()"/></span>
                        <span class="button"><input type="button" class=list value="${message(code: 'default.button.back.label')}" onclick='location.href = "${createLink(url: [controller: 'cifUser', action: 'show', id:cifUserInstance?.id])}"'/></span>
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
        <div id="statusBlockError" style="display:none;" title="${message(code: 'userDetails.status.label')}">
            <p>${message(code: 'default.not.block.message', args: [message(code: 'userDetails.status.label'), ''])}</p>
        </div>
        <div id="statusResetError" style="display:none;" title="${message(code: 'userDetails.status.label')}">
            <p>${message(code: 'default.not.reset.message', args: [message(code: 'userDetails.status.label'), ''])}</p>
        </div>
        
        <div id="statusResetInvalid" style="display:none;" title="${message(code: 'userDetails.status.label')}">
            <p>${message(code: 'newEmail.notActive.message')}</p>
        </div>
        <script type="text/javascript">
		    var urlImgSpinner = "${resource(dir:'images',file:'spinner.gif')}";
		    var urlBlockUser = "${createLink(url: [controller: 'cifUser', action: 'block'])}";
		    var urlResetPassword = "${createLink(url: [controller: 'cifUser', action: 'reset'])}";
		    var id = ${cifUserInstance?.id};
		    var username = "${cifUserInstance?.userDetails?.user?.username}";
		    var imgOk = "${resource(dir:'images',file:'ok.gif')}";
		    var imgFalse = "${resource(dir:'images',file:'false.png')}";
		    var inactive = "${message(code:'userDetails.status.inactive.label')}"
		    var block = "${message(code:'userDetails.status.blocked.label')}"
		    var userStatus = $('#userStatus').html().replace(/^\s+|\s+$/g, '');
		</script>
		<asset:javascript src='/perpage/editCorporateUser.js'/>
    </body>
</html>
