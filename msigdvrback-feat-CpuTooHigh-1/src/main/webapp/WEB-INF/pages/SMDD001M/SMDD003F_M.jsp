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
		function doRevert() {
			$("#updateForm").submit();
		}
		
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

		<%-- 編輯電子信箱 --%>
		function doEditEmail() {
			$('#editEmailUserId').val('${command.userId}');
			$('#editEmail').val('${command.email}');
			$('#editEmailForm').submit();
		}
		
		function doClear() {
			$("#insertForm").submit();
		}
		
		$(document).ready(function(){
			//會員狀態選單特殊處理
			var $status = $('#custStatus');
			if($status.val()!='U'){
				$status.find('option[value="U"]').attr("disabled", "disabled");
			}

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

			if(actionType ==  "insert") {
				readOnlyList = ["userId","userstamp","datestamp","crUser","crDate","pwdStatusDscr","agreeTimeDscr","appLoginTime","registerTime","deviceStatus","bindDate","uploadDate"];
				doBindCalendar();
			}else if (actionType == "update") {
				readOnlyList = ["userId","userstamp","datestamp","crUser","crDate","pwdStatusDscr","agreeTimeDscr","appLoginTime","registerTime","deviceStatus","bindDate","uploadDate",
								"email"];
				bindCalendar("dob", true);
				if('${command.tbDevice.sn}' != '') {
					var tbDeviceReadOnlyList = ["sn","devEffDate","devEndDate","simEffDate","simEndDate","simMobile","serviceType"];
					for(var i = 0; i < tbDeviceReadOnlyList.length ; i++) {
						readOnlyList.push(tbDeviceReadOnlyList[i]);
					}
					editAttrUpdate();
					$('#sn').attr('disabled', 'disabled');
				} else {
					doBindCalendar();
				}
				
			}else if (actionType == "detail") { 
				// 隱藏必key欄位  * 符號
	    		$(".notNullCol").hide();
				readOnlyList = ["userId","engName","nickname","email","dob",
				                "contactPhone","addr","custStatus",
				                "picUrl","sex",
				                "userStamp","dateStamp","crUser","crDate","effMileage","effMileageDate","effMileageImg"];
				msg = "<spring:message code ='detail.label' />";
				
				$("#tprodServiceMapDisplayTab").bindDetail(tab1_Options);
				editAttrUpdate();
			}
			$('#engName').bind('keyup blur', function() {
			    $(this).val($(this).val().replace(/[^A-Za-z0-9]/g, ''))
			});
			
			<%-- 查詢條件區塊套用CSS--%>
			bindFormCSS("smdd003fModForm", actionType, readOnlyList, excludeList);
			
			bindValidation("smdd003fModForm");
		});
		
		function isModified() {
			return $(":input[name$='detailModifyCheck']").length ? true : false;
		}
		<%-- insert or update --%>
		function doConfirm() {
			var valid = $("#smdd003fModForm").validationEngine('validate');
		//	var i = $(".tabsDiv").attr("selindex");
			var roleIdFlag = false;
  		//	var re = validateTab(i);
			if (valid==true) { //&&re==true
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
				
				var f1 = $("#smdd003fModForm").serialize();
				var param = f1;
				
				doCwAjax(actionUrl, param, ajaxOkDo);
			}
			else {
				$("#smdd003fModForm").validationEngine();
			}
		}

		<%-- 設備解除綁定 --%>
		function doRemoveDevice() {
			$('#sn').attr('disabled', false);
			var param = $("#smdd003fModForm").serialize();
			var actionUrl = "${ctx}/${pgId}/unBindDevice.html";
			doCwAjax(actionUrl, param, ajaxOkDo);
		}

		<%-- 重寄密碼信 --%>
		function doResetPwd() {
			if(confirm('<spring:message code="resetPwd.alert.message" />')) {
				var param = $("#smdd003fModForm").serialize();
				var actionUrl = "${ctx}/${pgId}/doResetPwd.html";
				doCwAjax(actionUrl, param);
			}
		}
		
		<%-- 回上一頁, 並保留原本的查詢條件 --%>
		function ajaxOkDo(data) {
			if (data.result == "${ajaxOk}") {
				doShowQuery();
			} 
		}


		function editAttrUpdate() {
			$('#devEffDate,#devEndDate,#simEffDate,#simEndDate').removeAttr('class');
			$('#devEffDate,#devEndDate,#simEffDate,#simEndDate').addClass('inputN');
		}

		function doBindCalendar() {
			bindCalendar("dob", true); <%-- 生日欄位加上日曆 --%>
			bindCalendar("devEffDate", false); <%-- 設備起始 --%>
			bindCalendar("devEndDate", false); <%-- 設備結束 --%>
			bindCalendar("simEffDate", false); <%-- SIM起始 --%>
			bindCalendar("simEndDate", false); <%-- SIM結束 --%>
		}

		<%-- 鍵盤輸入SN查詢設備 --%>
		$(function() {
			$('#sn').on('keyup',function() {
				if('${command.tbDevice.sn}' == '') {
					var param = {sn:$(this).val(),csrfPreventionSalt:'${csrfPreventionSalt}'};
					var actionUrl = "${ctx}/${pgId}/queryDevice.html";
					doCwAjax(actionUrl, param, ajaxOkAppend);
				}
			})
		})
		
		function ajaxOkAppend(data) {
			var tbDevice = data.map.tbDevice;
			if(tbDevice != null) {
				var deviceStatus = tbDevice.deviceStatus;
				var devEffDate = tbDevice.devEffDate;
				var devEndDate = tbDevice.devEndDate;
				var simMobile = tbDevice.simMobile;
				var serviceType = tbDevice.serviceType;
				var simEffDate = tbDevice.simEffDate;
				var simEndDate = tbDevice.simEndDate;

				$('#deviceStatus').val(deviceStatus);
				$('#devEffDate').val(devEffDate);
				$('#devEndDate').val(devEndDate);
				$('#simMobile').val(simMobile);
				$('#serviceType').val(serviceType);
				$('#simEffDate').val(simEffDate);
				$('#simEndDate').val(simEndDate);
			}
		}
	</script>
</head>
<body>
<br/><br/><br/>
<form id="queryForm" action="${ctx}/${pgId}/query.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<%-- 保留空白給上一頁的功能使用 --%>
	<%-- 設定頁次_分頁下拉選單及圖示用 --%>
	<input type="hidden" id="pages" name="pages" value="${queryForm.pages}"/>
</form>	
<form id="indexForm" action="${ctx}/${pgId}/index.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
</form>
<form id="insertForm" method="post" action="${ctx}/${pgId}/insert.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
</form>
<form id="updateForm" method="post" action="${ctx}/${pgId}/update.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" id="updateuserId" name="updatetuserId" value="${command.userId}"/>
</form>
<form id="deleteMapForm" method="post">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" id="delseqno" name="delseqno"/>
</form>
<form id="editEmailForm" method="post" action="${ctx}/${pgId}/email.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" id="editEmailUserId" name="emailUserId" />	
	<input type="hidden" id="editEmail" name="email" />	
</form>
<form id="resetPwdForm" method="post" action="${ctx}/${pgId}/doResetPwd.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" id="editEmailUserId" name="userId" />	
	<input type="hidden" id="editEmail" name="email" />	
</form>
<div class="mainDiv">
	<form id="smdd003fModForm" name="smdd003fModForm" method="post">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" name="deviceType" value="<c:out value='${command.tbDevice.deviceType}'/>" />
		<input type="hidden" id="actionType" value="<c:out value='${command.action}' />" />
		<table id="table1" class="modTableMaster" border="1" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2"  cellspacing="0">
			<tr>
				<!-- 會員編號 -->
				<th><spring:message code="smdd003f.customer.userid"/>：</th>
				<td>
					<input type="text" id="userId" 
							name="userId" value="${command.userId}" readonly/>
				</td>
				<!-- 中文姓名 -->
				<th><spring:message code="smdd003f.customer.user_name"/>：</th>
				<td>
					<input type="text" id="userName"name="userName" 
								value="${command.userName}" maxlength="50" />
				</td>
			</tr>
			
			<tr>
				<!-- *電子信箱 -->
				<th><span class="notNullCol">*</span><spring:message code="smdd003f.customer.email"/>：</th>
				<td>
					<input type="text" id="email" name="email" class="validate[required,custom[email]]" style="text-transform: lowercase"
							value="${command.email}" maxlength="100" />
				</td>
				<!-- *行動電話 -->
				<th><span class="notNullCol">*</span><spring:message code="smdd003f.customer.contact_phone"/>：</th>
				<td>
					<input type="text" id="contactPhone" name="contactPhone" class="validate[required]"
							value="${command.contactPhone}" maxlength="100" />
				</td>
			</tr>
			
			<tr>
				<!-- *會員狀態 -->
				<th><span class="notNullCol">*</span><spring:message code="smdd003f.customer.cust_status"/>：</th>
				<td>
				<c:choose>
	              <c:when test="${command.action eq 'detail'}">
	              	<c:forEach var="item" items="${model.custstatusaDscr}" varStatus="status">
	              		<c:if test="${item.key eq command.custStatus}">
						<input type="text" id="custStatus" name="custStatus" class="validate[required]"
								value="${item.value}"  readonly />
						</c:if>
					</c:forEach>
				   </c:when>	
				   <c:otherwise>	
					<select id="custStatus" name="custStatus"  class="validate[required]" >
						<c:forEach var="item" items="${model.custstatusaDscr}" varStatus="status">
							<c:choose>
	                        	<c:when test="${command.custStatus eq item.key}">
	                        		<option value="${item.key}" selected="selected">${item.value} </option>
	                       		</c:when>
	                       		<c:otherwise>
	                        		<option value="${item.key}">${item.value} </option>
	                      	 </c:otherwise>
	                  	 	</c:choose>
	                  	</c:forEach>
	             	</select>
	             </c:otherwise>
	            </c:choose>
				</td>
				<!-- 身分證號 -->
				<th><spring:message code="smdd003f.customer.cuid"/>：</th>
				<td>
					<input type="text" id="cuid" name="cuid" style="text-transform: uppercase"
							value="${command.cuid}"  maxlength="18"  />
				</td>
			</tr>
			
			<tr>
				<!-- 車牌號碼 -->
				<th><spring:message code="smdd003f.customer.car_no"/>：</th>
				<td>
					<input type="text" id="carNo" name="carNo" style="text-transform: uppercase"
							value="${command.carNo}" maxlength="20" />
				</td>
				<!-- 14天未使用DVR的累計次數 -->
				<th><spring:message code="smdd003f.customer.unuse_dvr_cnt"/>：</th>
				<td>
					<input type="text" id="unuseDvrCnt" name="unuseDvrCnt"  class="validate[custom[number]]"
							value="${command.unuseDvrCnt}" maxlength="10" />
				</td>
			</tr>
			
			<tr>
				<!-- 英文姓名 -->
				<th><spring:message code="smdd003f.customer.eng_name"/>：</th>
				<td>
					<input type="text" id="engName" name="engName" 
							value="${command.engName}" maxlength="100" />
				</td>
				<!-- 暱稱 -->
				<th><spring:message code="smdd003f.customer.nickname"/>：</th>
				<td>
					<input type="text" id="nickname" 
							name="nickname" value="${command.nickname}" maxlength="40" />
				</td>
			</tr>
		 
			<tr>
				<!-- 性別 -->
				<th><spring:message code="smdd003f.customer.sex"/>：</th>
				<td>
					<select id="sex" name="sex" >
	              	  <c:if test="${command.sex eq 'M'}">
	                 	<option value=""><spring:message code="select.options.0.label"/></option>
	                 	<option value="M" selected>男</option>
	                 	<option value="F">女</option>
	               	  </c:if>
	           		  <c:if  test="${command.sex eq 'F'}">
	                 		<option value=""><spring:message code="select.options.0.label"/></option>
		                  	<option value="M">男</option>
		                  	<option value="F" selected>女</option>
	                  </c:if>
	       			  <c:if  test="${command.sex eq null or command.sex eq ''}">
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
					 		name="dob" maxlength="10" value="${command.dob}" /> 
				</td>
			</tr>
		 
			<tr>
				<!-- 地址 -->
				<th><spring:message code="smdd003f.customer.addr"/>：</th>
				<td colspan="3">
					<input type="text" id="tprodid" name="addr"  maxlength="500"
							value="${command.addr}"  />
				</td>
			</tr>	
			
			<tr>
				<%-- 備註 --%>
				<th><spring:message code="smdd003f.customer.remarks"/>：</th>
				<td colspan="3">
					<textarea id="remarks" name="remarks" rows="3" cols="3">${command.remarks}</textarea>
				</td>
			</tr> 
			
			<tr>
				<!-- 註冊日期 -->
				<th><spring:message code="smdd003f.header6.label"/>：</th>
				<td>
					<input type="text" id="registerTime" name="registerTime" value="${command.registerTime}" readonly/>
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
					<input type="hidden" id="pwdStatus" name="pwdStatus" value="${command.psdStatus}" />
					<c:if test="${command.psdStatus eq 'FIRST'}">
						<input type="text" id="pwdStatusDscr" name="pwdStatusDscr" 
							value="<spring:message code="smdd003f.customer.pwd_status.first"/>"   maxlength="40" readonly/>
					</c:if>
					<c:if test="${command.psdStatus eq 'OK'}">
						<input type="text" id="pwdStatusDscr" name="pwdStatusDscr" 
							value="<spring:message code="smdd003f.customer.pwd_status.ok"/>"   maxlength="40" readonly/>
					</c:if>
					<c:if test="${command.psdStatus eq 'FORGET'}">
						<input type="text" id="pwdStatusDscr" name="pwdStatusDscr" 
							value="<spring:message code="smdd003f.customer.pwd_status.forget"/>"   maxlength="40" readonly/>
					</c:if>
				</td>
				<!-- 最近登入時間 -->
				<th><spring:message code="smdd003f.customer.app_login_time"/>：</th>
				<td>
					<input type="text" id="appLoginTime" name="appLoginTime" value="${command.appLoginTime}" readonly/>
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
			<tr>
				<td colspan="4"><spring:message code="smdd003f.label.bindDevice"/></td>
			</tr>
			<tr>
				<%-- DVR S/N--%>
				<th><spring:message code="omom003f.label.sn"/>：</th>
				<td>
					<input type="text" id="sn" name="sn"  maxlength="100" value="${command.sn}"  />
				</td>
				<%-- 設備狀態 --%>
				<th><spring:message code="omom003f.tbDevice.deviceStatusDscr"/>：</th>
				<td>
					<input type="text" id="deviceStatus" name="deviceStatus"  maxlength="100" value="${command.deviceStatus}"  />
				</td>
			</tr>
			<tr>
				<%-- 綁定日期 --%>
				<th><spring:message code="omom003f.tbDevice.bindDate"/>：</th>
				<td>
					<input type="text" id="bindDate" name="bindDate"  maxlength="100" value="${command.bindDate}" readonly />
				</td>
				<%-- 設備資料上傳時間 --%>
				<th><spring:message code="omom003f.tbDevice.uploadDate"/>：</th>
				<td>
					<input type="text" id="uploadDate" name="uploadDate"  maxlength="100" value="${command.uploadDate}"  readonly/>
				</td>
			</tr>
			<tr>
				<%-- 設備有效期間 --%>
				<th><spring:message code="omom003f.tbDevice.deviceTime"/>：</th>
				<td colspan="3">
					<input type="text" id="devEffDate" name="devEffDate" class="validate[past[#devEndDate]] inputY inputY_L"
								value="<fmt:formatDate value='${command.tbDevice.devEffDate}' type='Both' pattern='yyyy/MM/dd'/>" class="inputN_M" maxlength="40" />
					~
					<input type="text" id="devEndDate" name="devEndDate" class="validate[future[#devEffDate]] inputY inputY_L"
								value="<fmt:formatDate value='${command.tbDevice.devEndDate}' type='Both' pattern='yyyy/MM/dd'/>" class="inputN_M" maxlength="40" />
				</td>
			</tr>
			<tr>
				<%-- SIM卡門號 --%>
				<th><spring:message code="omom003f.tbDevice.simMobile"/>：</th>
				<td>
					<input type="text" id="simMobile" name="simMobile"  maxlength="100" value="${command.simMobile}"  />
				</td>
				<%-- 服務類別 --%>
				<th><spring:message code="omom003f.tbDevice.serviceType"/>：</th>
				<td>
					<select id="serviceType" name="serviceType">
						<option value=""><spring:message code="select.options.0.label"/></option>
						<c:forEach items="${model.serviceTypeDscr}" var="item">
							<option value="${item.key}"  <c:if test="${item.key == command.serviceType}">selected="selected"</c:if>>${item.value}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<%-- SIM卡有效期間 --%>
				<th><spring:message code="omom003f.tbDevice.simTime" /> : </th>
				<td colspan="3">
					<input type="text" id="simEffDate"name="simEffDate" class="validate[past[#simEndDate]] inputY inputY_L"
							value="<fmt:formatDate value='${command.tbDevice.simEffDate}' type='Both' pattern='yyyy/MM/dd'/>" maxlength="40" />
					~
					<input type="text" id="simEndDate"name="simEndDate" class="validate[future[#simEffDate]] inputY inputY_L"
								value="<fmt:formatDate value='${command.tbDevice.simEndDate}' type='Both' pattern='yyyy/MM/dd'/>" maxlength="40" />
				</td>
			</tr>
		</table>
	</form>
	
	<%-- 按鈕區域 --%>
	<div class="buttonDiv">
		<c:if test="${command.action ne 'detail'}">
			<input type="button" value="<spring:message code="confirm.label"/>"  class="btn" onclick="doConfirm();" />
		</c:if>
		<c:if test="${command.action eq 'update'}">
			<input type="button" value="<spring:message code="revert.label"/>"  class="btn" onclick="doRevert();" />
			<input type="button" value="<spring:message code="resetPwd.label"/>"  class="btn" onclick="doResetPwd();" />
			<c:if test="${not empty command.tbDevice.sn}">
				<input type="button" value="<spring:message code="omom003f.remove.device"/>"  class="btn" onclick="doRemoveDevice();" />
			</c:if>
			<input type="button" value="<spring:message code="editEmail.label"/>"  class="btn" onclick="doEditEmail();" />
		</c:if>
		<c:if test="${command.action eq 'insert'}">
			<input type="button" value="<spring:message code="clear.label"/>"  class="btn" onclick="doClear();" />
		</c:if>
		<c:if test="${command.action eq 'detail'}">
		<%-- show nothing --%>
		</c:if>
		<input type="button" value="<spring:message code="page.previous.label"/>" class="btn" onclick="doShowQuery();"/>
	</div>
</div>
</body>
</html>