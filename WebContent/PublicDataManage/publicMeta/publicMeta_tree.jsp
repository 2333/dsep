<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script src="${ContextPath}/js/jquery.common.js"></script>
<div id="left_menu">
	<div id="ztreeConllection" class="fr_panel left_block">
		<ul id="public_tree" class="ztree"></ul>
	</div>
	<div id="hide_tree">
		<span class="icon icon-left" title="关闭/打开树形列表"></span>
	</div>
</div>
<script type="text/javascript">
//tree的隐藏
var tree_flag=true;

function hideTree()
{
	if (tree_flag) {
		OutTree();
		tree_flag = false;
	} else {
		InTree();
		tree_flag = true;
	}
}
/**
 * 移除tree
 */
function OutTree()
{
	$('#hide_tree').html('<span class="icon icon-right" title="关闭/打开树形列表"></span>');
	$('#jq_collect_parent').css(
			'margin-left', '17px');
	$("#ztreeConllection").hide(
			'slide', {}, 500);
	$("#hide_tree").animate({
		left : 0
	}, 500);
}
/**
 * 移入tree
 */
function InTree()
{
	$('#hide_tree').html('<span class="icon icon-left" title="关闭/打开树形列表"></span>');
	$("#ztreeConllection").show('slide', {}, 500);
    $("#hide_tree").animate(
			{
				left : 200
			},
			500,
			function() {
				$('#jq_collect_parent').css('margin-left','220px');
			});
}
/*tree需要的设置*/
var treeSetting = {
	data: {
		key: {
			title:"title"
		},
		simpleData: {
			enable: true
		}
	},
	callback: {
		onClick: treeOnClick
	}
};
function treeOnClick(event, treeId, treeNode)
{
	
	if(treeNode.children==null)
	{
		remark = treeNode.entityMemo;
		console.log(treeNode.value);
		var commonUrl="${ContextPath}/PublicDataManage/viewData/JqConfig/initCollectJqgrid/"+treeNode.value;
		var config={url:commonUrl,dataType:'json',success:renderJqGrid};
		$.commonRequest(config);
		$('#jq_collect_parent').show();
	}
	
}
function renderTree(data)
{
	var zNodes=data;
	$.fn.zTree.init($("#public_tree"), treeSetting,zNodes);//tree的实例化
}
$(document).ready(function(){
		$.commonRequest({
			url:'${ContextPath}/PublicDataManage/viewData/TreeConfig/initTree',
			dataType:'json',
			success: renderTree
		});
		$("#left_menu").show();
	$("#hide_tree").click(function(){
		hideTree();
	});
});
</script>