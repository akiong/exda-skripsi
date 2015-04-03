

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="secure2" />
        <g:set var="entityName" value="${message(code: 'member.label')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <div class="contentbar"><h1><g:message code="group.member.label" /></h1></div>

		    <div class="contentbox">
		       <div class="transDetail"><g:message code="default.edit.label" args="[entityName]"/></div>
	            <g:if test="${flash.message}">
	            <div class="message">${flash.message}</div>
	            </g:if>
	            <g:if test="${flash.error}">
	            <div class="errors"><ul><li>${flash.error}</li></ul></div>
	            </g:if>
	            <g:hasErrors model="${[userDetails: cifUser?.userDetails, user: cifUser?.userDetails?.user, cifUser: cifUser]}">
		            <div class="errors">
		                <g:hasErrors bean="${cifUser?.userDetails}">
		                    <g:renderErrors bean="${cifUser?.userDetails}" as="list"/>
		                </g:hasErrors>
		                <g:hasErrors bean="${cifUser?.userDetails?.user}">
                            <g:renderErrors bean="${cifUser?.userDetails?.user}" as="list"/>
                        </g:hasErrors>
		                <g:hasErrors bean="${cifUser}">
		                    <g:renderErrors bean="${cifUser}" as="list"/>
		                </g:hasErrors>
		            </div>
		        </g:hasErrors>
	            <g:form action="update" useToken="true">
	            	<g:hiddenField name="id" value="${member?.id}"/>
                    <g:hiddenField name="version" value="${member?.version}"/>
	                <div class="dialog">
	                    <table class="form" width="100%">
	                        <tbody>
	                        	<tr>
				                    <td class="formLbl">
				                        <label for="firstName"><g:message code="userDetails.firstName.label"/></label>
				                    </td>
				                    <td colspan="2" class="value ${hasErrors(bean: cifUser, field: 'firstName', 'errors')}">
				                        <g:textField name="firstName" maxlength="100" value="${cifUser?.firstName}"/>
				                    </td>
				                </tr>
				
				                <tr class="borderTop">
				                    <td class="formLbl">
				                        <label for="lastName"><g:message code="userDetails.lastName.label"/></label>
				                    </td>
				                    <td colspan="2" class="value ${hasErrors(bean: cifUser, field: 'lastName', 'errors')}">
				                        <g:textField name="lastName" maxlength="100" value="${cifUser?.lastName}"/>
				                    </td>
				                </tr>
				
				                <tr class="borderTop">
				                    <td class="formLbl singgleTr">
				                        <label for="username"><g:message code="userDetails.userID.label"/></label>
				                    </td>
				
				                    <td colspan="2" class="value">
				                        ${cifUser?.userDetails?.user?.username}
				                    </td>
				                </tr>
				
				                <tr class="borderTop">
				                    <td class="formLbl">
				                        <label for="userAlias"><g:message code="userDetails.userAlias.label"/></label>
				                    </td>
				                    <td colspan="2" class="value ${hasErrors(bean: cifUser?.userDetails, field: 'userAlias', 'errors')}">
				                        <g:textField id="userAlias" name="userDetails.userAlias" maxlength="100" value="${cifUser?.userDetails?.userAlias}"/>
				                    </td>
				                </tr>
				
				                <tr class="borderTop">
				                    <td class="formLbl">
				                        <label for="email"><g:message code="userDetails.email.label"/></label>
				                    </td>
				                    <td colspan="2" class="value ${hasErrors(bean: cifUser?.userDetails, field: 'email', 'errors')}">
				                    	<g:if test="${email}">
			                        		<g:textField id="email" name="userDetails.email" value="${email}"/>
					                    </g:if>
					                    <g:else>
					                        <g:textField id="email" name="userDetails.email" value="${cifUser?.userDetails?.email}"/>
					                    </g:else>
				                        
				                    </td>
				                </tr>
				
				                <tr class="borderTop">
				                    <td class="formLbl">
				                        <label for="mobilePhoneNo"><g:message code="userDetails.mobilePhoneNo.label"/></label>
				                    </td>
				                    <td colspan="2" class="value ${hasErrors(bean: cifUser?.userDetails, field: 'mobilePhoneNo', 'errors')}">
				                        <g:textField id="mobilePhoneNo" class="numeric" name="userDetails.mobilePhoneNo" value="${cifUser?.userDetails?.mobilePhoneNo}"/>
				                    </td>
				                </tr>
				                
				                <%--<tr class="borderTop">
				                    <td class="formLbl">
				                        <label for="userLimit"><g:message code="group.userLimit.label"/></label>
				                    </td>
				                    <td colspan="2" class="value ${hasErrors(bean: cifUser, field: 'userLimit', 'errors')}">
				                        <input type="text" id="userLimit" class="price" name="userLimit" maxlength="100" value="${member?.memberLimit?.userLimit}"/>
				                    </td>
				                </tr>
				                
				                --%><tr class="borderTop">
				                    <td class="formLbl">
				                       <g:message code="position.label"/>
				                    </td>
				                    <td colspan="2">
		                            	<g:if test="${cifUser.position.equals('1')}">
		                            		<g:message code="leader.label"/>
		                            	</g:if>
		                            	<g:else>
		                            		<g:message code="member.label"/>
		                            	</g:else>
		                            </td>
				                </tr>
				                
				                <tr class="borderTop">
				                	<td class="formLbl">
				                        <g:message code="userDetails.status.label"/>
				                    </td>
					                <td id="userstattd">
		                        		<g:if test="${cifUser?.userDetails?.status == '1'}">
					                        <g:message code="userDetails.status.inactive.label"/>
					                    </g:if>
					
					                    <g:elseif test="${cifUser?.userDetails?.status == '2'}">
					                        <g:message code="userDetails.status.active.label"/>
					                    </g:elseif>
					
					                    <g:elseif test="${cifUser?.userDetails?.status == '3'}">
					                        <g:message code="userDetails.status.locked.label"/>
					                    </g:elseif>
					
					                    <g:else>
					                        <g:message code="userDetails.status.blocked.label"/>
					                    </g:else>
		                        	</td>	                        	
		                        	<td>
		                        		<input id="resetPassBtn" type="button" class="reset" value="${message(code: 'default.button.reset.label')}"/>
				                        <input id="blockStatusBtn" type="button" class="block redbtn" value="${message(code: 'default.button.block.label')}"/>
		                        	</td>
	                        	</tr>
	                        
	                        </tbody>
	                    </table>
	                </div>
	                <div class="buttons">		                
		                <div class="buttons">
	                    <span class="button"><input type="button" class="save"
                                            value="${message(code: 'default.button.update.label')}" onClick="checkData();"/>
		                </span>
		                <span class="button"><input type="button" class="back" value="${message(code: 'default.button.back.label')}"
		                                            onclick='location.href = "${createLink(url: [controller: 'member', action: 'show', id:member?.id])}"'/>
		                </span>
	                </div>
		                
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
		<div id="emailInvalid" style="display:none;" title="${message(code: 'userDetails.email.label')}">
		    <p>${message(code: 'userDetails.email.matches.invalid')}</p>
		</div>
		<div id="phoneNoBlank" style="display:none;" title="${message(code: 'cif.phoneNo.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'cif.phoneNo.label')])}</p>
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
		    var blockId = ${cifUser?.id};
		    var imgOk = "${resource(dir:'images',file:'ok.gif')}";
		    var imgFalse = "${resource(dir:'images',file:'false.png')}";
		    var inactive = "${message(code:'userDetails.status.inactive.label')}"
		    var block = "${message(code:'userDetails.status.blocked.label')}"
		</script>
		<asset:javascript src='/perpage/memberEdit.js'/>
    </body>
</html>
