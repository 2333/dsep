<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<div class="con_header">
	<h3>
		<span class="icon icon-publiccontrast"></span>数据规范化
	</h3>
</div>
<div class="selectbar">

  <!--    <a id="check_nonstandardized" class="button" href="#"><span class="icon icon-search"></span>规范化检测</a> -->
     <a id="start" class="button" href="#"><span class="icon icon-start"></span>启动</a>
     <a id="finish" class="button" href="#"><span class="icon icon-stop"></span>停止</a>
     <div class="right">
     	<a id="batch_normalize" class="button" href="#"><span class="icon icon-set"></span>批量规范</a>
     	<a id="exportAllResult" class="button" href="#" ><span class="icon icon-export"></span>导出</a>
     </div>
</div>
<div class="layout_holder">
	<div class="data_normalize_first">
		<table id="data_normalize_listtb"></table>
	    <div id="data_normalize_listpager"></div>
	</div>
	<div class="data_normalize_second">
	     <table id="data_normalize_oritb"></table>
	     <div id="data_normalize_oripager"></div>
	</div> 
</div>

<script type="text/javascript">


$(document).ready(function(){
	
	$("input[type=submit], a.button , button").button();
	
	$("#data_normalize_listtb").jqGrid({
		url: '${ContextPath}/dataNormalization/normConfig/',
		datatype : 'json',
		mtype : 'GET',
		colNames:['表名','实体ID','规范状态','上次操作时间'],
		colModel:[
	               {name:'tableChsName',index:'tableChsName', align:"center",width:150},
	               {name:'entityId',index:'entityId',align:"center",width:50,hidden:true},
	               {name:'normStatus',index:'normStatus',align:"center",width:80,formatter:function(cellvalue, options, row){
				     	if(cellvalue==0){
				        	 return "未规范";
				    	}else if(cellvalue==1){
				        	return "已完成";
				   		}else{
				   			return "规范中";
				   			}
				   		}},
	               {name:'normTime',index:'normTime',align:"center",width:50,hidden:true}
		],
		height:"100%",
		autowidth:true,
		pager: '#data_normalize_listpager',
		rowNum:9999,
		viewrecords: true,
		multiselect: true,
		multiboxonly: true,
		sortname:'name',
		sortorder: "desc",
		caption: "采集项表",
		onSelectRow:function(id){
			var rowData=$("#data_normalize_listtb").jqGrid("getRowData",id);
			var entityId=rowData["entityId"];
			$("#data_normalize_oritb").clearGridData();
			$(".data_normalize_second").show();
			$("#data_normalize_oritb").setGridParam({url:'${ContextPath}/dataNormalization/normdetail?entityId='+entityId}).trigger("reloadGrid");
			
		}
	}).navGrid('#data_normalize_listpager',{edit:false,add:false,del:false});
	
	$("#data_normalize_oritb").jqGrid({
		url: '${ContextPath}/dataNormalization/normdetail?entityId=',
		datatype : 'json',
		mtype : 'GET',
		colNames:['ID','实体ID','所属表名','非规范列名','字段名称','列数据','对应规范化数据',''],
		colModel:[  
					{name:'id',index:'id', align:"center",width:150,hidden:true},
		           {name:'entityId',index:'entityId',align:"center",width:50,hidden:true},
                   {name:'entityChsName',index:'entityChsName', align:"center",width:150},
	               {name:'fieldChsName',index:'fieldChsName', align:"center",width:150},
	               {name:'fieldName',index:'fieldName',align:"center",width:50,hidden:true},
	               {name:'oldValue',index:'oldValue', align:"center",width:235},
	               {name:'normValue',index:'normValue', align:"center",width:235,edittype:"select",editoptions:{value:modifyNormal_Info("")},editable:true,formatter:function(cellvalue, options, row){
				     	if(cellvalue=="未规范"){
				        	 return "";
				    	}else{
				   			return cellvalue;
				   			}
				   		}},
	               {name:'edit_save',index:'edit_save', align:"center",width:150},
		],
		height:"100%",
		autowidth:true,
		pager: '#data_normalize_oripager',
		rowNum:10,
		rowList:[10,20,30],
		viewrecords: true,
		multiselect: true,
		sortorder: "desc",
		caption: "检测结果",
		gridComplete: function(){
			var ids = jQuery("#data_normalize_oritb").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var id=ids[i];
				
				var modify = "<a  href='#' onclick=modifyNormal_Info('"+id+"')>规范数据</a>"; 
				var save="<a  href='#' onclick=saveNormal_Info('"+id+"')>保存</a>";
				jQuery("#data_normalize_oritb").jqGrid('setRowData',ids[i],{edit_save :modify+"  "+save});
			}	
		},
	}).navGrid('#data_normalize_oripager',{edit:false,add:false,del:false});
});


var contextPath="${ContextPath}";
$("#exportAllResult").click(function(){
	var exportURL = "${ContextPath}/dataNormalization/normExport/";
	outputJS(exportURL);
});

function modifyNormal_Info(id)
{
	///////////////////////////
	var rowData=$("#data_normalize_oritb").jqGrid("getRowData",id);
	var entityId=rowData["entityId"];
	var fieldName=rowData["fieldName"];
	var data;
    $.ajax({url:"${ContextPath}/dataNormalization/normdataset?entityId="+entityId+"&fieldName="+fieldName,async:false, success:function(e){           
    if (e != null) { 
         data = e;                                                            
     }    
    }}); 
  
    $("#data_normalize_oritb").setColProp('normValue', { editoptions: { value: data} });

    $("#data_normalize_oritb").jqGrid('editRow',id);
}
function saveNormal_Info(id)
{
	$("#data_normalize_oritb").jqGrid('saveRow',id);	
	 
	var rowData=$("#data_normalize_oritb").jqGrid("getRowData",id);	
	var dataId=rowData["id"];
	var normValue=rowData["normValue"];    //下拉框对象  encodeURIComponent
	//传递规范结果到后台 */
	$.post("${ContextPath}/dataNormalization/normSaveOne",
			{dataId:dataId,normValue:normValue},
			function(data){
				if(data==1||data==2){
					$("#data_normalize_listtb").setGridParam({url:'${ContextPath}/dataNormalization/normConfig/'}).trigger("reloadGrid");
				}	
		});
}

function test1(){
	$(".data_normalize_second").show();
	
	$("#data_normalize_oritb").clearGridData();
	$("#data_normalize_oritb").jqGrid({
		datatype: "local",
		colNames:['ID','所属表名','非规范列名','列数据','对应规范化数据',''],
		colModel:[  
					{name:'id',index:'id', align:"center",width:150,hidden:true},
                   {name:'entityChsName',index:'entityChsName', align:"center",width:150},
	               {name:'fieldChsName',index:'fieldChsName', align:"center",width:150},
	               {name:'oldValue',index:'oldValue', align:"center",width:150},
	               {name:'normalize',index:'normalize', align:"center",width:150,edittype:"select",editoptions:{value:"未规范：请选择"},editable:true},
	               {name:'edit_save',index:'edit_save', align:"center",width:150},
		],
		height:"100%",
		autowidth:true,
		pager: '#data_normalize_oripager',
		rowNum:10,
		rowList:[10,20,30],
		viewrecords: true,
		multiselect: true,
		sortorder: "desc",
		caption: "检测结果",
		gridComplete: function(){
			var ids = jQuery("#data_normalize_oritb").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var modify = "<a href='#' onclick=modifyNormal_Info('"+ids[i]+"')>规范数据</a>"; 
				var save="<a href='#' onclick='saveNormal_Info("+ids[i]+")'>保存</a>";
				jQuery("#data_normalize_oritb").jqGrid('setRowData',ids[i],{edit_save :modify+"  "+save});
			}	
		},
	}).navGrid('#data_normalize_oripager',{edit:false,add:false,del:false});
	var mydata1 = [
		   			{tablename:"科研获奖",unnormalize:"获奖等级",coldata:"图灵奖"},
		   			{tablename:"国家级、省部级、境外合作项目",unnormalize:"项目类型",coldata:"重大科研项目"}
		   		];
	for(var i=0;i<=mydata1.length;i++)
		$("#data_normalize_oritb").jqGrid('addRowData',i+1,mydata1[i]); 
	
	/*树结构*/
	var nodes = [
	{"name":"科学研究", open:true, 
		children: [
                    {"name": "科研获奖"},
			       	{"name": "本学科代表性学术论文质量",	open:true,children: [{"name":"近五年收录的本学科代表性学术论文"},{"name":"近三年发表的高水平论文"}]},
			       	{"name": "代表性科研项目",open:true,children:[{"name":"国家级、省部级、境外合作科研项目"}]},
                  ]
},
     {"name":"师资队伍与资源", open:true, 
           		children: [
           		        {"name": "专家"},
           		        {"name": "团队"},
                 ]
     
     },
     {"name":"人才培养质量", open:true, 
    		children: [
    		           {"name":"优秀教学成果奖"},
    		           {"name":"学生国际交流情况"}
          ]

     }
    ],
	setting = {
		view: {
			selectedMulti: false
		}
	};
	$.fn.zTree.init($("#check_tree"), setting, nodes);
}

</script>