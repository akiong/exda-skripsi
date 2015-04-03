
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="secure2" />
        <g:set var="entityName" value="${message(code: 'cif.label')}" />
        <title><g:message code="default.detail.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <div class="contentbar"><h1><g:message code="default.management.label" args="[entityName]" /></h1></div>
            <div class="contentbox">
                <div class="transDetail"><g:message code="default.detail.label" args="[entityName]" /></div>
	            <g:if test="${flash.message}">
	            <div class="message">${flash.message}</div>
	            </g:if>
	            <g:hasErrors bean="${cifInstance}">
	            <div class="errors">
	                <g:renderErrors bean="${cifInstance}" as="list" />
	            </div>
	            </g:hasErrors>
	            <div class="dialog">
	            	
	           
	                <table class="form" width="100%">
	                    <tbody>
	                    	<tr>
                                <td class="formLbl singgleTr"><g:message code="cif.cifId.label"/></td>
                
                                <td class="value">${fieldValue(bean: cifInstance, field: "cifId")}</td>
                               
                                <td class="showhide">              
                                	<input type="button" value='SHOW' id="hd"/>
                                </td>
                
                            </tr>
	                  
	                        <tr class="borderTop hide1" >
				                <td class="formLbl singgleTr"><g:message code="cif.corpName.label"/></td>
				
				                <td class="value" colspan="2">${cifInstance?.corpName}</td>
				
				            </tr>
				
				            <tr class="borderTop hide1">
				                <td class="formLbl topPadding"><g:message code="cif.address1.label"/></td>
				
				                <td class="value" colspan="2"><g:textArea name="address1" disabled="disabled" value="${cifInstance?.address1}"/></td>
				
				            </tr>
				            
				            <tr class="borderTop hide1">
				                <td class="formLbl topPadding"><g:message code="cif.address2.label"/></td>
				
				                <td class="value" colspan="2"><g:textArea name="address2" disabled="disabled" value="${cifInstance?.address2}"/></td>
				
				            </tr>
	                    
				
				            <tr class="borderTop hide1">
				                <td class="formLbl singgleTr"><g:message code="cif.city.label"/></td>
				
				                <td class="value" colspan="2">${cifInstance?.city?.city}</td>
				            </tr>
				            				            				            
				            <tr class="borderTop hide1">
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
				                <td class="formLbl singgleTr"><g:message code="userDetails.firstName.label"/></td>
				                <td class="value">${pic1?.firstName}</td>
				                <td class="showhide">              
                                	<input type="button" value='SHOW' id="hd2"/>
                                </td>
				            </tr>
				            
				            <tr class="borderTop hide2">
				                <td class="formLbl singgleTr"><g:message code="userDetails.lastName.label"/></td>
				                <td class="value" colspan="2">${pic1?.lastName}</td>
				            </tr>
				            
				            <tr class="borderTop hide2">
				                <td class="formLbl singgleTr"><g:message code="userDetails.userID.label"/></td>
				                <td class="value" colspan="2">${pic1?.user?.username}</td>
				            </tr>
				            
				            <tr class="borderTop hide2">
				                <td class="formLbl singgleTr"><g:message code="userDetails.userAlias.label"/></td>
				                <td class="value" colspan="2">${pic1?.userAlias}</td>
				            </tr>
				            
				            <tr class="borderTop hide2">
				                <td class="formLbl singgleTr"><g:message code="userDetails.email.label"/></td>
				                <td class="value" colspan="2">${pic1?.email}</td>
				            </tr>
				            
				            <tr class="borderTop hide2">
				                <td class="formLbl singgleTr"><g:message code="userDetails.mobilePhoneNo.label"/></td>
				                <td class="value" colspan="2">${pic1?.mobilePhoneNo}</td>
				            </tr>
				            
				            <tr class="borderTop  hide2">
				                <td class="formLbl singgleTr"><g:message code="userDetails.status.label"/></td>
				                <td class="value"><span id="userStatus">
				                    <g:if test="${pic1?.status == '1'}">
				                        <g:message code="userDetails.status.inactive.label"/>
				                    </g:if>				
				                    <g:elseif test="${pic1?.status == '2'}">
				                        <g:message code="userDetails.status.active.label"/>
				                    </g:elseif>				
				                    <g:elseif test="${pic1?.status == '3'}">
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
				                <td class="formLbl singgleTr"><g:message code="userDetails.firstName.label"/></td>
				                <td class="value">${pic2?.firstName}</td>
				                <td class="showhide">              
                                	<input type="button" value='SHOW' id="hd3"/>
                                </td>
				            </tr>
				            
				            <tr class="borderTop hide3">
				                <td class="formLbl singgleTr"><g:message code="userDetails.lastName.label"/></td>
				                <td class="value" colspan="2">${pic2?.lastName}</td>
				            </tr>
				            
				            <tr class="borderTop hide3">
				                <td class="formLbl singgleTr"><g:message code="userDetails.userID.label"/></td>
				                <td class="value" colspan="2">${pic2?.user?.username}</td>
				            </tr>
				            
				            <tr class="borderTop hide3">
				                <td class="formLbl singgleTr"><g:message code="userDetails.userAlias.label"/></td>
				                <td class="value" colspan="2">${pic2?.userAlias}</td>
				            </tr>
				            
				            <tr class="borderTop hide3">
				                <td class="formLbl singgleTr"><g:message code="userDetails.email.label"/></td>
				                <td class="value" colspan="2">${pic2?.email}</td>
				            </tr>
				            
				            <tr class="borderTop hide3">
				                <td class="formLbl singgleTr"><g:message code="userDetails.mobilePhoneNo.label"/></td>
				                <td class="value" colspan="2">${pic2?.mobilePhoneNo}</td>
				            </tr>
				            
				            <tr class="borderTop hide3">
				                <td class="formLbl singgleTr"><g:message code="userDetails.status.label"/></td>
				                <td class="value"><span id="userStatus2">
				                    <g:if test="${pic2?.status == '1'}">
				                        <g:message code="userDetails.status.inactive.label"/>
				                    </g:if>				
				                    <g:elseif test="${pic2?.status == '2'}">
				                        <g:message code="userDetails.status.active.label"/>
				                    </g:elseif>				
				                    <g:elseif test="${pic2?.status == '3'}">
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
		            <br/>
		            <span><g:link controller="cgroup" action="list"
		                          id="${cifInstance.id}">${message(code: 'grup.label')}</g:link></span>&nbsp;&nbsp;|&nbsp;&nbsp;
		            <span><g:link controller="cuser" action="list"
		                          id="${cifInstance.id}">${message(code: 'cifuser.user.label')}</g:link></span>&nbsp;&nbsp;|&nbsp;&nbsp;
		            <span><g:link controller="cardProfile" action="index"
		                          id="${cifInstance?.id}">${message(code: 'cardProfile.label')}</g:link></span>
		            <br/><br/>
	            <div class="buttons">
	                <g:form action="delete" useToken="true">
	                    <g:hiddenField name="id" value="${cifInstance?.id}" />
	                    <g:hiddenField name="version" value="${cifInstance?.version}"/>
	                    <span class="button"><input type="button" class="edit" value="${message(code: 'default.button.edit.label')}"
                                            onclick='location.href = "${createLink(url: [controller: 'cif', action: 'edit', id: cifInstance?.id])}"'/>
		                </span>
		                <span class="button"><input type="submit" class="delete redbtn" value="${message(code: 'default.button.delete.label')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message')}');"/></span>
		                <span class="button"><input type="button" class="back" value="${message(code: 'default.button.back.label')}" onclick='location.href = "${createLink(url: [controller: 'cif', action: 'list'])}"'/>
		                </span>
	                </g:form>
	            </div>
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
            
        <script type="text/javascript">
		    var urlImgSpinner = "${resource(dir:'images',file:'spinner.gif')}";
		    var urlBlockUser = "${createLink(url: [controller: 'cifUser', action: 'blockUser'])}";
		    var urlResetPassword = "${createLink(url: [controller: 'cifUser', action: 'resetPassword'])}";
		    var id = ${pic1?.user?.id};
		    var id2 = ${pic2?.user?.id};
		    var imgOk = "${resource(dir:'images',file:'ok.gif')}";
		    var imgFalse = "${resource(dir:'images',file:'false.png')}";
		    var inactive = "${message(code:'userDetails.status.inactive.label')}"
		    var block = "${message(code:'userDetails.status.blocked.label')}"
		    var userStatus = $('#userStatus').html().replace(/^\s+|\s+$/g, '');
		    var userStatus = $('#userStatus2').html().replace(/^\s+|\s+$/g, '');

		    $("#hd").click(function(){
		        if($('#hd').val() == 'HIDE'){
			        $(".hide1").hide();
			        $('#hd').val('SHOW');
		        }
		        else{
		        	$(".hide1").show();
			        $('#hd').val('HIDE');
			        }
		      });
		    $("#hd2").click(function(){
		        if($('#hd2').val() == 'HIDE'){
			        $(".hide2").hide();
			        $('#hd2').val('SHOW');
		        }
		        else{
		        	$(".hide2").show();
			        $('#hd2').val('HIDE');
			        }
		      });

		    $("#hd3").click(function(){
		        if($('#hd3').val() == 'HIDE'){
			        $(".hide3").hide();
			        $('#hd3').val('SHOW');
		        }
		        else{
		        	$(".hide3").show();
			        $('#hd3').val('HIDE');
			        }
		      });
		</script>
		<asset:javascript src='/perpage/showCorporate.js'/>
    </body>
</html>
