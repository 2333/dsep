<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type='text/css'>
       .redStyle{
       font-family: Lucida Grande,Lucida Sans,Arial,sans-serif;
       font-size: 1.1em;
       font-weight: bold;
       color: red;
       text-align: center;
       }
       
        .orangeStyle{
       font-family: Lucida Grande,Lucida Sans,Arial,sans-serif;
       font-size: 1.1em;
       font-weight: bold;
       color: orange;
       text-align: center;
       }
       
       .greenStyle{
       font-family: Lucida Grande,Lucida Sans,Arial,sans-serif;
       font-size: 1.1em;
       font-weight: bold;
       color: green;
       text-align: center;
       }
</style>
<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>打分流程与进度
	</h3>
</div>

<div class="table">
  	<table id="prog_list"></table>
</div>


<script type="text/javascript">
	var currentBatchId = "";
	var expertId = "";
	/*
	 * ★★
	 * ！合并单元格的js方法！
	 * 之所以要把colModelndex中的index这个属性也加入进来，是为了方便构造JSON对象
	 * colModelndex中的index属性是为了合并单元格而设立的，
	 * EvalProgressRowVM内置的display属性为数字时表示合并单元格的个数，为"none"时表明此时这项不显示
	 * 原来是colModelndex中的name属性承担这项显示工作，但是这样给后台构建JSON带来困难
	 * 现在加入index属性，name属性只要设置为String即可，
	 * 而index属性设置为一个类，内部维护String类型display的显示信息
	 */
	attrSetting = function (rowId, val, rawObject, cm) {
		var attr = rawObject.attr[cm.index], result = '';
		if (attr.display != "none") 
			result = ' rowspan=' + '"' + attr.display + '"';
		else 
			result = ' style="display:' + attr.display + '"';
		return result;
	};
				    
	createTasks();
	var firstLoad = true;
	var taskNum = 1;
	// 这里Tasks相当于对一类题目的总称
	function createTasks() {
		$("#prog_list").jqGrid({
			url: '${ContextPath}/evaluation/progressShowBatches/',
			mtype : 'GET',
			datatype : "json",
			colNames : ['batchId', 'expertId', '任务名称（点击名称查看具体打分项目）'],
			/*★★注意！
			colModel的name和index的字段名要和后台传递来的JSON串的名字一致
			即后台VM封装的实体的属性名应该叫做aName、aNameIndex等
			而且其中aNameIndex中的属性名应该与attrSetting()中的attr.display的dispaly叫一样的名称
			*/
			colModel : [ 
				 {name : 'batchId',		index : 'batchId',		width : 70,		align : "center",		hidden : true}, 
				 {name : 'expertId',	index : 'expertId',		width : 70,		align : "center",		hidden : true}, 
				 {name : 'batchName',	index : 'batchName',	width : 200,	align : "center", 		cellattr: addCellAttr}],
			cmTemplate : {sortable : false},
			height : '100%',
			autowidth : true,
			rowNum : 30,
			viewrecords : true,
			sortorder : "desc",
			caption : "评价任务列表",
			rownumbers : false,
			onSelectRow: function (rowId) {
			    $("#prog_list").jqGrid ('toggleSubGridRow', rowId);
			},
			gridComplete : function() {
				var rowIds = $("#prog_list").getDataIDs();
				if (rowIds.length == 1) {
					$("#" + rowIds[0]).trigger("click");
				}
				
			},
			subGrid : true,
			//subGridOptions: {expandOnLoad: true},
			subGridRowExpanded: function(subgrid_id, row_id) {
				currentBatchId = $('#prog_list').getCell(row_id, 'batchId');
				expertId = $('#prog_list').getCell(row_id, 'expertId');
				var subgrid_table_id, pager_id;
				subgrid_table_id = subgrid_id+"_t";
				pager_id = "p_"+subgrid_table_id;

				$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
				$("#"+subgrid_table_id).jqGrid({
					url: '${ContextPath}/evaluation/progressGetData/' + currentBatchId  + "/" + expertId,
					mtype : 'GET',
					datatype : "json",
					colNames : ['打分名称', '完成进度','打分子项', '进度状态', 'cLink', '打分'],
					/*★★注意！
					colModel的name和index的字段名要和后台传递来的JSON串的名字一致
					即后台VM封装的实体的属性名应该叫做aName、aNameIndex等
					而且其中aNameIndex中的属性名应该
					与attrSetting()中的attr.display的dispaly叫一样的名称
					*/
					colModel : [ 
			             {name : 'aName',	index : 'aNameIndex',	width : 90,	align : "center",	cellattr : attrSetting}, 
						 {name : 'aPCT',	index : 'aPCTIndex',	width : 70,		align : "center",	cellattr : attrSetting, 	hidden : true}, 
						 {name : 'bName',	index : 'bNameIndex',	width : 180,	align : "center",	cellattr : attrSetting}, 
						 {name : 'bPCT',	index : 'bPCTIndex',	width : 70,		align : "center",	cellattr : attrSetting}, 
						 {name : 'cLink',	index : 'cLink',		width : 200,	align : "center",	hidden : true},
						 {name : 'link',	index : 'link',			width : 200,	align : "center"}],
					cmTemplate : {sortable : false},
					height : '100%',
					autowidth : true,
					rowNum : 30,
					viewrecords : true,
					sortorder : "desc",
					caption : "打分流程与进度",
					rownumbers : false,
					gridComplete: function(){
						
						var ids = jQuery("#"+subgrid_table_id).jqGrid('getDataIDs');
						for (var i = 0; i < ids.length; i++) {
							var rowId = ids[i];
							var rowdata=jQuery("#"+subgrid_table_id).jqGrid('getRowData',rowId);
						    var cLink = rowdata.cLink; 
						    // 加上该任务的编号和已选专家的编号
						    var myLink =  "<a href='#' onclick='openLink(\"" 
						    		+ cLink + currentBatchId + "\/" 
						    		+ expertId+"\");return false;'>请点击链接前往打分</a>";
				    		var bPCT = rowdata.bPCT;
						    if (bPCT == "完成，未提交"){
	    				    	jQuery("#"+subgrid_table_id).jqGrid('setCell',rowId, 'bPCT', '', 'orangeStyle');
	    				    } else if (bPCT == "已提交"){
	    				    	jQuery("#"+subgrid_table_id).jqGrid('setCell',rowId, 'bPCT', '', 'greenStyle');
	    				    } else {
	    				    	jQuery("#"+subgrid_table_id).jqGrid('setCell',rowId, 'bPCT', '', 'redStyle');
	    				    }
							jQuery("#"+subgrid_table_id).jqGrid('setRowData', ids[i], {link:myLink});
						}
						//alert("#" + taskNum++ + "trigger")
						//$("#" + (taskNum)).trigger("click"); 
						//var ids2 = jQuery("#prog_list").jqGrid('getDataIDs');
						//alert(ids2[1]);
						//$("#" + ids2[1] + " td").trigger("click");
					},
				});
			}
			});
		}
	
	function addCellAttr(rowId, val, rawObject, cm, rdata) {
	    return "style='font-weight:bold; font-size:16px; color:black'";
	};
	
	function openLink(url) {
		if (url.trim() == "") return;
		$.post(url, function(data) {
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		  }, 'html');
	}
</script>

