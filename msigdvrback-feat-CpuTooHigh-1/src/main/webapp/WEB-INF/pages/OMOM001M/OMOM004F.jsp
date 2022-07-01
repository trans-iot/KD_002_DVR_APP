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
    function doSearch() {
    	if(new Date($("#applyDateBegin").val()) - new Date($("#applyDateEnd").val()) > 0) {
    		alert('起日不可大於訖日')
    		return false
    	}
        $("#orderByClause").val("datestamp desc");//預設排序欄位
        $("#pages").val("1");//預設頁次
        $("#queryForm").submit();
    }
    
    function doClear() {
        $("#clearForm").submit();
    }
    
  
    function doUpdate(btn, key) {
        btn.disabled = true;
        $("#updateSeqNo").val(key);
        $("#updateForm").submit();
        btn.disabled = false;
    }
    
    function doDetail(btn, key1) {
    	btn.disabled = true;
        $("#detailSeqNo").val(key1);
        $("#detailQueryForm").submit();
        btn.disabled = false;
    }
    
    function doCreate() {
        $("#insertForm").submit();
    }
    
    function doDelete(btn,key) {
		btn.disabled;
		if($('#pushStatus'+key).val() != 'N'){
    		alert("訊息狀態不為尚未推播,不可刪除資料")
    		return false
    	}
		var ans = confirm('<spring:message code="delete.confirm.message" />');
		if (ans) {
			$("#deleteSeqNo").attr("value", key);
			var actionUrl = $("#deleteForm").attr("action"); 
			var param = $("#deleteForm").serialize();
			doCwAjax(actionUrl, param, ajaxDeleteDo);
		}
		
		btn.enabled;
	}
    function ajaxDeleteDo(data) {
		if (data.result == "${ajaxOk}") {
			$("#queryForm").submit();
		}
		else if(data.result == "${ajaxDeleteFail}"){
			alert('<spring:message code="delete.fail"/>');	
		}
	}
    //以修改日期排序
 	function orderBy(obj){ 
    		$("#orderByClause").val("datestamp desc");  
    		$("#pages").val("1");
    		$("#queryForm").submit();
    	}
    
    $(document).ready(function(){
    	// 調整編輯欄位的button padding
    	$("td[id^='editTd'] button").css("padding", "0");
        bindFormCSS("queryForm", "query");
    });
    
    function doSetting(btn, key) {
        btn.disabled = true;
        $("#settingSeqNo").val(key);
        $("#settingForm").submit();
        btn.disabled = false;
    }
    function doCreateData() {
        $("#insertDataPageForm").submit();
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
        <%-- 排序欄位 --%>
        <input type="hidden" id="orderByClause" name="orderByClause" value="${queryForm.orderByClause}"/>
        <%-- 頁次 --%>
        <input type="hidden" id="pages" name="pages" value="${queryForm.pages}"/>
        <%-- 每頁筆數 --%>
        <input type="hidden" id="perPageNum" name="perPageNum" value="${queryForm.perPageNum}" >
        <table class="queryTable" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2" cellspacing="0">
            <tr>
            	<%-- 消息類別 --%>
            	<th style="width:10%"><spring:message code="omom004f.search1"/>：</th><%-- 複製來源 --%>
				<td>
					<select class="select" id="msgClass" name="msgClass">
						<option value="" selected><spring:message code="select.options.0.label"/></option>
						<c:forEach items="${lookupMsgclassList}" var="item">
							<option value="${item.lookupCde}"  ${queryForm.msgClass eq item.lookupCde ? 'selected' : ''}>${item.dscr}</option>
						</c:forEach>
					</select>
				</td>
            	<%-- 推播狀態 --%>
            	<th style="width:10%"><spring:message code="omom004f.search2"/>：</th><%-- 複製來源 --%>
				<td>
					<select class="select" id="pushStatus" name="pushStatus">
						<option value="" selected><spring:message code="select.options.0.label"/></option>
						<c:forEach items="${lookupPushstaList}" var="item">
							<option value="${item.lookupCde}"  ${queryForm.pushStatus eq item.lookupCde ? 'selected' : ''}>${item.dscr}</option>
						</c:forEach>
					</select>
				</td>
            </tr>
            <tr>
				<%-- 消息標題 --%>
				<th><spring:message code="omom004f.search3"/>：</th>
                <td><input type="text" id="title" name="title" maxlength='80' value="${queryForm.title}"/></td>
            </tr>
        </table>
    </form>
    <%-- 按鈕區域 --%>
    <div class="buttonDiv">
        <input type="button" class="btn" value="<spring:message code="searchBtn.label"/>" onclick="javascript:doSearch();" />
        <input type="button" class="btn" value="<spring:message code="clear.label"/>" onclick="javascript:doClear();" />
    </div>
    <div id="blockB">
        <table class="resultTable" id="mainTable">
            <thead>
                <tr> <%-- 分頁 --%>
                    <th class="link" colspan="12">
							<table width="97%" border="0" cellpadding="0" cellspacing="0">
								<tr>
									<td style="color:#cedb00;background-color:#2B2B2B; width:30%;" align="left">
	    										<button onclick="javascript:doCreateData();"><img src="${ctx}/images/NEW.png" title="<spring:message code="insert.label"/>" class="imgBox"></button>
									</td>
									<td style="color:#cedb00;background-color:#2B2B2B; width:60%;" align="right">
									<cw:separate totalRows="${totalCount}" perPageNum="${queryForm.perPageNum}" formName="queryForm"  
				 				      firstImg="${firstImg}" leftImg="${leftImg}" rightImg="${rightImg}" lastImg="${lastImg}"  
				 				      firstImgEnd="${firstImgEnd}" leftImgEnd="${leftImgEnd}" rightImgEnd="${rightImgEnd}"  lastImgEnd="${lastImgEnd}"/> 
								
									</td>
									<td style="color:#cedb00;background-color:#2B2B2B; width:10%;" align="right"><spring:message code="count.all.label"/><span id="totalRow">${totalCount}</span><spring:message code="count.unit.label"/></td>
								</tr>
							</table>
                    </th>  
                </tr>
                <tr class="nowrapHeader listHeader">
                    <th width="10%"><spring:message code="omom004f.header1.label"/></th><%-- 編輯 --%>
                    <th width="10%"><spring:message code="omom004f.header2.label"/></th><%-- 消息類別 --%>
                    <th width="20%"><spring:message code="omom004f.header3.label"/></th><%-- 消息標題 --%>
                    <th width="10%"><spring:message code="omom004f.header4.label"/></th><%-- 預計推播時間 --%>
                    <th width="10%"><spring:message code="omom004f.header5.label"/></th><%-- 推播狀態 --%>
                    <th width="10%"><spring:message code="omom004f.header6.label"/></th><%-- 實際推播時間 --%>
                    <th width="10%"><spring:message code="omom004f.header7.label"/></th><%-- 生效日 --%>
                    <th width="10%"><spring:message code="omom004f.header8.label"/></th><%-- 失效日 --%>
                    <th width="6%"><spring:message code="userstamp.label"/></th><%-- 修改人員 --%>
                    <th width="6%"><%-- 修改日期 --%>
                        <spring:message code="datestamp.label"/>
                        <button onClick="<c:if test='${not empty list}'>orderBy(this);</c:if>" class="imgBox">
                            <img width="15" src="${ctx}/images/desc.png" title="<spring:message code='sort.label'/>" border="0"/>
                        </button>
                    </th>
                    <th width="5%"><spring:message code="omom004f.header9.label"/></th><%-- 是否顯示 --%>
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
								<button onclick="javascript:doUpdate(this,'${item.seqNo}');"><img src="${ctx}/images/UPDATE.png" title="<spring:message code="modify.label"/>" class="imgBox"/></button>
								<button onclick="javascript:doDelete(this,'${item.seqNo}');"><img src="${ctx}/images/DELETE.png" title="<spring:message code="delete.label"/>" class="imgBox"/></button>
								<button onclick="javascript:doDetail(this,'${item.seqNo}')"><img src="${ctx}/images/detail.png" title="<spring:message code="detail.search.label"/>" class="imgBox"/></button>
							</td>
							<td>
                            <c:forEach var="lookupItem" items="${lookupMsgclassList}" varStatus="status">
                            	<c:if test="${lookupItem.lookupCde eq  item.msgClass}">
                            		<input type="text" value="${lookupItem.dscr}"/>
                            	</c:if>
                            </c:forEach>
                            </td>
                            <td><input type="text" value="${item.title}"/></td>
                            <td><input type="text" value="<fmt:formatDate value="${item.prePushTime}" type="Both" pattern="yyyy/MM/dd HH:mm:ss"/>"/></td>
                            <td>
                            	<input type="hidden" id="pushStatus${item.seqNo}" value="${item.pushStatus}"/>
	                            <c:forEach var="lookupItem" items="${lookupPushstaList}" varStatus="status">
	                            	<c:if test="${lookupItem.lookupCde eq  item.pushStatus}">
	                            		<input type="text" value="${lookupItem.dscr}"/>
	                            	</c:if>
	                            </c:forEach>
                            </td>
                            <td><input type="text" value="<fmt:formatDate value="${item.pushTime}" type="Both" pattern="yyyy/MM/dd HH:mm:ss"/>"/></td>
                            <td><input type="text" value="<fmt:formatDate value="${item.effDate}" type="Both" pattern="yyyy/MM/dd"/>"/></td>
                            <td><input type="text" value="<fmt:formatDate value="${item.endDate}" type="Both" pattern="yyyy/MM/dd"/>"/></td>
                            <td><input type="text" value="${item.userstamp}"/></td>
                            <td><input type="text" class="alignC" id="datestamp${status.count}" value="<fmt:formatDate value="${item.datestamp}" type="Both" pattern="yyyy/MM/dd"/>"/></td>
                            <c:if test="${item.msgClass eq 'PERSONAL'}">
	                            <td id="editTd${status.index}" class="alignC">
	                                <input type="text" value=" >>" onclick="javascript:doSetting(this, '${item.seqNo}');" style="cursor: pointer"/>
	                            </td>
                            </c:if>
                            <c:if test="${item.msgClass ne 'PERSONAL'}">
	                            <td class="alignC">
	                                <input type="text" />
	                            </td>
                            </c:if>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>    
    </div>
    <form id="deleteForm" method="post" action="${ctx}/OMOM004F/delete.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="deleteSeqNo" name="deleteSeqNo"/>
	</form>
    <form id="clearForm" method="post" action="${ctx}/OMOM004F/index.html">
    <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    </form>
 
    <form id="updateForm" method="post" action="${ctx}/OMOM004F/update.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="updateSeqNo" name="updateSeqNo"/>
    </form>
 
    <form id="detailQueryForm" method="post" action="${ctx}/OMOM004F/detail.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="detailSeqNo" name="detailSeqNo" />
        <input type="hidden" id="orderByClause" name="orderByClause" value="" />
        <input type="hidden" name="pages" value="1"/><%-- 前往detail的查詢頁數初始 = 1 --%>
        <input type="hidden" id="perPageNum" name="perPageNum" value="${detailQueryForm.perPageNum}" />
    </form>
 	<form id="insertForm" method="post" action="${ctx}/OMOM004F/insert.html">
    <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    </form>
 	<form id="insertDataPageForm" method="post" action="${ctx}/OMOM004F/insertDataPage.html">
    <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    </form>
 	<form id="settingForm" method="post" action="${ctx}/OMOM004F/setting.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="settingSeqNo" name="settingSeqNo" />
    </form>
</div>
</body>
</html>