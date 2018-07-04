<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<div class="con_header">
	<h3>
		<span class="icon icon-search"></span>论文抽查
	</h3>
</div> 
<div class="selectbar inner_table_holder">
    <div class="left">
         <a id="generate_spot_list"  class="button" href="#" ><span class="icon icon-makelist"></span>生成抽查清单</a>
    </div>
    <div id="spot_check_rule_div" class="left">
        <a id="spot_check_rule" class="button" href="#"><span class="icon icon-explanation"></span>论文抽查规则</a>
    </div>
    <div class="right">
        <a id="exportData" class="button" href="#" ><span class="icon icon-export"></span>结果导出</a>
    </div>
</div> 
<div>
	<div id="paper_spot" >		

 	    <div class="spot_check_div">
			 <span class="ui-icon ui-icon-info" style="float: left; margin-right: .3em;"></span>论文抽查规则:
 		     <p>1.每个参评高校都会被抽查到。</p>
			 <p>2.参评学科数少于10个，抽查1个学科。</p>
			 <p>3.参评学科数在10至30之间，抽查2个学科。</p>
			 <p>4.参评学科数多于30个，抽查3个学科。</p>
	    </div>
		<div class="table" id="paper_tb">
			<table id="paper_list"></table>
			<div id="pager"></div>
		</div>
	</div>
</div>

<script type="text/javascript"> 

$("#spot_check_rule").click(function(){
	$(".spot_check_div").show();
	  $(".spot_check_div").dialog({
		    title:"论文抽查规则说明",
		    height:'400',
			width:'500',
			position:'center',
			modal:true,
			draggable:true,
		    autoOpen:true,
		    buttons:{  
	            "关闭":function(){
	            	$(".spot_check_div").dialog("close");
	            }
		    }});
});
 
 var contextPath = "${ContextPath}";
$(document).ready(function(){
	$(".spot_check_div").hide();
	$( "input[type=submit], a.button , button" ).button();
	$("#exportData").click(function(){//下载查重结果EXCEL表
		var url = '${ContextPath}/check/spotExport/';
		outputJS(url);
	});
	
	
	/********************  jqGrid ***********************/
	
	$("#generate_spot_list").click(function(){
		
		$.post('${ContextPath}/check/spotGenerateList',function(data){
			if(data){
				$("#paper_list").setGridParam({url : '${ContextPath}/check/spotList'}).trigger("reloadGrid");
			}else{
				alert("生成清单失败！");
			}
		});
	});
	
	
	$("#paper_list").jqGrid({
		url:"${ContextPath}/check/spotList",
		datatype : 'json',
		mtype : 'GET',
		colNames:['主键','单位代码','单位名称','一级学科代码','一级学科名称'],
		colModel:[
                    {name:'id',index:'id',align:'center',width:60,hidden:true},
                    {name:'unitID',index:'unitID',align:"center"},
                    {name:'unitName',index:'unitName',align:"center"},
                    {name:'discID',index:'discID', align:"center"},
                    {name:'discName',index:'discName',align:"center"}
		],
		height:"100%",
		autowidth:true,
		pager: '#pager',
		rowNum:10,
		rowList:[10,20,30],
		viewrecords: true,
		sortname: 'unitID',
		sortorder: "desc",
		caption: "论文抽查",
		jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
			root : "rows", //包含实际数据的数组  
			page : "pageIndex", //当前页  
			total : "totalPage",//总页数  
			records : "totalCount", //查询出的记录数  
			repeatitems : false,
		},
	}).navGrid('#pager',{edit:false,add:false,del:false});
});	
	
</script>
    