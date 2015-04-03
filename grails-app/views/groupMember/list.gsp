
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="secure2" />
        <g:set var="entityName" value="${message(code: 'member.label')}" />
        <g:set var="entityName2" value="${message(code: 'grup.label')}" />
        <title><g:message code="default.management.label" args="[entityName2]" /></title>
    </head>
    <body>
        <div class="body">
            <div class="contentbar"><h1><g:message code="default.management.label" args="[entityName2]" /></h1></div>
            <div class="contentbox">
            	<div class="transDetail"><g:message code="default.detail.label" args="[entityName2]" /></div>
            	<div class="dialog">
	                <table class="form" width="100%">
	                    <tbody>
	                    	<tr>
                                <td class="formLbl singgleTr"><g:message code="grup.grupId.label"/></td>
                
                                <td class="value">${fieldValue(bean: group, field: "grupId")}</td>
                
                            </tr>
                            
	                    	<tr class="borderTop">
                                <td class="formLbl singgleTr"><g:message code="grup.name.label"/></td>
                
                                <td class="value">${fieldValue(bean: group, field: "name")}</td>
                
                            </tr>
                            
                            <tr class="borderTop">
                                <td class="formLbl singgleTr"><g:message code="grup.dbaName.label"/></td>
                
                                <td class="value">${fieldValue(bean: group, field: "dbaName")}</td>
                
                            </tr>
				            
				            <tr class="borderTop">
			                    <td class="formLbl topPadding"><g:message code="grup.location.label"/>
			                    </td>
			                    <td class="value">
			                        <g:textArea disabled="disabled" name="location" cols="40" rows="5" value="${group?.location}"/>
			                    </td>
			                </tr>
			                
			                <tr>
								<td colspan="2" class="detail">
									<g:message code="group.owner.label" />
								</td>
							</tr>
							
							<tr>
			                    <td class="formLbl singgleTr"><g:message code="userDetails.firstName.label"/></td>
			                    <td class="value">
			                       ${group?.owner?.firstName}
			                    </td>
			                </tr>
			
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.lastName.label"/></td>
			                    <td class="value">
			                        ${group?.owner?.lastName}
			                    </td>
			                </tr>
			
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.userID.label"/></td>
			
			                    <td class="value">
			                        ${group?.owner?.userDetails?.user?.username}
			                    </td>
			                </tr>
			
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.userAlias.label"/></td>
			                    <td class="value">
			                        ${group?.owner?.userDetails?.userAlias}
			                    </td>
			                </tr>
			
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.email.label"/></td>
			                    <td class="value">
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
			                    <td class="value">
			                        ${group?.owner?.accoutnNo}
			                    </td>
			                </tr>
	                    </tbody>
	                    </table>
                    </div>
                    
                <div class="transDetail"><g:message code="group.member.label"/></div>
	            <g:if test="${flash.message}">
	            <div class="message">${flash.message}</div>
	            </g:if>
	            
	            <div class="listcontent">
	           
                    <table class="table13" width="100%">
	                    <thead>
	                        <tr>
	                        	<g:sortableColumn property="cuFirstName" title="${message(code: 'default.name.label')}" params='[name:"${name}", phone:"${phone}"]'/>	                    
	                            <th class="tblTitle"><g:message code="user.username.label"/></th>
	                            <th class="tblTitle"><g:message code="userDetails.email.label"/></th>
	                        	<th class="tblTitle"><g:message code="userDetails.mobilePhoneNo.label"/></th>
	                        	<th class="tblTitle"><g:message code="userDetails.status.label"/></th>
	                        	<%--<th class="tblTitle"><g:message code="group.userLimit.label"/></th>
	                        --%></tr>	                        
	                    </thead>
	                    <tbody>
	                    <g:each in="${memberList}" status="i" var="member">
	                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
	                        
	                            <td><g:link controller="groupMember" action="show" id="${member.id}">${member.cifUser?.firstName} ${member.cifUser?.lastName}</g:link></td>
	                            <td>${member.cifUser?.userDetails?.user?.username}</td>
	                            <td>
	                            	<g:if test="${member.cifUser?.userDetails?.activationKey != null }">
	                            		${member.cifUser?.userDetails?.newEmail}
	                            	</g:if>
	                            	<g:else>
	                            		${member.cifUser?.userDetails?.email}
	                            	</g:else>
	                            </td>
	                            <td>${member.cifUser?.userDetails?.mobilePhoneNo}</td>	                            
	                            <td>
	                            	<g:if test="${member.cifUser?.userDetails?.status.equals('1')}"><g:message
			                                  code="userDetails.status.inactive.label"/></g:if>
			                          <g:elseif test="${member.cifUser?.userDetails?.status.equals('2')}"><g:message
			                                  code="userDetails.status.active.label"/></g:elseif>
			                          <g:elseif test="${member.cifUser?.userDetails?.status.equals('3')}"><g:message
			                                  code="userDetails.status.locked.label"/></g:elseif>
			                          <g:else><g:message code="userDetails.status.blocked.label"/></g:else>
	                            </td>
	                        	<%--<td class="price">${member.memberLimit.userLimit}</td>
	                        	
	                        --%></tr>
	                    </g:each>
	                    </tbody>
	                </table>
	                <div class="paginateButtons">
		                <g:paginate total="${memberTotal}" params='[name:"${name}", phone:"${phone}"]'/>
		            </div>

	            </div>
	            
	            <div class="listfilter">
	            	<g:form action="list">
	            		<div class="filterfont"><g:message code="default.filter.label"/></div>
			            <label for="name"><g:message code="default.name.label"/></label>
			            <g:textField name="name" maxlength="100" value="${name}"/>
			            <label for="phone"><g:message code="userDetails.mobilePhoneNo.label"/></label>
			            <g:textField name="phone" maxlength="100" value="${phone}"/>
			
			            <div>
			                <g:submitButton name="search" class="search" value="${message(code: 'default.button.search.label')}"/>
			                <input type="button" class="add" value="${message(code: 'default.button.add.label')}"
			                                            onclick='location.href = "${createLink(url: [controller: 'groupMember', action: 'create'])}"'/>
			            </div>
			        </g:form>
			     </div>
			     <div class="clear"></div>
	            </div>
            </div>
    </body>
</html>
