<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%--
	共用 Public JavaScript
	version 1.0 
	
	change dialog position to fix IE8 & IE10
--%>
<%--
**menu頁面使用
顯示 LOV 視窗
name  = LOV 頁面名稱，對應到 tw.com.help 的 Controller 定義
param = 傳遞的參數，使用 form.serialize 收集即可
title = LOV title顯示名稱
level = 開啟LOV的層級,第一層時可不傳值
--%>

function doShowLOV(url, title, level) {
    var thisLevel = level == null ? 1 : level;
    var dialogId = "#dialogDiv_" + thisLevel;
    $("#lovFrame_"+thisLevel).attr("src", url);
    var dialogObj = $(dialogId);
    dialogObj.dialog({title : title + " - <spring:message code='lov.label'/>"});
    dialogObj.dialog("open");
    dialogObj.dialog("option", "position",  {my: "center", at: "center", of: window.parent.wrap, within: window.parent.wrap} );
    
}
