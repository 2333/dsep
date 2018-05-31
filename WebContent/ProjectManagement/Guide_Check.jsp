<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<style>
<!--
	#guide_panel{margin:0px auto;width:70%;}
	#guide_name_check{height:40px;border-bottom:5px solid #33A1C9;margin:15px 0px;}
	#guide_name_check p{margin:0px auto;text-align: center;font-size:30px;font-weight:bold;font-family:黑体;}
	#guide_state_check{font-size:15px;text-align: center;color:#bbb;margin:10px;font-weight:bold;}
	#guide_content_check{margin:20px;padding:10px 20px;min-height:300px;}
	#guide_file_check{height:40px;border-top:2px solid #33A1C9;margin:15px 0px;}
	#guide_file_check p{margin:0px 20px;padding:10px 20px;font-size:15px;font-weight:bold;color:#bbb;}
	#guide_content_check h3{margin-left:0px; text-align:left; font-size:18px;font-family:黑体;font-weight:bold;border-bottom:1px dashed #d4d4d4;padding-bottom:5px;padding-top:20px;}
	#guide_content_check p{font-size:15px;padding-top:15px;}
-->
</style>


<div id="guide_panel">

<div id="guide_name_check"><p>${project.project.projectName }</p></div>

<div id="guide_state_check"><p>申报状态：${project.currentState } &nbsp;&nbsp;发布时间：${project.startDate } &nbsp;&nbsp;结束时间：${project.endDate }</p></div>

<div id="guide_content_check">
	
	<div id="guide_introduce_check" style="min-height:100px">
		<h3>项目简介</h3>
		<p>${project.project.projectIntro}</p>
	</div>
		
	<div id="guide_requirments_check" style="min-height:100px">
		<h3>申报条件</h3>
		<p>${project.project.projectRestrict}</p>
	</div>
	
	<div id="guide_notice_check" style="min-height:100px">
		<h3>答辩事项</h3>
		<p>${project.project.projectDetail}</p>
	</div>
	
</div>

<div id="guide_file_check"><p>附件信息：<a href="#" id="downloadFile_check">${project.project.attachment.name}</a><span id="file_no_check" style="display:none;">${project.project.attachment.id}</span></p></div>
</div>

<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script src="${ContextPath}/js/fileOper/filename_oper.js"></script>
<script type="text/javascript">
	  var contextPath = "${ContextPath}";
      $(document).ready(function(){
    	  
    	 	$('input[type=submit], a.button , button').button();
    	 	
    	 	$('#downloadFile_check').click(function(){
    	 		$.post('${ContextPath}/project/punit_fileDownload',{id:$("#file_no_check").text()}, function(data){
    				if(data=="false")
    				{
    					alert_dialog("文件不存在！");
    				}else{
    					downloadProveMaterial(data);
    				}
    			}, 'json');
    			event.preventDefault();
    		});
      });
      
     
</script>

