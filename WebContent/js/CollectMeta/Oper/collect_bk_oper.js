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
	console.log(controllerDic.YEARMONTH);
	setYearMonthCol(controllerDic.YEARMONTH,id);
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
	edit_link=buildLink(id,'edit',type);
	del_link=buildLink(id,'del',type);	
	var rowData = $("#"+eleId).jqGrid("getRowData",id);
	primaryKey=rowData['ID'];
	console.log('pk : '+primaryKey);
	seqNo=rowData['SEQ_NO'];
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
 * 不公示某条数据
 * @param id
 * @param type
 */
function notPubCollectItem(id,type){
	var eleId='';
	if(type=='0'){
		eleId='jqGrid_collect_tb';
	}
	if(type=='1'){
		eleId='jqGrid_collect_fm';
	}
	var rowData = $("#"+eleId).jqGrid("getRowData",id);
	primaryKey=rowData['ID'];
	$.ajax({
		url:unPrepubUrl,
		type:'POST',
		data:'entityId='+tableId+'&versionId='+versionId+'&pkValue='+primaryKey,
		success : function(data) {
			if (data) {
				alert_dialog("不公示成功");
				$("#"+eleId).trigger("reloadGrid");
			}
		}
	});
}

/**
 * 公示某条数据
 * @param id
 * @param type
 */
function pubCollectItem(id,type){
	var eleId='';
	if(type=='0'){
		eleId='jqGrid_collect_tb';
	}
	if(type=='1'){
		eleId='jqGrid_collect_fm';
	}
	var rowData = $("#"+eleId).jqGrid("getRowData",id);
	primaryKey=rowData['ID'];
	$.ajax({
		url:prepubUrl,
		type:'POST',
		data:'entityId='+tableId+'&versionId='+versionId+'&pkValue='+primaryKey,
		success : function(data) {
			if (data) {
				alert_dialog("公示成功");
				$("#"+eleId).trigger("reloadGrid");
			}
		}
	});
}

/**
 * 删除
 * @param id
 */
function delCollectItem(id,type)
{
	var eleId='';
	if(type=='0'){
		eleId='jqGrid_collect_tb';
	}
	if(type=='1'){
		eleId='jqGrid_collect_fm';
	}
	var rowData = $("#"+eleId).jqGrid("getRowData",id);
	primaryKey=rowData['ID'];
	seqNo=rowData['SEQ_NO'];
	var unitId = rowData['UNIT_ID'];
	var discId = rowData['DISC_ID'];
	console.log('seqNo : '+seqNo);
	if(typeof(seqNo)=='undefined')
	{
		seqNo=-1;
	}
	jQuery('#'+eleId).jqGrid('delGridRow',id,
	{
		/*url:contextPath+'/Collect/toCollect/JqOper/collectionEdit/'
		+ tableId
		+ '/'
		+primaryKey
		+'/'
		+ versionId
		+'/'
		+ seqNo,*/
		url : delPrefixUrl
			+'/'
			+ tableId
			+'/'
			+primaryKey
			+'/'
			+versionId
			+'/'
			+unitId
			+'/'
			+discId
			+'/'
			+seqNo,
		afterSubmit:function(data){
			if(data.responseText=='success')
			{
				//alert_dialog('删除成功！');
				return [true,'操作成功'];
			}else
				{
					return [false,'操作失败！'];
				}
	}});
}