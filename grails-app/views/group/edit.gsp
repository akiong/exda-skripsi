%{--
  - @copyright 2011 PT. Teravin Technovations. All rights reserverd.
  -
  - This source code is an unpublished work and the use of  a copyright  notice
  - does not imply otherwise. This source  code  contains  confidential,  trade
  - secret material of PT. Teravin Technovations.
  - Any attempt or participation in deciphering, decoding, reverse  engineering
  - or in any way altering the source code is strictly  prohibited, unless  the
  - prior  written consent of PT. Teravin Technovations is obtained.
  -
  - Unless  required  by  applicable  law  or  agreed  to  in writing, software
  - distributed under the License is distributed on an "AS IS"  BASIS,  WITHOUT
  - WARRANTIES OR CONDITIONS OF ANY KIND, either express or  implied.  See  the
  - License for the specific  language  governing  permissions  and limitations
  - under the License.
  --}%

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="secure2"/>
    <g:set var="entityName" value="${message(code: 'grup.label')}"/>
    <title><g:message code="default.edit.label" args="[entityName]"/></title>
</head>

<body>
<div class="body">
    <div class="contentbar"><h1>${entityName}</h1></div>

    <div class="contentbox">
        <div class="transDetail"><g:message code="default.edit.label" args="[entityName]"/></div>
        
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <g:if test="${flash.error}">
            <div class="errors"><ul><li>${flash.error}</li></ul></div>
        </g:if>
        <g:hasErrors bean="${groupInstance}">
            <div class="errors">
                <g:renderErrors bean="${groupInstance}" as="list"/>
            </div>
        </g:hasErrors>
        <g:form action="update" useToken="true">
            <g:hiddenField name="id" value="${groupInstance?.id}"/>
            <g:hiddenField name="version" value="${groupInstance?.version}"/>
            <table class="form" width="100%">
                <tbody>
                
                <tr>
                    <td class="formLbl singgleTr"><g:message code="grup.grupId.label"/></td>
    
                    <td class="value">${fieldValue(bean: groupInstance, field: "grupId")}</td>
    
                </tr>

                <tr class="borderTop">
			        <td class="formLbl"><label for="name"><g:message code="grup.name.label"/></label></td>
			        <td class="value ${hasErrors(bean: groupInstance, field: 'name', 'errors')}">
			            <g:textField name="name" maxlength="200" value="${groupInstance?.name}"/>
			        </td>
			    </tr>
			    
			    <tr class="borderTop">
			        <td class="formLbl"><label for="dbaName"><g:message code="grup.dbaName.label"/></label></td>
			        <td class="value ${hasErrors(bean: groupInstance, field: 'dbaName', 'errors')}">
			            <g:textField name="dbaName" maxlength="200" value="${groupInstance?.dbaName}"/>
			        </td>
			    </tr>
			    
			    <tr>
	                <td class="formLbl">
	                    <label for="province"><g:message code="province.label"/></label>
	                </td>
	
	                <td>
	                    <g:select id="province" name="province.id" value="${groupInstance?.province?.id}"
						           noSelection="${['null':'-Choose your Province-']}"
						           from="${provinceList}" optionKey="id"
						           onchange="${remoteFunction(
	                                         controller:'group',
	                                         action:'findCityByProvince',
	                                         params:'\'province=\' + escape(this.value)', 
	                                         onComplete:'updateClassCity1(arguments[0])')}" 
	           			/>
	                </td>	
            	</tr>

               	<tr>
                    <td class="formLbl">
                        <label for="city"><g:message code="city.label"/></label>
                    </td>

                    <td>
                        <g:select id="city" name="city.id" value="${groupInstance?.city?.id}"
                                         noSelection="${['null':'-Choose your City-']}"
                                         from="${cityList}" optionKey="id"/>
                    </td>   
                </tr>   

                <tr class="borderTop">
                    <td class="formLbl topPadding"><label for="location"><g:message code="grup.location.label"/></label>
                    </td>
                    <td class="value">
                        <g:textArea name="location" cols="40" rows="5" value="${groupInstance?.location}"/>
                    </td>
                </tr>
                
                <tr>
					<td colspan="2" class="detail">
						<g:message code="group.owner.label" />
					</td>
				</tr>
				
				<tr>
                    <td class="formLbl">
                        <label for="ofname"><g:message code="userDetails.firstName.label"/></label>
                    </td>
                    <td class="value ${hasErrors(bean: owner, field: 'firstName', 'errors')}">
                        <g:textField id="ofname" name="owner.firstName" maxlength="100" value="${ownernya?.firstName}"/>
                    </td>
                </tr>

                <tr class="borderTop">
                    <td class="formLbl">
                        <label for="olname"><g:message code="userDetails.lastName.label"/></label>
                    </td>
                    <td class="value ${hasErrors(bean: owner, field: 'lastName', 'errors')}">
                        <g:textField id="olname" name="owner.lastName" maxlength="100" value="${ownernya?.lastName}"/>
                    </td>
                </tr>

                <tr class="borderTop">
                    <td class="formLbl singgleTr"><g:message code="userDetails.userID.label"/></td>

                    <td class="value">
                        ${ownernya?.userDetails?.user?.username}
                    </td>
                </tr>

                <tr class="borderTop">
                    <td class="formLbl">
                        <label for="ouserAlias"><g:message code="userDetails.userAlias.label"/></label>
                    </td>
                    <td class="value ${hasErrors(bean: owner?.userDetails, field: 'userAlias', 'errors')}">
                        <g:textField id="ouserAlias" name="owner.userDetails.userAlias" maxlength="100" value="${ownernya?.userDetails?.userAlias}"/>
                    </td>
                </tr>

                <tr class="borderTop">
                    <td class="formLbl">
                        <label for="oemail"><g:message code="userDetails.email.label"/></label>
                    </td>
                    <td class="value ${hasErrors(bean: owner?.userDetails, field: 'email', 'errors')}">
                        <g:textField id="oemail" name="owner.userDetails.email" value="${ownernya?.userDetails?.email}"/>
                    </td>
                </tr>

                <tr class="borderTop">
                    <td class="formLbl">
                        <label for="omobilePhoneNo"><g:message code="userDetails.mobilePhoneNo.label"/></label>
                    </td>
                    <td class="value ${hasErrors(bean: owner?.userDetails, field: 'mobilePhoneNo', 'errors')}">
                        <g:textField id="omobilePhoneNo" class="numeric" name="owner.userDetails.mobilePhoneNo" value="${ownernya?.userDetails?.mobilePhoneNo}"/>
                    </td>
                </tr>
                
                <tr class="borderTop">
                    <td class="formLbl">
                        <label for="accoutnNo"><g:message code="account.no.label"/></label>
                    </td>
                    <td class="value ${hasErrors(bean: owner, field: 'accoutnNo', 'errors')}">
                        <g:textField id="accoutnNo" class="numeric" name="owner.accoutnNo" maxlength="100" value="${ownernya?.accoutnNo}"/>
                    </td>
                </tr>

                </tbody>
            </table>

            <div class="buttons">
            	<span><input type="button" name="Submit" value="${message(code: 'default.button.update.label')}" onclick="checkData();"/></span>
                <span class="button"><input type="button" class="redbtn" value="${message(code: 'default.button.back.label')}"
	                                            onclick='window.location.href = "${createLink(url: [controller: 'group', action: 'show', id: groupInstance?.id])}"'/>
                </span>
            </div>
        </g:form>
    </div>
    <div id="cityBlank" style="display:none;" title="${message(code: 'branch.city.label')}">
	    <p>${message(code: 'city.select.label')}</p>
	</div>		
	<div id="provinceBlank" style="display:none;" title="${message(code: 'branch.province.label')}">
        <p>${message(code: 'province.select.label')}</p>
    </div> 
	<div id="groupNameBlank" style="display:none;" title="${message(code: 'grup.name.label')}">
	    <p>${message(code: 'default.blank.message', args: [message(code: 'grup.name.label')])}</p>
	</div>
	<div id="groupDbaNameBlank" style="display:none;" title="${message(code: 'grup.dbaName.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'grup.dbaName.label')])}</p>
		</div>
	<div id="groupLocBlank" style="display:none;" title="${message(code: 'grup.location.label')}">
	    <p>${message(code: 'default.blank.message', args: [message(code: 'grup.location.label')])}</p>
	</div>
	<div id="firstNameBlank" style="display:none;" title="${message(code: 'userDetails.firstName.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'userDetails.firstName.label')])}</p>
		</div>		
		<div id="lastNameBlank" style="display:none;" title="${message(code: 'userDetails.lastName.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'userDetails.lastName.label')])}</p>
		</div>
		<div id="userAliasBlank" style="display:none;" title="${message(code: 'userDetails.userAlias.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'userDetails.userAlias.label')])}</p>
		</div>
		<div id="emailBlank" style="display:none;" title="${message(code: 'userDetails.email.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'userDetails.email.label')])}</p>
		</div>
		<div id="emailInvalid" style="display:none;" title="${message(code: 'userDetails.email.label')}">
		    <p>${message(code: 'userDetails.email.matches.invalid')}</p>
		</div>
		<div id="phoneNoBlank" style="display:none;" title="${message(code: 'cif.phoneNo.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'cif.phoneNo.label')])}</p>
		</div>		
		<div id="accountBlank" style="display:none;" title="${message(code: 'account.no.label')}">
		    <p>${message(code: 'default.blank.message', args: [message(code: 'account.no.label')])}</p>
		</div>
	<div id="duplicateName" style="display:none"
	     title="${message(code: 'default.duplicate.label', args: [message(code: 'grup.label')])}">
	    <p>${message(code: 'default.duplicate.msg.label', args: [message(code: 'grup.label'), message(code: 'grup.grupId.label')])}</p>
	</div>
	
	<script type="text/javascript">	
			function updateClassCity1(e) {
		        updateCity(e, "city");
		    }
	</script>
	<asset:javascript src='/perpage/groupEdit.js'/>	
</div>
</body>
</html>
