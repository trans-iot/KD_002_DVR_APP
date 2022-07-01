<!-- 
    title 最新消息維護
    version 1.0 
    author Bob
    since 2018/10/26
-->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title><spring:message code ="omom002f.title" /></title>
<script>
    function doSearch() {
        $("#userIdQueryForm").submit();
    }
    
    function doClear() {
        $("#clearForm").submit();
    }
    
  
    function doCloseWindow(btn){
    	btn.disabled = true;
    	var checkedUserIdValue = $("input[type='radio']:checked").val()
    	if(checkedUserIdValue != undefined){
			$("#workAreaFrame",window.parent.document).contents().find("#userIdSearch").val(checkedUserIdValue)
    	}
   		closeLOV();
        btn.disabled = false;
    }
    
</script>
<style>
</style>
</head>
<body>
<div class="mainDiv">
<br/><br/><br/>
    <form id="userIdQueryForm" name="userIdQueryForm" method="post" action="${ctx}/OMOM002F/userIdQuery.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <table class="queryTable" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2" cellspacing="0">
            <tr>
				<%-- 會員編號 --%>
				<th><spring:message code="omom002f.userId.search.search1"/>：</th>
                <td><input type="text" id="userId" name="userId" maxlength='80' value="${userIdQueryForm.userId}"/></td>
				<%-- 姓名 --%>
				<th><spring:message code="omom002f.userId.search.search2"/>：</th>
                <td><input type="text" id="userName" name="userName" maxlength='80' value="${userIdQueryForm.userName}"/></td>
            </tr>
            <tr>
				<%-- 電子信箱 --%>
				<th><spring:message code="omom002f.userId.search.search3"/>：</th>
                <td><input type="text" id="email" name="email" maxlength='80' value="${userIdQueryForm.email}"/></td>
				<%-- 車號 --%>
				<th><spring:message code="omom002f.userId.search.search4"/>：</th>
                <td><input type="text" id="carNo" name="carNo" maxlength='80' value="${userIdQueryForm.carNo}"/></td>
            </tr>
        </table>
    </form>
    <%-- 按鈕區域 --%>
    <div class="buttonDiv">
        <input type="button" class="btn" value="<spring:message code="searchBtn.label"/>" onclick="javascript:doSearch();" />
        <input type="button" class="btn" value="<spring:message code="clear.label"/>" onclick="javascript:doClear();" />
        <input type="button" class="btn" value="<spring:message code="omom002f.close.window"/>" onclick="javascript:doCloseWindow(this);" />
    </div>
    <div id="blockB">
        <table class="resultTable" id="mainTable">
            <thead>
                <tr class="nowrapHeader listHeader">
                    <th width="10%"><spring:message code="omom002f.userId.search.header1.label"/></th><%-- 選取 --%>
                    <th width="10%"><spring:message code="omom002f.userId.search.header2.label"/></th><%-- 會員編號 --%>
                    <th width="10%"><spring:message code="omom002f.userId.search.header3.label"/></th><%-- 姓名 --%>
                    <th width="10%"><spring:message code="omom002f.userId.search.header4.label"/></th><%-- 電子信箱 --%>
                    <th width="10%"><spring:message code="omom002f.userId.search.header5.label"/></th><%-- 車號 --%>
                </tr>
            </thead>
            <tbody>
                <c:if test="${list ne null and fn:length(list) eq 0}">
                    <tr>
                        <td colspan='13' class="alignC"><spring:message code='no.result.message'/></td>
                    </tr>
                </c:if>
                <c:if test="${not empty list}">
                    <c:forEach var="item" items="${list}" varStatus="status">
                        <tr>
                            <td class="alignC">
                            	<input type="radio" name="userIdRadio" id="radio${item.userId}" value="${item.userId}"/>
							</td>
                            <td><input type="text" id="userId${status.count}" value="${item.userId}"/></td>
                            <td><input type="text" id="userName${status.count}" value="${item.userName}"/></td>
                            <td><input type="text" id="email${status.count}" value="${item.email}"/></td>
                            <td><input type="text" id="carNo${status.count}" value="${item.carNo}"/></td>
                        </tr>
                    </c:forEach>
                    <%-- 按鈕區域2 --%>
                </c:if>
            </tbody>
        </table>  
	        
    </div>
    <form id="deleteForm" method="post" action="${ctx}/OMOM002F/delete.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="deleteSeqNo" name="deleteSeqNo"/>
	</form>
    <form id="clearForm" method="post" action="${ctx}/OMOM002F/userIdSearchLov.html">
    <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    </form>
 
    <form id="updateForm" method="post" action="${ctx}/OMOM002F/update.html">
    <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="updateSeqNo" name="updateSeqNo"/>
    </form>
 
    <form id="detailQueryForm" method="post" action="${ctx}/OMOM002F/detail.html">
    <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="detailSeqNo" name="detailSeqNo" />
        <input type="hidden" id="orderByClause" name="orderByClause" value="" />
        <input type="hidden" name="pages" value="1"/><%-- 前往detail的查詢頁數初始 = 1 --%>
        <input type="hidden" id="perPageNum" name="perPageNum" value="${detailQueryForm.perPageNum}" />
    </form>
 	<form id="insertForm" method="post" action="${ctx}/OMOM002F/insert.html">
 	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    </form>
 	<form id="settingForm" method="post" action="${ctx}/OMOM002F/setting.html">
 	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="settingSeqNo" name="settingSeqNo" />
    </form>
 	<form id="checkboxValidateForm" method="post" action="${ctx}/OMOM002F/checkUserIdExit.html">
 	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="checkedUserId" name="checkedUserId" />
        <input type="hidden" id="checkedSeqNo" name="checkedSeqNo" />
    </form>
 	<form id="checkboxValidateAllForm" method="post" action="${ctx}/OMOM002F/checkUserIdExitAll.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="checkedUserIdArray" name="checkedUserIdArray" />
        <input type="hidden" id="checkedSeqNoAll" name="checkedSeqNoAll" />
    </form>
 	<form id="doInsertBatchForm" method="post" action="${ctx}/OMOM002F/doBatchInsert.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="batchInsertArray" name="batchInsertArray" />
    </form>
</div>
</body>
</html>