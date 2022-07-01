<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/system/include.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="X-UA-Compatible" content="edge" />
<title>MsigDvrBack</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/menu.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/default.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/blitzer/jquery-ui-1.9.2.custom.min.css" />
<script type="text/javascript" src="${ctx}/scripts/jquery.min.js?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery-ui-1.9.2.custom.min.js?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery-corner.js?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/mainFrame.js?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/mainFrameJs.jsp?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/cwUtils.js?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/publicJs.jsp?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/passwordValidate.jsp?${jsVer}"></script>
<script>
	function returnHomePage(){
		$("#workPath").html("");
		changeWorkArea("${ctx}/blank.jsp");
	}
	<%-- 20160805 benny edit:重新建立的LOV 
	參數
	title:LOV標題
	param:要傳到controller的參數
	prefixUrl:呼叫哪一個controller
	lovWidth:lov的寬度
	lovHeight:lov的高度
	level:是要開哪一層的LOV
	--%>
	function doShowNewLOV(title, param, prefixUrl, lovWidth ,lovHeight, level) {
		var thisLevel = level == null ? 1 : level;
	    var dialogId = "#dialogDiv_" + thisLevel;
	    var dialogObj = $(dialogId);
	    
	    <%-- 重新串該按鈕的url以及重設LOV長寬 --%>
	    var url = resetNewLov(title, param, dialogId, prefixUrl, thisLevel, lovWidth ,lovHeight);
	
	    $("#lovFrame_"+thisLevel).attr("src", url);
	    
	    dialogObj.dialog("open");
	    dialogObj.dialog("option", "position",  {my: "center", at: "center", of: window.parent.wrap, within: window.parent.wrap} );
	
	}
	
	<%-- 20160805 benny edit:重新建立的LOV --%>
	function resetNewLov(title, param, dialogId, prefixUrl, thisLevel, lovWidth, lovHeight){
		var newUrl = prefixUrl;
		var roundDate = Math.round(new Date().getTime());
		var dialogObj = $(dialogId);
		var newTitle = "";
		
		newTitle = title;
		newUrl = newUrl + roundDate + '&' + param;
		
		<%-- LOV長寬重設 --%>
		dialogObj.dialog({
			title : newTitle,
			width : lovWidth,
			open: function(event, ui) {
					$(dialogId).css("height", lovHeight);
			},
			<%-- 關閉視窗要恢復原本共用LOV長寬的的設定值 --%>
			close: function(event, ui) {
				$("#lovFrame_"+ thisLevel).removeAttr("src"); 
				dialogObj.dialog({
					width: 800,
					open: function(event, ui) {
						$(dialogId).css("height", "400px");
					}
				});
		    } 
		});
		return newUrl;
	}
</script>
</head>
<body id="wrap">
	<form id="chgRoleForm">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="roleId" name="roleId"/>
	</form>
	<div id="main">
		<div id="header">
			<table>
				<tr>
					<td id="hdWrapper1" nowrap="nowrap" onclick="returnHomePage();" style="width: 100%">
						<span style="cursor: pointer;font-family: Microsoft JhengHei;color: #9B9C93;font-size: 36px;">
						<spring:message code='system.title.main.frame'/>
						</span>
					</td>
					<td id="hdWrapper" style="width:180px;float:left;white-space: nowrap;padding-left: 15px" nowrap="nowrap">
					
				    </td>
					<td id="hdWrapper2" nowrap="nowrap">
						<span>
							<spring:message code='login.account.label'/> : <span style="font-weight: bold;"><c:out value="${userId}" /></span>
							| <span id="chgPwdSel" style="cursor: pointer;"><spring:message code="changePwd.label"/></span> 
							| <spring:message code='login.time.label'/> : <c:out value="${sessionData.loginTime}" />
							| <a href="${ctx}/system/logout.html" style="text-decoration: none;"><span style="color: #FFFFFF;"><spring:message code='logout.label'/></span></a>
						</span>
					</td>
				</tr>
			</table>
		</div>
		<table id="contentTable" width=100% border="0" cellpadding="0" cellspacing="0" style="margin-left: 0px; margin-top: 5px; margin-right: 0px; margin-bottom: 0px;">
			<tr>
				<td id="mainNavTD" style="vertical-align: top;background:#000;">
					<div id="mainNav" style="width: 250px;">
					<table id="menuTable" border="0" cellpadding="0" cellspacing="0" style="width: 250px;">
			            <tr>
			                <td nowrap="nowrap">
							${menu}
			                </td>
			            </tr>
			        </table>
					</div>
				</td>
				<td style="padding:0;">
					<div style="height:100%; width:100%; position:relative;">
						<div id="content" style="float: left;background-color:#222;margin-left:5px;width:100%; ">  
							<div id="workPathArea" style="position:relative;height: 25px;">
				   				<span style="padding-right: 10px;padding-top: 3px;float:left;">
				           			<img src="${ctx}/images/expand.png" align="absmiddle" style="cursor: pointer;z-index: 200;opacity: 0.50;" onclick="expandWorkArea()" title="sys.expand" /> 
									<img src="${ctx}/images/expandBack.png" align="absmiddle" style="cursor: pointer;z-index: 200;opacity: 0.50;" onclick="defaultWorkArea()" title="sys.expand.back" /> 
				            	</span>
				            	<span id="workPath" style="float:left;line-height: 25px"></span>
			       			</div>
							<div>
								<iframe id="workAreaFrame" name="workAreaFrame" src="${ctx}/blank.jsp" width="100%" scrolling="auto" frameborder="0" marginwidth="0" marginheight="0" >
								</iframe>
							</div>
						</div>
						<div id="loadingImg"  style="display: none;position:absolute;top: 3px;left: 45%;z-index: 200;background: #acd5ff; padding: 3px 15px 3px 15px; font-size: 9pt;" >
							<img src="${ctx}/images/spinner.gif" align="absmiddle" /> <spring:message code="sys.processing"/> ...
						</div>
						
						<div style="width:100%; height:10px;">&nbsp;</div>
					</div>
				</td>
			</tr>	
		</table>
		<div id="chgPwdDiv" style="display:none">
			<iframe style="border: 0px;" frameBorder="0" src="${ctx}/sso/chgPwd.html" width="100%" height="100%"></iframe>
		</div>
		<div id="footer">
		</div>
	</div>
</body>
</html>