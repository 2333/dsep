/*学科数据的状态*/
function collectDsepStatus(collegeId,disciplineId) {
	collegeId=$("#college").val();
	disciplineId=$("#dsep").val();
	console.log('collegeId is : '+collegeId+' disciplineId is : '+disciplineId);
		if (collegeId != null && collegeId != '' && collegeId != 'all')
			if (disciplineId != null && disciplineId != '' && disciplineId != 'all') {
				var commonUrl=requestUrl+"/Collection/getstatus/" + collegeId+ "/" + disciplineId;
				commonAjaxRequest(commonUrl,'text',setCollectStatus,error_function);
			}
}
/*设置采集信息的状态*/
function setCollectStatus(data)
{
	var status;
	console.log(data);
	switch (data) {
	case "0":
		status = "学科正在修改";
		break;
	case '1':
		status = "已提交至学校";
		break;
	case "2":
		status = "已提交至学位中心";
		break;
	case "3":
		status = "退回至学科";
		break;
	case "4":
		status = "退回至学校";
		break;
	case "5":
		status = "提交终止";
		break;
	}
	$("#disstatus").html(status);
}
/*通过学校级联学科*/
function cascade_discipline(collegeId) {
	var commonUrl=requestUrl+'/SchoolDisciplineList/CascadeDiscipline?cId='+collegeId;
	commonAjaxRequest(commonUrl,'json',setDisOptions,error_function);
	globalInfoControl();

}
/*设置学科 options*/
function setDisOptions(data)
{
	$("#dsep").empty();
	$("#dsep").append('<option value=all>' + '请选择学科' + '</option>');
	$.each(data, function(i, item) {
	if (disciplineId == i)
		$("#dsep").append('<option value="'+i+'" selected=selected>'+ item + '</option>');
	else
		$("#dsep").append('<option value="'+i+'">' + item+ '</option>');
	});
}
//向后台请求是否可以编辑表格
function editableCollectInfo() {
	var collegeId = $("#college").val();
	var disciplineId = $("#dsep").val();
		if (collegeId != 'all' &&collegeId!=''&&collegeId!=null&& disciplineId != 'all'&&disciplineId!=''&&disciplineId!=null) {
			var commonUrl=requestUrl+"/Collection/editAble/" + collegeId + "/" + disciplineId;
			commonAjaxRequest(commonUrl,'text',jqGird_control,error_function);
		}	
}
//向后台请求是否可以编辑表格
function editableCollectInfo_p(collegeId,disciplineId) {
		if ( disciplineId != 'all'&&disciplineId!=''&&disciplineId!=null) {
			var commonUrl=requestUrl+"/Collection/editAble/" + '10006' + "/" + disciplineId;
			commonAjaxRequest(commonUrl,'text',jqGird_control,error_function);
			collectDsepStatus('10006',disciplineId);
		}	
}
//显示页面元素,初始化tree,获取数据的编辑权限
function globalInfoControl()
{
	showElements();
	initTree();
	editableCollectInfo();
}
function globalInfoControl_p(collegeId,disciplineId)
{
	showElements_p(disciplineId);
	initTree();
	editableCollectInfo_p(collegeId,disciplineId);
}
/*打开备份的对话框*/
function addBackUpDialog(url) {
	$.post(url, function(data) {
		$('#dialog').empty().append(data);
		$('#dialog').dialog({
			title : "学科数据备份管理",
			height : '500',
			width : '720',
			position : 'center',
			modal : true,
			draggable : true,
			hide : 'fade',
			show : 'fade',
			autoOpen : true,
		}, 'html');
	});
}
/*初始化用户信息以及显示信息*/
function initUserInfo(userType) {
	if (userType == "1") {
		$("#submit2College").show();
		$("#college").attr("disabled", true);
		$("#dsep").attr("disabled", true);
		globalInfoControl();
	}
	if (userType == "2") {
		$("#college").attr("disabled", true);
		$("#functions_div").hide();
		var commonUrl=requestUrl+'/SchoolDisciplineList/addDiscipline';
		commonAjaxRequest(commonUrl,'json',setDisOptions,error_function);
		globalInfoControl();
	}
}
//设置学校
function setCollegeOptions(data)
{
	$("#college").empty();
	$("#college").append(
			'<option value=all>' + '请选择学校' + '</option>');
	$.each(data, function(i, item) {
		if (collegeId == i)
			$("#college").append(
					'<option value="'+i+'" selected=selected>'
							+ item + '</option>');
		else
			$("#college").append(
					'<option value="'+i+'">' + item
							+ '</option>');
	});
	if (collegeId != 'all' && collegeId!=''&&disciplineId != 'all'&&disciplineId!='') {
		console.log('collegeId is '+collegeId);
		cascade_discipline(collegeId);
		globalInfoControl();
	}
}
//控制显示的元素
function showElements()
{
	$("#ztreeConllection").hide();
	var disciplineId = $("#dsep").val();
	if(disciplineId!=''&&disciplineId!='all'&&disciplineId!=null){	
		$("#hide_tree").show();
		if(tree_flag)
			InTree();
		else
			OutTree();
		
	}else{
		$("#discipline_info").hide();
		$("#ztreeConllection").hide();
		$("#hide_tree").hide();
	}
	if(collect_flag)
		$("#discipline_info").show();
	else
		$("#discipline_info").hide();
	
}
function showElements_p(disciplineId)
{
	$("#ztreeConllection").hide();
	//var disciplineId = $("#dsep").val();
	if(disciplineId!=''&&disciplineId!='all'&&disciplineId!=null){	
		$("#hide_tree").show();
		if(tree_flag)
			InTree();
		else
			OutTree();
		
	}else{
		$("#discipline_info").hide();
		$("#ztreeConllection").hide();
		$("#hide_tree").hide();
	}
	if(collect_flag)
		$("#discipline_info").show();
	else
		$("#discipline_info").hide();
}

//窗体变化刷新jqgrid
function resize_jqgrid()
{
	$(window).resize(function() {
		$("#jqGrid_tb").setGridWidth($("#content").width());
	});
}
//提交到学校
function submit2College()
{
	var collegeId = $("#college").val();
	var disciplineId = $("#dsep").val();
	var commonUrl=requestUrl+"/FlowActions/discip2College/" + collegeId + "/" + disciplineId;
	commonAjaxRequest(commonUrl,'text',success_function,error_function);
	collectDsepStatus(collegeId,disciplineId);
}
//学科下拉框变动
function dsepChange()
{
	var disciplineId = $("#dsep").val();
	var collegeId = $("#college").val();
	if (collegeId == 'all')
		alert('请选择学校！');
	else if (disciplineId == 'all')
		alert('请选择学科！');
	else {
		collectDsepStatus(collegeId,disciplineId);
		globalInfoControl();
	}
}
//通用的ajax请求
function commonAjaxRequest(commonUrl,data_type,success_function,error_function)
{
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		dataType:data_type,
		url : commonUrl,
		error : function() {
			error_function();
		},
		success : function(data) {
			success_function(data);
		}
	});
}
function error_function()
{
	alert('请求失败！');
}
function success_function(data)
{
	if(data=='success')
	{
		alert('操作成功!');
		globalInfoControl();
	}else
		alert('操作失败！');
}
