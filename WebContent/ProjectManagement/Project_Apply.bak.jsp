<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>项目申报
	</h3>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">项目类别：</span>
			</td>
			<td>
				<select id="guide_type">
				<option value="1">国防类纵向项目</option>
				<option value="2">民口类纵向项目</option>
				<option value="3">基础类纵向项目</option>
				<option value="4">人文社科纵向项目</option>
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

<script type="text/javascript">
	
	function apply(id)
	{
		$.post("${ContextPath}/project/papply_create", function(data){
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		 	  }, 'html');
	}
	function checkInfo(id)
	{
		$.post("${ContextPath}/project/papply_check", function(data){
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		 	  }, 'html');
	}
	
	$(document).ready(function(){
		 $( 'input[type=submit], a.button , button' ).button();
		 
		 jQuery("#guide_list").jqGrid({
	   			datatype: "local",
	   		   	colNames:['序号', '项目名称','项目类别','发布时间','截止时间','操作'],
	   		   	colModel:[
	   		   		{name:'id',index:'id', width:125,align:"center"},
	   		   		{name:'name',index:'name', width:300,align:"center"},
	   		  	 	{name:'type',index:'type', width:260,align:"center"},
	   		   		{name:'start_date',index:'start_date', width:150, align:"center"},		
	   		   		{name:'end_date',index:'end_date', width:150, align:"center"},		
	   		 		{name:'modifyInfo',index:'modifyInfo',align:"center", width:252},
	   		   	],
	   		   	rowNum:10,
	   		   	height:"100%",
	   		   	rowList:[10,20,30],
	   		   	pager: '#guide_pager',
	   		   	sortname: 'name',
	   		    viewrecords: true,
	   		    sortorder: "desc",
	   		    caption: "项目指南汇总",
	   		    gridComplete: function(){
	    		    	var ids = jQuery("#guide_list").jqGrid('getDataIDs');
	    				for(var i=0;i < ids.length;i++){
	    					var modify = "<a class='' href='#' onclick='checkInfo("+ids[i]+")'>查看详情</a>"+" | "+
	    					              "<a class='' href='#' onclick='apply("+ids[i]+")'>申报</a>"; 
	    					jQuery("#guide_list").jqGrid('setRowData',ids[i],{modifyInfo :modify});
	    				}
	    		    },
	   		});
	   		jQuery("#guide_list").jqGrid('navGrid','#guide_pager',{edit:false,add:false,del:false,search:true});
	   		var mydata= [
	   		             {id:"1",name:"航空科学基金项目",type:"国防类纵向项目",start_date:"2013-6-26",end_date:"2014-6-26"},
	   		          	 {id:"2",name:"GF863课题",type:"国防类纵向项目",start_date:"2013-4-26",end_date:"2014-6-26"},
	   		             {id:"3",name:"国家科技计划",type:"民口类纵向项目",start_date:"2013-6-26",end_date:"2014-6-26"},
	   		       	     {id:"4",name:"民用飞机技术科研项目",type:"民口类纵向项目",start_date:"2013-6-26",end_date:"2014-6-26"},
	   		    		 {id:"5",name:"霍英东基金流程",type:"基础类纵向项目",start_date:"2013-6-26",end_date:"2014-6-26"},
	   		 			 {id:"6",name:"博士点基金项目",type:"基础类纵向项目",start_date:"2013-6-26",end_date:"2014-6-26"},
	   		 			 {id:"7",name:"国家社科基金项目",type:"人文社科纵向项目",start_date:"2013-6-26",end_date:"2014-6-26"},
	   		             ];
	   		for( var i=0;i<mydata.length;i++ )
	   		{
	   			jQuery("#guide_list").addRowData(i+1,mydata[i]);	
	   		}
	});
</script>
