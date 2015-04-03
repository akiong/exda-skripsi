<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="secure2"/>
    <g:set var="entityName" value="${message(code: 'role.label')}"/>
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
        <g:hasErrors bean="${roleInstance}">
            <div class="errors">
                <g:renderErrors bean="${roleInstance}" as="list"/>
            </div>
        </g:hasErrors>
        <div class="dialog">
            <table class="form" width="100%">
                <tbody>

                <tr>
                    <td class="formLbl singgleTr"><label for="authority"><g:message
                            code="role.authority.label"/></label></td>
                    <td class="value ${hasErrors(bean: roleInstance, field: 'authority', 'errors')}">
                        ${roleInstance?.authority}
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
            <g:form action="delete" useToken="true">
                <g:hiddenField name="id" value="${roleInstance?.id}"/>
                <g:hiddenField name="version" value="${roleInstance?.version}"/>
                <g:hiddenField name="show" value="show"/>
                <g:if test="${roleInstance.isSystem == 'N'}">
	                <span class="button"><input type="button" class="edit" value="${message(code: 'default.button.edit.label')}"
	                                            onclick='location.href = "${createLink(url: [controller: 'role', action: 'edit', id: roleInstance?.id])}"'/></span>
	                <span class="button"><input type="submit" class="delete redbtn" value="${message(code: 'default.button.delete.label')}"
	                                                     onclick="return confirm('${message(code: 'default.button.delete.confirm.message')}');"/></span>
                </g:if>
                <span class="button"><input type="button" class="back"
                                            value="${message(code: 'default.button.back.label')}"
                                            onclick='location.href = "${createLink(url: [controller: 'role', action: 'list'])}"'/>
                </span>
            </g:form>
        </div>
    </div>

</div>
<script>
    var theUrl = "${resource(dir:'js/themes')}";
    var theTheme = 'classic'; //default, classic, apple, default-rtl
    //alert('url = '+theUrl);
</script>
<asset:javascript src='jquery.jstree.js'/>
<asset:javascript src='/perpage/roleShow.js'/>
</body>
</html>
