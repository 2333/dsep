<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>权限管理
	</h3>
</div>

<div class="selectbar">
   <table id="editright_tb">
     <tr>
         <td><a id="rightAdd" class="button" href="#"><span class="icon icon-adduser"></span>添加权限</a></td>
         <td><a id="rightEdit" class="button" href="#"><span class="icon icon-edituser"></span>编辑权限</a></td>
         <td><a id="rightDel"class="button" href="#"><span class="icon icon-deleteuser"></span>删除权限</a></td>
     </tr>
   </table>
</div>

<div class="table">
   <table id="rightlist_tb"></table>
   <div id="right_pager_tb"></div>
</div>

<div id="dialog-confirm" title="警告">
</div>
<script type="text/javascript">
    $(document).ready(function(){
   		$("input[type=submit], a.button , button").button();
   	  	$("#rightlist_tb").jqGrid({
   		    url: '${ContextPath}/rbac/rightlist',
            datatype: 'json',
            mtype: 'GET',
   			colNames:['权限ID','权限名称', '父权限ID','权限URL','权限类别','权限等级','相对顺序','备注'],
   			colModel:[
   				{name:'right.id',index:'id', width:60, sorttype:"String",align:"center",editable:true},
   				{name:'right.name',index:'name', width:60,align:"center",editable:true},	
   				{name:'right.parentId',index:'parentId', width:60, align:"center",sorttype:"String",editable:true },
   				{name:'right.url',index:'url', width:60, align:"center",sorttype:"String",editable:true },
   				{name:'rightTypeName',index:'rightTypeName', width:60, edittype:"button",align:"center",sorttype:"String",editable:true },
   				{name:'right.levelNo',index:'levelNo', width:60, align:"center",sorttype:"String",editable:true },
   				{name:'right.seqNo',index:'seqNo', width:60, align:"center",sorttype:"String",editable:true },
   				{name:'right.memo',index:'memo', width:60, align:"center",sorttype:"String",editable:true }
   			],
   			height:"100%",
   			autowidth:true,
   			pager: '#right_pager_tb',
   			pgbuttons:true,
   			rowNum:15,
   			rowList:[15,20,30],
   			viewrecords: true,
   			sortname: 'id',
   			sortorder: "desc",
   			caption: "权限列表",
   			modal:false,
   		    jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
                   root: "rows",  //包含实际数据的数组  
                   page: "pageIndex",  //当前页  
                   total: "totalPage",//总页数  
                   records:"totalCount", //查询出的记录数  
                   repeatitems : false,
               }
   		}).navGrid('#right_pager_tb',{edit:false,add:false,del:false});
   	  
        //权限删除
	    $('#rightDel').click(function(event){
		      var gr = $('#rightlist_tb').jqGrid('getGridParam','selrow');
		      var rowData = $('#rightlist_tb').jqGrid("getRowData", gr);
		      var rightId=rowData["right.id"];
		   	  if(gr!=null)
		   	  {	
		   		  //清空警告框内文本并添加新的警告文本
		   		  $( "#dialog-confirm" ).empty().append("<p>你确定删除该权限吗？</p>");
		   		  $( "#dialog-confirm" ).dialog({
		       	      height:150,
		       	      buttons: {
		       	        "确定": function() {
		       	        	$( this ).dialog( "close" );
		       	        	$.post('${ContextPath}/rbac/rightdelete?rightId=' + rightId, function(data){
		       	    			  if(data == true){
		       	    				  alert_dialog("删除"+ rightId +"权限成功！");
		       	    				  $("#rightlist_tb").setGridParam({url:'${ContextPath}/rbac/rightlist'}).trigger("reloadGrid");
		       	    			  }
		       	    			  else if(data == false)
		       	    			  {
		       	    				  alert_dialog("删除"+ rightId +"权限失败！");
		       	   				  }
		       	    			  else{
		       	    				  alert_dialog(data);
		       	    			  }
		       	    		  });
		       	        },
		       	        "取消": function() {
		       	            $( this ).dialog( "close" );
		       	        }
		       	      }
		       	  });
		   	  }else
	  		  {
	   		      alert_dialog("请选择删除行！");
	  		  }
	   });
       
	   //权限添加
	   $('#rightAdd').click(function(){
		   rightAddDialog("${ContextPath}/rbac/rightadd");
	   }); 
		
	   function rightAddDialog(url){
	 	  $.post(url, function(data){
	 		  $("#dialog").empty();
	 		  $("#dialog").append( data );
	 	 	  $('#dialog').dialog({
	 	  		    title:"添加权限",
	 	  		    height:'300',
	 	  			width:'750',
	 	  			position:'center',
	 	  			modal:true,
	 	  			draggable:true,
	 	  		    hide:'fade',
	 	  			show:'fade',
	 	  		    autoOpen:true,
	 	  		    buttons:{  
	 	  		    	"确定":function(){ 
	 	  		    		if($('.fr_form').valid()){
		 	  	                var fmstr=$('#addRightInfo_fm').serialize();
		 	  	         		$.ajax({
			 	  	      			type:'POST',
			 	  	      			url:'${ContextPath}/rbac/rightsaveadd',
			 	  	      			data:fmstr,
			  	      				success: function(data){
				  	      				if(data){
				  	      					$("#rightlist_tb").setGridParam({url:'${ContextPath}/rbac/rightlist'}).trigger("reloadGrid");
			  	      	   		    		$("#dialog").dialog("close");
			    	  	      			}
			    	  	      			else
			    	  	      			{
				    	  	      			$("#dialog").dialog("close");
			    	  	      				alert_dialog("保存失败！");	
			    	  	      			}
			   	  	      			}
		 	  	      			});
	 	  		    		}
	 	  	            },
	 	 	            "关闭":function(){
	 	 	            	$("#dialog").dialog("close");
	 	 	            }
	 	  		    }
	 	 	  }); 
	 	  }, 'html');
	   }
	   
	   //权限编辑
	   $('#rightEdit').click(function(){
		    var gr = $('#rightlist_tb').jqGrid('getGridParam','selrow');
			if(gr != null){
				var rowData = $('#rightlist_tb').jqGrid("getRowData", gr);
			    var rightId=rowData["right.id"];
			    rightEditDialog("${ContextPath}/rbac/rightedit?rightId="+rightId);
			}
			else{
				alert_dialog("请选择需要编辑的权限!");
			}
		});
	   
	   function rightEditDialog(url){
	 	    $.post(url, function(data){
	 	    	$("#dialog").empty();
		 		$("#dialog").append( data );
		 	 	$('#dialog').dialog({
	 	  		    title:"编辑权限",
	 	  		    height:'300',
	 	  			width:'750',
	 	  			position:'center',
	 	  			modal:true,
	 	  			draggable:true,
	 	  		    hide:'fade',
	 	  			show:'fade',
	 	  		    autoOpen:true,
	 	  		    buttons:{  
	 	  		    	"确定":function(){
	 	  		    		if($('.fr_form').valid()){
			  	                var fmstr=$('#editRightInfo_fm').serialize();
			  	         		$.ajax({
				  	      			type:'POST',
				  	      			url:'${ContextPath}/rbac/rightsaveedit',
				  	      			data:fmstr,
				  	      			success: function(data){
					  	      			if(data){
				  	      					$("#rightlist_tb").setGridParam({url:'${ContextPath}/rbac/rightlist'}).trigger("reloadGrid");
			  	      	   		    		$("#dialog").dialog("close");
			    	  	      			}
			    	  	      			else
			    	  	      			{
				    	  	      			$("#dialog").dialog("close");
			    	  	      				alert_dialog("保存失败！");	
			    	  	      			}
			 	  	                }
		  	      				});
	 	  		    		}
		  	         	},
	   	 	            "关闭":function(){
	   	 	            	$("#dialog").dialog("close");
	   	 	            }
	 	 	        }
	 	  		}); 
	 	    }, 'html');
	   }
    });
</script>

       