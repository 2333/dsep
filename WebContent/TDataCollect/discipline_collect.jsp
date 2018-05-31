<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="icon icon-search" ></span>
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
				<input type="text" id="unit_Id" value='${user.unitId}' disabled/>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<span class="TextFont">学科代码：</span>
			</td>
			<td>
				<input type="text" id="disc_Id" value='${user.discId}' disabled/>
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
			<td>&nbsp;&nbsp;&nbsp;</td>
		    <td>
		     	<a id="discMainInfo" class="button" href="#">
			         		<span class="icon icon-edit"></span>学科概要信息</a>
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
	var loginId = $("#login_Id").val();
	jQuery("#jqGrid_collect_tb").setGridParam({
		url:'${ContextPath}/TCollect/toTCollect/CollectData/'
			+'collectionTData/'+tableId+'?unitId=${user.unitId}&discId=${user.discId}&loginId='+loginId,
		}).trigger("reloadGrid");
}
$(document).ready(function(){
	$("#collect_sort").hide();
	$("#search_t_collect").click(function(){
		
	});
	$("#discMainInfo").click(function(){
		openDiscMainDialog();//该方法在CollectTMeta/discMainInfo.jsp
	});
});
</script>