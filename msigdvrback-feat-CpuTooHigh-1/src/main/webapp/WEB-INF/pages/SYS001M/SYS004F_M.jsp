<!-- 
	程式維護畫面
	version 1.0 
	author Olson
	since 2012/11/19
 -->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<script>
	$(document).ready(function(){
	    <%-- 綁定驗正 --%>
	    bindValidation("modForm");
		//自動轉pgProjId大寫
		autoUpperCase("pgId");
		<%-- 根據不同 action 顯示不同字串 --%>
		var actionType = $("#actionType").val();
		var msg = "";
		var readOnlyList;
		var excludeList;
		
		if ( actionType == "insert") {
			bindCalendar("effDate");
			bindCalendar("endDate");
			newEditable();
			msg = '<spring:message code ="insert.label" />';
			readOnlyList = ['crUser','crDate'];
			excludeList = ["seqNo"];
		}else if (actionType == "update") {
			bindCalendar("endDate");
			updAndDisEditable();
			readOnlyList = ["pgProjId","pgId","effDate","userstamp","datestamp"];
			excludeList = ["seqNo"];
			msg = "<spring:message code ='edit.label' />";
		}else if (actionType == "detail") {
			detail_updAndDisEditable();
			readOnlyList = ["pgProjId","modName","parentModId","effDate","endDate","seqNo","userStamp","dateStamp","crUser","crDate"];
			excludeList = [];
			msg = "<spring:message code ='detail.label' />";
		}
		
		//查詢條件區塊套用CSS
		bindFormCSS("modForm", actionType, readOnlyList, excludeList);
	});
	
	<%-- insert or update --%>
	function doConfirm() {
		var valid = $("#modForm").validationEngine('validate');
		if (valid == true) {
			var c = confirm("<spring:message code='confirm.update.message'/>");
			if (!c) {
				return false;
			}
			var action = $("#actionType").val();
			var actionUrl = "";
			if (action=='insert') {
				actionUrl = "${ctx}/SYS004F/insertData.html";
			}
			else if (action=='update') {
				actionUrl = "${ctx}/SYS004F/updateData.html";
			}
			
			var param = $("#modForm").serialize();
			doCwAjax(actionUrl, param, ajaxOkDo);
		}
		else {
			$("#modForm").validationEngine();
		}
	}
	
	function ajaxOkDo(data) {
		if (data.result == "${ajaxOk}") {
			// 回上一頁, 並保留原本的查詢條件
			doShowQuery();
		}
	}
	
	function newEditable(){
		$("#seqNo").removeClass("inputN").addClass("inputY");
	}
	
	function updAndDisEditable(){
		$("#seqNo").removeClass("inputN").addClass("inputY");
		
		var pgProjId = "${command.pgProjId}";
		$("#pgProjId").val(pgProjId);
	}
	
	function detail_updAndDisEditable(){		
		var pgProjId = "${command.pgProjId}";
		$("#pgProjId").val(pgProjId);
	}
	function doRevert() {
		$("#updateForm").submit();
	}
	
	function doShowIndex() {
		$("#indexForm").submit();
	}
	
	function doShowQuery() {
		if(${queryForm.queryClicked}){
			$("#queryForm").submit();
		}else{
			doShowIndex();
		}
	}
	
	function doClear() {
		$("#insertForm").submit();
	}
	
	function ajaxOkDo(data) {
		if (data.result == "${ajaxOk}") {
			// 回上一頁, 並保留原本的查詢條件
			doShowQuery();
		}
	}
	
	
	</script>
</head>
<body>
<form id="queryForm" action="${ctx}/SYS004F/query.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<%-- 保留空白給上一頁的功能使用 --%>
	<%-- 設定頁次_分頁下拉選單及圖示用 --%>
	<input type="hidden" id="pages" name="pages" value="${queryForm.pages}"/>
</form>	
<form id="indexForm" action="${ctx}/SYS004F/index.html">
<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
</form>
<form id="insertForm" method="post" action="${ctx}/SYS004F/insert.html">
<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
</form>
<form id="updateForm" method="post" action="${ctx}/SYS004F/update.html">
<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" id="updatePgId" name="pgId" value="${command.pgId}"/>
	<input type="hidden" id="updateEffDate" name="effDate" value="${command.effDate}"/>
</form>
<div class="mainDiv">
<br/><br/><br/>
	<form id="modForm" name="modForm" method="post">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="actionType" value="${command.action}" />
		<table id="table1" class="modTableMaster" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2"  cellspacing="0">
			<tr>
				<!-- 模組代碼 -->
				<th><span class="notNullCol">*</span><spring:message code="sys004f.mod.id"/>：</th>
				<td>
					<select id="pgProjId" name="pgProjId" class="validate[required]">
						<option value=""><spring:message code="select.options.0.label"/></option>
						<c:forEach var="modList" items="${model.modList}" varStatus="count">
							<option value="${modList.pgProjId}">${modList.pgProjId} - ${modList.pgProjDscr}</option>
						</c:forEach>
					</select>
				</td>
				<!-- 程式代碼 -->
				<th><span class="notNullCol">*</span><spring:message code="sys004f.pg.id"/>：</th>
				<td>
					<input type="text" class="validate[required]" id="pgId" name="pgId" value="${command.pgId}" maxlength="8" />
				</td>
			</tr>
			<tr>
				<!-- 程式名稱 -->
				<th><spring:message code="sys004f.pg.name"/>：</th>
				<td>
					<input type="text" id="pgName" name="pgName" value="${command.pgName}" maxlength="30" />
				</td>
				<!-- 順序代碼 -->
				<th><span class="notNullCol">*</span><spring:message code="sys004f.order.no"/>：</th>
				<td >
					<input type="text" class="inputN validate[required,custom[onlyNumberSp]]" id="seqNo" name="seqNo" maxlength="2" value="${command.seqNo}"/>
				</td>
			</tr>
			<tr>
				<%-- 生效日期 --%>	
				<th><span class="notNullCol">*</span><spring:message code="eff.date.label"/>：</th>
				<td><input type="text" class="validate[required,funcCall[cwDate]]" id="effDate" name="effDate" maxlength="10" value="${command.effDate}" /></td>
				<%-- 失效日期 --%>
				<th><spring:message code="end.date.label"/>：</th>
				<td><input type="text" class="validate[funcCall[cwFuture[effDate]]]" id="endDate" name="endDate" maxlength="10" value="${command.endDate}" /></td>
			</tr>
			<tr>
				<c:choose>
					<%-- 新增顯示 --%>
					<c:when test="${command.action eq 'insert'}">
						<%-- 建立人員 --%>
						<th><spring:message code="cr.user.label"/>：</th>
						<td>
							<input type="text" class="inputN" id="crUser" name="crUser" value="${command.crUser}" readonly/>
							<input type="hidden" id="userstamp" name="userstamp" value="${command.userstamp}"/>
						</td>
						<%-- 建立日期 --%>
						<th><spring:message code="cr.date.label"/>：</th>
						<td>
							<fmt:formatDate value='${today}' type='Both' pattern='yyyy/MM/dd HH:mm:ss'/>
						</td>
					</c:when>
					<%-- 其他顯示 --%>
					<c:otherwise>
						<%-- 修改人員 --%>
						<th><spring:message code="userstamp.label"/>：</th>
						<td><input type="text" id="userstamp" value="${command.userstamp}" readonly/></td>
						<%-- 修改日期 --%>
						<th><spring:message code="datestamp.label"/>：</th>
						<td><input type="text" id="datestamp" value="<fmt:formatDate value='${command.datestamp}' type='Both' pattern='yyyy/MM/dd HH:mm:ss'/>" readonly/></td>
					</c:otherwise>
				</c:choose>
			</tr>
		</table>
	</form>
</div>
	<%-- 按鈕區域 --%>
	<div class="buttonDiv">
		<c:if test="${command.action ne 'detail'}">
			<input type="button" value="<spring:message code="confirm.label"/>"  class="btn" onclick="doConfirm();" />
		</c:if>
		<c:if test="${command.action eq 'update'}">
			<input type="button" value="<spring:message code="revert.label"/>"  class="btn" onclick="doRevert();" />
		</c:if>
		<c:if test="${command.action eq 'insert'}">
			<input type="button" value="<spring:message code="clear.label"/>"  class="btn" onclick="doClear();" />
		</c:if>
		<c:if test="${command.action eq 'detail'}">
		<%-- show nothing --%>
		</c:if>
		<c:if test="${command.action eq 'insert' }">
			<input type="button" value="<spring:message code="page.previous.label"/>" class="btn" onclick="doShowIndex();"/>
		</c:if>
		<c:if test="${command.action eq 'update' or command.action eq 'detail'}">
			<input type="button" value="<spring:message code="page.previous.label"/>" class="btn" onclick="doShowQuery();"/>
		</c:if>
	</div>
</body>
</html>