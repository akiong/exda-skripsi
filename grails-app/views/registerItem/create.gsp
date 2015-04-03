<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="secure2"/>
    <g:set var="entityName" value="${message(code: 'register.item.label')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<div class="body">

    <div class="contentbar"><h1>${entityName}</h1></div>

    <div class="leftbottombar"></div>

    <div class="cuttedbluebartop"></div>

    <div class="rightbottombar"></div>

    <div class="contentbox">

        <div class="secondaryText"><g:message code="default.create.label" args="[entityName]"/></div>
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
                <div class="span6">
                	<div class="span6"><h5 class="text-center" style="padding-top:15px">Sender</h5></div>
                	<table class="form"  width="100%">
	                   <tbody>
		                   <tr>
		                       <td class="formLbl noPadding">
		                           <label for="senderName"><g:message code="default.name.label"/></label>
		                       </td>
		                       
		                       <td class="noPadding value ${hasErrors(bean: cityInstance, field: 'senderName', 'errors')}">
		                           <g:textField name="senderName"  value="${senderName}"/>
		                       </td>
		                   </tr> 
		                   <tr>
		                       <td class="formLbl noPadding">
		                           <label for="noTelpSender"><g:message code="no.tlp.label"/></label>
		                       </td>
		                       
		                       <td class="noPadding value ${hasErrors(bean: cityInstance, field: 'noTelpSender', 'errors')}">
		                           <g:textField name="noTelpSender" class="numeric" value="${noTelpSender}"/>
		                       </td>
		                   </tr>		                   
		                   <tr>
		                       <td class="formLbl noPadding">
		                           <label for="noHpSender"><g:message code="mobile.phone.label"/></label>
		                       </td>
		                       
		                       <td class="noPadding value ${hasErrors(bean: cityInstance, field: 'noHpSender', 'errors')}">
		                           <g:textField name="noHpSender" class="numeric" value="${noHpSender}"/>
		                       </td>
		                   </tr>   
		                   <tr>
		                       <td class="formLbl noPadding">
		                           <label for="emailSender"><g:message code="email.label"/></label>
		                       </td>
		                       
		                       <td class="noPadding value ${hasErrors(bean: cityInstance, field: 'emailSender', 'errors')}">
		                           <g:textField name="emailSender"  value="${emailSender}"/>
		                       </td>
		                   </tr> 
		                   <tr>
		                       <td class="noPadding formLbl">
		                           <label for="addressSender"><g:message code="address.label"/></label>
		                       </td>
		
		                       <td class="noPadding value ${hasErrors(bean: cityInstance, field: 'addressSender', 'errors')}">
		                           <g:textArea  name="addressSender" value="${addressSender}"/>
		                       </td>
		                   </tr> 		                                  
		                   <tr>
		                       <td class="noPadding formLbl">
		                           <label for="descriptionSender"><g:message code="city.description"/></label>
		                       </td>
		
		                       <td class="noPadding value ${hasErrors(bean: cityInstance, field: 'description', 'errors')}">
		                           <g:textArea  name="descriptionSender" value="${descriptionSender}"/>
		                       </td>
		                   </tr>
	                   </tbody>
                	</table>                	
                </div>
                <div class="span6">
                	<div class="span6"><h5 class="text-center" style="padding-top:15px">Receiver</h5></div>
                	<table class="form" width="100%">
	                   <tbody>
		                   <tr>
		                       <td class="noPadding formLbl">
		                           <label for="name"><g:message code="default.name.label"/></label>
		                       </td>
		                       
		                       <td class="noPadding value ${hasErrors(bean: cityInstance, field: 'receiverName', 'errors')}">
		                           <g:textField name="receiverName"  value="${receiverName}"/>
		                       </td>
		                   </tr> 
		                   <tr>
		                       <td class="noPadding formLbl">
		                           <label for="noTlpReceiver"><g:message code="no.tlp.label"/></label>
		                       </td>
		                       
		                       <td class="noPadding value ${hasErrors(bean: cityInstance, field: 'noTlpReceiver', 'errors')}">
		                           <g:textField name="noTlpReceiver" class="numeric" value="${noTlpReceiver}"/>
		                       </td>
		                   </tr>		                   
		                   <tr>
		                       <td class="noPadding formLbl">
		                           <label for="noHpReceiver"><g:message code="mobile.phone.label"/></label>
		                       </td>
		                       
		                       <td class="noPadding value ${hasErrors(bean: cityInstance, field: 'noHpReceiver', 'errors')}">
		                           <g:textField name="noHpReceiver" class="numeric" value="${noHpReceiver}"/>
		                       </td>
		                   </tr>   
		                   <tr>
		                       <td class="noPadding formLbl">
		                           <label for="emailReceiver"><g:message code="email.label"/></label>
		                       </td>
		                       
		                       <td class="noPadding value ${hasErrors(bean: cityInstance, field: 'emailReceiver', 'errors')}">
		                           <g:textField name="emailReceiver"  value="${emailReceiver}"/>
		                       </td>
		                   </tr> 
		                   <tr>
		                       <td class="noPadding formLbl">
		                           <label for="addressReceiver"><g:message code="address.label"/></label>
		                       </td>
		
		                       <td class="noPadding value ${hasErrors(bean: cityInstance, field: 'addressReceiver', 'errors')}">
		                           <g:textArea  name="addressReceiver" value="${addressReceiver}"/>
		                       </td>
		                   </tr> 		                                  
		                   <tr>
		                       <td class="noPadding formLbl">
		                           <label for="descriptionReceiver"><g:message code="city.description"/></label>
		                       </td>
		
		                       <td class="noPadding value ${hasErrors(bean: cityInstance, field: 'descriptionReceiver', 'errors')}">
		                           <g:textArea  name="descriptionReceiver" value="${descriptionReceiver}"/>
		                       </td>
		                   </tr>
	                   </tbody>
                	</table>                	
                </div>
                <br/>
                <div class=" borderAtas">
                	<table class="table13" width="100%" >
	                   <thead>
			                <tr>
								<th class="tblTitle"><g:message code="quantity.label"/></th>
								<th class="tblTitle"><g:message code="unit.label"/></th>
								<th class="tblTitle"><g:message code="price.label"/></th>
								<th class="tblTitle"><g:message code="condition.label"/></th>
								<th class="tblTitle"><g:message code=""/></th>
			                </tr>
			           </thead>
	                   <tbody>
		                   <tr id="rows0">		                   	                     		                       
		                       <td id="kolom1">
		                           <g:textField class="numeric" name="muchItem"  value="${receiverName}"/>
		                       </td>
		                       <td id="kolom2">
		                           <g:select  name="unit" from="${unitList}" optionKey="id" value="${}" optionValue="${{it.name+' - '+it.description}}"/>
		                       </td>
		                       <td id="kolom3">
		                           <g:textField class="price" name="itemAmount"  value="${receiverName}"/>
		                       </td>
		                       <td id="kolom4">
		                           <g:textField name="conditionItem"  value="${receiverName}"/>
		                       </td>
		                       
		                       <td id="buttonMinusAdd">
		                       	   <input type="button" onClick="addbaris(0);" name="addRow" class="add addRow" value="+" />
<%--	                               <input type="button" onClick="minbaris(0);" name="remRow" class="redbtn remRow" value="-"/>--%>
		                       </td>		                       
		                   </tr> 		                   
	                   </tbody>
                	</table>
                </div>
                <div class="span12">
                	<div class="buttons">
		            	<input type="button" class="back redbtn"
		                                            value="${message(code: 'default.button.back.label')}"
		                                            onclick='location.href = "${createLink(url: [controller: 'registerItem', action: 'index'])}"'/>
		                
		                <input type="button" name="create" class="save"
		                                            value="${message(code: 'default.button.create.label')}"
		                                            onClick="checkName();"/>
            		</div>
                </div>
                
                
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
    
    <div id="numberSenderBlank" style="display:none;" title="${message(code: 'contact.sender.number.label')}">
        <p>${message(code: 'default.blank.message', args: [message(code: 'default.sender.number.blank.label')])}</p>
    </div>
    
    <div id="emailBlank" style="display:none;" title="${message(code: 'email.label')}">
        <p>${message(code: 'default.blank.message', args: [message(code: 'email.label')])}</p>
    </div>
    
    <div id="emailInvalid" style="display:none;" title="${message(code: 'userDetails.email.label')}">
	    <p>${message(code: 'userDetails.email.matches.invalid')}</p>
	</div>
	
	<div id="numberReceiverBlank" style="display:none;" title="${message(code: 'contact.receiver.number.label')}">
        <p>${message(code: 'default.blank.message', args: [message(code: 'default.sender.number.blank.label')])}</p>
    </div>
    
    <div id="addressBlank" style="display:none;" title="${message(code: 'address.label')}">
	    <p>${message(code: 'default.blank.message', args: [message(code: 'address.label')])}</p>
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
		<div id="NoPlatBlank" style="display:none;" title="${message(code: 'car.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'no.plat.label')])}</p>
		</div>
		<div id="countryBlank" style="display:none;" title="${message(code: 'branch.country.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'branch.country.label')])}</p>
		</div>
		<div id="addressBlank" style="display:none;" title="${message(code: 'branch.address1.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'branch.address1.label')])}</p>
		</div>
		<div id="phoneNoBlank" style="display:none;" title="${message(code: 'branch.phoneNo.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'branch.phoneNo.label')])}</p>
		</div>

	
<script type="text/javascript">
	var url = "${createLink(url: [controller: 'car', action: 'findNoPlatByName'])}";
<%--	var hostSize = ${hostList.size()};--%>
<%--    var currentHost = ${host != null ? host.size() : 1};--%>
var temp = 1;
</script>
<asset:javascript src='/perpage/registerItem.js'/>
</body>
</html>

