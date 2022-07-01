<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title><spring:message code ="sys004f.title" /></title>
	<script>
		$(document).ready(function(){
			//自動轉apId大寫
			autoUpperCase("pgProjId");
			autoUpperCase("pgId");
			
			//直接按Enter
			$("#queryForm input").keypress(function (e) {
		        if (event.keyCode == 13) //檢查帳號欄位是否為空
		        {
		            $("#queryBtn").click(); //執行登入click
		        }
	   		});
		});
		function doSearch() {
			$("#orderByClause").val("pg_id");
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
		
		function doDetail(btn, pgId, effDate) {
    		btn.disabled;
			$("#detailPgId").attr("value", pgId);
    		
			$("#detailForm").submit();
			btn.enabled;
    	}
		
		//以修改日期排序
    	function orderBy(obj){ 
    		$("#orderByClause").val("datestamp desc");  
    		$("#pages").val("1");
    		$("#queryForm").submit();
    	}
		
    	function doUpdate(btn, pgId, effDate) {
			btn.disabled;
			$("#updatePgId").attr("value", pgId);
			$("#updateEffDate").attr("value", effDate);
			
			$("#updateForm").submit();
			btn.enabled;
		}
    	
    	function doDelete(btn,pgId,effDate,endDate) {
    		btn.disabled;
    		var ans = confirm('<spring:message code="delete.confirm.message" />');
    		if (ans) {
    			//檢核，若未到終止日則不可刪除
    			var checked = chkEndEffDate(effDate, endDate);
	    			if(checked){
	    			$("#deletePgId").attr("value", pgId);
	    			$("#deleteEffDate").attr("value", effDate);
	    			var actionUrl = $("#deleteForm").attr("action"); 
	    			var param = $("#deleteForm").serialize();
	    			doCwAjax(actionUrl, param, ajaxDeleteDo);
    			}
    		}
    		
    		btn.enabled;
    	}
    	
    	function chkEndEffDate(effDate, endDate){
			//若失效日期為空值，禁止刪除
			if(endDate==''){
				alert('<spring:message code="sys004f.endDate.delete.error"/>')
				return false;
			}
			//若失效日小於今日 表示還不能刪除
			var today = "<fmt:formatDate value='${today}' type='Both' pattern='yyyy/MM/dd'/>";
			<%-- 若生效日大於今日並且生效日等於失效日 , edited by Marks 2020/09/18--%>
			if(effDate > today && effDate == endDate) {
				return true;
			} else if (endDate > today) {
				alert('<spring:message code="sys004f.endDate.delete.error"/>')
				return false;
			}
			return true;
		}
    	
    	function ajaxDeleteDo(data) {
    		if (data.result == "${ajaxOk}") {
    			$("#queryForm").submit();
    		}
    		else if(data.result == "${ajaxDeleteFail}"){
    			alert('<spring:message code="delete.fail"/>');	
    		}
    	}
	</script>
</head>
<body>
	<div class="mainDiv">
<br/><br/><br/>
		<form id="queryForm" name="queryForm" method="post" action="${ctx}/SYS004F/query.html">
			<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
			<%-- 排序欄位 --%>
			<input type="hidden" id="orderByClause" name="orderByClause" value="${queryForm.orderByClause}"/>
			<%-- 頁次 --%>
			<input type="hidden" id="pages" name="pages" value="${queryForm.pages}"/>
			<%-- 每頁筆數 --%>
			<input type="hidden" id="perPageNum" name="perPageNum" value="${queryForm.perPageNum}" >
			
			<table class="queryTable" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2" cellspacing="0">
				<tr>
					<%-- 模組代碼 --%>
					<th><spring:message code="sys004f.mod.id"/>：</th>
					<td>
						<input type="text" class="inputY" id="pgProjId" name="pgProjId" maxlength="10" value="${queryForm.pgProjId}"/>
					</td>
					<%-- 程式代碼 --%>
					<th><spring:message code="sys004f.pg.id"/>：</th>
					<td >
						<input type="text" class="inputY" id="pgId" name="pgId" maxlength="10" value="${queryForm.pgId}"/>
					</td>
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
						<th width="10%"><spring:message code="edit.label"/></th>
						<%-- 模組代碼 --%>
						<th width="10%"><spring:message code="sys004f.mod.id"/></th>
						<%-- 程式代碼 --%>
						<th width="10%"><spring:message code="sys004f.pg.id"/></th>
						<%-- 程式名稱 --%>
						<th width="25%"><spring:message code="sys004f.pg.name"/></th>
						<%-- 順序代碼 --%>
						<th width="10%"><spring:message code="sys004f.order.no"/></th>
						<%-- 生效日期 --%>
						<th width="10%"><spring:message code="eff.date.label"/></th>
						<%-- 失效日期 --%>
						<th width="10%"><spring:message code="end.date.label"/></th>
						<%-- 修改人員 --%>
						<th width="10%"><spring:message code="userstamp.label"/></th>
						<%-- 修改日期 --%>
						<th>
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
							<td colspan='10'><spring:message code='no.result.message'/></td>
						</tr>
					</c:if>
					<c:if test="${not empty list}">
						<c:forEach var="item" items="${list}" varStatus="status">
							<tr class="${status.count % 2 == 0? 'rowS' : 'rowC' }">
								<td class="alignC">
									<button onclick="javascript:doUpdate(this,'${item.pgId}','<fmt:formatDate value="${item.effDate}" type="Both" pattern="yyyy/MM/dd"/>');"><img src="${ctx}/images/UPDATE.png" title="<spring:message code="modify.label"/>" class="imgBox"/></button>
									<button onclick="javascript:doDelete(this,'${item.pgId}','<fmt:formatDate value="${item.effDate}" type="Both" pattern="yyyy/MM/dd"/>','<fmt:formatDate value="${item.endDate}" type="Both" pattern="yyyy/MM/dd"/>');"><img src="${ctx}/images/DELETE.png" title="<spring:message code="delete.label"/>" class="imgBox"/></button>
									<button onclick="javascript:doDetail(this,'${item.pgId}','<fmt:formatDate value="${item.effDate}" type="Both" pattern="yyyy/MM/dd"/>');"><img src="${ctx}/images/detail.png" title="<spring:message code="detail.search.label"/>" class="imgBox"/></button>
									<input type="hidden" id="crDate_${status.count}" value="<fmt:formatDate value="${item.crDate}" type="Both" pattern="yyyy/MM/dd"/>" />
								</td>
								<%-- 模組代碼 --%>
								<td><input class="texboxtNF" value="${item.pgProjId}" readonly></td>
								<%-- 程式代碼 --%>
								<td><input class="texboxtNF" value="${item.pgId}" readonly></td>
								<%-- 程式名稱 --%>
								<td><input class="texboxtNF" value="${item.pgName}" readonly></td>
								<%-- 順序代碼 --%>
								<td><input class="texboxtNF alignR" value="${item.seqNo}" readonly></td>
								<%-- 生效日期 --%>
								<td><input class="texboxtNF alignC" value="<fmt:formatDate value='${item.effDate}' type='Both' pattern='yyyy/MM/dd'/>" /></td>
								<%-- 失效日期 --%>
								<td><input class="texboxtNF alignC" value="<fmt:formatDate value='${item.endDate}' type='Both' pattern='yyyy/MM/dd'/>" /></td>
								<%-- 修改人員 --%>
								<td><input class="texboxtNF" value="${item.userstamp}" readonly></td>
								<%-- 修改日期 --%>
								<td><input class="texboxtNF alignC" value="<fmt:formatDate value='${item.datestamp}' type='Both' pattern='yyyy/MM/dd'/>" /></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	<form id="deleteForm" method="post" action="${ctx}/SYS004F/delete.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="deletePgId" name="pgId"/>
		<input type="hidden" id="deleteEffDate" name="effDate"/>
	</form>
	<form id="clearForm" method="post" action="${ctx}/SYS004F/index.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	</form>
	<form id="insertForm" method="post" action="${ctx}/SYS004F/insert.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	</form>
	<form id="updateForm" method="post" action="${ctx}/SYS004F/update.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="updatePgId" name="pgId"/>
		<input type="hidden" id="updateEffDate" name="effDate"/>
	</form>
	<form id="detailForm" method="post" action="${ctx}/SYS004F/detail.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="detailPgId" name="pgId"/>
	</form>
	<form id="pageForm" method="post" action="${ctx}/SYS004F/query.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	</form>
</body>
</html>