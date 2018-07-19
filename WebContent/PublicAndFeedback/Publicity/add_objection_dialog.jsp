<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
<META HTTP-EQUIV="Expires" CONTENT="0">
<div id="addObjection_dv" class="table">
	<div id="dv_table">
		<table id="object_content_tb"></table>
	</div>
	<br/>
	<div id="dv_objectionList_table" ><!-- 某条数据已提出的异议汇总 -->
		<table id="tb_objection_list" ></table>
	</div>
	<br />
	<table id="objection_tb" class="fr_table" style="width:90%;">
		<tr class="hidden">
            <td>
            	<input type="hidden" id="sessionid" value="${pageContext.session.id}" />
            </td>
        </tr>  
		<tr>
			<td align="right">
				<label>异议类型：</label>
			</td>
			<td align="left">
				<select id="ddl_object_type" style="float:left">
				
				</select>&nbsp;
			</td>
		</tr>
		<tr>
			<td valign="top" align="right">
				<label>异议内容：</label>
			</td>
			<td align="left">
				<textarea rows="5" cols="60" id="ta_objectionContent">
				</textarea>
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

<div id="dialog-confirm" title="警告">
</div>

<script src="${ContextPath}/js/fileOper/excel_oper.js"></script>
<script src="${ContextPath}/js/fileOper/filename_oper.js"></script>
<script src="${ContextPath}/js/uploadify/jquery.uploadify.js"></script>
<script src="../js/edit_jqgrid.js"></script>

<style>
	.progress { 
		position:relative; margin-top:5px; width:400px; border: 1px solid #ddd; height:20px; padding:1px;border-radius: 3px; 
	}
	.bar { 
		background-color: #B4F5B4; width:0%; height:20px; border-radius: 3px; 
	}
	.percent { 
		position:absolute; display:inline-block; top:3px; left:48%; 
	}
</style>
<script type="text/javascript">

	var filePath = "";//证明材料的路径
	var jqTable = "#tb_objection_list";
	var delUpload = true;
	var uploading = false;
	
	$(document).ready(function() {
		$("input[type=submit], a.button , button").button();
		initialPage();
		
		
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
			pageDownload($("#file_no").text()); 
		else{
			alert_dialog("暂无证明材料");
		}
	});
	
 	
	//下载证明材料
	function pageDownload(proveMaterialId){
		$.ajax({
			type:'POST',
			dataType:'json',
			url:'${ContextPath}/publicity/viewPub_getDownloadPath',
			data:'proveMaterialId='+proveMaterialId,
			success : function(data) {
				if( data != null && data != "undefined"){
					downloadProveMaterial(data);
				}
			}
		});
	}
	
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
 		 	       			'${ContextPath}/publicity/viewPub_fileDelete',
 		 	       			{
 		 	       				attachmentId:$("#file_no").text()
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
	
	
	//初始化页面，加载、提交、取消时执行
	function initialPage(){
		$(".bar").width('0%');
	 	$(".percent").html('0%');
		$("#ta_objectionContent").val("");
		$("#input_file").val("");
		$("#lb_message").text("");
		$("#file_no").text("");
		$("#file_detail").text("暂无");
		$("#btn_downloadProveMaterial").hide();
	}
	
	function closePage(){
		$(".bar").width('0%');
	 	$(".percent").html('0%');
		$("#ta_objectionContent").val("");
		$("#input_file").val("");
		$("#lb_message").text("");
		$("#file_no").text("");
		$("#file_detail").text("暂无");
		var queueLength = $('#file_upload').data('uploadify').queueData.queueLength;
		if( queueLength > 0 ){
			$('#file_upload').uploadify('cancel');
		}
		$("#btn_downloadProveMaterial").hide();
	}
	

	function uploadifyLoad(){
		$("#file_upload").uploadify({  
            'swf'            : '${ContextPath}/js/uploadify/uploadify.swf',  
            'uploader'       : '${ContextPath}/publicity/viewPub_uploadFile?jsessionid=' + $("#sessionid").val(),//后台处理的请求
            'queueID'        : 'fileQueue',
            'queueSizeLimit'  :1,  
            'auto'           : false,  
            'multi'          : true, 
            'buttonText'     : '证明材料',
            'onUploadSuccess': function(file,data,response){
            	$("#file_detail").html(file.name);
            	$("#file_no").html(data);
            	alert_dialog('上传成功');	
				$("#btn_downloadProveMaterial").show();
            }
        });
	}

	
	
	 //提出异议
	function raiseObject(id,type){
		var eleId='';
		if(type=='0'){
			eleId='jqGrid_collect_tb';	
		}
		if(type=='1'){
			eleId='jqGrid_collect_fm';
		}
		var rowData = $("#"+eleId).jqGrid("getRowData",id);
		var colModels= $("#"+eleId).getGridParam("colModel");

		initObjectJqgrid(colModels,rowData);
		$("#object_content_tb").jqGrid('addRowData',1,
				rowData);
		
		initObjectListJqgrid(rowData);//初始化已提出异议汇总的Jqgrid	
	 	//getObjectType();//动态获取异议类型
	 	$("#btn_downloadProveMaterial").hide();
	 	uploadifyLoad();//加载上传文件控件
	 	openObjectDialog();
	}
	 
	
	 
	//初始化“提出异议汇总”的Jqgrid
	function initObjectListJqgrid(rowData){
		$("#tb_objection_list").jqGrid('GridUnload');
		if(rowData['objectionCount'] > 0){
			$("#tb_objection_list").jqGrid({
				url:"${ContextPath}/publicity/viewObjection_showExistedObjection?"+
					"&currentRoundId="+$("#ddl_publicity_round").val()+
					"&objectCollectItemId="+$("#object_content_tb").jqGrid("getRowData",1).ID,
				editurl:'${ContextPath}/publicity/viewObjection_saveEditRowData',
				datatype:"json",
				mtype:'GET',
				colNames:['ID','证明材料路径','实体Id','数据项Id','提出异议学校ID','提出异议学科ID',
				          '异议内容', '异议类型','下载','编辑','删除'],
				colModel:[
	 		 		{name:'objectionId',index:'objectionId',align:"center",editable:true,hidden:true},
	 			 	{name:'proveMaterial.id',index:'proveMaterial.id',align:"center",editable:true,hidden:true},	
		 		 	{name:'objectEntityId',index:'objectEntityId',align:"center",editable:true,hidden:true},
		 			{name:'objectDataId',index:'objectDataId',align:"center",editable:true,hidden:true},
      				{name:'objectUnitId',index:'objectUnitId',align:"center",hidden:true}, 
		          	{name:'objectDiscId',index:'objectDiscId',align:"center",hidden:true}, 
		          	{name:'unitObjectContent',index:'unitObjectContent',width:70,align:"center",editable:true}, 
		          	{name:'unitObjectType',index:'unitObjectType',width:30,align:"center"},
	         	 	{name:'download',index:'download',width:13,align:'center'},
		          	{name:'edit',index:'edit',width:15,align:'center'},
 					{name:'cancel',index:'cancel',width:10,align:'center'}
		        ],
				height : '100%',
				width:window.screen.availWidth-250,
				autowidth : false,
				pgbuttons : true,
				shrinkToFit:true,
				rowNum : 20,
				rownumbers:true,
				caption: '已提出的异议',
				rowList : [ 10, 20, 30 ],
				viewrecords : true,
				sortname : 'objectDiscId',
				gridComplete:function(){//给表格添加编辑列和删除列
					var ids = $("#tb_objection_list").jqGrid('getDataIDs');
					for(var i=0; i < ids.length; i++){
						
						//下载
						
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
						var editRow = "<a href='#' id='editGrid'  onclick=\"editRow('"+jqTable+"','"+ids[i]+"');\">编辑</a>";
						$(jqTable).setRowData(ids[i],{edit:editRow});
						var deleteRow = "<a href='#' onclick=\"deleteRow('"+ids[i]+"');\">删除</a>";
						$(jqTable).setRowData(ids[i],{cancel:deleteRow});	
					}
					if( ids.length == 0 ){
						$("#dv_objectionList_table").hide();
					}
					else{
						$("#dv_objectionList_table").show();
					}
				},
				loadComplete:function(){
					getObjectType();
				},
				jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
					root : 'rows', //包含实际数据的数组  
					page : 'pageIndex', //当前页  
					total : 'totalPage',//总页数  
					records : 'totalCount', //查询出的记录数  
					repeatitems : false
				}
			});
		}
		else{
			getObjectType();
		}
	}
	
	//删除某一行，实际上仅改变其状态位，将其状态位改为已删除
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
	
	//重新加载已提出的异议表
	function loadJqgrid(){
		$(jqTable).setGridParam({
			url:"${ContextPath}/publicity/viewObjection_showExistedObjection?"+
			"&currentRoundId="+$("#ddl_publicity_round").val()+
			"&objectCollectItemId="+$("#object_content_tb").jqGrid("getRowData",1).ID
		}).trigger("reloadGrid");
	}
	 
	//对学科整体提出异议
	function openObjectDisciplineDialog(){
		$("#dv_table").hide(); //隐藏显示异议数据的表格
		$("#dv_objectionList_table").hide(); //隐藏“针对某条数据已提出异议汇总”的表格
		$("#ddl_object_type").empty();
		$("#ddl_object_type").append("<option selected='selected' value=''>学科数据整体存在问题</option>");
		uploadifyLoad();
		$('#addObjection_dv').dialog({
	    	title:"提出异议",
	   	 	height:'500',
			width:'70%',
			position:'center',
			modal:true,
			draggable:true,
	    	hide:'fade',
			show:'fade',
	    	autoOpen:true,
	    	close:function(){
	    	
	    	},
	    	buttons:{  
	    		"提交":function(){
	    			if( !isSubmit() ){
	    				return ;
	    			}
	    			var user_id = "${user.loginId}";
	    			var addUserUnitId = "";
	    			var addUserDiscId = "";
			   		switch("${user.userType}"+""){
				   		case "2"://学校用户
				   			addUserUnitId = "${user.unitId}";
				  			break;
		   				case "3"://学科用户	
							addUserUnitId = "${user.unitId}";
	    					addUserDiscId = "${user.discId}";
	    					break;
					}
			   		//传到后台的异议实体
			   		var dataString="unitObjectContent="+$("textarea#ta_objectionContent").val()+
			                   "&userId="+user_id+
			                   "&problemUnitId="+"${choosenUnitId}"+
			                   "&problemDiscId="+"${choosenDisciplineId}"+
			                   "&currentPublicRoundId="+$("#ddl_publicity_round").val()+
							   "&unitObjectType="+$("#ddl_object_type option:selected").text()+
							   "&objectUnitId="+addUserUnitId+
							   "&objectDiscId="+addUserDiscId
							   ;
					ajaxSubmitObjection(dataString);
					$(this).dialog("destroy");
    			},
            	"取消":function(){
            		closePage();
            		$(this).dialog("destroy");
            	}
	   	 }}); 
	}
	 
	//动态获取异议类型
	function getObjectType(){
		$.ajax({
			url:'${ContextPath}/publicity/viewPub_getObjectType',
			type:"POST",
			async : false,
			dataType:"json",
			data:"entityId="+tableId,
			success : function(data){
				$("#ddl_object_type").empty();
				if(!$.isEmptyObject(data)){
					$.each(data,function(i,item){
						$("#ddl_object_type").append("<option value='"+i+"'>"+item+"</option>");
					});	
				}
				$("#ddl_object_type").append("<option value=''>其他</option>");
			} 
		});
	}
	 
	//打开提出异议对话框
	function openObjectDialog(){//打开提出异议页面
		$("#dv_table").show();
		$('#addObjection_dv').dialog({
	    	title:"提出异议",
	   	 	height:'600',
			width:'85%',
			position:'center',
			modal:true,
			draggable:true,
	    	hide:'fade',
			show:'fade',
	    	autoOpen:true,
	    	close:function(){
	    	
	    	},
	    	buttons:{  
	    		"提交":function(){
	    			if( !isSubmit() ){
	    				return ;
	    			}
	    			var grid = $("#object_content_tb").jqGrid("getRowData",1);//被异议的采集项
	    			var user_id = "${user.loginId}";
	    			var addUserUnitId = "";
	    			var addUserDiscId = "";
			   		switch("${user.userType}"+""){
				   		case "2"://学校用户
				   			addUserUnitId = "${user.unitId}";
				  			break;
		   				case "3"://学科用户	
							addUserUnitId = "${user.unitId}";
	    					addUserDiscId = "${user.discId}";
	    					break;
					}
			   		var objectTypeArray = $("#ddl_object_type").val().split(",");
			   		
			   		var objectCollectAttrValue = "";
					
			   		//获取被异议的属性值
			   		for(attribute in grid){
			   			if( attribute == objectTypeArray[0]){
			   				objectCollectAttrValue = grid[attribute];
			   				break;
			   			}
			   		}
			   		
			   		
			   		var dataString="objectCollectItemId="+grid.ID+
			   		   "&importantAttrValue="+grid[attrId]+
	                   "&problemUnitId="+grid.UNIT_ID+
	                   "&problemDiscId="+grid.DISC_ID+
	                   "&unitObjectContent="+$("textarea#ta_objectionContent").val()+
	                   "&userId="+user_id+
	                   "&currentPublicRoundId="+$("#ddl_publicity_round").val()+
	                   "&unitObjectCollectAttrId="+objectTypeArray[0]+
	                   "&unitObjectCollectAttrName="+objectTypeArray[1]+
					   "&unitObjectCollectAttrValue="+objectCollectAttrValue+
	                   "&objectCollectEntityId="+tableId+ //tableId来自pub_view页面,采集项ID
					   "&objectCollectEntityName="+tableName+//tableName来自pub_view页面，采集项名称
					   "&unitObjectType="+$("#ddl_object_type option:selected").text()+
					   "&objectUnitId="+addUserUnitId+
					   "&objectDiscId="+addUserDiscId;
			   			
			   		if( grid.SEQ_NO != "undefined"){
			   			dataString += "&seqNo="+grid.SEQ_NO;
			   		}
			   		else{
			   			dataString += "&seqNo=0";
			   		}
					ajaxSubmitObjection(dataString);		
					$(this).dialog("destroy");
    			},
            	"取消":function(){
            		closePage();
            		$(this).dialog("destroy");
            	}
	   	 }}); 
	}
	
	//是否可以提交异议
	function isSubmit(){
		if( uploading ){
			alert_dialog("文件正在上传中,请等待上传结束");
			return false;
		}
		else{
			return true;
		}
	}
	
	//提交提出的异议
	function ajaxSubmitObjection(dataString){
		$.ajax({
			url:'${ContextPath}/publicity/viewPub_addOriginalObjection',   
            type:"POST",   
            data:dataString+"&proveMaterialId="+$("#file_no").text(),   
            success : function(data) {   
                if(data == true) {   
                    alert_dialog("异议已提出，请于异议汇总页面查看");
             		closePage();
             		jQuery("#jqGrid_collect_tb").trigger("reloadGrid");
                }   
                else{
                	alert_dialog(data);
                }
            },   
            error : function(data) {   
            	console.log("error data:"+data);
                closePage();
            	alert_dialog("出现异常错误");
            }   

		});
	}
	
	function initObjectJqgrid(colModels,rowData)
	{
		var mydata;
		$("#object_content_tb").jqGrid('GridUnload');
		$("#object_content_tb").jqGrid({
			colModel:colModels,
			height:"100%",
			data: mydata, 
			datatype: "local",
			width:window.screen.availWidth-180,
			autowidth : false,
			shrinkToFit : true,
			rowNum:10,
			rowList:[10,20,30],
			viewrecords: true,
			sortorder: "desc",
			caption: tableName,
			loadComplete:function(){
				initObjectListJqgrid(rowData);
			},
			gridComplete: function(){
				jQuery("#object_content_tb").setGridParam().hideCol("oper");
			},
		}).navGrid('#object_content_tb',{edit:false,add:false,del:false});	
	}

</script>