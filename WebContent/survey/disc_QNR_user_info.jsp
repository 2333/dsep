<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>问卷调查用户管理
	</h3>
</div>

<div class="selectbar inner_table_holder" >
	<!-- 
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">学科ID：</span>
			</td>
			<td>
				<input type="text" id="disc_Id" value='${discId}' disabled/>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<span class="TextFont">对象类型：</span>
			</td>
			<td>
				<select id="respondent_type">
				<option value="1">在校生</option>
				<option value="2">毕业生</option>
				<option value="3">专家</option>
				<option value="4">用人单位</option>
				</select>
			</td>
 			<td>
				<a id="query_btn" class="button" href="#"><span class="icon icon-search"></span>查询</a>
			</td>
		</tr>
	</table>
	-->
	
	<table class="layout_table right">
		<tr>
			<td>
				<a id="add_btn" class="button" href="#"><span class="icon icon-add"></span>添加</a>
			</td>
			<td>
				<a id="import_btn" class="button" href="#"><span class="icon icon-export"></span>导入</a>
			</td>	
			<!--  
            <td>
				<a id="export_btn" class="button" href="#"><span class="icon icon-export"></span>导出</a>
			</td>
			-->
		</tr>
	</table>
</div>

<div class="layout_holder">
	<div class="layout_holder">
		<table id="user_list"></table>
		<div id="user_pager"></div>
	</div>
</div>

<div id="dialog-confirm" title="警告"></div>
<script type="text/javascript">
	  
      $(document).ready(function(){
    	  
    	 	$('input[type=submit], a.button , button').button();
    	 	
    	 	jQuery("#user_list").jqGrid({
    		 	url:'${ContextPath}/InfoMaintain/discQNRInfo_user_list',
           		datatype: 'json',
           		mtype:'GET',
    		   	colNames:['', "ID",'姓名', '性别','专业','邮箱'],
    		   	colModel:[
					{name:'modifyInfo',index:'modifyInfo',align:"center", width:200},
					{name:'id',index:'id', width:200,align:"center", hidden:true},
					{name:'name',index:'name', width:200,align:"center"},
    		   		{name:'gender',index:'gender', width:200, align:"center",formatter:function(cellvalue, options, row){
				     	if(cellvalue=="0"){
				        	 return "女";
				    	}else{
				        	return "男";
				   		}
				 	}},
    		   		{name:'discId',index:'discId', width:200, align:"center"},		
    		   		{name:'email',index:'email', width:340, align:"center"},				
    		   	],
    		   	rowNum:20,
    		   	autowidth:true,
    		   	height:"100%",
    		   	rowList:[20,30],
    		   	pager: '#user_pager',
    		   	sortname: 'name',
    		    viewrecords: true,
    		    sortorder: "desc",
    		    gridComplete: function(){
    		    	var ids = jQuery("#user_list").jqGrid('getDataIDs');
    				for(var i=0;i < ids.length;i++){
    					
    					var modify = "<a  href='#' onclick=modifyUser('"+ids[i]+"')>编辑</a>"+"/"+
    					              "<a href='#' onclick=deleteUser('"+ids[i]+"')>删除</a>"; 
    					jQuery("#user_list").jqGrid('setRowData',ids[i],{modifyInfo :modify});
    				}
    		    },
    		    caption: "调查对象汇总"
    		});
    		jQuery("#user_list").jqGrid('navGrid','#user_pager',{edit:true,add:true,del:true,search:true});
      		
    		//添加问卷对象
    		$("#add_btn").click(function(){
    			 $.post("${ContextPath}/InfoMaintain/discQNRInfo_surveyUserAdd", function(data){
   		   		  $('#dialog').empty();
   				  $('#dialog').append( data );
   			 	  $('#dialog').dialog({
   		   	  		    title:"新建调查对象",
   		   	  		    height:'300',
   		   	  			width:'800',
   		   	  			position:'center',
   		   	  			modal:true,
   		   	  			draggable:true,
   		   	  		    hide:'fade',
   		   	  			show:'fade',
   		   	  		    autoOpen:true,
   		   	  		    buttons:{
   		   	  		    	"确定":function(){
   		   	  		    		if($("#add_survey_user").valid()){
   			   	  	         		$.ajax({
   			    	  	      			type:'POST',
   			    	  	      			url:'${ContextPath}/InfoMaintain/discQNRInfo_surveyUserSave',
   			    	  	      			data:$('#add_survey_user').serialize(),
   			    	  	      			success: function(data){
   			   	  	      	   				if(data == true){
   			   	  	      	   					alert_dialog("新建成功！");	
   			   	  	      	   					$("#user_list").setGridParam({url:'${ContextPath}/InfoMaintain/discQNRInfo_user_list'}).trigger("reloadGrid");
   			   	  	      	   					$("#dialog").dialog("close");
   			   	  	      	   				}
   			   	  	      	   				else{
   					   	  	      	   			$("#dialog").dialog("close");
   						  	      				alert_dialog("新建失败！");	
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
    		});
    		
    		//导入调查对象
    		$("#import_btn").click(function(){
   			 $.post("${ContextPath}/InfoMaintain/discQNRInfo_importUser", function(data){
  		   		  $('#dialog').empty();
  				  $('#dialog').append( data );
  			 	  $('#dialog').dialog({
  		   	  		    title:"导入调查对象",
  		   	  		    height:'300',
  		   	  			width:'800',
  		   	  			position:'center',
  		   	  			modal:true,
  		   	  			draggable:true,
  		   	  		    hide:'fade',
  		   	  			show:'fade',
  		   	  		    autoOpen:true,
  		   	  		    buttons:{
  		   	 	            "关闭":function(){
  		   	 	            	$("#dialog").dialog("close");
  		   	 	            }
  		   	  		    }
  		   	 	  }); 
  		   	  }, 'html');
   			});
    		
    		
      
      });
      
      //删除用户
      function deleteUser(userId){
	  	  
	  	 
  		  //清空警告框内文本并添加新的警告文本
  		  $( "#dialog-confirm" ).empty().append("<p>你确定删除该用户吗？</p>");
  		  $( "#dialog-confirm" ).dialog({
      	      height:150,
      	      modal:true,
      	      buttons: {
      	        "确定": function() {
      	        	$( this ).dialog( "close" );
      	  			
      	        	$.post('${ContextPath}/InfoMaintain/discQNRInfo_deleteUser?userId=' +userId, function(data){
      	    			  if(data == true){
      	    				  alert_dialog("删除成功！");
      	    				  $("#user_list").setGridParam({url:'${ContextPath}/InfoMaintain/discQNRInfo_user_list'}).trigger("reloadGrid");
      	    			  }
      	    			  else if(data == false)
      	    			  {
      	    				  alert_dialog("删除失败！");
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
	  	event.preventDefault();
	  	  
      }
      
     //编辑用户
      function modifyUser(userId){
	  	
    	  $.post("${ContextPath}/InfoMaintain/discQNRInfo_surveyUserEdit?userId= "+ userId, function(data){
		   		  $('#dialog').empty();
				  $('#dialog').append( data );
			 	  $('#dialog').dialog({
		   	  		    title:"编辑调查对象",
		   	  		    height:'300',
		   	  			width:'800',
		   	  			position:'center',
		   	  			modal:true,
		   	  			draggable:true,
		   	  		    hide:'fade',
		   	  			show:'fade',
		   	  		    autoOpen:true,
		   	  		    buttons:{
		   	  		    	"确定":function(){
		   	  		    		if($("#edit_survey_user").valid()){
			   	  	         		$.ajax({
			    	  	      			type:'POST',
			    	  	      			url:'${ContextPath}/InfoMaintain/discQNRInfo_surveyUserSave',
			    	  	      			data:$('#edit_survey_user').serialize(),
			    	  	      			success: function(data){
			   	  	      	   				if(data == true){
			   	  	      	   					alert_dialog("修改成功！");	
			   	  	      	   					$("#user_list").setGridParam({url:'${ContextPath}/InfoMaintain/discQNRInfo_user_list'}).trigger("reloadGrid");
			   	  	      	   					$("#dialog").dialog("close");
			   	  	      	   				}
			   	  	      	   				else{
					   	  	      	   			$("#dialog").dialog("close");
						  	      				alert_dialog("修改失败！");	
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
  		
	  	event.preventDefault();
	  	  
      }
      
     
</script>