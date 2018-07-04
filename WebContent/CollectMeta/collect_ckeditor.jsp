<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://ckeditor.com" prefix="ckeditor"%>
<%-- <script type="text/javascript" src="${ContextPath}/ckeditor/ckeditor.js"></script>   --%>
<script type="text/javascript">
function CKupdate(){
	var id = $("#id").val();
	var discIntroduce = CKEDITOR.instances.disc_editor.getData()+'';
	console.log(discIntroduce);
	//var data = {'oper': 'edit','DISC_INTRODUCE' : discIntroduce};
	var editColumn =titleValues[0]; 
	//var data = {'oper': 'edit', column : discIntroduce};
	var data = '{\"'+'oper'+'\":\"'+ 'edit'+'\",\"'+editColumn+'\":\"'+''+'\"}';
	data = JSON.parse(data);
	$.each(data,function(index,item){
		if(index==editColumn){
			data[index] = discIntroduce;
		}
	});
	var primaryKey = id;
	if(id==''){
		primaryKey=0;
	}
	var eidtUrl = '${ContextPath}/Collect/toCollect/JqOper/collectionEdit/'
		+ tableId
		+ '/'
		+ primaryKey
		+ '/'
		+ titleValues
		+ '/'
		+ unitId
		+ '/'
		+ discId
		+ '/'
		+ 0;
	$.post(eidtUrl,data,function(result){
		if(result=='success'){
			alert_dialog('保存成功！');
		}else{
			alert_dialog('保存失败！');
		}
	});
    /* for ( instance in CKEDITOR.instances )
        CKEDITOR.instances[instance].updateElement(); */
}
function getData(){
	//前台获取ckeditor的内容
	var editor_data = CKEDITOR.instances.disc_editor.getData();   
	//alert(editor_data);
	if(data != null){
	return true ;
	}
}
function initCKForm(data){
	
	//CKEDITOR.remove(CKEDITOR.instances['disc_editor']);
	var editor = CKEDITOR.instances.disc_editor;
	//var editor = CKEDITOR.replace('disc_editor');
	//editor.setData("<head><title></title></head><body></body></html>");
	$("#disc_editor_lb").html(data.name);
	requestCKEditorData(editor);
	if(isEditable=='1'){
		$("#save_ckeditor").bind('click',function(){
			CKupdate();
		});
		editor.setReadOnly(false);
	}else{
		$("#save_ckeditor").hide();
		$("#save_ckeditor").unbind('click');
		editor.setReadOnly(true);
	}
	
	CKEDITOR.editorConfig = function( config ) {
		config.width = data.width;
	    config.height = data.height;
	};
}
function requestCKEditorData(editor){
	$.commonRequest({
		url:'${ContextPath}/Collect/toCollect/CollectData/collectionData/'
			+ tableId
			+ '/'
			+ unitId
			+ '/'
			+ discId,
		datatype:'json',
		success: function(data){
			if(data.rows.length>0){
				$.each(data.rows[0],function(i,item){
					console.log(i);
					if(i=='ID'){
						$('#id').val(item);
					}
					if(i=='UNIT_ID'){
						$('#unit_id').val(item);
					}
					if(i=='DISC_ID'){
						$("#disc_id").val(item);
					}
					if(i==titleValues[0]){
						console.log(item);
						//CKEDITOR.instances.disc_editor.setData(item); 
						if($.trim(item)==""){
							editor.setData("");
						}else{
							editor.setData(item);
						}
						
					}
				});
			}else{
				editor.setData("");
			}
			
		},
	});
}
$(document).ready(function() { 
    
    //bind form using 'ajaxForm' 
    //$('#ckeditor_Form').ajaxForm(options).submit(function(){return false;}); 
});
</script>
<div id="checkitor_dv">
	<form id="ckeditor_Form"
		action="${ContextPath}/Collect/toCollect/CkEditor/submit"
		method="post">
		<p>
			<input id="id" name="id" hidden='true'>
		</p>
		<p>
			<input id="unit_id" name="unitId" hidden='true'>
		</p>
		<p>
			<input id="disc_id" name="discId" hidden='true'>
		</p>
		<p>
			<span class="TextFont"><label id="disc_editor_lb"
				for="disc_editor"></label></span>
			<textarea cols="100" id="disc_editor" name="disc_editor" rows="10"></textarea>
		</p>
		<p>
			<a id="save_ckeditor" class="button" href="#"> <span
				class="icon icon-save"></span>保存
			</a>
		</p>
	</form>
</div>
<ckeditor:replace replace="disc_editor"
	basePath="${ContextPath}/ckeditor/" />