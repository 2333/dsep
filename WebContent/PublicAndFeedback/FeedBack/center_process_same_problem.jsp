<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>反馈数据源配置-数据处理
	</h3>
</div>

<div class="selectbar inner_table_holder">
	<table class="left layout_table">
     	<tr>
     		<td>
	     		<jsp:include page="../_ddl_join_unit.jsp"></jsp:include>
	     		&nbsp;
	     		<jsp:include page="../_ddl_join_discipline.jsp"></jsp:include>
     		</td>
     	</tr>
     </table>
     <table class="right" id="tb_export">
     	<tr>
     		<td>
	         	<a id="btn_return" class="button" href="#">
	         		<span class="icon icon-undo"></span>返回
	         	</a>
     		</td>
     	</tr>
     </table>
</div>

<div id="div_feedback_layout" class="layout_holder" style="width:100%">
	<div id="dv_sameProcess">
		<div class="table">
			<table id="tb_sameItem_list"></table>
			<div id="dv_sameItem_pager"></div>
		</div>
	</div>
</div>

<div hidden="true">
	<jsp:include page="query_same_problem_dialog.jsp"></jsp:include> 
</div>


<script src="${ContextPath}/js/feedback/feedback_jqgrid.js"></script>
<script type="text/javascript">

	var jqTable = "#tb_sameItem_list";
	var currentFeedbackRoundId = "${currentRound.id}";
	var backupVersionId = "${currentRound.backupVersionId}";

	$(document).ready(function() {	
		$("input[type=submit], a.button , button").button();
		addDropdownList();
		initJqTable();
	});
	
	//初始化学科和学校下拉框，增加“全部”这一项
	function addDropdownList(){
		$("#ddl_join_discipline").append('<option value="" selected="selected">全部</option>');
		$("#ddl_join_unit").append('<option value="" selected="selected">全部</option>');
	}
	
	//查看学校发生改变
	$("#ddl_join_unit").change(function(){
		initJqTable();
	});
	
	//查看学科发生改变
	$("#ddl_join_discipline").change(function(){
		initJqTable();
	});
	
	//初始化反馈数据表
	function initJqTable(){
		$(jqTable).jqGrid('GridUnload');
		$(jqTable).jqGrid({
			url:'${ContextPath}/feedback/beginRound_getProcessEntity?'
				+'problemDiscId='+$("#ddl_join_discipline").val()//被异议的学科ID
				+'&problemUnitId='+$("#ddl_join_unit").val()//被异议的学科ID
				+'&currentRoundId='+currentFeedbackRoundId,//当前公示批次ID
			datatype:'json',
			mtype:'POST',
			colNames:['id','查看','采集项ID','数据项ID','学校','${textConfiguration.disc}','采集项','关键项','序号'],
			colModel:[
				{name:'id',index:'id',align:"center",editable:true,hidden:true},
				{name:'query',index:'query',width:60,align:'center',sortable:false}, 
			 	{name:'problemCollectEntityId',index:'problem_collect_entity_id',algin:'center',hidden:true,editable:true},
				{name:'problemCollectItemId',index:'problem_collect_item_Id',algin:'center',hidden:true,editable:true},
				{name:'unitId',index:'problem_unit_id',align:'center',width:60},
	            {name:'discId',index:'problem_disc_id',align:'center',width:60},
	            {name:'entityName',index:'problem_collect_entity_name',align:'center',width:300},
	            {name:'importantAttrValue',index:'important_attr_value',align:'center',width:300},
	            {name:'seqNo',index:'problem_seq_no',align:'center',width:60} 		    
	        ],
			height : '100%',
			width:$("#content").width(),
			autowidth : false,
			shrinkToFit : true,
			pager : '#dv_sameItem_pager',
			pgbuttons : true,
			rowNum : 20,
			caption:'采集数据',
			rownumbers:true,
			rowList : [ 10, 20, 30 ],
			viewrecords : true,
			sortname : 'problem_unit_id',
			gridComplete:function(){ //给表格添加编辑列和删除列
				showNoRecords(jqTable);
				var ids = $(jqTable).jqGrid('getDataIDs');
				addQueryCol(ids);
				
			},
/////////////////////////////////展示数据详情
			subGrid: true,
			subGridRowExpanded: function(subgrid_id, row_id) {
				showSubGridData(subgrid_id,row_id,
					'${ContextPath}/feedback/beginRound_getViewConfig',
					'${ContextPath}/feedback/beginRound_getCollectData');	
			},
			jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
				root : 'rows', //包含实际数据的数组  
				page : 'pageIndex', //当前页  
				total : 'totalPage',//总页数  
				records : 'totalCount', //查询出的记录数  
				repeatitems : false
			}}).navGrid('#dv_sameItem_pager',{edit:false,add:false,del:false
		});
	}

	$("#btn_return").click(function(){
		$.ajax({
			url:"${ContextPath}/feedback/beginRound",
			type:'POST',
			success:function(data){
				$("#content").empty();
				$("#content").append(data);
			}
		});
	});
	
	//给jqGrid增加查看列
	function addQueryCol(ids){
		for(var i=0; i < ids.length; i++){
			var entityId = $(jqTable).getCell(ids[i],"problemCollectEntityId");//实体ID
			var itemId = $(jqTable).getCell(ids[i],"problemCollectItemId");
			
			//querySameProblem函数位于query_same_problem_dialog.jsp页面
			var queryRow = "<a href='#' onclick=\"querySameProblem('"+entityId+"','"+itemId+"');\">查看</a>";
			
			$(jqTable).setRowData(ids[i],{query:queryRow});	
			/* $(jqTable+" #"+ids[i]).find('td').css('color','red'); */
		}
	}
	
</script>