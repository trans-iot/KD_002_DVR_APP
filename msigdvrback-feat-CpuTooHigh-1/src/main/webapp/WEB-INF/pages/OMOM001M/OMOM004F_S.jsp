<!-- 
    title 最新消息維護
    version 1.0 
    author Bob
    since 2018/10/29
-->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title><spring:message code ="omom004f.title" /></title>
<script>
    //以修改日期排序
 	function orderBy(btn,key){ 
 		btn.disabled = true;
 		$("#orderByClause").val("datestamp desc");  
        $("#settingSeqNo").val(key);
   		$("#settingForm").submit();
        btn.disabled = false;
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
	 	readOnlyList = ["msgClass","title","prePushTime"];
		<%-- 查詢條件區塊套用CSS--%>
		bindFormCSS("queryForm", actionType, readOnlyList, excludeList);
    });
    
    function doBatchInsert(btn,key){
    	btn.disabled = true;
        $("#batchInsertSeqNo").val(key);
   		$("#batchInsertForm").submit();
        btn.disabled = false;
    }
    function doCarNoInsert(btn,key){
    	btn.disabled = true;
        $("#fileInsertSeqNo").val(key);
   		$("#fileInsertForm").submit();
        btn.disabled = false;
    }
    
    function doShowIndex() {
		$("#indexForm").submit();
	}
	
	function doShowQuery() {
		var click = ${queryForm.queryClicked};
		if(click){
			$("#queryBackForm").submit();
		}else{
			doShowIndex();
		}
	}
</script>
<style>
</style>
</head>
<body>
<div class="mainDiv">
<br/><br/><br/>
    <form id="queryForm" name="queryForm" method="post" action="${ctx}/OMOM004F/query.html">        
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <table class="queryTable" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2" cellspacing="0">
            <tr>
            	<%-- 消息類別 --%>
            	<th style="width:10%"><spring:message code="omom004f.search1"/>：</th>
				<td>
					<select class="select" id="msgClass" name="msgClass" readonly>
						<option value="" selected><spring:message code="select.options.0.label"/></option>
						<c:forEach items="${lookupMsgclassList}" var="item">
							<option value="${item.lookupCde}"  ${command.msgClass eq item.lookupCde ? 'selected' : ''}>${item.dscr}</option>
						</c:forEach>
					</select>
				</td>
            	<%-- 預計推播時間 --%>
            	<th style="width:10%"><spring:message code="omom004f.tbNews.prePushTime"/>：</th>
				<td>
					<input type="text" id="prePushTime" name="prePushTime" maxlength='80' value="${command.prePushTimeDscr}" readonly/>
				</td>
            </tr>
            <tr>
				<%-- 消息標題 --%>
				<th><spring:message code="omom004f.search3"/>：</th>
                <td colspan="3"><input type="text" id="title" name="title" maxlength='80' value="${command.title}" readonly/></td>
            </tr>
        </table>
    </form>
    <%-- 按鈕區域 --%>
    <div class="buttonDiv">
        <input type="button" class="btn" value="<spring:message code="omom004f.batch.insert.label"/>" onclick="javascript:doBatchInsert(this,${command.seqNo});" ${command.pushStatus ne 'N' ? 'disabled' : ''} />
        <input type="button" class="btn" value="<spring:message code="omom004f.carNo.insert.label"/>" onclick="javascript:doCarNoInsert(this,${command.seqNo});" ${command.pushStatus ne 'N' ? 'disabled' : ''} />
    </div>
    <div id="blockB">
        <table class="resultTable" id="mainTable">
            <thead>
                <tr class="nowrapHeader listHeader">
                    <th width="10%"><spring:message code="omom004f.header10.label"/></th><%-- 會員編號 --%>
                    <th width="10%"><spring:message code="omom004f.header11.label"/></th><%-- 車號--%>
                    <th width="10%"><spring:message code="omom004f.header5.label"/></th><%-- 推播狀態 --%>
                    <th width="10%"><spring:message code="omom004f.header6.label"/></th><%-- 實際推播時間 --%>
                    <th width="6%"><spring:message code="userstamp.label"/></th><%-- 修改人員 --%>
                    <th width="6%"><%-- 修改日期 --%>
                        <spring:message code="datestamp.label"/>
                        <button onClick="<c:if test='${not empty list}'>orderBy(this,${command.seqNo});</c:if>" class="imgBox">
                            <img width="15" src="${ctx}/images/desc.png" title="<spring:message code='sort.label'/>" border="0"/>
                        </button>
                    </th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${not empty vwNewsuserCustcarList}">
                    <c:forEach var="item" items="${vwNewsuserCustcarList}" varStatus="status">
                        <tr>
                            <td><input type="text" value="${item.userId}"/></td>
                            <td><input type="text" value="${item.carNo}"/></td>
                            <td><input type="text" value="${item.dscr}"/></td>
                            <td><input type="text" value="<fmt:formatDate value="${item.pushTime}" type="Both" pattern="yyyy/MM/dd HH:mm:ss"/>"/></td>
                            <td><input type="text" value="${item.userstamp}"/></td>
                            <td><input type="text" class="alignC" id="datestamp${status.count}" value="<fmt:formatDate value="${item.datestamp}" type="Both" pattern="yyyy/MM/dd"/>"/></td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>    
    </div>
    <input type="button" value="<spring:message code="page.previous.label"/>" class="btn" onclick="doShowQuery();"/>
    <form id="batchInsertForm" method="post" action="${ctx}/OMOM004F/batchInsert.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="batchInsertSeqNo" name="seqNo" />
    </form>
    <form id="fileInsertForm" method="post" action="${ctx}/OMOM004F/fileInsert.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="fileInsertSeqNo" name="seqNo" />
    </form>
    <form id="queryBackForm" name="queryForm" action="${ctx}/OMOM004F/query.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<%-- 保留空白給上一頁的功能使用 --%>
		<%-- 設定頁次_分頁下拉選單及圖示用 --%>
		<input type="hidden" id="pages" name="pages" value="${queryForm.pages}"/>
	</form>
	<form id="indexForm" action="${ctx}/${pgId}/index.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	</form>
</div>
</body>
</html>