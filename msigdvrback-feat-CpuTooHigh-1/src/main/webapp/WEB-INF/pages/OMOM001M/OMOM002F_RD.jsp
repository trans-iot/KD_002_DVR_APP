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
		function doRemoveDevice(key){
			
			$("#remove_device_type").attr("value", $("#deviceType" + key).val());
			$("#remove_mac_address").attr("value", $("#macAddress" + key).val());
			$("#remove_sn").attr("value", $("#sn" + key).val());
			$("#remove_user_id").attr("value", $("#userId").val());
			$("#remove_userstamp").attr("value", $("#isReset" + key).val());
			var actionUrl = "${ctx}/OMOM003F/checkDeviceStatus.html";
			var param = $("#removeDeviceForm").serialize();
			doCwAjax(actionUrl, param, function(data){
				if (data.result == "${ajaxOk}") {
					var isResetValue = $('#mileageStatus' + key).val()
					if(isResetValue == ""){
						alert('<spring:message code="omom002f.reset.empty"/>');
						return false;
					}
					var actionUrl = "${ctx}/${pgId}/doRemoveDevice.html";
					$('#remove_is_reset').val(isResetValue)
					param = $("#removeDeviceForm").serialize();
					doCwAjax(actionUrl, param, ajaxOkDo);
				}
			});
		}
		function ajaxOkDo(data) {
			$("#removeDeviceQueryForm").submit()
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
<form id="removeDeviceQueryForm" method="post" action="${ctx}/OMOM002F/removeDeviceLov.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" id="removeDeviceUserId" name="userId" value="${command.userId}"/>
	<input type="hidden" id="removeDeviceEmail" name="email" value="${command.email}"/>
	<input type="hidden" id="removeDeviceUserName" name="userName" value="${command.userName}"/>
	<input type="hidden" id="removeDeviceMobilePhone" name="mobilePhone" value="${command.mobilePhone}"/>
</form>
<form id="removeDeviceForm" method="post" action="${ctx}/${pgId}/removeDevice.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" id="remove_device_type" name="device_type"/>
	<input type="hidden" id="remove_mac_address" name="mac_address"/>
	<input type="hidden" id="remove_sn" name="sn"/>
	<input type="hidden" id="remove_user_id" name="user_id"/>
	<input type="hidden" id="remove_is_reset" name="is_reset"/>
	<input type="hidden" id="remove_userstamp" name="userstamp"/>
	<input type="hidden" id="remove_trx_time" name="trx_time"/>
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
	                    <th width="10%"><spring:message code="omom002f.header21.label"/></th><%-- 設備種類 --%>
	                    <th width="10%"><spring:message code="omom002f.header22.label"/></th><%-- MAC ADDERESS --%>
	                    <th width="10%"><spring:message code="omom002f.header23.label"/></th><%-- SN --%>
	                    <th width="10%"><spring:message code="omom002f.header24.label"/></th><%-- 設備狀態--%>
	                    <th width="10%"><spring:message code="omom002f.header25.label"/></th><%-- 綁定日期 --%>
	                    <th width="10%"><spring:message code="omom002f.header26.label"/></th><%-- 行車紀錄是否歸零重算 --%>
	                    <th width="10%"><spring:message code="omom002f.header27.label"/></th><%-- 行車紀錄是否歸零重算 --%>
	                </tr>
	            </thead>
	            <tbody>
	                <c:if test="${list ne null and fn:length(list) eq 0}">
	                    <tr>
	                        <td colspan='9' class="alignC"><spring:message code='no.result.message'/></td>
	                    </tr>
	                </c:if>
	                <c:if test="${not empty list}">
	                    <c:forEach var="item" items="${list}" varStatus="statusouter">
	                        <tr>
	                        	<td>
		                            <c:forEach var="detypItem" items="${detyplist}" varStatus="status">
		                            	<c:if test="${detypItem.lookupCde eq  item.deviceType}">
		                            		<input type="text" value="${detypItem.dscr}"/>
		                            	</c:if>
		                            </c:forEach>
		                            <input type="hidden" value="${item.deviceType}" id="deviceType${statusouter.count}">
	                            </td>
	                            <td><input type="text" value="${item.macAddress}" id="macAddress${statusouter.count}"/></td>
	                            <td><input type="text" value="${item.sn}" id="sn${statusouter.count}"/></td>
	                            <td>
	                            
		                            <c:forEach var="destaItem" items="${destaList}" varStatus="status">
		                            	<c:if test="${destaItem.lookupCde eq  item.deviceStatus}">
		                            		<input type="text" value="${destaItem.dscr}" id="deviceStatus${statusouter.count}" />
		                            	</c:if>
		                            </c:forEach>
	                            </td>
	                            <td><input type="text" value="<fmt:formatDate value="${item.bindDate}" type="Both" pattern="yyyy/MM/dd"/>" id="bindDate${statusouter.count}"/></td>
	                        	<td>
		                        	<select class="select" id="mileageStatus${statusouter.count}" name="mileageStatus">
		                        		<option value="" selected><spring:message code="select.options.0.label"/></option>
										<option value="Y">Y</option>
										<option value="N">N</option>
									</select>
								</td>
								<td>
									<input type="button" class="btn" value="<spring:message code="omom002f.remove.device"/>" onclick="javascript:doRemoveDevice('${statusouter.count}');" />
	                        	</td>
	                        </tr>
	                    </c:forEach>
	                </c:if>
	            </tbody>
	        </table>    
	    </div>
	    <div class="buttonDiv">
	        <input type="button" class="btn" value="<spring:message code="omom004f.batch.insert.close.window"/>" onclick="javascript:doCloseWindow(this);" />
	    </div>
</div>
</body>
</html>