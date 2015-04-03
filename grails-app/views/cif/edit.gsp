

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="secure2" />
        <g:set var="entityName" value="${message(code: 'cif.label')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <div class="contentbar"><h1><g:message code="default.management.label" args="[entityName]" /></h1></div>
            <div class="contentbox">
                <div class="transDetail"><g:message code="default.edit.label" args="[entityName]" /></div>
	            <g:if test="${flash.message}">
	            	<div class="message">${flash.message}</div>
	            </g:if>
	            <g:if test="${flash.error}">
	            <div class="errors"><ul><li>${flash.error}</li></ul></div>
	            </g:if>
	            <g:hasErrors model="${[cifInstance: cifInstance, userDetailsInstance1: userDetailsInstance1, userDetailsInstance2: userDetailsInstance2]}">
		            <div class="errors">
		                <g:renderErrors bean="${cifInstance}" as="list" />
		                <g:renderErrors bean="${userDetailsInstance1}" as="list" />
	                	<g:renderErrors bean="${userDetailsInstance2}" as="list" />
		            </div>
	            </g:hasErrors>
	            <g:form action="update" useToken="true">
	                <g:hiddenField name="id" value="${cifInstance?.id}"/>
                    <g:hiddenField name="version" value="${cifInstance?.version}"/>
	                <div class="dialog">
	                    <table class="form" width="100%">
	                        <tbody>
	                           
	                           <tr >
							        <td class="formLbl singgleTr"><label for="cifId"><g:message code="cif.cifId.label"/></label></td>
							        <td colspan="2" class="value">${cifInstance?.cifId}</td>
							    </tr>
	                        
	                            <tr class="borderTop">
								        <td class="formLbl"><label for="corpName"><g:message code="cif.corpName.label"/></label></td>
								        <td colspan="2" class="value ${hasErrors(bean: cifInstance, field: 'corpName', 'errors')}">
								            <g:textField id="corpName" name="corpName" maxlength="100" value="${cifInstance?.corpName}"/>								            
								        </td>
								    </tr>								    
								    
								
								    <tr class="borderTop">
								        <td class="formLbl topPadding"><label for="address1"><g:message code="cif.address1.label"/></label></td>
								        <td colspan="2" class="value ${hasErrors(bean: cifInstance, field: 'address1', 'errors')}">
								            <g:textArea name="address1" value="${cifInstance?.address1}"/>
								        </td>
								    </tr>
								    
								    <tr class="borderTop">
								        <td class="formLbl topPadding"><label for="address2"><g:message code="cif.address2.label"/></label></td>
								        <td colspan="2" class="value ${hasErrors(bean: cifInstance, field: 'address2', 'errors')}">
								            <g:textArea name="address2" value="${cifInstance?.address2}"/>
								        </td>
								    </tr>
								    
								    <tr class="borderTop">
					                    <td class="formLbl"><label for="province"><g:message code="cif.province.label"/></label></td>
		    
					                    <td colspan="2" class="value ${hasErrors(bean: cifInstance, field: 'province', 'errors')}">
					                        <g:select id="province" name="province.id"
						                      from="${provinceList}" optionKey="id" value="${cifInstance?.province?.id}"/>
					                    </td>
					                </tr>
					                
					                <tr class="borderTop">
					                    <td class="formLbl"><label for="city"><g:message code="cif.city.label"/></label></td>
		    
					                    <td colspan="2" class="value ${hasErrors(bean: cifInstance, field: 'city', 'errors')}">
					                        <g:select id="city" name="city.id"
						                      from="${cityList}" optionKey="id" value="${cifInstance?.city?.id}"/>
					                    </td>
					                </tr>
					                
					                <tr class="borderTop">
					                    <td class="formLbl"><label for="city"><g:message code="cif.rateType.label"/></label></td>
		    
					                    <td class="value ${hasErrors(bean: cifInstance, field: 'rateType', 'errors')}">
					                        <g:select id="city" name="rateType.id" noSelection="${['':message(code: 'default.none.label')]}"
						                      from="${rateTypeList}" optionKey="id" value="${cifInstance?.rateType?.id}"/>
					                    </td>
					                </tr>
					                
					                <tr class="borderTop">
					                    <td class="formLbl"><label for="network"><g:message code="network.label"/></label></td>
		    
					                    <td class="value ${hasErrors(bean: cifInstance, field: 'network', 'errors')}">
					                        <g:select id="network" name="network.id" from="${network}" optionKey="id" 
					                        	optionValue="${{it.code +' - '+it.name}}"
					                        	value="${cifInstance?.network?.id}" />
					                    </td>
					                </tr>
					                
					                <tr class="borderTop">
								        <td class="formLbl singgleTr"><label for="joinDate"><g:message code="cif.joinDate.label"/></label></td>
								        <td class="value" colspan="2"><g:formatDate format="dd/MM/yyyy"
	                                                             date="${cifInstance?.joinDate}"/></td>
								    </tr>
                                
                                <tr>
									<td colspan="3" class="detail">
										<g:message code="cif.pic1.label" />
									</td>
								</tr>
                                
                                <tr>
					                    <td class="formLbl">
					                        <label for="firstName1"><g:message code="userDetails.firstName.label"/></label>
					                    </td>
					                    <td colspan="2" class="value ${hasErrors(bean: userDetailsInstance1, field: 'firstName', 'errors')}">
					                        <g:textField id="firstName1" name="pic1.firstName" maxlength="100" value="${userDetailsInstance1?.firstName}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="lastName1"><g:message code="userDetails.lastName.label"/></label>
					                    </td>
					                    <td colspan="2" class="value ${hasErrors(bean: userDetailsInstance1, field: 'lastName', 'errors')}">
					                        <g:textField id="lastName1" name="pic1.lastName" maxlength="100" value="${userDetailsInstance1?.lastName}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl singgleTr">
					                        <label for="username1"><g:message code="userDetails.userID.label"/></label>
					                    </td>
					
					                    <td colspan="2" class="value">${userDetailsInstance1?.user?.username}
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="userAlias1"><g:message code="userDetails.userAlias.label"/></label>
					                    </td>
					                    <td colspan="2" class="value ${hasErrors(bean: userDetailsInstance1, field: 'userAlias', 'errors')}">
					                        <g:textField id="userAlias1" name="pic1.userAlias" maxlength="100" value="${userDetailsInstance1?.userAlias}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="email1"><g:message code="userDetails.email.label"/></label>
					                    </td>
					                    <td colspan="2" class="value ${hasErrors(bean: userDetailsInstance1, field: 'email', 'errors')}">
					                        <g:textField id="email1" name="pic1.email" value="${userDetailsInstance1?.email}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="mobilePhoneNo1"><g:message code="userDetails.mobilePhoneNo.label"/></label>
					                    </td>
					                    <td colspan="2" class="value ${hasErrors(bean: userDetailsInstance1, field: 'mobilePhoneNo', 'errors')}">
					                        <g:textField id="mobilePhoneNo1" class="numeric" name="pic1.mobilePhoneNo" value="${userDetailsInstance1?.mobilePhoneNo}"/>
					                    </td>
					                </tr>
					                
					                <tr class="borderTop">
						                <td class="formLbl singgleTr"><g:message code="userDetails.status.label"/></td>
						                <td class="value"><span id="userStatus">
						                    <g:if test="${userDetailsInstance1?.status == '1'}">
						                        <g:message code="userDetails.status.inactive.label"/>
						                    </g:if>						
						                    <g:elseif test="${userDetailsInstance1?.status == '2'}">
						                        <g:message code="userDetails.status.active.label"/>
						                    </g:elseif>						
						                    <g:elseif test="${userDetailsInstance1?.status == '3'}">
						                        <g:message code="userDetails.status.locked.label"/>
						                    </g:elseif>						
						                    <g:else>
						                        <g:message code="userDetails.status.blocked.label"/>
						                    </g:else></span>
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
					                
					                <tr>
										<td colspan="3" class="detail">
											<g:message code="cif.pic2.label" />
										</td>
									</tr>
									
									<tr>
					                    <td class="formLbl">
					                        <label for="firstName2"><g:message code="userDetails.firstName.label"/></label>
					                    </td>
					                    <td colspan="2" class="value ${hasErrors(bean: userDetailsInstance2, field: 'firstName', 'errors')}">
					                        <g:textField id="firstName2" name="pic2.firstName" maxlength="100" value="${userDetailsInstance2?.firstName}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="lastName2"><g:message code="userDetails.lastName.label"/></label>
					                    </td>
					                    <td colspan="2" class="value ${hasErrors(bean: userDetailsInstance2, field: 'lastName', 'errors')}">
					                        <g:textField id="lastName2" name="pic2.lastName" maxlength="100" value="${userDetailsInstance2?.lastName}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl singgleTr">
					                        <label for="username2"><g:message code="userDetails.userID.label"/></label>
					                    </td>
					
					                    <td colspan="2" class="value">${userDetailsInstance2?.user?.username}
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="userAlias2"><g:message code="userDetails.userAlias.label"/></label>
					                    </td>
					                    <td colspan="2" class="value ${hasErrors(bean: userDetailsInstance2, field: 'userAlias', 'errors')}">
					                        <g:textField id="userAlias2" name="pic2.userAlias" maxlength="100" value="${userDetailsInstance2?.userAlias}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="email2"><g:message code="userDetails.email.label"/></label>
					                    </td>
					                    <td colspan="2" class="value ${hasErrors(bean: userDetailsInstance2, field: 'email', 'errors')}">
					                        <g:textField id="email2" name="pic2.email" value="${userDetailsInstance2?.email}"/>
					                    </td>
					                </tr>
					
					                <tr class="borderTop">
					                    <td class="formLbl">
					                        <label for="mobilePhoneNo2"><g:message code="userDetails.mobilePhoneNo.label"/></label>
					                    </td>
					                    <td colspan="2" class="value ${hasErrors(bean: userDetailsInstance2, field: 'mobilePhoneNo', 'errors')}">
					                        <g:textField id="mobilePhoneNo2" class="numeric" name="pic2.mobilePhoneNo" value="${userDetailsInstance2?.mobilePhoneNo}"/>
					                    </td>
					                </tr>
					                
					                <tr class="borderTop">
						                <td class="formLbl singgleTr"><g:message code="userDetails.status.label"/></td>
						                <td class="value"><span id="userStatus2">
						                    <g:if test="${userDetailsInstance2?.status == '1'}">
						                        <g:message code="userDetails.status.inactive.label"/>
						                    </g:if>						
						                    <g:elseif test="${userDetailsInstance2?.status == '2'}">
						                        <g:message code="userDetails.status.active.label"/>
						                    </g:elseif>						
						                    <g:elseif test="${userDetailsInstance2?.status == '3'}">
						                        <g:message code="userDetails.status.locked.label"/>
						                    </g:elseif>						
						                    <g:else>
						                        <g:message code="userDetails.status.blocked.label"/>
						                    </g:else></span>
						                </td>
						                <td>
					                          <span>
					                              <input id="resetPassBtn2" type="button" class="reset"
					                                     value="${message(code: 'default.button.reset.label')}"/>
					                              <input id="blockStatusBtn2" type="button" class="block redbtn"
					                                     value="${message(code: 'default.button.block.label')}"/>
					                          </span>
					                          <span id="loadingRequest2"></span>
					                    </td>
						            </tr>
	                        
	                        </tbody>
	                    </table>
	                </div>
	                <div class="buttons">
	                    <span class="button"><input type="button" class="save"
                                            value="${message(code: 'default.button.update.label')}" onClick="checkData();"/>
		                </span>
		                <span class="button"><input type="button" class="back" value="${message(code: 'default.button.back.label')}"
		                                            onclick='location.href = "${createLink(url: [controller: 'cif', action: 'show', id:cifInstance?.id])}"'/>
		                </span>
	                </div>
	            </g:form>
            </div>
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
        <div id="corpNameBlank" style="display:none;" title="${message(code: 'cif.corpName.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'cif.corpName.label')])}</p>
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
		
		<script type="text/javascript">
			var urlImgSpinner = "${resource(dir:'images',file:'spinner.gif')}";
		    var urlBlockUser = "${createLink(url: [controller: 'cifUser', action: 'blockUser'])}";
		    var urlResetPassword = "${createLink(url: [controller: 'cifUser', action: 'resetPassword'])}";
		    var id = ${userDetailsInstance1?.user?.id};
		    var id2 = ${userDetailsInstance2?.user?.id};
		    var imgOk = "${resource(dir:'images',file:'ok.gif')}";
		    var imgFalse = "${resource(dir:'images',file:'false.png')}";
		    var inactive = "${message(code:'userDetails.status.inactive.label')}"
		    var block = "${message(code:'userDetails.status.blocked.label')}"
		    var userStatus = $('#userStatus').html().replace(/^\s+|\s+$/g, '');
		    var userStatus = $('#userStatus2').html().replace(/^\s+|\s+$/g, '');
		    var btnremove = "${message(code: 'default.button.remove.label')}";
		</script>
        <asset:javascript src='/perpage/editCorporate.js'/>
    </body>
</html>
