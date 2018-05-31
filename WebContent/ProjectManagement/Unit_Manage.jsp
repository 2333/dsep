<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>项目监控
	</h3>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">项目名称：</span>
			</td>
			<td>
				<input id = "guide_name" type = "text" >
 			</td>
			<td>&nbsp;&nbsp;</td>
			<td>
				<span class="TextFont">项目类别：</span>
			</td>
			<td>
				<select id="guide_type_select">
				<option value="全部">全部</option>
				<option value="国防类纵向项目">国防类纵向项目</option>
				<option value="民口类纵向项目">民口类纵向项目</option>
				<option value="基础类纵向项目">基础类纵向项目</option>
				<option value="人文社科纵向项目">人文社科纵向项目</option>
				</select>
			</td>
			<td>&nbsp;&nbsp;</td>
			<td>
				<span class="TextFont">状态：</span>
			</td>
			<td>
				<select id="guide_state_select">
				<option value="9">全部</option>
				<option value="1">未发布</option>
				<option value="2">申报中</option>
				<option value="4">进行中</option>
				<option value="5">中期检查</option>
				<option value="6">中期检查结束</option>
				<option value="7">结题检查</option>
				<option value="8">结果发布</option>
				<option value="0">已结束</option>
				</select>
			</td>
			<td>&nbsp;&nbsp;</td>
			<td>
				<a  id="project_search_btn" class="button" href="#"><span class="icon icon-search"></span>查询</a>
			</td>
		</tr>
	</table>
	<table class="layout_table right">
		<tr>
			<td>
				<a  id="create_btn" class="button" href="#"><span class="icon icon-add"></span>新建项目</a>
			</td>
		</tr>
	</table>
</div>
<div>
		<table id="guide_list"></table>
		<div id="guide_pager"></div>
</div>
<div id="dialog_projectDetail" >
</div>
<div id = "delete_confirm" title = "警告">
</div>
<div id = "publish_confirm" title = "警告">
</div>
<script type="text/javascript">

 	function toApproval(id)
 	{
 		var rowData = $('#guide_list').jqGrid("getRowData", id);
 		var projectId = rowData['project.id'];
 		$.post("${ContextPath}/project/punit_approval",{projectId:projectId}, function(data){
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		 	  }, 'html');
 	}
 	function toExecute(id)
 	{
 		var rowData = $('#guide_list').jqGrid("getRowData", id);
 		var projectId = rowData['project.id'];
 		$.post("${ContextPath}/project/punit_execute",{projectId:projectId}, function(data){
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		 	  }, 'html');
 	}
 	function toCheckResult(id)
 	{
 		var rowData = $('#guide_list').jqGrid("getRowData", id);
 		var projectId = rowData['project.id'];
 		$.post("${ContextPath}/project/punit_execute",{projectId:projectId}, function(data){
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		 	  }, 'html');
 	}
 	function guide_check(id)
 	{
 		var rowData = $('#guide_list').jqGrid("getRowData", id);
 		var projectId = rowData['project.id'];
 		$.post('${ContextPath}/project/punit_check',{projectId:projectId}, function(data){
	   		  $('#dialog_projectDetail').empty();
			  $('#dialog_projectDetail').append( data );
		 	  $('#dialog_projectDetail').dialog({
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
	   	 	            	$("#dialog_projectDetail").dialog("close");
	   	 	            }
	   	  		    }
		 	  }); 
	   	 	}, 'html');
   	    event.preventDefault();
 	}
 	function guide_edit(id)
 	{
 		var rowData = $('#guide_list').jqGrid("getRowData", id);
 		var projectId = rowData['project.id'];
 		$.post('${ContextPath}/project/punit_edit',{projectId:projectId},function(data){
 			  $( "#content" ).empty();
			  $( "#content" ).append( data );
 		},'html');
 	}
 	function guide_publish(id)
 	{
 		$('#publish_confirm').empty().append("<p>你确定要发布该项目吗？</p>");
		$('#publish_confirm').dialog({
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  			show:'fade',
	  		    autoOpen:true,
	  		    buttons:{  
					"确定":function(){
						var rowData = $('#guide_list').jqGrid("getRowData", id);
				 		var projectId = rowData['project.id'];
				 		$.post('${ContextPath}/project/punit_publish',{projectId:projectId},function(data){
				 			if(data)
				 			{
				 				alert_dialog("发布成功！");
				 				$("#guide_list").trigger("reloadGrid");
				 			}
				 			else 
				 			 	alert_dialog("发布失败！");
				 		});
						$("#publish_confirm").dialog("close");
					},
	 	           "取消":function(){
	 	            	$("#publish_confirm").dialog("close");
	 	            }
	  		    }
		});
 	}
 	function guide_delete(id)
 	{
 		$('#delete_confirm').empty().append("<p>你确定要删除该项目吗？</p>");
		$('#delete_confirm').dialog({
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  			show:'fade',
	  		    autoOpen:true,
	  		    buttons:{  
					"确定":function(){
						var rowData = $('#guide_list').jqGrid("getRowData", id);
				 		var projectId = rowData['project.id'];
				 		$.post("${ContextPath}/project/punit_delete",{projectId:projectId},function(data){
				 			if(data){
				 				$("#guide_list").trigger("reloadGrid");
				 			}
				 			else{
				 				alert("删除失败!");
				 			}
				 		},'json');
						$("#delete_confirm").dialog("close");
					},
	 	           "取消":function(){
	 	            	$("#delete_confirm").dialog("close");
	 	            }
	  		    }
		});
 		
 	}
	$(document).ready(function(){
		 $( 'input[type=submit], a.button , button' ).button();
		 
		 $('#create_btn').click(function(){
			 $.post("${ContextPath}/project/punit_create", function(data){
				  $( "#content" ).empty();
				  $( "#content" ).append( data );
			 	  }, 'html');
		 });
		 
		 $('#project_search_btn').click(function(){
			var projectType = $('#guide_type_select').val();
			var currentState = $('#guide_state_select').val();
			var projectName = $('#guide_name').val();
			jQuery("#guide_list").setGridParam({
				url:"${ContextPath}/project/punit_search?projectName="+projectName+"&projectType="+projectType+"&currentState="+currentState,
				sortorder:"desc",
				sortname:"project_name",
				page:1,
			}).trigger("reloadGrid");
		 });
		 
		 $.datepicker.regional['zh-CN'] = {  
			      clearText: '清除',  
			      clearStatus: '清除已选日期',  
			      closeText: '关闭',  
			      closeStatus: '不改变当前选择',  
			      prevText: '<上月',  
			      prevStatus: '显示上月',  
			      prevBigText: '<<',  
			      prevBigStatus: '显示上一年',  
			      nextText: '下月>',  
			      nextStatus: '显示下月',  
			      nextBigText: '>>',  
			      nextBigStatus: '显示下一年',  
			      currentText: '今天',  
			      currentStatus: '显示本月',  
			      monthNames: ['一月','二月','三月','四月','五月','六月', '七月','八月','九月','十月','十一月','十二月'],  
			      monthNamesShort: ['一','二','三','四','五','六', '七','八','九','十','十一','十二'],  
			      monthStatus: '选择月份',  
			      yearStatus: '选择年份',  
			      weekHeader: '周',  
			      weekStatus: '年内周次',  
			      dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
			      dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
			      dayNamesMin: ['日','一','二','三','四','五','六'],  
			      dayStatus: '设置 DD 为一周起始',  
			      dateStatus: '选择 m月 d日, DD',  
			      dateFormat: 'yy-mm-dd-hh-mm',  
			      firstDay: 1,  
			      initStatus: '请选择日期',  
			      isRTL: false  
			    };
		   $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
		   jQuery("#start_date").datepicker({
			   yearRange:'1900:2020',
			   changeMonth: true,
	          changeYear: true,
	          showButtonPanel: true,
			   dateFormat: 'yy-mm-dd',  //日期格式，自己设置              buttonImage: 'calendar.gif',  //按钮的图片路径，自己设置              buttonImageOnly: true,  //Show an image trigger without any button.             showOn: 'both',//触发条件，both表示点击文本域和图片按钮都生效         yearRange: '1990:2008',//年份范围          clearText:'清除',//下面的就不用详细写注释了吧，呵呵，都是些文本设置         closeText:'关闭',         prevText:'前一月',         nextText:'后一月',         currentText:' ',          monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],  
		   });
		   jQuery("#end_date").datepicker({
			   yearRange:'1900:2020',
			   changeMonth: true,
	          changeYear: true,
	          showButtonPanel: true,
			   dateFormat: 'yy-mm-dd',  //日期格式，自己设置              buttonImage: 'calendar.gif',  //按钮的图片路径，自己设置              buttonImageOnly: true,  //Show an image trigger without any button.             showOn: 'both',//触发条件，both表示点击文本域和图片按钮都生效         yearRange: '1990:2008',//年份范围          clearText:'清除',//下面的就不用详细写注释了吧，呵呵，都是些文本设置         closeText:'关闭',         prevText:'前一月',         nextText:'后一月',         currentText:' ',          monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],  
		   });
		 
		 jQuery("#guide_list").jqGrid({
	   			url:'${ContextPath}/project/punit_viewGuides',
			 	datatype: "json",
			 	mtype: 'GET',
	   		   	colNames:['项目ID','项目名称','项目类别','状态','发布时间','截止时间','详情','操作'],
	   		   	colModel:[
	   		   		{name:'project.id',index:'projectId', width:300,align:"center",hidden:true},
	   		   		{name:'project.projectName',index:'projectName', width:300,align:"center"},
	   		  	 	{name:'project.projectType',index:'projectType', width:260,align:"center"},
	   		   		{name:'currentState',index:'currentState', width:125, align:"center"},
	   		   		{name:'startDate',index:'startDate', width:125, align:"center"},		
	   		   		{name:'endDate',index:'endDate', width:125, align:"center"},
	   		   		{name:'detail',index:'detail', width:125, align:"center"},	
	   		 		{name:'modifyInfo',index:'modifyInfo',align:"center", width:200},
	   		   	],
	   		   	autowidth:true,
	   		   	rowNum:10,
	   		    rownumbers:true,
	   		   	height:"100%",
	   		   	rowList:[10,20,30],
	   		   	pager: '#guide_pager',
	   		   	sortname: 'startDate',
	   		    viewrecords: true,
	   		    sortorder: "desc",
	   		    caption: "项目汇总",
	   			 jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
	             root: "rows",  //包含实际数据的数组  
	             page: "pageIndex",  //当前页  
	             total: "totalPage",//总页数  
	             records:"totalCount", //查询出的记录数  
	             repeatitems : false,
	         	},
	   		    gridComplete: function(){
	    		    	var ids = jQuery("#guide_list").jqGrid('getDataIDs');
	    				for(var i=0;i < ids.length;i++){
	    					var rowData = $('#guide_list').jqGrid("getRowData", ids[i]);
	    					var currentState = rowData['currentState'];
	    					var modify;
	    					if( currentState == '未发布') {
	    						modify = "<a class='' href='#' style='text-decoration:none' style='text-decoration:none' onclick=guide_edit('"+ids[i]+"')>编辑</a>"+" | "
	    								 +"<a class='' href='#' style='text-decoration:none' onclick=guide_delete('"+ids[i]+"')>删除</a>"+" | "
	    								 +"<a class='' href='#' style='text-decoration:none' onclick=guide_publish('"+ids[i]+"')>发布</a>";
	    					}
	    					else if ( currentState == '申报中' || currentState == '评审中') {
	    						modify = "<a class='' href='#' style='text-decoration:none' onclick=toApproval('"+ids[i]+"')>申报管理</a>";
	    					}
	    					else if ( currentState == '进行中' || currentState == '中期检查' || currentState == '中期检查结束' || currentState == '结题检查' || currentState == '结果发布')
	    					{
	    						modify = "<a class='' href='#' style='text-decoration:none' onclick=toApproval('"+ids[i]+"')>申报管理</a>"+" | "+ 
	    							"<a class='' href='#' style='text-decoration:none' onclick=toExecute('"+ids[i]+"')>执行管理</a>";
	    					}
	    					else  
	    					{
	    						modify =  "<a class='' href='#' style='text-decoration:none' onclick=toCheckResult('"+ids[i]+"')>查看结果</a>";
	    					}
	    					var detail = "<a class='' href='#' style='text-decoration:none' onclick=guide_check('"+ids[i]+"')>查看</a>";
	    					jQuery("#guide_list").jqGrid('setRowData',ids[i],{modifyInfo :modify});
	    					jQuery("#guide_list").jqGrid('setRowData',ids[i],{detail :detail});
	    				}
	    		    },
	   		});
	   		jQuery("#guide_list").jqGrid('navGrid','#guide_pager',{edit:false,add:false,del:false,search:true});
	});
</script>
