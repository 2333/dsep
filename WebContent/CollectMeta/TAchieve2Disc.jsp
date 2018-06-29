<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="choose_Collect_item_dv" class="table">
	<form id="choose_Collect_item_fm">
		<div class="selectbar inner_table_holder">
			<table class="layout_table left">
				<tr>
					<td><span class="TextFont">选择教师成果项</span></td>
					<td><select id="collect_entity_id">
					</select></td>
				</tr>
				<tr>
					<td><a id="collect_origin_Mul_Search" class="button" href="#">
							<span class="icon icon-search"></span>查询
					</a></td>
					<td><a id="import2Disc" class="button" href="#"> <span
							class="icon icon-import"></span>导入学科
					</a></td>
				</tr>
			</table>
		</div>
		<jsp:include page="/CollectMeta/collect_origin_jqgrid.jsp"></jsp:include>
	</form>
</div>

<script type="text/javascript">
function import2Disc(oriEntityId,tarEntityId,unitId,discId,importType){
	var pkValues = new Array();
	var ids=$('#jqGrid_origin_collect_tb').jqGrid('getGridParam','selarrrow');
	for(var i = 0;i<ids.length;i++){
		var rowData = $("#jqGrid_origin_collect_tb").jqGrid("getRowData",ids[i]);
		pkValues.push(rowData['ID']);
	}
	if(pkValues.length>0){
		$.commonRequest({
			url:'${ContextPath}/Collect/toCollect/JqOper/import2Disc/'
				+oriEntityId
				+'/'
				+tarEntityId
				+'/'
				+unitId
				+'/'
				+discId
				+'/'
				+pkValues
				+'/'
				+importType,
			dataType:'text',
			success:function(result){
				if(result=='success'){
					alert_dialog('导入成功！');
					//$("#jqGrid_origin_collect_tb").jqGrid('setGridParam').trigger("reloadGrid");
					$("#choose_Collect_item_dv").dialog("close");
					$("#jqGrid_collect_tb").jqGrid('setGridParam').trigger("reloadGrid");
				}else{
					alert_dialog(result);
				}
			}
			});
	}else{
		alert_dialog('请选择教师成果！');
	}
	
}
//多功能查询
function multiSearch(){
	$("#jqGrid_origin_collect_tb").jqGrid('searchGrid', {
		modal:false,
		caption: "查找",  
        Find: "查询",  
        closeAfterSearch: true,  
        multipleSearch: true,  
        multipleGroup: true,
       // showQuery: true,
        groupOps: [{ op: "AND", text: "全部" },{ op:"OR",text: "任何"}]
	});
	return false;
}
/*动态添加编辑列*/
function addIsSelectedCol(data)
{
	var isSelectedCol={  
		  label : '是否导入学科采集项',  
		  name : 'IS_SELECTED',  
		  sortable : false,  
		  width : 80,  
		  fixed : false,  
		  align : "center",
		  search:false,
		  hidden:true,
		  editable:false
	};
	data.colConfigs.unshift(isSelectedCol);
	return data;

}
//显示用户名和用户姓名
function initColumns(data){
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
//初始化采集表的配置信息
function initCollectConfig(entityId,tarEntityId,unitId,discId){
	$.commonRequest({
		url:'${ContextPath}/Collect/toCollect/JqConfig/initCollectJqgrid/'+entityId,
		dataType:'json',
		success:function(data){
				data=addIsSelectedCol(data);
				initColumns(data);
				var dataUrl = '${ContextPath}/TCollect/toTCollect/CollectData/'
				+'viewCollectTData/'+entityId+'/'+tarEntityId+'?unitId='+unitId+'&discId='+discId;
				initOrigJqTable(data,dataUrl);//
			}
		});
}
//获取源实体
function getOriginEntity(entityId,discId){
	$.commonRequest({
		url:'${ContextPath}/Collect/toCollect/toOriginCollect/'+entityId+'/'+discId,
		dataType:'json',
		success:function(data){
				$("#collect_entity_id").empty();
				$.each(data,function(i,item){
					console.log(i);
					var option = "<option value = "+i+">"+item+"</option>";
					$("#collect_entity_id").append(option);
				});
				
			}
		});
}
//打开导入页面
	function openTCImport2Disc(entityId,discId){
		getOriginEntity(entityId,discId);
		$("#jqGrid_origin_collect_tb").jqGrid('GridUnload');//清空jqgrid
		 $('#choose_Collect_item_dv').dialog({
	  		    title:"教师成果",
	  		    height:'800',
	  			width:'90%',
	  			position:'center',
	  			modal:false,
	  			draggable:true,
	  		    hide:'fade',
	  			show:'fade',
	  		    autoOpen:true,
	  		    close:function(){
	  		    	
	  		    },
	  		    buttons:{
	  		    	"关闭":function(){
	  		    		$("#choose_Collect_item_dv").dialog("close");
	  		    	}
	  		    }
	  		    });
		 $("#collect_entity_id").trigger('click');
	}
	$(document).ready(function(){
		$("#collect_entity_id").click(function(){
			var oriEntityId=$("#collect_entity_id").val();
			var tarEntityId = tableId;//tableId 参数在colect_view.jsp中
			if(oriEntityId!=''&& oriEntityId!='all'){
				$("#jqGrid_origin_collect_tb").jqGrid('GridUnload');
				$("#gview_jqGrid_origin_collect_tb").show();
				initCollectConfig(oriEntityId,tarEntityId,unitId,discId);//三个参数在collect_view.jsp中
			}
			
		});
		$("#collect_origin_Mul_Search").click(function(){
			multiSearch();
		});
		$("#import2Disc").click(function(){
			var oriEntityId = $("#collect_entity_id").val();
			console.log(oriEntityId);
			var tarEntityId = tableId;//tableId在collect_view.jsp页面
			var importType = 0;//导入类型 0为从教师采集数据中导入
			if(oriEntityId!=''&&oriEntityId!='all'){
				import2Disc(oriEntityId,tarEntityId,unitId,discId,importType);
			}
			
		});
	});
</script>