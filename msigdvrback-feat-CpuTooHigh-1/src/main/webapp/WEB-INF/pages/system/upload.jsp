<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
<%--
/**
 * data structure: [name: [], name2: [], ... ]
 * name name2 為 form 的field name 
 * 元素皆為陣列: [絕對路徑1, 絕對路徑2, ...], 若該欄位無上傳檔案則為空陣列
 */
--%>
var data = ${pathInfo};
var workFrame = $("#workAreaFrame", parent.document);
var targetContext = workFrame.length ? workFrame.get(0).contentWindow : parent;
targetContext.$.cw.afterUploaded(data);
</script>
</head>
<body>
</body>
</html>