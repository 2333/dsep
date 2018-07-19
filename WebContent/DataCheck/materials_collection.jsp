<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
a{text-decoration:none;font-family:宋体;font-size:12px;}

.left_box{width:22%;float:left;}
.hori_li{background-color:#dfeffc;padding:12px;border-bottom:1px solid #dcdcdc;border-top:1px solid #fff;color:#727272;text-shadow:0px 1px 0px rgba(255, 255, 255, 0.8);}
.selected{background-color:#f4f4f4;}
.label{padding-left:0px;text-align:center;font-family:宋体;font-size:14px;font-weight:bold;}
.arrow{display:block;width:16px;height:16px;background:no-repeat center;float:right;}
.up{background-image:url(${ContextPath}/images/arrow_u.png);}
.down{background-image:url(${ContextPath}/images/arrow_d.png);}
.left_menu{display:none;}
.left_header
{
	padding:12px;
	height:10px;
	color:white;
	text-shadow:0px 1px 0px rgba(255, 255, 255, 0.2);
 	background-image:url(${ContextPath}/images/hori_bg.png); 
}

div.left_header:hover{
	background-image:url(${ContextPath}/images/menu_ho.png);
	cursor:pointer;
}

.hori_li:not(.selected):hover{
	background:#e7e7e7;
	cursor:pointer;
}
.hori_li:not(.selected):active{
	background:#f1f1f1;
	cursor:pointer;
} 
</style>
<div id="left_box" class="left_box">
	<div class="left_header">
		<span class="label" >采集项（点击展开/关闭数据项）</span>
		<span class="arrow up"></span>
	</div>
	<ul class="left_menu">
		<li id="E20120101" class="hori_li"><a href="#" class="TextFont">实习实践基地</a></li>
		<li id="E20120102" class="hori_li"><a href="#" class="TextFont">校内导师结构</a></li>
		<li id="E20120103" class="hori_li"><a href="#" class="TextFont">学缘结构</a></li>
	</ul>
	
</div> 
<script type="text/javascript">
$(document).ready(function(){
	   
	/* 滑动/展开 */
	$(".left_header").click(function(){
												   
		var arrow = $(this).find("span.arrow");
	
		if(arrow.hasClass("up")){
			arrow.removeClass("up");
			arrow.addClass("down");
		}else if(arrow.hasClass("down")){
			arrow.removeClass("down");
			arrow.addClass("up");
		}
		$(this).parent().find("ul.left_menu").slideToggle();
		
	});
	
	$(".hori_li").click(function(){
 		$("li").each(function(){
			if($(this).hasClass("selected"))
				$(this).removeClass("selected");
		}); 
		$(this).addClass("selected");
		$("#collect_grid").css("display","");
	});
	
	
});
</script>