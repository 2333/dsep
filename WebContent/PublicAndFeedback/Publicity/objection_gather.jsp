<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script src="${ContextPath}/js/fileOper/filename_oper.js"></script>
<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>异议处理
	</h3>
</div>
<div class="selectbar inner_table_holder">
	<jsp:include page="../_ddl_publicity_round.jsp"></jsp:include>
</div>
<div id="dv_select" class="selectbar inner_table_holder">
	<table id="tb_joinDiscipline" class="layout_table left">
		<tr>
			<jsp:include page="../_ddl_join_unit.jsp"></jsp:include>
			
			<jsp:include  page="../_ddl_join_discipline.jsp"></jsp:include>
		</tr>
	</table>
	<table id="tb_operateButton" class="layout_table right">
		<tr>
			<td><a id="objectProcess" class="button" href="#"><span
					class="icon icon-store"></span>全部处理完成</a></td>
			<td><a id="objectSubmit" class="button" href="#"><span
					class="icon icon-submit"></span>提交所有异议</a></td>
			<td><a id="objectSubmitDownload" class="button" href="#"><span
					class="icon icon-download"></span>导出所有提交异议</a></td>
			<td><a id="objectProcessDownload" class="button" href="#"><span
					class="icon icon-download"></span>导出所有处理异议</a></td>
		</tr>
	</table>
</div>

<div class="tabs" >
	<ul id="tab_status">
		<c:if test="${!empty statusMap}">
			<c:forEach items="${statusMap}" var="statusItem">
				<li id="${statusItem.key}">
					<a id="${statusItem.key}" class="objectLink" href="#" >${statusItem.value}</a>
				</li>
			</c:forEach>
		</c:if>
	</ul>
	<div id="dv_objection_list" >
		<table id="tb_objection_list"></table>
		<div id="dv_objection_pager"></div>
	</div>
</div>
<div id="dialog-confirm" title="警告">
</div>

<script src="../js/edit_jqgrid.js"></script>

<script type="text/javascript">
	var contextPath = "${ContextPath}";
	var discChangeData = '';
	var objectTableId = '#tb_objection_list';//异议汇总表的Id
	var objectFocusStatus = $("#tab_status").children().eq(0).children().eq(0).attr("id");//获取标签页的状态
	var publictityBegin="进行中";
	var publicityEnd="已结束";
	var jqTable = "#tb_objection_list";
	var proveMaterialPath = ""; //证明材料路径
	
	var unitColModel = [
		{name:'objectionId',index:'objectionId',align:"center",editable:true,hidden:true},
		{name:'proveMaterial.id',index:'proveMaterial.id',hidden:true,width:13},	
		{name:'objectEntityId',index:'objectEntityId',align:"center",editable:true,hidden:true},
		{name:'objectDataId',index:'objectDataId',align:"center",editable:true,hidden:true},
		{name:'objectUnitId',index:'objectUnitId',align:"center",hidden:true}, 
		{name:'objectDiscId',index:'objectDiscId',align:"center",hidden:true}, 
		{name:'problemUnitId',index:'problemUnitId',align:'center',width:200},
		{name:'problemDiscId',index:'problemDiscId',align:'center',width:200},
		{name:'problemEntityName',index:'objectCollectEntityName',align:'center',width:300},
		{name:'problemAttrName',index:'unitObjectCollectAttrName',align:'center',width:300},
		{name:'problemSeqNo',index:'seqNo',align:'center',width:20},
		{name:'importantAttrValue',index:'importantAttrValue',align:'center',width:40},
		{name:'unitObjectContent',index:'unitObjectContent',width:120,align:"center",editable:true}, 
		{name:'unitObjectType',index:'unitObjectType',width:70,align:"center"},
		{name:'download',index:'download',width:13,align:'center',sortable:false},
   		{name:'edit',index:'edit',width:15,align:'center',sortable:false},
		{name:'cancel',index:'cancel',width:10,align:'center',sortable:false}
	];
	
	
	
	$(document).ready(function() {
		$("input[type=submit], a.button , button").button();
		
		$("#td_proveMaterial").hide();//下载证明材料
		
		
		setDisciplineText("被异议${textConfiguration.disc}:");//设置学科下拉框的名称，位于_ddl_join_discipline.jsp页面
		setUnitText("被异议学校:");//位于_ddl_join_unit.jsp页面
		
		addDropdownList();
		
		showPublicityMessage(); //显示公示批次的相关信息，位于_ddl_publicity_round.jsp页面
		
		$(".tabs").tabs({//生成标签页
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
		
		$("#tb_objection_list").jqGrid({
			url:'${ContextPath}/publicity/viewObjection_showAndEditObjection?status='+objectFocusStatus+'&currentRoundId='+$("#ddl_publicity_round").val(),
			editurl:'${ContextPath}/publicity/viewObjection_saveEditRowData',
			datatype:"json",
			mtype:'GET',
			colNames:['ID','证明材料ID','实体Id','数据项Id','提出异议学校ID','提出异议学科ID','被异议学校',
			          '被异议${textConfiguration.disc}','异议采集项','异议项','序号','关键项','异议内容','异议内容','异议类型','下载','编辑','删除'],
			colModel:[
	 		 	{name:'objectionId',index:'objectionId',align:"center",editable:true,hidden:true},
	 			{name:'proveMaterial.id',index:'proveMaterial.id',hidden:true,width:13},	
 		 		{name:'objectEntityId',index:'objectEntityId',align:"center",editable:true,hidden:true},
 			 	{name:'objectDataId',index:'objectDataId',align:"center",editable:true,hidden:true},
           		{name:'objectUnitId',index:'objectUnitId',align:"center",hidden:true}, 
             	{name:'objectDiscId',index:'objectDiscId',align:"center",hidden:true}, 
            	{name:'problemUnitId',index:'problemUnitId',align:'center',width:17},
             	{name:'problemDiscId',index:'problemDiscId',align:'center',width:17},
             	{name:'problemEntityName',index:'objectCollectEntityName',align:'center',width:20},
	            {name:'unitObjectCollectAttrName',index:'unitObjectCollectAttrName',align:'center',width:25},
	            {name:'problemSeqNo',index:'seqNo',align:'center',width:8},
	            {name:'importantAttrValue',index:'importantAttrValue',align:'center',width:14},
	            {name:'unitObjectContent',index:'unitObjectContent',width:70,align:"center",editable:true}, 
	            {name:'centerObjectContent',index:'centerObjectContent',width:70,align:'center',editable:true},
	            {name:'unitObjectType',index:'unitObjectType',width:30,align:"center"},
	            {name:'download',index:'download',width:13,align:'center',sortable:false},
	            {name:'edit',index:'edit',width:20,align:'center',sortable:false},
				{name:'cancel',index:'cancel',width:10,align:'center',sortable:false}
		     ],
			height : '100%',
			pager : '#dv_objection_pager',
			pgbuttons : true,
			/* shrinkToFit : false, */
			rowNum : 20,
			rownumbers:true,
			rowList : [ 10, 20, 30 ],
			viewrecords : true,
			sortname : 'objectDiscId',
/////////////////////////////////展示数据详情
			subGrid: true,
			subGridRowExpanded: function(subgrid_id, row_id) {
				var entityId = $(jqTable).getCell(row_id, 'objectEntityId');
				var itemId = $(jqTable).getCell(row_id, 'objectDataId');
				var subgrid_table_id;
				subgrid_table_id = subgrid_id+"_t";

				$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"'></table>");
				
				$.getJSON("${ContextPath}/publicity/viewObjection_getViewConfig?entityId="+entityId, //获取某entity的元数据信息
					function initJqTable(data) {
						$("#"+subgrid_table_id).jqGrid({
		 					url :'${ContextPath}/publicity/viewObjection_getCollectData?'+
		 							'entityId='+ entityId + '&itemId=' + itemId+'&backupVersionId='+backupVersionId,//取数据
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
			},
			sortorder : "desc",
			gridComplete:function(){//给表格添加编辑列和删除列
				var ids = $("#tb_objection_list").jqGrid('getDataIDs');
				for(var i=0; i < ids.length; i++){
					var list_name = "#tb_objection_list";
					
					var proveMaterialId = $(jqTable).getCell(ids[i],'proveMaterial.id');
					console.log(proveMaterialId);
					/* alert(proveMaterialId); */
					//downloadProveMaterialPath位于/js/fileOper/excel_oper.js中
					var downloadRow = "<a href='#' id='downloadGrid'  onclick=\"pageDownload('"+proveMaterialId+"');\">证明材料</a>";
					if(proveMaterialId != "null" && proveMaterialId != "" && proveMaterialId != "undefined"){
						$(jqTable).setRowData(ids[i],{download:downloadRow});	
					}
					else{
						$(jqTable).setRowData(ids[i],{download:'/'});
					}
					
					//editRow函数引自edit_jqgrid.js文件
					var editRow = "<a href='#' id='editGrid'  onclick=\"editRow('"+list_name+"','"+ids[i]+"');\">编辑</a>";
					$(jqTable).setRowData(ids[i],{edit:editRow});
					var deleteRow = "<a href='#' onclick=\"deleteRow('"+ids[i]+"');\">删除</a>";
					$(jqTable).setRowData(ids[i],{cancel:deleteRow});	
					if( $(jqTable).getCell(ids[i],"problemSeqNo")+"" == "0"){
						$(jqTable).setRowData(ids[i],{problemSeqNo:'/'});
					}
				}
			},
			jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
				root : 'rows', //包含实际数据的数组  
				page : 'pageIndex', //当前页  
				total : 'totalPage',//总页数  
				records : 'totalCount', //查询出的记录数  
				repeatitems : false
			},}).navGrid('#dv_objection_pager',{edit:false,add:false,del:false
		});
		
		isShowEditRow();
		isShowSubmit();
	});
	
	//给下拉框增加全部这一项
	function addDropdownList(){
		$("#ddl_join_discipline").append('<option value="" selected="selected">全部</option>');
		$("#ddl_join_unit").append('<option value="" selected="selected">全部</option>');
	}
	
	//批次发生改变
	$("#ddl_publicity_round").change(function() {
		showPublicityMessage();//位于_ddl_publicity_round.jsp页面
		loadJqgrid(); //加载异议表格数据
		isShowEditRow();//是否显示jqGrid的编辑列和删除列
		isShowSubmit();//是否显示提交按钮和导出异议按钮
	});
	
	//导出所有已提交的异议
	$('a#objectSubmitDownload').click(function() {
		if( $(jqTable).getGridParam("reccount") == 0){
			alert_dialog('没有可导出的异议');
		}
		else{
			var url = '${ContextPath}/publicity/viewObjection_downloadSubmitObjection';
			var dataParam = "currentRoundId="+$("#ddl_publicity_round").val();
			outputJSWithParam(url,dataParam);
		}
	});
	
	//导出所有已处理的异议
	$("a#objectProcessDownload").click(function(){
		if( $(jqTable).getGridParam("reccount") == 0){
			alert_dialog('没有可导出的异议');
		}
		else{
			var url = '${ContextPath}/publicity/viewObjection_downloadProcessObjection';
			var dataParam = "currentRoundId="+$("#ddl_publicity_round").val();
			outputJSWithParam(url,dataParam);
		}
	});
	
	//标签页的获取焦点事件
	$('#tab_status').children().focus(function() {
		objectFocusStatus = $(this).attr("id");//标签页的状态
		loadJqgrid(); //重新加载异议表格
		isShowEditRow();// 是否显示表格的编辑列和删除列
		isShowSubmit(); //提交按钮和导出异议按钮是否显示
	});
	
	//标签页的点击事件
	$('#tab_status').children().click(function(){
		objectFocusStatus = $(this).attr("id");//标签页的状态
		loadJqgrid(); //重新加载异议表格
		isShowEditRow();// 是否显示表格的编辑列和删除列
		isShowSubmit(); //提交按钮和导出异议按钮是否显示
	});

	//提交异议
	$('a#objectSubmit').click(function() {
		if( $(jqTable).getGridParam("reccount") == 0){
			alert_dialog('没有异议');
		}else{
			$("#dialog-confirm").empty().append("<p>确定要提交所有异议吗?</p>");
			$("#dialog-confirm").dialog({
	     	      height:150,
	     	      buttons: {
	     	        "确定":function(){
	     	        	$.ajax({
	     					type:'POST',
	     					url:'${ContextPath}/publicity/viewObjection_submitAllObjection',
	     					data:'currentRoundId='+$("#ddl_publicity_round").val(),
	     					success : function(data) {
	     						if (data) {
	     							alert_dialog("提交成功");
	     							loadJqgrid();//重新加载异议数据
	     						}
	     					}
	     				});
	     	        	$(this).dialog("close");
					},
					"取消":function(){
						$(this).dialog("close");
					}
	   	      	}
			});	
		}
	});
	
	//将所有异议设置为已处理
	$("a#objectProcess").click(function(){
		if( $(jqTable).jqGrid("getGridParam","reccount") <= 0 ){
			alert_dialog("暂无未处理的异议");
			return ;
		}
		$("#dialog-confirm").empty().append("<p>确定已全部处理完成吗?</p>");
		$("#dialog-confirm").dialog({
     	      height:150,
     	      buttons: {
     	        "确定":function(){
     	        	$.ajax({
     					type:'POST',
     					url:'${ContextPath}/publicity/viewObjection_processAllObjection',
     					data:'currentRoundId='+$("#ddl_publicity_round").val(),
     					success : function(data) {
     						if (data) {
     							alert_dialog("操作成功");
     							loadJqgrid();//重新加载异议数据
     						}
     						else{
     							alert_dialog("操作失败");
     							loadJqgrid();
     						}
     					},
     					error:function(data){
     						alert_dialog("出现错误");
     					}
     				});
     	        	$(this).dialog("close");
				},
				"取消":function(){
					$(this).dialog("close");
				}
   	      	}
		});
	});

	//学科的下拉框值改变后jqGrid做出相应的调整
	$('#ddl_join_discipline').change(function() {
		loadJqgrid();//重新加载异议数据
	});
	
	
	$('#ddl_join_unit').change(function(){
		loadJqgrid();
	});
	
	
	//加载异议表格
	function loadJqgrid(){
		$("#tb_objection_list").setGridParam({
			url : '${ContextPath}/publicity/viewObjection_showAndEditObjection?'
				+'status='+objectFocusStatus //异议的状态，已提交或未提交
				+'&problemDiscId='+$("#ddl_join_discipline").val()//被异议的学科ID
				+'&currentRoundId='+$("#ddl_publicity_round").val()//当前公示批次ID
				+'&problemUnitId='+$("#ddl_join_unit").val()
		}).trigger("reloadGrid");
	}
	
	//下载证明材料
	function pageDownload(proveMaterialId){
		$.ajax({
			type:'POST',
			dataType:'json',
			url:'${ContextPath}/publicity/viewObjection_getDownloadPath',
			data:'proveMaterialId='+proveMaterialId,
			success : function(data) {
				if( data != null && data != "undefined"){
					downloadProveMaterial(data);
				}
			}
		});
	}
	

	//控制页面上一些按钮的显示
	function isShowSubmit(){
		//ddl_open_status变量位于_ddl_publicity_round.jsp页面,表示公示批次的开启状态
		//objectFocusStatus表示哪个标签被选中,未提交时表示可以编辑
		$("#td_proveMaterial").hide();//隐藏下载证明材料按钮
		
		//objectSubmitDownload:导出所有提交异议
		//objectProcess:设置未处理的异议为已处理
		//objectSubmit:提交所有异议到中心
		//objectProcessDownload:导出所有已处理异议
		
		if( "${user.userType}"+"" == "2"){//学校用户
			$("a#objectProcess").hide();
			if( objectFocusStatus == '0'){//标签为未提交
				$('a#objectSubmitDownload').hide();
				$("a#objectProcessDownload").hide();
				if(ddl_open_status == "1"){
					$('a#objectSubmit').show();//提交按钮
				}
				else{
					$('a#objectSubmit').hide();
				}
			}
			else if(objectFocusStatus == '1'){//已提交
				$("a#objectSubmitDownload").show();
				$("a#objectSubmit").hide();
				$("a#objectProcessDownload").hide();
			}
			else{
				$("a#objectSubmitDownload").hide();
				$("a#objectSubmit").hide();
				$("a#objectProcessDownload").hide();
			}
		}
		else if("${user.userType}"+"" == "3"){//学科用户
			console.log("${user.userType}");
			$('a#objectSubmit').hide();
			$("a#objectProcess").hide();
			$("td.td_ddl_join_discipline").hide(); //隐藏学科下拉框
			if( objectFocusStatus == '1'){//已提交
				$("#objectSubmitDownload").show();
				$("a#objectProcessDownload").hide();
			}
			else{
				$("#objectSubmitDownload").hide();
				$("a#objectProcessDownload").hide();
			}
		}
		else if("${user.userType}"+"" == "1"){//中心用户
			$('a#objectSubmit').hide();
			$("a#objectSubmitDownload").hide();
			if( objectFocusStatus == '1'){//已提交
				$("a#objectProcess").show();
				$("a#objectProcessDownload").hide();
			}
			else if( objectFocusStatus == '2'){//已处理
				$("a#objectProcess").hide();
				$("a#objectProcessDownload").show();
			}
			else{
				$("a#objectProcessDownload").hide();
				$("a#objectProcess").hide();
			}
		}
		else{
			$("#tb_operateButton").hide();
		}
	}
	
	
 	//是否显示jqGrid的编辑列和删除列
	function isShowEditRow(){
 		if( "${user.userType}"+"" == "2"){//学校用户
 			$("#tb_objection_list").setGridParam().showCol("unitObjectContent");
			$("#tb_objection_list").setGridParam().hideCol("centerObjectContent");
 			//ddl_open_status变量位于_ddl_publicity_round.jsp页面,表示公示批次的开启状态
 	 		if( objectFocusStatus == '0' && ddl_open_status == "1") {
 	 			$("#tb_objection_list").setGridParam().showCol("edit");
 				$("#tb_objection_list").setGridParam().showCol("cancel");
 			}else{
 				$("#tb_objection_list").setGridParam().hideCol("edit");
 				$("#tb_objection_list").setGridParam().hideCol("cancel");
 				
 			}
 		}
 		else if( "${user.userType}"+"" == "3"){//学科用户
 			$("#tb_objection_list").setGridParam().showCol("unitObjectContent");
			$("#tb_objection_list").setGridParam().hideCol("centerObjectContent");
 			if( objectFocusStatus == '0' && ddl_open_status == "1"){
 				$("#tb_objection_list").setGridParam().showCol("edit");
 				$("#tb_objection_list").setGridParam().showCol("cancel");
 			}
 			else{
 				$("#tb_objection_list").setGridParam().hideCol("cancel");
 				$("#tb_objection_list").setGridParam().hideCol("edit");
 			}
 		}
 		else if("${user.userType}"+"" == "1"){//中心用户
 			$("#tb_objection_list").setGridParam().showCol("centerObjectContent");
			$("#tb_objection_list").setGridParam().hideCol("unitObjectContent");
 			if( objectFocusStatus == '1' ) {
 	 			$("#tb_objection_list").setGridParam().showCol("edit");
 				$("#tb_objection_list").setGridParam().showCol("cancel");
 			}else{
 				$("#tb_objection_list").setGridParam().hideCol("edit");
 				$("#tb_objection_list").setGridParam().hideCol("cancel");
 			}
 		}
 		$("#tb_objection_list").setGridWidth($(window).width()*0.99);
	}
 	
 	
 	//删除某一列，实际上仅改变其状态位，将其变为已删除
 	function deleteRow(id){
 		$("#dialog-confirm").empty().append("<p>确定删除吗？</p>");
		$("#dialog-confirm").dialog({
     	      height:150,
     	      buttons: {
     	        "确定":function(){
     	        	var objectionId = $(jqTable).getCell(id,"objectionId");
     	 	 		$.ajax({
     	 	 			url:'${ContextPath}/publicity/viewObjection_deleteRow',
     	 	 			type:"POST",
     	 	 			data:'objectionId='+objectionId,
     	 	 			success:function(data){
     	 	 				if( data ){
     	 	 					alert_dialog('删除成功');
     	 	 					$("#td_proveMaterial").hide();//隐藏下载证明材料按钮
     	 	 					loadJqgrid();
     	 	 				}
     	 	 				else{
     	 	 					alert_dialog('删除失败');
     	 	 					loadJqgrid();
     	 	 				}
     	 	 			},
     	 	 			error:function(data){
     	 	 				alert_dialog('出现错误');
     	 	 				
     	 	 			}
   	 	 			});
     	 	 		$(this).dialog("close");
				},
				"取消":function(){
					$(this).dialog("close");
				}
   	      	}
		});
 	}
</script>