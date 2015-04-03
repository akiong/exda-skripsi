
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="secure2" />
        <g:set var="entityName" value="${message(code: 'grup.label')}" />
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
	            
	            <div class="buttons">
	            	<g:form action="delete" useToken="true">
		                <g:hiddenField name="id" value="${group?.id}"/>
		                <g:hiddenField name="version" value="${group?.version}"/>	                
	                    <span class="button"><input type="button" class="back" value="${message(code: 'default.button.edit.label')}" onclick='location.href = "${createLink(url: [controller: 'group', action: 'edit', id: group.id])}"'/>
		                </span>
		                <span class="button"><input type="submit" class="delete redbtn" value="${message(code: 'default.button.delete.label')}"
                                                     onclick="return confirm('${message(code: 'default.button.delete.confirm.message')}');"/>
		                </span>
		                <span class="button"><input type="button" style="width:auto !important" class="back" value="${message(code: 'default.button.add.member.label')}" onclick='location.href = "${createLink(url: [controller: 'member', action: 'create', id: group.id])}"'/>
		                </span>
		                <span class="button"><input type="button" class="back" value="${message(code: 'default.button.back.label')}" onclick='location.href = "${createLink(url: [controller: 'group', action: 'list'])}"'/>
		                </span>
		            </g:form>
	            </div>
	            
	            <div class="dialog">
	                <table class="form" width="100%">
	                    <tbody>
	                    	<tr>
                                <td class="formLbl singgleTr"><g:message code="grup.grupId.label"/></td>
                
                                <td class="value" colspan="2">${fieldValue(bean: group, field: "grupId")}</td>
                
                            </tr>
                            
	                    	<tr class="borderTop">
                                <td class="formLbl singgleTr"><g:message code="grup.name.label"/></td>
                
                                <td class="value" colspan="2">${fieldValue(bean: group, field: "name")}</td>
                
                            </tr>
                            
                            <tr class="borderTop">
                                <td class="formLbl singgleTr"><g:message code="grup.dbaName.label"/></td>
                
                                <td class="value" colspan="2">${fieldValue(bean: group, field: "dbaName")}</td>
                
                            </tr>
                            
                            <tr class="borderTop">
                                <td class="formLbl singgleTr"><g:message code="province.label"/></td>
                
                                <td class="value" colspan="2">${fieldValue(bean: group, field: "province.province")}</td>
                
                            </tr>
                            
                            <tr class="borderTop">
                                <td class="formLbl singgleTr"><g:message code="city.label"/></td>
                
                                <td class="value" colspan="2">${fieldValue(bean: group, field: "city.city")}</td>
                
                            </tr>
				            
				            <tr class="borderTop">
			                    <td class="formLbl topPadding"><g:message code="grup.location.label"/>
			                    </td>
			                    <td class="value" colspan="2">
			                        <g:textArea disabled="disabled" name="location" cols="40" rows="5" value="${group?.location}"/>
			                    </td>
			                </tr>
			                
			                <tr>
								<td colspan="3" class="detail">
									<g:message code="group.owner.label" />
								</td>
							</tr>
							
							<tr>
			                    <td class="formLbl singgleTr"><g:message code="userDetails.firstName.label"/></td>
			                    <td class="value" colspan="2">
			                       ${group?.owner?.firstName}
			                    </td>
			                </tr>
			
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.lastName.label"/></td>
			                    <td class="value" colspan="2">
			                        ${group?.owner?.lastName}
			                    </td>
			                </tr>
			
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.userID.label"/></td>
			
			                    <td class="value" colspan="2">
			                        ${group?.owner?.userDetails?.user?.username}
			                    </td>
			                </tr>
			
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.userAlias.label"/></td>
			                    <td class="value" colspan="2">
			                        ${group?.owner?.userDetails?.userAlias}
			                    </td>
			                </tr>
			
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.email.label"/></td>
			                    <td class="value" colspan="2">
			                        ${group?.owner?.userDetails?.email}
			                    </td>
			                </tr>
			
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.mobilePhoneNo.label"/></td>
			                    <td class="value">
			                        ${group?.owner?.userDetails?.mobilePhoneNo}
			                    </td>
			                </tr>
			                
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="account.no.label"/></td>
			                    <td class="value" colspan="2">
			                        ${group?.owner?.accoutnNo}
			                    </td>
			                </tr>
			                
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.status.label"/></td>
			                    <td width="150px">
			                        <g:if test="${group?.owner?.userDetails?.status == '1'}">
				                        <g:message code="userDetails.status.inactive.label"/>
				                    </g:if>
				
				                    <g:elseif test="${group?.owner?.userDetails?.status == '2'}">
				                        <g:message code="userDetails.status.active.label"/>
				                    </g:elseif>
				
				                    <g:elseif test="${group?.owner?.userDetails?.status == '3'}">
				                        <g:message code="userDetails.status.locked.label"/>
				                    </g:elseif>
				
				                    <g:else>
				                        <g:message code="userDetails.status.blocked.label"/>
				                    </g:else>
			                    </td>
			                    <td class="value">			                    
				                    <g:hiddenField name="id" value="${group?.owner?.id}"/>
	                        		<input class="resetPassBtn" style="width:auto !important" type="button" value="${message(code: 'default.button.reset.label')}"/>
			                        <input class="blockStatusBtn redbtn" style="width:auto !important" type="button" value="${message(code: 'default.button.block.label')}"/>
			                    </td>
			                </tr>
	                    </tbody>
	                    </table>
                    </div>
                    <div class="transDetail"><g:message code="group.member.label"/></div>
                    <table class="table13" width="100%">
	                    <thead>
	                        <tr>	     
	                        	<th class="tblTitle"><g:message code="default.name.label"/></th>
	                        	<th class="tblTitle"><g:message code="userDetails.mobilePhoneNo.label"/></th>
	                        	<th class="tblTitle"><g:message code="position.label"/></th>
	                        	<th class="tblTitle"><g:message code="userDetails.email.label"/></th>
	                        	<%--<th class="tblTitle"><g:message code="group.userLimit.label"/></th>
	                        	--%>
	                        	<th class="tblTitle"><g:message code="userDetails.status.label"/></th>
	                        	<th class="tblTitle" width="26%"></th>
	                        </tr>	                        
	                    </thead>
	                    <tbody>
	                    <g:each in="${memberList}" status="i" var="member">
	                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	                        
	                            <td><g:link controller="member" action="show" id="${member.id}">${member.cifUser?.firstName} ${member.cifUser?.lastName}</g:link></td>
	                            <td>${member.cifUser?.userDetails?.mobilePhoneNo}</td>
	                            <td>
	                            	<g:if test="${member.position.equals('1')}">
	                            		<g:message code="leader.label"/>
	                            	</g:if>
	                            	<g:else>
	                            		<g:message code="member.label"/>
	                            	</g:else>
	                            </td>
	                            <td>
	                            	<g:if test="${member.cifUser?.userDetails?.activationKey != null }">
	                            		${member.cifUser?.userDetails?.newEmail}
	                            	</g:if>
	                            	<g:else>
	                            		${member.cifUser?.userDetails?.email}
	                            	</g:else>
	                            </td>
	                        	<%--<td class="price">${member.memberLimit.userLimit}</td>--%>
	                        	<td>
	                        		<g:if test="${member.cifUser?.userDetails?.status == '1'}">
				                        <g:message code="userDetails.status.inactive.label"/>
				                    </g:if>
				
				                    <g:elseif test="${member.cifUser?.userDetails?.status == '2'}">
				                        <g:message code="userDetails.status.active.label"/>
				                    </g:elseif>
				
				                    <g:elseif test="${member.cifUser?.userDetails?.status == '3'}">
				                        <g:message code="userDetails.status.locked.label"/>
				                    </g:elseif>
				
				                    <g:else>
				                        <g:message code="userDetails.status.blocked.label"/>
				                    </g:else>
	                        	</td>
	                        	<td>
	                        		<g:hiddenField name="id" value="${member.cifUser?.id}"/>
	                        		<input class="resetPassBtn" style="width:auto !important" type="button" value="${message(code: 'default.button.reset.label')}"/>
			                        <input class="blockStatusBtn redbtn" style="width:auto !important" type="button" value="${message(code: 'default.button.block.label')}"/>
			                        <g:if test="${!member.position.equals('1')}">
			                        	<g:hiddenField name="idmm" value="${member.id}"/>
			                        	<input class="promote" style="width:auto !important" type="button" value="${message(code: 'default.button.leader.label')}"/>
			                        </g:if>
	                        	</td>
	                        </tr>
	                    </g:each>
	                    </tbody>
	                </table>
	                <div class="paginateButtons">
		                <g:paginate total="${memberTotal}" action="show" params='[id:"${group?.id}"]'/>
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
		
		<div id="leaderInvalid" style="display:none;" title="${message(code: 'position.label')}">
		    <p>${message(code: 'default.not.leader.message')}</p>
		</div>
		
		    
        <script type="text/javascript">
		    var urlImgSpinner = "${resource(dir:'images',file:'spinner.gif')}";
		    var urlBlockUser = "${createLink(url: [controller: 'cifUser', action: 'block'])}";
		    var urlResetPassword = "${createLink(url: [controller: 'cifUser', action: 'reset'])}";
		    var urlPromote = "${createLink(url: [controller: 'member', action: 'promote'])}";
		    var imgOk = "${resource(dir:'images',file:'ok.gif')}";
		    var imgFalse = "${resource(dir:'images',file:'false.png')}";
		    var inactive = "${message(code:'userDetails.status.inactive.label')}";
		    var block = "${message(code:'userDetails.status.blocked.label')}";
		</script>
		<asset:javascript src='/perpage/showGroup.js'/>
    </body>
</html>
