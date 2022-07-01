<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
%>
<!-- This comment will put IE 6, 7, 8, and 9 in quirks mode -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="edge" />

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<title>MsigDvrBack: <decorator:title default="Home" /></title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/NewBilling.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/blitzer/jquery-ui-1.9.2.custom.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.tabs.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/validationEngine.jquery.css" />
<script type="text/javascript" src="${ctx}/scripts/jquery.min.js?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery-ui-1.9.2.custom.min.js?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.ui.datepicker-zh-TW.js?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.tabs.js?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/js/custom/jquery.maskedinput-1.3.1.js?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.placeholder.js?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.metadata.js?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/autoNumeric-1.7.0.min.js?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.validationEngine-zh_TW.js?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.validationEngine.js?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/jquery.paginator.js?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/cwUtils.js?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/publicJs.jsp?${jsVer}"></script>
<script type="text/javascript" src="${ctx}/scripts/passwordValidate.jsp?${jsVer}"></script>

<script type="text/javascript">
    // decorator javascript
	$(document).ready(function(){
		bindQryResultCSS();
		var flag = true;
		if (flag) {
			resizeParent();
			flag = false;
		}
	});
	
	function resizeParent() {
		var iframe = parent.document.getElementById("workAreaFrame");
		if (iframe!=null) {
			iframe.height=document.body.scrollHeight;	
		}
	}
	
</script>
<decorator:head />

</head>
<body>
	<decorator:body />
	<div style="width:100%; height:10px;">&nbsp;</div>
<div id="loadingImg"  style="display: none;position:absolute;top: 3px;left: 45%;z-index: 200;background: #acd5ff; padding: 3px 15px 3px 15px; font-size: 9pt;" >
 <img src="${ctx}/images/spinner.gif" align="absmiddle" /> <spring:message code="sys.processing"/> ...
</div>
</body>
</html>