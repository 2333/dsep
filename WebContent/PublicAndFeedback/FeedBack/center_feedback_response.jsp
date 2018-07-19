<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>反馈答复处理
	</h3>
</div>
<div class="selectbar inner_table_holder">
	<jsp:include page="../_ddl_feedback_round.jsp"></jsp:include>
</div>

<div id="div_feedback_layout" class="layout_holder" >
	<jsp:include page="../TreeAndView/_feed_type_tree.jsp"></jsp:include>
	<div id="dv_parent" class="selectbar right_block" hidden="true">
		<div class="inner_table_holder">
			<table class="layout_table left">
				<tr>
					<jsp:include page="../_ddl_join_unit.jsp"></jsp:include>
					
		     		<jsp:include page="../_ddl_join_discipline.jsp"></jsp:include>
		     		
		     		<jsp:include page="../_ddl_response_type.jsp"></jsp:include>
				</tr>
			</table>
			<!-- <table id="tb_operateButton" class="layout_table right">
				<tr>
					<td><a id="btn_agree" class="button" href="#"><span
							class="icon icon-store"></span>同意</a></td>
					<td><a id="btn_disagree" class="button" href="#"><span
							class="icon icon-store"></span>不同意</a></td>
					<td><a id="objectProcessDownload" class="button" href="#"><span
							class="icon icon-export"></span>全部导出</a></td>
				</tr>
			</table> -->
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
			<div id="dv_feedback_list">
				<div class="table">
					<table id="tb_feedback_list"></table>
					<div id="dv_feedback_pager"></div>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="dialog-confirm" title="警告">
</div>

<div hidden="true">
	<jsp:include page="center_check_response_detail.jsp"></jsp:include> 
</div>

<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script src="${ContextPath}/js/fileOper/filename_oper.js"></script>

<script src="${ContextPath}/js/feedback/feedback_jqgrid.js"></script>

<script type="text/javascript">
	var unitId;
	var discId;
	var feedbackStatus = $("#tab_status").children().eq(0).children().eq(0).attr("id");//获取标签页的状态
	var currentFeedbackRoundId = "${currentRound.id}";
	var feedbackType;
	var jqTable = "#tb_feedback_list";
	var jqCaption = "";
	
	$(document).ready(function() {	
		$("input[type=submit], a.button , button").button();
		
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
		
		initDiv("#div_feedback_layout","#dv_feedback_list");//函数位于TreeAndView/_feed_type_tree.jsp页面
		showFeedbackMessage("${ContextPath}/feedback/centerResponse_getFeedbackRoundMessage");//显示批次信息
		addDropdownList();//给下拉框增加“全部”这一项
	});
	
	//初始化反馈数据表
	function initJqTable(){
		$(jqTable).jqGrid('GridUnload');
		$(jqTable).jqGrid({
			url:'${ContextPath}/feedback/centerResponse_getResponse?'
				+'feedbackStatus='+feedbackStatus //异议的状态，已提交或未提交
				+'&problemDiscId='+$("#ddl_join_discipline").val()//被异议的学科ID
				+'&problemUnitId='+$("#ddl_join_unit").val()//被异议的学科ID
				+'&currentRoundId='+$("#ddl_feedback_round").val()//当前公示批次ID
				+'&responseType='+$("#ddl_response_type").val()
				+'&feedbackType='+feedbackType,//反馈类型，目前有六类
			datatype:"json",
			mtype:'POST',
			colNames:['查看','学校意见','中心意见','证明材料ID','ID','下载','是否是唯一反馈项','采集项ID','数据项ID','属性ID','学校','${textConfiguration.disc}','采集项','问题项','问题项原始值','关键项','序号','问题','问题'],
			colModel:[
					{name:'check',index:'check',width:10,align:'center',sortable:false},
					{name:'responseType',index:'responseType',width:20,align:'center',sortable:true},
					{name:'centerAdvice',index:'centerAdvice',width:20,align:'center',sortable:true},
					{name:'proveMaterial.id',index:'proveMaterial.id',hidden:true,width:13},
					{name:'id',index:'id',align:"center",editable:true,hidden:true}, 
					{name:'download',index:'download',align:"center",width:12,sortable:false},
					{name:'sameItemNumber',index:'sameItemNumber',align:"center",editable:true,hidden:true},
		 		 	{name:'problemCollectEntityId',index:'problemCollectEntityId',algin:'center',hidden:true,editable:true},
	 		 		{name:'problemCollectItemId',index:'problemCollectItemId',align:'center',hidden:true,editable:true},
	 		 		{name:'problemCollectAttrId',index:'problemCollectAttrId',align:'center',hidden:true,editable:true},
	 		 		{name:'problemUnitId',index:'problemUnitId',align:'center',width:10},
		            {name:'problemDiscId',index:'problemDiscId',align:'center',width:10},
		            {name:'problemCollectEntityName',index:'problemCollectEntityName',align:'center',width:20},
		            {name:'problemCollectAttrName',index:'problemCollectAttrName',align:'center',width:20},
		            {name:'problemCollectAttrValue',index:'problemCollectAttrValue',align:'center',width:20},
		            {name:'importantAttrValue',index:'importantAttrValue',align:'center',width:14},
		            {name:'problemSeqNo',index:'problemSeqNo',align:'center',width:8},
		            {name:'problemContent',index:'problemContent',width:80,align:"center",editable:true},
		            {name:'similarityProblemContent',index:'problemContent',width:80,align:"center",sortable:false,hidden:true,editable:true},
		            ],
			height : '100%',
			width:$("#content").width(),
			autowidth : false,
			shrinkToFit : true,
			multiselect:true,
			pager : '#dv_feedback_pager',
			pgbuttons : true,
			rowNum : 10,
			rowList : [ 10, 20, 30 ],
			caption:jqCaption,
			rownumbers:true,
			viewrecords : true,
			sortname : 'problemCollectEntityId',
			gridComplete:function(){//给表格添加编辑列和删除列
				if( showNoRecords(jqTable)){
					var ids = $(jqTable).jqGrid('getDataIDs');
					for(var i=0;i < ids.length;i++){
						var entityId = $(jqTable).getCell(ids[i],"problemCollectEntityId");//实体ID
						var collectItemId = $(jqTable).getCell(ids[i],"problemCollectItemId");//反馈数据项ID
						var responseItemId = $(jqTable).getCell(ids[i],"id");
						var responseRow = "<a href='#' onclick=\"responseFeedback('"+responseItemId+"','"+entityId+"','"+collectItemId+"');\">查看</a>";
						$(jqTable).setRowData(ids[i],{check:responseRow});
						
						var proveMaterialId = $(jqTable).getCell(ids[i],'proveMaterial.id');
						/* console.log(proveMaterialId); */
						/* alert(proveMaterialId); */
						//downloadProveMaterialPath位于/js/fileOper/excel_oper.js中
						var downloadRow = "<a href='#' id='downloadGrid'  onclick=\"pageDownload('"+proveMaterialId+"');\">证明材料</a>";
						if(proveMaterialId != "null" && proveMaterialId != "" && proveMaterialId != "undefined"){
							$(jqTable).setRowData(ids[i],{download:downloadRow});	
						}
						else{
							$(jqTable).setRowData(ids[i],{download:'/'});
						}
					}
					if( feedbackType == "3"){ //如果是重复数据则隐藏问题列
						$(jqTable).setGridParam().hideCol('problemContent');
						$(jqTable).setGridParam().showCol('similarityProblemContent');
					}
					else{
						$(jqTable).setGridParam().showCol('problemContent');
						$(jqTable).setGridParam().hideCol('similarityProblemContent');
					}
					$(jqTable).setGridWidth($("#content").width());
				}
				
				isShowColumn(); //判断一些列是否显示
			},
			loadComplete : function() {
				$("#tab_status").css("width",$(jqTable).css("width"));
			},
	/////////////////////////////////展示数据详情
			subGrid: true,
			subGridRowExpanded: function(subgrid_id, row_id) {
				if( feedbackType != 3){
					showSubGridData(subgrid_id,row_id,
						'${ContextPath}/feedback/centerResponse_getViewConfig',
						'${ContextPath}/feedback/centerResponse_getCollectData');
				}
				else{
					showSimilarityData(subgrid_id,row_id,
						'${ContextPath}/feedback/centerResponse_getViewConfig',
						'${ContextPath}/feedback/centerResponse_getCollectData');
				}
			},
			jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
				root : 'rows', //包含实际数据的数组  
				page : 'pageIndex', //当前页  
				total : 'totalPage',//总页数  
				records : 'totalCount', //查询出的记录数  
				repeatitems : false
			}}).navGrid('#dv_feedback_pager',{edit:false,add:false,del:false
		});
	}
	
	//批次发生改变
	$("#ddl_feedback_round").change(function() {
		showFeedbackMessage("${ContextPath}/feedback/centerResponse_getFeedbackRoundMessage"); //位于_ddl_feedback_round.jsp页面
		initJqTable(); //加载异议表格数据
	});
	
	//标签页的获取焦点事件
	$('#tab_status').children().focus(function() {
		feedbackStatus = $(this).attr("id"); //标签页的状态
		initJqTable(); //重新加载异议表格
	});
	
	//标签页的点击事件
	$('#tab_status').children().click(function(){
		feedbackStatus = $(this).attr("id"); //标签页的状态
		initJqTable(); //重新加载异议表格
	});
	
	//下载证明材料
	function pageDownload(proveMaterialId){
		$.ajax({
			type:'POST',
			dataType:'json',
			url:'${ContextPath}/feedback/centerResponse_getDownloadPath',
			data:'proveMaterialId='+proveMaterialId,
			success : function(data) {
				if( data != null && data != "undefined"){
					downloadProveMaterial(data);
				}
			}
		});
	}

	
	//处理相似反馈
	//相似反馈指的是对同一采集数据项提出问题的反馈
	function sameFeedbackProcess(ids){
		var sameNumberRow = 0;
		for(var i=0; i < ids.length; i++){
			if( $(jqTable).getCell(ids[i],"sameItemNumber") > 0  ){
				sameNumberRow++;
				
				//querySameProblem函数位于query_same_problem_dialog.jsp页面
				var queryRow = "<a href='#' onclick=\"querySameProblem('"+entityId+"','"+collectItemId+"');\">合并查看</a>";
				
				$(jqTable).setRowData(ids[i],{query:queryRow});	
				$(jqTable+" #"+ids[i]).find('td').css('color','red');
			}
			else{
				$(jqTable).setRowData(ids[i],{query:'/'});
			}
			if( $(jqTable).getCell(ids[i],"problemSeqNo")+"" == "0"){
				$(jqTable).setRowData(ids[i],{problemSeqNo:'/'});
			}
		}
		if(sameNumberRow == 0){
			$(jqTable).setGridParam().hideCol("query");
		}
		else{
			$(jqTable).setGridParam().showCol("query");
		}
	}
	
	//设置反馈类型
	function setFeedbackType(theType){
		feedbackType = theType;
	}
	
	//设置反馈状态
	function setFeedbackStatus(theStatus){
		feedbackStatus = theStatus;
	}
	
	
	//设置反馈表的标题
	function setJqCaption(theCaption){
		jqCaption = theCaption;
	}
	
	
	//获取所有被选中的行的主键数组
	function getSelectPrimaryKey(){
		var rowids = $(jqTable).jqGrid('getGridParam','selarrrow');
		var ids = [];
		$.each(rowids,function(i,value){
			ids.push($(jqTable).getCell(value,'id'));
		});
		return ids;
	}
	
	//是否有行被选中
	function isRowSelected(){
		
	}
	
	//同意学校的意见
	$("#btn_agree").click(function(){
		var ids = getSelectPrimaryKey();
		if( ids.length == 0 ){
			alert_dialog("请先选择要处理的数据！");
		}
		else{
			$.ajax({
				url:'${ContextPath}/feedback/centerResponse_agree',   
                type:"POST",   
                data:"responseItemIdList="+ids,   
                success : function(data) {   
                	console.log(data);
                    if(data == true) {   
                        alert_dialog("处理成功");
                        initJqTable();
                    }   
                    else{
                    	alert_dialog("处理失败");
                    	initJqTable();
                    	/* alert("异议添加失败"); */
                    }
                },   
                error : function(data) {   
                	/* alert('test'+ " "+data); */
                    alert("出现异常错误");
                }
			});
		}
	});
	
	$("#btn_disagree").click(function(){
		var ids = getSelectPrimaryKey();
		if( ids.length == 0 ){
			alert_dialog("请先选择要处理的数据！");
		}
		else{
			$.ajax({
				url:'${ContextPath}/feedback/centerResponse_disagree',   
                type:"POST",   
                data:"responseItemIdList="+ids,   
                success : function(data) {   
                	console.log(data);
                    if(data == true) {   
                        alert_dialog("处理成功");
                        initJqTable();
                    }   
                    else{
                    	alert_dialog("处理失败");
                    	initJqTable();
                    	/* alert("异议添加失败"); */
                    }
                },   
                error : function(data) {   
                	/* alert('test'+ " "+data); */
                    alert("出现异常错误");
                }
			});
		}
	});
	
	
	//加载反馈数据表
	function loadFeedbackJqGrid(){
		$(jqTable).setGridParam({
			url:'${ContextPath}/feedback/centerResponse_getResponse?'
				+'feedbackStatus='+feedbackStatus //异议的状态
				+'&problemDiscId='+$("#ddl_join_discipline").val() //反馈学科ID
				+'&problemUnitId='+$("#ddl_join_unit").val() //反馈学校ID
				+'&currentRoundId='+$("#ddl_feedback_round").val() //当前公示批次ID
				+'&responseType='+$("#ddl_response_type").val()
				+'&feedbackType='+feedbackType
		}).trigger("reloadGrid");
	}
	
	//初始化学科和学校下拉框，增加“全部”这一项
	function addDropdownList(){
		$("#ddl_join_discipline").append('<option value="" selected="selected">全部</option>');
		$("#ddl_join_unit").append('<option value="" selected="selected">全部</option>');
		$("#ddl_response_type").append('<option value="" selected="selected">全部</option>');
	}
	
	//判断一些列是否显示
	function isShowColumn(){
		if( feedbackStatus == "2"){
			$(jqTable).setGridParam().hideCol("centerAdvice");
			$(jqTable).setGridParam().showCol("responseType");
		}
		else if( feedbackStatus == "3"){
			$(jqTable).setGridParam().showCol("centerAdvice");
			$(jqTable).setGridParam().showCol("responseType");
		}
	}
	
	function isShowButtton(){
		if( feedbackStatus = "2"){
			
		}
	}
	
	$("#ddl_feedback_round").change(function(){
		showFeedbackMessage("${ContextPath}/feedback/centerResponse_getFeedbackRoundMessage");
		initJqTable();
	});
	
	//查看学校发生改变
	$("#ddl_join_unit").change(function(){
		unitId = $("#ddl_join_unit").val();
		initJqTable();
	});
	
	//查看学科发生改变
	$("#ddl_join_discipline").change(function(){
		discId = $("#ddl_join_discipline").val();
		initJqTable();
	});
	
	//反馈答复类型发生变化
	$("#ddl_response_type").change(function(){
		initJqTable();
	});
</script>