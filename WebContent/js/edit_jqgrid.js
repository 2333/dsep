

function editRow(list_name,ci){
	$(list_name).editRow(ci,true,{reloadAfterSubmit:true});
	$(list_name).find("input").keydown(function(){
		if(event.keyCode == 13){
			saveRow(list_name,ci);
		}
		else if( event.keyCode == 27){
			cancelRow(list_name,ci);
		}
	});
	var save = "<a href='#' id='saveGrid'  onclick=\"saveRow('"+list_name+"','"+ci+"')\">保存</a>";
	var cancel = "<a href='#' id='cancelGrid'  onclick=\"cancelRow('"+list_name+"','"+ci+"');\">取消</a>";
	$(list_name).setRowData(ci,{edit:save+' | '+cancel});
}

function saveRow(list_name,ci){
	$(list_name).saveRow(ci);
	var edit = "<a href='#' id='editGrid' onclick=\"editRow('"+list_name+"','"+ci+"');\">编辑</a>";
	$(list_name).setRowData(ci,{edit:edit});
}

function cancelRow(list_name,ci){
	$(list_name).restoreRow(ci);
	var edit = "<a href='#' id='editGrid'  onclick=\"editRow('"+list_name+"','"+ci+"');\">编辑</a>";
	$(list_name).setRowData(ci,{edit:edit});
}