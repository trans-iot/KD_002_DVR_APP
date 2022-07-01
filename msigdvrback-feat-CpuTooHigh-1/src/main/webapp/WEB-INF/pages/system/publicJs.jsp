<%--
	共用 Public JavaScript
	version 1.0 
	author Alan Lin
	date 2013/10/11	
--%>
<%@ page language="java" contentType="content-type:application/x-javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
%>
	<%--
	  開啟LOV用
	 @param name
	 @param param
	 @param title
	 --%>
	function showLOV(name, param, title){
		var newUrl = "${ctx}/" + name + "/index.html?" + Math.round(new Date().getTime())+'&'+param;
		var level = getCurrentLevel() + 1;
		window.parent.doShowLOV(newUrl, title, level);
	}
	<%--
		檢查ajax回傳結果，依回傳結果顯示(alert)不同訊息
	--%>
	function checkAjaxResult(data) {
		if (data.result == "${ajaxOk}") {
			if($.trim(data.message) != ""){
				alert(data.message);
			}
			return true;
		}
		else if (data.result == "${ajaxLoginTimeout}") {
			alert("please re-login, user session timeout.");
			return false;
		}	
		else if (data.result == "${ajaxFail}") {
			alert("query encounter problem.");
			return false;
		}
		else {
			if($.trim(data.message) != ""){
				alert(data.message);
			}
			return false;
		}
	}
	<%-- 
		ajax共用方法(非同步) - 查詢頁面用
		url		request url
		param		傳入參數
		okCall		ajax執行成功後呼叫的function name
		hideImg	是否顯示"處理中"圖示,不傳值:顯示,有傳入值:不顯示
	--%>
	function doCwAjax(url, param, okCall, hideImg) {
		return _doAjax(url, param, okCall, hideImg, true);
	}
	
	<%-- 
		ajax共用方法(同步) - 維護頁面用
		url 		request url
		param		傳入參數
		okCall		ajax執行成功後呼叫的function name
		hideImg	是否顯示"處理中"圖示,不傳值:顯示,有傳入值:不顯示
	--%>
	function doCwAjaxSync(url, param, okCall, hideImg) {
		return _doAjax(url, param, okCall, hideImg, false);
	}

	function _doAjax(url, param, okCall, hideImg, setAsync) {
		var showImg = hideImg == null ? true : false ;
		var ajaxResultOk = false;
		if(showImg){
			$("#loadingImg").toggle();	
		}
	
		//	$.getJSON(url+'?' + Math.round(new Date().getTime()), param)
		var request = $.ajax({type:'POST', url: url, data:param, cache: false, dataType: "json", async : setAsync})
		.fail(function(data) {
			if(showImg){
				$("#loadingImg").toggle();	
			}
			checkAjaxResult(data);
		})
		.done(function(data) {
			ajaxResultOk = checkAjaxResult(data);
			if(typeof(okCall)==='function') {
				okCall.call(this, data);
			}
			if(showImg){
				$("#loadingImg").toggle();
			}
		});
		return ajaxResultOk;
	}
	<%--  
		檢查Ajax結果是否正確
		for validation use
	--%>
	function cwAjax(field, rules, i, options){
		if (field.attr("ajaxChk") != "${ajaxOk}" && field.attr("ajaxChk") != "") {
			return '<spring:message code="ajax.submit.check"/>';
		}
	}
	
	<%-- 
		依"生效日期"及"建立日期"判斷資料是否可刪除 
		crDateId     建立日期id
		effDateId    生效日期id, 若無生效日欄位可不傳值
		回傳值               true:可刪除 ,false:不可刪除       
	--%>
	function chkDateBeforeDelete(crDateId, effDateId) {
		var today = "<fmt:formatDate value='${today}' type='Both' pattern='yyyy/MM/dd'/>";
		var crDate = $("#" + crDateId).val();
		if (crDate < today) {
			if (effDateId != null) {
				var effDate = $("#" + effDateId).val();
				if (effDate <= today) {
					alert("<spring:message code ='effdate.effect.message'/>");
					return false;
				}
			} else {
				alert("<spring:message code ='delete.fail.today.greater.crDate.message'/>");
				return false;
			}
		}
		return true;
	}
	<%--  
		檢查生效日是否可修改
		id		生效日id
		return	true:可修改
	--%>
	function chkEffDateForEdit(id, canEqual){
		var effDate = $("#"+id).val();
		var today = "<fmt:formatDate value='${today}' type='Both' pattern='yyyy/MM/dd'/>";
		if( effDate != ""){
			if(canEqual){
				if(effDate >= today)
			return true;
			}else{
				if(effDate > today)
				return true;
			}
		}
		return false;
	}
        <%--
	 * init hidden iframe for targe of upload in order to 
	 * @returns void
	 --%>
	function initIfrmForUpload() {
		$("body").append('<iframe id="uploadIfrm" name="uploadIfrm" style="display: none" />');
	}
	<%--  
    ajax上傳檔案至  server
    uploadForm  上傳的 jQuery form 物件或 id
    callback    回呼函式
    --%>
	function cwAjaxUpload(uploadForm, callback) {
	    var uploadForm = typeof(uploadForm) == "string" ? $("#" + uploadForm) : uploadForm;
	    uploadForm.attr("action", "${ctx}/common/upload.html");
	    uploadForm.attr("target", "uploadIfrm");
	    uploadForm.submit();
	    // 在jQuey上註冊一個function, 執行 callback方法用
	    $.cw = $.cw || {};
	    $.cw.afterUploaded = function(pathInfo) {
	    	callback.call(null, pathInfo);
	    };
	}
