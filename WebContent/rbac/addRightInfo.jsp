<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <div id="addRightInfo_div" class="form">
     <form id="addRightInfo_fm" class="fr_form" method="post">  
           <table class="fr_table">  
              <tr>  
                 <td>权限ID：</td>  
                 <td>  
                    <input type="text" name="id" id="right_id"/>  
                 </td>
                 <td>权限名称：</td>  
                 <td>  
                    <input type="text" name="name" />  
                 </td>   
              </tr>
              <tr>  
                 <td>父权限ID：</td>  
                 <td>  
                    <input type="text" name="parentId" />  
                 </td>
                 <td>权限URL：</td>  
                 <td>  
                    <input type="text" name="url" />  
                 </td>   
              </tr>
              <tr>  
                 <td>权限类别：</td>  
                 <td>  
                    <select name = "category"  style="width:150px;">
		                 <c:if test="${!empty categories}">
		                     <c:forEach items="${categories}" var="category">
				                  <option value="${category.key }">${category.value}</option>
				             </c:forEach>	                 
		                 </c:if> 
                     </select>  
                 </td>
                 <td>权限等级：</td>  
                 <td>  
                    <input type="text" name="levelNo" />  
                 </td>   
              </tr>
              <tr>  
                 <td>相对顺序：</td>  
                 <td>  
                    <input type="text" name="seqNo" />  
                 </td>
                 <td>备注：</td>  
                 <td>  
                    <input type="text" name="memo" />  
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
		            required: true,
		            remote: {
                        url: "${ContextPath}/rbac/rightidcheck",
                        type: "post", //发送方式
                        dataType: "json", //数据格式 
                        data: { //要传递的数据
                        	id: function () {
                                return $("#right_id").val();
                            }
                        }
                    }
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
		            required: "请输入权限ID",
		            remote:"权限ID已存在"
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