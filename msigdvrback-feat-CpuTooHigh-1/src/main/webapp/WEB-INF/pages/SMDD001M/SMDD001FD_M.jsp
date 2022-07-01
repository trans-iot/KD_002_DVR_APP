<!-- 
    title 參考資料代碼維護查詢 明細編輯
    version 1.0 
    author Gary
    since 2013/01/09
 -->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<script>
var ctx = {
    action: "${command.action}",
    initRowSize: null,
    loginUser: "${loginUser}",
    dtlTBody: null,
    warnMsg: '<spring:message code="sys.not.save" />',
    warnMsg2: '<spring:message code="sys.not.save.clear" />'
};
function doShowIndex() {
    $("#indexForm").submit();
}

function doShowQuery() {
    if (isModified()) {
        if (confirm(ctx.warnMsg)) {
        	toQueryPage();
        }
    } else {
    	toQueryPage();
    }
}

function toQueryPage() {
    if ("${queryForm.queryClicked}") {
        $("#queryForm").submit();
    } else{
        doShowIndex();
    }
}

function doRevert() {
    $("#updateForm").submit();
}

function doClear() {
	var crudDetailQueryForm = $("#crudDetailQueryForm");
    if (isModified()) {
        if (confirm(ctx.warnMsg2)) {
        	crudDetailQueryForm.submit();
        }
    } else {
    	crudDetailQueryForm.submit();
    }
}

//以修改日期排序
function orderBy(obj) {	
	if (isModified()) {
		if (confirm(ctx.warnMsg2)) {
		    doOrderBy(obj);
		}
    } else {
    	doOrderBy(obj);
    }
}

function doOrderBy(obj) {
	$("#crudOrderByClause").val("datestamp desc");
    $("#crudPages").val("1");
    $("#crudDetailQueryForm").submit();
}

function doDelete(idx) {
	// 刪除新增的 row
    if (idx >= ctx.initRowSize) {
    	return confirm("<spring:message code='delete.confirm.message'/>");
    } else {
    	var result = false;
    	if (confirm("<spring:message code='delete.confirm.message'/>")) {
    		var lookupType = ctx.dtlTBody.find(":input[name='crudDetailForm[" + idx + "].lookupType']").val();
    		var lookupCde = ctx.dtlTBody.find(":input[name='crudDetailForm[" + idx + "].lookupCde']").val();
    		// console.log("lookupType: " + lookupType + ", lookupCde: " + lookupCde);
            doCwAjaxSync("${ctx}/SMDD001F/deleteDetail.html", "lookupType=" + lookupType + "&lookupCde=" + lookupCde+"&csrfPreventionSalt="+$("#csrfPreventionSalt").val(), function(data) {
            	// console.log(data);
                if (data.result == "${ajaxOk}") {
                	result = true;
                }
            });
        }
    	return result;
    }
}
/**
 * 轉換唯讀的欄位變成可以edit的欄位, function doCrudDetailUpdate才做真正送出
 */
function doUpdate(uptBtn, lookupType, lookupCde) {
	var thisTr = $(uptBtn).parents("tr");
	// 可編輯狀態回復原狀
	if (thisTr.find(":input[name$='detailModifyCheck']").length) {
		$.each("dscr value type1 type2 type3 ".split(" "), function(i, col) {
            var input = thisTr.find("input[name$='" + col + "']");      
            input.removeClass("inputY_F")
                 .addClass("inputN_F")
                 // .removeAttr("style")
                 .attr("readonly", true)
                 .val(input.data("oriValue"));
            if (col == "dscr") {
                input.prev().hide();
            }
            if (col == "value") {
                input.addClass("alignR");
            }
        });
		thisTr.find("td:eq(0) input[name$='detailModifyCheck']").remove();
	// 變成可編輯狀態
	} else {
		$.each("dscr value type1 type2 type3 ".split(" "), function(i, col) {
	        var input = thisTr.find("input[name$='" + col + "']");      
	        input.removeClass("inputN_F")
	             .addClass("inputY_F")
	             .attr("readonly", false)
	             .data("oriValue", input.val());
	        if (col == "dscr") {
	            input.prev().show();
	        }
	        if (col == "value") {
	        	input.removeClass("alignR");
	        }
	    });
		var idx = thisTr.attr("id").replace("row", "");
		//edit by Marks 2020/06/19 編輯時移除千分位逗點
		var numberValue = $('#value'+idx).val();
		$('#value'+idx).val(numberValue.replace(/,/g,''));
		
		thisTr.find("td:eq(0)").prepend('<input type="hidden" name="crudDetailForm[' + idx +'].detailModifyCheck" value="1" />');
	}
	return false;
}

function doCrudDetailUpdate() {
	if (!confirm('<spring:message code="confirm.update.message"/>')) {
        return false;
    }
	if (!isModified()) {
		alert('<spring:message code="not.modified.label"/>');
		return false;
	}
	var actionUrl = "${ctx}/SMDD001F/crudDetailUpdate.html";
    var valid = $("#crudDetailForm").validationEngine('validate');
    if (valid) {
        var params = $("#crudDetailForm").serialize();
        doCwAjaxSync(actionUrl, params, function(data) {
        	// console.log(data);
            if (data.result == "${ajaxOk}") {
                // $("#crudPages").val("1");
                $("#crudDetailQueryForm").submit();
            }
        });
    }
}

function adjRadioBtn() {
    if (ctx.action == "insert") {
        $("#sysIndicY").attr("checked", true);
    }else if(ctx.action == "update"){
    	
    } else {
        if ("${command.sysIndic}" == "Y") {
            $("#sysIndicY").attr("checked", true);
        } else {
            $("#sysIndicN").attr("checked", true);
        }
    }
}

function isModified() {
	return $(":input[name$='detailModifyCheck']").length ? true : false;
}

$(document).ready(function(){
    var actionType = $("#actionType").val();
    var readOnlyList = null;
    var msg = "";
    
    // 調整radio btn
    adjRadioBtn();
    // 調整 moduleCde select
    $("#moduleCde").val("${command.moduleCde}"); 
    // 自動套用CSS
    bindFormCSS("modForm", "detail");
    ctx.dtlTBody = $("#dtlTBody");
    ctx.initRowSize = ctx.dtlTBody.find("tr").size();
    // 綁定驗正
    bindValidation("crudDetailForm");
    
	<%-- 輸入英文自動轉換大寫 --%>
	$('#crudDetailTable thead tr :input[name$="lookupCde"]').each(function(){
		autoUpperCase($(this).attr('id'));
	});
	
    // 初始化tab crud操作
    $("#crudDetailTable").bindDetail({
    	index: ctx.initRowSize,
    	prepend: true,
    	addButton: "addBtn",
    	setCSS: false,
    	actType: "update",
    	delFunc: "doDelete"
    });
    // 調整換頁事件, 修改中提示不可換頁
    // .parents("span").find("")
    var pageSelect = $("#currentPage"), changeFn = pageSelect[0].onchange;
    if (changeFn) {
    	// 移除select事件
    	pageSelect.removeAttr("onchange");
    	pageSelect.change(function(e) {
    		if (isModified()) {
                if (confirm(ctx.warnMsg)) {
                	changeFn.call(pageSelect[0]);
                }
            } else {
            	changeFn.call(pageSelect[0]);
            }
    	});
    }
	// 移除click事件: 第一頁, 上一頁, 下一頁, 最後一頁
	pageSelect.parents("span").find("img").each(function() {
		var span = $(this).parent(), clickFn = span[0].onclick;
		if (clickFn) {
			span.removeAttr("onclick");
			span.click(function() {
	            if (isModified()) {
            		if (confirm(ctx.warnMsg)) {
            			clickFn.call(span[0]);
            		}
	            } else {
	            	clickFn.call(span[0]);
	            }
			});
		}
	});
    // console.log("pageSelect.parents(span).find(img).size()= " + pageSelect.parents("span").find("img").size());
});
function toNoFloat(value) {
  return ~~value;
}
</script>
</head>
<body>
<div class="mainDiv">
<br/><br/><br/>
    <form id="indexForm" action="${ctx}/SMDD001F/index.html">
    	<input type="hidden" id="csrfPreventionSalt" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
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
                <td><input type="text" class="validate[funcCall[cwMax[80]]]" id="valueDscr" name="valueDscr" maxlength="80" value="${command.valueDscr}" /></td>
                <%--類別一備註--%>
                <th><spring:message code="smdd001f.header4.label"/>：</th>
                <td><input type="text" class="validate[funcCall[cwMax[80]]]" id="type1Dscr" name="type1Dscr" maxlength="80" value="${command.type1Dscr}" /></td>
            </tr>
            <tr><%--類別二備註--%>
                <th><spring:message code="smdd001f.header5.label"/>：</th>
                <td><input type="text" class="validate[funcCall[cwMax[80]]]" id="type2Dscr" name="type2Dscr" maxlength="80" value="${command.type2Dscr}" /></td>
                <%--類別三備註--%>
                <th><spring:message code="smdd001f.header6.label"/>：</th>
                <td><input type="text" class="validate[funcCall[cwMax[80]]]" id="type3Dscr" name="type3Dscr" maxlength="80" value="${command.type3Dscr}" /></td>
            </tr>
            <tr>
                <%--是否為系統參數--%>
                <th><spring:message code="smdd001f.param.or.not.label"/>：</th>
                <td colspan="3">
                	<c:if test="${command.action ne 'update'}">
	                	<input type="radio" name="sysIndic" id="sysIndicY" value="Y" /><spring:message code="yes.label"/>
    	                <input type="radio" name="sysIndic" id="sysIndicN" value="N" /><spring:message code="no.label"/>
                	</c:if>
               		<c:if test="${command.action eq 'update'}">
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
                    <th><spring:message code="cr.date.label"/>：</th>
                    <td>${loginUser}</td>
                    <%--建立日期--%>
                    <th><spring:message code="cr.date.label"/>：</th>
                    <td><fmt:formatDate value='${today}' type='Both' pattern='yyyy/MM/dd HH:mm:ss'/></td>
                </tr>
            </c:if>
            <c:if test="${command.action eq 'update' or command.action eq 'detail' }">
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
        <input type="button" value="<spring:message code="smdd001f.back.to.master"/>" class="btn" onclick="doShowQuery();"/>
    </div>
    <form id="crudDetailQueryForm" name="crudDetailQueryForm" method="post" action="${ctx}/SMDD001F/crudDetail.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="crudDetailSeq" name="seq" value="${command.lookupType}" />    
        <%-- 排序欄位 --%>
        <input type="hidden" id="crudOrderByClause" name="orderByClause" value="${crudDetailQueryForm.orderByClause}"/>
        <%-- 頁次 --%>
        <input type="hidden" id="crudPages" name="pages" value="${crudDetailQueryForm.pages}"/>
        <%-- 每頁筆數 --%>
        <input type="hidden" id="perPageNum" name="perPageNum" value="${crudDetailQueryForm.perPageNum}" >
    </form>  
    <form id="crudDetailForm" name="crudDetailForm" method="post" action="${ctx}/SMDD001F/crudDetailUpdate.html">
        <input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
        <input type="hidden" id="crudDetailSeq" name="seq" value="${command.lookupType}" />  
        <%-- detail block --%>
        <div id="detailBlock">
            <table class="modTableDetail" border=0;>
                <thead>
                    <tr><%-- 分頁 --%>
                        <th class="link" colspan="10">
                            
							<table width="97%" border="0" cellpadding="0" cellspacing="0">
								<tr>
    									<td style="color:#cedb00;background-color:#2B2B2B; width:30%;" align="left">
                                <img id="addBtn" src="${ctx}/images/NEW.png" title="<spring:message code="insert.label"/>" class="imgBox">
									</td>
									<td style="color:#cedb00;background-color:#2B2B2B; width:60%;" align="right">
								<cw:separate totalRows="${totalCount}" perPageNum="${queryForm.perPageNum}" formName="crudDetailQueryForm"  
			 				      firstImg="${firstImg}" leftImg="${leftImg}" rightImg="${rightImg}" lastImg="${lastImg}"  
			 				      firstImgEnd="${firstImgEnd}" leftImgEnd="${leftImgEnd}" rightImgEnd="${rightImgEnd}"  lastImgEnd="${lastImgEnd}"/> 
								
									</td>
									<td style="color:#cedb00;background-color:#2B2B2B; width:10%;" align="right"><spring:message code="count.all.label"/><span id="totalRow">${totalCount}</span><spring:message code="count.unit.label"/></td>
								</tr>
							</table>
							                            
                        </th>  
                    </tr>
                    
                </thead>            
            </table>
            <table id="crudDetailTable" class="modTableDetail">
                <thead>
                    
                    <tr class="nowrapHeader listHeader">
                        <th width="6%"><spring:message code="edit.label"/></th><%-- 編輯 --%>
                        <th width="8%"><spring:message code="smdd001f.detail.lookupCde"/></th><%-- 參數代碼 --%>
                        <th width="20%"><spring:message code="smdd001f.detail.dscr"/></th><%-- 說明 --%>
                        <th width="8%"><spring:message code="smdd001f.detail.value"/></th><%-- 數值 --%>
                        <th width="8%"><spring:message code="smdd001f.detail.type1"/></th><%-- 類別1 --%>
                        <th width="8%"><spring:message code="smdd001f.detail.type2"/></th><%-- 類別2 --%>
                        <th width="8%"><spring:message code="smdd001f.detail.type3"/></th><%-- 類別3 --%>
                        <th width="7%"><spring:message code="userstamp.label"/></th><%-- 修改人員 --%>
                        <th width="7%"><spring:message code="datestamp.label"/>
                            <span class="pointer" onClick="<c:if test='${not empty list}'>orderBy(this); return false</c:if>" class="imgBox">
                                <img width="15" src="${ctx}/images/desc.png" title="<spring:message code='sort.label'/>" border="0"/>
                            </span>
                        </th><%-- 修改日期 --%>
                    </tr>
                    <tr>
                        <td class="alignC">
                            <input type="hidden" name="crudDetailForm[].detailModifyCheck" />
                            <input type="hidden" name="crudDetailForm[].isNew" value="1" />
                            <input type="hidden" name="crudDetailForm[].lookupType" value="${command.lookupType}" />
                            <span class="pointer"><img src="${ctx}/images/DELETE.png" title="<spring:message code="delete.label"/>" class="imgBox"/></span>
                        </td>
                        <td nowrap>
                        	<span class="notNullCol">*</span>
                        	<input type="text" id="lookupCde" name="crudDetailForm[].lookupCde" class="validate[required,funcCall[cwMax[10]]] inputY_F" maxlength="10" />
                        </td>
                        <td nowrap>
                        	<span class="notNullCol">*</span>
                        	<input type="text" name="crudDetailForm[].dscr" class="validate[required,funcCall[cwMax[200]]] inputY_F" maxlength="200" maxlength="80" />
                        </td>
                        <td><input type="text" name="crudDetailForm[].value" class="validate[custom[number],cwMax[9]] inputY_F" maxlength="9" /></td>
                        <td><input type="text" name="crudDetailForm[].type1" class="inputY_F validate[cwMax[200]]" maxlength="200" value=""/></td>
                        <td><input type="text" name="crudDetailForm[].type2" class="inputY_F validate[cwMax[200]]" maxlength="200" value=""/></td>
                        <td><input type="text" name="crudDetailForm[].type3" class="inputY_F validate[cwMax[200]]" maxlength="200" value=""/></td>
                        <td><input type="text" name="crudDetailForm[].userstamp" class="inputN_F" value="${loginUser}" readonly /></td>
                        <td><input type="text" name="crudDetailForm[].datestamp" class="alignC inputN_F" value='<fmt:formatDate value='${today}' type='Both' pattern='yyyy/MM/dd'/>' readonly /></td>
                    </tr>
                </thead>
                <tbody id="dtlTBody">
                    <c:if test="${not empty list}">
                        <c:forEach var="item" items="${list}" varStatus="status">
                            <tr id="row${status.index}">
                                <td class="alignC" id="tdOprt${status.index}">
                                    <input type="hidden" name="crudDetailForm[${status.index}].lookupType" value="${command.lookupType}" />
                                    <input type="hidden" name="crudDetailForm[${status.index}].crUser" value="${item.crUser}" />
                                    <input type="hidden" name="crudDetailForm[${status.index}].crDate" value="<fmt:formatDate value='${item.crDate}' type='Both' pattern='yyyy/MM/dd HH:mm:ss'/>" />
                                    <span class="pointer" onclick="doUpdate(this, '${command.lookupType}', '${item.lookupCde}'); return false;"><img src="${ctx}/images/UPDATE.png" title="<spring:message code="modify.label"/>" class="imgBox"/></span>
                                    <span class="pointer"><img src="${ctx}/images/DELETE.png" title="<spring:message code="delete.label"/>" class="imgBox"/></span>
                                </td>
                                <td nowrap><input type="text" id="lookupCde${status.index}" name="crudDetailForm[${status.index}].lookupCde" value="${item.lookupCde}" class="validate[required,funcCall[cwMax[10]]] inputN_F" maxlength="10" readonly /></td>
                                <td nowrap>
                                	<span class="notNullCol" style="display: none">*</span>
                                	<input type="text" id="dscr${status.index}"  name="crudDetailForm[${status.index}].dscr" value="${item.dscr}" class="validate[required,funcCall[cwMax[200]]] inputN_F" maxlength="200" maxlength="80" readonly />
                                </td>
                                <%-- 將 value 用JSTL 取除小數 --%>
                                <fmt:formatNumber type="number" value="${item.value}" maxFractionDigits="0" var="value" /> 
                                <td><input type="text" id="value${status.index}" name="crudDetailForm[${status.index}].value" class="inputN_F alignR validate[custom[number],cwMax[9]]" maxlength="9" value='${value}' readonly />   </td>
                                <td><input type="text" id="type1${status.index}" name="crudDetailForm[${status.index}].type1" class="inputN_F validate[cwMax[200]]" value="${item.type1}" maxlength="200" readonly /></td>
                                <td><input type="text" id="type2${status.index}" name="crudDetailForm[${status.index}].type2" class="inputN_F validate[cwMax[200]]" value="${item.type2}" maxlength="200" readonly /></td>
                                <td><input type="text" id="type3${status.index}" name="crudDetailForm[${status.index}].type3" class="inputN_F validate[cwMax[200]]" value="${item.type3}" maxlength="200" readonly /></td>
                                <td><input type="text" id="userstamp${status.index}" name="crudDetailForm[${status.index}].userstamp" class="inputN_F" value="${item.userstamp}" readonly /></td>
                                <td><input type="text" id="datestamp${status.index}" name="crudDetailForm[${status.index}].datestamp" class="alignC inputN_F" value="${item.datestamp}" readonly /></td>
                            </tr>
                        </c:forEach>
                    </c:if>
                </tbody>
            </table>
            <%-- 明細按鈕區域 --%>
            <div class="buttonDiv">
                <input type="button" class="btn" value="<spring:message code="confirm.label"/>" onclick="doCrudDetailUpdate(this);"/>
                <input type="button" class="btn" value="<spring:message code="clear.label"/>" onclick="doClear();" />
            </div>
        </div>
    </form>
</div>
</body>
</html>