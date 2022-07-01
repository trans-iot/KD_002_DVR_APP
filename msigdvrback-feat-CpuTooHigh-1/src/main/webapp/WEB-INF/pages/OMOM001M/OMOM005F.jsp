<!-- 
    title 保單申請資料查詢
    version 1.0 
    author Bob
    since 2018/10/22
-->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title><spring:message code ="omom005f.title" /></title>
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
        $("#updatePageType").val(key);
        $("#updateForm").submit();
        btn.disabled = false;
    }
    
    function doDetail(btn, key1) {
    	btn.disabled = true;
        $("#detailApplyNo").val(key1);
        $("#detailQueryForm").submit();
        btn.disabled = false;
    }
    
    function doCreate() {
        $("#insertForm").submit();
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
        bindCalendar("applyDateEnd");
        bindCalendar("applyDateBegin");
    });
</script>
<style>
</style>
</head>
<body>
<div class="mainDiv">
<br/><br/><br/>
    <form id="queryForm" name="queryForm" method="post" action="${ctx}/OMOM005F/query.html">        
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <%-- 排序欄位 --%>
        <input type="hidden" id="orderByClause" name="orderByClause" value="${queryForm.orderByClause}"/>
        <%-- 頁次 --%>
        <input type="hidden" id="pages" name="pages" value="${queryForm.pages}"/>
        <%-- 每頁筆數 --%>
        <input type="hidden" id="perPageNum" name="perPageNum" value="${queryForm.perPageNum}" >
        <table class="queryTable" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2" cellspacing="0">
            <tr>
                <%-- 申請單號--%>
                <th><spring:message code="omom005f.search1"/>：</th>
                <td><input type="text" id="applyNo" name="applyNo"  maxlength='80' value="${queryForm.applyNo}"/></td>
                <%-- 會員編號 --%>
                <th><spring:message code="omom005f.search2"/>：</th>
                <td><input type="text" id="userId" name="userId" maxlength='80' value="${queryForm.userId}"/></td>
            </tr>
            <tr>
            	<%-- 申請狀態 --%>
            	<th style="width:10%"><spring:message code="omom005f.search3"/>：</th><%-- 複製來源 --%>
				<td>
					<select class="select" id="applyStatus" name="applyStatus">
						<option value="" selected><spring:message code="select.options.0.label"/></option>
						<c:forEach items="${lookupList}" var="item">
							<option value="${item.lookupCde}"  ${queryForm.applyStatus eq item.lookupCde ? 'selected' : ''}>${item.dscr}</option>
						</c:forEach>
					</select>
				</td>
				<%-- 車號 --%>
				<th><spring:message code="omom005f.search4"/>：</th>
                <td><input type="text" id="carNo" name="carNo" maxlength='80' value="${queryForm.carNo}"/></td>
            </tr>
            <tr>
                <%-- 推薦代碼 --%>
                <th><spring:message code="omom005f.search5"/>：</th>
                <td><input type="text" id="promCode" name="promCode"  maxlength='80' value="${queryForm.promCode}"/></td>
                
            </tr>
            <tr>
                <%-- 申請日期起訖 --%>
                <th><spring:message code="omom005f.search6"/>：</th>
                <td colspan="3" style="color:white">
	                <input type="text" id="applyDateBegin" name="applyDateBegin" class="inputY inputY_L" maxlength='30' value="${queryForm.applyDateBegin}"/>
	            	~
	                <input type="text" id="applyDateEnd" name="applyDateEnd" class="inputY inputY_L"  maxlength='30' value="${queryForm.applyDateEnd}"/>
                </td>
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
                    <th width="10%"><spring:message code="omom005f.header1.label"/></th><%-- 歡迎頁圖檔URL --%>
                    <th width="10%"><spring:message code="omom005f.header2.label"/></th><%-- 是否顯示 --%>
                    <th width="10%"><spring:message code="omom005f.header3.label"/></th><%-- 是否顯示 --%>
                    <th width="10%"><spring:message code="omom005f.header4.label"/></th><%-- 是否顯示 --%>
                    <th width="10%"><spring:message code="omom005f.header5.label"/></th><%-- 是否顯示 --%>
                    <th width="10%"><spring:message code="omom005f.header6.label"/></th><%-- 是否顯示 --%>
                    <th width="10%"><spring:message code="omom005f.header7.label"/></th><%-- 是否顯示 --%>
                    <th width="10%"><spring:message code="omom005f.header8.label"/></th><%-- 是否顯示 --%>
                    <th width="10%"><spring:message code="omom005f.header9.label"/></th><%-- 是否顯示 --%>
                    <th width="10%"><spring:message code="omom005f.header10.label"/></th><%-- 是否顯示 --%>
                    <th width="6%"><spring:message code="userstamp.label"/></th><%-- 修改人員 --%>
                    <th width="6%"><%-- 修改日期 --%>
                        <spring:message code="datestamp.label"/>
                        <button onClick="<c:if test='${not empty list}'>orderBy(this);</c:if>" class="imgBox">
                            <img width="15" src="${ctx}/images/desc.png" title="<spring:message code='sort.label'/>" border="0"/>
                        </button>
                    </th>
                    <th width="10%"><spring:message code="omom005f.header11.label"/></th><%-- 是否顯示 --%>
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
                            
                            <td><input type="text" value="${item.applyNo}"/></td>
                            <td><input type="text" value="${item.userId}"/></td>
                            <td><input type="text" value="${item.carNo}"/></td>
                            <td><input type="text" value="${item.promCode}"/></td>
                            <c:forEach var="lookupItem" items="${lookupList}" varStatus="status">
                            	<c:if test="${lookupItem.lookupCde eq  item.applyStatus}">
                            		<td><input type="text" value="${lookupItem.dscr}"/></td>
                            	</c:if>
                            </c:forEach>
                            <td><input type="text" value="<fmt:formatDate value="${item.applyDate}" type="Both" pattern="yyyy/MM/dd"/>"/></td>
                            <td><input type="text" value="${item.insuranceRate}"/></td>
                            <td><input type="text" value="<fmt:formatDate value="${item.compDate}" type="Both" pattern="yyyy/MM/dd"/>"/></td>
                            <td><input type="text" value="<fmt:formatDate value="${item.effUbiDate}" type="Both" pattern="yyyy/MM/dd"/>"/></td>
                            <td><input type="text" value="<fmt:formatDate value="${item.endUbiDate}" type="Both" pattern="yyyy/MM/dd"/>"/></td>
                            <td><input type="text" value="${item.userstamp}"/></td>
                            <td><input type="text" class="alignC" id="datestamp${status.count}" value="<fmt:formatDate value="${item.datestamp}" type="Both" pattern="yyyy/MM/dd"/>"/></td>
                            <td id="editTd${status.index}" class="alignC">
                                <input type="text" value=" >>" onclick="javascript:doDetail(this, '${item.applyNo}');" style="cursor: pointer"/>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>    
    </div>
    <form id="clearForm" method="post" action="${ctx}/OMOM005F/index.html">
    	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    </form>
 
    <form id="updateForm" method="post" action="${ctx}/OMOM005F/update.html">
    	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="updatePageType" name="updatePageType"/>
    </form>
 
    <form id="detailQueryForm" method="post" action="${ctx}/OMOM005F/detail.html">
    	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="detailApplyNo" name="detailApplyNo" />
        <input type="hidden" id="orderByClause" name="orderByClause" value="" />
        <input type="hidden" name="pages" value="1"/><%-- 前往detail的查詢頁數初始 = 1 --%>
        <input type="hidden" id="perPageNum" name="perPageNum" value="${detailQueryForm.perPageNum}" />
    </form>
 	<form id="insertForm" method="post" action="${ctx}/OMOM005F/insert.html">
 		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    </form>
</div>
</body>
</html>