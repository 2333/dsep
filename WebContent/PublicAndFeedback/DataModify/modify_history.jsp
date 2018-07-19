<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="con_header inner_table_holder">
	<table class="layout_table">
		<tr>
			<td>
				<span class="icon icon-web"></span>
				<span class="TextFont">数据修改记录</span>
			</td>
		</tr>
	</table>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<jsp:include page="../_ddl_join_unit.jsp"></jsp:include>
			
		    <jsp:include page="../_ddl_join_discipline.jsp"></jsp:include>
		</tr>
	</table>
</div>
<div class="tabs" style="width:100%">
	<ul id="tab_status">
		<c:if test="${!empty modifyTypeMap}">
			<c:forEach items="${modifyTypeMap}" var="modifyTypeItem">
				<li id="${modifyTypeItem.key}">
					<a id="${modifyTypeItem.key}" class="objectLink" href="#" >
						${modifyTypeItem.value}
					</a>
				</li>
			</c:forEach>
		</c:if>
	</ul>
	<div class="table">
		<table id="modifyHistory_list"></table>
		<div id="pager"></div>
	</div>
</div>

<script src="${ContextPath}/js/edit_jqgrid.js"></script>
<script src="${ContextPath}/js/feedback/feedback_jqgrid.js"></script>
<script type="text/javascript">

	var jqTable = "#modifyHistory_list";
	var modifyType = $("#tab_status").children().eq(0).children().eq(0).attr("id");
	var backupVersionId = 'center_delete';
	
	var modifyGrid = {
		model:[{name:'id',index:'id',align:'center',editable:true,hidden:true},
		 	{name:'entityId',index:'entityId',hidden:true},
		    {name:'entityItemId',index:'entityItemId',hidden:true},
		    {name:'unitId',index:'unitId',align:'center',width:15},
			{name:'discId',index:'discId',align:'center',width:15},
			{name:'entityName',index:'entityName', width:30,align:'center'},
			{name:'seqNo',index:'seqNo', width:10,align:'center'},
			{name:'attrName',index:'attrName', width:30,align:"center",editable:true},
			{name:'attrOriginalValue',index:'attrOriginalValue',width:30,align:'center'},
			{name:'attrModifyValue',index:'attrModifyValue',width:30,align:'center'},
			{name:'modifyTime',index:'modifyTime',width:30, sorttype:"date",align:'center'},
			{name:'operateUserId',index:'operateUserId',width:20,align:'center',sortable:false}],
		caption:'修改记录',
		name:['ID','实体ID','采集项ID','学校','${textConfiguration.disc}','采集项','序号','修改项','原始值','修改值','修改时间','操作用户']
	};
	
	var deleteGrid = {
		model: [{name:'id',index:'id',align:'center',editable:true,hidden:true},
		    {name:'entityId',index:'entityId',hidden:true},
		    {name:'entityItemId',index:'entityItemId',hidden:true},
		    {name:'unitId',index:'unitId',align:'center',width:15},
			{name:'discId',index:'discId',align:'center',width:15},
			{name:'entityName',index:'entityName', width:30,align:'center'},
			{name:'seqNo',index:'seqNo', width:10,align:'center'},
			{name:'modifyTime',index:'modifyTime',width:30, sorttype:"date",align:'center'},
			{name:'operateUserId',index:'operateUserId',width:20,align:'center',sortable:false}],
		caption:'删除记录',
		name:['ID','实体ID','采集项ID','学校','${textConfiguration.disc}','采集项','序号','修改时间','操作用户']
	};
	
	var gridParam;
	
	$(document).ready(function(){
		
		$(".tabs").tabs({//生成标签页
			beforeLoad : function(event, ui) {
				event.preventDefault();
				return;
			},
			create : function(event, ui) {
				event.preventDefault();
				return;
			},
			load : function(event, ui) {
				event.preventDefault();
				return;
			}
		});
		
		changeTab();
		
		$("input[type=submit], a.button , button" ).button();
		addDropdownList(); //给下拉框增加“全部”这一项
		
		loadModifyHistoryJqGrid();
		
	});
	
	//标签页的获取焦点事件
	$('#tab_status').children().focus(function() {
		modifyType = $(this).attr("id"); //标签页的状态
		changeTab();
		loadModifyHistoryJqGrid(); //重新加载异议表格
	});
	
	//标签页的点击事件
	$('#tab_status').children().click(function(){
		modifyType = $(this).attr("id"); //标签页的状态
		changeTab();
		loadModifyHistoryJqGrid(); //重新加载异议表格
	});
	
	//标签页切换时需要重新设置一些属性值，比如jqGrid的数据Model等
	function changeTab(){
		if( modifyType == "1"){ //修改
			gridParam = modifyGrid;
			backupVersionId = ''; 
		}
		else{ //删除
			gridParam = deleteGrid;
			backupVersionId = 'center_delete';
		}
	}

	$("#ddl_join_discipline").change(function(){
		loadModifyHistoryJqGrid();
	});
	
	$("#ddl_join_unit").change(function(){
		loadModifyHistoryJqGrid();
	});
	
	//给下拉框增加全部这一项
	function addDropdownList(){
		$("#ddl_join_discipline").append('<option value="" selected="selected">全部</option>');
		$("#ddl_join_unit").append('<option value="" selected="selected">全部</option>');
	}
	

	
	//加载反馈数据表
	function loadModifyHistoryJqGrid(){
		$(jqTable).jqGrid("GridUnload");
		$(jqTable).jqGrid({
			url:'${ContextPath}/feedback/modifyHistory_getModifyHistory?'
				+'&modifyDiscId='+$("#ddl_join_discipline").val() //反馈学科ID
				+'&modifyUnitId='+$("#ddl_join_unit").val() //反馈学校ID
				+'&modifyType='+modifyType,
			datatype: "json",
			colNames:gridParam.name,
			colModel:gridParam.model,
			jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
	            root: "rows",  //包含实际数据的数组  
	            page: "pageIndex",  //当前页  
	            total: "totalPage",//总页数  
	            records:"totalCount", //查询出的记录数  
	            repeatitems : false,
	        },
	        height:"100%",
			sortable:true,
			rownumbers:true,
			width:$("#content").width(),
			sortname:'modifyTime',
			rownumWidth:15,
			pager: '#pager',
			rowNum:10,
			multiselect:false,
			rowList:[10,20,30],
			autowidth:false,
			shrinkToFit:true,
			viewrecords: true,
			sortorder: "desc",
			caption:gridParam.caption,
			gridComplete:function(){
				showNoRecords(jqTable);
			},
			/////////////////////////////////展示数据详情
			subGrid: true,
			subGridRowExpanded: function(subgrid_id, row_id) {
				showSubGridHistoryData(subgrid_id,row_id,
					'${ContextPath}/feedback/modifyHistory_getViewConfig',
					'${ContextPath}/feedback/modifyHistory_getCollectData',
					'entityId','entityItemId',backupVersionId);
			},
		}).navGrid('#pager',{edit:false,add:false,del:false});
		
	}
</script>