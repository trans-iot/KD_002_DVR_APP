<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/javascript; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	response.setHeader("Pragma", "No-Cache");
	response.setHeader("Cache-Control", "No-Cache");
	response.setDateHeader("Expires", 0);
%>
function pwdValidate(pwd, sysUserId) {
	var count = 0;
	if(pwd == undefined) {
		<%--請輸入密碼--%>
		alert('<spring:message code="pwd.label" />');
		return false;
	}
	
	if(pwd.length < 8) {
		<%--新密碼長度至少為8個字元--%>
		alert('<spring:message code="newPwd.length.label" />');
		return false;
	}	
	
	if(sysUserId == pwd) {
		<%--密碼不可與帳號相同--%>
		alert('<spring:message code="pwd.different.label" />');
		return false;
	}
	
	//密碼須符合下列四種中的三種
	if(validateUpperCase(pwd)) {
		count++;
	}
	if(validateLowerCase(pwd)) {
		count++;
	}
	if(validateNumber(pwd)) {
		count++;
	}
	if(validateSpecial(pwd)) {
		count++;
	}
	
	if(count < 3) {
		<%--新密碼不符合密碼修改原則--%>
		alert('<spring:message code="newPwd.role.label" />');
		return false;
	}
	
	if(!validateContinuous(pwd)) {
		<%--新密碼不符合密碼修改原則--%>
		alert('<spring:message code="newPwd.role.label" />');
		return false;
	}
	
	return true;
	
}


//符合大寫
function validateUpperCase(pwd) {
	var regex = new RegExp('[A-Z]', 'g');
	if(regex.test(pwd)) {
		return true;
	}
	return false;
}

//符合小寫
function validateLowerCase(pwd) {
	var regex = new RegExp('[a-z]', 'g');
	if(regex.test(pwd)) {
		return true;
	}
	return false;
}

//符合數字
function validateNumber(pwd) {
	var regex = new RegExp('[0-9]', 'g');
	if(regex.test(pwd)) {
		return true;
	}
	return false;
}

//符合特殊符號
function validateSpecial(pwd) {
	var regex = new RegExp('[^\u4e00-\u9fa5a-zA-Z0-9]', 'g');
	if(regex.test(pwd)) {
		return true;
	}
	return false;
}

//不可連續三碼相同, 連號英數字
function validateContinuous(pwd) {
	for(var i = 0 ; i < pwd.length ; i++) {
		if(i > 0) {
			var firstChar = pwd.substring(i-1, i);
			var middleChar = pwd.substring(i, i+1);
			var lastChar = pwd.substring(i+1, i+2);
			if(lastChar != '') {
				
				var firstCharCode = firstChar.charCodeAt();
				var middleCharCode = middleChar.charCodeAt();
				var lastCharCode = lastChar.charCodeAt();
				
				if((Math.abs(firstCharCode - middleCharCode) == 0 && Math.abs(lastCharCode - middleCharCode) == 0) ||
				   (middleCharCode - firstCharCode == 1 && middleCharCode - lastCharCode == -1) || 
				   (middleCharCode - firstCharCode == -1 && middleCharCode - lastCharCode == 1) ) {
					return false;
				}
			}
		}
	}
	return true;
}
