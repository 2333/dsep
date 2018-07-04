<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div   >
	<form id="fm_result" class="fr_form" method="post"> 
	 	<table class="layout_table left">
			<tr>
				<td style="width:100px;">
				    <span class="TextFont">项目评分：</span>
				</td>
				<td style="width:150px;">
					<select id="score" name="score">
						<option value="A">A</option>
						<option value="B">B</option>
						<option value="C">C</option>
						<option value="D">D</option>
						<option value="E">E</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
				    <span class="TextFont">是否立项：</span>
				</td>
				<td>
					<select id="isAccept" name="isAccept">
						<option value="是">是</option>
						<option value="否">否</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>
				    <span class="TextFont">意见建议：</span>
				</td>
				<td colspan="4">
					<textarea name="opinion" id="opinion"></textarea> 
				</td>
			</tr>
			</table>
	</form>
</div>
<script type="text/javascript">

var editor = new baidu.editor.ui.Editor({initialFrameHeight:150,initialFrameWidth:400,autoClearinitialContent :true, });
editor.render("opinion");
</script>