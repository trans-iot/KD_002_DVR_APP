<!-- 
    title 最新消息維護
    version 1.0 
    author Bob
    since 2018/10/26
-->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title><spring:message code ="omom004f.title" /></title>
<script>
    function doSearch() {
        $("#batchInsertQueryForm").submit();
    }
    
    function doClear() {
        $("#clearForm").submit();
    }
    
    
    $(document).ready(function(){
    	// 調整編輯欄位的button padding
    	$("td[id^='editTd'] button").css("padding", "0");
        bindFormCSS("queryForm", "query");
    });
    
    function doCloseWindow(btn, key){
    	btn.disabled = true;
        $("#settingSeqNo").val(key);
        $("#settingForm").submit();
        btn.disabled = false;
    }
    
    function checkboxValidate(btn, key){
    	if(btn.checked) {
    		var actionUrl = $("#checkboxValidateForm").attr("action"); 
    		$("#checkedUserId").attr("value", key);
    		$("#checkedSeqNo").attr("value", $("#seqNo").val());
			var param = $("#checkboxValidateForm").serialize();
			doCwAjax(actionUrl, param, function(data){
				if (data.result == "${ajaxOk}") {
					
				}
				else if(data.result == "${ajaxQueryFail}"){
					alert('<spring:message code="omom004f.userId.already.exit" />')
					$(btn).attr('checked',false);
				}
			});
        }
    }
    function checkboxAllValidate(btn){
    	if(btn.checked) {
	    	var checkedUserIdArray = [];
	    	$( "input[name='check']" ).each(function() {
		   	 	checkedUserIdArray.push($( this ).val())
		   	 	$( this ).attr('checked',true);
	   	  	});
	    	var checkedUserIdArrayJsonString = JSON.stringify(checkedUserIdArray);
    		var actionUrl = $("#checkboxValidateAllForm").attr("action"); 
    		$("#checkedUserIdArray").attr("value", checkedUserIdArrayJsonString);
    		$("#checkedSeqNoAll").attr("value", $("#seqNo").val());
			var param = $("#checkboxValidateAllForm").serialize();
			doCwAjax(actionUrl, param, function(data){
				if (data.result == "${ajaxOk}") {
					
				}
				else if(data.result == "${ajaxQueryFail}"){
					var jsonData = JSON.parse(data.map.duplicatedArray);
					var errMessage = ""
					for (var i = 0; i < jsonData.length; i++) {
					    var duplicatedCheckedUserId = jsonData[i];
					    errMessage = errMessage + duplicatedCheckedUserId + '<spring:message code="omom004f.userId.already.exit" />\n'
						$("#check"+ duplicatedCheckedUserId).attr('checked',false);
					}
					alert(errMessage)
				}
			});
        }else {
        	$( "input[name='check']" ).each(function() {
		   	 	$( this ).attr('checked',false);
	   	  	});
        }
    }
    
    function doConfirm(){
    	var listSize = $("#listSize").val();
    	var batchInsertArray = []
    	var seqNo = $("#seqNo").val()
    	for(var i = 1; i <= listSize; i ++){
    		var tempUserId = $("#userId" + i).val()
    		if($("#check" + tempUserId).attr('checked')){
	    		var tempObj = {
	    			userId:	tempUserId,
	    			seqNo:	seqNo
	    		}
	    		batchInsertArray.push(tempObj)
    		}
    	}
    	if(batchInsertArray.length == 0){
    		alert('<spring:message code="not.checked.label" />')
    		return false
    	}
    	var batchInsertArrayJsonString = JSON.stringify(batchInsertArray);
		var actionUrl = $("#doInsertBatchForm").attr("action"); 
		$("#batchInsertArray").attr("value", batchInsertArrayJsonString);
		var param = $("#doInsertBatchForm").serialize();
		doCwAjax(actionUrl, param, function(data){
			if (data.result == "${ajaxOk}") {
				$("#confirmButton").prop('disabled', true);
			}
			else if(data.result == "${ajaxInsertFail}"){
				
			}
			else if(data.result == "${ajaxQueryFail}"){
				
			}
		});
    	
    }
</script>
<style>
</style>
</head>
<body>
<div class="mainDiv">
<br/><br/><br/>
    <form id="batchInsertQueryForm" name="batchInsertQueryForm" method="post" action="${ctx}/OMOM004F/batchInsertQuery.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" value="${listSize}" name="listSize" id="listSize"/>
		<input type="hidden" value="${seqNo}" name="seqNo" id="seqNo"/>
        <table class="queryTable" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2" cellspacing="0">
            <tr>
				<%-- 會員編號 --%>
				<th><spring:message code="omom004f.batch.insert.search1"/>：</th>
                <td><input type="text" id="userId" name="userId" maxlength='80' value="${batchInsertQueryForm.userId}"/></td>
				<%-- 姓名 --%>
				<th><spring:message code="omom004f.batch.insert.search2"/>：</th>
                <td><input type="text" id="userName" name="userName" maxlength='80' value="${batchInsertQueryForm.userName}"/></td>
            </tr>
            <tr>
				<%-- 電子信箱 --%>
				<th><spring:message code="omom004f.batch.insert.search3"/>：</th>
                <td><input type="text" id="email" name="email" maxlength='80' value="${batchInsertQueryForm.email}"/></td>
				<%-- 車號 --%>
				<th><spring:message code="omom004f.batch.insert.search4"/>：</th>
                <td><input type="text" id="carNo" name="carNo" maxlength='80' value="${batchInsertQueryForm.carNo}"/></td>
            </tr>
        </table>
    </form>
    <%-- 按鈕區域 --%>
    <div class="buttonDiv">
        <input type="button" class="btn" value="<spring:message code="searchBtn.label"/>" onclick="javascript:doSearch();" />
        <input type="button" class="btn" value="<spring:message code="clear.label"/>" onclick="javascript:doClear();" />
    </div>
    <div id="blockB">
        <table class="resultTable" id="mainTable">
            <thead>
                <tr class="nowrapHeader listHeader">
                    <th width="10%"><spring:message code="omom004f.batch.insert.header1.label"/><input type="checkbox" name="checkAll" id="checkAll" onchange="checkboxAllValidate(this)"/></th><%-- 選取 --%>
                    <th width="10%"><spring:message code="omom004f.batch.insert.header2.label"/></th><%-- 會員編號 --%>
                    <th width="10%"><spring:message code="omom004f.batch.insert.header6.label"/></th><%-- 會員狀態 --%>
                    <th width="10%"><spring:message code="omom004f.batch.insert.header3.label"/></th><%-- 姓名 --%>
                    <th width="10%"><spring:message code="omom004f.batch.insert.header4.label"/></th><%-- 電子信箱 --%>
                    <th width="10%"><spring:message code="omom004f.batch.insert.header5.label"/></th><%-- 車號 --%>
                </tr>
            </thead>
            <tbody>
                <c:if test="${list ne null and fn:length(list) eq 0}">
                    <tr>
                        <td colspan='13' class="alignC"><spring:message code='no.result.message'/></td>
                    </tr>
                </c:if>
                <c:if test="${not empty list}">
                    <c:forEach var="item" items="${list}" varStatus="status">
                        <tr>
                            <td class="alignC">
                            	<c:if test="${item.custStatus eq 'A'}">
	                            	<input type="checkbox" name="check" id="check${item.userId}" value="${item.userId}" onchange="checkboxValidate(this,'${item.userId}')" />
                            	</c:if>
							</td>
                            <td><input type="text" id="userId${status.count}" value="${item.userId}"/></td>
                            <c:set var="refreshSent" value="false"/>
                            <c:forEach items="${custstatusaDscr}" var="custstatusaDscr">
								<c:choose>
						 			<c:when test="${custstatusaDscr.key eq 	item.custStatus}">
						 				 <c:set var="refreshSent" value="true"/>
										 <td><input type="text" value="${custstatusaDscr.key}  -  ${custstatusaDscr.value}"/></td>
									</c:when>
								</c:choose>
							</c:forEach>
							<c:choose>
						 		<c:when test="${refreshSent eq false}">
										<td><input type="text" value="${item.custStatus}  -   "/></td>
								</c:when>
							</c:choose>
                            <td><input type="text" id="userName${status.count}" value="${item.userName}"/></td>
                            <td><input type="text" id="email${status.count}" value="${item.email}"/></td>
                            <td><input type="text" id="carNo${status.count}" value="${item.carNo}"/></td>
                        </tr>
                    </c:forEach>
                    <%-- 按鈕區域2 --%>
                </c:if>
            </tbody>
        </table>  
	    <div class="buttonDiv">
	        <input type="button" class="btn" value="<spring:message code="confirm.label"/>" id="confirmButton" onclick="javascript:doConfirm();" />
	        <input type="button" class="btn" value="<spring:message code="omom004f.batch.insert.close.window"/>" onclick="javascript:doCloseWindow(this,${seqNo});" />
	    </div>
    </div>
    <form id="clearForm" method="post" action="${ctx}/OMOM004F/batchInsert.html">
    	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    	<input type="hidden" value="${seqNo}" name="seqNo"/>
    </form>
 	<form id="settingForm" method="post" action="${ctx}/OMOM004F/setting.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="settingSeqNo" name="settingSeqNo" />
    </form>
 	<form id="checkboxValidateForm" method="post" action="${ctx}/OMOM004F/checkUserIdExit.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="checkedUserId" name="checkedUserId" />
        <input type="hidden" id="checkedSeqNo" name="checkedSeqNo" />
    </form>
 	<form id="checkboxValidateAllForm" method="post" action="${ctx}/OMOM004F/checkUserIdExitAll.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="checkedUserIdArray" name="checkedUserIdArray" />
        <input type="hidden" id="checkedSeqNoAll" name="checkedSeqNoAll" />
    </form>
 	<form id="doInsertBatchForm" method="post" action="${ctx}/OMOM004F/doBatchInsert.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="batchInsertArray" name="batchInsertArray" />
    </form>
</div>
</body>
</html>