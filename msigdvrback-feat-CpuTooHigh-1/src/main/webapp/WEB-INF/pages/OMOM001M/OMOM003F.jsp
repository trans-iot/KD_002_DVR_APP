<!-- 
    title 設備資料查詢
    version 1.0 
    author Bob
    since 2018/10/24
-->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title><spring:message code ="omom003f.title" /></title>
<script>
    function doSearch() {
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
    
    function doDetail(btn, key1, key2, key3) {
    	btn.disabled = true;
        $("#detailDeviceType").val(key1);
        $("#detailMacAddress").val(key2);
        $("#detailSn").val(key3);
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
    });
</script>
<style>
</style>
</head>
<body>
<div class="mainDiv">
<br/><br/><br/>
    <form id="queryForm" name="queryForm" method="post" action="${ctx}/OMOM003F/query.html">        
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <%-- 排序欄位 --%>
        <input type="hidden" id="orderByClause" name="orderByClause" value="${queryForm.orderByClause}"/>
        <%-- 頁次 --%>
        <input type="hidden" id="pages" name="pages" value="${queryForm.pages}"/>
        <%-- 每頁筆數 --%>
        <input type="hidden" id="perPageNum" name="perPageNum" value="${queryForm.perPageNum}" >
        <table class="queryTable" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2" cellspacing="0">
            <tr>
            	<%-- 設備種類 --%>
            	<th style="width:10%"><spring:message code="omom003f.search1"/>：</th><%-- 複製來源 --%>
				<td>
					<select class="select" id="deviceType" name="deviceType">
						<option value="" selected><spring:message code="select.options.0.label"/></option>
						<c:forEach items="${detyplist}" var="item">
							<option value="${item.lookupCde}"  ${queryForm.deviceType eq item.lookupCde ? 'selected' : ''}>${item.dscr}</option>
						</c:forEach>
					</select>
				</td>
				<%-- DVR S/N --%>
				<th><spring:message code="omom003f.label.sn"/>：</th>
                <td><input type="text" id="sn" name="sn" maxlength='80' value="${queryForm.sn}"/></td>
            </tr>
            <tr>
            	<%-- DVR IMEI --%>
				<th><spring:message code="omom003f.label.imei"/>：</th>
                <td><input type="text" id="imei" name="imei" maxlength='80' value="${queryForm.imei}"/></td>
				<%-- 設備狀態 --%>
            	<th style="width:10%"><spring:message code="omom003f.search4"/>：</th><%-- 複製來源 --%>
				<td>
					<select class="select" id="deviceStatus" name="deviceStatus">
						<option value="" selected><spring:message code="select.options.0.label"/></option>
						<c:forEach items="${destaList}" var="item">
							<option value="${item.lookupCde}"  ${queryForm.deviceStatus eq item.lookupCde ? 'selected' : ''}>${item.dscr}</option>
						</c:forEach>
					</select>
				</td>
            </tr>
            <tr>
            	<%-- 綁定標的號碼 --%>
				<th><spring:message code="omom003f.label.targetNo"/>：</th>
                <td><input type="text" id="targetNo" name="targetNo" maxlength='80' value="${queryForm.targetNo}"/></td>
         		 <%-- SIM卡門號 --%>
				<th><spring:message code="omom003f.label.simMobile"/>：</th>
                <td><input type="text" id="simMobile" name="simMobile" maxlength='80' value="${queryForm.simMobile}"/></td>
            </tr>
             <tr>
            	<%-- 綁定會員 --%>
				<th><spring:message code="omom003f.header5.label"/>：</th>
                <td><input type="text" id="userId" name="userId" maxlength='80' value="${queryForm.userId}"/></td>
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
                    <th width="10%"><spring:message code="omom003f.header1.label"/></th><%-- 設備種類 --%>
                    <th width="10%"><spring:message code="omom003f.label.sn"/></th><%-- DVR S/N --%>
                    <th width="10%"><spring:message code="omom003f.label.imei"/></th><%-- DVR IMEI --%>
                    <th width="10%"><spring:message code="omom003f.header4.label"/></th><%-- 設備狀態--%>
                    <th width="10%"><spring:message code="omom003f.header5.label" /></th><%-- 綁定會員 --%>
                    <th width="10%"><spring:message code="omom003f.label.targetNo"/></th><%-- 綁定標的號碼 --%>
                    <th width="10%"><spring:message code="omom003f.label.simMobile"/></th><%-- SIM卡門號 --%>
                    <th width="10%"><spring:message code="omom003f.label.uploadTime"/></th><%-- 設備資料上傳時間 --%>
                    <th width="6%"><spring:message code="userstamp.label"/></th><%-- 修改人員 --%>
                    <th width="6%"><%-- 修改日期 --%>
                        <spring:message code="datestamp.label"/>
                        <button onClick="<c:if test='${not empty list}'>orderBy(this);</c:if>" 設備狀態class="imgBox">
                            <img width="15" src="${ctx}/images/desc.png" title="<spring:message code='sort.label'/>" border="0"/>
                        </button>
                    </th>
                    <th width="6%"><spring:message code="omom003f.header7.label"/></th><%-- 設備解綁 --%>
                </tr>
            </thead>
            <tbody>
                <c:if test="${list ne null and fn:length(list) eq 0}">
                    <tr>
                        <td colspan='11' class="alignC"><spring:message code='no.result.message'/></td>
                    </tr>
                </c:if>
                <c:if test="${not empty list}">
                    <c:forEach var="item" items="${list}" varStatus="status">
                        <tr>
                            <c:forEach var="detypItem" items="${detyplist}" varStatus="status">
                            	<c:if test="${detypItem.lookupCde eq  item.deviceType}">
                            		<td><input type="text" value="${detypItem.dscr}"/></td>
                            	</c:if>
                            </c:forEach>
                            <td><input type="text" value="${item.sn}"/></td>
                            <td><input type="text" value="${item.imei}"/></td>
                            <c:forEach var="destaItem" items="${destaList}" varStatus="status">
                            	<c:if test="${destaItem.lookupCde eq  item.deviceStatus}">
                            		<td><input type="text" value="${destaItem.dscr}"/></td>
                            	</c:if>
                            </c:forEach>
                            <td><input type="text" value="${item.userId}"/></td>
                            <td><input type="text" value="${item.targetNo}"/></td>
                            <td><input type="text" value="${item.simMobile}"/></td>
                            <td><input type="text" value="<fmt:formatDate value="${item.uploadDate}" type="Both" pattern="yyyy/MM/dd HH:mm"/>"/></td>
                            <td><input type="text" value="${item.userstamp}"/></td>
                            <td><input type="text" class="alignC" id="datestamp${status.count}" value="<fmt:formatDate value="${item.datestamp}" type="Both" pattern="yyyy/MM/dd"/>"/></td>
                            <td id="editTd${status.index}" class="alignC">
                                <input type="text" value=" >>" onclick="javascript:doDetail(this, '${item.deviceType}', '${item.imei}', '${item.sn}');" style="cursor: pointer"/>
                            </td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>    
    </div>
    <form id="clearForm" method="post" action="${ctx}/OMOM003F/index.html">
    <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    </form>
 
    <form id="updateForm" method="post" action="${ctx}/OMOM003F/update.html">
    <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="updatePageType" name="updatePageType"/>
    </form>
 
    <form id="detailQueryForm" method="post" action="${ctx}/OMOM003F/detail.html">
    <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="detailDeviceType" name="detailDeviceType" />
        <input type="hidden" id="detailMacAddress" name="detailMacAddress" />
        <input type="hidden" id="detailSn" name="detailSn" />
        <input type="hidden" id="orderByClause" name="orderByClause" value="" />
        <input type="hidden" name="pages" value="1"/><%-- 前往detail的查詢頁數初始 = 1 --%>
        <input type="hidden" id="perPageNum" name="perPageNum" value="${detailQueryForm.perPageNum}" />
    </form>
 	<form id="insertForm" method="post" action="${ctx}/OMOM003F/insert.html">
 	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    </form>
</div>
</body>
</html>