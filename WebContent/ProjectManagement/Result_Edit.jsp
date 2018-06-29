<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div  >
	<form id="fm_result" class="fr_form" method="post"> 
	 	<table class="layout_table left">
			<tr class="hidden">
	             <td >ID：</td>  
	             <td >
	                <input id="id" type="text" name="id" value="${judgeResult.id}" readonly/>  
	             </td>
            </tr>  
			<tr>
				<td style="width:100px;">
				    <span class="TextFont">项目评分：</span>
				</td>
				<td style="width:150px;">
					<select id="score" name="score" >
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
					<select id="isAccept" name="isAccept" >
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
					<textarea name="opinion" id="opinion">${judgeResult.opinion}</textarea> 
				</td>
			</tr>
			</table>
	</form>
</div>
<script type="text/javascript">

var editor = new baidu.editor.ui.Editor({initialFrameHeight:150,initialFrameWidth:400,autoClearinitialContent :true, });
editor.render("opinion");

$(document).ready(function(){
	var score = document.getElementById("score");
	for( i=0; i < score.length; i++ )
	{
		if(score.options[i].value=="${judgeResult.score}"){
			score.options[i].selected=true;
			break;
		}
	}
	var isAccept = document.getElementById("isAccept");
	for( i=0; i < isAccept.length; i++ )
	{
		if(isAccept.options[i].value=="${judgeResult.isAccept}"){
			isAccept.options[i].selected=true;
			break;
		}
	}
});
</script>