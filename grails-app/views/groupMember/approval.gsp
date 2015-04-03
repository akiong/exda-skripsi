
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="secure2" />
        <g:set var="entityName" value="${message(code: 'member.label')}" />
        <g:set var="entityName2" value="${message(code: 'member.approval.label')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
        	<g:if test="${!isApproval}">
            	<div class="contentbar"><h1><g:message code="group.member.label" /></h1></div>
            </g:if>
            <g:else>
            	<div class="contentbar"><h1>${entityName2}</h1></div>
            </g:else>

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
	            <div class="dialog">
	                <table class="corpUserTable form" width="100%">
	                    <tbody>
			                <tr>
			                    <td class="formLbl singgleTr"><g:message code="userDetails.firstName.label"/></td>
			        
			                    <td class="value">${cifUserInstance?.cifUser?.firstName}</td>
			        
			                </tr>
			        
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.lastName.label"/></td>
			        
			                    <td class="value">${cifUserInstance?.cifUser?.lastName}</td>
			        
			                </tr>
                            
                            <tr class="borderTop">
                                <td class="formLbl singgleTr"><g:message code="userDetails.userID.label"/></td>
                    
                                <td class="value">${cifUserInstance?.cifUser?.userDetails?.user?.username}</td>
                    
                            </tr>
			        
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.userAlias.label"/></td>
			        
			                    <td class="value">${cifUserInstance?.cifUser?.userDetails?.userAlias}</td>
			        
			                </tr>			        
			                
			        
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.email.label"/></td>
			        
			                    <g:if test="${email}">
			                        <td class="value">${email}</td>
			                    </g:if>
			                    <g:else>
			                        <td class="value">${cifUserInstance?.cifUser?.userDetails?.email}</td>
			                    </g:else>
			        
			                </tr>
			        
			                <tr class="borderTop">
			                    <td class="formLbl singgleTr"><g:message code="userDetails.mobilePhoneNo.label"/></td>
			        
			                    <td class="value">${cifUserInstance?.cifUser?.userDetails?.mobilePhoneNo}</td>
			        
			                </tr>
			                
			                <%--<g:if test="${!isApproval}">
			                	<tr class="borderTop">
				                    <td class="formLbl singgleTr"><g:message code="group.userLimit.label"/></td>
				        
				                    <td class="value" colspan="2"><span class="price">${cifUserInstance?.memberLimit?.userLimit}</span></td>
				        
				                </tr>
			                </g:if>
			                <g:else>
			                <tr class="borderTop">
				                    <td class="formLbl">
				                        <label for="userLimit"><g:message code="group.userLimit.label"/></label>
				                    </td>
				                    <td class="value ${hasErrors(bean: cifUserInstance, field: 'userLimit', 'errors')}">
				                        <input type="text" id="userLimit" class="price" name="userLimit" maxlength="100" value="${cifUserInstance?.memberLimit?.userLimit}"/>
				                    </td>
				                </tr>
	                    	</g:else>
	                    --%></tbody>
	                </table>
	            </div>
	            <g:if test="${!isApproval}">
						<div class="buttons">	
			         		<span class="button"><input type="button" class="back"
			                                            value="${message(code: 'default.button.back.label')}"
			                                            onclick='location.href = "${createLink(url: [controller: 'groupMember', action: 'list'])}"'/>
			                </span>
			        	</div>
					</g:if>
					<g:else>
						<div class="buttons">
							<%
								def tokensHolder = org.codehaus.groovy.grails.web.servlet.mvc.SynchronizerTokensHolder.store(session)
								def tokennya = tokensHolder.generateToken(request.forwardURI)
								def tokenurlnya = request.forwardURI
							 %>
				        	<g:form action="approve" class="formButton">
				        		<g:hiddenField name="${org.codehaus.groovy.grails.web.servlet.mvc.SynchronizerTokensHolder.TOKEN_KEY}" value="${tokennya}"/>
            					<g:hiddenField name="${org.codehaus.groovy.grails.web.servlet.mvc.SynchronizerTokensHolder.TOKEN_URI}" value="${tokenurlnya}"/>
				        	      <g:hiddenField name="taskId" value="${taskId}"/>
				        	      <%--<g:hiddenField name="userLimit" id="aUserLimit"/>
				                   --%><input type="button" class="approve" value="${message(code: 'default.button.approve.label')}"/>
				            </g:form>
			                <g:form action="reject" class="formButton">
			                	<g:hiddenField name="${org.codehaus.groovy.grails.web.servlet.mvc.SynchronizerTokensHolder.TOKEN_KEY}" value="${tokennya}"/>
            					<g:hiddenField name="${org.codehaus.groovy.grails.web.servlet.mvc.SynchronizerTokensHolder.TOKEN_URI}" value="${tokenurlnya}"/>
			                        <g:hiddenField name="taskId" value="${taskId}"/>
									<input type="button" class="reject redbtn" value="${message(code: 'default.button.reject.label')}"/>
					        </g:form>
					               <span class="button"><input type="button" class="back"
				                                 value="${message(code: 'default.button.back.label')}"
				                                onclick='location.href = "${createLink(url: [controller: 'myTask'])}"'/></span>
				        </div>
			        </g:else>
	        </div>
        </div>
        <asset:javascript src='/perpage/memberApproval.js'/>
        
    </body>
</html>
