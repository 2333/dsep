<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id='jqgird_oper' class="inner_table_holder">
	<table id="collect_oper_bts" class="editItem_tb left collect_oper_bts">
		<tr>
			<td><a id="collect_batch_add" class="button" href="#" style="display:none"> <span
					class="icon icon-add"></span>批量添加
			</a> 
		    <a id="collect_batch_del" class="button" href="#" style="display:none"> <span
					class="icon icon-del"></span>批量删除
			</a><!-- 此按钮功能在本页面实现-->
			<a
				id="collect_sort" class="button" href="#" style="display:none"> <span
					class="icon icon-store"></span>排序
			</a>
			<a id="collect_pubData" class="button" href="#" style="display:none"> <span
					class="icon icon-store"></span>导入公共库数据
			</a>
			</td>
			<td>
			</td>

		</tr>
	</table>
	<table class="left" >
		<tr>
			<td><a id="collect_Mul_Search" class="button" href="#" style="display:none"> <span
					class="icon icon-search"></span>查询
			</a></td>
		</tr>
	</table>
	<c:if test="${DomainId== 'D201301'}">
		<table class="left">
			<tr>
				<td><a id="tAchieveToDisc" class="button" href="#"
					style="display:none"> <span class="icon icon-import"></span>查看教师成果
				</a></td>
			</tr>
		</table>
	</c:if>
	
	<table class="right collect_oper_bts" id="collect_oper_bts2">
		<tr>
			<td><a id="excel_import" class="button" href="#" style="display:none"><span
					class="icon icon-import" ></span>导入</a></td>
		</tr>
	</table>
	<table id="tb_export" class="right">
		<tr>
			<td><a id="excel_baseSearch_output" class="button" href="#" style="display:none"><span
					class="icon icon-export"></span>根据查询导出</a></td>
			<td><a id="excel_output" class="button" href="#" style="display:none"><span
					class="icon icon-export"></span>全部导出</a></td>
		</tr>
	</table>
</div>
<div id = "dialog_confirm">
</div>
<script type="text/javascript">
//查询功能
function searchCollectItem(viewType){
	var itemId='';
	if(viewType=='JQGRID'){
		itemId='jqGrid_collect_tb';
	}else{
		itemId='jqGrid_collect_fm';
	}
	isAllData=false;
	$("#"+itemId).jqGrid('searchGrid', {
		modal:true,
		caption: "查找",  
        Find: "查询",  
        closeAfterSearch: true,  
        multipleSearch: true,  
        multipleGroup: true,
       // showQuery: true,
        groupOps: [{ op: "AND", text: "全部" },{ op:"OR",text: "任何"}],
	});
}
//教师成果导入学科
 function tAchieveToDisc(){
	openTCImport2Disc(tableId,discId);//该函数在TAchieve2Disc.jsp页面,变量的定义在collect_view.jsp页面
} 
//不公示功能
function unPublic(viewType){
	var itemId='';
	console.log(viewType);
	if(viewType=='JQGRID'){
		itemId='jqGrid_collect_tb';
	}else{
		itemId='jqGrid_collect_fm';
	}
	console.log(itemId);
	var ids=$('#'+itemId).jqGrid('getGridParam','selarrrow');
	console.log(ids);
}
	$(document).ready(function(){
		$("#collect_Mul_Search").click(function(){
			console.log('search!');
			searchCollectItem(viewType);
		});
		$("#unpublic").click(function(){
			unPublic(viewType);
		});
		$("#tAchieveToDisc").click(function(){
			tAchieveToDisc();
		});
		$("#collect_batch_del").click(function(){
			batchDelItems(viewType);
			/* $("#dialog_confirm").empty().append("<p>你确定要删除吗？</p>");
			$("#dialog_confirm").dialog({
				height:150,
				buttons:{
					"确定":function(){
						batchDelItems(viewType);
						$(this).dialog("close");
					},
					"取消":function(){
						$(this).dialog("close");
					}
				}
			}); */
		});
		$("#collect_pubData").click(function(){
			if(records>0){
				$("#dialog_confirm").empty().append("<p>导入后会覆盖掉当前采集的数据，你确定导入公共库数据吗？</p>");
			}else{
				$("#dialog_confirm").empty().append("<p>你确定导入公共库数据吗？</p>");
			}
			$("#dialog_confirm").dialog({
				height:150,
				buttons:{
					"确定":function(){
						importPubData(viewType);//collect_oper.js
						$(this).dialog("close");
					},
					"取消":function(){
						$(this).dialog("close");
					}
				}
			});
		})
	});
</script>