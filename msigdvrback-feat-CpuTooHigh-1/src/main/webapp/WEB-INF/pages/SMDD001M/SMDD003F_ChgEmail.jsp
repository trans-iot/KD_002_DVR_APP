<!-- 
    title  會員資料維護管理
    author Leo
    since 2016/04/15
-->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="pgId" value="SMDD003F" />
<html>
<head>
	<script>
		function doShowQuery() {
			$('#updatetuserId').val('<c:out value="${userId}" />');
			$("#updateForm").submit();
		}
		
		$(document).ready(function(){

			<%-- 根據不同 action 顯示不同字串 --%>
			var actionType = $("#actionType").val();
			var msg = "";
			var readOnlyList;
			var excludeList;
			
			var tab1_Options = new Object();
			tab1_Options.actType = actionType;
			<%-- detail區塊按鈕加入事件 --%>
			var tab1_index = 0;
			var tab1_delFunc = "";
			tab1_Options.setCSS = true; //自行套CSS:false

			readOnlyList = ["userId"];
			
			<%-- 查詢條件區塊套用CSS--%>
			bindFormCSS("smdd003fModForm", actionType, readOnlyList, excludeList);
			
			bindValidation("smdd003fModForm");
		});
		
		<%-- insert or update --%>
		function doConfirm() {
			var valid = $("#smdd003fModForm").validationEngine('validate');
			var roleIdFlag = false;
			if (valid==true) { //&&re==true
				var c = confirm("<spring:message code='confirm.update.message'/>");
				if (!c) {
					return false;
				}
				var actionUrl = "${ctx}/${pgId}/doUpdateEmail.html";
				
				var param = $("#smdd003fModForm").serialize();
				
				doCwAjax(actionUrl, param, ajaxOkDo);
			}
			else {
				$("#smdd003fModForm").validationEngine();
			}
		}
		
		<%-- 回上一頁, 並保留原本的查詢條件 --%>
		function ajaxOkDo(data) {
			if (data.result == "${ajaxOk}") {
				doShowQuery();
			} 
		}

	</script>
</head>
<body>
<br/><br/><br/>
<form id="updateForm" method="post" action="${ctx}/SMDD003F/update.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    <input type="hidden" id="updatetuserId" name="updatetuserId"/>
</form>
<div class="mainDiv">
	<form id="smdd003fModForm" name="smdd003fModForm" method="post">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<table id="table1" class="modTableMaster" border="1" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2"  cellspacing="0">
			<tr>
				<!-- 會員編號 -->
				<th><spring:message code="smdd003f.customer.userid"/>：</th>
				<td>
					<input type="text" id="userId" 
							name="userId" value="<c:out value='${userId}' />" readonly/>
				</td>
			</tr>
			<tr>
				<!-- *電子信箱 -->
				<th><span class="notNullCol">*</span><spring:message code="smdd003f.customer.email"/>：</th>
				<td>
					<input type="text" id="email" name="email" class="validate[required,custom[email]]" style="text-transform: lowercase"
							maxlength="100" value="${email}" />
				</td>
			</tr>
		</table>
	</form>
	
	<%-- 按鈕區域 --%>
	<div class="buttonDiv">
		<input type="button" value="<spring:message code="confirm.label"/>"  class="btn" onclick="doConfirm();" />
		<input type="button" value="<spring:message code="page.previous.label"/>" class="btn" onclick="doShowQuery();"/>
	</div>
</div>
</body>
</html>