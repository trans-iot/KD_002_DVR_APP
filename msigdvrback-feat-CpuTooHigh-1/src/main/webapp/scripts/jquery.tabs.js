$(function(){
	// 預設顯示第一個 Tab
	var _showTab = 0;
	var $defaultLi = $('ul.tabs li a').eq(_showTab).addClass('active');
	$('ul.tabs li').parent().parent().attr('selIndex', _showTab);
	$($defaultLi.attr('href')).siblings().hide();
	
	// 當 li 頁籤被點擊時...
	// 若要改成滑鼠移到 li 頁籤就切換時, 把 click 改成 mouseover
	$('ul.tabs li').click(function() {
		// 找出 li 中的超連結 href(#id)
		var $this = $(this).children('a'), _clickTab = $this.attr('href');
		// 把目前點擊到的 li 頁籤加上 .active
		// 並把兄弟元素中有 .active 的都移除 class
		$this.addClass('active').parent().siblings('li').children('a').removeClass('active');
		var selIndex = $(this).prevAll().length;
		$(this).parent().parent().attr('selIndex', selIndex);
		// 淡入相對應的內容並隱藏兄弟元素
		$(_clickTab).stop(false, true).fadeIn().siblings().hide();

		return false;
	}).find('a').focus(function(){
		this.blur();
	});
});