<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%><%@ include file="/WEB-INF/pages/system/include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
$(document).ready(function() {
	var main = $("#main").css({
        margin: "0 2em 2em 2em",
        padding: ".5em",
        border: "1px solid red",
        font: "consolas",
        color: "red",
        fontSize: "12px",
        whiteSpace: "pre"
    });
});
</script>
</head>
<body>
<h6>發生錯誤，請通知管理員</h6>
<div id="main">${message}</div>
</body>
</html>