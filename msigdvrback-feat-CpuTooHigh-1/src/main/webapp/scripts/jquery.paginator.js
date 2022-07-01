/*
 * jquery.paginator.js 
 * version 1.0
 * author: Joey Hsu
 * Created: 2012/09/07
 */
;(function($){
	$.fn.paginator = function(options){
		var defaults = {
				perPage: 10,
				prefix: "共",
				suffix: "笔",
				disFirstImg: "../images/firstPageEnd.gif",
				enFirstImg:  "../images/firstPage.gif",
				disPrevImg:  "../images/prePageEnd.gif",
				enPrevImg:   "../images/prePage.gif",
				disNextImg:  "../images/nextPageEnd.gif",
				enNextImg:   "../images/nextPage.gif",
				disLastImg:  "../images/lastPageEnd.gif",
				enLastImg:   "../images/lastPage.gif",
				disCursor: "default",
				enCursor: "pointer"
		}
		var _options = $.extend(defaults, options);
		var _trs = this.find("tbody > tr");
		var _rows = _trs.length;
		if(_rows == 1 && $(_trs[0]).children().first().attr("colspan") != undefined ){
			$(_trs[0]).addClass("rowC");
			_rows = 0;
		}
		var _pages = Math.ceil(_rows / _options.perPage);
		var buttons = "<span style='float:right;'>"+_options.prefix +" "+ _rows +" "+ _options.suffix
						+ "<button id='_firstPage' class='pageBtn' value='1' disabled><img src='"+_options.disFirstImg+"' border='0'/></button>"
						+ "<button id='_prePage' class='pageBtn' disabled><img src='"+_options.disPrevImg+"' border='0'/></button>"
						+ "<select id='_pageSel' style='margin:0 2px;'></select>"
						+ "<button id='_nextPage' class='pageBtn' disabled><img src='"+_options.disNextImg+"' border='0'/></button>"
						+ "<button id='_lastPage' class='pageBtn' value='"+_pages+"' disabled><img src='"+_options.disLastImg+"' border='0'/></button>"
						+ "</span>";
		
		var col = this.find("thead > tr > th").length;
		this.find("thead > tr").first().before("<tr><th colspan='"+col+"'>"+buttons+"</th></tr>");
		for(var i=1; i <= _pages; i++){
			$("#_pageSel").append("<option value='"+i+"'>"+i+"</option>");
		}
		
		$("#_pageSel").change(function(){
			chgPage(parseInt($(this).val(), 10));
		});
		this.find("thead span .pageBtn").click(function(){
			chgPage(parseInt($(this).val(), 10));
		});
		
		chgPage(1);
		
		function chgPage(gotoPage){
			if(gotoPage > _pages || gotoPage < 1){
				return false;
			}
			_trs.hide();
			for (var i = (gotoPage - 1) * _options.perPage; i <= gotoPage * _options.perPage - 1; i++){
				$(_trs[i]).attr("class", i % 2 == 0 ? "rowC" : "rowS").show();
			}
			$("#_pageSel").val(gotoPage);
			
			if(gotoPage >= 1 && gotoPage <= _pages){
				$("#_prePage").val(gotoPage - 1);
				$("#_nextPage").val(gotoPage + 1);
			}
			
			if(gotoPage >= _pages){
				$("#_nextPage").css("cursor", _options.disCursor).attr("disabled", true).children("img").attr("src", _options.disNextImg);
				$("#_lastPage").css("cursor", _options.disCursor).attr("disabled", true).children("img").attr("src", _options.disLastImg);
			}
			else{
				$("#_nextPage").css("cursor", _options.enCursor).attr("disabled", false).children("img").attr("src", _options.enNextImg);
				$("#_lastPage").css("cursor", _options.enCursor).attr("disabled", false).children("img").attr("src", _options.enLastImg);
			}
			
			if(gotoPage <= 1){
				$("#_firstPage").css("cursor", _options.disCursor).attr("disabled", true).children("img").attr("src", _options.disFirstImg);
				$("#_prePage").css("cursor", _options.disCursor).attr("disabled", true).children("img").attr("src", _options.disPrevImg);
			}
			else{
				$("#_firstPage").css("cursor", _options.enCursor).attr("disabled", false).children("img").attr("src", _options.enFirstImg);
				$("#_prePage").css("cursor", _options.enCursor).attr("disabled", false).children("img").attr("src", _options.enPrevImg);
			}
		}
	};
})(jQuery);