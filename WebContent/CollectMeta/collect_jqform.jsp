<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="table">
	<table id="jqGrid_collect_fm"></table>
</div>
<script type="text/javascript">
function initDefaultValue(data)
{
	var defaultData='{';
	$.each(data.colConfigs,function(i,item){
		if(i>0){
			if(i==data.colConfigs.length-1)
				defaultData+='\"'+item.name+'\":\"'+'0'+'\"';
			else
				defaultData+='\"'+item.name+'\":\"'+'0'+'\",';
		}
	});
	defaultData+='}';
	return JSON.parse(defaultData);
}

function initOneRow(data,records)
{
	console.log(records);
	if(records==0)
	{
		var defaultValue=initDefaultValue(data);
		$("#jqGrid_collect_fm").jqGrid('addRow',{  
    	      rowID :1,   
   	          position :"last",  
   	          useDefValues : true,  
   	          useFormatter : true,  
   	          initdata :defaultValue,
   	       }); 			
	}
}
	function initJqForm(data,dataUrl)
	{
		$("#jqGrid_collect_fm").jqGrid(
				{
					/* url :'${ContextPath}/Collect/toCollect/CollectData/collectionData/'
							+ tableId
							+ '/'
							+ unitId
							+ '/'
							+ discId, */
						url : dataUrl,
					/* editurl :'${ContextPath}/Collect/toCollect/JqOper/collectionEdit/'
							+ tableId
							+'/'
							+ titleValues
							+ '/'
							+ unitId
							+ '/'
							+ discId, */
					datatype : 'json',
					mtype : 'POST',
					colModel : data.colConfigs,
					height : "100%",
					autowidth : true,
					shrinkToFit : false,
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
						$("#jqGrid_collect_fm").setGridWidth(
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
						bindFunc(isEditable);
						if(isEditable=='1'){
							/* jQuery("#jqGrid_collect_fm").setGridParam().showCol("oper");
							var ids = jQuery("#jqGrid_collect_fm").jqGrid('getDataIDs');
							for(var i=0;i < ids.length;i++){
								var edit_link = "<a class='' href='#' onclick='editCollectItem("+ids[i]+","+'1'+")'>编辑</a>";
								var del_link="<a class='' href='#' onclick='delCollectItem("+ids[i]+","+'1'+")'>删除</a>";
								jQuery("#jqGrid_collect_fm").jqGrid('setRowData',ids[i],{oper :edit_link+' | '+del_link});
							} */
							addJFEditBt();
						}else{
							jQuery("#jqGrid_collect_fm").setGridParam().hideCol("oper");
						}
						records=$("#jqGrid_collect_fm").jqGrid('getGridParam','records');//当前总记录数
						sortorder=$("#jqGrid_collect_fm").jqGrid('getGridParam','sortorder');//排序方式
						sortname=$("#jqGrid_collect_fm").jqGrid('getGridParam','sortname');//排序字段	
						initOneRow(data,records);
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
</script>