<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script src="${ContextPath}/js/download/briefsheet.js"></script>
<script src="${ContextPath}/js/fileOper/filename_oper.js"></script>
<div class="con_header">
	<h3>
		<span class="icon icon-search"></span>逻辑检查
	</h3>
</div>


<div class="selectbar inner_table_holder">
	<div class="left">
		<a id="logic_check_btn" class="button" href="#">
			<span class="icon icon-search"></span>开始检查
		</a> 
		<a id="logic_check_showResult" class="button" href="#">
			<span class="icon icon-search"></span>查看最新结果
		</a>	
	</div>

	<div id="logic_check_rule_div" class="right">
		<a id="exportErrorAndWarnData" class="button" href="#">
			<span class="icon icon-export"></span>导出检查结果
		</a>		
		<a id="logic_check_rule" class="button" href="#">
			<span class="icon icon-explanation"></span>逻辑检查规则下载
		</a>
	</div>
</div>



<div id="logic_check_config" class="logic_left_block">
	<table id="logic_config_tb"></table>
	<div id="logic_pager_tb"></div>
</div>
<div class="logic_right_block">
	<div class="radiobutton_div">
		<table id="resultGroups">
		</table>
	</div>

	<div id="logic_check_result" class="tabs">
		<ul>
			<li><a id="showerror" href="#logic_error">错误信息</a></li>
			<li><a id="showwarn" href="#logic_warn">警告信息</a></li>
		</ul>
		<div id="logic_error" class="scroll-x">
			<table id="logic_result_tb"></table>
			<div id="logic_result_pager"></div>
		</div>
		<div id="logic_warn">
			<table id="result_warn_tb"></table>
			<div id="result_warn_pager"></div>
		</div>
	</div>

</div>



<c:if test="${DomainId== 'D201301'}">
	<div class="logic_check_div">
		<span class="ui-icon ui-icon-info"
			style="float: left; margin-right: .3em;"></span> 数据核查规则说明:
		<div id="logic_check_result" class="tabs">
			<ul>
				<li><a id="" href="#logic_error">专业学位学生与导师情况</a></li>
				<li><a id="" href="#logic_warn">校内导师结构</a></li>
				<li><a id="" href="#logic_warn">具有较强实践指导能力的校内导师</a></li>
				<li><a id="" href="#logic_warn">具有较强实践能力的校外导师</a></li>
				<li><a id="" href="#logic_warn">校外专家开设课程</a></li>
				<li><a id="" href="#logic_warn">校外专家讲座</a></li>
				<li><a id="" href="#logic_warn">实习实践基地</a></li>
				<li><a id="" href="#logic_warn">临床教学资源</a></li>
				<li><a id="" href="#logic_warn">学生赴境外交流</a></li>
				<li><a id="" href="#logic_warn">境外学生交流情况</a></li>
				<li><a id="" href="#logic_warn">学生代表性成果</a></li>
				<li><a id="" href="#logic_warn">学生比赛获奖</a></li>
				<li><a id="" href="#logic_warn">获职业资格证书情况</a></li>
			</ul>
			<div id="logic_error">
				<p>1.必填字段为空。</p>
				<p>2.电话（手机、固定电话）格式不对。</p>
				<p>3.电子邮件格式不合理。</p>
			</div>
			<div id="logic_warn">
				<p>4.身份证号码格式不合理。</p>
				<p>5.最大值、最小值不合理。</p>
				<p>6.数字填写不规范。</p>
			</div>
		</div>
	</div>
</c:if>
<c:if test="${DomainId== 'D201401'}">
	<div class="logic_check_div">
		<span class="ui-icon ui-icon-info"
			style="float: left; margin-right: .3em;"></span> 数据核查规则说明:
		<div id="logic_check_result" class="tabs">
			<ul>
				<li><a id="showerror" href="#logic_error">错误信息</a></li>
				<li><a id="showwarn" href="#logic_warn">警告信息</a></li>
			</ul>
			<div id="logic_error">
				<p>1.必填字段为空。</p>
				<p>2.电话（手机、固定电话）格式不对。</p>
				<p>3.电子邮件格式不合理。</p>
			</div>
			<div id="logic_warn">
				<p>4.身份证号码格式不合理。</p>
				<p>5.最大值、最小值不合理。</p>
				<p>6.数字填写不规范。</p>
			</div>
		</div>
	</div>
</c:if>


<div class="logic_checking">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 后台逻辑检查已经开始，请稍等...
</div>

<div id="logic_check_download">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 正在进行下载处理
</div>

<script type="text/javascript">
var templateId  = "templateId";
	//线性处理异步请求的标志量
	var needLoadWarn = false;
	var needLoadWarnEntityId = "";
	var contextPath = "${ContextPath}";
	$(document).ready(function() {
		$(".logic_check_div").hide();//逻辑检查规则div
		$(".logic_checking").hide();
		$("#logic_check_download").hide();
		$("input[type=submit], a.button , button").button();
		$(".tabs").tabs({
			beforeLoad : function(event, ui) {
				event.preventDefault();
				return;
			},
			create : function(event, ui) {
				event.preventDefault();
				return;
			},
			load : function(event, ui) {
				event.preventDefault();
				return;
			}
		});
		$("#exportErrorAndWarnData").click(function(){
			var url = '${ContextPath}/check/disclogiccheck_exportDiscWrongAndWarnData/';
			outputJS(url);
		});
		$('#logic_config_tb').jqGrid({
			//url : '${ContextPath}/check/disclogiccheck_getEntityList/',
			//datatype : 'json',
			datatype : 'local',
			mtype : 'GET',
			colNames : [ 'ID', '学校ID', '学科ID','表ID', '采集项', '检查用户','检查时间', '结论', '是否通过' ],
			colModel : [
				{name : 'ID',           index : 'ID',             width : 60,  align : "center", hidden : true},
				{name : 'UNIT_ID',      index : 'UNIT_ID',        width : 100, align : "center", hidden : true},
				{name : 'discId',       index : 'disc_Id',         width : 100, align : "center", hidden : true},
				{name : 'entityId',     index : 'entity_Id',       width : 100, align : "center", hidden : true},
				{name : 'entityChsName',index : 'ENTITY_CHS_NAME',width : 200, align : "center", sortable:false, editable : true},
				{name : 'USER_ID',      index : 'USER_ID',        width : 200, align : "center", sortable:false, editable : true,   hidden : true},
				{name : 'CHECK_DATE',   index : 'CHECK_DATE',     width : 100, align : "center", formatter : 'date',  sorttype : 'date', hidden : true, search : false,
				 editrules : { date : true },editable : false,    formatoptions : { srcformat : 'Y-m-d H:i:s', newformat : 'Y-m-d '}, datefmt : 'Y-m-d '},
				{name : 'CONCLUSION',   index : 'CONCLUSION',     width : 200, align : "center", sortable:false, editable : true,   hidden : true},
				{name : 'passInfo',     index : 'HAS_ERROR',      width : 80, align : "center", sortable:false, editable : true,
				 cellattr : function(rowId, val, rawObject, cm, rdata) {
				 	if (val == "通过") {
						return "style='color:green'";
					} else if (val == "错误" || val == "错误、警告") {
						return "style='color:red'";
					} else {
						return "style='color:orange'";
					}
				}}],
			height : '100%',
			autowidth : true,
			pager : '#logic_pager_tb',
			rowNum : 20,
			rowList : [ 10, 20, 30 ],
			viewrecords : true,
			sortable : false,
			sortname : 'HAS_ERROR,HAS_WARN',
			sortorder : 'asc',
			caption : "采集项配置表 ",
			jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
				root : 'rows', //包含实际数据的数组  
				page : 'pageIndex', //当前页  
				total : 'totalPage',//总页数  
				records : 'totalCount', //查询出的记录数  
				repeatitems : false
			},
			onSelectRow : function(id) {
				$("#logic_result_tb").setGridParam({ page: 1 });
				$("#result_warn_tb").setGridParam({ page: 1 });
				var rowData = $('#logic_config_tb').jqGrid("getRowData", id);
				var entityId = rowData["entityId"];
				var passed = rowData["passInfo"];

				if (passed == '错误') {
					$("#logic_check_result").show();
					$("#showerror").show();
					$("#resultGroups").empty();
                    $("#logic_result_tb")
                    	.setGridParam({url : '${ContextPath}/check/disclogiccheck_getCheckResultList?entityId=' + entityId, datatype:'json'})
						.trigger("reloadGrid");
					//只有错误信息，隐藏警告tab
					$("#showwarn").hide();
					$("#showerror").click();
				} else if (passed == '警告') {
					$("#logic_check_result").show();
					$("#showwarn").show();
					$("#resultGroups").empty();
					$("#result_warn_tb")
						.setGridParam({url : '${ContextPath}/check/disclogiccheck_getCheckWarnList?entityId=' + entityId + "&onlyGetWarn=yes", datatype:'json'})
						.trigger("reloadGrid");
					//只有警告信息，隐藏错误tab
					$("#showerror").hide();
					$("#showwarn").click();
				} else if (passed == '通过') {
					$("#logic_check_result").hide();
					$("#resultGroups").empty();
					$("#resultGroups").html("<h3 style='color:green;'>该组通过检查！</h3>");
					$("#repeat_result_tb").jqGrid('GridUnload');
				} else if (passed == '错误、警告') {
					$("#logic_check_result").show();
					$("#showerror").show();
					$("#showwarn").show();
					$("#resultGroups").empty();
					// 将needLoadWarn设置true，会在加载完errorList之后再加载warnList
					// 加载完成后needLoadWarn回设成false
					needLoadWarn = true;
					needLoadWarnEntityId = entityId;
					//needLoadWarnDiscId = inputDiscId;
					$("#logic_result_tb")
						.setGridParam({url : '${ContextPath}/check/disclogiccheck_getCheckResultList?entityId=' + entityId, datatype:'json'})
						.trigger("reloadGrid");
					

				} else if (passed == '未检查') {
					alert_dialog("该实体未检查！！！");
				}
			},
			loadComplete : function() {
				passStyle();
			},
		}).navGrid('#logic_pager_tb', {edit : false,add : false,del : false,});
		$('#logic_result_tb').jqGrid({
			//url : '${ContextPath}/check/disclogiccheck_getCheckResultList/',
			//datatype : 'json',
			datatype : 'local',
			mtype : 'GET',
			colNames : [ 'ID', '学校ID', '学科ID','表ID', '采集项', '数据ID','原数据序号', '字段ID', '错误表字段','错误分类', '错误信息' ],
			colModel : [ {name : 'id',            index : 'id',            width : 60,  align : "center", hidden : true}, 
			             {name : 'unitId',        index : 'unit_Id',       width : 60,  align : "center", hidden : true}, 
			             {name : 'discId',        index : 'disc_Id',       width : 60,  align : "center", hidden : true}, 
			             {name : 'entityId',      index : 'entityId',      width : 60,  align : "center", hidden : true}, 
			             {name : 'entityChsName', index : 'entityChsName', width : 60,  align : "center", hidden : true}, 
			             {name : 'dataId',        index : 'dataId',        width : 30,  align : "center", sorttype : "String",hidden : true}, 
			             {name : 'seqNo',         index : 'seq_No',        width : 30,  align : "center", sorttype : "String"}, 
			             {name : 'attrId',        index : 'attrId',        width : 60,  align : "center", hidden : true}, 
			             {name : 'attrChsName',   index : 'attrChsName',   width : 60,  align : "center"}, 
			             {name : 'errorType',     index : 'errorType',     width : 50,  align : "center", sorttype : "String"}, 
			             {name : 'error',         index : 'error',         width : 100, align : "center", sorttype : "String"} 
			             ],
			height : '100%',
			autowidth : true,
			pager : '#logic_result_pager',
			rowNum : 20,
			rowList : [ 10, 20, 30 ],
			viewrecords : true,
			sortname : 'seq_no',
			sortorder : 'asc',
			caption : "逻辑检查结果表",
			jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
				root : 'rows', //包含实际数据的数组  
				page : 'pageIndex', //当前页  
				total : 'totalPage',//总页数  
				records : 'totalCount', //查询出的记录数  
				repeatitems : false
			},
			loadComplete : function() {
				//alert(needLoadWarn);
				if (needLoadWarn) {
					needLoadWarn = false;
					$("#result_warn_tb")
						.setGridParam({url : '${ContextPath}/check/disclogiccheck_getCheckWarnList?entityId=' + needLoadWarnEntityId + "&onlyGetWarn=yes", datatype:'json'})
						.trigger("reloadGrid");
					/* $("#result_warn_tb").setGridParam({
						url : '${ContextPath}/check/centerLogicCheck_getCheckWarnList?unitId='
							+ needLoadWarnUnitId + "&inputDiscId=" + needLoadWarnDiscId, datatype:'json'}).trigger("reloadGrid"); */
				}
			},
			/////////////////////////////////展示数据详情
			subGrid: true,
			subGridRowExpanded: function(subgrid_id, row_id) {
				
				var entityId = $('#logic_result_tb').getCell(row_id, 'entityId');
				var seqNo    = $('#logic_result_tb').getCell(row_id, 'seqNo');
				var unitId   = $('#logic_result_tb').getCell(row_id, 'unitId');
				var discId   = $('#logic_result_tb').getCell(row_id, 'discId');
				
				var subgrid_table_id;
				subgrid_table_id = subgrid_id+"_t";

				$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"'></table>");
				
				$.getJSON("${ContextPath}/check/disclogiccheck_DataConfig?entityId="+entityId, //获取某entity的元数据信息
					function initJqTable(data) {
						$("#"+subgrid_table_id).jqGrid({
		 					url :'${ContextPath}/check/disclogiccheck_getSubData?unitId='
		 							+ unitId + '&discId=' + discId + "&entityId=" 
		 							+ entityId + '&seqNo=' + seqNo,//取数据
							datatype : 'json',
							mtype : 'POST',
							colModel : data.colConfigs,
							height : "100%",
							autowidth : true,
							shrinkToFit : false,
							rowNum : 10,
							rowList : [ 10],
							viewrecords : true,
							sortname : data.defaultSortCol,
							sortorder : "asc",
							caption : data.name,
							jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
								root : "rows", //包含实际数据的数组  
								page : "pageIndex", //当前页  
								total : "totalPage",//总页数  
								records : "totalCount", //查询出的记录数  
								repeatitems : false,
							},
							prmNames : {
								page : "page",
								rows : "rows",
								sort : "sidx",
								order : "sord"
							}
						});
					});
			}
			/////////////////////////////////展示数据详情
			
		}).navGrid('#logic_result_pager', {edit : false,add : false,del : false});
		
		$('#result_warn_tb').jqGrid({
			//url : '${ContextPath}/check/disclogiccheck_getCheckWarnList/',
			//datatype : 'json',
			datatype : 'local',	
			mtype : 'GET',
			colNames : [ 'ID', '学校ID', '学科ID','实体ID', '采集项', '用户', '警告信息' ],
			colModel : [ {name : 'id',            index : 'id',            width : 60,  align : "center", hidden : true}, 
			             {name : 'unitId',        index : 'unitId',        width : 60,  align : "center", hidden : true}, 
			             {name : 'discId',        index : 'discId',        width : 100, align : "center", hidden : true}, 
			             {name : 'entityId',      index : 'entityId',      width : 100, align : "center", hidden : true}, 
			             {name : 'entityChsName', index : 'entityChsName', width : 100, align : "center"}, 
			             {name : 'userId',        index : 'userId',        width : 100, align : "center", hidden : true}, 
			             {name : 'conclusion',    index : 'conclusion',    width : 150, align : "center", align : "center", sorttype : "String", editable : true}, 
			           ],
			height : '100%',
			autowidth : true,
			pager : '#result_warn_pager',
			rowNum : 20,
			rowList : [ 10, 20, 30 ],
			viewrecords : true,
			sortname : 'ENTITY_ID',
			sortorder : 'asc',
			caption : "采集项检查警告表",
			jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
				root : 'rows', //包含实际数据的数组  
				page : 'pageIndex', //当前页  
				total : 'totalPage',//总页数  
				records : 'totalCount', //查询出的记录数  
				repeatitems : false
			},
		}).navGrid('#result_warn_pager', {edit : false,add : false,del : false});
			$("#result_warn_tb").setGridWidth($("#logic_check_result").width());
		});

	$("#logic_check_btn").click(function() {
		$(".logic_checking").show();
		$('.logic_checking').dialog({
			position : 'center',
			modal:true,
			autoOpen : true,
		});
		$.post('${ContextPath}/check/disclogiccheck_startLogicCheck',function(data) {
			if (data) {
				$(".logic_checking").dialog("destroy");
				$(".logic_checking").hide();
				$("#logic_config_tb")
				 .setGridParam({url : '${ContextPath}/check/disclogiccheck_getEntityList/', datatype:'json'})
				   .trigger("reloadGrid");
				/* $("#result_warn_tb").setGridParam({url : '${ContextPath}/check/disclogiccheck_getCheckWarnList/', datatype:'json'})
				   .trigger("reloadGrid");
				$("#logic_result_tb").setGridParam({url : '${ContextPath}/check/disclogiccheck_getCheckResultList/', datatype:'json'})
				   .trigger("reloadGrid"); */
			} else {
				alert_dialog("请求失败，请重试！");
			}
		});
	});
	
	$.ajax({
		type : 'POST',
		url : '${ContextPath}/check/disclogiccheck_whetherCheckComplete',
		success : function(data) {
			//alert(data);
			if (data == 'checked') {
				$('#logic_check_btn').show();
				$("#logic_config_tb")
					.setGridParam({url:'${ContextPath}/check/disclogiccheck_getEntityList/', datatype:'json'})
					.trigger("reloadGrid");
			} else if (data == 'checking') {
				//$('#logic_check_btn').hide();
				//alert_dialog("后台逻辑检查正在进行，请稍后查看检查结果!");
			} else {
				//alert_dialog("您还没有进行过逻辑检查，请点击开始检查按钮！");
			}
		},
	});
	
	$('#logic_check_showResult').click(function() {
		$("#logic_config_tb")
			.setGridParam({url:'${ContextPath}/check/disclogiccheck_getEntityList/', datatype:'json'})
			.trigger("reloadGrid");
		$("#showerror").show();
		$("#showerror").text("错误信息");
		$("#showwarn").show();
		$("#showwarn").text("警告信息");
		$("#result_label").text("点击左表显示具体检查结果");
		$("#logic_result_tb").jqGrid("clearGridData");
		$("#result_warn_tb").jqGrid("clearGridData");
	});

	$("#logic_check_rule").click(function() {
		var url = "${ContextPath}/check/disclogiccheck_downLoadTemplate/"+templateId;
		$.post(url,function(data){
				console.log('path: '+data);
				downloadProveMaterial(data);
		}, 'json');
		/* $(".logic_check_div").show();
		$('.logic_check_div').dialog({
			title : "逻辑检查规则说明",
			height : '400',
			width : '1200',
			position : 'center',
			modal : true,
			draggable : true,
			autoOpen : true,
			buttons : {
				"关闭" : function() {
					$(".logic_check_div").dialog("close");
					$(".logic_check_div").destroy();
				}
			}
		}); */
	});

	function passStyle() {
		var ids = jQuery("#logic_config_tb").jqGrid('getDataIDs');
		//alert(ids);
		for (var i = 0; i <= ids.length; i++) {
			var data = $('#logic_config_tb').getRowData(ids[i]); //取该行的值array
			//console.log(data);
			switch (data.PASSED) {
			case '通过':
				$('#logic_config_tb').setCell(ids[i], "PASSED", '通过', { color : 'green' });
				break;
			case '错误':
				$('#logic_config_tb').setCell(ids[i], "PASSED", '错误', { color : 'red' });
				break;
			case '警告':
				$('#logic_config_tb').setCell(ids[i], "PASSED", '警告', { color : '#00f' });
				break;
			case '未检查':
				$('#logic_config_tb').setCell(ids[i], "PASSED", '未检查', { color : 'blue' });
				break;
			}
		}
	}
</script>
