<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="secure2" />
        <g:set var="entityName" value="${message(code: 'car.label')}" />
        <title><g:message code="default.list.label" args="[entityName]"/></title>
    </head>
    <body>
    <div class="body">
    	<div class="contentbar"><h1>${entityName}</h1></div>

   		<div class="leftbottombar"></div>

    	<div class="cuttedbluebartop"></div>

    	<div class="rightbottombar"></div>

    	<div class="contentbox">

        <div class="transDetail"><g:message code="default.list.label" args="[entityName]"/></div>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        
        <div class="listcontent">
        <table class="table13" width="100%">
                <thead>
                <tr>
					<g:sortableColumn property="noPlat" title="${message(code: 'no.plat.label')}"
                                      params='[noPlat:"${noPlat}", descrtipion:"${description}"]'/>
                    <g:sortableColumn property="description" title="${message(code: 'city.description')}"
                                      params='[noPlat:"${noPlat}", descrtipion:"${description}"]'/>

                                                          
                </tr>
                </thead>
                <tbody>
                <g:each in="${carList}" status="i" var="car">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                        <td><g:link action="show"
                                    id="${car.id}">${fieldValue(bean: car, field: "noPlat")}</g:link></td>
                        
                        <td>${fieldValue(bean: car, field: "description")}</td>
                    </tr>
                </g:each>
                </tbody>
            </table>
            
            	<div class="paginateButtons">
            		<g:paginate total="${carTotal}" params='[noPlat:"${noPlat}", descrtipion:"${description}"]'/>
        		</div>
        	</div>
        	
        	<div class="listfilter">        	
	    	<g:form action="index">
            	<div class="filterfont"><g:message code="default.filter.label"/></div>
	            <label for="name"><g:message code="no.plat.label"/></label>
	            <g:textField name="noPlat" value="${noPlat}"/>
	
	            <div>
	                <span class="button">
                				<g:submitButton name="search" class="search" value="${message(code: 'default.button.search.label')}"/>
                			</span>
	                		<span class="button">
	                			<input type="button" class="add" value="${message(code: 'default.button.add.label')}"
	                                            onclick='location.href = "${createLink(url: [controller: 'car', action: 'create'])}"'/>
	                		</span>
	            </div>
            	
	        </g:form>
	        </div>
	        <div class="clear"></div>
	        <br/>
            </div>
        </div>
    </body>
</html>
