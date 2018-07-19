<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="${ContextPath}/js/expert.common.js"></script>

<style type='text/css'>
       .treeGridFont{
       font-family: Lucida Grande,Lucida Sans,Arial,sans-serif;
       font-size: 1.1em;
       font-weight: bold;
       color: red;
       text-align: center;
       }
       
       
       .treeGridFont2{
       font-family: Lucida Grande,Lucida Sans,Arial,sans-serif;
       font-size: 1.1em;
       font-weight: bold;
       color: black;
       text-align: center;
       }
</style>
    

<script type="text/javascript">  
       $(document).ready(function(){          	
     
    	   $("input[type=submit], a.button, button").button();    	      	      	   	       	   
    	   
            jQuery("#treegrid").jqGrid({  
            	url:'${ContextPath}/evaluation/progress/getResults',
                datatype: 'json', 
                mtype : 'GET',
            
                colNames:['指标项ID',	'指标项级别',	
                          '一级指标',	'一级指标ID',	'参考值',	'实际值',
                          '二级指标',	'二级指标ID',	'二级指标父ID', '参考值','实际值',
                          '末级指标', '末级指标父ID',			 '权重参考值',
           				  '分数','打分','状态','结果ID','上次打分','题目ID'],  
                colModel:[
                    {name:'id',						 index:'id', 					   width:90,hidden:true},
                    {name:'indexMapScore.indexLevel',index:'indexMapScore.indexLevel', width:90,	align : "center",hidden:true},
                    {name:'level1Name',		 index:'level1Name', 	   width:150,
                    	cellattr: function(rowId, tv, rawObject, cm, rdata) {
                            //合并单元格
                            return 'id=\'level1Name' + rowId + "\'";
                        }},
                    {name:'level1Id',		 index:'level1Id', 	   width:90,hidden:true},
                    {name:'level1Weight',     index:'level1Weight',  width:60,align : "center",
                        cellattr: function(rowId, tv, rawObject, cm, rdata) {
                            //合并单元格
                            return 'id=\'level1Weight' + rowId + "\'";
                        }}, 
                    {name:'level1Score',     index:'level1Score', 	   width:60,align : "center",
                    	cellattr: function(rowId, tv, rawObject, cm, rdata) {
                            //合并单元格
                            return 'id=\'level1Score' + rowId + "\'";
                        }},
                    {name:'level2Name',		 index:'level2Name', 	   width:150,
                    	cellattr: function(rowId, tv, rawObject, cm, rdata) {
                            //合并单元格
                            return 'id=\'level2Name' + rowId + "\'";
                        }},
                    {name:'level2Id',		 index:'level2Id', 	   width:90,hidden:true},
                    {name:'level2ParentId',		 index:'level2ParentId', 	   width:90,hidden:true},
                    {name:'level2Weight',		 index:'level2Weight', 	   width:60,align : "center",
                        cellattr: function(rowId, tv, rawObject, cm, rdata) {
                            //合并单元格
                            return 'id=\'level2Weight' + rowId + "\'";
                        }},
                    {name:'level2Score',		 index:'level2Score', 	   width:60,align : "center",
                    	cellattr: function(rowId, tv, rawObject, cm, rdata) {
                            //合并单元格
                            return 'id=\'level2Score' + rowId + "\'";
                        }},
                    {name:'indexMapScore.name',		 index:'indexMapScore.name', 	   width:370},  
                    {name:'indexMapScore.parentId',  index:'indexMapScore.parentId',   width:90,	align : "center",hidden:true},
                    {name:'indexMapScore.weight',	 index:'indexMapScore.weight',	   width:90, 	align : "center"},                   
                    {name:'indexMapScore.score',	 index:'indexMapScore.score',	   width:90,	align : "center",hidden:true},
                    {name:'score',					 index:'score',width:90},
                    {name:'state',					 index:'state',width:90,hidden:true},
                    {name:'indexMapScore.resultId',			     index:'resultId',width:90,hidden:true},
                    {name:'prevScore',	 index:'prevScore',	   width:90,	align : "center",hidden:true},
                    {name:'indexMapScore.questionId',		   index:'questionId',width:90,hidden:true}
                    
                ],                                                          
               
                jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
        			root : "rows", //包含实际数据的数组  
        			page : "pageIndex", //当前页  
        			total : "totalPage",//总页数  
        			records : "totalCount", //查询出的记录数  
        			repeatitems : false
        		},
        		beforeSelectRow: function(rowid, e) {
        		    return false;
        		},
                caption: "指标体系",             
                pager : '#pager',
                height:"100%",
                autowidth : true,
                viewrecords:true,    // 设为具体数值则会根据实际记录数出现垂直滚动条  
                sortorder : "desc",
        		sortname : "level1Name",
        		rowNum: 60,

				 gridComplete : function() {
					var gridName = "treegrid"; 
					Merger(gridName,'level1Name');
					Merger(gridName,'level2Name');
					var ids = jQuery("#treegrid").jqGrid(
							'getDataIDs');
					var input;	
					//建立变量存储遍历到某一行后的累加的一级指标项权重
					var level1Weight = 0;
					//建立变量存储遍历到某一行后的累加的一级指标项分数
					var level1Score = 0;
					var nextLevel1Id = "";
					var level2Weight = 0;
					var level2Score = 0;
					var nextLevel2Id = "";
					//记录一级指标ID相同的数据的行数，初值为1
					var level1Count =1;
					//记录二级指标项ID相同的数据的行数
					var level2Count = 1;
					for ( var i = 0; i < ids.length; i++) {
						var rowData = $('#treegrid').jqGrid("getRowData",ids[i]);
						var score = rowData["indexMapScore.score"];
						var weight = rowData["indexMapScore.weight"];
						//获得本行一级指标ID
						var level1Id = rowData["level1Id"];
						var level2Id = rowData["level2Id"];
						jQuery("#treegrid").jqGrid('setRowData', ids[i], {prevScore : score});
						input = "<input type='text' class='scoreinput' value='" + score +"'/>";
						jQuery("#treegrid").jqGrid('setRowData', ids[i], {score : input});
	                    if(i<ids.length-1){
	                    	var nextRowData = $('#treegrid').jqGrid("getRowData",ids[i+1]);
	                    	//获得本行下一行的一级指标ID
	                    	nextLevel1Id = nextRowData["level1Id"];
	                    	nextLevel2Id = nextRowData["level2Id"];
	                    }  
	                    //如果循环到最后一行，则将nextLevel1Id和nextLevel2Id置为空
	                    if(i==ids.length-1){
	                    	nextLevel1Id = "";
	                    	nextLevel2Id = "";
	                    }
	                   /*  判断，若本行的一级指标ID和下一行的一级指标ID相同且本行的一级指标项打分不为空
	                                                                     则将level1Score与本行打分值相加，level1Count自增1，
	                                                                    并将本行的一级指标分数重置为和本行打分值相加后的level1Score，权重同理 */
	                    if(level1Id==nextLevel1Id){
	                    	if(score!=""&&score!=null){
	                    		level1Score = parseFloat(level1Score) + parseFloat(score);
	                    	}
	                    	level1Weight = parseFloat(level1Weight) + parseFloat(weight);
	                    	level1Count = parseInt(level1Count) + 1;
	                    }	 
						if(level2Id==nextLevel2Id){
							if(score!=""&&score!=null){
								level2Score = parseFloat(level2Score) + parseFloat(score);
							}
							level2Weight = parseFloat(level2Weight) + parseFloat(weight);
							level2Count = parseInt(level2Count) + 1;
						}
						//如果本行的一级指标ID和下一行的不同
						if(level1Id!=nextLevel1Id){
							if(score!=""&&score!=null){
								//如果本行打分项不为空，则level1Score加上本行打分值
								level1Score = parseFloat(level1Score) + parseFloat(score);
							}	
							if(parseInt(level1Score)!=level1Score)
								level1Score = level1Score.toFixed(2);
							//权重同理
							level1Weight = parseFloat(level1Weight) + parseFloat(weight);
							//将所有的一级指标项ID相同的行的一级指标项打分列和权重列分别赋值为最后累加下来的level1Score和level1Weight
							for(var temp=i-(level1Count-1);temp<i+1;temp++){
								jQuery("#treegrid").jqGrid('setRowData', ids[temp], {level1Score : level1Score});
								jQuery("#treegrid").jqGrid('setRowData', ids[temp], {level1Weight : level1Weight});
							}
							var cellName = 'level1Score';
							var cellName2 = 'level1Weight';
							//将与本行一级指标项ID相同的所有数据的第一行的level1Score列的跨越行数设置为level1Count，权重同理
							$("#" + cellName + "" + ids[i-(level1Count-1)] + "").attr('rowspan',level1Count);
							$("#" + cellName2 + "" + ids[i-(level1Count-1)] + "").attr('rowspan',level1Count);
							//将除去第一行以外的所有行的一级指标项打分列隐藏，权重同理
							for(var j=i-(level1Count-1)+1;j<i+1;j++){
								$("#treegrid").setCell(ids[j],'level1Score','',{display:'none'});
								$("#treegrid").setCell(ids[j],'level1Weight','',{display:'none'});
							}
							//如果累加的level1Score和level1Weight不同，则遍历所有的数据，找到一级指标ID和本行相同的
							//并将其一级指标项打分列的字体改变样式
							if(parseFloat(level1Score)!=parseFloat(level1Weight)){
								for(var k=0;k<ids.length;k++){
									var check = $('#treegrid').jqGrid("getRowData",ids[k]);
									var level1IdCheck = check["level1Id"]; 
									if(level1Id==level1IdCheck){
										$("#level1Score"+ids[k]+"").addClass("treeGridFont");
										break;
									}	
								}
							}
							//重置所有临时变量
							level1Score = 0;
							level1Weight = 0;
							level1Count = 1; 
						}
						if(level2Id!=nextLevel2Id){
							if(score!=""&&score!=null){
								level2Score = parseFloat(level2Score) + parseFloat(score);
							}
							if(parseInt(level2Score)!=level2Score)
								level2Score = level2Score.toFixed(2);
							level2Weight = parseFloat(level2Weight) + parseFloat(weight);
							for(var temp=i-(level2Count-1);temp<i+1;temp++){
								jQuery("#treegrid").jqGrid('setRowData', ids[temp], {level2Score : level2Score});
								jQuery("#treegrid").jqGrid('setRowData', ids[temp], {level2Weight : level2Weight});
							}
							var cellName = 'level2Score';
							var cellName2 = 'level2Weight';
							$("#" + cellName + "" + ids[i-(level2Count-1)] + "").attr('rowspan',level2Count);
							$("#" + cellName2 + "" + ids[i-(level2Count-1)] + "").attr('rowspan',level2Count);
							for(var j=i-(level2Count-1)+1;j<i+1;j++){
								$("#treegrid").setCell(ids[j],'level2Score','',{display:'none'});
								$("#treegrid").setCell(ids[j],'level2Weight','',{display:'none'});
							}
							if(parseFloat(level2Score)!=parseFloat(level2Weight)){
								for(var k=0;k<ids.length;k++){
									var check = $('#treegrid').jqGrid("getRowData",ids[k]);
									var level2IdCheck = check["level2Id"]; 
									if(level2Id==level2IdCheck){
										$("#level2Score"+ids[k]+"").addClass("treeGridFont");
										break;
									}	
								}
							}
							level2Score = 0;
							level2Weight = 0;
							level2Count = 1; 
						}
												
					}
					$("#points").val(checkPoints());
					
					$(".scoreinput").blur(function(){	
			     	   var score = $(this).val().trim();
			     	   if(score==null||score=="")
			     		   score = "";
			     	   var id = $(this).closest("td").prev().prev().prev().prev().prev().prev().prev()
			     	   .prev().prev().prev().prev().prev().prev().prev().prev().text();
			     	   //获得本行的一级指标ID
			     	   var level1Id = $(this).closest("td").prev().prev().prev().prev().prev().prev()
			     	   .prev().prev().prev().prev().prev().prev().text();
			     	   var level2Id = $(this).closest("td").prev().prev().prev().prev().prev().prev()
			     	   .prev().prev().text();
			     	   var rowData = $('#treegrid').jqGrid("getRowData",id);
			     	   //记录本行的上一次打分，注意此变量会根据上一次相关的blur事件变化
			     	   var prevScore = rowData["indexMapScore.score"];
			     	   if(prevScore==null||prevScore=="")
			     		   prevScore = 0;
			     	   if(parseInt(prevScore)!=prevScore)
			     		   prevScore = parseFloat(prevScore).toFixed(2);
			     	   console.log(prevScore);
			     	   //建立变量存储一级指标项打分
			     	   var level1Score = 0;
			     	   var level2Score = 0;
			     	   var ids = $("#treegrid").jqGrid('getDataIDs'); 
			     	   //遍历所有数据，找到本行对应的一级指标项打分
			     	   for(var i=0;i<ids.length;i++){
			     		  var check = $('#treegrid').jqGrid("getRowData",ids[i]);
			     		  var level1IdCheck = check["level1Id"];
			     		  var level2IdCheck = check["level2Id"];
			     		  if(level1IdCheck==level1Id){
			     			  level1Score = check["level1Score"];
			     		  }  
			     		  if(level2IdCheck==level2Id){
			     			  level2Score = check["level2Score"];
			     			  //console.log(level2Score);
			     			  //console.log(prevScore);
			     		  }  
			     	   } 
			     	   var totalScore = $("#points").val();
			     	   //如果打分一栏为空则视为0，level1Score减去上一次的打分，level2Score同理
			     	   if(score==""){
			     		  level1Score = parseFloat(level1Score) - parseFloat(prevScore);
			     		  if(parseInt(level1Score)!=level1Score)
			     			  level1Score = level1Score.toFixed(2);
			     		  level2Score = parseFloat(level2Score) - parseFloat(prevScore);
			     		  if(parseInt(level2Score)!=level2Score)
			     			  level2Score = level2Score.toFixed(2);
			     		  else level2Score = parseInt(level2Score);
			     		  $("#points").val((parseFloat(totalScore)-parseFloat(prevScore)).toFixed(2));
			     	   }
			     	   //如果打分一栏不为0，则减去上一次打分并加上本次打分
			     	   else {
			     		  level1Score = parseFloat(level1Score) + parseFloat(score) - parseFloat(prevScore);
			     		  if(parseInt(level1Score)!=level1Score)
			     			  level1Score = level1Score.toFixed(2);
			     		  level2Score = parseFloat(level2Score) + parseFloat(score) - parseFloat(prevScore);
			     		  if(parseInt(level2Score)!=level2Score)
			     			  level2Score = level2Score.toFixed(2);
			     		  $("#points").val((parseFloat(totalScore)-parseFloat(prevScore) + parseFloat(score)).toFixed(2));
			     	   }
			     	   if(score=="")
			     		   score = 0;
			     	   if(parseInt(score)!=score)
			     		   score = parseFloat(score).toFixed(2);
			     	   console.log(score);
			     	   //将保存打分值的隐藏列重置为打分栏中的分值
			     	   jQuery("#treegrid").jqGrid('setRowData', id, {'indexMapScore.score' : score});
			     	   /* 遍历所有数据 ，找到所有一级指标ID与本行相同的行，
			     	                  检查其一级指标打分与权重是否相同并设置一级指标打分列对应的字体样式
			     	                  二级指标同理*/
			     	   for(var temp=0;temp<ids.length;temp++){
			     		  var checkForFont = $('#treegrid').jqGrid("getRowData",ids[temp]);
			     		  var level1IdFont = checkForFont["level1Id"];
			     		  var level1WeightFont = checkForFont["level1Weight"];
			     		  var level2IdFont = checkForFont["level2Id"];
			     		  var level2WeightFont = checkForFont["level2Weight"];
			     		  if(level1Id==level1IdFont){
			     			 $("#level1Score"+ids[temp]).removeAttr("class");
			     			 jQuery("#treegrid").jqGrid('setRowData', ids[temp], {'level1Score' : level1Score});
			     			 if(parseFloat(level1Score)!=parseFloat(level1WeightFont)){
			     				 $("#level1Score"+ids[temp]).addClass("treeGridFont");
			     			 } 
			     			 else $("#level1Score"+ids[temp]).addClass("treeGridFont2");
			     		  }	 
			     		  if(level2Id==level2IdFont){
			     			 $("#level2Score"+ids[temp]).removeAttr("class");
			     			 jQuery("#treegrid").jqGrid('setRowData', ids[temp], {'level2Score' : level2Score});
			     			if(parseFloat(level2Score)!=parseFloat(level2WeightFont))
			     				 $("#level2Score"+ids[temp]).addClass("treeGridFont");
			     			else $("#level2Score"+ids[temp]).addClass("treeGridFont2");
			     		  }	 
			     	   }
			        });  
				} 
            }).navGrid('#pager', {edit : true,add : false,del : false});  
            
          //公共调用方法
            function Merger(gridName, CellName) {
                //得到显示到界面的id集合
                var mya = $("#" + gridName + "").getDataIDs();
                //当前显示多少条
                var length = mya.length;
                for (var i = 0; i < length; i++) {
                    //从上到下获取一条信息
                    var before = $("#" + gridName + "").jqGrid('getRowData', mya[i]);
                    //定义合并行数
                    var rowSpanTaxCount = 1;
                    for (j = i + 1; j <= length; j++) {
                        //和上边的信息对比 如果值一样就合并行数+1 然后设置rowspan 让当前单元格隐藏
                        var end = $("#" + gridName + "").jqGrid('getRowData', mya[j]);
                        if (before[CellName] == end[CellName]) {
                            rowSpanTaxCount++;
                            $("#" + gridName + "").setCell(mya[j], CellName, '', { display: 'none' });
                        } else {
                            rowSpanTaxCount = 1;
                            break;
                        }
                        $("#" + CellName + "" + mya[i] + "").attr("rowspan", rowSpanTaxCount);
                    }
                }
            }  
        });
       
      function checkPoints(){
       	var ids = $("#treegrid").jqGrid('getDataIDs');
       	var totalScore = 0;
       	for(var i=0;i<ids.length;i++){
       		var rowData = $("#treegrid").jqGrid("getRowData",ids[i]);
       		var s = rowData['score'];
       		//console.log(s);
       		var score = "";
    		for(var j=0;j<s.length;j++){
    			if(((47<s.charCodeAt(j))&&(s.charCodeAt(j)<58))||s[j]==".")
    				score = score + s.charAt(j);
    		}
       		//console.log(score);
       		if(score==null||score=="")
       			score = 0;
       		totalScore = parseFloat(totalScore) + parseFloat(score); 
       	}
       	total = totalScore.toFixed(2);
       	return totalScore;
       } 
        
        $("#calculate").click(function(){
        	   var process = $("#process").val();
        	   console.log(process);
        	   var process2 = process*100;
        	   $(".divProgressbar").progressbar({value: process2});        
           });     
       
       $("#submitAllResults1").click(function(){
           save();      
       });
       $("#submitAllResults2").click(function(){
	       save();
       });
       
       
       
       save = function() {
    	   var results = [];
    	   var total = $("#points").val();
    	   console.log(total);
    	   var standerd = 100;
    	   if(parseInt(total)==parseInt(standerd)){
    		   console.log("lalala");
    	   }
    	   else alert_dialog("注意！提交时所有打分项分值总和应为100");
       	var ids = $("#treegrid").jqGrid('getDataIDs');
       	var indexItemIds = [];
       	var scores = [];
       	var questionIds = [];
       	var resultIds = [];
       	var prevScores = [];
       	for (var i = 0; i < ids.length; i++) {
       		var rowData = $('#treegrid').jqGrid("getRowData",ids[i]);
       		var indexItemId = rowData["id"];
       		var score = rowData["indexMapScore.score"];
       		var questionId = rowData["indexMapScore.questionId"];
       		var resultId = rowData["indexMapScore.resultId"];
       		var prevScore = rowData["prevScore"];
       		if(resultId==null)
       			resultId = "";
       		/* var score = "";
       		for(var j=0;j<s.length;j++){
       			if(((47<s.charCodeAt(j))&&(s.charCodeAt(j)<58))||(s[j]=="."))
       				score = score + s.charAt(j);
       		} */
       		//indexItemIds.push(indexItemId);
       		scores.push(score);
       		questionIds.push(questionId);
       		resultIds.push(resultId);
       		prevScores.push(prevScore);
       		
       		evalResultFromJSP = {"resultId" : resultId, "value" : score, "questionId" : questionId}
        	
        	results.push(evalResultFromJSP);
       	}
       	//console.log(scores);
       	if(scores.length==ids.length){
       		//var evalIndexItems = {"indexItemIds":indexItemIds,"scores":scores,"questionIds":questionIds,"resultIds":resultIds,"prevScores":prevScores};
       		$.ajax({ 
                   type : "POST", 
                   url : "${ContextPath}/evaluation/progress/indicIdxSaveOrUpdateResults", 
                   dataType : "json",      
                   contentType : "application/json",               
                   data : JSON.stringify(results), 
                   success:function(data){
                   	//alert_dialog("提交成功");
                   	results = [];
                   	$("#treegrid").setGridParam({url:'${ContextPath}/evaluation/progress/getResults/'}).trigger("reloadGrid");
                   	createNextQuestionRouteEle(data[0].nextQuestionRoute);
                   }
                });
           };
       };
       
       function openLink(url) {
    	   var results = [];
    	   var total = $("#points").val();
    	   console.log(total);
    	   var standerd = 100;
    	   if(parseInt(total)==parseInt(standerd)){
    		   console.log("lalala");
    	   }
    	   else {
    		   $.post(url, function(data){
					  $( "#content" ).empty();
					  $( "#content" ).append( data );
				  }, 'html');
    		   return;
    	   };
       	var ids = $("#treegrid").jqGrid('getDataIDs');
       	var indexItemIds = [];
       	var scores = [];
       	var questionIds = [];
       	var resultIds = [];
       	var prevScores = [];
       	for (var i = 0; i < ids.length; i++) {
       		var rowData = $('#treegrid').jqGrid("getRowData",ids[i]);
       		var indexItemId = rowData["id"];
       		var score = rowData["indexMapScore.score"];
       		var questionId = rowData["indexMapScore.questionId"];
       		var resultId = rowData["indexMapScore.resultId"];
       		var prevScore = rowData["prevScore"];
       		if(resultId==null)
       			resultId = "";
       		/* var score = "";
       		for(var j=0;j<s.length;j++){
       			if(((47<s.charCodeAt(j))&&(s.charCodeAt(j)<58))||(s[j]=="."))
       				score = score + s.charAt(j);
       		} */
       		//indexItemIds.push(indexItemId);
       		scores.push(score);
       		questionIds.push(questionId);
       		resultIds.push(resultId);
       		prevScores.push(prevScore);
       		
       		evalResultFromJSP = {"resultId" : resultId, "value" : score, "questionId" : questionId}
        	
        	results.push(evalResultFromJSP);
       	}
       	//console.log(scores);
       	if(scores.length==ids.length){
       		//var evalIndexItems = {"indexItemIds":indexItemIds,"scores":scores,"questionIds":questionIds,"resultIds":resultIds,"prevScores":prevScores};
       		$.ajax({ 
                   type : "POST", 
                   url : "${ContextPath}/evaluation/progress/indicIdxSaveOrUpdateResults", 
                   dataType : "json",      
                   contentType : "application/json",               
                   data : JSON.stringify(results), 
                   success:function(data){
                	   $.post(url, function(data){
     					  $( "#content" ).empty();
     					  $( "#content" ).append( data );
     				  }, 'html');
                   }
                });
           };
       }
       
       
    </script>  
    

 <div class="con_header">
	<h3>
		<span class="icon icon-web"></span>指标体系评价
		<input id="points"  value="" style="float:right" disabled="disabled">
		<label style="float:right">打分值总和：  </label> 
	</h3>
	
	    
	
</div>

<!-- <div class="selectbar">     
<input id="points"  value="">
</div> -->
<input id="nextQuestionRoute" type="hidden" value="${nextQuestionRoute}"/> 

<!-- 导航箭头 -->
<jsp:include page="eval_navi.jsp"/>

<div class="selectbar layout_holder" style="width:100%;">
	<table id="treegrid"></table>
	<div id="pager"></div>
</div>

<div class="selectbar inner_table_holder" style="width:100%">
	<table class="layout_table right">
		<tbody>
			<tr>
				<td width="115"><a id="submitAllResults2" class="button" href="#"><span
						class="icon icon-store "></span>整体保存</a></td>
			</tr>
		</tbody>
	</table>
</div>

<!-- <div class="selectbar inner_table_holder">
	<table class="layout_table right">
		<tbody>
			<tr>
				<td width="90"><a id="unSubmitAllResults" class="button" href="#"><span
						class="icon icon-store "></span>删除所有提交</a></td>
			</tr>
		</tbody>
	</table>
</div> -->

<!-- <div class="progressbar_div">
           <table class="fr_table">                                           
                 <tr>
                    <td>
                        <div style="float:left">
                            <label class="progress"></label>
                        </div>
                    </td>
                    <td>
                        <div class="divProgressbar" style='width:800px;float:left'>
                        </div>
                    </td> 
                 </tr>
           </table>
      </div> -->
<!-- 导航箭头 -->
<jsp:include page="eval_navi.jsp"/>
<%-- <div class="selectbar">     
<a id="calculate" href="#" class="button">显示打分进度</a>
</div>
  
<input id="process" type="hidden" value="${process}"> --%>



 




 
