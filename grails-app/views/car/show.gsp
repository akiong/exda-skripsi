<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="secure2"/>
    <g:set var="entityName" value="${message(code: 'car.label')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div class="body">
    <div class="contentbar"><h1>${entityName}</h1></div>

    <div class="leftbottombar"></div>

    <div class="cuttedbluebartop"></div>

    <div class="rightbottombar"></div>

    <div class="contentbox">

        <div class="transDetail"><g:message code="default.show.label" args="[entityName]"/></div>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${cityInstance}">
            <div class="errors">
                <g:renderErrors bean="${cityInstance}" as="list"/>
            </div>
        </g:hasErrors>
        <div class="dialog">
            <table class="form" width="100%">
	            <tbody>
	
	            <tr>
	                <td class="formLbl">
				            <label for="noPlat"><g:message code="no.plat.label"/></label>
					</td>
			
				    <td class="value">${carInstance?.noPlat}</td>
				  	        
	            </tr>
				<tr class="borderTop">
                        <td class="formLbl">
                            <label for="description"><g:message code="city.description"/></label>
                        </td>
                        
                        <td>${carInstance?.description}</td>
                    </tr>
				
	            </tbody>
	        </table>
        </div>

        <div class="buttons">
            <g:form action="delete" useToken="true">
                <g:hiddenField name="id" value="${carInstance?.id}"/>
                <g:hiddenField name="version" value="${carInstance?.version}"/>
                <g:hiddenField name="show" value="show"/>
                <input type="button" class="edit"
                                            value="${message(code: 'default.button.edit.label')}"
                                            onclick='location.href = "${createLink(url: [controller: 'car', action: 'edit', id: carInstance?.id])}"'/>
                <input type="button" class="delete redbtn" value="${message(code: 'default.button.delete.label')}"
                                                     onclick="askDeleteDialog()"/>
                <input type="button" class="back" value="${message(code: 'default.button.back.label')}"
                                            onclick='location.href = "${createLink(url: [controller: 'car', action: 'index'])}"'/>
            </g:form>
        </div>
    </div>
    
    <div id="QuestionDelete" style="display:none" title="${message(code: 'default.button.delete.label')}">
        <p>${message(code: 'default.question.delete')}</p>
    </div>

    <div class="leftbottomcorner"></div>

    <div class="cuttedbluebarbottom"></div>

    <div class="rightbottomcorner"></div>

    <div class="bottombar"></div>
</div>
<asset:javascript src='/perpage/carEdit.js'/>
</body>
</html>
