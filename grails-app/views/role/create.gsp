<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="secure2"/>
    <g:set var="entityName" value="${message(code: 'role.label')}"/>
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
        <g:if test="${flash.error}">
            <div class="errors"><ul><li>${flash.error}</li></ul></div>
        </g:if>
        <g:hasErrors bean="${roleInstance}">
            <div class="errors">
                <g:renderErrors bean="${roleInstance}" as="list"/>
            </div>
        </g:hasErrors>
        <g:form action="save" useToken="true">
            <div class="dialog">
                <table class="form" width="100%">
                    <tbody>

                    <tr>
                        <td class="formLbl"><label for="authority"><g:message
                                code="role.authority.label"/></label></td>
                        <td class="value ${hasErrors(bean: roleInstance, field: 'authority', 'errors')}">
                            <g:textField id="authority" name="authority" value="${roleInstance?.authority}"/>
                        </td>
                    </tr>

                    </tbody>
                </table>
                <g:message code="role.menu.label"/><br/>

                <div id="tree">
                    <%
                        def createMenu = { obj ->
                            out << "<ul>"
                            obj.each { menu ->
                                def isChecked = roleInstance?.roleMenus.collect {it.menu}*.id.contains(menu.id)
                                def list = com.akiong.security.Menu.createCriteria().list {
                                    eq("parentMenu", menu)
                                    ne("sequence", 0)
                                    order("sequence", "asc")
                                }
                                if (list.size() > 0) {
                                    out << "<li id='" + menu.id + "' class='jstree-open" + (isChecked ? " jstree-undetermined" : "") + "'>" << "<a class='atr3' href='#'>" << menu.name << "</a>"
                                    owner.call(list)
                                }
                                else {
                                    out << "<li id='" + menu.id + "'" + (isChecked ? " class='jstree-checked'" : "") + ">" << "<a class='atr3' href='#'>" << menu.name << "</a>"
                                }
								
								out << "</li>"
                            }
                            out << "</ul>"
                        }

                        def menuList = com.akiong.security.Menu.createCriteria().list {
                            isNull("parentMenu")
                            ne("sequence", 0)
                            order("sequence", "asc")
                        }
                        createMenu(menuList)

                    %>
                </div>
            </div>

            <div class="buttons">
                <span class="button"><input type="button" class="back redbtn"
                                            value="${message(code: 'default.button.back.label')}"
                                            onclick='location.href = "${createLink(url: [controller: 'role', action: 'list'])}"'/>
                </span>
                <span class="button"><input type="button" name="create" class="save"
                                            value="${message(code: 'default.button.create.label')}"
                                            onClick="checkName();"/></span>
                
            </div>
        </g:form>
    </div>

    <div id="authorityExistSection" style="display:none;" title="${message(code: 'default.duplicate.label', args: [message(code: 'role.authority.label')])}">
        <p>${message(code: 'default.duplicate.msg.label', args: [message(code: 'role.label'), message(code: 'role.authority.label')])}</p>
    </div>
    
    <div id="authorityError" style="display:none;" title="${message(code: 'role.authority.label')}">
        <p>${message(code: 'role.mustnot.contain.msg')}</p>
    </div>

</div>
<asset:javascript src='jquery.jstree.js'/>
<script type="text/javascript">
    var theUrl = "${resource(dir:'js/themes')}";
    var theTheme = 'classic'; //default, classic, apple, default-rtl

    var url = "${createLink(url: [controller: 'role', action: 'findByAuthority'])}";
</script>
<asset:javascript src='/perpage/roleCreate.js'/>
</body>
</html>
