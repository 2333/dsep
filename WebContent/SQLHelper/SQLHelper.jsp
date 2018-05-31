<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="infofr_table">
	<textarea id="sqlContent" rows="5" cols="50">
		
	</textarea>
</div>
<div>
	<a  id="submitQuery" class="button" href="#" ><span class="icon icon-export"></span>查询</a>
	<a  id="submitUpdateOrSave" class="button" href="#" ><span class="icon icon-export"></span>更新或者保存</a>
</div>
<div id="rowDatas_dv">
	<table id="rowDatas_tb" width="100%" border="1" cellpadding="2" cellspacing="0">
	   
	</table>
</div>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script type="text/javascript">
function submitQuery(){
	var sqlVar = $("#sqlContent").val();
	$.commonRequest({
		url:"${ContextPath}/SQLEXE/view/querySet",
		dataType:'json',
		data:{sqlStr:sqlVar},
		success:function(data){
			console.log(data);
			$("#rowDatas_tb").empty();
			var tempHtml = "<tr>";
			$.each(data.colNames,function(i,item){
				tempHtml+="<td>"+item+"</td>";
			});
			tempHtml+="</tr>";
			$.each(data.rowDatas,function(i,item){
				tempHtml+="<tr>";
				$.each(item,function(j,value){
					tempHtml+="<td>"+value+"</td>";
				});
				tempHtml+="</tr>";
			});
			$(tempHtml).appendTo("#rowDatas_tb");
		},
		error:function(data){
			alert_dialog("连接错误，请联系管理员！");
		}
	});
}
function submitUpdateOrSave(){
	var sqlVar = $("#sqlContent").val();
	$.commonRequest({
		url:"${ContextPath}/SQLEXE/view/updateOrSave",
		dataType:'text',
		data:{sqlStr:sqlVar},
		success:function(data){
			alert_dialog("更新了 "+data+"条记录！");
		},
		error:function(data){
			alert_dialog("连接错误，请联系管理员！");
		}
	});
	
}
$(document).ready(function(){
	$( "input[type=submit], a.button , button" ).button();
	$("#submitQuery").click(function(){
		submitQuery();
	});
	$("#submitUpdateOrSave").click(function(){
		submitUpdateOrSave();
	})
});
</script>