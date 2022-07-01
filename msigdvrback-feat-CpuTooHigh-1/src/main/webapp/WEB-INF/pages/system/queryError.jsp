<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
	<div class="mainDiv" style="margin-top: 100px;">
		<h1>
			<spring:message code="data.not.exist" />
		</h1>
		<br>
		<a href="${ctx}${goBackUrl}"><spring:message code="page.previous.label" /></a>
	</div>
</body>
</html>
	