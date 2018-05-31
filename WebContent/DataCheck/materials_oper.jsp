<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="mate_oper" class="inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">学校编号：</span>
			</td>
			<td>
				<input type="text" id="unit_id" value=""/>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<span class="TextFont">${textConfiguration.discNumber}：</span>
			</td>
			<td>
				<input type="text" id="disc_id" value=""/>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<a id="search_materials" class="button" href="#"><span class="icon icon-search"></span>查询</a>
			</td>
		</tr>
	</table>
	<table class="right">
		<tr>
			<td>
				<a id="material_export" class="button" href="#"><span class="icon icon-export"></span>导出</a>
			</td>
			<td>
				<a id="multi_download" class="button" href="#"><span class="icon icon-download"></span>打包下载</a>
			</td>
		</tr>
	</table>
</div>
<div id="dialog_confirm">
</div>

<script type="text/javascript">
var unitId='';
var discId='';

$("#material_export").click(function(){
	var url =contextPath+'/check/certi_materials/export_excel?'+'entityId='+entityId
			+'&unitId='+unitId+'&discId='+discId+'&sortOrder='+sortOrder
			+'&sortName='+sortName;
	console.log(url);
	export_excel(url);
});
$("#multi_download").click(function(){
	var attachIds=[];
	var gridData = $("#mateJqGrid_tb").jqGrid("getRowData");
	$.each(gridData,function(i,item){
		console.log(item.ATTACH_ID);
		attachIds.push(item.ATTACH_ID);
	});
 	url = contextPath+'/check/certi_materials/downLoadMultiAttachCertiMate?attachIds='+attachIds;
 	$.post(url,function(data){
		downloadProveMaterial(data);
}, 'json'); 
});
function export_excel(url){
	jQuery.ajax({
		url:url,
		contentType:"application/x-www-form-urlencoded; charset=utf-8",
		type:"post",
		async: false,
		dataType:"json",
		beforeSend:function(){
			if($("#outputModal").length == 0){
				console.log("create div");
				$("html").append('<div id="outputModal" style="display:none;"></div>');
			}
			$("#outputModal").dialog({
				title:"正在导出文件，请稍等",
				height:'10',
				width:'200',
				position:'center',
				draggable:false,
				modal:true
			}).show();
			console.log("dialog show");
		},
		success:function(data){
			if(data){
				console.log(data);
				var filePath = data.filepath;
				var fileName = data.filename;
				//base64加密
				var baseHelper = new Base64();  
				filePath = baseHelper.encode(filePath);
				var link = contextPath + '/download.jsp?'+"filepath="+filePath+"&"+"filename="+fileName;		
				window.open(link,"_self");
				console.log("dialog close");
				$("#outputModal").dialog("close");
			}
		},
		complete:function(){
			
		}
	});
}
function downLoadAttachCertiMate(){
	console.log("attachId="+attachId);
	var url = contextPath+'/check/certi_materials/downLoadAttachCertiMate?'+'attachId='+attachId;
	$.post(url,function(data){
			downloadProveMaterial(data);
	}, 'json');
}
function downLoadCertiMaterials(id,type){
	var eleId='';
	console.log("type="+type);
	if(type=='0'){
		eleId='mateJqGrid_tb';	
	}
	if(type=='1'){
		eleId='mateJqGrid_fm';
	}
	if(type=='2'){
		eleId ='mateJqGrid_initJq';
	}
	var rowData = $("#"+eleId).jqGrid("getRowData",id);
	attachId = rowData['ATTACH_ID'];
	if($.trim(attachId)!=''){
		downLoadAttachCertiMate();
	}else{
		alert_dialog('该条数据没有相关附件！');
	}
}
</script>