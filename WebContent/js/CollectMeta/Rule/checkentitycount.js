function checkEntityCount(elemId)
{
	var result = false;
	var key;
	var value;
	if($.trim(entityRule)!=''){
		var rules = entityRule.split(";");
		if(rules.length>0){
			$.each(rules,function(index,item){
				key = item.split(":")[0];
				if(key==categoryId){
					value = item.split(":")[1];
					//var ids = jQuery("#collect_batch_add_tb").jqGrid('getDataIDs');
					var ids = jQuery("#"+elemId).jqGrid('getDataIDs');
					var orignalRecords=$("#jqGrid_collect_tb").jqGrid('getGridParam','records');
					if(elemId=="collect_import_excel_tb"){
						if(parseInt(ids.length)+parseInt(orignalRecords)<=parseInt(value))
						{
							result = true;
						}
					}else{
						if(parseInt(ids.length)+parseInt(orignalRecords)<parseInt(value))
						{
							result = true;
						}
					}
					
				}
			});
		}else{
			result = true;
		}
	}else{
		result = true;
	}
	if(result){
		return result;
	}else{
		alert_dialog('该项采集成果不得超过'+value+'条！');
		return result;
	}
	
}