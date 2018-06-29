<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header">
	<h3>
		<span class="icon icon-user" ></span>
		<span class='TextFont' id="page_title">执行管理</span>
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
		</tr>
	</table>
	<table class="layout_table right">
		<tr>
			<td>
				<a onclick='showEmailConfigDialog()' id='startMedium_btn' class='button' href='#' ><span class='icon icon-commit'></span>开展中期检查</a>
				<a  id='closeMedium_btn' class='button' href='#' ><span class='icon icon-stop'></span>关闭中期检查</a>
				<a onclick='showEmailConfigDialog()' id='startFinal_btn' class='button' href='#' ><span class='icon icon-commit'></span>开展结题检查</a>
				<a  id='closeFinal_btn' class='button' href='#' ><span class='icon icon-stop'></span>关闭结题检查</a>
				<a  id='resultPublish_btn' class='button' href='#' ><span class='icon icon-commit'></span>发布验收结果</a>
			</td>
		</tr>
	</table>
</div>
		
		<div class="table" id='inprogress_div' >
    		<table id="inprogress_tb"></table>
			<div id="inprogress_pager"></div>
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
<!-- <div id="dialog-confirm" title="警告"></div> -->
<div id='dialog_passItemDetail'></div>
<div id="dialog_showEmailConfig"></div>
<div id='inputResult_div' title = "结果录入"></div>
<div id = "oper_confirm" title = "警告">
</div>
<script type="text/javascript">

	function inputResult(id)
	{
		var rowData = $('#inprogress_tb').jqGrid("getRowData", id);
		var itemId = rowData['id'];
		
		$.post('${ContextPath}/project/punit_inputFinalResult',function(data){
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
	 	            	$.post('${ContextPath}/project/punit_saveFinalResult',$("#fm_result").serialize()+"&itemId="+itemId,function(data){
	 	            		if(data) alert_dialog("提交成功！");
	 	            		else alert_dialog("提交失败！");
	 	            		$('#inprogress_tb').trigger("reloadGrid");
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
		var rowData = $('#inprogress_tb').jqGrid("getRowData", id);
		var itemId = rowData['id'];
		$.post('${ContextPath}/project/punit_editFinalResult',{itemId:itemId},function(data){
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
	 	            	$.post('${ContextPath}/project/punit_updateFinalResult',$("#fm_result").serialize()+"&itemId="+itemId,function(data){
	 	            		if(data) alert_dialog("提交成功！");
	 	            		else alert_dialog("提交失败！");
	 	            		$('#inprogress_tb').trigger("reloadGrid");
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
	
	function checkPassItem(id)
	{
		var rowData = jQuery("#inprogress_tb").jqGrid('getRowData',id);
		var itemId = rowData['passItem.id'];
		$.post('${ContextPath}/project/punit_passItem_check/' + itemId, function(data){
	  		$('#dialog_passItemDetail').empty();
		  	$('#dialog_passItemDetail').append( data );
	 	  	$('#dialog_passItemDetail').dialog({
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
	  	 	            	$("#dialog_passItemDetail").dialog("close");
	  	 	            }
	  	  		    }
	 	  }); 
	  	 	}, 'html');
	    event.preventDefault();
	}
	
	function checkProgress(id)
	{
		var rowData = $('#inprogress_tb').jqGrid("getRowData", id);
 		var itemId = rowData['passItem.id'];
 		//alert(itemId);
 		$.post("${ContextPath}/project/punit_checkProgress",{item_id:itemId}, function(data){
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		 	  }, 'html');
	}
	function operControl()
	{
		var currentState = ${project.project.currentState};
		if( currentState == 4 )
		{
			$('#startMedium_btn').show();
			$('#closeMedium_btn').hide();
			$('#startFinal_btn').hide();
			$('#closeFinal_btn').hide();	
			$('#resultPublish_btn').hide();
		}
		else if( currentState == 5 )
		{
			$('#startMedium_btn').hide();
			$('#closeMedium_btn').show();
			$('#startFinal_btn').hide();
			$('#closeFinal_btn').hide();	
			$('#resultPublish_btn').hide();
		}
		else if( currentState == 6 )
		{
			$('#startMedium_btn').hide();
			$('#closeMedium_btn').hide();
			$('#startFinal_btn').show();
			$('#closeFinal_btn').hide();	
			$('#resultPublish_btn').hide();
		}	
		else if( currentState == 7 )
		{
			$('#startMedium_btn').hide();
			$('#closeMedium_btn').hide();
			$('#startFinal_btn').hide();
			$('#closeFinal_btn').show();
			$('#resultPublish_btn').hide();
		}
		else if( currentState == 8 )
		{
			$('#startMedium_btn').hide();
			$('#closeMedium_btn').hide();
			$('#startFinal_btn').hide();
			$('#closeFinal_btn').hide();	
			$('#resultPublish_btn').show();
		}
		else 
		{
			$('#startMedium_btn').hide();
			$('#closeMedium_btn').hide();
			$('#startFinal_btn').hide();
			$('#closeFinal_btn').hide();	
			$('#resultPublish_btn').hide();
			$('#page_title').text("查看结果");
		}
	}
	
	function resultPublish(){
		$('#oper_confirm').empty().append("<p>你确定要发布验收结果吗？</p>");
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
						$.post("${ContextPath}/project/punit_finalResultPublish",{projectId:projectId},function(publishResult){
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
		$( 'input[type=submit], a.button , button' ).button();
		
		// 操作控制
		operControl();
		
		
		$('#closeMedium_btn').click(function(){
			var projectId = $('#schoolProjectId').val();
			$.post('${ContextPath}/project/punit_closeMedium',{projectId:projectId},function(data){
				if(data) 
				{
					alert_dialog("操作成功!");
					$('#schoolProjectState').val("中期检查结束");
					$('#startMedium_btn').hide();
					$('#closeMedium_btn').hide();
					$('#startFinal_btn').show();
					$('#closeFinal_btn').hide();
					$('#resultPublish_btn').hide();
				}
				else alert_dialog("操作失败!");
			});	
		});
		
		$('#closeFinal_btn').click(function(){
			var projectId = $('#schoolProjectId').val();
			$.post('${ContextPath}/project/punit_closeFinal',{projectId:projectId},function(data){
				if(data) 
				{
					alert_dialog("操作成功!");
					$('#schoolProjectState').val("结果发布");
					jQuery("#inprogress_tb").trigger("reloadGrid");
					$('#startMedium_btn').hide();
					$('#closeMedium_btn').hide();
					$('#startFinal_btn').hide();
					$('#closeFinal_btn').hide();	
					$('#resultPublish_btn').show();
				}
				else alert_dialog("操作失败!");
			});	
		});
		
		$('#resultPublish_btn').click(function(){
			var ids = jQuery("#inprogress_tb").jqGrid('getDataIDs');
			if(ids.length==0) alert_dialog("没有可发布的数据项！");
			else{
				var logicCheck = true;
				for( var i=0;i<ids.length;i++){
					var rowData = jQuery("#inprogress_tb").jqGrid("getRowData",ids[i]);
					if ( rowData['itemState'] == 1){
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
 	    //已立项的项目
		$('#inprogress_tb').jqGrid({
         	url:'${ContextPath}/project/punit_viewPassItems/'+$('#schoolProjectId').val(),
			datatype: 'json',
         	colNames:['项目编号','项目名称','学科编号','负责人','申请经费(人民币/万元)','项目详情','进度检查','状态','操作'],
 			colModel:[
						{name:'passItem.id',index:'Id',width:30,align:'center',hidden:true},
                        {name:'itemName',index:'item_name',width:60,align:'center'},
                        {name:'passItem.discId',index:'discId',width:60,align:'center'},
                        {name:'principalName',index:'principal',width:60,align:'center'},
                        {name:'applyFunds',index:'funds',width:60,align:'center'},
                        {name:'detail',index:'detail',width:60,align:'center'},
                        {name:'progress',index:'progress',width:60,align:'center'},
                        {name:'itemState',index:'item_state',width:60,align:'center',hidden:true},
                        {name:'operate',index:'operate',width:60,align:'center'}
 			],
 			height:'100%',
 			autowidth:true,
 			pager: '#inprogress_pager',
 			rowNum:20,
 			rownumbers:true,
 			rowList:[20,30,40],
 			viewrecords: true,
 			sortname: 'itemName',
 			sortorder: 'asc',
 			caption: "已立项的项目",
		    gridComplete: function(){
		    	var currentState = $('#schoolProjectState').val();
				if( currentState != '结果发布' ) 
				{
					jQuery("#inprogress_tb").setGridParam().hideCol("operate");
					$("#inprogress_tb").setGridWidth($("#content").width());
				}
				
		    	var ids = jQuery("#inprogress_tb").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					var rowData = jQuery("#inprogress_tb").jqGrid('getRowData',ids[i]);
					
					var detail = "<a href='#' style='text-decoration:none' onclick=checkPassItem(\""+ids[i]+"\")>查看</a>";
					var progress = "<a href='#' style='text-decoration:none' onclick=checkProgress(\""+ids[i]+"\")>查看</a>"; 
					var rowData = $('#inprogress_tb').jqGrid("getRowData", ids[i]);
					var  operates;
					if( rowData['itemState']==1 ) operates = "<a href='#' style='text-decoration:none' onclick=inputResult('"+ids[i]+"')>录入结果</a>";
					else operates = "<a href='#' style='text-decoration:none' onclick=updateResult('"+ids[i]+"')>已录入</a>";
					jQuery("#inprogress_tb").jqGrid('setRowData',ids[i],{detail :detail});
					jQuery("#inprogress_tb").jqGrid('setRowData',ids[i],{progress :progress});
					jQuery("#inprogress_tb").jqGrid('setRowData',ids[i],{operate :operates});
				}
		    }
 		});
 	    
		jQuery("#inprogress_tb").jqGrid('navGrid','#inprogress_pager',{edit:false,add:false,del:false,search:true}); 
		
		$('#return_btn').click(function(){
	 		$.post("${ContextPath}/project/punit", function(data){
				  $( "#content" ).empty();
				  $( "#content" ).append( data );
			 	  }, 'html');
		});
		
		
		
		$("#startMedium_btn").click(function() {
			
			/* // status = "1" 表示中期检查
			var status = "1";
			$.ajax({ 
   		        type : "POST", 
   		        url : "${ContextPath}/project/punit_sendEmail/" + status + "/" + $('#schoolProjectId').val(), 
   		        dataType : "json",      
   		        contentType : "application/json",               
   		        success: function(data) {
					/* if (data) {
						$('#allTeachers').show();
						$('#allTeachers').find('option')
					    .remove().end().append("<option value=''>请选择教师</option>");
						$.each(data,function(key, value) {
							var option = "<option value='" + key 
							+ "' value2='" + value.szbm 
							+ "' value3='" + value.address + "'>" + value.name + "</option>";
							$('#allTeachers').append(option);
						});
					} else {
						alert_dialog("系统出错，请重新刷新检查！");
					} * /
   		        }
   		     }); */
		});
}); 
	
	function showEmailConfigDialog()
	{
		$.post('${ContextPath}/project/punit_show_emailConfig/' + $('#schoolProjectId').val(), function(data){
	  		$('#dialog_showEmailConfig').empty();
		  	$('#dialog_showEmailConfig').append( data );
	 	  	$('#dialog_showEmailConfig').dialog({
	  	  		    title:"群发邮件配置",
	  	  		    height:'650',
	  	  			width:'800',
	  	  			position:'center',
	  	  			modal:true,
	  	  			draggable:true,
	  	  		    hide:'fade',
	  	  			show:'fade',
	  	  		    autoOpen:true,
	  	  		    buttons:{  
		  	  		    "保存设置并发送":function(){
			  	  		    var emailTitle = $("#emailTitle").val();
		  	 	         	
		  	 	            /* if ($("#startTime").val() == undefined || 
		  	 	            		$("#startTime").val().trim() == "") {
		  	 	            	alert($("#startTime").val());
		  	 	            	alert_dialog("请填写检查时间！");
		  	 	            	return;
		  	 	            } */
		  	 	            if (emailTitle == undefined || emailTitle.trim() == "") {
		  	 	            	alert_dialog("请填写邮件标题！");
		  	 	            	return;
		  	 	            } 
		  	  		  		/* //清空警告框内文本并添加新的警告文本
		  	    		    $( "#dialog-confirm" ).empty().append("<p>即将对教师进行邮件通知，是否继续？</p>");
		  	    		    $( "#dialog-confirm" ).dialog({
		  	        	        height:150,
		  	        	        buttons: {
		  	        	            "确定": function() {
		  	        	            	$( this ).dialog( "close" ); */
		  	        	            	$("#dialog_showEmailConfig").dialog("close");
		  		  	 	            	
		  	        	            	//alert($("#startTime").val());
		  		  	 	            	var emailConfigData = {
		  		  	 	            			"projectId"      : $("#emailPageProjectId").val(), 
		  		  	 	    	 				"emailTitle"     : emailTitle, 
		  		  	 	    	 				"attachmentName" : $("#attachmentName") .val(), 
		  		  	 	    	 				"startTime"      : $("#startTime").val(), 
		  		  	 	    	 				"content"        : editor.getContent(), 
		  		  	 	    					"attachmentId"   : $("#file_no").text()
		  		  	 	    			};
		  		  	 	            	
		  		  	 	            	$.ajax({ 
		  		  	 	    		        type : "POST", 
		  		  	 	    		        url : "${ContextPath}/project/punit_sendEmail", 
		  		  	 	    		        dataType : "json",      
		  		  	 	    		        contentType : "application/json",               
		  		  	 	    		        data : JSON.stringify(emailConfigData), 
		  		  	 	    		        success: function(data) {
		  		  	 	    		        	//if (data == "success") {
		  		  	 	    		        		alert_dialog("群发邮件成功！");
			  		  	 	    		        	$.post("${ContextPath}/project/punit", function(data){
			  		  	 	    					  	$( "#content" ).empty();
			  		  	 	    					  	$( "#content" ).append( data );
			  		  	 	    				 	}, 'html');
		  		  	 	    		        	//}
		  		  	 	    		        	/* $(".process_dialog").dialog("destroy");
		  		  	 	    					$(".process_dialog").hide();
		  		  	 	    					alert_dialog("申请已经成功保存,3秒钟后跳转到\"我的项目\"页面");
		  		  	 	    					setTimeout(3000, toMyItemPage()); */
		  		  	 	    		        }
		  		  	 	    		     });
		  	  	      	        	/* },
		  	        	        	"取消": function() {
		  	        	           		 $( this ).dialog( "close" );
		  	        	          		}
		  	        	        	}
		  	        	  	}); */
	  	 	            },
	  	 	            "关闭":function(){
	  	 	            	$("#dialog_showEmailConfig").dialog("close");
	  	 	            }
	  	  		    }
	 	  }); 
	  	}, 'html');
	    event.preventDefault();
	}
	
</script>