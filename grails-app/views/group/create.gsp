

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="secure2" />
        <g:set var="entityName" value="${message(code: 'grup.label')}" />
        <title><g:message code="default.add.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <div class="contentbar"><h1><g:message code="default.add.label" args="[entityName]" /></h1></div>
            <div class="contentbox">
            	
	            <g:if test="${flash.message}">
	            <div class="message">${flash.message}</div>
	            </g:if>
	            
	            <g:if test="${error}">
	            <div class="errors">
		            <g:if test="${flash.error}">
			            <ul><li>${flash.error}</li></ul>
			        </g:if>
	                <g:renderErrors bean="${groupInstance}" as="list" />
	                <g:renderErrors bean="${ownernya}" as="list" />
	                <g:renderErrors bean="${ownernya.userDetails}" as="list" />
	                <g:renderErrors bean="${ownernya?.userDetails?.user}" as="list" />
	                <g:renderErrors bean="${cifUser}" as="list" />
	                <g:renderErrors bean="${cifUser.userDetails}" as="list" />
	                <g:renderErrors bean="${cifUser?.userDetails?.user}" as="list" />
	            </div>
	            </g:if>
	            
	            <g:form action="save" useToken="true">
		            <div class="transDetail">${entityName}</div>
		                <div class="dialog">
		                    <table class="form" width="100%">
		                        <tbody>
		                        	<tr >
								        <td class="formLbl"><label for="grupId"><g:message code="grup.grupId.label"/></label></td>
								        <td class="value ${hasErrors(bean: groupInstance, field: 'grupId', 'errors')}">
								            <g:textField name="grupId" maxlength="100" value="${groupInstance?.grupId}"/>
								        </td>
								    </tr>
								    
		                        	<tr class="borderTop">
								        <td class="formLbl"><label for="name"><g:message code="grup.name.label"/></label></td>
								        <td class="value ${hasErrors(bean: groupInstance, field: 'name', 'errors')}">
								            <g:textField name="name" maxlength="200" value="${groupInstance?.name}"/>
								        </td>
								    </tr>
								    
								    <tr class="borderTop">
								        <td class="formLbl"><label for="dbaName"><g:message code="grup.dbaName.label"/></label></td>
								        <td class="value ${hasErrors(bean: groupInstance, field: 'dbaName', 'errors')}">
								            <g:textField name="dbaName" maxlength="200" value="${groupInstance?.dbaName}"/>
								        </td>
								    </tr>
								    
								    <tr>
				                        <td class="formLbl">
				                            <label for="province"><g:message code="province.label"/></label>
				                        </td>
				
				                        <td>
				                            <g:select id="province" name="province.id" value="${cif?.province?.id}"
										             noSelection="${['null':'-Choose your Province-']}"
										             from="${provinceList}" optionKey="id"
										             onchange="${remoteFunction(
			                                                     controller:'group',
			                                                     action:'findCityByProvince',
			                                                     params:'\'province=\' + escape(this.value)', 
			                                                     onComplete:'updateClassCity1(arguments[0])')}" 
										             />
				                        </td>	
		                   			 </tr>
	
				                    <tr>
			                            <td class="formLbl">
			                                <label for="city"><g:message code="city.label"/></label>
			                            </td>
			    
			                            <td>
			                                <g:select id="city" name="city.id" value="${cif?.city?.id}"
			                                                 noSelection="${['null':'-Choose your City-']}"
			                                                 from="${cityList}" optionKey="id"/>
			                            </td>   
			                        </tr>   
								    
								    <tr class="borderTop">
					                    <td class="formLbl topPadding">
					                        <label for="location"><g:message code="grup.location.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: groupInstance, field: 'location', 'errors')}">
					                        <g:textArea name="location" cols="40" rows="5" value="${groupInstance?.location}"/>
					                    </td>
					                </tr>
					                
					                <tr>
										<td colspan="2" class="detail">
											<g:message code="group.owner.label" />
										</td>
									</tr>
									
									<tr>
					                    <td class="formLbl">
					                        <label for="ofname"><g:message code="userDetails.firstName.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: owner, field: 'firstName', 'errors')}">
					                        <g:textField id="ofname" name="owner.firstName" maxlength="100" value="${ownernya?.firstName}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="olname"><g:message code="userDetails.lastName.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: owner, field: 'lastName', 'errors')}">
					                        <g:textField id="olname" name="owner.lastName" maxlength="100" value="${ownernya?.lastName}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="ousername"><g:message code="userDetails.userID.label"/></label>
					                    </td>
					
					                    <td class="value ${hasErrors(bean: owner?.userDetails, field: 'user.username', 'errors')}">
					                        <input type="text" maxlength="20" id="ousername" name="owner.userDetails.user.username"
					                               value="${ownernya?.userDetails?.user?.username}"/>
					                        <span id="usernameAvailable1"></span>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="ouserAlias"><g:message code="userDetails.userAlias.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: owner?.userDetails, field: 'userAlias', 'errors')}">
					                        <g:textField id="ouserAlias" name="owner.userDetails.userAlias" maxlength="100" value="${ownernya?.userDetails?.userAlias}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="oemail"><g:message code="userDetails.email.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: owner?.userDetails, field: 'email', 'errors')}">
					                        <g:textField id="oemail" name="owner.userDetails.email" value="${ownernya?.userDetails?.email}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="omobilePhoneNo"><g:message code="userDetails.mobilePhoneNo.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: owner?.userDetails, field: 'mobilePhoneNo', 'errors')}">
					                        <g:textField id="omobilePhoneNo" class="numeric" name="owner.userDetails.mobilePhoneNo" value="${ownernya?.userDetails?.mobilePhoneNo}"/>
					                    </td>
					                </tr>
					                
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="accoutnNo"><g:message code="account.no.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: owner, field: 'accoutnNo', 'errors')}">
					                        <g:textField id="accoutnNo" class="numeric" name="owner.accoutnNo" maxlength="100" value="${ownernya?.accoutnNo}"/>
					                    </td>
					                </tr>
					                
					                <tr>
										<td colspan="2" class="detail">
											<g:message code="group.leader.label" />
										</td>
									</tr>
									
									<tr>
					                    <td class="formLbl">
					                        <label for="firstName"><g:message code="userDetails.firstName.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: cifUser, field: 'firstName', 'errors')}">
					                        <g:textField name="firstName" maxlength="100" value="${cifUser?.firstName}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="lastName"><g:message code="userDetails.lastName.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: cifUser, field: 'lastName', 'errors')}">
					                        <g:textField name="lastName" maxlength="100" value="${cifUser?.lastName}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="username"><g:message code="userDetails.userID.label"/></label>
					                    </td>
					
					                    <td class="value ${hasErrors(bean: cifUser?.userDetails, field: 'user.username', 'errors')}">
					                        <input type="text" maxlength="20" id="username" name="userDetails.user.username"
					                               value="${cifUser?.userDetails?.user?.username}"/>
					                        <span id="usernameAvailable"></span>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="userAlias"><g:message code="userDetails.userAlias.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: cifUser?.userDetails, field: 'userAlias', 'errors')}">
					                        <g:textField id="userAlias" name="userDetails.userAlias" maxlength="100" value="${cifUser?.userDetails?.userAlias}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="email"><g:message code="userDetails.email.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: cifUser?.userDetails, field: 'email', 'errors')}">
					                        <g:textField id="email" name="userDetails.email" value="${cifUser?.userDetails?.email}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="mobilePhoneNo"><g:message code="userDetails.mobilePhoneNo.label"/></label>
					                    </td>
					                    <td class="value ${hasErrors(bean: cifUser?.userDetails, field: 'mobilePhoneNo', 'errors')}">
					                        <g:textField id="mobilePhoneNo" class="numeric" name="userDetails.mobilePhoneNo" value="${cifUser?.userDetails?.mobilePhoneNo}"/>
					                    </td>
					                </tr>
								    
		                        </tbody>
		                        
		                    </table>
		                </div>
		                
		                <div class="buttons">
		                    <span class="button"><input type="button" class="redbtn" value="${message(code: 'default.button.back.label')}"
	                                            onclick='window.location.href = "${createLink(url: [controller: 'group', action: 'list'])}"'/>
	                        </span>
	                
	                        <span><input type="button" name="Submit" value="${message(code: 'default.button.create.label')}"
	                             onclick="checkData();"/></span>
		                </div>	                
	            </g:form>		        
            </div>
        </div>
        <div id="cityBlank" style="display:none;" title="${message(code: 'branch.city.label')}">
		    <p>${message(code: 'city.select.label')}</p>
		</div>		
		<div id="provinceBlank" style="display:none;" title="${message(code: 'branch.province.label')}">
            <p>${message(code: 'province.select.label')}</p>
        </div> 
        <div id="grupIdBlank" style="display:none;" title="${message(code: 'grup.grupId.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'grup.grupId.label')])}</p>
		</div>
		<div id="groupNameBlank" style="display:none;" title="${message(code: 'grup.name.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'grup.name.label')])}</p>
		</div>
		<div id="groupDbaNameBlank" style="display:none;" title="${message(code: 'grup.dbaName.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'grup.dbaName.label')])}</p>
		</div>
		<div id="groupLocBlank" style="display:none;" title="${message(code: 'grup.location.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'grup.location.label')])}</p>
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
		<div id="accountBlank" style="display:none;" title="${message(code: 'account.no.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'account.no.label')])}</p>
		</div>

		<div id="duplicateName" style="display:none"
		     title="${message(code: 'default.duplicate.label', args: [message(code: 'grup.label')])}">
		    <p>${message(code: 'default.duplicate.msg.label', args: [message(code: 'grup.label'), message(code: 'grup.grupId.label')])}</p>
		</div>
		
		<script type="text/javascript">
			var urlIsUsernameAvailable = "${createLink(url: [controller: 'userDetails', action: 'isUsernameAvailable'])}";
			var imgSpinner = "${resource(dir:'images',file:'spinner.gif')}";
		    var imgOk = "${resource(dir:'images',file:'ok.gif')}";
		    var imgFalse = "${resource(dir:'images',file:'false.png')}";
		    var url = "${createLink(url: [controller: 'group', action: 'findById'])}";


			function updateClassCity1(e) {
		        updateCity(e, "city");
		    }
		</script>
		<asset:javascript src='/perpage/groupCreate.js'/>
    </body>
</html>
