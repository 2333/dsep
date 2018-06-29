<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script src="${ContextPath}/js/jquery.form.min.js"></script>
<script src="${ContextPath}/js/CollectMeta/Oper/collect_t_oper.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Controllers/date_controller.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Controllers/yearMonth_controller.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Controllers/percent_controller.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Controllers/onlyRead_controller.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Controllers/linkSelect_controller.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/checkDataLength.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Rule/logicchecknumberrules.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/initRules.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Controllers/uploadFile_controller.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Rule/logicchecklogisticrules.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/logiccheckdaterules.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Rule/logiccheckstringrules.js"></script>
<script
	src="${ContextPath}/js/CollectMeta/Rule/autoComplete.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/checkIfExistAttach.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/jscheckfuncsfactory.js"></script>
<script src="${ContextPath}/js/CollectMeta/Rule/checkentitycount.js"></script>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script type="text/javascript">
var jqGridConfig;//存贮jqgrid的配置信息
var lastsel;//最后选择行号
var titleValues;//表的可编辑列名
var tableId;//实体表的Id
var tableName;//实体中文名
var titleNames=new Array();//实体header名
var controllerDic;//数据类型的字段
var linkSelectDic;//关联下拉框字段数据格式Map<String(主),Map<string(从),Map<String(主选项),List<String>(从选项)>>
var primaryKey;//条目的主键
var attrId;//主属性Id
var seqNo;//序号
var records;//总记录数
var sortorder;//排序方式
var sortname;//排序字段
var jsCheckfunParamsDic;//前台js验证函数参数及其值的字典
var jsCheckFunNameDic;//
var contextPath="${ContextPath}";//绝对路径
var maxNum;//记录的最大条数
var viewType;//页面显示类型
var version=-1;
var isSelect = true;//jqgrid是否为多选
var dataUrl='';
var batchUrl='${ContextPath}/TCollect/toTCollect/JqOper/batchTSubmit/';
var sortUrl= '${ContextPath}/TCollect/toTCollect/JqOper/reorderT';
var percentUrl = '${ContextPath}/TCollect/toTCollect/percentdialog';
var isAllData = true;//是所有的数据
var rowNumber=true;//显示行数
var attachId;//附件号(每一行附件临时使用)
var attachElement='';//附件元素
var attachOperElem='';//附件操作元素
var fileValues= new Array();//上传文件的列
var currentSaveRowId='';//正在保存的行id
var currentJqGridId='';//正在保存的jqgirdID
var searchRule='';
var templateId='';
var entityRule; // 实体检查规则
/**
 * 构造一个上传文件的URL
 */
function createUploadUrl(){
	uploadUrl = '${ContextPath}/TCollect/toTCollect/JqOper/uploadFile?jsessionid=' + $("#sessionid").val();
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
				$("#excel_import,#excel_output").show();
			}else{
				$("#excel_output").show();
			}
			break;
		case 'JQFORM':
			if(isEditable=='1'){
				$("#excel_import,#excel_output").show();
			}else{
				$("#excel_output").show();
			}
			break;
		case 'INITJQGRID':
			if(isEditable=='1'){
				$("#excel_import,#excel_output").show();
			}else{
				$("#excel_output").show();
			}
			break;
		case 'JQGRID':
			if(isEditable=='1'){
				$("#collect_batch_add,#collect_batch_del,#collect_Mul_Search,"
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
//初始化批量添加的连接
function initBatchUrl(){
	
}
//初始化获取数据的连接
function initDataUrl(userType){
	dataUrl = '${ContextPath}/TCollect/toTCollect/CollectData/'
		+'collectionTData/'+tableId;
	switch(userType){
		case '4' : 
			dataUrl+='?userId=${user.id}';
			break;
		case '3' :
			var unitId=$("#unit_Id").val();
			var discId=$("#disc_Id").val();
			dataUrl += '?unitId='+unitId+'&discId='+discId;
			break;
		case '2':
			var unitId=$("#unit_Id").val();
			dataUrl += '?unitId='+unitId;
			var discId=$("#disc_Id").val();
			if(discId!='')
				dataUrl += '&discId='+discId;
			break;
		default:
			break;
 	}
}
//初始化显示的列
function initCols(data,userType){
	if(userType=='4'){
		$.each(data.colConfigs,function(i,item){
			if(item.name=='CGSSR_ID'){
				item.editable=false;
				item.hidden=true;
			}
			if(item.name=='CGSSR_LOGINID'){
				item.editable=false;
				item.hidden=true;
			}
			if(item.name=='CGSSR_NAME'){
				item.editable=false;
				item.hidden=true;
			}
		});
	}
	if(userType=='3'||userType=='2'){
		$.each(data.colConfigs,function(i,item){
			
			if(item.name=='CGSSR_LOGINID'){
				data.editableColIds.push('CGSSR_LOGINID');
				item.editable=true;
				item.hidden=false;
			}
			if(item.name=='CGSSR_NAME'){
				data.editableColIds.push('CGSSR_NAME');
				item.editable=true;
				item.hidden=false;
			}
		});
	}
}
function downLoadImportTemplate(){
	var url = "${ContextPath}/TCollect/toTCollect/JqOper/downLoadTemplate/"+templateId;
	$.post(url,function(data){
			console.log('path: '+data);
			downloadProveMaterial(data);
	}, 'json');
}
//绑定事件
function bindFunc(isEditable)
{
	$("#collect_batch_add,#collect_sort,#excel_output,#excel_import,#excel_baseSearch_output").unbind('click');
	operBarShowControl(viewType,isEditable);
	//点击导出按钮后，响应的函数
	$("#excel_output").click(function(){
		//函数在collect_t_oper.js文件中
		excel_output(tableId, sortorder, sortname,'');
	});
	$("#excel_baseSearch_output").click(function(){
		excel_output(tableId, sortorder, sortname,searchRule);
	});
	if(isEditable=='1')
	{
		$(".collect_oper_bts").show();
		$("#collect_batch_add").click(function(){
			batch_Add_Dialog(jqGridConfig);
		});
		$("#collect_sort").click(function(){
			sort_Dialog(jqGridConfig);
		});
		//点击导入 按钮后，显示包含form的对话框
		$("#excel_import").click(function(){
			var url="${ContextPath}/TCollect/toTCollect/import/excelTemplate";
			$.post(url,
				{tableId:tableId},
				function(data){
					console.log("in import"+data);
					$('#uploadFile a').empty().append("下载模板--> " + data.fileName);
					$('#uploadFile a').attr('id','downImpTemp');
					$('#uploadFile a').attr('href','#');
					$('#downImpTemp').unbind('click').click(function(){
						downLoadImportTemplate();
					});
					//$('#uploadFile a').attr('href','../download.jsp?'+"filepath="+data.filePath+"&"+"filename="+data.fileName);
					$('#uploadFile').dialog({
						title:"导入",
						height:'250',
						width:'400',
						position:'center',
						hide:'fade',
						show:'fade',
						draggable:true,
						autoOpen:true,
						//modal behavior; other items on the page will be disabled
						modal:true
					}).show();
				},
				'json'
			);
		});	
	    $("#uploadFileForm").validate({ // initialize the plugin
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
		    url: "${ContextPath}/TCollect/toTCollect/import/excel", 
		    target: '#import_excel_fm',   // target element(s) to be updated with server response
	        beforeSubmit: function () {
	            return $("#uploadFileForm").valid();
	        }, 
		    success: function(data) {
				//$("#uploadFile").dialog("close");
				$("#uploadFile").dialog("close");
				initRules(data.viewconfig);
				data.viewconfig=addEditCol(data.viewconfig);//添加编辑列
				controllerDic= data.viewconfig.idsOfControlDic;
				if($.inArray("ATTACH_ID",data.viewconfig.editableColIds)!=-1){
					//alert('file');
					data.viewconfig = addFileCol(data.viewconfig);
					controllerDic.FILE.push("fileOper");
				}
				import_Data_Dialog(data.viewconfig,data.jqgridvm);
			},  // post-submit callback 
			error:function(data){
				$("#uploadFile").dialog("close");
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
		//$(".collect_oper_bts").hide();
		$("#collect_batch_add,#collect_sort,#excel_import").click(function(){
			alert_dialog('您无权进行此项操作！');
		});
	}
	//$("#excel_import,#excel_baseSearch_output").show();//导出按钮显示
}
function import_Data_Dialog(jqGridConfig,data)
{
	//函数实现在collect_import_excel.jsp页面
	import_Dialog(jqGridConfig,data);	
}
//批量添加
function batch_Add_Dialog(jqGridConfig)
{
	console.log(jqGridConfig);
	batch_Dialog(jqGridConfig);	//此函数在collect_batch_add_view.jsp页面
}
function renderTJqGrid(data)
{  
	hiddenAllBts();
	var userType="${user.userType}";
	initCols(data,userType);
	viewType= data.type;
	templateId = data.templateId;
	if(viewType=='CKFORM'){
		tableId= data.id;
		tableName=data.name;
		$("#ckeditor_Form").show();
		$("#collect_comment").hide();
		$("#gview_jqGrid_collect_fm").hide();
		$("#gview_jqGrid_collect_tb").hide();
		$("#pager_collect_tb").hide();
		initCKForm(data);
	}else{
		entityRule = data.entityRule;
		renderCollectComment("collect_comment",data.memo);
		linkSelectDic = data.linkSelectDicValues;
		console.log(linkSelectDic);
		controllerDic= data.idsOfControlDic;
		jsCheckFunNameDic = data.jsCheckFunNameDic;
		jsCheckfunParamsDic = data.jsCheckParamsAndValueDic;
		initRules(data);
		jqGridConfig=data;
		data=addEditCol(data);
		$("#jqGrid_collect_tb").jqGrid('GridUnload');
		tableId= data.id;
		tableName=data.name;
		titleValues = data.editableColIds;
		if($.inArray("ATTACH_ID",titleValues)!=-1){
			data = addFileCol(data);
			controllerDic.FILE.push("fileOper");
		}
		fileValues = controllerDic.FILE;//文件的列名
		maxNum=data.maxNum;
		initDataUrl("${user.userType}");
		//dataUrl = '${ContextPath}/TCollect/toTCollect/CollectData/collectionTData/'+tableId;
		if(viewType=='JQFORM')
		{	//显示采集表单
			$("#gview_jqGrid_collect_fm").show();
			$("#collect_comment").show();
			$("#gview_jqGrid_collect_tb").hide();
			$("#ckeditor_Form").hide();
			initJqForm(data);
		}else if(viewType='JQGRID'){
			console.log(data);
			$("#gview_jqGrid_collect_tb").show();
			$("#collect_comment").show();
			$("#pager_collect_tb").show();
			$("#gview_jqGrid_collect_fm").hide();	
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
/**
 * 对于没有编辑权限的采集项进行下载证明材料的操作
 */
function addFileOperLink(id,element,type){
	//alert('不可编辑！')
	var downFile_link="<a class='' href='#' onclick='onlyDownLoadAttachFile("+id+","+type+")'>下载附件</a>";
	jQuery("#"+element).jqGrid('setRowData',id,{fileOper:downFile_link});
}
function addFileOperCol(type){
	var eleId='';
	if(type=='0'){
		eleId='jqGrid_collect_tb';	
	}
	if(type=='1'){
		eleId='jqGrid_collect_fm';
	}
	if(type=='2'){
		eleId ='jqGrid_collect_initJq';
	}
	var ids = jQuery("#"+eleId).jqGrid('getDataIDs');
	for(var i=0;i < ids.length;i++){
		addFileOperLink(ids[i],eleId,type);
	}
}
function dealFileColumn(id,element,type){
	/* var rowData = $("#"+element).jqGrid("getRowData",id);
	var attach = rowData['ATTACH_ID'];
	if(attach==''){
		jQuery("#"+element).jqGrid('setRowData',id,{ATTACH_ID:'请上传附件'});
	} */
	if($.inArray("ATTACH_ID",titleValues)!=-1){
		if(isEditable=='1'){
			//可编辑
			var rowData = $("#"+element).jqGrid("getRowData",id);
			var attach = rowData['ATTACH_ID'];
			if(attach==''){
				//jQuery("#"+element).jqGrid('setRowData',id,{ATTACH_ID:'请上传附件'});
				jQuery("#"+element).jqGrid('setRowData',id,{fileOper:'请上传附件'});
			}else{
				jQuery("#"+element).jqGrid('setRowData',id,{fileOper:'查看附件'});
			}
		}else{
			//不可编辑
			alert('不可编辑！')
			addFileOperLink(id,element,type);
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
		var del_link="<a class='' href='#' onclick='delCollectItem("+ids[i]+","+'1'+")'>删除</a>";
		jQuery("#jqGrid_collect_fm").jqGrid('setRowData',ids[i],{oper :edit_link+' | '+del_link});
		dealFileColumn(ids[i],'jqGrid_collect_fm');
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
		<!-- <div class="table">
  		<table id="jqGrid_collect_tb"></table>
		<div id="pager_collect_tb"></div>
	</div> -->
		<jsp:include page="/CollectMeta/collect_jqgrid.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_jqform.jsp"></jsp:include>
		<div id="collect_comment" class="comment"></div>
	</div>
	<div hidden="true">
		<jsp:include page="/CollectMeta/collect_batch_add_view.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_import_excel.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_downLoad.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/collect_uploadFile.jsp"></jsp:include>
		<%-- <jsp:include page="/CollectMeta/collect_downLoad.jsp"></jsp:include> --%>
		<jsp:include page="/CollectMeta/collect_sort.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/yyMMeditor.jsp"></jsp:include>
		<jsp:include page="/CollectMeta/TAchieve2Disc.jsp"></jsp:include>
		<jsp:include page="/CollectTMeta/discMainInfo.jsp"></jsp:include>
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