function setLinkSelectCol(collectionLinkSelectCols,id){
	console.log(collectionLinkSelectCols);
	$.each(collectionLinkSelectCols,function(i,item){
		   jQuery("#"+id+"_"+item,"#jqGrid_collect_tb").change(function(){
			   var mainSel= jQuery("#"+id+"_"+item,"#jqGrid_collect_tb").val();
			   if(mainSel!=''){
				   $.each(linkSelectDic,function(index,value){
						  if(index==item){
							  var subLinkSelect = value;
							  $.each(subLinkSelect,function(subIndex,subValue){
								  var linkSelectValues = new Array();
								  $.each(subValue,function(opItem,opValue){
									  if(opItem==mainSel){
										  $.each(opValue,function(i,opv){
											  linkSelectValues.push(opv);
										  });  
									  }
								  });
								  console.log(linkSelectValues);
								  setSelColumnValues(id,subIndex,linkSelectValues);
							  });
						  } 
					   });
			   }
		   });
		   jQuery("#"+id+"_"+item,"#collect_batch_add_tb").change(function(){
			   var mainSel= jQuery("#"+id+"_"+item,"#collect_batch_add_tb").val();
			   if(mainSel!=''){
				   $.each(linkSelectDic,function(index,value){
						  if(index==item){
							  var subLinkSelect = value;
							  $.each(subLinkSelect,function(subIndex,subValue){
								  var linkSelectValues = new Array();
								  $.each(subValue,function(opItem,opValue){
						
									  console.log(mainSel);
									  if(opItem==mainSel){
										  $.each(opValue,function(i,opv){
											  linkSelectValues.push(opv);
										  });
										  
									  }
								  });
								  setBatchSelectColumnValues(id,subIndex,linkSelectValues);
							  });
						  } 
					   });
			   	}
			   
		   });
	});	
}
function setSelColumnValues(id,column,values){
	if($.inArray(column,controllerDic.SELECT)!=-1){
		//var preValue = jQuery("#"+id+"_"+column,"#jqGrid_collect_tb").val();
		jQuery("#"+id+"_"+column,"#jqGrid_collect_tb").empty();
		//var option = "<option value="+preValue+">"+preValue+"</option>";
		//jQuery("#"+id+"_"+column,"#jqGrid_collect_tb").append(option);
		var option;
		$.each(values,function(i,item){
			option = "<option value="+item+">"+item+"</option>";
			jQuery("#"+id+"_"+column,"#jqGrid_collect_tb").append(option);
		});
	}else{
		//$("input[id$='"+id+"_"+column+"']").val("");
		jQuery("#"+id+"_"+column,"#jqGrid_collect_tb").val('');
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
	
}
function setBatchSelectColumnValues(id,column,values){
	if($.inArray(column,controllerDic.SELECT)!=-1){
		//var preValue = jQuery("#"+id+"_"+column,"#collect_batch_add_tb").val();
		jQuery("#"+id+"_"+column,"#collect_batch_add_tb").empty();
	//	var option = "<option value="+preValue+">"+preValue+"</option>";
		var option;
		//jQuery("#"+id+"_"+column,"#collect_batch_add_tb").append(option);
		$.each(values,function(i,item){
			option = "<option value="+item+">"+item+"</option>";
			jQuery("#"+id+"_"+column,"#collect_batch_add_tb").append(option);
		});
	}else{
		jQuery("#"+id+"_"+column,"#collect_batch_add_tb").val('');
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