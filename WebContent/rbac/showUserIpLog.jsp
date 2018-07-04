<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>日志管理
	</h3>
</div>
<div class="selectbar inner_table_holder">
	<form id="fm_search" method="post">
		<table class="layout_table">
			<tr>
				<td class="left_space">
				    <span class="TextFont">登录ID：</span>
				</td>
				<td >
					<input id="loginId" name="loginId" type="text" value="" size="20"/>
				</td>
				<!-- <td class="left_space">
				    <span class="TextFont">姓名：</span>
				</td>
				<td >
					<input id="name" name="name" type="text" value="" size="10"/>
				</td>
				
				<td>
				    <span class="TextFont">用户类型：</span>
				</td>
				<td >
					<select id="userType" name="userType">
			      		<option value="-">全部</option>
			      		<option value="0">管理员用户</option>
			      		<option value="1">test用户</option>
			      		<option value="2">高级代理</option>
			      		<option value="3">普通代理</option>
			      		<option value="4">普通用户</option>
			      		<option value="5">other1用户</option>
			      		<option value="6">other2用户</option>
		               </select>
				</td>
				
				<td class="left_space">
				    <span class="TextFont">单位码：</span>
				</td>
				<td >
					<input id="unitId" name="unitId" type="text" value="" size="6"/>
				</td>
				
				<td class="left_space">
				    <span class="TextFont">用户码：</span>
				</td>
				<td >
					<input id="discId" name="discId" type="text" value="" size="6"/>
				</td> -->
				<td class="left_space">
					<a  id="search_btn" class="button" href="#"><span class="icon icon-search"></span>查询</a>
				</td>
			</tr>
		</table>
	</form>
</div>
<div class="selectbar">
   <!-- <table>
	   <tr>
	       <td><a id="useradd" class="button" href="#"><span class="icon icon-adduser"></span>添加用户</a></td>
	       <td><a id="useredit" class="button" href="#"><span class="icon icon-edituser"></span>编辑用户</a></td>
	       <td><a id="userdelete" class="button" href="#"><span class="icon icon-deleteuser"></span>删除用户</a></td>
	   </tr>
   </table> -->
</div>
<div class="table">
    <table id="userlist_tb"></table>
	<div id="user_pager_tb"></div>
</div>
<div id="dialog-confirm" title="警告">
</div>
<script type="text/javascript">
	$(document).ready(function(){    	
	   	$( 'input[type=submit], a.button , button' ).button();
	   	  
	   	$('#userlist_tb').jqGrid({
	 		url: '${ContextPath}/rbac/userIpLogQuery',
	        datatype: 'json',
	        mtype: 'GET',
			colNames:['用户ID','登陆ID','myLog'],
			colModel:[
	             	{name:'id',index:'id',width:60,align:"center", hidden:true},
					{name:'loginId',index:'loginId', width:90,align:"center", sorttype:"String",editable:true },
					{name:'myLog',index:'myLog', width:100,align:"center", sorttype:"String",editable:true }
			],
			height:'100%',
			autowidth:true,
			pager: '#user_pager_tb',
			rowNum:20,
			rowList:[20,30],
			viewrecords: true,
			sortname: 'name',
			sortorder: 'desc',
			caption: "日志列表",
		    jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
	            root: 'rows',  //包含实际数据的数组  
	            page: 'pageIndex',  //当前页  
	            total: 'totalPage',//总页数  
	            records:'totalCount', //查询出的记录数  
	            repeatitems : false      
	   		}
	 	}).navGrid('#user_pager_tb',{edit:false,add:false,del:false});
	   	
	  	//查询用户
		$('#search_btn').click(function(){
			
			jQuery("#userlist_tb").setGridParam({
				url:"${ContextPath}/rbac/userSearch?"
					+"loginId="+$("#loginId").val()
					+"&name="+$("#name").val()
				 	+"&userType="+$("#userType").val()
				 	+"&unitId="+$("#unitId").val()
				 	+"&discId="+$("#discId").val(),
				sortorder: "desc",
				sortname:'name',
				page:1,
			}).trigger("reloadGrid");
			event.preventDefault();
		});
	
	});
 
 
     //编辑用户
     $('#useredit').click(function(){
		var gr = $('#userlist_tb').jqGrid('getGridParam','selrow');
		if(gr != null){
			var rowData = $('#userlist_tb').jqGrid("getRowData", gr);
			var id=rowData["user.id"];
			userEditDialog("${ContextPath}/rbac/useredit?userId="+id);
		}
		else{
			alert_dialog("请选择需要编辑的用户!");
		}
     });
     
     //弹出编辑框
     function userEditDialog(url){
	   	 $.post(url, function(data){
	   		  $('#dialog').empty();
			  $('#dialog').append( data );
		 	  $('#dialog').dialog({
	   	  		    title:"编辑用户",
	   	  		    height:'750',
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
		   	  	                var ids = getCheckedArray("user_roles");
		   	  	                var fmstr=$('#editUserInfo_fm').serialize();
		   	  	         		$.ajax({
			   	  	      			type:'POST',
			   	  	      			url:'${ContextPath}/rbac/usersaveedit',
			   	  	      			data:fmstr+"&userRoles="+ids,
				   	  	      		success: function(data){
			    	  	      			if(data){
		    	  	      	   				$("#userlist_tb").setGridParam({url:'${ContextPath}/rbac/userlist'}).trigger("reloadGrid");
			  	      	   		    		$("#dialog").dialog("close");
			    	  	      			}
			    	  	      			else
			    	  	      			{
				    	  	      			$("#dialog").dialog("close");
			    	  	      				alert_dialog("保存失败！");	
			    	  	      			}
		    	  	      			},
		    	  	      			error: function(data){
		    	  	      				alert_dialog("Error:"+ data);
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
     
     //添加用户
     $('#useradd').click(function(){
	     userAddDialog("${ContextPath}/rbac/useradd");
     });
     
     //弹出添加框
     function userAddDialog(url){
   	   $.post(url, function(data){
   		  $('#dialog').empty();
		  $('#dialog').append( data );
	 	  $('#dialog').dialog({
   	  		    title:"新建用户",
   	  		    height:'750',
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
	   	  	                var ids = getCheckedArray("user_roles");
	   	  	                var fmstr=$('#addUserInfo_fm').serialize();
	   	  	         		$.ajax({
	    	  	      			type:'POST',
	    	  	      			url:'${ContextPath}/rbac/usersaveadd',
	    	  	      			data:fmstr+"&userRoles="+ids,
	    	  	      			success: function(data){
	   	  	      	   				if(data == true){
	   	  	      	   					$("#userlist_tb").setGridParam({url:'${ContextPath}/rbac/userlist/'}).trigger("reloadGrid");
	   	  	      	   					$("#dialog").dialog("close");
	   	  	      	   				}
	   	  	      	   				else{
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
    
 	//删除角色
    $('#userdelete').click(function(event){
      var gr = jQuery("#userlist_tb").jqGrid('getGridParam','selrow');
  	  if(gr!=null)
  	  {	
  		  //清空警告框内文本并添加新的警告文本
  		  $( "#dialog-confirm" ).empty().append("<p>你确定删除该用户吗？</p>");
  		  $( "#dialog-confirm" ).dialog({
      	      height:150,
      	      modal:true,
      	      buttons: {
      	        "确定": function() {
      	        	$( this ).dialog( "close" );
      	        	var rowData = $("#userlist_tb").jqGrid("getRowData", gr);
      	  			var id = rowData["user.id"];
      	  			var name = rowData["user.name"];
      	        	$.post('${ContextPath}/rbac/userdelete?userId='+ id, function(data){
      	    			  if(data == true){
      	    				  alert_dialog("删除用户"+ name +"成功！");
      	    				  $("#userlist_tb").setGridParam({url:'${ContextPath}/rbac/userlist'}).trigger("reloadGrid");
      	    			  }
      	    			  else if(data == false)
      	    			  {
      	    				  alert_dialog("删除"+ name +"用户失败！");
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
		  alert_dialog("请选择删除用户！");
	  }
    });
</script>
