<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>申报管理
	</h3>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr class="hidden">
			<td>
				<input id="schoolProjectId" type="text" name="schoolProjectId" value="${project.project.id}" readonly/>  
			</td>
		</tr>
		<tr>
			<td>
				<span class='TextFont'>项目来源：</span>
			</td>
			<td>
				<input id="schoolProjectName" type="text" name="schoolProjectName" value="${project.project.projectName}" readonly/>
			</td>
			<td>&nbsp;&nbsp;</td>
			<td>
				<span class='TextFont'>项目状态：</span>
			</td>
			<td>
				<input id="schoolProjectState" type="text" name="schoolProjectState" value="${project.currentState}" readonly/>
			</td>
			<td>
				<input id="commitState" type="hidden" name="commitState" value="${project.project.commitState}" readonly/>
			</td>
		</tr>
	</table>
	<table class="layout_table right">
		<tr>
			<td>
				<a  id="resultPublish_btn" class="button" href="#"><span class="icon icon-commit"></span>发布结果</a>
			</td>
			<td>
				<a  id="projectClose_btn" class="button" href="#"><span class="icon icon-stop"></span>终止提交</a>
			</td>
			<td>
				<a  id="projectRestart_btn" class="button" href="#"><span class="icon icon-start"></span>重启提交</a>
			</td>
		</tr>
	</table>
</div>

<div class="table" id='unpublish_div'>
    		<table id="unpublish_tb"></table>
			<div id="unpublish_pager"></div>
</div>

<div class="toolbar inner_table_holder" style="border-top: 1px solid #9fc3de;">
	<table class="layout_table">
		<tr>
			<td>
				<a  id="return_btn" class="button" href="#">返回</a>
			</td>
		</tr>
	</table>
</div>

<div id = "oper_confirm" title = "警告">
</div>
<div id = "inputResult_div" title = "结果录入"></div>
<div id="dialog_applyItemDetail"></div>

<script type="text/javascript">

function viewProject(id)
{
	$.post('${ContextPath}/project/punit_applyItem_check/' + id, function(data){
  		$('#dialog_applyItemDetail').empty();
	  	$('#dialog_applyItemDetail').append( data );
 	  	$('#dialog_applyItemDetail').dialog({
  	  		    title:"项目详情",
  	  			height:'850',
	  			width:'900',
  	  			position:'center',
  	  			modal:true,
  	  			draggable:true,
  	  		    hide:'fade',
  	  			show:'fade',
  	  		    autoOpen:true,
  	  		    buttons:{  
  	 	            "关闭":function(){
  	 	            	$("#dialog_applyItemDetail").dialog("close");
  	 	            	$("#dialog_applyItemDetail").dialog("destroy");
  	   					$("#dialog_applyItemDetail").hide();
  	 	            }
  	  		    }
 	  }); 
  	 	}, 'html');
    event.preventDefault();
}

function inputResult(id)
{
	var rowData = $('#unpublish_tb').jqGrid("getRowData", id);
	var itemId = rowData['id'];
	$.post('${ContextPath}/project/punit_inputResult',function(data){
		$('#inputResult_div').empty();
		$('#inputResult_div').append(data);
		$('#inputResult_div').dialog({
  		    height:'500',
  			width:'600',
  			position:'center',
  			modal:true,
  			draggable:true,
  		    hide:'fade',
  			show:'fade',
  		    autoOpen:true,
  		    buttons:{  
 	            "提交":function(){
 	            	$.post('${ContextPath}/project/punit_saveResult',$("#fm_result").serialize()+"&itemId="+itemId,function(data){
 	            		if(data) alert_dialog("提交成功！");
 	            		else alert_dialog("提交失败！");
 	            		$('#unpublish_tb').trigger("reloadGrid");
 	            	});
 	            	$("#inputResult_div").dialog("close");
 	            },
 	           "取消":function(){
 	            	$("#inputResult_div").dialog("close");
 	            }
  		    }
  }); 
});
	
}

function updateResult(id)
{
	var rowData = $('#unpublish_tb').jqGrid("getRowData", id);
	var itemId = rowData['id'];
	$.post('${ContextPath}/project/punit_editResult',{itemId:itemId},function(data){
		$('#inputResult_div').empty();
		$('#inputResult_div').append(data);
		$('#inputResult_div').dialog({
  		    height:'500',
  			width:'600',
  			position:'center',
  			modal:true,
  			draggable:true,
  		    hide:'fade',
  			show:'fade',
  		    autoOpen:true,
  		    buttons:{  
 	            "提交":function(){
 	            	$.post('${ContextPath}/project/punit_updateResult',$("#fm_result").serialize()+"&itemId="+itemId,function(data){
 	            		if(data) alert_dialog("提交成功！");
 	            		else alert_dialog("提交失败！");
 	            		$('#unpublish_tb').trigger("reloadGrid");
 	            	});
 	            	$("#inputResult_div").dialog("close");
 	            },
 	           "取消":function(){
 	            	$("#inputResult_div").dialog("close");
 	            }
  		    }
  }); 
	 }); 
}

function operControl()
{
	var state=$('#commitState').val();
	if(state=='否'){
		$('#projectClose_btn').hide();
		$('#projectRestart_btn').show();
	}
	else{
		$('#projectRestart_btn').hide();
		$('#projectClose_btn').show();
	}
}

function resultPublish(){
	$('#oper_confirm').empty().append("<p>你确定要发布评审结果吗？</p>");
	$('#oper_confirm').dialog({
  			position:'center',
  			modal:true,
  			draggable:true,
  		    hide:'fade',
  			show:'fade',
  		    autoOpen:true,
  		    buttons:{  
				"确定":function(){
					var projectId = $('#schoolProjectId').val();
					$.post("${ContextPath}/project/punit_resultPublish",{projectId:projectId},function(publishResult){
						if(publishResult)
						{
							alert_dialog("操作成功!");
							$.post("${ContextPath}/project/punit",function(data){
								$("#content").empty();
								$("#content").append(data);
							});
						}
						else{
							alert_dialog("操作失败!");
						}
					});
					$("#oper_confirm").dialog("close");
				},
 	           "取消":function(){
 	            	$("#oper_confirm").dialog("close");
 	            }
  		    }
	});
}

$(document).ready(function(){
	//console.log($('#schoolProjectId').val());
	$( 'input[type=submit], a.button , button' ).button();
	
	operControl();
	
	$('#projectClose_btn').click(function(){
		
		var dialogParent = $('#oper_confirm').parent();  
		var dialogOwn = $('#oper_confirm').clone();
		$('#oper_confirm').empty().append("<p>你确定要关闭该项目吗？</p>");
		$('#oper_confirm').dialog({
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  			show:'fade',
	  		    autoOpen:true,
	  		  	close:function(){
	  		    	dialogOwn.appendTo(dialogParent);
	  		   		$(this).dialog("destroy").remove();
	  		    },
	  		    buttons:{  
					"确定":function(){
						var projectId = $('#schoolProjectId').val();
						$.post("${ContextPath}/project/punit_closeProject",{projectId:projectId},function(data){
							if(data)
							{
								alert_dialog("操作成功!");
								$('#projectClose_btn').hide();
								$('#projectRestart_btn').show();
							}
							else{
								alert_dialog("操作失败!");
							}
						});
						$("#oper_confirm").dialog("close");
					},
		 			"取消":function(){
          				$("#oper_confirm").dialog("close");
        		  }
	  		    }
		});
		
	});
	
	$('#projectRestart_btn').click(function(){
		
		var dialogParent = $('#oper_confirm').parent();  
		var dialogOwn = $('#oper_confirm').clone();
		$('#oper_confirm').empty().append("<p>你确定要重新启动该项目吗？</p>");
		$('#oper_confirm').dialog({
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  			show:'fade',
	  		    autoOpen:true,
	  		  	close:function(){
	  		    	dialogOwn.appendTo(dialogParent);
	  		   		$(this).dialog("destroy").remove();
	  		    },
	  		    buttons:{  
					"确定":function(){
						var projectId = $('#schoolProjectId').val();
						$.post("${ContextPath}/project/punit_restartProject",{projectId:projectId},function(data){
							if(data)
							{
								alert_dialog("操作成功!");
								$('#projectRestart_btn').hide();
								$('#projectClose_btn').show();
							}
							else{
								alert_dialog("操作失败!");
							}
						});
						$("#oper_confirm").dialog("close");
					},
	 	           "取消":function(){
	 	            	$("#oper_confirm").dialog("close");
	 	            }
	  		    }
		});
		
	});
	$('#resultPublish_btn').click(function(){
		var ids = jQuery("#unpublish_tb").jqGrid('getDataIDs');
		if(ids.length==0) alert_dialog("没有可发布的数据项！");
		else{
			var logicCheck = true;
			for( var i=0;i<ids.length;i++){
				var rowData = jQuery("#unpublish_tb").jqGrid("getRowData",ids[i]);
				if ( rowData['currentState'] == 2){
					logicCheck = false;
					break;
				}
			}
			if(logicCheck) resultPublish();
			else {
				alert_dialog("有项目还未录入结果，请确保全部录入之后再发布结果！");
			}
		}
	});
	//申报中的项目
	$('#unpublish_tb').jqGrid({
     	url:'${ContextPath}/project/punit_viewApplyItems/'+$('#schoolProjectId').val(),
		datatype: 'json',
		colNames:['项目Id','项目名称','学科编号','负责人','申请经费(万元)','项目详情','状态','操作'],
		colModel:[
          {name:'id',index:'Id',width:30,align:'center',hidden:true},
          {name:'itemName',index:'item_name',width:60,align:'center'},
          {name:'discId',index:'discId',width:60,align:'center'},
          {name:'principalName',index:'principal',width:60,align:'center'},
          {name:'funds',index:'funds',width:60,align:'center'},
          {name:'detail',index:'detail',width:60,align:'center'},
          {name:'currentState',index:'current_state',width:60,align:'center',hidden:true},
          {name:'operate',index:'operate',width:60,align:'center'}
		],
			height:'100%',
			autowidth:true,
			pager: '#unpublish_pager',
			rowNum:20,
			rownumbers:true,
			rowList:[20,30,40],
			viewrecords: true,
			sortname: 'item_name',
			sortorder: 'asc',
			caption: "申报中的项目",
	    gridComplete: function(){
	    	var ids = jQuery("#unpublish_tb").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var rowData = $('#unpublish_tb').jqGrid("getRowData", ids[i]);
				var details = "<a href='#' style='text-decoration:none' onclick=viewProject('"+ids[i]+"')>进入查看</a>";
				var  operates;
				if( rowData['currentState']==2 ) operates = "<a href='#' style='text-decoration:none' onclick=inputResult('"+ids[i]+"')>录入结果</a>";
				else operates = "<a href='#' style='text-decoration:none' onclick=updateResult('"+ids[i]+"')>已录入</a>";
				jQuery("#unpublish_tb").jqGrid('setRowData',ids[i],{operate :operates});
				jQuery("#unpublish_tb").jqGrid('setRowData',ids[i],{detail :details});
			}
	    },
		});
	jQuery("#unpublish_tb").jqGrid('navGrid','#unpublish_pager',{edit:false,add:false,del:false,search:true});
	
	$('#return_btn').click(function(){
 		$.post("${ContextPath}/project/punit", function(data){
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		 	  }, 'html');
	});
}); 
</script>