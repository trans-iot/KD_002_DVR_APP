<!-- 
    title 設備資料查詢
    version 1.0 
    author Bob
    since 2018/10/24
-->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="pgId" value="OMOM003F" />
<html>
<head>
	<script>
		$(document).ready(function(){
			var actionType = $("#actionType").val();
			var msg = "";
			var readOnlyList;
			var excludeList;
			var tbCustomerReadOnlyList;
			
			var tab1_Options = new Object();
			tab1_Options.actType = actionType;
			<%-- detail區塊按鈕加入事件 --%>
			var tab1_index = 0;
			var tab1_delFunc = "";
			tab1_Options.setCSS = true; //自行套CSS:false
			if (actionType == "update") {
			 	readOnlyList = ["removeDeviceStatus","deviceTypeDscr","imei","sn","deviceStatusDscr", 
				 	"userId","bindDate","userstamp","datestamp","simMobile", "targetNo", "serviceType",
				 	 "uploadDate", "devEffDate","devEndDate", "simEffDate", "simEndDate"];
			 	 
			 	tbCustomerReadOnlyList = ["userId","engName","nickname","email","dob",
	                "contactPhone","addr","custStatus","sex","userName","cuid","carNo","unuseDvrCnt","addr","remarks",
	                "registerTime","pwdStatusDscr","appLoginTime","agreeTimeDscr"];
				for(var i = 0; i < tbCustomerReadOnlyList.length ; i++) {
					readOnlyList.push(tbCustomerReadOnlyList[i]);
				}
			}
			<%-- 查詢條件區塊套用CSS--%>
			bindFormCSS("omom003fModForm", actionType, readOnlyList, excludeList);
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
			if(data.result == '${ajaxFail}'){
			}else if(data.result == '${ajaxOk}'){
				doDetail(data.map.result,data.map.errMessage);
			}
			
		}
		
	    function doDetail(result,errMessage) {
	    	$("#detailResult").val(result)
    		$("#detailErrMessage").val(errMessage)
	        $("#detailQueryForm").submit();
	    }
		
		function doRemoveDevice(){
			if('${command.deviceStatus}' != 'BINDING') {
				alert('<spring:message code="omom003f.tbdevice.errMsg1" />');
				return false;
			}
			var actionUrl = "${ctx}/${pgId}/removeDevice.html";
			param = $("#removeDeviceForm").serialize();
			doCwAjax(actionUrl, param, ajaxOkDo);
		}
	</script>
</head>
<body>
<br/><br/><br/>
<form id="detailQueryForm" method="post" action="${ctx}/OMOM003F/detail.html">
    <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    <input type="hidden" id="detailDeviceType" name="detailDeviceType" value="${command.deviceType}" />
    <input type="hidden" id="detailImei" name="detailImei" value="${command.imei}" />
    <input type="hidden" id="detailSn" name="detailSn" value="${command.sn}" />
    <input type="hidden" id="detailResult" name="detailResult" value="" />
    <input type="hidden" id="detailErrMessage" name="detailErrMessage" value="" />
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
<form id="removeDeviceForm" method="post" action="${ctx}/${pgId}/removeDevice.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" id="remove_device_type" name="device_type" value="${command.deviceType}"/>
	<input type="hidden" id="remove_imei" name="mac_imei" value="${command.imei}"/>
	<input type="hidden" id="remove_sn" name="sn" value="${command.sn}"/>
	<input type="hidden" id="remove_user_id" name="user_id" value="${command.userId}"/>
	<input type="hidden" id="remove_is_reset" name="is_reset" value=""/>
	<input type="hidden" id="remove_userstamp" name="userstamp" value="${command.userstamp}"/>
	<input type="hidden" id="remove_trx_time" name="trx_time" value=""/>
</form>
<div class="mainDiv">
	<form id="omom003fModForm" name="omom003fModForm" method="post">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="actionType" value="${command.action}" />
		<table id="table1" class="modTableMaster" border="1" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2"  cellspacing="0">
		<c:if test="${model.detailResult eq ajaxOk}">
			<tr>
				<th style="color:#77FF00"><spring:message code="omom002f.remove.device.title" />：</th>
				<td colspan="3">
					<input type="text" id="removeDeviceStatus"
							name="removeDeviceStatus" value="<spring:message code='success.label'/>" readonly  style="color:#77FF00"/>
				</td>
			</tr>
		</c:if>
		<c:if test="${model.detailResult eq ajaxFail}">
			<tr>
				<th style="color:red"><spring:message code="omom002f.remove.device.title" />：</th>
				<td colspan="3">
					<input type="text" id="removeDeviceStatus"
							name="removeDeviceStatus" value="<spring:message code='fail.label'/> ${model.detailErrMessage}" readonly style="color:red"/>
				</td>
			</tr>
		</c:if>
		<tr>
			<!-- 設備種類-->
			<th><spring:message code="omom003f.tbDevice.deviceTypeDscr"/>：</th>
			<td>
				<input type="text" id="deviceTypeDscr"
						name="deviceTypeDscr" value="${command.deviceTypeDscr}" readonly/>
			</td>
			<!-- DVR S/N-->
			<th><spring:message code="omom003f.tbDevice.sn"/>：</th>
			<td>
				<input type="text" id="sn"name="sn" 
							value="${command.sn}" maxlength="40" readonly/>
			</td>
		</tr>
		<tr>
			<!-- DVR IMEI-->
			<th><spring:message code="omom003f.tbDevice.imei"/>：</th>
			<td>
				<input type="text" id="imei"
						name="imei" value="${command.imei}" readonly/>
			</td>
			<!-- 設備狀態 -->
			<th><spring:message code="omom003f.tbDevice.deviceStatusDscr"/>：</th>
			<td>
				<input type="text" id="deviceStatusDscr"name="deviceStatusDscr" 
							value="${command.deviceStatusDscr}" maxlength="40" readonly/>
			</td>
		</tr>
		<tr>
			<!-- 綁定會員-->
			<th><spring:message code="omom003f.tbDevice.userId"/>：</th>
			<td>
				<input type="text" id="userId"
						name="userId" value="${command.userId}" readonly/>
			</td>
			<!-- 綁定日期 -->
			<th><spring:message code="omom003f.tbDevice.bindDate"/>：</th>
			<td>
				<input type="text" id="bindDate"name="bindDate" 
							value="<fmt:formatDate value='${command.bindDate}' type='Both' pattern='yyyy/MM/dd'/>" maxlength="40" readonly/>
			</td>
		</tr>
		<tr>
			<%-- 設備有效期間 --%>
			<th><spring:message code="omom003f.tbDevice.deviceTime"/> : </th>
			<td colspan="3">
				<input type="text" id="devEffDate"name="devEffDate" 
							value="<fmt:formatDate value='${command.devEffDate}' type='Both' pattern='yyyy/MM/dd'/>" class="inputN_M" maxlength="40" readonly/>
				~
				<input type="text" id="devEndDate"name="devEndDate" 
							value="<fmt:formatDate value='${command.devEndDate}' type='Both' pattern='yyyy/MM/dd'/>" class="inputN_M" maxlength="40" readonly/>
			</td>
		</tr>
		<tr>
			<%-- SIM卡門號 --%>
			<th><spring:message code="omom003f.tbDevice.simMobile"/> : </th>
			<td><input type="text" id="simMobile"name="simMobile" value="${command.simMobile}" readonly/>
			</td>
			<th></th>
			<td></td>
		</tr>
		<tr>
			<%-- SIM卡有效期間 --%>
			<th><spring:message code="omom003f.tbDevice.simTime" /> : </th>
			<td colspan="3">
				<input type="text" id="simEffDate"name="simEffDate" 
						value="<fmt:formatDate value='${command.simEffDate}' type='Both' pattern='yyyy/MM/dd'/>" class="inputN_M" maxlength="40" readonly/>
				~
				<input type="text" id="simEndDate"name="simEndDate" 
							value="<fmt:formatDate value='${command.simEndDate}' type='Both' pattern='yyyy/MM/dd'/>" class="inputN_M" maxlength="40" readonly/>
			</td>
		</tr>
		<tr>
			<!-- 綁定標的號碼 -->
			<th><spring:message code="omom003f.tbDevice.targetNo"/>：</th>
			<td>
				<input type="text" id="targetNo"
						name="targetNo" value="${command.targetNo}" readonly/>
			</td>
			<%-- 服務類別 --%>
			<th><spring:message code="omom003f.tbDevice.serviceType"/>：</th>
			<td>
				<input type="text" id="serviceType"name="serviceType" 
							value="${command.serviceType}" maxlength="40" readonly/>
			</td>
		</tr>
		<tr>
			<%-- 設備資料上傳時間 --%>
			<th><spring:message code="omom003f.tbDevice.uploadDate"/>：</th>
			<td>
				<input type="text" id="uploadDate"name="uploadDate" 
							value="<fmt:formatDate value='${command.uploadDate}' type='Both' pattern='yyyy/MM/dd HH:mm'/>" class="inputN_M" maxlength="40" readonly/>
			</td>
			<th></th>
			<td></td>
		</tr>
		<tr>
			<!-- 修改人員-->
			<th><spring:message code="userstamp.label"/>：</th>
			<td>
				<input type="text" id="userstamp"
						name="userstamp" value="${command.userstamp}" readonly/>
			</td>
			<!-- 修改日期 -->
			<th><spring:message code="datestamp.label"/>：</th>
			<td>
				<input type="text" id="datestamp"name="datestamp" 
							value="<fmt:formatDate value='${command.datestamp}' type='Both' pattern='yyyy/MM/dd HH:mm:ss'/>" maxlength="40" readonly/>
			</td>
		</tr>
		
		
		<tr>
			<td colspan="4"><spring:message code="omom003f.label.bindUser"/></td>
		</tr>
		
		<tr>
			<!-- 會員編號 -->
			<th><spring:message code="smdd003f.customer.userid"/>：</th>
			<td>
				<input type="text" id="userId" 
						name="userId" value="${command.tbCustomer.userId}" readonly/>
			</td>
			<!-- 中文姓名 -->
			<th><spring:message code="smdd003f.customer.user_name"/>：</th>
			<td>
				<input type="text" id="userName"name="userName" 
							value="${command.tbCustomer.userName}" maxlength="40" />
			</td>
		</tr>
		
		<tr>
			<!-- *電子信箱 -->
			<th><spring:message code="smdd003f.customer.email"/>：</th>
			<td>
				<input type="text" id="email" name="email" class="validate[required,custom[email]]" style="text-transform: lowercase"
						value="${command.tbCustomer.email}" maxlength="100" />
			</td>
			<!-- *行動電話 -->
			<th><spring:message code="smdd003f.customer.contact_phone"/>：</th>
			<td>
				<input type="text" id="contactPhone" name="contactPhone" class="validate[required]"
						value="${command.tbCustomer.contactPhone}" maxlength="100" />
			</td>
		</tr>
		
		<tr>
			<!-- *會員狀態 -->
			<th><spring:message code="smdd003f.customer.cust_status"/>：</th>
			<td>
              	<c:forEach var="item" items="${custstatusaDscr}" varStatus="status">
              		<c:if test="${item.key eq command.tbCustomer.custStatus}">
					<input type="text" id="custStatus" name="custStatus" class="validate[required]"
							value="${item.value}"  readonly />
					</c:if>
				</c:forEach>
			</td>
			<!-- 身分證號 -->
			<th><spring:message code="smdd003f.customer.cuid"/>：</th>
			<td>
				<input type="text" id="cuid" name="cuid" style="text-transform: uppercase"
						value="${command.tbCustomer.cuid}"  maxlength="40"  />
			</td>
		</tr>
		
		<tr>
			<!-- 車牌號碼 -->
			<th><spring:message code="smdd003f.customer.car_no"/>：</th>
			<td>
				<input type="text" id="carNo" name="carNo" style="text-transform: uppercase"
						value="${command.tbCustomer.carNo}" maxlength="40" />
			</td>
			<!-- 14天未使用DVR的累計次數 -->
			<th><spring:message code="smdd003f.customer.unuse_dvr_cnt"/>：</th>
			<td>
				<input type="text" id="unuseDvrCnt" name="unuseDvrCnt" 
						value="${command.tbCustomer.unuseDvrCnt}" maxlength="40" />
			</td>
		</tr>
		
		<tr>
			<!-- 英文姓名 -->
			<th><spring:message code="smdd003f.customer.eng_name"/>：</th>
			<td>
				<input type="text" id="engName" name="engName" 
						value="${command.tbCustomer.engName}" maxlength="40" />
			</td>
			<!-- 暱稱 -->
			<th><spring:message code="smdd003f.customer.nickname"/>：</th>
			<td>
				<input type="text" id="nickname" 
						name="nickname" value="${command.tbCustomer.nickname}" maxlength="40" />
			</td>
		</tr>
	 
		<tr>
			<!-- 性別 -->
			<th><spring:message code="smdd003f.customer.sex"/>：</th>
			<td>
				<select id="sex" name="sex" >
              	  <c:if test="${command.tbCustomer.sex eq 'M'}">
                 	<option value=""><spring:message code="select.options.0.label"/></option>
                 	<option value="M" selected>男</option>
                 	<option value="F">女</option>
               	  </c:if>
           		  <c:if  test="${command.tbCustomer.sex eq 'F'}">
                 		<option value=""><spring:message code="select.options.0.label"/></option>
	                  	<option value="M">男</option>
	                  	<option value="F" selected>女</option>
                  </c:if>
       			  <c:if  test="${command.tbCustomer.sex eq null or command.tbCustomer.sex eq ''}">
                 		<option value="" selected><spring:message code="select.options.0.label"/></option>
	                  	<option value="M">男</option>
	                  	<option value="F">女</option>
                  </c:if>
                </select>
			</td>
			<!-- 生日 -->
			<th><spring:message code="smdd003f.customer.dob"/>：</th>
			<td>
				<input type="text" id="dob" 
				 		name="dob" maxlength="10" value="${command.dobDscr}" /> 
			</td>
		</tr>
	 
		<tr>
			<!-- 地址 -->
			<th><spring:message code="smdd003f.customer.addr"/>：</th>
			<td colspan="3">
				<input type="text" id="addr" name="addr"  maxlength="100"
						value="${command.tbCustomer.addr}"  />
			</td>
		</tr>	
		
		<tr>
			<%-- 備註 --%>
			<th><spring:message code="smdd003f.customer.remarks"/>：</th>
			<td colspan="3">
				<textarea id="remarks" name="remarks" rows="3" cols="3">${command.tbCustomer.remarks}</textarea>
			</td>
		</tr> 
		
		<tr>
			<!-- 註冊日期 -->
			<th><spring:message code="smdd003f.header6.label"/>：</th>
			<td>
				<input type="text" id="registerTime" name="registerTime" value="${command.registerTimeDscr}" readonly/>
			</td>
			<!-- 隱私權與個資使用聲明同意時間 -->
			<th><spring:message code="smdd003f.customer.agree_time"/>：</th>
			<td>
				<input type="text" id="agreeTimeDscr" name="agreeTimeDscr" value="${command.agreeTimeDscr}" readonly/>
			</td>
		</tr>
	 
		<tr>
			<!-- 密碼狀態 -->
			<th><spring:message code="smdd003f.customer.pwd_status"/>：</th>
			<td>
				<input type="hidden" id="pwdStatus" name="pwdStatus" value="${command.tbCustomer.pwdStatus}" />
				<c:if test="${command.tbCustomer.pwdStatus eq 'FIRST'}">
					<input type="text" id="pwdStatusDscr" name="pwdStatusDscr" 
						value="<spring:message code="smdd003f.customer.pwd_status.first"/>"   maxlength="40" readonly/>
				</c:if>
				<c:if test="${command.tbCustomer.pwdStatus eq 'OK'}">
					<input type="text" id="pwdStatusDscr" name="pwdStatusDscr" 
						value="<spring:message code="smdd003f.customer.pwd_status.ok"/>"   maxlength="40" readonly/>
				</c:if>
				<c:if test="${command.tbCustomer.pwdStatus eq 'FORGET'}">
					<input type="text" id="pwdStatusDscr" name="pwdStatusDscr" 
						value="<spring:message code="smdd003f.customer.pwd_status.forget"/>"   maxlength="40" readonly/>
				</c:if>
			</td>
			<!-- 最近登入時間 -->
			<th><spring:message code="smdd003f.customer.app_login_time"/>：</th>
			<td>
				<input type="text" id="appLoginTime" name="appLoginTime" value="${command.appLoginTimeDscr}" readonly/>
			</td>
		</tr>
		</table>
	</form>
	
	<%-- 按鈕區域 --%>
	<div class="buttonDiv">
		<input type="button" value="<spring:message code="omom003f.remove.device"/>" class="btn" onclick="doRemoveDevice();"/>
		<input type="button" value="<spring:message code="page.previous.label"/>" class="btn" onclick="doShowQuery();"/>
	</div>
</div>
</body>
</html>