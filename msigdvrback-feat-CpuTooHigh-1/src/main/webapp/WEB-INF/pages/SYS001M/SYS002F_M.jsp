<!-- 
	系統維護畫面
	version 1.0 
	author Olson
	since 2012/11/15
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
		autoUpperCase("pgProjId");
		<%-- 根據不同 action 顯示不同字串 --%>
		var actionType = $("#actionType").val();
		var msg = "";
		var readOnlyList;
		var excludeList;
		
		if ( actionType == "insert") {
			msg = '<spring:message code ="insert.label" />';
			readOnlyList = ['crUser','crDate'];
			excludeList = [];
		}else if (actionType == "update") {
			readOnlyList = ["pgProjId","userstamp","datestamp"];
			excludeList = [];
			displiable();
			msg = "<spring:message code ='edit.label' />";
		}else if (actionType == "detail") {
			displiable();
			readOnlyList = ["pgProjId","pgProjDscr","pPgProjId","seqNo","userStamp","dateStamp","crUser","crDate"];
			excludeList = [];
			msg = "<spring:message code ='detail.label' />";
		}
		
		//查詢條件區塊套用CSS
		bindFormCSS("modForm", actionType, readOnlyList, excludeList);
	});
	
	
	function displiable(){
		var pPgProjId = "${command.pPgProjId}";
		$("#pPgProjId").val(pPgProjId);
	}
	
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
				actionUrl = "${ctx}/SYS002F/insertData.html";
			}
			else if (action=='update') {
				actionUrl = "${ctx}/SYS002F/updateData.html";
			}
			
			var param = $("#modForm").serialize();
			doCwAjax(actionUrl, param, ajaxOkDo);
		}
		else {
			$("#modForm").validationEngine();
		}
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
<form id="queryForm" action="${ctx}/SYS002F/query.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<%-- 保留空白給上一頁的功能使用 --%>
	<%-- 設定頁次_分頁下拉選單及圖示用 --%>
	<input type="hidden" id="pages" name="pages" value="${queryForm.pages}"/>
</form>	
<form id="indexForm" action="${ctx}/SYS002F/index.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
</form>
<form id="insertForm" method="post" action="${ctx}/SYS002F/insert.html">
<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
</form>
<form id="updateForm" method="post" action="${ctx}/SYS002F/update.html">
<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" id="updateModid" name="pgProjId" value="${command.pgProjId}"/>
</form>
<div class="mainDiv">
<br/><br/><br/>
	<form id="modForm" name="modForm" method="post">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="actionType" value="${command.action}" />
		<table id="table1" class="modTableMaster" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2"  cellspacing="0">
			<tr>
				<!-- 模組代碼 -->
				<th><span class="notNullCol">*</span><spring:message code="sys002f.proj.pg.id"/>：</th>
				<td>
					<input type="text" class="validate[required]" id="pgProjId" name="pgProjId" value="${command.pgProjId}" maxlength="10" />
				</td>
				<!-- 模組名稱 -->
				<th><spring:message code="sys002f.proj.pg.name"/>：</th>
				<td>
					<input type="text" id="pgProjDscr" name="pgProjDscr" value="${command.pgProjDscr}" maxlength="30" />
				</td>
			</tr>
			<tr>
				<!-- 上層模組代碼 -->
				<th><spring:message code="sys002f.pproj.pg.id"/>：</th>
				<td>
					<select id="pPgProjId" name="pPgProjId" >
						<option value=""><spring:message code="select.options.0.label"/></option>
                        <c:forEach var="item" items="${model.parentModList}" varStatus="status">
                        	<option value="${item.pgProjId}">${item.pgProjId} - ${item.pgProjDscr}</option>
                        </c:forEach>
                    </select>
				</td>
				<!-- 順序代碼 -->
				<th><span class="notNullCol">*</span><spring:message code="sys002f.order.no"/>：</th>
				<td>
					<input type="text" class="inputN validate[required,custom[onlyNumberSp]]" id="seqNo" name="seqNo" maxlength="2" value="${command.seqNo}"/>
				</td>
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