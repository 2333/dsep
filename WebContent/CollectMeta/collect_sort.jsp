<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="collect_sort_dv" class="table">
	<form id="collect_sort_fm">
		<div>
			<table>
				<tr>
					<td><a id="move_up" class="button" href="#"> <span
							class="icon icon-up"></span>上移
					</a> <a id="move_down" class="button" href="#"> <span
							class="icon icon-down"></span>下移
					</a></td>
				</tr>
			</table>
		</div>
		<table id="collect_sort_tb"></table>
	</form>
</div>
<script type="text/javascript">
/*重新排序提交至服务器*/
function reorder()
{
	var ids = jQuery("#collect_sort_tb").jqGrid('getDataIDs');
	var dataString='{\"tableId\":[\"'+tableId+'\"],\"rows\":[';
	var rowData = $("#collect_sort_tb").jqGrid("getRowData");
	for(var i=0;i<ids.length;i++)
	{
		if(i==ids.length-1)
    		dataString+='{\"SEQ_NO\":\"'+rowData[i]['SEQ_NO']+'\",\"ID\":\"'+rowData[i]['ID']+'\"}';
    	else
    		dataString+='{\"SEQ_NO\":\"'+rowData[i]['SEQ_NO']+'\",\"ID\":\"'+rowData[i]['ID']+'\"},';
	}
	dataString+=']}';

	//var myurl='${ContextPath}/Collect/toCollect/JqOper/reorder';
	$.post(sortUrl,{params:dataString},
			function(result){
				if(result=='success'){
					$("#collect_sort_dv").dialog("close");
 	            	$("#jqGrid_collect_tb").jqGrid('setGridParam').trigger("reloadGrid");
 	            	alert_dialog('排序成功！');
				}else
					{
						alert_dialog('操作失败,请重新提交！');
					}
				
	}); 
}
/*排序中的jqgrid*/
function initSortJqgrid(data)
{
	//var collegeId='10006';
	//var disciplineId='0835';
	$("#collect_sort_tb").jqGrid('GridUnload');
	$("#collect_sort_tb").jqGrid({
		/* url :'${ContextPath}/Collect/toCollect/CollectData/collectionData/'
			+ tableId
			+ '/'
			+ titleValues
			+ '/'
			+ unitId
			+ '/'
			+ discId, */
		url : dataUrl,
		datatype : 'json',
		mtype : 'POST',
		colModel:data.colConfigs,
		height:"100%",
		autowidth : true,
		shrinkToFit : false,
		rowNum:'all',
		rowList:[10,20,30],
		viewrecords : true,
		sortname : data.defaultSortCol,
		sortorder : "asc",
		caption : data.name,
		jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
			root : "rows", //包含实际数据的数组  
			page : "pageIndex", //当前页  
			total : "totalPage",//总页数  
			records : "totalCount", //查询出的记录数  
			repeatitems : false,
		},
		loadComplete : function() {
			/* $("#jqGrid_collect_tb").setGridWidth(
					$("#content").width()); */
		},
		prmNames : {
			page : "page",
			rows : "rows",
			sort : "sidx",
			order : "sord"
		},
		gridComplete: function(){
			jQuery("#collect_sort_tb").setGridParam().hideCol("oper");
		},
	}).navGrid('#collect_sort_tb',{edit:false,add:false,del:false});	
}
/*排序的对话框*/
function sort_Dialog(data)
{
		  initSortJqgrid(data);
	 	  $('#collect_sort_dv').dialog({
	  		    title:"排序",
	  		    height:'800',
	  			width:'90%',
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  			show:'fade',
	  		    autoOpen:true,
	  		    close:function(){
	  		    	$("#collect_sort_dv").dialog("close");
	  		    	$("#jqGrid_collect_tb").jqGrid('setGridParam').trigger("reloadGrid");
	  		    },
	  		    buttons:{  
	  		    	"提交":function(){ 
	  		    		reorder();	
	  	             },
	 	  
	 	            "取消":function(){
	 	            	$("#collect_sort_dv").dialog("close");
	 	            	$("#jqGrid_collect_tb").jqGrid('setGridParam').trigger("reloadGrid");
	 	            }
	  		    }}); 
	 
}
function moveUp(rowId,warn)
{
	var rowData = $("#collect_sort_tb").jqGrid('getRowData',rowId);
	
	var preRowId=rowId-1;

	if(preRowId>0){
		rowData['SEQ_NO']=preRowId;
		var preRowData=$("#collect_sort_tb").jqGrid('getRowData',preRowId);
		preRowData['SEQ_NO']=rowId;
		jQuery("#collect_sort_tb").jqGrid('setRowData',rowId,preRowData);
		jQuery("#collect_sort_tb").jqGrid('setRowData',preRowId,rowData);
		jQuery('#collect_sort_tb').jqGrid('setSelection',preRowId);
	}else{
		alert_dialog(warn);
	}	
}
function moveDown(rowId,warn)
{
	var rowData = $("#collect_sort_tb").jqGrid('getRowData',rowId);
	var nextRowId=parseInt(rowId)+1;
	if(nextRowId<=records){
		rowData['SEQ_NO']=nextRowId;
		var nextRowData=$("#collect_sort_tb").jqGrid('getRowData',nextRowId);
		nextRowData['SEQ_NO']=rowId;
		jQuery("#collect_sort_tb").jqGrid('setRowData',rowId,nextRowData);
		jQuery("#collect_sort_tb").jqGrid('setRowData',nextRowId,rowData);
		jQuery('#collect_sort_tb').jqGrid('setSelection',nextRowId);
	}else{
		alert_dialog(warn);
	}	
}
$(document).ready(function(){
	$("#move_up").click(function(){
		var rowId = jQuery("#collect_sort_tb").jqGrid('getGridParam', 'selrow');
		moveUp(rowId,'已经移至开始！');
	});
	$("#move_down").click(function(){
		var rowId = jQuery("#collect_sort_tb").jqGrid('getGridParam', 'selrow');
		moveDown(rowId,'已经移至最后！');
	});
});
</script>