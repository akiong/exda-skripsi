<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="secure2"/>
    <g:set var="entityName" value="${message(code: 'city.label')}"/>
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
				            <label for="cityCode"><g:message code="city.name"/></label>
					</td>
			
				    <td class="value">${cityInstance?.city}</td>
				  	        
	            </tr>
				<tr class="borderTop">
                        <td class="formLbl">
                            <label for="name"><g:message code="city.description"/></label>
                        </td>
                        
                        <td>${cityInstance?.description}</td>
                    </tr>
				
	            </tbody>
	        </table>
        </div>

        <div class="buttons">
            <g:form action="delete" useToken="true">
                <g:hiddenField name="id" value="${cityInstance?.id}"/>
                <g:hiddenField name="version" value="${cityInstance?.version}"/>
                <g:hiddenField name="show" value="show"/>
                <input type="button" class="edit"
                                            value="${message(code: 'default.button.edit.label')}"
                                            onclick='location.href = "${createLink(url: [controller: 'city', action: 'edit', id: cityInstance?.id])}"'/>
                <input type="submit" class="delete redbtn" value="${message(code: 'default.button.delete.label')}"
                                                     onclick="return confirm('${message(code: 'default.button.delete.confirm.message')}');"/>
                <input type="button" class="back" value="${message(code: 'default.button.back.label')}"
                                            onclick='location.href = "${createLink(url: [controller: 'city', action: 'index'])}"'/>
            </g:form>
        </div>
    </div>

    <div class="leftbottomcorner"></div>

    <div class="cuttedbluebarbottom"></div>

    <div class="rightbottomcorner"></div>

    <div class="bottombar"></div>
</div>
</body>
</html>
