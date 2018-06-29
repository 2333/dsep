$(document).ready(function () {
	
	/*菜单栏初始化*/
	$( ".menu_lv1" ).accordion();
	
	/*横向菜单*/
	$("#menu_holder>li").find('ul').addClass('hidden');
	
	$("#menu_holder>li").hover(
		function(){
			$(this).find('ul').removeClass('hidden');
			$(this).find('ul').addClass('display');
		}
		,
		function(){
			$(this).find('ul').removeClass('display');
			$(this).find('ul').addClass('hidden');
		}
	);
	
	/* Home 键跳转*/
	$('span.home').click(function(){
		var url = '#';
		$.each($('a.hori_lvl1') , function(index, item){
			if($(item).find('span').html() == '首页')
				url =  $(item).attr('href');
		});
		
		console.log(url);
		$.post(url, function(data){
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		  }, 'html');
		event.preventDefault();
	});
	
	/*菜单的隐藏
	var flag = true;
	$('#hide').click(function() {
	  if(flag)
	  {
		$('#content').css('margin-left', '15px');
		$( "#menu" ).hide( 'slide', {}, 500);
		$( "#hide" ).animate({left: 0}, 500 );
		flag = false;
	  }
	  else
	  {
		$( "#menu" ).show( 'slide', {}, 500);
		$( "#hide" ).animate({left: 163}, 500 , function(){
			$('#content').css('margin-left', '175px');
		});
		flag = true;
	  }
	});
	*/
	
	
	/* 按钮初始化*/
	$( "input[type=submit], a.button , button" ).button();
	
	
	/*Tab 标签页*/
	//var tabs = $( "#content" ).tabs();

	
	/*点击菜单打开标签页*/
	$('.hori_lvl2 a').click(function(event){
		//activeTab(this);
		$.post(this.href, function(data){
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		  }, 'html');
		event.preventDefault();
	});
	
	$('.hori_lvl1').click(function(event){
		//activeTab(this);
		if(this.href == "#") return;
		$.post(this.href, function(data){
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		  }, 'html');
		event.preventDefault();
	});
	
	/*删除标签页
	tabs.delegate( "span.ui-icon-close", "click", function() {
	  var panelId = $( this ).closest( "li" ).remove().attr( "aria-controls" );
	  $( "#" + panelId ).remove();
	  $( "#content" ).tabs( "refresh" );
	});
	*/
	
	
	/* 表格化表单布局*/
	$(".fr_table td:nth-child(even)").addClass("fr_left");
	$(".fr_table td:nth-child(odd)").addClass("fr_right");
	  
});