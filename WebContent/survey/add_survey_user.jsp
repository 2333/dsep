<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="form">
    <form id="add_survey_user" class="fr_form" method="post">  
    	<table class="fr_table">
          <tr>  
	          <td>姓名：</td>  
	          <td>  
	             <input type="text" name="name" />  
	          </td>   
	          <td>性别：</td>
              <td>
                  <select name ="gender" >
		              <option value="1">男</option>
		              <option value="0">女</option>
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
	          <td>Email：</td>
	          <td>  
	             <input type="text" name="email" />  
	          </td>
	          <td>用户类型</td>
	          <td>
	          	<select id="userType" name="userType">
					<option value="1">在校生</option>
					<option value="2">毕业生</option>
					<option value="3">专家</option>
					<option value="4">用人单位</option>
				</select>
	          </td>
          </tr> 
        </table>  
    </form> 	
</div>
<script type="text/javascript">
	
	$(function(){ 
		
		$("#add_survey_user").validate({
		    rules: {
		    	unitId: {
		            required: true
		        },
		        name: {
		            required: true
		        },
		        discId: {
		            required: true
		        },
		        email:{
		        	required: true,
		        	email:true
		        }
		    }, 
		    messages: {
		    	unitId: {
		            required: "请输入单位号码"
		        },
		        discId: {
		            required: "请输入学科号码"
		        },
		        name:{
		        	required:"请输入姓名"
		        },
		        email:{
		        	required:"请输入邮箱",
		        	email:"邮箱格式不对"
		        }
		    }
	  	});
   	  
		
		$(".fr_table td:nth-child(even)").addClass("fr_left");
		$(".fr_table td:nth-child(odd)").addClass("fr_right");
		    
		$( "input[type=submit], a.button , button" ).button();
	});
</script>