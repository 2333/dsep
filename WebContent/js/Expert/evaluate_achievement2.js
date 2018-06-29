$(document).ready(function() {
	$("input[type=submit], a.button , button").button();
	$("#achievement_content").tabs();
		/**
		 * tab_width是第一个tab的宽，赋值给第二个tab中的jqgrid的width
		 */
		var tab_width = $("#achievement_content div.ui-tabs-panel:not(.ui-tabs-hide)").width();
		tab_width -= 2;
		console.log("tabwidth"+tab_width);
		
		var subData = [ 
		    {id : "1", type : "优秀在校生", name : "张三", birthdate : "1990.4",
			 degree : "硕士", time : "2012.9", introduction : "品学兼优，乐于助人"}, 
		    {id : "2", type : "优秀在校生", name : "张三", birthdate : "1990.4",
			 degree : "硕士", time : "2012.9", introduction : "品学兼优，乐于助人"
		}, {
			id : "3",
			type : "优秀毕业生",
			name : "李四",
			birthdate : "1986.4",
			degree : "博士",
			time : "2011.3",
			introduction : "品学兼优，乐于助人"
		} ];

		$("#student_list")
				.jqGrid(
						{
							datatype : "local",
							colNames : ['学科', '学校', '参考数据项',
									'评价' ],
							colModel : [ {
								name : 'discipline',
								index : 'discipline',
								width : 100,
								align : "center"
							}, {
								name : 'college',
								index : 'college',
								width : 100,
								align : "center"
							}, {
								name : 'dataItem',
								index : 'dataItem',
								width : 100,
								align : "center"
							}, {
								name : 'evaluate',
								index : 'evaluate',
								width : 100,
								align : "center"
							} ],
							cmTemplate : {
								sortable : false
							},
							height : '100%',
							width : tab_width,
							rowNum : 10,
							rowList : [ 10, 20, 30 ],
							viewrecords : true,
							sortorder : "desc",
							pager : "#student_pager",
							caption : "优秀在校生与毕业生评估",
							rownumbers: true,
							
							gridComplete : function() {
								// $("#student_list").expandSubGridRow("1");
								var ids = jQuery("#student_list").jqGrid('getDataIDs');
								var showData;
								var evaluateText;
								for ( var i = 1; i <= ids.length; i++) {
									showData = "<a class='showData' href='#' onclick=\"$('#student_list').toggleSubGridRow('"+ i + "');\">显示/隐藏数据项</a>";
									evaluateText = "<select style='width:100px'> <option>-</option> <option>A</option> <option>B</option> <option>C</option> <option>D</option> </select>";
									jQuery("#student_list").jqGrid('setRowData',ids[i - 1], {dataItem : showData});
									jQuery("#student_list")
											.jqGrid(
													'setRowData',
													ids[i - 1],
													{
														evaluate : evaluateText
													});
								}
							},

							/*
							 * http://www.trirand.com/blog/jqgrid/jqgrid.html
							 * Advanced/Grid as Subgrid
							 */
							subGrid : true,
							subGridRowExpanded : function(
									subgrid_id, row_id) {
								// subgrid_id:subgridstudent_list_1
								// row_id:subgrid1
								// we pass two parameters
								// subgrid_id is a id of the div tag
								// created whitin a table data
								// the id of this elemenet is a
								// combination of the "sg_" + id of
								// the row
								// the row_id is the id of the row
								// If we wan to pass additinal
								// parameters to the url we can use
								// a method getRowData(row_id) -
								// which returns associative array
								// in type name-value
								// here we can easy construct the
								// flowing
								var subgrid_table_id, pager_id;
								subgrid_table_id = subgrid_id
										+ "_t";
								pager_id = "p_" + subgrid_table_id;
								// console.log("subgrid"+row_id);
								$("#" + subgrid_id)
										.html(
												"<table id='"
														+ subgrid_table_id
														+ "' class='scroll'></table><div id='"
														+ pager_id
														+ "' class='scroll'></div>");
								jQuery("#" + subgrid_table_id)
										.jqGrid(
												{
													// url:"subgrid.php?q=2&id="+row_id,
													// datatype:
													// "xml",
													datatype : "local",
													data : subData,
													colNames : [
															'序号',
															'学生类型',
															'姓名',
															'出生年月',
															'攻读学位/最高学位',
															'入学时间/获得学位时间',
															'简介' ],
													colModel : [
															{
																name : "id",
																index : "id",
																width : 30,
																key : true,
																align : "center"
															},
															{
																name : "type",
																index : "type",
																width : 100,
																align : "center"
															},
															{
																name : "name",
																index : "name",
																width : 150,
																align : "center"
															},
															{
																name : "birthdate",
																index : "birthdate",
																width : 70,
																align : "center"
															},
															{
																name : "degree",
																index : "degree",
																width : 70,
																align : "center"
															},
															{
																name : "time",
																index : "time",
																width : 70,
																align : "center"
															},
															{
																name : "introduction",
																index : "introduction",
																width : 100,
																align : "center"
															} ],
													autowidth : true,
													rowNum : 20,
													pager : pager_id,
													// sortname:
													// 'num',
													// sortorder:
													// "asc",
													cmTemplate : {
														sortable : false
													},
													height : '100%'
												});
								//jQuery("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,{edit:false,add:false,del:false})
							},
							subGridRowColapsed : function(
									subgrid_id, row_id) {
								// this function is called before removing the data
								//var subgrid_table_id;
								//subgrid_table_id = subgrid_id+"_t";
								//jQuery("#"+subgrid_table_id).remove();
							}
						});

		var mydata = [ {
			id : "1",
			discipline : "0835-软件工程",
			college : "北京航空航天大学",
			paper : "",
			student : "",
			introduction : ""
		}, {
			id : "2",
			discipline : "0835-软件工程",
			college : "北京大学",
			paper : "",
			student : "",
			introduction : ""
		}, {
			id : "3",
			discipline : "0835-软件工程",
			college : "清华大学",
			paper : "",
			student : "",
			introduction : ""
		}, {
			id : "4",
			discipline : "0835-软件工程",
			college : "北京交通大学",
			paper : "",
			student : "",
			introduction : ""
		}, {
			id : "5",
			discipline : "0835-软件工程",
			college : "北京理工大学",
			paper : "",
			student : "",
			introduction : ""
		}, {
			id : "6",
			discipline : "0835-软件工程",
			college : "北京科技大学",
			paper : "",
			student : "",
			introduction : ""
		}, {
			id : "7",
			discipline : "0835-软件工程",
			college : "北京邮电大学",
			paper : "",
			student : "",
			introduction : ""
		} ];

		for ( var i = 0; i <= mydata.length; i++)
			jQuery("#student_list").jqGrid('addRowData', i + 1,
					mydata[i]);
	});