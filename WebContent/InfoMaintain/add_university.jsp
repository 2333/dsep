<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="form">
	<form id="universityAdd_fr" class="fr_form" method="post">
		<table id="universityAdd_tb" class="fr_table">
			<tr>
				<td><label for="unitId" >学校编号：</label></td>
				<td colspan='3'>
					<select id="select_unitId" name="unitId">
						  <c:if test="${!empty universityList}">
						  	<c:forEach items="${universityList}" var="universityList">
		                  		<option value="${universityList.key }">${universityList.key }-${universityList.value}</option>
		                  	</c:forEach>
						  </c:if>
					</select>
				</td>
			</tr>
			<tr>
	 			<td><label for="loginId">登陆ID：</label></td>
				<td>
					<input id="login_id" class="uniAndDiscJqGridInput" type="text" name="loginId" value="" readonly="readonly"/>
					<span class="dialog_validSpan"></span>
				</td>
				
				<td><label for="password" class="alert_info">*用户密码：</label></td>
				<td>
					<input id="password" type="password" name="password" class="uniAndDiscJqGridInput"/>
					<span class="dialog_validSpan"></span>
				</td>
			</tr>
			<tr>
				<td>
					<Span ><b>联系人信息</b></Span>
				</td>
			</tr>
			<tr>
				<td><label for="name" class="alert_info">*姓名：</label>
				<td>
					<input type="text" name="name" class="uniAndDiscJqGridInput"/>
					<span class="dialog_validSpan"></span>
				</td>

				<td><label for="idCardNo" class="alert_info">*身份证号：</label></td>
				<td>
					<input type="text"  name="idCardNo" class="uniAndDiscJqGridInput"/>
					<span class="dialog_validSpan"></span>
				</td>
			</tr>	
			<tr>
			
				<td><label for="email" class="alert_info">*电子邮箱：</label></td>
				<td>
					<input type="text" name="email" class="uniAndDiscJqGridInput"/>
					<span class="dialog_validSpan"></span>
				</td>
				
				<td><label for="zipCode">邮政编码：</label></td>
				<td>
					<input type="text" name="zipCode" class="uniAndDiscJqGridInput"/>
					<span class="dialog_validSpan"></span>
				</td>
			</tr>
			<tr>
				<td><label for="officePhone">办公电话：</label></td>
				<td>
					<input type="text" name="officePhone" class="uniAndDiscJqGridInput"/>
					<span class="dialog_validSpan"></span>
				</td>		
				<td><label for="cellPhone">移动电话：</label></td>
				<td>
					<input type="text" name="cellPhone" class="uniAndDiscJqGridInput"/>
					<span class="dialog_validSpan"></span>
				</td>			
			</tr>			
			<tr>
				<td><label for="officeAddr">办公地址：</label></td>
				<td colspan='3'>
					<input type="text" name="officeAddr" class="uniAndDiscJqGridMemo"/>
					<span class="dialog_validSpan"></span>
				</td>
			</tr>
			
			<tr>
				<td><label for="address">通信地址：</label></td>
				<td colspan='3'>
					<input type="text" name="address" class="uniAndDiscJqGridMemo"/>
					<span class="dialog_validSpan"></span>
				</td>
			</tr>
			<tr>
				<td><label for="memo">备注：</label></td>
				<td colspan='3'>
					<textarea name="memo" class="uniAndDiscJqGridMemo" style='resize:none;height:50px;'>
					</textarea>
				</td>
			</tr>
		</table> 
	</form>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$( "input[type=submit], a.button , button" )
		  .button()
		  .click(function( event ) {
			event.preventDefault();
		});
		$(".fr_table td:nth-child(even)").addClass("fr_left");
		$(".fr_table td:nth-child(odd)").addClass("fr_right");
		/*登陆id直接由学校编号决定 */
		$("#select_unitId").change(function(){
			var selected = $(this).children('option:selected').val();
			$("#login_id").val(selected);
		});
		
		/* 公共验证函数  */
		validator(".fr_form");
	});
</script>