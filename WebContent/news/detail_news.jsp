<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<style>
<!--
	#news_panel{margin:0px auto;width:70%;}
	#news_title{height:40px;border-bottom:5px solid #33A1C9;margin:15px 0px;}
	#news_title p{margin:0px auto;text-align: center;font-size:30px;font-weight:bold;font-family:黑体;}
	#news_state{font-size:15px;text-align: center;color:#bbb;margin:10px;font-weight:bold;}
	#news_content{margin:20px;padding:10px 20px;min-height:300px;}
	#news_file{height:40px;border-top:2px solid #33A1C9;border-bottom:2px solid #33A1C9;margin:15px 0px;}
	#news_file p{margin:0px 20px;padding:10px 20px;font-size:15px;font-weight:bold;color:#bbb;}
-->
</style>
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>公告详情
	</h3>
</div>

<div id="news_panel">

<div id="news_title"><p>${news.news.title}</p></div>

<div id="news_state"><p>[${news.importantLevelName}][${news.newsTypeName}] &nbsp;&nbsp;发布人： ${news.news.publisher}&nbsp;&nbsp;发布时间：
<c:set var="date"  value="${news.news.date}"/> ${fn:substring(date, 0, 10)}</p></div>

<div id="news_content">${news.news.content}</div>

<div id="news_file"><p>附件信息：<a href="#" id="downloadFile">${news.news.attachment.name}</a><span id="file_no" style="display:none;">${news.news.attachment.id}</span></p></div>

<!-- <div class="toolbar inner_table_holder" style="border-top: 1px solid #9fc3de;">
	<table class="layout_table">
		<tr>
			<td>
				<a  id="return_btn" class="button" href="#">跳转公告列表</a>
			</td>
		</tr>
	</table>
</div> -->
</div>

<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script src="${ContextPath}/js/fileOper/filename_oper.js"></script>
<script type="text/javascript">
	  var contextPath = "${ContextPath}";
      $(document).ready(function(){
    	  
    	 	$('input[type=submit], a.button , button').button();
    	 	
    	 	$('#downloadFile').click(function(){
    			$.post('${ContextPath}/news/newsdownload',{id:$("#file_no").text()}, function(data){
    				if(data=="false")
    				{
    					alert_dialog("文件不存在！");
    				}else{
    					downloadProveMaterial(data);
    				}
    			}, 'json');
    			event.preventDefault();
    		});
    	 	

    	 	/* $('#return_btn').click(function(){
    			$.post('${ContextPath}/news/news', function(data){
    				  $( "#content" ).empty();
    				  $( "#content" ).append( data );
    			  }, 'html');
    			$("#dialog").dialog("close");
    			event.preventDefault();
    		}); */
    	 	
      });
      
     
</script>

