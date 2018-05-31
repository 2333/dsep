<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="con_header inner_table_holder">
<table class="layout_table left" >
	<tr>
			<td>
				<span class="icon icon-disciplinecollect"></span>
				<span class="TextFont">${textConfiguration.discReportManage}</span>
			</td>
		</tr>
	</table>
	<jsp:include page="/MainFlow/mainFlowState.jsp"></jsp:include>
</div>
<div class="selectbar inner_table_holder" >
	<table class="layout_table left">
	      <tr>
			  <td>
			    <span class="TextFont">学校代码：</span>
    		     	<input id="unit_Id" type="text" style="width:80px;"/>
    		     &nbsp;&nbsp;
         		<span class="TextFont">${textConfiguration.discNumber}：</span>
         			<input id="disc_Id" type="text" style="width:80px;" />
         			&nbsp;&nbsp;
                <span class="TextFont">采集状态：</span>
		      	<select id="state" >
		      		<option value="all" selected>全部</option>
		      		<option value="0">学科正在修改</option>
		      		<option value="1">已经提交至学校</option>
		      		<option value="2">提交至中心</option>
		      		<option value="3">退回至学科</option>
		      		<option value="4">学校撤销提交</option>
                </select>
                &nbsp;&nbsp;
                <span class="TextFont">是否参评：</span>
				<select id="isEval" >
		      		<option value="all" selected>全部</option>
		      		<option value="1">是</option>
		      		<option value="0">否</option>
                </select>
                &nbsp;&nbsp;
                <span class="TextFont">是否需要报告：</span>
				<select id="isReport" >
		      		<option value="all" selected>全部</option>
		      		<option value="1">是</option>
		      		<option value="0">否</option>
                </select>
                &nbsp;&nbsp;
                <a id="search_list" class="button" href="#"><span class="icon icon-search "></span>查找</a >
			  </td>
		  </tr>
	</table>
	<table class="layout_table right">
		<tr>
			<td>
				<a  id="rebootSubmit" class="button" href="#" style="display:none"><span class="icon icon-undo"></span>重启参评</a>
			 </td>
			<td>
				<a  id="terminateSubmit" class="button" href="#" style="display:none"><span class="icon icon-undo"></span>终止提交</a>
			 </td>
		</tr>
	</table>
</div>
<div class="selectbar layout_holder">
	<table id="centercollection_tb"></table>
	<div id="contercollection_pager"></div>
</div>
<div hidden="true">
	<div id="dialog-confirm" title="警告"></div>
</div>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script src="${ContextPath}/js/download/briefsheet.js"></script>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script src="${ContextPath}/js/fileOper/filename_oper.js"></script>
<script type="text/javascript">
var contextPath = "${ContextPath}";
var evalState;//参评状态
/**
 * 控制重启、终止按钮
 */
function controlTerminateAndRebootBT(evalState){
	if(evalState=='5'){
		$("#rebootSubmit").show();
		$("#terminateSubmit").hide();
	}else{
		$("#rebootSubmit").hide();
		$("#terminateSubmit").show();
	}	
}
/**
 * 下载简况表
 */
function downloadBrief(id){
	var rowData = $('#centercollection_tb').jqGrid("getRowData", id);
	var templateId = rowData['eval.briefId'];
	var url="${ContextPath}/CenterDisciplineList/gatherList_download/"+templateId;
	$.commonRequest({
		url:url,
		dataType:'json',
		success:function(message){
			if(message.result=="success"){
				downloadProveMaterial(message.data);
			}
			else 
		    {
				alert_dialog("下载失败!");
		    }
		},
	complete:function(){
		
	}
	});
}
/**
 * 查看学科详情
 */
function viewDiscDetail(id){
	 var rowData = $('#centercollection_tb').jqGrid("getRowData", id);
	 var unitId=rowData['eval.unitId'];
	 var discId=rowData['eval.discId'];
	 var reUrl="${ContextPath}/Collect/toCollect?unitId="+unitId+"&discId="+discId;
	 $.post(reUrl, function(data){
		  $( "#content" ).empty();
		  $( "#content" ).append( data );
	  }, 'html');
	event.preventDefault();
};
function terminateSubmit()
{
	var unitId=$("#unit_Id").val();
	var discId=$("#disc_Id").val();
	var state=$("#state").val();
	$.commonRequest({
		url:"${ContextPath}/FlowActions/terminateSubmit",
		dataType:'text',
		success:function(result){
			if(result=='success'){
				alert_dialog('操作成功！');
				evalState = '5';
				jQuery("#centercollection_tb").setGridParam({
					url:"${ContextPath}/CenterDisciplineList/gatherList_gatherData?unitId="+unitId+"&discId="+discId+"&state="+state,
					}).trigger("reloadGrid"); 
				
			}else{
				alert_dialog('操作失败！');
			}
		}
	});
}
function rebootSubmit()
{
	var unitId=$("#unit_Id").val();
	var discId=$("#disc_Id").val();
	var state=$("#state").val();
	$.commonRequest({
		url:"${ContextPath}/FlowActions/rebootSubmit",
		dataType:'text',
		success:function(result){
			if(result=='success'){
				alert_dialog('重启成功！');
				evalState='2'
				jQuery("#centercollection_tb").setGridParam({
					url:"${ContextPath}/CenterDisciplineList/gatherList_gatherData?unitId="+unitId+"&discId="+discId+"&state="+state,
					}).trigger("reloadGrid"); 
				
			}else{
				alert_dialog(result);
			}
		}
	});
}
function searchList(unitId,discId,state,isEval,isReport)
{
	jQuery("#centercollection_tb").setGridParam({
		url:"${ContextPath}/CenterDisciplineList/gatherList_gatherData?"
				+"unitId="+unitId
				+"&discId="+discId
				+"&state="+state
				+"&isEval="+isEval
				+"&isReport"+isReport,
		sortorder: "asc",
		sortname:'ID',
		page:1,
		}).trigger("reloadGrid"); 
	}
$(document).ready(function(){
	evalState = "${evalState}";
	mainState();
	$( "input[type=submit], a.button , button" ).button();
	$("#search_list").click(function(){
		var unitId=$("#unit_Id").val();
   	 	var discId=$("#disc_Id").val();
    	var state=$("#state").val();
    	var isEval = $("#isEval").val();
    	var isReport = $("#isReport").val();
    	searchList(unitId,discId,state,isEval,isReport);
    
    		
    	
	});
	$("#terminateSubmit").click(function(){
		$( "#dialog-confirm" ).empty().append("<p>你确定终止学校提交吗？</p>");
		  $( "#dialog-confirm" ).dialog({
    	      height:150,
    	      buttons: {
    	        "确定": function() {
    	        	terminateSubmit();
    	        	$( this ).dialog( "close" );
    	        },
    	        "取消": function() {
    	            $( this ).dialog( "close" );
    	          }
    	        }
    	  });
		
	});
	$("#rebootSubmit").click(function(){
		$( "#dialog-confirm" ).empty().append("<p>你确定重启参评吗？</p>");
		  $( "#dialog-confirm" ).dialog({
  	      height:150,
  	      buttons: {
  	        "确定": function() {
  	        	rebootSubmit();
  	        	$( this ).dialog( "close" );
  	        },
  	        "取消": function() {
  	            $( this ).dialog( "close" );
  	          }
  	        }
  	  });
	});
	/********************  jqGrid ***********************/
			var unitId=$("#unit_Id").val();
	   	 	var discId=$("#disc_Id").val();
	    	var state=$("#state").val();
    	   $("#centercollection_tb").jqGrid({
			datatype: "json",
	        url:"${ContextPath}/CenterDisciplineList/gatherList_gatherData?unitId="+unitId+"&discId="+discId+"&state="+state,
			colNames:['ID','学校代码','学校名称','${textConfiguration.discNumber}','${textConfiguration.discName}','状态','学科采集状态','是否参评','是否需要报告','学校版本号','学科版本号','学科成果','下载',''],
			colModel:[	
						{name:'eval.ID',align:"center", width:100,hidden:true},
						{name:'eval.unitId',index:'unitId',align:"center", width:100},
						{name:'unitName',align:"center", width:200,sortable:false},
						{name:'eval.discId',index:'discId',align:"center", width:100},
						{name:'discName',index:'discName',align:"center",width:200,sortable:false},
						{name:'eval.state',align:"center", width:100,sortable:false,hidden:true},
						{name:'stateName',index:'stateName',align:"center", width:150},
						{name:'isEval',index:'IS_EVAL',align:"center", width:80},
						{name:'isReport',index:'IS_REPORT',align:"center", width:80},
						{name:'eval.unitVersionNo',index:'unitVersionNo',align:'center',width:240},
						{name:'eval.discVersionNo',index:'discVersionNo',align:'center',width:240},
						{name:'detail',index:'detail', width:100,align:"center",sortable:false},
						{name:'download',index:'download', width:150,align:"center",sortable:false},
						{name:'eval.briefId', width:150,align:"center",sortable:false,hidden:true}
						
			],
			rownumbers:true,
			height:"100%",
			autowidth:true,
			pager: '#contercollection_pager',
			rowNum:20,
			rowList:[20,30,40],
			viewrecords: true,
			sortorder: "asc",
			sortname: "ID",
		    jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
                root: "rows",  //包含实际数据的数组  
                page: "pageIndex",  //当前页  
                total: "totalPage",//总页数  
                records:"totalCount", //查询出的记录数  
                repeatitems : false      
            },
			gridComplete: function(){
				var ids = jQuery("#centercollection_tb").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					var check = "<a class='check_detail' href='#' onclick='viewDiscDetail("+ids[i]+")'>查看</a>"; 
					jQuery("#centercollection_tb").jqGrid('setRowData',ids[i],{detail:check});
					/* //报告下载
					var download = "<a class='check_detail' href='#' onclick='viewCollegeDiscipline("+ids[i]+")'>下载</a>"; 
					jQuery("#centercollection_tb").jqGrid('setRowData',ids[i],{report:download}); */
					//简况表 
					var rowData = $('#centercollection_tb').jqGrid("getRowData", ids[i]);//获取本行数据
					if(rowData['eval.briefId']!=''){
						var brief = "&nbsp;&nbsp;<a class='check_detail' href='#' onclick='downloadBrief("+ids[i]+")'>下载简况表</a>"; 
						jQuery("#centercollection_tb").jqGrid('setRowData',ids[i],{download:brief});
					}else{
						jQuery("#centercollection_tb").jqGrid('setRowData',ids[i],{download:'简况表未生成'});
					}
					
				}
				var state_eval = '';
				if(ids.lenght>0){
					for(var i = 0;i<ids.length;i++){
						var rowData = $('#schoolCol_tb').jqGrid("getRowData",ids[i]);
						if(rowData['eval.isEval']=='true'){
							state_eval = rowData['eval.state'];
						}
					}
					controlTerminateAndRebootBT(state_eval);
				}else{
					controlTerminateAndRebootBT(evalState);	
				}
			},
			caption: "学科信息汇总"
		}).navGrid('#contercollection_pager',{edit:false,add:false,del:false});
    	   
	
});
</script>