<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <div id="editRightInfo_div" class="form">
     <form id="editRightInfo_fm" class="fr_form" method="post">  
           <table class="fr_table">  
              <tr>  
                 <td>权限ID：</td>  
                 <td>  
                    <input type="text" name="id" value="${right.id}" readonly style="color:gray;"/>  
                 </td>
                 <td>权限名称：</td>  
                 <td>  
                    <input type="text" name="name" value="${right.name}"/>  
                 </td>   
              </tr>
              <tr>  
                 <td>父权限ID：</td>  
                 <td>  
                    <input type="text" name="parentId" value="${right.parentId}" />  
                 </td>
                 <td>权限URL：</td>  
                 <td>  
                    <input type="text" name="url" value="${right.url}"/>  
                 </td>   
              </tr>
              <tr>                  
                 <td>权限类别：</td>  
                 <td>  
                     <select name = "category"  style="width:150px;">
	                     <c:if test="${!empty categories}">
	                       <c:forEach items="${categories}" var="category">
	                       		<c:if test="${category.key == right.category}">
			                    	<option value="${category.key}" selected="selected">${category.value}</option>
			                    </c:if> 
			                    <c:if test="${category.key != right.category}">
			                    	<option value="${category.key}">${category.value}</option>
			                    </c:if> 
			               </c:forEach>	                 
	                    </c:if> 
                     </select>  
                 </td> 
                 <td>权限等级：</td>  
                 <td>  
                    <input type="text" name="levelNo" value="${right.levelNo}"/>  
                 </td>   
              </tr>
              <tr>  
                 <td>相对顺序：</td>  
                 <td>  
                    <input type="text" name="seqNo" value="${right.seqNo}" />  
                 </td>
                 <td>备注：</td>  
                 <td>  
                    <input type="text" name="memo" value="${right.memo}"/>  
                 </td>   
              </tr>					  
           </table>  
     </form> 	
</div>

<script type="text/javascript">
	
	$(function(){ 
		$("form.fr_form").validate({
		    rules: {
		    	id: {
		            required: true
		        },
		        name: {
		            required: true
		        },
		        levelNo: {
		            required: true
		        },
		        seqNo: {
		            required: true
		        }
		    }, 
		    messages: {
		    	id: {
		            required: "请输入权限ID"
		        },
		        name: {
		            required: "请输入权限名"
		        },
		        levelNo: {
		            required: "请输入权限等级"
		        },
		        seqNo:{
		        	required:"请输入权限顺序"
		        }
		    }
	  	});
   	  
		
		$(".fr_table td:nth-child(even)").addClass("fr_left");
		$(".fr_table td:nth-child(odd)").addClass("fr_right");
	});
</script>