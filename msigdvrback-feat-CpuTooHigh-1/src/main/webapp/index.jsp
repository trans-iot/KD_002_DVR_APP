<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style type="text/css">

/*******************
SELECTION STYLING
*******************/

::selection {
	color: #fff;
	background: #f676b2; /* Safari */
}
::-moz-selection {
	color: #fff;
	background: #f676b2; /* Firefox */
}

/*******************
BODY STYLING
*******************/

* {
	margin: 0;
	padding: 0;
	border: none;
	outline: none;
}

body {
	background: url('images/bg.png');
	font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", sans-serif;
	font-weight:300;
	text-align: left;
	text-decoration: none;
	height: 500px;
}

#wrapper {
	/* Center wrapper perfectly */
	width: 600px;
 	height: 400px;
 	margin: 70px auto;
	_margin: 70px auto;
	*margin: 70px auto;
font-family: "標楷體",Arial, Helvetica, sans-serif;
	font-size:20px;
 	position: absolute;
 	top: 5%;
 	left:30%;
/* 	padding:0 120px; */

}

/* Download Button (Demo Only) */
.download {
	display: block;
	position: absolute;
	float: right;
	right: 25px;
	bottom: 25px;
	padding: 5px;
	
	font-weight: bold;
	font-size: 11px;
	text-align: right;
	text-decoration: none;
	color: rgba(0,0,0,0.5);
	text-shadow: 1px 1px 0 rgba(256,256,256,0.5);
}

.download:hover {
	color: rgba(0,0,0,0.75);
	text-shadow: 1px 1px 0 rgba(256,256,256,0.5);
}

.download:focus {
	bottom: 24px;
}

/*
.gradient {
	width: 600px;
	height: 600px;
	position: fixed;
	left: 50%;
	top: 50%;
	margin-left: -300px;
	margin-top: -300px;
	
	background: url(images/gradient.png) no-repeat;
}
*/

.gradient {
	/* Center Positioning */
	width: 600px;
	height: 600px;
	position: fixed;
	left: 50%;
	top: 50%;
	margin-left: -300px;
	margin-top: -300px;
	z-index: -2;
	
	/* Fallback */ 
	background-image: url(images/gradient.png); 
	background-repeat: no-repeat; 
	
	/* CSS3 Gradient */
	background-image: -webkit-gradient(radial, 0% 0%, 0% 100%, from(rgba(213,246,255,1)), to(rgba(213,246,255,0)));
	background-image: -webkit-radial-gradient(50% 50%, 40% 40%, rgba(213,246,255,1), rgba(213,246,255,0));
	background-image: -moz-radial-gradient(50% 50%, 50% 50%, rgba(213,246,255,1), rgba(213,246,255,0));
	background-image: -ms-radial-gradient(50% 50%, 50% 50%, rgba(213,246,255,1), rgba(213,246,255,0));
	background-image: -o-radial-gradient(50% 50%, 50% 50%, rgba(213,246,255,1), rgba(213,246,255,0));
}

/*******************
LOGIN FORM
*******************/

.login-form {
	width: 610px;
	margin: 0 auto;
	position: relative;
	
	background: #f3f3f3;
	border: 1px solid #fff;
	border-radius: 5px;
	
	box-shadow: 0 1px 3px rgba(0,0,0,0.5);
	-moz-box-shadow: 0 1px 3px rgba(0,0,0,0.5);
	-webkit-box-shadow: 0 1px 3px rgba(0,0,0,0.5);
}

/*******************
header1
*******************/

.login-form .header1 {
	padding: 40px 30px 10px 30px;
}

.login-form .header1 h1 {
	font-family: 'Bree Serif', serif;
	font-weight: 300;
	font-size: 28px;
	line-height:34px;
	color: #414848;
	text-shadow: 1px 1px 0 rgba(256,256,256,1.0);
	margin-bottom: 10px;
}
.login-form .header1 span {
	font-size: 11px;
	line-height: 16px;
	color: #678889;
	text-shadow: 1px 1px 0 rgba(256,256,256,1.0);
}

/*******************
CONTENT
*******************/

.login-form .content1 {
	padding: 0 30px 25px 30px;
}

/* Input field */
.login-form .content1 .input {
	width: 430px;
	padding: 15px 25px;	
	
	font-family: "HelveticaNeue-Light", "Helvetica Neue Light", "Helvetica Neue", Helvetica, Arial, "Lucida Grande", sans-serif;
	font-weight: 400;
	font-size: 14px;
	color: #9d9e9e;
	text-shadow: 1px 1px 0 rgba(256,256,256,1.0);
	
	background: #fff;
	border: 1px solid #fff;
	border-radius: 5px;
	
	box-shadow: inset 0 1px 3px rgba(0,0,0,0.50);
	-moz-box-shadow: inset 0 1px 3px rgba(0,0,0,0.50);
	-webkit-box-shadow: inset 0 1px 3px rgba(0,0,0,0.50);
}

/* Second input field */
.login-form .content1 .password, .login-form .content1 .pass-icon {
	margin-top: 25px;
}

.login-form .content1 .input:hover {
	background: #dfe9ec;
	color: #414848;
}

.login-form .content1 .input:focus {
	background: #dfe9ec;
	color: #414848;
	
	box-shadow: inset 0 1px 2px rgba(0,0,0,0.25);
	-moz-box-shadow: inset 0 1px 2px rgba(0,0,0,0.25);
	-webkit-box-shadow: inset 0 1px 2px rgba(0,0,0,0.25);
}

.user-icon, .pass-icon {
	width: 46px;
	height: 47px;
	display: block;
	position: absolute;
	left: 0px;
	padding-right: 2px;
	z-index: -1;
	
	-moz-border-radius-topleft: 5px;
	-moz-border-radius-bottomleft: 5px;
	-webkit-border-top-left-radius: 5px;
	-webkit-border-bottom-left-radius: 5px;
}

.user-icon {
	top:88px; /* Positioning fix for slide-in, got lazy to think up of simpler method. */
	background: rgba(65,72,72,0.75) url(images/user-icon.png) no-repeat center;	
}

.pass-icon {
	top:135px;
	background: rgba(65,72,72,0.75) url(images/pass-icon.png) no-repeat center;
}

.content1 input:focus + div{
	left: -46px;
}

/* Animation */
.input, .user-icon, .pass-icon, .button, .register {
	transition: all 0.5s ease;
	-moz-transition: all 0.5s ease;
	-webkit-transition: all 0.5s ease;
	-o-transition: all 0.5s ease;
	-ms-transition: all 0.5s ease;
}

/*******************
FOOTER
*******************/

.login-form .footer1 {
	padding: 25px 30px 35px 30px;
	overflow: auto;
	
	/*background: #414848;*/
	/*border-top: 1px solid #fff;*/
	
	box-shadow: inset 0 1px 0 rgba(0,0,0,0.15);
	-moz-box-shadow: inset 0 1px 0 rgba(0,0,0,0.15);
	-webkit-box-shadow: inset 0 1px 0 rgba(0,0,0,0.15);
}

/* Login button */
.login-form .footer1 .button {
	padding: 11px 25px;

	font-family: 'Bree Serif', serif;
	font-weight: 300;
	font-size: 18px;
	color: #fff;
	text-shadow: 0px 1px 0 rgba(0,0,0,0.25);
	
	background: #56c2e1;
	border: 1px solid #46b3d3;
	border-radius: 5px;
	cursor: pointer;
	
	box-shadow: inset 0 0 2px rgba(256,256,256,0.75);
	-moz-box-shadow: inset 0 0 2px rgba(256,256,256,0.75);
	-webkit-box-shadow: inset 0 0 2px rgba(256,256,256,0.75);
	
	margin-left: 280px;
}

.login-form .footer1 .img {
	float:right;
}

.login-form .footer1 .button:hover {
	background: #3f9db8;
	border: 1px solid rgba(256,256,256,0.75);
	
	box-shadow: inset 0 1px 3px rgba(0,0,0,0.5);
	-moz-box-shadow: inset 0 1px 3px rgba(0,0,0,0.5);
	-webkit-box-shadow: inset 0 1px 3px rgba(0,0,0,0.5);
}

.login-form .footer1 .button:focus {
	position: relative;
	bottom: -1px;
	
	background: #56c2e1;
	
	box-shadow: inset 0 1px 6px rgba(256,256,256,0.75);
	-moz-box-shadow: inset 0 1px 6px rgba(256,256,256,0.75);
	-webkit-box-shadow: inset 0 1px 6px rgba(256,256,256,0.75);
}

/* Register button */
.login-form .footer1 .register {
	display: block;
	float: right;
	padding: 10px;
	margin-right: 20px;
	
	background: none;
	border: none;
	cursor: pointer;
	
	font-family: 'Bree Serif', serif;
	font-weight: 300;
	font-size: 18px;
	color: #414848;
	text-shadow: 0px 1px 0 rgba(256,256,256,0.5);
}

.login-form .footer1 .register:hover {
	color: #3f9db8;
}

.login-form .footer1 .register:focus {
	position: relative;
	bottom: -1px;
}

#img {
	margin-right: 0px;
}

#fShadow {
	width : 85%; 
	height:3px;
	background-color : #EBEBFF;
	float:right;
	margin: 0px, 0px, 5px, 0px;
	position:absolute;
	bottom: 0;
	right: 0;
	z-index: 10000;
}
#footer div {
	color:#7D9EC0;
	font-size: 16px;
 	margin-top:7px; 
 	text-align : right; 
}
#newFooter {
	font-size: 14px;
	text-align: center;
}
</style>
</head>
<body style="text-align:center;">
<div id="wrapper">
	<form name="login-form" class="login-form" action="${ctx}/system/login.html" method="post">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<div class="header1">
			<div > 
				<div align="center">
				<h1><spring:message code='login.page.msg'/></h1></div>
			</div>
			
			<div class="content1">
			<input name="username" type="text" class="input username" placeholder="Username" />
			<div class="user-icon"></div>
			<input name="password" type="password" class="input password" placeholder="Password" autocomplete="off"/>
			<div class="pass-icon"></div>		
			</div>
			
			<div class="footer1">
				<input type="submit" name="submit" value="<spring:message code='login.submit.label' />" class="button" />
			<div id="errorMessage" style="color:red;">${errorMessage}</div>
			</div>
<!-- 			<div id="footer" style="z-index:10001"> -->
<!-- 				FOOT區 -->
<!-- 			<div id="fShadow"> -->
<!-- 			</div> -->
<!-- 			</div> -->
		</div>
	</form>
	<br>
		<div id="newFooter">
		</div>
	
	
	<script type="text/javascript">
	function getURLParameter(name) {
	    return decodeURI(
	        (RegExp(name + '=' + '(.+?)(&|$)').exec(location.search)||[,''])[1]
	    );
	}

	if (parent.frames.length > 0) {
	//20181114 edit:Jerry 因應IE 先ENCODE再傳
	 var esca = encodeURI("<spring:message code='logout.reason.timeout'/>");
	    parent.location.href = "./index.jsp?errorMessage="+esca;
// 	    parent.location.href = "./index.jsp?errorMessage=<spring:message code='logout.reason.timeout'/>";
	}

	
	document.getElementById("errorMessage").innerText = getURLParameter("errorMessage");
	</script>
                                                                                                                   
</body>
</html>