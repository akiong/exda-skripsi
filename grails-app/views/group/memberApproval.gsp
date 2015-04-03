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
    <g:set var="entityName" value="${message(code: 'member.approval.label')}"/>
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>

<body>
<div class="body">
    <div class="contentbar"><h1>${entityName}</h1></div>

    <div class="contentbox">		
		<div class="transDetail"><g:message code="default.list.label" args="[entityName]" /></div>
		<div class="buttons">
                  <span class="button"><input type="button" class="back" value="${message(code: 'default.button.back.label')}" onclick='location.href = "${createLink(url: [controller: 'group', action: 'show', id: groupId])}"'/>
               </span>
            </div>
		
	            <table class="table13" width="100%">
	                <thead>
	                <tr>
	                    <th class="tblTitle"><g:message code="default.name.label"/></th>
	                    <th class="tblTitle"><g:message code="userDetails.mobilePhoneNo.label"/></th>
						<th class="tblTitle"><g:message code="userDetails.email.label"/></th>
						<th class="tblTitle"><g:message code="group.userLimit.label"/></th>
	                </tr>
	                </thead>
	                <tbody>
	                <g:each in="${taskList}" status="i" var="task">
	                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
							<td><g:link controller="groupMember" action="approval" id="${task['taskId']}">${task["memberName"]}</g:link></td>
	                        
	                        <td>${task["mobilePhoneNo"]}</td>
	
	                        <td>${task["email"]}</td>
	                        
	                        <td class="price">${task["userLimit"]}</td>
	                    </tr>
	                </g:each>
	                </tbody>
	            </table>
	        <div class="paginateButtons">
	            <g:paginate total="${total}" params='[id:"${groupId}"]'/>
	        </div>
    </div>

</div>
</body>
</html>
