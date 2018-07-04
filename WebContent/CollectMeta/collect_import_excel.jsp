<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="import_excel_dv" class="table">
	<form id="import_excel_fm">
		<div>
			<table>
				<tr>
					<td>
						<p>
							<strong><font color="red">请确保导入的数据各列内容符合当前表格要求</font></strong>
						</p>
					</td>
				</tr>
			</table>
		</div>
		<table id="collect_import_excel_tb"></table>
	</form>
</div>
<script type="text/javascript">
	var viewConfig4Import;
	var selectedRow;//选中的一行
	var initDataValues;//初始化显示内容
	var pass_validate='1';
	var counter=0;//添加的行号RowId
	/**
	 * 删除
	 */
	function delExcelItem(id) {
		var ids = jQuery("#collect_import_excel_tb").jqGrid('getDataIDs');
		var rowData = $("#collect_import_excel_tb").jqGrid("getRowData");
		$.each(ids, function(i, item) {
			if (item > id) {
				jQuery("#collect_import_excel_tb").jqGrid('setRowData', item, {
					SEQ_NO : rowData[i]['SEQ_NO'] - 1
				});
			}
		});
		$("#collect_import_excel_tb").jqGrid("delRowData", id);
		records--;
	}
	/**
	 * 确认
	 */
	function resureExcelItem(id) {
		jQuery("#collect_import_excel_tb").jqGrid('saveRow', id);
		var resure_link = "<a class='' href='#' onclick='editExcelItem(" + id
				+ ")'>编辑</a>";
		var del_link = "<a class='' href='#' onclick='delExcelItem(" + id
				+ ")'>删除</a>";
		jQuery("#collect_import_excel_tb").jqGrid('setRowData', id, {
			oper : resure_link + ' | ' + del_link
		});
	}
	/**
	 * 编辑
	 */
	function editExcelItem(id) {

		$("#collect_import_excel_tb").jqGrid('editRow', id, false,
				setRowControllers);
		//var resure_link="<a class='' href='#' onclick='resureExcelItem("+id+")'>确认</a>";
		var del_link = "<a class='' href='#' onclick='delExcelItem(" + id
				+ ")'>删除</a>";
		jQuery("#collect_import_excel_tb").jqGrid('setRowData', id, {
			oper : del_link
		});
	}
	/**
	 *取消
	 */
	function cancelExcelItem(id) {

		jQuery("#collect_import_excel_tb").jqGrid('restoreRow', id);
		var resure_link = "<a class='' href='#' onclick='editExcelItem(" + id
				+ ")'>编辑</a>";
		var del_link = "<a class='' href='#' onclick='delExcelItem(" + id
				+ ")'>删除</a>";
		jQuery("#collect_import_excel_tb").jqGrid('setRowData', id, {
			oper : resure_link + ' | ' + del_link
		});

	}
	/**
	 *批量提交之前进行检验
	 */
	function validateExcelRows() {
		var ids = jQuery("#collect_import_excel_tb").jqGrid('getDataIDs');

		for ( var i = 0; i < ids.length; i++) {
			var isPassValid = '0';
			currentSaveRowId = ids[i];
			currentJqGridId = 'collect_import_excel_tb'; 
			jQuery("#collect_import_excel_tb").jqGrid("saveRow", ids[i], {
				url:'clientArray',
				aftersavefunc : function(data) {
					if (data) {
						isPassValid = '1';
					}
				}
			});
			if (isPassValid == '0') {
				$("#info_dialog").css({"z-index":"2000"});//检查信息对话框弹出最上层
				var positionX = 100;//设置弹出框的位置
				var positionY = 200;
				var myOffset = new Object();
				myOffset.left = positionX;
				myOffset.top = positionY;
				$("#info_dialog").offset(myOffset);
				for ( var j = i - 1; j >= 0; j--) {
					$("#collect_import_excel_tb #" + ids[j]).find('td').css(
							'background-color', 'transparent');
					$("#collect_import_excel_tb").jqGrid('editRow', ids[j],
							false, setRowControllers);
					var del_link = "<a class='' href='#' onclick='delExcelItem("
							+ ids[j] + ")'>删除</a>";
					jQuery("#collect_import_excel_tb").jqGrid('setRowData',
							ids[j], {
								oper : del_link
							});
				}
				return ids[i];
			}
		}
		return 'success';

	}
	/*导入数据，configdata由回调函数获取service返回数据重新解析获得*/
	function import_Dialog(configData, data) {
		console.log('configData');
		console.log(configData);
		console.log(data);
		initImportExcelJqgrid(configData);
		var rows = data.rows;
		$('#import_excel_dv').dialog(
				{
					title : "批量导入",
					height : '500',
					width : '80%',
					position : 'center',
					modal : true,
					draggable : true,
					hide : 'fade',
					show : 'fade',
					autoOpen : true,
					close : function() {
						if(isOpen){
							$("#yy_mm_dv").dialog("close");
						}
						records = $("#jqGrid_collect_tb").jqGrid(
								'getGridParam', 'records');//当前总记录数
					},
					buttons : {
						"提交" : function() {
							//excelSubmit();
							if(checkEntityCount("collect_import_excel_tb")){
								var valid_res = validateExcelRows();

								if (valid_res != 'success') {
									$("#collect_import_excel_tb #" + valid_res)
											.find('td').css('background-color',
													'yellow');
								} else {
									excelSubmit();
								}
							}
						},

						"取消" : function() {

							$("#collect_import_excel_tb").jqGrid('GridUnload');
							$("#import_excel_dv").dialog("close");
							records = $("#jqGrid_collect_tb").jqGrid(
									'getGridParam', 'records');//当前总记录数
						}
					}
				});

		records = $("#jqGrid_collect_tb").jqGrid('getGridParam', 'records');//当前总记录数
		for ( var counter = 0; counter < rows.length; counter++) {
			rowCounter = counter + 1;
			$("#collect_import_excel_tb").jqGrid('addRowData', rowCounter,
					rows[counter]);

			$("#collect_import_excel_tb").jqGrid('editRow', rowCounter, false,
					setRowControllers);
			var del_link = "<a class='' href='#' onclick='delExcelItem("
							+ rowCounter + ")'>删除</a>";
			jQuery("#collect_import_excel_tb").
				jqGrid('setRowData', 
						rowCounter,
						{oper : del_link}
				);
			jQuery("#collect_import_excel_tb").
				jqGrid('setRowData', 
						rowCounter,
						{SEQ_NO : ++records}
				);
		}
		counter = rows.length;
		//检测当前导入加载的数据，有问题的数据将高亮显示
		checkData(data);
	}
	/* 检查被初始化为空的数据，有误的数据航将高亮显示 */
	//该功能暂时不进行开发
	function checkData(data){
		alert_dialog("您从模板导入了 " + data.rows.length + " 条数据");
	}
	/*批量添加中的jqgrid*/
	function initImportExcelJqgrid(data) {
		initDefaultDataValues(data);
		var mydata;//临时变量，作为数据参数
		$("#collect_import_excel_tb").jqGrid('GridUnload');
		$("#collect_import_excel_tb").jqGrid({
			colModel : data.colConfigs,
			data: mydata,
			datatype: "local",
			height : "100%",
			autowidth : true,
			shrinkToFit : false,
			rowNum : 10,
			rowList : [ 10, 20, 30 ],
			viewrecords : true,
			sortorder : "desc",
			caption : "批量导入",
			gridComplete : function() {

			},
		}).navGrid('#collect_import_excel_tb', {
			edit : false,
			add : false,
			del : false
		});
	}
	/*页面整体提交*/
	function excelSubmit()
	{   
		
		var ids = jQuery("#collect_import_excel_tb").jqGrid('getDataIDs');
		var dataString='{\"tableId\":[\"'+tableId+'\"],\"titleValues\":[';
		dataString+='\"SEQ_NO\",';
		for(var i=0;i<titleValues.length;i++)
		{
			if(i!=titleValues.length-1)
				dataString+='\"'+titleValues[i]+'\",';
			else
				dataString+='\"'+titleValues[i]+'\"],';
		}
		dataString +='\"rows\":[';
		var rowData = $("#collect_import_excel_tb").jqGrid("getRowData");  
		console.log('rowData');
		console.log(rowData);
		    if (rowData.length < 1) { alert("没有数据！"); return; }  
		    for (var i = 0; i < ids.length; i++) { 
		    	dataString +='{';
		    	dataString+='\"SEQ_NO\":\"'+rowData[i]['SEQ_NO']+'\",';
		    	$.each(titleValues,function(j,item){
		    		if(j==(titleValues.length-1))
		    		{
		    			//dataString +='\"'+item+'\":\"'+rowData[i][item]+'\"';
		    			if($.inArray(item,fileValues)!=-1){
		    				if(rowData[i][item]!='请上传附件'){
		    					dataString +='\"'+item+'\":\"'+rowData[i][item]+'\"';
		    				}else{
		    					dataString +='\"'+item+'\":\"'+''+'\"';
		    				}
		    			}else{
		    				dataString +='\"'+item+'\":\"'+rowData[i][item]+'\"';
		    			}
		    		}
		    			
		    		else
		    		{
		    			//dataString+='\"'+item+'\":\"'+$.trim(rowData[i][item])+'\",';
		    			if($.inArray(item,fileValues)!=-1){
		    				if(rowData[i][item]!='请上传附件'){
		    					dataString+='\"'+item+'\":\"'+rowData[i][item]+'\",';
		    				}else{
		    					dataString+='\"'+item+'\":\"'+''+'\",';
		    				}
		    			}else{
		    				dataString+='\"'+item+'\":\"'+rowData[i][item]+'\",';
		    			}
		    		}
		    			
		    		});
		    	if(i==(ids.length-1))
		    		dataString+='}';
		    	else
		    		dataString+='},';
		    }
		    dataString+=']}';
			
			//var myurl="${ContextPath}/Collect/toCollect/JqOper/batchSubmit/"+unitId+"/"+discId;
			initBatchUrl();
			$.post(batchUrl,{params:dataString},
					function(result){
						if(result.status=='success'){
							alert_dialog('操作成功！');
							$("#collect_import_excel_tb").jqGrid('GridUnload');
							$("#import_excel_dv").dialog("close");
							$("#jqGrid_collect_tb").jqGrid('setGridParam').trigger("reloadGrid");
						}else
							{
								$.each(result,function(i,item){
									if(i!='status')
										$("#collect_import_excel_tb").jqGrid("delRowData",ids[i]);
							
								});
								for(var j=0;j<rowData.length;j++){
									$("#collect_import_excel_tb #"+ids[j]).find('td').css('background-color','transparent');
									$("#collect_import_excel_tb").jqGrid('editRow',ids[j],false,setRowControllers);
									var del_link = "<a class='' href='#' onclick='delBatchItem("+ids[j]+")'>删除</a>"; 
						    		jQuery("#collect_import_excel_tb").jqGrid('setRowData',ids[j],{oper :del_link}); 
								}
								alert_dialog('窗口保留数据提交失败,请重新提交！');
							}
			}); 
		
	}
</script>