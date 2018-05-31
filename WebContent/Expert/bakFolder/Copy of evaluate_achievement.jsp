<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="con_header" style="overflow: hidden">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="icon icon-web"></span> 
				<span class="TextFont">学科成果评价</span>
			</td>
		</tr>
	</table>
</div>

<div class="table">
  		<table id="jqGrid_collect_tb"></table>
		<div id="pager_collect_tb"></div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	alert(1);
	$.getJSON("${ContextPath}/evaluation/achievement_initCollectJqgrid/", 
	function initJqTable(data)
	{alert(data);
	alert(data.colConfigs);
		$("#jqGrid_collect_tb").jqGrid(
				{
					
 					url :'${ContextPath}/evaluation/achievement_collectionData/'
 						+ 'E20130101/10006/0835',		
 					//+ tableId + '/' + unitId + '/' + discId,
 					editurl :'${ContextPath}/Collect/toCollect/JqOper/collectionEdit/'
 						+ 'E20130101/10006/0835',		
 					//+ tableId + '/' + titleValues + '/' + unitId + '/' + discId,
					datatype : 'json',
					mtype : 'POST',
					colModel : data.colConfigs,
					height : "100%",
					autowidth : true,
					shrinkToFit : false,
					pager : '#pager_collect_tb',
					pgbuttons : true,
					rowNum : 20,
					rowList : [ 20, 30, 40 ],
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
						$("#jqGrid_collect_tb").setGridWidth(
								$("#content").width());
					},
					prmNames : {
						page : "page",
						rows : "rows",
						sort : "sidx",
						order : "sord"
					},
					onSelectRow:function(rowid){
					    
					},
					gridComplete: function(){
						//bindFunc(isEditable);
// 						if(isEditable=='1'){
// 							var ids = jQuery("#jqGrid_collect_tb").jqGrid('getDataIDs');
// 							for(var i=0;i < ids.length;i++){
// 								var edit_link = "<a class='' href='#' onclick='editCollectItem("+ids[i]+","+'0'+")'>编辑</a>";
// 								var del_link="<a class='' href='#' onclick='delCollectItem("+ids[i]+","+'0'+")'>删除</a>";
// 								jQuery("#jqGrid_collect_tb").jqGrid('setRowData',ids[i],{oper :edit_link+' | '+del_link});
// 							}
// 						}else{
// 							jQuery("#jqGrid_collect_tb").setGridParam().hideCol("oper");
// 						}
// 						records=$("#jqGrid_collect_tb").jqGrid('getGridParam','records');//当前总记录数
// 						sortorder=$("#jqGrid_collect_tb").jqGrid('getGridParam','sortorder');//排序方式
// 						sortname=$("#jqGrid_collect_tb").jqGrid('getGridParam','sortname');//排序字段	
					},
					onPaging:function(){
						//alert("是否翻页，修改的数据没有保存！");
					}
				}).navGrid('#pager_collect_tb', {
			edit : false,
			add : false,
			del : false,search:false
		});
	}
	);
});
	
</script>



