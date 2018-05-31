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
			<tr class="hidden">
	             <td>
	             	<input type="hidden" id="sessionid" value="${pageContext.session.id}" />
	             </td>
            </tr>  
			<tr>
				<td align="right">
					<label id="lb_processMethod" class="TextFont" style="padding:0px">处理方式:</label>
				</td>
				<td align="left">
					<div id="dv_delete" style="display:inline-block;">
						<input id="rd_delete" value="1" type="radio" name="responseType" />
						<label class="TextFont" style="padding:0px" for="rd_delete">删除</label>
						&nbsp;
					</div>
					<div id="dv_keep" style="display:inline-block;">
						<input id="rd_keep" value="2" type="radio" name="responseType" />
						<label class="TextFont" for="rd_keep" style="padding:0px">保留</label>
						&nbsp;
					</div>
					<div id="dv_modify" style="display:inline-block;">
						<input id="rd_modify" value="3" type="radio" name="responseType" />
						<label class="TextFont" for="rd_modify" style="padding:0px">修改</label>
						<label class="TextFont">:</label>
						<input type="text" id="tb_modifyValue" style="width:200px"/>
					</div>
				</td>
			</tr>
			<tr>
				<td valign="top" align="right">
					<label class="TextFont">处理意见:</label>
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
					<input id="file_upload" name="file_upload" type="file">
					<div id="fileQueue"></div>
					<a class="button" id="upload">上传</a>| 
         			<a class="button" id="cancel">取消上传</a>	|
         			<a class="button" id="delete">删除证明材料</a>	
         			<b>您上传的证明材料：</b><a id="file_detail" style="color:Green;font-weight:bold;">
         			</a><a id="file_no" style="display:none"></a>
         			<a class="button" id="btn_downloadProveMaterial">下载</a>	
				</td>
			</tr>
		</table>
	</div>
</div>

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
		console.log('entityid:'+entityId);
	}
	
	function setProblemItemId(theId){
		problemItemId = theId;
		console.log('problemItemid:'+problemItemId);
	}
	
	function setResponseItemId(theResponseItemId){
		responseItemId = theResponseItemId;
	}
	
	function setAttrId(theAttrId){
		attrId = theAttrId;
	}
	
	$(document).ready(function(){
		$("input[type=submit], a.button , button").button();
		
		$("#file_upload").uploadify({  
            'swf'            : '${ContextPath}/js/uploadify/uploadify.swf',  
            'uploader'       : '${ContextPath}/feedback/feedResponse_uploadFile?jsessionid=' + $("#sessionid").val(),//后台处理的请求
            'queueID'        : 'fileQueue',
            'queueSizeLimit' : 1,  
            'auto'           : false,
            'buttonText'     : '证明材料',
            'onUploadSuccess':function(file,data,response){
            	$("#file_detail").html(file.name);
            	$("#file_no").html(data);
            	$.ajax({
    				url:'${ContextPath}/feedback/feedResponse_saveProveMaterial',
    				data:'responseItemId='+responseItemId+
    					'&proveMaterialId='+$("#file_no").text(),
    				type:'POST',
    				success:function(data){
    					if( data ){
    						alert_dialog('上传成功');	
    						$("#btn_downloadProveMaterial").show();
    					}
    				}
    			});
            }
        });
		
	});
	
	//附件上传
	$('#upload').click(function(){
 		$('#file_upload').uploadify('upload');
	});
 	
	//附件取消
 	$('#cancel').click(function(){
 		$('#file_upload').uploadify('cancel');
	});
	
	//下载证明材料
	$("#btn_downloadProveMaterial").click(function(){
		if( $("#file_no").text() != null && $("#file_no").text() != "")
			pageDownload($("#file_no").text()); //该函数位于unit_feedback_gather页面
		else{
			alert_dialog("暂无证明材料");
		}
	});
	
 	
	//附件删除
 	$('#delete').click(function(){
 		if($("#file_no").text() == ""){
 			alert_dialog("尚无证明材料");
 		}
 		else{
 			$("#dialog-confirm").empty().append("<p>确定删除吗？</p>");
 			$("#dialog-confirm").dialog({
 		 	      height:150,
 		 	      buttons: {
 		 	        "确定":function(){
 		 	        	$.post(
 		 	       			'${ContextPath}/feedback/feedResponse_fileDelete',
 		 	       			{
 		 	       				attachmentId:$("#file_no").text(),
 		 	       				responseItemId:window.responseItemId
 		 	       			},
 		 	       			function(data){
 		 	       	 			if(data){
 		 	       	 				alert_dialog("证明材料已删除！");
 		 	       	 				$("#file_detail").text("暂无");
 		 	       	 				$("#file_no").text("");
 		 	       	 				$("#btn_downloadProveMaterial").hide();
 		 	       	 			}
 		 	       	 			else{
 		 	       	 				alert_dialog("尚无证明材料或证明材料删除失败！");
 		 	       	 			}
 		 	        			}, 
 		 	       	 	'json');
 		 	 	 		$(this).dialog("close");
 					},
 					"取消":function(){
 						$(this).dialog("close");
 					}
 		      	}
 			});
 		}
	});
	
	
	function initPage(){
		clearHtml();
		entityId = "";
		problemItemId = "";
		responseItemId = "";
		responseType = "";
		var queueLength = $('#file_upload').data('uploadify').queueData.queueLength;
		if( queueLength > 0 ){
			$('#file_upload').uploadify('cancel');
		}
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
			url:'${ContextPath}/feedback/feedResponse_getSameResponse?'
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
		            {name:'problemContent',index:'problemContent',width:80,align:"center",editable:true},
		            ],
			height : '100%',
			width:window.screen.availWidth-200,
			autowidth : false,
			shrinkToFit : true,
			pager : '#dv_same_problem_pager',
			pgbuttons : true,
			rowNum : 10,
			rowList : [ 10, 20, 30 ],
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
				if( attrId == "/"){
					$("#dv_modify").hide();
				}
				else{
					$("#dv_modify").show();
				}
				clearHtml();
				ajaxQueryResponse();
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
	
	
	function ajaxQueryResponse(){
		$.ajax({
			url:'${ContextPath}/feedback/feedResponse_getCertainResponse',
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
				if(data.responseType == "1"){
					$("#rd_delete").click();
				}
				else if( data.responseType == "2"){
					$("#rd_keep").click();
				}
				else if( data.responseType == "3"){
					$("#rd_modify").click();
					$("#tb_modifyValue").val(data.adviceValue);
				}
			},
			error:function(data){
				alert('出现错误');
			}
		});
	}
	
	//答复类型的radioButton点击事件
	$("[name='responseType']").click(function(){
		setResponseType($(this).val());
		if( $(this).attr("id") == "rd_modify"){
			$("#tb_modifyValue").attr("disabled",false);
		}
		else{
			$("#tb_modifyValue").attr("disabled",true);
			$("#tb_modifyValue").val("");
		}
	});
	
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
	    		"保存":function(){
	    			if( responseType == "3"){ //答复意见为修改
	    				if( $("#tb_modifyValue").val() == ""){ //没有填写修改值
	    					alert_dialog("请填写修改值");
	    				}
	    				else{ //进行逻辑检查
	    					$.ajax({
	    						url:'${ContextPath}/feedback/feedResponse_logicCheck',
	    						data:'entityId='+entityId+
	    							'&attrId='+attrId+
	    							'&attrValue='+$("#tb_modifyValue").val(),
	    						type:'POST',
	    						success:function(data){
	    							if( data == "CHECKPASS"){ //逻辑检查通过
	    								ajaxSave(); //保存反馈答复
	    							}
	    							else{
	    								alert_dialog(data);
	    							}
	    						},
	    						error:function(data){
	    							alert_dialog("出现错误");
	    						}
	    					});
	    				}
	    			}
	    			//对于同一数据项的反馈意见有多条时，如果其中一条的意见为删除，那么所有的均为删除
	    			else if( responseType == "1" && $("#tb_same_problem_list").getGridParam("reccount") > 1){
	    				$("#dialog-confirm").empty().append("<p>确定意见为删除吗？此数据项的其他反馈项的意见都将变为删除</p>");
	    	 			$("#dialog-confirm").dialog({
	    	 		 	      height:150,
	    	 		 	      buttons: {
	    	 		 	        "确定":function(){
				    				$.ajax({
			    						url:'${ContextPath}/feedback/feedResponse_saveDeleteResponse',
			    						data:'problemEntityItemId='+problemItemId+
			    							'&responseItemId='+responseItemId+
				    						'&responseType='+responseType+
				    						'&adviceValue='+$("#tb_modifyValue").val()+
				    						'&responseAdvice='+$("textarea#tb_responseAdvice").val()+
				    						'&feedbackRoundId='+$("#ddl_feedback_round").val(),
				    					type:'POST',
			    						success:function(data){
			    							if( data == true ){
			    								alert_dialog("保存成功");
			    							}
			    						},
			    						error:function(data){
			    							
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
	    			else{
	    				ajaxSave();
	    			}
	    		},
            	"关闭":function(){
            		initPage();
            		$(jqTable).trigger("reloadGrid");
            		$(this).dialog("destroy");		
            	}
	   	 }}); 
	}
	
	//保存反馈答复操作
	function ajaxSave(){
		$.ajax({
			url:'${ContextPath}/feedback/feedResponse_saveResponse',
			data:'responseItemId='+responseItemId+
				'&responseType='+responseType+
				'&adviceValue='+$("#tb_modifyValue").val()+
				'&responseAdvice='+$("textarea#tb_responseAdvice").val(),
			type:'POST',
			success:function(data){
				alert_dialog('保存成功');
			}
		});
	}
	
	//有些反馈问题不针对具体的某条数据，比如对学科整体提出的异议，对于这些问题没有处理方式，只有处理意见
	//处理方式即保留、修改、删除；处理意见为具体的文本内容
	function processNoneEntityProblem(){
		$("#tb_same_problem_list").jqGrid("GridUnload");
		$("#lb_processMethod").hide();
		$("#dv_modify").hide();
		$("#dv_keep").hide();
		$("#dv_delete").hide();
		ajaxQueryResponse();
		return;
	}
	
	function processEntityProblem(){
		$("#lb_processMethod").show();
		$("#dv_modify").show();
		$("#dv_keep").show();
		$("#dv_delete").show();
	}
	
	//初始化采集数据
	function initCollectData(){
		$("#problem_content_tb").jqGrid('GridUnload');
		if( entityId == ''){
			processNoneEntityProblem();
		}
		else{
			processEntityProblem();
		}
		$.getJSON("${ContextPath}/feedback/feedResponse_getViewConfig?entityId="+entityId, //获取某entity的元数据信息
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
 					url :'${ContextPath}/feedback/feedResponse_getCollectData?'+
 							'entityId='+ entityId+
 							'&itemIds=' + problemItemId+
 							'&backupVersionId='+backupVersionId,//取数据
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
		});
	}
	
	//查看对于某采集数据项提出的所有问题
	function responseFeedback(responseItemId,theEntityId,theProblemCollectItemId){
		setResponseItemId(responseItemId);
		setEntityId(theEntityId);
		setProblemItemId(theProblemCollectItemId);
		initCollectData(); //显示有问题的那条采集数据	
		openResponseFeedbackDialog();
	}
	
</script>