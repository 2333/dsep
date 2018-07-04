<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="table">
	<table id="jqGrid_collect_tb"></table>
	<div id="pager_collect_tb"></div>
</div>
<script type="text/javascript">
	function initJqTable(data,dataUrl)
	{
		
		$("#jqGrid_collect_tb").jqGrid(
				{
					url : dataUrl,
					datatype : 'json',
					mtype : 'POST',
					colModel : data.colConfigs,
					height : "100%",
					autowidth : true,
					shrinkToFit : false,
					pager : '#pager_collect_tb',
					pgbuttons : true,
					rowNum : 10,
					rowList : [ 10, 20, 30 ],
					viewrecords : true,
					rownumbers:rowNumber,
					multiselect: isSelect,
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
					//	$("#jqGrid_collect_tb").setGridWidth($("#content").width());
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
						bindFunc(isEditable);
						if(isEditable=='1'){
							addJQEditBt();
						}else{
							jQuery("#jqGrid_collect_tb").setGridParam().hideCol("oper");
							addFileOperCol('0');
						}
						if(isAllData){
							records=$("#jqGrid_collect_tb").jqGrid('getGridParam','records');//当前总记录数
							batchRecords = records;
							
						}else{
							isAllData=true;
						}
						sortorder=$("#jqGrid_collect_tb").jqGrid('getGridParam','sortorder');//排序方式
						sortname=$("#jqGrid_collect_tb").jqGrid('getGridParam','sortname');//排序字段	
					},
					onPaging:function(){
					}
				}).navGrid('#pager_collect_tb', {
			edit : false,
			add : false,
			del : false,search:false
		});
	}
</script>