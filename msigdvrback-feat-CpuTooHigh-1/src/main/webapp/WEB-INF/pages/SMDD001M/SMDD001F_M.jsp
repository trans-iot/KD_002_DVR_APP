<!-- 
    title 參考資料代碼維護查詢 新增
    version 1.0 
    author Gary
    since 2013/01/07
 -->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<script>
var ctx = {
	action: "${command.action}"
};
function doShowIndex() {
    $("#indexForm").submit();
}

function doShowQuery() {
    if (${queryForm.queryClicked}) {
        $("#queryForm").submit();
    }else{
        doShowIndex();
    }
}

function doClear() {
    $("#insertForm").submit();
}

function doConfirm() {
	if (!confirm('<spring:message code="confirm.update.message"/>')) {
		return false;
	}
    var action = $("#actionType").val();
    if (action=='insert') {
        actionUrl = "${ctx}/SMDD001F/insertData.html";
    } else if (action=='update') {
        actionUrl = "${ctx}/SMDD001F/updateData.html";
    }
    var valid = $("#modForm").validationEngine('validate');
    if (valid) {
        var param = $("#modForm").serialize();
        doCwAjax(actionUrl, param, ajaxOkDo);
    } else {
        $("#modForm").validationEngine();
    }
}

function ajaxOkDo(data) {
    if (data.result == "${ajaxOk}") {
        // 回上一頁, 並保留原本的查詢條件
        doShowQuery();
    }
}

function doRevert() {
    $("#updateForm").submit();
}

// 以修改日期排序
function orderBy(obj){ 
    $("#orderByClause").val("datestamp desc");  
    $("#detailQueryPages").val("1");
    $("#detailQueryForm").submit();
}

function adjRadioBtn() {
	if (ctx.action == "insert") {
        $("#sysIndicY").attr("checked", true);
    } else if(ctx.action == "detail"){
		
    } else {
        if ("${command.sysIndic}" == "Y") {
        	$("#sysIndicY").attr("checked", true);
        } else {
        	$("#sysIndicN").attr("checked", true);
        }
    }
}

$(document).ready(function(){
    var actionType = $("#actionType").val();
    var readOnlyList = null;
    var msg = "";
    if (actionType == "insert") {
        msg = "<spring:message code ='insert.label' />";
    } else if (actionType == "update") {
    	readOnlyList = ['lookupType'];
        msg = "<spring:message code ='update.label' />";
    } else if (actionType == "detail") {
    	// 隱藏必key欄位  * 符號
        $(".notNullCol").hide();
    	// 顯示detail區塊
    	$("#detailBlock").show();
    	// 
        msg = "<spring:message code='detail.label' />";
    }
    // 調整radio btn
    adjRadioBtn();
    // 自動套用CSS
    bindFormCSS("modForm", actionType, readOnlyList);
    bindValidation("modForm");
    
	<%-- 輸入英文自動轉換大寫 --%>
	autoUpperCase("lookupType");
});
</script>
</head>
<body>
<div class="mainDiv">
<br/><br/><br/>
    <form id="indexForm" action="${ctx}/SMDD001F/index.html">
    	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    </form>
    <form id="queryForm" action="${ctx}/SMDD001F/query.html">
    	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <%-- 保留空白給上一頁的功能使用 --%>
        <%-- 設定頁次_分頁下拉選單及圖示用 --%>
        <input type="hidden" id="pages" name="pages" value="${queryForm.pages}"/>
    </form> 
    <form id="insertForm" method="post" action="${ctx}/SMDD001F/insert.html">
    	<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
    </form>
    <form id="updateForm" method="post" action="${ctx}/SMDD001F/update.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="updateSeq" name="seq" value="${command.lookupType}"/> 
    </form>
    <form id="modForm" name="modForm" method="post">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="actionType" name="action" value="${command.action}" />
        <input type="hidden" id="seq" name="seq" value="${command.lookupType}" /> 
        <table class="modTableMaster" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2" cellspacing="0" >
            <tr><%--參考資料種類--%>
                <th><span class="notNullCol">*</span><spring:message code="smdd001f.header1.label"/>：</th>
                <td><input type="text" class="validate[required,funcCall[cwMax[10]]]" id="lookupType" name="lookupType" maxlength="10" value="${command.lookupType}" /></td>
                <%-- 名稱 --%>
                <th><span class="notNullCol">*</span><spring:message code="smdd001f.header2.label"/>：</th>
                <td><input type="text" class="validate[required,funcCall[cwMax[120]]]" id="dscr" name="dscr" value="${command.dscr}" /></td>
            </tr>
            <tr><%--數值備註--%>
                <th><spring:message code="smdd001f.header3.label"/>：</th>
                <td><input type="text" class="validate[funcCall[cwMax[120]]]" id="valueDscr" name="valueDscr" maxlength="120" value="${command.valueDscr}" /></td>
                <%--類別一備註--%>
                <th><spring:message code="smdd001f.header4.label"/>：</th>
                <td><input type="text" class="validate[funcCall[cwMax[120]]]" id="type1Dscr" name="type1Dscr" maxlength="120" value="${command.type1Dscr}" /></td>
            </tr>
            <tr><%--類別二備註--%>
                <th><spring:message code="smdd001f.header5.label"/>：</th>
                <td><input type="text" class="validate[funcCall[cwMax[120]]]" id="type2Dscr" name="type2Dscr" maxlength="120" value="${command.type2Dscr}" /></td>
                <%--類別三備註--%>
                <th><spring:message code="smdd001f.header6.label"/>：</th>
                <td><input type="text" class="validate[funcCall[cwMax[120]]]" id="type3Dscr" name="type3Dscr" maxlength="120" value="${command.type3Dscr}" /></td>
            </tr>
            <tr>
                <%--是否為系統參數--%>
                <th><spring:message code="smdd001f.param.or.not.label"/>：</th>
                <td colspan="3">
                	<c:if test="${command.action ne 'detail'}">
	                	<input type="radio" name="sysIndic" id="sysIndicY" value="Y" /><spring:message code="yes.label"/>
    	                <input type="radio" name="sysIndic" id="sysIndicN" value="N" /><spring:message code="no.label"/>
                	</c:if>
                	<c:if test="${command.action eq 'detail'}">
	                	<c:if test="${command.sysIndic eq 'Y'}">
        	        		<spring:message code="yes.label"/>
	                	</c:if>
	                	<c:if test="${command.sysIndic eq 'N'}">
    	            		<spring:message code="no.label"/>
	                	</c:if>
                	</c:if>
                </td>
            </tr>
            <c:if test="${command.action eq 'insert'}">
                <tr><%-- 建立人員 --%>
                    <th><spring:message code="cr.user.label"/>：</th>
                    <td>${command.crUser}</td>
                    <%--建立日期--%>
                    <th><spring:message code="cr.date.label"/>：</th>
                    <td><fmt:formatDate value='${today}' type='Both' pattern='yyyy/MM/dd HH:mm:ss'/></td>
                </tr>
            </c:if>
            <c:if test="${command.action ne 'insert'}">
                <tr><%-- 修改人員 --%>
                    <th><spring:message code="userstamp.label"/>：</th>
                    <td>${command.userstamp}</td>
                    <%--修改日期--%>
                    <th><spring:message code="datestamp.label"/>：</th>
                    <td>${command.datestamp}</td>
                </tr>
            </c:if>
        </table>
    </form>
    <%-- 按鈕區域 --%>
    <div class="buttonDiv">
        <c:if test="${command.action ne 'detail'}">
            <input type="button" value="<spring:message code="confirm.label"/>" class="btn" onclick="doConfirm();" />
        </c:if>
        <c:if test="${command.action eq 'update'}">
            <input type="button" value="<spring:message code="revert.label"/>" class="btn" onclick="doRevert();" />
        </c:if>
        <c:if test="${command.action eq 'insert'}">
            <input type="button" value="<spring:message code="clear.label"/>" class="btn" onclick="doClear();" />
        </c:if>
        <input type="button" value="<spring:message code="page.previous.label"/>" class="btn" onclick="doShowQuery();"/>
    </div>
    <%-- detail block --%>
    <div id="detailBlock" style="display: none">
        <form id="detailQueryForm" name="detailQueryForm" method="post" action="${ctx}/SMDD001F/detail.html">
            <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
            <input type="hidden" id="detailSeq" name="seq" value="${command.lookupType}"/>     
            <%-- 排序欄位 --%>
            <input type="hidden" id="orderByClause" name="orderByClause" value="${detailQueryForm.orderByClause}"/>
            <%-- 頁次 --%>
            <input type="hidden" id="detailQueryPages" name="pages" value="${detailQueryForm.pages}"/>
            <%-- 每頁筆數 --%>
            <input type="hidden" id="perPageNum" name="perPageNum" value="${detailQueryForm.perPageNum}" >
        </form>
        <table class="resultTable">
            <thead>
                <tr><%-- 分頁 --%>
                    <th class="link" colspan="11">
                        <span style="float:right;">
                            <spring:message code="count.all.label"/><span id="totalRow">${totalCount}</span><spring:message code="count.unit.label"/>
                            <cw:separate totalRows="${totalCount}" perPageNum="${detailQueryForm.perPageNum}" formName="detailQueryForm"  
                              firstImg="${firstImg}" leftImg="${leftImg}" rightImg="${rightImg}" lastImg="${lastImg}"  
                              firstImgEnd="${firstImgEnd}" leftImgEnd="${leftImgEnd}" rightImgEnd="${rightImgEnd}"  lastImgEnd="${lastImgEnd}"/> 
                        </span>
                    </th>  
                </tr>
                <tr class="nowrapHeader listHeader">
                    <th><spring:message code="smdd001f.detail.lookupType"/></th><%-- 參考資料種類 --%>
                    <th width="10%"><spring:message code="smdd001f.detail.lookupCde"/></th><%-- 參數代碼 --%>
                    <th><spring:message code="smdd001f.detail.dscr"/></th><%-- 說明 --%>
                    <th><spring:message code="smdd001f.detail.value"/></th><%-- 數值 --%>
                    <th><spring:message code="smdd001f.detail.type1"/></th><%-- 類別1 --%>
                    <th><spring:message code="smdd001f.detail.type2"/></th><%-- 類別2 --%>
                    <th><spring:message code="smdd001f.detail.type3"/></th><%-- 類別3 --%>
                    <th><spring:message code="cr.user.label"/></th><%-- 建立人員 --%>
                    <th><spring:message code="cr.date.label"/></th><%-- 建立日期 --%>
                    <th><spring:message code="userstamp.label"/></th><%-- 修改人員 --%>
                    <th><spring:message code="datestamp.label"/>
                        <button onClick="<c:if test='${not empty list}'>orderBy(this);</c:if>" class="imgBox">
                            <img width="15" src="${ctx}/images/desc.png" title="<spring:message code='sort.label'/>" border="0"/>
                        </button>
                    </th><%-- 修改日期 --%>
                </tr>
            </thead>
            <tbody>
                <c:if test="${list ne null and fn:length(list) eq 0}">
                    <tr><td colspan='11' class="alignC"><spring:message code='no.result.message'/></td></tr>
                </c:if>
                <c:if test="${not empty list}">
                    <c:forEach var="item" items="${list}" varStatus="status">
                        <tr>
                            <td><input type="text" value="${item.lookupType}"/></td>
                            <td><input type="text" value="${item.lookupCde}"/></td>
                            <td><input type="text" value="${item.dscr}"/></td>
                            <td><input type="text" value="${item.value}"/></td>
                            <td><input type="text" value="${item.type1}"/></td>
                            <td><input type="text" value="${item.type2}"/></td>
                            <td><input type="text" value="${item.type3}"/></td>
                            <td><input type="text" value="${item.crUser}"/></td>
                            <td><input type="text" class="alignC" value='<fmt:formatDate value="${item.crDate}" type="Both" pattern="yyyy/MM/dd"/>' /></td>
                            <td><input type="text" value="${item.userstamp}"/></td>
                            <td><input type="text" class="alignC" value='<fmt:formatDate value="${item.datestamp}" type="Both" pattern="yyyy/MM/dd"/>' /></td>
                        </tr>
                    </c:forEach>
                </c:if>
            </tbody>
        </table>    
    </div>
</div>
</body>
</html>