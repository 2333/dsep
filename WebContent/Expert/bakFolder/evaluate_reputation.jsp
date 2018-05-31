<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>学科声誉评价
	</h3>
</div>
<input id="expertId" type="hidden" value="${expertId}"/> 
<input id="discId" type="hidden" value="${discId}"/>
<input id="reputationQuestionId" type="hidden" value="${reputationQuestionId}"/>
<!-- onPaging: function() {
			wholeSave();
		},onPaging: function() {
			wholeSave();
		},
<div class="selectbar">
	<span class="TextFont">学校</span> <select id="school">
		<option value="" selected="selected">全部</option>
		<c:if test="${!empty colleges}">
			<c:forEach items="${colleges}" var="college">
				<option value="${college.key }">${college.value}</option>
			</c:forEach>
		</c:if>
	</select> 
	<span class="TextFont">学科</span> <select id="dsep" name=dsep>
		<option value="" selected="selected">全部</option>
		<c:if test="${!empty disciplines}">
			<c:forEach items="${disciplines}" var="discipline">
				<option value="${discipline.key }">${discipline.value}</option>
			</c:forEach>
		</c:if>
	</select>
	<a id="search" class="button" href="#"><span class="icon icon-search "></span>查找</a>
	<a id="viewThisProgress" class="button" href="#"><span class="icon icon-search "></span>查看声誉评价打分进度</a>
	<a id="viewWholeProgress" class="button" href="#"><span class="icon icon-search "></span>查看总体打分进度</a>
</div>-->

<input id="expertId" type="hidden" value="${expertId}"/> 
<input id="discId" type="hidden" value="${discId}"/>
<input id="prevQuestionRoute" type="hidden" value="${prevQuestionRoute}"/> 
<input id="nextQuestionRoute" type="hidden" value="${nextQuestionRoute}"/> 
<div id="myDialog">
</div>

<div class="selectbar" style="overflow: hidden">
	<table class="layout_table right">
		<tbody>
			<tr>
				<td width="135" style='<c:if test="${empty prevQuestionRoute}">visibility:hidden;</c:if>'>
					<a class="button" href="#" onclick="openLink('${prevQuestionRoute}');return false;">
						<span class="icon icon-left"></span>
						上一项打分
					</a>
				</td>
				<td width="135">
					<a class="button" href="#" onclick="openLink('${homeRoute}');return false;">
						<span class="icon icon-home"></span>
						返回流程页
					</a>
				</td>
				<td width="135">
					<a id="nextQuestionRoute1" class="button" href="#" onclick="openLink('${nextQuestionRoute}');return false;">
						<c:if test="${not empty nextQuestionRoute}">
						<span class="icon icon-right "></span>
						下一项打分
						</c:if>
						<c:if test="${empty nextQuestionRoute}">
						<span class="icon icon-del "></span>
						该页未完成
						</c:if>
					</a>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<div class="cur"><!--class="selectbar layout_holder">  -->
	<table id="reputation_list"></table>
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

<div class="selectbar" style="overflow: hidden">
	<table class="layout_table right">
		<tbody>
			<tr>
				<td width="135" style='<c:if test="${empty prevQuestionRoute}">visibility:hidden;</c:if>'>
					<a class="button" href="#" onclick="openLink('${prevQuestionRoute}');return false;">
						<span class="icon icon-left"></span>
						上一项打分
					</a>
				</td>
				<td width="135">
					<a class="button" href="#" onclick="openLink('${homeRoute}');return false;">
						<span class="icon icon-home"></span>
						返回流程页
					</a>
				</td>
				<td width="135">
					<a id="nextQuestionRoute2" class="button" href="#" onclick="openLink('${nextQuestionRoute}');return false;">
						<c:if test="${not empty nextQuestionRoute}">
						<span class="icon icon-right "></span>
						下一项打分
						</c:if>
						<c:if test="${empty nextQuestionRoute}">
						<span class="icon icon-del "></span>
						该页未完成
						</c:if>
					</a>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<div class="process_dialog">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 正在保存……
</div>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script type="text/javascript">
$("#search").click(function() {
	var schoolid = $("#school").val();
	var dsepid = $("#dsep").val();
	/* var status = $("#collectStatus").val(); */
	$("#reputation_list").setGridParam({url : "${ContextPath}/progress/evaluation/getreputationdata?schoolid="
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
	});
						
	$("input[type=submit], a.button , button").button();
	/********************  jqGrid ***********************/
	var schoolid = $("#school").val();
	var dsepid = $("#dsep").val();
	
	var expertId = $('input#expertId').val();
	var discId = $('input#discId').val();
	var reputationQuestionId = $('input#reputationQuestionId').val();
	
	
	$("#reputation_list").jqGrid({
		ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },
		url : "${ContextPath}/evaluation/progress/reputation_getUnits/" + reputationQuestionId + "/" + discId,
		mtype : 'GET',
		datatype : "json",
		colNames : [ 'id', '学校代码', '学校名称', '学科代码', 
		             '学科名称', '声誉评价', '保存', '简况表' ],
		colModel : [
			{name : 'resultId',	 index : 'resultId',	width : 100,	align : "center", hidden: true}, 
			{name : 'unitId',	 index : 'unitId', 		align : "center", 		width : 100},
			{name : 'unitName',	 index : 'unitName',	align : "center", width : 120},
			{name : 'discId',	 index : 'discId',		align : "center", width : 100},
			{name : 'discName',	 index : 'discName',	align : "center", width : 120}, 
			{name : 'score',	 index : 'score',	 	width : 100,	  align : "center",	sortable : false}, 
			{name : 'saveButton',index : 'saveButton', 	width : 50, hidden: true},
			{name : 'brief',	 index : 'report',	 	width : 100,	  align : "center"}
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
			var ids = jQuery("#reputation_list").jqGrid('getDataIDs');
			var evaluateText, introDownload;
			for (var i = 1; i <= ids.length; i++) {
				var rowdata=jQuery("#reputation_list").jqGrid('getRowData',i);
			    var score = rowdata.score; 
			    //alert(score);
			    switch(score) {
			    case "A":
			    	evaluateText = "<select disabled='disabled' style='width:100px' id='evaluateText" + i +"'> <option>-</option> <option selected='selected'>A</option> <option>B</option> <option>C</option> <option>D</option> </select>";
			    	edit="<a class='editScore' href='#' onclick=''>编辑</a>";
			    	break;
			    case "B":
			    	evaluateText = "<select disabled='disabled' style='width:100px' id='evaluateText" + i +"'> <option>-</option> <option>A</option> <option selected='selected'>B</option> <option>C</option> <option>D</option> </select>";
			    	//edit="<a class='editScore' href='#' onclick='editScore("+ids[i-1]+")'>编辑</a>";
			    	edit="<a class='editScore' href='#' onclick=''>编辑</a>";
			    	break;
			    case "C":
			    	evaluateText = "<select disabled='disabled' style='width:100px' id='evaluateText" + i +"'> <option>-</option> <option>A</option> <option>B</option> <option selected='selected'>C</option> <option>D</option> </select>";
			    	edit="<a class='editScore' href='#' onclick=''>编辑</a>";
			    	break;
			    case "D":
			    	evaluateText = "<select disabled='disabled' style='width:100px' id='evaluateText" + i +"'> <option>-</option> <option>A</option> <option>B</option> <option>C</option> <option selected='selected'>D</option> </select>";
			    	edit="<a class='editScore' href='#' onclick=''>编辑</a>";
			    	break;
			    default: 
			    	evaluateText = "<select style='width:100px' id='evaluateText" + i +"'> <option>-</option> <option>A</option> <option>B</option> <option>C</option> <option>D</option> </select>";
			    	edit="<a class='saveScore' href='#' onclick=''>保存</a>";
			    	break;
			    }
				//showData = "<a class='showData' href='#' onclick=\"$('#achievement_list').toggleSubGridRow('"+i+"');\">显示/隐藏数据项</a>"; 
				introDownload = "<a href='#' onclick='' id='download_brief_report_btn'>简况表.pdf</a>";
				button = "<input type='button' class='saveButton' value='保存' id='button"+ i + "'/>";
				
				//jQuery("#expert_list2").jqGrid('setRowData',ids[i],{edit:edit});
				jQuery("#reputation_list").jqGrid('setRowData',ids[i-1],{brief:introDownload});
				jQuery("#reputation_list").jqGrid('setRowData',ids[i-1],{score:evaluateText});
				jQuery("#reputation_list").jqGrid('setRowData', ids[i-1], {saveButton:edit});
				
			}
		},
		onPaging: function() {
			wholeSave();
		},
		caption : "学科声誉打分"
	}).navGrid('#pager', {edit : true,add : false,del : false});
						
	$('#reputation_list').on("click", "#download_brief_report_btn", function() {
		var discId = $(this).closest("td").prev().prev().prev().prev().text();
		var unitId = $(this).closest("td").prev().prev().prev().prev().prev().prev().text();
		//alert(discId + " " + unitId);
		downloadBriefSheet(unitId, discId);		
	});
	
	/* $("#reputation_list").jqGrid('sortableRows'); */
	
	
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
	
	
	$('#reputation_list').on("click", ".saveScore", function() {
		var $this = $(this);
		
		var resultId =  $this.closest("td").prev().prev().prev().prev().prev().prev().text();
		var score = $this.closest("td").prev().children(0).val();
		var discId = $this.closest("td").prev().prev().prev().text();
		var unitId = $this.closest("td").prev().prev().prev().prev().prev().text();
		//alert(score + discId + unitId);
        var resultFromJSP={"id":resultId,"unitId":unitId,
        		"disciplineId":discId, "expertId":"", "evalValue":score,
        		"evalQuestionId":reputationQuestionId};  
        if (!(score == "-")) {
        	$this.removeClass("saveScore");
        	$this.attr('class', 'editScore');
        	$this.text('编辑');
        	$this.closest("td").prev().children(0).attr("disabled","disabled");
        	$.ajax({ 
                type : "POST", 
                url : "${ContextPath}/evaluation/reputation_saveSingleResult", 
                dataType : "json",      
                contentType : "application/json",               
                data : JSON.stringify(resultFromJSP), 
                success:function(data){ 
                } 
             }); 
        }
        
	});
	
	$('#reputation_list').on("click", ".editScore", function() {
		var $this = $(this);
		$this.closest("td").prev().children(0).removeAttr("disabled");
		$this.removeClass("editScore");
    	$this.attr('class', 'saveScore');
    	$this.text('保存');
	});
	
	function wholeSave() {
		var ids = $("#reputation_list").jqGrid('getDataIDs');
		var results = [];
		for(var i = 0; i < ids.length; i++) {
			var rowId = ids[i];
			var jqGridData = $("#reputation_list").jqGrid('getRowData',rowId);
			
			var resultId = jqGridData.resultId;
			var oldScore = jqGridData.score;
			var unitId = jqGridData.unitId;
			// 理应整个页面都是针对一个disc学科打分的，这里discId2表明从jqGrid中取出的数据，上文discId是从后台拿到的数据
			var discId2 = jqGridData.discId;
			var score = jQuery('#evaluateText' + rowId).val();

			if (!(score == oldScore)) {
	        	score = score.trim();
	        	resultId = resultId.trim();
	        	oldScore = oldScore.trim();
	        	if (score == "-") score = "";
	        	
	        	//alert(rowId + " " + resultId + " " + unitId + " " + discId2 + " old:" + oldScore + " new:"  + score); 	
	        	
	        	resultFromJSP = 
	        	{"id" : resultId,	"unitId" : unitId,	 "disciplineId" : discId2, 
	        	 "expertId" : "", 	"evalValue" : score, "evalQuestionId" : questionId,
	        	 "oldEvalValue" : oldScore};
	        	
	        	results.push(resultFromJSP);
			}
      	}
      	
      	$.ajax({ 
	            type : "POST", 
	            url : "${ContextPath}/evaluation/progress/achievement_saveMultiResults", 
	            dataType : "json",      
	            contentType : "application/json",               
	            data : JSON.stringify(results), 
	            success:function(backData){ 
	            	$("#achievement_list").setGridParam({url:'${ContextPath}/evaluation/progress/achievement_getUnits/'+ questionId + '/' + expertId + '/' + discId}).trigger("reloadGrid");
	            	//alert(backData[0].nextQuestionRoute); 
	            	createNextQuestionRouteEle(backData[0].nextQuestionRoute);
	            	//alert("herehere");
	            	
	            } 
	         });
		var needSaveArr = $(".saveScore");
		var results = [];
      	for(var i = 0; i < needSaveArr.length ;i++) {
      			var resultId = $(needSaveArr[i]).closest("td").prev().prev().prev().prev().prev().prev().text();
	        	var score = $(needSaveArr[i]).closest("td").prev().children(0).val();
	        	var discId = $(needSaveArr[i]).closest("td").prev().prev().prev().text();
	        	var unitId = $(needSaveArr[i]).closest("td").prev().prev().prev().prev().prev().text();
	        	
	        	if (!(score == "-")) {
	        		$(needSaveArr[i]).removeClass("saveScore");
	        		$(needSaveArr[i]).attr('class', 'editScore');
	        		$(needSaveArr[i]).text('编辑');
	        		$(needSaveArr[i]).closest("td").prev().children(0).attr("disabled","disabled");
	        		//alert(score+" "+discId+" "+unitId);
	        		resultFromJSP={"id":resultId,"unitId":unitId,
  		        		"disciplineId":discId, "expertId":"", "evalValue":score,
  		        		"evalQuestionId":reputationQuestionId};
	        		results.push(resultFromJSP);
	        		//alert(results.length);
	        		}
			}
      	
      	$.ajax({ 
	            type : "POST", 
	            url : "${ContextPath}/evaluation/reputation_saveMultiResults", 
	            dataType : "json",      
	            contentType : "application/json",               
	            data : JSON.stringify(results), 
	            success:function(data){ 
	            	alert("打分已经保存");    
	            } 
	         });
	}
	$("#save").click(function() {
		wholeSave();		        
	});

</script>