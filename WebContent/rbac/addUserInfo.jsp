<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="addUserInfo_div" class="form">
    <form id="addUserInfo_fm" class="fr_form" method="post">  
    	<table class="fr_table">
          <tr>  
	          <td>账户名：</td>  
	          <td>  
	             <input type="text" name="loginId" />  
	          </td>
	          <td>用户名：</td>  
	          <td>  
	             <input type="text" name="name" />  
	          </td>   
          </tr>  
          <tr>
	          <td>密码：</td>
	          <td>  
	             <input type="password" name="password" />  
	          </td>
	          <td>身份证号：</td>
	          <td>  
	             <input type="text" name="idCardNo" />  
	          </td>    
          </tr> 
          <tr>
              <td>用户类型：</td>
              <td>
                  <select id="userType" name ="userType" >
		              <c:if test="${!empty userTypes}">
		                 <c:forEach items="${userTypes}" var="userType">
		                  <option value="${userType.key }">${userType.value}</option>
		             </c:forEach>	                 
		              </c:if> 
                  </select>  
			  </td>
          </tr>
          <tr>
             <td>单位代码：</td>
             <td>  
                <input type="text" name="unitId" />  
             </td>
             <td>学科代码：</td>
             <td>  
                <input type="text" name="discId" />  
             </td>    
          </tr> 
          <tr>
             <td>院系名称：</td>
             <td>  
                <input type="text" name="schoolName" />  
             </td>
             <td>电子邮箱：</td>
             <td>  
                <input type="text" name="email" />  
             </td>    
          </tr> 
          <tr>
             <td>办公电话：</td>
             <td>  
                <input type="text" name="officePhone" />  
             </td>
             <td>移动电话：</td>
             <td>  
                <input type="text" name="cellPhone" />  
             </td>    
          </tr> 
          <tr>
             <td>办公地址：</td>
             <td>  
                <input type="text" name="officeAddr" />  
             </td>
             <td>通信地址：</td>
             <td>  
                <input type="text" name="address" />  
             </td>    
          </tr> 
          <tr>
             <td>邮政编码：</td>
             <td>  
                <input type="text" name="zipCode" />  
             </td>
             <td>备注：</td>
             <td>  
                <input type="text" name="memo" />  
             </td>    
          </tr> 
          <tr>
              <td>角色列表：</td>
              <td>
				<div class="fr_panel">
					<ul id="user_roles" class="ztree"></ul>
			 	</div>
			  </td>
          </tr>  	  
        </table>  
    </form> 	
</div>
<script type="text/javascript">
	var zNodes;
	
	$(function(){ 
		$.ajax({  
			async:false,  
			cache:false,  
			type:'POST',  
			dataType:'json',  
			url: "${ContextPath}/rbac/userallroletree",
			error: function () {//请求失败处理函数  
			    alert_dialog('请求角色树失败');  
			},  
			success:function(data){ //请求成功后处理函数。    
			    zNodes = data;   //把后台封装好的简单Json格式赋给treeNodes  
			}  
		}); 
		
		$("form.fr_form").validate({
		    rules: {
		    	loginId: {
		            required: true
		        },
		        name: {
		            required: true
		        },
		        password: {
		            required: true
		        },
		        email:{
		        	email:true
		        }
		    }, 
		    messages: {
		    	loginId: {
		            required: "请输入用户账号"
		        },
		        password: {
		            required: "请输入密码"
		        },
		        name:{
		        	required:"请输入用户名"
		        },
		        email:{
		        	email:"邮箱格式不对"
		        }
		    }
	  	});
   	  
		
		$(".fr_table td:nth-child(even)").addClass("fr_left");
		$(".fr_table td:nth-child(odd)").addClass("fr_right");
		    
		$( "input[type=submit], a.button , button" ).button();
		
		$.fn.zTree.init($("#user_roles"), setting, zNodes);
	
	});
</script>