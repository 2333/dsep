function setUploadFileCol(collectionFileCols,id){
	  initUploadConfig();//该函数在collect_upload.jsp中，初始化配置信息
	  $.each(collectionFileCols,function(i,item){
		  var fileNo=$("#"+id+"_"+item).val();
		  if($.trim(fileNo)==""&&item!="ATTACH_ID"){
			  $("#"+id+"_"+item).val("请上传附件");
		  }
		  $("#"+id+"_"+item).click(function(){
			attachOperElem = $("#"+id+"_"+item);
			attachElement = $("#"+id+"_"+"ATTACH_ID");
			fileNo=$("#"+id+"_"+item).val();
			attachId = attachElement.val();
			if(fileNo=='请上传附件'){
			//var dialogParent = $('#uploadFile_dialog_div').parent();  
			//克隆弹框里面的内容
			//var dialogOwn = $('#uploadFile_dialog_div').clone();
			$("#uploadFile_dialog_div").dialog({
								 title:"上传附件",
					  	  		 height:'300',
					  	  	     width:'400',
					  	  		 position:'center',
					  	  		 modal:true,
					  	  		 draggable:true,
					  	  		 close:function(){
					  	  			//$('#file_upload').uploadify('destroy');
					  	  			 //dialogOwn.appendTo(dialogParent);
					  		   		//$(this).dialog("destroy").remove(); 
					  		   		
					  	  		 },
					  	  	     buttons:{  
				  	  		    	"关闭":function(){ 
				  	  		    	    $("#uploadFile_dialog_div").dialog("close");
				  	  		    	}
							     }
							  });
				   }else{
					   //attachId= attachElement.val();
					   //var dialogParent = $('#downloadFile_dialog_div').parent();  
					   //克隆弹框里面的内容
					   var dialogOwn = $('#downloadFile_dialog_div').clone();
					   initDownLondFunc();//在collet_download.jsp页面
					   $("#downloadFile_dialog_div").dialog(
							   		{
										 title:"下载附件",
							  	  		 height:'250',
							  	  	     width:'400',
							  	  		 position:'center',
							  	  		 modal:true,
							  	         close:function(){
							  	        	//dialogOwn.appendTo(dialogParent);
							  		   		//$(this).dialog("destroy").remove(); 
							  		   		
							  	         },
							  	  		 draggable:true,
							  	  	     buttons:{  
						  	  		    	"关闭":function(){ 
						  	  		    	      $("#downloadFile_dialog_div").dialog("close");
						  	  		    	}
									     }
									  });
				   }  
			   });
		  
	  });	
}


 


