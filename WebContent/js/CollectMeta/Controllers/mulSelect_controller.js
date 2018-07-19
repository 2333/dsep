function setMulSelectCol(collectionDateCols,id){
	
	$.each(collectionDateCols,function(i,item){
		 var values=$("#"+id+"_"+item).val();   
		 if(values==''){
			 $("#"+id+"_"+item).val('点击进行选择');
		 }
		 //$("#"+id+"_"+item).attr('values',values); 
		 //$("#"+id+"_"+item).val('请点击进行选择');
		 var mulSelectSource = new Array();
		 
		 for(var i=0;i<jqGridConfig.colConfigs.length;i++)
		 {	
			if(jqGridConfig.colConfigs[i].name==item){
				var values=jqGridConfig.colConfigs[i].editoptions.valueMulSel;
				$.each(values,function(i,item){
					mulSelectSource.push(item);
				});
			}			
		 }
		 $("#"+id+"_"+item).click(function(){
			 $("#checks_dv").empty();
			 $.each(mulSelectSource,function(i,selValue){
				 if($.trim(selValue)!=''){
					 var checkBox = '<label><input name="check_'+item+'" type="checkbox" value="'+selValue+'"/>'+selValue+'</label><br>';  
					 $("#checks_dv").append(checkBox);
				 }
			 });
			 var seledValues = $("#"+id+"_"+item).val().split(',');
			 $.each(seledValues,function(i,seledValue){
				 $("input[name='check_"+item+"']").each(function(){
					 //alert($(this).val() +" : "+seledValue);
					 if($(this).val()==seledValue){
						 $(this).attr("checked",true);
					 }else{
						 $(this).attr("checked");
					 }
				 }); 
			 });
			  $("#mulSelect_dialog_div").dialog(
			  {
					title:"多选选择",
				  	height:'250',
				  	width:'400',
				  	position:'center',
				  	modal:true,
				  	draggable:true,
				  	buttons:{ 
				  	  	  "确定":function(){
				  	  		var reSeledVals=''; 
				  	  		$("input[name='check_"+item+"']").each(function(){
				  	  		 // alert($(this).is(':checked'));
				  	          if ($(this).is(':checked')){
				  	        	if(reSeledVals!=''){
				  	        		reSeledVals +=","+$(this).val();
				  	        	}else{
				  	        		reSeledVals +=$(this).val();
				  	        	}
				  	        	
				  	          }
				  	  		});	
				  	  		//alert('已选：' +reSeledVals);
				  	  		$("#"+id+"_"+item).val(reSeledVals);
				  	  		$("#mulSelect_dialog_div").dialog("close");
				  	  	   },
			  	  		   "关闭":function(){ 
			  	  			   $("#mulSelect_dialog_div").dialog("close");
			  	  		   }
					}
			  });
		 });
		 
	});
}