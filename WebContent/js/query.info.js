
/**
 * 生成jqGrid的查看按钮
 * 调用此函数时jqGrid的colModle的查看按钮ID必须为query
 * @param tableId jqGrid的ID
 * @param clickMethodName 按钮事件的名称
 */
function produceQuery(theTableId,clickMethodName){
	//alert(theTableId);
	var ids = $(theTableId).getDataIDs();
	for(var i=0;i < ids.length;i++){
		var view = "<a href='#' class='check_detail' onclick='"+clickMethodName+"("+ids[i]+")'>查看</a>";
		jQuery(theTableId).setRowData(ids[i],{query:view});//按钮ID必须为query
	}
}

/**
 * 进入编辑模式
 */
function intoEditStyle(){
	$("input").removeAttr("readonly");
	$("input").css("border","1px solid #B7B7B7");
	$("textarea").removeAttr("readonly");
	$("select").removeClass("disable_select");
	$("input.hidden_text").attr("type","hidden");
}
/**
 * 进入编辑模式（保留部分字段只读）---刘琪
 */
function gotoEditStyle(){
	$(".info_inputL").removeAttr("readonly");
	$(".info_inputR").removeAttr("readonly");
	$(".info_input_memo").removeAttr("readonly");
	$("input").css("border","1px solid #B7B7B7");
	$("textarea").removeAttr("readonly");
	$("input.hidden_text").attr("type","hidden");
}
/**
 * 保存后进入不可编辑模式
 */
function intoSaveStyle(){
	$("input").attr({readonly:"readonly"});
	$("input").css("border","0px solid");
	$("textarea").attr({readonly:"readonly"});
	$("input.hidden_text").attr("type","text");
}