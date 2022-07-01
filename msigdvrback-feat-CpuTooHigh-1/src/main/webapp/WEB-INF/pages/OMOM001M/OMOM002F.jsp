<!-- 
    title 會員服務異動
    version 1.0 
    author Bob
    since 2018/10/31
-->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
	<script>
	 $(document).ready(function(){
		 $("#tabFrame").attr('src',"${ctx}/OMOM002F/doQuery001.html?userId=" + $("#userId").val());
    	if("${searchResult}" != ""){
    		if("${searchResult}" == "noexit"){
    			alert("<spring:message code='no.result.message'/>")
    		}
    	}
	 });
	
	
	function doSearch() {
		
		if($("#userIdSearch").val().trim() != "" || $("#emailSearch").val().trim() != "" || $("#carNoSearch").val().trim() != ""){
			$("#queryForm").submit();
		}else{
			alert('<spring:message code="omom002f.search.no.input"/>')
		}
    }
    
    function doClear() {
        $("#clearForm").submit();
    }
    
    function doRemoveDevice(){
    	alert("doRemoveDevice")
    }
    
    function doResetMileAge(){
    	alert("doResetMileAge")
    	
    }
    
    function tagQuery(queryTag){
   		$("#tabFrame").attr('src',"${ctx}/OMOM002F/"+queryTag+".html?userId=" + $("#userId").val());
    }
    
	function doRemoveDeviceLov(obj){
		obj.disabled = true;
		var param = encodeURI($('#removeDeviceQueryForm').serialize());
		
		var title = "<spring:message code='omom002f.remove.device.title'/>";
		var prefixUrl = "${ctx}/OMOM002F/removeDeviceLov.html?";
		doOpenLov(title, param, prefixUrl, "800px", "250px");
		
		obj.disabled = false;
	}
	
	function doResetCarNoLov(obj){
		obj.disabled = true;
		var param = encodeURI($('#resetCarNoQueryForm').serialize());
		
		var title = "<spring:message code='omom002f.reset.carNo'/>";
		var prefixUrl = "${ctx}/OMOM002F/resetCarNoLov.html?";
		doOpenLov(title, param, prefixUrl, "800px", "250px");
		
		obj.disabled = false;
	}

	function doUserIdSearchLov(obj){
		obj.disabled = true;
		var param = $('#resetCarNoQueryForm').serialize();
		
		var title = "<spring:message code='omom002f.userId.search.title'/>";
		var prefixUrl = "${ctx}/OMOM002F/userIdSearchLov.html?";
		doOpenLov(title, param, prefixUrl, "800px", "500px");
		
		obj.disabled = false;
	}
	
	function doOpenLov(title, param, prefixUrl, width, height){
		window.parent.doShowNewLOV(title, param, prefixUrl, width, height, getCurrentLevel() + 1 );
	}
	
	function doUpdateTbCustCar(obj){
		obj.disabled = true;
		var param = encodeURI($('#updateTbCustCarQueryForm').serialize());
		
		var title = "<spring:message code='omom002f.update.tbCustCar'/>";
		var prefixUrl = "${ctx}/OMOM002F/updateTbCustCarLov.html?";
		doOpenLov(title, param, prefixUrl, "800px", "250px");
		
		obj.disabled = false;
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
<form id="removeDeviceQueryForm" method="post" action="${ctx}/OMOM002F/removeDeviceLov.html">
<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" id="removeDeviceUserId" name="userId" value="${command.userId}"/>
	<input type="hidden" id="removeDeviceEmail" name="email" value="${command.email}"/>
	<input type="hidden" id="removeDeviceUserName" name="userName" value="${command.userName}"/>
	<input type="hidden" id="removeDeviceMobilePhone" name="mobilePhone" value="${command.mobilePhone}"/>
</form>
<form id="resetCarNoQueryForm" method="post" action="${ctx}/OMOM002F/resetCarNoLov.html">
<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<input type="hidden" id="removeDeviceUserId" name="userId" value="${command.userId}"/>
	<input type="hidden" id="removeDeviceEmail" name="email" value="${command.email}"/>
	<input type="hidden" id="removeDeviceUserName" name="userName" value="${command.userName}"/>
	<input type="hidden" id="removeDeviceMobilePhone" name="mobilePhone" value="${command.mobilePhone}"/>
</form>
<form id="clearForm" method="post" action="${ctx}/OMOM002F/index.html">
<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
</form>
	<form id="queryForm" name="queryForm" method="post" action="${ctx}/OMOM002F/query.html">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<table id="table1" class="modTableMaster" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2"  cellspacing="0">
			<tr>
				<!-- 會員編號-->
				<th><spring:message code="omom002f.search1"/>：</th>
				<td>
					<input type="text" id="userIdSearch" 
							name="userId" value="${queryForm.userId}" maxlength="20" />
					<span id="userIdLov" class="pointer"><img src="${ctx}/images/lov.gif" class="imgBox" onclick="javascript:doUserIdSearchLov(this);" /></span>
				</td>
			</tr>
			<tr>
				<!-- 電子信箱 -->
				<th><spring:message code="omom002f.search2"/>：</th>
				<td>
					<input type="text"  id="emailSearch"
							name="email" value="${queryForm.email}" maxlength="100"/>
				</td>
			</tr>
			<tr>
				<!-- 車號 -->
				<th><spring:message code="omom002f.search3"/>：</th>
				<td>
					<input type="text" id="carNoSearch" 
							name="carNo" value="${queryForm.carNo}"  maxlength="20"/>
				</td>
			</tr>
			
			
		</table>
	</form>
	<%-- 按鈕區域 --%>
    <div class="buttonDiv">
        <input type="button" class="btn" value="<spring:message code="searchBtn.label"/>" onclick="javascript:doSearch();" />
        <input type="button" class="btn" value="<spring:message code="clear.label"/>" onclick="javascript:doClear();" />
    </div>
	<c:if test="${not empty command}">
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
				<tr>
					<!-- 身分證號 -->
					<th><spring:message code="omom002f.header5.label"/>：</th>
					<td>
						<input type="text"  id="cuid" 
								name="cuid" value="${command.cuid}" maxlength="20" />
					</td>
					<!-- 會員狀態 -->
					<th><spring:message code="omom002f.header6.label"/>：</th>
					<td>
						<c:forEach var="custaItem" items="${custaList}" varStatus="status">
	                       	<c:if test="${custaItem.lookupCde eq  command.custStatus}">
	                       		<input type="text"  id="custStatus" 
								name="custStatus" value="${custaItem.dscr}" maxlength="20" />
	                       	</c:if>
                        </c:forEach>
						
					</td>
				</tr>
				<tr>
					<!-- 事故通知聯絡人姓名 -->
					<th><spring:message code="omom002f.header7.label"/>：</th>
					<td>
						<input type="text"  id="accCtcName" 
								name="accCtcName" value="${command.accCtcName}" maxlength="20" />
					</td>
					<!-- 事故通知聯絡人手機 -->
					<th><spring:message code="omom002f.header8.label"/>：</th>
					<td>
						<input type="text"  id="accCtcMobile" 
								name="accCtcMobile" value="${command.accCtcMobile}" maxlength="20" />
					</td>
				</tr>
	        </table>    
	    </div>
	    <%-- 按鈕區域 --%>
	    <div class="buttonDiv">
	        <input type="button" class="btn" value="<spring:message code="omom002f.remove.device"/>" onclick="javascript:doRemoveDeviceLov(this);" />
	        <input type="button" class="btn" value="<spring:message code="omom002f.reset.carNo"/>" onclick="javascript:doResetCarNoLov(this);" />
	        <input type="button" class="btn" value="<spring:message code="omom002f.update.tbCustCar"/>" onclick="javascript:doUpdateTbCustCar(this);" />
	    </div>
    
    
		<%-- 明細區塊 --%>
		<div class="tabsDiv">
			<%-- 頁籤 --%>
			<ul id="tabDiv" class="tabs">
				<li><a onclick="tagQuery('doQuery001')" href="#" ><spring:message code="omom002f.subtitle1"/></a></li>
				<li><a onclick="tagQuery('doQuery002')" href="#" ><spring:message code="omom002f.subtitle2"/></a></li>
			</ul>
			<div class="tab_container">
	            <div id="tabs-1" class="tab_content">
	                <iframe name="tabFrame" id='tabFrame' style="height: 300px" width='100%' frameBorder="0" src=""></iframe>
	            </div>			
			</div>
			
		</div>
	</c:if>
</div>
</body>
</html>