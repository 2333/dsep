<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>学校项目信息
	</h3>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">项目类别：</span>
			</td>
			<td>
				<input type="hidden" value="${projectTypes }"> 
				<select id="project_type">
					<option value="">全选</option>
					<c:if test="${!empty projectTypes}">
						<c:forEach items="${projectTypes}" var="type">
							<option value="${type}">${type}</option>
						</c:forEach>
					</c:if>
				</select>
			</td>
			<td>&nbsp;&nbsp;</td>
			<td>
				<a  id="search_btn" class="button" href="#"><span class="icon icon-search"></span>查询</a>
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
<script type="text/javascript">
	
	function apply(id)
	{
		var rowData = $('#guide_list').jqGrid("getRowData", id);
 		var projectId = rowData['project.id'];
		$.post("${ContextPath}/project/papply_create/" + projectId, function(data){
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		 	  }, 'html');
	}
	function checkInfo(id)
	{
		var rowData = $('#guide_list').jqGrid("getRowData", id);
 		var projectId = rowData['project.id'];
 		$.post('${ContextPath}/project/papply_showProjects',{projectId:projectId}, function(data){
	   		  $('#dialog_projectDetail').empty();
			  $('#dialog_projectDetail').append( data );
		 	  $('#dialog_projectDetail').dialog({
	   	  		    title:"项目详情",
	   	  			height:'750',
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
	
	$(document).ready(function(){
		 $( 'input[type=submit], a.button , button' ).button();
		 
		 jQuery("#guide_list").jqGrid({
			// ★★page方法
			url: '${ContextPath}/project/papply_getProjects/',
			datatype : "json", 
   		   	colNames:['序号', '提交状态', '项目名称','项目类别','项目状态','发布时间','截止时间','操作'],
   		   	colModel:[
   		   		{name:'project.id',			 index:'projectId', 		  width:125,	align:"center",hidden:true},
   		   		{name:'project.commitState', index:'commitState', 		  width:125, hidden : true},
   		   		{name:'project.projectName', index:'project_name', width:300,	align:"center"},
   		  	 	{name:'project.projectType', index:'project_type', width:260,	align:"center"},
   		  		{name:'currentState',index:'currentState', 		  width:125 ,align:"center"},
   		   		{name:'startDate',	 index:'start_date',  width:150, 	align:"center"},		
   		   		{name:'endDate',	 index:'end_date', 	  width:150, 	align:"center"},
   		 		{name:'modifyInfo',  index:'modifyInfo',  width:252,	align:"center"},
   		   	],
   		 	autowidth:true,
   		   	rowNum:10,
   		   	height:"100%",
   			rownumbers:true,
   		   	rowList:[10,20,30],
   		   	pager: '#guide_pager',
   		   	sortname: 'start_date',
   		    viewrecords: true,
   		    sortorder: "desc",
   		    caption: "项目指南汇总",
   		    gridComplete: function(){
    		    	var ids = jQuery("#guide_list").jqGrid('getDataIDs');
    				for(var i=0;i < ids.length;i++){
    					var rowId = ids[i];
    					var rowdata = $("#guide_list").jqGrid('getRowData',rowId);
    					var currentState = rowdata["currentState"];
    					var commitState = rowdata["project.commitState"];
    					//alert(currentState);
    					if (currentState == "1"){
    						$("#" + rowId).hide();
    						var modify = "<span class='' href='#' style='text-decoration:none' onclick= ('" + ids[i] + "')>未发布</span>";
							jQuery("#guide_list").jqGrid('setRowData',ids[i],{modifyInfo :modify});
    					} else if (commitState == "是"){
    						var modify = "<a class='' href='#' style='text-decoration:none' onclick= checkInfo('" + ids[i] + "')>查看详情</a>" + " | " +
				             "<a class='' href='#' style='text-decoration:none' onclick= apply('"     + ids[i] + "')>申报   </a>";
							jQuery("#guide_list").jqGrid('setRowData',ids[i],{modifyInfo :modify});
    					} else if (currentState == "未发布"){
    						$("#" + rowId).hide();
    						var modify = "<span class='' href='#' style='text-decoration:none' onclick= ('" + ids[i] + "')>未发布</span>";
							jQuery("#guide_list").jqGrid('setRowData',ids[i],{modifyInfo :modify});
    					} else {
    						var modify = "<a class='' href='#' style='text-decoration:none' onclick= checkInfo('" + ids[i] + "')>查看详情</a>";
							jQuery("#guide_list").jqGrid('setRowData',ids[i],{modifyInfo :modify});
    					} 
    				}
    		    },
   		});
   		jQuery("#guide_list").jqGrid('navGrid','#guide_pager',{edit:false,add:false,del:false,search:true});
	});
	
	$("#search_btn").click(function() {
		var text = $("#project_type option:selected").text();
		$("#guide_list").setGridParam({url : '${ContextPath}/project/papply_getProjectByQuery/' + text})
			.trigger("reloadGrid");
	});
</script>
