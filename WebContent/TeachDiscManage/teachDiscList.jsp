<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="con_header inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="icon icon-disciplinecollect"></span>
				<span class="TextFont">教师范围设置</span>
			</td>
		</tr>
	</table>
	<table class="layout_table right">
		<tr>
			
		</tr>
	</table>
</div>
<div class="selectbar inner_table_holder">
	<!-- <table class="layout_table left">
		<tr>
			<td>
				<span class="icon icon-disciplinecollect"></span>
				<span class="TextFont">查看教师信息</span>
			</td>
		</tr>
	</table>
	<table class="layout_table right">
		<tr>
			<td>
				<a id="choiceTeach" class="button" href="#"><span class="icon icon-confirm"></span>选定</a>
			</td>	
		</tr>
	</table> -->
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td >
				<span class="TextFont">教师用户名：</span>
			</td>
			<td >
				<input id = "teachLoginId" type="text"/>
			</td>
			<td>&nbsp;&nbsp;</td>
			<td  >
				<span class="TextFont">教师姓名：</span>
			</td>
			<td>
				<input id = "teachName" type="text"/>
			</td>
			<td>&nbsp;&nbsp;</td>
			<td  >
				<span class="TextFont">学科类型：</span>
			</td>
			<td >
				<select id="discType">
					<option value="all">全部</option>
					<option value="1">一级学科1</option>
					<option value="2">一级学科2</option>
				</select>
			</td>
			<td>&nbsp;&nbsp;</td>
			<td  >
				<a id="searchTeach" class="button" href="#"><span class="icon icon-search"></span>查询</a>
			</td>
			<td>&nbsp;&nbsp;</td>
			<td  >
				<a id="choiceTeach" class="button" href="#"><span class="icon icon-confirm"></span>选定</a>
			</td>
		</tr>
	</table>
</div>
  <div class="table">
		<table id="viewTeach_tb"></table>
		<div id="pager_viewTeach_tb"></div>
	</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">教师用户名：</span>
			</td>
			<td>
			<input id = "selTeachLoginId" type="text"/>
			</td>
			<td>&nbsp;&nbsp;</td>
			<td>
				<span class="TextFont">教师姓名：</span>
				
			</td>
			<td>
				<input id = "selTeachName" type="text"/>
			</td>
			<td>&nbsp;&nbsp;</td>
			<td>
				<a id="searchSelTeach" class="button" href="#"><span class="icon icon-search"></span>查询</a>
			</td>
			<td>&nbsp;&nbsp;</td>
			<td>
				<a id="cancelTeach" class="button" href="#"><span class="icon icon-confirm"></span>取消</a>
			</td>
		</tr>
	</table>
</div>
	<div class="table">
		<table id="selectedTeach_tb"></table>
		<div id="pager_selectedTeach_tb"></div>
	</div>
<!-- <div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="icon icon-disciplinecollect"></span>
				<span class="TextFont">学科已选教师信息</span>
			</td>
		</tr>
	</table>
</div>
<div class="selectbar layout_holder">
     <div class="table">
		<table id="selectedTeach_tb"></table>
		<div id="pager_selectedTeach_tb"></div>
	</div>
</div> -->
<div hidden="true">
	<div id="dialog-confirm" title="警告"></div>
</div>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script type="text/javascript">
	var unitId = "${unitId}";
	var discId1 = "${discId}";
	var discId2 = "${discId}";
	var viewTeachUrl = '';
	var selectedTeachUrl = '';
	//取消一个选中的教师
	function cancelOneTeach(id){
		console.log(id);
		var unitId = "${unitId}";
		var discId = "${discId}";
		cancelTeachers(unitId,discId,id);
	}
	//取消选中的教师
	function cancelTeachers(unitId,discId,rowId){
		var pkAndSeqNos = new Array();
		var ids;
		if(rowId==''){
			ids=$('#selectedTeach_tb').jqGrid('getGridParam','selarrrow');
			console.log(ids);
		}else{
		  	ids = new Array();
		  	ids.push(rowId);
		}
		for(var i = 0;i<ids.length;i++){
			var rowData = $("#selectedTeach_tb").jqGrid("getRowData",ids[i]);
			pkAndSeqNos.push(rowData['teachDisc.id']+':'+rowData['teachDisc.seqNo']);
		}
		if(pkAndSeqNos.length>0){
			$.commonRequest({
				url:'${ContextPath}/teachDiscManage/viewTeachList/delTeachDisc/'
					+unitId
					+'/'
					+discId
					+'/'
					+pkAndSeqNos,
				dataType:'text',
				success:function(result){
					if(result=='success'){
						//alert_dialog('导入成功！');
						$('#searchTeach').trigger("click");
					}else{
						alert_dialog('操作失败！');
					}
				}
				});
		}else{
			alert_dialog('请选中将要取消的教师！')
		}
		
	}
	/**
	*选择一个教师
	*/
	function selectOneTeach(id){
		console.log(id);
		var unitId = "${unitId}";
		var discId = "${discId}";
		selectTeachers(unitId,discId,id);
	}
	/*
	*选择教师
	*/
	function selectTeachers(unitId,discId,rowId){
		var pkValues = new Array();
		var ids;
		if(rowId==''){
			ids=$('#viewTeach_tb').jqGrid('getGridParam','selarrrow');
		}else{
		  	ids = new Array();
		  	ids.push(rowId);
		}
		for(var i = 0;i<ids.length;i++){
			var rowData = $("#viewTeach_tb").jqGrid("getRowData",ids[i]);
			pkValues.push(rowData['teacher.id']);
		}
		if(pkValues.length>0){
			$.commonRequest({
				url:'${ContextPath}/teachDiscManage/viewTeachList/import2TeachDisc/'
					+unitId
					+'/'
					+discId
					+'/'
					+pkValues,
				dataType:'text',
				success:function(result){
					if(result=='success'){
						//alert_dialog('导入成功！');
						$('#searchTeach').trigger("click");
					}else{
						alert_dialog(result);
					}
				}
				});
		}else{
			alert_dialog('请首先选中教师！');
		}
		
	}
	//查看教师详细信息
	function viewDetial(id){
		var rowData = $("#viewTeach_tb").jqGrid("getRowData",id);
		var pkValue = rowData['teacher.id'];
		var reUrl = '${ContextPath}/teachDiscManage/viewTeachList/teachDetail/'+pkValue;
		$.post(reUrl, function(data){
			  console.log(data);
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		  }, 'html');
		event.preventDefault();
	}
	//初始化已选教师的URL
	function initSelectedTeachUrl(teachLoginId,teachName,unitId,discId){
		selectedTeachUrl = "${ContextPath}/teachDiscManage/viewTeachList/selectedTeachData"
							+"?unitId="+unitId
							+"&discId="+discId
							+"&teachLoginId="+teachLoginId
							+"&teachName="+teachName;
	}
	//初始化基础教师信息的URL
	function initViewTeachUrl(teachLoginId,teachName,unitId,discId1,discId2){
		viewTeachUrl = "${ContextPath}/teachDiscManage/viewTeachList/viewTeachData"
			+"?unitId="+unitId
			+"&discId1="+discId1
			+"&discId2="+discId2
			+"&teachLoginId="+teachLoginId
			+"&teachName="+teachName;
	}
	//查询教师信息
	function searchTeacher(teachLoginId,teachName,unitId,discId1,discId2){
		initViewTeachUrl(teachLoginId,teachName,unitId,discId1,discId2);
		jQuery("#viewTeach_tb").setGridParam({
			url:viewTeachUrl,
			}).trigger("reloadGrid"); 
				
	}
	//查询已选教师信息
	function searchSelTeach(teachLoginId,teachName,unitId,discId){
		initSelectedTeachUrl(teachLoginId,teachName,unitId,discId);
		jQuery("#selectedTeach_tb").setGridParam({
			url: selectedTeachUrl,
			}).trigger("reloadGrid"); 
	}
	function initSelectedTeachJqgrid(){
		$("#selectedTeach_tb").jqGrid('GridUnload');
		$("#selectedTeach_tb").jqGrid({
			url: selectedTeachUrl,
			datatype: "json",
			mtype:"post",
			colNames:['ID','学校代码','学科代码','','序号','教师用户名','教师姓名'],
			colModel:[
				{name:'teachDisc.id',index:'id',align:"center", width:100,hidden:true,editable:true},
				{name:'teachDisc.unitId',index:'unitId',align:"center", width:100,hidden:true,editable:true},
				{name:'teachDisc.discId',index:'discId',align:"center", width:100,hidden:true,editable:true},
				{name:'cancel',align:"center", width:20,hidden:false,editable:false,sortable:false},
				{name:'teachDisc.seqNo',index:"seqNo",align:"center",width:10},
				{name:'teachDisc.teachLoginId',index:'teachLoginId',align:"center", width:100},
				{name:'teachDisc.teachName',index:'teachName',align:"center", width:100}
			],
			rownumbers: false,
			height:"100%",
			autowidth:true,
			pager: '#pager_selectedTeach_tb',
			rowNum:10,
			rowList:[10,20,30],
			viewrecords: true,
			sortorder: "asc",
			multiselect:true,
			sortname:'seqNo',
			jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
	            root: "rows",  //包含实际数据的数组  
	            page: "pageIndex",  //当前页  
	            total: "totalPage",//总页数  
	            records:"totalCount", //查询出的记录数  
	            repeatitems : false,
	        },
			gridComplete: function(){
				var ids = jQuery("#selectedTeach_tb").jqGrid('getDataIDs');
				for(var i=0;i<ids.length;i++){
					console.log(ids[i]);
					var cancel_link="<a class='' href='#' onclick='cancelOneTeach("+ids[i]+")'>取消</a>";
					jQuery("#selectedTeach_tb").jqGrid('setRowData',ids[i],{cancel :cancel_link});
				}
			},
			caption: "已选教师列表"
		}).navGrid('#selectedTeach_tb',{edit:false,add:false,del:false});
	}
	$(document).ready(function(){
		$( "input[type=submit], a.button , button" ).button();
		initViewTeachUrl('','',unitId,discId1,discId2);//对查看教师信息jqgrid的url进行初始化
		initSelectedTeachUrl('','',unitId,discId1)//对已选教师信息jqgrid的url进行初始化
		$("#viewTeach_tb").jqGrid({
			url:viewTeachUrl,
			datatype: "json",
			mtype:"post",
			colNames:['ID','学校代码','','教师用户名','教师姓名','一级学科代码1','一级学科代码2','',''],
			colModel:[
				{name:'teacher.id',index:'id',align:"center", width:100,hidden:true,editable:true},
				{name:'teacher.unitId',index:'unitId',align:"center", width:100,hidden:true,editable:true},
				{name:'choose',align:"center", width:50,hidden:false,editable:false,sortable:false},
				{name:'teacher.loginId',index:'loginId',align:"center", width:100},
				{name:'teacher.name',index:'name',align:"center", width:100},
				{name:'teacher.discId',index:'discId',align:"center", width:100},
				{name:'teacher.yjxkm2',index:'discId2',align:'center',width:140},
				{name:'isSelected',align:"center", width:100,hidden:true,editable:false,sortable:false},
				{name:'view',align:"center", width:100,hidden:false,editable:false,sortable:false},
				
			],
			rownumbers: true,
			height:"100%",
			autowidth:true,
			pager: '#pager_viewTeach_tb',
			rowNum:10,
			rowList:[10,20,30],
			viewrecords: true,
			sortorder: "asc",
			multiselect:true,
			sortname:'id',
			jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
	            root: "rows",  //包含实际数据的数组  
	            page: "pageIndex",  //当前页  
	            total: "totalPage",//总页数  
	            records:"totalCount", //查询出的记录数  
	            repeatitems : false,
	        },
			gridComplete: function(){
				initSelectedTeachJqgrid();//加载已选教师信息
				var ids = jQuery("#viewTeach_tb").jqGrid('getDataIDs');
				for(var i=0;i<ids.length;i++){
					var rowData =  $("#viewTeach_tb").jqGrid("getRowData",ids[i]);
					if(rowData['isSelected']=='true'){
						$("#viewTeach_tb #"+ids[i]).find('td').css('background-color','#E0E0E0');
					}
					var sel_link="<a class='' href='#' onclick='selectOneTeach("+ids[i]+")'>选中</a>";
					var viewDetail = "<a class='' href='#' onclick='viewDetial("+ids[i]+")'>查看教师详情</a>";
					jQuery("#viewTeach_tb").jqGrid('setRowData',ids[i],{choose :sel_link});
					jQuery("#viewTeach_tb").jqGrid('setRowData',ids[i],{view :viewDetail});
				}
				
			},
			caption: "可选教师列表"
		}).navGrid('#viewTeach_tb',{edit:false,add:false,del:false});
		/*按钮的click函数*/
		$("#searchTeach").click(function(){
			var teachLoginId = $("#teachLoginId").val();
			var teachName =encodeURI(encodeURI( $("#teachName").val()));
			var discType = $("#discType").val();
			switch(discType){
				case 'all':
					discId1="${discId}";
					discId2="${discId}";
					break;
				case '1':
					discId1="${discId}";
					discId2='';
					break;
				case '2':
					discId1='';
					discId2="${discId}";
					break;
				default:
					break;		
			}
			searchTeacher(teachLoginId,teachName,unitId,discId1,discId2);
		});
		$("#searchSelTeach").click(function(){
			var teachLoginId = $("#selTeachLoginId").val();
			var teachName =encodeURI(encodeURI( $("#selTeachName").val()));
			var unitId = "${unitId}";
			var discId = "${discId}";
			searchSelTeach(teachLoginId,teachName,unitId,discId);
		});
		$("#choiceTeach").click(function(){
			var unitId = "${unitId}";
			var discId = "${discId}";
			selectTeachers(unitId,discId,'');
		});
		$("#cancelTeach").click(function(){
			var unitId = "${unitId}";
			var discId = "${discId}";
			cancelTeachers(unitId,discId,'');
		})
	});
</script>