<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="layout" content="secure2"/>
    <g:set var="entityName" value="${message(code: 'menu.tree.label')}"/>
    <title><g:message code="default.create.label" args="[entityName]"/></title>
</head>

<body>
<div class="body">
    <div class="contentbar"><h1>${entityName}</h1></div>

    <div class="contentbox">

        <div class="transDetail"><g:message code="default.edit.label" args="[entityName]"/></div>
        
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${menuInstance}">
            <div class="errors">
                <g:renderErrors bean="${menuInstance}" as="list"/>
            </div>
        </g:hasErrors>
        <g:form action="saveTree" useToken="true">
            <div class="dialog">
                <table class="form" width="100%">
                    <tbody>

                    <tr>
                        <td class="formLbl">
                            <label for="menuOption"><g:message code="menu.label"/></label>
                        </td>
                        <td class="value ${hasErrors(bean: menuInstance, field: 'name', 'errors')}">
                            <g:select name="option" id="menuOption"
                                      from="${options}"
                                      optionKey="id" optionValue="name" value=""/>
                            <input type="button" value="${message(code: 'default.button.add.label')}" id="menuBtn"/>
                        </td>
                    </tr>

                    </tbody>
                </table>
                <label onClick="clearSelectNode();">${entityName}</label><br/>

                <div id="tree">
                    <%
                        def createMenu = { parentID, obj ->
                            out << "<ul>"
                            obj.each { menu ->

                                def list = com.akiong.security.Menu.createCriteria().list {
                                    eq("parentMenu", menu)
                                    ne("sequence", 0)
                                    order("sequence", "asc")
                                }
                                if (list.size() > 0) {
                                    out << "<li id='" + menu.id + "' class='jstree-open'>" << "<input type='hidden' name='menu." + parentID + "' value='" + menu.id + "'>"
                                    out << "<a href='#'>" << menu.name << "</a>"
                                    owner.call(menu.id, list)
                                }
                                else {
                                    out << "<li id='" + menu.id + "'>" << "<input type='hidden' name='menu." + parentID + "' value='" + menu.id + "'>"
                                    out << "<a href='#'>" << menu.name << "</a>"
                                }
                            }
                            out << "</ul>"
                        }

                        def menuList = com.akiong.security.Menu.createCriteria().list {
                            isNull("parentMenu")
                            ne("sequence", 0)
                            order("sequence", "asc")
                        }
                        createMenu(0, menuList)

                    %>
                </div><br/>
                <input type="button" class="redbtn" value="${message(code: 'default.button.remove.label')}" id="removeBtn"/>
            </div>

            <div class="buttons">
                <span class="button"><g:submitButton name="create" class="save"
                                                     value="${message(code: 'default.button.save.label')}"/></span>
                <span class="button"><input type="button" class="back"
                                            value="${message(code: 'default.button.back.label')}"
                                            onclick='location.href = "${createLink(url: [controller: 'menu', action: 'treeDetail'])}"'/>
                </span>
            </div>
        </g:form>
    </div>

</div>
<script>
    var theUrl = "${resource(dir:'js/themes')}";
    var theTheme = 'classic'; //default, classic, apple, default-rtl
    //alert('url = '+theUrl);
</script>
<asset:javascript src='jquery.jstree.js'/>
<asset:javascript src='/perpage/tree.js'/>
</body>
</html>
