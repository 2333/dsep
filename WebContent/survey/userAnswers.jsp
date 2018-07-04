<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<head>
<link rel="stylesheet" type="text/css" href="${ContextPath}/css/survey.css" />
</head>
<input id="qNRId" type="hidden" value="${qNRId}"/>
<input id="qNRName" type="hidden" value="${qNRName}"/>
<input id="qNRType" type="hidden" value="${qNRType}"/>
<div class="process_dialog">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 正在加载问卷，请稍候……
</div>
<div class="process_dialog2">
	<span class="ui-icon ui-icon-info"
		style="float: left; margin-right: .3em;"></span> 正在保存答案，请稍候……
</div>
<div id="questoinnaire_content">
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>用户结果查看
	</h3>
</div>
<div class="selectbar">
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
		$('div#content').css("height", "auto");
		$(".process_dialog").hide();
		$(".process_dialog2").hide();
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
		var qNRId   = $("#qNRId").val();
		var qNRName = $("#qNRName").val();
		var qNRType = $("#qNRType").val();
		$( 'input[type=submit], a.button , button' ).button();
		//未发布的问卷列表 
		$('#queslist_tb').jqGrid({
			url:'${ContextPath}/survey/home_getUserList',
         	datatype: 'json',
         	mtype:'GET',
 			colNames:['用户ID','用户名称','问卷名称','问卷类型','查看结果','发布时段'],
 			colModel:[
              {name:'id',   	index:'id',	 	width:80,	align:"center", hidden:true},
              {name:'name', 	index:'name',	width:80,	align:'center'},
              {name:'qNRName', 	index:'qNRName',width:60,	align:'center'},
              {name:'qNRType',	index:'qNRType',width:40,	align:'center'},
              {name:'review', 	index:'review',	width:50,	align:'center'},
              {name:'userType', index:'',		width:50, 	align:'center', hidden:true}
 			],
 			height:'100%',
 			autowidth:true,
 			pager: '#queslist_pager',
 			rowNum:20,
 			rowList:[20,30,40],
 			viewrecords: true,
 			sortname: 'name',
 			sortorder: 'asc',
 			caption: "问卷列表",//"未发布的问卷",
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
					var qNRNameSpan = "<span>" + qNRName + "</span>";
					var qNRTypeSpan = "<span>" + qNRType + "</span>";
					var reviewSpan = "<a class='' href='#' onclick='reviewFunc(\"" + ids[i] + "\")'>查看结果</a>";
					tb.jqGrid('setRowData',ids[i],{qNRName 	: qNRNameSpan});
					tb.jqGrid('setRowData',ids[i],{qNRType 	: qNRTypeSpan});
					tb.jqGrid('setRowData',ids[i],{review 	: reviewSpan});
				} 
		    },
 		}).navGrid('#queslist_tb',{edit:false,add:false,del:false}); 	
	}); 


	
	function reviewFunc(selRowId) {
		$(".process_dialog").show();
		$('.process_dialog').dialog({
			position : 'center',
			modal:true,
			autoOpen : true,
		});
		
		var id = $("#qNRId").val();
		var userId = $("#queslist_tb").jqGrid('getCell', selRowId, 'id');
		
		//alert(id + userId);	 
		$.post("${ContextPath}/survey/home_review/" + id, function(data) {
			$( "#questoinnaire_content" ).empty();
			
			// 通过后台数据data组装回答问卷页面
			var navi = "<table><tr><td><a id='questionnaire_home_btn' class='button' href='#' onclick='backHome()'><span class='icon icon-left'></span>返回主页</a></td></tr></table>";
			var quesId = "<input type='hidden' value='" + id + "' id='quesId'>"
			data = navi + quesId + data;
			$( "#questoinnaire_content" ).append(data);
			$('input[type=submit], a.button , button').button();
			
			// 设置问卷每道题的题号
			setQNum();
			
			jsonData = $("#questoinnaire_content :first").val();
			jsonObj = eval(jsonData);
			
			
			// 如果该专家已经填写过问卷，那么将保存的答案展现出来
			$.post("${ContextPath}/survey/home_getAnswersByQNRIdAndUserId/" + id + "/" + userId, function(answer) {
				//alert(answer);
				
				answer = eval(answer);
				// 双重each赋值语句
				$.each($(".value"), function(i,val) {
					var $val = $(val);
					if ("radio" == $val.attr("type") || "checkbox" == $val.attr("type")) {
						var itemId = $val.attr("itemId");
						// 遍历json的循环
						$(answer).each(function(idx, ans){
							//alert(ans["itemid"] + " " + itemId);
							if (ans["itemid"] == itemId) {
								$val.prop('checked', true);
							}
						}); 
					} else if ("text" == $val.attr("type")) {
						var itemId = $val.attr("itemId");
						// 遍历json的循环
						$(answer).each(function(idx, ans){
							//alert(ans["itemid"] + " " + itemId);
							if (ans["itemid"] == itemId) {
								$val.val(ans["result"]);
							}
						}); 
					}
				});
				$(".process_dialog").dialog("destroy");
				$(".process_dialog").hide();
			});
			event.preventDefault();
		  }, 'html');
	}
	
	
	
	function backHome() {
		$.post("${ContextPath}/survey/home", function(data) {
			 $( "#content" ).empty();
			  $( "#content" ).append( data );
		 	  }, 'html');
			event.preventDefault();  
	}
	
	function setQNum() {$(".process_dialog").show();
	$('.process_dialog').dialog({
		position : 'center',
		modal:true,
		autoOpen : true,
	});
		$.each($(".publishQStem"), function(i, val) {
			$(val).html("<span class='publishQNum'>" + (i + 1) + "</span>. " + $(val).html());			
		});
	}
	
	/*保存选中和填写的答案   */
	function saveAnswer() {
		$(".process_dialog2").show();
		$('.process_dialog2').dialog({
			position : 'center',
			modal:true,
			autoOpen : true,
		});
		
		var userId = $("#userId").val().trim();
		//获取答案数组
		var jsonArr = [] ;
		
		//存每个答案
		var answer = {};
		//获取所有被选中的选择题的选项
		var items = $(".value");
		$.each(items, function(i, val) {
			var qid = $("#quesId").val();
			var pid, itemid, result;
			if ($(val).attr("type") == 'radio') {
				if ($(val)[0].checked == true) {
					
					pid    = $(val).attr("name");
					itemid = $(val).attr("itemid");
					result = $(val).attr("value");
					answer = {
						"userid":userId,
						"qid"   :qid, 
						"pid"   :pid, 
						"itemid":itemid , 
						"result":result
					};
					jsonArr.push(answer);
				}
			} else if ($(val).attr("type") == 'text') {
				if ($(val).val().trim() != "") {
					pid    = $(val).attr("name");
					itemid = $(val).attr("itemid");
					result = $(val).val();
					answer = {
						"userid":userId,
						"qid"   :qid, 
						"pid"   :pid, 
						"itemid":itemid , 
						"result":result
					};
					jsonArr.push(answer);
					//alert(pid + " " + itemid + " " + result);
				}
			} else if ($(val).attr("type") == 'checkbox') {
				if ($(val)[0].checked == true) {
					pid    = $(val).attr("name");
					itemid = $(val).attr("itemid");
					result = $(val).attr("value");
					answer = {
						"userid":userId,
						"qid"   :qid, 
						"pid"   :pid, 
						"itemid":itemid , 
						"result":result
					};
					jsonArr.push(answer);
					//alert(pid + " " + itemid + " " + result);
				}
			} else {
			}
			
		});
		
		
		//console.log(JSON.stringify(jsonArr));
		//将答案json格式传入后台 存入数据库
		$.ajax({ 
	        type : "POST", 
	        url : "${ContextPath}/survey/answer_save/" , 
	        dataType : "json",      
	        contentType : "application/json",               
	        data : JSON.stringify(jsonArr), 
	        success: function(data) {
	        	$(".process_dialog2").dialog("destroy");
				$(".process_dialog2").hide();
	        	alert_dialog("答案已提交！");
	        }
	     });
	}
</script>

	

