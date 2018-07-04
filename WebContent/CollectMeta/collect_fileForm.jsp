<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- <div id="fileForm">
<p class="smallTitle">专业学位学生培养简介</p>
		<table id="discInfo_tb" class="infofr_table">
			<tr>
				<td style="width:100px;"><label id="unitId_lb" for="unitId_lb" class="fr_label"><span class="TextFont">学校代码：</span></label></td>
				<td><input id="fileFormUnitId" type="text" readonly="readonly"/></td>
				<td><label id="discId_lb" for="discId_lb" class="fr_label"><span class="TextFont">学科代码：</span></label></td>
				<td><input id="fileFormDiscId" type="text" readonly="readonly" /></td>
				<td><label for="attachNo_lb" class="fr_label"><span class="TextFont">附件编号：</span></label></td>
				<td><input id="attachNo" type="text" readonly="readonly"/></td>
				<td><input id="fileFormId" type="text" style="display:none"/></td>
				<td><a href="#" id ="downFileAttach" style="display:none">(点击下载附件)</a>
					&nbsp&nbsp&nbsp
					<a href="#" id ="delFileAttach" style="display:none">(点击删除附件)</a>
				</td>
				
				<td><input id="attachName" type="text" readonly="readonly"/></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align:left;padding-left:10px;">
					<a style="display:none" id="uploadFileBt" class="button" href="#"><span class="icon icon-search"></span>上传附件</a>
					<a style="display:none" id="downLoadTemplet" class="button" href="#"><span class="icon icon-search"></span>下载模板</a>
				</td>	
			</tr>
		</table>
</div> -->
<div id="fileForm" class="infofr_div">
      <fieldset class = "infofr_fieldset" >
      		<legend class="smallTitle">专业学位学生培养简介</legend>
      		<form action="" class = "infofr_form" id = "basicInfoForm" >
      			<%-- <label id="unitId_lb" for="unitId_lb" class="fr_label"><span class="TextFont">学校代码：</span></label>
      			<input id="fileFormUnitId" type="text" readonly="readonly" class="info_input"/>
      			<span class = "validSpan"></span>
      			<label id="discId_lb" for="discId_lb" class="fr_label"><span class="TextFont">学科代码：</span></label>
      			<input id="fileFormDiscId" type="text" readonly="readonly" class="info_input" value="${brief.discId}"/>
      			<span class = "validSpan"></span>
      			<br> --%>
      			<label id="sumbitTime_lb" for="sumbitTime_lb" class="fr_label" ><span class="TextFont">上传时间：</span></label>
      			<input id="fileFormsbTime" type="text" readonly="readonly" 
      				class="info_input" size="30"/>
      			<span class = "validSpan"></span>
      			<a href="#" class ="button" id ="downFileAttach" style="display:none">
      				<span class="icon icon-download"></span>下载附件</a>
				<a href="#" class="button" id ="delFileAttach" style="display:none">
					<span class="icon icon-del"></span>删除附件</a>
      			<input id="attachNo" type="text" readonly="readonly" style="display:none"/>
				<input id="fileFormId" type="text" style="display:none"/>
      		</form>
      </fieldset>	
      <fieldset id="operFileForm"class = "infofr_fieldset" >
      		<legend class="smallTitle">操作</legend>
      		<form action="" class = "infofr_form" id = "basicInfoForm" >
      			<a style="display:none" id="uploadFileBt" class="button" href="#">
      				<span class="icon icon-submit"></span>上传附件</a>
				<a style="display:none" id="downLoadTemplet" class="button" href="#">
					<span class="icon icon-download"></span>下载模板</a>
      		</form>
      </fieldset>	
</div>
<div hidden="true">
	<div id="uploadFileForm_dialog_dv" class="table">
		<form id="collect_uploadFileForm" method="post" action="javascript:;"
			enctype="multipart/form-data">
			<table>
				<tr class="hidden">
		             <td>
		             	<input type="hidden" id="sessionid" value="${pageContext.session.id}" />
		             </td>
           		</tr>
				<tr>
					<td><span class="TextFont">文件上传：</span></td>
				<td align="left" colspan="4">
					<input id="file_form_upload" name="file_upload" type="file">
					<div id="fileFormQueue"></div>
					<a href="#" class="button" id="upload_file_form">上传</a>| 
         			<a href="#" class="button" id="cancel_file_form">取消上传</a>	|
				</td>
				</tr>
			</table>
		</form>
	</div>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script src="${ContextPath}/js/fileOper/filename_oper.js"></script>
</div>
<script type="text/javascript">
function controlBts(){
	$("#uploadFileBt").hide();
	$("#downLoadTemplet").hide();
	$("#delFileAttach").hide();
	if(isEditable=='1'){
		$("#uploadFileBt").show();
		$("#downLoadTemplet").show();
		//$("#delFileAttach").show();
	}
}
 function readyFileForm(){
	$('input[type=submit], a.button , button').button();
	$("#uploadFileBt").click(function(){
		unitId = $("#unit_Id").val();
		discId = $("#disc_Id").val();
		//initUploadFile();
		pkValue = $("#fileFormId").val();
		if(pkValue==''){
			pkValue='0';
		}
		console.log(titleValues);
		initUploadFile(unitId,discId,tableId,pkValue,titleValues);
		upLoadFileForm();
	});
	$("#downFileAttach").click(function(){
		attachId=$("#attachNo").val();//该变量在collect_view.jsp
		downLoadAttach();//该函数在collect_oper.js中
	});
	$("#delFileAttach").click(function(){
		unitId = $("#unit_Id").val();
		discId = $("#disc_Id").val();
		var pkValue = $("#fileFormId").val();
		if(pkValue==''){
			pkValue='0';
		}
		var attachId = $("#attachNo").val();
		delFileFormAttach(unitId,discId,tableId,pkValue,titleValues,attachId);
	});
	$("#downLoadTemplet").click(function(){
		downLoadTemplate();
	});
	//附件上传
	$('#upload_file_form').click(function(){
 		$('#file_form_upload').uploadify('upload');
	});
 	
	//附件取消
 	$('#cancel_file_form').click(function(){
 		$('#file_form_upload').uploadify('cancel');
	});
	
}
	function downLoadTemplate(){
		var url = "${ContextPath}/Collect/toCollect/JqOper/downLoadTemplate/"+templateId;
		$.post(url,function(data){
				console.log('path: '+data);
				downloadProveMaterial(data);
		}, 'json');
	}
	function delFileFormAttach(unitId,discId,tableId,pkValue,titleValues,attachId){
		var url = '${ContextPath}/Collect/toCollect/JqOper/delFileFormAttach/'
			+unitId
			+'/'
			+discId
			+'/'
			+tableId
			+'/'
			+pkValue
			+'/'
			+titleValues[0]
			+'/'			
			+attachId;//后台处理的请求 
		$.post(url,function(data){
			if(data=='success')
			{
				alert_dialog('文件删除成功！');
				initFileFormData();
			}else{
				alert_dialog('文件删除失败！');
			}
			
		}, 'text');
	}
	function upLoadFileForm(){
		$("#uploadFileForm_dialog_dv").dialog({
			 title:"上传附件",
 	  		 height:'250',
 	  	     width:'400',
 	  		 position:'center',
 	  		 modal:true,
 	  		 draggable:true,
 	  	     buttons:{  
	  		    	"关闭":function(){ 
	  		    	    $("#uploadFileForm_dialog_dv").dialog("close");
	  		    	}
		     }
		  });
	}
	//{unitId}/{discId}/{entityId}/{pkValue}/{titleValue}
	function initUploadFile(unitId,discId,tableId,pkValue,titleValues){
		$("#file_form_upload").uploadify({  
            'swf'            : '${ContextPath}/js/uploadify/uploadify.swf',  
            'uploader'       : '${ContextPath}/Collect/toCollect/JqOper/uploadFileForm/'
								+unitId
								+'/'
								+discId
								+'/'
								+tableId
								+'/'
								+pkValue
								+'/'
								+titleValues[0]+'?jsessionid='+ $("#sessionid").val(),//后台处理的请求  
            'queueID'        : 'fileFormQueue',
            'queueSizeLimit' : 1,  
            'auto'           : false,
            'buttonText'     : '选择文件',
            'onUploadSuccess':function(file,data,response){
            	if(data=='success'){
	        		alert_dialog('上传文件成功！');
	        		initFileFormData();
	        	}
            }
        });
	}
	function initFileForm(){
		readyFileForm();
		initFileFormData();
		//initUploadFile();
	}
	/**
	* 
	*/
	function initFileFormData(){
		controlBts();
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
						
						if(i=='ID'){
							$('#fileFormId').val(item);
						}
						if(i=='UNIT_ID'){
							$('#fileFormUnitId').val(item);
						}
						if(i=='DISC_ID'){
							$("#fileFormDiscId").val(item);
						}
						if(i=='MODIFY_TIME'){
							//var date = new Date(item);
							$("#fileFormsbTime").val(item.substring(0,10));
						}
						if(i==titleValues[0]){
							if($.trim(item)==''){
								$("#downFileAttach").hide();
								$("#delFileAttach").hide();
								$("#fileFormsbTime").val("未生成");
								$("#attachNo").val('目前未上传文件');
							}else{
								if(isEditable=='1'){
									$("#delFileAttach").show();
									$("#downFileAttach").show();
									$("#attachNo").val(item);
								}else{
									$("#downFileAttach").show();
									$("#attachNo").val(item);
									$("#operFileForm").hide();
								}
							}
							
						}
					});
				}else{
					unitId = $("#unit_Id").val();
					discId = $("#disc_Id").val();
					$("#fileFormsbTime").val("未生成");
					$('#fileFormUnitId').val(unitId);
					$("#fileFormDiscId").val(discId);
					$("#downFileAttach").hide();
					$("#delFileAttach").hide();
					$("#attachNo").val('目前未上传文件');
				}
				//controlBts();
			},
		});
	}
</script>