<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<head>
<link rel="stylesheet" type="text/css" href="${ContextPath}/css/survey.css" />
</head>
<div id="questoinnaire_content">
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>主页
	</h3>
</div>
<div class="selectbar">
   <table>
	   <tr >
	       <td>
	           <a id="questionnaire_new_btn" class="button" href="#">
	           	<span class="icon icon-add "></span>新建问卷
	           </a>
	       </td>
	   </tr>
   </table>
   <div id="dialog-confirm"></div>
</div>
<div id="ques_table" class="tabs">
<!-- 		<ul id="tab_status">
			<li ><a id="show_unpublish" class="objectLink" href="#unpublish_div" >未发布</a></li>
			<li ><a id="show_inprogress" class="objectLink" href="#inprogress_div" >进行中</a></li>
			<li ><a id="show_end" class="objectLink" href="#end_div" >已结束</a></li>
		</ul> -->
		<div class="table" id='queslist_div'>
    		<table id="queslist_tb"></table>
			<div id="queslist_pager"></div>
		</div>
</div>
</div>

<script type="text/javascript">
	var jsonData = [];
	var jsonObj = null;
	$(document).ready(function() {
		$("body").on("click", ".publishQ input", function() {
			var item1 = $(this).next().html();
			var publishQ = $(this).closest(".publishQ");
			var qId1 = "q" + publishQ.find(".publishQNum").html();
			$.each(jsonObj, function(i, val) {
				if (item1.trim() == val.item1.trim() && qId1.trim() == val.qId1.trim()) {
					alert("跳转到" + val.qId2);
				}
			});
		});
		$( 'input[type=submit], a.button , button' ).button();
		//未发布的问卷列表 
		 $('#queslist_tb').jqGrid({
			url:'${ContextPath}/survey/home_list',
         	datatype: 'json',
         	mtype:'GET',
 			colNames:['问卷ID','问卷名称','问卷类型','预览','编辑','删除','发布','当前状态标志','当前状态', '发布时段', '用户结果查看'],
 			colModel:[
              {name:'ques.id',    index:'id',	  width:80,	 align:"center", hidden:true},
              {name:'ques.name',  index:'name',	  width:80,	 align:'center'},
              {name:'ques.type',  index:'type',	  width:80,	 align:'center'},
              {name:'preview',	  index:'preview',width:30,	 align:'center'},
              {name:'edit',		  index:'edit',	  width:30,	 align:'center'},
              {name:'del',		  index:'del',	  width:30,	 align:'center'},
              {name:'pub',		  index:'pub',	  width:30,  align:'center'},
              {name:'ques.status',index:'status', width:50,  align:'center', hidden:true},
              {name:'statusName', index:'',       width:50,  align:'center'},
              {name:'time', 	  index:'time',   width:50,  align:'center', hidden:true},
              {name:'collect',    index:'collect',width:50,  align:'center'}
 			],
 			height:'100%',
 			autowidth:true,
 			pager: '#queslist_pager',
 			rowNum:20,
 			rowList:[20,30,40],
 			viewrecords: true,
 			sortname: 'name',
 			sortorder: 'asc',
 			caption: "问卷",//"未发布的问卷",
   		    jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
                root: 'rows',  //包含实际数据的数组  
                page: 'pageIndex',  //当前页  
                total: 'totalPage',//总页数  
                records:'totalCount', //查询出的记录数  
                repeatitems : false  ,    
   		 	},
		    gridComplete: function(){
		    	var tb = $("#queslist_tb");
		    	var ids = tb.jqGrid('getDataIDs');
				for(var i = 0; i < ids.length; i++) {
					var jqGridData = tb.jqGrid('getRowData',ids[i]);
					var status = jqGridData["ques.status"];
					var preview = "<a class='' href='#' onclick='previewFunc("	+ids[i] + ")'>预览</a>";
					var edit, del, publish;
					if (status == 0) {
						edit 	= "<a class='' href='#' onclick='updateFun("	+ids[i] + ")'>编辑</a>";
						del 	= "<a class='' href='#' onclick='delFun("		+ids[i] + ")'>删除</a>"; 
						publish = "<a class='' href='#' onclick='publishFunc("  +ids[i] + ")'>发布</a>";
						statusName = "未发布";
						collect = "<span>--</span>";
					} else {
						edit 	= "<span>--</span>";
						del 	= "<span>--</span>";
						publish = "<span>--</span>";
						statusName = "已发布";
						collect = "<a class='' href='#' onclick='collectFunc("  +ids[i] + ")'>查看</a>";
					}
					
					tb.jqGrid('setRowData',ids[i],{preview 	  : preview});
					tb.jqGrid('setRowData',ids[i],{edit	 	  : edit});
					tb.jqGrid('setRowData',ids[i],{del 		  : del});
					tb.jqGrid('setRowData',ids[i],{pub        : publish});
					tb.jqGrid('setRowData',ids[i],{statusName : statusName});
					tb.jqGrid('setRowData',ids[i],{collect    : collect});
				}
		    },
 		}).navGrid('#queslist_tb',{edit:false,add:false,del:false}); 	
	}); 
	$("#questionnaire_new_btn").click(function() {
		$.post("${ContextPath}/survey/create", function(data){
			$("#content").empty();
			$("#content").append( data );
 		}, 'html');
		event.preventDefault();  
	});
	
	function previewFunc(selRowId) {
		id = $("#queslist_tb").jqGrid('getCell', selRowId, 'ques.id');
			 
		$.post("${ContextPath}/survey/create/preview/" + id, function(data) {
			$("#questoinnaire_content").empty();
			
			// 含有编辑问卷的navi
			//var navi = "<table><tr><td><a id='questionnaire_home_btn' class='button' href='#' onclick='backHome()'><span class='icon icon-left'></span>返回主页</a></td><td><a id='questionnaire_edit_btn' class='button' href='#' onclick='updateFun2(\""+id+"\")'><span class='icon icon-edit'></span>编辑问卷</a></td></tr></table>";
			// 不含编辑问卷的navi
			var navi = "<table><tr><td><a id='questionnaire_home_btn' class='button' href='#' onclick='backHome()'><span class='icon icon-left'></span>返回主页</a></td></tr></table>";
			
			var submit = "<div align='center'><a type='submit' id='' class='button' onclick='' href='#'>提交</a></div>";
			data = navi + data;
			//data += submit;
			$( "#questoinnaire_content" ).append(data);
			$('input[type=submit], a.button , button').button();
			setQNum();
			jsonData = $("#questoinnaire_content :first").val();
			jsonObj = eval(jsonData);
			$('div#content').css("height", "auto");
			event.preventDefault();
		  }, 'html');
	}
	
	function collectFunc(selRowId) {
		id = $("#queslist_tb").jqGrid('getCell', selRowId, 'ques.id');
		
		$.post("${ContextPath}/survey/home_showUserAnswers/" + id, function(data){
			$("#content").empty();
			$("#content").append( data );
 		}, 'html');
		event.preventDefault();  
	}
	
	
	function delFun(selRowId) {
		$("#dialog-confirm").empty().append("<p>确定删除吗？</p>");
		$("#dialog-confirm").dialog({
			height:150,
			title:'警告',
			modal:true,/*弹出dialog后 焦点不能再主页面上  */
   	      	buttons: {
   	        	"确定":function(){
   	        		id = $("#queslist_tb").jqGrid('getCell', selRowId, 'ques.id');
   	        		$.post("${ContextPath}/survey/create/delete/" + id, function(data) {
   	     			if(data){
   	     				alert_dialog('删除成功');
   	     				$("#queslist_tb")
   	     				.setGridParam({url:'${ContextPath}/survey/home_list'})
   	     				.trigger("reloadGrid");
   	     				}
   	     			else{
   	     				alert_dialog('删除失败');
	     				$("#queslist_tb")
	     				.setGridParam({url:'${ContextPath}/survey/home_list'})
	     				.trigger("reloadGrid");
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
	
	/* 发布问卷 */
	function publishFunc(selRowId){
		id = $("#queslist_tb").jqGrid('getCell', selRowId, 'ques.id');
		$("#dialog-confirm").empty().append(
			"<div>"+
				"<div>是否发布问卷？<br></div>"+
				"<label><input style='margin:3px;margin:3px 3px 0 3px;' id='template_confirm' type='checkbox'>共享到模板库</label>"+
			"</div>");
		$("#dialog-confirm").dialog({
			height:160,
			width:270,
			modal:true,
			resizable:false,
			title:'确认发布',
			buttons:{
				"确定":function(){
					if($("#template_confirm").is(':checked')){
						$.post("${ContextPath}/survey/create/publishing/" + id,function(){	
							$("#queslist_tb")
	   	     					.setGridParam({url:'${ContextPath}/survey/home_list'})
	   	     					.trigger("reloadGrid");
						});
					} 
					// 没有把问卷存储为问卷模板，需要后台添加一个路由处理，此处未实现
					else {
						$.post("${ContextPath}/survey/create/publishing/" + id,function(){	
							$("#queslist_tb")
	   	     					.setGridParam({url:'${ContextPath}/survey/home_list'})
	   	     					.trigger("reloadGrid");
						});
					}
					//alert("已经成功将问卷链接发送至指定的联系人邮箱！");
					$(this).dialog("close");
				},
				"取消":function(){
					$(this).dialog("close");
				}
			}
		});
		
	}
	function updateFun(selRowId) {
		var id = $("#queslist_tb").jqGrid('getCell', selRowId, 'ques.id');
		$.post("${ContextPath}/survey/create/update/" + id, function(data) {
			 $( "#content" ).empty();
			  $( "#content" ).append( data );
		 	  }, 'html');
			event.preventDefault();  
	}
	
	function updateFun2(guid) {
		$.post("${ContextPath}/survey/create/update/" + id, function(data) {
			 $( "#content" ).empty();
			  $( "#content" ).append( data );
		 	  }, 'html');
			event.preventDefault();  
	}
	
	function backHome() {
		$.post("${ContextPath}/survey/home", function(data) {
			 $( "#content" ).empty();
			  $( "#content" ).append( data );
		 	  }, 'html');
			event.preventDefault();  
	}
	
	function setQNum() {
		$.each($(".publishQStem"), function(i, val) {
			$(val).html("<span class='publishQNum'>" + (i + 1) + "</span>. " + $(val).html());			
		});
	}
	
	/*保存选中和填写的答案   */
	function saveAnswer(){
		
		//获取答案数组
		var jsonArr = [] ;
		
		//存每个答案
		var ans={};
		//获取所有被选中的选择题的选项
		var Item= $(".publishQ :checked");
		$.each(Item,function(i,val){
			alert($(val).html());
			var pid = $(val).attr("name");
			var itemid = $(val).attr("itemid");
			var result = $(val).attr("value");
			alert(pid + " " + itemid + " " + result);
			//alert(result);
			ans={"userid":"zhongxin","qid":pid, "pid":pid, "itemid":itemid , "result":result};
			//alert(ans);
			jsonArr.push(ans);
			//alert(jsonArr);
		});
		
		//获取填空题的填入值 
		var blanks = $(":input:text");
		$.each(blanks,function(i,val){
			var pid = $(val).attr("name");
			var itemid = $(val).attr("itemid");
			var result = $(val).val();
			ans={"userid":"zhongxin","qid":pid, "pid":pid, "itemid":itemid , "result":result};
			jsonArr.push(ans);
		});
		//console.log(JSON.stringify(jsonArr));
		//将答案json格式传入后台 存入数据库
		$.ajax({ 
	        type : "POST", 
	        url : "${ContextPath}/survey/home_saveAnswer/" , 
	        dataType : "json",      
	        contentType : "application/json",               
	        data : JSON.stringify(jsonArr), 
	        success: function(data) {
	        	//alert_dialog("答案已提交！");
	        }
	     });
	}
</script>

	

