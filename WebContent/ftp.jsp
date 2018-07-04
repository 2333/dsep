<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div>
	<input id="file_upload" name="file_upload" type="file">
	<div id="fileQueue"></div>
	<a class="button" id="upload">上传</a>| 
	<a class="button" id="cancel">取消上传</a>	|
	<a class="button" id="delete">删除附件</a>	
	<b>您上传的附件：</b><a id="file_detail" style="color:Green;font-weight:bold;"></a><a id="file_no" style="display:none"></a>
</div>

<script type="text/javascript">
	  
      $(document).ready(function(){
    	  
    	 	$('input[type=submit], a.button , button').button();
    	 	
    	 	$("#file_upload").uploadify({  
                'swf'            : '${ContextPath}/js/uploadify/uploadify.swf',  
                'uploader'       : '${ContextPath}/ftp/ftpUpload',//后台处理的请求    
                'queueID'        : 'fileQueue',
                'queueSizeLimit' : 1,  
                'auto'           : false,
                'buttonText'     : '附件',
                'onUploadSuccess':function(file,data,response){
                	$("#file_detail").html(file.name);
                	$("#file_no").html(data);
                }
            });
    	 	
    	 	$('#upload').click(function(){
    	 		$('#file_upload').uploadify('upload');
    		});
    	 	
    	 	$('#cancel').click(function(){
    	 		$('#file_upload').uploadify('cancel');
    		});
    	 	
    	 	$('#delete').click(function(){
    	 		$.post('${ContextPath}/ftp/delete',{id:$("#file_no").text()} ,function(data){
    	 			if(data){
    	 				alert_dialog("附件已删除！");
    	 				$("#file_detail").text("");
    	 				$("#file_no").text("");
    	 			}else
    	 			{
    	 				alert_dialog("尚无附件或附件删除失败！");	
    	 			}
  			  	}, 'json');
    		});
    	 
      });
      
     
</script>