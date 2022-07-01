<!-- 
-->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="pgId" value="OMOM005F" />
<html>
<head>
	<script>
		$(document).ready(function(){
			var actionType = $("#actionType").val();
			<%-- 查詢條件區塊套用CSS--%>
			bindFormCSS("omom005fModForm", actionType);
			$('#effMileageImg').removeClass( "inputN_M" )
			$('#endMileageImg').removeClass( "inputN_M" )
		})
		function doShowIndex() {
			$("#indexForm").submit();
		}
		
		function doShowQuery() {
			var click = "${queryForm.queryClicked}";
			if(click){
				$("#queryForm").submit();
			}else{
				doShowIndex();
			}
		}
		
		<%-- 回上一頁, 並保留原本的查詢條件 --%>
		function ajaxOkDo(data) {
			if (data.result == "${ajaxOk}") {
				doDetail();
			}
		}
		function cancelApply(){
			var statusValue = $('#applyStatus').val()
			if(statusValue == 'I'|| statusValue == 'CHK'){
				var c = confirm("<spring:message code='omom005f.cancel.apply.check'/>");
				if (!c) {
					return false;
				}
				var actionUrl = "${ctx}/${pgId}/cancelApply.html";
				var param = $("#cancelApplyForm").serialize();
				doCwAjax(actionUrl, param, ajaxOkDo);
			} else {
				alert('申請狀態=' + $('#applyStatusDscr').val() + ',無法取消申請')
			}
		}
		
		function chkApply(){
			var c = confirm("<spring:message code='omom005f.chk.apply.check'/>");
			if (!c) {
				return false;
			}
			var actionUrl = "${ctx}/${pgId}/chkApply.html";
			var param = $("#chkApplyForm").serialize();
			doCwAjax(actionUrl, param, ajaxOkDo);
		}
		
		function failApply(){
			var c = confirm("<spring:message code='omom005f.fail.apply.check'/>");
			if (!c) {
				return false;
			}
			var actionUrl = "${ctx}/${pgId}/failApply.html";
			var param = $("#failApplyForm").serialize();
			doCwAjax(actionUrl, param, ajaxOkDo);
		}
		
		function successApply(obj){
			obj.disabled = true;
			var param = encodeURI($('#successApplyQueryForm').serialize());
			
			var title = "<spring:message code='omom005f.success.apply'/>";
			var prefixUrl = "${ctx}/OMOM005F/successApplyLov.html?";
			doOpenLov(title, param, prefixUrl, "800px", "250px");
			
			obj.disabled = false;
		}
		
		function doOpenLov(title, param, prefixUrl, width, height){
			window.parent.doShowNewLOV(title, param, prefixUrl, width, height, getCurrentLevel() + 1 );
		}
		
		function doDetail(btn) {
	        $("#detailQueryForm").submit();
	    }
	</script>
</head>
<body>
<br/><br/><br/>
<form id="detailQueryForm" method="post" action="${ctx}/OMOM005F/detail.html">
    <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    <input type="hidden" id="detailApplyNo" name="detailApplyNo" value="${command.applyNo}"/>
</form>
<form id="successApplyQueryForm" method="post" action="${ctx}/${pgId}/successApplyLov.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" name="successApplyServicer" value="${command.servicer}"/>
	<input type="hidden" name="successApplyEffUbiDate" value="${command.effUbiDate}"/>
	<input type="hidden" name="successApplyEndUbiDate" value="${command.endUbiDate}"/>
	<input type="hidden" name="successApplyApplyNo" value="${command.applyNo}"/>
	<input type="hidden" name="userId" value="${command.userId}"/>
</form>
<form id="failApplyForm" method="post" action="${ctx}/${pgId}/failApply.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" name="failApplyApplyNo" value="${command.applyNo}"/>
</form>
<form id="chkApplyForm" method="post" action="${ctx}/${pgId}/chkApply.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" name="chkApplyApplyNo" value="${command.applyNo}"/>
</form>
<form id="queryForm" action="${ctx}/${pgId}/query.html">
<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<%-- 保留空白給上一頁的功能使用 --%>
	<%-- 設定頁次_分頁下拉選單及圖示用 --%>
	<input type="hidden" id="pages" name="pages" value="${queryForm.pages}"/>
</form>	
<form id="indexForm" action="${ctx}/${pgId}/index.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
</form>
<form id="cancelApplyForm" method="post" action="${ctx}/${pgId}/cancelApply.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" id="cancelApplyUserId" name="cancelApplyUserId" value="${command.userId}"/>
	<input type="hidden" id="cancelApplyApplyNo" name="cancelApplyApplyNo" value="${command.applyNo}"/>
</form>
<div class="mainDiv">
	<form id="omom005fModForm" name="omom005fModForm" method="post">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="actionType" value="${command.action}" />
		<table id="table1" class="modTableMaster" border="1" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2"  cellspacing="0">

		<tr>
			<!-- 申請單號-->
			<th><spring:message code="omom005f.vwCustUbiApply.applyNo"/>：</th>
			<td>
				<input type="text" id="applyNo"
						name="applyNo" value="${command.applyNo}" readonly/>
			</td>
			<!-- 會員編號 -->
			<th><spring:message code="omom005f.vwCustUbiApply.userId"/>：</th>
			<td>
				<input type="text" id="userId"name="userId" 
							value="${command.userId}" maxlength="40" readonly/>
			</td>
		</tr>
		<tr>
			<!-- 申請日期 -->
			<th><spring:message code="omom005f.vwCustUbiApply.applyDate"/>：</th>
			<td>
				<input type="text" id="applyDate"
						name="applyDate" value="<fmt:formatDate value='${command.applyDate}' type='Both' pattern='yyyy/MM/dd'/>" readonly/>
			</td>
			<!-- 車號 -->
			<th><spring:message code="omom005f.vwCustUbiApply.carNo"/>：</th>
			<td>
				<input type="text" id="carNo"
						name="carNo" value="${command.carNo}" readonly/>
			</td>
		</tr>
		<tr>
			<!-- 申請狀態 -->
			<th><spring:message code="omom005f.vwCustUbiApply.applyStatus"/>：</th>
			<td>
				<input type="hidden" id="applyStatus"name="applyStatus" 
							value="${command.applyStatus}" maxlength="40" readonly/>
				<input type="text" id="applyStatusDscr"name="applyStatusDscr" 
							value="${command.applyStatusDscr}" maxlength="40" readonly/>
			</td>
			<!-- 保費加減率(%) -->
			<th><spring:message code="omom005f.vwCustUbiApply.insuranceRate"/>：</th>
			<td>
				<input type="text" id="insuranceRate"
						name="insuranceRate" value="${command.insuranceRate}" readonly/>
			</td>
		</tr>
		<tr>
			<!-- 交付審核日期 -->
			<th><spring:message code="omom005f.vwCustUbiApply.chkDate"/>：</th>
			<td>
				<input type="text" id="chkDate"name="chkDate" 
							value="<fmt:formatDate value='${command.chkDate}' type='Both' pattern='yyyy/MM/dd'/>" maxlength="40" readonly/>
			</td>
			<!-- 完成日期 -->
			<th><spring:message code="omom005f.vwCustUbiApply.compDate"/>：</th>
			<td>
				<input type="text" id="compDate"
						name="compDate" value="<fmt:formatDate value='${command.compDate}' type='Both' pattern='yyyy/MM/dd'/>" readonly/>
			</td>
		</tr>
		<tr>
			<!-- 失敗日期 -->
			<th><spring:message code="omom005f.vwCustUbiApply.failDate"/>：</th>
			<td>
				<input type="text" id="failDate"name="failDate" 
							value="<fmt:formatDate value='${command.failDate}' type='Both' pattern='yyyy/MM/dd'/>" maxlength="40" readonly/>
			</td>
			<!-- 失敗日期 -->
			<th><spring:message code="omom005f.vwCustUbiApply.servicer"/>：</th>
			<td>
				<input type="text" id="servicer"name="servicer" 
							value="${command.servicer}" maxlength="40" readonly/>
			</td>
			
		</tr>
		<tr>
			<!-- UBI車險起日 -->
			<th><spring:message code="omom005f.vwCustUbiApply.effUbiDate"/>：</th>
			<td>
				<input type="text" id="effUbiDate"
						name="effUbiDate" value="<fmt:formatDate value='${command.effUbiDate}' type='Both' pattern='yyyy/MM/dd'/>" readonly/>
			</td>
			<!-- UBI車險到期日 -->
			<th><spring:message code="omom005f.vwCustUbiApply.endUbiDate"/>：</th>
			<td>
				<input type="text" id="endUbiDate"name="endUbiDate" 
							value="<fmt:formatDate value='${command.endUbiDate}' type='Both' pattern='yyyy/MM/dd'/>" maxlength="40" readonly/>
			</td>
		</tr>
		<tr>
			<!-- 起始里程數 -->
			<th><spring:message code="omom005f.vwCustUbiApply.effMileage"/>：</th>
			<td>
				<input type="text" id="effMileage"name="effMileage" 
							value="${command.effMileage}" maxlength="40" readonly/>
			</td>
			<!-- 起始里程設定日期 -->
			<th><spring:message code="omom005f.vwCustUbiApply.effMileageDate"/>：</th>
			<td>
				<input type="text" id="effMileageDate"
						name="effMileageDate" value="<fmt:formatDate value='${command.effMileageDate}' type='Both' pattern='yyyy/MM/dd'/>" readonly/>
			</td>
		</tr>
		<tr>
			<!-- 起始里程數照片url -->
			<th><spring:message code="omom005f.vwCustUbiApply.effMileageImg"/>：</th>
			<td colspan="3">
				<c:if test="${command.effMileageImg ne null}">
					<a href="${command.effMileageImg}"  target="_blank">
						<input type="text" id="effMileageImg"name="effMileageImg" 
								value="${command.effMileageImg}" maxlength="40" readonly style=" cursor: pointer" class="inputN_F"/>
					</a>
				</c:if>
			</td>
		</tr>
		<tr>
			<!-- 結束里程數 -->
			<th><spring:message code="omom005f.vwCustUbiApply.endMileage"/>：</th>
			<td>
				<input type="text" id="endMileage"
						name="endMileage" value="${command.endMileage}" readonly/>
			</td>
			<!-- 結束里程設定日期 -->
			<th><spring:message code="omom005f.vwCustUbiApply.endMileageDate"/>：</th>
			<td>
				<input type="text" id="endMileageDate"name="endMileageDate" 
							value="<fmt:formatDate value='${command.endMileageDate}' type='Both' pattern='yyyy/MM/dd'/>" maxlength="40" readonly/>
			</td>
		</tr>
		<tr>
			<!-- 結束里程數照片url -->
			<th><spring:message code="omom005f.vwCustUbiApply.endMileageImg"/>：</th>
			<td colspan="3">
				<c:if test="${command.endMileageImg ne null}">
					<a href="${command.endMileageImg}"  target="_blank">
						<input type="text" id="endMileageImg"
								name="endMileageImg" value="${command.endMileageImg}" readonly style=" cursor: pointer"  class="inputN_F" />
					</a>
				</c:if>
			</td>
		</tr>
		<tr>
			<!-- 連絡電話 -->
			<th><spring:message code="omom005f.vwCustUbiApply.contactPhone"/>：</th>
			<td>
				<input type="text" id="contactPhone"
						name="contactPhone" value="${command.contactPhone}" readonly/>
			</td>
			<!-- 推薦代碼 -->
			<th><spring:message code="omom005f.vwCustUbiApply.promCode"/>：</th>
			<td>
				<input type="text" id="promCode"
						name="promCode" value="${command.promCode}" readonly/>
			</td>
		</tr>
		<tr>
			<!-- 縣市 -->
			<th><spring:message code="omom005f.vwCustUbiApply.city"/>：</th>
			<td>
				<input type="text" id="city"name="city" 
							value="${command.city}" maxlength="40" readonly/>
			</td>
			<!-- 鄉鎮市區 -->
			<th><spring:message code="omom005f.vwCustUbiApply.area"/>：</th>
			<td>
				<input type="text" id="area"
						name="area" value="${command.area}" readonly/>
			</td>
		</tr>
		<tr>
			<!-- 郵遞區號 -->
			<th><spring:message code="omom005f.vwCustUbiApply.zip"/>：</th>
			<td>
				<input type="text" id="zip"name="zip" 
							value="${command.zip}" maxlength="40" readonly/>
			</td>
			<th colspan="2">
			</th>
		</tr>
		<tr>
			<%-- 修改人員 --%>
			<th><spring:message code="userstamp.label"/>：</th>
			<td><input type="text" id="userstamp" value="${command.userstamp}" readonly/></td>
			<%-- 修改日期 --%>
			<th><spring:message code="datestamp.label"/>：</th>
			<td><input type="text" id="datestamp" value="<fmt:formatDate value='${command.datestamp}' type='Both' pattern='yyyy/MM/dd HH:mm:ss'/>" readonly/></td>
		</tr>
		</table>
	</form>
	
	<%-- 按鈕區域 --%>
	<div class="buttonDiv">
		<c:if test="${command.applyStatus eq 'I'}">
			<input type="button" value="<spring:message code="omom005f.cancel.apply"/>"  class="btn" onclick="cancelApply(this);" />
			<input type="button" value="<spring:message code="omom005f.chk.apply"/>"  class="btn" onclick="chkApply(this);" />
			<input type="button" value="<spring:message code="page.previous.label"/>" class="btn" onclick="doShowQuery(this);"/>
		</c:if>
		<c:if test="${command.applyStatus eq 'CHK'}">
			<input type="button" value="<spring:message code="omom005f.fail.apply"/>"  class="btn" onclick="failApply(this);" />
			<input type="button" value="<spring:message code="omom005f.success.apply"/>"  class="btn" onclick="successApply(this);" />
			<input type="button" value="<spring:message code="page.previous.label"/>" class="btn" onclick="doShowQuery(this);"/>
		</c:if>
		<c:if test="${command.applyStatus eq 'CANCEL' or command.applyStatus eq 'F' or command.applyStatus eq 'COMP'}">
			<input type="button" value="<spring:message code="page.previous.label"/>" class="btn" onclick="doShowQuery(this);"/>
		</c:if>
	</div>
</div>
</body>
</html>