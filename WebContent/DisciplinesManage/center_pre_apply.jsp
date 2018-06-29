<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <div  class="con_header inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>  
				<h3>
					<span class="icon icon-web"></span>预参评信息管理
				</h3>
 			</td>		
		</tr>
	</table>
	<!-- <table class="layout_table right">
		<tr>		
			<td>
			    <span class="TextFont">当前申报状态：</span>
			</td>
             <td>
                 <label class="TextFont">申报中</label>
             </td>
		</tr>
	</table> -->
	<jsp:include page="/MainFlow/mainFlowState.jsp"></jsp:include>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
			    <span class="TextFont">学校代码：</span>
			    <%-- <select id="unit_Id">
			    <c:if test="${!empty unitMap}">
					<c:forEach items="${unitMap}" var="unit">
					<option value="${unit.key}">
						${unit.key}-${unit.value}</option>
					</c:forEach>
				</c:if>
				</select> --%>
			    <input id="unit_Id" type="text" value=""/>
			</td>
			<td>&nbsp;&nbsp;</td>
			<td>
			    <span class="TextFont">${textConfiguration.discNumber}：</span>
			   <%--  <select id="disc_Id">
			    <c:if test="${!empty discMap}">
					<c:forEach items="${discMap}" var="disc">
					<option value="${disc.key}">
						${disc.key}-${disc.value}</option>
					</c:forEach>
				</c:if>
				</select> --%>
			    <input id="disc_Id" type="text" value=""/>
			</td>
			<td>&nbsp;&nbsp;</td>
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
			<td>&nbsp;&nbsp;</td>
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
			<td>&nbsp;&nbsp;</td>
			<td>
			    <span class="TextFont">是否单位报告：</span>
			</td>
			<td>
				<select id="isUnitReport" >
		      		<option value="all" selected>全部</option>
		      		<option value="0">否</option>
		      		<option value="1">是</option>
                </select>
			</td>
			<td>&nbsp;&nbsp;</td>
			<td>
				<a id="search_pre_disc" class="button" href="#"><span class="icon icon-search"></span>查询</a>
			</td>
		</tr>
	</table>
</div>
<div class="selectbar layout_holder">
		<table id="center_pre_tb"></table>
		<div id="pager_centerpre_tb"></div>
</div>
<script type="text/javascript">
function searchPreDisc(unitId,discId,isReport,isEval,isUnitReport)
{
	jQuery("#center_pre_tb").setGridParam({
		url:"${ContextPath}/CenterPreList/gatherList_gatherData?unitId="+unitId+"&discId="+discId+"&isReport="+isReport+"&isEval="+isEval+"&isUnitReport="+isUnitReport,
		sortorder: "asc",
		sortname:'id',
		page:1,
		}).trigger("reloadGrid"); 
}
function unitChange(unitId){
	$.commonRequest({
		url:'${ContextPath}/CenterPreList/gatherList_gatherData/unitChange?unitId='+unitId,
		dataType:'json',
		success:function(discMaps){
			$.each(discMaps,function(key,value){
				$("#disc_Id").empty();
				var option = "<option value="+key+">"+key+"-"+value+"</option>";
				$("#disc_Id").append(option);
			});
		}
	});	
}
function discChange(discId){
	$.commonRequest({
		url:'${ContextPath}/CenterPreList/gatherList_gatherData/discChange?discId='+discId,
		dataType:'json',
		success:function(unitMaps){
			$.each(unitMaps,function(key,value){
				$("#unit_Id").empty();
				var option = "<option value="+key+">"+key+"-"+value+"</option>";
				$("#unit_Id").append(option);
			});
		}
	});	
}
$(document).ready(function(){

	mainState();//获取主流程状态
	$("#search_pre_disc").click(function(){
		var unitId=$("#unit_Id").val();
		var discId=$("#disc_Id").val();
		var isReport=$("#isReport").val();
		var isEval= $("#isEval").val();
		var isUnitReport=$("#isUnitReport").val();
		searchPreDisc(unitId,discId,isReport,isEval,isUnitReport);
		
	});
	
	common_Css();
	$("#center_pre_tb").jqGrid({
		url:"${ContextPath}/CenterPreList/gatherList_gatherData",
		datatype: "json",
		mtype:"post",
		colNames:['ID','学校代码','学校名称','${textConfiguration.discNumber}','${textConfiguration.discName}','是否参评','是否需要学科报告','是否需要单位报告','状态'],
		colModel:[
			{name:'preEval.id',index:'id',align:"center", width:100,editable:true,hidden:true},
			{name:'preEval.unitId',index:'unitId',align:"center", width:100},
			{name:'unitName',align:"center", width:100,sortable:false},
			{name:'preEval.discId',index:'discId',align:"center", width:100},
			{name:'discName',index:'discName',align:"center", width:100,sortable:false},
			{name:'preEval.isEval',align:"center", width:80,formatter:"select",edittype:"select",editoptions:{value:{"true":'是',"false":'否'}},sortable:false},
			{name:'preEval.isReport',align:"center", width:80,formatter:"select",edittype:"select",editoptions:{value:{"true":'是',"false":'否'}},sortable:false},
			{name:'preEval.isUnitReport',align:"center", width:80,formatter:"select",edittype:"select",editoptions:{value:{"true":'是',"false":'否'}},sortable:false},
			{name:'state',align:"center", width:80},
		],
		rownumbers: true,
		height:"100%",
		autowidth:true,
		pager: '#pager_centerpre_tb',
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
			
		},
		caption: "学科预信息汇总",
	}).navGrid('#center_pre_tb',{edit:false,add:false,del:false});	
	/* $("#unit_Id").change(function(){
		var unitId=$("#unit_Id").val();
		unitChange(unitId);
	});
	$("#disc_Id").change(function(){
		var discId=$("#disc_Id").val();
		discChange(discId);
	}); */
});
</script>