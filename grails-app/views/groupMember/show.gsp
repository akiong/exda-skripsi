
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="secure2" />
        <g:set var="entityName" value="${message(code: 'member.label')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <div class="contentbar"><h1><g:message code="group.member.label" /></h1></div>

            <div class="contentbox">
                <div class="transDetail"><g:message code="default.show.label" args="[entityName]"/></div>
	            <g:if test="${flash.message}">
	            <div class="message">${flash.message}</div>
	            </g:if>
	            <g:if test="${flash.error}">
		            <div class="errors"><ul><li>${flash.error}</li></ul></div>
		        </g:if>
		        <g:hasErrors bean="${cifUserInstance}">
		            <div class="errors">
		                <g:renderErrors bean="${cifUserInstance}" as="list"/>
		            </div>
		        </g:hasErrors>

	            <%--<div style="float:left;width:45%">
	                --%><table class="form" width="100%">
	                    <tbody>
			                <tr>
			                    <td class="formLbl singgleTr"><g:message code="userDetails.firstName.label"/></td>
			        
			                    <td class="value" colspan="2">${cifUserInstance?.cifUser?.firstName}</td>
			        
			                </tr>
			        
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.lastName.label"/></td>
			        
			                    <td class="value" colspan="2">${cifUserInstance?.cifUser?.lastName}</td>
			        
			                </tr>
                            
                            <tr class="borderTop">
                                <td class="formLbl singgleTr"><g:message code="userDetails.userID.label"/></td>
                    
                                <td class="value" colspan="2">${cifUserInstance?.cifUser?.userDetails?.user?.username}</td>
                    
                            </tr>
			        
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.userAlias.label"/></td>
			        
			                    <td class="value" colspan="2">${cifUserInstance?.cifUser?.userDetails?.userAlias}</td>
			        
			                </tr>			        
			                
			        
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.email.label"/></td>
			        
			                    <g:if test="${email}">
			                        <td class="value">${email}</td>
			                    </g:if>
			                    <g:else>
			                        <td class="value" colspan="2">${cifUserInstance?.cifUser?.userDetails?.email}</td>
			                    </g:else>
			        
			                </tr>
			        
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.mobilePhoneNo.label"/></td>
			        
			                    <td class="value" colspan="2">${cifUserInstance?.cifUser?.userDetails?.mobilePhoneNo}</td>
			        
			                </tr>
			                
			                <%--<tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="member.cardNo.label"/></td>
			        
			                    <td class="value" colspan="2">${cifUserInstance?.card?.cardNo}</td>
			        
			                </tr>
			                
			                <g:if test="${cifUserInstance?.vaMember && cifUserInstance?.vaMember.deleteFlag.equals('N')}">
					                <tr class="borderTop">
					                    <td class="formLbl singgleTr"><g:message code="vaMember.label"/></td>
					        
					                    <td class="value" colspan="2">${cifUserInstance?.vaMember?.accountNo}</td>
					        
					                </tr>
				                </g:if>
			                
			                --%><tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="position.label"/></td>
			        
			                    <td class="value" colspan="2">
			                    	<g:if test="${cifUserInstance?.position.equals('1')}">
	                            		<g:message code="leader.label"/>
	                            	</g:if>
	                            	<g:else>
	                            		<g:message code="member.label"/>
	                            	</g:else>
	                            </td>
			        
			                </tr>
			        
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.status.label"/></td>
			                    <td class="value"><span id="userStatus">
			                          <g:if test="${cifUserInstance?.cifUser?.userDetails?.status.equals('1')}"><g:message
			                                  code="userDetails.status.inactive.label"/></g:if>
			                          <g:elseif test="${cifUserInstance?.cifUser?.userDetails?.status.equals('2')}"><g:message
			                                  code="userDetails.status.active.label"/></g:elseif>
			                          <g:elseif test="${cifUserInstance?.cifUser?.userDetails?.status.equals('3')}"><g:message
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
	            <%--</div>
	            <div style="float:right;width:45%">
	            	<table class="form" width="100%">
	                    <tbody>
	                    	<tr>
			                    <td class="formLbl singgleTr"><g:message code="group.userLimit.label"/></td>
			        
			                    <td class="value" colspan="2"><span class="price">${cifUserInstance?.memberLimit?.userLimit}</span></td>
			        
			                </tr>
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="outstanding.limit.label"/></td>
			        
			                    <td class="value" colspan="2"><span class="price">${cifUserInstance?.memberLimit?.outstandingLimit}</span></td>
			        
			                </tr>
	                    </tbody>
                  	</table>
	            </div>
	            <div class="clear"></div>
	            --%><div class="buttons">
	                <span class="button"><input type="button" class=list value="${message(code: 'default.button.back.label')}" onclick='location.href = "${createLink(url: [controller: 'groupMember', action: 'list'])}"'/></span>
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
		    var urlBlockUser = "${createLink(url: [controller: 'cifUser', action: 'block'])}";
		    var urlResetPassword = "${createLink(url: [controller: 'cifUser', action: 'reset'])}";
		    var id = ${cifUserInstance?.cifUser?.id};
		    var imgOk = "${resource(dir:'images',file:'ok.gif')}";
		    var imgFalse = "${resource(dir:'images',file:'false.png')}";
		    var inactive = "${message(code:'userDetails.status.inactive.label')}"
		    var block = "${message(code:'userDetails.status.blocked.label')}"
		    var userStatus = $('#userStatus').html().replace(/^\s+|\s+$/g, '');
		</script>
		<asset:javascript src='/perpage/showMemberUser.js'/>
    </body>
</html>
