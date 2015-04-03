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
    <g:set var="entityName" value="${message(code: 'mytask.label')}"/>
    <title>${entityName}</title>
</head>

<body>
<div class="body">
    <div class="contentbar"><h1>${entityName}</h1></div>

    <div class="contentbox">

        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <g:if test="${flash.error}">
            <div class="errors"><ul><li>${flash.error}</li></ul></div>
        </g:if>
		<br/>
		
		<div class="listcontent">
			<g:form useToken="true">
	            <table class="table13" width="100%">
	                <thead>
	                <tr>
	                	<th class="tblTitle" width="5%"><g:checkBox name="checkAll" id="checkAll" checked="false"/></th>
	                    <th class="tblTitle" width="25%"><g:message code="date.time.label"/></th>
	                    <th class="tblTitle" width="30%"><g:message code="cif.corporate.label"/></th>
						<th class="tblTitle" width="40%"><g:message code="default.type.label"/></th>
	                </tr>
	                </thead>
	                <tbody>
	                <g:each in="${taskList}" status="i" var="task">
	                    <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
							<td style="text-align: center;"><g:checkBox class="checkk" name="id" value="${task['taskId']}" checked="false"/></td>
							
	                        <td><g:formatDate format="dd/MM/yyyy HH:mm:ss" date="${task['changeDate']}"/></td>
	                        
	                        <td>${task["corpName"]}</td>
	
	                        <td>
	                        	<g:if test="${task['objectType'] == 'cif'}">
	                        		<g:if test="${task['type'] == 'create'}">
	                        			<g:link controller="cif" action="approval" id="${task['taskId']}">${message(code: 'default.create.label', args:[message(code: 'cif.corporate.label')])}</g:link>
	                        		</g:if>
	                        		<g:elseif test="${task['type'] == 'update'}">
	                        			<g:link controller="cif" action="approval" id="${task['taskId']}">${message(code: 'default.edit.label', args:[message(code: 'cif.corporate.label')])}</g:link>
	                        		</g:elseif>
	                        		<g:elseif test="${task['type'] == 'delete'}">
	                        			<g:link controller="cif" action="approval" id="${task['taskId']}">${message(code: 'default.delete.label', args:[message(code: 'cif.corporate.label')])}</g:link>
	                        		</g:elseif>
	                        	</g:if>
	                        	<g:if test="${task['objectType'] == 'cardProfile'}">
	                        		<g:if test="${task['type'] == 'create'}">
	                        			<g:link controller="cardProfile" action="approval" id="${task['taskId']}">${message(code: 'default.create.label', args:[message(code: 'cardProfile.label')])}</g:link>
	                        		</g:if>
	                        		<g:elseif test="${task['type'] == 'update'}">
	                        			<g:link controller="cardProfile" action="approval" id="${task['taskId']}">${message(code: 'default.edit.label', args:[message(code: 'cardProfile.label')])}</g:link>
	                        		</g:elseif>
	                        		<g:elseif test="${task['type'] == 'delete'}">
	                        			<g:link controller="cardProfile" action="approval" id="${task['taskId']}">${message(code: 'default.delete.label', args:[message(code: 'cardProfile.label')])}</g:link>
	                        		</g:elseif>
	                        	</g:if>
	                        	<g:if test="${task['objectType'] == 'terminal'}">
	                        		<g:if test="${task['type'] == 'create'}">
	                        			<g:link controller="terminal" action="approval" id="${task['taskId']}">${message(code: 'default.create.label', args:[message(code: 'terminal.label')])}</g:link>
	                        		</g:if>
	                        		<g:elseif test="${task['type'] == 'update'}">
	                        			<g:link controller="terminal" action="approval" id="${task['taskId']}">${message(code: 'default.edit.label', args:[message(code: 'terminal.label')])}</g:link>
	                        		</g:elseif>
	                        		<g:elseif test="${task['type'] == 'delete'}">
	                        			<g:link controller="terminal" action="approval" id="${task['taskId']}">${message(code: 'default.delete.label', args:[message(code: 'terminal.label')])}</g:link>
	                        		</g:elseif>
	                        	</g:if>
	                        </td>
	                    </tr>
	                </g:each>
	                </tbody>
	            </table>
	        <div class="paginateButtons">
	            <g:paginate total="${total}" params='[name:"${name}"]'/>
	        </div>
	        
	        <br/>
	        <div class="buttons">
	        	
	        	<g:hiddenField name="taskId" value="${taskId}"/>
	                	<span class="button"><g:actionSubmit class="reject redbtn" action="rejectBulk"
	                                                         value="${message(code: 'default.button.reject.label')}"/></span>
	                    <span class="button"><g:actionSubmit class="approve" action="approveBulk"
		                                                     value="${message(code: 'default.button.approve.label')}"/></span>
						
		    </div>
		    </g:form>
	    </div>
	    <div class="listfilter">
            	<g:form action="list">
            		<div class="filterfont"><g:message code="default.filter.label"/></div>
		            <label for="group"><g:message code="cif.corpName.label"/></label>
		            <g:textField name="name" maxlength="100" value="${name}"/>
		
		            <div>
		                <g:submitButton name="search" class="search" value="${message(code: 'default.button.search.label')}"/>
		            </div>
		        </g:form>
		     </div>
		     <div class="clear"></div>
    </div>

</div>
<script type="text/javascript">
    var total = "${taskList.size()}";
</script>
<asset:javascript src='/perpage/corporateMaintenance.js'/>
</body>
</html>
