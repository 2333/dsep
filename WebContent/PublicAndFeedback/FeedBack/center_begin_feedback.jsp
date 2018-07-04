<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="con_header inner_table_holder">
	<table class="layout_table">
		<tr>
			<td>
				<span class="icon icon-web"></span>
				<span class="TextFont">反馈数据源配置</span>
			</td>
		</tr>
	</table>
</div>
<div class="con_header inner_table_holder">
	<table class="layout_table right">
		<tr>
			<td>
				<c:choose>
					<c:when test="${empty currentRound}">
						<a class="button" type="submit" href="#" id="btn_open_feedback"> <span
						class="icon icon-backup"></span>新建反馈
					</a>
					</c:when>
					<c:when test="${currentRound.status == 1}">
						<a class="button" href="#" id="btn_immediate_feedback"> <span
						class="icon icon-backup"></span>立即反馈
						</a>
						&nbsp;
						<a class="button" href="#" id="btn_begin_feedback"> <span
						class="icon icon-backup"></span>反馈配置
						</a>
						&nbsp;
						<a id="btn_process" class="button" href="#">
			         		<span class="icon icon-export"></span>查看重复数据
			         	</a>
				 	</c:when>
					<c:when test="${currentRound.status == 2}">
						<span class="TextFont">反馈正在进行中</span>
					</c:when>
					<c:otherwise>
					 </c:otherwise>
				</c:choose> 
			</td>
		</tr> 
	</table>
</div>


<div id="dialog-confirm" title="警告">
</div>

<div id="div_feedback_layout" class="layout_holder" style="width:100%">
	<c:if test="${!empty currentRound}">
		<!-- 反馈类型的树结构 -->
		<jsp:include page="../TreeAndView/_feed_type_tree.jsp"></jsp:include>
		
		<div id="dv_parent" class="selectbar right_block" hidden="true">
			<div class="inner_table_holder">
				<table class="left">
			     	<tr>
			     		<td>
				     		<jsp:include page="../_ddl_join_unit.jsp"></jsp:include>
				     		&nbsp;
				     		<jsp:include page="../_ddl_join_discipline.jsp"></jsp:include>
			     		</td>
			     	</tr>
			     </table>
			     <table class="right" id="tb_export">
			     	<tr>
			     		<td>
			     			<a id="btn_import" class="button" href="#">
				         		<span class="icon icon-import"></span>导入数据
				         	</a>
			     			<a id="btn_export" class="button" href="#">
				         		<span class="icon icon-export"></span>导出数据
				         	</a>
				         	
			     		</td>
			     	</tr>
			     </table>
			</div>
			<div id="dv_feedbackResource">
				<div class="table">
					<table id="tb_dataSource_list"></table>
					<div id="dv_dataSource_pager"></div>
				</div>
			</div>
		</div>								
	</c:if>			
</div>


<script src="${ContextPath}/js/feedback/feedback_jqgrid.js"></script>
<script type="text/javascript">
	var unitId;
	var discId;
	var feedbackStatus="1";
	var currentFeedbackRoundId = "${currentRound.id}";
	var backupVersionId = "${currentRound.backupVersionId}";
	var feedbackType;
	var jqTable = "#tb_dataSource_list";
	var jqCaption = "";
	var feedbackRoundStatus = "${currentRound.status}";
	
	$(document).ready(function() {	
		
		isShowButton();
		addDropdownList(); //给学校和学科下拉框增加“全部”这一项
		
		$("input[type=submit], a.button , button").button();
		
	});
	
	//是否第一个日期大于第二个
	function isFirstOverSecond(firstDate,secondDate){
		if( firstDate > secondDate ){
			return true;
		}
		else{
			return false;
		}
	}
	
	//初始化反馈数据表
	function initJqTable(){
		$(jqTable).jqGrid('GridUnload');
		$(jqTable).jqGrid({
			url:'${ContextPath}/feedback/beginRound_getResponse?'
				+'feedbackStatus='+feedbackStatus //异议的状态，已提交或未提交
				+'&problemDiscId='+$("#ddl_join_discipline").val()//被异议的学科ID
				+'&problemUnitId='+$("#ddl_join_unit").val()//被异议的学科ID
				+'&currentRoundId='+currentFeedbackRoundId//当前公示批次ID
				+'&feedbackType='+feedbackType,
			datatype:'json',
			mtype:'POST',
			colNames:['删除','ID','是否是唯一反馈项','采集项ID','数据项ID','属性ID','学校','${textConfiguration.disc}','采集项','问题项','关键项','序号','问题','问题'],
			colModel:[
				{name:'cancel',index:'cancel',width:60,align:'center',sortable:false},
		 		{name:'id',index:'id',align:"center",editable:true,hidden:true}, 
		 		{name:'sameItemNumber',index:'sameItemNumber',align:"center",editable:true,hidden:true},
			 	{name:'problemCollectEntityId',index:'problemCollectEntityId',algin:'center',hidden:true,editable:true},
				{name:'problemCollectItemId',index:'problemCollectItemId',algin:'center',hidden:true,editable:true},
				{name:'problemCollectAttrId',index:'problemCollectAttrId',align:'center',hidden:true,editable:true},
				{name:'problemUnitId',index:'problemUnitId',align:'center',width:70},
	            {name:'problemDiscId',index:'problemDiscId',align:'center',width:70},
	            {name:'problemCollectEntityName',index:'problemCollectEntityName',align:'center',width:300},
	            {name:'problemCollectAttrName',index:'problemCollectEntityName',align:'center',width:200},
	            {name:'importantAttrValue',index:'importantAttrValue',align:'center',width:300},
	            {name:'problemSeqNo',index:'problemSeqNo',align:'center',width:60},
	            {name:'problemContent',index:'problemContent',width:600,align:"center",editable:true,sortable:false},
	            {name:'similarityProblemContent',index:'similarityProblemContent',width:600,align:"center",editable:true,sortable:false,hidden:true}
	        ],
			height : '100%',
			width:$("#content").width(),
			autowidth : false,
			shrinkToFit : true,
			pager : '#dv_dataSource_pager',
			pgbuttons : true,
			rowNum : 20,
			caption:jqCaption,
			rownumbers:true,
			rowList : [ 10, 20, 30 ],
			viewrecords : true,
			sortname : 'problemUnitId',
			gridComplete:function(){ //给表格添加编辑列和删除列
				if( showNoRecords(jqTable)){
					var ids = $(jqTable).jqGrid('getDataIDs');
					if( window.feedbackRoundStatus == "1" ){
						addDeleteCol(ids);
					}
					else{
						$(jqTable).setGridParam().hideCol("cancel");
					}
					if( feedbackType == "3"){ //如果是重复数据则隐藏问题列
						$(jqTable).setGridParam().hideCol('problemContent');
						$(jqTable).setGridParam().showCol('similarityProblemContent');
					}
					else{
						$(jqTable).setGridParam().showCol('problemContent');
						$(jqTable).setGridParam().hideCol('similarityProblemContent');
					}
					
				}
			},
			loadComplete : function() {
			},
/////////////////////////////////展示数据详情
			subGrid: true,
			subGridRowExpanded: function(subgrid_id, row_id) {
				if( feedbackType != 3){
					showSubGridData(subgrid_id,row_id,
						'${ContextPath}/feedback/beginRound_getViewConfig',
						'${ContextPath}/feedback/beginRound_getCollectData');	
				}
				else{
					showSimilarityData(subgrid_id,row_id,
						'${ContextPath}/feedback/beginRound_getViewConfig',
						'${ContextPath}/feedback/beginRound_getCollectData');
				}
				
			},
			jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
				root : 'rows', //包含实际数据的数组  
				page : 'pageIndex', //当前页  
				total : 'totalPage',//总页数  
				records : 'totalCount', //查询出的记录数  
				repeatitems : false
			}}).navGrid('#dv_dataSource_pager',{edit:false,add:false,del:false
		});
	}
	
	
	
	//给jqGrid增加删除列
	function addDeleteCol(ids){
		
		for(var i=0; i < ids.length; i++){
			var deleteRow = "<a href='#' onclick=\"deleteRow('"+ids[i]+"');\">删除</a>";
			$(jqTable).setRowData(ids[i],{cancel:deleteRow});	
			
			if( $(jqTable).getCell(ids[i],"problemSeqNo")+"" == "0"){
				$(jqTable).setRowData(ids[i],{problemSeqNo:'/'});
			}
		}
	}
	
	
	//是否显示一些按钮
	function isShowButton(){
		if( feedbackRoundStatus == "1"){ //正在配置反馈数据
			$("#btn_import").show();
			$("#btn_export").show();
			$("#btn_process").show();
		}
		else{
			$("#btn_import").hide();
			$("#btn_process").hide();
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
	
	
	//加载反馈数据表
	function loadFeedbackJqTable(){
		$(jqTable).setGridParam({
			url:'${ContextPath}/feedback/beginRound_getResponse?'
				+'problemDiscId='+$("#ddl_join_discipline").val()//被异议的学科ID
				+'&problemUnitId='+$("#ddl_join_unit").val()//被异议的学科ID
				+'&currentRoundId='+currentFeedbackRoundId//当前公示批次ID
				+'&feedbackType='+feedbackType,
			datatype:"json",
		}).trigger("reloadGrid");
	}
	
	
	//导入反馈数据源
	function importFeedbackSource(){
		$.ajax({
   			url:'${ContextPath}/feedback/beginRound_importObjection',
   			type:'POST',
   			data:'feedbackType='+feedbackType+'&feedbackRoundId='+currentFeedbackRoundId,
   			success:function(data){
   				if( data >= 0){
   					/* console.log(data); */
   					alert_dialog('成功导入'+data+'条数据');
   					initJqTable();
   				}
   				else{
   					alert_dialog('导入失败');
   				}
   			},
   			error:function(data){
   				alert_dialog('出现错误,请重新导入');
   			}
   		});
	}
	
	//初始化学科和学校下拉框，增加“全部”这一项
	function addDropdownList(){
		$("#ddl_join_discipline").append('<option value="" selected="selected">全部</option>');
		$("#ddl_join_unit").append('<option value="" selected="selected">全部</option>');
	}
	
	
	//删除某一列，实际上仅改变其状态位，将其变为已删除
 	function deleteRow(id){
 		$("#dialog-confirm").empty().append("<p>确定删除吗？</p>");
		$("#dialog-confirm").dialog({
     	      height:150,
     	      buttons: {
     	        "确定":function(){
     	        	var feedbackId = $(jqTable).getCell(id,"id");
     	 	 		$.ajax({
     	 	 			url:'${ContextPath}/feedback/beginRound_deleteRow',
     	 	 			type:"POST",
     	 	 			data:'feedbackId='+feedbackId,
     	 	 			success:function(data){
     	 	 				if( data ){
     	 	 					alert_dialog('删除成功');
     	 	 					initJqTable();
     	 	 				}
     	 	 				else{
     	 	 					alert_dialog('删除失败');
     	 	 					initJqTable();
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
	
	//开启反馈
	$("#btn_open_feedback").click(function(){
		$("#dialog-confirm").empty().append("<p>确定新建批次吗</p>");
		$("#dialog-confirm").dialog({
     	      height:150,
     	      buttons: {
     	        "确定":function(){
					$.ajax({
						type:'POST',
						url:'${ContextPath}/feedback/beginRound_openNewRound',
						success:function(data){
							if( data == true ){
								alert_dialog("反馈已建立");
								$.post('${ContextPath}/feedback/beginRound', function(data){
									  $( "#content" ).empty();
									  $( "#content" ).append( data );
									  documentReady();
								  }, 'html');
								initDiv("#div_feedback_layout","#dv_feedbackResource"); //函数位于TreeAndView/_feed_type_tree.jsp页面
							}
							else{
								alert_dialog("批次建立失败,出现错误,请联系管理员");
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
	});
	
	//导入数据源
	$("#btn_import").click(function(){
		if($(jqTable).getGridParam("reccount") > 0){
			$("#dialog-confirm").empty().append("<p>重新导入会删除已有数据，确定重新导入吗？</p>");
			$("#dialog-confirm").dialog({
	     	      height:150,
	     	      buttons: {
	     	        "确定":function(){
	     	        	importFeedbackSource();
	     	        	$(this).dialog("close");
	     	        },
					"取消":function(){
						$(this).dialog("close");
					}
	     	      }
			});
		}
		else{
			importFeedbackSource();
		}
	});
	
	$("#btn_process").click(function(){
		$.ajax({
			url:"${ContextPath}/feedback/beginRound_processSameProblem",
			type:"POST",
			data:"unitId="+$("#tb_choosenUnit").val()+"&discId="+$("#tb_choosenDiscipline").val(),
			success:function(data){
				 $( "#content" ).empty();
				 $( "#content" ).append(data);
			}
		});
	});
	
	
	//打开进行反馈的对话框
	$("#btn_begin_feedback").click(function(){
		beginFeedbackDialog("${ContextPath}/feedback/beginRound_beginFeedbackDialog");
	});
	
	//进行反馈的对话框
	function beginFeedbackDialog(url){
		$.post(url, function(data){
 		  	$("#dialog").empty();
 		  	$("#dialog").append( data );
 	 	  	$('#dialog').dialog({
	    		title:"进行反馈",
 	  		    height:'230',
 	  			width:'750',
 	  			position:'center',
 	  			modal:true,
 	  			draggable:true,
 	  		    hide:'fade',
 	  			show:'fade',
 	  		    autoOpen:true,
 	  		    buttons:{  
  		    		"确定":function(){	 
  		    			if( !isDate10($("#input_beginTime").val())||!isDate10($("#input_endTime").val())){ //日期是否合法
  		    				alert_dialog('请输入yyyy-mm-dd格式的合法日期');
  		    				return;
  		    			}
  		    			
  		    			var beginDateArray = $("#input_beginTime").val().split("-");
  		    			var endDateArray = $("#input_endTime").val().split("-");
  		    			var currentDate = new Date();
  		    			var beginSetDate = new Date(beginDateArray[0],beginDateArray[1]-1,beginDateArray[2]);
  		    			var endSetDate = new Date(endDateArray[0],endDateArray[1]-1,endDateArray[2]);
  		    			
  		    			if(!isFirstOverSecond(beginSetDate,currentDate)){
  		    				alert_dialog('开始日期应大于当前日期');
  		    				return;
  		    			}
  		    			if(!isFirstOverSecond(endSetDate,currentDate)){
  		    				alert_dialog('结束日期应大于当前日期');
  		    				return;
  		    			}
  		    			if(!isFirstOverSecond(endSetDate,beginSetDate)){
  		    				alert_dialog('结束日期应大于开始日期');
  		    				return;
  		    			}
  		    			if($("#input_feedbackName").val() == ""){
  		    				alert_dialog('请输入反馈名称');
  		    				return;
  		    			}
  		    			
	  	                var fmstr=$('#beginFeedback_fm').serialize();
	  	         		$.ajax({
	 	  	      			type:'POST',
	 	  	      			url:'${ContextPath}/feedback/beginRound_beginFeedback',
	 	  	      			data:fmstr,
	  	      				success: function(data){
		  	      				if(data==true){
	  	      	   		    		alert_dialog("设置成功");
		  	      					$("#dialog").dialog("close");
			  	      				$.post('${ContextPath}/feedback/beginRound', function(data){
			  						  	$( "#content" ).empty();
			  						 	$( "#content" ).append( data );
			  					  	}, 'html');
	    	  	      			}
	    	  	      			else
	    	  	      			{
		    	  	      			/* $("#dialog").dialog("close"); */
	    	  	      				alert_dialog(data);	
	    	  	      			}
	   	  	      			},
	   	  	      			error:function(data){
	   	  	      				alert(data.message);
	   	  	      			}
	  	      			});
	  	            },
	 	            "关闭":function(){
	 	            	$("#dialog").dialog("close");
 	            }
  		    }
 	 	  }); 
 	  }, 'html');
	}
	
	//立即反馈按钮
	$("#btn_immediate_feedback").click(function(){
		$.ajax({
     			type:'POST',
     			url:'${ContextPath}/feedback/beginRound_immediateFeedback',
				success: function(data){
					console.log(data);
    				if(data=="success"){
	   		    		alert_dialog("反馈成功");
	      				$.post('${ContextPath}/feedback/beginRound', function(data){
						  	$( "#content" ).empty();
						 	$( "#content" ).append( data );
					  	}, 'html');
	      			}
	      			else if(data=="notSet"){
	      				alert_dialog("请先进行反馈配置");	
	      			}
	      			},
	      			error:function(data){
	      				alert_dialog("反馈异常");
	      			}
			});
		
	});
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
</script>