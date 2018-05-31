<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="con_header">
	<h3>
		<span class="icon icon-search"></span>奇异数据抽查
	</h3>
</div> 
<div class="selectbar inner_table_holder">
    <div class="left">
         <a class="button" href="#" onclick="GenerateSpot()"><span class="icon icon-makelist"></span>生成抽查清单</a>
    </div>
    <div class="left">
         <a class="button" href="#" onClick="ResultOutPut()"><span class="icon icon-export"></span>结果导出</a>
    </div>
    <div id="singulardata_check_rule_div" class="right">
        <a id="singulardata_check_rule" class="button" href="#"><span class="icon icon-explanation"></span>奇异数据抽查规则</a>
    </div>  
</div> 
<div id="paper_spot">
    <div class="singulardata_check_div">
		<span class="ui-icon ui-icon-info" style="float: left; margin-right:.3em;"></span>奇异数据抽查规则：
		<p>1.论文数超过100、获奖数超过100等。</p>
	    <p>2.本指标排序超出最终排序10位以上的学科。</p>
	    <p>3.本学科超出上轮评估结果10位以上的学科。</p> 
	</div>		
 	<div id="paper_tb">
		<table id="paper_list"></table>
		<div id="pager"></div>
	</div>
</div>

<script type="text/javascript">

$("#singulardata_check_rule").click(function(){
	$(".singulardata_check_div").show();
	$(".singulardata_check_div").dialog({
		    title:"抽查规则说明",
		    height:'400',
			width:'500',
			position:'center',
			modal:true,
			draggable:true,
		    autoOpen:true,
		    buttons:{  
	            "关闭":function(){
	            	$(".singulardata_check_div").dialog("close");
	            }
		    }});
});
 function GenerateSpot(){
	 $("#paper_tb").show();
 }
 function ResultOutPut(){
	 
 }
 
$(document).ready(function(){
    
	$(".singulardata_check_div").hide();
	$( "input[type=submit], a.button , button" ).button();
	
	
	$("#paper_list").jqGrid({
		datatype: "local",
		colNames:['单位代码','单位名称','一级学科代码','一级学科名称'],
		colModel:[
                    {name:'id',index:'id',align:"center", width:80,editable:true},
                    {name:'name',index:'name',align:"center", width:80,editable:true },
                    {name:'id2',index:'id2', align:"center",width:150,editable:true },
                    {name:'name2',index:'name2',align:"center", width:150,editable:true }
		],
		height:"100%",
		autowidth:true,
		pager: '#pager',
		rowNum:10,
		rowList:[10,20,30],
		viewrecords: true,
		sortorder: "desc",
		caption: "奇异数据抽查"
	}).navGrid('#pager',{edit:false,add:false,del:false});
	var mydata = [
			{id:"10001",name:"北京大学",id2:"0101",name2:"哲学"},
			{id:"10002",name:"中国人民大学",id2:"0301",name2:"法学"},
			{id:"10003",name:"清华大学",id2:"1201",name2:"管理科学与工程"},
			{id:"10004",name:"北京交通大学",id2:"0202",name2:"应用经济学"},
			{id:"10005",name:"北京工业大学",id2:"1201",name2:"管理科学与工程"},
			{id:"10006",name:"北京航空航天大学",id2:"1201",name2:"管理科学与工程"},
			{id:"10007",name:"北京理工大学",id2:"1201",name2:"管理科学与工程"},
			{id:"10008",name:"北京科技大学",id2:"1201",name2:"管理科学与工程"},
			{id:"10009",name:"北方工业大学",id2:"1202",name2:"工商管理"},
			{id:"10010",name:"北京化工大学",id2:"1201",name2:"管理科学与工程"},
			{id:"10011",name:"北京工商大学",id2:"0202",name2:"应用经济学"}
			];
	for(var i=0;i<=mydata.length;i++)
		jQuery("#paper_list").jqGrid('addRowData',i+1,mydata[i]);
	
	$("#paper_tb").hide();$("#data_tb").hide();
});


</script>
    