<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>进度维护
	</h3>
</div>
<div id="progress_tb" class="tabs">
	<input  id="user_type" hidden="true" value="${userSession.userType}">
	<ul id="tab_kinds">
		<!-- <li ><a id="show_result" class="objectLink" href="#result_div" >成果管理</a></li> -->
		<li ><a id="show_funding" class="objectLink" href="#funding_div">经费管理</a></li>
	</ul>
	<%-- <jsp:include page="/ProjectManagement/resultManage.jsp"></jsp:include> --%>
	<jsp:include page="/ProjectManagement/fundsManage.jsp"></jsp:include>
</div>

<script type="text/javascript">
$(document).ready(function(){

	$( 'input[type=submit], a.button , button' ).button();
	$(".tabs").tabs({
	beforeLoad : function(event, ui) {
		event.preventDefault();
		return;
	},
	create : function(event, ui) {
		event.preventDefault();
		return;
	}, 
	load : function(event, ui) {
		event.preventDefault();
		return;
	}
	});
});
</script>