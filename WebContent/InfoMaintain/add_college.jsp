<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="form">
	<form id="collegeAdd_fr" class="fr_form" action="" method="post">
		<table id="collegeAdd_tb" class="fr_table">
			<tr>
				<td>
					<Span ><b>学院信息</b></Span>
				</td>
			</tr>
			<tr>
				<td><label for="collegeId">学院编号：</label></td>
				<td><input type="text" name="collegeId" value="" /></td>
				<td><label for="collegeName">学院名称：</label></td>
				<td><input type="text"  name="collegeName"  /></td>
			</tr>
			<tr>
				<td><label for = "provinceId">省编号：</label></td>
				<td><input type = "text" name = "provinceId" value = ""/></td>
				<td><label for = "collegeOldName">曾用院名：</label></td>
				<td><input type = "text" name = "collegeOldName" value = ""/></td>
			</tr>
			<tr>
				<td><label for = "zipCode">邮政编码：</label></td>
				<td><input type = "text" name = "zipCode" value = ""/></td>
				<td><label for = "zipAddress">邮政地址：</label></td>
				<td><input type = "text" name = "zipAddress" value = ""/></td>
			</tr>
			<tr>
				<td><label for = "linkMan">联络人：</label></td>
				<td><input type = "text" name = "linkMan" value = ""/></td>
				<td><label for = "ministryId">部门编号：</label></td>
				<td><input type = "text" name = "ministryId" value = ""/></td>
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
	
	});

</script>