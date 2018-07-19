<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="responseFeedback_dv" class="table">
	<div id="dv_table">
		<table id="problem_content_tb"></table>
	</div>
	<br/>
	<div id="dv_objectionList_table" ><!-- 某条数据已提出的异议汇总 -->
		<table id="tb_same_problem_list" ></table>
		<div id="dv_same_problem_pager"></div>
	</div>
	<div>
		<table id="tb_response" class="fr_table" style="width:90%">
			<tr>
				<td align="right">
					<label id="lb_responseAdvice" class="TextFont" style="padding:0px">答复意见:</label>
				</td>
				<td align="left">
					<div id="dv_delete" >
						<input id="rd_delete" value="1" type="radio" name="responseType" />
						<label class="TextFont" style="padding:0px" for="rd_delete">删除</label>
						&nbsp;
					</div>
					<div id="dv_keep" >
						<input id="rd_keep" value="2" type="radio" name="responseType" />
						<label class="TextFont" for="rd_keep" style="padding:0px">保留</label>
						&nbsp;
					</div>
					<div id="dv_modify" >
						<input id="rd_modify" value="3" type="radio" name="responseType" />
						<label class="TextFont" for="rd_modify" style="padding:0px">修改</label>
						<label class="TextFont">:</label>
						<input type="text" disabled="disabled" id="tb_modifyValue" style="width:200px"/>
					</div>
				</td>
			</tr>
			<tr>
				<td valign="top" align="right">
					<label class="TextFont">详情:</label>
				</td>
				<td align="left">
					<textarea id="tb_responseAdvice" rows="7" cols="50"></textarea>
				</td>
			</tr>
			<tr>
				<td align="right">
				    <span class="TextFont">证明材料：</span>
				</td>
				<td align="left" colspan="4">
         			<a id="file_detail" style="color:Green;font-weight:bold;">
         			</a><a id="file_no" style="display:none"></a>
         			<a class="button" id="btn_downloadProveMaterial">下载</a>	
				</td>
			</tr>
		</table>
	</div>
</div>

<script src="${ContextPath}/js/feedback/feedback_jqgrid.js"></script>
<script type="text/javascript">

	var entityId;//某条采集数据的实体Id
	var attrId;//有问题的属性Id
	var problemItemId;//某条采集数据项的Id
	var responseItemId;//反馈答复项Id
	var responseType;//反馈答复类型
	
	function setResponseType(theType){
		responseType = theType;
	}
	
	function setEntityId(theEntityId){
		entityId = theEntityId;
	}
	
	function setProblemItemId(theId){
		problemItemId = theId;
	}
	
	function setResponseItemId(theResponseItemId){
		responseItemId = theResponseItemId;
	}
	
	function setAttrId(theAttrId){
		attrId = theAttrId;
	}
	
	$(document).ready(function(){
		$("input[type=submit], a.button , button").button();
		
	});
	
	
	//下载证明材料
	$("#btn_downloadProveMaterial").click(function(){
		if( $("#file_no").text() != null && $("#file_no").text() != "")
			pageDownload($("#file_no").text()); //该函数位于unit_feedback_gather页面
		else{
			alert_dialog("暂无证明材料");
		}
	});
	
	
	function initPage(){
		clearHtml();
		entityId = "";
		problemItemId = "";
		responseItemId = "";
		responseType = "";
	}
	
	function clearHtml(){
		$("[name='responseType']").attr("checked",false);
		$("#tb_responseAdvice").val("");
		$("#tb_modifyValue").val("");
		$("#file_no").text("");
		$("#file_detail").text("");
	}

	//初始化所有相似问题的列表
	function initAllResponseJqTable(){
		$("#tb_same_problem_list").jqGrid("GridUnload");//先卸载表格
		$("#tb_same_problem_list").jqGrid({
			url:'${ContextPath}/feedback/centerResponse_getSameResponse?'
				+'feedbackStatus='+window.feedbackStatus //异议的状态，已提交或未提交
				+'&currentRoundId='+$("#ddl_feedback_round").val()//当前公示批次ID
				+'&problemCollectItemId='+problemItemId,
			datatype:"json",
			mtype:'POST',
			colNames:['反馈类型','ID','采集项ID','数据项ID','属性ID','问题项','问题'],
			colModel:[
					{name:'feedbackType',index:'feedbackType',width:20,align:"center",editable:true},
					{name:'id',index:'id',align:"center",editable:true,hidden:true}, 
 		 		 	{name:'problemCollectEntityId',index:'problemCollectEntityId',algin:'center',hidden:true,editable:true},
   	 		 		{name:'problemCollectItemId',index:'problemCollectItemId',algin:'center',hidden:true,editable:true},
   	 		 		{name:'problemCollectAttrId',index:'problemCollectAttrId',align:'center',hidden:true,editable:true},
		            {name:'problemCollectAttrName',index:'problemCollectEntityName',align:'center',width:20},
		            {name:'problemContent',index:'problemContent',width:80,align:"center",editable:true}
		            ],
			height : '100%',
			width:window.screen.availWidth-200,
			autowidth : false,
			shrinkToFit : true,
			pager : '#dv_same_problem_pager',
			pgbuttons : true,
			rowNum : 10,
			caption:"反馈问题",
			rownumbers:true,
			viewrecords : true,
			sortname : 'problemUnitId',
			gridComplete:function(){
				//选择指定行
				$("#tb_same_problem_list").setSelection(responseItemId);
				var ids = $("#tb_same_problem_list").getDataIDs();
				for(var i=0;i < ids.length;i++){
					if( $("#tb_same_problem_list").getCell(ids[i],"feedbackType") == "重复填报"){
						$("#tb_same_problem_list").setRowData(ids[i],{problemContent:"数据重复填报"});
					}	
				}
			},
			onSelectRow:function(id){
				setResponseItemId(id);
				setAttrId($("#tb_same_problem_list").getCell(id,"problemCollectAttrId")); 
				console.log(attrId);
				clearHtml();
				$.ajax({
					url:'${ContextPath}/feedback/centerResponse_getCertainResponse',
					data:'responseItemId='+id,
					type:'POST',
					dataType:'json',
					success:function(data){
						$("#tb_responseAdvice").val(data.responseAdvice);
						var proveMaterial = data.proveMaterial;
						if( proveMaterial != null && proveMaterial != "undefined" ){
							$("#file_detail").text(proveMaterial.name);
							$("#file_no").text(proveMaterial.id);
							$("#btn_downloadProveMaterial").show();
						}
						else{
							$("#file_detail").text("暂无");
							$("#file_no").text("");
							$("#btn_downloadProveMaterial").hide();
						}
						if(data.responseType == "1"){
							$("#rd_delete").click();
							$("#dv_delete").show();
							$("#dv_keep").hide();
							$("#dv_modify").hide();
						}
						else if( data.responseType == "2"){
							$("#rd_keep").click();
							$("#dv_keep").show();
							$("#dv_delete").hide();
							$("#dv_modify").hide();
						}
						else if( data.responseType == "3"){
							$("#rd_modify").click();
							$("#dv_modify").show();
							$("#dv_delete").hide();
							$("#dv_keep").hide();
							$("#tb_modifyValue").val(data.adviceValue);
						}
					},
					error:function(data){
						alert('失败');
					}
				});
			},
			loadComplete : function() {
				showNoRecords();
				/* $("#tb_same_problem_list").setGridWidth(
						$("#content").width()-100); */
			},
			jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
				root : 'rows', //包含实际数据的数组  
				page : 'pageIndex', //当前页  
				total : 'totalPage',//总页数  
				records : 'totalCount', //查询出的记录数  
				repeatitems : false
			}
		}).navGrid('#dv_same_problem_pager',{edit:false,add:false,del:false
		});
	}
	
	

	
	//打开反馈合并对话框
	function openResponseFeedbackDialog(){
		$('#responseFeedback_dv').dialog({
	    	title:"反馈答复",
	   	 	height:'650',
			width:'90%',
			position:'center',
			modal:true,
			draggable:true,
	    	hide:'fade',
			show:'fade',
	    	autoOpen:true,
	    	close:function(){
	    	
	    	},
	    	buttons:{  
            	"关闭":function(){
            		initPage();
            		$(jqTable).trigger("reloadGrid");
            		$(this).dialog("close");		
            	}
	   	 }}); 
	}
	

	
	//初始化采集数据
	function initCollectData(){
		$("#problem_content_tb").jqGrid('GridUnload');
		$.ajax({
			type:'POST',
			dataType:'json',
			url:"${ContextPath}/feedback/centerResponse_getViewConfig",
			data:"entityId="+entityId,
			success : function(data) {
				$.each(data.colConfigs,function(i,item){
					if(item.name=='UNIT_ID'){
						item.editable=false;
						item.hidden=false;
					}
					if(item.name=='DISC_ID'){
						item.editable=false;
						item.hidden=false;
					}
					if(item.name=="SEQ_NO"){
						item.width = 50;
					}
				});
				$("#problem_content_tb").jqGrid({
 					url :'${ContextPath}/feedback/centerResponse_getCollectData?entityId='+ entityId+'&itemIds=' + problemItemId,//取数据
					datatype : 'json',
					mtype : 'POST',
					colModel : data.colConfigs,
					height : "100%",
					width:window.screen.availWidth-200,
					autowidth : false,
					shrinkToFit : true,
					rowNum : 10,
					rowList : [10],
					viewrecords : true,
					sortname : data.defaultSortCol,
					sortorder : "asc",
					caption : data.name,
					loadComplete:function(){
						initAllResponseJqTable();
					}
				});
			}
		});
		/* $.getJSON("${ContextPath}/feedback/centerResponse_getViewConfig?entityId="+entityId, //获取某entity的元数据信息
			function initJqTable(data) {
				$.each(data.colConfigs,function(i,item){
					if(item.name=='UNIT_ID'){
						item.editable=false;
						item.hidden=false;
					}
					if(item.name=='DISC_ID'){
						item.editable=false;
						item.hidden=false;
					}
					if(item.name=="SEQ_NO"){
						item.width = 50;
					}
				});
				$("#problem_content_tb").jqGrid({
 					url :'${ContextPath}/feedback/centerResponse_getCollectData?entityId='+ entityId+'&itemIds=' + problemItemId,//取数据
					datatype : 'json',
					mtype : 'POST',
					colModel : data.colConfigs,
					height : "100%",
					width:window.screen.availWidth-200,
					autowidth : false,
					shrinkToFit : true,
					rowNum : 10,
					rowList : [10],
					viewrecords : true,
					sortname : data.defaultSortCol,
					sortorder : "asc",
					caption : data.name,
					loadComplete:function(){
						initAllResponseJqTable();
					}
				});
		}); */
	}
	
	function getNoEntityItemFeedback(){
		$.ajax({
			url:'${ContextPath}/feedback/centerResponse_getCertainResponse',
			data:'responseItemId='+responseItemId,
			type:'POST',
			dataType:'json',
			success:function(data){
				$("#tb_responseAdvice").val(data.responseAdvice);
				var proveMaterial = data.proveMaterial;
				if( proveMaterial != null && proveMaterial != "undefined" ){
					$("#file_detail").text(proveMaterial.name);
					$("#file_no").text(proveMaterial.id);
					$("#btn_downloadProveMaterial").show();
				}
				else{
					$("#file_detail").text("暂无");
					$("#file_no").text("");
					$("#btn_downloadProveMaterial").hide();
				}
			},
			error:function(data){
				alert('失败');
			}
		});
	}
	
	function hideAdvice(){
		$("#dv_modify").hide();
		$("#dv_delete").hide();
		$("#dv_keep").hide();
		$("#lb_responseAdvice").hide();
	}
	
	function showAdvice(){
		$("#dv_modify").show();
		$("#dv_delete").show();
		$("#dv_keep").show();
		$("#lb_responseAdvice").show();
	}
	
	//查看对于某采集数据项提出的所有问题
	function responseFeedback(responseItemId,theEntityId,theProblemCollectItemId){
		setResponseItemId(responseItemId);
		setEntityId(theEntityId);
		setProblemItemId(theProblemCollectItemId);
		if( theEntityId != ""){
			showAdvice();
			initCollectData(); //显示有问题的那条采集数据
		}
		else{
			hideAdvice();
			getNoEntityItemFeedback();
		}
		openResponseFeedbackDialog();
	}
	
</script>