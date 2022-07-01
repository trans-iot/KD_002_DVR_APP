<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<title><spring:message code ="sys001f.title" /></title>
	<script>
	$(document).ready(function() {
		bindFormCSS("queryForm", "query");
		$('#sysDept').val('${queryForm.sysDept}');
	});
		function doSearch() {
			$("#orderByClause").val("sys_user_id");
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
		
		function doUpdate(btn, userId) {
    		btn.disabled;
    		$("#updateSeq").val(userId);
    		$("#updateForm").submit();
    		btn.enabled;
    	}
		
		function doDelete(btn, userId) {
    		btn.disabled;
    		var ans = confirm('<spring:message code="delete.confirm.message" />');
    		if (ans) {
    			$("#deleteSeq").val(userId);
    			var actionUrl = $("#deleteForm").attr("action"); 
    			var param = $("#deleteForm").serialize();
    			doCwAjax(actionUrl, param, ajaxDeleteDo);
    		}
    		btn.enabled;
    	}
		
		function doDetail(btn,userId) {
    		btn.disabled;
    		$("#detailSeq").val(userId);
    		$("#detailForm").submit();
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
	</script>
</head>
<body>
	<div class="mainDiv">
	<br/><br/><br/>
		<form id="queryForm" name="queryForm" method="post" action="${ctx}/SYS001F/query.html">
			<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
			<%-- 排序欄位 --%>
			<input type="hidden" id="orderByClause" name="orderByClause" value="${queryForm.orderByClause}"/>
			<%-- 頁次 --%>
			<input type="hidden" id="pages" name="pages" value="${queryForm.pages}"/>
			<%-- 每頁筆數 --%>
			<input type="hidden" id="perPageNum" name="perPageNum" value="${queryForm.perPageNum}" >
			
			<table class="queryTable" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2" cellspacing="0">
				<tr>
					<%-- 使用者帳號 --%>
					<th style="width:10%;text-align:center;"><spring:message code="sys001f.userId"/>：<input type="text" class="inputY" id="userId" name="sysuserId" maxlength="20" value="${queryForm.sysuserId}"/></th>
	
					<%-- 使用者名稱 --%>
					<th style="width:10%;text-align:center;"><spring:message code="sys001f.userName"/>：<input type="text" class="inputY" id="userName" name="sysUserName" maxlength="20" value="${queryForm.sysUserName}"/></th>
	
					<%-- 部門 --%>
					<th style="width:10%;text-align:center;"><spring:message code="sys001f.deptDscr"/>：
						<select id="sysDept" name="sysDept">
							<option value="" label="<spring:message code='select.options.0.label' />">
							<c:forEach items="${deptList}" var="item">
								<option value="${item.key}" label="${item.value}">
							</c:forEach>
						</select>
					</th>
	
				</tr>
			</table>
		</form>
		
		<%-- 按鈕區域 --%>
		<div class="buttonDiv">
			<input type="button" value="<spring:message code="searchBtn.label"/>" class="btn" onclick="javascript:doSearch();" />
			<input type="button" value="<spring:message code="clear.label"/>" class="btn" onclick="javascript:doClear();" />
		</div>
		
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
						<th width="8%"><spring:message code="edit.label"/></th>
						<%-- 使用者帳號 --%>
						<th><spring:message code="sys001f.userId"/></th>
						<%-- 使用者名稱 --%>
						<th><spring:message code="sys001f.userName"/></th>
						<%-- 部門 --%>
						<th><spring:message code="sys001f.deptDscr"/></th>
						<%-- EMail --%>
						<th><spring:message code="sys001f.email"/></th>
						<%-- 狀態 --%>
						<th><spring:message code="sys001f.status"/></th>
						<%-- 修改人員 --%>
						<th width="10%"><spring:message code="userstamp.label"/></th>
						<%-- 修改日期 --%>
						<th width="10%">
							<spring:message code="datestamp.label"/>
							<%-- 依照日期排序 --%>
							<button onClick="<c:if test="${not empty list}">orderBy(this);</c:if>" class="imgBox">
						  		<img src="${ctx}/images/desc.png" title="<spring:message code='sort.label'/>" border="0"/>
						  	</button>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${list ne null and fn:length(list) eq 0}">
						<tr class="rowC alignC">
							<td colspan='8'><spring:message code='no.result.message'/></td>
						</tr>
					</c:if>
					<c:if test="${not empty list}">
						<c:forEach var="item" items="${list}" varStatus="status">
							<tr class="${status.count % 2 == 0? 'rowS' : 'rowC' }">
								<td class="alignC">
									<button onclick="javascript:doUpdate(this,'${item.sysUserId}');"><img src="${ctx}/images/UPDATE.png" title="<spring:message code="modify.label"/>" class="imgBox"/></button>
									<button onclick="javascript:doDelete(this,'${item.sysUserId}');"><img src="${ctx}/images/DELETE.png" title="<spring:message code="delete.label"/>" class="imgBox"/></button>
									<button onclick="javascript:doDetail(this,'${item.sysUserId}');"><img src="${ctx}/images/detail.png" title="<spring:message code="detail.search.label"/>" class="imgBox"/></button>
									<input type="hidden" id="crDate_${status.count}" value="<fmt:formatDate value="${item.crDate}" type="Both" pattern="yyyy/MM/dd"/>" />
								</td>
								<%-- 使用者帳號 --%>
								<td><input class="texboxtNF" value="${item.sysUserId}" maxlength="20" readonly/></td>
								<%-- 中文姓名 --%>
								<td><input class="texboxtNF" value="${item.userName}" maxlength="30" readonly/></td>
								<%-- 部門 --%>
								<td><input class="textboxNF" value="${item.deptDscr}"  maxlength="120" readonly/></td>
								<%-- EMail --%>
								<td><input class="texboxtNF" value="${item.email}" maxlength="10" readonly></td>
								<%-- 狀態 --%>
								<td><input class="texboxtNF" value="${item.statusDscr}"  maxlength="120" readonly></td>
								<%-- 修改人員 --%>
								<td><input class="texboxtNF" value="${item.userstamp}"  maxlength="20" readonly></td>
								<%-- 修改日期 --%>
								<td><input class="texboxtNF alignC" value="<fmt:formatDate value='${item.datestamp}' type='Both' pattern='yyyy/MM/dd'/>" /></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
		</div>
	</div>
	
	<form id="deleteForm" method="post" action="${ctx}/SYS001F/delete.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="deleteSeq" name="userId"/>
	</form>
	<form id="clearForm" method="post" action="${ctx}/SYS001F/index.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	</form>
	<form id="insertForm" method="post" action="${ctx}/SYS001F/insert.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	</form>
	<form id="updateForm" method="post" action="${ctx}/SYS001F/update.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="updateSeq" name="userId"/>
	</form>
	<form id="detailForm" method="post" action="${ctx}/SYS001F/detail.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="detailSeq" name="userId"/>
	</form>
	<form id="pageForm" method="post" action="${ctx}/SYS001F/query.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	</form>
</body>
</html>