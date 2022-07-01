<!-- 
    title UBI保單申請資料查詢
    version 1.0 
    author Bob
    since 2018/11/26
-->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<c:set var="pgId" value="OMOM005F" />
<html>
<head>
<title><spring:message code ="omom005f.title" /></title>
<script>
	$(document).ready(function(){
		bindCalendar("successApplyEffUbiDate");
		bindCalendar("successApplyEndUbiDate");
	})
    function doApplySuccess() {
		var c = confirm("<spring:message code='omom005f.success.apply.check'/>");
		if (!c) {
			return false;
		}
    	var actionUrl = "${ctx}/${pgId}/successApply.html";
		var param = $("#successApplyForm").serialize();
		doCwAjax(actionUrl, param, ajaxOkDo);
    }
  
    function doApplySuccessCancel(btn){
    	btn.disabled = true;
   		closeLOV();
        btn.disabled = false;
    }

	function ajaxOkDo(data) {
		if (data.result == "${ajaxOk}") {
			$("#workAreaFrame",window.parent.document).contents().find("#detailQueryForm").submit()		
			closeLOV();
		}
	}
    
	function autoCompleteEndUbiDate(item){
		if($('#successApplyEndUbiDate').val() != ""){
			return false
		}
		var startDate = new Date(item.value)
		if((startDate.getFullYear()%4==0 && startDate.getFullYear()%100!=0)||(startDate.getFullYear()%100==0 && startDate.getFullYear()%400==0)){
			if(startDate.getDate() == "29" && startDate.getMonth()+1 == 2){
				startDate.setFullYear( startDate.getFullYear() + 1 );
				startDate.setMonth(1)
				startDate.setDate(28);
			}else{
				startDate.setFullYear( startDate.getFullYear() + 1 );
			}
		}else{
			startDate.setFullYear( startDate.getFullYear() + 1 );
		}
		month = startDate.getMonth() + 1
        day = startDate.getDate()
        if((""+day).length == 1){
        	day = "0" + day
        }
        year = startDate.getFullYear()
		$('#successApplyEndUbiDate').val(year + "/" + month + "/" + day)
	}
	
</script>
<style>
</style>
</head>
<body>
<div class="mainDiv">
<br/><br/><br/>
    <form id="successApplyForm" name="successApplyForm" method="post" action="${ctx}/OMOM005F/successApplyLov.html">
		<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<input type="hidden" id="userId" name="userId" maxlength='80' value="${command.userId}"/>        
		<input type="hidden" id="applyNo" name="successApplyApplyNo" maxlength='80' value="${command.applyNo}"/>        
        <table id="table1" class="modTableMaster" border="1" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2"  cellspacing="0">
		   <tr>
				<%-- 保險服務人員 --%>
				<th><spring:message code="omom005f.vwCustUbiApply.servicer"/>：</th>
                <td><input type="text" id="successApplyServicer" name="successApplyServicer" maxlength='80' value="${command.servicer}"/></td>
				<th></th>
                <td></td>
            </tr>
            <tr>
				<%-- UBI車險起日 --%>
				<th><spring:message code="omom005f.vwCustUbiApply.effUbiDate"/>：</th>
                <td><input type="text" id="successApplyEffUbiDate" name=successApplyEffUbiDate maxlength='80' value="${command.effUbiDate}" onchange="javascript:autoCompleteEndUbiDate(this);" /></td>
				<%-- UBI車險到期日 --%>
				<th><spring:message code="omom005f.vwCustUbiApply.endUbiDate"/>：</th>
                <td><input type="text" id="successApplyEndUbiDate" name="successApplyEndUbiDate" maxlength='80' value="${command.endUbiDate}"/></td>
            </tr>
        </table>
    </form>
    <%-- 按鈕區域 --%>
    <div class="buttonDiv">
        <input type="button" class="btn" value="<spring:message code="omom005f.apply.success.submit"/>" onclick="javascript:doApplySuccess();" />
        <input type="button" class="btn" value="<spring:message code="omom005f.apply.success.cancel"/>" onclick="javascript:doApplySuccessCancel(this);" />
    </div>
</div>
</body>
</html>