<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="secure2"/>
    <g:javascript src="jquery-2.1.0.js"/>
    <g:javascript src="perpage/createCity.js"/>
    <g:set var="entityName" value="${message(code: 'city.label')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<div class="body">

    <div class="contentbar"><h1>${entityName}</h1></div>

    <div class="leftbottombar"></div>

    <div class="cuttedbluebartop"></div>

    <div class="rightbottombar"></div>

    <div class="contentbox">

        <div class="transDetail"><g:message code="default.create.label" args="[entityName]"/></div>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <g:if test="${flash.error}">
            <div class="errors"><ul><li>${flash.error}</li></ul></div>
        </g:if>
        <g:hasErrors bean="${cityInstance}">
            <div class="errors">
                <g:renderErrors bean="${cityInstance}" as="list"/>
            </div>
        </g:hasErrors>
        <g:form action="save"  useToken="true">
            <div class="dialog">
                <table class="form" width="100%">
                    <tbody>
                    <tr>
                        <td class="formLbl">
                            <label for="name"><g:message code="city.name"/></label>
                        </td>
                        
                        <td class="value ${hasErrors(bean: cityInstance, field: 'name', 'errors')}">
                            <g:textField id="city" name="city" maxlength="100" value="${city}"/>
                        </td>
                    </tr>                
                    <tr class="borderTop">
                        <td class="formLbl">
                            <label for="description"><g:message code="city.description"/></label>
                        </td>

                        <td class="value ${hasErrors(bean: cityInstance, field: 'description', 'errors')}">
                            <g:textArea  name="description" maxlength="100" value="${description}"/>
                        </td>		       				        
				        
                    </tr>

                    </tbody>
                </table>
            </div>

            <div class="buttons">
            	<input type="button" class="back redbtn"
                                            value="${message(code: 'default.button.back.label')}"
                                            onclick='location.href = "${createLink(url: [controller: 'city', action: 'index'])}"'/>
                
                <input type="button" name="create" class="save"
                                            value="${message(code: 'default.button.create.label')}"
                                            onClick="checkName();"/>
<%--               <g:submitButton name="Submit" onClick="checkName()" value="${message(code: 'default.button.create.label')}" /> --%>
                                     
            </div>
        </g:form>
    </div>
	
	<div id="nameExistSection" style="display:none;"
         title="${message(code: 'default.duplicate.label', args: [message(code: 'default.name.label')])}">
        <p>${message(code: 'default.duplicate.msg.label', args: [message(code: 'city.label'), message(code: 'default.name.label')])}</p>
    </div>
    
    <div id="replaceSection" style="display:none;" title="${message(code: 'default.replacement.label')}">
        <p>${message(code: 'default.replacement.msg.label')}</p>
    </div>
    
    <div id="nameBlank" style="display:none;" title="${message(code: 'default.name.label')}">
        <p>${message(code: 'default.blank.message', args: [message(code: 'default.name.label')])}</p>
    </div>
    
    <div id="codeBlank" style="display:none;" title="${message(code: 'code.label')}">
        <p>${message(code: 'default.blank.message', args: [message(code: 'code.label')])}</p>
    </div>
    
    <div id="duplicateHost" style="display:none;" title="${message(code: 'host.label')}">
        <p>${message(code: 'default.duplicate.label', args: [message(code: 'host.label')])}</p>
    </div>
    
    <div class="leftbottomcorner"></div>

    <div class="cuttedbluebarbottom"></div>

    <div class="rightbottomcorner"></div>

    <div class="bottombar"></div>
     
</div>
	
		<div id="branchCodeBlank" style="display:none;" title="${message(code: 'branch.code.label')}">
            <p>${message(code: 'default.blank.message', args: [message(code: 'branch.code.label')])}</p>
        </div>
        <div id="nameBlank" style="display:none;" title="${message(code: 'branch.name.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'branch.name.label')])}</p>
		</div>
		<div id="cityBlank" style="display:none;" title="${message(code: 'branch.city.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'branch.city.label')])}</p>
		</div>
		<div id="countryBlank" style="display:none;" title="${message(code: 'branch.country.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'branch.country.label')])}</p>
		</div>
		<div id="address1Blank" style="display:none;" title="${message(code: 'branch.address1.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'branch.address1.label')])}</p>
		</div>
		<div id="phoneNoBlank" style="display:none;" title="${message(code: 'branch.phoneNo.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'branch.phoneNo.label')])}</p>
		</div>

	
<script type="text/javascript">
	var url = "${createLink(url: [controller: 'city', action: 'findCityByName'])}";
<%--	var hostSize = ${hostList.size()};--%>
<%--    var currentHost = ${host != null ? host.size() : 1};--%>
</script>
<asset:javascript src='/perpage/createCity.js'/>
</body>
</html>

