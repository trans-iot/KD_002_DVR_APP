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
<title><spring:message code ="omom004f.title" /></title>
<script>
    
    function doClear() {
        $("#clearForm").submit();
    }
    function doUpload() {
    	if(!$("#uploadFile").val()){
    		alert('<spring:message code="not.checked.label" />')
			return false    		
    	}
    	var actionUrl = $("#checkPushStatus").attr("action"); 
    	$("#checkSeqNo").attr("value", $("#seqNo").val());
    	var param = $("#checkPushStatus").serialize();
    	doCwAjax(actionUrl, param, function(data){
			if (data.result == "${ajaxOk}") {
				doFileInsert()
			}
			else if(data.result == "${ajaxQueryFail}"){
				alert('<spring:message code="omom004f.pushStatus.incorrect.upload" />')
			}
			
		});
    	
		
    }
    
    function doFileInsert(){
    	$("#doFileInsertForm").submit(); 
    }
    
    
    $(document).ready(function(){
    	var msg = "";
		var readOnlyList;
		var excludeList;
		actionType = "detail"
		var tab1_Options = new Object();
		tab1_Options.actType = actionType;
		<%-- detail區塊按鈕加入事件 --%>
		var tab1_index = 0;
		var tab1_delFunc = "";
		tab1_Options.setCSS = true; //自行套CSS:false
	 	readOnlyList = ["separator","extension"];
		<%-- 查詢條件區塊套用CSS--%>
		bindFormCSS("doFileInsertForm", actionType, readOnlyList, excludeList);
    });
    
    function doCloseWindow(btn, key){
    	btn.disabled = true;
        $("#settingSeqNo").val(key);
        $("#settingForm").submit();
        btn.disabled = false;
    }
</script>
<style>
</style>
</head>
<body>
<div class="mainDiv">
<br/><br/><br/>
    <form id="doFileInsertForm" name="doFileInsertForm" method="post" action="${ctx}/OMOM004F/doFileInsert.html" enctype="multipart/form-data">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" value="${listSize}" name="listSize" id="listSize"/>
		<input type="hidden" value="${seqNo}" name="seqNo" id="seqNo"/>
        <table class="queryTable" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2" cellspacing="0">
            <tr>
				<%-- 匯入檔案 --%>
				<th><spring:message code="omom004f.file.insert.search1"/>：</th>
                <td colspan="2">
	                <input type="file" accept=".csv" name="uploadFile" id="uploadFile"  value="<spring:message code="omom004f.file.insert.file.browse"/>" class="upfile" />
	                <input type="button"  ${ disableUpload ? 'disabled' : '' } value="<spring:message code="omom004f.file.insert.file.upload"/>" onclick="javascript:doUpload(this);"/>
	                <input type="button" value="<spring:message code="clear.label"/>" onclick="javascript:doClear(this);" />
                </td>
            </tr>
            <tr>
				<%-- 文件副檔名 --%>
				<th><spring:message code="omom004f.file.insert.search2"/>：</th>
                <td><input type="text" id="extension" name="extension"  value="<spring:message code="omom004f.file.insert.file.extension"/>" readonly/></td>
				<%-- 文件分隔符號 --%>
				<th><spring:message code="omom004f.file.insert.search3"/>：</th>
                <td><input type="text" id="separator" name="separator"  value="<spring:message code="omom004f.file.insert.file.separator"/>" readonly/></td>
            </tr>
            <tr>
				<%-- 上傳欄位 --%>
				<th><spring:message code="omom004f.file.insert.search4"/>：</th>
                <td>
                	<div id="blockB">
	                	<table class="resultTable" id="mainTable">
				            <thead>
				                <tr class="nowrapHeader listHeader">
				                    <th width="10%" style="text-align: center"><spring:message code="omom004f.file.insert.file.order"/></th><%-- 順序 --%>
				                    <th width="10%" style="text-align: center"><spring:message code="omom004f.file.insert.file.column.name"/></th><%-- 欄位名稱 --%>
				                </tr>
				            </thead>
				            <tbody>
		                        <tr>
		                            <td><input type="text" value="<spring:message code="omom004f.file.insert.file.column.one"/>"/></td><%-- 一 --%>
		                            <td><input type="text" value="<spring:message code="omom004f.file.insert.file.column.one.name"/>"/></td><%-- 順序--%>
		                        </tr>
				            </tbody>
				        </table>
			        </div>
                </td>
            </tr>
        </table>
    </form>
    <div id="blockB">
        <table class="resultTable" id="mainTable">
            <thead>
                <tr class="nowrapHeader listHeader">
                    <th width="10%"><spring:message code="omom004f.file.insert.file.column.one.name"/></th><%-- 選取 --%>
                    <th width="10%"><spring:message code="omom004f.file.insert.file.column.two.name"/></th><%-- 會員編號 --%>
                    <th width="10%"><spring:message code="omom004f.file.insert.file.header3.label"/></th><%-- 姓名 --%>
                </tr>
            </thead>
            <tbody>
                <c:if test="${not empty list}">
                    <c:forEach var="item" items="${list}" varStatus="status">
                        <tr>
                            <td><input type="text"  value="${item.carNo}"/></td>
                            <td><input type="text"  value="${item.userId}"/></td>
                            <td><input type="text"  value="${item.result}"/></td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>  
	    <div class="buttonDiv">
	        <input type="button" class="btn" value="<spring:message code="omom004f.batch.insert.close.window"/>" onclick="javascript:doCloseWindow(this,${seqNo});" />
	    </div>
    </div>
    <form id="clearForm" method="post" action="${ctx}/OMOM004F/fileInsert.html">
    	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    	<input type="hidden" value="${seqNo}" name="seqNo"/>
    </form>
 	<form id="settingForm" method="post" action="${ctx}/OMOM004F/setting.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="settingSeqNo" name="settingSeqNo" />
    </form>
 	<form id="checkPushStatus" method="post" action="${ctx}/OMOM004F/checkPushStatus.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="checkSeqNo" name="checkSeqNo" />
    </form>
</div>
</body>
</html>