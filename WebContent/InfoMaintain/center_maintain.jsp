<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="con_header">
	<h3>
		<span class="icon icon-web">中心信息维护</span>
	</h3>
</div>
<div class="infofr_div">
	<form id="centerMaintain_fm" class="fr_form" action="" method="post">
		<p class="smallTitle">联系人信息：</p>
		<table id="centerMaintain_tb" class="infofr_table">
			<tr>
				<td><label for="userName">姓名：</label></td>
				<td><input id="num1" name="userName" value="" /></td>
				<td><label for="positionSelectList">职务：</label></td>
				<td><select id="positionSelectList">
					  <option value="教务秘书">教务秘书</option>
					  <option value="教师">教师</option>
					  <option value="学科主任">学科主任</option>
					  <option value="其他">其他</option>
					</select >
				</td>
			</tr>
			<tr>
				<td ><label for="userOffice">办公室：</label></td>
				<td><input id="num3" name="userOffice" value="" /></td>
				<td><label for="userMailBox">邮箱：</label></td>
				<td><input id="num4" name="userMailBox" value="" /></td>
			</tr>
			<tr>
				<td><label for="userTelephone">固定电话：</label></td>
				<td><input id="num5" name="userTelephone" value="" /></td>
				<td><label for="userMobilephone">移动电话：</label></td>
				<td><input id="num6" name="userMobilephone" value="" /></td>	
			</tr>
		</table>
		<table class="fr_table">
			<tr>
				<td>
					<a href="#" class="button" ><span class="icon icon-store"></span>保存</a>
				</td>
				<td>
					<a href="#" class="button" ><span class="icon icon-cancel"></span>取消</a>
				</td>
			</tr>
		</table>
	</form>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$( "input[type=submit], a.button , button" ).button();
			
			/* 表格化表单布局*/
			$(".infofr_table td:nth-child(even)").addClass("fr_left");
			$(".infofr_table td:nth-child(odd)").addClass("fr_right");
		});
		
	</script>