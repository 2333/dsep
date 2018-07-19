<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<div class="con_header">
	<h3>
		<span class="icon icon-search"></span>公共库比对
	</h3>
</div>

<div class="layout_holder">
	<div class="pubLib_check_first_block">
		<table id="public_entry"></table>
		<div style="padding:5px 5px;"><a id="check" class="button" href="#" style="width:230px;">比对</a></div>
	</div>

	<div class="pubLib_check_second_block">
		<div class="con_header">
			<h3>
				<span class="icon icon-search"></span>比对结果<span id="result_label"></span><span id="publibId" style="display:none;"></span>
			</h3>
		</div>
		<div class="con_header inner_table_holder" >
			<table class="left" style="height:29px;">
	     		<tr>
					<td><label class="TextFont">显示结果：&nbsp;</label></td>
					<td>
						<input type="radio" name="result_type" class="TextFont" value="" checked/><span class="TextFont">全部结果</span> &nbsp;
						<input type="radio" name="result_type" class="TextFont" value="O"/><span class="TextFont">与公共库不一致</span>  &nbsp;
						<input type="radio" name="result_type" class="TextFont" value="M"/><span class="TextFont">公共库中不存在</span> &nbsp;
					</td>
				</tr>
	     	</table>
   			<table class="right">
	     		<tr>
					<td>
						<a  id="exportData" class="button" href="#">
						<span class="icon icon-export"></span>导出比对结果</a>
					</td>
				</tr>
	     	</table>
    	</div> 
    	<div>
   			<table id="public_result"></table>
			<div id="public_result_page"></div>
   		</div>
	</div>
	
</div>


<div class="process_dialog">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 正在比对，请稍候！
</div>


<script type="text/javascript">

	var contextPath = "${ContextPath}";
	$(document).ready(function(){	
		
		$("#result_label").html("");
		$( "input[type=submit], a.button , button" ).button();
		$(".process_dialog").hide();
		$("#exportData").click(function(){//下载查重结果EXCEL表
			var url = '${ContextPath}/check/pubExportPublicResult/';
			outputJS(url);
		});
		
		//开始比对
		$("#check").click(function(){

			$(".process_dialog").show();
			$('.process_dialog').dialog({
				position : 'center',
				modal:true,
				autoOpen : true,
			});
			
			$.post('${ContextPath}/check/pubStartCompare',function(data){
				if (data){
					$(".process_dialog").dialog("destroy");
					$(".process_dialog").hide();
					alert_dialog("比对完成！");
					$("#public_entry").setGridParam({url:'${ContextPath}/check/pubGetPublicEntry/'}).trigger("reloadGrid");
				}
				else{
					$(".process_dialog").dialog("destroy");
					$(".process_dialog").hide();
					alert_dialog("比对出錯！");
				}
			});
		});
		
		//载入入口表
		$("#public_entry").jqGrid({
			url:'${ContextPath}/check/pubGetPublicEntry/',
			datatype : 'json',
			mtype : 'GET',
			colNames:['表ID','表名','比对时间','比对结果'],
			colModel:[
				{name :'publibId',index:'publibId', width:40,hidden : true},      
				{name :'publibChsName',index:'publibChsName', width:40},
				{name :'checkDate',index : 'checkDate',width : 80,align : "center",formatter:"date",formatoptions: {srcformat:'u',newformat:'Y-m-d'}},
				{name :'isChecked',index : 'isChecked',width : 60,align : "center",editable : true,formatter:function(cellvalue, options, row){
			     	if(cellvalue=="0"){
			        	 return "未比对";
			    	}else{
			        	return "已比对";
			   		}
			 },cellattr: function(rowId, val, rawObject, cm, rdata){
				 if(val=="已比对"){
		        	return "style='color:green'";
		    	}else{
		    		return "style='color:red'";
		   		}
			}}],
			height:"100%",
			autowidth:true,
			rowNum:30,
			viewrecords: true,
			sortorder: "desc",
			caption: "选择比对的公共库表",
			jsonReader : {
				root : 'rows',
				page : 'pageIndex',
				total : 'totalPage',
				records : 'totalCount', 
				repeatitems : false
			},
			onSelectRow : function(id) {
				var rowData = $('#public_entry').jqGrid("getRowData",id);
				
				var isChecked = rowData["isChecked"];
				var publibId = rowData["publibId"];
				var publibChsName = rowData["publibChsName"];

			if (isChecked == "未比对") {
					alert_dialog("该公共库尚未比对！");
				}
				else{
					$("#result_label").html("-"+publibChsName);
					$("#publibId").html(publibId);
					//载入publibId库比对的结果表
					$("#public_result").setGridParam({url:'${ContextPath}/check/pubGetPublicResult?pubLibId=' + publibId + '&type='}).trigger("reloadGrid");
				}
			}
		});
		
		$("#public_result").jqGrid({
			url:'${ContextPath}/check/pubGetPublicResult?pubLibId=&type=',
			datatype : 'json',
			mtype : 'GET',
			colNames:['主键','学校ID','学科ID','实体ID','采集项','序号','标识字段','字段名','本地值','公共库ID','公共库值','比对结论','详细情况'],
			colModel:[
				{name:'id',index:'ID', width:60,align:"center",hidden : true},
				{name:'unitId',index:'UNIT_ID', width:60,align:"center"},
				{name:'discId',index:'DISC_ID', width:60,align:"center"},
				{name:'entityId',index:'ENTITY_CHS_ID',width:60,align:"center",hidden : true},
				{name:'entityChsName',index:'ENTITY_CHS_NAME',width:60,align:"center"},
				{name:'seqNo',index:'SEQ_NO',width:40,align:"center", sortble:true},
				{name:'flagField',index:'FLAG_FIELD',width:60,align:"center"},
				{name:'localChsField',index:'LOCAL_CHS_FIELD',width:60,align:"center"},
				{name:'localValue',index:'LOCAL_VALUE',width:160,align:"center"},
				{name:'publibId',index:'PUBLIB_ID',width:70,align:"center",hidden : true},
				{name:'pubValue',index:'PUB_VALUE',width:80,align:"center"},
				{name:'compareResult',index:'COMPARE_RESULT',align:"center",width:60,editable : true,formatter:function(cellvalue, options, row){
				     	if(cellvalue=="M"){
				        	 return "不存在公共库";
				    	}else{
				        	return "与公共库不一致";
				   		}
				 },cellattr: function(rowId, val, rawObject, cm, rdata){
					 if(val=="与公共库不一致"){
			        	return "style='color:orange'";
			    	}else{
			    		return "style='color:red'";
			   		}
				}},
				{name:'localItemId',index:'LOCAL_ITEM_ID', width:100 ,align:"center",hidden : true}
			],
			//cmTemplate : {sortable : false},
			height:"100%",
			autowidth:true,
			pager: '#public_result_page',
			rowNum:20,
			rowList:[20,30],
			viewrecords: true,
			sortname: 'SEQ_NO',
			sortorder: "asc",
			caption: "比对结果",
			jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
				root : "rows", //包含实际数据的数组  
				page : "pageIndex", //当前页  
				total : "totalPage",//总页数  
				records : "totalCount", //查询出的记录数  
				repeatitems : false,
			},
			/////////////////////////////////展示数据详情
			subGrid: true,
			subGridRowExpanded: function(subgrid_id, row_id) {
				
				var entityId = $('#public_result').getCell(row_id, 'entityId');
				var itemId = $('#public_result').getCell(row_id, 'localItemId');
				
				var subgrid_table_id;
				subgrid_table_id = subgrid_id+"_t";

				$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"'></table>");
				
				$.getJSON("${ContextPath}/check/pubLocalDataConfig?entityId="+entityId, //获取某entity的元数据信息
					function initJqTable(data) {
						$("#"+subgrid_table_id).jqGrid({
		 					url :'${ContextPath}/check/pubDataDetail?entityId='+ entityId + '&itemId=' + itemId,//取数据
							datatype : 'json',
							mtype : 'POST',
							colModel : data.colConfigs,
							height : "100%",
							autowidth : true,
							shrinkToFit : false,
							rowNum : 10,
							rowList : [ 10],
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
					});
			}
			/////////////////////////////////展示数据详情
		}).navGrid('#public_result_page',{edit:false,add:false,del:false});
		
		//结果表过滤
		$("input[name='result_type']").change(function(){
			var type = $(this).attr("value");
			var publibId = $("#publibId").html();
			$("#public_result").setGridParam({url:'${ContextPath}/check/pubGetPublicResult?pubLibId=' + publibId + '&type=' + type}).trigger("reloadGrid");
		});
	});
</script>