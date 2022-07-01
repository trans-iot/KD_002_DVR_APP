<!-- 
    title 參考會員資料維護
    version 1.0 
    author Leo
    since 2016/04/14
-->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title><spring:message code ="smdd003f.title" /></title>
<script>
    function doSearch() {
    	var valid = $("#queryForm").validationEngine('validate');

    	if(!valid){
    		$("#queryForm").validationEngine();
    		return false;
       	}
        
        $("#orderByClause").val("datestamp desc");//預設排序欄位
        $("#pages").val("1");//預設頁次
        $("#queryForm").submit();
    }
    
    function doClear() {
        $("#clearForm").submit();
    }

    function doCreateData() {
		$('#insertForm').submit();
    }
    
  
    function doUpdate(btn, key) {
        btn.disabled = true;
        $("#updatetuserId").val(key);
        $("#updateForm").submit();
        btn.disabled = false;
    }
    
    function doDetail(btn, key1) {
    	btn.disabled = true;
        $("#detailuserId").val(key1);
        $("#detailQueryForm").submit();
        btn.disabled = false;
    }

    function doSearchMilage(btn, userId) {
    	btn.disabled = true;
        $("#milageUserId").val(userId);
        $("#detailMilageForm").submit();
        btn.disabled = false;
    }
    
    function doCrudDetail(btn, key) {
    	btn.disabled = true;
        $("#crudDetailSeq").val(key);
        $("#crudDetailQueryForm").submit();
        btn.disabled = false;
    }

    function doDelete(btn, userId) {
		if(confirm('<spring:message code="delete.confirm.message" />')) {
			$('#deleteUserId').val(userId);
			var actionUrl = $("#deleteForm").attr("action"); 
			var param = $("#deleteForm").serialize();
			doCwAjax(actionUrl, param, ajaxDeleteDo);
		}
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
        bindCalendar("crDateBegin");
        bindCalendar("crDateEnd");

        bindValidation("queryForm");
    });
</script>
</head>
<body>
<div class="mainDiv">
<br/><br/><br/>
    <form id="queryForm" name="queryForm" method="post" action="${ctx}/SMDD003F/query.html">        
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <%-- 排序欄位 --%>
        <input type="hidden" id="orderByClause" name="orderByClause" value="${queryForm.orderByClause}"/>
        <%-- 頁次 --%>
        <input type="hidden" id="pages" name="pages" value="${queryForm.pages}"/>
        <%-- 每頁筆數 --%>
        <input type="hidden" id="perPageNum" name="perPageNum" value="${queryForm.perPageNum}" >
        <table class="queryTable" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2" cellspacing="0">
            <tr>
                <%-- 會員編號 --%>
                <th><spring:message code="smdd003f.search1"/>：</th>
                <td><input type="text" id="user_id" name="user_id"  maxlength='80' value="${queryForm.user_id}"/></td>
                <%-- 電子信箱 --%>
                <th><spring:message code="smdd003f.search3"/>：</th>
                <td><input type="text" id="email" name="email" maxlength='80' value="${queryForm.email}"/></td>
            </tr>
            <tr>
            	<%-- 中文姓名--%>
                <th><spring:message code="smdd003f.search2"/>：</th>
                <td><input type="text" id="user_name" name="user_name" maxlength='80' value="${queryForm.user_name}"/></td>
              	
                <%-- 身分證號 --%>
                <th><spring:message code="smdd003f.search4"/>：</th>
                <td><input type="text" id="cuid" name="cuid" maxlength='80' value="${queryForm.cuid}"/></td>
            </tr>
            <tr>
            	<%-- 會員狀態--%>
                <th><spring:message code="smdd003f.search5"/>：</th>
                <td>
                	<select class="select" id="cust_status" name="cust_status">
						<option value="" selected><spring:message code="select.options.0.label"/></option>
						<c:forEach items="${custstatusaDscr}" var="custstatusaDscr">
							<option value="${custstatusaDscr.key}"  ${custstatusaDscr.key eq queryForm.cust_status ? 'selected' : ''}>${custstatusaDscr.key}  -  ${custstatusaDscr.value}</option>
						</c:forEach>
					</select>
                </td>
            </tr>
            <tr>
                <%-- 註冊日期起訖 --%>
                <th><spring:message code="smdd003f.registerTime"/>：</th>
                <td colspan="3" style="color:white">
	                <input type="text" id="crDateBegin" name="registerTimeBegin" class="inputY inputY_L" maxlength='30' value="${queryForm.registerTimeBegin}"/>
	            	~
	                <input type="text" id="crDateEnd" name="registerTimeEnd" class="inputY inputY_L"  maxlength='30' value="${queryForm.registerTimeEnd}"/>
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
                    <th width="6%"><spring:message code="edit.label"/></th><%-- 編輯 --%>
                    <th width="10%"><spring:message code="smdd003f.header1.label"/></th><%-- USER ID --%>
                    <th width="10%"><spring:message code="smdd003f.header2.label"/></th><%-- 中文姓名  --%>
                    <th width="10%"><spring:message code="smdd003f.header3.label"/></th><%-- 電子信箱  --%>
                    <th width="10%"><spring:message code="smdd003f.header4.label"/></th><%-- 車牌號碼  --%>
                    <th width="10%"><spring:message code="smdd003f.header5.label"/></th><%-- 會員狀態  --%>
                    <th width="10%"><spring:message code="smdd003f.header7.label"/></th><%-- 綁定DVR S/N  --%>
                    <th width="6%"><spring:message code="smdd003f.header6.label"/></th><%-- 註冊日期 --%>
                    <th width="6%"><spring:message code="userstamp.label"/></th><%-- 修改人員 --%>
                    <th width="6%"><%-- 修改日期 --%>
                        <spring:message code="datestamp.label"/>
                        <button onClick="<c:if test='${not empty list}'>orderBy(this);</c:if>" class="imgBox">
                            <img width="15" src="${ctx}/images/desc.png" title="<spring:message code='sort.label'/>" border="0"/>
                        </button>
                    </th>
                    <th width="6%"><spring:message code="smdd003f.header8.label"/></th><%-- 里程數查詢 --%>
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
                                <button onclick="javascript:doUpdate(this, '${item.userId}');"><img src="${ctx}/images/UPDATE.png" title="<spring:message code="modify.label"/>" class="imgBox" /></button>
                                <button onclick="javascript:doDetail(this, '${item.userId}');"><img src="${ctx}/images/detail.png" title="<spring:message code="detail.search.label"/>" class="imgBox"/></button>
                            </td>
                            <td><input type="text" value="${item.userId}"/></td>
                            <td><input type="text" value="${item.userName}"/></td>
                            <td><input type="text" value="${item.email}"/></td>
                            <td><input type="text" value="${item.carNo}"/></td>
                            <c:set var="refreshSent" value="false"/>
                            <c:forEach items="${custstatusaDscr}" var="custstatusaDscr">
								<c:choose>
						 			<c:when test="${custstatusaDscr.key eq 	item.custStatus}">
						 				 <c:set var="refreshSent" value="true"/>
										 <td><input type="text" value="${custstatusaDscr.key}  -  ${custstatusaDscr.value}"/></td>
									</c:when>
								</c:choose>
							</c:forEach>
							<c:choose>
						 		<c:when test="${refreshSent eq false}">
										<td><input type="text" value="${item.custStatus}  -   "/></td>
								</c:when>
							</c:choose>
                            <td><input type="text" value="${item.sn}"/></td>
                            <td><input type="text" value="<fmt:formatDate value="${item.registerTime}" type="Both" pattern="yyyy/MM/dd"/>"/></td>
                            <td><input type="text" value="${item.userstamp}"/></td>
                            <td><input type="text" class="alignC" id="datestamp${status.count}" value="<fmt:formatDate value="${item.datestamp}" type="Both" pattern="yyyy/MM/dd"/>"/></td>
                            <td  class="alignC"><input type="text" value=" >>" onclick="javascript:doSearchMilage(this,'${item.userId}');" style="cursor: pointer"/></td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>    
    </div>
    <form id="clearForm" method="post" action="${ctx}/SMDD003F/index.html">
    	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    </form>
    
    <form id="insertForm" method="post" action="${ctx}/SMDD003F/insert.html">
    	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    </form>
 
    <form id="updateForm" method="post" action="${ctx}/SMDD003F/update.html">
    	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="updatetuserId" name="updatetuserId"/>
    </form>
    
    <form id="deleteForm" method="post" action="${ctx}/SMDD003F/delete.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="deleteUserId" name="deleteUserId"/>
	</form>
 
    <form id="detailQueryForm" method="post" action="${ctx}/SMDD003F/detail.html">
    	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="detailuserId" name="detailuserId" />
        <input type="hidden" id="orderByClause" name="orderByClause" value="" />
        <input type="hidden" name="pages" value="1"/><%-- 前往detail的查詢頁數初始 = 1 --%>
        <input type="hidden" id="perPageNum" name="perPageNum" value="${detailQueryForm.perPageNum}" />
    </form>
 
  	<form id="detailMilageForm" method="post" action="${ctx}/SMDD003F/miageView.html">
    	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="milageUserId" name="milageUserId"/>
    </form>
</div>
</body>
</html>