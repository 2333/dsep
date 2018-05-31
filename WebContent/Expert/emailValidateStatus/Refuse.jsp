<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>学科自检平台</title>
<link rel="stylesheet" type="text/css" href="${ContextPath}/css/index.css" />
<link rel="stylesheet" type="text/css" href="${ContextPath}/css/layout.css" />
<link rel="stylesheet" type="text/css" href="${ContextPath}/css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" href="${ContextPath}/css/icons.css"/>
<link rel="stylesheet" type="text/css" href="${ContextPath}/css/Form.css"/>
<link rel="stylesheet" type="text/css" href="${ContextPath}/css/Font.css"/>
<link rel="stylesheet" type="text/css" href="${ContextPath}/css/zTreeStyle/zTreeStyle.css">
<link href="${ContextPath}/css/redmond/jquery-ui-1.10.3.custom.css" rel="stylesheet">
<link href="${ContextPath}/css/zTreeStyle/zTreeStyle.css" rel="stylesheet">
<script src="${ContextPath}/js/jquery-1.9.1.js"></script>
<script src="${ContextPath}/js/jquery-ui-1.10.3.custom.js"></script>
<script src="${ContextPath}/js/jquery.jqGrid.min.js"></script>
<script src="${ContextPath}/js/grid.locale-cn.js"></script>
<script src="${ContextPath}/js/jqDnR.js"></script>
<script src="${ContextPath}/js/jqModal.js"></script>
<script src="${ContextPath}/js/common.js"></script>
<script src="${ContextPath}/js/index.jquery.js"></script>
<script src="${ContextPath}/js/jquery.ztree.core-3.5.min.js"></script>
<script src="${ContextPath}/js/jquery.ztree.excheck-3.5.min.js"></script>
<script src="${ContextPath}/js/jquery.validate.min.js"></script>
<script src="${ContextPath}/js/query.info.js"></script>
<script src="${ContextPath}/js/common_validate.js"></script>
</head>
<div id="header">
	<div id="logo"></div>
	<div id="status">
		<table class="right">
			<tr>
				<td align="right" style="height:20px;">
					<span>
						欢迎您，<span class="highlight_info">${userSession.name}！</span>|
						<a href="${ContextPath}/rbac/logout" class="alert_info">安全退出</a>
					</span>
				</td>	   
			</tr>
			<tr>
				<td align="right" style="height:20px;">上次登录时间：${userSession.loginTime}；上次登录IP：${userSession.loginIp}</td>
			</tr>
		</table>
	</div>
</div>
<style type="text/css">
	textarea {
		-moz-box-shadow:1px 1px 0 #E7E7E7;
		-moz-box-sizing:border-box;
		border-color:#CCCCCC #999999 #999999 #CCCCCC;
		border-style:solid;
		border-width:1px;
		font-family:arial,sans-serif;
		font-size:13px;
		height:500px;
		margin:30px 40px 0;
		outline-color:-moz-use-text-color;
		outline-style:none;
		outline-width:medium;
		padding:2px;
		width:70%;
	}
</style>
<input id="expertId" type="hidden" value="${expertId}">
<textarea onblur="f1();" onfocus="f2();">
请您在此输入拒绝参评的理由。
</textarea>
<br>
<input type="button" id="submit" value="提交">
<script type="text/javascript">
function f1() {
	if($("textarea").val()=='') {
		$("textarea").val('请您在此输入拒绝参评的理由。');
	}
}

function f2() {
	if($("textarea").val()=='请您在此输入拒绝参评的理由。') {
		$("textarea").val('');
	}
}
$("#submit").click(function() {
	
	var expertId = $("#expertId").val();
	var content = $("textarea").val();
	alert(expertId + " " + content);
	var reason={"expertId":expertId,"content":content};  
	$.ajax({ 
	    type : "POST", 
	    //url : "${ContextPath}/expert/expertRefuseReason", 
	    dataType : "json",      
	    contentType : "application/json",               
	    data : JSON.stringify(reason), 
	    success:function(data){ 
	    } 
	 }); 
});

</script>
<div id="footer">
	<p>@北京航空航天大学软件学院</p>
</div>



