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
    <g:set var="entityName" value="${message(code: 'changePassword.label')}"/>
    <title>${entityName}</title>
</head>

<body>
<div class="body">
    <div class="contentbar">
        <g:if test="${firstTime == true}">
            <h1><g:message code="changePasswordBO.welcome.label" args="${[name]}"/></h1>
        </g:if>
        <g:else>
            <h1>${entityName}</h1>
        </g:else>
    </div>

    <div class="contentbox">

        <g:if test="${firstTime == true}">
            <div class="myH2"><g:message code="changePasswordBO.firstTime.label"/><br/>
                <g:message code="changePasswordBO.change.label"/></div>
            <br/>
        </g:if>
        <div class="transDetail">${entityName}</div>

        <g:form name="changePasswordForm" action="redirectForm">

            <table class="form" width="100%">
                <tbody>
                <tr>
                    <td class="formLbl"><label for="oldPassword"><g:message code="changePasswordFO.oldPassword.label"/></label></td>
                    <td class="value">
                        <g:passwordField name="oldPassword" maxlength="20" size="35"/>
                    </td>
                </tr>

                <tr class="borderTop">
                    <td class="formLbl"><label for="newPassword"><g:message code="changePasswordFO.newPassword.label"/></label></td>
                    <td class="value">
                        <g:passwordField id="newPassword" name="newPassword" maxlength="20" size="35"/>
                    </td>
                </tr>

                <tr class="borderTop">
                    <td class="formLbl"><label for="confirmNewPassword"><g:message code="changePasswordFO.confirmNewPassword.label"/></label></td>
                    <td class="value">
                        <g:passwordField id="confirmNewPassword" name="confirmNewPassword" maxlength="20"
                                         size="35"/>
                    </td>
                </tr>
                </tbody>
            </table>

            <div class="buttons">
                <span class="button"><input type="button" name="reset" class="redbtn" id="reset"
                                            value="${message(code: 'default.button.reset.label')}"
                                            onClick="resetField();"/></span>
                <span class="button"><input type="button" name="save" class="save" id="save"
                                            value="${message(code: 'default.button.save.label')}"
                                            onClick="cekPassword();"/></span>
                
            </div>
        </g:form>
    </div>

</div>

<div id="changePassword" style="display:none;" title="${message(code: 'changePasswordFO.forceChangePassword.label')}">
    <p>${message(code: 'changePasswordFO.forceChangePassword.msg.label')}</p>
</div>

<div id="notMatchPassword" style="display:none;" title="${message(code: 'changePasswordBO.notMatchPassword.label')}">
    <p>${message(code: 'changePasswordBO.notMatchPassword.msg.label')}</p>
</div>

<div id="blankOldPassword" style="display:none;" title="${message(code: 'changePasswordBO.blankOldPassword.label')}">
    <p>${message(code: 'changePasswordBO.blankOldPassword.msg.label')}</p>
</div>

<div id="blankNewPassword" style="display:none;" title="${message(code: 'changePasswordBO.blankNewPassword.label')}">
    <p>${message(code: 'changePasswordBO.blankNewPassword.msg.label')}</p>
</div>

<div id="incorrectOldPassword" style="display:none;" title="${message(code: 'changePasswordBO.incorrectOldPassword.label')}">
    <p>${message(code: 'changePasswordBO.incorrectOldPassword.msg.label')}</p>
</div>

<div id="invalidNewPassword" style="display:none;" title="${message(code: 'changePasswordBO.incorrectNewPassword.label')}">
    <p>${message(code: 'changePasswordBO.incorrectNewPassword.msg.label')}</p>
</div>

<div id="lockedID" style="display:none;" title="${message(code: 'user.id.lock.title')}">
    <p>${message(code: 'user.id.lock.msg')}</p>
</div>
<asset:javascript src='/jquery/jquery.jcryption-1.1.js'/>
<script type="text/javascript">
    var urlUpdatePassword = "${createLink(url: [controller: 'changePassword', action: 'updatePassword'])}";
    var encryptPaswd = "${createLinkTo(dir:'')}" + "/api/userDetails/encryptionPassword";
    var passConfig = "${passConfig?:''}";
</script>
<asset:javascript src='/perpage/changePassword.js'/>
</body>
</html>
