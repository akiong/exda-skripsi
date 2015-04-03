<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="secure2" />
        <g:set var="entityName" value="${message(code: 'cif.label')}" />
        <title><g:message code="default.register.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <div class="contentbar"><h1><g:message code="default.register.label" args="[entityName]" /></h1></div>
            <div class="contentbox">
            	
	            <g:if test="${flash.message}">
	            <div class="message">${flash.message}</div>
	            </g:if>
	            <g:if test="${flash.error}">
	            <div class="errors"><ul><li>${flash.error}</li></ul></div>
	            </g:if>
	            <g:hasErrors model="${[cifInstance: cifInstance, userDetailsInstance1: userDetailsInstance1, userDetailsInstance2: userDetailsInstance2, user1: userDetailsInstance1?.user, user2: userDetailsInstance2?.user]}">
	            <div class="errors">
	                <g:renderErrors bean="${cifInstance}" as="list" />
	                <g:renderErrors bean="${userDetailsInstance1}" as="list" />
	                <g:renderErrors bean="${userDetailsInstance2}" as="list" />
	                <g:renderErrors bean="${userDetailsInstance1?.user}" as="list" />
	                <g:renderErrors bean="${userDetailsInstance2?.user}" as="list" />
	            </div>
	            </g:hasErrors>
	            
	            <g:form action="save" useToken="true">
	            	<div id="step1">
		            <div class="transDetail">${entityName}</div>
		                <div class="dialog">
		                    <table class="form" width="100%">
		                        <tbody>
		                        	<tr >
								        <td class="formLbl"><label for="cifId"><g:message code="cif.cifId.label"/></label></td>
								        <td class="value ${hasErrors(bean: cifInstance, field: 'cifId', 'errors')}">
								            <g:textField id="cifId" name="cifId" maxlength="100" value="${cifInstance?.cifId}"/>
								            <span id="corpNameAvailable"></span>
								        </td>
								    </tr>
	                
		                           <tr class="borderTop">
								        <td class="formLbl"><label for="corpName"><g:message code="cif.corpName.label"/></label></td>
								        <td class="value ${hasErrors(bean: cifInstance, field: 'corpName', 'errors')}">
								            <g:textField id="corpName" name="corpName" maxlength="100" value="${cifInstance?.corpName}"/>								            
								        </td>
								    </tr>								    
								    
								
								    <tr class="borderTop">
								        <td class="formLbl topPadding"><label for="address1"><g:message code="cif.address1.label"/></label></td>
								        <td class="value ${hasErrors(bean: cifInstance, field: 'address1', 'errors')}">
								            <g:textArea name="address1" value="${cifInstance?.address1}"/>
								        </td>
								    </tr>
								    
								    <tr class="borderTop">
								        <td class="formLbl topPadding"><label for="address2"><g:message code="cif.address2.label"/></label></td>
								        <td class="value ${hasErrors(bean: cifInstance, field: 'address2', 'errors')}">
								            <g:textArea name="address2" value="${cifInstance?.address2}"/>
								        </td>
								    </tr>
								  					                
					                <tr class="borderTop">
					                    <td class="formLbl"><label for="city"><g:message code="cif.city.label"/></label></td>
		    
					                    <td class="value ${hasErrors(bean: cifInstance, field: 'city', 'errors')}">
					                        <g:select id="city" name="city.id"
						                      from="${cityList}" optionKey="id" value="${cifInstance?.city?.id}"/>
					                    </td>
					                </tr>
					                
					                
					                <tr>
										<td colspan="2" class="detail">
											<g:message code="cif.pic1.label" />
										</td>
									</tr>
									
									<tr>
					                    <td class="formLbl">
					                        <label for="firstName1"><g:message code="userDetails.firstName.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: userDetailsInstance1, field: 'firstName', 'errors')}">
					                        <g:textField id="firstName1" name="pic1.firstName" maxlength="100" value="${userDetailsInstance1?.firstName}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="lastName1"><g:message code="userDetails.lastName.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: userDetailsInstance1, field: 'lastName', 'errors')}">
					                        <g:textField id="lastName1" name="pic1.lastName" maxlength="100" value="${userDetailsInstance1?.lastName}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="username1"><g:message code="userDetails.userID.label"/></label>
					                    </td>
					
					                    <td class="value ${hasErrors(bean: userDetailsInstance1?.user, field: 'username', 'errors')}">
					                        <input type="text" maxlength="100" id="username1" name="pic1.username"
					                               value="${userDetailsInstance1?.user?.username}"/>
					                        <span id="usernameAvailable1"></span>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="userAlias1"><g:message code="userDetails.userAlias.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: userDetailsInstance1, field: 'userAlias', 'errors')}">
					                        <g:textField id="userAlias1" name="pic1.userAlias" maxlength="100" value="${userDetailsInstance1?.userAlias}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="email1"><g:message code="userDetails.email.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: userDetailsInstance1, field: 'email', 'errors')}">
					                        <g:textField id="email1" name="pic1.email" value="${userDetailsInstance1?.email}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="mobilePhoneNo1"><g:message code="userDetails.mobilePhoneNo.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: userDetailsInstance1, field: 'mobilePhoneNo', 'errors')}">
					                        <g:textField id="mobilePhoneNo1" class="numeric" name="pic1.mobilePhoneNo" value="${userDetailsInstance1?.mobilePhoneNo}"/>
					                    </td>
					                </tr>
	                
									<tr>
										<td colspan="2" class="detail">
											<g:message code="cif.pic2.label" />
										</td>
									</tr>
									
									<tr>
					                    <td class="formLbl">
					                        <label for="firstName2"><g:message code="userDetails.firstName.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: userDetailsInstance2, field: 'firstName', 'errors')}">
					                        <g:textField id="firstName2" name="pic2.firstName" maxlength="100" value="${userDetailsInstance2?.firstName}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="lastName2"><g:message code="userDetails.lastName.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: userDetailsInstance2, field: 'lastName', 'errors')}">
					                        <g:textField id="lastName2" name="pic2.lastName" maxlength="100" value="${userDetailsInstance2?.lastName}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="username2"><g:message code="userDetails.userID.label"/></label>
					                    </td>
					
					                    <td class="value ${hasErrors(bean: userDetailsInstance2?.user, field: 'username', 'errors')}">
					                        <input type="text" maxlength="100" id="username2" name="pic2.username"
					                               value="${userDetailsInstance2?.user?.username}"/>
					                        <span id="usernameAvailable2"></span>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="userAlias2"><g:message code="userDetails.userAlias.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: userDetailsInstance2, field: 'userAlias', 'errors')}">
					                        <g:textField id="userAlias2" name="pic2.userAlias" maxlength="100" value="${userDetailsInstance2?.userAlias}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="email2"><g:message code="userDetails.email.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: userDetailsInstance2, field: 'email', 'errors')}">
					                        <g:textField id="email2" name="pic2.email" value="${userDetailsInstance2?.email}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="mobilePhoneNo2"><g:message code="userDetails.mobilePhoneNo.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: userDetailsInstance2, field: 'mobilePhoneNo', 'errors')}">
					                        <g:textField id="mobilePhoneNo2" class="numeric" name="pic2.mobilePhoneNo" value="${userDetailsInstance2?.mobilePhoneNo}"/>
					                    </td>
					                </tr>
								    
		                        </tbody>
		                        
		                    </table>
		                </div>
		                
		                <div class="buttons">
		                    <span class="button"><input type="button" class="redbtn" value="${message(code: 'default.button.back.label')}"
	                                            onclick='location.href = "${createLink(url: [controller: 'cif', action: 'list'])}"'/>
	                        </span>
	                
	                        <span><input type="button" name="Submit" value="${message(code: 'default.button.create.label')}"
	                             onclick="checkData();"/></span>
		                </div>
	                </div>
	            </g:form>		        
            </div>
        </div>
        
		<div id="corpNameBlank" style="display:none;" title="${message(code: 'cif.corpName.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'cif.corpName.label')])}</p>
		</div>
		<div id="corpIdBlank" style="display:none;" title="${message(code: 'cif.cifId.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'cif.cifId.label')])}</p>
		</div>
		<div id="address1Blank" style="display:none;" title="${message(code: 'cif.address1.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'cif.address1.label')])}</p>
		</div>
		<div id="firstNameBlank" style="display:none;" title="${message(code: 'userDetails.firstName.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'userDetails.firstName.label')])}</p>
		</div>		
		<div id="lastNameBlank" style="display:none;" title="${message(code: 'userDetails.lastName.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'userDetails.lastName.label')])}</p>
		</div>
		<div id="userIdBlank" style="display:none;" title="${message(code: 'userDetails.userID.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'userDetails.userID.label')])}</p>
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
		<div id="userIdSame" style="display:none;" title="${message(code: 'userDetails.userID.label')}">
		    <p>${message(code: 'default.same.message', args: [message(code: 'userDetails.userID.label')])}</p>
		</div>
		
		<script type="text/javascript">
			var urlIsUsernameAvailable = "${createLink(url: [controller: 'userDetails', action: 'isUsernameAvailable'])}";
			var urlIsCorpnameAvailable = "${createLink(url: [controller: 'cif', action: 'isCorpIdAvailable'])}";
			var imgSpinner = "${resource(dir:'images',file:'spinner.gif')}";
		    var imgOk = "${resource(dir:'images',file:'ok.gif')}";
		    var imgFalse = "${resource(dir:'images',file:'false.png')}";
		    var btnremove = "${message(code: 'default.button.remove.label')}";
		</script>
		<asset:javascript src='/perpage/corporateRegistration.js'/>
    </body>
</html>
