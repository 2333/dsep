<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div>
<div id="uploadFile_dialog_div" class="table">
	<form id="collect_uploadFileForm" method="post" action="javascript:;"
		enctype="multipart/form-data">
		<table>
			<tr class="hidden">
	             <td>
	             	<input type="hidden" id="sessionid" value="${pageContext.session.id}" />
	             </td>
            </tr>  
			<tr>
				<td><span class="TextFont">附件上传：</span></td>
				<td colspan="4"><input id="file_upload" name="file_upload"
					type="file"/>
					<div id="fileQueue"></div> <a class="button"
					href="javascript:$('#file_upload').uploadify('upload')">上传</a>| <a
					class="button"
					href="javascript:$('#file_upload').uploadify('cancel')">取消上传</a></td>
			</tr>
		</table>
	</form>
</div>
</div>
<script type="text/javascript">
function initUploadConfig(){
	createUploadUrl();
	//$('input[type=submit], a.button , button').button();
	$("#file_upload").uploadify({  
        'swf'       : '${ContextPath}/js/uploadify/uploadify.swf',  
        //'uploader'       : '${ContextPath}/Collect/toCollect/JqOper/uploadFile/'+unitId,//后台处理的请求
        'uploader'       : uploadUrl,  
        'cancelImg'      : '${ContextPath}/js/uploadify/uploadify-cancel.png',  
        'folder'         : 'uploads',//您想将文件保存到的路径  
        'queueID'        : 'fileQueue',//与下面的id对应  
        'queueSizeLimit'  :1,  
        'auto'           : false,  
        'multi'          : true,  
        'buttonText'     : '点击上传',
        'onUploadSuccess':function(file,data,response){
        	console.log(attachElement);
        	attachElement.val(data);
        	attachId = data;
        	attachOperElem.val("查看附件");
        }
    });
}
$(document).ready(function(){
	$('input[type=submit], a.button , button').button();
});
</script>
