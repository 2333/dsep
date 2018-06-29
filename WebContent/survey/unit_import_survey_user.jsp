<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="selectbar inner_table_holder">
	<form id="fm_addNews" class="fr_form" method="post">  
		<table class="layout_table left">
			<tr class="hidden">
	             <td>
	             	<input type="hidden" id="sessionid" value="${pageContext.session.id}" />
	             </td>
            </tr>  
			<tr>
				<td>
				    <span class="TextFont">导入用户信息：</span>
				</td>
				<td colspan="4">
					<input id="file_upload" name="file_upload" type="file">
					<div id="fileQueue"></div>
					<a class="button" id="upload">上传Excel</a>| 
         			<a class="button" id="cancel">取消上传</a>	|
         			<a class="button" id="delete">删除附件</a>	
         			<b>您上传的附件：</b><a id="file_detail" style="color:Green;font-weight:bold;"></a><a id="file_no" style="display:none"></a>
				</td>
			</tr>
		</table>
	</form>
</div>
<div>
	<a href="#" id="download">下载“学科导入调查对象信息模板.xls”</a>
</div>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script src="${ContextPath}/js/fileOper/filename_oper.js"></script>
<script type="text/javascript">
	
	$(function(){ 
		    
		$( "input[type=submit], a.button , button" ).button();
		$("#download").click(function() {
			var url = "${ContextPath}/InfoMaintain/unitQNRInfo_download/E082B40F1E30401B9501566DEDC2FC87";
			$.post(url,function(filePath){
				//根据文件路径和分隔符获取文件名
				var fileArray = filePath.split("/");
				var fileName = fileArray[fileArray.length-1];

				//根据文件全路径获取文件所在文件夹的绝对路径
				var lastIndex = filePath.lastIndexOf("/");
				var path = filePath.substring(0,lastIndex+1);
					
				var link = '${ContextPath}/download.jsp?'+"filepath="+path+"&filename="+fileName;		
				window.open(link,"_self");
			}, 'json');
		});
		$("#file_upload").uploadify({  
            'swf'            : '${ContextPath}/js/uploadify/uploadify.swf',  
            'uploader'       : '${ContextPath}/InfoMaintain/unitQNRInfo_upload?jsessionid=' + $("#sessionid").val(),//后台处理的请求    
            'queueID'        : 'fileQueue',
            'queueSizeLimit' : 1,  
            'auto'           : false,
            'buttonText'     : '附件',
            'onUploadSuccess':function(file,data,response){
            	$("#file_detail").html(file.name);
            	$("#file_no").html(data);
            	$("#user_list").setGridParam({url:'${ContextPath}/InfoMaintain/unitQNRInfo_user_list'}).trigger("reloadGrid");
            }
        });  

	 	
	 	$('#upload').click(function(){
	 		$('#file_upload').uploadify('upload');
		});
	 	
	 	$('#cancel').click(function(){
	 		$('#file_upload').uploadify('cancel');
		});
	 	
	 	$('#delete').click(function(){
	 		$.post('${ContextPath}/InfoMaintain/unitQNRInfo_deleteFile',{id:$("#file_no").text()} ,function(data){
	 			if(data){
	 				alert_dialog("附件已删除！");
	 				$("#file_detail").text("");
	 				$("#file_no").text("");
	 			}else{
	 				alert_dialog("尚无附件或附件删除失败！");	
	 			}
			  	}, 'json');
		});
	});
</script>