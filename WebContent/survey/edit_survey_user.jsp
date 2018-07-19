<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="form">
    <form id="edit_survey_user" class="fr_form" method="post">  
    	<table class="fr_table">
          <tr class="hidden">
             <td >ID：</td>  
             <td >  
                <input type="text" name="id" value="${surveyUser.id}" readonly/>  
             </td>
          </tr>  
          <tr>  
	          <td>姓名：</td>  
	          <td>  
	             <input type="text" name="name" value="${surveyUser.name}"/>  
	          </td>   
	          <td>性别：</td>
              <td>
                  <select name ="gender">
		              <c:if test="${!empty genders}">
	                       <c:forEach items="${genders}" var="gender">
	                       		<c:if test="${gender.key == surveyUser.gender}">
			                    	<option value="${gender.key}" selected="selected">${gender.value}</option>
			                    </c:if> 
			                    <c:if test="${gender.key != surveyUser.gender}">
			                    	<option value="${gender.key}">${gender.value}</option>
			                    </c:if> 
			               </c:forEach>	                 
	                    </c:if> 
                  </select>  
			  </td>
          </tr>  
          <tr>
             <td>单位代码：</td>
             <td>  
                <input type="text" name="unitId" value="${surveyUser.unitId}"/>  
             </td>
             <td>学科代码：</td>
             <td>  
                <input type="text" name="discId" value="${surveyUser.discId}"/>  
             </td>    
          </tr> 
          <tr>
	          <td>Email：</td>
	          <td>  
	             <input type="text" name="email" value="${surveyUser.email}"/>  
	          </td>
	          <td>用户类型</td>
	          <td>
	          	<select id="userType" name="userType">
					<c:if test="${!empty userTypes}">
	                       <c:forEach items="${userTypes}" var="userType">
	                       		<c:if test="${userType.key == surveyUser.userType}">
			                    	<option value="${userType.key}" selected="selected">${userType.value}</option>
			                    </c:if> 
			                    <c:if test="${userType.key != surveyUser.userType}">
			                    	<option value="${userType.key}">${userType.value}</option>
			                    </c:if> 
			               </c:forEach>	                 
	                    </c:if> 
				</select>
	          </td>
          </tr> 
        </table>  
    </form> 	
</div>
<script type="text/javascript">
	
	$(function(){ 
		
		$("#edit_survey_user").validate({
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