<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <div  class="con_header">
	<h3>
		<span class="icon icon-web"></span>报告下载
	</h3>
</div>
<div  class="con_header inner_table_holder">
	<table class="layout_table right">
		<tr>
			<td>
			    <a class="button" href="#"><span class="icon icon-download"></span>下载单位报告</a>
			</td>		
		</tr>
	</table>
</div>
<div class="selectbar layout_holder">
		<table id="school_pre_tb"></table>
		<div id="pager_schoolpre_tb"></div>
</div>
<script type="text/javascript">
function modifyDSEP_Info(id)
{
	alert('下载成功！');
}
$(document).ready(function(){
	common_Css();
	$("#school_pre_tb").jqGrid({
		url:"/DSEP/SchoolDisciplineList/Data",
		datatype: "json",
		mtype:"post",
		colNames:['学科代码','学科名称','是否参评','下载'],
		colModel:[
			{name:'collectFlow.disciplineId',index:'collectFlow.disciplineId',align:"center", width:100,hidden:true},
			{name:'disciplineName',index:'disciplineId',align:"center", width:120 },
			{name:'isAttend',index:'isAttend',align:"center", width:80},
			{name:'download',index:'download',align:"center", width:80},
		],
		rownumbers: true,
		height:"100%",
		autowidth:true,
		pager: '#pager_schoolpre_tb',
		rowNum:10,
		rowList:[10,20,30],
		viewrecords: true,
		sortorder: "desc",
		sortname:'disciplineId',
		//multiselect: true,
		//multiboxonly: true,
		jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
            root: "rows",  //包含实际数据的数组  
            page: "pageIndex",  //当前页  
            total: "totalPage",//总页数  
            records:"totalCount", //查询出的记录数  
            repeatitems : false,
        },
		gridComplete: function(){  
			var ids = jQuery("#school_pre_tb").jqGrid('getDataIDs');
			var load_link;
			for(var i=0;i < ids.length;i++){
				var isAttend= $("#school_pre_tb").getCell(ids[i],"isAttend");
				if(isAttend=='是')
					load_link = "<a class='' href='#' onclick='modifyDSEP_Info("+ids[i]+")'>学科报告</a>"; 
				else
					load_link = "<a class='' href='#' onclick='modifyDSEP_Info("+ids[i]+")'>未参评报告</a>"; 
				jQuery("#school_pre_tb").jqGrid('setRowData',ids[i],{download:load_link});
			}	
		},
		caption: "学科预信息汇总"
	}).navGrid('#school_pre_tb',{edit:false,add:false,del:false});	
	
});
</script>