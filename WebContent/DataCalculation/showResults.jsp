<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="con_header">
	<h3>
		<span class="icon icon-publiccontrast"></span>计算结果查看
	</h3>
	<input id='discId' type="text" value='${discId}'/>
</div> 

<div class="selectbar layout_holder">
    <table id="cal_result_tb"></table>
    <div id="cal_result_pager_tb"></div>
</div>

<script type='text/javascript'>
$(document).ready(function(){
	var discId = $("#discId").val();
	console.log(discId);
	$("#cal_result_tb").jqGrid({
		url : '${ContextPath}/calculate/calResultList?discId=' + discId,
		datatype : 'json',
		mtype : 'GET',
		colNames:['ID','学科名称','学校名称','指标项名称','计算结果'],
		colModel:[
                 {name:'discLastIndexValue.id',index:'discLastIndexValue.id', 
                	 width:30,align:'center',hidden:true},
                 {name:'discName',index:'discName', width:30,align:'center',hidden:true},
                 {name:'unitName',index:'unitName', width:30,align:'center'},
                 {name:'discLastIndexValue.indexContent',index:'discLastIndexValue.indexContent', 
                	 width:30,align:'center'},
                 {name:'discLastIndexValue.value',index:'discLastIndexValue.value', 
                     width:30,align:'center'},
		         ],
		height:"100%",
		width:"700",
		rowNum : 999,
		pager: '#cal_result_pager_tb',
		caption: "计算结果",
	}).navGrid('#cal_result_pager_tb',{edit : false,add : false,del : false, search:false});
});
</script>