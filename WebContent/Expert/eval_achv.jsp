<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${ContextPath}/js/expert.common.js"></script>
<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>学科成果评价
	</h3>
</div>

<div class="table">
	<table id="jqGrid_collect_tb"></table>
	<div id="pager_collect_tb"></div>
</div>

<input id="convertedSubQuestionId" type="hidden" value="${convertedSubQuestionId}"/>
<input id="subQuestionId" type="hidden" value="${subQuestionId}"/>
<input id="collectId1" type="hidden" value="${collectId1}"/>
<input id="collectId2" type="hidden" value="${collectId2}"/>
<input id="collectId3" type="hidden" value="${collectId3}"/>
<input id="collectId1Name" type="hidden" value="${collectId1Name}"/>
<input id="collectId2Name" type="hidden" value="${collectId2Name}"/>
<input id="collectId3Name" type="hidden" value="${collectId3Name}"/>
<input id="collectName" type="hidden" value="${collectName}"/>

<input id="prevQuestionRoute" type="hidden" value="${prevQuestionRoute}"/> 
<input id="nextQuestionRoute" type="hidden" value="${nextQuestionRoute}"/> 
 
<div id="myDialog">
</div>
<!-- 导航箭头 -->
<jsp:include page="eval_navi.jsp"/>

<!-- 此处显示专家要打分的类别，如"团队","专家","论文"等 -->
<div id="achv_content">
	<ul>
		<c:if test="${!empty itemName}">
			<li id="itemName">
				<a class="tt" href="#">${itemName}</a>
			</li>
		</c:if>
	</ul>
 	<div id="dsep_thesis" class="cur" >
 		<table id="achv_list"></table> 
 		<div id="achievement_pager"></div> 
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

<script type="text/javascript">
	$("input[type=submit], a.button, button").button();
	$(".process_dialog").hide();
	
	// "团队","专家","论文"tabs风格
	$("#achv_content").tabs({
		beforeLoad: function( event, ui ) {
	      	event.preventDefault();
	      	return;
		},
		create: function( event, ui ) {
			event.preventDefault();
		    return;	
		},
		load: function( event, ui ) {
			event.preventDefault();
		    return;	
		}
	});
	
	var collectId1 = $('input#collectId1').val();
	var collectId2 = $('input#collectId2').val();
	var collectId3 = $('input#collectId3').val();
	var collectId1Name = $('input#collectId1Name').val();
	var collectId2Name = $('input#collectId2Name').val();
	var collectId3Name = $('input#collectId3Name').val();
	var collectName = $('input#collectName').val();
	var subQuestionId = $("#convertedSubQuestionId").val();
	
	$("#achv_list").jqGrid({
		ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },
		url: '${ContextPath}/evaluation/progress/achvGetResults/' + subQuestionId,
		mtype : 'GET',
		datatype : "json",
		colNames : [ 'resultId', 'questionId', '学科代码', '学科', '学校代码', '学校', '参考数据项', '评价', '分数'],//, '保存'],
		colModel : [
			{name : 'resultId',	    index : 'resultId',		width : 100,	align : "center", hidden: true}, 
			{name : 'questionId',	index : 'questionId',	width : 100,	align : "center", hidden: true}, 
			{name : 'discId',	    index : 'discId',	    width : 100,	align : "center", hidden: true}, 
			{name : 'discName',		index : 'discName',		width : 100,	align : "center", sortable:false}, 
			{name : 'unitId',		index : 'unitId',		width : 100,	align : "center", hidden: true}, 
			{name : 'unitName',		index : 'unitName',		width : 100,	align : "center", sortable:false}, 
			{name : 'dataItem',		index : 'dataItem',		width : 100,	align : "center", sortable:false}, 
			{name : 'score',		index : 'score',		width : 100,	align : "center", hidden: true}, 
			{name : 'evaluate',		index : 'evaluate',		width : 100,	align : "center", sortable:false},	
		],
		cmTemplate : {sortable : false},
		height : '100%',
		autowidth : true,
		rowNum : 15,
		rowList : [ 15, 20, 25 ],
		viewrecords : true,
		sortorder : "desc",
		sortname : "UNIT_ID",
		pager : "#achievement_pager",
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
			var ids = jQuery("#achv_list").jqGrid('getDataIDs');
			for (var i = 0; i < ids.length; i++) {
				var rowId = ids[i];
				var rowdata=jQuery("#achv_list").jqGrid('getRowData',rowId);
			    var score = rowdata.score; 
			    var showData,evaluateText;
			    /* switch(score) {
			    case "A":
			    	evaluateText = "<select style='width:100px' id='evaluateText" + i + "' class='saveRow'> <option>-</option> <option selected='selected'>A</option> <option>B</option> <option>C</option> <option>D</option> </select>";
			    	break;
			    case "B":
			    	evaluateText = "<select style='width:100px' id='evaluateText" + i + "' class='saveRow'> <option>-</option> <option>A</option> <option selected='selected'>B</option> <option>C</option> <option>D</option> </select>";
			    	break;
			    case "C":
			    	evaluateText = "<select style='width:100px' id='evaluateText" + i + "' class='saveRow'> <option>-</option> <option>A</option> <option>B</option> <option selected='selected'>C</option> <option>D</option> </select>";
			    	break;
			    case "D":
			    	evaluateText = "<select style='width:100px' id='evaluateText" + i + "' class='saveRow'> <option>-</option> <option>A</option> <option>B</option> <option>C</option> <option selected='selected'>D</option> </select>";
			    	break;
			    default: 
			    	evaluateText = "<select style='width:100px' id='evaluateText" + i + "' class='saveRow'> <option>-</option> <option>A</option> <option>B</option> <option>C</option> <option>D</option> </select>";
			    	break;
			    }*/
			    evaluateText = "<input class='text' id='evaluateText" + rowId + "' value='" + score + "'>";
				showData = "<a class='showData' href='#' onclick=\"openDialog(this);\">点击显示数据项</a>"; 
				jQuery("#achv_list").jqGrid('setRowData', ids[i], {dataItem:showData});
				//jQuery("#achv_list").jqGrid('setRowData', ids[i], {evaluate:evaluateText});
			    jQuery("#achv_list").jqGrid('setRowData', ids[i], {evaluate:evaluateText});
			}	
		},
		onPaging: function() {
			/* var pagerId = this.p.pager.substr(1); // ger paper id like "pager"
		    var newValue = $('input.ui-pg-input', "#pg_" + $.jgrid.jqID(pagerId)).val();
			alert(newValue); */
			// ！！这里要重写onPaging方法！！IMPORTANT!!;
			//！！！！！！wholeSave();
		},
	}).navGrid('#achievement_pager');
	
	
	var scoreRecorder = "";
	var currentRow;
	var str1 = "";
	if (collectId2 == "" && collectId3 == "") {
		
	} else {
		if (collectId3 == "") {
			str1 = "<span class='TextBold' style=''>点击下拉框可查看其它学科成果：</span><select style='width:150px;' onchange='switchCollectId(this);'><option value ='" + collectId1 + "'>" + collectId1Name + "</option><option value ='" + collectId2 + "'>" + collectId2Name + "</option></select><br>";
		} else {
			str1 = "<select style='width:150px;float:right;' onchange='switchCollectId(this);'><option value ='" + collectId1 + "'>" + collectId1Name + "</option><option value ='" + collectId2 + "'>" + collectId2Name + "</option><option value='" + collectId3 + "'>" + collectId3Name + "</option></select><br><span class='TextBold' style='float:right;'>点击下拉框可查看其它学科成果：</span>";
		}
	}
	
	
	var str2 = "";
	
	var selectedDiscId = "";
	var selectedUnitId = "";
	function openDialog(param) {
		currentRow = $(param);
		selectedDiscId = currentRow.closest("td").prev().prev().prev().prev().text();
		selectedUnitId = currentRow.closest("td").prev().prev().text();
		console.log(selectedUnitId);
		var scoreDropDown = currentRow.closest("td").next().next().children(0)[0];
		var score = scoreDropDown.value;
		console.log(score);
		/*var score = scoreDropDown.options[scoreDropDown.selectedIndex].value;
		if (score == "" || score == "-") {
			str2 =  "<select style='width:100px;float:right;' id='evaluateText' onchange='passValue(this);'><option value='-'>-</option><option value='A'>A</option><option value='B'>B</option><option value='C'>C</option><option value='D'>D</option> </select><h3 style='float:right;'>您可以在此选择评价：</h3>";
		} else {
			 switch(score) {
			    case "A":
			    	str2 = "<select style='width:100px;float:right;' id='evaluateText' onchange='passValue(this);'><option value='-'>-</option><option value='A' selected='selected'>A</option><option value='B'>B</option><option value='C'>C</option><option value='D'>D</option> </select><h3 style='float:right;'>您可以在此更改评价：</h3>"; 
			    	break;
			    case "B":
			    	str2 = "<select style='width:100px;float:right;' id='evaluateText' onchange='passValue(this);'><option value='-'>-</option><option value='A'>A</option><option value='B' selected='selected'>B</option><option value='C'>C</option><option value='D'>D</option> </select><h3 style='float:right;'>您可以在此更改评价：</h3>"; 
			    	break;
			    case "C":
			    	str2 = "<select style='width:100px;float:right;' id='evaluateText' onchange='passValue(this);'><option value='-'>-</option><option value='A'>A</option><option value='B'>B</option><option value='C'  selected='selected'>C</option><option value='D'>D</option> </select><h3 style='float:right;'>您可以在此更改评价：</h3>"; 
			    	break;
			    case "D":
			    	str2 = "<select style='width:100px;float:right;' id='evaluateText' onchange='passValue(this);'><option value='-'>-</option><option value='A'>A</option><option value='B'>B</option><option value='C'>C</option><option value='D' selected='selected'>D</option> </select><h3 style='float:right;'>您可以在此更改评价：</h3>"; 
			    	break;
			 }
		} */
		str2 = "<input style='width:100px;float:right;' id='evaluateText' onchange='passValue(this);' value='" + score + "'><h3 style='float:right;'>您可以在此更改评价：</h3>";
		url = "${ContextPath}/evaluation/progress/achvShowUnitAchv/" + selectedDiscId + "/" + selectedUnitId + "/" + collectId1;
		$.post(url, function(data) {
			$('#dialog').empty();
			if (str1 != "") $("#dialog").append(str1);
			$("#dialog").append(data);
			$("#dialog").append(str2);
			$('#dialog').dialog({
				title : collectName + "的详细信息",
				autoheight: true,
				width: window.screen.availWidth*0.92,			
				position : 'top',
				modal : true,			
				draggable : true,
				hide : 'fade',			
				show : 'fade',
				autoOpen : true,
				buttons : {
					"关闭" : function() {
						$("#dialog").dialog("close");
						$('#dialog').empty();
					}
				}
			});
		}, 'html');
	};
	
	passValue = function(dropdown) {
		//scoreRecorder = dropdown.options[dropdown.selectedIndex].value;
		scoreRecorder = dropdown.value;
		$("#dialog").dialog("close");
		$("#dialog").empty();
		currentRow.closest("td").next().next().children(0).val(scoreRecorder);
	};
	
	switchCollectId = function(sel) {
		var selectedCollectId = sel.options[sel.selectedIndex].value;
		refreshDialog(selectedDiscId, selectedUnitId, selectedCollectId);
	};
	
	refreshDialog = function(selectedDiscId, selectedunitId, selectedCollectId) {
		url = "${ContextPath}/evaluation/progress/achvShowUnitAchv/" + selectedDiscId + "/" + selectedUnitId + "/" + selectedCollectId;
		$.post(url, function(data) {
			//$("#dialog").dialog("close");
			$('#dialog').empty();
			
			if (selectedCollectId == collectId1) {
				if (collectId3 == "") {
					str1 = "<span class='TextBold' style=''>点击下拉框可查看其它学科成果：</span><select style='width:150px;' onchange='switchCollectId(this);'><option value ='" + collectId1 + "'>" + collectId1Name + "</option><option value ='" + collectId2 + "'>" + collectId2Name + "</option></select><br>";
				} else {
					str1 = "<span class='TextBold' style='float:right;'>点击下拉框可查看其它学科成果：</span><select style='width:150px;' onchange='switchCollectId(this);'><option value ='" + collectId1 + "'>" + collectId1Name + "</option><option value ='" + collectId2 + "'>" + collectId2Name + "</option><option value='" + collectId3 + "'>" + collectId3Name + "</option></select><br>";
				}
			} 
			// 把collectId2排在下拉框最前面
			else if (selectedCollectId == collectId2) {
				if (collectId3 == "") {
					str1 = "<span class='TextBold' style=''>点击下拉框可查看其它学科成果：</span><select style='width:150px;' onchange='switchCollectId(this);'><option value ='" + collectId2 + "'>" + collectId2Name + "</option><option value ='" + collectId1 + "'>" + collectId1Name + "</option></select><br>";
				} else {
					str1 = "<span class='TextBold' style='float:right;'>点击下拉框可查看其它学科成果：</span><select style='width:150px;' onchange='switchCollectId(this);'><option value ='" + collectId2 + "'>" + collectId2Name + "</option><option value ='" + collectId1 + "'>" + collectId1Name + "</option><option value='" + collectId3 + "'>" + collectId3Name + "</option></select><br>";
				}
			} 
			// 把collectId3排在下拉框最前面
			else if (selectedCollectId == collectId3) {
				str1 = "<span class='TextBold' style='float:right;'>点击下拉框可查看其它学科成果：</span><select style='width:150px;' onchange='switchCollectId(this);'><option value ='" + collectId3 + "'>" + collectId3Name + "</option><option value ='" + collectId1 + "'>" + collectId1Name + "</option><option value='" + collectId2 + "'>" + collectId2Name + "</option></select><br>";
			}
			
			if (str1 != "") $("#dialog").append(str1);
			$("#dialog").append(data);
			$("#dialog").append(str2);
			$('#dialog').dialog({
				title : collectName + "的详细信息",
				autoheight: true,
				width: window.screen.availWidth*0.92,			
				position : 'top',
				modal : true,			
				draggable : true,
				hide : 'fade',			
				show : 'fade',
				autoOpen : true,
				buttons : {
					"关闭" : function() {
						$("#dialog").dialog("close");
						$('#dialog').empty();
					}
				}
			});
		}, 'html');
	};
	
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
			modal : true,
			autoOpen : true,
		});
		var ids = $("#achv_list").jqGrid('getDataIDs');
		var results = [];
		for(var i = 0; i < ids.length; i++) {
			var rowId = ids[i];
			var jqGridData = $("#achv_list").jqGrid('getRowData',rowId);
			
			var resultId = jqGridData.resultId;
			var questionId = jqGridData.questionId;
			var oldScore = jqGridData.score;
			var score = jQuery('#evaluateText' + rowId).val();
			console.log(score);

        	score = score.trim();
        	if (score == "-") score = "";
        	resultId = resultId.trim();
        	oldScore = oldScore.trim();
        	//alert(oldScore + " " + score);
        	// 表明这次专家改变了评价，需要保存
        	if (score != oldScore) {
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
	        url : "${ContextPath}/evaluation/progress/achvSaveOrUpdateResults/" + subQuestionId, 
	        dataType : "json",      
	        contentType : "application/json",               
	        data : JSON.stringify(results), 
	        success:function(backData){ 
	        	if (clickFromSaveButton) {
	        		$("#achv_list")
	        			.setGridParam({url:'${ContextPath}/evaluation/progress/achvGetResults/'+ subQuestionId})
	        			.trigger("reloadGrid");
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
