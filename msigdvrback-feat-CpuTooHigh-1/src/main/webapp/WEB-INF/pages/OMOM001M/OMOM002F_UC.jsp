<!-- 
    title 會員服務異動
    version 1.0 
    author Bob
    since 2018/11/26
-->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="pgId" value="OMOM002F" />
<html>
<head>
	<script>
		$(document).ready(function(){
			 $(".inputItem").attr("readonly", false);
			 bindCalendar("endUbiDate");
			 bindCalendar("effUbiDate");
			 $("#effUbiDate").removeClass("inputN_F");
			 $("#endUbiDate").removeClass("inputN_F");
	   });
		<%-- 關閉視窗 --%>
		function doCloseWindow(btn){
			closeLOV();
		}
		function doUpdateTbCustCar(){
			$("#update_user_id").attr("value", $("#userId").val());
			$("#update_eff_ubi_date").attr("value", $("#effUbiDate").val());
			$("#update_end_ubi_date").attr("value", $("#endUbiDate").val());
			$("#update_servicer").attr("value", $("#servicer").val());
			var actionUrl = "${ctx}/${pgId}/doUpdateTbCustCar.html";
			param = $("#updateTbCustCarForm").serialize();
			doCwAjax(actionUrl, param, ajaxOkDo);
		}
		function ajaxOkDo(data) {
			$("#updateTbCustCarQueryForm").submit()
			var parentWindowContent = $("#workAreaFrame",window.parent.document).contents() 
			parentWindowContent.find("#userIdSearch").val(parentWindowContent.find("#userId").val())
			parentWindowContent.find("#queryForm").submit()
		}
	</script>
</head>
<body>

<div class="mainDiv">
<br/><br/><br/>
<form id="updateTbCustCarQueryForm" method="post" action="${ctx}/OMOM002F/updateTbCustCarLov.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" name="userId" value="${command.userId}"/>
	<input type="hidden" name="email" value="${command.email}"/>
	<input type="hidden" name="userName" value="${command.userName}"/>
	<input type="hidden" name="mobilePhone" value="${command.mobilePhone}"/>
</form>
<form id="updateTbCustCarForm" method="post" action="${ctx}/${pgId}/doUpdateTbCustCar.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" id="update_user_id" name="userId"/>
	<input type="hidden" id="update_eff_ubi_date" name="effUbiDate"/>
	<input type="hidden" id="update_end_ubi_date" name="endUbiDate"/>
	<input type="hidden" id="update_servicer" name="servicer"/>
</form>
		<div id="blockB">
	        <table class="resultTable" id="mainTable">
	            <tr>
					<!-- 會員編號 -->
					<th><spring:message code="omom002f.header1.label"/>：</th>
					<td>
						<input type="text"  id="userId" 
								name="userId" value="${command.userId}" maxlength="20" />
					</td>
					<!-- 電子信箱 -->
					<th><spring:message code="omom002f.header2.label"/>：</th>
					<td>
						<input type="text"  id="email" 
								name="email" value="${command.email}" maxlength="20" />
					</td>
				</tr>
				<tr>
					<!-- 中文姓名 -->
					<th><spring:message code="omom002f.header3.label"/>：</th>
					<td>
						<input type="text"  id="userName" 
								name="userName" value="${command.userName}" maxlength="20" />
					</td>
					<!-- 手機 -->
					<th><spring:message code="omom002f.header4.label"/>：</th>
					<td>
						<input type="text"  id="mobilePhone" 
								name="mobilePhone" value="${command.mobilePhone}" maxlength="20" />
					</td>
				</tr>
	        </table>    
	    </div>
		<div id="blockB">
	        <table class="resultTable" id="mainTable">
	            <thead>
	                <tr class="nowrapHeader listHeader">
	                    <th width="5%"><spring:message code="omom002f.header9.label"/></th><%--  車號 --%>
	                    <th width="10%"><spring:message code="omom002f.header31.label"/></th><%-- 保險生效日 --%>
	                    <th width="10%"><spring:message code="omom002f.header10.label"/></th><%-- 保險到期日 --%>
	                    <th width="10%"><spring:message code="omom002f.header32.label"/></th><%-- 保險服務人員--%>
	                    <th width="10%"><spring:message code="omom002f.header30.label"/></th><%-- 修改確認 --%>
	                </tr>
	            </thead>
	            <tbody>
                       <tr>
                           <td><input type="text" value="${tbCustCar.carNo}"/></td>
                           <td><input type="text" id="effUbiDate" value="<fmt:formatDate value="${tbCustCar.effUbiDate}" type="Both" pattern="yyyy/MM/dd"/>" class="inputY_F inputItem"/></td>
                           <td><input type="text" id="endUbiDate" value="<fmt:formatDate value="${tbCustCar.endUbiDate}" type="Both" pattern="yyyy/MM/dd"/>" class="inputY_F inputItem"/></td>
                           <td><input type="text" id="servicer" value="${tbCustCar.servicer}"  class="inputY_F inputItem"/></td>
							<td>
								<input type="button" class="btn" value="<spring:message code="update.label"/>" onclick="javascript:doUpdateTbCustCar();" />
	                       	</td>
                       </tr>
	            </tbody>
	        </table>    
	    </div>
	    <div class="buttonDiv">
	        <input type="button" class="btn" value="<spring:message code="omom004f.batch.insert.close.window"/>" onclick="javascript:doCloseWindow(this);" />
	    </div>
</div>
</body>
</html>