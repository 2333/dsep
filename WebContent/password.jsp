<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="modify_password_div" class="form">
   <form id="modify_password_form" class="fr_form" method="post">  
       <table class="fr_table" > 
          <tr>  
             <td>原密码：</td>  
             <td>  
                <input type="password" name="old_password" id="old_password" value="" />  
             </td>
          </tr>  
          
          <tr>
             <td>新密码：</td>
             <td>  
                <input type="password" name="new_password" id="new_password" value=""/>  
             </td>
          </tr> 
          
           <tr>
             <td>新密码确认：</td>
             <td>  
                <input type="password" name="new_password_check" value=""/>  
             </td>
          </tr> 
       </table>  
   </form> 	
</div>
    
<script type="text/javascript">
	
    $(document).ready(function(){
    	$("#modify_password_form").validate({
		    rules: {
		    	old_password: {
		            required: true,
		            remote: {
                        url: "${ContextPath}/rbac/check_password",
                        type: "post",
                        dataType:"json", 
                        data:{
                        	old_password:function(){return $("#old_password").val();}
                        } 
                    } 
		        },
		        new_password: {
		            required: true,
		            minlength: 5
		        },
		        new_password_check: {
		            required: true,
		            minlength: 5,
		            equalTo: "#new_password"
		        }
		    }, 
		    messages: {
		    	old_password: {
		            required: "请输原密码",
		            remote: "原密码错误"
		        },
		        new_password: {
		            required: "请输入新密码",
		            minlength: jQuery.format("密码不能小于{0}个字 符")
		        },
		        new_password_check:{
		        	required:"请输入新密码确认",
		        	minlength: jQuery.format("密码不能小于{0}个字 符"),
		        	equalTo:"两次输入密码不一致"
		        }
		    }
	  	});
    	
    	$(".fr_table td:nth-child(even)").addClass("fr_left");
        $(".fr_table td:nth-child(odd)").addClass("fr_right");
        
        $( "input[type=submit], a.button , button" ).button();
 
    });  
</script> 