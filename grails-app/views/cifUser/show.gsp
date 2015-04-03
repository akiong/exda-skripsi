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

<%@ page import="com.teravin.collection.security.UserDetails" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="secure2"/>
    <g:set var="entityName" value="${message(code: 'user.label')}"/>
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
        <g:hasErrors bean="${cifuser}">
            <div class="errors">
                <g:renderErrors bean="${cifuser}" as="list"/>
            </div>
        </g:hasErrors>

        <table class="form" width="100%">
            <tbody>
            <tr>
                <td class="formLbl singgleTr"><g:message code="userDetails.name.label"/></td>
                <td class="value">${cifuser?.userDetails?.firstName} ${cifuser?.userDetails?.lastName}</td>
            </tr>
            <tr class="borderTop">
                <td class="formLbl singgleTr"><g:message code="userDetails.userID.label"/></td>
                <td class="value">${cifuser?.userDetails?.user?.username}</td>
            </tr>
            <tr class="borderTop">
                <td class="formLbl singgleTr"><g:message code="userDetails.userAlias.label"/></td>
                <td class="value">${cifuser?.userDetails?.userAlias}</td>
            </tr>
            <tr class="borderTop">
                <td class="formLbl singgleTr"><g:message code="userDetails.email.label"/></td>
                <td class="value">
                	<g:if test="${email}">
                       ${email}
                    </g:if>
                    <g:else>
                        ${cifuser?.userDetails?.email}
                    </g:else>
                </td>
            </tr>
            <tr class="borderTop">
                <td class="formLbl singgleTr"><g:message code="userDetails.mobilePhoneNo.label"/></td>
                <td class="value">${cifuser?.userDetails?.mobilePhoneNo}</td>
            </tr>
            <tr class="borderTop">
                <td class="formLbl singgleTr"><g:message code="userDetails.status.label"/></td>
                <td class="value">
                    <g:if test="${cifuser?.userDetails?.status.equals('1')}"><g:message
                            code="userDetails.status.inactive.label"/></g:if>
                    <g:elseif test="${cifuser?.userDetails?.status.equals('2')}"><g:message
                            code="userDetails.status.active.label"/></g:elseif>
                    <g:elseif test="${cifuser?.userDetails?.status.equals('3')}"><g:message
                            code="userDetails.status.locked.label"/></g:elseif>
                    <g:else><g:message code="userDetails.status.blocked.label"/></g:else>
                </td>
            </tr>
            <tr class="borderTop">
                <td class="formLbl singgleTr"><g:message code="role.label"/></td>
                <td class="value">
                	<g:if test="${cifuser.bo.equals('Y') }">
                		<div><g:message code="bo.label"/></div>
                	</g:if>
                	<g:if test="${cifuser.finance.equals('Y') }">
                		<div><g:message code="finance.label"/></div>
                	</g:if>
                </td>
            </tr>
            </tbody>
            </table>
            <br/>
                
        <br/>
        <div class="buttons">
            <g:form action="delete" useToken="true">
                <g:hiddenField name="id" value="${cifuser?.id}"/>
                <g:hiddenField name="version" value="${cifuser?.version}"/>
                <span class="button"><input type="button" class="edit" value="${message(code: 'default.button.edit.label')}"
                                            onclick='location.href = "${createLink(url: [controller: 'cifUser', action: 'edit', id: cifuser?.id])}"'/></span>
                <span class="button"><input type="submit" class="delete redbtn" value="${message(code: 'default.button.delete.label')}"
                                                     onclick="return confirm('${message(code: 'default.button.delete.confirm.message')}');"/></span>
                <span class="button"><input type="button" class="back"
                                            value="${message(code: 'default.button.back.label')}"
                                            onclick='location.href = "${createLink(url: [controller: 'cifUser', action: 'list'])}"'/>
                </span>
            </g:form>
        </div>
    </div>

</div>
</body>
</html>
