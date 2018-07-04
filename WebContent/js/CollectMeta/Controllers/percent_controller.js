function setPercentCol(collectionDateCols,id){

	  $.each(collectionDateCols,function(i,item){
		  var hjunitnum=$("#"+id+"_"+item).val();   //获取参与单位数(总)
		  if($.trim(hjunitnum)==""){
			  $("#"+id+"_"+item).val("点击编辑");
			}
		   $("#"+id+"_"+item).click(function(){        //按钮点击事件
		         hjunitnum=$("#"+id+"_"+item).val();      //获取参与单位数(总)
		         if(hjunitnum.indexOf("(") > 0 ){
		        	 $.post(percentUrl, function(data){
		        		  $('#percent_dialog_div').empty();
		    			  $('#percent_dialog_div').append( data );
		    			  $("#deparorder_div").show();
		    			  $("#deparpropor_div").hide();
		    			  $("#propor").removeAttr("checked");
		    			  $("#order").attr("checked",true);
		        	      //解析相关字段，并给对话框赋值
					      var orderstartkey=hjunitnum.indexOf("(");
					      var orderendkey=hjunitnum.indexOf(")");
					      var orderparnumber = hjunitnum.substring (0,orderstartkey);       //获取参与单位数
					      $("#parnumber").val(orderparnumber);                              //给即将出现的对话框字段赋值
					      var orderdeparorder = hjunitnum.substring (orderstartkey+1,orderendkey); //本单位署名次序
				          $("#deparorder").val(orderdeparorder); 
				          $("#parTnumber").val(orderparnumber);               //初始化教师人数
	  	  		    	  $("#teacherOrder").val(orderdeparorder); 			//初始化教师排名
                           //给即将出现的对话框字段赋值
				          $("#order").click(function(){
						       $("#deparpropor_div").hide();
						       $("#deparorder").val("");
						       $("#deparorder_div").show();
						  });
						  $("#propor").click(function(){
						       $("#deparorder_div").hide();
						       $("#deparpropor").val("");
						       $("#deparpropor_div").show();
						       	});
		        	 },'html');
	
			     }
		        else /*if(hjunitnum.indexOf("[") > 0 )*/
		        {
		        	 $.post(percentUrl, function(data){
		        		  $('#percent_dialog_div').empty();
		    			  $('#percent_dialog_div').append( data );
					      $("#deparorder_div").hide();
					      $("#deparpropor_div").show();
		    			  $("#order").removeAttr("checked");
		    			  $("#propor").attr("checked",true);
					      //解析相关字段，并给对话框赋值
					      var proporstartkey=hjunitnum.indexOf("[");
					      var proporendkey=hjunitnum.indexOf("]");
					      var proporparnumber = hjunitnum.substring (0,proporstartkey);      
				          $("#parnumber").val(proporparnumber);  

                           
				          var propordeparorder = hjunitnum.substring (proporstartkey+1,proporendkey-1);
				          $("#deparpropor").val(propordeparorder);
				          $("#order").click(function(){
				        	   $(this).attr("checked","checked");
				        	   $("#propor").removeAttr("checked");
						       $("#deparpropor_div").hide();
						       $("#deparorder").val("");
						       $("#deparorder_div").show();
						  });
						  $("#propor").click(function(){
							   $(this).attr("checked","checked");
							   $("#order").removeAttr("checked");
						       $("#deparorder_div").hide();
						       $("#deparpropor").val("");
						       $("#deparpropor_div").show();
						 });
		        	 },'html');

		        }
				  $("#percent_dialog_div").dialog(
						  {
							 title:"编辑",
				  	  		 height:'300',
				  	  	     width:'400',
				  	  		 position:'center',
				  	  		 modal:true,
				  	  		 draggable:true,
				  	  	     buttons:{  
			  	  		    	"确定":function(){ 
			  	  		    	var newhjunitnum = '';
			  	  		    	if(editType=='4'){
			  	  		    		parnumber = $("#parTnumber").val();               //得到当前对话框字段的参与单位数
			  	  		    		orderdeparorder = $("#teacherOrder").val();        //得到当前对话框字段的本单位署名次序
			  	  		    		newhjunitnum = parnumber+"("+orderdeparorder+")";//组合成一个字段
			  	  		    	}else{
			  	  		    		
			  	  		    		if($("#order").is(':checked')){
			  	  		    			parnumber = $("#parnumber").val();               //得到当前对话框字段的参与单位数
			  	  		    			orderdeparorder = $("#deparorder").val();        //得到当前对话框字段的本单位署名次序
			  	  		    			newhjunitnum = parnumber+"("+orderdeparorder+")";//组合成一个字段
		  	  		    			}else if($("#propor").is(':checked')){
			  	  		    			parnumber = $("#parnumber").val();                 //得到当前对话框字段的参与单位数
			  	  		    			propordeparorder = $("#deparpropor").val();        //得到当前对话框字段的本单位署名次序
			  	  		    			newhjunitnum = parnumber+"["+propordeparorder+"%]";//组合成一个字段
			  	  		    		}
			  	  		    	}
			  	  		    	      
			  	  		    	      $("#"+id+"_"+item).val(newhjunitnum);//向表格相应字段赋值
			  	  		    	      $("#percent_dialog_div").dialog("close");
			  	  		    	},
			  	  		    	"取消":function(){ 
			  	  		    	      $("#percent_dialog_div").dialog("close");
			  	  		    	}
						     }
						  });
		   });
	  });	
}

 


