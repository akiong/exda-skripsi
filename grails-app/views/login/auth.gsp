<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>Expedisi Darat</title>
<%--    <p:favicon src='images/favicon'/>--%>
<%--    <p:css name='cssForLogin'/>--%>
    <asset:link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>
    <asset:javascript src="jquery-2.1.0.js"/>
    <asset:javascript src="application.js"/>
	<asset:javascript src="/perpage/auth.js"/>
	<asset:stylesheet href="application.css"/>
</head>
<body>
<div id="bgImage"></div>
<div class="navbar">
	<div class="navbar-inner">
                <ul class="nav">
                  	<li>
                  		<a>
                  			<asset:image src='kiong.png' alt="@kiong"/>
                  		</a>
                  	</li>                  	
               </ul>
               <ul class="nav  pull-right">
               		<li>
                  		<a>
                  			<label id="timeStr"></label>
                  			
                  		</a>
                  	</li>
               	</ul>
       
	</div>
</div>

<div id="body" align="center">
    <div>
        <div class="smartLiving" style="width:100px">
            <asset:image src='norton-seal.jpg' alt="Norton"/><br/><br/><br/>            
        </div>
        <div class="loginBox">
            <div id="logBox1">
                <h1>Login</h1>
                <div class="detail">Enter your user details</div>
                <div id="login">                    
                    <input type="text" id="userId" name="j_username"/> <br/>
                    <input type="password" id="password" name="j_password"/> <br/><br/>
                    <div>
                    	<input type="button" name="login" id="loginBtn" value="Log In" class="loginBtn"/>
                    </div>
                </div>
            </div>
        </div>
        <div class="clear"></div>
    </div>
   
</div>

<div id="copyright" align="center">
        <span>Copyright &copy; 2014 <a href="http://mrkiong.wordpress.com">@KIONG.</a> <span id="teamDialog" style="cursor:pointer">&trade;</span>
        </span>
</div>

<div id="usernameBlock" style="display:none;" title="User ID Blocked">
    <p>Your user id has been blocked</p>
</div>

<div id="usernamePassError" style="display:none;" title="Error">
    <p>Username or password is incorrect. Please try again.</p>
</div>

<div id="teraTeam" style="display:none;">
    <div class="wrapper"><b>Crafted by:</b>Bobby</br>NIM : 32110046</br>Teknik Informatika</br></div>
</div>

<script>
	var urlBank = "<%=session.preUrl%>";
	if(urlBank == ""){
        urlBank = "${createLink(url: [controller: 'main'])}"
    }
    var urlFirstPassword = "${createLink(url: [controller: 'changePassword', action: 'edit'])}";
    var serverDate = ${(new Date()).getTime()};
    var urlAjaxSuccess = "${createLinkTo(dir:'')}" + "/j_spring_security_check?spring-security-redirect=/login/ajaxSuccess";
    var encryptPaswd = "${createLinkTo(dir:'')}" + "/api/userDetails/encryptionPassword";
    //$.backstretch("${createLinkTo(dir:'')}/images/background.jpg");
</script>
<%--<p:javascript src='bootstrap'/>--%>
</body>
</html>