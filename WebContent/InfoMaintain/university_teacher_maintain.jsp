<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>教师信息维护
	</h3>
</div>
<div class="selectbar">
	<table class="layout_table">
		<tr>
			<td><span class="TextFont">姓名：</span></td>
			<td><input type="text" name="teacherName" /></td>
			<td><span class="TextFont">身份证号：</span></td>
			<td><input type="text" name="teacherIdentifyNumber" /></td>
			<td><a id="search" class="button" href="#"><span class="icon icon-search "></span>查找</a></td>
		</tr>
	</table>
</div>
<div class="selectbar">
	<table class="layout_table">
		<tr>
			<td><a class="button" href="#"><span
				class="icon icon-import"></span>批量导入</a></td>
			<td><a class="button" href="#"><span
				class="icon icon-export"></span>批量导出</a></td>
			<td><a class="button" href="#" id="addTeacher_btn"><span
				class="icon icon-adduser"></span>添加教师</a></td>
			<td><a class="button" href="#" id="editTeacher_btn"><span
				class="icon icon-edituser"></span>编辑教师</a></td>
			<td><a class="button" href="#"><span
				class="icon icon-deleteuser"></span>删除教师</a></td>
		</tr>
	</table>
</div>

<div class="selectbar layout_holder">
	<table id="teacherList_tb"></table>
	<div id="pager"></div>
</div>


<script type="text/javascript">
	$(document).ready(function(){
		$("input[type=submit], a.button , button" ).button();
	
		
		
		$("#teacherList_tb").jqGrid({
			datatype: "local",
			colNames:['身份证号','姓名','联系电话','职称','导师类型','主学科'],
			colModel:[	
				{name:'identification',index:'identification',align:"center", width:100},
				{name:'name',index:'name',align:"center", width:120 },
				{name:'telephone',index:'telephone',align:"center", width:100},
				{name:'title',index:'title',align:"center", width:120 },
				{name:'teacherType',index:'teacherType',align:"center", width:100},
				/* {name:'isDoctor',index:'isDoctor',align:"center", width:80}, */
				{name:'mainDiscipline',index:'mainDiscipline',align:'center',width:240},
				/* {name:'query',index:'query',width:100,align:"center",sortable:false} */
			],
			height:"100%",
			autowidth:true,
			pager: '#pager',
			rowNum:10, 
			rowList:[10,20,30],
			viewrecords: true,
			sortorder: "desc",
			caption: "教师列表",
			gridComplete:
				function(){
				//produceQuery("#teacherList_tb","showTeacherInfo");//query.info.js
			}
				
			  /* function(){
				var ids = $("#teacherList_tb").jqGrid('getDataIDs');
				for(var i=0;i < ids.length; i++){
					var view = "<a href='#'>查看</a>";
					$("#teacherList_tb").jqGrid('setRowData',ids[i],{data:view});
				}
			}   */
		});
		
		var myData= [
             {identification:'34262211154431',name:'张三',telephone:'1234567',title:'教授',teacherType:'硕导',mainDiscipline:'软件工程'},
             {identification:'34262211110023',name:'李四',telephone:'6345434',title:'讲师',teacherType:'无',mainDiscipline:'软件工程'},
             {identification:'34262211113421',name:'王五',telephone:'4356763',title:'讲师',teacherType:'无',mainDiscipline:'软件工程'},
             {identification:'34262211112434',name:'朱六',telephone:'6898457',title:'教授',teacherType:'博导',mainDiscipline:'软件工程'},
             {identification:'34262211115323',name:'龙七',telephone:'0239745',title:'副教授',teacherType:'硕导',mainDiscipline:'软件工程'},
             {identification:'34262211153113',name:'牛老师',telephone:'7878454',title:'教授',teacherType:'博导',mainDiscipline:'软件工程'},
             {identification:'34262214556544',name:'孙老师',telephone:'1872132',title:'教授',teacherType:'博导',mainDiscipline:'软件工程'},
             {identification:'34262215444311',name:'柯老师',telephone:'5669874',title:'教授',teacherType:'博导',mainDiscipline:'软件工程'},
             {identification:'34262216554531',name:'方老师',telephone:'2369745',title:'讲师',teacherType:'无',mainDiscipline:'软件工程'},
             {identification:'34262215443112',name:'彭老师',telephone:'0589946',title:'副教授',teacherType:'硕导',mainDiscipline:'软件工程'},
             {identification:'34262215445327',name:'朱老师',telephone:'7894562',title:'教授',teacherType:'博导',mainDiscipline:'计算机科学与工程'},
             {identification:'34262217667890',name:'余老师',telephone:'1234567',title:'副教授',teacherType:'硕导',mainDiscipline:'计算机科学与工程'},
             {identification:'34262211324325',name:'王老师',telephone:'1234567',title:'讲师',teacherType:'无',mainDiscipline:'计算机科学与工程'}
             ];
	 	for(var i=0;i < myData.length; i++)
			 $("#teacherList_tb").jqGrid('addRowData',i+1,myData[i]);

	});
	
	
	$('#addTeacher_btn').click(function(event){
		addTeacherDialog("${ContextPath}/DSEP/infoMaintain/toAddTeacher");
		event.preventDefault();
	});
	
	function addTeacherDialog(url){
		$.post(url,function(data){
			$("#dialog").empty();
			$("#dialog").append(data);
			$("#dialog").dialog({
				title:"添加教师",
	  		    height:'700',
	  			width:'900',
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  			show:'fade',
	  		    autoOpen:true,
	  		    buttons:{
	  		    	"保存":function(){
	  		    		$("#dialog").dialog("close");
	  		    		$("#dialog").empty();
	  		    	},
	  		    	"取消":function(){
	  		    		$("#dialog").dialog("close");
	  		    		$("#dialog").empty();
	  		    	}
	  		    }
			});
		},'html');
	}
	
	$('#editTeacher_btn').click(function(event){
		editTeacherDialog("${ContextPath}/DSEP/infoMaintain/toEditTeacher");
		event.preventDefault();
	});
	
	function editTeacherDialog(theUrl){
		$.post(theUrl,function(data){
			$("#dialog").empty();
			$("#dialog").append(data);
			$("#dialog").dialog({
				title:"教师信息",
	  		    height:'700',
	  			width:'900',
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  			show:'fade',
	  		    autoOpen:true,
	  		    buttons:{
	  		    	"保存":function(){
	  		    		$("#dialog").dialog("close");
	  		    		$("#dialog").empty();
	  		    	},
	  		    	"关闭":function(){
	  		    		$("#dialog").dialog("close");
	  		    		$("#dialog").empty();
	  		    	}
	  		    }
			});
		},'html');
	}
	
	function showTeacherInfo(rowId){
		var theUrl = "${ContextPath}/DSEP/infoMaintain/toShowTeacherInfo";
		$.post(theUrl,function(data){
			$("#dialog").empty();
			$("#dialog").append(data);
			$("#dialog").dialog({
				title:"教师信息",
	  		    height:'700',
	  			width:'900',
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  			show:'fade',
	  		    autoOpen:true,
	  		    buttons:{
	  		    	"关闭":function(){
	  		    		$("#dialog").dialog("close");
	  		    		$("#dialog").empty();
	  		    	}
	  		    }
			});
		},'html');
	}
	
	
	function searchTeacher(){
		
	}
	
	
</script>
