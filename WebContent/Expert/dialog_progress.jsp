<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="progress_div" class="form">
	<div>
		专家姓名：<input type="text" value="李明" disabled />
	</div>
	<hr>
	<div>
		<table id="progress_list"></table>
	</div>
</div>

<script type="text/javascript">

	$("input[type=submit], a.button , button").button();

	$(document).ready(
			
			function() {
				
				/********************  jqGrid ***********************/
				$("#progress_list").jqGrid(
						{
							datatype : "local",
							colNames : [ '评价类别', '评价进程' ],
							colModel : [ {
								name : 'type',
								index : 'type',
								width : 250,
								align : "center"
							}, {
								name : 'progress',
								index : 'progress',
								width : 250,
								align : "center"
							} ],
							height : '100%',
							width : '100%',
							//autowidth : true,
							viewrecords : true,
							//sortorder : "desc",
							caption: "专家评价进程"
							/* gridComplete: function(){
								var ids = jQuery("#progress_list").jqGrid('getDataIDs');
								var remarkCheck;
								var progressCheck;
								var progress;
								
								for(var i=0;i < ids.length;i++){
									progress = $("#expert_list").jqGrid("getRowData", ids[i]).progress;
									remarkCheck = "<a href='#' onclick='openRemarkDialog()'>[查看]</a>"; 
									progressCheck = "<a href='#' onclick='openProgressDialog()'>"+progress+"</a>"; 
									jQuery("#expert_list").jqGrid('setRowData',ids[i],{remark:remarkCheck});
									jQuery("#expert_list").jqGrid('setRowData',ids[i],{progress:progressCheck});
								}	
							} */
						
						});

				var mydata = [ {
					type : "指标评价",
					progress : "5/8"
				}, {
					type : "专项评价",
					progress : "0/9"
				}, {
					type : "声誉评价",
					progress : "10/10"
				} ];

				for ( var i = 0; i <= mydata.length; i++)
					jQuery("#progress_list").jqGrid('addRowData', i + 1,
							mydata[i]);

			});
		
</script>