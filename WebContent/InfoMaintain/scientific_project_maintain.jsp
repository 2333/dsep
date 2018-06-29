<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>科研项目管理
	</h3>
</div>
    
<div class="selectbar">
	<table class="logout_table">
		<tr>
			<td><a class="button" href="#"><span
				class="icon icon-import"></span>批量导入</a></td>
			<td><a class="button" href="#"><span
				class="icon icon-export"></span>批量导出</a></td>
			<td><a class="button" href="#"><span
				class="icon icon-add"></span>添加科研项目</a></td>
				<td><a class="button" href="#"><span
				class="icon icon-edit"></span>编辑科研项目</a></td>
			<td><a class="button" href="#"><span
				class="icon icon-del"></span>删除科研项目</a></td>
		</tr>
	</table>
</div>

<div class="selectbar layout_holder">
	<table id="researchProjectList_tb"></table>
	<div id="pager"></div>
</div>


<script type="text/javascript">
	$(document).ready(function(){
		$("input[type=submit], a.button , button" ).button();
		
		$("#researchProjectList_tb").jqGrid({
			datatype: "local",
			colNames:['项目编号','项目名称','项目来源','项目下达部门','项目类型'],
			colModel:[
          		{name:'projectNo',index:'projectNo',align:'center', width:100},
          		{name:'projectName',index:'projectName',align:'center',width:100},
          		{name:'projectSource',index:'projectSource',align:'center',width:100},
          		{name:'projectDepartment',index:'projectDepartment',align:'center',width:100},
          		{name:'projectType',index:'projectType',align:'center',width:100},
        	],
        	height:"100%",
			autowidth:true,
			pager:'#pager',
			rowNum:10, 
			rowList:[10,20,30],
			viewrecords: true,
			sortorder: "desc",
			caption: "科研项目列表"
		});
		
		var myData=[
        	{projectNo:'01',projectName:'国家重大科研项目',projectSource:'教育部',projectDepartment:'北航',projectType:'国家级'},
        	{projectNo:'02',projectName:'省级科研项目',projectSource:'国防部',projectDepartment:'工信部',projectType:'省级'},
        	{projectNo:'03',projectName:'863科研项目',projectSource:'教育部',projectDepartment:'北航',projectType:'市级'},
        	{projectNo:'04',projectName:'985高校联合项目',projectSource:'国务院',projectDepartment:'北航',projectType:'国家级'},
        	{projectNo:'05',projectName:'211高校项目',projectSource:'国务院',projectDepartment:'北航',projectType:'省级'}
        ];
		
		for(var i=0;i < myData.length; i++)
			 $("#researchProjectList_tb").jqGrid('addRowData',i+1,myData[i]);
	});

</script>


