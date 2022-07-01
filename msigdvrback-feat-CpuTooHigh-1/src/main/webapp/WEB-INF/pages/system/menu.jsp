<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	<%--
	**menu頁面使用
	顯示 LOV 視窗
	name  = LOV 頁面名稱，對應到 tw.com.help 的 Controller 定義
	param = 傳遞的參數，使用 form.serialize 收集即可
	title = LOV title顯示名稱
	level = 開啟LOV的層級,第一層時可不傳值
	--%>
	function doShowLOV(url, title, level) {
		var thisLevel = level == null ? 1 : level;
		var dialogId = "#dialogDiv_" + thisLevel;
		$("#lovFrame_"+thisLevel).attr("src", url);
		$(dialogId).dialog("option", "title", title + " - <spring:message code='lov.label'/>");
		$(dialogId).dialog("open");
		$(dialogId).dialog("option", "position", { my: "center", at: "center", of: $("body"), within: $("body")});
	}
	
	function setArrange() {
		//     var pe = new PeriodicalExecuter(timeOut, 1830 );
		var height = document.body.offsetHeight;
		var width = document.body.offsetWidth - 10;
		$('#mainNav').height((height - 92) + "px");
		$('#mainNav').width("180px");
		if (navigator.userAgent.indexOf("MSIE", 0) != -1) {
			$('#content').height((height - 82) + "px");
		} else {
			$('#content').height((height - 74) + "px");
		}
		$('#content').width((width-213)+"px");
		$('#workAreaFrame').height((height - 127) + "px");
		$('#workAreaFrame').width((width - 219) + "px");
		// 	 window.onscroll=resetMask;
	}
	
	//menu
	function changeWorkAreaA(a, b) {
// 		alert("target : " + a + "/" + b + "/index.html");
		var user = "<c:out value='${userId}' />";
		$("#workAreaFrameA").attr("src", "${ctx}/"+b+"/index.html?userId="+user);
	}
	
	$(document).ready(function(){
		$(window).resize(function(){
			setArrange();
		});
		
		$("body").css("background-color","#e7e7ef");
		//menu start
		$('.menu').click(function(e){
			if (($(this).attr('class')).indexOf('open')<0){
				$(this).removeClass("close");
				$(this).addClass("open");
				$(this).next().show();
			}
			else if (($(this).attr('class')).indexOf('close')<0){
				$(this).addClass("close");
				$(this).removeClass("open");
				$(this).next().hide();
			}
			else {
				Console.write("error");			
			}
		});
		
		$(".menucontent").click(function() {
			var mod = $(this).parent().parent().prev().attr('id');
			var ap = $(this).attr('id');
			changeWorkAreaA(mod, ap);
		});
		
		$('.menu').each(function() {
			if (($(this).attr('class')).indexOf('open')<0) {
				$(this).next().hide();
			}
			else if (($(this).attr('class')).indexOf('close')<0){
				$(this).next().show();
			}
		});
		//menu end
		
		initLOV(1);
    	initLOV(2);
    	initLOV(3);
    	
    	setArrange();
	});
</script>
<style type="text/css">

/* menu uses  default.css*/
.company-name{
font-size: 12pt;
color:#707070;
font-weight:bold;
font-family: Trebuchet MS, arial, verdana, helvetica, sans-serif;
}
.box-left-bottom {
background: url(../images/left_bottomImg.jpg) no-repeat left bottom;
}
.box-right-bottom {
background: url(../images/right_bottomImg.jpg) no-repeat right bottom;
}
.box-left-top {
background: url(../images/left_topImg.jpg) no-repeat left top;
}
.box-right-top {
background: url(../images/right_topImg.jpg) no-repeat right top;
}

.box-top{
background: url(../images/topImg.jpg) repeat-x top;
}
.box-bottom{
/*width: 20em;*/
background: #ffffff url(../images/bottomImg.jpg) repeat-x bottom;
}
.box-left{
background: url(../images/leftImg.jpg) repeat-y left;
}
.box-right{
background: url(../images/rightImg.jpg) repeat-y right;
}



.wbox-left-bottom {
background: url(../images/indexLeftBottom.jpg) no-repeat left bottom;
}
.wbox-right-bottom {
background: url(../images/indexRightBottom.jpg) no-repeat right bottom;
}
.wbox-left-top {
background: url(../images/indexLeftTop.jpg) no-repeat left top;
}
.wbox-right-top {
background: url(../images/indexRightTop.jpg) no-repeat right top;
}
.wbox-top{
background: url(../images/indexHorizon.jpg) repeat-x top;
}
.wbox-bottom{
/*width: 20em;*/
background: #ffffff url(../images/indexHorizon.jpg) repeat-x bottom;
}
.wbox-left{
background: url(../images/indexVertical.jpg) repeat-y left;
}
.wbox-right{
background: url(../images/indexVertical.jpg) repeat-y right;
}

/*menu 890916*/
.blue-left-bottom {
background:  url(../images/blueLeftBottom.jpg) no-repeat left bottom;
}
.blue-right-bottom {
background:  url(../images/blueRightBottom.jpg) no-repeat right bottom;
}
.blue-left-top {
background: #356289 url(../images/blueLeftTop.jpg) no-repeat left top;
}
.blue-right-top {
background:  url(../images/blueRightTop.jpg) no-repeat right top;
}

/** red */
.red-left-bottom {
background:  url(../images/redLeftBottom.jpg) no-repeat left bottom;
}
.red-right-bottom {
background:  url(../images/redRightBottom.jpg) no-repeat right bottom;
}
.red-left-top {
background: #890916 url(../images/redLeftTop.jpg) no-repeat left top;
}
.red-right-top {
background:  url(../images/redRightTop.jpg) no-repeat right top;
}

/** orange **/
.orange-left-bottom {
background:  url(../images/orangeLeftBottom.jpg) no-repeat left bottom;
}
.orange-right-bottom {
background:  url(../images/orangeRightBottom.jpg) no-repeat right bottom;
}
.orange-left-top {
background: #ff9933 url(../images/orangeLeftTop.jpg) no-repeat left top;
}
.orange-right-top {
background:  url(../images/orangeRightTop.jpg) no-repeat right top;
}

/* main.css */
#wrapper{	
	margin: 0 auto;	    
    margin-top:5px;
    text-align: left;
    }

#mainNav {
    float: left;
    width:180px;
    }
#content {
	margin-left:5px;
    float: right;
    width :810px;
    }
div.menuFront {
 	BACKGROUND: url(../images/front.jpg);
	color:#890916;
	height : 26px;
}
div.menuBack {
	BACKGROUND: url(../images/back.jpg) no-repeat right;
	color:#890916;
    height : 26px;
    padding-right:20px;
  
}
div.divDir{
	background: #f1f2f6 url(../images/back.jpg)  no-repeat right;
	height : 25px;
	margin-top: 1px;
    /*border-bottom: 1px groove #c5c5c5;*/
}
div.divDirMiddle {
	BACKGROUND: url(../images/leftContainer-mid-bg.png) no-repeat right;
	height : 23px;
}

#hdWrapper {
	width: 600px;
	height: 43px;
	padding-top:5px;
	margin-top:0;
	color:#FFFFFF;
	font: 100% Verdana, Arial, Helvetica, sans-serif;
	font-size: 17pt;
}


.divDir span {
	font-size:9pt;
	
	color: #FFFFFF;
	text-decoration: none;
	cursor:pointer;
}


.divDir A {
	font-size:9pt;
	
	color: #FFFFFF;
	text-decoration: none;
}

.divDir A:link {
	font-size:9pt;
	
	color: #FFFFFF;
	text-decoration: none;
}

.divDir A:visited { 
	font-size:9pt;
	
	color: #FFFFFF;
	text-decoration: none;
}

.divDir A:active {
	font-size:9pt;
	
	color: #FFFFFF;
}

.divDir A:hover { 
	font-size:9pt;
	
	color: #FFFFFF;
}

/**/
.menuBack span {
	font-size:9pt;
	
	color:#595241;
	text-decoration: none;
}
.menuBack A {
	font-size:9pt;
	
	color:#595241;
	text-decoration: none;
}

.menuBack A:link {
	font-size:9pt;
	
	color:#595241;
	text-decoration: none;
}

.menuBack A:visited { 
	font-size:9pt;
	
	color:#595241;
	text-decoration: none;
}

.menuBack A:active {
	font-size:9pt;
	
	color:#595241;
}

.menuBack A:hover { 
	font-size:9pt;
	
	color:#595241;
}

/**/
.divDirMiddle span {
	font-size:9pt;	
	color:#890916;
	text-decoration: none;
	cursor:pointer;
}

.divDirMiddle a {
	font-size:9pt;	
	color:#890916;
	text-decoration: none;
}

.divDirMiddle A:link {
	font-size:9pt;
	
	color:#890916;
	text-decoration: none;
}

.divDirMiddle A:visited { 
	font-size:9pt;
	
	color:#890916;
	text-decoration: none;
}

.divDirMiddle A:active {
	font-size:9pt;
	
	color:#890916;
}

.divDirMiddle A:hover { 
	font-size:9pt;
	
	color:#890916;
}

/* alan new menu*/
#nav {
	float: left;
	padding:0;
	width:180px;
}

#nav li {
    text-align: left;
    display: block;
    vertical-align: middle;
}

#nav li ul {
	padding:0;
	list-style:none;
	text-decoration: none;
    text-align: left;
}

.menu {
	display: block;
    padding: 10px 15px;
    background: #ccc;
    border-top: 1px solid #eee;
    border-bottom: 1px solid #999;
    text-decoration: none;
    text-align: left;
    color: #000;
    cursor: pointer;
}

.menu.item.close
{
	background: url(../images/close.png);
	background-repeat: no-repeat;
	padding: 3px 0 3px 20px;
	margin: .4em 0;
}

.menu.item.open
{
	background: url(../images/open.png);
	background-repeat: no-repeat;
	padding: 3px 0 3px 20px;
	margin: .4em 0;
}
 
.menu.dir.close
{
	background: url(../images/close.png);
	background-repeat: no-repeat;
	padding: 3px 0 3px 20px;
	margin: .4em 0;
	color: #FFFFFF;
}

.menu.dir.open
{
	background: url(../images/open.png);
	background-repeat: no-repeat;
	padding: 3px 0 3px 20px;
	margin: .4em 0;
	color: #FFFFFF;
}
.menu.dir.back{
	background-image: url("../images/redBack.jpg"); 
	background-attachment: scroll; 
	background-repeat: no-repeat; 
	background-position-x: right; 
	background-position-y: 50%; 
	background-color: rgb(137, 9, 22);
	color: #FFFFFF;
}
.menucontent {
	cursor: pointer;
}

</style>
</head>
<body id="mainBody" style="background: #e7e7ef;" >
	

<div style="height: 45px;background: url(../images/bg_top.jpg) repeat-x;white-space: nowrap;">
	<table style="padding: 0px;margin: 0px;white-space: nowrap; width:100%;">
		<tr>
			<td id="hdWrapper" style="width:400px;float:left;white-space: nowrap;padding-left: 75px;padding-bottom: 5px;" nowrap="nowrap">
			 <td id="hdWrapper" style="width:400px;float:left;white-space: nowrap;padding-left: 75px;padding-bottom: 5px;" nowrap="nowrap">
			    SPAS V3<span style="font-size:16px;font-weight:bold;">帳務管理系統</span>
			 </td>
			 <td style="color: #ffffff;font-size: 9pt;white-space: nowrap;text-align: right;padding-right: 10px;" nowrap="nowrap">
			 	 登入帳號:<c:out value="${userId}" />
			 </td>
		</tr>
	</table> 
</div>
<div>
<div id="wrapper" >
<!--   <table> -->
<!--     <tr> -->
<!--       <td style="vertical-align: top;"> -->
	<div id="mainNav" style="overflow:auto;height:100%;width:197px;float:left;">
	    <table id="menuTable" style="width: 175px;">
	      <tr>
	        <td style="padding-right: 3px;" nowrap="nowrap">
		        <div style="background:url('../images/leftContainer-top-bg.png') no-repeat right bottom;height:10px;" ></div>
			        <ul id="nav">
					  <li>
					  	<div class="divDir" style="background-image: url('../images/redBack.jpg'); background-attachment: scroll; background-repeat: no-repeat; background-position-x: right; background-position-y: 50%; background-color: rgb(137, 9, 22);">
					  		<span class="divDir">
					  			<img align="absMiddle" style="margin-bottom: 3px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-top-style: none; border-right-style: none; border-bottom-style: none; border-left-style: none;" src="../images/close.png"/>
					  			<span>拆帳作業</span>
					  		</span>
					  	</div>
						<ul>
						  <li>
						  	<div class="divDirMiddle" >
						  		<span id="STSS001M" style="position:relative;left:12px;">
						  			<img align="absMiddle" style="margin-bottom: 3px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-top-style: none; border-right-style: none; border-bottom-style: none; border-left-style: none;" src="../images/close.png"/>
						  			<span>系統管理</span>
						  		</span>
						  	</div>
						  	<ul>
						  		<li>
						  			<div class="menuFront">
						  				<div class="menuBack">
						  					<span class="menucontent" id="STSS001F" style="color: #595241; font-weight: normal;position:relative;left:32px;">
						  						<span><spring:message code="cw.stss001f.title" /></span>
						  					</span>
						  				</div>
						  			</div>
						  		</li>
						  		<li>
						  			<div class="menuFront">
						  				<div class="menuBack">
						  					<span class="menucontent" id="STSS002F" style="color: #595241; font-weight: normal;position:relative;left:32px;">
						  						<span><spring:message code="cw.stss002f.title" /></span>
						  					</span>
						  				</div>
						  			</div>
						  		</li>
						  		<li>
						  			<div class="menuFront">
						  				<div class="menuBack">
						  					<span class="menucontent" id="STSS004F" style="color: #595241; font-weight: normal;position:relative;left:32px;">
						  						<span>使用者維護及授權</span>
						  					</span>
						  				</div>
						  			</div>
						  		</li>
						  		<li>
						  			<div class="menuFront">
						  				<div class="menuBack">
						  					<span class="menucontent" id="STSS005F" style="color: #595241; font-weight: normal;position:relative;left:32px;">
						  						<span><spring:message code="cw.stss005f.title" /></span>
						  					</span>
						  				</div>
						  			</div>
						  		</li>
						  		<li>
						  			<div class="menuFront">
						  				<div class="menuBack">
						  					<span class="menucontent" id="STSS006F" style="color: #595241; font-weight: normal;position:relative;left:32px;">
						  						<span><spring:message code="cw.stss006f.title" /></span>
						  					</span>
						  				</div>
						  			</div>
						  		</li>
						  		<li>
						  			<div class="menuFront">
						  				<div class="menuBack">
						  					<span class="menucontent" id="STSS007F" style="color: #595241; font-weight: normal;position:relative;left:32px;">
						  						<span>假日設定維護</span>
						  					</span>
						  				</div>
						  			</div>
						  		</li>
						  		<li>
						  			<div class="menuFront">
						  				<div class="menuBack">
						  					<span class="menucontent" id="STSS008F" style="color: #595241; font-weight: normal;position:relative;left:32px;">
						  						<span>拆帳業者設定</span>
						  					</span>
						  				</div>
						  			</div>
						  		</li>
						  		<li>
						  			<div class="menuFront">
						  				<div class="menuBack">
						  					<span class="menucontent" id="STSS009F" style="color: #595241; font-weight: normal;position:relative;left:32px;">
						  						<span>程式模組設定</span>
						  					</span>
						  				</div>
						  			</div>
						  		</li>
						  	</ul>
						  </li>
						  <li>
						  	<div class="divDirMiddle" >
						  		<span id="STSD001M" style="position:relative;left:12px;">
						  			<img align="absMiddle" style="margin-bottom: 3px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-top-style: none; border-right-style: none; border-bottom-style: none; border-left-style: none;" src="../images/close.png"/>
						  			<span>同業話務及批價費率設定</span>
						  		</span>
						  	</div>
						  	<ul>
						  		<li>
						  			<div class="menuFront">
						  				<div class="menuBack">
						  					<span class="menucontent" id="STSD001F" style="color: #595241; font-weight: normal;position:relative;left:32px;">
						  						<span>話號前置碼業者對照設定</span>
						  					</span>
						  				</div>
						  			</div>
						  		</li>
						  		<li>
						  			<div class="menuFront">
						  				<div class="menuBack">
						  					<span class="menucontent" id="STSD002F" style="color: #595241; font-weight: normal;position:relative;left:32px;">
						  						<span>Trunk 設定</span>
						  					</span>
						  				</div>
						  			</div>
						  		</li>
						  		<li>
						  			<div class="menuFront">
						  				<div class="menuBack">
						  					<span class="menucontent" id="STSD003F" style="color: #595241; font-weight: normal;position:relative;left:32px;">
						  						<span>Routing設定</span>
						  					</span>
						  				</div>
						  			</div>
						  		</li>
						  		<li>
						  			<div class="menuFront">
						  				<div class="menuBack">
						  					<span class="menucontent" id="STSD004F" style="color: #595241; font-weight: normal;position:relative;left:32px;">
						  						<span>話務行為設定</span>
						  					</span>
						  				</div>
						  			</div>
						  		</li>
						  		<li>
						  			<div class="menuFront">
						  				<div class="menuBack">
						  					<span class="menucontent" id="STSD005F" style="color: #595241; font-weight: normal;position:relative;left:32px;">
						  						<span>批價費率設定檔維護</span>
						  					</span>
						  				</div>
						  			</div>
						  		</li>
						  	</ul>
						  </li>
						  <li>
						  	<div class="divDirMiddle" >
						  		<span id="STVD001M" style="position:relative;left:12px;">
						  			<img align="absMiddle" style="margin-bottom: 3px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-top-style: none; border-right-style: none; border-bottom-style: none; border-left-style: none;" src="../images/close.png"/>
						  			<span>加值產品及批價費率設定</span>
						  		</span>
						  	</div>
						  	<ul>
						  		<li>
						  			<div class="menuFront">
						  				<div class="menuBack">
						  					<span class="menucontent" id="STVD001F" style="color: #595241; font-weight: normal;position:relative;left:32px;">
						  						<span>帳務系統產品設定轉入作業</span>
						  					</span>
						  				</div>
						  			</div>
						  		</li>
						  	</ul>
						  </li>
						  <li>
						  	<div class="divDirMiddle" >
						  		<span id="STMT001M" style="position:relative;left:12px;">
						  			<img align="absMiddle" style="margin-bottom: 3px; border-top-width: 0px; border-right-width: 0px; border-bottom-width: 0px; border-left-width: 0px; border-top-style: none; border-right-style: none; border-bottom-style: none; border-left-style: none;" src="../images/close.png"/>
						  			<span>月處理作業</span>
						  		</span>
						  	</div>
						  	<ul>
						  		<li>
						  			<div class="menuFront">
						  				<div class="menuBack">
						  					<span class="menucontent" id="STMT002F" style="color: #595241; font-weight: normal;position:relative;left:32px;">
						  						<span>加值外部資料輸入維護</span>
						  					</span>
						  				</div>
						  			</div>
						  		</li>
						  	</ul>
						  </li>
						</ul>
					  </li>
					</ul>
		        <div style="background:url('../images/leftContainer-btm-bg.png') no-repeat right top;height:10px;" ></div>
	        </td>
	      </tr>
	    </table>    
  	</div>
	<!-- 主頁面 -->  
	<div id="content" style="float:left;">
		<div class="box-bottom" style="margin: 0 auto;">
			<div class="box-right">
				<div class="box-top" >
					<div class="box-left">
	    				<div class="box-left-top">
	    					<div class="box-right-top">
	    						<div class="box-left-bottom" >
	    							<div class="box-right-bottom" style="padding: 1px">  
	    								<div style="padding-left: 10px;padding-top: 2px;padding-bottom: 2px;">
	    									<div style="padding-top: 3px;">
									    		<span style="padding-right: 10px;">
									              <img src="../images/expand.png" align="absmiddle" style="cursor: pointer;" onclick="" title="xxx"> 
									              <img src="../images/expandBack.png" align="absmiddle" style="cursor: pointer;" onclick="" title="yyy">
									            </span>
									            <span id="workPath" style="font-size: 10pt;color: #5e5e5e;" >&nbsp;</span>
											</div>
											<div class="box-top" style="margin-top: 0px" />
											<div style="padding:2px">
												<iframe id="workAreaFrameA" frameBorder="0" marginWidth="0" marginHeight="0" scrolling="auto" src="" height="100%" width="100%"></iframe>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<!-- 		</td> -->
<!--       </tr> -->
<!--   </table> -->
</div>
</div>
<div style="margin: 0 auto;clear: both;background:url(../images/bg_top.jpg) center;font-size:10px;width:100%;height: 20px;text-align: center;color:#fff;padding-top:2px;"  ></div>	

</body>
</html>