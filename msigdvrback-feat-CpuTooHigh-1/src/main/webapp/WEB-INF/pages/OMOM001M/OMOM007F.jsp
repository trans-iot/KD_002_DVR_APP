<!-- 
    title 長期未使用DVR的客戶清單
    version 1.0 
    author mingkun
    since 2020/06/12
-->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title><spring:message code ="omom007f.title" /></title>
<script>
    function doSearch() {
        if($('#day').val() === '') {
			alert('<spring:message code="omom007f.chooseDate.message" />');
			return false;
        }
        $("#orderByClause").val("page_type");//預設排序欄位
        $("#pages").val("1");//預設頁次
        $("#queryForm").submit();
        $('#exportBtn').prop("disabled", false);
    }
    
    function doClear() {
        $('#clearForm').submit();
        $('#exportBtn').prop("disabled", true);
    }

    function doExport() {
        $("#queryForm").attr("action","${ctx}/OMOM007F/doExport.html");
        $("#queryForm").submit();
        $("#queryForm").attr("action","${ctx}/OMOM007F/query.html");
    }

    $(document).ready(function(){
        bindFormCSS("queryForm", "query");
        if(${totalCount} > 0){
            $("#exportBtn").prop('disabled', false);
        }
        if ("${errMsg}" != '') {
            alert("${errMsg}");
        }
    });
</script>
</head>
<body>
<div class="mainDiv">
<br/><br/><br/>
    <form id="queryForm" name="queryForm" method="post" action="${ctx}/OMOM007F/query.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <%-- 排序欄位 --%>
        <input type="hidden" id="orderByClause" name="orderByClause" value="${queryForm.orderByClause}"/>
        <%-- 頁次 --%>
        <input type="hidden" id="pages" name="pages" value="${queryForm.pages}"/>
        <%-- 每頁筆數 --%>
        <input type="hidden" id="perPageNum" name="perPageNum" value="${queryForm.perPageNum}" >
        <table class="queryTable" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2" cellspacing="0">
            <tr>
            	<th style="width:10%"><spring:message code="omom006f.pageType"/>：</th><%-- 複製來源 --%>
				<td>
					<select class="select" id="day" name="day">
						<option value="" selected><spring:message code="select.options.0.label"/></option>
						<c:forEach items="${unUsedDaysList}" var="item">
                            <option value="${item.day}" ${queryForm.day eq item.day ? 'selected' : ''}>${item.desc}</option>
						</c:forEach>
					</select>
				</td>
            </tr>
        </table>
    </form>
    <%-- 按鈕區域 --%>
    <div class="buttonDiv">
        <input type="button" class="btn" value="<spring:message code="searchBtn.label"/>" onclick="javascript:doSearch();" />
        <input type="button" class="btn" value="<spring:message code="clear.label"/>" onclick="javascript:doClear();" />
        <input id ="exportBtn" type="button" class="btn" value="<spring:message code="export.excel.label"/>" onclick="javascript:doExport();" disabled/>
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
                    <th width="10%"><spring:message code="omom007f.userId"/></th><%-- 會員編號 --%>
                    <th width="15%"><spring:message code="omom007f.sn"/></th><%-- SN --%>
                    <th width="10%"><spring:message code="omom007f.carsNo"/></th><%-- 車牌號碼 --%>
                    <th width="10%"><spring:message code="omom007f.email"/></th><%-- 電子信箱 --%>
                    <th width="10%"><spring:message code="omom007f.unuserDays"/></th><%-- 未使用天數 --%>
                    <th width="15%"><spring:message code="omom007f.lastUserDay"/></th><%-- 最後使用日期 --%>
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
                            <td><input type="text" class="alignC" value="${item.userId}"/></td>
                            <td><input type="text" class="alignC" value="${item.sn}"/></td>
                            <td><input type="text" class="alignC" value="${item.carNo}"/></td>
                            <td><input type="text" class="alignC" value="${item.email}"/></td>
                            <td><input type="text" class="alignC" value="${item.unusedDays}"/></td>
                            <td><input type="text" class="alignC" value="${item.uploadDate}"/></td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>    
    </div>
    <form id="clearForm" method="post" action="${ctx}/OMOM007F/index.html">
    	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    </form>
</div>
</body>
</html>