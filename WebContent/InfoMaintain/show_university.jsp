<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="form">
	<form  id="universityShow_fr" class="fr_form" method="post">
		<table id="universityShow_tb" class="fr_table">
			<tr class="hidden">
             	<td >ID：</td>  
             	<td >  
                	<input type="text" name="id" value="${user.id}" readonly/>  
                	<span class="dialog_validSpan"></span>
             	</td>
             	<td >userType</td>  
             	<td >  
                	<input type="text" name="userType" value="${user.userType}" readonly/> 
                	<span class="dialog_validSpan"></span> 
             	</td>
				<td><label for="loginId">登陆ID</label></td>
				<td>
					<input id="login_id" type="text" name="loginId" value="${user.loginId}"/>
					<span class="dialog_validSpan"></span>
				</td>
          	</tr>
			<tr>
				<td><label for="unitId">学校编号：</label></td>
				<td width="300px">
					<input id="unitId" type="text" name="unitId" value="${user.unitId}" class="discAndUnitJqGridInputL onlyread" readonly="readonly" />
				</td>
			</tr>
			<tr>
				<td>
					<Span ><b>联系人信息</b></Span>
				</td>
			</tr>
			<tr>
				<td><label for="name" >学校名称：</label>
				<td>
					<input type="text" name="name" class="discAndUnitJqGridInputL onlyread" value="${user.name}" readonly="readonly"/>
					
				</td>
				<td><label for="email">电子邮箱：</label></td>
				<td>
					<input type="text" name="email" class="discAndUnitJqGridInputR" value="${user.email}" />
					<span class="dialog_validSpan"></span>
				</td>
			</tr>			
			<tr>
				<td><label for="address">通信地址：</label></td>
				<td>
					<input type="text" name="address" value="${user.address}" class="discAndUnitJqGridInputL"/>	
				</td>
				<td><label for="cellPhone">移动电话：</label></td>
				<td>
					<input type="text" name="cellPhone" class="discAndUnitJqGridInputR" value="${user.cellPhone}"/>
				</td>
			</tr>
			<tr>
				<td><label for="officeAddr">办公地址：</label></td>
				<td>
					<input type="text" name="officeAddr" value="${user.officeAddr}" class="discAndUnitJqGridInputL" />
				</td>
				<td><label for="officePhone">办公电话：</label></td>
				<td>
					<input type="text" name="officePhone" class="discAndUnitJqGridInputR" value="${user.officePhone}"/>
				</td>
			</tr>
			<tr>
				<td valign="top"><label for="memo">备注：</label></td>
				<td colspan='4'>
					<textarea name="memo" value="${user.memo}" class="uniAndDiscJqGridMemo" style='resize:none;height:50px;'>${user.memo}
					</textarea>
				</td>
			</tr>
		</table>
	</form>
</div>

<script type="text/javascript">
	$(document).ready(
			function() {
				$("input[type=submit], a.button , button").button().click(
						function(event) {
							event.preventDefault();
						});
				$(".fr_table td:nth-child(even)").addClass("fr_left");
				$(".fr_table td:nth-child(odd)").addClass("fr_right");
				
				$(".form input").attr({readonly:"readonly"});
				$(".form textarea").attr({readonly:"readonly"});
		
			});
</script>