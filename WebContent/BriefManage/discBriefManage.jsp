<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header">
	<h3><span class="icon icon-web"></span>${textConfiguration.discReportManage}</h3>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table right" style="margin-right:130px;">
		<tr>
			<td>
				<span class="TextFont">评估轮次：</span>
			</td>
			<td>
			    <label id="eval_info">2014年第一轮评估</label>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
		    <td>
				<span class="TextFont">截止日期：</span>
			</td>
			<td>
			    <label id="deadline">2014-10-28</label>
			</td>
		</tr>
	</table>
</div>
<div id="briefManageForm" class="infofr_div">
      <fieldset class = "infofr_fieldset" >
      		<legend class="smallTitle">${textConfiguration.discBaseInfo}：</legend>
      		<form action="" class = "infofr_form" id = "basicInfoForm" >
      			<label id="unitId_lb" for="unitId_lb" class="info_label2">学校编号：</label>
      			<input id="unitId" type="text" readonly="readonly" class="info_inputL" value="${brief.unitId}"/>
      			<span class = "validSpan"></span>
      			<label id="unitName_lb" for="unitName_lb" class="info_label2">学校名称：</label>
      			<input id="unitName" type="text" readonly="readonly" class="info_inputL" value="${brief.unitName}"/>
      			<span class = "validSpan"></span>
      			<br />
      			<label id="discId_lb" for="discId_lb" class="info_label2">${textConfiguration.discNumber}：</label>
      			<input id="discId" type="text" readonly="readonly" class="info_inputL" value="${brief.discId}"/>
      			<span class = "validSpan" "></span>
      			<label id="discName_lb" for="discName_lb" class="info_label2">${textConfiguration.discName}：</label>
      			<input id="discName" type="text" readonly="readonly" class="info_inputL" value="${brief.discName}"/>
      			<span class = "validSpan"></span>
      			<br />
      			<jsp:include page="/CollectMeta/disc_state.jsp"></jsp:include>
      			<span class = "validSpan"></span>
      		</form>
      </fieldset>	
       <fieldset class = "infofr_fieldset" >
       		<legend class="smallTitle">简况表管理：</legend>
       		<form action="" class = "infofr_form" id = "basicInfoForm" >
       		<label id="briefState_lb" for="briefState_lb" class="info_label2">简况表状态：</label>
      		<input id="briefState" type="text" readonly="readonly" class="info_inputL" value="${brief.briefState}"/>
      		<label id="briefTime_lb" for="briefTime_lb" class="info_label2">生成时间：</label>
      		<input id="briefTime" type="text" readonly="readonly" class="info_inputL" value="${brief.createDate}"/>
      			<span style="margin-left:14%;" ><a id="produceBrief" class="button" href="#"><span class="icon icon-export"></span>生成</a></span>
      			<span style="margin-left:-88px;" ><a id="reproduceBrief" class="button" href="#"><span class="icon icon-export"></span>重新生成</a></span>
      			<span style="margin-left:0px;" ><a id="downLoadBrief" class="button" href="#"><span class="icon icon-download"></span>下载</a></span>
             </form>
      </fieldset>
      <a id="submit2Unit" class="button" href="#" style="margin-top:20px;margin-left:450px;"><span class="icon icon-commit"></span>提交</a>
</div>
<div id="dialog-confirm">
</div>
<div id="messageBox_dv" class="table" hidden="true">
	<form id="messageBox_fm">
	<div id="message">
		
	</div>
	</form>
</div>
<input type="text" id="briefId" value="${brief.briefId}" style="display:none;">
<div class="process_dialog">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 正在生成，请稍候！
</div>
<input id="logicCheckInfo" type="hidden" class="" value="${logicCheckInfo}"/>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script src="${ContextPath}/js/download/briefsheet.js"></script>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script src="${ContextPath}/js/fileOper/filename_oper.js"></script>
<script type="text/javascript">
/**
 * 学科数据提交至学校
 */
 var domainId = "${configurations.domainId}";
 var unitId="${brief.unitId}";//学校代码
 var discId="${brief.discId}";//学科代码
 var contextPath="${ContextPath}";
 function submit2Unit(unitId,discId,isConfirm){
		$.commonRequest({
			url:'${ContextPath}/FlowActions/disc2Unit/'+unitId+'/'+discId+'/'+isConfirm,
			dataType:'json',
			success:function(result){
				if(result.message=='success')
				{
					collectState(unitId,discId);
					//isEditable_fun(unitId,discId);
					//var editor = CKEDITOR.instances.disc_editor;
					//console.log(editor);
					//editor.setReadOnly(true);
					//$("#jqGrid_collect_tb").jqGrid('setGridParam').trigger("reloadGrid");
					$("#submit2Unit").hide();
					alert_dialog('提交成功！');	
					
				}else{
					var isExistFalse=false;
					var errorMessage = result;
					if(errorMessage.passed==false){
						console.log(errorMessage.data);
						$( "#message" ).empty();
						$.each(errorMessage.data,function(i,item){
							
							if(item.typeName=='错误'){
								isExistFalse = true;
								$( "#message" ).append("<div class='message'><span class='red'>"+item.typeName+"</span>"+
										" : \""+item.entityName+"\" , "+item.conclusion+"</div>");
							}else{
								$( "#message" ).append("<div class='message'><span class='yellow'>"+item.typeName+"</span>"+
										" : \""+item.entityName+"\" , "+item.conclusion+"</div>");
							}				
						});
						//$("#message").append("<div><span class='TextFont'>您确定提交吗？</span></div>");
						$(".red").css({"color":"red"});
						$(".yellow").css({"color":"#EEC591"});
						$(".message").css({"font-size":"18px"});
						if(isExistFalse){
							$("#message").append("<div><span class='TextFont'>数据错在错误，请修改正确后提交！</span></div>");
							$( "#messageBox_dv" ).dialog({
								  width:500,
					      	      height:450,
					      	      buttons: {
					      	        "关闭": function() {
					      	            $( this ).dialog( "close" );
					      	          }
					      	        }
					      	  });
						}else{
							$("#message").append("<div><span class='TextFont'>您确定提交吗？</span></div>");
							$( "#messageBox_dv" ).dialog({
								  width:500,
					      	      height:450,
					      	      buttons: {
					      	        "确定": function() {
					      	        	$( this ).dialog( "close" );
					      	        	submit2Unit(unitId,discId,1);
					      	        	
					      	        },
					      	        "取消": function() {
					      	            $( this ).dialog( "close" );
					      	          }
					      	        }
					      	  });	
						}
					}else if(errorMessage.passed!=false){
						alert_dialog(result.message);
					}	
				}
			}
		});
	}
function operControl(){
	document.getElementById("downLoadBrief").style.visibility = "hidden";
	document.getElementById("reproduceBrief").style.visibility = "hidden";
	document.getElementById("produceBrief").style.visibility = "hidden";
	var briefState = $("#briefState").val();
	if(briefState=='已生成'){
		/* $("#downLoadBrief,#reproduceBrief").show();
		document.getElementById("downLoadBrief").style.visibility = "visible";
		document.getElementById("reproduceBrief").style.visibility = "visible"; */
	}else{
		$("#briefTime").val("无");
		/* document.getElementById("produceBrief").style.visibility = "visible"; */
	}
}
function produceBrief(unitId,discId)
{
	$(".process_dialog").show();	
	$('.process_dialog').dialog({
		position : 'center',
		modal:true,
		autoOpen : true,
	});
	$.ajax({
		url:'${ContextPath}/DiscInfoManage/viewBrief/produceBrief/'+unitId+'/'+discId,
		dataType:'json',
		success:function(message){
			if(message.result=="failure")
			{
				alert_dialog("生成失败,请先上传学科简介！");
			}
			else
			{
				
				alert_dialog("生成成功！");
				$.post("${ContextPath}/DiscInfoManage/viewBrief", function(data){
					  $( "#content" ).empty();
					  $( "#content" ).append( data );
				 	  }, 'html');
				var data=message.data;
				$("#briefId").val(data);
				document.getElementById("downLoadBrief").style.visibility = "visible";
				document.getElementById("reproduceBrief").style.visibility = "visible";
				document.getElementById("produceBrief").style.visibility = "hidden";
			}
			$(".process_dialog").dialog("destroy");
			$(".process_dialog").hide();
		},
		error:function()
		{
			$(".process_dialog").dialog("destroy");
			$(".process_dialog").hide();
		}
	});
}
function downLoadBrief(briefId)
{
	$.commonRequest({
		url:'${ContextPath}/DiscInfoManage/viewBrief/downLoadBrief/'+briefId,
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
	operControl();
	var state=$("#briefState").val();
	if( state=="未生成" )
	{
		$("#briefTime_lb").hide();
		$("#briefTime").hide();
	}
	else 
	{
		$("#briefState_lb").hide();
		$("#briefState").hide();
	}
	$(".process_dialog").hide();
	$( "input[type=submit], a.button , button" ).button();
	intoSaveStyle();
	collectState(unitId,discId);
	$("#submit2Unit").click(function(){
		var state=$("#briefState").val();
		var logicCheckInfo = $("#logicCheckInfo").val();
		/* if( state=="未生成" ){
			alert_dialog("学科简况表还未生成，请在生成简况表后提交学科成果！");
		} else */ 
		if (logicCheckInfo == "error") {
			alert_dialog("逻辑检查有错误，请重新检查后再提交！");
		} else if(logicCheckInfo == "unchecked"){
			alert_dialog("未进行逻辑检查，请检查后提交！");
		}
		else{
			$( "#dialog-confirm" ).empty().append(
					"<p>提示信息：</p><br>"+
					"<p>逻辑检查时间： "+"${logicCheckInfo}。"+"</p><br>"+
					"<p>简况表生成时间： "+"${brief.createDate}。"+"</p><br>"+
					"<p>请确保逻辑检查和简况表的有效性后，再提交数据！</p>");
	  		  $( "#dialog-confirm" ).dialog({
	      	      height:350,
	      	      buttons: {
	      	        "确定": function() {
	      	        	submit2Unit(unitId,discId,0);
	      	        	$( this ).dialog( "close" );
	      	        },
	      	        "取消": function() {
	      	            $( this ).dialog( "close" );
	      	          }
	      	        }
	      	  });
		}
		
	});
	
	$("#produceBrief").click(function(){
		produceBrief(unitId,discId);
	});
	$("#reproduceBrief").click(function(){
		produceBrief(unitId,discId);
	});
	$("#downLoadBrief").click(function(){
		var briefId=$("#briefId").val();
		downLoadBrief(briefId);
	});
});
</script>
