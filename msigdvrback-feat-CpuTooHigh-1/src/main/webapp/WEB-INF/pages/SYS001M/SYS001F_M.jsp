<!-- 
	使用者維護畫面
	version 1.0 
	author Olson
	since 2012/11/9
 -->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<script>
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
		
		$(document).ready(function(){
			
			bindValidation("sys001fModForm");
			var formArray = new Array(
					{Name:"sys001fModUserRoleMapDetailForm"}	
			);
			bindTabForm(formArray);
			
			<%-- 根據不同 action 顯示不同字串 --%>
			var actionType = $("#actionType").val();
			var msg = "";
			var readOnlyList;
			var excludeList;
			if ( actionType == "insert") {
				msg = '<spring:message code ="insert.label" />';
				<%-- 帳號狀態，新增時預設點選第一欄radio --%>
				$("input[name='sys001fModForm.status']").get(2).checked=true;
				newEditable();
				readOnlyList = ["crUser","crDate"];
			}else if (actionType == "update") {
				updateEditable();
				readOnlyList = ["userId","userstamp","datestamp"];
			}else if (actionType == "detail") {
				displiable();
				readOnlyList = ["userId","userName","email","userStamp","dateStamp","crUser","crDate"];
				excludeList = ["status"];
				msg = "<spring:message code ='detail.label' />";
			}
			
			<%-- 查詢條件區塊套用CSS--%>
			bindFormCSS("sys001fModForm", actionType, readOnlyList, excludeList);
			
			var tab2_Options = new Object();
			tab2_Options.addButton = 'add2'; //新增一筆明細 buttonID
			tab2_Options.actType = actionType;
			
			<%-- detail區塊按鈕加入事件 --%>
			var tab2_index = 1;
			var tab2_delFunc = "";
			tab2_Options.setCSS = true; //自行套CSS:false
			if(actionType == "update"){
				
				
				<%-- 按鈕二 --%>
				tab2_index = "${fn:length(command.sys001fModUserRoleMapDetailForm.userRoleMapList)}";
				tab2_delFunc = "delUserRoleMapDtl"; //自訂的刪除function name	
				tab2_Options.index = tab2_index;
				tab2_Options.delFunc=tab2_delFunc;
				
				$("#userRoleMapDisplayTab").bindDetail(tab2_Options);
				
				<%-- 因roleId為primary key 故修改時不可編輯，在bind完detail後調整CSS --%>
				var _tbodyB = $('#userRoleMapDisplayBody');
				$(":input[name$='roleId']", _tbodyB).each(function(){
					$(this).addClass('inputN_F').removeClass('inputY_F')
						.attr('readonly','readonly');
				});
			}else{
				$("#userRoleMapDisplayTab").bindDetail(tab2_Options);
			}
		});
		
		
		function delUserRoleMapDtl(idx){
			
			var indexFlag = "${fn:length(command.sys001fModUserRoleMapDetailForm.userRoleMapList)}";
			if(idx+1>indexFlag){return true;}
			
			var roleId = "roleId_"+idx;
			var roleIdVal = $('#'+roleId).val();
			$("#delRoleId").val(roleIdVal);
			
			var param = $("#deleteRoleForm").serialize();
			var actionUrl ='';
			actionUrl = "${ctx}/SYS001F/delUserRoleMapDtl.html";
			return doCwAjaxSync(actionUrl, param);
		}
		
		function newEditable(){
			<%-- 將狀態欄位disable --%>
			$('input[name="sys001fModForm.status"]').each(function(){
				$(this).attr('disabled','disabled');
			});
		}
		
		function updateEditable(){
			<%-- 勾選狀態欄位 --%>
			$('input[name="sys001fModForm.status"]').each(function(){
				$(this).attr('disabled','disabled');
				if($(this).val()=="${command.sys001fModForm.status}"){
					$(this).attr('checked','checked');
					$(this).removeAttr('disabled');
				}
			});
			<%-- 營業點 --%>
	    	var deptNo = '${command.sys001fModForm.deptNo}';
	    	if (deptNo != '') {
	    		$('#deptNo').val(deptNo);
	    	}else{
	    		$('#deptNo').val('');
	    	}
			<%-- 部門 --%>
			$("#deptNo").val("${command.sys001fModForm.deptNo}");
		}
		
		function displiable(){
			//將狀態欄位disable
			$('input[name="sys001fModForm.status"]').each(function(){
				$(this).attr('disabled','disabled');
				if($(this).val()=="${command.sys001fModForm.status}"){
					$(this).attr('checked','checked');
					$(this).removeAttr('disabled');
				}
			});
			
			<%-- 下拉選單塞值--%>
			<%-- 營業點 --%>
	    	var deptNo = '${command.sys001fModForm.deptNo}';
	    	if (deptNo != '') {
	    		$('#deptNo').val(deptNo);
	    	}else{
	    		$('#deptNo').val('');
	    	}
			<%-- 部門 --%>
			$("#deptNo").val("${command.sys001fModForm.deptNo}");
		}
		
		<%-- insert or update --%>
		function doConfirm() {
			var valid = $("#sys001fModForm").validationEngine('validate');
			var i = $(".tabsDiv").attr("selindex");
			var re = validateTab(i);
			
			if (valid == true&&re==true) {
				var c = confirm("<spring:message code='confirm.update.message'/>");
				if (!c) {
					return false;
				}
				var action = $("#actionType").val();
				var actionUrl = "";
				if (action=='insert') {
					actionUrl = "${ctx}/SYS001F/insertData.html";
				}
				else if (action=='update') {
					actionUrl = "${ctx}/SYS001F/updateData.html";
				}
				
				var f1 = $("#sys001fModForm").serialize();
				var f3 = $("#sys001fModUserRoleMapDetailForm").serialize();
				var param = f1 + "&" + f3;
				doCwAjax(actionUrl, param, ajaxOkDo);
			}
			else {
				$("#modForm").validationEngine();
			}
		}
		<%-- 回上一頁, 並保留原本的查詢條件 --%>
		function ajaxOkDo(data) {
			if (data.result == "${ajaxOk}") {
				doShowQuery();
			}
		}
		
		function doResetPw(){
			var param = $("#resetForm").serialize();
			var actionUrl = "${ctx}/SYS001F/resetPw.html";
			doCwAjax(actionUrl, param, ajaxOkDo);
		}

		<%-- 帳號停權 --%>
		function doSuspend(){
			var param = $("#suspendForm").serialize();
			var actionUrl = "${ctx}/SYS001F/suspend.html";
			doCwAjax(actionUrl, param, ajaxOkDo);
		}
		
	</script>
</head>
<body>
<form id="queryForm" action="${ctx}/SYS001F/query.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<%-- 保留空白給上一頁的功能使用 --%>
	<%-- 設定頁次_分頁下拉選單及圖示用 --%>
	<input type="hidden" id="pages" name="pages" value="${queryForm.pages}"/>
</form>	
<form id="indexForm" action="${ctx}/SYS001F/index.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
</form>
<form id="insertForm" method="post" action="${ctx}/SYS001F/insert.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
</form>
<form id="updateForm" method="post" action="${ctx}/SYS001F/update.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" id="updateSeq" name="userId" value="${command.sys001fModForm.userId}"/>
</form>
<form id="resetForm" method="post" action="${ctx}/SYS001F/resetPw.html">
	<input type="hidden" id="resetSeq" name="userId" value="${command.sys001fModForm.userId}"/>
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
</form>
<form id="suspendForm" method="post" action="${ctx}/SYS001F/suspend.html">
	<input type="hidden" id="suspendSeq" name="userId" value="${command.sys001fModForm.userId}"/>
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
</form>
<form id="deleteMapForm" method="post">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" id="delUserId" name="userId" value="${command.sys001fModForm.userId}"/>
	<input type="hidden" id="delApId" name="apId">
	<input type="hidden" id="delsysUserId" name="sysUserId">
</form>
<form id="deleteRoleForm" method="post">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" id="delUserId" name="sysUserId" value="${command.sys001fModForm.userId}"/>
	<input type="hidden" id="delRoleId" name="roleId">
</form>
<div class="mainDiv">
<br/><br/><br/>
	<form id="sys001fModForm" name="sys001fModForm" method="post">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="actionType" value="${command.sys001fModForm.action}" />
		<input type="hidden" name="sys001fModForm.resetPawdIndic" value="${command.sys001fModForm.resetPawdIndic}" />
		<table id="table1" class="modTableMaster" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2"  cellspacing="0">
			<tr>
				<!-- 登入帳號 -->
				<th><span class="notNullCol">*</span><spring:message code="sys001f.userId"/>：</th>
				<td>
					<input type="text" class="validate[required]" id="userId" 
							name="sys001fModForm.userId" value="${command.sys001fModForm.userId}" maxlength="20" />
				</td>
				<!-- 中文姓名 -->
				<th><span class="notNullCol">*</span><spring:message code="sys001f.userName"/>：</th>
				<td>
					<input type="text" class="validate[required,funcCall[cwMax[30]]]" id="userName" 
							name="sys001fModForm.userName" value="${command.sys001fModForm.userName}" maxlength="30" />
				</td>
			</tr>
			<tr>
				<!-- 部門 -->
				<th><span class="notNullCol">*</span><spring:message code="sys001f.dept.no.dscr"/>：</th>
				<td colspan="3">
					<select id="deptNo" name="sys001fModForm.deptNo" class="validate[required]">
                        <option value=""><spring:message code="select.options.0.label" /></option>
                        <%-- deptNoList --%>
                        <c:forEach var="item" items="${command.sys001fModForm.deptNoList}" varStatus="status">
                        <option value="${item.key}">${item.value}</option>
                        </c:forEach>
                    </select>
				</td>
			</tr>
			<tr>
				<!-- 狀態 -->
				<th><spring:message code="sys001f.status"/>：</th>
				<td colspan="3">
					<c:forEach var="statuslist" items="${model.statuslist}" varStatus="count">
						<input type="radio" id="status" name="sys001fModForm.status" value="${statuslist.lookupCde}">
						<span>${statuslist.dscr}</span>
					</c:forEach>
				</td>
			</tr>
			<tr>
				<!-- 電郵 -->
				<th><spring:message code="sys001f.email"/>：</th>
				<td colspan="3">
					<input type="text" class="validate[custom[email],funcCall[cwMax[100]]]" id="email" 
							name="sys001fModForm.email" value="${command.sys001fModForm.email}" maxlength="100" />
				</td>
			</tr>
			<tr>
				<c:choose>
					<%-- 新增顯示 --%>
					<c:when test="${command.sys001fModForm.action eq 'insert'}">
						<%-- 建立人員 --%>
						<th><spring:message code="cr.user.label"/>：</th>
						<td>
							<input type="text" class="inputN" id="crUser" name="crUser" value="${command.sys001fModForm.crUser}" readonly/>
							<input type="hidden" id="userstamp" name="userstamp" value="${command.sys001fModForm.userstamp}"/>
						</td>
						<%-- 建立日期 --%>
						<th><spring:message code="cr.date.label"/>：</th>
						<td>
							<fmt:formatDate value='${today}' type='Both' pattern='yyyy/MM/dd HH:mm:ss'/>
							<input type="hidden" id="crDate" name="crDate" value="<fmt:formatDate value='${today}' type='Both' pattern='yyyy/MM/dd HH:mm:ss'/>"/>
						</td>
					</c:when>
					<%-- 其他顯示 --%>
					<c:otherwise>
						<%-- 修改人員 --%>
						<th><spring:message code="userstamp.label"/>：</th>
						<td><input type="text" id="userstamp" value="${command.sys001fModForm.userstamp}" readonly/></td>
						<%-- 修改日期 --%>
						<th><spring:message code="datestamp.label"/>：</th>
						<td><input type="text" id="datestamp" value="<fmt:formatDate value='${command.sys001fModForm.datestamp}' type='Both' pattern='yyyy/MM/dd HH:mm:ss'/>" readonly/></td>
					</c:otherwise>
				</c:choose>
			</tr>
		</table>
	</form>
	<%-- 明細區塊 --%>
	<div class="tabsDiv">
		<%-- 頁籤 --%>
		<ul id="tabDiv" class="tabs">
			<li><a href="#userRoleMapDisplay"><spring:message code="sys001f.user.role.map"/></a></li>
		</ul>
		
		<div class="tabs-container">
			<%-- 使用者角色維護頁籤 --%>
			<div id="userRoleMapDisplay">
				<form id="sys001fModUserRoleMapDetailForm">
					<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
					<table id="userRoleMapDisplayTab" class="modTableDetail">
						<thead>
							<tr>
								<td colspan="5" class="alignL">
									<c:if test="${command.sys001fModForm.action ne 'detail'}">										
 										<input id="add2" value="<spring:message code="insert.single.detail.label"/>" class="btn" type="button">
									</c:if>
								</td>
							</tr>
							<tr class="nowrapHeader listHeader">
								<%-- 編輯 --%>
								<th  style="width:60"><spring:message code="edit.label"/></th>
								<%-- 角色代碼 --%>
								<th>*<spring:message code="sys001f.role.id"/></th>
								<c:choose>
								<%-- 新增顯示 --%>
								<c:when test="${command.sys001fModForm.action eq 'insert'}">
									<%-- 建立人員 --%>
									<th style="width:130"><spring:message code="cr.user.label"/></th>
									<%-- 建立日期 --%>
									<th style="width:90"><spring:message code="cr.date.label"/></th>
								</c:when>
								<%-- 其他顯示 --%>
								<c:otherwise>
									<%-- 修改人員 --%>
									<th style="width:130"><spring:message code="userstamp.label"/></th>
									<%-- 修改時間 --%>
									<th style="width:90"><spring:message code="datestamp.label"/></th>
								</c:otherwise>
							</c:choose>
							</tr>
							<!-- 新增時用的框架 -->
							<tr style="display: none;">
								<td class="alignC">
									<span class="pointer"><img src="../images/DELETE.png" title="<spring:message code="remove.label"/>" class="imgBox"/></span>
								</td>
								<td>
									<select name="sys001fModUserRoleMapDetailForm.userRoleMapList[].roleId" class="validate[required] dtlSelect">
										<option value=""><spring:message code="select.options.0.label"/></option>
										<c:forEach var="roleList" items="${model.roleList}" varStatus="count">
											<option value="${roleList.roleId}" >${roleList.roleId}</option>
										</c:forEach>
									</select>
								</td>
								<td>${command.sys001fModForm.crUser}</td>
								<td align="center"><fmt:formatDate value='${today}' type='Both' pattern='yyyy/MM/dd'/></td>
							</tr>
						</thead>
						<tbody  id="userRoleMapDisplayBody">
							<c:if test="${not empty command.sys001fModUserRoleMapDetailForm.userRoleMapList and command.sys001fModForm.action ne 'insert'}">
								<c:forEach var="userRoleMapList" items="${command.sys001fModUserRoleMapDetailForm.userRoleMapList}" varStatus="name">
									<tr class="${name.count % 2 == 0? 'rowS' : 'rowC'}" >
										<td class="alignC">
											<span class="pointer"><img src="../images/DELETE.png" title="<spring:message code="remove.label"/>" class="imgBox"/></span>
										</td>
										<td>
											<input type="text" id="roleId_${name.count - 1}" name="sys001fModUserRoleMapDetailForm.userRoleMapList[${name.count-1}].roleId" class="intputN validate[required]"
												value="${userRoleMapList.roleId}" readonly/>
										</td>
										<td>${userRoleMapList.userstamp}</td>
										<td align="center"><fmt:formatDate value='${userRoleMapList.datestamp}' type='Both' pattern='yyyy/MM/dd'/></td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>	
					</table>
				</form>
			</div>
		</div>
	</div>
	<%-- 按鈕區域 --%>
	<div class="buttonDiv">
		<c:if test="${command.sys001fModForm.action ne 'detail'}">
			<input type="button" value="<spring:message code="confirm.label"/>"  class="btn" onclick="doConfirm();" />
		</c:if>
		<c:if test="${command.sys001fModForm.action eq 'update'}">
			<input type="button" value="<spring:message code="revert.label"/>"  class="btn" onclick="doRevert();" />
			<input type="button" value="<spring:message code="reset.code.label"/>" class="btn" onclick="doResetPw();"/>
			<input type="button" value="<spring:message code="account.suspend.label"/>" class="btn" onclick="doSuspend();"/>
		</c:if>
		<c:if test="${command.sys001fModForm.action eq 'insert'}">
			<input type="button" value="<spring:message code="clear.label"/>"  class="btn" onclick="doClear();" />
		</c:if>
		<c:if test="${command.sys001fModForm.action eq 'detail'}">
		<%-- show nothing --%>
		</c:if>
		<input type="button" value="<spring:message code="page.previous.label"/>" class="btn" onclick="doShowQuery();"/>
	</div>
</div>
</body>
</html>