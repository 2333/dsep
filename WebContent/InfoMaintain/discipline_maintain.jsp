<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="con_header">
	<h3><span class="icon icon-web"></span>用户信息维护</h3>
</div>
<!-- 以下是联系人信息列表--by刘琪 -->
<div class="infofr_div" > 
	<fieldset class = "infofr_fieldset">
		<legend class="smallTitle">联系人信息：</legend>
		<form action="" class = "infofr_form" id = "infoForm" >
			<label for="loginId" class = "info_label2">登陆ID：</label>
			<input type="text" id = "loginId" name = "loginId" class = "info_inputL_readonly"  value="${user.loginId}"/>		
			<span class="validSpan"></span>
			<label for="name" class = "info_label2">${textConfiguration.discName }：</label>
			<input type="text" id = "name" name = "name" class = "info_inputR_readonly"  value="${user.name}"/>
			<br />
			<label for="userType" class = "info_label2">用户类型：</label>
			<c:if test="${!empty userTypes}">
					<c:forEach items="${userTypes}" var="userType">
						<c:if test="${userType.key == user.userType}">
							<input type="text" id="userType" name="userType" class="info_inputL hidden" value="${userType.key}">
							<input type="text" id="userTypevalue" name="userTypevalue" class="info_inputL_readonly" value="${userType.value}" >	
						</c:if> 
					</c:forEach>	                 
			</c:if>
			<span class="validSpan"></span>
			<label for="email" class = "info_label2">联系人邮箱：</label>
			<input type="text" id = "email" name = "email" class = "info_inputR" value="${user.email}"/>
			<span class = "validSpan"></span>		
			<br />
			<label for="officePhone" class = "info_label2">办公电话：</label>
			<input type="text" id = "officePhone" name = "officePhone" class = "info_inputL"  value="${user.officePhone}"/>
			<span class = "validSpan"></span>
			<label for="cellPhone" class = "info_label2">移动电话：</label>
			<input type="text" id = "cellPhone" name = "cellPhone" class = "info_inputR"  value="${user.cellPhone}"/>
			<span class = "validSpan"></span>
			<br />
			<label for="officeAddr" class = "info_label2">办公地址：</label>
			<input type="text" id = "officeAddr" name = "officeAddr" class = "info_input_memo"  value="${user.officeAddr}"/>
			<span class = "validSpan"></span>
			<br />
			<label for="address" class = "info_label2">通信地址：</label>
			<input type="text" id = "address" name = "address" class = "info_input_memo"  value="${user.address}"/>
			<span class = "validSpan"></span>
			<br />
			<label for="memo" class = "info_label2">备注：</label>
			<input type="text"  name = "memo" class = "info_input_memo" value="${user.memo}"/>
			<br />
			<label for="id" class = "hidden">用户ID：</label>
			<input type="text" id = "id" name = "id" class = "hidden"  value="${user.id}"/>
			<label for="unitId" class = "hidden">单位代码：</label>
			<input type="text" id = "unitId" name = "unitId" class = "hidden"  value="${user.unitId}"/>
			<label for="discId" class = "hidden">学科代码：</label>
			<input type="text" id = "discId" name = "discId" class = "hidden"  value="${user.discId}"/>
		</form>
	</fieldset>
	<fieldset class = "infofr_fieldset">	
		<span class = "info_span_edit" ><button id = "editButton"><span class="icon icon-edit"></span>编辑</button></span>
		<span class = "info_span_save" ><button id = "saveButton"><span class="icon icon-store"></span>保存</button></span>
		<span class = "info_span_cancel"><button id = "cancelButton"><span class="icon icon-cancel"></span>取消</button></span>
	</fieldset>
</div>
<script>
$(document).ready(function(){
	/* 按钮初始化*/
	$( "input[type=submit], a.button , button" )
	  .button()
	  .click(function( event ) {
		event.preventDefault();
	});
	intoSaveStyle();
	//以下两句是先隐藏保存和取消按钮--by刘琪
	document.getElementById("saveButton").style.visibility = "hidden";
	document.getElementById("cancelButton").style.visibility = "hidden";
	validator(".infofr_form");
});

/*以下是编辑保存取消按钮的点击事件--by刘琪*/
$("#editButton").click(function(){
	document.getElementById("editButton").style.visibility = "hidden";
	document.getElementById("saveButton").style.visibility = "visible";
	document.getElementById("cancelButton").style.visibility = "visible";
	gotoEditStyle();
});
$("#saveButton").click(function(){
	if($(".infofr_form").valid()){
		document.getElementById("editButton").style.visibility = "visible";
		document.getElementById("saveButton").style.visibility = "hidden";
		document.getElementById("cancelButton").style.visibility = "hidden";
		intoSaveStyle();
		saveInfo();
	}
});
$("#cancelButton").click(function(){
	document.getElementById("editButton").style.visibility = "visible";
	document.getElementById("saveButton").style.visibility = "hidden";
	document.getElementById("cancelButton").style.visibility = "hidden";
	intoSaveStyle();
	$.post('${ContextPath}/InfoMaintain/disciplineInfo',
			function(data){
				$( "#content" ).empty();
				$( "#content" ).append( data );
			}, 
	'html');
	
});
/*保存当前用户的修改信息--by刘琪*/ 
function saveInfo(){
	var fmstr = $("form").serialize();
	if(!$(".infofr_form").valid())
		return false;
	else{
		$.ajax({
			type:'POST',
			url:'${ContextPath}/InfoMaintain/disciplineInfosaveEditSelf',
			data:fmstr,
			success: function(data){
	   				if(data){
	   					alert_dialog("保存成功！");
	   				}
	   				else
   		    		{
	      	   			alert_dialog("保存失败！");	
   		    		}
			}
		});
	}
}
</script>