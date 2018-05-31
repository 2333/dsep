<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>学科成果评价
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
<!-- 此处显示专家要打分的类别，如"团队","专家","论文"等 -->
<div id="achievement_content">
	<ul>
		<c:if test="${!empty evalitems}">
			<c:forEach items="${evalitems}" var="evalitem">
				<li id="${evalitem.key}">
					<a class="tt" href="#" title1="${evalitem.key}">${evalitem.value}</a>
				</li>
			</c:forEach>
		</c:if>
	</ul>
 	<div id="dsep_thesis" class="cur" >
 		<table id="achievement_list"></table> 
 		<div id="achievement_pager"></div> 
 	</div>
	
<!-- 	<div id="dsep_student" class='hidden'> -->
<!-- 		<table id="student_list"></table> -->
<!-- 		<div id="student_pager"></div> -->
<!-- 	</div> -->
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

<!-- 
<div class="progressbar_div">
           <table class="fr_table">                                           
                 <tr>
                    <td>
                        <div style="float:left">
                            <label class="progress"></label>
                        </div>
                    </td>
                    <td>
                        <div class="divProgressbar" style='width:800px;float:left'>
                        </div>
                    </td> 
                 </tr>
           </table>
      </div>  
<div class="selectbar">     
<a id="calculate" href="#" class="button">显示打分进度</a>
</div>
-->
<input id="process" type="hidden" value="${process}">
<!-- <div id="progressbar"></div> -->
<script type="text/javascript">
	/* $(function() {
    $( "#progressbar" ).progressbar({
      value: 37
    });
  }); */
	$("input[type=submit], a.button, button").button();
	
	// "团队","专家","论文"tabs风格
	$("#achievement_content").tabs({
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
		/* cache: false,
        ajaxOptions: {
            cache: false,
            error: function (xhr, status, index, anchor) {
            	alert(1);
                    // how do I test to handle the HTTP code 302 ? (xhr.status contains 0 here and xhr.responseText is empty!)
                if ((xhr.status == 301) || (xhr.status == 302) || (xhr.status == 303) || (xhr.status == 304) || (xhr.status == 305) || (xhr.status == 306) || (xhr.status == 307) || (xhr.status == 308)) {
                    // how do I redirect to the redirection url
                    alert(1);
                }
                else {
                    //$(anchor.hash).html("Error ...<br><br>URL : " + ??? + "<br><br>Error Code : " + xhr.status + "<br>" + xhr.responseText);
                }
            }
        } */
	});
	
	// 存储当前评价学科的entityId和questionId,用$分割
	// questionId通过转码,把字母全部转为了数字,为了防止浏览器转码
	entityIdAndQuestionId = $("#achievement_content ul li:first-child a").attr('title1');
	
	var indexOfSeparator = entityIdAndQuestionId.indexOf('$');
	var questionId = entityIdAndQuestionId.substring(0, indexOfSeparator);
	var entityId = entityIdAndQuestionId.substring(indexOfSeparator+1);
	
	var expertId = $('input#expertId').val();
	var discId = $('input#discId').val();

	$("#achievement_list").jqGrid({
		ajaxGridOptions: { contentType: 'application/json; charset=utf-8' },
		url: '${ContextPath}/evaluation/achievement_getUnits/' + questionId + '/' + expertId + '/' + discId,
		mtype : 'GET',
		datatype : "json",
		colNames : ['id', '学科代码', '学科', '学校代码', '学校', '参考数据项', '评价', '分数', '保存'],
		colModel : [
		{name : 'resultId',	    index : 'resultId',		width : 100,	align : "center", hidden: true}, 
		{name : 'discId',	    index : 'discId',	    width : 100,	align : "center", hidden: true}, 
		{name : 'discName',		index : 'discName',		width : 100,	align : "center", sortable:false}, 
		{name : 'unitId',		index : 'unitId',		width : 100,	align : "center", hidden: true}, 
		{name : 'unitName',		index : 'unitName',		width : 100,	align : "center", sortable:false}, 
		{name : 'dataItem',		index : 'dataItem',		width : 100,	align : "center", sortable:false}, 
		{name : 'score',		index : 'score',		width : 100,	align : "center", hidden: true}, 
		{name : 'evaluate',		index : 'evaluate',		width : 100,	align : "center", sortable:false},	
		{name : 'saveButton', 	index : 'saveButton', 	width : 50 }],
		cmTemplate : {sortable : false},
		height : '100%',
		autowidth : true,
		rowNum : 15,
		rowList : [ 15 ],
		viewrecords : true,
		sortorder : "desc",
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
			var ids = jQuery("#achievement_list").jqGrid('getDataIDs');
			var showData, evaluateText;
			for (var i = 1; i <= ids.length; i++) {
				var rowdata=jQuery("#achievement_list").jqGrid('getRowData',i);
			    var score = rowdata.score; 
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
				showData = "<a class='showData' href='#' onclick=\"$('#achievement_list').toggleSubGridRow('"+i+"');\">显示/隐藏数据项</a>"; 
				button = "<input type='button' class='saveButton' value='保存' id='button"+ i + "'/>";
				
				jQuery("#expert_list2").jqGrid('setRowData',ids[i],{edit:edit});
				jQuery("#achievement_list").jqGrid('setRowData',ids[i-1],{dataItem:showData});
				jQuery("#achievement_list").jqGrid('setRowData',ids[i-1],{evaluate:evaluateText});
				jQuery("#achievement_list").jqGrid('setRowData', ids[i-1], {saveButton:edit});
				//jQuery("#achievement_list").jqGrid('setRowData', ids[i-1], {saveButton:button});
			}	
		},
		onPaging: function() {
			wholeSave();
		},
						
		subGrid: true,
		subGridRowExpanded: function(subgrid_id, row_id) {
			var discId = $('#achievement_list').getCell(row_id, 'discId');
			var unitId = $('#achievement_list').getCell(row_id, 'unitId');
			//alert(discId + " " + unitId);
			var subgrid_table_id, pager_id;
			subgrid_table_id = subgrid_id+"_t";
			pager_id = "p_"+subgrid_table_id;

			$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
			
			$.getJSON("${ContextPath}/evaluation/achievement_initGrid/"+entityId, 
				function initJqTable(data) {
					$("#"+subgrid_table_id).jqGrid({
	 					url :'${ContextPath}/evaluation/achievement_collectionData/'
	 						+ entityId + '/' + unitId + '/' + discId,		
	 					editurl :'${ContextPath}/Collect/toCollect/JqOper/collectionEdit/'
	 						+ entityId + '/' + unitId + '/' + discId,
						datatype : 'json',
						mtype : 'POST',
						colModel : data.colConfigs,
						height : "100%",
						autowidth : true,
						shrinkToFit : false,
						pager : pager_id,
						pgbuttons : true,
						rowNum : 10,
						rowList : [ 10, 20, 30 ],
						viewrecords : true,
						sortname : data.defaultSortCol,
						sortorder : "asc",
						caption : data.name,
						jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
							root : "rows", //包含实际数据的数组  
							page : "pageIndex", //当前页  
							total : "totalPage",//总页数  
							records : "totalCount", //查询出的记录数  
							repeatitems : false,
						},
						loadComplete : function() {
							$("#"+subgrid_table_id).setGridWidth(		
									$("#content").width());
						},
						prmNames : {
							page : "page",
							rows : "rows",
							sort : "sidx",
							order : "sord"
						},
						onSelectRow:function(rowid){
						    
						},
						gridComplete: function(){
							//bindFunc(isEditable);
//							if(isEditable=='1') {
//						 		var ids = jQuery("#jqGrid_collect_tb").jqGrid('getDataIDs');
//						 		for(var i=0;i < ids.length;i++){
//						 			var edit_link = "<a class='' href='#' onclick='editCollectItem("+ids[i]+","+'0'+")'>编辑</a>";
//						 			var del_link="<a class='' href='#' onclick='delCollectItem("+ids[i]+","+'0'+")'>删除</a>";
//						 			jQuery("#jqGrid_collect_tb").jqGrid('setRowData',ids[i],{oper :edit_link+' | '+del_link});
//						 		}
//						 	}else{
//						 		jQuery("#jqGrid_collect_tb").setGridParam().hideCol("oper");
//						 	}
//						 	records=$("#jqGrid_collect_tb").jqGrid('getGridParam','records');//当前总记录数
//						 	sortorder=$("#jqGrid_collect_tb").jqGrid('getGridParam','sortorder');//排序方式
//						 	sortname=$("#jqGrid_collect_tb").jqGrid('getGridParam','sortname');//排序字段	
						},
						onPaging:function(){
						}
					}).navGrid('#pager_collect_tb', {
						edit : false,
						add : false,
						del : false,search:false
			});
		});
		},
		subGridRowColapsed: function(subgrid_id, row_id) {
			// this function is called before removing the data
			//var subgrid_table_id;
			//subgrid_table_id = subgrid_id+"_t";
			//jQuery("#"+subgrid_table_id).remove();
		}
	}).navGrid('#achievement_pager');
	
						
	// 点击"专家"、"团队"或者"论文"tab时，加载参评学校
 	$("#achievement_content li").click(function() {
 		
		
 		entityIdAndQuestionId = $(this).children(0).attr("title1");
 		indexOfSeparator = entityIdAndQuestionId.indexOf('$');
 		questionId = entityIdAndQuestionId.substring(0, indexOfSeparator);
 		entityId = entityIdAndQuestionId.substring(indexOfSeparator+1);
 		dataFromJSP = {"expertId":expertId, "questionId":questionId, "discId":discId};

 		
 		$("#achievement_list").setGridParam({url:'${ContextPath}/evaluation/achievement_getUnits/' + questionId + '/' + expertId + '/' + discId})
 		 		.trigger("reloadGrid",[{page:1}]);
 		
 		
 		//event.preventDefault();	
 	});
	
	$('#achievement_list').on("click", ".saveScore", function() {
		var $this = $(this);
		
		var resultId =  $this.closest("td").prev().prev().prev().prev().prev().prev().prev().prev().text();
		var score = $this.closest("td").prev().children(0).val();
		var discId = $this.closest("td").prev().prev().prev().prev().prev().prev().prev().text();
		var unitId = $this.closest("td").prev().prev().prev().prev().prev().text();
		//alert(score + discId + unitId);
        var resultFromJSP={"id":resultId,"unitId":unitId,
        		"disciplineId":discId, "expertId":"", "evalValue":score,
        		"evalQuestionId":questionId};  
        if (!(score == "-")) {
        	$this.removeClass("saveScore");
        	$this.attr('class', 'editScore');
        	$this.text('编辑');
        	$this.closest("td").prev().children(0).attr("disabled","disabled");
        	$.ajax({ 
                type : "POST", 
                url : "${ContextPath}/evaluation/achievement_saveSingleResult", 
                dataType : "json",      
                contentType : "application/json",               
                data : JSON.stringify(resultFromJSP), 
                success:function(data){ 
                } 
             }); 
        }
        
	});
	
	$('#achievement_list').on("click", ".editScore", function() {
		var $this = $(this);
		$this.closest("td").prev().children(0).removeAttr("disabled");
		$this.removeClass("editScore");
    	$this.attr('class', 'saveScore');
    	$this.text('保存');
	});
	
	// 打分
	/* function editScore(id)
	{
		evaluateText = "<select style='width:100px' id='evaluateText" + id +"'> <option>-</option> <option>A</option> <option>B</option> <option>C</option> <option>D</option> </select>";
    	edit="<a class='saveScore' href='#' onclick='editScore("+id+")'>修改</a>";
		
		jQuery("#achievement_list").jqGrid('setRowData',id,{saveButton:edit});	
		
		var score = $(this).closest("td").prev().children(0).val();
		var discId = $(this).closest("td").prev().prev().prev().prev().prev().prev().text();
		var unitId = $(this).closest("td").prev().prev().prev().prev().text();
		var resultFromJSP={"id":"","unitId":unitId,
	        		"disciplineId":discId, "expertId":"", "evalValue":score,
	        		"evalQuestionId":questionId};  
		if (!(score == "-")) {
			 $.ajax({ 
		            type : "POST", 
		            url : "${ContextPath}/evaluation/achievement_saveSingleResult", 
		            dataType : "json",      
		            contentType : "application/json",               
		            data : JSON.stringify(resultFromJSP), 
		            success:function(data){ 
		            	alert(1);    
		            } 
		         }); 
		}
	} */
	
	
	function cancelEdit(id)
	{
		jQuery("#achievement_list").jqGrid('restoreRow',id);	
		var modify = "<a class='' href='#' onclick='editScore("+id+")'>编辑</a>"; 
		jQuery("#achievement_list").jqGrid('setRowData',id,{edit :modify});
	}
	
	function saveScore(id)
	{
		jQuery("#achievement_list").saveRow(id, false, '');
		
//			var rowData = $('#expert_list2').jqGrid("getRowData",id);
//			var chooseDiscipline = rowData["chooseDiscipline"];
//			if (chooseDiscipline == "false") {
				
//			}
		var modify = "<a class='' href='#' onclick='editScore("+id+")'>编辑</a>"; 
		jQuery("#achievement_list").jqGrid('setRowData',id,{edit :modify});	
	}
	
	$("#save").click(function() {
		wholeSave();		        
	});
	
	$("#calculate").click(function(){
  	   var process = $("#process").val();
  	   console.log(process);
  	   var process2 = process*100;
  	   $(".divProgressbar").progressbar({value: process2});        
     }); 

	function wholeSave() {
		var needSaveArr = $(".saveScore");
		var results = [];
      	for(var i = 0; i < needSaveArr.length ;i++) {
      			var resultId = $(needSaveArr[i]).closest("td").prev().prev().prev().prev().prev().prev().prev().prev().text();
	        	var score = $(needSaveArr[i]).closest("td").prev().children(0).val();
	        	var discId = $(needSaveArr[i]).closest("td").prev().prev().prev().prev().prev().prev().prev().text();
	        	var unitId = $(needSaveArr[i]).closest("td").prev().prev().prev().prev().prev().text();
	        	
	        	if (!(score == "-")) {
	        		//alert(score+" "+discId+" "+unitId);
	        		resultFromJSP={"id":resultId,"unitId":unitId,
  		        		"disciplineId":discId, "expertId":"", "evalValue":score,
  		        		"evalQuestionId":questionId};
	        		results.push(resultFromJSP);
	        		//alert(results.length);
	        		}
			}
      	
      	$.ajax({ 
	            type : "POST", 
	            url : "${ContextPath}/evaluation/achievement_saveMultiResults", 
	            dataType : "json",      
	            contentType : "application/json",               
	            data : JSON.stringify(results), 
	            success:function(data){ 
	            	alert("打分已经保存");    
	            } 
	         });
	}
</script>
<!-- <script src="/DSEP/js/Expert/evaluate_achievement2.js"></script> -->
