<!-- 
    title 會員服務異動
    version 1.0 
    author Bob
    since 2018/10/31
-->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="pgId" value="OMOM002F" />
<html>
<head>
	<script>
		 $(document).ready(function(){
			 $(".inputItem").attr("readonly", false);
	    });
		function doResetCarNo(){
			var errMsg = ""			
			if($("#newCarNo").val().trim() == ""){
				errMsg += '<spring:message code="omom002f.carNo.empty"/>\n'
				
			}
			if($("#mileageStatus").val().trim() == ""){
				errMsg += '<spring:message code="omom002f.reset.empty"/>\n'
				
			}
			if(errMsg != ""){
				alert(errMsg)
				return false
			}
			$("#reset_user_id").attr("value", $("#userId").val());
			$("#reset_newCarNo").attr("value", $("#newCarNo").val());
			$("#reset_device_type").attr("value", $("#deviceType").val());
			$("#reset_mac_address").attr("value", $("#macAddress").val());
			$("#reset_sn").attr("value", $("#sn").val());
			var actionUrl = "${ctx}/${pgId}/doResetCarNo.html";
			var isResetValue = $('#mileageStatus').val()
			$('#reset_is_reset').val(isResetValue)
			param = $("#resetCarNoForm").serialize();
			doCwAjax(actionUrl, param, ajaxOkDo);
		}
		function ajaxOkDo(data) {
			$("#resetCarNoQueryForm").submit()
			var parentWindowContent = $("#workAreaFrame",window.parent.document).contents() 
			parentWindowContent.find("#userIdSearch").val(parentWindowContent.find("#userId").val())
			parentWindowContent.find("#queryForm").submit()
		}
		<%-- 關閉視窗 --%>
		function doCloseWindow(btn){
			closeLOV();
		}
	</script>
</head>
<body>

<div class="mainDiv">
<br/><br/><br/>
<form id="resetCarNoQueryForm" method="post" action="${ctx}/OMOM002F/resetCarNoLov.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" name="userId" value="${command.userId}"/>
	<input type="hidden" name="email" value="${command.email}"/>
	<input type="hidden" name="userName" value="${command.userName}"/>
	<input type="hidden" name="mobilePhone" value="${command.mobilePhone}"/>
</form>
<form id="resetCarNoForm" method="post" action="${ctx}/${pgId}/doResetCarNo.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" id="reset_device_type" name="device_type"/>
	<input type="hidden" id="reset_mac_address" name="mac_address"/>
	<input type="hidden" id="reset_sn" name="sn"/>
	<input type="hidden" id="reset_user_id" name="user_id"/>
	<input type="hidden" id="reset_is_reset" name="is_reset"/>
	<input type="hidden" id="reset_newCarNo" name="newCarNo"/>
	<input type="hidden" id="reset_userstamp" name="userstamp"/>
	<input type="hidden" id="reset_trx_time" name="trx_time"/>
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
	                    <th width="10%"><spring:message code="omom002f.header28.label"/></th><%--  原車號 --%>
	                    <th width="10%"><spring:message code="omom002f.header10.label"/></th><%-- 保險到期日 --%>
	                    <th width="10%"><spring:message code="omom002f.header11.label"/></th><%-- VIP類別 --%>
	                    <th width="10%"><spring:message code="omom002f.header15.label"/></th><%-- 起始里程數--%>
	                    <th width="10%"><spring:message code="omom002f.header19.label"/></th><%-- 起始里程計算日期 --%>
	                    <th width="10%"><spring:message code="omom002f.header29.label"/></th><%-- 新車號 --%>
	                    <th width="10%"><spring:message code="omom002f.header26.label"/></th><%-- 行車紀錄是否歸零重算--%>
	                    <th width="10%"><spring:message code="omom002f.header30.label"/></th><%-- 修改確認 --%>
	                </tr>
	            </thead>
	            <tbody>
                       <tr>
                           <td><input type="text" value="${tbCustCar.carNo}"/></td>
                           <td><input type="text" value="<fmt:formatDate value="${tbCustCar.endUbiDate}" type="Both" pattern="yyyy/MM/dd"/>"/></td>
                           <td><input type="text" value="${tbCustCar.vipType}"/></td>
                           <td><input type="text" value="${tbCustCar.effMileage}"/></td>
                           <td><input type="text" value="<fmt:formatDate value="${tbCustCar.effMileageDate}" type="Both" pattern="yyyy/MM/dd"/>"/></td>
                           <td><input type="text" value=""  class="inputY_F inputItem" id="newCarNo"/></td>
                       	<td>
                        	<select class="select" id="mileageStatus" name="mileageStatus">
                        		<option value="" selected><spring:message code="select.options.0.label"/></option>
								<option value="Y">Y</option>
								<option value="N">N</option>
							</select>
						</td>
						<td>
							<input type="button" class="btn" value="<spring:message code="update.label"/>" onclick="javascript:doResetCarNo();" />
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