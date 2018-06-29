<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" deferredSyntaxAllowedAsLiteral="true"%>
    
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>角色管理
	</h3>
</div>
<div class="selectbar">
   <table id="editRole_tb">
	   <tr>
	       <td><a id="addrole" class="button" href="#"><span class="icon icon-adduser"></span>添加角色</a></td>
	       <td><a id="editrole" class="button" href="#"><span class="icon icon-edituser"></span>编辑角色</a></td>
	       <td><a id="deleterole" class="button" href="#"><span class="icon icon-deleteuser"></span>删除角色</a></td>
	   </tr>
   </table>
</div>
<div class="table">
    <table id="rolelist_tb"></table>
	<div id="role_pager_tb"></div>
</div>

<div id="dialog-confirm" title="警告">
</div>
    
<script type="text/javascript">
	$(document).ready(function(){
	  //应用按钮样式
  	  $( "input[type=submit], a.button , button" ).button(); 
   	  
	  //表格初始化
   	  $("#rolelist_tb").jqGrid({
   		    url: '${ContextPath}/rbac/rolelist',
            datatype: 'json',
            mtype: 'GET',
   			colNames:['角色ID','角色名'],
   			colModel:[
   				{name:'id',index:'id', align:"center",width:"300", sorttype:"String",editable:true },
   				{name:'name',index:'name',align:"center", width:"300",sorttype:"String", editable:true }	
   				//{name:'category',index:'category', align:"center",width:"300",sorttype:"String",editable:true }
   			], 
   			height:'100%',
   			autowidth:true,
   			pager: '#role_pager_tb',
   			rowNum:10,
   			rowList:[10,20,30],
   			viewrecords: true,
   			sortname: 'id',
   			sortorder: "desc",
   			caption: "角色列表",
   		    jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
                   root: "rows",  //包含实际数据的数组  
                   page: "pageIndex",  //当前页  
                   total: "totalPage",//总页数  
                   records:"totalCount", //查询出的记录数  
                   repeatitems : false        
       			}
   		}).navGrid('#role_pager_tb',{edit:false,add:false,del:false});
      });
      
	  //弹出编辑框
      $('#editrole').click(function(event){
    	  var gr = jQuery("#rolelist_tb").jqGrid('getGridParam','selrow');
    	  if(gr!=null)
    	  {
    		  //var rowData = $("#rolelist_tb").jqGrid("getRowData", gr);
        	  editRoleDialog("${ContextPath}/rbac/roleedit?roleId="+gr);
    	  }else
   		  {
   		  	alert_dialog("请选择编辑行！");
   		  }
      });
      
	  //弹出新建框
      $('#addrole').click(function(event){
    	  addRoleDialog("${ContextPath}/rbac/roleadd");
      });
	  
      //删除角色
      $('#deleterole').click(function(event){
    	  var gr = jQuery("#rolelist_tb").jqGrid('getGridParam','selrow');
    	  if(gr!=null)
    	  {	
    		  //清空警告框内文本并添加新的警告文本
    		  $( "#dialog-confirm" ).empty().append("<p>你确定删除该角色吗？</p>");
    		  $( "#dialog-confirm" ).dialog({
        	      height:150,
        	      buttons: {
        	        "确定": function() {
        	        	$( this ).dialog( "close" );
        	        	$.post('${ContextPath}/rbac/roledelete?roleId=' + gr, function(data){
        	    			  if(data == true){
        	    				  alert_dialog("删除"+ gr +"角色成功！");
        	    				  $("#rolelist_tb").setGridParam({url:'${ContextPath}/rbac/rolelist'}).trigger("reloadGrid");
        	    			  }
        	    			  else if(data == false)
        	    			  {
        	    				  alert_dialog("删除"+ gr +"角色失败！");
        	   				  }else
        	   				  {
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
      
	  //生成编辑对话框
      function editRoleDialog(url)
      {
    	  $.post(url, function(data){
    		  $('#dialog').empty();
    		  $('#dialog').append( data );
    	 	  $('#dialog').dialog({
    	  		    title:"编辑角色",
    	  		    height:'600',
  	  				width:'700',
  	  				position:'center',
  	  				modal:true,
  	  				draggable:true,
  	  		    	hide:'fade',
  	  				show:'fade',
  	  		    	autoOpen:true,
    	  		    buttons:{  
    	  		    	"确定":function(){
    	  		    		if($('.fr_form').valid()){
	    	  		    		var menu_ids = getCheckedArray("editmenuright");
	    	  	                var action_ids = getCheckedArray("editactionright");
	    	  	                var ids = 	menu_ids.concat(action_ids);
	    	  	                var roleinfo=$('#editRole_fm').serialize();
	    	  	         		$.ajax({
		    	  	      			type:'POST',
		    	  	      			url:'${ContextPath}/rbac/rolesaveedit',
		    	  	      			data:roleinfo+"&rightIds="+ids,
		    	  	      			success: function(data){
			    	  	      			if(data){
		    	  	      	   				$("#rolelist_tb").setGridParam({url:'${ContextPath}/rbac/rolelist'}).trigger("reloadGrid");
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
    	  		    }}); 
    	  }, 'html');
      }
	  
      //生成编辑对话框
      function addRoleDialog(url)
      {
    	  $.post(url, function(data){
    		  $('#dialog').empty();
    		  $('#dialog').append( data );
    	 	  $('#dialog').dialog({
    	  		    title:"添加角色",
    	  		    height:'600',
    	  			width:'700',
    	  			position:'center',
    	  			modal:true,
    	  			draggable:true,
    	  		    hide:'fade',
    	  			show:'fade',
    	  		    autoOpen:true,
    	  		    buttons:{  
    	  		    	"确定":function(){
    	  		    		if($('.fr_form').valid()){
	    	  	                var menu_ids = getCheckedArray("addmenuright");
	    	  	                var action_ids = getCheckedArray("addactionright");
	    	  	                var ids = 	menu_ids.concat(action_ids);
	    	  	                var roleinfo=$('#addRole_fm').serialize();
	    	  	         		$.ajax({
		    	  	      			type:'POST',
		    	  	      			url:'${ContextPath}/rbac/rolesaveadd',
		    	  	      			data:roleinfo+"&rightIds="+ids,
		    	  	      			success: function(data){
			    	  	      			if(data){
		    	  	      	   				$("#rolelist_tb").setGridParam({url:'${ContextPath}/rbac/rolelist'}).trigger("reloadGrid");
			  	      	   		    		$("#dialog").dialog("close");
			    	  	      			}
			    	  	      			else
			    	  	      			{
				    	  	      			$("#dialog").dialog("close");
			    	  	      				alert("保存失败！");	
			    	  	      			}
		    	  	      			}
	    	  	      			});
    	  		    		}
    	  	            },
    	 	  
    	 	            "关闭":function(){
    	 	            	$("#dialog").dialog("close");
    	 	            }
    	  		    }}); 
    	  }, 'html');
      }
</script>