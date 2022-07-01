<!-- 
	角色主檔查詢畫面
	version 1.0 
	author Olson
	since 2012/11/21
 -->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title><spring:message code ="sys005f.title" /></title>
	<script>
		$(document).ready(function(){
			//自動轉roleId大寫
			autoUpperCase("roleId");
			
			//直接按Enter
			$("#queryForm input").keypress(function (e) {
		        if (event.keyCode == 13) //檢查帳號欄位是否為空
		        {
		            $("#queryBtn").click(); //執行登入click
		        }
	   		});
		}); 
		
		function doSearch() {
			$("#orderByClause").val("role_id");
			//預設排序欄位
			$("#pages").val("1");
			$("#queryForm").submit();
		}
		
		function doClear() {
			$("#clearForm").submit();
		}
		
		function doCreate() {
			$("#insertForm").submit();
		}
		
		function doUpdate(btn, roleId, effDate) {
			btn.disabled;
			$("#updateRoleId").attr("value", roleId);
			$("#updateEffDate").attr("value", effDate);
			$("#updateForm").submit();
			btn.enabled;
		}
		
		function doDetail(btn, roleId , effDate) {
    		btn.disabled;
    		$("#detailRoleId").attr("value", roleId);
			$("#detailEffDate").attr("value", effDate);
			$("#detailForm").submit();
    		btn.enabled;
    	}
		
		function doDelete(btn, roleId,effDate,endDate) {
    		btn.disabled;
    		var ans = confirm('<spring:message code="delete.confirm.message" />');
    		if (ans) {
    			//檢核，若未到終止日則不可刪除
    			var checked = chkEndEffDate(effDate, endDate);
	    			if(checked){
	    			$("#deleteRoleId").attr("value", roleId);
	    			$("#deleteEffDate").attr("value", effDate);
	    			var actionUrl = $("#deleteForm").attr("action"); 
	    			var param = $("#deleteForm").serialize();
	    			doCwAjax(actionUrl, param, ajaxDeleteDo);
    			}
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
		
		function chkEndEffDate(effDate, endDate){
			//若失效日期為空值，禁止刪除
			if(endDate==''){
				alert('<spring:message code="sys005f.endDate.delete.error"/>')
				return false;
			}
			//若失效日小於今日 表示還不能刪除
			var today = "<fmt:formatDate value='${today}' type='Both' pattern='yyyy/MM/dd'/>";
			<%-- 若生效日大於今日並且生效日等於失效日 , edited by Marks 2020/09/18--%>
			if(effDate > today && effDate == endDate) {
				return true;
			} else if (endDate > today) {
				alert('<spring:message code="sys005f.endDate.delete.error"/>')
				return false;
			}
			return true;
		}
		
		//以修改日期排序
    	function orderBy(obj){ 
    		$("#orderByClause").val("datestamp desc");  
    		$("#pages").val("1");
    		$("#queryForm").submit();
    	}
	</script>
	
</head>
<body>
<div class="mainDiv">
<br/><br/><br/>
	<form id="queryForm" name="queryForm" method="post" action="${ctx}/SYS005F/query.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<%-- 排序欄位 --%>
		<input type="hidden" id="orderByClause" name="orderByClause" value="${queryForm.orderByClause}"/>
		<%-- 頁次 --%>
		<input type="hidden" id="pages" name="pages" value="${queryForm.pages}"/>
		<%-- 每頁筆數 --%>
		<input type="hidden" id="perPageNum" name="perPageNum" value="${queryForm.perPageNum}" >
		
		<table class="queryTable" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2" cellspacing="0">
			<tr>
				<%-- 角色代碼 --%>
				<th style="width:10%;text-align:center;"><spring:message code="sys005f.role.id"/>：<input type="text" class="inputY" id="roleId" name="roleId" maxlength="10" value="${queryForm.roleId}"/></th>
			</tr>
		</table>
		
		<%-- 按鈕區域 --%>
		<div class="buttonDiv">
			<input type="button" id="queryBtn" value="<spring:message code="searchBtn.label"/>" class="btn" onclick="javascript:doSearch();" />
			<input type="button" value="<spring:message code="clear.label"/>" class="btn" onclick="javascript:doClear();" />
		</div>
	
	</form>
	
	<div id="blockB">
		<table class="resultTable" id="mainTable">
			<thead>
				<tr>
					<!-- 分頁 -->
					<th class="link" colspan="15">
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
						<script type="text/javascript">
							function beforeReOrdering() {
				    			//資料驗證...
				    		}
						</script>
					</th>
				</tr>
				
				<tr class="nowrapHeader listHeader">
					<%-- 編輯 --%>
					<th style="width:60px"><spring:message code="edit.label"/></th>
					<%-- 角色代碼 --%>
					<th><spring:message code="sys005f.role.id"/></th>
					<%-- 生效日期 --%>
					<th><spring:message code="eff.date.label"/></th>
					<%-- 失效日期 --%>
					<th><spring:message code="end.date.label"/></th>
					<%-- 修改人員 --%>
					<th style="width:130px"><spring:message code="userstamp.label"/></th>
					<%-- 修改日期 --%>
					<th style="width:90px">
						<spring:message code="datestamp.label"/>
						<button onClick="<c:if test="${not empty list}">orderBy(this);</c:if>" class="imgBox">
					  		<img src="${ctx}/images/desc.png" title="<spring:message code='sort.label'/>" border="0"/>
					  	</button>
					</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${list ne null and fn:length(list) eq 0}">
					<tr class="rowC alignC">
						<td colspan='11'><spring:message code='no.result.message'/></td>
					</tr>
				</c:if>
				<c:if test="${not empty list}">
					<c:forEach var="item" items="${list}" varStatus="status">
						<tr class="${status.count % 2 == 0? 'rowS' : 'rowC' }">
							<td class="alignC" style="width:60px">
								<button onclick="javascript:doUpdate(this,'${item.roleId}','<fmt:formatDate value="${item.effDate}" type="Both" pattern="yyyy/MM/dd"/>');"><img src="${ctx}/images/UPDATE.png" title="<spring:message code="modify.label"/>" class="imgBox"/></button>
								<button onclick="javascript:doDelete(this,'${item.roleId}','<fmt:formatDate value="${item.effDate}" type="Both" pattern="yyyy/MM/dd"/>','<fmt:formatDate value="${item.endDate}" type="Both" pattern="yyyy/MM/dd"/>');"><img src="${ctx}/images/DELETE.png" title="<spring:message code="delete.label"/>" class="imgBox"/></button>
								<button onclick="javascript:doDetail(this,'${item.roleId}','<fmt:formatDate value="${item.effDate}" type="Both" pattern="yyyy/MM/dd"/>');"><img src="${ctx}/images/detail.png" title="<spring:message code="detail.search.label"/>" class="imgBox"/></button>
								<input type="hidden" id="crDate_${status.count}" value="<fmt:formatDate value="${item.crDate}" type="Both" pattern="yyyy/MM/dd"/>" />
							</td>
							<%-- 角色代碼 --%>
							<td><input class="texboxtNF" value="${item.roleId}" readonly></td>
							<%-- 生效日期 --%>
							<td><input class="texboxtNF alignC" value="<fmt:formatDate value='${item.effDate}' type='Both' pattern='yyyy/MM/dd'/>" /></td>
							<%-- 失效日期 --%>
							<td><input class="texboxtNF alignC" value="<fmt:formatDate value='${item.endDate}' type='Both' pattern='yyyy/MM/dd'/>" /></td>
							<%-- 修改人員 --%>
							<td style="width:130px"><input class="texboxtNF" value="${item.userstamp}" readonly></td>
							<%-- 修改日期 --%>
							<td style="width:90px"><input class="texboxtNF alignC" value="<fmt:formatDate value='${item.datestamp}' type='Both' pattern='yyyy/MM/dd'/>" /></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
	</div>
	
	<form id="deleteForm" method="post" action="${ctx}/SYS005F/delete.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="deleteRoleId" name="roleId"/>
		<input type="hidden" id="deleteEffDate" name="effDate"/>
	</form>
	<form id="clearForm" method="post" action="${ctx}/SYS005F/index.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	</form>
	<form id="insertForm" method="post" action="${ctx}/SYS005F/insert.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	</form>
	<form id="updateForm" method="post" action="${ctx}/SYS005F/update.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="updateRoleId" name="roleId"/>
		<input type="hidden" id="updateEffDate" name="effDate"/>
	</form>
	<form id="detailForm" method="post" action="${ctx}/SYS005F/detail.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="detailRoleId" name="roleId"/>
		<input type="hidden" id="detailEffDate" name="effDate"/>
	</form>
	<form id="pageForm" method="post" action="${ctx}/SYS005F/query.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	</form>
</div>
</body>
</html>