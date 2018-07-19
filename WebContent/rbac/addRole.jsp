<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <div id="addRole_dv" class="form" >
     <form id="addRole_fm" class="fr_form" method="post">  
           <table class="fr_table">  
              <tr>  
                 <td><label>角色ID：</label></td>  
                  <td>  
                     <input type="text" name="id" value=""/>
                  </td>
                  <td><label>角色名：</label></td>  
                  <td>  
                      <input type="text" name="name" value=""/>  
                  </td>   
              </tr>  
              <%-- <tr>
                  <td><label>角色类型:</label></td>
                  <td>  
                      <input type="text" name="roleType" value="${role.category}"/>  
                  </td>   
              </tr>	 --%>
			<tr>
				<td style="vertical-align:top;"><label>菜单权限：</label></td>
				<td >
					<div class="fr_panel">
						<ul id="addmenuright" class="ztree"></ul>
		   			</div>
				</td>
				<td style="vertical-align:top;"><label>动作权限：</label></td>
				<td >
					<div class="fr_panel">
						<ul id="addactionright" class="ztree"></ul>
		   			</div>
				</td>
			</tr>
			<tr>
			 	  <td></td>
			 	  <td></td>
		   	</tr>
           </table>  
         </form> 	
    </div>
    
<script type="text/javascript">
		
	 	var zNodes_menu, zNodes_action;//tree节点
	 	
		$(function(){  
			
		    $.ajax({  
		        async : false,  
		        cache:false,  
		        type: 'POST',  
		        dataType : "json",  
		        url: "${ContextPath}/rbac/rolemenutree?roleId=${role.id}",//请求的路径  
		        error: function () {//请求失败处理函数  
		            alert_dialog('请求权限树失败');  
		        },  
		        success:function(data){ //请求成功后处理函数。    
		            //alert(data);  
		        	zNodes_menu = data;   //把后台封装好的简单Json格式赋给treeNodes  
		        }  
		    }); 
		    
		    $.ajax({  
		        async : false,  
		        cache:false,  
		        type: 'POST',  
		        dataType : "json",  
		        url: "${ContextPath}/rbac/roleactiontree?roleId=${role.id}",//请求的路径  
		        error: function () {//请求失败处理函数  
		            alert_dialog('请求权限树失败');  
		        },  
		        success:function(data){ //请求成功后处理函数。    
		            //alert(data);  
		        	zNodes_action = data;   //把后台封装好的简单Json格式赋给treeNodes  
		        }  
		    }); 
		    
		    $("form.fr_form").validate({
			    rules: {
			    	id: {
			            required: true
			        },
			        name: {
			            required: true
			        }
			    }, 
			    messages: {
			    	id: {
			            required: "请输入角色ID"
			        },
			        name:{
			        	required:"请输入角色名"
			        }
			    }
		  	});
		    
		    $(".fr_table td:nth-child(even)").addClass("fr_left");
		    $(".fr_table td:nth-child(odd)").addClass("fr_right");
		    
		    $( "input[type=submit], a.button , button" ).button();
		    
		    $.fn.zTree.init($("#addmenuright"), setting, zNodes_menu);
		    $.fn.zTree.init($("#addactionright"), setting, zNodes_action);
			
		});
		
	
</script>