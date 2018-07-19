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
		<span class="icon icon-web"></span>提交页面
	</h3>
</div>

<div class="selectbar" style="overflow: hidden">
	<table class="layout_table right">
		<tbody>
			<tr>
				<td width="135">
					<a class="button" href="#" onclick="openHomeLink('${homeRoute}');return false;">
						<span class="icon icon-home"></span>
						返回流程页
					</a>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<div class="process_dialog">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 正在提交……
</div>

<input id="currentBatchId" type="hidden" value="${currentBatchId}"/>
<input id="expertId" type="hidden" value="${expertId}"/>

<div class="table">
  	<table id="submit_list"></table>
</div>

<div class="selectbar" style="overflow: hidden">
	<table class="layout_table right">
		<tbody>
			<tr>
				<td width="90">
					<a id="submit" class="button" href="#">
						<span class="icon icon-submit"></span>
						提交
					</a>
				</td>
			</tr>
		</tbody>
	</table>
</div>


<script type="text/javascript">
	$(".process_dialog").hide();
	$("input[type=submit], a.button, button").button();
	var currentBatchId = $('input#currentBatchId').val();
	var expertId = $('input#expertId').val();
	var allFinished = true;
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
	
	// 这里Tasks相当于对一类题目的总称
	function createTasks() {
		$("#submit_list").jqGrid({
			url: '${ContextPath}/evaluation/progressSubmitGetData/',
			mtype : 'GET',
			datatype : "json",
			colNames : ['打分名称', '完成进度','打分子项', '进度状态', 'cLink', '打分'],
			/*★★注意！
			colModel的name和index的字段名要和后台传递来的JSON串的名字一致
			即后台VM封装的实体的属性名应该叫做aName、aNameIndex等
			而且其中aNameIndex中的属性名应该与attrSetting()中的attr.display的dispaly叫一样的名称
			*/
			colModel : [ 
				{name : 'aName',	index : 'aNameIndex',	width : 90,		align : "center",	cellattr : attrSetting}, 
				{name : 'aPCT',		index : 'aPCTIndex',	width : 70,		align : "center",	cellattr : attrSetting, 	hidden : true}, 
				{name : 'bName',	index : 'bNameIndex',	width : 180,	align : "center",	cellattr : attrSetting}, 
				{name : 'bPCT',		index : 'bPCTIndex',	width : 70,		align : "center",	cellattr : attrSetting}, 
				{name : 'cLink',	index : 'cLink',		width : 200,	align : "center",	hidden : true},
				{name : 'link',		index : 'link',			width : 200,	align : "center"}],
			cmTemplate : {sortable : false},
			height : '100%',
			autowidth : true,
			rowNum : 30,
			viewrecords : true,
			sortorder : "desc",
			caption : "完成情况表",
			rownumbers : false,
			gridComplete: function(){
				var ids = $("#submit_list").jqGrid('getDataIDs');
				for (var i = 0; i < ids.length; i++) {
					var rowId = ids[i];
					var rowdata=$("#submit_list").jqGrid('getRowData',rowId);
				    var cLink = rowdata.cLink; 
				    // 加上该任务的编号和已选专家的编号
				    var myLink =  "<a href='#' onclick='openLink(\"" 
				    		+ cLink + currentBatchId + "\/" 
				    		+ expertId+"\");return false;'>请点击链接前往打分</a>";
		    		var bPCT = rowdata.bPCT;
				    if (bPCT == "完成，未提交") {
				    	$("#submit_list").jqGrid('setCell',rowId, 'bPCT', '', 'orangeStyle');
				    } else if (bPCT == "已提交") {
				    	$("#submit_list").jqGrid('setCell',rowId, 'bPCT', '', 'greenStyle');
				    } else {
				    	allFinished = false;
				    	$("#submit_list").jqGrid('setCell',rowId, 'bPCT', '', 'redStyle');
				    } 
				    
				    $("#submit_list").jqGrid('setRowData', ids[i], {link:myLink});
				}	
			},
			});
		}
	
	function addCellAttr(rowId, val, rawObject, cm, rdata) {
	    return "style='font-weight:bold;color:#2E6E9E'";
	};
	
	$("#submit").click(function() {
		if (!allFinished) {
			alert_dialog("您的评价未完成，不能提交");
		} else {
			$(".process_dialog").show();
			$('.process_dialog').dialog({
				position : 'top',
				modal : true,
				autoOpen : true,
			});
			$.ajax({ 
		        type : "POST", 
		        url : "${ContextPath}/evaluation/progressSubmitClickSubmit/", 
		        dataType : "json",      
		        contentType : "application/json",               
		        success : function(backData) { 
		        	$("#submit_list")
		        			.setGridParam({url:'${ContextPath}/evaluation/progressSubmitGetData/'})
		        			.trigger("reloadGrid");
		        	$(".process_dialog").dialog("destroy");
					$(".process_dialog").hide();
		        }
		    });
		}
	});
	function openLink(url) {
		if (url.trim() == "") return;
		$.post(url, function(data) {
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		  }, 'html');
	}
</script>

