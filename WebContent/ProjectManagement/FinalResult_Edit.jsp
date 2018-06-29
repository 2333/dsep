<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div  >
	<form id="fm_result" class="fr_form" method="post"> 
	 	<table class="layout_table left">
			<tr class="hidden">
	             <td >ID：</td>  
	             <td >
	                <input id="id" type="text" name="id" value="${finalResult.id}" readonly/>  
	             </td>
	             <td>
	             <input id="isCheck" type="text" name="isCheck" value="${isCheck}" readonly/>  
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
				    <span class="TextFont">意见建议：</span>
				</td>
				<td colspan="4">
					<textarea name="txtRecord" id="txtRecord">${finalResult.txtRecord}</textarea> 
				</td>
			</tr>
			</table>
	</form>
</div>
<script type="text/javascript">

var editor ;
var isCheck = $('#isCheck').val();
if( isCheck == "是")
{
	$('#score').attr("disabled", "disabled");	
	editor = new baidu.editor.ui.Editor({initialFrameHeight:150,initialFrameWidth:400,autoClearinitialContent :true,readonly:true, });
}
else editor = new baidu.editor.ui.Editor({initialFrameHeight:150,initialFrameWidth:400,autoClearinitialContent :true, });
editor.render("txtRecord");

$(document).ready(function(){
	var score = document.getElementById("score");
	for( i=0; i < score.length; i++ )
	{
		if(score.options[i].value=="${FinalResult.score}"){
			score.options[i].selected=true;
			break;
		}
	}
	
	
});
</script>