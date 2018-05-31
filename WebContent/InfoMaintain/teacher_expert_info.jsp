<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<p class="smallTitle">专家信息:</p>
<table id="teacherSelfinfoMaintain_tb" class="infofr_table">	
	<tr>
		<td style="width:200px;"><label for="firstDisplineCode">主要从事的一级学科：</label></td>
		<td>
			<select  id="firstDisplineCode">
				<option value="1" selected="selected">软件工程</option>
				<option value="2">计算机科学与技术</option>
			</select>
			<!-- <input type="text" class="hidden_text" value="软件工程" name="firstDisplineCode" /> -->
		</td>
		<td><label for="secondDisplineCode">次要从事的一级学科：</label></td>
		<td>
			<select  id="secondDisplineCode">
				<option value="1">软件工程</option>
				<option value="2" selected="selected">计算机科学与技术</option>
			</select>
			<!-- <input type="text" class="hidden_text" value="计算机科学与技术" name="secondDisplineCode" /> -->
		</td>
		
	</tr>
	
	<tr>
		<td><label for="researchDirection1">研究方向1：</label></td>
		<td><input type="text" name="researchDirection1" value="软件工程" /></td>
		<td><label for="researchDirection2">研究方向2：</label></td>
		<td><input type="text" name="researchDirection2" value="数据库"  /></td>
	</tr>
	<tr>
		<td><label for="researchDirection3">研究方向3：</label></td>
		<td><input type="text" name="researchDirection3" value="大数据" /></td>
		<td><label for="researchDirection2">研究方向4：</label></td>
		<td><input type="text" name="researchDirection4" value="云计算"  /></td>
	</tr>
	
	<tr>
		<td><label for="highestDegree">最高学位：</label></td>
		<td>
			<select  id="highestDegree">
				<option value="1" selected="selected">博士</option>
				<option value="2">硕士</option>
				<option value="3">学士</option>
				<option value="4">无</option>
			</select>
			<!-- <input type="text" class="hidden_text" value="博士" name="highestDegree" /> -->
		</td>
		<td><label for="expertType">专家类别：</label></td>
		<td>
			<select  id="expertType">
				<option value="1" selected="selected">其他</option>
				<option value="2">中国科学院院士</option>
				<option value="3">中国工程院院士</option>
				<option value="4">双院士</option>
			</select>
			<!-- <input type="text" class="hidden_text" value="其他" name="expertType" /> -->
		</td>
		
	</tr>
	
	<tr>
		<td><label for="techPositionCode">专业技术职务：</label></td>
		<td>
			<select  id="techPositionCode">
				<option value="1" selected="selected">正高级</option>
				<option value="2">副高级</option>
			</select>
			<!-- <input type="text" class="hidden_text" value="正高级" name="techPositionCode" /> -->
		</td>
		<td><label for="tutorType">导师类别：</label></td>
		<td>
			<select  id="tutorType">
				<option value="1">博士生导师</option>
				<option value="2" selected="selected">硕士生导师</option>
			</select>
			<!-- <input type="text" class="hidden_text" value="硕士生导师" name="tutorType" /> -->
		</td>
		
	</tr>
	
	<tr>
		<td><label for="workSince" class="fr_label">任导师年月：</label></td>
		<td><input class="fr_input" type="text" name="workSince" value="1999-05-21" /></td>
		<td><label for="highestDegreeSince" class="fr_label">获学位年月：</label></td>
		<td><input class="fr_input" type="text" name="highestDegreeSince" value="1999-05-21" /></td>
	</tr>
	
	<tr>
		<td><label for="foreignLanguageCode">外国语种名称：</label></td>
		<td>
			<select  id="foreignLanguageCode">
				<option value="1" selected="selected">英语</option>
				<option value="2">俄语</option>
			</select>
			<!-- <input type="text" class="hidden_text" value="英语" name="foreignLanguageCode" /> -->
		</td>
		<td><label for="foreignLanguageLevel">外语熟练程度：</label></td>
		<td>
			<select  id="foreignLanguageLevel">
				<option value="1">精通</option>
				<option value="2" selected="selected">熟练</option>
			</select>
			<!-- <input type="text" class="hidden_text" value="熟练" name="foreignLanguageLevel" /> -->
		</td>
		
	</tr>
	
	<tr>
		<td><label for="ifParttimeTutor">是否在外单位担任兼职导师：</label></td>
		<td>
			<select  id="ifParttimeTutor">
				<option value="1" selected="selected">是</option>
				<option value="2">否</option>
			</select>
			<!-- <input type="text" class="hidden_text" value="否" name="ifParttimeTutor" /> -->
		</td>
		<td><label for="parttimeUnit">兼职单位名称：</label></td>
		<td><input class="fr_input" type="text" name="parttimeUnit" value="无" /></td>
	</tr>
	
	<tr>
		<td style="vertical-align:top;">
			<label for="beizhu">备注：</label>
		</td>
		<td colspan="3">
			<textarea rows="10" cols="40">
			</textarea>
		</td>
	</tr>
</table>