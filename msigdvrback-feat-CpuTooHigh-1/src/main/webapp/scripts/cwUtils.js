/**
 * 初始化LOV
 * @param {int} level LOV層級,例如:1,2
 * @returns
 */
function initLOV(level){
	window.parent._createLOV(level);
}

function _createLOV(level){
	//console.log("createLOV level:"+level);
	var thisLevel = level == null ? 1 : level;
	var dialogId = "#dialogDiv_" + thisLevel;
	$("body").append("<div id='dialogDiv_"+thisLevel+"'><iframe id='lovFrame_"+thisLevel+"' height='100%' width='100%' frameBorder='0'>noiframe</iframe></div>");
	$(dialogId).dialog({
		height : "auto",
		width : 800,
		title : "辅助窗口",
		resizable : false,
		closeOnEscape : false,
		modal : true,
		autoOpen : false,
		draggable: true,
		zIndex: thisLevel*2000,
		open: function(event, ui) {
				$(dialogId).css("height", "400px");
			  },
		close: function(event, ui) { 
				$("#lovFrame_"+ thisLevel).removeAttr("src"); 
			   } 
	});
}
/**
 * 取得目前LOV的層級
 * 
 * @returns {int}
 */
function getCurrentLevel(){
	if ($.browser.msie && $.browser.version != 10) {
		return $("iframe[id^='lovFrame_'][src!='']", window.parent.document).length;
	} else if ($.browser.msie && $.browser.version == 10) {
		return $("iframe[id^='lovFrame_'][src]", window.parent.document).length;
	}else{
		return $("iframe[id^='lovFrame_'][src]", window.parent.document).length;
	}
}

/**
 *	關閉dialog
 */
function _closeDialog(level) {
	var thisLevel = level == null ? 1 : level;
	$("#dialogDiv_" + thisLevel).dialog("close");
}
/**
 * 關閉LOV視窗並清空父頁面LOV使用的form
 * @param id 父頁面LOV使用的form ID
 */
function closeLOV(id){
	if(id != null && id != undefined){
		$("#"+id+" > input", window.parent.document).val("");//清空LOV查詢用的form
	}
	window.parent._closeDialog(getCurrentLevel()); 
}

/**
 * 在LOV頁面設定選取之值到開啟LOV之頁面(code)
 * @param id 
 * @param value 
 * @returns
 */
function setLovResultCde(id, value){
	_setLovResult(id, value, true)
}
/**
 * 在LOV頁面設定選取之值到開啟LOV之頁面(dscr)
 * @param id 
 * @param value 
 * @returns
 */
function setLovResultDscr(id, value){
	_setLovResult(id, value, false)	
}
/**
 * 設定LOV選取值到開啟頁面
 * @param id
 * @param value
 * @param isCode 是否設定"ajaxChk"的值
 */
function _setLovResult(id, value, isCode){
	var level = getCurrentLevel();
	var iframeId = "";
	if( level == 1){
		iframeId = "#workAreaFrame";//menu 頁面iframe id="workAreaFrame"
	}else if(level > 1){
		iframeId = "#lovFrame_"+(level-1);
	}
	if(iframeId){
		var target = $(iframeId, window.parent.document).contents().find("#"+id);
		target.val(value);
		if(isCode)
			target.attr("ajaxChk", "OK");
	}
}

/**
 * (頁籤使用)
 * 在LOV頁面設定選取之值到開啟LOV之頁面(code)
 * @param id 
 * @param value 
 * @returns
 */
function setTagLovResultCde(id, value){
	_setTagLovResult(id, value, true)
}
/**
 * (頁籤使用)
 * 在LOV頁面設定選取之值到開啟LOV之頁面(dscr)
 * @param id 
 * @param value 
 * @returns
 */
function setTagLovResultDscr(id, value){
	_setTagLovResult(id, value, false)	
}
/**
 * (頁籤使用)
 * 設定LOV選取值到開啟頁面
 * @param id
 * @param value
 * @param isCode 是否設定"ajaxChk"的值
 */
function _setTagLovResult(id, value, isCode){
	var level = getCurrentLevel();
	var iframeId = "";
	if( level == 1){
		iframeId = "#workAreaFrame";//menu 頁面iframe id="workAreaFrame"
	}else if(level > 1){
		iframeId = "#lovFrame_"+(level-1);
	}
	if(iframeId){
		var target = $(iframeId, window.parent.document).contents().find('iframe').contents().find("#"+id);
		target.val(value);
		if(isCode)
			target.attr("ajaxChk", "OK");
	}
}

/**
 * 數字 binding，如果有 readonly，則blur使該物件不能被修改
 */
function bindAuto(id, target) {
	var $id = $("#" + id);
	var $target = $("#" + target);
	$id.autoNumeric();
	$id.bind('keyup', function(e) {
		var v = $(this).autoNumericGet();
		if ($id.val() == "") {
			$target.val("");
		} else {
			$target.val(v);
		} 
		if ($id.attr("readonly")) {
			$id.focus(function() {
				$(this).blur();
			});
			e.preventDefault();
		}
	});
}
/**  
 *	欄位加月曆及顯示格式
 *@param id: 輸入欄位id 或 input"jq物件"皆可
 *@param isBirthday: 是否為 生日 欄位
 *@param beforeYears: "年"下拉選單顯示今天以前的年數
 *@param afterYears: "年"下拉選單顯示今天以後的年數
 */
function bindCalendar(id, isBirthday, beforeYears, afterYears){
	var obj;
	if(typeof(id) == "string"){
		obj = $("#" + id);
 	}else{
 		obj = id;
 	}
	var beforY = "100";
	var afterY = "100";
	if(isBirthday === true){
		beforY = "99";
		afterY = "0";
	}
	if(beforeYears != null && !isNaN(beforeYears)){
		beforY = beforeYears;
	}
	if(afterYears != null && !isNaN(afterYears)){
		afterY = afterYears;
	}
	if( obj.prop("tagName") == "INPUT"){
		obj.mask("9999/99/99");
		obj.attr("placeholder", "YYYY/MM/DD");
		obj.placeholder();
		obj.datepicker({
			showOn: "button",
			buttonImageOnly: true,
			buttonImage: "../images/calendar.gif",
			buttonText: "點擊日期",
			firstDay: 0,
			changeYear: true,
			yearRange: "-" + beforY + ":" + "+" + afterY,
			monthNames: ['1月','2月','3月','4月','5月','6月', '7月','8月','9月','10月','11月','12月'],
			onClose: function(dateText, inst) {
				$(this).blur();
			}
		});
		obj.next("img[title='點擊日期']").addClass("pointer imgBox");
	} else {
		alert("bindCalendar error!!");
	}
}
/**
 * 年月欄位加mask及placeholder
 * @param id: 輸入欄位id 或 input"jq物件"皆可
 */
function bindPeriod(id){
	var obj;
	if(typeof(id) == "string"){
		obj = $("#" + id);
 	}else{
 		obj = id;
 	}
	if( obj.prop("tagName") == "INPUT"){
		obj.mask("999999");
		obj.attr("placeholder", "YYYYMM");
		obj.placeholder();
	} else {
		alert("bindPeriod error!!");
	}
}
/**
 *	套用Form資料檢核
 *@param id: 欲檢核form id
 */
function bindValidation(id) {
	var obj = $("#" + id);
	if (obj.prop("tagName") == "FORM") {
		obj.validationEngine({
			scroll : false,                   // 有錯誤訊息是否浮動頁面
			returnIsValidate : true,          // 送出前回傳是否valid
			autoHidePrompt : true,            // 10秒後，錯誤訊息自動消失
			validationEventTrigger : "submit" //只有form submit 才檢查
		});
	} else {
		alert("id:" + id + " bindValidation error!!");
	}
}

/**
 *	依傳入form自動套用輸入欄位CSS、readonly、disabled
 *@param formId = form id
 *@param actType = "insert","update","copy","detail","query"
 *@param readOnlyList {array} = 唯讀欄位id清單(ajax、LOV說明欄位id不用傳入)
 *@param excludeList {array} = 自行套用CSS欄位id清單 
 */ 
function bindFormCSS(formId, actType, readOnlyList, excludeList){
	var formObj = $("#" + formId + " :input");
	var editable = actType != "detail" ? true : false;
	var thisEditable;
	formObj.each(function(i){
		var obj = $(this);
		//actType不是"detail"才處理"唯讀欄位清單"
		if(editable){
			if(readOnlyList != null && readOnlyList != undefined && jQuery.inArray(obj.attr("id"), readOnlyList) != -1){
				thisEditable = !editable;
			}else{
				thisEditable = editable;
			}
		}
		//自行套用CSS清單,不處理
		if(excludeList == null || excludeList == undefined || jQuery.inArray(obj.attr("id"), excludeList) == -1){
			_setClass(obj, thisEditable);
		}
	});
}

/**
 *	套用CSS
 *@param obj 套用CSS之object
 *@param editable true:可修改, fasle:不可修改
 */
function _setClass(obj, editable){
	//use CSS class
	var inputY = "inputY", inputY_S = "inputY_S", inputY_L = "inputY_L";
	var LovDscrL = "LovDscrL", LovDscrS = "LovDscrS";
	var inputN = "inputN", inputN_S = "inputN_S", inputN_M = "inputN_M",inputN_F = "inputN_F";
	var textareaY = "textareaboxY", textareaN = "textareaboxN";
	var tag = obj.prop("tagName");
	var colspan = obj.parent().attr("colspan");
	if(tag == "INPUT" ){
		var inputType = obj.attr("type");

		if(inputType == "text"){
			//判斷是否有LOV圖示
			var prevButton = obj.prev().prop("tagName") == "SPAN" ? true : false;//input前有SPAN
			var nextButton = obj.next().prop("tagName") == "SPAN" ? true : false;//input後有SPAN
			if(colspan == "3"){
				if(prevButton){
					setReadonly(obj, LovDscrL);
					if(!editable)
						obj.prev().hide();
				}
				else if(nextButton){
					if(editable)
						obj.addClass(inputY);
					else{
						setReadonly(obj, inputN);
					}
				}
				else{
					if(editable)
						obj.addClass(inputY_L);
					else{
						setReadonly(obj, inputN_F);
					}
				}
			}	
			else {
				if(prevButton){
					setReadonly(obj, LovDscrS);
					if(!editable)
						obj.prev().hide();
				}
				else if(nextButton){
					if(editable)
						obj.addClass(inputY_S);
					else{
						setReadonly(obj, inputN_S);
					}
				}
				else{
					if(editable)
						obj.addClass(inputY);
					else{
						setReadonly(obj, inputN_M);
					}
				}
			}
		}
		else if(inputType == "radio" && !editable)
			obj.attr("disabled", true);
		else if(inputType == "checkbox" && !editable)
			obj.attr("disabled", true);
	}
	else if(tag == "SELECT"){
		if(editable)
			obj.addClass("select");
		else{
			var selDscr = ""; 
			if(obj.val() != "")
				selDscr = $("option:selected", obj).text();
			obj.hide();
			if(colspan == "3"){
				obj.after("<input type='text' class='"+inputN_F+"' value='"+selDscr+"' readonly/>");
			}else{
				obj.after("<input type='text' class='"+inputN_M+"' value='"+selDscr+"' readonly/>");
			}
		}
	}
	else if(tag == "TEXTAREA"){
		obj.css("width", "100%");
		if(editable){
			obj.addClass(textareaY);
		}else{
			setReadonly(obj, textareaN);
		}
	}
	//設定唯讀欄位
	function setReadonly(obj, className){
		obj.addClass(className);
		obj.attr("readonly", true);
	}
}

/**
 * "查詢頁面"及"LOV頁面" 的"查詢結果"區塊,套用CSS
 */
function bindQryResultCSS(){
	//查詢頁面及LOV頁面"查詢結果區塊資料唯讀及套CSS處理
	$(".resultTable input:text , .LovResultTable input:text").each(function(i){
		$(this).attr("readonly", true);
		$(this).addClass("inputN_F");
	});
	//查詢結果區塊tr
	$(".resultTable tbody tr:odd").addClass("rowS");
	$(".resultTable tbody tr:even").addClass("rowC");
}

function setDetailCSS(){
	$(".modTableDetail input:text").each(function(i){
		$(this).addClass("inputY_F");
	});
	//維護頁面detail區塊 tr
	$(".modTableDetail").each(function(){
		$(this).find("tbody tr:odd").addClass("rowS");
		$(this).find("tbody tr:even").addClass("rowC");
	});
}
/**
 * 重新套用tr css
 * @param id =table id 或table物件
 */
function refreshTable(id){
	var obj;
	if(typeof(id) == "string"){
		obj = $("#"+id);
	}else{
		obj = id;
	}
	$("tbody tr:odd", obj).attr("class", "rowS");
	$("tbody tr:even", obj).attr("class", "rowC");
}

/**
 *	
 *輸入英文自動轉換大寫
 *@param id 輸入欄位id
 */
function autoUpperCase(id) {
	var obj = $("#" + id);
	if (obj.prop("tagName") == "INPUT") {
		obj.css("text-transform", "uppercase");
		obj.blur(function() {
			$(this).val($(this).val().toUpperCase());
		});
	} else {
		alert("id:" + id + " autoUpperCase bind error!!");
	}
}

/**
 * 
 *日期比較 
 *@param dateS    開始日(input obj)
 *@param dateE    結束日(input obj)
 *@param canEqual 未傳值預設:true
 *         		  true: 結束日不可小於開始日       
 *         		  false:結束日不可小於等於開始日
 */
function compareDate(dateS, dateE, canEqual){
	dateS = typeof(dateS) == 'string' ? dateS : dateS.val();
	dateE = typeof(dateE) == 'string' ? dateE : dateE.val();
	var timeS = Date.parse(dateS);
	var timeE = Date.parse(dateE);
	var eq = true;
	if(canEqual != null){
		eq = canEqual;
	}
	if(eq){
		if(timeS > timeE){
			return false;
		}
	}else {
		if(timeS >= timeE ){
			return false;
		}
	}
	return true;
}

function winPos(winX ,winY){
	var pos = new Array();
	if (isNaN(winX) || isNaN(winY)) {
		pos[0] = pos[1] = 0;
		return pos;
	} else {
		var scrW = screen.width;
		var scrH = screen.height;
		pos[0] = scrW - winX - (scrW - winX)/2;
		pos[1] = scrH - winY - (scrH - winY)/2;
		return pos;
	}
}

/**
 *取得目前時間 yyyy/MM/dd hh:MM:ss 格式字串 
 */
function getNowTime(){
	var nowTime;
	var date = new Date();
	var year = date.getFullYear();
	var month = ('0' + (date.getMonth() + 1)).slice(-2);
	var day = ('0' + date.getDate()).slice(-2);
	var hour = date.getHours();
	var min = date.getMinutes();
	var sec = ('0' + date.getSeconds()).slice(-2);
	nowTime = year+'/'+month+'/'+day+' '+hour+':'+min+':'+sec;
	return nowTime;
}
/*======== for validation use functions start ========*/
/**
 *	檢查身份證字號是否正確
 */
function cwId(field, rules, i, options) {
	if (field.val().length != 10) {
		return "* 输入的身分证字号长度不对！";
	}

	if (!_checkTwIdSum(field.val())) {
		return "* 输入的身分证字号有误！";
	}
}

/**
 * 檢查輸入文字長度最小值(中文3byte)
 * 用法:funcCall[cwMin[1]]
 */
function cwMin(field, rules, i, options) {
	var min = rules[i + 2];
	if (!_countChinese(field.val(), min)) {
		return options.allrules.minSize.alertText + min + options.allrules.minSize.alertText2
		+ "（中文以一个byte计算）";
	}
	;
}

/**
 * 檢查輸入文字長度最大值(中文3byte)
 * 用法:funcCall[cwMax[80]]
 */
function cwMax(field, rules, i, options) {
	var max = rules[i + 2];
	if (_countChinese(field.val(), max)) {
		return options.allrules.maxSize.alertText + max + options.allrules.maxSize.alertText2
		+ "（中文以一个byte计算）";
	}
	;
}

/**
 * 檢查輸入日期是否合法
 * 用法:funcCall[cwDate]
 */
function cwDate(field, rules, i, options) {
	//IE 加placeholder時,空值時vlaue="YYYY/MM/DD"
	if(field.val() == field.attr("placeholder") || field.val() == ""){
		return;
	}
	if( !isValidDate(field)){
		return "* 无效的日期，格式必需为 YYYY/MM/DD";
	}
}

/**
 * 檢查1.輸入日期是否晚於另一日期;2.起日有輸入值,迄日不可為空值
 * 不需再檢核日期格式funcCall[cwDate]
 * 用法:	1.不檢核(起日有值,迄日不可為空值)funcCall[cwFuture[要比較日期的id]]
 *		2.檢核(起日有值,迄日不可為空值)funcCall[cwFuture[要比較日期的id,Y,起日,迄日]]]
 */
function cwFuture(field, rules, i, options){
	//檢核日期格式
	var msg = cwDate(field, rules, i, options);
	if(msg){
		return msg;
	}
	//檢查要比較的日期格式,正確才做比較
	var effDate = $("#"+rules[i + 2]);
	//起日有值,迄日必填檢核
	var chkNotNull = rules[i + 3];
	var starDate = rules[i + 4] == undefined ? "起日" : rules[i + 4];
	var endDate = rules[i + 5] == undefined ? "迄日" : rules[i + 5];
	if(chkNotNull == "Y" && effDate.val() != "YYYY/MM/DD" &&field.val() != ""){
		return "* " + starDate + "有输入值时，" + endDate + "不可为空值！";
	}
	
	if(isValidDate(effDate) && effDate.val() > field.val()){
		return "* 日期必需晚于" + effDate.val();
	}
}
/**
 * 檢查輸入年月是否晚於另一年月,
 * 不需再檢核日期格式funcCall[cwPeriod]
 * 用法:funcCall[cwYMFuture[要比較年月的id]]
 */
function cwYMFuture(field, rules, i, options){
	//檢核年月格式
	var msg = cwPeriod(field, rules, i, options);
	if(msg){
		return msg;
	}
	//檢查要比較的年月格式,正確才做比較
	var effYM = $("#"+rules[i + 2]);
	if(isValidDate(effYM.val()+"01", "yyyyMMdd") && effYM.val() > field.val()){
		return "* 输入年月必需晚于" + effYM.val();
	}
}
/**
 * 檢查輸入年月是否正確
 * 用法:funcCall[cwPeriod]
 */
function cwPeriod(field, rules, i, options){
	if(field.val() == "" || field.val() == "YYYYMM")
		return ;
	if(!isValidDate(field.val()+"01", "yyyyMMdd"))
		return "* 无效的年月，格式必需为 YYYYMM";
}
/**
 * 檢查身份證字號是否正確
 */
function _checkTwIdSum(id) {
	var tab = "ABCDEFGHJKLMNPQRSTUVXYWZIO"
		var A1 = new Array(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3,
				3, 3);
	var A2 = new Array(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3,
			4, 5);
	var Mx = new Array(9, 8, 7, 6, 5, 4, 3, 2, 1, 1);

	if (id.length != 10)
		return false;
	var i = tab.indexOf(id.charAt(0));
	if (i == -1)
		return false;
	var sum = A1[i] + A2[i] * 9;

	for (i = 1; i < 10; i++) {
		v = parseInt(id.charAt(i));
		if (isNaN(v))
			return false;
		sum = sum + v * Mx[i];
	}
	if (sum % 10 != 0) {
		return false;
	}
	return true;
}

/**
 * 計算中文字長度(ORACLE UTF-8 中文佔3byte)
 */
function _countChinese(val, maxlength) {
	var byteCnt = 0;
	for (i = 0; i < val.length; i++) {
		byteCnt = val.charCodeAt(i) > 255 ? byteCnt + 1 : byteCnt + 1;
	}
	return byteCnt > maxlength ? true : false;
}
/**
 * 檢查是否為合法日期,空值回傳false
 * @param obj jquery物件或日期string
 * @param pattern 可不傳,預設為"yyyy/MM/dd"
 * @returns {Boolean}
 */
function isValidDate(obj, pattern){
	if(pattern == null || pattern == undefined){
		pattern = "yyyy/MM/dd";
	}
	
	var timeStr = typeof(obj) == "string" ? obj : obj.val();
	var origPattern = pattern;
	var pattern = pattern;
	//do d first avoid replace d
	pattern = _replacePattern("d",pattern);
	pattern = _replacePattern("M",pattern);
	pattern = _replacePattern("y",pattern);   
	var myReg = new RegExp(pattern, "g")
	var mymatch = myReg.exec(timeStr);
	if(mymatch == null){
		return false;
	}else{
		var year = _getDateNbr("y",timeStr,origPattern); 
		var month = _getDateNbr("M",timeStr,origPattern); 
		var day = _getDateNbr("d",timeStr,origPattern); 
		var checkDate = new Date(year,(month-1),day); 	
		if(checkDate.getFullYear() == year && checkDate.getMonth()+1 == month && checkDate.getDate() == day ){
			return true;	   
		}else{
			return false;
		}  
	}  	
}

function _getDateNbr(timeType,timeStr,pattern){
	if(pattern.indexOf(timeType)!=-1){
	    return parseFloat(timeStr.substring(pattern.indexOf(timeType),pattern.lastIndexOf(timeType)+1));
	}
	if(timeType=="y")return 1900;
	if(timeType=="M")return 12;
	if(timeType=="d")return 31;
}

function _replacePattern( timeType,pattern){
	if(pattern.indexOf(timeType)!=-1){
		var len=pattern.lastIndexOf(timeType)-pattern.indexOf(timeType)+1;
		var subPattern=pattern.substring(pattern.indexOf(timeType),pattern.lastIndexOf(timeType)+1);
		var dy="\\d{"+len+"}";
		pattern=pattern.replace(subPattern,dy);
	}
	return pattern;
}

/*=============== for validation use functions end =======================*/

/* For Tab 使用的 Fucntions Start */
/**
 * _formArray 
 * Object property :
 * 		Name:		頁籤內的form name
 * 		Flag:		檢查結果
 * 		Valid: 		true=要做額外檢核,false=不做額外檢核
 * 		CallFunc:	額外檢核的function name	
 */
var _formArray  = new Array();
function bindTabForm(formArray) {
	_formArray = formArray;
	for (var i=0; i<_formArray.length; i++) {
		bindValidation(_formArray[i].Name);
		_formArray[i].Flag = false;
	}
}

function showTabIndex(idx) {
	 $('ul.tabs li').eq(idx).trigger('click');
}
/**
 * 
 *For Tab 檢核所有頁籤
 *@param {int}selIndex:當前頁籤index
 */
function validateTab(selIndex) {
	//檢查本頁
	chkResult(selIndex);
	if (!_formArray[selIndex].Flag) {
		return false;
	}
	//檢核所有頁籤(除了當前頁籤)
	for (var i=0; i<_formArray.length; i++){
		if (i == selIndex){
			continue;
		}
		showTabIndex(i);
		chkResult(i);
		if (!_formArray[i].Flag) {
			return false;
		}
	}
	//檢核完成 切換回使用者當前頁籤
	showTabIndex(selIndex);
	return true;
	
	function chkResult(i){
		_formArray[i].Flag = $("#"+_formArray[i].Name).validationEngine('validate');
		//輸入資料檢核無誤且需做其檢核時,則執行傳入的function
		if(_formArray[i].Flag && _formArray[i].Valid){
			var fn = window[_formArray[i].CallFunc];
			if(typeof(fn) == "function")
				_formArray[i].Flag &= fn();
		}
	}
}
/*For Tab 使用的 Fucntions End*/
/**
 * validation 顯示錯誤訊息
 * @param name 輸入欄位name
 * @param contextId table or form Id
 * @param msg 顯示訊息
 */
function _validateShowPrompt(name, contextId, msg){
	$("input[name='"+name+"']", "#"+contextId).validationEngine('showPrompt', msg, 'alert', 'topRight', true);
}
/**
 *  比較Array日期區間是否重疊,重疊時顯示validation alert message<br>
 *  dateArray(Object Array): Object內容 effDate, endDate, name(effDate的name,顯示alert用)
 *  例:[{effDate:"2012/01/01", endDate:"2012/01/31", name:"xxx[1].effDate"}]
 * @param tableId input欄位所屬table
 * @param dateArray 日期區間Array
 * @param isYM 是否為年月檢核
 * @returns {Boolean} true:未重疊, false:重疊
 */
function _compareDateArray(tableId, dateArray, isYM){
	var result = true;
	//依開始日期排序array
	if(isYM){
		dateArray.sort(function(a, b){
			var x = a.effDate == "" ? 0 : a.effDate;
			var y = b.effDate == "" ? 0 : b.effDate;
			return x - y;
		});
	} else{
		dateArray.sort(function(a, b){
			var x = a.effDate == "" ? 0 : new Date(a.effDate);
			var y = b.effDate == "" ? 0 : new Date(b.effDate);
			return x - y;
		});
	}
	
	for(var i=0, cnt = dateArray.length; i < cnt-1; i++) {
		if (dateArray[i].effDate == "" || (dateArray[i].endDate != ""
				&& dateArray[i].effDate > dateArray[i].endDate)) {
			result = dateArray[i].sName;
			break;
		}
		//年月
		if(isYM){
			if(dateArray[i].endDate == ""){
				result = dateArray[i+1].editable ? dateArray[i+1].sName : dateArray[i].sName
				break;
			}else if(dateArray[i].effDate == dateArray[i+1].effDate){
				result = dateArray[i+1].editable ? dateArray[i+1].sName : dateArray[i].sName
						break;				
			}else if(dateArray[i].endDate >= dateArray[i+1].effDate){
				result = dateArray[i+1].editable ? dateArray[i+1].sName : dateArray[i].eName
				break;
			}
		//日期
		}else{
			if(dateArray[i].endDate == ""){
				result = dateArray[i+1].editable ? dateArray[i+1].sName : dateArray[i].sName
				break;
			}else if(dateArray[i].effDate == dateArray[i+1].effDate){
				result = dateArray[i+1].editable ? dateArray[i+1].sName : dateArray[i].sName
						break;				
			}else if(dateArray[i].endDate >= dateArray[i+1].effDate){
				result = dateArray[i+1].editable ? dateArray[i+1].sName : dateArray[i].eName
				break;
			}
		}
	}
	//有錯誤時顯示validation message
	if(result != true){
		var msg = isYM ? "* 年月区间重叠" : "* 日期区间重叠";
		_validateShowPrompt(result, tableId, msg);
		return false;
	}
	return true;
}
/**
 * 日期區間檢查
 * @param id table ID
 * @param effDateName 開始日name
 * @param endDateName 結束日name
 * @param keyArray group by 的欄位name arrey ["name","name2",... ];可不傳
 * @param isYM
 * @returns {Boolean}
 */
function _chkDateRanges(id, effDateName, endDateName, keyArray, isYM) {
	var dateArray = new Array(), keyValues = new Array();
	var keyValue = "empty", placeholder = isYM ? "YYYYMM" : "YYYY/MM/DD";
	if(keyArray && !$.isArray(keyArray)){
		alert("第四个参数需为Array!");
		return false;
	}
	if(!keyArray || $.isArray(keyArray) && keyArray.length == "0"){
		keyValues.push(keyValue);
	}
	$("#"+ id +" tbody tr").each(function(){
		var effObj = $(this).find("input[name$='"+effDateName+"']");
		var endObj = $(this).find("input[name$='"+endDateName+"']");
		var sd = effObj.val() == placeholder ? "" : effObj.val();
		var ed = endObj.val() == placeholder ? "" : endObj.val();
		var sName = $(this).find("input[name$='"+effDateName+"']").attr("name");
		var eName = $(this).find("input[name$='"+endDateName+"']").attr("name");
		var editable = effObj.attr("readonly") != "readonly" ? true : false;
		//有傳key值時
		if(keyArray){
			keyValue = "";
			for(var i=0,key; key=keyArray[i]; i++){
				var val = $(this).find("[name$='"+key+"']").val();
				if(i == 0){
					keyValue += val+",";
				}else if(i == keyArray.length){
					keyValue += val; 
				}else{
					keyValue += val+","; 
				}
			}
			if($.inArray(keyValue, keyValues) == -1){
				keyValues.push(keyValue);
			}
		}
		
		dateArray.push({effDate: sd, endDate: ed, sName: sName, eName: eName, key1: keyValue, editable: editable});
	});
	
	for(var i=0, j=keyValues.length; i < j; i++){
		var tempArray = new Array();
		for(var x=0, cnt=dateArray.length; x < cnt; x++ ){
			if(dateArray[x].key1 == keyValues[i]){
				tempArray.push(dateArray[x]);
			}
		}
		//檢核日期array是否正確
		if(!_compareDateArray(id, tempArray, isYM)){
			return false;
		}
	}
	return true;
}

/**
 * 日期區間檢查
 * @param tableId 
 * @param effDateName 開始日name
 * @param endDateName 結束日name
 * @param keyArray group by 的欄位name arrey ["name","name2",... ];可不傳
 * @returns {Boolean}
 */
function chkDateRanges(tableId, effDateName, endDateName, keyArray) {
	return _chkDateRanges(tableId, effDateName, endDateName, keyArray, false);
}
/**
 * 年月區間檢查
 * @param tableId 
 * @param effYM 開始年月name
 * @param endYM 結束年月name
 * @param keyArray group by 的欄位name arrey ["name","name2",... ];可不傳
 * @returns {Boolean}
 */
function chkYMRanges(tableId, effYM, endYM, keyArray) {
	return _chkDateRanges(tableId, effYM, endYM, keyArray, true);
}

/**
 * Detail區塊共用程式
 * 用法:	$("tableId").bindDetail();
 * 		$("tableId").bindDetail(options);
 * 		options.index= <input name="xxx[].xxx" > []內的開始號碼
 * 		options.prepend = 新增列放置位置,true:第一列 , false:最後一列(預設值)
 * 		options.addButton= 新增一筆明細button物件or ID
 * 		options.dateName= string array, 要套日曆的input name([]後面的部份,如form1[1].effDate的".effDate")
 * 		options.lov= lov圖示要bind的function (含openLOV及ajax); Object array, 
 * 					Object 內容{name:input name, lovFunc:lov fucntion,ajaxFunc:ajax的function};例:[{name:".lov", lovFunc:"openLov",ajaxFunc:"ajax"}]
 * 		options.ajax= 只有ajax(無LOV)的欄位要bind的function; {name: input name, func:ajax的function}
 * 		options.actType = insert,update,copy,detail,multi(detail區塊,初始戕態為readonly)
 * 		options.delFunc = 刪除時要先執行的function 
 * 		options.editFunc = 修改時執行的function 
 *      options.addFunc = 新增時要先執行的function
 * 		options.bindEventFunc =  Object array
 * 						 其他要加入事件的tagid,event,functionName;
 * 						 例[{tagId: "bcid", event: "click", func:"openLov"},{tagId: "cuno", event: "blur", func:"doAjax"}]
 * 		options.addIdValidateFunc = String Array;
 * 							validate中funcCall使用自訂function中參數為同列中某個id時使用,例:validate[funcCall[cwFuture[tab1_effDate_]]]
 * 							預設值為:["cwFuture","cwYMFuture"]
 * 		options.afterAddRowCall = function name;新增一列後,有另外要處理的動作,可在此fucntion中做,如bindAuto
 *		options.setCSS = 是否讓程式自動套用CSS,預設為true
 * 		options.afterDelFunc = 刪除後執行的function 
 * 	例:$("tableId").bindDetail({index:5});
 * 功能:	1."新增一筆明細"、"刪除圖示"、"lov圖示"加入onclick 
 *		2."ajax欄位"加入onblur
 *		3.新增後刷新tr底色	
 * version 1.0
 * author: Joey Hsu
 * Created: 2012/09/14
 */
;(function($){
	$.fn.bindDetail = function(options){
		var defaults = {
				index: 0,
				prepend: false,				
				addButton: this.find("thead tr:first td:first span.btn:first"),
				dateName: [".effDate", ".endDate"],
				lov: [],
				ajax: [],
				actType: "",
				delImage: "/DELETE.png",
				delFunc: "",
				editImage: "/UPDATE.png",
				editFunc: "",
				addFunc: "",
				bindEventFunc: "", 
				addIdValidateFunc: ["cwFuture","cwYMFuture"],
				afterAddRowCall: "",
				setCSS: true,
				afterDelFunc: ""
		}
		var _options = $.extend({}, defaults, options);
		
		var _table = this;
		var _button = typeof(_options.addButton) == "string" ? $("#"+_options.addButton) : _options.addButton; //新增一筆明細button
		var _idx = _options.index;//起始編號
		var _date = _options.dateName;//要套日曆的input name([]後面的部份,如xxx[1].effDate的".effDate")
		var _lov = _options.lov;
		var _ajax = _options.ajax;
		var _actType = _options.actType;
		var _tr = _table.find("thead tr:last").clone().show();
		
		var method = {
				init: function(){
					//移除template tr,避免submit時送出
					_table.find("thead tr:last").remove();
					
					//已存在tr,套用CSS
					$("tbody tr:odd", _table).addClass("rowS");
					$("tbody tr:even", _table).addClass("rowC");
					
					//已存在之detail資料套用CSS,
					if(_actType != "detail"){
						if(_options.setCSS){
							//輸入欄位input 依template套CSS
							$(":input", _tr).each(function(){
								var name = $(this).attr("name").split("[]");
								var selector = "tbody :input[name$='"+name[1]+"']";
								var css = $(this).attr("class");
								var style = $(this).attr("style");
								var readonly = $(this).attr("readonly") == undefined ? false : true;
								var newCss = "";
								$(selector, _table).attr("style", style).attr("readonly", readonly);
								if(css != null && css != undefined){
									var classes = css.split(/\s+/);
									for(var i=0,cnt=classes.length; i< cnt; i++){
										if(classes[i].indexOf("validate") == -1){
											newCss += classes[i]+" ";
										}
									}
								}
								if(newCss != "")
									$(selector, _table).addClass(newCss);
							});
						}
						//Joey 2013/03/06 add
						if(_actType == "multi"){
							//修改圖示 加入 onclick
							$("img[src$='"+ _options.editImage +"']", _table).each(function(){
								var thisTr = $(this).closest('tr');
								var thisIdx = thisTr.index();
								$(this).click(function(){
									method.bindEdit(thisTr, thisIdx);
								});
							});	
						}else{
							//加日曆onclick
							method.bindDate($("tbody", _table));
							//LOV圖示加入 onclick
							method.bindLOV(_table, "table");
							//ajax欄位加入 onblur
							method.bindAjax(_table, "table");
							//20130308 Joey add 其他tag 加入event 
							method.bindEventFunc(_table, "table");
						}
						
						//刪除圖示 加入 onclick 
						$("img[src$='"+ _options.delImage +"']", _table).each(function(){
							var thisTr = $(this).closest('tr');
							var thisIdx = thisTr.index();
							$(this).click(function(){
								method.bindDelete(thisTr, thisIdx);
							});
						});
						
						//"新增一筆明細" 按鈕加入 onclick
						$(_button).click(function(){
							var result = method.bindAdd();
							if(result === true){
								method.addRow(_tr.clone());
							}
						});
					}
					else{
						//"新增一筆明細" 按鈕隱藏
						$(_button).hide();
						_table.find("tr:not(:first)").find("td:first ,th:first").remove();
						
						if(_options.setCSS){
							$(":text", _table).addClass("inputN_F").attr("readonly", true);
							$(":checkbox ,:radio", _table).attr("disabled", true);
							//日期置中
							for(var i=0,dateName; dateName=_date[i]; i++){
								$(":input[name$='"+dateName+"']", _table).addClass("alignC");
							}
							//LOV圖示移除
							for(var i=0,lov; lov = _lov[i]; i++){
								$(":input[name$='"+lov.name+"']", _table).each(function(){
									if($(this).next().prop("tagName") == "SPAN")
										$(this).next().hide();
									//cde及dscr合併為一欄顯示
									var newValue = $(this).val()+"-"+$(this).next().next().val()
									$(this).val(newValue);
									$(this).next().next().hide();
								});
							}
							//ajax欄位,cde及dscr 合併顯示
							for(var i=0,ajax; ajax = _ajax[i]; i++){
								$(":input[name$='"+ajax.name+"']", _table).each(function(){
									var newValue = $(this).val()+"-"+$(this).next().val()
									$(this).val(newValue);
									$(this).next().hide();
								});
							}
							//下拉選單轉為input
							$("select", _table ).each(function(){
								var selDscr = $("option:selected", $(this)).text();
								$(this).hide();
								$(this).after("<input type='text' class='inputN_F' value='"+selDscr+"' readonly/>");
							});
						}
					}
				},
				//新增一筆明細
				addRow: function(sourceTr){
					var _index = _idx++;
					sourceTr.find(":input").each(function(){
						var newName = $(this).attr("name").replace("[]","["+_index+"]");
						$(this).attr("name", newName);
						//有id時,將id後加上index
						var oldId = $(this).attr("id");
						if(oldId != undefined && oldId != ""){
							$(this).attr("id", oldId + _index);
						}
						//validate 中funcCall的function有需有同列其他欄位的ID為參數時,將[]內ID加上index,如cwFuture
						var oldClass = $(this).attr("class");
						if(oldClass != undefined && $.isArray(_options.addIdValidateFunc)){
							for(var i=0,validateFunc; validateFunc = _options.addIdValidateFunc[i]; i++){
								if(oldClass.indexOf(validateFunc) != -1){
									var reg1 = new RegExp(validateFunc+"\\[|].+");
									var rules = oldClass.split(reg1);
									if(rules.length > 1){
										var sId = rules[1].split(",");
										var newSId = sId[0] + _index + (sId[1] == undefined ? "" : ","+sId[1]) + (sId[2] == undefined ? "" : ","+sId[2]) + (sId[3] == undefined ? "" : ","+sId[3]);
										var reg2 = new RegExp("(.+"+validateFunc+"\\[).+(]])(.*].*)");
										var newClass = oldClass.replace(reg2, "$1"+newSId+"$2"+"$3");
										$(this).attr("class", newClass);
									}
								}
							}
						}
					});
					
					//日期欄位加上日曆
					method.bindDate(sourceTr);
					
					//span 有id時加上index
					sourceTr.find("span").each(function(){
						var oldId = $(this).attr("id");
						if(oldId != undefined && oldId != ""){
							$(this).attr("id", oldId + _index);
						}
					});
					
					//LOV圖示加入onclick
					method.bindLOV(sourceTr, "tr", _index);
					//ajax欄位加入 onblur
					method.bindAjax(sourceTr, "tr", _index);
					//20130308 Joey add
					method.bindEventFunc(sourceTr, "tr", _index);
					
					//刪除圖示 加入 click event 
					$("img[src$='"+ _options.delImage +"']", sourceTr).click(function(){
						method.bindDelete(sourceTr, _index);
					});
					
					//新增至table
					if (_options.prepend) {
						sourceTr.prependTo($("tbody", _table));
					} else {
						sourceTr.appendTo($("tbody", _table));
					}
					//2013/03/08 Joey add 新增一列後,有另外要處理的動作,可在此fucntion中做,如bindAuto
					var afterCallFn = window[_options.afterAddRowCall];
					if(typeof(afterCallFn) == 'function'){
						afterCallFn(_index);
					}
					//刷新tr底色
					refreshTable(_table);
				},
				chkFunc: function (contextType, fn, event, idx, selector){
					if(typeof(fn) == 'function'){
						selector.each(function(){
							if(contextType == "table"){
								idx = $(this).closest("tr").index();
							}
							method.bindFunc($(this), event, fn, idx);
						});
					}
				},
				bindLOV: function(context, contextType, idx){
					for(var i=0,lov; lov = _lov[i]; i++){
						var lovFn = window[lov.lovFunc];
						var ajaxFn = window[lov.ajaxFunc];
						method.chkFunc(contextType, lovFn, "click", idx, $("input[name$='"+lov.name+"'] + span.pointer", context));
						method.chkFunc(contextType, ajaxFn, "blur", idx, $("input[name$='"+lov.name+"']", context));
					}
				},
				bindAjax: function(context, contextType, idx){
					for(var i=0,ajax; ajax=_ajax[i]; i++){
						var fn = window[ajax.func];
						method.chkFunc(contextType, fn, "blur", idx, $("input[name$='"+ajax.name+"']", context));
					}
				},
				bindDelete: function(targetTr, index){
					var result = true;
					var fn = window[_options.delFunc];
					if(typeof(fn) == "function"){
						result = fn(index);
					}
					if(result){
						targetTr.remove();
						refreshTable(_table);
					}	
					//2014/04/01 刪除一row後須執行的function
					var afterDelFn = window[_options.afterDelFunc];
					if(typeof(afterDelFn) == "function"){
						afterDelFn(index);
					}
				},
				bindEdit: function(targetTr, index){
					var fn = window[_options.editFunc];
					if(typeof(fn) == "function"){
						fn(index);
					}
				},
				bindAdd: function(){
					var fn = window[_options.addFunc];
					if(typeof(fn) == "function"){
						return fn();
					}else{
						return true;
					}
				},				
				bindDate: function(context){
					var dateFn = window["bindCalendar"];
					for(var i=0,dateName; dateName=_date[i]; i++){
						$(":input[name$='"+dateName+"']", context).each(function(){
							dateFn($(this));
						});
					}
				},
				bindFunc: function(target, event, func, index){
					target.bind(event, function(){
						func(index);
					});
				},
				bindEventFunc: function(context, contextType, idx){
					if(_options.bindEventFunc != "" && $.isArray(_options.bindEventFunc)){
						//{tagId: "bcid", event: "click", func:"openLov"}
						for(var i=0,obj; obj = _options.bindEventFunc[i]; i++){
							var fn = window[obj.func];
							method.chkFunc(contextType, fn, obj.event, idx, $("[id^='"+obj.tagId+"']", context));
							
						}
					}
				}
		};
		
		method.init();
	};
})(jQuery);
