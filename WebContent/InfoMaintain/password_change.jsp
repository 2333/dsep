<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="infofr_div">
	<table class="infofr_table">
		<tr>
			<td><label for="formalPassword">输入旧密码：</label></td>
			<td><input type="password" name="formalPassword"/></td>
		</tr>
		<tr>
			<td><label for="newPassword">输入新密码：</label></td>
			<td><input type="password" name="newPassword" /></td>
		</tr>
		<tr>
			<td><label for="confirmPassword">确认新密码：</label></td>
			<td><input type="password" name="confirmPassword"></td>
		</tr>
	</table>
</div>
 
<script type="text/javascript">
	$(document).ready(function() {
		$("input[type=submit], a.button , button").button();

		$(".infofr_table td:nth-child(even)").addClass("fr_left");
		$(".infofr_table td:nth-child(odd)").addClass("fr_right");

	});
</script>