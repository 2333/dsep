/*jqgrid的自动提示*/
function loadAutoComplete(elem){		
		var elem_id=$(elem).attr('id');
		var colname=elem_id.split('_')[1];
		var selectSource=new Array();
		for(var i=0;i<jqGridConfig.colConfigs.length;i++)
		{	
			
			if(jqGridConfig.colConfigs[i].name==colname){
				var values=jqGridConfig.colConfigs[i].editoptions.value;
				$.each(values,function(i,item){
					var tmp=new Array();
					tmp['label']=item;
					tmp['value']=i;
					selectSource.push(item);
				});
			}
				
		}
		$(elem).focus().autocomplete({
			source:selectSource,
			minLength:0,
			select:function(event,ui){
				$(elem).val(ui.item.label);
			}
		}).click(function () {
			$("[id^=ui-id-]").css('z-index',10000);
			  // 点击的时候进行查找
			  //$(this).val('');
			  $(this).autocomplete('search','');
			});
		
}
//多选提示框
function mulLoadAutoComplete(elem){		
	var preValue = $(elem).val();
	var elem_id=$(elem).attr('id');
	var colname=elem_id.split('_')[1];
	var selectSource=new Array();
	for(var i=0;i<jqGridConfig.colConfigs.length;i++)
	{	
		
		if(jqGridConfig.colConfigs[i].name==colname){
			var values=jqGridConfig.colConfigs[i].editoptions.value;
			$.each(values,function(i,item){
				var tmp=new Array();
				tmp['label']=item;
				tmp['value']=i;
				selectSource.push(item);
			});
		}
			
	}
	$(elem).focus().autocomplete({
		source:selectSource,
		minLength:0,
		select:function(event,ui){
			
		},
		multiple:true,
	}).click(function () {
		$("[id^=ui-id-]").css('z-index',10000);
		  // 点击的时候进行查找
		  //$(this).val('');
		  $(this).autocomplete('search','');
	});
	
}
//关联的下拉提示框
function linkLoadAutoComplete(elem){
	var elem_id=$(elem).attr('id');
	var colname=elem_id.split('_')[1];
	var id = elem_id.split('_')[0];
	var selectSource=new Array();
	for(var i=0;i<jqGridConfig.colConfigs.length;i++)
	{	
		
		if(jqGridConfig.colConfigs[i].name==colname){
			var values=jqGridConfig.colConfigs[i].editoptions.value;
			$.each(values,function(i,item){
				selectSource.push(item);
			});
		}
			
	}
	$(elem).focus().autocomplete({
		source:selectSource,
		minLength:0,
		select:function(event,ui){
			$(elem).val(ui.item.label);
			var data = ui.item.label;
			 $.each(linkSelectDic,function(index,value){
				 console.log(linkSelectDic);
				  if(index==colname){
					  var subLinkSelect = value;
					  $.each(subLinkSelect,function(subIndex,subValue){
						  var linkSelectValues = new Array();
						  $.each(subValue,function(opItem,opValue){
							  if(opItem==data){
								  $.each(opValue,function(i,opv){
									  linkSelectValues.push(opv);
								  });
								  
							  }
						  });
						  if(linkSelectValues.length>0){
							  setSelectColumnValues(id,subIndex,linkSelectValues);
						  }
					  });
				  } 
			   });
		}
	}).click(function () {
		$("[id^=ui-id-]").css('z-index',10000);
		  // 点击的时候进行查找
		  //$(this).val('');
		  $(this).autocomplete('search','');
		});
}
function setSelectColumnValues(id,column,values){
	if($.inArray(column,controllerDic.SELECT)!=-1){
		var preValue = jQuery("#"+id+"_"+column,"#jqGrid_collect_tb").val();
		jQuery("#"+id+"_"+column,"#jqGrid_collect_tb").empty();
		var option = "<option value="+preValue+">"+preValue+"</option>";
		jQuery("#"+id+"_"+column,"#jqGrid_collect_tb").append(option);
		$.each(values,function(i,item){
			option = "<option value="+item+">"+item+"</option>";
			jQuery("#"+id+"_"+column,"#jqGrid_collect_tb").append(option);
		});
	}else{
		jQuery("#"+id+"_"+column,"#jqGrid_collect_tb").focus().autocomplete({
			source:values,
			minLength:0,
			select:function(event,ui){
				//$(elem).val(ui.item.label);
				jQuery("#"+id+"_"+column,"#jqGrid_collect_tb").val(ui.item.label);
			}
		}).click(function () {
			$("[id^=ui-id-]").css('z-index',10000);
			  // 点击的时候进行查找
			  //$(this).val('');
			  $(this).autocomplete('search','');
			});
	}
	if($.inArray(column,controllerDic.SELECT)!=-1){
		var preValue = jQuery("#"+id+"_"+column,"#collect_batch_add_tb").val();
		jQuery("#"+id+"_"+column,"#collect_batch_add_tb").empty();
		var option = "<option value="+preValue+">"+preValue+"</option>";
		jQuery("#"+id+"_"+column,"#collect_batch_add_tb").append(option);
		$.each(values,function(i,item){
			option = "<option value="+item+">"+item+"</option>";
			jQuery("#"+id+"_"+column,"#collect_batch_add_tb").append(option);
		});
	}else{
		jQuery("#"+id+"_"+column,"#collect_batch_add_tb").focus().autocomplete({
			source:values,
			minLength:0,
			select:function(event,ui){
				//$(elem).val(ui.item.label);
				jQuery("#"+id+"_"+column,"#collect_batch_add_tb").val(ui.item.label);
			}
		}).click(function () {
			$("[id^=ui-id-]").css('z-index',10000);
			  // 点击的时候进行查找
			  //$(this).val('');
			  $(this).autocomplete('search','');
			});
	}
}