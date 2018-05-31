<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
 <div  class="con_header inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>  
				<h3>
					<span class="icon icon-web"></span>${textConfiguration.discPreManage}
				</h3>
 			</td>		
		</tr>
	</table>
	<jsp:include page="/MainFlow/mainFlowState.jsp"></jsp:include>
</div>

<div  class="con_header inner_table_holder">
<div id="unsubmitPre" hidden="true">
	<table  class="layout_table right" >
		<tr>
			<td>
			    <span class="TextFont">是否需要单位报告</span>
			    <select id="isUnitReport">
			    	<option value="uncheck">请选择</option>
			    	<option value="0">否</option>
			    	<option value="1">是</option>
			    </select>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>	
			<td>
			    <a id="submitUnitPre" class="button" href="#"><span class="icon icon-commit"></span>提交</a>
			</td>
		</tr>
	</table>
</div>
<div id="submitPre" hidden="true">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">预参评状态：</span>
			    <label  for="" class="TextFont">已提交至中心</label>
			</td>
		</tr>
	</table>
	<table  class="layout_table right" >
		<tr>
			<td>
				<span class="TextFont">单位报告：</span>
			    <label id="is_unit_report" for="is_unit_report" class="TextFont"></label>
			</td>	
			<!-- <td>&nbsp;&nbsp;&nbsp;</td>		
			<td>
			    <a id="import2Eval" class="button" href="#"><span class="icon icon-commit"></span>导入至参评管理</a>
			</td> -->
		</tr>
	</table>
</div>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
			    <span class="TextFont">${textConfiguration.discNumber}：</span>
			    <%-- <select id="disc_Id">
			    <c:if test="${!empty discMap}">
					<c:forEach items="${discMap}" var="item">
					<option value="${item.key}">
						${item.key}-${item.value}</option>
					</c:forEach>
				</c:if>
				</select> --%>
			   <input id="disc_Id" type="text" value=""/>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
			    <span class="TextFont">是否参评：</span>
			</td>
			<td>
				<select id="isEval" >
		      		<option value="all" selected>全部</option>
		      		<option value="0">否</option>
		      		<option value="1">是</option>
                </select>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
			    <span class="TextFont">是否需要报告：</span>
			</td>
			<td>
				<select id="isReport" >
		      		<option value="all" selected>全部</option>
		      		<option value="0">否</option>
		      		<option value="1">是</option>
                </select>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<a id="search_pre_disc" class="button" href="#"><span class="icon icon-search"></span>查询</a>
			</td>
		</tr>
	</table>
	<table class="layout_table right">
		<tr>
			<td>
				<a id="import_pre_disc" class="button" href="#" style="display:none"><span class="icon icon-import"></span>导入本校所有学科</a>
			</td>
			<td>
			    <a id="export_pre_disc" class="button" href="#" ><span class="icon icon-export"></span>导出本校所有学科</a>
			</td>
		</tr>
	</table>
</div>
<div class="selectbar layout_holder">
		<table id="school_pre_tb"></table>
		<div id="pager_schoolpre_tb"></div>
</div>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script type="text/javascript">

var lastsel=-1;
var totalCount;
var preState;//预参评状态（0：未提交；1：已提交）
var isImported;//是否已经从基础数据库导入到预参评表
var isReport;//是否订阅单位报告
var contextPath="${ContextPath}";//绝对路径
/**
 * 绑定select点击事件
 */
function bindSelect(id)
{
	var isEval=$("select[id$='"+id+"_preEval.isEval']").children('option:selected').val();
	if(isEval=='true')
	{
		$("select[id$='"+id+"_preEval.isReport']").prop('disabled',false);		
	}else{
		$("select[id$='"+id+"_preEval.isReport']").val('false');
		$("select[id$='"+id+"_preEval.isReport']").prop('disabled',true);
	}
	$("select[id$='preEval.isEval']").change(function(){
		isEval=$(this).children('option:selected').val();
		if(isEval=='true'){
			$("select[id$='"+id+"_preEval.isReport']").prop('disabled',false);
		   /*  var colModels= $("#school_pre_tb").getGridParam("colModel");
			colModels[7].editable = true; */
		}else{ 
			$("select[id$='"+id+"_preEval.isReport']").val('false');
			$("select[id$='"+id+"_preEval.isReport']").prop('disabled',true);
		} 
	}); 
}
/**
 * 与参评学科信息导入参评管理
 */
function import2Eval(unitId)
{
	$.commonRequest({
		url:'${ContextPath}/SchoolPreApply/PreGatherList_import2Eval?unitId='+unitId,
		dataType:'text',
		success:function(data){
			if(data=='success')
			{
				alert_dialog('导入成功！');
				isImported='1';
				controlImportBt(isImported);
			}else
				{
					alert_dialog(data);
				}
		}
	});			
}

function submitUnitPre(unitId,isUnitReport)
{
	$.commonRequest({
		url:'${ContextPath}/SchoolPreApply/PreGatherList_unitPre2Center?unitId='+unitId+"&isUnitReport="+isUnitReport,
		dataType:'text',
		success:function(data){
			if(data=='success')
			{
				alert_dialog('提交成功！');
				jQuery("#school_pre_tb").setGridParam({
					url:"${ContextPath}/SchoolPreApply/PreGatherList_PreGatherData?unitId="+unitId,
					sortorder: "asc",
					sortname:'id',
					page:1,
					rowNum:20,
					}).trigger("reloadGrid");
				preState = '1';//刷新预参评状态
				isReport = isUnitReport;//刷新单位报告订阅状态
			}else
				{
					alert_dialog(data);
				}
		}
	});	
	
}
//控制导入按钮的显隐
function controlImportBt(isImported){
	if(isImported=='1'){
		$("#import_pre_disc").hide();
	}else if(isImported=='0'){
		$("#import_pre_disc").show();
	}
}
/**
 * 动态修改操作栏
 */
function bindSubmitBar(isUnitReport,state,isImported)
{
	$("#unsubmitPre").hide();
	$("#submitPre").hide();
	if(state=='0'){
		$("#unsubmitPre").show();
	}	
	if(state=='1')
	{
		$("#submitPre").show();
		if(isUnitReport=="true"||isUnitReport=='1')
			$("#is_unit_report").html("已预订");
		else
			$("#is_unit_report").html("未预定");
	}	
}
/**
 * 查询
 */
function searchPreDisc(unitId,discId,isReport,isEval)
{
	jQuery("#school_pre_tb").setGridParam({
		url:"${ContextPath}/SchoolPreApply/PreGatherList_PreGatherData?unitId="+unitId+"&discId="+discId+"&isReport="+isReport+"&isEval="+isEval,
		sortorder: "asc",
		sortname:'id',
		page:1,
		}).trigger("reloadGrid"); 
}
/**
 * 从基础数据导入学科信息
 */
 function importPreDisc(unitId)
 {
	 $.commonRequest({
			url:'${ContextPath}/SchoolPreApply/PreGatherList_importBaseDisc?unitId='+unitId,
			dataType:'text',
			success:function(data){
				if(data=='success')
				{
					isImported= '1';
					jQuery("#school_pre_tb").setGridParam({
						url:"${ContextPath}/SchoolPreApply/PreGatherList_PreGatherData?unitId="+unitId,
						sortorder: "asc",
						sortname:'id',
						page:1,
						rowNum:20,
						}).trigger("reloadGrid"); 	
				}else
					{
						alert_dialog(data);
					}
			}
		});
 }
 /**
  * 导出本校所有学科预参评信息 
 */
 function exportPreDisc(unitId)
 {
	 var url='${ContextPath}/SchoolPreApply/PreGatherList_export/'+ unitId;
	 outputJS(url);
 }
function addPreDisc()
{
	//jQuery("#school_pre_tb").jqGrid('setColProp','preEval.id',{editable:false});
	jQuery("#school_pre_tb").jqGrid('addRow',{  
        	rowID : ++totalCount,   
            position :"last",  
            useDefValues :true,  
            useFormatter : true,  
        	initdata :{'preEval.unitId':'${unitId}'},
       }		
	);
	jQuery("#school_pre_tb").jqGrid('editRow',totalCount);
	var cancel = "<a class='' href='#' onclick='cancelPreInfo("+totalCount+")'>取消</a>"; 
	var save = "<a class='' href='#' onclick='savePreInfo("+totalCount+")'>保存</a>"; 
	jQuery("#school_pre_tb").jqGrid('setRowData',totalCount,{modifyInfo :save+" | "+cancel});
}
//修改操作
function modifyPreInfo(id)
{	
	jQuery("#school_pre_tb").jqGrid('editRow',id);
	var cancel = "<a class='' href='#' onclick='cancelPreInfo("+id+")'>取消</a>"; 
	var save = "<a class='' href='#' onclick='savePreInfo("+id+")'>保存</a>"; 
	jQuery("#school_pre_tb").jqGrid('setRowData',id,{modifyInfo :save+" | "+cancel});
	bindSelect(id);
	
}
//保存操作
function savePreInfo(id)
{
	jQuery("#school_pre_tb").jqGrid('saveRow',id,{
		aftersavefunc:function(data){
				if(data){
					var modify = "<a class='' href='#' onclick='modifyPreInfo("+id+")'>编辑</a>"; 
					jQuery("#school_pre_tb").jqGrid('setRowData',id,{modifyInfo :modify});
				}else{
					alert_dialog('保存失败！');
				}
			},
		successfunc:function(data){
			if(data.responseText=='success')
			{
				return true;		
			}else{
				alert_dialog('编辑失败！');
				cancelPreInfo(id);
				return false;
			}
		}
	});	
	
}
//取消操作
function cancelPreInfo(id){
	jQuery("#school_pre_tb").jqGrid('restoreRow',id);	
	var modify = "<a class='' href='#' onclick='modifyPreInfo("+id+")'>编辑</a>"; 
	jQuery("#school_pre_tb").jqGrid('setRowData',id,{modifyInfo :modify});
}
$(document).ready(function(){
	preState = "${state}";
	isImported="${isExist}";
	isReport = "${isUnitReport}";
	console.log(isReport);
	controlImportBt(isImported);
	$('#add_pre_disc').click(function(){
		addPreDisc();
	});
	$('#import_pre_disc').click(function(){
		var unitId=${unitId};
		importPreDisc(unitId);
	});
	$('#export_pre_disc').click(function(){
		var unitId=${unitId};
		exportPreDisc(unitId);
	});
	$("#search_pre_disc").click(function(){
		var unitId=${unitId};
		var discId=$("#disc_Id").val();
		var isReport=$("#isReport").val();
		var isEval= $("#isEval").val();
		searchPreDisc(unitId,discId,isReport,isEval);
		
	});
	$("#submitUnitPre").click(function(){
		var unitId=${unitId};
		var isUnitReport=$("#isUnitReport").val();
		if(isUnitReport=='uncheck')
		{
			alert_dialog('请选择是否需要报告！');
		}else{
			submitUnitPre(unitId,isUnitReport);
		}	
	});
	$("#import2Eval").click(function(){
		var unitId=${unitId};
		import2Eval(unitId);
	});
	common_Css();
	mainState();
	$("#school_pre_tb").jqGrid({
		url:"${ContextPath}/SchoolPreApply/PreGatherList_PreGatherData?unitId=${unitId}",
		editurl:"${ContextPath}/SchoolPreApply/PreGatherList_PreGatherEdit?unitId=${unitId}",
		datatype: "json",
		mtype:"post",
		colNames:['ID','状态','单位报告','学校代码','${textConfiguration.discNumber}','${textConfiguration.discName}','是否参评','是否需要报告',''],
		colModel:[
			{name:'preEval.id',index:'id',align:"center", width:100,editable:true,hidden:true},
			{name:'preEval.state',index:'id',align:"center", width:100,editable:true,hidden:true},
			{name:'preEval.isUnitReport',align:"center", width:80,editable:true,hidden:true},
			{name:'preEval.unitId',index:'unitId',align:"center", width:100,editable:true,hidden:true},
			{name:'preEval.discId',index:'discId',align:"center", width:100,editable:false},
			{name:'discName',index:'discName',align:"center", width:100,editable:false,sortable:false},
			{name:'preEval.isEval',align:"center", width:80,formatter:"select",edittype:"select",editoptions:{value:{"true":'是',"false":'否'}},editable:true,sortable:false},
			{name:'preEval.isReport',align:"center", width:80,formatter:"select",edittype:"select",editoptions:{value:{"true":'是',"false":'否'}},editable:true,sortable:false},
			{name:'modifyInfo',index:'modifyInfo',align:"center", width:80},
		],
		rownumbers: true,
		height:"100%",
		autowidth:true,
		pager: '#pager_schoolpre_tb',
		rowNum:20,
		rowList:[20,30,40],
		viewrecords: true,
		sortorder: "desc",
		sortname:'id',
		//multiselect: true,
		//multiboxonly: true,
		jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
            root: "rows",  //包含实际数据的数组  
            page: "pageIndex",  //当前页  
            total: "totalPage",//总页数  
            records:"totalCount", //查询出的记录数  
            repeatitems : false,
        },
		gridComplete: function(){
			var ids = jQuery("#school_pre_tb").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var modify = "<a class='' href='#' onclick='modifyPreInfo("+ids[i]+")'>编辑</a>"; 
				jQuery("#school_pre_tb").jqGrid('setRowData',ids[i],{modifyInfo :modify});
			};
			totalCount=$("#school_pre_tb").jqGrid('getGridParam','records');//当前总记录数
			if(ids.length>0)
			{
				var rowData = $('#school_pre_tb').jqGrid("getRowData", ids[0]);
				var isUnitReport=rowData['preEval.isUnitReport'];
				var state=rowData['preEval.state'];
				bindSubmitBar(isUnitReport,state);
				if(state=='1'){
					jQuery("#school_pre_tb").setGridParam().hideCol("modifyInfo");//隐藏编辑列
					$("#school_pre_tb").setGridWidth($(window).width()*0.99);
				}
					
			}else{
				console.log(isReport);
				bindSubmitBar(isReport,preState);
			}
			controlImportBt(isImported);
		},
		caption: "学科预信息汇总"
	}).navGrid('#pager_schoolpre_tb',{edit:false,add:false,del:false});	
	
});
</script>