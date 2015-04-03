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
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<div class="body">
    <div class="contentbar"><h1>${entityName}</h1></div>

    <div class="contentbox">
        <div class="transDetail"><g:message code="default.show.label" args="[entityName]"/></div>
        
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        
        <table class="form" width="100%">
            <tbody>

            <tr>
                <td class="formLbl singgleTr"><g:message code="default.name.label"/></td>

                <td class="value">${fieldValue(bean: menuInstance, field: "name")}</td>

            </tr>

            <tr class="borderTop">
                <td class="formLbl singgleTr"><g:message code="default.description.label"/></td>

                <td class="value">${fieldValue(bean: menuInstance, field: "description")}</td>

            </tr>

            <tr class="borderTop">
                <td class="formLbl singgleTr"><g:message code="menu.url.label"/></td>

                <td class="value">${fieldValue(bean: menuInstance, field: "url")}</td>

            </tr>

            </tbody>
        </table>

        <div class="buttons">
            <g:form action="delete" useToken="true">
                <g:hiddenField name="version" value="${menuInstance?.version}"/>
                <g:hiddenField name="id" value="${menuInstance?.id}"/>
                <span class="button"><input type="button" class="edit" value="${message(code: 'default.button.edit.label')}"
                                            onclick='location.href = "${createLink(url: [controller: 'menu', action: 'edit', id: menuInstance?.id])}"'/></span>
                <span class="button"><input type="submit" class="delete redbtn" value="${message(code: 'default.button.delete.label')}"
                                                     onclick="return confirm('${message(code: 'default.button.delete.confirm.message')}');"/></span>
                <span class="button"><input type="button" class="back"
                                            value="${message(code: 'default.button.back.label')}"
                                            onclick='location.href = "${createLink(url: [controller: 'menu', action: 'list'])}"'/>
                </span>
            </g:form>
        </div>
    </div>

</div>
</body>
</html>
