<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!-- 点击查看passItem后的弹框 -->
<style>
<!--
	#passItem_panel{margin:0px auto;width:70%;}
	#passItem_name{height:40px;border-bottom:5px solid #33A1C9;margin:15px 0px;}
	#passItem_name p{margin:0px auto;text-align: center;font-size:30px;font-weight:bold;font-family:黑体;}
	#passItem_state{font-size:15px;text-align: center;color:#bbb;margin:10px;font-weight:bold;}
	#passItem_content{margin:20px;padding:10px 20px;min-height:300px;}
	#passItem_file{height:40px;border-top:2px solid #33A1C9;margin:15px 0px;}
	#passItem_file p{margin:0px 20px;padding:10px 20px;font-size:15px;font-weight:bold;color:#bbb;}
	#passItem_content h3{margin-left:0px; text-align:left; font-size:18px;font-family:黑体;font-weight:bold;border-bottom:1px dashed #d4d4d4;padding-bottom:5px;padding-top:20px;}
	#passItem_content p{font-size:15px;padding-top:15px;}
-->
</style>


<div id="passItem_panel">

<div id="passItem_name"><p>${passItem.itemName }</p></div>

<%-- <div id="passItem_state"><p>申报状态：${project.currentState } &nbsp;&nbsp;发布时间：${project.startDate } &nbsp;&nbsp;结束时间：${project.endDate }</p></div>
 --%>
<div id="passItem_content">
	
	<div id="passItem_introduce">
		<h3>项目目标</h3>
		<p>${passItem.itemTarget}</p>
	</div>
		
	<div id="passItem_requirments">
		<h3>项目内容</h3>
		<p>${passItem.itemContent}</p>
	</div>
	
	<div id="passItem_notice">
		<h3>团队成员信息</h3>
		<c:if test="${!empty passItem}">
			<ul id="team_list">
				<c:forEach items="${passItem.teamMembers}" var="member">
					<li class='team_list_li'>
						<input type='hidden' class='li_teacherId' value='${member.id }'/>
						<span class='li_teacherName'>${member.name }</span>
						:${member.email}地址：${member.info}
					</li>
				</c:forEach>
			</ul>
		</c:if>
	</div>
	
</div>

<div id="passItem_file">
	<p>附件信息：
	<a href="#" id="downloadFile">${passItem.attachment.name}</a>
	<span id="file_no" style="display:none;">${passItem.attachment.id}</span>
	</p>
	<p>中期报告：
	<a href="#" id="downloadMedium">${passItem.mediumReport.name}</a>
	<span id="medium_no" style="display:none;">${passItem.mediumReport.id}</span>
	</p>
	<p>结题报告：
	<a href="#" id="downloadFinal">${passItem.finalReport.name}</a>
	<span id="final_no" style="display:none;">${passItem.finalReport.id}</span>
	</p>
</div>
</div>

<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script src="${ContextPath}/js/fileOper/filename_oper.js"></script>
<script type="text/javascript">
	  var contextPath = "${ContextPath}";
      $(document).ready(function(){
    	  
    	 	$('input[type=submit], a.button , button').button();
    	 	
    	 	$('#downloadFile').click(function(){
    	 		$.post('${ContextPath}/project/papply_fileDownload',{id:$("#file_no").text()}, function(data){
    				if(data=="false")
    				{
    					alert_dialog("文件不存在！");
    				}else{
    					downloadProveMaterial(data);
    				}
    			}, 'json');
    			event.preventDefault();
    		});
    	 	
    	 	$('#downloadMedium').click(function(){
    	 		$.post('${ContextPath}/project/papply_fileDownload',{id:$("#medium_no").text()}, function(data){
    				if(data=="false")
    				{
    					alert_dialog("文件不存在！");
    				}else{
    					downloadProveMaterial(data);
    				}
    			}, 'json');
    			event.preventDefault();
    		});
    	 	
    	 	$('#downloadFinal').click(function(){
    	 		$.post('${ContextPath}/project/papply_fileDownload',{id:$("#final_no").text()}, function(data){
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

