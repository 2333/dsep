<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="addExpertInfo_div" class="form">
	<form id="expertInfo_fm" class="fr_form">
		<table id="rule_tb_dialog" class="fr_table" style="width:800px">
			<tr>
				<td><span class="TextBold">学科</span></td>
				<td> 0835-软件工程
				</td>
			</tr>
			<tr>
				<td><span class="TextBold">学校</span></td>
				<td> 北京航空航天大学
				</td>
			</tr>
		</table>
		
		<div>
			<table id="achieve_list"></table>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(document).ready(
			function() {

				$("input[type=submit], a.button , button").button();

				$(document).ready(

						function() {
							$("#achieve_list").jqGrid(
									{
										datatype : "local",
										colNames : [ '评价条目', '参考数据', '评价结果'],
										colModel : [ {
											name : 'item',
											index : 'item',
											width : 50,
											align : "center"
										}, {
											name : 'data',
											index : 'data',
											width : 100,
											align : "center"
										}, {
											name : 'value',
											index : 'value',
											width : 50,
											align : "center"
										} ],
										height : '100%',
										width : 800,
										//autowidth : true,
										//rowNum : 10,
										//rowList : [ 10, 20, 30 ],
										viewrecords : true,
										sortorder : "desc",
										//pager : "#pager",
										caption : "评估",
										
										gridComplete: function(){
											var ids = jQuery("#achieve_list").jqGrid('getDataIDs');
											var input;
											for(var i=0;i < ids.length;i++){
												input = "<input type='text' />"; 
												jQuery("#achieve_list").jqGrid('setRowData',ids[i],{value:input});
											}	
										}
									});

							var mydata = [ {
								item : "高水平学术论文",
								data : "数据项",
								value : ""
							},{
								item : "优秀在校生与毕业生",
								data : "数据项",
								value : ""
							} ];

							for ( var i = 0; i <= mydata.length; i++)
								jQuery("#achieve_list").jqGrid('addRowData',
										i + 1, mydata[i]);

						});
			});
</script>