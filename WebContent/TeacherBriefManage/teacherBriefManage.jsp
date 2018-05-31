<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>教师简况表管理
	</h3>
</div>
<div id="briefManageForm" class="infofr_div">
 <fieldset class = "infofr_fieldset" >
       		<legend class="smallTitle">简况表管理：</legend>
       		<form action="" class = "infofr_form" id = "basicInfoForm" >
       		<input id="briefId" type="text" hidden="true" readonly="readonly" value="${brief.briefId}"/>
       		<label id="briefState_lb" for="briefState_lb" class="fr_label"><span class="TextFont">简况表状态：</span></label>
      		<input id="briefState" type="text" readonly="readonly" class="info_input" value="${brief.briefState}"/>
      		<label id="briefTime_lb" for="briefTime_lb" class="fr_label"><span class="TextFont">生成时间：</span></label>
      		<input id="briefTime" type="text" readonly="readonly" class="info_input" value="${brief.createDate}"/>
      			<span style="margin-left:14%;" ><a id="produceBrief" class="button" href="#"><span class="icon icon-export"></span>生成</a></span>
      			<span style="margin-left:-88px;" ><a id="reproduceBrief" class="button" href="#"><span class="icon icon-export"></span>重新生成</a></span>
      			<span style="margin-left:0px;" ><a id="downLoadBrief" class="button" href="#"><span class="icon icon-download"></span>下载</a></span>
             </form>
      </fieldset>
</div>
<div class="process_dialog">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 正在生成，请稍候！
</div>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script src="${ContextPath}/js/download/briefsheet.js"></script>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script src="${ContextPath}/js/fileOper/filename_oper.js"></script>
<script type="text/javascript">
var contextPath = "${ContextPath}";
function controlOperBts(){
	var briefState = $("#briefState").val();
	if(briefState=='已生成'){
		$("#reproduceBrief,#downLoadBrief").show();
		$("#produceBrief").hide();
	}else{
		$("#briefTime").val("无");
		$("#produceBrief").show();
		$("#reproduceBrief,#downLoadBrief").hide();
	}
}
function produceBrief(){
	$(".process_dialog").show();	
	$('.process_dialog').dialog({
		position : 'center',
		modal:true,
		autoOpen : true,
	});
	$.ajax({
		url:'${ContextPath}/Teacher/briefManage/createTeacherBrief',
		dataType:'json',
		success:function(message){
			if(message.result=="failure")
			{
				alert_dialog("生成失败,请重新尝试！");
			}
			else
			{
				alert_dialog("生成成功！");
				$.post("${ContextPath}/Teacher/briefManage/viewBrief", function(data){
					  $( "#content" ).empty();
					  $( "#content" ).append( data );
				 	  }, 'html');
				var data=message.data;
				$("#briefId").val(data);
				controlOperBts();
			}
			$(".process_dialog").dialog("destroy");
			$(".process_dialog").hide();
		},
		error:function()
		{
			alert_dialog("生成失败，请联系管理员！");
			$(".process_dialog").dialog("destroy");
			$(".process_dialog").hide();
		}
	});
}
function downLoadBrief(briefId)
{
	$.commonRequest({
		url:'${ContextPath}/Teacher/briefManage/downLoadBrief/'+briefId,
		dataType:'json',
		success:function(message){
			if(message.result=="success"){
				downloadProveMaterial(message.data);
			}
			else 
		    {
				alert_dialog("下载失败!");
		    }
		},
	complete:function(){
		
	}
	});
}
$(document).ready(function(){
	$(".process_dialog").hide();
	$( "input[type=submit], a.button , button" ).button();
	controlOperBts();
	$("#produceBrief").click(function(){
		produceBrief();
	});
	$("#reproduceBrief").click(function(){
		produceBrief();
	});
	$("#downLoadBrief").click(function(){
		var briefId=$("#briefId").val();
		downLoadBrief(briefId);
	});
});
</script>