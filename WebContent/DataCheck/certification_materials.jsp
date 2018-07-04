<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script src="${ContextPath}/js/fileOper/filename_oper.js"></script>
<div class="con_header">
	<h3>
		<span class="icon icon-search"></span>证明材料
	</h3>
</div>
<div id="collect_mate">
	<div class="selectbar inner_table_holder">
		<table class="layout_table left">
			<tr>
				<td >
					<span class="TextFont">采集项：</span>
				</td>
				<td>
					<select id="mate_select_collect">
						<option  value="not_sel">&nbsp;&nbsp;&nbsp;--采集项--</option>
						<c:forEach items="${entityMap}" var="entity">
							<option value="${entity.key}">${entity.value}</option>
						</c:forEach>
					</select>
				</td>
				<td>&nbsp;&nbsp;&nbsp;</td>
			</tr>
		</table>
	</div>
	<div id="mate_grid_parent" class="selectbar" hidden="true">
		<jsp:include page="/DataCheck/materials_oper.jsp"></jsp:include>
		<jsp:include page="/DataCheck/materials_grid.jsp"></jsp:include>
	</div>
</div>

<script type="text/javascript">
var sortName;
var sortOrder;
var entityId='';
var contextPath = '${ContextPath}';
var attachId; //附件iD
$(document).ready(function(){
	$( "input[type=submit], a.button , button" )
	  .button();
});
$("#mate_select_collect").change(function(){
	entityid = $("#mate_select_collect option:selected").val();
	console.log(entityid);
	if(entityid != "not_sel"){
		var commonUrl=contextPath+"/check/certi_materials/initCertiJqgrid/"+entityid;
		var config={url:commonUrl,dataType:'json',success:mate_renderJqGrid};
		$.commonRequest(config);
		$('#mate_grid_parent').show();
	}
	else{
		$('#mate_grid_parent').hide();
	}
});

function mate_renderJqGrid(data){
	  console.log(data);
	  controllerDic= data.idsOfControlDic;
	  entityId = data.id;
	  unitId='';
	  discId='';
	  data = addMaterialsCol(data);
	  controllerDic.FILE.push("materialsOper");
	  dataUrl = contextPath+'/check/certi_materials/certiMaterials_dataJqGrid?'+'entityId='+entityId+
	  '&unitId='+unitId+'&discId='+discId,
	  initJqTable(data,dataUrl);
}

$("#search_materials").click(function(){
	var entityId = $("#mate_select_collect option:selected").val();
	var unitId = $("#unit_id").val();
	var discId = $("#disc_id").val();
	jQuery("#mateJqGrid_tb").setGridParam({
		url:contextPath+'/check/certi_materials/certiMaterials_dataJqGrid?'+'entityId='+ entityId +
			  '&unitId='+unitId+'&discId='+discId,
	}).trigger("reloadGrid");
	event.preventDefault();
});

/*增加 证明材料的下载 列   */
function addMaterialsCol(data){
	var materialsCol={  
			label : '证明材料',  
			name : 'materialsOper',  
			sortable : false,  
			width : 80,  
			fixed : false,  
			align : "center",
			search:false,
			editable:true,
			edittype:"button"
		};
	data.colConfigs.push(materialsCol);
	return data;
}

</script>

