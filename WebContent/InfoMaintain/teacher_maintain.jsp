<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>个人信息维护
	</h3>
</div>
<div class="infofr_div">
	<form id="teacherMaintain_fm" class="fr_form" >
		<jsp:include page="/InfoMaintain/teacher_basic_info.jsp"></jsp:include>
		<jsp:include page="/InfoMaintain/teacher_expert_info.jsp"></jsp:include>
		<table class="fr_table">
			<tr class="fr_right">
				<td>
					<a href="#" id="saveTeacher" class="button"><span class="icon icon-store"></span>保存</a>
					<a href="#" id="cancelTeacher" class="button"><span class="icon icon-cancel"></span>取消</a>
				</td>
			</tr>
		</table>
	</form>
</div>
	
	
<script type="text/javascript">
	$(document).ready(function(){
		
		$( "input[type=submit], a.button , button" ).button();
		
		$(".infofr_table td:nth-child(even)").addClass("fr_left");
		$(".infofr_table td:nth-child(odd)").addClass("fr_right");
		
		/* $("input").attr({readonly:"readonly"});
		$("input").css("border","0px solid");
		$("textarea").attr({readonly:"readonly"});
		$("select").addClass("disable_select"); */
		
		$("#editPassword").click(function(event){
			changePassword("${ContextPath}/DSEP/infoMaintain/toChangePassword");
		})
		
		function changePassword(url)
		{
			$.post(url,function(data){
				$("#dialog").empty();
				$("#dialog").append(data);
				$("#dialog").dialog({
					title:"修改密码",
		  		    height:'270',
		  			width:'450',
		  			position:'center',
		  			modal:true,
		  			draggable:true,
		  		    hide:'fade',
		  			show:'fade',
		  		    autoOpen:true,
		  		    buttons:{
		  		    	"确定":function(){
		  		    		$("#dialog").dialog("close");
		  		    		$("#dialog").empty();
		  		    	},
		  		    	"取消":function(){
		  		    		$("#dialog").dialog("close");
		  		    		$("#dialog").empty();
		  		    	}
		  		    }
				});
			},'html');
		}
		
		$("#editTeacher").click(function(){
			//produceQuery();
			intoEditStyle();
		});
		
		$("#saveTeacher").click(function(){
			intoSaveStyle();
		});
		
		$("#cancelTeacher").click(function(){
			intoSaveStyle();
		})
	});
</script>
