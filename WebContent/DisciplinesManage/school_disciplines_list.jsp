<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="con_header inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="icon icon-disciplinecollect"></span>
				<span class="TextFont">${textConfiguration.discResultManage}</span>
			</td>
		</tr>
	</table>
	<jsp:include page="/MainFlow/mainFlowState.jsp"></jsp:include>
	<table class="layout_table right">
		<tr>
			<td>
				<span class="TextFont">学校当前版本号：</span>
			</td>
			<td>
                 <label id="unit_version_no" class="TextFont">${unitVersionNo}</label>
             </td>
		</tr>
	</table>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<input type="text" id="unit_Id" name="unitId" value="${unitId}" hidden="true"/>
			<td>
				<span class="TextFont">${textConfiguration.discNumber}：</span>
			</td>
			<td>
				<input type="text" id="disc_Id" name="discId" value="${discId}"/>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<span class="TextFont">采集状态：</span>
				<select id="state" >
		      		<option value="all" selected>全部</option>
		      		<option value="0">学科正在修改</option>
		      		<option value="1">已经提交至学校</option>
		      		<option value="2">提交至中心</option>
		      		<option value="3">退回至学科</option>
		      		<option value="4">学校撤销提交</option>
                </select>
                &nbsp;&nbsp;&nbsp;
                <span class="TextFont">是否参评：</span>
				<select id="isEval" >
		      		<option value="all" selected>全部</option>
		      		<option value="1">是</option>
		      		<option value="0">否</option>
                </select>
                &nbsp;&nbsp;&nbsp;
                <span class="TextFont">是否需要报告：</span>
				<select id="isReport" >
		      		<option value="all" selected>全部</option>
		      		<option value="1">是</option>
		      		<option value="0">否</option>
                </select>
                &nbsp;&nbsp;&nbsp;
                <a id="search_list" class="button" href="#"><span class="icon icon-search "></span>查找</a >
			</td>	
		</tr>
	</table>
	<table class="layout_table right">
		<tr>
			<td>
				<a  id="export_eval" class="button" href="#" ><span class="icon icon-export"></span>导出</a>
			 </td>
			<td>
				<a  id="submit2Center" class="button" href="#" style="dispaly:none"><span class="icon icon-commit"></span>提交</a>
			 </td>
			  <td>
				<a  id="repealFromCenter" class="button" href="#" style="display:none"><span class="icon icon-undo"></span>撤销提交</a>
			 </td>		
		</tr>
	</table>
</div>
<div class="selectbar layout_holder">
     <div class="table">
		<table id="schoolCol_tb"></table>
		<div id="pager_schoolCol_tb"></div>
	</div>
</div>
<div hidden="true">
	<div id="dialog-confirm" title="警告"></div>
</div>
<input type="text" id="path" hidden="true" value="${ContextPath}"/>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script src="${ContextPath}/js/download/briefsheet.js"></script>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script src="${ContextPath}/js/fileOper/filename_oper.js"></script>
<script type="text/javascript">
//var unitId=$("#unit_Id").val();
var unitId=${unitId};
var contextPath = $("#path").val();
var isEditable=0;
var evalState;//参评时数据的状态
/**
 * 控制提交、撤销按钮的显示与隐藏
 */
function controlSubmitAndRepeal(evalState){
	if(evalState=='2'){
		$("#submit2Center").hide();
		$("#repealFromCenter").show();
	}else if(evalState=='5'){
		$("#submit2Center").hide();
		$("#repealFromCenter").hide();
	}else{
		$("#submit2Center").show();
		$("#repealFromCenter").hide();
	}
}
/**
 * 是否可以编辑
 */
function isEditableEval(unitId){
	$.commonRequest({
		url:"${ContextPath}/SchoolDisciplineList/GatherList_isEditableEval?unitId="+unitId,
		dataType:'text',
		success:function(data){
			isEditable=data;
		},
		error:function(data){}
	});
}
/**
 * 下载简况表
 */
function downloadBrief(id){
	var rowData = $('#schoolCol_tb').jqGrid("getRowData", id);
	/* var unitId = rowData['eval.unitId'];
	var discId = rowData['eval.discId']; */
	var templateId = rowData['eval.briefId'];
	var url="${ContextPath}/SchoolDisciplineList/GatherList_download/"+templateId;
	$.commonRequest({
		url:url,
		dataType:'json',
		success:function(message){
			if(message.result=="success"){
				downloadProveMaterial(message.data);
			}
			else 
		    {
				alert_dialog("下载失败!");
		    }
		},
	complete:function(){
		
	}
	});
}
/**
 * 绑定select点击事件
 */
function bindSelect(id)
{
	var isEval=$("select[id$='"+id+"_eval.isEval']").children('option:selected').val();
	if(isEval=='true')
	{
		$("select[id$='"+id+"_eval.isReport']").prop('disabled',false);		
	}else{
		$("select[id$='"+id+"_eval.isReport']").val('false');
		$("select[id$='"+id+"_eval.isReport']").prop('disabled',true);
	}
	$("select[id$='eval.isEval']").change(function(){
		isEval=$(this).children('option:selected').val();
		if(isEval=='true'){
			$("select[id$='"+id+"_eval.isReport']").prop('disabled',false);
		   /*  var colModels= $("#school_pre_tb").getGridParam("colModel");
			colModels[7].editable = true; */
		}else{ 
			$("select[id$='"+id+"_eval.isReport']").val('false');
			$("select[id$='"+id+"_eval.isReport']").prop('disabled',true);
		} 
	}); 
}
/**
 * 编辑学科信息
 */
function editDiscInfo(id)
{
	jQuery("#schoolCol_tb").jqGrid('editRow',id);
	var cancel = "<a class='' href='#' onclick='cancelDiscInfo("+id+")'>取消</a>"; 
	var save = "<a class='' href='#' onclick='saveDiscInfo("+id+")'>保存</a>"; 
	jQuery("#schoolCol_tb").jqGrid('setRowData',id,{edit :save+" | "+cancel});	
	bindSelect(id);
}
function cancelDiscInfo(id)
{
	jQuery("#schoolCol_tb").jqGrid('restoreRow',id);	
	var modify = "<a class='' href='#' onclick='editDiscInfo("+id+")'>编辑</a>"; 
	jQuery("#schoolCol_tb").jqGrid('setRowData',id,{edit :modify});
}
function saveDiscInfo(id)
{
	jQuery("#schoolCol_tb").jqGrid('saveRow',id,{
		aftersavefunc:function(data){
				if(data){
					var rowData = $('#schoolCol_tb').jqGrid("getRowData", id);
					var state = rowData['eval.state'];
					var modify = "<a class='' href='#' onclick='editDiscInfo("+id+")'>编辑</a>"; 
					jQuery("#schoolCol_tb").jqGrid('setRowData',id,{edit :modify});
					//数据查看
					var view = "<a class='' href='#' onclick='viewDiscDetail("+id+")'>查看</a>"; 
					//退回学科
					var back = "<a class='' href='#'  onclick='back2Disc("+id+")'>退回学科</a>"; 
					//确认
					var confirm= "<a class='' href='#'  onclick='confirmDisc("+id+")'>确认编辑</a>";
					//下载
					var brief = "<a class='' href='#' onclick='downloadBrief("+id+")'>下载简况表</a>"; 
					var operBt = view+' | '+brief;
					if(state=='1'||state=='4'){
						operBt +=' | '+back;
					}
					if(state=='6'){
						operBt +=' | '+confirm;
					}
					jQuery("#schoolCol_tb").jqGrid('setRowData',id,{oper:operBt});
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
				cancelDiscInfo(id);
				return false;
			}
		}
	});		
}
/**
 * 获取版本号
 */
 function getVersion(unitId)
 {
	$.commonRequest({
		url:"${ContextPath}/Collect/collectVersion?unitId="+unitId,
		dataType:'text',
		success:function(data){
			$("#unit_version_no").html(data);
		},
		error:function(data){}
	});
 }
/**
 * 查询功能
 */
function searchList(unitId,discId,state,isEval,isReport)
{
	jQuery("#schoolCol_tb").setGridParam({
		url:"${ContextPath}/SchoolDisciplineList/GatherList_GatherData?"
			+"unitId="+unitId
			+"&discId="+discId
		 	+"&state="+state
		 	+"&isEval="+isEval
		 	+"&isReport="+isReport,
		sortorder: "asc",
		sortname:'ID',
		page:1,
		}).trigger("reloadGrid"); 
}
/**
 * 学校提交到中心
 */
function submit2Center(unitId)
{
	var state= $("#state").val();
	$.commonRequest({
		url:'${ContextPath}/FlowActions/unit2Center/'+unitId,
		dataType:'text',
		success:function(result){
			if(result=='success'){
				alert_dialog('提交成功！');
				evalState='2'
				jQuery("#schoolCol_tb").setGridParam({
					url:"${ContextPath}/SchoolDisciplineList/GatherList_GatherData?unitId="+unitId+"&state="+state,
					}).trigger("reloadGrid");  
			}else{
				alert_dialog(result);
			}
				
		}
	});
}
/**
 * 学校退回学科
 */
function back2Disc(id)
{
	$( "#dialog-confirm" ).empty().append("<p>你确定要退回学科吗？</p>");
	  $( "#dialog-confirm" ).dialog({
	      height:150,
	      buttons: {
	        "确定": function() {
	        	var state= $("#state").val();
	        	var rowData = $('#schoolCol_tb').jqGrid("getRowData", id);
	        	var unitId = rowData['eval.unitId'];
	        	var discId = rowData['eval.discId'];
	        	$.commonRequest({
	        		url:'${ContextPath}/FlowActions/unitback2Disc/'+unitId+"/"+discId,
	        		dataType:'text',
	        		success:function(result){
	        			if(result=='success'){
	        				alert_dialog('退回成功！');
	        				jQuery("#schoolCol_tb").setGridParam({
	        					url:"${ContextPath}/SchoolDisciplineList/GatherList_GatherData?unitId="+unitId+"&state="+state,
	        					}).trigger("reloadGrid"); 
	        			}else{
	        				alert_dialog('退回失败！');
	        			}
	        		}
	        	});
	        	$( this ).dialog( "close" );
	        },
	        "取消": function() {
	            $( this ).dialog( "close" );
	          }
	        }
	  });
	
}
/**
 * 学校撤回提交至中心的数据
 */
function repealFromCenter(unitId)
{
	var state= $("#state").val();
	$.commonRequest({
		url:'${ContextPath}/FlowActions/unitRepealFromCenter/'+unitId,
		dataType:'text',
		success:function(result){
			if(result=='success'){
				alert_dialog('撤销成功！');
				evalState='1';
				jQuery("#schoolCol_tb").setGridParam({
					url:"${ContextPath}/SchoolDisciplineList/GatherList_GatherData?unitId="+unitId+"&state="+state,
					}).trigger("reloadGrid"); 
			}else{
				alert_dialog('撤销失败！');
			}
		}
	});
}
function confirmDisc(id){
	var rowData = $('#schoolCol_tb').jqGrid("getRowData", id);
	var unitId = rowData['eval.unitId'];
	var discId = rowData['eval.discId'];
	if(unitId!=''&&discId!=''){
		$.commonRequest({
			url:'${ContextPath}/Collect/toCollect/unitConfirmData/'+unitId+'/'+discId,
			dataType:'text',
			success:function(data){
				if(data=='success'){
					var state= $("#state").val();
					var discId = $("#disc_Id").val();
					jQuery("#schoolCol_tb").setGridParam({
    					url:"${ContextPath}/SchoolDisciplineList/GatherList_GatherData"
    					+"?unitId="+unitId
    					+"&discId="+discId
    					+"&state="+state,
    					}).trigger("reloadGrid"); 
				}else if(data=='error'){
					alert_dialog("学校确认失败！");
				}else{
					alert_dialog(data);
				}
			}
		});
	}
}
//查看学科
function viewDiscDetail(id)
{
	 var rowData = $('#schoolCol_tb').jqGrid("getRowData", id);
	 var unitId=rowData['eval.unitId'];
	 var discId=rowData['eval.discId'];
	 var reUrl="${ContextPath}/Collect/toCollect?discId="+discId;
	 $.post(reUrl, function(data){
		  $( "#content" ).empty();
		  $( "#content" ).append( data );
	  }, 'html');
	event.preventDefault();
}

$(document).ready(function(){
	evalState="${evalState}";
	controlSubmitAndRepeal(evalState);
	mainState();
	/********页面的按钮点击事件*******/
	$("#submit2Center").click(function(){
		$( "#dialog-confirm" ).empty().append("<p>你确定要提交吗？</p>");
		  $( "#dialog-confirm" ).dialog({
    	      height:150,
    	      buttons: {
    	        "确定": function() {
    	        	submit2Center(unitId);
    	        	$( this ).dialog( "close" );
    	        },
    	        "取消": function() {
    	            $( this ).dialog( "close" );
    	          }
    	        }
    	  });
		
	});
	
	$("#export_eval").click(function(){
		var url='${ContextPath}/SchoolDisciplineList/GatherList_export/'+ unitId;
	    outputJS(url);
	});
	
	$("#repealFromCenter").click(function(){
		$( "#dialog-confirm" ).empty().append("<p>你确定要撤销提交吗？</p>");
		  $( "#dialog-confirm" ).dialog({
  	      height:150,
  	      buttons: {
  	        "确定": function() {
  	        	repealFromCenter(unitId);
  	        	$( this ).dialog( "close" );
  	        },
  	        "取消": function() {
  	            $( this ).dialog( "close" );
  	          }
  	        }
  	  });
		
	});
	$("#search_list").click(function(){
		var unitId=$("#unit_Id").val();
		var discId=$("#disc_Id").val();
		var state=$("#state").val();
		var isEval=$("#isEval").val();
		var isReport=$("#isReport").val()
		searchList(unitId,discId,state,isEval,isReport);
	});
	$( "input[type=submit], a.button , button" ).button();
	/********************  jqGrid ***********************/
	
	$("#schoolCol_tb").jqGrid({
		url:"${ContextPath}/SchoolDisciplineList/GatherList_GatherData?unitId="+unitId,
		editurl:"${ContextPath}/SchoolDisciplineList/GatherList_editData?unitId="+unitId,
		datatype: "json",
		mtype:"post",
		colNames:['ID','学校代码','${textConfiguration.discNumber}','${textConfiguration.discName}','状态','学科采集状态','学科当前版本号','是否参评','是否需要报告','','',''],
		colModel:[
			{name:'eval.id',index:'id',align:"center", width:100,hidden:true,editable:true},
			{name:'eval.unitId',index:'unitId',align:"center", width:100,hidden:true,editable:true},
			{name:'eval.discId',index:'discId',align:"center", width:100},
			{name:'discName',index:'discName',align:"center",width:100,sortable:false},
			{name:'eval.state',align:"center", width:100,sortable:false,hidden:true},
			{name:'stateName',align:"center", width:100,sortable:false},
			{name:'eval.discVersionNo',index:'discVersionNo',align:'center',width:240},
			{name:'eval.isEval',align:"center", width:80,sortable:false,formatter:"select",edittype:"select",editoptions:{value:{"true":'是',"false":'否'}},editable:true},
			{name:'eval.isReport',align:"center", width:80,sortable:false,formatter:"select",edittype:"select",editoptions:{value:{"true":'是',"false":'否'}},editable:true},
			{name:'edit',index:'edit', width:50,align:"center",sortable:false},
			{name:'oper',width:150,align:'center',sortable:false},
			{name:'eval.briefId',width:100,align:"center",hidden:true,editable:false}
		],
		rownumbers: true,
		height:"100%",
		autowidth:true,
		pager: '#pager_schoolCol_tb',
		rowNum:20,
		rowList:[20,30,40],
		viewrecords: true,
		sortorder: "asc",
		sortname:'ID',
		jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
            root: "rows",  //包含实际数据的数组  
            page: "pageIndex",  //当前页  
            total: "totalPage",//总页数  
            records:"totalCount", //查询出的记录数  
            repeatitems : false,
        },
		gridComplete: function(){
			isEditableEval(unitId);
			var ids = jQuery("#schoolCol_tb").jqGrid('getDataIDs');
			for(var i=0;i < ids.length;i++){
				var rowData = $('#schoolCol_tb').jqGrid("getRowData", ids[i]);//获取本行数据
				//数据查看
				var view = "<a class='check_detail' href='#' onclick='viewDiscDetail("+ids[i]+")'>查看</a>"; 
				//jQuery("#schoolCol_tb").jqGrid('setRowData',ids[i],{detail:view});
				var edit="<a class='check_detail' href='#' onclick='editDiscInfo("+ids[i]+")'>编辑</a>";
				jQuery("#schoolCol_tb").jqGrid('setRowData',ids[i],{edit:edit});
				//退回学科
				var back = "<a class='' href='#'  onclick='back2Disc("+ids[i]+")'>退回学科</a>"; 
				//下载
				var brief = "<a class='' href='#' onclick='downloadBrief("+ids[i]+")'>下载简况表</a>"; 
				
				var confirm= "<a class='' href='#'  onclick='confirmDisc("+ids[i]+")'>确认编辑</a>";
				var jqOper = view ;
				if(rowData['eval.briefId']!=''){
					jqOper+= ' | '+brief;
				}
				if(isEditable=='1'){
					if(rowData['eval.state']=='4'||rowData['eval.state']=='1'){
						jqOper += ' | '+back; 
					}else if(rowData['eval.state']=='6'){
						jqOper +=' | '+confirm;
					}
					//jQuery("#schoolCol_tb").jqGrid('setRowData',ids[i],{oper:view+' | '+back+' | '+brief});
				}else{
					//jQuery("#schoolCol_tb").jqGrid('setRowData',ids[i],{oper:view+' | '+brief});
				}
				jQuery("#schoolCol_tb").jqGrid('setRowData',ids[i],{oper:jqOper});
				var discVersionNo=rowData['eval.discVersionNo'];
				if(discVersionNo=='')
				{
					jQuery("#schoolCol_tb").jqGrid('setRowData',ids[i],{'eval.discVersionNo':'未生成'});
				}
			}
			if(isEditable=='0'){
				jQuery("#schoolCol_tb").setGridParam().hideCol("edit");//隐藏编辑列
				$("#schoolCol_tb").setGridWidth($(window).width()*0.99);
			}else{
				jQuery("#schoolCol_tb").setGridParam().showCol("edit");//隐藏编辑列
				$("#schoolCol_tb").setGridWidth($(window).width()*0.99);
			}
			//控制提交、撤销按钮的显隐
			var ids = jQuery("#schoolCol_tb").jqGrid('getDataIDs');
			var state_eval = ''; 
			if(ids.length>0){
				for(var i = 0;i<ids.length;i++){
					var rowData = $('#schoolCol_tb').jqGrid("getRowData",ids[i]);
					if(rowData['eval.isEval']=='true'){
						state_eval = rowData['eval.state'];
					}
				}
				controlSubmitAndRepeal(state_eval);
			}else{
				controlSubmitAndRepeal(evalState);
			}
		},
		caption: "学科信息汇总"
	}).navGrid('#schoolCol_tb',{edit:false,add:false,del:false});	
});
</script>