<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <g:layoutHead/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <sec:ifNotLoggedIn>
        <META http-equiv="refresh" content="1;url=${createLink(url: [controller: 'login', action: 'auth'])}">
    </sec:ifNotLoggedIn>
    <title><g:layoutTitle default="Expedisi Darat"/></title>
    <asset:link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>
<%--    <p:favicon src='images/favicon'/>--%>
<%--    <p:css name='cssForSecure'/>--%>
<%--    <p:javascript src='jquery-2.1.0'/>--%>
    
    	<asset:javascript src="jquery-2.1.0.js"/>
		<asset:stylesheet href="application.css"/>
</head>

<body>
<div id="bgImage"></div>

<div class="navbar">
	<div class="navbar-inner">
<%--    	<div class="container">--%>

				<a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a>
					
				<ul class="nav">
	                  	<li>
	                  		<a>
	                  			<asset:image src='kiong.png' alt="@kiong"/>
	                  		</a>
	                  	</li>
	            </ul>
					
				<div class="nav-collapse collapse">
	                <ul class="nav">
	                  	
<%--	                  	<li ><a href="#home">Home</a></li> <!-- class="active" -->--%>
<%--						<li><a href="#aaa">aaa</a></li>--%>
<%--						<li><a href="#bbb">bbb</a></li>--%>
<%--						<li><a href="#ccc">ccc</a></li>--%>
<%--						<li><a href="#ddd">ddd</a></li>--%>
<%--						<li><a href="#eee">eee</a></li>--%>
<%--						<li><a href="#fff">fff</a></li>--%>
<%--						<li><a href="#ggg">ggg</a></li>--%>
<%--						<li><a href="#hhh">hhh</a></li>--%>
	                  
	                  	<sec:ifLoggedIn>
	                   
	                            	<%
		                      			out << session.menuList
		                  			%>
	                   	
	                   	</sec:ifLoggedIn>
	               	</ul>
               	
	                <ul class="nav  pull-right nav-collapse collapse">
	                	<sec:ifLoggedIn>
	                		<sec:ifSwitched><li><a href='${request.contextPath}/j_spring_security_exit_user'> Resume as <sec:switchedUserOriginalUsername/> </a></li></sec:ifSwitched>
		                	<li>
		                  		<a>
									<span style="font-size: 14px; color: #666;font-weight:bold">Hi 
										<span style="font-size: 14px; color: #81af52;font-weight:bold">${sec.loggedInUserInfo(field:'username')}</span>
									</span>
								</a>
	                  		</li>
	                  	</sec:ifLoggedIn>
	                  	<li>
	                  		<a>
	                  			<label id="timeStr"></label>
	                  		</a>
	                  	</li>
	                	<sec:ifLoggedIn>
		                    <li class="dropdown" >
		                     		<a class="dropdown-tonggle" data-toggle="dropdown">
		                     			<asset:image src='icon-setting.png' alt="setting"/> Setting 
		                     			<b class="caret"></b>
		                     		</a>
		                     		<ul class="dropdown-menu" >
		                     			<li>
		                     				<a  href="${createLink(url: [controller: 'changePassword', action: 'edit'])}">Change Password</a>
		 								</li>                     	
		                     		</ul>            
		                    </li>
		              	
		                     <li>
		                     	<a onclick="window.location='${createLink(url: [controller: 'logout'])}'"><asset:image src='icon-logout.png' alt="logut"/> Log out</a>
		                     </li>
	                     </sec:ifLoggedIn>
	                 </ul>
                 </div>
<%--            </div>--%>
	</div>
</div>


<div id="body" data-target=".bs-docs-sidebar" align="center">
	<div>
		<div class="rightBar">
			<div class="main" >
				<div id="contentBank">
	                <g:layoutBody/>
	            </div>
			</div>
		</div>
	</div>
</div>

<script>
    var serverDate = ${(new Date()).getTime()};
    //$.backstretch("${createLinkTo(dir:'')}/images/background.jpg");
</script>
<%--<p:javascript src='jsForSecure'/>--%>
<%--<p:javascript src='/perpage/secure'/>--%>
<asset:javascript src="application.js"/>
<asset:javascript src="/perpage/secure.js"/>

</body>
</html>
