
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
	            
	            <div style="float:left;width:45%">
	            <div class="dialog">
	                <table class="form" width="100%">
	                    <tbody>
	                    	<tr>
                                <td class="formLbl singgleTr"><g:message code="grup.name.label"/></td>
                
                                <td class="value">${fieldValue(bean: group, field: "name")}</td>
                
                            </tr>
				            
				            <tr class="borderTop">
			                    <td class="formLbl topPadding"><g:message code="grup.location.label"/>
			                    </td>
			                    <td class="value">
			                        <g:textArea disabled="disabled" name="location" cols="40" rows="5" value="${group?.location}"/>
			                    </td>
			                </tr>
			                
			                <tr class="borderTop">
				                <td class="formLbl singgleTr"><g:message code="grup.coordinate.label"/></td>
				
				                <td class="value">${group?.lang}, ${group?.longt}</td>
				
				            </tr>
	                    </tbody>
	                    </table>
                    </div>
                    </div>
                    <div style="float:right;width:45%">
                    <div id="googleMap" style="width:75%;height:275px;"></div>
                    </div>
                    <div class="clear"></div>
                    
                    <g:if test="${!isApproval}">
						<div class="buttons">	
			         		<span class="button"><input type="button" class="back"
			                                            value="${message(code: 'default.button.back.label')}"
			                                            onclick='location.href = "${createLink(url: [controller: 'group', action: 'list'])}"'/>
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
				                                onclick='location.href = "${createLink(url: [controller: 'myTask'])}"'/></span>
				        </div>
			        </g:else>
                 </div>
	            
            </div>
		    
        <script type="text/javascript">
	    	var langth = ${group?.lang ?: 0};
	    	var longth = ${group?.longt ?: 0};
		</script>
		<script src="https://maps.googleapis.com/maps/api/js?v=3.16&sensor=false&language=id"></script>
		<asset:javascript src='/perpage/deviceTrx.js'/>
    </body>
</html>
