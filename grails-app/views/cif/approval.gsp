
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="secure2" />
        <g:set var="entityName" value="${message(code: 'cif.label')}" />
        <title><g:message code="default.detail.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="body">
            <div class="contentbar"><h1>${entityName}</h1></div>
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
                
                            </tr>
	                    
	                        <tr class="borderTop">
				                <td class="formLbl singgleTr"><g:message code="cif.corpName.label"/></td>
				
				                <td class="value">${cifInstance?.corpName}</td>
				
				            </tr>
				
				            <tr class="borderTop">
				                <td class="formLbl topPadding"><g:message code="cif.address1.label"/></td>
				
				                <td class="value"><g:textArea name="address1" disabled="disabled" value="${cifInstance?.address1}"/></td>
				
				            </tr>
				            
				            <tr class="borderTop">
				                <td class="formLbl topPadding"><g:message code="cif.address2.label"/></td>
				
				                <td class="value"><g:textArea name="address2" disabled="disabled" value="${cifInstance?.address2}"/></td>
				
				            </tr>
	                    
	                        <tr class="borderTop">
				                <td class="formLbl singgleTr"><g:message code="cif.province.label"/></td>
				
				                <td class="value">${cifInstance?.province?.province}</td>
				
				            </tr>
				
				            <tr class="borderTop">
				                <td class="formLbl singgleTr"><g:message code="cif.city.label"/></td>
				
				                <td class="value">${cifInstance?.city?.city}</td>
				            </tr>
				            
				            <tr class="borderTop">
				                <td class="formLbl singgleTr"><g:message code="cif.rateType.label"/></td>
				
				                <td class="value">
				                	<g:if test="${cifInstance?.rateType != null}">
				                		${cifInstance?.rateType?.name}
				                	</g:if>
				                	<g:else>
				                		<g:message code="default.none.label"/>
				                	</g:else>
				                </td>
				            </tr>
				            				            
				             <tr>
								<td colspan="2" class="detail">
									<g:message code="cif.pic1.label" />
								</td>
							</tr>
	                    	
	                    	<tr>
				                <td class="formLbl singgleTr"><g:message code="userDetails.firstName.label"/></td>
				                <td class="value">${pic1?.firstName}</td>
				            </tr>
				            
				            <tr class="borderTop">
				                <td class="formLbl singgleTr"><g:message code="userDetails.lastName.label"/></td>
				                <td class="value">${pic1?.lastName}</td>
				            </tr>
				            
				            <tr class="borderTop">
				                <td class="formLbl singgleTr"><g:message code="userDetails.userID.label"/></td>
				                <td class="value">${pic1?.user?.username}</td>
				            </tr>
				            
				            <tr class="borderTop">
				                <td class="formLbl singgleTr"><g:message code="userDetails.userAlias.label"/></td>
				                <td class="value">${pic1?.userAlias}</td>
				            </tr>
				            
				            <tr class="borderTop">
				                <td class="formLbl singgleTr"><g:message code="userDetails.email.label"/></td>
				                <td class="value">${pic1?.email}</td>
				            </tr>
				            
				            <tr class="borderTop">
				                <td class="formLbl singgleTr"><g:message code="userDetails.mobilePhoneNo.label"/></td>
				                <td class="value">${pic1?.mobilePhoneNo}</td>
				            </tr>
				            
				            <tr>
								<td colspan="2" class="detail">
									<g:message code="cif.pic2.label" />
								</td>
							</tr>
							
							<tr>
				                <td class="formLbl singgleTr"><g:message code="userDetails.firstName.label"/></td>
				                <td class="value">${pic2?.firstName}</td>
				            </tr>
				            
				            <tr class="borderTop">
				                <td class="formLbl singgleTr"><g:message code="userDetails.lastName.label"/></td>
				                <td class="value">${pic2?.lastName}</td>
				            </tr>
				            
				            <tr class="borderTop">
				                <td class="formLbl singgleTr"><g:message code="userDetails.userID.label"/></td>
				                <td class="value">${pic2?.user?.username}</td>
				            </tr>
				            
				            <tr class="borderTop">
				                <td class="formLbl singgleTr"><g:message code="userDetails.userAlias.label"/></td>
				                <td class="value">${pic2?.userAlias}</td>
				            </tr>
				            
				            <tr class="borderTop">
				                <td class="formLbl singgleTr"><g:message code="userDetails.email.label"/></td>
				                <td class="value">${pic2?.email}</td>
				            </tr>
				            
				            <tr class="borderTop">
				                <td class="formLbl singgleTr"><g:message code="userDetails.mobilePhoneNo.label"/></td>
				                <td class="value">${pic2?.mobilePhoneNo}</td>
				            </tr>
	                    
	                    </tbody>
	                    </table>
	                    </div>
		            <g:if test="${!isApproval}">
						<div class="buttons">	
			         		<span class="button"><input type="button" class="back"
			                                            value="${message(code: 'default.button.back.label')}"
			                                            onclick='location.href = "${createLink(url: [controller: 'cif', action: 'list'])}"'/>
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
				                   <input type="submit" class="approve" value="${message(code: 'default.button.approve.label')}"/>
				            </g:form>
			                <g:form action="reject" class="formButton">
			                	<g:hiddenField name="${org.codehaus.groovy.grails.web.servlet.mvc.SynchronizerTokensHolder.TOKEN_KEY}" value="${tokennya}"/>
            					<g:hiddenField name="${org.codehaus.groovy.grails.web.servlet.mvc.SynchronizerTokensHolder.TOKEN_URI}" value="${tokenurlnya}"/>
			                        <g:hiddenField name="taskId" value="${taskId}"/>
									<input type="submit" class="reject redbtn" value="${message(code: 'default.button.reject.label')}"/>
					        </g:form>
					               <span class="button"><input type="button" class="back"
				                                 value="${message(code: 'default.button.back.label')}"
				                                onclick='location.href = "${createLink(url: [controller: 'corporateMaintenance', action: 'list'])}"'/></span>
				        </div>
			        </g:else>
	            </div>
	            
            </div>
    </body>
</html>
