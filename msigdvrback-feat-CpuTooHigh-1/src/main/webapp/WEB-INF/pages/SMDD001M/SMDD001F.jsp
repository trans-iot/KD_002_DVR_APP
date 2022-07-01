<!-- 
    title 參考資料代碼維護查詢
    version 1.0 
    author Gary
    since 2013/01/03
-->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title><spring:message code ="smdd001f.title" /></title>
<script>
    function doSearch() {
        $("#orderByClause").val("a.lookup_type");//預設排序欄位
        $("#pages").val("1");//預設頁次
        $("#queryForm").submit();
    }
    
    function doClear() {
        $("#clearForm").submit();
    }
    
    function doCreate() {
        $("#insertForm").submit();
    }
    
    function doUpdate(btn, key) {
        btn.disabled = true;
        $("#updateSeq").val(key);
        $("#updateForm").submit();
        btn.disabled = false;
    }
    
    function doDelete(btn, key) {
    	btn.disabled = true;
        var ans = confirm("<spring:message code='delete.confirm.message'/>")
        if (ans) {
            $("#deleteSeq").val(key);
            var actionUrl = $("#deleteForm").attr("action"); 
            var param = $("#deleteForm").serialize();
            var chkUrl = "${ctx}/SMDD001F/hasDetail.html";
            var hasData = false, fail = false;
            doCwAjaxSync(chkUrl, param, function(data) {
            	if (data.result == "${ajaxOk}") {
            		if (data.map.hasDetail) {
            			if (confirm("<spring:message code='smdd001f.detail.check.prompt'/>")) {
            				doCwAjax(actionUrl, param, ajaxDeleteDo);
            			}
                    } else {
                        doCwAjax(actionUrl, param, ajaxDeleteDo);
                    }
            	}
            });
            
        }
        btn.disabled = false;
    }
    
    function ajaxDeleteDo(data) {
        if (data.result == "${ajaxOk}") {
            $("#queryForm").submit();
        }
    }
    
    function doDetail(btn, key) {
    	btn.disabled = true;
        $("#detailSeq").val(key);
        $("#detailQueryForm").submit();
        btn.disabled = false;
    }
    
    function doCrudDetail(btn, key) {
    	btn.disabled = true;
        $("#crudDetailSeq").val(key);
        $("#crudDetailQueryForm").submit();
        btn.disabled = false;
    }
    
    //以修改日期排序
    function orderBy(obj){ 
        $("#orderByClause").val("a.datestamp desc");  
        $("#pages").val("1");
        $("#queryForm").submit();
    }
    
    $(document).ready(function(){
    	// 調整編輯欄位的button padding
    	$("td[id^='editTd'] button").css("padding", "0");
        bindFormCSS("queryForm", "query");
    });
</script>
</head>
<body>
<div class="mainDiv">
<br/><br/><br/>
    <form id="queryForm" name="queryForm" method="post" action="${ctx}/SMDD001F/query.html">        
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <%-- 排序欄位 --%>
        <input type="hidden" id="orderByClause" name="orderByClause" value="${queryForm.orderByClause}"/>
        <%-- 頁次 --%>
        <input type="hidden" id="pages" name="pages" value="${queryForm.pages}"/>
        <%-- 每頁筆數 --%>
        <input type="hidden" id="perPageNum" name="perPageNum" value="${queryForm.perPageNum}" >
        <table class="queryTable" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2" cellspacing="0">
            <tr>
                <%-- 參考資料種類 --%>
                <th><spring:message code="smdd001f.search1"/>：</th>
                <td><input type="text" id="lookupType" name="lookupType" maxlength='10' value="${queryForm.lookupType}"/></td>
                <%-- 名稱 --%>
                <th><spring:message code="smdd001f.search2"/>：</th>
                <td><input type="text" id="dscr" name="dscr" maxlength='80' value="${queryForm.dscr}"/></td>
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
    										<button onclick="javascript:doCreate();"><img src="${ctx}/images/NEW.png" title="<spring:message code="insert.label"/>" class="imgBox"></button>
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
                    <th width="6%"><spring:message code="edit.label"/></th><%-- 編輯 --%>
                    <th width="10%"><spring:message code="smdd001f.header1.label"/></th><%-- 參考資料種類 --%>
                    <th width="10%"><spring:message code="smdd001f.header2.label"/></th><%-- 名稱 --%>
                    <th width="10%"><spring:message code="smdd001f.header3.label"/></th><%-- 數值備註 --%>
                    <th width="10%"><spring:message code="smdd001f.header4.label"/></th><%-- 類別一備註 --%>
                    <th width="10%"><spring:message code="smdd001f.header5.label"/></th><%-- 類別二備註 --%>
                    <th width="10%"><spring:message code="smdd001f.header6.label"/></th><%-- 類別三備註 --%>
                    <th width="10%"><spring:message code="smdd001f.param.or.not.label"/></th><%-- 是否為系統參數 --%>
                    <th width="6%"><spring:message code="userstamp.label"/></th><%-- 修改人員 --%>
                    <th width="6%"><%-- 修改日期 --%>
                        <spring:message code="datestamp.label"/>
                        <button onClick="<c:if test='${not empty list}'>orderBy(this);</c:if>" class="imgBox">
                            <img width="15" src="${ctx}/images/desc.png" title="<spring:message code='sort.label'/>" border="0"/>
                        </button>
                    </th>
                    <th width="1%">&nbsp;</th><%--查看明細--%>
                </tr>
            </thead>
            <tbody>
                <c:if test="${list ne null and fn:length(list) eq 0}">
                    <tr>
                        <td colspan='12' class="alignC"><spring:message code='no.result.message'/></td>
                    </tr>
                </c:if>
                <c:if test="${not empty list}">
                    <c:forEach var="item" items="${list}" varStatus="status">
                        <tr>
                            <td id="editTd${status.index}" class="alignC">
                                <button onclick="javascript:doUpdate(this, '${item.lookupType}');"><img src="${ctx}/images/UPDATE.png" title="<spring:message code="modify.label"/>" class="imgBox" /></button>
                                <button onclick="javascript:doDelete(this, '${item.lookupType}');"><img src="${ctx}/images/DELETE.png" title="<spring:message code="delete.label"/>" class="imgBox"/></button>
                                <button onclick="javascript:doDetail(this, '${item.lookupType}');"><img src="${ctx}/images/detail.png" title="<spring:message code="detail.search.label"/>" class="imgBox"/></button>
                                <input type="hidden" id="crDate_${status.count}" value="<fmt:formatDate value="${item.crDate}" type="Both" pattern="yyyy/MM/dd"/>" />
                            </td>
                            <td><input type="text" value="${item.lookupType}"/></td>
                            <td><input type="text" value="${item.dscr}"/></td>
                            <td><input type="text" value="${item.valueDscr}"/></td>
                            <td><input type="text" value="${item.type1Dscr}"/></td>
                            <td><input type="text" value="${item.type2Dscr}"/></td>
                            <td><input type="text" value="${item.type3Dscr}"/></td>
                            <td><input type="text" class="alignC" value="<c:if test="${item.sysIndic eq 'Y'}"><spring:message code='yes.label'/></c:if><c:if test="${item.sysIndic eq 'N'}"><spring:message code='no.label'/></c:if>"/></td>
                            <td><input type="text" value="${item.userstamp}"/></td>
                            <td><input type="text" class="alignC" id="datestamp${status.count}" value="<fmt:formatDate value="${item.datestamp}" type="Both" pattern="yyyy/MM/dd"/>"/></td>
                            <td align="center">
                                <input type="button" onclick="doCrudDetail(this, '${item.lookupType}');" class="btn" value=">>" readonly />
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>    
    </div>
    <form id="clearForm" method="post" action="${ctx}/SMDD001F/index.html">
    <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    </form>
    <form id="insertForm" method="post" action="${ctx}/SMDD001F/insert.html">
    <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    </form>
    <form id="updateForm" method="post" action="${ctx}/SMDD001F/update.html">
    	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="updateSeq" name="seq"/>
    </form>
    <form id="deleteForm" method="post" action="${ctx}/SMDD001F/delete.html">
    	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="deleteSeq" name="seq"/>
    </form>
    <form id="detailQueryForm" method="post" action="${ctx}/SMDD001F/detail.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="detailSeq" name="seq" />
        <input type="hidden" id="orderByClause" name="orderByClause" value="lookup_cde" />
        <input type="hidden" name="pages" value="1"/><%-- 前往detail的查詢頁數初始 = 1 --%>
        <input type="hidden" id="perPageNum" name="perPageNum" value="${detailQueryForm.perPageNum}" />
    </form>
    <form id="crudDetailQueryForm" method="post" action="${ctx}/SMDD001F/crudDetail.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="crudDetailSeq" name="seq" />
        <input type="hidden" id="orderByClause" name="orderByClause" value="lookup_cde, datestamp desc" />
        <input type="hidden" name="pages" value="1"/><%-- 前往crudDetail的查詢頁數初始 = 1 --%>
        <input type="hidden" id="perPageNum" name="perPageNum" value="${crudDetailQueryForm.perPageNum}" />
    </form>
</div>
</body>
</html>