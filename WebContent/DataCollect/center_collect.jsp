<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header inner_table_holder">
	<h3 class="layout_table left">
		<span class="icon icon-file"></span>${textConfiguration.discResultQuery}
	</h3>
	<table class="layout_table right">
		<tr>
			<td>
				<span class="TextFont">数据状态：</span>
			</td>
			<td>
				<label id="collect_state" for="collect_state" style="font-size:14px;font-weight:bold;color:#2e6e9e;"></label>
			</td>
		</tr>
	</table>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">学校编号：</span>
			</td>
			<td>
				<input type="text" id="unit_Id" value='${unitId}'/>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<span class="TextFont">${textConfiguration.discNumber}：</span>
			</td>
			<td>
				<input type="text" id="disc_Id" value='${discId}'/>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<a id="search_collect" class="button" href="#"><span class="icon icon-search"></span>查询</a>
			</td>
		</tr>
	</table>
	<table class="layout_table right">
		<tr>
 			 <!-- <td>
				<a  id="download_brief_report_btn" class="button" href="#"><span class="icon icon-download"></span>下载简况表</a>
			 </td>	 -->	
		</tr>
</table>
</div>
<div>
	<jsp:include page="/CollectMeta/collect_tree.jsp"></jsp:include>
	<jsp:include page="/CollectMeta/collect_view.jsp"></jsp:include>
</div>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script src="${ContextPath}/js/download/briefsheet.js"></script>
<script type="text/javascript">
/**
 * 采集状态
 */
 var domainId="${configurations.domainId}";
function collectState(unitId,dicsId)
{
	if(unitId!=""&&discId!=""){
		$.commonRequest({
			url:'${ContextPath}/Collect/toCollect/collectState/'+unitId+'/'+dicsId,
			dataType:'text',
			success:function(data){
				data=convertStatus(data,domainId);
				$("#collect_state").html(data);
				}
			});
	}	
}
/**
*按照学校学科进行查询
*/
function searchCollect(unitId,discId)
{
	if(unitId!=""&&discId!=""){
		$.commonRequest({
			url:'${ContextPath}/Collect/toCollect/TreeConfig/initCollectTree/'+discId,
			dataType:'json',
			success: renderCollectTree
		});
		$("#left_menu").show();
		$("#jq_collect_parent").hide();
		isEditable_fun(unitId,discId);
	}else{
		$("#left_menu").hide();
		alert_dialog('学校和学科代码不能为空！');
	}
}
	$(document).ready(function(){
		unitId=$("#unit_Id").val();
		discId=$("#disc_Id").val();
		collectState(unitId,discId);
		$("#search_collect").click(function(){
			unitId=$("#unit_Id").val();
			discId=$("#disc_Id").val();
			searchCollect(unitId,discId);
			collectState(unitId,discId);
		});
	});
</script>