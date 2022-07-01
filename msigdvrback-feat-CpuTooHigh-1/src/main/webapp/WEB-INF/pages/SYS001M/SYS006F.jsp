<!-- 
	群組權限設定
	version 1.0 
	author alan.lin
	since 2012/12/13
 -->
 <%@ include file="/WEB-INF/pages/system/include.jsp" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title><spring:message code ="sys006f.title" /></title>
	<script>
	    var splitChar = '${cwConstants.splitChar}';
	    var arrayChar = '${cwConstants.arrayChar}';
	    
		$(document).ready(function(){
			
			$('#treeDiv :checkbox').each(function() {
				$(this).click(function () {
					var id = $(this).attr("id"); 
					checkIt(id);
				});
			});
			
			$(".imgOpen").click(function() {
				var imgSrc = $(this).attr("src");
				if (imgSrc.indexOf("open")>=0) {
					$(this).next().hide();
					imgSrc = imgSrc.replace("open", "close");
				}
				else {
					$(this).next().show();
					imgSrc = imgSrc.replace("close", "open");
				}
				$(this).attr("src", imgSrc);
			});
			
			$("#sourceRoleId").change(function() {
				if ($(this).val()!="") {
					$("#copyRoleId").attr("disabled", false);
				}
				else {
					$("#copyRoleId").attr("disabled", true);
				}
			});
			
			$('#roleId').change(function() {
				if ($(this).val()!="") {
					var param = "roleId="+$(this).val()+"&csrfPreventionSalt="+$("#csrfPreventionSalt").val();
					var url = "${ctx}/SYS006F/ajaxLoad.html";
					doCwAjaxSync(url, param, selectRecord, null)
				}
				else {
					$('#treeDiv :checkbox').each(function() {
						$(this).attr("checked", false);
					});
				}
			});
			
			$("#copyRoleId").click(function() {
				if ($("#sourceRoleId").val()!="") {
					var param = "roleId="+$("#sourceRoleId").val()+"&csrfPreventionSalt="+$("#csrfPreventionSalt").val();
					var url = "${ctx}/SYS006F/ajaxLoad.html";
					doCwAjaxSync(url, param, selectSrcRecord, null);
				}
			});
			
			$("#SPASV3").click(function() {
				var _checked = $(this).prop('checked');
				if(_checked){
					doSelectAll();
				}else{
					doClear();
				}
			});
			
			
			//產生indeterminate 
			$('input[name^="src'+splitChar+'"]').change(function() {
				//先判斷自己是否為checked
				var _checked = $(this).prop('checked');
				var _name = $(this).prop('name');
				setChked(_name);
				
				//更改SPASV3按鈕的狀態
				chkAllchecked();
			});
			
		});
		
		//檢查該層模組是否全選or全不選or有些有選，並操作上層模組的狀態
		function setChked(comName){
			
			var comNameArray = comName.split(splitChar);
			
			//判斷上層checkbox狀態的flag
			var flag = "";
			
			//長度等於2 表示 他的上層只剩 'src'字串  跳脫
			if(comNameArray.length==2){
				return;
			}
			
			//取得上層模組字串 
			var comModName = bindAryModStr(comNameArray);
			
			var trueCnt =0;
			var falCnt =0;
			
			$('input[name^="'+comModName+splitChar+'"]').each(function() {
				var isChk = $(this).prop('checked');
				if(isChk){
					trueCnt++;
				}else{
					falCnt++;
				}
				if (trueCnt>0 && falCnt>0) {
					return;
				}
			});
			
			/*  trueCnt =0 表示全都沒勾  上層checked = false 
			 *  falCnt =0 表示全都有勾  上層checked = true
			 *  其他狀況   上層indeterminate
			 */ 
			$('#'+comModName).removeAttr('indeterminate');
			if (trueCnt==0) {
				$('#'+comModName).prop('checked',false);
			} else if (falCnt==0) {
				$('#'+comModName).prop('checked',true);
			} else if (trueCnt!=0&&falCnt!=0) {
				$('#'+comModName).prop('indeterminate',true);
			}
			setChked(comModName);
		}
		
		//確認畫面上checkbox是否全勾選or全不勾選  而變更SPASV3的點選狀態
		function chkAllchecked(){
			var trueCnt =0;
			var falCnt =0;
			$('input[name^="src'+splitChar+'"]').each(function() {
				//先判斷自己是否為checked
				var _checked = $(this).prop('checked');
				
				if(_checked){
					trueCnt++;
				}else{
					falCnt++;
				}
				if (trueCnt>0 && falCnt>0) {
					return;
				}
			});
			
			$('#SPASV3').removeAttr('indeterminate');
			if (trueCnt==0) {
				$('#SPASV3').prop('checked',false);
			} else if (falCnt==0) {
				$('#SPASV3').prop('checked',true);
			} else if (trueCnt!=0&&falCnt!=0) {
				$('#SPASV3').prop('indeterminate',true);
			}
		}
		
		//將split掉的字串矩陣組合，少取最後一位 取得上層模組name字串
		function bindAryModStr(comNameArray){
			comNameArray.pop();
			return comNameArray.join(splitChar);
		}
		
		//點下複製, 重選 src_, 保留 org_
		function selectSrcRecord(data) {
			$('input[name^="src'+splitChar+'"]').each(function() {
				$(this).attr("checked", false);
			});
			
			var ss = data.map.listStr.split(arrayChar);
			for (var i=0; i<ss.length; i++) {
				$('#src'+splitChar+ss[i]).attr("checked", true);
			}
		}
		
		//roleId change 之後
		function selectRecord(data) {
			//先清空全部, 在設定 src 和 org
			$('#treeDiv :checkbox').each(function() {
				$(this).attr("checked", false);
				$(this).prop('indeterminate', false);
			});
			var ss = data.map.listStr.split(arrayChar);
			for (var i=0; i<ss.length; i++) {
				$('#src'+splitChar+ss[i]).attr("checked", true);
				$('#org'+splitChar+ss[i]).attr("checked", true);
			}
		}
		
		function checkIt(id) {
			var b = $("#"+id).attr('checked') == "checked" ? true : false;
			//選取下層
			$('input[name^="src'+splitChar+'"]').each(function() {
				if ($(this).attr("id").indexOf(id)>=0) {
					$(this).removeAttr('indeterminate');
					$(this).attr("checked", b);
				}
			});

			//清空上層
			if (!b) {
				var ss = id.split(splitChar);
				if (ss.length>2) {
					for (var i=0; i<ss.length; i++) {
						var nss = ss.slice(0, i);
						var cid = nss.join(splitChar);
						$("#"+cid).removeAttr('indeterminate');
						$("#"+cid).attr("checked", b);
					}
				}
			}
		}
		
		function doSelectAll() {
			$('input[name^="src'+splitChar+'"]').each(function() {
				$(this).attr("checked", true);
			});
		}
		
		function doClear() {
			$('input[name^="src'+splitChar+'"]').each(function() {
				$(this).attr("checked", false);
			});
		}
		
		function doSave() {
			if (confirm("<spring:message code='confirm.update.message'/>")) {
				var roleId = $("#roleId").val();
				if(roleId==''||roleId==null){
					alert("<spring:message code='sys006f.no.role.id.error'/>");
					return false;
				}else{
					var ids = [];
					$('input[name^="src'+splitChar+'"]').each(function() {
						var flag = $(this).attr("checked")=="checked"?true:false;
						if (flag) {
							ids.push($(this).attr("id"));
						}
					});
					var param = "roleId="+roleId+"&ids="+ids.join(arrayChar)+"&csrfPreventionSalt="+$("#csrfPreventionSalt").val();
					var url = "${ctx}/SYS006F/ajaxSave.html";
					doCwAjaxSync(url, param, aftersave, null);
				}
				
			}
		}
		
		function aftersave(data) {
			if (data.result == "${ajaxOk}") {
				//重新查詢，顯示資料
				$('#roleId').change();
			}
		}
		
	</script>
</head>
<body>
<div class="mainDiv">
<br/><br/><br/>
	<form id="queryForm" name="queryForm" method="post" action="${ctx}/SYS006F/query.html">
		<input type="hidden" id="csrfPreventionSalt" name="csrfPreventionSalt" value="<c:out value='${csrfPreventionSalt}'/>"/>
		<table class="queryTable" border="0" bordercolordark="#ffffff" bordercolorlight="#595241" cellpadding="2" cellspacing="0">
			<tr>
				<th style="width:10%"><spring:message code="sys006f.role.id"/>：</th><%-- 權限代碼 --%>
				<td>
					<select class="select" id="roleId" name="roleId">
						<option value="" selected><spring:message code="select.options.0.label"/></option>
						<c:forEach items="${roleList}" var="item">
							<option value="${item.roleId}">${item.roleId}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th style="width:10%"><spring:message code="sys006f.source.id"/>：</th><%-- 複製來源 --%>
				<td>
					<select class="select" id="sourceRoleId" name="sourceRoleId">
						<option value="" selected><spring:message code="select.options.0.label"/></option>
						<c:forEach items="${roleList}" var="item">
							<option value="${item.roleId}">${item.roleId}</option>
						</c:forEach>
					</select>
					<input type="button" class="btn" value="<spring:message code='copy.label'/>" id="copyRoleId" disabled/>
				</td>
			</tr>
		</table>
	</form>
</div>
<%-- 按鈕區域 --%>
<div class="buttonDiv">
	<input type="button" id="queryBtn" value="<spring:message code="selectAll.label"/>" class="btn" onclick="javascript:doSelectAll();" />
	<input type="button" value="<spring:message code="save.label"/>" class="btn" onclick="javascript:doSave();" />
	<input type="button" value="<spring:message code="clear.label"/>" class="btn" onclick="javascript:doClear();" />
</div>
<div class="mainDiv">
	<div id="treeDiv">
		<table border="0" cellpadding="0" cellspacing="0" width="500px" style="text-align: left;margin: 0 auto;">
			<tbody>
				<tr>
					<td>
						<div id="SPASV3_DIV">
							<input type="checkbox" id="SPASV3" name="SPASV3"/><input type="checkbox" id="org_SPASV3" name="org_SPASV3" disabled="disabled"/><img src="../images/tree/open.gif" align="absmiddle" style="cursor:pointer;" class="imgOpen">
							<spring:message code='system.title'/>	
						<div>
						${menuTable}
						</div>
						</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
</div>
</body>
</html>