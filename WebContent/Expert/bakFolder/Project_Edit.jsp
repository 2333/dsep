<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<style>
	body {
		font-size:14px;
	}
	#team_list {
		margin-left:0px;
		list-style-type: disc;
		font-size:14px;
		font-weight:bold;
		display:block;
	}
	.team_list_li {
		display:block;
		margin：8px 0px;
	}
	 .ui-jqgrid .ui-jqgrid-title{font-size:14px;}    /*修改grid标题的字体大小*/  
        
        .ui-jqgrid-sortable {font-size:14px;}   /*修改列名的字体大小*/  
        
        .ui-jqgrid tr.jqgrow td {font-size:14px;} /*修改表格内容字体*/ 
</style>

<c:if test="${!empty applyItem}">
	<input type="hidden" id="applyItemId" value="${applyItem.id}"/>
</c:if>

<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>
		
		<c:if test="${!empty applyItem}">编辑项目</c:if>
		<c:if test="${empty applyItem}">新建项目</c:if>
		
		<c:if test="${!empty applyItem}"><input type="hidden" value="true" id="createApplyItem"/></c:if>
		<c:if test="${empty applyItem}"><input type="hidden" value="true" id="editApplyItem"/></c:if>
		
	</h3>
</div>

<div class="selectbar inner_table_holder">
<form id="fm_addGuides" class="fr_form" method="post" >
	<input type="hidden" id="projectId" value="${projectId}">
	<input type="hidden" id="sessionid" value="${pageContext.session.id}" />
	<input type="hidden" id="itemName" value="${fn:trim(applyItem.itemName) }"/>
	<table class="layout_table" >
		<tr>
			<td style="width:100px;">
				<span class="TextFont">项目来源：</span>
			</td>
			<td>
				<span class="TextFont" style="color:#000;">${projectName}</span>
			</td>
			<td>&nbsp;&nbsp;</td>
			<td style="width:100px;">
				<span class="TextFont">申请经费：</span>
			</td>
			<td >
				<c:if test="${!empty applyItem}">
					<input type='text' id='funding_apply' value='${applyItem.funds }'/>
					<textarea style="width:705px;height:80px;" id="project_name" style="font-size:14px;">
					</textarea>
				</c:if>
				<c:if test="${empty applyItem}">
					<input type='text' id='funding_apply' />
					<textarea style="width:705px;height:80px;" id="project_name" style="font-size:14px;"></textarea>
				</c:if>
				<label>人民币（万元）</label>
			</td>
			<td>&nbsp;&nbsp;</td>
			<td style="width:100px;">
				<span class="TextFont">项目名称：</span>
			</td>
			<td colspan="4">
				<c:if test="${!empty applyItem}">
					<input id="project_name" name="project_name" type="text" size="35" />
				</c:if>
				<c:if test="${empty applyItem}">
					<input id="project_name" name="project_name" type="text" size="35" />
				</c:if>
			</td>
		</tr>
		<tr>
			<td>
				<span class="TextFont">添加成员：</span>
			</td>
			<td>
				<a id="" class="button" href="#" onclick="addOneBatchRow();">
					<span class="icon icon-add"></span>添加
					</span>
				</a>
			</td>
		</tr>
	</table>
	<div class="layout_table"  style="width:1000px; margin:8px 0 0;">
		<table id="guide_list"></table>
		<div id="guide_pager"></div>
	</div>
	

	<table class="layout_table"  style="width:1000px; margin:8px 0 0;">
		<tr>
			<td >
				<span class="TextFont">负责人：</span>
			</td>
			<td>
				<select id="principal">
					<c:if test="${!empty applyItem}">
						<c:forEach items="${applyItem.teamMembers}" var="member">
							<c:if test="${member.isPrincipal}">
								<option value="${member.email}" selected = "selected">${member.name }</option>
							</c:if>
							<c:if test="${!member.isPrincipal}">
								<option value="${member.email}">${member.name }</option>
							</c:if>
							
						</c:forEach>
					</c:if>
					<c:if test="${empty applyItem}">
						<option>请选择负责人</option>
					</c:if>
				</select>
			</td>
		</tr>
		<tr style="visibility:hidden;height:10px;">
			<td>
				<span class="TextFont"></span>
			</td>
			<td colspan='4'>
				<textarea cols="85" rows="0" id="" style="font-size:14px;height:3px;">
				</textarea>
			</td>
		</tr>
		<tr>
			<td>
			    <span class="TextFont">项目目标：</span>
			</td>
			<td colspan="4">
				<textarea name="project_purpose" id="project_purpose"></textarea> 
				<script type="text/javascript" >
				var editor1 = new baidu.editor.ui.Editor({initialFrameHeight:100,initialFrameWidth:900,autoClearinitialContent :true, });
				editor1.addListener( 'ready', function( editor1 ) {
					setEditor1Contents(); //
				 } );
				editor1.render("project_purpose");
				</script>
			</td>
		</tr>

		<tr>
			<td>
			    <span class="TextFont">项目内容：</span>
			</td>
			<td colspan="4">
				<textarea name="project_content" id="project_content"></textarea>
				<script type="text/javascript" >
				var editor2 = new baidu.editor.ui.Editor({initialFrameHeight:100,initialFrameWidth:900,autoClearinitialContent :true, });
				editor2.addListener( 'ready', function( editor2 ) {
					setEditor2Contents(); //编辑器家在完成后，让编辑器拿到焦点
				 } );
				editor2.render("project_content");
				</script>
			</td>
		</tr>
	
		<tr>
			<td>
			    <span class="TextFont">附件：</span>
			</td>
			<td colspan="4">
				<input id="file_upload" name="file_upload" type="file">
				<div id="fileQueue"></div>
				<a class="button" id="upload">上传</a>| 
        			<a class="button" id="cancel">取消上传</a>	|
        			<a class="button" id="delete">删除附件</a>	
        			<b>您上传的附件：</b>
        			<c:if test="${!empty applyItem}">
        				<a id="file_detail" style="color:Green;font-weight:bold;">${applyItem.attachment.name }</a>
        				<a id="file_no" style="display:none">${applyItem.attachment.id }</a>
        			</c:if>
        			<c:if test="${empty applyItem}">
        				<a id="file_detail" style="color:Green;font-weight:bold;"></a>
        				<a id="file_no" style="display:none"></a>
        			</c:if>
			</td>
		</tr>
	</table>
	</form>
</div>
<div class="selectbar inner_table_holder" style="text-align: center;">
	<table class="layout_table" >
		<tr>
			<td>
				<a  id="save_btn" class="button" href="#"><span class="icon icon-store"></span>保存</a>
			</td>
			<td>
				<a  id="return_btn" class="button" href="#"><span class="icon icon-cancel"></span>取消</a>
			</td>
		</tr>
	</table>
</div>

<c:if test="${!empty applyItem}">
	<div id="item_target" style="display:none;">
		${applyItem.itemTarget}
	</div>
	<div id="item_content" style="display:none;">
		${applyItem.itemContent}
	</div>
</c:if> 

<div class="process_dialog">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 正在保存……
</div>

<script type="text/javascript">
    	$('input[type=submit], a.button , button').button();
    	$(".process_dialog").hide();
    	
    	
    	function setEditor1Contents() {
    		var itemTarget  = $("#item_target").html();
    		editor1.setContent(itemTarget);
    	}
    	
    	function setEditor2Contents() {
        	var itemContent = $("#item_content").html();
    		editor2.setContent(itemContent);
    	}
    	$("#project_name").val($("#itemName").val());
   	 	
   		// 默认先把自己加入团队
   		//addMember();
   		 $("#allTeachers").change(function() {
   			addMember();
 	 	});
   		 
   		 function addMember() {
   			var teamNames = [];
   			var teamEmails = [];
   			var ids = $("#guide_list").jqGrid('getDataIDs');
   			 for(var i=0;i < ids.length;i++){
				var rowData = $('#guide_list').jqGrid("getRowData", ids[i]);
				var name  = rowData['name'];
				var email = rowData['email'];
				teamNames.push(name);
				teamEmails.push(email);
			}
   			
   			$("#principal").find('option').remove().end().append("<option value=''>请选择负责人</option>");
			
   			 
			$.each(teamNames, function(idx, val) {
	   	 		var option = "<option value='" + teamEmails[idx] + "'>" + teamNames[idx] + "</option>";
	   	 		$("#principal").append(option);
   	 		}); 
 	 	} 
   		 
   		$("#file_upload").uploadify({  
            'swf'            : '${ContextPath}/js/uploadify/uploadify.swf',  
            'uploader'       : '${ContextPath}/project/papply_fileUpload?jsessionid=' + $("#sessionid").val(),//后台处理的请求   
            'queueID'        : 'fileQueue',
            'queueSizeLimit' : 1,  
            'auto'           : false,
            'buttonText'     : '附件',
            'onUploadSuccess':function(file,data,response){
            	$("#file_detail").html(file.name);
            	$("#file_no").html(data);
            	//alert(data);
            }
        });  
   	 	
   	 	$('#save_btn').click(function(){
   	 		var applyItemId = $("#applyItemId").val();
   	 		var projectId = $("#projectId").val();
   	 		var funds = $("#funding_apply").val();
   	 		var itemName = $("#project_name").val();
   	 		var principalName = $("#principal option:selected").text();
   	 		var principalEmail = $("#principal option:selected").attr("value");
   	 		var teamNames  = [];
   	 		var teamEmails = [];
   	 		var teamInfos   = [];
   	 		var ids = $("#guide_list").jqGrid('getDataIDs');
   	 		
   	 		var saved = true;
	   	 	for(var i=0;i < ids.length;i++){
				var rowData = $('#guide_list').jqGrid("getRowData", ids[i]);
				var oper  = rowData['oper'];
				if (oper.indexOf("保存") > -1) {
					saved = false;
					break;
				}
			}
	   	 	
	   	 	if (!saved) {
	   	 		alert_dialog("请先保存团队人员信息！");
	   	 		return;
	   	 	}
	   	 	for(var i=0;i < ids.length;i++){
				var rowData = $('#guide_list').jqGrid("getRowData", ids[i]);
				var name  = rowData['name'];
				var email = rowData['email'];
				var info  = rowData['info'];
				teamNames.push(name);
				teamEmails.push(email);
				teamInfos.push(info);
			}

   	 		if (isNaN(funds)) {
   	 			alert_dialog("请输入合法的经费数额");
	 			return;
   	 		} else {
   	 			if (parseFloat(funds) < 0) {
	   	 			alert_dialog("经费数额不应小于0");
	   	 			return;
   	 			}
   	 		}
   	 		
   	 		
   	 		if (principalName == undefined || principalName.trim() == "") {
	 			alert_dialog("请指定项目负责人！");
 				return;
	 		}
   	 		
   	 		
   	 		
   	 		
   	 		$(".process_dialog").show();
			$('.process_dialog').dialog({
				position : 'center',
				modal : true,
				autoOpen : true,
			});
   	 		//alert(projectId + " " + itemName + " " + funds + " " + principalId + " " + teamIds);
   	 		var postData = {
   	 				"applyItemId" : applyItemId, 
   	 				"projectId" : projectId, 
   	 				"itemName" : itemName, 
   	 				"funds" : funds, 
   	 				"teamNames" : teamNames, 
   	 				"teamEmails" : teamEmails, 
   	 				"teamInfos" : teamInfos,  
   	 				"principalName" : principalName, 
   	 				"principalEmail" : principalEmail,
   	 				"itemTarget" : editor1.getContent(),
   	 				"itemContent": editor2.getContent(), 
   					"attachmentId" : $("#file_no").text()};
   	 		console.log("applyItemId:" + applyItemId);
   		 	console.log("projectId:" + projectId);
   	 		console.log("itemName:" + itemName);
   			console.log("funds:" + funds);
		   	console.log("teamNames:" + teamNames);
		   	console.log("teamEmails:" + teamEmails);
		   	console.log("teamInfos:" + teamInfos);
		   	console.log("principalName:" + principalName);
		   	console.log("principalEmail:" + principalEmail);
   	 		$.ajax({ 
   		        type : "POST", 
   		        url : "${ContextPath}/project/papply_saveApply", 
   		        dataType : "json",      
   		        contentType : "application/json",               
   		        data : JSON.stringify(postData), 
   		        success: function(data) {
   		        	postData = {};
   		        	jSONArr = [];
   		        	$(".process_dialog").dialog("destroy");
   					$(".process_dialog").hide();
   					alert_dialog("保存成功！");
   					toMyItemPage();
   		        }
   		     });
   		});
   	 	
   	 	
   	 	function toMyItemPage() {
	   	 	$.post("${ContextPath}/project/papply_toMyItemPage", function(data){
				  $( "#content" ).empty();
				  $( "#content" ).append( data );
			 	  }, 'html');
   			event.preventDefault();
   	 	}
   	 	
   	 	$('#return_btn').click(function(){
   	 		$.post("${ContextPath}/project/pproject", function(data){
				$( "#content" ).empty();
			  	$( "#content" ).append( data );
		 	}, 'html');
   		});
   	 	
   	 	$('#upload').click(function(){
   	 		$('#file_upload').uploadify('upload');
   		});
   	 	
   	 	$('#cancel').click(function(){
   	 		$('#file_upload').uploadify('cancel');
   		});
   	 	
   	 	$('#delete').click(function(){
	 		$.post('${ContextPath}/project/papply_fileDelete',{id:$("#file_no").text()} ,function(data){
	 			if(data){
	 				$("#file_detail").text("");
	 				$("#file_no").text("");
	 			}else{
	 				alert_dialog("尚无附件或附件删除失败！");	
	 			}
		  	}, 'json');
		});
   	 	
    	$("#add_teacher").click(function() {
    		url = "";
    		$.post(url, function(data) {
    			$('#dialog').empty();
    			$("#dialog").append(data);
    			$('#dialog').dialog({
    				title : "",
    				autoheight: true,
    				width: window.screen.availWidth*0.92,			
    				position : 'top',
    				modal : true,			
    				draggable : true,
    				hide : 'fade',			
    				show : 'fade',
    				autoOpen : true,
    				buttons : {
    					"关闭" : function() {
    						$("#dialog").dialog("close");
    						$('#dialog').empty();
    					}
    				}
    			});
    		}, 'html');
    	});
	//});
	
	function validEmail(email) {
		var emailReg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	 	if (email.trim() == "") {
	 		//alert_dialog("请输入合法的邮箱，系统会在学校检查时向负责人发送提醒邮件！");
			return [false, "请输入合法的邮箱"];
	 	} else if (!emailReg.test(email)) {
	 		//alert_dialog("请输入合法的邮箱，系统会在学校检查时向负责人发送提醒邮件！");
	 		return [false, "请输入合法的邮箱"];
	 	}
		return [true, ""];
	}
	
	function validName(name) {
	 	if (name.trim() == "") {
			return [false, "请输入姓名"];
	 	}
		return [true, ""];
	}
    $("#guide_list").jqGrid({
   		url:'${ContextPath}/project/papply_showTeamMembers/' + $("#applyItemId").val(),
		datatype: "json",
		mtype: 'GET',
   		colNames:['项目ID','操作', '教师姓名','教师邮箱','所在院系'],
   		colModel:[
   		   		{name:'project.id',index:'projectId', width:100,align:"center",hidden:true},
   		   		{name:'oper',	index:'oper',	width:100,align:"center"},
   		   		{name:'name',	editable:true,	index:'name', 	width:100,	align:"center",
   		   			editrules: {
		         		custom: true,
		        		custom_func: function (value, colName) {
		            		return validName(value);
		        	}}},	
   		   		
   		  	 	{name:'email',	editable:true,	index:'email', 	width:200,	align:"center",
   		   			editrules: {
   		         		custom: true,
   		        		custom_func: function (value, colName) {
   		            		return validEmail(value);
   		        	}}},
   		   		{name:'info',	editable:true,	index:'info', 	width:325, 	align:"center"},
   		   	],
   		   	autowidth:true,
   		   	rowNum:10,
   		    rownumbers:true,
   		   	height:"100%",
   		   	rowList:[10,20,30],
   		   	pager: '#guide_pager',
   		   	sortname: 'ITEM_NAME',
   		    viewrecords: true,
   		    sortorder: "desc",
   		    caption: "团队成员",
   			jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
            root: "rows",  //包含实际数据的数组  
            page: "pageIndex",  //当前页  
            total: "totalPage",//总页数  
            records:"totalCount", //查询出的记录数  
            repeatitems : false,
         	},
         	gridComplete: function(){
         		if (!changeByAddButton) {
         			var ids = jQuery("#guide_list").jqGrid('getDataIDs');
    				for(var i=0;i < ids.length;i++){
    					var resure_link="<a class='' href='#' onclick='editBatchItem(\""+ids[i]+"\")'>编辑</a>";
    	   	   			var del_link = "<a class='' href='#' onclick='delBatchItem(\""+ids[i]+"\")'>删除</a>"; 
    	   	   			$("#guide_list").jqGrid('setRowData',ids[i],{oper :resure_link+' | '+del_link});
    				}
         		}
		    	
		    },
   		});
    	$("#guide_list").jqGrid('navGrid','#guide_pager',{edit:false,add:false,del:false,search:false});
     
    	$("#guide_list").jqGrid('setGridWidth', '815');
   		var changeByAddButton = false;
   		var batchRecords;
   		var selectedId;//选中的一行
   		var defaultDataValues;//初始化显示内容
   		var pass_validate='1';
   		var counter=0;//添加的行号RowId
   		function addOneBatchRow(){
 			$("#guide_list").jqGrid('addRow',{  
 				rowID : ++counter,   
 			    position :"last",  
 			    useDefValues : true,  
 			    useFormatter : true,  
 			    initdata :defaultDataValues,
 			}); 
 			//$("#guide_list").jqGrid('editRow',counter,false,setRowControllers);
 			var save_link = "<a class='' href='#' onclick='resureBatchItem("+counter+")'>保存</a>"; 
 			$("#guide_list").jqGrid('editRow',counter,{});
 			$("#guide_list").jqGrid('setRowData',counter,{oper :save_link});
 			$("#guide_list").jqGrid('setRowData',counter,{SEQ_NO :++batchRecords});	 
 			changeByAddButton = true;
   		}
   		
   		/**
   		 * 删除
   		 */
   		function delBatchItem(id)
   		{
   			var ids = jQuery("#guide_list").jqGrid('getDataIDs');
   			var rowData = $("#guide_list").jqGrid("getRowData");
   			$.each(ids,function(i,item){
   				if(item>id)
   				{
   					jQuery("#guide_list").jqGrid('setRowData',item,{SEQ_NO :rowData[i]['SEQ_NO']-1});		
   				}
   			});
   			$("#guide_list").jqGrid("delRowData",id);
   			batchRecords--;
   			addMember();
   		}
   		/**
   		 * 确认
   		 */
   		function resureBatchItem(id)
   		{
						
   			var result = $("#guide_list").jqGrid('saveRow',id);
   			if (result) {
   				var resure_link="<a class='' href='#' onclick='editBatchItem(\""+id+"\")'>编辑</a>";
   	   			var del_link = "<a class='' href='#' onclick='delBatchItem(\""+id+"\")'>删除</a>"; 
   	   			$("#guide_list").jqGrid('setRowData',id,{oper :resure_link+' | '+del_link});
   	   			addMember();
   			}
   			
   		}
   		/**
   		 * 编辑
   		 */
   		 function editBatchItem(id)
   		 {
   				$("#guide_list").jqGrid('editRow',id,{});
   				//var resure_link="<a class='' href='#' onclick='resureBatchItem("+id+")'>确认</a>";
   				var del_link = "<a class='' href='#' onclick='resureBatchItem(\""+id+"\")'>保存</a>"; 
   				jQuery("#guide_list").jqGrid('setRowData',id,{oper :del_link});
   		 }
   		 /**
   		 *取消
   		 */
   		 function cancelBatchItem(id)
   		 {
   			 
   			 jQuery("#guide_list").jqGrid('restoreRow',id);
   			 var resure_link="<a class='' href='#' onclick='editBatchItem(\""+id+"\")'>编辑</a>";
   			 var del_link = "<a class='' href='#' onclick='delBatchItem(\""+id+"\")'>删除</a>"; 
   			 jQuery("#guide_list").jqGrid('setRowData',id,{oper :resure_link+' | '+del_link});
   			 
   		 }
</script>