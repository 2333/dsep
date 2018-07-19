/*控制表格是否可以编辑*/
function jqGird_control(edittype) {
	$('#itemAdd,#itemEdit,#itemDel').unbind('click');
	if (edittype == 'editable') {
		$("#itemEdit").click(
				function() {
					/*弹出窗口的方式编辑  */
					var gr = jQuery("#jqGrid_tb").jqGrid(
							'getGridParam', 'selrow');
					if (gr != null) {
						jQuery("#jqGrid_tb").jqGrid('editGridRow',
								gr, {
									closeAfterEdit : true,
									width : 500,
									top : 100,
									left : 300,
									reloadAfterSubmit : true,
									beforeShowForm : pickdate,
									afterComplete : function(a, data, c) {
										if (a.responseText == 'success')
											alert('编辑成功！');
										else
											alert('编辑失败!');
									}
								});
					} else
						alert("请选择条目！");

				});
		$('#itemAdd').click(function(id) {
			jQuery("#jqGrid_tb").jqGrid('editGridRow', "new", {
				closeAfterAdd : true,
				width : 500,
				top : 100,
				left : 300,
				reloadAfterSubmit : true,
				beforeShowForm : pickdate,
				afterComplete : function(a, data, c) {
					if (a.responseText == 'success')
						alert('添加成功！');
					else
						alert('添加失败！');

				}
			});

		});
		$('#itemDel').click(
						function() {
							var gr = jQuery("#jqGrid_tb").jqGrid(
									'getGridParam', 'selrow');
							if (gr != null) {
								var rowData = $("#jqGrid_tb")
										.jqGrid("getRowData", gr);
								var index = 'ROW_ID';
								var itemId = rowData[index];
								//alert(itemId);
								jQuery("#jqGrid_tb")
										.jqGrid(
												'delGridRow',
												gr,
												{
													top : 100,
													left : 300,
													url : requestUrl+'/Collection/collectionDel?tableId='
															+ tableId
															+ '&itemId='
															+ itemId,
													reloadAfterSubmit : true,
													afterComplete : function(
															a, data, c) {
														if (a.responseText == 'success')
															alert('删除成功！');
														else
															alert('删除失败！');
													}
												});
							} else
								alert("请选择条目！");
						});
	} else {
		$('#itemAdd,#itemEdit,#itemDel')
				.click(function(event) {
					alert('您无权对数据进行此项操作！');
					event.preventDefault();
				});
	}
}
/*jqgrid生成函数以及渲染数据*/
function myAjaxForJqgrid(table) {
	var commonUrl=requestUrl+"/Collection/initJqgrid?tableId="+ table;
	commonAjaxRequest(commonUrl,'json',renderJqGrid,error_function);
}
/*渲染jqgrid*/
function renderJqGrid(data)
{
	var collegeId = $("#college").val();
	var disciplineId = $("#dsep").val();
	$("#discipline_info").show();
	$("#jqGrid_tb").jqGrid('GridUnload');
	tableId = data.tableId;
	var titleValues = data.titleValues;
	collectionDateCols = new Array();
	collectionDateCols = data.dateColIds;
	$("#jqGrid_tb").jqGrid(
					{
						url : requestUrl+'/Collection/collectionData/'
								+ tableId
								+ '/'
								+ titleValues
								+ '/'
								+ collegeId
								+ '/'
								+ disciplineId,
						editurl : requestUrl+'/Collection/collectionEdit/'
								+ tableId
								+ '/'
								+ titleValues
								+ '/'
								+ collectionDateCols
								+ '/'
								+ collegeId
								+ '/'
								+ disciplineId,
						datatype : 'json',
						mtype : 'POST',
						colModel : data.colConfigs,
						height : "100%",
						autowidth : true,
						shrinkToFit : false,
						pager : '#pager_tb',
						pgbuttons : true,
						rowNum : 10,
						rowList : [ 10, 20, 30 ],
						viewrecords : true,
						sortname : data.defaultSortName,
						sortorder : "desc",
						caption : data.tableName,
						jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
							root : "rows", //包含实际数据的数组  
							page : "pageIndex", //当前页  
							total : "totalPage",//总页数  
							records : "totalCount", //查询出的记录数  
							repeatitems : false,
						},
						loadComplete : function() {
							$("#jqGrid_tb").setGridWidth(
									$("#content").width());
						},
						prmNames : {
							page : "page",
							rows : "rows",
							sort : "sidx",
							order : "sord"
						},
						multiselect: true,
						multiboxonly: true,
					}).navGrid('#pager_tb', {
				edit : false,
				add : false,
				del : false
			});
}