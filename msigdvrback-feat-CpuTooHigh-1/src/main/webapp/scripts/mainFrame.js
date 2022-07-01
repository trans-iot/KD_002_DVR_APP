
$(document).ready(function () {

	//第一層目錄加上展開收合圖示
	$(".divDir > span").prepend('<img class="act_img" src="../images/close.png"/>');
	//第二層目錄加上展開收合圖示及內縮
	$(".divDirMiddle > span").prepend('<div class="menu_node"></div><img class="act_img" src="../images/close.png"/>');
	//程式選項內縮
	$(".menucontent").prepend('<div class="menu_leaf"></div>');
	
//	$(".divDir > span , .divDirMiddle > span").click(function(){
	$(".divDir  , .divDirMiddle ").click(function(){
//		var targetDiv = $(this).parent().next("div");
		var targetDiv = $(this).next("div");
	    var divDisplay = targetDiv.css("display");
	    var targetImg = $(this).find("img");
	    if(divDisplay == "none"){
	        targetImg.attr("src", "../images/open.png");
	        targetDiv.show();
	    }else{
	    	targetImg.attr("src", "../images/close.png");
	        targetDiv.hide();
	    }
    });
	
	$('.menu').click(function(e){
		if ($(this).attr('class').indexOf('open')<0){
			$(this).removeClass("close");
			$(this).addClass("open");
			$(this).next().show();
		}
		else if ($(this).attr('class').indexOf('close')<0){
			$(this).addClass("close");
			$(this).removeClass("open");
			$(this).next().hide();
		}
	});
	
	$('.menu').each(function() {
		if ($(this).attr('class').indexOf('open')<0) {
			$(this).next().hide();
		}
		else if ($(this).attr('class').indexOf('close')<0){
			$(this).next().show();
		}
	});
	
//	$(".menucontent").click(function() {
//		var menuDir = $(this).parent().parent().parent().parent().prev().text();
//		var menuItem = $(this).parent().parent().parent().prev().children(":first").text();
//		var menuContent = $(this).text();
//		var text = menuDir + " &gt; " + menuItem + " &gt; <span style='color:#FFA042;font-weight:900;'>" + menuContent + "</span>";
//		$("#workPath").html(text);
//
//		var msig = $(this).find('[name=msig]').val();
//		// var path = $(this).find('[name=path]').val();
//		// var target = path + "?msig=" + encodeURIComponent(msig);
//		var target = "../sso/main.html?msig=" + encodeURIComponent(msig);
//		
//		changeWorkArea(target);
//		
//		//變更程式選單顏色
//		$(".act_link").removeClass("act_link");
//        $(this).addClass("act_link");
//	});
	
	$(".menuBack").click(function() {
		var menuDir = $(this).parent().parent().parent().prev().text();
		var menuItem = $(this).parent().parent().prev().children(":first").text();
		var menuContent = $(this).find("span").text();
		var text = menuDir + " &gt; " + menuItem + " &gt; <span style='color:#FFA042;font-weight:900;'>" + menuContent + "</span>";
		$("#workPath").html(text);

		var msig = $(this).find("span").find('[name=msig]').val();
		// var path = $(this).find('[name=path]').val();
		// var target = path + "?msig=" + encodeURIComponent(msig);
		var target = "../sso/main.html?msig=" + encodeURIComponent(msig);
		
		changeWorkArea(target);
		
		//變更程式選單顏色
		$(".act_link").removeClass("act_link");
        $(this).find("span").addClass("act_link");
	});	
	
	$("#chgPwdSel").click(function() {
		$("#chgPwdDiv").dialog({
			height : 450,
			width : 350,
			title : "變更密碼",
			resizable : false,
			closeOnEscape : false,
			modal : true,
			autoOpen : false,
			position: "center",
			zIndex: 2000,
			open: function(event, ui) {
					$("#chgPwdDiv").css("height", "450px");
				  }
		});
		$("#chgPwdDiv").dialog("open"); 
	});
	
	var resizeTimer = null;
	$(window).bind('resize', function() { 
	    if (resizeTimer) clearTimeout(resizeTimer); 
	    resizeTimer = setTimeout(defaultWorkArea, 100);
	});
	
	$("#content").corner("10px sc:#222");
	defaultWorkArea();
        
    // <%--預先建立3層LOV框架--%>
	initLOV(1);
    initLOV(2);
    initLOV(3);

    // 產生上傳用的 hidden iframe 於body尾端並隱藏
	initIfrmForUpload();
	
	$("#chgPwdDiv").dialog({
        autoOpen: false,
        modal: true,
        height: 230,
        width: 570,
        title: "<spring:message code='cw.changePwd.label'/> - <spring:message code='lov.label'/>"
    });
});

function changeWorkArea(target) {
	$("#workAreaFrame").attr("src", target);
	defaultWorkArea();
}

function defaultWorkArea(){
	$('#mainNavTD').css("width","250px");
	$('#mainNav').show();
	$("#content").css("width", document.body.offsetWidth - 10 - $('#mainNav').width());
	setTimeout(function(){ setArrange(false); }, 50);
}

function expandWorkArea(){
	$('#mainNavTD').css("width","auto");
	$('#mainNav').hide();
	$("#content").css("width", document.body.offsetWidth - 10);
	setTimeout(function(){ setArrange(true); }, 50);
}

function setArrange(change) {
	var height = document.body.offsetHeight;
	var width = document.body.offsetWidth;
	$('#main').css('height', height - 10);
	$('#contentTable').css('height', height- 76);
	$('#workAreaFrame').css('height', height - 125);
	if(!change){
		$('#workAreaFrame').css('width', width - 15 - $('#mainNav').width());
		$('#workPathArea').css('width', width - 15 - $('#mainNav').width());
	}else{
		$('#workAreaFrame').css('width', width - 15);
		$('#workPathArea').css('width', width - 15);		
	}
	$('#mainNav').css('height', height - 95);
	
}