<%@ page import="com.akiong.security.Menu" %>
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
        <div class="transDetail"><g:message code="default.show.label" args="[entityName]"/></div>
        <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
        <g:hasErrors bean="${menuInstance}">
            <div class="errors">
                <g:renderErrors bean="${menuInstance}" as="list"/>
            </div>
        </g:hasErrors>
            <div class="dialog">
                <label id="menuTree">${entityName}</label><br/>

                <div id="tree">
                    <%
                        def createMenu = { parentID, obj ->
                            out << "<ul>"
                            obj.each { menu ->
                                def list = Menu.createCriteria().list {
                                    eq("parentMenu", menu)
                                    ne("sequence", 0)
                                    order("sequence", "asc")
                                }
                                if (list.size() > 0) {
                                    out << "<li class='jstree-open'>" << "<a href='#'>" << menu.name << "</a>"
                                    owner.call(menu.id, list)
                                }
                                else {
                                    out << "<li>" << "<a href='#'>" << menu.name << "</a>"
                                }
                                out << "</li>"
                            }
                            out << "</ul>"
                        }

                        def menuList = Menu.createCriteria().list {
                            isNull("parentMenu")
                            ne("sequence", 0)
                            order("sequence", "asc")
                        }
                        createMenu(0, menuList)

                    %>
                </div>
            </div>

            <div class="buttons">
                <span class="button"><input type="button" class="edit"
                                            value="${message(code: 'default.button.edit.label')}"
                                            onclick='location.href = "${createLink(url: [controller: 'menu', action: 'tree'])}"'/>
                </span>
            </div>
    </div>

</div>
<script>
    var theUrl = "${resource(dir:'js/themes')}";
    var theTheme = 'classic'; //default, classic, apple, default-rtl
    //alert('url = '+theUrl);
</script>
<asset:javascript src='jquery.jstree.js'/>
<asset:javascript src='/perpage/treeDetail.js'/>
</body>
</html>
