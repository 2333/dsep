/**
 * 按钮集
 */
var id;
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
		 link="<a class='' href='#' onclick='delCollectItem("+id+","+type+")'>删除</a>";
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
	//console.log(controllerDic.YEARMONTH);
	setYearMonthCol(controllerDic.YEARMONTH,id);
	//console.log(controllerDic.LINKSELECT);
	setLinkSelectCol(controllerDic.LINKSELECT,id);
	//上传文件附件
	setUploadFileCol(controllerDic.FILE,id);
}

/**
 * 保存
 * @param id
 */
function saveCollectItem(id,type)
{
	var eleId='';
	var edit_link='';
	var del_link='';
	if(type=='0'){
		eleId='jqGrid_collect_tb';	
	}
	if(type=='1'){
		eleId='jqGrid_collect_fm';
	}
	currentSaveRowId = id;
	currentJqGridId = eleId; 
	edit_link=buildLink(id,'edit',type);
	del_link=buildLink(id,'del',type);	
	var rowData = $("#"+eleId).jqGrid("getRowData",id);
	primaryKey=rowData['ID'];
	console.log('pk : '+primaryKey);
	//var collegeId='10006';
	//var disciplineId='0835';
	var userId = rowData['ACHIEVE_OF_PERSON_ID'];
	jQuery("#"+eleId).jqGrid('saveRow',id,
	  {
		url:contextPath+'/TCollect/toTCollect/JqOper/collectionTEdit/'
		+ tableId
		+ '/'
		+primaryKey
		+'/'
		+ titleValues
		+ '/'
		+ userId
		+ '/'
		+seqNo,
		aftersavefunc:function(data){
		if(data){
			jQuery("#"+eleId).jqGrid('setRowData',id,{oper :edit_link+' | '+del_link});	
		}else
			alert_dialog('保存失败！');
		},successfunc:function(data){
			if(data.responseText=='success'){
				//alert_dialog('编辑成功！');
				//如果主键为0，表示数据为前台生成，需重新加载数据的主键
				if(primaryKey=='0')
				{
					$("#"+eleId).jqGrid('setGridParam').trigger("reloadGrid");
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
	console.log('cancel!');
	var eleId='';
	var del_link='';
	var edit_link='';
	if(type=='0'){
		eleId='jqGrid_collect_tb';
	}
		
	if(type=='1'){
		eleId='jqGrid_collect_fm';
	}
	edit_link=buildLink(id,'edit',type);
	del_link=buildLink(id,'del',type);	
	jQuery('#'+eleId).jqGrid('restoreRow',id);
	jQuery("#"+eleId).jqGrid('setRowData',id,{oper :edit_link+' | '+del_link});
	
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
	if(type=='0'){
		eleId='jqGrid_collect_tb';
	}
	if(type=='1'){
		eleId='jqGrid_collect_fm';
	}
	var rowData = $("#"+eleId).jqGrid("getRowData",id);
	primaryKey=rowData['ID'];
	//seqNo=rowData['SEQ_NO'];
	//console.log('seqNo : '+seqNo);
	seqNo=-1;
	var userId = rowData['ACHIEVE_OF_PERSON_ID'];
	if(typeof(seqNo)=='undefined')
	{
		seqNo=-1;
	}
	jQuery('#'+eleId).jqGrid('delGridRow',id,
	{
		url:contextPath+'/TCollect/toTCollect/JqOper/collectionTEdit/'
		+ tableId
		+ '/'
		+primaryKey
		+'/'
		+ titleValues
		+ '/'
		+  userId
		+'/'
		+ seqNo,
		afterSubmit:function(data){
			if(data.responseText=='success')
			{
				//alert_dialog('删除成功！');
				return [true,'删除成功！'];
			}else
				{
					return [false,'删除失败！'];
				}
	}});
}

function excel_output(tableId,sortOrder,sortName,searchRule)
{
	var url=contextPath+'/TCollect/toTCollect/export/excel/'
			+ tableId
			+ '/'
			+ sortOrder
			+'/'
			+ sortName
			;
	if(searchRule==''){
		outputJS(url);
	}else{
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
	var pkValues= new Array();//主键集合
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
						pkValues.push(pkvalue);
					});
					$.commonRequest({
						url:contextPath+"/TCollect/toTCollect/JqOper/batchDel/"+tableId+"/"+pkValues,
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
	var url = contextPath+"/TCollect/toTCollect/JqOper/downLoadAttach/"+attachId;
	$.post(url,function(data){
			downloadProveMaterial(data);
	}, 'json');
}