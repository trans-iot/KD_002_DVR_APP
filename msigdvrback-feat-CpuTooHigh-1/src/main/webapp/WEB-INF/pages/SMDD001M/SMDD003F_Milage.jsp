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

			readOnlyList = ["userId","userName","email","carNo"];
			bindCalendar("dateStart", true); <%-- 起始 --%>
			bindCalendar("dateEnd", true); <%-- 結束 --%>

			<%-- 查詢條件區塊套用CSS--%>
			bindFormCSS("milageQueryForm", null, readOnlyList);
			bindValidation("milageQueryForm");
		});
		
		function isModified() {
			return $(":input[name$='detailModifyCheck']").length ? true : false;
		}

		function doSearch() {
			 $("#milageQueryForm").submit();
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
<div class="mainDiv">
<br/><br/><br/>
<form id="milageQueryForm" action="${ctx}/${pgId}/queryMilage.html" method="POST">
	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
	<table  class="modTableMaster" border="1" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2"  cellspacing="0">
	    <tr>
	        <%-- 會員編號 --%>
	        <th><spring:message code="smdd003f.search1"/>：</th>
	        <td><input type="text" id="userId" name="userId"  maxlength='80' value="${milageQueryForm.userId}" readonly/></td>
	        <%-- 電子信箱 --%>
	        <th><spring:message code="smdd003f.search3"/>：</th>
	        <td><input type="text" id="email" name="email" maxlength='80' value="${milageQueryForm.email}" readonly/></td>
	    </tr>
	    <tr>
	    	<%-- 中文姓名--%>
	        <th><spring:message code="smdd003f.search2"/>：</th>
	        <td><input type="text" id="userName" name="userName" maxlength='80' value="${milageQueryForm.userName}" readonly/></td>
	      	
	       <%-- 車牌號碼  --%>
			<th><spring:message code="smdd003f.customer.car_no"/>：</th>
			<td><input type="text" id="carNo" name="carNo" style="text-transform: uppercase" value="${milageQueryForm.carNo}" maxlength="40" readonly/></td>
	    </tr>
	    <tr>
            <%-- 日期起訖 --%>
            <th><span class="notNullCol">*</span><spring:message code="smdd003f.search6"/>：</th>
            <td>
            	<input type="text" id="dateStart" name="dateStart" class="inputY inputY_L validate[required]" maxlength='30' value="${milageQueryForm.dateStart}"/>
         	</td>
         	<th style="text-align: center;">~</th>
         	<td>
            	<input type="text" id="dateEnd" name="dateEnd" class="inputY inputY_L validate[required]"  maxlength='30' value="${milageQueryForm.dateEnd}"/>
            </td>
        </tr>
	</table>
</form>
<%-- 按鈕區域 --%>
<div class="buttonDiv">
    <input type="button" class="btn" value="<spring:message code="searchBtn.label"/>" onclick="javascript:doSearch();" />
    <input type="button" class="btn" value="<spring:message code="page.previous.label"/>" onclick="javascript:doShowQuery();" />
</div>
<div id="blockB">
        <table class="resultTable" id="mainTable">
            <thead>
            	<tr class="nowrapHeader listHeader">
            		<th><spring:message code="smdd003f.label.mileage" /> </th>
            	</tr>
            </thead>
            <tbody>
            	 <c:if test="${totalMileage ne null && empty totalMileage}">
                    <tr>
                        <td colspan='12' class="alignC"><spring:message code='no.result.message'/></td>
                    </tr>
                </c:if>
                 <c:if test="${not empty totalMileage}">
	            	<tr>
	            		<td>${totalMileage}</td>
	            	</tr>
            	</c:if>
            </tbody>
        </table>
</div>

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
	
</div>
</body>
</html>