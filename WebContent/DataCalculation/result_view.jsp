<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
 <div class="con_header">
	<h3>
		<span class="icon icon-publiccontrast"></span>结果查看
	</h3>
</div> 
<div class="selectbar inner_table_holder">
	<span class="TextFont">学校代码：</span>
	<input id="unitInput" type="text"/>
	<a id="unitresult" href="#" class="button">查找</a>	
	<div class="right">
        <a id="exportAll_bt" class="button" href="#"><span class="icon icon-export"></span>导出所有学科</a>
    </div>
    
</div>
<div id="result_view_first_block" class="result_view_first_block">
	
    <table id="dsepInfo"></table>
	<div id="pager_dsepInfo"></div>
</div>
<div id="result_view_second_block" class="result_view_second_block">
	<table id="cal_result"></table>
	<div id="cal_result_page"></div>
</div>

<script type="text/javascript">
	
	var contextPath = "${ContextPath}"; 
	$(document).ready(function(){
		$( "input[type=submit], a.button , button" ).button();
		
		$("#exportAll_bt").click(function(){//下载查重结果EXCEL表
			var url = '${ContextPath}/calculateview/calviewexport';
			outputJS(url);
		});
		
		$("#unitresult").click(function(){
	    	var unit=$("#unitInput").val();
	    	$("#cal_result").setGridParam({url:'${ContextPath}/calculateview/calviewunit?unit='+unit}).trigger("reloadGrid");
	    	$("#cal_result").setGridParam().showCol(["discId","discName"]).trigger("reloadGrid");
	    	$("#cal_result").setGridParam().hideCol(["unitId","unitName"]).trigger("reloadGrid");
	    });
		
	});
	
	$("#dsepInfo").jqGrid({
		url : '${ContextPath}/calculateview/calviewdisclist/',
		datatype: 'json',
		mtype : 'GET',
		colNames:['ID','学科代码','学科名称','计算时间'],
		colModel:[
			{name:'id',index:'id', width:50,align:'center',hidden : true},     
			{name:'discId',index:'discId', width:35,align:'center',hidden : true},
			{name:'discName',index:'discName', width:50,align:'center'},
			{name:'calTime',index:'calTime',width:50,align:'center',hidden : true}
		],
		height:"100%",
		autowidth:true,
		pager: '#pager_dsepInfo',
		rowNum:20,
		rowList:[10,20,30],
		viewrecords: true,
		sortname: 'DISC_ID',
		sortorder: "desc",
		jsonReader : { 
			root : 'rows',
			page : 'pageIndex',
			total : 'totalPage',
			records : 'totalCount', 
			repeatitems : false
		},
		caption: "选择学科",
		onSelectRow : function(id){
			var rowdata=$('#dsepInfo').jqGrid("getRowData",id);
			var discId=rowdata['discId'];
			
			$("#cal_result").setGridParam({url:'${ContextPath}/calculateview/calviewresult?discId='+discId}).trigger("reloadGrid");
			$("#cal_result").setGridParam().showCol(["unitId","unitName"]).trigger("reloadGrid");
	    	$("#cal_result").setGridParam().hideCol(["discId","discName"]).trigger("reloadGrid");
		}
	}).navGrid('#pager_dsepInfo', {edit:false,add:false,del:false});
	
	$("#dsepInfo").setSelection(1);
	
	var s;
	var discId1;
	s = $("#dsepInfo").getGridParam('selarrrow');
	//alert("cag"+s);
	if(s.length>0){
		var row1 = $("#dsepInfo").jqGrid("getRowData",s[0]);
		discId1=row1['discId'];
	}

	$("#cal_result").jqGrid({
		url: "${ContextPath}/calculateview/calviewresult?discId="+discId1,
		datatype: "json",
		mtype: 'GET', 
		colNames:['ID','学科代码','学校代码','学科名称','学校名称','得分','排名','聚类'],
		colModel:[
			{name:'ID',index:'id', width:80,align:'center',hidden : true},
			{name:'discId',index:'discId', width:100,align:'center',hidden : true},
			{name:'unitId',index:'unitId', width:100,align:'center'},
			{name:'discName',index:'discName', width:200,align:'center',hidden : true},
			{name:'unitName',index:'unitName', width:200,align:'center'},
			{name:'score',index:'score', width:100,align:'center'},
			{name:'rank',index:'rank', width:100,align:'center'},
			{name:'cluClass',index:'cluClass', width:100,align:'center'}
		],
		height:"100%",
		shrinkToFit:false,
		autoScroll: true,
		autowidth:true,
		rownumbers:true,
		pager: '#cal_result_page',
		rowNum:10,
		rowList:[10,20,30],
		viewrecords: true,
		sortname: 'SCORE',
		sortorder: "desc",
		multibodyonly: true,
		caption: "计算结果",
 //子表
		subGrid: true,
		subGridRowExpanded: function(subgrid_id, row_id) {
			
			var discId=$('#cal_result').getCell(row_id,'discId');
			var unitId=$('#cal_result').getCell(row_id,'unitId');
			var discName=$('#cal_result').getCell(row_id,'discName');
			var unitName=$('#cal_result').getCell(row_id,'unitName');
			
			var subgrid_table_id;
			subgrid_table_id = subgrid_id+"_t";

			$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"'></table>");
		
			initJqTable(subgrid_table_id,discId,unitId,discName,unitName);   //初始化子表
		}
		
	}).navGrid('#cal_result_page',{edit:false,add:false,del:false,search:false,refresh:false});
	
	function initJqTable(subgrid_table_id,discId,unitId,discName,unitName) {
		$("#"+subgrid_table_id).jqGrid({
			url :'${ContextPath}/calculateview/calviewdetial?discId='+ discId + '&unitId=' + unitId,//取数据
			datatype : 'json',
			mtype : 'POST',
			colNames:['指标级别','指标名称','得分'],
			colModel :[{name:'indexLevel',index:'indexLevel', width:100,align:'center',formatter:function(cellvalue, options, row){
				     	if(cellvalue=="1"){
				        	 return "一级";
				    	}else if(cellvalue=="2"){
				        	return "二级";
				   		}},},
			           {name:'indexName',index:'indexName', width:300,align:'center'},
			           {name:'score',index:'score', width:100,align:'center'}
			           ],
			height : "100%",
			autowidth : true,
			shrinkToFit : false,
			rowNum : 50,
			rowList : [50],
			viewrecords : true,
			sortname : "INDEX_LEVEL",
			sortorder : "asc",
			caption : unitName+"-"+discName,
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
	
	
</script>