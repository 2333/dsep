<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="form">
	<form id=disciplineAdd_fr class="fr_form" method="post">
		<table id="disciplineAdd_tb" class="fr_table">
			<tr>
				<td><label for="disc_id" class="alert_info">*学科编号：</label></td>
				<td >
					<select id = "select_disc" name="discId" class="uniAndDiscJqGridInput">
						<c:if test="${!empty disclist}">
						  	<c:forEach items="${disclist}" var="disclist">
		                  		<option value="${disclist.id }" >${disclist.id }-${disclist.name}</option>
		                  	</c:forEach>
						  </c:if>
					</select >
				</td>
				<td><label for="password" class="alert_info">*密码：</label></td>
				<td>
					<input type="password"  name="password" id="password" class="uniAndDiscJqGridInput" />
					<span class = "dialog_validSpan"></span>
				</td>
			</tr>
			
			<tr> 
				<td><label for="loginId" >登陆ID：</label></td>
				<td><input id="login_id" type="text" name="loginId" readonly="readonly" class="uniAndDiscJqGridInput"/></td>
				<td><label for="confirm_password" class="alert_info">*确认密码：</label></td>
				<td>
					<input type="password"  name="confirm_password" class="uniAndDiscJqGridInput"  />
					<span class = "dialog_validSpan"></span>
				</td>
				<td></td>
			</tr>
			<tr> 
				
				<td><label for="school_name" class="alert_info">*院系名称：</label></td>
				<td>
					<input type="text"  name="schoolName" class="uniAndDiscJqGridInput" />
					<span class = "dialog_validSpan"></span>
				</td>
			</tr>
			
			<tr>
				<td>
					<Span ><b>联系人信息</b></Span>
				</td>
			</tr>
			<tr>
				<td><label for="name" class="alert_info">*姓名：</label></td>
				<td>
					<input type="text" name="name" value="" class="uniAndDiscJqGridInput"/>
					<span class = "dialog_validSpan"></span>	
				</td>
				<td><label for="idcard_no" class="alert_info">*身份证号：</label></td>
				<td>
					<input type="text"  name="idCardNo" class="uniAndDiscJqGridInput" />
					<span class = "dialog_validSpan"></span>	
				</td>
				<td></td>
			</tr>
			
			<tr>
				<td><label for="email" class="alert_info">*邮箱：</label></td>
				<td>
					<input type="text" name="email" class="uniAndDiscJqGridInput"/>
					<span class = "dialog_validSpan"></span>	
				</td>
				
				<td><label for="zip_code">邮政编码：</label></td>
				<td><input type="text" name="zipCode" class="uniAndDiscJqGridInput"/></td>
				
				
			</tr>
			<tr>
				<td><label for="office_phone">办公电话：</label></td>
				<td><input type="text" name="officePhone" class="uniAndDiscJqGridInput" /></td>
				<td><label for="cell_phone">移动电话：</label></td>
				<td><input type="text" name="cellPhone" class="uniAndDiscJqGridInput" /></td>
			</tr>
			<tr>
				<td><label for="office_addr">办公地址：</label></td>
				<td colspan='3'>
					<input type="text" name="officeAddr" class="uniAndDiscJqGridMemo"/>
				</td>
			</tr>
			<tr>
				<td><label for="address">通讯地址：</label></td>
				<td colspan='3'>
					<input type="text" name="address" class="uniAndDiscJqGridMemo"/>
				</td>
			</tr>
						
			<tr>
				<td><label for="memo">备注：</label></td>
				<td colspan="3">
					<textarea name="memo"  style='resize:none;height:50px;width:695px;'>
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
		//$(".fr_table td:nth-child(even)").addClass("fr_left");
		$(".fr_table td:nth-child(odd)").addClass("fr_right");
		
		//给登陆的id附上初值
		var p1 = $("#select_disc").children('option:selected').val();
		$("#login_id").val("${user.unitId}"+"_"+p1);
		
		//当添加的学科改变时,更改登陆的id
		$("#select_disc").change(function(){
			var p1 = $(this).children('option:selected').val();
			$("#login_id").val("${user.unitId}"+"_"+p1);
		});
		validator(".fr_form");
		//数据验证
		/*  $("#disciplineAdd_fr").validate({
			rules: {
				password:{
						required:true,
						minlength:6
				},
				confirm_password:{
						required:true,
						equalTo: "#password"
				},
				schoolName:"required",
				email:{
						required:true,
						email:true	
				},
				name:{
						required:true
				},
				idCardNo:{
						required:true
				},
				officePhone:{
						required:true
				},
				officeAddr:{
						required:true
				},
				address:{
						required:true
				},
				zipCode:{
						required:true
				}
			},
			messages:{
				password:{
					required: "密码不能为空！",
					minlength: "长度不能少于6位！"
				},
				confirm_password:{
					required: "请再次输入密码！",
					equalTo: "两次密码不匹配！"
				},
				schoolName:{
					required:"不能为空！",
				},
				email:{
					required:"不能为空！",
					email:"邮箱格式错误！"	
				},
				name:{
					required:"不能为空！"
				},
				idCardNo:{
					required:"不能为空！"
				},
				officePhone:{
					required:"不能为空！"
				},
				officeAddr:{
					required:"不能为空！"
				},
				address:{
					required:"不能为空！"
				},
				zipCode:{
					required:"不能为空！"
				}
			},
			onkeyup:false
			
		}); */ 
	
	});

</script>