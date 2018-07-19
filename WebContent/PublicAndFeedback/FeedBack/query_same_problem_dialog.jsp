<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="querySameResponse_dv" class="table">
	<div id="dv_table">
		<table id="problem_content_tb"></table>
	</div>
	<br/>
	<div id="dv_objectionList_table" ><!-- 某条数据已提出的异议汇总 -->
		<table id="tb_same_problem_list" ></table>
		<div id="dv_same_problem_pager"></div>
	</div>
</div>


<div id="dialog-confirm" title="警告">
</div>


<script type="text/javascript">

	var entityId;//某条采集数据的实体Id
	var problemItemId;//某条采集数据项的Id
	
	function setEntityId(theEntityId){
		entityId = theEntityId;
	}
	
	function setProblemItemId(theId){
		problemItemId = theId;
	}

	//重新加载表格
	function loadSameProblemGrid(){
		$("#tb_same_problem_list").setGridParam({
			url:'${ContextPath}/feedback/beginRound_getSameResponse?'
				+'feedbackStatus='+feedbackStatus //异议的状态，已提交或未提交
				+'&currentRoundId='+currentFeedbackRoundId//当前公示批次ID
				+'&problemCollectItemId='+problemItemId	
		}).trigger("reloadGrid");
	}

	function deleteSameRow(id){
		console.log('delete begin');
		$("#dialog-confirm").empty().append("<p>确定删除吗？</p>");
		$("#dialog-confirm").dialog({
	 	      height:150,
	 	      buttons: {
	 	        "确定":function(){
	 	        	var feedbackId = $("#tb_same_problem_list").getCell(id,"id");
	 	        	console.log(feedbackId);
	 	 	 		$.ajax({
	 	 	 			url:'${ContextPath}/feedback/beginRound_deleteRow',
	 	 	 			type:"POST",
	 	 	 			data:'feedbackId='+feedbackId,
	 	 	 			success:function(data){
	 	 	 				if( data ){
	 	 	 					alert_dialog('删除成功');
	 	 	 					loadSameProblemGrid();
	 	 	 				}
	 	 	 				else{
	 	 	 					alert_dialog('删除失败');
	 	 	 					loadSameProblemGrid();
	 	 	 					
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

	//初始化所有相似问题的列表
	function initAllResponseJqTable(){
		$("#tb_same_problem_list").jqGrid("GridUnload");//先卸载表格
		$("#tb_same_problem_list").jqGrid({
			url:'${ContextPath}/feedback/beginRound_getSameResponse?'
				+'feedbackStatus='+feedbackStatus //异议的状态，已提交或未提交
				+'&currentRoundId='+currentFeedbackRoundId//当前公示批次ID
				+'&problemCollectItemId='+problemItemId,
			datatype:"json",
			mtype:'POST',
			colNames:['删除','反馈类型','ID','采集项ID','数据项ID','属性ID','问题项','问题'],
			colModel:[
					{name:'cancel',index:'cancel',width:10,align:'center',sortable:false},
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
			rowNum : 20,
			caption:"合并数据",
			rownumbers:true,
			rowList : [ 10, 20, 30 ],
			viewrecords : true,
			sortname : 'problemUnitId',
			gridComplete:function(){//给表格添加编辑列和删除列
				var ids = $("#tb_same_problem_list").jqGrid('getDataIDs');
				if( window.feedbackRoundStatus == "1"){
					for(var i=0; i < ids.length; i++){
						var deleteRow = "<a href='#' onclick=\"deleteSameRow('"+ids[i]+"');\">删除</a>";
						$("#tb_same_problem_list").setRowData(ids[i],{cancel:deleteRow});	
						
						var theSeqNo = $("#tb_same_problem_list").getCell(ids[i],"problemSeqNo")+""; 
						if( theSeqNo == "0" || theSeqNo == "-1"){
							$("#tb_same_problem_list").setRowData(ids[i],{problemSeqNo:'/'});
						}
						
						var cellFeedbackType = $("#tb_same_problem_list").getCell(ids[i],"feedbackType")+"";
						if( cellFeedbackType == "重复填报"){
							$("#tb_same_problem_list").setRowData(ids[i],{problemContent:"数据重复填报"});	
						}
					}	
				}
				
				else{
					$("#tb_same_problem_list").setGridParam().hideCol("cancel");
					 $("#tb_same_problem_list").setGridWidth(window.screen.availWidth-200);
				}
			},
			loadComplete : function() {
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
	
	$(document).ready(function(){
		$("input[type=submit], a.button , button").button();
	});
	
	//打开反馈合并对话框
	function openQuerySameResponseDialog(){
		$('#querySameResponse_dv').dialog({
	    	title:"反馈合并",
	   	 	height:'500',
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
            		setProblemItemId("");
            		$(jqTable).trigger("reloadGrid");
            		$(this).dialog("close");		
            	}
	   	 }}); 
	}
	
	//初始化采集数据
	function initCollectData(){
		$.ajax({
			type:'POST',
			dataType:'json',
			url:"${ContextPath}/feedback/beginRound_getViewConfig",
			data:"entityId="+entityId,
			success : function(data) {
				/* console.log(data); */
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
				console.log('entityId='+entityId+'&itemIds=' + problemItemId+'&backupVersionId='+backupVersionId);
				$("#problem_content_tb").jqGrid('GridUnload');
				$("#problem_content_tb").jqGrid({
					url :'${ContextPath}/feedback/beginRound_getCollectData?entityId='
 						+ entityId+'&itemIds=' + problemItemId+'&backupVersionId='+backupVersionId,//取数据
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
		/* $.getJSON("${ContextPath}/feedback/beginRound_getViewConfig?entityId="+entityId, //获取某entity的元数据信息
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
 					url :'${ContextPath}/feedback/beginRound_getCollectData?entityId='
 						+ entityId+'&itemIds=' + problemItemId+'&backupVersionId='+backupVersionId,//取数据
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
					loadComplete: function(data){
						console.log('finish');
						initAllResponseJqTable();//对于某条采集数据，初始化其所有反馈问题的列表		
					}
				}); */
		/* }); */
	}
	
	//查看对于某采集数据项提出的所有问题
	function querySameProblem(theEntityId,theProblemItemId){
		console.log(theEntityId+"  "+theProblemItemId);
		setEntityId(theEntityId);
		setProblemItemId(theProblemItemId);
		initCollectData();//显示有问题的那条采集数据
		openQuerySameResponseDialog();
	}
	
</script>