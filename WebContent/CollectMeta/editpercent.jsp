<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<table id="discDialog" class="fr_table" hidden="true">
	<tr>
		<td class="fr_right" style="width: 100px;">参加单位数：</td>
		<td class="fr_left"><input id="parnumber" type="text" size="5" />
		</td>
	</tr>
	<tr>
		<td class="fr_right"><input id="order" type="radio"
			name="radiobutton" value="order" />填写次序&nbsp;&nbsp;</td>
		<td><input id="propor" type="radio" name="radiobutton"
			value="propor" />填写比例</td>
	</tr>
	<tr id="deparorder_div" style="display: none">
		<td class="fr_right">署名次序：</td>
		<td class="fr_left"><input id="deparorder" type="text" size="5" />
		</td>
	</tr>
	<tr id="deparpropor_div" style="display: none">
		<td class="fr_right">所占比率：</td>
		<td class="fr_left"><input id="deparpropor" type="text" size="5" />%
		</td>
	</tr>
</table>
<table id="teacherDialog" class="fr_table" hidden="true">
	<tr>
		<td class="fr_right" style="width: 100px;">参加教师数：</td>
		<td class="fr_left"><input id="parTnumber" type="text" size="5" />
		</td>
	</tr>
	<tr id="deparorder_div">
		<td class="fr_right">署名次序：</td>
		<td class="fr_left"><input id="teacherOrder" type="text" size="5" />
		</td>
	</tr>
</table>
<script type="text/javascript"> 
var editType="${user.userType}";
$(document).ready(function(){
	if(editType=='4'){
		$("#teacherDialog").show();
	}else{
		$("#discDialog").show();
	}
});
</script>