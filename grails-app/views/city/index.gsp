<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="secure2" />
        <g:set var="entityName" value="${message(code: 'city.label')}" />
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
					<g:sortableColumn property="city" title="${message(code: 'city.label')}"
                                      params='[city:"${city}", descrtipion:"${description}"]'/>
                    <g:sortableColumn property="description" title="${message(code: 'city.description')}"
                                      params='[city:"${city}", descrtipion:"${description}"]'/>

                                                          
                </tr>
                </thead>
                <tbody>
                <g:each in="${citydetaillist}" status="i" var="cityInstance">
                    <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                        <td><g:link action="show"
                                    id="${cityInstance.id}">${fieldValue(bean: cityInstance, field: "city")}</g:link></td>
                        
                        <td>${fieldValue(bean: cityInstance, field: "description")}</td>
                    </tr>
                </g:each>
                </tbody>
            </table>
            
            	<div class="paginateButtons">
            		<g:paginate total="${citydetaillisttotal}" params='[city:"${city}", descrtipion:"${description}"]'/>
        		</div>
        	</div>
        	
        	<div class="listfilter">        	
	    	<g:form action="index">
            	<div class="filterfont"><g:message code="default.filter.label"/></div>
	            <label for="name"><g:message code="city.label"/></label>
	            <g:textField name="city" value="${city}"/>
	
	            <div>
	                <span class="button">
                				<g:submitButton name="search" class="search" value="${message(code: 'default.button.search.label')}"/>
                			</span>
	                		<span class="button">
	                			<input type="button" class="add" value="${message(code: 'default.button.add.label')}"
	                                            onclick='location.href = "${createLink(url: [controller: 'city', action: 'create'])}"'/>
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
