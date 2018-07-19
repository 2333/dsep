<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="con_header inner_table_holder">
	<table class="layout_table">
		<tr>
			<td>
				<span class="icon icon-web"></span>
				<span class="TextFont">公示管理</span>
			</td>
		</tr>
	</table>
</div>
<div class="selectbar">
	<table>
	     <tr>
	         <td>
	         	<a class="button" href="#" id="btn_closeCurrentPublicity">
	         		<span class="icon icon-stop"></span>关闭当前公示</a>
	   			<a class="button" href="#" id="btn_reopenCurrentPublicity">
	         		<span class="icon icon-stop"></span>重新开启最近关闭公示</a>
	        </td>
	     </tr>
    </table>
</div>
<div class="table">
	<table id="publicityRound_list"></table>
	<div id="pager"></div>
</div>

<div id="dialog-confirm" title="警告">
</div>

<script src="${ContextPath}/js/edit_jqgrid.js"></script>
<script type="text/javascript">

	var jqTable = "#publicityRound_list";
	
	$(document).ready(function(){
		
		
		$("input[type=submit], a.button , button" ).button();
		$("#publicityRound_list").jqGrid({
			url:'${ContextPath}/publicity/viewPublicityManagement_getAllPublicityRound',
			editurl:'${ContextPath}/publicity/viewPublicityManagement_editPublicity',
			datatype: "json",
			colNames:['ID','批次名称','开始时间','预期结束时间','实际结束时间','备注','状态','编辑'],
			colModel:[
			    {name:'roundId',index:'roundId',align:'center',editable:true,hidden:true},
			    {name:'publicRoundName',index:'publicRoundName',align:'center',width:120,editable:false},
				{name:'beginTime',index:'beginTime',align:'center',width:40,editable:false,sorttype:'data'},
				{name:'endTime',index:'endTime', width:40,align:'center',sorttype:'data'},
				{name:'actualEndTime',index:'actualEndTime', width:50, sorttype:"date",align:'center'},
				{name:'remark',index:'remark', width:150,align:"center",editable:true},
				{name:'openStatus',index:'openStatus',width:20,align:'center'},
				{name:'edit',index:'edit',width:30,align:'center',sortable:false},
			],
			jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
	            root: "rows",  //包含实际数据的数组  
	            page: "pageIndex",  //当前页  
	            total: "totalPage",//总页数  
	            records:"totalCount", //查询出的记录数  
	            repeatitems : false,
	        },
	        height:"100%",
			width:700,
			sortable:true,
			rownumbers:true,
			sortname:'beginTime',
			rownumWidth:15,
			pager: '#pager',
			rowNum:10,
			multiselect:false,
			rowList:[10,20,30],
			autowidth:true,
			viewrecords: true,
			sortorder: "desc",
			caption:"公示批次",
			gridComplete:function(){
				var ids = $(jqTable).jqGrid('getDataIDs');
				for(var i=0; i < ids.length; i++){
					/* var list_name = "#tb_objection_list"; */
					//editRow函数引自edit_jqgrid.js文件
					var editRow = "<a href='#' id='editGrid'  onclick=\"editRow('"+jqTable+"','"+ids[i]+"');\">编辑</a>";
					$(jqTable).setRowData(ids[i],{edit:editRow});
				}
				if( isRoundAllClosed()){
					$("#btn_closeCurrentPublicity").hide();
					$("#btn_reopenCurrentPublicity").show();
				}
				else{
					$("#btn_closeCurrentPublicity").show();
					$("#btn_reopenCurrentPublicity").hide();
				}
			}
		}).navGrid('#pager',{edit:false,add:false,del:false});
	});
	
	

	
	//是否所有的公示批次均已关闭
	function isRoundAllClosed(){
		var statusArray = $("#publicityRound_list").jqGrid('getCol','openStatus','false');
		/* var statusArray = ['sf','czc','sdf']; */
		if( $.inArray('进行中',statusArray) >= 0){
			return false;
		}
		else{
			return true;
		}
	}
	
	//重新开启最近关闭公示批次
	$("#btn_reopenCurrentPublicity").click(function(){
		if( isRoundAllClosed()){
			$("#dialog-confirm").empty().append("<p>确认重新开启吗？</p>");
			$("#dialog-confirm").dialog({
	     	      height:150,
	     	      buttons: {
	     	        "确定":function(){
	     	        	$.ajax({
	     					url:'${ContextPath}/publicity/viewPublicityManagement_reopenCurrentRound',
	     					type:"POST",
	     					dataType:"json",
	     					success:function(data){
	     						if( data == true){
	     							alert_dialog('开启成功');	
	     							$("#publicityRound_list").setGridParam({url:'${ContextPath}/publicity/viewPublicityManagement_getAllPublicityRound'}).trigger("reloadGrid");
	     						}
	     						else{
	     							alert_dialog('开启失败');
	     						}
	     					}	
	     				});
	     	        	$(this).dialog("close");
					},
					"取消":function(){
						$(this).dialog("close");
					}
	   	      	}
			});
		}
		else{
			alert_dialog('尚有未关闭的批次');
		}
	});
	
	
	//关闭当前公示批次
	$("#btn_closeCurrentPublicity").click(function(){
		if( !isRoundAllClosed()){
			$("#dialog-confirm").empty().append("<p>批次关闭后学校将无法提交新的异议，确认关闭吗？</p>");
			$("#dialog-confirm").dialog({
	     	      height:150,
	     	      buttons: {
	     	        "确定":function(){
	     	        	$.ajax({
	     					url:'${ContextPath}/publicity/viewPublicityManagement_closeCurrentRound',
	     					type:"POST",
	     					dataType:"json",
	     					success:function(data){
	     						if( data == true){
	     							alert_dialog('关闭成功');	
	     							$("#publicityRound_list").setGridParam({url:'${ContextPath}/publicity/viewPublicityManagement_getAllPublicityRound'}).trigger("reloadGrid");
	     						}
	     						else{
	     							alert_dialog('关闭失败');
	     						}
	     					}	
	     				});
	     	        	$(this).dialog("close");
					},
					"取消":function(){
						$(this).dialog("close");
					}
	   	      	}
			});
		}
		else{
			alert_dialog('没有开启的批次');
		}
	});
</script>
