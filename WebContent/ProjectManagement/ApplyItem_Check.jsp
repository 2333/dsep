<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!-- 点击查看applyItem后的弹框 -->
<style>
<!--
	#applyItem_panel{margin:0px auto;width:70%;}
	#applyItem_name{height:40px;border-bottom:5px solid #33A1C9;margin:15px 0px;}
	#applyItem_name p{margin:0px auto;text-align: center;font-size:30px;font-weight:bold;font-family:黑体;}
	#applyItem_state{font-size:15px;text-align: center;color:#bbb;margin:10px;font-weight:bold;}
	#applyItem_content{margin:20px;padding:10px 20px;min-height:300px;}
	#applyItem_file{height:40px;border-top:2px solid #33A1C9;margin:15px 0px;}
	#applyItem_file p{margin:0px 20px;padding:10px 20px;font-size:15px;font-weight:bold;color:#bbb;}
	#applyItem_content h3{margin-left:0px; text-align:left; font-size:18px;font-family:黑体;font-weight:bold;border-bottom:1px dashed #d4d4d4;padding-bottom:5px;padding-top:20px;}
	#applyItem_content p{font-size:15px;padding-top:15px;}
-->
</style>


<div id="applyItem_panel">

<div id="applyItem_name"><p>${applyItem.itemName }</p></div>

<%-- <div id="applyItem_state"><p>申报状态：${project.currentState } &nbsp;&nbsp;发布时间：${project.startDate } &nbsp;&nbsp;结束时间：${project.endDate }</p></div>
 --%>
<div id="applyItem_content">
	
	<div id="applyItem_introduce">
		<h3>项目目标</h3>
		<p>${applyItem.itemTarget}</p>
	</div>
		
	<div id="applyItem_requirments">
		<h3>项目内容</h3>
		<p>${applyItem.itemContent}</p>
	</div>
	
	<div id="applyItem_notice">
		<h3>团队成员信息</h3>
		<c:if test="${!empty applyItem}">
			<ul id="team_list">
				<c:forEach items="${applyItem.teamMembers}" var="member">
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

<div id="applyItem_file"><p>附件信息：<a href="#" id="downloadFile">${applyItem.attachment.name}</a><span id="file_no" style="display:none;">${applyItem.attachment.id}</span></p></div>
</div>

<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script src="${ContextPath}/js/fileOper/filename_oper.js"></script>
<script type="text/javascript">
	  var contextPath = "${ContextPath}";
      $(document).ready(function(){
    	  
    	 	$('input[type=submit], a.button , button').button();
    	 	
    	 	$('#downloadFile').click(function(){
    	 		$.post('${ContextPath}/project/punit_fileDownload',{id:$("#file_no").text()}, function(data){
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

