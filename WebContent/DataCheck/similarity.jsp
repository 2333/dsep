<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<div class="con_header">
	<h3>
		<span class="icon icon-search"></span>数据查重
	</h3>
</div>
<div class="con_header">
	<table class="left">
		<tr>
			<td>
				<a id="startCheck" class="button" href="#"><span class="icon icon-search"></span>开始检查</a>
			</td>
		</tr>
	</table>
	<table class="right">
		<tr>
			<td>
				<a id="exportResult" class="button" href="#"><span class="icon icon-export"></span>导出结果</a>
			</td>
		</tr>
	</table>
</div>
<div class="layout_holder">
	<div class="sim_check_first_block">
		<div class="table">
			<table id="sim_entry_tb"></table>
			<div id="sim_entry_pager"></div>
		</div>
	</div>
	<div class="sim_check_second_block">
		<div class="con_header">
			<h3>
				<span class="icon icon-search"></span>检查结果<span id="result_label"></span>
			</h3>
		</div>
		<div class="selectbar inner_table_holder">
			<table class="layout_table">
				<tr>
					<td>
					    <span class="TextFont">单位：</span>
					</td>
					<td >
						<c:choose>
							<c:when test="${userSession.userType == 2 || userSession.userType == 3}">
								<input id="unitId" type="text" value="${userSession.unitId}" disabled="disabled"/>
							</c:when>
							<c:otherwise>
								<input id="unitId" type="text" value=""/>
							</c:otherwise>
						</c:choose>
					</td>
					<td class="left_space">
					    <span class="TextFont">${textConfiguration.disc}：</span>
					</td>
					<td>
						<c:choose>
							<c:when test="${userSession.userType == 3}">
								<input id="discId" type="text" value="${userSession.discId}" disabled="disabled"/>
							</c:when>
							<c:otherwise>
								<input id="discId" type="text" value=""/>
							</c:otherwise>
						</c:choose>
					</td>
					
					<td>
						
						<c:choose>
							<c:when test="${userSession.userType == 3}">
							</c:when>
							<c:otherwise>
								<a  id="search_btn" class="button" href="#"><span class="icon icon-search"></span>过滤结果</a>
							</c:otherwise>
						</c:choose>
					</td>
					
				</tr>
			</table>
		</div>
		<div class="table">
			<table id="sim_result_tb"></table>
			<div id="sim_result_paper"></div>
		</div>
	</div>
</div>

<div class="process_dialog">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 正在查重，请稍候！
	<div id="process_bar" style="margin-top:10px;"></div>
</div>


<script type="text/javascript">

	var contextPath = "${ContextPath}";
	
	$("#process_bar").progressbar({value: 0});
	
	var newValue, timeOut;
	//进度条
	function updateProcessBar(){
		
		newValue = $("#process_bar").progressbar("option", "value") + 3;
        
		$("#process_bar").progressbar("option", "value", newValue);
		
		console.log(newValue);
        
		if(newValue <= 100) timeOut = setTimeout(updateProcessBar, 1000);
		else {
			$("#process_bar").progressbar({value: 0});
			updateProcessBar();
		}
	}
	
	$(document).ready(function() {
	
		$("#result_label").html("-全部结果");
		
		$(".process_dialog").hide();
		
		$("input[type=submit], a.button , button").button();
		
		//开始查重
		$("#startCheck").click(function() {
			
			var selectedEntities = $("#sim_entry_tb").jqGrid("getGridParam", "selarrrow");
			
			if(selectedEntities.length == 0){
				alert_dialog("请选择要查重的采集项!");
			}else{
			
				$("#result_label").html("");
				$(".norecords").hide(); 
				$(".process_dialog").show();
				
				$('.process_dialog').dialog({
					position : 'center',
					modal:true,
					autoOpen : true,
				});
				
				updateProcessBar();
				
				//获取选中要查重的配置表
				var entityIds = new Array();
				for(var i = 0;i<selectedEntities.length;i++){
					var entityId =  $("#sim_entry_tb").jqGrid("getCell", selectedEntities[i], "entityId");
					entityIds.push(entityId);
				}
				
				$.post('${ContextPath}/check/similarityStart?entityIds='+ entityIds,function(data){
					if (data){
						$.post('${ContextPath}/check/similarityUpdate?entityIds='+ entityIds,function(result) {
							if (result) {
								$(".process_dialog").dialog("destroy");
								$(".process_dialog").hide();
								alert_dialog("检查完成！");
								$("#sim_entry_tb").setGridParam({url : '${ContextPath}/check/similarityEntry'}).trigger("reloadGrid");
								$("#sim_result_tb").setGridParam({url:'${ContextPath}/check/similarityResult?entityId=&unitId=&discId='}).trigger("reloadGrid");
								$("#result_label").html("-全部结果");
							}
							else{
								$(".process_dialog").dialog("destroy");
								$(".process_dialog").hide();
								alert_dialog("载入有重复的采集项列表失败！");
							}
						});
					}
					else{
						$(".process_dialog").dialog("destroy");
						$(".process_dialog").hide();
						alert_dialog("检查出错！");
						$("#sim_entry_tb").setGridParam({url : '${ContextPath}/check/similarityEntry/'}).trigger("reloadGrid");
						$("#sim_result_tb").setGridParam({url:'${ContextPath}/check/similarityResult?entityId=&unitId=&discId='}).trigger("reloadGrid");
					}
					clearTimeout(timeOut);//清空进度条
				});
			}
		});
		
		//读取配置表内容
		$('#sim_entry_tb').jqGrid({
			url : '${ContextPath}/check/similarityEntry/',
			datatype : 'json',
			mtype : 'GET',
			colNames : [ 'ID','采集项ID', '采集项', '检查用户','检查时间', '结果'],
			colModel : [ {name : 'id',index : 'id',width : 60,align : "center",hidden : true}, 
			             {name : 'entityId',index : 'entity_id',width : 100,align : "center",hidden : true}, 
			             {name : 'entityChsName',index : 'entity_chs_name',width : 150, align : "center"}, 
			             {name : 'userId',index : 'user_id',width : 100,align : "center",editable : true,hidden : true},
			             {name : 'checkDate',index : 'check_date',width : 100,align : "center",formatter:"date",formatoptions: {srcformat:'u',newformat:'Y-m-d'}},
			             {name :'simFlag',index : 'sim_flag',width:60,align:"center",editable : true,formatter:function(cellvalue, options, row){
						     	if(cellvalue=="0"){
						        	return "通过";
						    	}else if(cellvalue=="1"){
						        	return "有重复";
						   		}else{
						   			return "尚未查重";
						   		}
						 },cellattr: function(rowId, val, rawObject, cm, rdata){
							 if(val=="通过"){
					        	return "style='color:green'";
					    	}else{
					    		return "style='color:red'";
					   		}
						}}
			           ],
			height : '100%',
			autowidth : true,
			pager : '#sim_entry_pager',
			rowNum : 20,
			rowList : [ 20, 30 ],
			sortname : 'entity_id',
			sortorder : 'asc',
			caption : "有重复的采集项",
			jsonReader : { 
				root : 'rows',
				page : 'pageIndex',
				total : 'totalPage',
				records : 'totalCount', 
				repeatitems : false
			},
			multiselect : true,
			multiboxonly : true,
			onSelectRow : function(id) {
				var rowData = $('#sim_entry_tb').jqGrid("getRowData",id);
				var entityId = rowData["entityId"];
				var entityName = rowData["entityChsName"];
				
				$("#sim_result_tb").setGridParam({url:'${ContextPath}/check/similarityResult?entityId='+ entityId +'&unitId=&discId=',
						sortorder: "asc",
    					sortname:'seq_no',
    					page:1}).trigger("reloadGrid");
				$("#result_label").html("-"+entityName);
				
			}
		}).navGrid('#sim_entry_pager', {edit:false,add:false,del:false});
		
		//载入结果表
		$('#sim_result_tb').jqGrid({
			url : '${ContextPath}/check/similarityResult',
			datatype : 'json',
			mtype : 'GET',
			colNames : [ 'ID','采集项ID', '采集项', '单位ID','${textConfiguration.discNumber}','序号','数据ID','重复字段','重复字段名','重复值','关键字段','重复数据ID','检查用户'],
			colModel : [ {name : 'id',index : 'id',hidden : true}, 
			             {name : 'entityId',index:'entity_id',hidden : true}, 
			             {name : 'entityChsName',index : 'entity_chs_name',hidden : true}, 
			             {name : 'unitId',index : 'unit_id',width : 50,align : "center"},
			             {name : 'discId',index : 'disc_id',width : 70,align : "center"},
			             {name : 'seqNo',index : 'seq_no', sorttype:"number",width : 30,align : "center"},
			             {name : 'dataId',index : 'data_id',hidden : true}, 
			             {name : 'field',index : 'field',hidden : true}, 
			             {name : 'fieldName',index : 'field_name',width : 70,align : "center"},
			             {name : 'dataValue',index : 'data_value',width : 400,align : "center"}, 
			             {name : 'keyValue',index : 'key_value',width : 250,align : "center",hidden : true}, 
			             {name : 'similarityIds',index : 'similarity_ids',hidden : true}, 
			             {name : 'userId',index : 'user_id',hidden : true}
					   ],
			rownumbers:true,
			viewrecords: true,
			height : '100%',
			autowidth : true,
			pager : '#sim_result_paper',
			rowNum : 20,
			rowList : [ 20, 30 ],
			sortname : 'seq_no',
			sortorder : 'asc',
			caption : "重复结果",
			jsonReader : { 
				root : 'rows',
				page : 'pageIndex',
				total : 'totalPage',
				records : 'totalCount', 
				repeatitems : false
			},
			////////////////////////////////展示数据详情
			loadComplete: function(){
				var re_records = $("#sim_result_tb").getGridParam('records');
				console.log(re_records);
				if(re_records == 0 || re_records == null){
					console.log("re_records == 0 || re_records == null");	
					if($(".norecords").html() == null){
						console.log("norecords.html() == null");	
						$("#sim_result_tb").parent().append("<div class=\"norecords\">没有重复数据</div>");
					}
					
					$(".norecords").show();
				}else{
					$(".norecords").remove();
				}
			},
			subGrid: true,
			subGridRowExpanded: function(subgrid_id, row_id) {
				
				var entityId = $('#sim_result_tb').getCell(row_id, 'entityId');
				var dataId = $('#sim_result_tb').getCell(row_id, 'dataId');
				var simIds = $('#sim_result_tb').getCell(row_id, 'similarityIds');
				
				var subgrid_table_id;
				subgrid_table_id = subgrid_id+"_t";

				$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"'></table>");
				
				$.getJSON("${ContextPath}/check/similarityGroupConfig?entityId="+entityId, //获取某entity的元数据信息
					function initJqTable(data) {
						$("#"+subgrid_table_id).jqGrid({
		 					url :'${ContextPath}/check/similarityDetails?entityId='+ entityId + '&dataId=' + dataId+ '&simIds=' + simIds,//取数据
							datatype : 'json',
							mtype : 'POST',
							colModel : data.colConfigs,
							height : "100%",
							autowidth : true,
							shrinkToFit : false,
							rowNum : 50,
							rowList : [50],
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
							prmNames : {
								page : "page",
								rows : "rows",
								sort : "sidx",
								order : "sord"
							}
						});
					}
				);
			}
			/////////////////////////////////展示数据详情
		}).navGrid('#sim_result_paper', {edit:false,add:false,del:false});
		
		//结果表过滤
		$("#search_btn").click(function(){
			
			var entityId;
			
			var id=$('#sim_entry_tb').jqGrid('getGridParam','selrow');
			if(id==null||id=="") entityId="";
			else{
				var rowData = $('#sim_entry_tb').jqGrid("getRowData",id);
				entityId = rowData["entityId"];
			}
			
			$("#sim_result_tb").setGridParam({url:'${ContextPath}/check/similarityResult?entityId='+ entityId +'&unitId='+ $("#unitId").val() +'&discId=' + $("#discId").val()}).trigger("reloadGrid");
		});
		
		//导出结果表
		$("#exportResult").click(function(){
			var url = '${ContextPath}/check/similarityExport/';
			outputJS(url);
		});
	});
	
</script>