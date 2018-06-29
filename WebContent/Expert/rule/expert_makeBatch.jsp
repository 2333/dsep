<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
	<div class="con_header">
	<h3><span class="icon icon-web"></span>遴选批次管理</h3>
    </div>
    <div class="selectbar">
		<table class="layout_table">
			<tr>
		        <!-- <td><a id="editBatch" class="button" href="#" style="float:right;margin-right: .3em;">
				<span class="icon icon-edit"></span>编辑</a></td> -->
				<td><a id="addBatch2" class="button" href="#" style="float:right;margin-right: .3em;">
				<span class="icon icon-add"></span>添加</a></td>
		        <td><a id="delBatch" class="button" href="#" style="float:right;margin-right: .3em;">
				<span class="icon icon-del"></span>删除</a></td>
		    </tr>
		</table>
	</div>
	
	<div class="cur">
		<table id="batch_list"></table>
		<div id="pager"></div>
    </div>
    <div id="dialog-confirm" title="警告"></div>
	
<script type="text/javascript">
$(document).ready(function(){
	
	$("input[type=submit], a.button , button").button();
	
	$("#batch_list").jqGrid({
		url: "${ContextPath}/rule/makeRuleGetBatchData",
	    datatype : "json",
		mtype: 'GET',
		colNames : ['批次ID','批次序号','批次名称','开始时间','结束时间'],
		colModel : [ 
           	{name : 'id',			index : 'id',			hidden : true}, 
            {name : 'batchNum',		index : 'batchNum',		hidden : true},
            {name : 'batchChName',	index : 'batchChName',	width : 100,	align : "center"},
            {name : 'beginDateStr', index : 'beginDate', 	width : 100,	align : "center"},
            {name : 'endDateStr', 	index : 'endDate', 		width : 100,	align : "center"},
		],
		height : '100%',
		autowidth: true,
		rowNum : 5,
		rowList : [ 5, 10, 15 ],
		viewrecords : true,
		sortorder : "desc",
		pager: '#pager',
							
		caption : "专家遴选批次",
		jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
	        root: "rows",  //包含实际数据的数组  
	        page: "pageIndex",  //当前页  
	        total: "totalPage",//总页数  
	        records:"totalCount", //查询出的记录数  
	        repeatitems : false,
	    },
	    gridComplete: function(){
 			var ids = $("#batch_list").jqGrid('getDataIDs');
 			for (var i = 0; i < ids.length; i++) {
 				var rowId = ids[i];
 				//$("#batch_list").jqGrid('setCell',rowId, 'beginTime', '2014年9月1日', '');
 				//$("#batch_list").jqGrid('setCell',rowId, 'endTime', '2014年12月31日', '');
 			}	
 		},
	 }).navGrid('#pager',{edit:false,add:false,del:false});
	
});

/* $("#editBatch").click(function(){
	var rowId = $("#batch_list").jqGrid('getGridParam','selrow');
	console.log(rowId);
	if(rowId!=null){
		openEditBatchDialog("${ContextPath}/rule/makeRuleEditBatch?id="+rowId);
	}else{
		alert_dialog("请选择一行进行编辑");
	}
}); */

$("#addBatch2").click(function(){
	console.log("click");
	addBatchDialog2("${ContextPath}/rule/makeRuleAddBatchDialog");
});

$("#delBatch").click(function(){
	var rowId = $("#batch_list").jqGrid('getGridParam','selrow');
	console.log(rowId);
	if(rowId == null) {
		alert_dialog("请选择一个批次删除");
	} else {
		//清空警告框内文本并添加新的警告文本
	    $("#dialog-confirm").empty()
	    	.append("<p>删除批次会级联删除该批次下制定的专家任务、遴选规则以及遴选出的专家，确定删除该批次？</p>");
	    
		$("#dialog-confirm").dialog({
	        height:150,
	        buttons: {
	            "确定": function() {
	            	$(this).dialog("close");
	            	$.post("${ContextPath}/rule/makeRuleDelBatch?id="+rowId,function(data){
	        			$("#batch_list").setGridParam({url:'${ContextPath}/rule/makeRuleGetBatchData'}).trigger('reloadGrid');
	        		},'json');
				},
	        	"取消": function() {
	           		 $(this).dialog("close");
	          	}
	        }
	  	});
	}
});

/* function openEditBatchDialog(url){
	$.post(url,function(data){
		$('#dialog').empty();
		$('#dialog').append(data);
		$('#dialog').dialog({
			 title : "编辑批次",
		     autoheight : true,
			 width : '500',
			 position : 'center',
			 modal : true,
			 draggable : true,
			 hide : 'fade',
			 show : 'fade',
			 autoOpen : true,
		});
	});
}; */

function addBatchDialog2(url){
	 $.post(url,function(data){
		 $("#dialog2").empty();
		 $("#dialog2").append(data);
		 $("#dialog2").dialog({
			 title : "添加批次",
		     autoheight : true,
			 width : '700',
			 position : 'center',
			 modal : true,
			 draggable : true,
			 hide : 'fade',
			 show : 'fade',
			 autoOpen : true,
			 buttons:{
				 "关闭" : function() {
						$("#dialog2").dialog("close");
						$('#dialog2').empty();
					},
			 },
		 });
	 },'html');
}
</script>