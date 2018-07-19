<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${ContextPath}/js/expert.common.js"></script>

<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>学科声誉评价
	</h3>
</div>
<input id="expertId" type="hidden" value="${expertId}"/> 
<input id="discId" type="hidden" value="${discId}"/>
<input id="reputationQuestionId" type="hidden" value="${reputationQuestionId}"/>
<input id="prevQuestionRoute" type="hidden" value="${prevQuestionRoute}"/> 
<input id="nextQuestionRoute" type="hidden" value="${nextQuestionRoute}"/> 
<div id="myDialog">
</div>

<!-- 导航箭头 -->
<jsp:include page="eval_navi.jsp"/>

<div class="cur"><!--class="selectbar layout_holder">  -->
	<table id="repu_list"></table>
	<div id="pager"></div>
</div>

<div class="selectbar inner_table_holder">
	<table class="layout_table right">
		<tbody>
			<tr>
				<td width="115"><a id="save" class="button" href="#"><span
						class="icon icon-store "></span>整体保存</a></td>
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
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script type="text/javascript">
/* $("#search").click(function() {
	var schoolid = $("#school").val();
	var dsepid = $("#dsep").val();
	//var status = $("#collectStatus").val();
	$("#repu_list").setGridParam({url : "${ContextPath}/progress/evaluation/getreputationdata?schoolid="
									+ schoolid + "&dsepid=" + dsepid}).trigger("reloadGrid");
	});
	$("#school").change(function() {
		var collegeid = $('#school').val();
		if (collegeid != "") {
			$.post("${ContextPath}/CenterDisciplineList/loadDisciplines?collegeid="+ collegeid,
					function(data) {
						var selector = $('#dsep');
						selector.append('<option value="" selected="selected">全部</option>');
						$.each(data,function(key,value) {
							selector.append('<option value="'+key+'">'
								+ value + '</option>');
						});
					}, "json");
		} else {
			var selector = $('#dsep');
			selector.append('<option value="" selected="selected">全部</option>');
		}
	}); */
						
	$("input[type=submit], a.button , button").button();
	/********************  jqGrid ***********************/
	var schoolid = $("#school").val();
	var dsepid = $("#dsep").val();
	
	var expertId = $('input#expertId').val();
	var discId = $('input#discId').val();
	//var reputationQuestionId = $('input#reputationQuestionId').val();
	$(".process_dialog").hide();
	
	$("#repu_list").jqGrid({
		ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },
		url : "${ContextPath}/evaluation/progress/repuGetResults/",
		mtype : 'GET',
		datatype : "json",
		colNames : [ 'id', 'questionId', '学校代码', '学校名称', '学科代码', 
		             '学科名称', '上次打分', '声誉评价', '保存', '简况表下载' ],
		colModel : [
			{name : 'resultId',	  index : 'resultId',	width : 100,	align : "center",  	hidden : true}, 
			{name : 'questionId', index : 'questionId',	width : 100,	align : "center",  	hidden : true}, 
			{name : 'unitId',	  index : 'unitId', 	width : 100,	align : "center",  	hidden : true},
			{name : 'unitName',	  index : 'unitName',	width : 120,	align : "center"},
			{name : 'discId',	  index : 'discId',		width : 100,	align : "center",	hidden : true},
			{name : 'discName',	  index : 'discName',	width : 120,	align : "center"}, 
			{name : 'score',	  index : 'score',	 	width : 100,	align : "center",	hidden : true}, 
			{name : 'evaluate',	  index : 'evaluate',	width : 100,	align : "center",	sortable : false}, 
			{name : 'saveButton', index : 'saveButton', width : 100, 	align : "center",	hidden : true},
			{name : 'brief',	  index : 'report',	 	width : 120,	align : "center"}
			],
		rownumbers : true,
		height : "100%",
		autowidth : true,
		pager : '#pager',
		rowNum : 15,
		rowList : [ 15 ],
		viewrecords : true,
		sortorder : "desc",
		sortname : "schoolId",

		jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
			root : "rows", //包含实际数据的数组  
			page : "pageIndex", //当前页  
			total : "totalPage",//总页数  
			records : "totalCount", //查询出的记录数  
			repeatitems : false
		},
		gridComplete : function() {
			var ids = jQuery("#repu_list").jqGrid('getDataIDs');
			var evaluateText, introDownload;
			for (var i = 1; i <= ids.length; i++) {
				var rowdata=jQuery("#repu_list").jqGrid('getRowData',i);
				var rowId = ids[i];
			    var score = rowdata.score; 
			    /* switch(score) {
			    case "A":
			    	evaluateText = "<select style='width:100px' id='evaluateText" + i +"'> <option>-</option> <option selected='selected'>A</option> <option>B</option> <option>C</option> <option>D</option> </select>";
			    	break;
			    case "B":
			    	evaluateText = "<select style='width:100px' id='evaluateText" + i +"'> <option>-</option> <option>A</option> <option selected='selected'>B</option> <option>C</option> <option>D</option> </select>";
			    	break;
			    case "C":
			    	evaluateText = "<select style='width:100px' id='evaluateText" + i +"'> <option>-</option> <option>A</option> <option>B</option> <option selected='selected'>C</option> <option>D</option> </select>";
			    	break;
			    case "D":
			    	evaluateText = "<select style='width:100px' id='evaluateText" + i +"'> <option>-</option> <option>A</option> <option>B</option> <option>C</option> <option selected='selected'>D</option> </select>";
			    	break;
			    default: 
			    	evaluateText = "<select style='width:100px' id='evaluateText" + i +"'> <option>-</option> <option>A</option> <option>B</option> <option>C</option> <option>D</option> </select>";
			    	break;
			    } */
				introDownload = "<a href='#' onclick='' id='download_brief_report_btn'>简况表.pdf</a>";
				button = "<input type='button' class='saveButton' value='保存' id='button"+ i + "'/>";
				evaluateText = "<input type='text' id='evaluateText" + i + "' value='" + score + "'>";
				jQuery("#repu_list").jqGrid('setRowData',ids[i-1],{brief:introDownload});
				jQuery("#repu_list").jqGrid('setRowData',ids[i-1],{evaluate:evaluateText});
			}
		},
		onPaging: function() {
			//wholeSave();
		},
		caption : "学科声誉打分"
	}).navGrid('#pager', {edit : true,add : false,del : false});
						
	$('#repu_list').on("click", "#download_brief_report_btn", function() {
		var discId = $(this).closest("td").prev().prev().prev().prev().text();
		var unitId = $(this).closest("td").prev().prev().prev().prev().prev().prev().text();
		downloadBriefSheet(unitId, discId);		
	});
	
	var contextPath="${ContextPath}";//绝对路径
	$("#download_brief_report_btn").click(function() {
		console.log("download brief sheet");
		var url = '${ContextPath}/evaluation/progress/reputation_briefsheet/'+unitId+"/"+discId; 
		//alert(url);
		outputJS(url);
		event.preventDefault();
	});
	
	function downloadBriefSheet(unitId, discId){
		var url = '${ContextPath}/evaluation/progress/reputation_briefsheet/'+unitId+"/"+discId; 
		//alertalert(url);
		outputJS(url);	
	}
	
	$("#save").click(function() {
		wholeSave(true, null);
	});
	
	function openLink(url) {
		wholeSave(false, url);
	}
	
	function wholeSave(clickFromSaveButton, url) {
		$(".process_dialog").show();
		$('.process_dialog').dialog({
			position : 'center',
			modal:true,
			autoOpen : true,
		});
		
		var ids = $("#repu_list").jqGrid('getDataIDs');
		var results = [];
		for(var i = 0; i < ids.length; i++) {
			var rowId = ids[i];
			var jqGridData = $("#repu_list").jqGrid('getRowData',rowId);
			
			var resultId = jqGridData.resultId;
			var oldScore = jqGridData.score;
			var questionId =jqGridData.questionId;
			var d = jqGridData.evaluate;
			console.log(d);
			var score = jQuery('#evaluateText' + rowId).val();
			console.log(score);
			
			if (!(score == oldScore)) {
	        	score = score.trim();
	        	resultId = resultId.trim();
	        	oldScore = oldScore.trim();
	        	
	        	
	        	resultFromJSP = {"resultId" : resultId, "value" : score, 
	        			"questionId" : questionId};
	        	results.push(resultFromJSP);
			}
      	}
        
        	$.ajax({ 
    	    	type : "POST", 
    	        url : "${ContextPath}/evaluation/progress/repuSaveOrUpdateResults", 
    	        dataType : "json",      
    	        contentType : "application/json",               
    	        data : JSON.stringify(results), 
    	        success:function(backData){ 
    	        	if (clickFromSaveButton) {
    	        	$("#repu_list").setGridParam({url:'${ContextPath}/evaluation/progress/repuGetResults/'}).trigger("reloadGrid");
    	        	$(".process_dialog").dialog("destroy");
    				$(".process_dialog").hide();
    				}
    	        	else {
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
	
	$("#save").click(function() {
		wholeSave();		        
	});

</script>