<!-- 
	重設密碼
	version 1.0 
	author alan.lin
	since 2012/12/13
 -->
<%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String getProtocol=request.getScheme();
	String getDomain=request.getServerName();
	String getPort=Integer.toString(request.getServerPort());
	String getPath = getProtocol+"://"+getDomain+":"+getPort+path+"/";
		
	String logoutService = getPath + "sso/index.jsp";
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="edge" />
	<script>
		$(document).ready(function(){
			bindValidation("modForm"); 
		});
		
		function doSave() {
			if (/\s/.test($("#renewpwd").val())  || /\s/.test($("#newpwd").val()) || /\s/.test($("#oldpwd").val()) ) {
				alert("密碼不可含有空格")
				return false;
			}
			var valid = $("#modForm").validationEngine('validate');
			if (valid) {
				//密碼驗證原則
				if(!pwdValidate($("#newpwd").val(), "<c:out value='${sessionData.sysUserId}' />")) {
					return false;
 				}
				$.ajax({
					type:'POST', 
					url: '${ctx}/sso/validatePwdHistory.html',
					data: {
						csrfPreventionSalt : '${csrfPreventionSalt}',
						pwd : $("#newpwd").val()
					},
					dataType: "json"
				})
				.done(function(data) {
					if(data.result == "OK") {
						var flag = confirm("<spring:message code='renewPwd.warning.label'/>");
						if (flag) {
							var param = $("#modForm").serialize();
							var url = "${ctx}/sso/chgPwdData.html";
							doCwAjaxSync(url, param, okDo);	
						}
					} else {
						alert('您的新密碼不可與之前使用過的密碼一樣');
					}
				});
			}
		}
		
		function okDo(data) {
			if (data.result == "${ajaxOk}") {
<%-- 				window.parent.location = "<%=logoutService%>"; --%>
				window.parent.location = "${ctx}/sso/index.jsp";
			}
		}
		
		function doCancel() {
			$("#oldpwd").val("");
			$("#newpwd").val("");
			$("#renewpwd").val("");
			window.parent.$("#chgPwdDiv").dialog("close");
		}
	</script>
</head>
<body>
	<br/><br/><br/>
	<div id="mainDiv">
		<form id="modForm" name="modForm" method="post" >
			<input type="hidden" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
			<table class="modTableMaster" border="1" bordercolordark="#ffffff" 
				   bordercolorlight="#595241" cellpadding="2" cellspacing="0" style="width:100%;">
				<tr>
					<th><spring:message code="oldPwd.label"/>：</th><%-- 舊密碼 --%>
					<td>
						<input type="password" id="oldpwd" name="oldpwd" class="inputY validate[required]" autocomplete="off"/>
					</td>
				</tr>
				<tr>
					<th><spring:message code="newPwd.label"/>：</th><%-- 新密碼 --%>
					<td>
						<input type="password" id="newpwd" name="newpwd" class="inputY validate[required]" autocomplete="off"/>
					</td>
				</tr>
				<tr>
					<th><spring:message code="renewPwd.label"/>：</th><%-- 再次新密碼 --%>
					<td>
						<input type="password" id="renewpwd" name="renewpwd" class="inputY validate[required, equals[newpwd]]" autocomplete="off"/>
					</td>
				</tr>
			</table>
		</form>
		
		<div class="buttonDiv">
			<input type="button" value="<spring:message code="save.label"/>" class="btn" onclick="javascript:doSave();" />
			<input type="button" value="<spring:message code="cancel.label"/>" class="btn" onclick="javascript:doCancel();" />
		</div>
		<div class="pwdRegex">
			<p>密碼修改原則：</p>
			<p>a. 長度至少為8個字元。</p>
			<p>b. 包含下列四種字元中的三種：</p>
			<p>&nbsp;&nbsp; 1. 英文大寫字元 (A到Z)  </p>
			<p>&nbsp;&nbsp; 2. 英文小寫字元 (a到z)  </p>
			<p>&nbsp;&nbsp; 3. 10個基本數字 (0到9)  </p>
			<p>&nbsp;&nbsp; 4. 非英文字母字元 (例如 !、$、#、%) </p>
			<p>c. 會員更改密碼時，5次內不得使用相同的密碼。 </p>
			<p>d. 不應訂為相同的英數字、連續英數字或連號數字(例如：aaa、abc、123...)</p>
			<p>e. 密碼與代號/帳號不應相同</p>
			<p>f. 避免使用他人容易取得之資料設為密碼(例如：英文名、生日或電話…等)</p>
			<p>g. 避免使用字典查得到的單字或機關名稱縮寫(例如：car、CYUT...等)</p>
		</div>
	</div>
</body>
</html>