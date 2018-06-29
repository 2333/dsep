<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="con_header inner_table_holder">
	<%-- <jsp:include page="../_ddl_publicity_round.jsp"></jsp:include> --%>
	<table class="layout_table left">
		<tr>
			<td><span class="icon icon-web"></span></td>
			<td><span class="TextFont">公示信息-公示批次:</span> <select
				id="ddl_publicity_round">
					<c:choose>
						<c:when test="${!empty publicityRoundMap}">
							<c:forEach items="${publicityRoundMap}" var="publicityrounditem">
								<option value="${publicityrounditem.key}">
									${publicityrounditem.value}</option>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<option value="0">暂无</option>
						</c:otherwise>
					</c:choose>
			</select></td>
		</tr>
	</table>
	<table id="tb_message" class="layout_table right">
		<tr>
			<td><span id="sp_status" class="TextFont">公示状态：</span></td>
			<td><label id="disstatus" for="disstatus" class="TextFont">
			</label>&nbsp;&nbsp;</td>
			<td><span id="sp_beginTime" class="TextFont">开始时间：</span></td>
			<td><label id="beginTime" for="disstatus" class="TextFont">
			</label>&nbsp;&nbsp;</td>
			<td><span id="sp_endTime" class="TextFont">结束时间：</span></td>
			<td><label id="endTime" for="disstatus" class="TextFont">
			</label>&nbsp;&nbsp;</td>
			<!-- <td>
				用于关闭公示批次，只有中心用户在查看公示信息页面时会用到
				<a class="button" href="#" id="btn_close_publicity"> 
				<span class="icon icon-stop"></span>关闭公示</a>
			</td> -->
			<td style="padding-bottom:10px">
				<a class="button" href="#" id="btn_return_set"> 
				<span class="icon icon-undo"></span>设置查看单位</a>
			</td>
		</tr>
	</table>
</div>
<div class="con_header inner_table_holder">
	<table id="tb_viewMessage" class="layout_table left">
		<tr id="tr_normal">
			<td><span id="sp_viewUnit" class="TextFont">当前查看学校：</span></td>
			<td><label id="lb_viewUnit" class="TextFont">${choosenUnitId}
			</label>&nbsp;&nbsp;</td>
			<td><span id="sp_viewDiscipline" class="TextFont">当前查看${textConfiguration.disc}：</span></td>
			<td><label id="lb_viewDiscipline" class="TextFont">${choosenDisciplineId}
			</label>&nbsp;&nbsp;</td>
		</tr>
		<tr id="tr_none">
			<td >
				<span id="sp_viewUnit" class="TextFont">请设置查看单位</span>
			</td>
		</tr>
	</table>
	<table id="tb_objectDiscipline" class="layout_table right">
		<tr>
			<td style="padding-bottom:10px">
				<a class="button" href="#" id="btn_objectDiscipline"> 
				<span class="icon icon-set"></span>对${textConfiguration.disc}整体提出异议</a>
			</td>
		</tr>
	</table>
</div>
<div class="layout_holder" id="div_publicity_layout" style="width:100%">
	<c:if test="${!empty publicityRoundMap}">
		<jsp:include page="../TreeAndView/pub_tree.jsp"></jsp:include>											
		<jsp:include page="../TreeAndView/pub_view.jsp"></jsp:include> 
	</c:if>
</div>

<script type="text/javascript">
	var ddl_open_status;
	var ddl_version_id;

	$(document).ready(function() {
		$("input[type=submit], a.button , button").button();
		showPublicityMessage();
		pageShowControl();//页面上一些控件的显示与隐藏
		
		if($("#publicityRoundMap") != null ){
			loadTreeByDiscId("${choosenDisciplineId}");//根据学科ID加载树，位于pub_tree.jsp页面
			initDiscId("${choosenDisciplineId}"); //此函数在pub_view.jsp
			initUnitId("${choosenUnitId}");//此函数在pub_view.jsp	
		}
	});
	
	//返回公示设置页面
	$("#btn_return_set").click(function(){
		$.ajax({
			url:"${ContextPath}/publicity/viewPub_return",
			type:'POST',
			data:'formerViewUnitId='+"${choosenUnitId}"+"&formerViewDisciplineId="+"${choosenDisciplineId}",
			success:function(data){
				$("#content").empty();
				$("#content").append(data);
			}
		});
	});
	
	
	$("#btn_objectDiscipline").click(function(){
		openObjectDisciplineDialog(); //对学科整体提出异议，位于add_objection_dialog.jsp页面
	});
	
	//对学科整体提出异议
	function objectDisciplineDialog(url){
		$.post(url,function(data){
				$("#dialog").empty();
	 		  	$("#dialog").append( data );
	 		  	$('#dialog').dialog({
		    		title:"提出异议",
	 	  		    height:'400',
	 	  			width:'900',
	 	  			position:'center',
	 	  			modal:true,
	 	  			draggable:true,
	 	  		    hide:'fade',
	 	  			show:'fade',
	 	  		    autoOpen:true,
	 	  		    buttons:{  
	  		    		"确定":function(){ 
	  		    			var user_id = "${user.loginId}";
	  		    			var addUserUnitId = "";
	  		    			var addUserDiscId = "";
	  		    			switch("${user.userType}"+""){
						   		case "2"://学校用户
						   			addUserUnitId = "${user.unitId}";
						  			break;
				   				case "3"://学科用户	
									addUserUnitId = "${user.unitId}";
			    					addUserDiscId = "${user.discId}";
			    					break;
							}
	  		    			var dataString="problemUnitId="+"${choosenUnitId}"+
			                   "&problemDiscId="+"${choosenDisciplineId}"+
			                   "&objectContent="+$("textarea#ta_objectionContent").val()+
			                   "&userId="+user_id+
			                   "&currentPublicRoundId="+$("#ddl_publicity_round").val()+ 
							   "&objectType="+$("#ddl_object_type option:selected").text()+
							   "&objectUnitId="+addUserUnitId+
							   "&objectDiscId="+addUserDiscId
							   ;
		  	         		$.ajax({
		  	         			url:'${ContextPath}/publicity/viewPub_addOriginalObjection',   
				                type:"POST",   
				                data:dataString+"&filePath="+filePath,
		  	      				success: function(data){
			  	      				if(data == true){
			  	      					/* $("#rightlist_tb").setGridParam({url:'${ContextPath}/rbac/rightlist'}).trigger("reloadGrid"); */
		  	      	   		    		alert_dialog("异议已提出，请于异议汇总页面查看");
			  	      					$("#dialog").dialog("close");
		    	  	      			}
		    	  	      			else
		    	  	      			{
			    	  	      			$("#dialog").dialog("close");
		    	  	      				alert_dialog("公示开启失败");	
		    	  	      			}
		   	  	      			}
		  	      			});
		  	            },
		 	            "关闭":function(){
		 	            	$("#dialog").dialog("close");
 	            		}
	  		    	}
	 	 	  	});
			}, 'html');
	};
 		  	
	
	$("#ddl_publicity_round").change(function(){
		$("#jq_collect_parent").hide();
		showPublicityMessage();
		pageShowControl();
	});
	
	
	//控制页面上一些控件的显示
	function pageShowControl(){
		if( "${choosenDisciplineId}" == "" || "${choosenUnitId}" == ""){//没有从公示设置页面进入，学校和学科ID有一个为空
			$("#tr_none").show();
			$("#tr_normal").hide();
			$("#tb_objectDiscipline").hide();//隐藏“对学科整体提出异议”按钮
		}
		else{
			$("#tr_none").hide();
			$("#tr_normal").show();
			if( ddl_open_status == "1"){
				$("#tb_objectDiscipline").show();	
			}
			else{
				$("#tb_objectDiscipline").hide();
			}
		}
		if( "${user.userType}" == "1"){
			$("#tb_objectDiscipline").hide();//隐藏“对学科整体提出异议”按钮
		}
	}
	
	function showPublicityMessage() {
		var obj = document.getElementById('ddl_publicity_round');
		var index = obj.selectedIndex; //序号，取当前选中选项的序号
		var val = obj.options[index].value;
		$.ajax({
			type : 'POST',
			async : false,
			url : '${ContextPath}/publicity/viewPub_getPublicityMessage',
			data : "publicity_round_id=" + val,
			dataType : 'json',
			success : function(data) {
				if (data != null) {
					$("label#disstatus").html(data.openStatus + "  ");
					$("label#beginTime").html(data.actualBeginTime + "  ");
					$("label#endTime").html(data.endTime + "  ");
					if (data.openStatus + "" == "进行中")
						ddl_open_status = "1";
					else
						ddl_open_status = "0";
					ddl_version_id = data.versionId + "";
					//pub_view页面调用此函数
					initRoundMessage(ddl_version_id, ddl_open_status);
				} else {
					$("#tb_message").children().children().hide();
				}
			}
		});
	}
	

	
	


</script>