<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>指标权重打分
	</h3>
</div>

<div class="table">
  		<table id="jqGrid_collect_tb"></table>
		<div id="pager_collect_tb"></div>
</div>

<!-- <div id="test_content"> -->
<!-- 	<table id="testtable_list"></table> -->
<!-- 	<div id="test_pager"></div>	 -->
<!-- </div> -->

<input id="expertId" type="hidden" value="${expertId}"/> 
<input id="discId" type="hidden" value="${discId}"/>
<input id="prevQuestionRoute" type="hidden" value="${prevQuestionRoute}"/> 
<input id="nextQuestionRoute" type="hidden" value="${nextQuestionRoute}"/> 
<div id="myDialog">
</div>

<!-- 导航箭头 -->
<jsp:include page="eval_navi.jsp"/>
<!-- 此处显示专家要打分的类别，如"团队","专家","论文"等 -->
<div id="achievement_content">
 	<div id="dsep_thesis" class="cur" >
 		<table id="indic_wt_list"></table> 
 		<div id="indicator_weight_pager"></div> 
 	</div>
</div>

<div id="dialog-confirm" title="警告"></div>

<div class="selectbar" style="overflow: hidden">
	<table class="layout_table right">
		<tbody>
			<tr>
				<td width="115">
					<a id="save" class="button" href="#">
						<span class="icon icon-store "></span>
						整体保存
					</a>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<!-- 导航箭头 -->
<jsp:include page="eval_navi.jsp"/>

<div class="process_dialog">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 正在保存……
</div>

<style>
.ui-jqgrid tr.jqgrow td {
  white-space: normal !important;
  height:auto;
  vertical-align:text-top;
  padding-top:2px;
  word-break:break-all;
 }
</style>

<script src="${ContextPath}/js/expert.common.js"></script>
<script type="text/javascript">
	$("input[type=submit], a.button, button").button();
	$(".process_dialog").hide();
	$("#indic_wt_list").jqGrid({
		ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },
		url: '${ContextPath}/evaluation/progress/indicWtGetResults/',
		mtype : 'GET',
		datatype : "json",
		colNames : ['id', 'resultId', 'questionId', '有效数', '指标权重项目', '建议值', '上一次打分', '打分' ],
		colModel : [
        {name : 'id',	        index : 'id',		    width : 100,	align : "center", hidden: true}, 
		{name : 'resultId',	    index : 'resultId',		width : 100,	align : "center", hidden: true}, 
		{name : 'questionId',	index : 'questionId',	width : 100,	align : "center", hidden: true}, 
		{name : 'effectItemNum',index : 'effectItemNum',width : 100,	align : "center", hidden: true}, 
		{name : 'questionStem',	index : 'questionStem', width : 190,	align : "center", sortable:false}, 
		{name : 'initVal1',		index : 'initVal1',		width : 50,		align : "center"}, 
		{name : 'oldVal',		index : 'oldVal',		width : 40,		align : "center", hidden:true}, 
		{name : 'evaluate',		index : 'evaluate',		width : 100,	align : "center", sortable:false}, 
		],
		cmTemplate : {sortable : false},
		height : '100%',
		autowidth : true,
		rowNum : 20,
		rowList : [ 20 ],
		viewrecords : true,
		sortorder : "desc",
		pager : "#indicator_weight_pager",
		pgbuttons : true,
		rownumbers: true,
		jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
	        root: "rows",  //包含实际数据的数组  
	        page: "pageIndex",  //当前页  
	        total: "totalPage",//总页数  
	        records:"totalCount", //查询出的记录数  
	        repeatitems : false,
	    },
		gridComplete: function(){
			var ids = jQuery("#indic_wt_list").jqGrid('getDataIDs');
			var evaluateTexts = "";
			for (var i = 0; i < ids.length; i++) {
				var rowId = ids[i];
				var rowdata=jQuery("#indic_wt_list").jqGrid('getRowData',rowId);
			    var num = rowdata.effectItemNum; 
			    var oldVal = rowdata.oldVal;
			    var vals = oldVal.split(",");
			    console.log(vals.length);
			    val0 = vals[0];
			    if (num >= 2) {
			    	evaluateTexts = "<input type='text' size='1' id='evaluateText" + i + "0' value='" + val0 + "'/>";
			    	for (var j = 1; j < num; j++) {
			    		evaluateTexts += "：<input type='text' size='1' id='evaluateText" + i + "" + j + "' value='" + getOldVal(j,vals) + "'/>";
			    	}
			    }
			    else{
			    	evaluateTexts = "<input type='text' size='1' id='evaluateText" + i + "0' value='" + val0 + "'/>";
			    }
				jQuery("#indic_wt_list").jqGrid('setRowData', ids[i], {evaluate:evaluateTexts});
			}
			
			function getOldVal(i,vals){
				if(vals.length>1)
					return vals[i];
				else return ' ';
	      	}
		},
		onPaging: function() {
		},
	}).navGrid('#achievement_pager');
	
	$("#save").click(function() {
		wholeSave(true, null);		        
	});
	
	function openLink(url) {
		wholeSave(false, url);
	}
	
	// clickFromSaveButton为true代表保存动作来自于"整体保存"按钮的命令，url参数为null
	// 为false代表保存来自于翻页（被动保存），url参数为下一个路由
	function wholeSave(clickFromSaveButton, url) {
		$(".process_dialog").show();
		$('.process_dialog').dialog({
			position : 'center',
			modal:true,
			autoOpen : true,
		});
		var ids = $("#indic_wt_list").jqGrid('getDataIDs');
		
		console.log(ids);
		
		var results = [];
		for(var i = 0; i < ids.length; i++) {
			var rowId = ids[i];
			var jqGridData = $("#indic_wt_list").jqGrid('getRowData',rowId);
			var num = jqGridData.effectItemNum;
			var resultId = jqGridData.resultId;
			resultId = resultId.trim();
			var questionId = jqGridData.questionId;
			questionId = questionId.trim();
			var oldVal = jqGridData.oldVal;
			console.log(oldVal);
			//var unitId = jqGridData.unitId;
			// 理应整个页面都是针对一个disc学科打分的，这里discId2表明从jqGrid中取出的数据，上文discId是从后台拿到的数据
			//var discId2 = jqGridData.discId;
			var allBlank = false;
			var v1 = "", v2 = "", v3 = "", v4 = "", v5 = "", v6= "", v7 = "";
			var score = "";
			if (num == 1) {
				v1 = $('#evaluateText' + i + "0").val().trim();
				if ("" == v1) allBlank = true;
				score = v1;
			} else if (num == 2) {
				v1 = $('#evaluateText' + i + "0").val().trim();
				v2 = $('#evaluateText' + i + "1").val().trim();
				if ("" == v1 && "" == v2) allBlank = true;
				score = v1 + "," + v2;
			} else if (num == 3) {
				v1 = $('#evaluateText' + i + "0").val().trim();
				v2 = $('#evaluateText' + i + "1").val().trim();
				v3 = $('#evaluateText' + i + "2").val().trim();
				if ("" == v1 && "" == v2 && "" == v3) allBlank = true;
				score = v1 + "," + v2 + "," + v3;
			} else if (num == 4) {
				v1 = $('#evaluateText' + i + "0").val().trim();
				v2 = $('#evaluateText' + i + "1").val().trim();
				v3 = $('#evaluateText' + i + "2").val().trim();
				v4 = $('#evaluateText' + i + "3").val().trim();
				if ("" == v1 && "" == v2 && "" == v3 && "" == v4) 
					allBlank = true;
				score = v1 + "," + v2 + "," + v3 + "," + v4;
			} else if (num == 5) {
				v1 = $('#evaluateText' + i + "0").val().trim();
				v2 = $('#evaluateText' + i + "1").val().trim();
				v3 = $('#evaluateText' + i + "2").val().trim();
				v4 = $('#evaluateText' + i + "3").val().trim();
				v5 = $('#evaluateText' + i + "4").val().trim();
				if ("" == v1 && "" == v2 && "" == v3 && "" == v4 && "" == v5) 
					allBlank = true;
				score = v1 + "," + v2 + "," + v3 + "," + v4 + "," + v5;
			}else if (num == 6) {
				v1 = $('#evaluateText' + i + "0").val().trim();
				v2 = $('#evaluateText' + i + "1").val().trim();
				v3 = $('#evaluateText' + i + "2").val().trim();
				v4 = $('#evaluateText' + i + "3").val().trim();
				v5 = $('#evaluateText' + i + "4").val().trim();
				v6 = $('#evaluateText' + i + "5").val().trim();
				if ("" == v1 && "" == v2 && "" == v3 && "" == v4 && "" == v5 && "" == v6) 
					allBlank = true;
				score = v1 + "," + v2 + "," + v3 + "," + v4 + "," + v5 + "," + v6;
			} else if (num == 7) {
				v1 = $('#evaluateText' + i + "0").val().trim();
				v2 = $('#evaluateText' + i + "1").val().trim();
				v3 = $('#evaluateText' + i + "2").val().trim();
				v4 = $('#evaluateText' + i + "3").val().trim();
				v5 = $('#evaluateText' + i + "4").val().trim();
				v6 = $('#evaluateText' + i + "5").val().trim();
				v7 = $('#evaluateText' + i + "6").val().trim();
				if ("" == v1 && "" == v2 && "" == v3 && "" == v4 && "" == v5 && "" == v6 && "" == v7) 
					allBlank = true;
				score = v1 + "," + v2 + "," + v3 + "," + v4 + "," + v5 + "," + v6 + "," + v7;
			}
			// 表明这次专家改变了评价，需要保存
        	if (!("" == oldVal && allBlank) && score != oldVal) {
        		
        		evalResultFromJSP = {
        			"resultId" : resultId, 
        			"value" : score, 
            		"questionId" : questionId
            	};
            	results.push(evalResultFromJSP);
        	}
        	
      	}
		
      	
      	$.ajax({ 
	    	type : "POST", 
            url : "${ContextPath}/evaluation/progress/indicWtSaveOrUpdateResults", 
            dataType : "json",      
            contentType : "application/json",               
            data : JSON.stringify(results), 
            success:function(backData){ 
				if (clickFromSaveButton) {
	            	$("#indic_wt_list").setGridParam({url:'${ContextPath}/evaluation/progress/indicWtGetResults/'}).trigger("reloadGrid");
	            	$(".process_dialog").dialog("destroy");
					$(".process_dialog").hide();
		    	} else {
		    		$(".process_dialog").dialog("destroy");
					$(".process_dialog").hide();
		    		if (url.trim() == "") return;
					$.post(url, function(data){
						  $( "#content" ).empty();
						  $( "#content" ).append( data );
					  }, 'html');
		    	}
            } 
	    });
      	
	}
	
	
						
</script>
