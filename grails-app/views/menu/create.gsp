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
    <g:set var="entityName" value="${message(code: 'menu.label')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<div class="body">
    <div class="contentbar"><h1>${entityName}</h1></div>

    <div class="contentbox">
        <div class="transDetail"><g:message code="default.create.label" args="[entityName]"/></div>
        
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${menuInstance}">
            <div class="errors">
                <g:renderErrors bean="${menuInstance}" as="list"/>
            </div>
        </g:hasErrors>
        <g:form action="save" useToken="true">
            <table class="form" width="100%">
                <tbody>
                <tr>
                    <td class="formLbl">
                        <label for="name"><g:message code="default.name.label"/></label>
                    </td>
                    <td class="value ${hasErrors(bean: menuInstance, field: 'name', 'errors')}">
                        <g:textField name="name" maxlength="100" value="${menuInstance?.name}"/>
                    </td>
                </tr>

                <tr class="borderTop">
                    <td class="formLbl topPadding">
                        <label for="description"><g:message code="default.description.label"/></label>
                    </td>
                    <td class="value ${hasErrors(bean: menuInstance, field: 'description', 'errors')}">
                        <g:textArea name="description" cols="40" rows="5" value="${menuInstance?.description}"/>
                    </td>
                </tr>

                <tr class="borderTop">
                    <td class="formLbl">
                        <label for="url"><g:message code="menu.url.label"/></label>
                    </td>
                    <td class="value ${hasErrors(bean: menuInstance, field: 'url', 'errors')}">
                        <g:textField name="url" maxlength="120" style="width:250px" value="${menuInstance?.url}"/>
                    </td>
                </tr>

                </tbody>
            </table>

            <div class="buttons">
                <span class="button"><input type="button" class="back redbtn"
                                            value="${message(code: 'default.button.back.label')}"
                                            onclick='location.href = "${createLink(url: [controller: 'menu', action: 'list'])}"'/>
                </span>
                
                <span class="button"><g:submitButton name="create" class="save"
                                                     value="${message(code: 'default.button.create.label')}"/></span>
                
            </div>
        </g:form>
    </div>
</div>
</body>
</html>
