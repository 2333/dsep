<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script src="${ContextPath}/js/jquery.common.js"></script>
<div id="left_menu">
	<div id="ztreeTConllection" class="fr_panel left_block">
		<ul id="collection_T_tree" class="ztree"></ul>
	</div>
	<div id="hide_tree">
	 <span class="icon icon-left" title="关闭/打开树形列表"></span>
	</div>
</div>
<script type="text/javascript">
//tree的隐藏
var tree_flag=true;
var isEditable='1';
function controlEdit(userType){
	if(userType=='4'){
		isEditable='1';
	}else{
		isEditable='0';
	}
}
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
	$("#ztreeTConllection").hide(
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
	$("#ztreeTConllection").show('slide', {}, 500);
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
		var commonUrl="${ContextPath}/TCollect/toTCollect/JqConfig/initCollectJqgrid/"+treeNode.value;
		var config={url:commonUrl,dataType:'json',success:renderTJqGrid};
		$.commonRequest(config);
		$('#jq_collect_parent').show();
		$('#jq_collect_notice').hide();
	}
	
}
function renderCollectTree(data)
{
	var zNodes=data;
	$.fn.zTree.init($("#collection_T_tree"), treeSetting,zNodes);//tree的实例化
}
$(document).ready(function(){
	$.commonRequest({
		url:'${ContextPath}/TCollect/toTCollect/TreeConfig/initTCollectTree/',
		dataType:'json',
		success: renderCollectTree
	});
	controlEdit("${user.userType}");
	$("#hide_tree").click(function(){
		hideTree();
	});
});
</script>