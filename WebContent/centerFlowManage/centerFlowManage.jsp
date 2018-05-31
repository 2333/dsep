<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>学科自检平台流程控制
	</h3>
</div>
<div class="infofr_div">
	<fieldset class="infofr_fieldset">
		<legend class="smallTitle">本轮参评信息：</legend>
		<form action="" class="infofr_form" id="infoForm">
			<label for="domainId"></label> <input type="text" id="domainId"
				name="id" class="info_input_readonly" value="${domain.id}"
				style="display: none" /> <label for="domainName" class="info_label">本轮参评名称：</label>
			<input type="text" id="domainName" name="domainName"
				class="info_input_readonly" value="${domain.name}" /> <label
				for="innerState" class="info_label">当前评估进度情况：</label> <input
				type="text" id="innerState" name="innerState" class="info_input" />
			<input type="text" id="innerStateHD" name="innerState"
				class="info_input" style="display: none"
				value="${domain.innerState}" />
			<!-- 隐藏存储innerState编号 -->
			<br /> <label class="info_label hide_block" style="display: none"></label>
			<label class="info_label hide_block" style="display: none"></label> <label
				id="innerStatelb" for="innerStatelb" class="info_label"
				style="display: none">修改评估状态：</label> <select id="innerStateSel"
				style="display: none">
				<option value="0">开启本轮申报</option>
				<option value="1">内部数据处理</option>
				<option value="2">关闭本轮参评</option>
			</select> <span class="validSpan"></span>
		</form>
	</fieldset>
	<fieldset class="infofr_fieldset">
		<a id="editInner" class="button"><span class="icon icon-edit"></span>编辑</a>
		<a id="saveInner" class="button" style="display: none"><span
			class="icon icon-save"></span>保存</a> <a id="cancelInner" class="button"
			style="display: none"><span class="icon icon-cancel"></span>取消</a>
	</fieldset>
</div>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script type="text/javascript">
var oriState;//源InnerState
/**
 * 修改状态
 */
function changeInnerState(){
	var newState = $("#innerStateSel").val();
	$("#innerStateHD").val(newState);
	$("#innerState").val(convertMainFlow(newState));
}
/**
 * 编辑按钮
 */
function editInner()
{
	$("#innerStatelb,#innerStateSel,#saveInner,#cancelInner,.hide_block").show();
	$("#editInner").hide();
}
/**
 * 保存按钮
 */
function saveInner(domainId,innerState){
	
	$.commonRequest({
		url:"${ContextPath}/MainFlow/centerFlowManage/saveInnerState"
				+"?domainId="
				+domainId
				+"&innerState="
				+innerState,
		dataType:'text',
		success:function(data){
			if(data=='success'){
				alert_dialog('修改成功！');	
				$("#innerStatelb,#innerStateSel,#saveInner,#cancelInner").hide();
				$("#editInner").show();
			}else{
				alert_dialog('修改失败！');
			}
		},
		error:function(data){}
	});
}
function cancelInner(){
	$("#innerStatelb,#innerStateSel,.hide_block,#cancelInner,#saveInner").hide();
	$("#editInner").show();
	$("#innerState").val(oriState);
}
$(document).ready(function(){
	//初始化按钮
	$( "input[type=submit], a.button , button" )
	  .button()
	  .click(function( event ) {
		event.preventDefault();
	});
	oriState =convertMainFlow("${domain.innerState}");
	$("#innerState").val(oriState);
	$("#innerStateSel").change(function(){
		changeInnerState();
	});
	$("#editInner").click(function(){
		editInner();
	});
	$("#saveInner").click(function(){
		var domainId = $("#domainId").val();
		var innerState = $("#innerStateHD").val();
		saveInner(domainId,innerState);
	});
	$("#cancelInner").click(function(){
		cancelInner();
	});
});
</script>