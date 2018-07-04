<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>表单采集样表
	</h3>
</div>
<div class="selectbar">
	<p>
		<label for="culture">指标类型:</label> <select id="culture">
			<option value="1">专家</option>
			<option value="2">团队</option>
			<option value="3" selected="selected">专职教师与学生情况</option>
			<option value="4">省重点学科</option>
			<option value="5">重点实验室</option>
		</select>
	</p>
</div>
<div class="form">
	<form class="fr_form" action="" method="post">
		<table class="fr_table">
			<tr>
				<td><label for="num1">目前在校博士生数：</label></td>
				<td><input id="num1" name="num1" value="" /></td>
				<td><label for="num2">目前在校硕士生数：</label></td>
				<td><input id="num2" name="num1" value="" /></td>
			</tr>
			<tr>
				<td><label for="num3">博士生导师数：</label></td>
				<td><input id="num3" name="num1" value="" /></td>
				<td><label for="num4">硕士生导师：</label></td>
				<td><input id="num4" name="num1" value="" /></td>
			</tr>
			<tr>
				<td><label for="num5">全日制专业学位学生“校外硕导”数：</label></td>
				<td><input id="num5" name="num1" value="" /></td>
			</tr>
			<tr>
				<td><label for="num6">专职教师及研究人员总数：</label></td>
				<td><input id="num6" name="num1" value="" /></td>
				<td><label for="num7">全校研究生指导教师总数：</label></td>
				<td><input id="num7" name="num1" value="" /></td>
			</tr>
			<tr>
				<td><label for="num8">近三年授予全日制专业学位硕士数：</label></td>
				<td><input id="num8" name="num1" value="" /></td>
			</tr>
			<tr>
				<td><label for="num9">近三年授予全日制学术学位硕士数：</label></td>
				<td><input id="num9" name="num1" value="" /></td>
				<td><label for="num10">近三年授予全日制学术学位博士数：</label></td>
				<td><input id="num10" name="num1" value="" /></td>
			</tr>
		</table>
	</form>
	<div class="toolbar">
		<table>
			<tr>
				<td><a class="button" href="#"><span
						class="icon icon-set"></span>保存</a></td>
				<td><a class="button" href="#"><span
						class="icon icon-logout"></span>重置</a></td>
			</tr>
		</table>
	</div>
</div>
<div class="comment">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 说明:
	<p>1.“专职教师及研究人员总数”是指统计时间点人事关系在本单位本学科的专职教师或研究人员总数；医科只计附属医院人员，教学医院、合作医院等的人员不计入内。</p>
	<p>2.“在校博士/硕士生数”是指统计时间点学籍在本单位的本学科博士/硕士生数，即全日制攻读专业学位和学术学位的学生。</p>
	<p>3.“硕士生导师”是指上述“在校硕士生”的指导教师（包括既指导硕士又指导博士的导师），“博士生导师”是指上述“在校博士生”的指导教师，以上均包括“兼职导师”；在统计时间点指导的研究生均已毕业的不计入导师数；专业学位研究生（如工程硕士等）聘任的“校外导师”数单独填写。</p>

</div>

<script type="text/javascript">
	$(document).ready(function(){
		/* 按钮初始化*/
		$( "input[type=submit], a.button , button" )
		  .button()
		  .click(function( event ) {
			event.preventDefault();
		});
		/* 表格化表单布局*/
		$(".fr_table td:nth-child(even)").addClass("fr_left");
		$(".fr_table td:nth-child(odd)").addClass("fr_right");
		//123
	});
</script>




