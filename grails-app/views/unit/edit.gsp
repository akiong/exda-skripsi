<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="secure2"/>
    <g:set var="entityName" value="${message(code: 'unit.label')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<div class="body">

    <div class="contentbar"><h1>${entityName}</h1></div>

    <div class="leftbottombar"></div>

    <div class="cuttedbluebartop"></div>

    <div class="rightbottombar"></div>

    <div class="contentbox">

        <div class="transDetail"><g:message code="default.edit.label" args="[entityName]"/></div>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <g:if test="${flash.error}">
            <div class="errors"><ul><li>${flash.error}</li></ul></div>
        </g:if>
        <g:hasErrors bean="${unitInstance}">
            <div class="errors">
                <g:renderErrors bean="${unitInstance}" as="list"/>
            </div>
        </g:hasErrors>
        <g:form action="delete" useToken="true">
            <g:hiddenField name="id" value="${unitInstance?.id}"/>
            <g:hiddenField name="version" value="${unitInstance?.version}"/>
            <div class="dialog">
                <table class="form" width="100%">
                    <tbody>
					<tr>
				        <td class="formLbl">
				            <label for="name"><g:message code="default.name.label"/></label>
						</td>
	    
	    				<td class="value ${hasErrors(bean: unitInstance, field: 'name', 'errors')}">
                            <g:textField name="name" value="${unitInstance?.name}"/>
                        </td>
					</tr>
                    <tr class="borderTop">
                        <td class="formLbl">
                            <label for="description"><g:message code="city.description"/></label>
                        </td>
                        
                        <td class="value ${hasErrors(bean: carInstance, field: 'description', 'errors')}">
                            <g:textArea name="description" value="${unitInstance?.description}"/>
                        </td>
                    </tr>

                    </tbody>
                </table>
            </div>
			
            <div class="buttons">
           				 <input type="button" class="save" value="${message(code: 'default.button.update.label')}"
                                            onClick="checkName();"/> 
		                <input type="button" class="delete redbtn" value="${message(code: 'default.button.delete.label')}"
                                                     onclick="askDeleteDialog()"/>
		                <input type="button" class="back" value="${message(code: 'default.button.back.label')}"
		                                            onclick='location.href = "${createLink(url: [controller: 'unit', action: 'show', id:unitInstance?.id])}"'/>
		                
	        </div>
        </g:form>
    </div>
	
	
	<div id="nameExistSection" style="display:none"
         title="${message(code: 'default.duplicate.label', args: [message(code: 'branch.name.label')])}">
        <p>${message(code: 'default.duplicate2.msg.label', args: [message(code: 'branch.label'), message(code: 'branch.code.label') , message(code: 'branch.name.label')])}</p>
    </div>

    <div id="replaceSection" style="display:none" title="${message(code: 'default.replacement.label')}">
        <p>${message(code: 'default.replacement.msg.label')}</p>
    </div>
    
    <div id="QuestionDelete" style="display:none" title="${message(code: 'default.button.delete.label')}">
        <p>${message(code: 'default.question.delete')}</p>
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
		<div id="address1Blank" style="display:none;" title="${message(code: 'branch.alamat1.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'branch.alamat1.label')])}</p>
		</div>  
		<div id="phoneNoBlank" style="display:none;" title="${message(code: 'branch.phoneNo.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'branch.phoneNo.label')])}</p>
		</div>
	
<script type="text/javascript">
	var url = "${createLink(url: [controller: 'unit', action: 'findUnitByName'])}";
	var urlUpdate = "${createLink(url: [controller: 'unit', action: 'update'])}";
    var urlReplace = "${createLink(url: [controller: 'unit', action: 'replace'])}";
</script>

		<asset:javascript src='/perpage/unitEdit.js'/>
</body>
</html>

