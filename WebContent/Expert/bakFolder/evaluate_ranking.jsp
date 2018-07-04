<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>学科排名  
	</h3>
</div>

<input id="discId" type="hidden" value="${discId}"/>
<input id="rankingQuestionId" type="hidden" value="${rankingQuestionId}"/>
<!--  
<div class="selectbar">
	<span class="TextFont">学校</span> <select id="school">
		<option value="" selected="selected">全部</option>
		<c:if test="${!empty colleges}">
			<c:forEach items="${colleges}" var="college">
				<option value="${college.key }">${college.value}</option>
			</c:forEach>
		</c:if>
	</select> <span class="TextFont">学科</span> <select id="dsep" name=dsep>
		<option value="" selected="selected">全部</option>
		<c:if test="${!empty disciplines}">
			<c:forEach items="${disciplines}" var="discipline">
				<option value="${discipline.key }">${discipline.value}</option>
			</c:forEach>
		</c:if>
	</select>
	<a id="search" class="button" href="#"><span class="icon icon-search "></span>查找</a>
</div>
-->
<div class="cur">
	<table id="ranking_list"></table>
	<div id="pager"></div>
</div>
<!-- 
<div class="selectbar inner_table_holder">
	<table class="layout_table right">
		<tbody>
			<tr>
				<td width="90"><a id="save" class="button" href="#"><span
						class="icon icon-store "></span>保存</a></td>
			</tr>
		</tbody>
	</table>
</div>
-->
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
<%-- <script src="${ContextPath}/js/fileOper/excel_oper.js"></script> --%>
<script type="text/javascript">
$("#search").click(function() {
	var unitId = $("#school").val();
	var dsepid = $("#dsep").val();
	/* var status = $("#collectStatus").val(); */
	$("#ranking_list").setGridParam({url : "${ContextPath}/evaluation/progress/getreputationdata?unitId="
									+ unitId + "&dsepid=" + dsepid}).trigger("reloadGrid");
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
	var unitId = $("#school").val();
	var dsepid = $("#dsep").val();
	
	var discId = $('input#discId').val();
	var rankingQuestionId = $('input#rankingQuestionId').val();
	
	//alert("${ContextPath}/evaluation/rankingGetUnitList/" + rankingQuestionId);
	$("#ranking_list").jqGrid({
		datatype : "json",
		url : "${ContextPath}/evaluation/progress/rankingGetUnitList/" + rankingQuestionId,
		colNames : [ 'id', '学校代码', '学校名称', '学科代码', 
		             '学科名称', '当前排名', '简况表' ],
		colModel : [
			{name : 'resultId',		 align : "resultId", 	align : "center", width : 100, 	hidden : true},
			{name : 'unitId',		 align : "unitId", 		align : "center", width : 100, 	hidden : true},
			{name : 'unitName',	 	 index : 'unitName',	align : "center", width : 120},
			{name : 'discId',	 	 index : 'discId',		align : "center", width : 100, 		hidden : true},
			{name : 'discName',		 index : 'discName',	align : "center", width : 120}, 
			{name : 'evaluate',		 index : 'evaluate', 	width : 100,	  align : "center",	sortable : false, 	hidden : true}, 
			{name : 'brief',		 index : 'report',	 	width : 100,	  align : "center"},
			],
		rownumbers : true,
		height : "100%",
		autowidth : true,
		//width : "50%",
		pager : '#pager',
		rowNum : 300,
		//rowList : [ 10, 20, 30 ],
		viewrecords : true,
		sortorder : "desc",
		sortname : "unitId",

		jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
			root : "rows", //包含实际数据的数组  
			page : "pageIndex", //当前页  
			total : "totalPage",//总页数  
			records : "totalCount", //查询出的记录数  
			repeatitems : false
		},

		gridComplete : function() {
			var ids = jQuery("#ranking_list").jqGrid('getDataIDs');
			var input;
			var introDownload;
			for ( var i = 0; i < ids.length; i++) {
				input = "<input type='text' style='width:50px'/>";
				introDownload = "<a href='#' onclick='' id='download_brief_report_btn'>简况表.pdf</a>";
				jQuery("#ranking_list").jqGrid('setRowData',ids[i],{brief : introDownload});
				jQuery("#ranking_list").jqGrid('setRowData',ids[i],{evaluate : input});
			}
		},
		caption : "学科排名  注意：请您通过拖拽数据行进行排名！"
	}).navGrid('#pager', {edit : true,add : false,del : false});
		
	/* $('#ranking_list').on("click", "#download_brief_report_btn", function() {
		var RowList;
		RowList = $("#ranking_list").getRowData();
		var row = null;
		for( row in RowList )
		{
		 $("#ranking_list1").addRowData(row,$("#ranking_list").getRowData(row));
		 $("#ranking_list").delRowData(row);
		}
	}); */
	
	$("#ranking_list").jqGrid('sortableRows', {
	    update: function (ev, ui) {
	        
	    	var item = ui.item[0], 
	        	ri = item.rowIndex, 
	        	itemId = item.id, 
	        	itemName = item.cells[1].innerText ;	//被拖拽的那行数据的第1（从0开始）个数据项的内容
	        	var resultId;
	        	if (ri < 10) {
	        		// 之所以是从1开始，是因为textContent第一个是序号
		        	resultId = item.textContent.substring(1, 33);
	        	} else if (ri >= 10 && ri < 100) {
	        		// 之所以是从2开始，是因为textContent前两个是序号
		        	resultId = item.textContent.substring(2, 34);
	        	} else if (ri >= 100 && ri < 1000) {
	        		// 之所以是从3开始，是因为textContent前三个是序号
		        	resultId = item.textContent.substring(3, 35);
	        	} else if (ri >= 1000 && ri < 10000) {
	        		// 之所以是从4开始，是因为textContent前四个是序号
		        	resultId = item.textContent.substring(4, 36);
	        	} 
	        if (null == item.cells[1].lastLocation) {
	        	//alert("from:" + itemId + "to" + ri);

	        	$.post("${ContextPath}/evaluation/progress/rankingUpdate/" + rankingQuestionId + "/" + resultId + "/" + itemId + "/" + ri, 
	        			function(result){
	        		//alert("success");
 				 });
	        	/* $.ajax({ 
		            type : "POST", 
		            url : "${ContextPath}/evaluation/rankingUpdate/" + rankingQuestionId + "/" + resultId + "/" + itemId + "/" + ri, 
		            dataType : "json",      
		            contentType : "application/json",
		            success : function() {
		            	alert("success");
		            }
		         });  */
	        } else {
	        	//alert("from:" + item.cells[1].lastLocation + "to" + ri);
	        	$.post("${ContextPath}/evaluation/progress/rankingUpdate/" + rankingQuestionId + "/" + resultId + "/" + item.cells[1].lastLocation + "/" + ri, 
	        			function(result){
	        		//alert("success");
 				 });
	        	/* $("#ranking_list").attr('disabled', true);
	        	$.ajax({ 
		            type : "POST", 
		            url : "${ContextPath}/evaluation/rankingUpdate/" + rankingQuestionId + "/" + resultId + "/" + item.cells[1].lastLocation + "/" + ri, 
		            dataType : "json",      
		            contentType : "application/json",
		            success : function() {
		            	alert("success");
		            }
		         });  */
	        }
	        item.cells[1].lastLocation = ri;
	        	
	        //输出item的所有属性和对应值。
			/* $.each(item, function(key, element){
				console.log(key + "~~" + element);
				//alert(key + "-" + element);
			}); */
	        
	    }} 
	);  

	/* $("#ranking_list").jqGrid('sortableRows'); */
	
	
	var contextPath="${ContextPath}";//绝对路径
	$("#download_brief_report_btn").click(function() {
		    
	});

</script>