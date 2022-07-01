<!-- 
    title 最新消息維護
    version 1.0 
    author Bob
    since 2018/10/29
-->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="pgId" value="OMOM004F" />
<html>
<head>
	<script>
		$(document).ready(function(){
			
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
			var pushStatusHidden = $("#pushStatusHidden").val()
			if (actionType == "update") {
				
				bindCalendar("endDate"); <%-- 生日欄位加上日曆 --%>
				if(pushStatusHidden == 'N'){
					bindCalendar("effDate"); <%-- 生日欄位加上日曆 --%>
					readOnlyList = ["userstamp","datestamp","pushTime","pushStatus","seqNo"];
				}else{
					readOnlyList = ["userstamp","datestamp","pushTime","pushStatus","seqNo","msgClass","prePushTime","title","content","csPhone","linkUrl","effDate"];
				}
			 	
			}else if (actionType == "detail") { 
				// 隱藏必key欄位  * 符號
	    		$(".notNullCol").hide();
				readOnlyList = ["userStamp","dateStamp","pushTime","prePushTimeDscr","seqNo","csPhone","linkUrl"];
				$("#prePushTime").hide();
				$("#prePushTimeDscr").prop('type', 'text');
				msg = "<spring:message code ='detail.label' />";
				
				$("#tprodServiceMapDisplayTab").bindDetail(tab1_Options);
				$("#csPhone").removeClass('inputN').addClass("inputN_F");
				$("#linkUrl").removeClass('inputN').addClass("inputN_F");
			}else if(actionType == "insert"){
				bindCalendar("effDate"); <%-- 生日欄位加上日曆 --%>
				bindCalendar("endDate"); <%-- 生日欄位加上日曆 --%>
				readOnlyList = ["userstamp","datestamp","crUser","crDate","pushStatus","pushTime","pushStatusDscr"];
			}
			<%-- 查詢條件區塊套用CSS--%>
			bindFormCSS("omom004fModForm", actionType, readOnlyList, excludeList);
			
			bindValidation("omom004fModForm");
			if (actionType == "update") {
				if(pushStatusHidden == 'N'){
					$("#linkUrl").removeClass("inputY").addClass("inputY_L")
					$("#csPhone").removeClass("inputY").addClass("inputY_L")
				}else{
					$("#csPhone").removeClass('inputN').addClass("inputN_F");
					$("#linkUrl").removeClass('inputN').addClass("inputN_F");
				}
			}
			if (actionType == "detail") { 
				$("#csPhone").removeClass('inputN').addClass("inputN_F");
				$("#linkUrl").removeClass('inputN').addClass("inputN_F");
			}
		})
		
		function IsURL(str_url){
		   var re = /(http|ftp|https):\/\/[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&:/~\+#]*[\w\-\@?^=%&/~\+#])?/;
		   if (re.test(str_url)){
		      return (true);
		   }else{
		      return (false);
		   }
		}
		
		function doRevert() {
			$("#updateForm").submit();
		}
		
		function doShowIndex() {
			$("#indexForm").submit();
		}
		
		function doShowQuery() {
			var click = ${queryForm.queryClicked};
			if(click){
				$("#queryForm").submit();
			}else{
				doShowIndex();
			}
		}
		
		function doClear() {
			$("#insertDataPageForm").submit();
		}
		
		<%-- 回上一頁, 並保留原本的查詢條件 --%>
		function ajaxOkDo(data) {
			if (data.result == "${ajaxOk}") {
				doShowQuery();
			}
		}
		
		<%-- insert or update --%>
		function doConfirm() {
			var errMsg = "";
			var linkUrl = $.trim($('#linkUrl').val())
			if(linkUrl != "" && !IsURL(linkUrl)){
				errMsg += '連結url不符合規範\n'
			}
			var csPhone = $.trim($('#csPhone').val())
			var patrn = /^[0-9]*$/;
			if (csPhone != "" && !patrn.test(csPhone)) {
				errMsg += '客服電話請輸入數字\n'
			}
			var prePushTimeValue = $.trim($('#prePushTime').val())
			var minutes = prePushTimeValue.substr(prePushTimeValue.length - 5);
			var pushStatusHidden = $("#pushStatusHidden").val()
			if(pushStatusHidden == 'N'){
				if(prePushTimeValue != "" && (new Date(prePushTimeValue) == 'Invalid Date') || (minutes == "24:00") || prePushTimeValue.indexOf('-') > -1 || (prePushTimeValue.length < 16 && prePushTimeValue != "")){
					errMsg += "預計推播時間，請輸入正確格式YYYY/MM/DD HH:MM\n"
					if(errMsg != ""){
						alert(errMsg)
						return false
					}
				}
				if(prePushTimeValue != "" && new Date(prePushTimeValue).getMonth()+1 != prePushTimeValue.substring(5, 7)){
					errMsg += "預計推播時間，請輸入正確格式YYYY/MM/DD HH:MM\n"
					if(errMsg != ""){
						alert(errMsg)
						return false
					}
				}
				if(prePushTimeValue != "" && new Date(prePushTimeValue)!= "" && new Date(prePushTimeValue) < new Date()){
					errMsg += "預計推播時間不可小於系統時間\n"
					if(errMsg != ""){
						alert(errMsg)
						return false
					}
				}
				
				
			}
			if(errMsg != ""){
				alert(errMsg)
				return false
			}
			var valid = $("#omom004fModForm").validationEngine('validate');
			if(valid){
				var c = confirm("<spring:message code='confirm.update.message'/>");
				if (!c) {
					return false;
				}
				var action = $("#actionType").val();
				var actionUrl = "";
				if (action=='insert') {
					actionUrl = "${ctx}/${pgId}/insertData.html";
				}
				else if (action=='update') {
					actionUrl = "${ctx}/${pgId}/updateData.html";
				}
				
				var f1 = $("#omom004fModForm").serialize();
				var param = f1;
				
				doCwAjax(actionUrl, param, ajaxOkDo);
			}else {
				$("#omom004fModForm").validationEngine();
			}
		}
	</script>
</head>
<body>
<br/><br/><br/>
<form id="insertDataPageForm" method="post" action="${ctx}/OMOM004F/insertDataPage.html">
<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
</form>
<form id="queryForm" action="${ctx}/${pgId}/query.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<%-- 保留空白給上一頁的功能使用 --%>
	<%-- 設定頁次_分頁下拉選單及圖示用 --%>
	<input type="hidden" id="pages" name="pages" value="${queryForm.pages}"/>
</form>	
<form id="updateForm" method="post" action="${ctx}/${pgId}/update.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" id="updateSeqNo" name="updateSeqNo" value="${command.seqNo}"/>
</form>
<form id="indexForm" action="${ctx}/${pgId}/index.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
</form>
<div class="mainDiv">
	<form id="omom004fModForm" name="omom004fModForm" method="post">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="actionType" value="${command.action}" />
		<table id="table1" class="modTableMaster" border="1" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2"  cellspacing="0">

		<tr>
			<c:if test="${command.action ne 'insert'}">
				<tr>
					<!-- 資料序號 -->
					<th><spring:message code="omom004f.tbNews.seqNo"/>：</th>
					<td colspan="3">
						<input type="text" id="seqNo"name="seqNo" 
									value="${command.seqNo}" maxlength="40" readonly/>
					</td>
				</tr>
			</c:if>
			<!-- 消息類別-->
			<th><span class="notNullCol">*</span><spring:message code="omom004f.tbNews.msgClass"/>：</th>
			<td>
				<select id="msgClass" name="msgClass" class="validate[required] select">
					<option value="" selected><spring:message code="select.options.0.label"/></option>
					<c:forEach items="${lookupMsgclassList}" var="item">
						<option value="${item.lookupCde}"  ${command.msgClass eq item.lookupCde ? 'selected' : ''}>${item.dscr}</option>
					</c:forEach>
				</select>
			</td>
			<!-- 預計推播時間 -->
			<th><span class="notNullCol">*</span><spring:message code="omom004f.tbNews.prePushTime"/>：<br></th>
			<td>
				<input type="text" id="prePushTime"name="prePushTime" 
							value="${command.prePushTime}" maxlength="16" class="validate[required]">
				<c:if test="${command.action ne 'detail'}">
					<br>
					<span style="color:red;display:inline-block">輸入格式: YYYY/MM/DD HH:MM</span>
				</c:if>
				<input type="hidden" id="prePushTimeDscr"
						name="prePushTimeDscr" value="${command.prePushTimeDscr}" readonly/>
			</td>
		</tr>
		<tr>
			<!-- 消息標題 -->
			<th><span class="notNullCol">*</span><spring:message code="omom004f.tbNews.title"/>：<br><spring:message code="omom004f.tbNews.title.subTitle"/></th>
			<td colspan="3">
				<textarea rows="4" cols="50" id="title" maxlength="200" ${command.action eq 'detail' ? 'disabled' : ''}
						name="title"  ${command.action eq 'detail' or (command.pushStatus ne 'N' and command.action != 'insert') ? 'style="color:#FFFFFF"' : ''}  class="validate[required]">${command.title}</textarea>
			</td>
		</tr>
		<tr>
			<!-- 消息內容 -->
			<th><span class="notNullCol">*</span><spring:message code="omom004f.tbNews.content"/>：<br><spring:message code="omom004f.tbNews.content.subTitle"/></th>
			<td colspan="3">
				<textarea rows="4" cols="50" type="text" id="content"  maxlength="2000" ${command.action eq 'detail' ? 'disabled' : ''}
						name="content" ${command.action eq 'detail' or (command.pushStatus ne 'N' and command.action != 'insert') ? 'style="color:#FFFFFF"' : ''}  class="validate[required]">${command.content}</textarea>
			</td>
		</tr>
		<tr>
			<!-- 客服電話 -->
			<th><spring:message code="omom004f.tbNews.csPhone"/>：</th>
			<td colspan="3">
				<input type="text" id="csPhone"name="csPhone" 
							value="${command.csPhone}" maxlength="40"/>
				<span style="color:red;display:block">輸入格式: 0212345678</span>
			</td>
		</tr>
		<tr>
			<!-- 連結url -->
			<th><spring:message code="omom004f.tbNews.linkUrl"/>：</th>
			<td colspan="3">
				<input type="text" id="linkUrl"name="linkUrl" 
							value="${command.linkUrl}" />
				<span style="color:red;display:block">輸入格式: https://www.msig-mingtai.com.tw/campaign/index.html</span>
			</td>
		</tr>
		<tr>
			<!-- 推播狀態 -->
			<th><spring:message code="omom004f.tbNews.pushStatus"/>：</th>
			<td>
				<input type="hidden" value="${command.pushStatus}" id="pushStatusHidden">
				<c:if test="${command.action ne 'insert'}">
					<select id="pushStatus" name="pushStatus" class="validate[required] select">
						<option value="" selected><spring:message code="select.options.0.label"/></option>
						<c:forEach items="${lookupPushstaList}" var="item">
							<option value="${item.lookupCde}"  ${command.pushStatus eq item.lookupCde ? 'selected' : ''}>${item.dscr}</option>
						</c:forEach>
					</select>
				</c:if>
				<c:if test="${command.action eq 'insert'}">
					<c:forEach items="${lookupPushstaList}" var="item">
						<c:if test="${item.lookupCde eq 'N'}">
							<input type="text" value="${item.dscr}"  id="pushStatusDscr" name="pushStatusDscr" readonly />
						</c:if>
					</c:forEach>
					<input type="hidden" id="pushStatus" name="pushStatus" 
							value="N" maxlength="40"/>
				</c:if>
			</td>
			<!-- 實際推播時間 -->
			<th><spring:message code="omom004f.tbNews.pushTime"/>：</th>
			<td>
				<input type="text" id="pushTime"name="pushTime" 
							value="${command.pushTime}" maxlength="40" readonly/>
			</td>
		</tr>
		<tr>
			<!-- 生效日 -->
			<th><span class="notNullCol">*</span><spring:message code="omom004f.tbNews.effDate"/>：</th>
			<td>
				<input type="text" id="effDate"name="effDate" 
							value="${command.effDate}" maxlength="40"  class="validate[required]"/>
			</td>
			<!-- 失效日 -->
			<th><spring:message code="omom004f.tbNews.endDate"/>：</th>
			<td>
				<input type="text" id="endDate"
						name="endDate" value="${command.endDate}" />
			</td>
		</tr>
		<tr>
			<c:choose>
				<%-- 新增顯示 --%>
				<c:when test="${command.action eq 'insert'}">
					<%-- 建立人員 --%>
					<th><spring:message code="cr.user.label"/>：</th>
					<td>
						<input type="text" class="inputN" id="crUser" value="${command.crUser}" readonly/>
						<input type="hidden" id="userstamp" value="${command.userstamp}"/>
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
	
	<%-- 按鈕區域 --%>
	<div class="buttonDiv">
		<c:if test="${command.action eq 'detail'}">
			<input type="button" value="<spring:message code="page.previous.label"/>" class="btn" onclick="doShowQuery();"/>
		</c:if>
		<c:if test="${command.action ne 'detail'}">
			<input type="button" value="<spring:message code="confirm.label"/>"  class="btn" onclick="doConfirm();" />
		</c:if>
		<c:if test="${command.action eq 'update'}">
			<input type="button" value="<spring:message code="revert.label"/>"  class="btn" onclick="doRevert();" />
			<input type="button" value="<spring:message code="page.previous.label"/>" class="btn" onclick="doShowQuery();"/>
		</c:if>
		<c:if test="${command.action eq 'insert'}">
            <input type="button" value="<spring:message code="clear.label"/>" class="btn" onclick="doClear();" />
			<input type="button" value="<spring:message code="page.previous.label"/>" class="btn" onclick="doShowIndex();"/>
        </c:if>
	</div>
</div>
</body>
</html>