<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="table">
	<table id="jqGrid_origin_collect_tb"></table>
	<div id="pager_origin_collect_tb"></div>
</div>
<script type="text/javascript">
	function initOrigJqTable(data,dataUrl)
	{
		
		$("#jqGrid_origin_collect_tb").jqGrid(
				{
					url : dataUrl,
					datatype : 'json',
					mtype : 'POST',
					colModel : data.colConfigs,
					height : "100%",
					autowidth : true,
					shrinkToFit : false,
					pager : '#pager_origin_collect_tb',
					pgbuttons : true,
					rowNum : 20,
					rowList : [ 20, 30, 40 ],
					viewrecords : true,
					rownumbers:rowNumber,
					multiselect:true,
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
						$("#jqGrid_origin_collect_tb").setGridWidth(
								$("#choose_Collect_item_fm").width());
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
						var ids = jQuery("#jqGrid_origin_collect_tb").jqGrid('getDataIDs');
						console.log(ids);
						for(var i=0;i < ids.length;i++){
							var rowData =  $("#jqGrid_origin_collect_tb").jqGrid("getRowData",ids[i]);
							console.log(rowData);
							console.log(rowData['IS_SELECTED']);
							if(rowData['IS_SELECTED']=='true'){
								$("#jqGrid_origin_collect_tb #"+ids[i]).find('td').css('background-color','red');
							}
						}
						
					},
					onPaging:function(){
						
					}
				}).navGrid('#pager_origin_collect_tb', {
			edit : false,
			add : false,
			del : false,search:false
		});
	}
</script>