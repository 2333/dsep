/*tree需要的设置*/
var collectSetting = {
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
	},
	
	check: {
		enable: true,
		autoCheckTrigger: true
	}
};

/*tree的点击事件*/
function treeOnClick(event, treeId, treeNode,clickFlag,mAjaxForJqgrid) {
	if(treeNode.children==null)
	{	
		myAjaxForJqgrid(treeNode.value);
		$("#discipline_info").show();
	}
}
/*请求tree的数据*/
function initTree() {
	var commonUrl= requestUrl+"/Collection/collectionTree";
	commonAjaxRequest(commonUrl,'json',renderTree,error_function);
}
/*请求教师tree的数据*/
function initTeacherTree() {
	var commonUrl= requestUrl+"/Collection/collectionTree?usertype=teacher";
	commonAjaxRequest(commonUrl,'json',renderTree,error_function);
}
/*渲染tree*/
function renderTree(data)
{
	zNodes=data;
	$.fn.zTree.init($("#dsep_tree"), collectSetting,zNodes);//tree的实例化
	
}
//tree的隐藏
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
function OutTree()
{
	$('#hide_tree').html('<span class="icon icon-right" title="关闭/打开树形列表"></span>');
	$('#discipline_info').css(
			'margin-left', '17px');
	$("#ztreeConllection").hide(
			'slide', {}, 500);
	$("#hide_tree").animate({
		left : 0
	}, 500);
}
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
				$('#discipline_info').css('margin-left','220px');
			});
}