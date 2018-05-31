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
				<input type="text" id="unit_Id" value='${unitId}' disabled/>
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
			 <td>
				<a  id="unit_edit_data" class="button" href="#" style="display:none;"><span class="icon icon-edit"></span>编辑</a>
			 </td>	
			 <td>
				<a  id="unit_confirm_data" class="button" href="#" style="display:none;"><span class="icon icon-confirm"></span>确认</a>
			 </td>	
 			<!--  <td>
				<a  id="download_brief_report_btn" class="button" href="#"><span class="icon icon-download"></span>下载简况表</a>
			 </td>		 -->
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
 var domainId="${configurations.domainId}"
function collectState(unitId,discId)
{
	if(unitId!=""&&discId!=""){
		$.commonRequest({
			url:'${ContextPath}/Collect/toCollect/collectState/'+unitId+'/'+discId,
			dataType:'text',
			success:function(data){
				data=convertStatus(data,domainId);
				var state = data;
				console.log(state);
				$("#collect_state").html(data);
				}
			});
	}	
}
	/**
	*学校编辑学科数据
	*/
	function unitEditData(unitId,dicsId){
		if(unitId!=''&&discId!=''){
			$.commonRequest({
				url:'${ContextPath}/Collect/toCollect/unitEditData/'+unitId+'/'+dicsId,
				dataType:'text',
				success:function(data){
					if(data=='success'){
						controlBtn('学校正在编辑');
						isEditable_fun(unitId,discId);//此函数在collect_tree.jsp中
						if(tableId==''){
							//searchCollect(unitId,discId);
						}else{
							if(viewType=='JQGRID')
								$("#jqGrid_collect_tb").jqGrid('setGridParam').trigger("reloadGrid");
							if(viewType=='JQFORM')
								$("#jqGrid_collect_fm").jqGrid('setGridParam').trigger("reloadGrid");
							if(viewType=='INITJQGRID'){
								$("#jqGrid_collect_initJq").jqGrid('setGridParam').trigger("reloadGrid");
							}
						}		
					}else if(data=='error'){
						alert_dialog("学校无编辑权限！");
					}else{
						alert_dialog(data);
					}
				}
			});
		}
	}
	/**
	*学校确认修改学科数据
	*/
	function unitConfirmData(unitId,discId){
		if(unitId!=''&&discId!=''){
			$.commonRequest({
				url:'${ContextPath}/Collect/toCollect/unitConfirmData/'+unitId+'/'+discId,
				dataType:'text',
				success:function(data){
					if(data=='success'){
						controlBtn('已提交至学校');
						isEditable_fun(unitId,discId);//此函数在collect_tree.jsp
						if(tableId==''){
							//searchCollect(unitId,discId);
						}else{
							if(viewType=='JQGRID')
								$("#jqGrid_collect_tb").jqGrid('setGridParam').trigger("reloadGrid");
							if(viewType=='JQFORM')
								$("#jqGrid_collect_fm").jqGrid('setGridParam').trigger("reloadGrid");
							if(viewType=='INITJQGRID'){
								$("#jqGrid_collect_initJq").jqGrid('setGridParam').trigger("reloadGrid");
							}
						}	
					}else if(data=='error'){
						alert_dialog("学校确认失败！");
					}else{
						alert_dialog(data);
					}
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
			alert_dialog('学校和学科代码不能为空！');
			$("#left_menu").hide();
		}
	}
	function controlBtn(state){
		console.log(state);
		if(state=='已提交至学校'||state=='学校撤销提交'){
			$("#unit_edit_data").show();
			$("#unit_confirm_data").hide();
		}
		if(state=='学校正在编辑'){
			$("#unit_edit_data").hide();
			$("#unit_confirm_data").show();
		}
	}
		$(document).ready(function(){
			unitId=$("#unit_Id").val();
			discId=$("#disc_Id").val();
			collectState(unitId,discId);
			controlBtn($("#collect_state").html());//根据数据状态显示按钮
			$("#search_collect").click(function(){
				unitId=$("#unit_Id").val();
				discId=$("#disc_Id").val();
				searchCollect(unitId,discId);
				collectState(unitId,discId);
				controlBtn($("#collect_state").html());
				
			});
			$("#unit_edit_data").click(function(){
				unitId=$("#unit_Id").val();
				discId=$("#disc_Id").val();
				unitEditData(unitId,discId);
			});
			$("#unit_confirm_data").click(function(){
				unitId=$("#unit_Id").val();
				discId=$("#disc_Id").val();
				unitConfirmData(unitId,discId);
			});
		});
</script>