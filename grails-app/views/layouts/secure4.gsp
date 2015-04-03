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


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <g:layoutHead/>
    <title><g:layoutTitle default="New Concept of Siera"/></title>
<%--    <p:favicon src='images/favicon'/>--%>
<%--    <p:css name='cssForSecure'/>--%>
<%--    <p:javascript src='jquery-2.1.0'/>--%>
    <asset:link rel="shortcut icon" href="favicon.ico" type="image/x-icon"/>
    <asset:javascript src="jquery-2.1.0.js"/>
	<asset:stylesheet href="application.css"/>
</head>

<body>
<div id="bgImage"></div>

<div class="navbar">
	<div class="navbar-inner">
<%--    	<div class="container">--%>
                <ul class="nav">
                  	<li>
                  		<a>
                  			<asset:image src='logo-teravin.png' alt="Teravin"/>
                  		</a>
                  	</li>
                  	
               	</ul>
               	
                <ul class="nav  pull-right">
                	<li>
                  		<a>
                  			<label id="timeStr"></label>
                  		</a>
                  	</li>
                	<li>
                     	<a onclick="window.location='${createLink(url: [controller: 'login'])}'"><label>Login</label></a>
                     </li>
                     <li>
                     	<a onclick="window.location='${createLink(url: [controller: 'signUp'])}'"><label>Sign Up</label></a>
                     </li>
                 </ul>
	</div>
</div>


<div id="body" align="center">
	<div>
		<div class="rightBar">
			<div class="main">
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
<asset:javascript src="application.js"/>
<asset:javascript src='/perpage/secure.js'/>

</body>
</html>
