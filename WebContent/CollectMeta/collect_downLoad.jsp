<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div>
<div id="downloadFile_dialog_div" class="table">
	<form id="collect_downloadFileForm" method="post" action="javascript:;"
		enctype="multipart/form-data">
		<div>
			<a href="#" class='button' id="downLoadAttach">点击下载附件</a>
			<a href='#' class='button' id="deleteAttach">删除附件</a>
		</div>
	</form>
	<div id="warning_info" class="comment" hidden="true">
		<span class="ui-icon ui-icon-info" style="float: left; margin-right: .10em;position:absolute"></span> 
		<p style="margin-left:0px;">提示:当前无可用附件，请上传后下载！</p>
	</div>
</div>
</div>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script src="${ContextPath}/js/fileOper/filename_oper.js"></script>
<script type="text/javascript">
function initDownLondFunc(){
	//$('input[type=submit], a.button , button').button();
	$('#downLoadAttach').show();
	$('#warning_info').hide();
	$("#downLoadAttach").click(function(){
		downLoadAttach();//collect_oper.js文件中
	});
	$("#deleteAttach").click(function(){
		deleteAttach();
	});
}
function deleteAttach(){
	attachElement.val('');
	$('#downLoadAttach').hide();
	$('#warning_info').show();
	attachOperElem.val("请上传附件");
	/* var url = "${ContextPath}/Collect/toCollect/JqOper/deleteAttach/"+attachId;
	$.post(url,function(data){
		if(data=='success'){
			attachElement.val('请上传附件');
		}
	},'text'); */
}
$(document).ready(function(){
	$('input[type=submit], a.button , button').button();
});
</script>
