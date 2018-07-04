<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">教师成果管理</span>
			</td>
		</tr>
	</table>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">学校代码：</span>
			</td>
			<td>
				<input type="text" id="unit_Id" value='${unitId}' disabled/>
			</td>
			<td>
				<span class="TextFont">学科代码：</span>
			</td>
			<td>
				<input type="text" id="disc_Id" value='${discId}'/>
			</td>
			<!-- <td>
				<span class="TextFont">教师用户名：</span>
			</td>
			<td>
				<input type="text" id="login_Id"/>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<a id="search_t_collect" class="button" href="#"><span class="icon icon-search"></span>查询</a>
			</td> -->
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<a id="search_t_collect" class="button" href="#"><span class="icon icon-search"></span>查询</a>
			</td>
		</tr>
	</table>
	<table class="layout_table right">
	</table>
</div>
<div class="layout_holder">
	<jsp:include page="/CollectTMeta/collect_t_tree.jsp"></jsp:include>
	<jsp:include page="/CollectTMeta/collect_t_view.jsp"></jsp:include>
</div>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script type="text/javascript">
function searchTList(){
	$('#jq_collect_parent').hide();
}
$(document).ready(function(){
	$("#collect_sort").hide();
	$("#search_t_collect").click(function(){
		searchTList();
	});
});
</script>