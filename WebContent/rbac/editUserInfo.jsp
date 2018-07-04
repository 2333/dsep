<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="editUserInfo_div" class="form">
   <form id="editUserInfo_fm" class="fr_form" method="post">  
       <table class="fr_table" > 
      	  <tr class="hidden">
             <td >ID：</td>  
             <td >  
                <input type="text" name="id" value="${user.id}" readonly/>  
             </td>
          </tr>  

          <tr>  
             <td>登录账户：</td>  
             <td>  
                <input type="text" name="loginId" value="${user.loginId}" />  
             </td>
             <td>用户名：</td>  
             <td>  
                <input type="text" name="name" value="${user.name}"/>  
             </td>   
          </tr>  
          
          <tr>
             <td>密码：</td>
             <td>  
                <input type="password" name="password" value="${user.password}"/>  
             </td>
             <td>身份证号：</td>
             <td>  
                <input type="text" name="idCardNo" value="${user.idCardNo}"/>  
             </td>    
          </tr> 
          
          <tr>
             <td>用户类型：</td>
             <td>
             	<select name="userType">
                    <c:if test="${!empty userTypes}">
                       <c:forEach items="${userTypes}" var="userType">
                       		<c:if test="${userType.key == user.userType}">
		                    	<option value="${userType.key}" selected="selected">${userType.value}</option>
		                    </c:if> 
		                    <c:if test="${userType.key != user.userType}">
		                    	<option value="${userType.key}">${userType.value}</option>
		                    </c:if> 
		               </c:forEach>	                 
                    </c:if> 
				</select>
			 </td>
          </tr> 
          <tr>
             <td>QQ：</td>
             <td>  
                <input type="text" name="QQ" value="${user.QQ}"/>  
             </td>
             <td>game：</td>
             <td>  
                <input type="text" name="game" value="${user.game}"/>  
             </td>    
          </tr> 
          
          <tr>
             <td>winnum数量：</td>
             <td>  
                <input type="text" name="winnum" value="${user.winnum}"/>  
             </td>
             <td>money：</td>
             <td>  
                <input type="text" name="money" value="${user.money}"/>  
             </td>    
          </tr> 
          
          <tr>
             <td>nownum数量：</td>
             <td>  
                <input type="text" name="officePhone" value="${user.officePhone}"/>  
             </td>
             <td>region：</td>
             <td>  
                <input type="text" name="region" value="${user.region}"/>  
             </td>    
          </tr> 
          
          <tr>
             <td>IPType：</td>
             <td>  
                <input type="text" name="IPType" value="${user.IPType}"/>  
             </td>
             <td>higherUserId：</td>
             <td>  
                <input type="text" name="higherUserId" value="${user.higherUserId}"/>  
             </td>    
          </tr> 
          
          <tr>
             <td>邮政编码：</td>
             <td>  
                <input type="text" name="zipCode" value="${user.zipCode}"/>  
             </td>
             <td>备注：</td>
             <td>  
                <input type="text" name="memo" value="${user.memo}"/>  
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
    $(document).ready(function(){
    	$.ajax({  
	        async : false,  
	        cache:false,  
	        type: 'POST',  
	        dataType : "json",  
	        url: "${ContextPath}/rbac/userroletree?userId="+"${user.id}",
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
		        }
		    }
	  	});
    	
    	$(".fr_table td:nth-child(even)").addClass("fr_left");
        $(".fr_table td:nth-child(odd)").addClass("fr_right");
        
        $( "input[type=submit], a.button , button" ).button();
    	
	    $.fn.zTree.init($("#user_roles"), setting, zNodes); 
    });  
</script> 