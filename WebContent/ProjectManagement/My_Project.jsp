<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <style type='text/css'>
       .redStyle{
       font-family: Lucida Grande,Lucida Sans,Arial,sans-serif;
       font-size: 1.1em;
       font-weight: bold;
       color: red;
       text-align: center;
       }
       
        .orangeStyle{
       font-family: Lucida Grande,Lucida Sans,Arial,sans-serif;
       font-size: 1.1em;
       font-weight: bold;
       color: orange;
       text-align: center;
       }
       
       .greenStyle{
       font-family: Lucida Grande,Lucida Sans,Arial,sans-serif;
       font-size: 1.1em;
       font-weight: bold;
       color: green;
       text-align: center;
       }
</style>
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>我的项目（显示负责人名下的所有项目）
	</h3>
</div>
<div id="project_table" class="tabs">
		<ul id="tab_status">
			<li>
				<a id="show_unpublish"   class="objectLink" href="#unpublish_div">  
					未申报
				</a>
			</li>
			<li>
				<a id="show_notapproval" class="objectLink" href="#notapproval_div">
					未通过
				</a>
			</li>
			<li>
				<a id="show_inprogress"  class="objectLink" href="#inprogress_div"> 
					进行中
				</a>
			</li>
			
			<li>
				<a id="show_finished" class="objectLink" href="#finished_div">
					已结束
				</a>
			</li>
		</ul>
		<div class="table" id='unpublish_div'>
    		<table id="unpublish_tb"></table>
			<div id="unpublish_pager"></div>
		</div>
		<div class="table" id='notapproval_div' >
    		<table id="notapproval_tb"></table>
			<div id="notapproval_pager"></div>
		</div>
		<div class="table" id='inprogress_div' >
    		<table id="inprogress_tb"></table>
			<div id="inprogress_pager"></div>
		</div>
		<div class="table" id='finished_div' >
    		<table id="finished_tb"></table>
			<div id="finished_pager"></div>
		</div>
</div>
<div id="dialog-confirm" title="警告"></div>
<div class="process_dialog">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 系统正在处理，请稍候……
</div>
<div id="dialog_applyItemDetail"></div>
<div id="dialog_checkResult"></div>
<div id="oper_confirm" title="警告"></div>
<script type="text/javascript">
	function viewProject(id)
	{
		$.post('${ContextPath}/project/pproject_applyItem_check/' + id, function(data){
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
	  	 	            }
	  	  		    }
	 	  }); 
	  	 	}, 'html');
	    event.preventDefault();
	}
	$("#dialog-confirm").hide();
	$(".process_dialog").hide();
	function applyEdit(applyItemId)
	{
		$.post("${ContextPath}/project/papply_edit_applyItem/" + applyItemId, function(data){
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		 	  }, 'html');
	}
	function progressUpdate(id)
	{
		$.post("${ContextPath}/project/pproject_progress",{item_id:id},function(data){
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		 	  }, 'html');
	}
	function checkResult(id)
	{
		$.post("${ContextPath}/project/pproject_checkResult",{item_id:id},function(data){
			  $( "#dialog_checkResult" ).empty();
			  $( "#dialog_checkResult" ).append( data );
			  $('#dialog_checkResult').dialog({
	  	  		    title:"校方意见",
	  	  		    height:'500',
	  	  			width:'600',
	  	  			position:'center',
	  	  			modal:true,
	  	  			draggable:true,
	  	  		    hide:'fade',
	  	  			show:'fade',
	  	  		    autoOpen:true,
	  	  		    buttons:{  
	  	 	            "关闭":function(){
	  	 	            	$("#dialog_checkResult").dialog("close");
	  	 	            }
	  	  		    }
	 	  }); 
		 	  }, 'html');
	}
	function checkProgress(id)
	{
		$.post("${ContextPath}/project/pproject_checkProgress",{item_id:id},function(data){
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		 	  }, 'html');
	}
	function deleteItem(id)
	{
		$('#oper_confirm').empty().append("<p>你确定要删除吗?</p>");
		$('#oper_confirm').dialog({
			buttons:{  
	 	            "确定":function(){
	 	            	$.post("${ContextPath}/project/pproject_deleteApplyItem/"+id,function(data){
	 	      			 if(data)
	 	      			 {
	 	      				 alert_dialog("操作成功!");
	 	      				 $('#unpublish_tb').trigger("reloadGrid");
	 	      			 }
	 	      			 else alert_dialog("操作失败!");
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
		$( 'input[type=submit], a.button , button' ).button();
		$("#dialog_applyItemDetail").hide();
		//未申报的项目
		$('#unpublish_tb').jqGrid({
			url : '${ContextPath}/project/pproject_viewUnpublishApplyItems/',
         	datatype: 'json',
         	//data:unpub_data,
 			colNames:['项目编号','projectId', '项目名称','申请经费（万元）','负责人','项目来源','截止日期','项目详情','操作'],
 			//data:unpub_data,
 			colModel:[
              	{name:'id',		index:'id', width:125,	align:"center",hidden:true},
 		   		{name:'projectId', index:"projectId",hidden:true},
              	{name:'itemName', index:'ITEM_NAME', width:250,	align:"center"},
 		  	 	{name:'funds', index:'funds', width:150,	align:"center"},
 		   		{name:'principalName',	 index:'',  width:150, 	align:"center"},
 		  	 	{name:'projectName',	 index:'',  width:150, 	align:"center"},
 		   		{name:'projectEndDate',	 index:'end_date',  width:150, 	align:"center"},//,formatter:"date",formatoptions: {srcformat:'u',newformat:'Y-m-d'}},
 		   		{name:'detail',index:'detail',width:100,align:'center'},
            	{name:'operate',index:'operate',width:150,align:'center'}
 			],
 			height:'100%',
 			autowidth:true,
 			rownumbers:true,
 			pager: '#unpublish_pager',
 			rowNum:20,
 			rowList:[20,30,40],
 			viewrecords: true,
 			sortname: 'project_id',
 			sortorder: 'asc',
 			caption: "未申报的项目",
		    gridComplete: function(){
		    	var ids = jQuery("#unpublish_tb").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					var rowId = ids[i];
					var rowdata=jQuery("#achv_list").jqGrid('getRowData',rowId);
				    var projectId = rowdata.projectId; 
					var details = "<a href='#' style='text-decoration:none' onclick='applyEdit(\"" + ids[i] + "\")'>编辑</a>";
					var operates = "<a href='#' style='text-decoration:none' onclick='commit(\""+ids[i]+"\")'>提交至学校</a>"
									+" | "+"<a href='#' style='text-decoration:none' onclick='deleteItem(\""+ids[i]+"\")'>删除</a>"; 
					jQuery("#unpublish_tb").jqGrid('setRowData',ids[i],{operate :operates});
					jQuery("#unpublish_tb").jqGrid('setRowData',ids[i],{detail :details});
				
				}
		    },
 		}).navGrid('unpublish_tb',{edit:false,add:false,del:false,search:true});
		
		$('#notapproval_tb').jqGrid({
         	datatype: 'local',
         	//data:unpub_data,
 			colNames:['项目编号','projectId', '项目名称','申请经费（人民币）','负责人','项目详情','操作'],
 			//data:unpub_data,
 			colModel:[
              	{name:'id',					index:'id', 		width:125,	align:"center",hidden:true},
 		   		{name:'schoolProject.id', 	index:"projectId",	hidden:true},
              	{name:'itemName', 			index:'ITEM_NAME', 	width:300,	align:"center"},
 		  	 	{name:'funds', 				index:'funds', 		width:150,	align:"center"},
 		   		{name:'principalName',	 	index:'',  			width:150, 	align:"center"},		
 		   		{name:'detail',				index:'detail',		width:150,	align:'center'},
            	{name:'operate',			index:'operate',	width:150,	align:'center'}
 			],
 			height:'100%',
 			autowidth:true,
 			rownumbers:true,
 			pager: '#notapproval_pager',
 			rowNum:20,
 			rowList:[20,30,40],
 			viewrecords: true,
 			sortname: 'ITEM_NAME',
 			sortorder: 'asc',
 			caption: "未通过的项目",
		    gridComplete: function(){
		    	var ids = jQuery("#notapproval_tb").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					var rowData = jQuery("#notapproval_tb").jqGrid('getRowData',ids[i]);
					var itemId = rowData['id'];
					var details = "<a href='#' style='text-decoration:none' onclick='applyEdit(\"" + ids[i] + "\")'>编辑</a>"+" | "+
						"<a href='#' style='text-decoration:none' onclick='viewProject(\""+ids[i]+"\")'>查看</a>";
					var operates = "<a href='#' style='text-decoration:none' onclick='commit(\""+ids[i]+"\")'>重新提交</a>"; 
					jQuery("#notapproval_tb").jqGrid('setRowData',ids[i],{operate :operates});
					jQuery("#notapproval_tb").jqGrid('setRowData',ids[i],{detail :details});
				}
		    },
 		}).navGrid('notapproval_tb',{edit:false,add:false,del:false,search:true}); 
 	  

 	    //已立项的项目
		$('#inprogress_tb').jqGrid({
         	datatype: 'local',
         	colNames:['项目编号','项目名称','项目来源','负责人','申请经费（万元）','到账经费（万元）','账户余额（万元）','项目状态','操作'],
 			//data:inprogress_data,
 			colModel:[
 				{name:'passItem.id',	index:'id',width:30,align:"center", hidden : true},
 		        {name:'itemName',index:'item_name',width:70,align:'center'},
 		        {name:'projectName',	index:'project_name',width:60,align:'center'},
 		        {name:'principalName',index:'member_name',width:40,align:'center'},
 		        {name:'applyFunds',index:'funds',width:40,align:'center'},
 		        {name:'providedFunds',index:'providedFunds',width:40,align:'center'},
 		        {name:'restFunds',index:'restFunds',width:40,align:'center'},
 		        {name:'projectStatus',width:40,align:'center'},
 		       	{name:'progress',index:'progress',width:60,align:'center'},
 			],
 			height:'100%',
 			autowidth:true,
 			rownumbers:true,
 			pager: '#inprogress_pager',
 			rowNum:20,
 			rowList:[20,30,40],
 			viewrecords: true,
 			sortname: 'id',
 			sortorder: 'desc',
 			caption: "已立项的项目",
		    gridComplete: function(){
		    	
		    	var ids = jQuery("#inprogress_tb").jqGrid('getDataIDs');
		    	
				for(var i=0;i < ids.length;i++){
					
					var rowData = jQuery("#inprogress_tb").jqGrid('getRowData',ids[i]);
					/* var state = rowData['projectStatus'];
					alert(state);
					if(state==0) 
					{
						jQuery("#inprogress_tb").jqGrid('delRowData',ids[i]);
					}
					var currentState;
				    if( state == 4 || state == 6 ) currentState = "进行中";
					else if ( state == 5 ) currentState = "中期检查中";
					else if ( state == 7 ) currentState = "结题检查中";
					else currentState = state;
					jQuery("#inprogress_tb").jqGrid('setRowData',ids[i],{'schoolProject.currentState' : currentState});
					 */
					 var progress = "<a href='#' style='text-decoration:none' onclick='progressUpdate(\""+rowData["passItem.id"]+"\")'>进度维护</a>"
					 jQuery("#inprogress_tb").jqGrid('setRowData',ids[i],{progress :progress});
					
				}
		    }
 		}).navGrid('#inprogress_tb',{edit:false,add:false,del:false,search:true});
 	    
 	    
		// 结束项目
		$('#finished_tb').jqGrid({
			//url : '${ContextPath}/project/pproject_showPassItems/',
         	//datatype: 'json',
         	datatype: 'local',
         	colNames:['项目编号','项目名称','项目来源','负责人','申请经费（万元）','到账经费（万元）','账户余额（万元）','项目状态','操作'],
 			//data:inprogress_data,
 			colModel:[
 				{name:'passItem.id',	index:'id',width:30,align:"center", hidden : true},
 		        {name:'itemName',		index:'itemName',width:70,align:'center'},
 		        {name:'projectName',	index:'project_name',width:60,align:'center'},
 		        {name:'principalName',	index:'principal.name',width:40,align:'center'},
 		        {name:'applyFunds',index:'funds',width:40,align:'center'},
 		        {name:'providedFunds',index:'discipline_id',width:40,align:'center'},
 		        {name:'restFunds',index:'discipline_id',width:40,align:'center'},
 		        {name:'projectStatus',index:'currentState',width:40,align:'center'},
 		       	{name:'progress',index:'progress',width:60,align:'center'},
 			],
 			height:'100%',
 			autowidth:true,
 			rownumbers:true,
 			pager: '#finished_pager',
 			rowNum:20,
 			rowList:[20,30,40],
 			viewrecords: true,
 			sortname: 'project_id',
 			sortorder: 'asc',
 			caption: "已结束的项目",
		    gridComplete: function(){
		    	var ids = jQuery("#finished_tb").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					var rowData = jQuery("#finished_tb").jqGrid('getRowData',ids[i]);
					/* var state = rowData['schoolProject.currentState'];
					if( state != 0 && state!="已结束") jQuery("#finished_tb").jqGrid('delRowData',ids[i]);
					var currentState;
					if( state == 4 || state==6 ) currentState = "进行中";
					else if ( state == 5 ) currentState = "中期检查中";
					else if ( state == 7 ) currentState = "结题检查中";
					else currentState = "已结束";
					jQuery("#finished_tb").jqGrid('setRowData',ids[i],{'schoolProject.currentState' : currentState});
					 */
					var progress = "<a href='#' style='text-decoration:none' onclick='checkProgress(\""+rowData["passItem.id"]+"\")'>进度查看</a>"
					+" | "+ "<a href='#' style='text-decoration:none' onclick='checkResult(\""+rowData["passItem.id"]+"\")'>结果查看</a>";
					jQuery("#finished_tb").jqGrid('setRowData',ids[i],{progress :progress});
					
				}
				
		    },
 		}).navGrid('finished_tb',{edit:false,add:false,del:false,search:true});
		
 	    
		jQuery("#inprogress_tb").jqGrid('setGroupHeaders', {
			useColSpanStyle: false, //没有表头的列是否与表头列位置的空单元格合并
			groupHeaders:[
				//合并 startColumnName(开始列),以sid列开始; numberOfColumns(合并几列),合并2列; titleText(合并后父列名),合并后父列名为All Student
				{startColumnName: 'funding_apply', numberOfColumns: 3, titleText: '经费明细(人民币/元)'}
			]	
		});

	$(".tabs").tabs({
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
	
	$("#show_inprogress").click(function() {
		$("#inprogress_tb").setGridParam({
			url : '${ContextPath}/project/pproject_showPassItems', datatype:'json'}).trigger("reloadGrid");
	});
	
	$("#show_notapproval").click(function() {
		$("#notapproval_tb").setGridParam({
			url : '${ContextPath}/project/pproject_showRejectApplyItems', datatype:'json'}).trigger("reloadGrid");
	});
	
	$("#show_finished").click(function() {
		$("#finished_tb").setGridParam({
			url : '${ContextPath}/project/pproject_showFinishedItems/', datatype:'json'}).trigger("reloadGrid");
	});
	
}); 
	function commit(id) {
		//清空警告框内文本并添加新的警告文本
	    $( "#dialog-confirm" ).empty().append("<p>项目将被提交至学校，是否确定？提交成功后，项目会从未申报项目表中移除。</p>");
	    $( "#dialog-confirm" ).dialog({
 	        height:150,
 	        buttons: {
 	            "确定": function() {
 	            	$( this ).dialog( "close" );
 	  				url = '${ContextPath}/project/pproject_commitApplyItemToSchool/' + id;
     	  			$(".process_dialog").show();
   					$('.process_dialog').dialog({
   						position : 'center',
   						modal:true,
   						autoOpen : true,
   					});
  					$.post(url, function(data) {
  						if (data == "success") {
  							$("#unpublish_tb").setGridParam({
  								url : '${ContextPath}/project/pproject_viewUnpublishApplyItems/', datatype:'json'}).trigger("reloadGrid");
  							$("#notapproval_tb").setGridParam({
  								url : '${ContextPath}/project/pproject_showRejectApplyItems/', datatype:'json'}).trigger("reloadGrid");
  							$(".process_dialog").dialog("destroy");
							$(".process_dialog").hide();
							alert_dialog("提交成功！");
  						} else if(data == "failure"){
  							$("#unpublish_tb").setGridParam({
  								url : '${ContextPath}/project/pproject_viewUnpublishApplyItems/', datatype:'json'}).trigger("reloadGrid");
  							$(".process_dialog").dialog("destroy");
							$(".process_dialog").hide();
							alert_dialog("提交失败！");
  						}
  						else {
  							alert_dialog(data);
  							$(".process_dialog").dialog("destroy");
							$(".process_dialog").hide();
  						}
  					});
     	        },
 	        	"取消": function() {
 	           		 $( this ).dialog( "close" );
 	          	}
 	        }
  	  });
	}
	
</script>