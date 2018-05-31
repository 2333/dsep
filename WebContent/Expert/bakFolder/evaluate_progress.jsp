<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>打分流程与进度
	</h3>
</div>

<div class="cur">
  		<table id="jqGrid_collect_tb"></table>
		<div id="pager_collect_tb"></div>
</div>

<div class="table">
  	<table id="weight_list"></table>
	<div id="pager_collect_tb"></div>
</div>
<script src="${ContextPath}/js/expert.common.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("input[type=submit], a.button , button").button();
	$("#indicator_content").tabs();
					
					
	/**
	 * tab_width是第一个tab的宽，赋值给第二个tab中的jqgrid的width
	 */
	var tab_width = $("#indicator_content div.ui-tabs-panel:not(.ui-tabs-hide)").width();
	tab_width -= 2;
									
	arrtSetting = function (rowId, val, rawObject, cm) {
		/*
		 * ★★
		 * 之所以要把colModelndex中的index这个属性也加入进来，是为了方便构造JSON对象
 		 * colModelndex中的index属性是为了合并单元格而设立的，
 		 * EvalProgressRowVM内置的display属性为数字时表示合并单元格的个数，为"none"时表明此时这项不显示
 		 * 原来是colModelndex中的name属性承担这项显示工作，但是这样给后台构建JSON带来困难
 		 * 现在加入index属性，name属性只要设置为String即可，
 		 * 而index属性设置为一个类，内部维护String类型display的显示信息
		 */
		var attr = rawObject.attr[cm.index], result = '';
		if (attr.display != "none") {
			result = ' rowspan=' + '"' + attr.display + '"';
		} else {
			result = ' style="display:' + attr.display + '"';
		}
		return result;
	};
				    
	linkformatter = function (cellValue, options, rowObject) {
		if (cellValue == null || cellValue == "") {
			return "<p>请先完成前面项目的打分</p>";	
		} else {
			return "<a href='#' onclick='openLink(\"" 
					+ cellValue + "\");return false;'>请点击链接前往打分</a>";
		}
		
	};
	
	createWeight();
					
	function createWeight(){
		$("#weight_list").jqGrid({
			url: '${ContextPath}/evaluation/progressGetData/',
			mtype : 'GET',
			datatype : "json",
			colNames : ['打分名称', '完成进度','打分子项', '进度状态', '打分'],
			/*★★注意！
			colModel的name和index的字段名要和后台传递来的JSON串的名字一致
			即后台VM封装的实体的属性名应该叫做aName、aNameIndex等
			而且其中aNameIndex中的属性名应该与arrtSetting()中的attr.display的dispaly叫一样的名称
			*/
			colModel : [ 
	             {name : 'aName',	index : 'aNameIndex',	width : 100,	align : "center",	cellattr : arrtSetting}, 
				 {name : 'aPCT',	index : 'aPCTIndex',	width : 70,		align : "center",	cellattr : arrtSetting, 	hidden : true}, 
				 {name : 'bName',	index : 'bNameIndex',	width : 160,	align : "center",	cellattr : arrtSetting}, 
				 {name : 'bPCT',	index : 'bPCTIndex',	width : 70,		align : "center",	cellattr : arrtSetting}, 
				 {name : 'cLink',	index : 'cLink',		width : 200,	align : "center",	formatter : linkformatter}
				 ],
			cmTemplate : {sortable : false},
			height : '100%',
			autowidth : true,
			rowNum : 30,
			viewrecords : true,
			sortorder : "desc",
				// pager : "#pager",
				caption : "打分流程与进度",
				rownumbers: false,
									
				gridComplete : function() {
					/* var ids = jQuery("#weight_list").jqGrid('getDataIDs');
					var input;
					for ( var i = 0; i < ids.length; i++) {
						
					} */
					/* var ids = $("#weight_list").jqGrid('getDataIDs');
					var input;
					for(var i = 0; i < ids.length; i++) {
						var rowId = ids[i];
						var jqGridData = $("#weight_list").jqGrid('getRowData',rowId);
						var link = jqGridData.cLink;
						if (link.trim() != '') {
							input = "<a href='" + link + "'/>前往打分</a>";
							jQuery("#weight_list").jqGrid('setRowData', ids[i], {eval : input});
						}
					}	 */
				}
			});
		}
	
});
</script>

