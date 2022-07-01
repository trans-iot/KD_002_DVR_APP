<!-- 
    title 會員服務異動
    version 1.0 
    author Bob
    since 2018/10/31
-->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<script>
	
	</script>
</head>
<body>

<div class="mainDiv">
<br/><br/><br/>
		<div id="blockB">
	        <table class="resultTable" id="mainTable">
	            <thead>
	                <tr class="nowrapHeader listHeader">
	                    <th width="10%"><spring:message code="omom002f.header21.label"/></th><%-- 設備種類 --%>
	                    <th width="10%"><spring:message code="omom002f.header22.label"/></th><%-- MAC ADDERESS --%>
	                    <th width="10%"><spring:message code="omom002f.header23.label"/></th><%-- SN --%>
	                    <th width="10%"><spring:message code="omom002f.header24.label"/></th><%-- 設備狀態--%>
	                    <th width="10%"><spring:message code="omom002f.header25.label"/></th><%-- 綁定日期 --%>
	                </tr>
	            </thead>
	            <tbody>
	                <c:if test="${tbDeviceList ne null and fn:length(tbDeviceList) eq 0}">
	                    <tr>
	                        <td colspan='9' class="alignC"><spring:message code='no.result.message'/></td>
	                    </tr>
	                </c:if>
	                <c:if test="${not empty tbDeviceList}">
	                    <c:forEach var="item" items="${tbDeviceList}" varStatus="status">
	                        <tr>
	                            <c:forEach var="detypItem" items="${detyplist}" varStatus="status">
	                            	<c:if test="${detypItem.lookupCde eq  item.deviceType}">
	                            		<td><input type="text" value="${detypItem.dscr}"/></td>
	                            	</c:if>
	                            </c:forEach>
	                            <td><input type="text" value="${item.macAddress}"/></td>
	                            <td><input type="text" value="${item.sn}"/></td>
	                            <td>
		                            <c:forEach var="destaItem" items="${destaList}" varStatus="status">
		                            	<c:if test="${destaItem.lookupCde eq  item.deviceStatus}">
		                            		<input type="text" value="${destaItem.dscr}"/>
		                            	</c:if>
		                            </c:forEach>
	                            </td>
	                            <td><input type="text" value="<fmt:formatDate value="${item.bindDate}" type="Both" pattern="yyyy/MM/dd"/>"/></td>
	                        </tr>
	                    </c:forEach>
	                </c:if>
	            </tbody>
	        </table>    
	    </div>
</div>
</body>
</html>