<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div>
	<jsp:include page="/CollectMeta/collect_vars.jsp"></jsp:include>
</div>
<script src="${ContextPath}/js/CollectMeta/Oper/public_oper.js"></script>
<script type="text/javascript">

var isEditable=1;
/**
 * 构造一个上传文件的URL
 */
function createUploadUrl(){
	var unitId = $("#unit_Id").val();
	uploadUrl = '${ContextPath}/Collect/toCollect/JqOper/uploadFile/'+unitId+'?jsessionid=' + $("#sessionid").val();
}
/**
 * 改动URL
 */
function initBatchUrl(){
	batchUrl='${ContextPath}/PublicDataManage/viewData/JqOper/batchSubmit/';//批量添加的URL
}
/**
 * 隐藏所有按钮
 */
function hiddenAllBts(){
	$("#collect_batch_add,#collect_batch_del,#collect_sort,#collect_Mul_Search,"
			+"#tAchieveToDisc,"
			+"#excel_import,"
			+"#excel_baseSearch_output,"
			+"#excel_output").hide();
}
//控制页面显示
function operBarShowControl(viewType,isEditable){
	switch(viewType){
		case 'CKFORM':
			if(isEditable=='1'){
				//$("#excel_import,#excel_output").show();
			}else{
				//$("#excel_output").show();
			}
			break;
		case 'JQFORM':
			if(isEditable=='1'){
				$("#excel_output").show();
			}else{
				$("#excel_output").show();
			}
			break;
		case 'INITJQGRID':
			if(isEditable=='1'){
				$("#excel_output").show();
			}else{
				$("#excel_output").show();
			}
			break;
		case 'JQGRID':
			if(isEditable=='1'){
				$("#collect_batch_add,#collect_batch_del,#collect_sort,#collect_Mul_Search,"
				+"#tAchieveToDisc,"
				+"#excel_import,"
				+"#excel_baseSearch_output,"
				+"#excel_output").show();
			}else{
				$("#collect_Mul_Search,"
				+"#excel_baseSearch_output,"
				+"#excel_output").show();
			}
			break;
		default:
			break;
			
	}
}
function downLoadImportTemplate(){
	
	//var url = "${ContextPath}/Collect/toCollect/JqOper/downLoadTemplate/"+templateId;
	$.post("${ContextPath}/PublicDataManage/viewData/JqOper/downLoadTemplate/"+templateId,function(data){
			downloadProveMaterial(data);
	}, 'json');
}
/*绑定click事件*/
function bindFunc(isEditable)
{
	//点击导出按钮后，响应的函数
	$("#excel_output").unbind('click').click(function(){
		//函数在collect_oper.js文件中
		excel_output(unitId,discId,tableId,sortorder,sortname,'');
	});
	$("#excel_baseSearch_output").unbind('click').click(function(){
		excel_output(unitId,discId,tableId,sortorder,sortname,searchRule);
	});
	$("#collect_batch_add,#collect_sort,#excel_import").unbind('click');
	hiddenAllBts();
	operBarShowControl(viewType,isEditable);
	if(isEditable=='1')
	{
		$("#collect_batch_add").click(function(){
			batch_Add_Dialog(jqGridConfig);
		});
		$("#collect_sort").click(function(){
			sort_All_Dialog(jqGridConfig);
		});
/* 		//点击导出按钮后，响应的函数
		$("#excel_output").click(function(){
			//函数在collect_oper.js文件中
			excel_output(unitId,discId,tableId,sortorder,sortname,'');
		});
		$("#excel_baseSearch_output").click(function(){
			excel_output(unitId,discId,tableId,sortorder,sortname,searchRule);
		});*/
		//点击导入 按钮后，显示包含form的对话框
		$("#excel_import").click(function(){
/* 			var url="${ContextPath}/Collect/toCollect/import/excelTemplate";
			$.post(url,
				{tableId:tableId},
				function(data){ */
					$('#uploadFile a').empty().append("下载模板--> " + tableName);
					$('#uploadFile a').attr('id','downImpTemp');
					$('#uploadFile a').attr('href','#');
					$('#downImpTemp').unbind('click').click(function(){
						downLoadImportTemplate();
					});
					//$('#uploadFile a').attr('href','../download.jsp?'+"filepath="+data.filePath+"&"+"filename="+data.fileName);
					$('#uploadFile').dialog({
						title:"导入",
						height:'200',
						width:'450',
						position:'center',
						hide:'fade',
						show:'fade',
						draggable:true,
						autoOpen:true,
						//modal behavior; other items on the page will be disabled
						modal:true
					}).show();
/* 				},
				'json'
			); */
		});	
	    $("#uploadFileForm").validate({ // initialize the plugin
	        // any other options,
	        onkeyup: false,
	        rules: {
	        	  file: {
	                  required: true,
	              }
	        },
	        messages: {
	            file: "请选择文件后再确认提交"
	        }
	    });
		// attach handler to form's submit event 
		var options = { 
		    type: "POST",  
		    contentType: false,
		    processData: false,
		    data:{
		    	tableId:tableId
		    },
		    url: "${ContextPath}/PublicDataManage/viewData/import/excel", 
		    target: '#import_excel_fm',   // target element(s) to be updated with server response
	        beforeSubmit: function () {
	            return $("#uploadFileForm").valid();
	        }, 
		    success: function(data) {
		    	$(':input','#uploadFileForm')
		    	 .not(':button, :submit, :reset, :hidden')
		    	 .val('')
		    	 .removeAttr('checked')
		    	 .removeAttr('selected');
				$("#uploadFile").dialog("close");
				initRules(data.viewconfig);
				data.viewconfig=addEditCol(data.viewconfig);//添加编辑列
				import_Data_Dialog(data.viewconfig,data.jqgridvm);
			},  // post-submit callback 
			error:function(data){
		    	$(':input','#uploadFileForm')
		    	 .not(':button, :submit, :reset, :hidden')
		    	 .val('')
		    	 .removeAttr('checked')
		    	 .removeAttr('selected');
// 				$("#uploadFile").dialog("close");
				alert_dialog(data.responseText);
			},
		    dataType:  'json',      // 'xml', 'script', or 'json' (expected server response type) 
		    restForm:true,
		    clearForm: true        // clear all form fields after successful submit    
		}; 
		$(function(){
			//forbid repeat submit,unbind before bind again
			$('#uploadFileForm').unbind('submit');
			// bind form using 'ajaxForm' 
			$('#uploadFileForm').submit(function() { 
				// inside event callbacks 'this' is the DOM element so we first 
				// wrap it in a jQuery object and then invoke ajaxSubmit 
				$(this).ajaxSubmit(options); 
				// !!! Important !!! 
				// always return false to prevent standard browser submit and page navigation 
				return false; 
			}); 
		});	
	}else{
		$("#collect_batch_add,#collect_sort,#excel_import").click(function(){
			alert_dialog('您无权进行此项操作！');
		});
	}
}
//批量添加
function batch_Add_Dialog(jqGridConfig)
{
	batch_Dialog(jqGridConfig);	//此函数在collect_batch_add_view.jsp页面
}
//排序页面
function sort_All_Dialog(jqGridConfig){
	sort_Dialog(jqGridConfig);//此函数在collect_sort.jsp页面
}
//导入数据
function import_Data_Dialog(jqGridConfig,data)
{
	//函数实现在collect_import_excel.jsp页面
	import_Dialog(jqGridConfig,data);	
}
function renderJqGrid(data)
{  
	hiddenAllBts();
	viewType= data.type;
	console.log(viewType);
	//console.log(data.memo);
	//renderCollectComment("collect_comment",data.memo);//渲染说明
	renderCollectComment("collect_comment",remark);//渲染说明
	templateId = data.templateId;
	if(viewType=='FILEFORM'){
		console.log(viewType);
		tableId= data.id;
		tableName=data.name;
		titleValues = data.editableColIds;//可编辑列
		$("#fileForm").show();
		$("#ckeditor_Form").hide();
		$("#gview_jqGrid_collect_fm").hide();
		$("#gview_jqGrid_collect_tb").hide();
		$("#gview_jqGrid_collect_initJq").hide();
		$("#pager_collect_tb").hide();
		initFileForm();
	}else if(viewType=='CKFORM'){
		tableId= data.id;
		tableName=data.name;
		titleValues = data.editableColIds;//可编辑列
		$("#fileForm").hide();
		$("#ckeditor_Form").show();
		$("#gview_jqGrid_collect_fm").hide();
		$("#gview_jqGrid_collect_tb").hide();
		$("#gview_jqGrid_collect_initJq").hide();
		$("#pager_collect_tb").hide();
		initCKForm(data);
	}else{
		//console.log(viewType);
		entityRule = data.entityRule;
		controllerDic= data.idsOfControlDic;
		jsCheckFunNameDic = data.jsCheckFunNameDic;
		jsCheckfunParamsDic = data.jsCheckParamsAndValueDic;
		initRules(data);
		jqGridConfig=data;
		data=addEditCol(data);
		$("#jqGrid_collect_tb").jqGrid('GridUnload');
		$("#jqGrid_collect_fm").jqGrid('GridUnload');
		$("#jqGrid_collect_initJq").jqGrid('GridUnload');
		$("#jqGrid_collect_initJq_hidden").jqGrid('GridUnload');
		tableId= data.id;
		tableName=data.name;
		attrId=data.attrId;//初始化采集项主键
		titleValues = data.editableColIds;
		if($.inArray("ATTACH_ID",titleValues)!=-1){
			data = addFileCol(data);
			controllerDic.FILE.push("fileOper");
		}
		fileValues = controllerDic.FILE;
		maxNum=data.maxNum;
		dataUrl='${ContextPath}/PublicDataManage/viewData/CollectData/collectionData/'+tableId;
		if(viewType=='JQFORM')
		{	//显示采集表单
			$("#fileForm").hide();
			$("#gview_jqGrid_collect_fm").show();
			$("#collect_comment").show();
			$("#gview_jqGrid_collect_tb").hide();
			$("#gview_jqGrid_collect_initJq").hide();
			$("#ckeditor_Form").hide();
			initJqForm(data,dataUrl);
		}else if(viewType=='INITJQGRID'){
			
			$("#fileForm").hide();
			$("#gview_jqGrid_collect_initJq").show();
			$("#collect_comment").show();
			$("#gview_jqGrid_collect_tb").hide();
			$("#gview_jqGrid_collect_fm").hide();
			$("#ckeditor_Form").hide();
			controlTypeRules = data.controlTypeRules;
			console.log(controlTypeRules);
			initJqRules = data.initColNameAndValues;
			initInitedJq(data,dataUrl);
		}else if(viewType='JQGRID'){
			$("#fileForm").hide();
			$("#gview_jqGrid_collect_tb").show();
			$("#collect_comment").show();
			$("#pager_collect_tb").show();
			$("#gview_jqGrid_collect_fm").hide();
			$("#gview_jqGrid_collect_initJq").hide();
			$("#ckeditor_Form").hide();
			initJqTable(data,dataUrl);
		}
	}
}
/*动态添加编辑列*/
function addEditCol(data)
{
	var editCol={  
		  label : '操作',  
		  name : 'oper',  
		  sortable : false,  
		  width : 80,  
		  fixed : false,  
		  align : "center",
		  search:false,
	};
	data.colConfigs.unshift(editCol);
	return data;

}
/**
 * 增加一文件操作列
 */
function addFileCol(data)
{
	var fileCol={  
		  label : '文件操作',  
		  name : 'fileOper',  
		  sortable : false,  
		  width : 80,  
		  fixed : false,  
		  align : "center",
		  search:false,
		  editable:true,
		  edittype:"button"
	};
	data.colConfigs.push(fileCol);
	return data;

}
function dealFileColumn(id,element){
	/* var rowData = $("#"+element).jqGrid("getRowData",id);
	var attach = rowData['ATTACH_ID'];
	if(attach==''){
		jQuery("#"+element).jqGrid('setRowData',id,{ATTACH_ID:'请上传附件'});
	} */
	if($.inArray("ATTACH_ID",titleValues)!=-1){
		var rowData = $("#"+element).jqGrid("getRowData",id);
		var attach = rowData['ATTACH_ID'];
		if(attach==''){
			//jQuery("#"+element).jqGrid('setRowData',id,{ATTACH_ID:'请上传附件'});
			jQuery("#"+element).jqGrid('setRowData',id,{fileOper:'请上传附件'});
		}else{
			jQuery("#"+element).jqGrid('setRowData',id,{fileOper:'查看附件'});
		}
	}
}
/**
 * 添加操作按钮，不同用途的数据操作按钮由此添加
 */
function addJQEditBt(){
	jQuery("#jqGrid_collect_tb").setGridParam().showCol("oper");
	var ids = jQuery("#jqGrid_collect_tb").jqGrid('getDataIDs');
	for(var i=0;i < ids.length;i++){
		jQuery("#jqGrid_collect_tb").setRowData (ids[i], false, {height: 30} );
		var edit_link = "<a class='' href='#' onclick='editCollectItem("+ids[i]+","+'0'+")'>编辑</a>";
		var del_link="<a class='' href='#' onclick='delCollectItem("+ids[i]+","+'0'+")'>删除</a>";
		jQuery("#jqGrid_collect_tb").jqGrid('setRowData',ids[i],{oper :edit_link+' | '+del_link});
		dealFileColumn(ids[i],'jqGrid_collect_tb');
	}
}
function addJFEditBt(){
	jQuery("#jqGrid_collect_fm").setGridParam().showCol("oper");
	var ids = jQuery("#jqGrid_collect_fm").jqGrid('getDataIDs');
	for(var i=0;i < ids.length;i++){
		var edit_link = "<a class='' href='#' onclick='editCollectItem("+ids[i]+","+'1'+")'>编辑</a>";
		var del_link="<a class='' href='#' onclick='delCollectItem("+ids[i]+","+'1'+")'>清空</a>";
		jQuery("#jqGrid_collect_fm").jqGrid('setRowData',ids[i],{oper :edit_link+' | '+del_link});
		dealFileColumn(ids[i],'jqGrid_collect_fm');
	}
}
function addInitedJqEditBt(){
	jQuery("#jqGrid_collect_initJq").setGridParam().showCol("oper");
	var ids = jQuery("#jqGrid_collect_initJq").jqGrid('getDataIDs');
	for(var i=0;i < ids.length;i++){
		var edit_link = "<a class='' href='#' onclick='editCollectItem("+ids[i]+","+'2'+")'>编辑</a>";
		var del_link="<a class='' href='#' onclick='delCollectItem("+ids[i]+","+'2'+")'>清空</a>";
		jQuery("#jqGrid_collect_initJq").jqGrid('setRowData',ids[i],{oper :edit_link+' | '+del_link});
		dealFileColumn(ids[i],'jqGrid_collect_initJq');
	}
}
function renderCollectComment(elementId,content)
{
	$('#'+elementId).empty();
	var html = $.parseHTML( content );
	$('#'+elementId).append(html);
}

$(document).ready(function(){
	$( "input[type=submit], a.button , button" ).button(); 	
	$("#gview_jqGrid_collect_tb").hide();//id为jqgrid根据<table Id="">自动生成
	$("#gview_jqGrid_collect_fm").hide();
});

</script>
<div id="jq_collect_parent" class="selectbar right_block" hidden="true">
	<jsp:include page="/CollectMeta/collect_oper_banner.jsp"></jsp:include>
	<div id="jq_collect_table">
		<jsp:include page="/CollectMeta/collect_jqgrid.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_jqform.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_ckeditor.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_initedJq.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_fileForm.jsp"></jsp:include>
		<div id="collect_comment" class="comment"></div>
	</div>
	<div hidden="true">
		<jsp:include page="/CollectMeta/collect_mulSelect.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_batch_add_view.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_uploadFile.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_downLoad.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_import_excel.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_sort.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/yyMMeditor.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/monthRangeSelector.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/TAchieve2Disc.jsp"></jsp:include>
	</div>
	<div id="percent_dialog_div" class="percentdialog_div"
		style="display: none"></div>
	<jsp:include page="/export_download_div.jsp"></jsp:include>
	<div id="uploadFile" style="display: none">
		<form id="uploadFileForm" method="post" action="javascript:;"
			enctype="multipart/form-data">
			<div style="margin-top: 10px;">
				<input type="text" size="40" name="upfile" id="upfile"
					style="padding: 5px; border: 1px solid #79b7e7"> <input
					type="button" value="浏览..." onclick="path.click()"
					class="button ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only ui-state-hover">
			</div>
			<div style="margin-top: 20px;">
				<input type="file" name="file" id="path"
					style="width: 120px; filter: alpha(opacity = 0); -moz-opacity: .0; opacity: 0.0; cursor: pointer;"
					onchange="upfile.value=this.value.split('\\')[this.value.split('\\').length-1];">
				<input type="submit"
					style="position: absolute; top: 60px; display: block;" value="确认导入" />
			</div>
		</form>
		<div style="margin-top: 20px;">
			<span class="icon icon-download"></span><a href=""
				style="font-weight: bold;"></a>
		</div>
	</div>
	<div hidden="true">
		<div id="dialog-confirm" title="警告"></div>
	</div>
</div>