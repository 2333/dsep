<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="icon icon-user"></span>教师成果录入
			</td>
		</tr>
	</table>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">教师用户名：</span>
			</td>
			<td>
				<input type="text" id="login_Id" value='${user.loginId}' disabled/>
			</td>
		</tr>
	</table>
	<table class="layout_table right">
	</table>
</div>
<div class="layout_holder">
	<jsp:include page="/CollectTMeta/collect_t_tree.jsp"></jsp:include>
	<jsp:include page="/CollectTMeta/collect_t_view.jsp"></jsp:include>
	<div id = "jq_collect_notice" class="selectbar right_block">
<div class="comment">
	<span class="ui-icon ui-icon-info" style="float: left; margin-right: .10em;position:absolute"></span> 
	<p style="margin-left:0px;">提示:如需添加信息，请选择左侧列表中相应类别！</p>
</div>
</div>
</div>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script type="text/javascript">
</script>