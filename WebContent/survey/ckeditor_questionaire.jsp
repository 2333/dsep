<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor" %>
<script type="text/javascript" src="${ContextPath}/ckeditor/ckeditor.js"></script>  
<script type="text/javascript">

function getData(){
	//前台获取ckeditor的内容
	var editor_data = CKEDITOR.instances.disc_editor.getData();   
	alert(editor_data);
	if(data != null){
	return true ;
	}
}

$(document).ready(function() { 
    $('#cke_disc_editor').css('width','700px');
    //bind form using 'ajaxForm' 
    //$('#ckeditor_Form').ajaxForm(options).submit(function(){return false;}); 
});
</script>
<div id="checkitor_dv">
	<form id="ckeditor_Form"  method="post">

 		<p>
			<span class="TextFont"><label id="disc_editor_lb" for="disc_editor">问卷说明</label></span>
			<textarea cols="10" id="disc_editor" name="disc_editor" rows="1"></textarea>
		</p>

	</form>
</div>
	<ckeditor:replace replace="disc_editor" basePath="${ContextPath}/ckeditor/" />
	<script>
		$('#cke_disc_editor').css('width','700px');
	</script>