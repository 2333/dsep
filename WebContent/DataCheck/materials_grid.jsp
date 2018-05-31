<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="grid_id" class="table" >
	<table id="mateJqGrid_tb"></table>
	<div id="pager_mate_tb"></div>
</div>
<script>
function initJqTable(data,dataUrl){
	$("#mateJqGrid_tb").jqGrid(
			{
				url : dataUrl,
				datatype : 'json',
				mtype : 'GET',
				colModel : data.colConfigs,
				height : "100%",
				width:$("#content").width(),
				autowidth :false,
				shrinkToFit:true,
				pager : '#pager_mate_tb',
				rowNum : 20,
				rowList : [ 20, 30, 40 ],
				viewrecords : true,
				sortname : data.defaultSortCol,
				sortorder : "asc",
				caption : data.name,
				jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
					root : "rows", //包含实际数据的数组  
					page : "pageIndex", //当前页  
					records : "totalCount", //查询出的记录数  
					total : "totalPage",//总页数  
					repeatitems : false,
				},
				gridComplete: function(){
					addMaterialsDownloadCol('0');
					sortOrder=$("#mateJqGrid_tb").jqGrid('getGridParam','sortorder');//排序方式
					sortName=$("#mateJqGrid_tb").jqGrid('getGridParam','sortname');//排序字段	
				},
			}).navGrid('#pager_mate_tb', {
		edit : false,
		add : false,
		del : false,search:false
	});
}

function addMaterialsDownloadCol(type){
	var eleId='';
	if(type=='0'){
		eleId='mateJqGrid_tb';	
	}
	if(type=='1'){
		eleId='mateJqGrid_fm';
	}
	if(type=='2'){
		eleId ='mateJqGrid_initJq';
	}
	var ids = jQuery("#"+eleId).jqGrid('getDataIDs');
	for(var i=0;i < ids.length;i++){
		addMaterialsOperLink(ids[i],eleId,type);
	}
}

function addMaterialsOperLink(id,element,type){
	var downMaterials_link="<a class='' style='text-decoration:none;' href='#' onclick='downLoadCertiMaterials("+id+","+type+")'>下载附件</a>";
	jQuery("#"+element).jqGrid('setRowData',id,{materialsOper:downMaterials_link});
}
</script>