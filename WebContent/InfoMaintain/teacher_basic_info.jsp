<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<table style="width:100%">
	<tr>
		<td style="width:50%">
			<p class="smallTitle">基本信息:</p>
		</td>
		
		<td style="float:right">
			<a id="editPassword" class="button" href="#"><span class="icon icon-edit"></span>修改密码</a>
			<!-- <a id="editTeacher" class="button" href="#"><span class="icon icon-edit"></span>编辑</a > -->
		</td>
	</tr>
</table>	
<table id="teacherBaseinfoMaintain_tb" class="infofr_table">
	<tr>
		<td style="width:200px;"><label for="loginId">登陆账户：</label></td>
		<td><input type="text" name="loginId" value="lingy@buaa.edu.cn" disabled="true"/></td>
	</tr>
	<tr>
		<td><label for="name" class="alert_info">*姓名：</label></td>
		<td><input type="text" name="name" value="林老师" /></td>
		<td><label for="gender" class="alert_info">*性别：</label></td>
		<td>
			<select id="gender">
				<option value="1" selected="selected">女</option>
				<option value="2">男</option>
			</select>
		</td>
	</tr>
	
	<tr>
		<td><label for="birthday">出生年月：</label></td>
		<td><input type="text" name="birthday" value="1964-01-01" /></td>
		<td><label for="idCardNo">身份证号：</label></td>
		<td><input type="text" name="idCardNo" value="140523199001090012"  /></td>
	</tr>
	<tr>
		<td><label for="ethnicGroup">民族：</label></td>
		<td>
			<select id="ethnicGroup">
				<option value="1" selected="selected">汉族</option>
				<option value="2">其他</option>
			</select>
		</td>
		<td><label for="politicalStatus">政治面貌：</label></td>
		<td>
			<select  id="politicalStatus">
				<option value="1" selected="selected">党员</option>
				<option value="2">团员</option>
				<option value="3">其他</option>
			</select>
		</td>
	</tr>
	
	<tr>
		<td><label for="unitId" class="alert_info">*单位代码及名称：</label></td>
		<td><input type="text" name="unitId" value="10006-北京航空航天大学" /></td>
		<td><label for="discId" class="alert_info">*学科代码及名称：</label></td>
		<td><input type="text" name="discId" value="0835-软件工程" /></td>	
	</tr>
	<tr>
		<td><label for="schoolName">所属学院：</label></td>
		<td><input type="text" name="schoolName" value="软件学院" /></td>
		<td><label for="email">个人邮箱：</label></td>
		<td><input type="text" name="email" value="lingy@buaa.edu.cn"/></td>
	</tr>
	<tr>
		<td><label for="cellPhone">移动电话：</label></td>
		<td><input type="text" name="cellPhone" value="18623569953" /></td>
		<td><label for="officePhone">办公电话：</label></td>
		<td><input type="text" name="officePhone" value="010-6548223"  /></td>
	</tr>
	<tr>
		<td><label for="officeAddr">办公地址：</label></td>
		<td><input type="text" name="officeAddr" value="工程训练中心308" /></td>
		<td><label for="address">通信地址：</label></td>
		<td><input type="text" name="address" value="北京航空航天大学" /></td>	
	</tr>
	<tr>
		<td><label for="zipCode">邮政编码：</label></td>
		<td><input type="text" name="zipCode" value="100191" /></td>
	</tr>
</table>