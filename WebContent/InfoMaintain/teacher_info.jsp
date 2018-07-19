<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>教师个人信息
	</h3>
</div>
<div class="infofr_div" > 
	<fieldset class = "infofr_fieldset">
		<legend class="smallTitle">基础信息：</legend>
		<form action="" class = "infofr_form" >
				<label for="teacherName" class="info_label2" style="width:150px;" >姓名：</label>
				<input type="text" name="teacherName" value="${teacher.name}" class="fr_input info_input_readonly" readonly="readonly"/>
				<span class = "validSpan"></span>
				<label for="teacherIdentification" class="info_label2" style="width:150px;">身份证号：</label>
				<input type="text" class="fr_input info_input_readonly" name="teacherIdentification" value="${teacher.idCardNo}" readonly="readonly" />
				<span class = "validSpan"></span>
				<br />
				<label for="teacherMobilephone" class="info_label2" style="width:150px;">移动电话：</label>
				<input type="text" class="fr_input info_input_readonly" name="teacherMobilephone" value="${teacher.cellPhone}" readonly="readonly"/>
				<span class = "validSpan"></span>
				<label for="teacherTelephone" class="info_label2" style="width:150px;">固定电话：</label>
				<input type="text" class="fr_input info_input_readonly" name="teacherTelephone" value="${teacher.zzdh}" readonly="readonly"/>	
				<span class = "validSpan"></span>
				<br />
				<label for="teacherOffice" class="info_label2" style="width:150px;">办公室：</label>
				<input type="text" class="fr_input info_input_readonly" name="teacherOffice" value="${teacher.officePhone}" readonly="readonly"/>
				<span class = "validSpan"></span>
				<label for="teacherMailBox" class="info_label2" style="width:150px;">邮箱：</label>
				<input type="text" class="fr_input info_input_readonly" name="teacherMailBox" value="${teacher.email}" readonly="readonly"/>
				<span class = "validSpan"></span>
				<br />
		</form>
	</fieldset>
	<fieldset class = "infofr_fieldset">
		<legend class="smallTitle">专业信息：</legend>
		<form action="" class = "infofr_form" >
				<label for="teacherUniversity" class="info_label2" style="width:150px;">所属学校：</label>
				<input class="fr_input info_input_readonly" type="text" name="teacherUniversity" value="${teacher.unitId}" readonly="readonly"/>
				<span class = "validSpan"></span>
				<label for="teacherDiscipline" class="info_label2" style="width:150px;" style="width:150px;">一级学科代码1：</label>
				<input class="fr_input info_input_readonly" type="text" name="teacherDiscipline" 
				  value="${teacher.discId}"	readonly="readonly"/>
				<br/>
				<label for="teacherCollege" class="info_label2" style="width:150px;" style="width:150px;">一级学科代码2：</label>
				<input class="fr_input info_input_readonly" type="text" name="teacherCollege" 
				  value="${teacher.yjxkm2}"	readonly="readonly"/>
				  <span class = "validSpan"></span>
				<label for="teacherWorknumber" class="info_label2" style="width:150px;">专家编号：</label>
				<input class="fr_input info_input_readonly" type="text" name="teacherWorknumber" value="${teacher.zjbh}" readonly="readonly"/>
				<br/>
				<label for="teacherTitle" class="info_label2" style="width:150px;" >专家类别码:</label>
				<input type="text" class="fr_input info_input_readonly" name="zjlb" value="${teacher.zjlbm}" readonly="readonly"/>
				<span class = "validSpan"></span>
				<label for="teacherHighestDegree" class="info_label2" style="width:150px;">最高学位码：</label>
				<input class="fr_input info_input_readonly" type="text" name="teacherHighestDegree" value="${teacher.zgxwm}" readonly="readonly" />
				<br/>
				<label for="teacherType" class="info_label2" style="width:150px;">导师类别码:</label>
				<input class="fr_input info_input_readonly" type="text" name="teacherHighestDegree" value="${teacher.dslbm}" readonly="readonly" />
				<span class = "validSpan"></span>
				<label for="teacherResearchDirection" class="info_label2" style="width:150px;">研究方向1：</label>
				<input class="fr_input info_input_readonly" type="text" name="teacherResearchDirection" value="${teacher.yjfx1}" readonly="readonly"/>
				<span class = "validSpan"></span>
				<label for="teacherResearchDirection" class="info_label2" style="width:150px;">研究方向2：</label>
				<input class="fr_input info_input_readonly" type="text" name="teacherResearchDirection" value="${teacher.yjfx2}" readonly="readonly"/>
				<span class = "validSpan"></span>
				<label for="teacherResearchDirection" class="info_label2" style="width:150px;">研究方向3：</label>
				<input class="fr_input info_input_readonly" type="text" name="teacherResearchDirection" value="${teacher.yjfx3}" readonly="readonly"/>
				<span class = "validSpan"></span>
				<label for="teacherResearchDirection" class="info_label2" style="width:150px;">研究方向4：</label>
				<input class="fr_input info_input_readonly" type="text" name="teacherResearchDirection" value="${teacher.yjfx4}" readonly="readonly"/>
				<span class = "validSpan"></span><br/>
				<%-- <label for="teacherBrief" class="info_label2" style="width:150px;">备注:</label>
				<textarea name="teacherBrief" class="fr_textarea" rows="10" cols="40" readonly="readonly">${teacher.bz}</textarea>
				</form> --%>
		</fieldset>
</div>
		 
	<script type="text/javascript">
		$(document).ready(function(){
				$(".infofr_table td:nth-child(even)").addClass("fr_left");
				$(".infofr_table td:nth-child(odd)").addClass("fr_right"); 
				intoSaveStyle();
		});
	</script>