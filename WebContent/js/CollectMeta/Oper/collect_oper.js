/**
 * 按钮集
 */
var id;
var initedJqrowData = new Array();
/**
 * 
 * @param id 行号
 * @param operType，操作类型，增删改查
 * @param type，表格、表单类型
 */
function buildLink(id,operType,type)
{
	var link='';
	if(operType=='save'){
		 link="<a class='' href='#' onclick='saveCollectItem("+id+","+type+")'>保存</a>";
	}
	if(operType=='edit'){
		 link="<a class='' href='#' onclick='editCollectItem("+id+","+type+")'>编辑</a>";
	}
	if(operType=='del')
	{
		if(type=='1'||type=='2'){
			link="<a class='' href='#' onclick='clearCollectItem("+id+","+type+")'>清空</a>";
		}else{
			link="<a class='' href='#' onclick='delCollectItem("+id+","+type+")'>删除</a>";
		}
		
	}
	if(operType=='cancel')
	{
		 link="<a class='' href='#' onclick='cancelCollectItem("+id+","+type+")'>取消</a>";
	}
	return link;	
}
//表格保按钮存
var save_link_tb = "<a class='' href='#' onclick='saveCollectItem("+id+","+'0'+")'>保存</a>"; 
//表格取消按钮
var cancel_link_tb="<a class='' href='#' onclick='cancelCollectItem("+id+","+'0'+")'>取消</a>";
//表格删除按钮
var del_link_tb="<a class='' href='#' onclick='delCollectItem("+id+","+'0'+")'>删除</a>";
//表格编辑按钮
var edit_link_tb = "<a class='' href='#' onclick='editCollectItem("+id+","+'0'+")'>编辑</a>"; 
//表单保存按钮
var save_link_fm = "<a class='' href='#' onclick='saveCollectItem("+id+","+'1'+")'>保存</a>"; 
//表单取消按钮
var cancel_link_fm="<a class='' href='#' onclick='cancelCollectItem("+id+","+'1'+")'>取消</a>";
//表单删除按钮
var del_link_fm="<a class='' href='#' onclick='delCollectItem("+id+","+'1'+")'>删除</a>";
//表单编辑按钮
var edit_link_fm = "<a class='' href='#' onclick='editCollectItem("+id+","+'1'+")'>编辑</a>"; 
/**
 * 编辑
 * @param id
 * @param type,0是table,1是form类型
 */
function editCollectItem(id,type)
{	
	var eleId='';
	if(type=='0')
	{
		eleId='jqGrid_collect_tb';
	}else if(type=='1'){
		eleId='jqGrid_collect_fm';
	}else if(type=='2'){
		eleId = 'jqGrid_collect_initJq';
		initedJqrowData[id] = $("#"+eleId).jqGrid("getRowData",id);
	}
	jQuery('#'+eleId).jqGrid('editRow',id,false,setRowControllers);
	var save_link= buildLink(id,'save',type);
	var cancel_link = buildLink(id,'cancel',type);
	jQuery("#"+eleId).jqGrid('setRowData',id,{oper :save_link+' | '+cancel_link});
}
/**
 * 设置相关控件列
 * @param id
 */
function setRowControllers(id){
	//设置日期控件
	setDateCol(controllerDic.DATE,id);	
	//设置percent模板列
	setPercentCol(controllerDic.PERCENT,id);
	//设置年月控件
	console.log(controllerDic.YEARMONTH);
	setYearMonthCol(controllerDic.YEARMONTH,id);
	//上传文件附件
	setUploadFileCol(controllerDic.FILE,id);
	//只读的输入框
	setOnlyReadCol(controllerDic.ONLYREADINPUT,id);
	setOnlyReadNum(controllerDic.ONLYREADRN,id);
	setMulSelectCol(controllerDic.MULSELECT,id);
}

/**
 * 保存
 * @param id
 */
function saveCollectItem(id,type)
{
	if(isOpen){
		$("#yy_mm_dv").dialog("close");
	}
	var eleId='';
	var edit_link='';
	var del_link='';
	if(type=='0'){
		eleId='jqGrid_collect_tb';	
	}
	if(type=='1'){
		eleId='jqGrid_collect_fm';
	}
	if(type=='2'){
		eleId ='jqGrid_collect_initJq';
	}
	var rowData = $("#"+eleId).jqGrid("getRowData",id);
	primaryKey=rowData['ID'];
	seqNo=rowData['SEQ_NO'];
	if(type=='2'){
		seqNo = $("#"+id+"_SEQ_NO").val();
	}
	currentSaveRowId = id;
	currentJqGridId = eleId; 
	edit_link=buildLink(id,'edit',type);
	del_link=buildLink(id,'del',type);	
	//var collegeId='10006';
	//var disciplineId='0835';
	jQuery("#"+eleId).jqGrid('saveRow',id,
	  {
		url:contextPath+'/Collect/toCollect/JqOper/collectionEdit/'
		+ tableId
		+ '/'
		+primaryKey
		+'/'
		+ titleValues
		+ '/'
		+ unitId
		+ '/'
		+ discId
		+ '/'
		+seqNo,
		aftersavefunc:function(data){
		if(data){
			if(jqGridConfig.dataType=='G'){
				jQuery("#"+eleId).jqGrid('setRowData',id,{oper :edit_link});	
			}else{
				jQuery("#"+eleId).jqGrid('setRowData',id,{oper :edit_link+' | '+del_link});	
			}
			
		}else
			alert_dialog('保存失败！');
		},successfunc:function(data){
			if(data.responseText=='success'){
				//alert_dialog('编辑成功！');
				//如果主键为0，表示数据为前台生成，需重新加载数据的主键
				if(primaryKey=='0')
				{
					if(type=='2'){
						$("#jqGrid_collect_initJq_hidden").jqGrid('setGridParam').trigger("reloadGrid");
					}else{
						$("#"+eleId).jqGrid('setGridParam').trigger("reloadGrid");
					}
				}
				return true;
			}else
				{
					alert_dialog('编辑失败！');
					cancelCollectItem(id,type);
					return false;
				}
				
		}});
}
/**
 * 取消
 * @param id
 */
function cancelCollectItem(id,type)
{
	if(isOpen){
		$("#yy_mm_dv").dialog("close");
	}
	var eleId='';
	var del_link='';
	var edit_link='';
	if(type=='0'){
		eleId='jqGrid_collect_tb';
	}	
	if(type=='1'){
		eleId='jqGrid_collect_fm';
	}
	if(type=='2'){
		eleId ='jqGrid_collect_initJq';
	}
	edit_link=buildLink(id,'edit',type);
	del_link=buildLink(id,'del',type);
	jQuery('#'+eleId).jqGrid('restoreRow',id);
	/*if(type=='2'){
		$("#jqGrid_collect_initJq_hidden").jqGrid('setGridParam').trigger("reloadGrid");
	}else{
		jQuery('#'+eleId).jqGrid('restoreRow',id);
	}*/
	if(jqGridConfig.dataType=='G'){
		jQuery("#"+eleId).jqGrid('setRowData',id,{oper :edit_link});
	}else{
		jQuery("#"+eleId).jqGrid('setRowData',id,{oper :edit_link+' | '+del_link});
	}
	
	
}
/**
 * 清空操作
 * @param id
 * @param type
 */
function clearCollectItem(id,type){
	var eleId='';
	var delOptions={
			caption: "删除操作",
			msg: "你确定要删除此条数据吗?",
			bSubmit: "删除",
			bCancel: "取消"
	};
	if(type=='0'){
		eleId='jqGrid_collect_tb';
	}
	if(type=='1'){
		delOptions={
				caption: "清空操作",
				msg: "你确定要清空此条数据吗?",
				bSubmit: "清空",
				bCancel: "取消"
		};
		eleId='jqGrid_collect_fm';
	}
	if(type=='2'){
		delOptions={
				caption: "清空操作",
				msg: "你确定要清空此条数据吗?",
				bSubmit: "清空",
				bCancel: "取消"
		};
		eleId ='jqGrid_collect_initJq';
	}
	var rowData = $("#"+eleId).jqGrid("getRowData",id);
	primaryKey=rowData['ID'];
	seqNo=rowData['SEQ_NO'];
	console.log('seqNo : '+seqNo);
	jQuery('#'+eleId).jqGrid('delGridRow',id,
	{
		url:contextPath+'/Collect/toCollect/JqOper/collectionClear/'
		+ tableId
		+ '/'
		+ unitId
		+ '/'
		+ discId
		+ '/'
		+ primaryKey
		+'/'
		+seqNo,
		caption: delOptions.caption,
		msg: delOptions.msg,
		bSubmit: delOptions.bSubmit,
		bCancel: delOptions.bCancel,
		afterSubmit:function(data){
			if(data.responseText=='success')
			{
				alert_dialog('清空成功！');
				if(type==2){
					$("#"+eleId).jqGrid('setGridParam').trigger("reloadGrid");
				}
				return [true,'清空成功！'];
			}else
				{
					return [false,'清空失败！'];
				}
	}});
}
/**
 * 删除
 * @param id
 */
function delCollectItem(id,type)
{
	//var collegeId='10006';
	//var disciplineId='0835';
	var eleId='';
	var delOptions={
			caption: "删除操作",
			msg: "你确定要删除此条数据吗?",
			bSubmit: "删除",
			bCancel: "取消"
	};
	if(type=='0'){
		eleId='jqGrid_collect_tb';
	}
	if(type=='1'){
		delOptions={
				caption: "清空操作",
				msg: "你确定要清空此条数据吗?",
				bSubmit: "清空",
				bCancel: "取消"
		};
		eleId='jqGrid_collect_fm';
	}
	if(type=='2'){
		delOptions={
				caption: "清空操作",
				msg: "你确定要清空此条数据吗?",
				bSubmit: "清空",
				bCancel: "取消"
		};
		eleId ='jqGrid_collect_initJq';
	}
	var rowData = $("#"+eleId).jqGrid("getRowData",id);
	primaryKey=rowData['ID'];
	seqNo=rowData['SEQ_NO'];
	console.log('seqNo : '+seqNo);
	if(typeof(seqNo)=='undefined'||type=='1'||type=='2')
	{
		seqNo=-1;
	}
	jQuery('#'+eleId).jqGrid('delGridRow',id,
	{
		url:contextPath+'/Collect/toCollect/JqOper/collectionEdit/'
		+ tableId
		+ '/'
		+primaryKey
		+'/'
		+ titleValues	
    	+ '/'
		+ unitId
		+ '/'
		+ discId
		+'/'
		+ seqNo,
		caption: delOptions.caption,
		msg: delOptions.msg,
		bSubmit: delOptions.bSubmit,
		bCancel: delOptions.bCancel,
		afterSubmit:function(data){
			if(data.responseText=='success')
			{
				//alert_dialog('删除成功！');
				/*if(type==2){
					$("#jqGrid_collect_initJq_hidden").jqGrid('setGridParam').trigger("reloadGrid");
				}*/
				return [true,'删除成功！'];
			}else
				{
					return [false,'删除失败！'];
				}
	}});
}
/**
 * 
 * @param unitId 学校代码
 * @param discId 学科代码
 * @param tableId 实体id
 * @param tableName 实体表中文名字
 * @param titleValues 表的英文字段
 * @param titleNames 表的中文字段
 */
function excel_output(unitId,discId,tableId,sortOrder,sortName,searchRule)
{
	var url=contextPath+'/Collect/toCollect/export/excel/'
			+ unitId
			+ '/'
			+ discId
			+'/'
			+ tableId
			+ '/'
			+ sortOrder
			+'/'
			+ sortName
			;
	if(searchRule==''){
		//js/fileOper/excel_oper.js
		outputJS(url);
	}else{
		//js/fileOper/excel_oper.js
		outputJSWithParam(url,{filters:JSON.stringify(searchRule)});
	}	
}
/**
 * 批量删除
 * @param viewType
 */
function batchDelItems(viewType){
	var itemId='';
	console.log(viewType);
	if(viewType=='JQGRID'){
		itemId='jqGrid_collect_tb';
	}else{
		itemId='jqGrid_collect_fm';
	}
	console.log(itemId);
	var ids=$('#'+itemId).jqGrid('getGridParam','selarrrow');
	var pkAndSeqNo= new Array();//主键集合
	if(ids.length>0){
		$("#dialog_confirm").empty().append("<p>你确定要删除吗？</p>");
		$("#dialog_confirm").dialog({
			height:150,
			buttons:{
				"确定":function(){
					$.each(ids,function(i,item){
						console.log(i+' : '+ item);
						var rowData = $("#"+itemId).jqGrid("getRowData",ids[i]);
						var pkvalue = rowData['ID'];
						var seqNo = rowData['SEQ_NO'];
						pkAndSeqNo.push(pkvalue+":"+seqNo);
					});
					$.commonRequest({
						url:contextPath+"/Collect/toCollect/JqOper/batchDel/"
						+tableId
						+"/"
						+pkAndSeqNo
						+"/"
						+unitId
						+"/"
						+discId
						+"/"
						+userId,
						dataType:'text',
						success:function(data){
							if(data=='success'){
								alert_dialog('删除成功！');
								$("#"+itemId).jqGrid('setGridParam').trigger("reloadGrid");
							}else{
								alert_dialog('删除失败！');
							}
						},
						error:function(data){
							
						}
					});
					$(this).dialog("close");
				},
				"取消":function(){
					$(this).dialog("close");
				}
			}
		});
	}else{
		alert_dialog('请选择删除的行！');
	}
}
/*
 * 下载附件
 */
function downLoadAttach(){
	console.log(contextPath);
	var url = contextPath+"/Collect/toCollect/JqOper/downLoadAttach/"+attachId;
	$.post(url,function(data){
			downloadProveMaterial(data);
	}, 'json');
}
/**
 * 在没有编辑权限下下载附件
 * @param id
 * @param type
 */
function onlyDownLoadAttachFile(id,type){
	var eleId='';
	if(type=='0'){
		eleId='jqGrid_collect_tb';	
	}
	if(type=='1'){
		eleId='jqGrid_collect_fm';
	}
	if(type=='2'){
		eleId ='jqGrid_collect_initJq';
	}
	var rowData = $("#"+eleId).jqGrid("getRowData",id);
	attachId = rowData['ATTACH_ID'];
	if($.trim(attachId)!=''){
		downLoadAttach();
	}else{
		alert_dialog('该条数据没有相关附件！');
	}
	
}
/**
 * 
 * 导入公共数据
 */
function importPubData(){
	var itemId='';
	console.log(viewType);
	if(viewType=='JQGRID'){
		itemId='jqGrid_collect_tb';
	}else{
		itemId='jqGrid_collect_fm';
	}
	$.commonRequest({
		url:contextPath+'/Collect/toCollect/JqOper/importFromPub/'
			+tableId
			+'/'
			+unitId
			+'/'
			+discId
			+'/'
			+categoryId,
		dataType:'text',
		success:function(data){
				if(data=='success'){
					alert_dialog('导入成功！');
					$("#"+itemId).jqGrid('setGridParam').trigger("reloadGrid");
				}else{
					alert_dialog(data);
				}
			}
		});
}
