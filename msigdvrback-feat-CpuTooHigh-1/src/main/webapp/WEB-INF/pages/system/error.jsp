<%@ page import="java.io.PrintWriter,java.util.*"%>
<%@ page isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error</title>
</head>
<body>
<div style="color: red">
<div>There has been an application error with error ID ${exception.uid} at <fmt:formatDate
	value="${exception.timestamp}" type="both" />.</div>
<div>Please report this error to your Service Provider along with the error ID.</div>
<div><pre style="font-size: 11px">
<%
	// default binding attribute for exception is "exception"
	Date date = new Date();
	out.write(date.toString());
%>
</pre></div>
</div>
</body>
</html>