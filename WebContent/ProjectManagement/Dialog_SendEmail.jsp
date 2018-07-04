<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div  >
	<form id="fm_result" class="fr_form" method="post"> 
	 	<table class="layout_table left">
			<tr class="hidden">
	             <td >ID：</td>  
	             <td >
	                <input id="emailPageProjectId" type="text" name="id" value="${projectId}" readonly/>  
	             </td>
            </tr>  
			<tr>
				<td style="width:150px;">
				    <span class="TextFont">设置检查时间：</span>
				</td>
				<td style="width:150px;">
					<input type="text" id="startDate"/>
				</td>
			</tr>
			<tr>
				<td style="width:150px;">
				    <span class="TextFont">设置邮件主题：</span>
				</td>
				<td  colspan="4">
					<input type="text" id="emailTitle" size="60"/>
				</td>
			</tr>
			<tr>
				<td>
				    <span class="TextFont">编辑邮件内容：</span>
				</td>
				<td colspan="4">
					<textarea name="opinion" id="opinion"></textarea> 
					<script type="text/javascript">
					var content  = "<p>尊敬的\${teacher}老师：</p><p>&nbsp;&nbsp;&nbsp;&nbsp;校方会在2014-12-03对您的“\${project}”项目开展${status}，请您准备好相关工作。<br/></p>";
					var editor = new baidu.editor.ui.Editor({initialFrameHeight:150,initialFrameWidth:600,autoClearinitialContent :true, });
					editor.addListener( 'ready', function( editor ) {
						setEditorContents(content); //编辑器家在完成后，让编辑器拿到焦点
					 } );
					editor.render("opinion");
					</script>
				</td>
			</tr>
			<tr>
				<td></td>
				<td colspan="4">
					<span class="TextFont">注：邮件中的"\${teacher}"和"\${project}"代表占位符，群发邮件时系统会根据每个老师申报的项目信息，自动将其替换为教师和项目的真实名称。</span>
				</td>
			</tr>
			<tr>
				<td style="width:150px;">
				    <span class="TextFont">设置附件标题：</span>
				</td>
				<td  colspan="4">
					<input type="text" id="attachmentName" size="60" disabled="disabled"/>
				</td>
			</tr>
			
			<tr >
				<td>
				    <span class="TextFont">附件：</span>
				</td>
				<td colspan="4">
					<input id="file_upload" name="file_upload" type="file">
					<div id="fileQueue"></div>
					<a class="button" id="upload">上传</a>| 
         			<a class="button" id="cancel">取消上传</a>	|
         			<a class="button" id="delete">删除附件</a>	
         			<b>您上传的附件：</b>
         			<a id="file_detail" style="color:Green;font-weight:bold;"></a>
         			<a id="file_no" style="display:none"></a>
				</td>
			</tr>
			</table>
	</form>
</div>
<script type="text/javascript">
function setEditorContents(content) {
	editor.setContent(content);
}


$(document).ready(function(){
	$('input[type=submit], a.button , button').button();
	
	$("#file_upload").uploadify({  
        'swf'            : '${ContextPath}/js/uploadify/uploadify.swf',  
        'uploader'       : '${ContextPath}/project/punit_emailFileUpload?jsessionid=' + $("#sessionid").val(),//后台处理的请求   
        'queueID'        : 'fileQueue',
        'queueSizeLimit' : 1,  
        'auto'           : false,
        'buttonText'     : '附件',
        'onUploadSuccess':function(file,data,response){
        	$("#file_detail").html(file.name);
        	$("#file_no").html(data);
        	$("#attachmentName").val(file.name);
        	//alert(data);
        }
    });  
	$('#upload').click(function(){
 		$('#file_upload').uploadify('upload');
	});
 	
 	$('#cancel').click(function(){
 		$('#file_upload').uploadify('cancel');
	});
	
 	$('#delete').click(function(){
 		$.post('${ContextPath}/project/punit_emailFileDelete',{id:$("#file_no").text()} ,function(data){
 			if(data){
 				alert_dialog("附件已删除！");
 				$("#file_detail").text("");
 				$("#file_no").text("");
 			}else{
 				alert_dialog("尚无附件或附件删除失败！");	
 			}
	  	}, 'json');
	});
 	
	$.datepicker.regional['zh-CN'] = {  
		      clearText: '清除',  
		      clearStatus: '清除已选日期',  
		      closeText: '关闭',  
		      closeStatus: '不改变当前选择',  
		      prevText: '<上月',  
		      prevStatus: '显示上月',  
		      prevBigText: '<<',  
		      prevBigStatus: '显示上一年',  
		      nextText: '下月>',  
		      nextStatus: '显示下月',  
		      nextBigText: '>>',  
		      nextBigStatus: '显示下一年',  
		      currentText: '今天',  
		      currentStatus: '显示本月',  
		      monthNames: ['一月','二月','三月','四月','五月','六月', '七月','八月','九月','十月','十一月','十二月'],  
		      monthNamesShort: ['一','二','三','四','五','六', '七','八','九','十','十一','十二'],  
		      monthStatus: '选择月份',  
		      yearStatus: '选择年份',  
		      weekHeader: '周',  
		      weekStatus: '年内周次',  
		      dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
		      dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
		      dayNamesMin: ['日','一','二','三','四','五','六'],  
		      dayStatus: '设置 DD 为一周起始',  
		      dateStatus: '选择 m月 d日, DD',  
		      dateFormat: 'yy-mm-dd-hh-mm',  
		      firstDay: 1,  
		      initStatus: '请选择日期',  
		      isRTL: false  
		    };
	   $.datepicker.setDefaults($.datepicker.regional['zh-CN']);

	   $("#startDate").datepicker({
		   yearRange:'1900:2020',
		   changeMonth: true,
           changeYear: true,
           showButtonPanel: true,
		   dateFormat: 'yy-mm-dd',  //日期格式，自己设置              buttonImage: 'calendar.gif',  //按钮的图片路径，自己设置              buttonImageOnly: true,  //Show an image trigger without any button.             showOn: 'both',//触发条件，both表示点击文本域和图片按钮都生效         yearRange: '1990:2008',//年份范围          clearText:'清除',//下面的就不用详细写注释了吧，呵呵，都是些文本设置         closeText:'关闭',         prevText:'前一月',         nextText:'后一月',         currentText:' ',          monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],  
		   
	   });
	   
	   $("#startDate").change(function() {
		   //alert(editor.getContent()); 
		   setEditorContents(editor.getContent().replace(/\S\S\S\S-\S\S-\S\S/g, $(this).val()));
	   });
	   
	   jQuery("#startDate").click(function(){
			$("#ui-datepicker-div").css({"z-index":"2000"});
		 });
	   
	   // 
	   // /^([0-2][0-9]):([0-5][0-9]):([0-5][0-9])$/;
});
</script>