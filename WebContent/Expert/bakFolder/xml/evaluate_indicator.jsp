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
            	url:'${ContextPath}/evaluation/progress/indicatorGetData',
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
					var level1Weight = 0;
					var level1Score = 0;
					var nextLevel1Id = "";
					var level2Weight = 0;
					var level2Score = 0;
					var nextLevel2Id = "";
					var num =1;
					var count = 1;
					for ( var i = 0; i < ids.length; i++) {
						var rowData = $('#treegrid').jqGrid("getRowData",ids[i]);
						var score = rowData["indexMapScore.score"];
						var weight = rowData["indexMapScore.weight"];
						var level1Id = rowData["level1Id"];
						var level2Id = rowData["level2Id"];
						jQuery("#treegrid").jqGrid('setRowData', ids[i], {prevScore : score});
	                    if(i<ids.length-1){
	                    	var nextRowData = $('#treegrid').jqGrid("getRowData",ids[i+1]);
	                    	nextLevel1Id = nextRowData["level1Id"];
	                    	nextLevel2Id = nextRowData["level2Id"];
	                    }  
	                    if(i==ids.length-1){
	                    	nextLevel1Id = "";
	                    	nextLevel2Id = "";
	                    }
	                    if(level1Id==nextLevel1Id){
	                    	if(score!=""&&score!=null)
								level1Score = parseFloat(level1Score) + parseFloat(score);
	                    	level1Weight = parseFloat(level1Weight) + parseFloat(weight);
	                    	num = parseInt(num) + 1;
	                    	jQuery("#treegrid").jqGrid('setRowData', ids[i], {level1Score : level1Score});
	                    }	 
						if(level2Id==nextLevel2Id){
							if(score!=""&&score!=null)
								level2Score = parseFloat(level2Score) + parseFloat(score);
							level2Weight = parseFloat(level2Weight) + parseFloat(weight);
							//console.log(level2Score);
							count = parseInt(count) + 1;
							//$("#treegrid").setCell(ids[i],'level2Score','',{display:'none'});
							jQuery("#treegrid").jqGrid('setRowData', ids[i], {level2Score : level2Score});
						}
						if(level1Id!=nextLevel1Id){
							if(score!=""&&score!=null)
								level1Score = parseFloat(level1Score) + parseFloat(score);
							level1Weight = parseFloat(level1Weight) + parseFloat(weight);
							if(parseFloat(level1Score)!=parseFloat(level1Weight))
								$("#"+rowData["level1Score"]).find("td").addClass("treeGridFont");
							jQuery("#treegrid").jqGrid('setRowData', ids[i-(num-1)], {level1Score : level1Score});
							jQuery("#treegrid").jqGrid('setRowData', ids[i-(num-1)], {level1Weight : level1Weight});
							var cellName = 'level1Score';
							var cellName2 = 'level1Weight';
							$("#" + cellName + "" + ids[i-(num-1)] + "").attr('rowspan',num);
							$("#" + cellName2 + "" + ids[i-(num-1)] + "").attr('rowspan',num);
							for(var j=i-(num-1)+1;j<i+1;j++){
								$("#treegrid").setCell(ids[j],'level1Score','',{display:'none'});
								$("#treegrid").setCell(ids[j],'level1Weight','',{display:'none'});
							}
							if(parseFloat(level1Score)!=parseFloat(level1Weight)){
								for(var k=0;k<ids.length;k++){
									var rd = $('#treegrid').jqGrid("getRowData",ids[k]);
									var l1Id = rd["level1Id"]; 
									if(level1Id==l1Id){
										$("#level1Score"+ids[k]+"").addClass("treeGridFont");
										break;
									}	
								}
							}
							level1Score = 0;
							level1Weight = 0;
							num = 1; 
						}
						if(level2Id!=nextLevel2Id){
							if(score!=""&&score!=null)
								level2Score = parseFloat(level2Score) + parseFloat(score);
							level2Weight = parseFloat(level2Weight) + parseFloat(weight);
							jQuery("#treegrid").jqGrid('setRowData', ids[i-(count-1)], {level2Score : level2Score});
							jQuery("#treegrid").jqGrid('setRowData', ids[i-(count-1)], {level2Weight : level2Weight});
							var cellName = 'level2Score';
							var cellName2 = 'level2Weight';
							$("#" + cellName + "" + ids[i-(count-1)] + "").attr('rowspan',count);
							$("#" + cellName2 + "" + ids[i-(count-1)] + "").attr('rowspan',count);
							for(var j=i-(count-1)+1;j<i+1;j++){
								$("#treegrid").setCell(ids[j],'level2Score','',{display:'none'});
								$("#treegrid").setCell(ids[j],'level2Weight','',{display:'none'})
							}
							if(parseFloat(level2Score)!=parseFloat(level2Weight)){
								//console.log("hehehe");
								for(var k=0;k<ids.length;k++){
									var rd = $('#treegrid').jqGrid("getRowData",ids[k]);
									var l2Id = rd["level2Id"]; 
									if(level2Id==l2Id){
										$("#level2Score"+ids[k]+"").addClass("treeGridFont");
										break;
									}	
								}
							}
							level2Score = 0;
							level2Weight = 0;
							count = 1; 
						}
						input = "<input type='text' class='scoreinput' value='" + score +" '/>";
						jQuery("#treegrid").jqGrid('setRowData', ids[i], {score : input});						
					}
					$("#points").val(checkPoints());
					
					$(".scoreinput").blur(function(){	
			     	   var score = $(this).val().trim();
			     	   if(score==null)
			     		   score = "";
			     	   //console.log(score);
			     	   var id = $(this).closest("td").prev().prev().prev().prev().prev().prev().prev()
			     	   .prev().prev().prev().prev().prev().prev().prev().prev().text();
			     	   var level1Id = $(this).closest("td").prev().prev().prev().prev().prev().prev()
			     	   .prev().prev().prev().prev().prev().prev().text();
			     	   var level2Id = $(this).closest("td").prev().prev().prev().prev().prev().prev()
			     	   .prev().prev().text();
			     	   var rowData = $('#treegrid').jqGrid("getRowData",id);
			     	   var prevScore = rowData["indexMapScore.score"];
			     	   if(prevScore==null||prevScore=="")
			     		   prevScore = 0;
			     	   //console.log(prevScore);
			     	   var level1Score = 0;
			     	   var level2Score = 0;
			     	   var ids = $("#treegrid").jqGrid('getDataIDs'); 
			     	   for(var i=0;i<ids.length;i++){
			     		  var rowData2 = $('#treegrid').jqGrid("getRowData",ids[i]);
			     		  var level1Id2 = rowData2["level1Id"];
			     		  var level2Id2 = rowData2["level2Id"];
			     		  if(level1Id2==level1Id){
			     			  if(parseFloat(level1Score)<parseFloat(rowData2["level1Score"]))
			     				  level1Score = rowData2["level1Score"];
			     		  }  
			     		  if(level2Id2==level2Id){
			     			  if(parseFloat(level2Score)<parseFloat(rowData2["level2Score"]))
			     				  level2Score = rowData2["level2Score"];
			     		  }  
			     	   } 
			     	   var totalScore = $("#points").val();
			     	   if(score==""){
			     		  level1Score = parseFloat(level1Score) - parseFloat(prevScore);
			     		  level2Score = parseFloat(level2Score) - parseFloat(prevScore);
			     		  $("#points").val(parseFloat(totalScore)-parseFloat(prevScore));
			     		  console.log(level2Score);
			     	   }
			     	   else {
			     		  level1Score = parseFloat(level1Score) + parseFloat(score) - parseFloat(prevScore);
			     		  level2Score = parseFloat(level2Score) + parseFloat(score) - parseFloat(prevScore);
			     		  $("#points").val(parseFloat(totalScore)-parseFloat(prevScore) + parseFloat(score));
			     		  //console.log(level2Score);
			     	   }
			     	   jQuery("#treegrid").jqGrid('setRowData', id, {'indexMapScore.score' : score});
			     	   for(var i=0;i<ids.length;i++){
			     		  var rowData2 = $('#treegrid').jqGrid("getRowData",ids[i]);
			     		  var level1Id2 = rowData2["level1Id"];
			     		  var level1Weight = rowData2["level1Weight"];
			     		  var level2Id2 = rowData2["level2Id"];
			     		  var level2Weight = rowData2["level2Weight"];
			     		  if(level1Id==level1Id2){
			     			 jQuery("#treegrid").jqGrid('setRowData', ids[i], {'level1Score' : level1Score});
			     			 if(parseFloat(level1Score)!=parseFloat(level1Weight))
			     				 $("#level1Score"+ids[i]).addClass("treeGridFont");
			     			 else $("#level1Score"+ids[i]).addClass("treeGridFont2");
			     		  }	 
			     		  if(level2Id==level2Id2){
			     			 //console.log("lalalala");
			     			 jQuery("#treegrid").jqGrid('setRowData', ids[i], {'level2Score' : level2Score});
			     			if(parseFloat(level2Score)!=parseFloat(level2Weight))
			     				 $("#level2Score"+ids[i]).addClass("treeGridFont");
			     			else $("#level2Score"+ids[i]).addClass("treeGridFont2");
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
       	for(var i=0;i<ids.length;i++){
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
       		indexItemIds.push(indexItemId);
       		scores.push(score);
       		questionIds.push(questionId);
       		resultIds.push(resultId);
       		prevScores.push(prevScore);
       	}
       	//console.log(scores);
       	if(scores.length==ids.length){
       		var evalIndexItems = {"indexItemIds":indexItemIds,"scores":scores,"questionIds":questionIds,"resultIds":resultIds,"prevScores":prevScores};
       		$.ajax({ 
                   type : "POST", 
                   url : "${ContextPath}/evaluation/progress/indicator_submitAllResults", 
                   dataType : "json",      
                   contentType : "application/json",               
                   data : JSON.stringify(evalIndexItems), 
                   success:function(data){
                   	//alert_dialog("提交成功");
                   	$("#treegrid").setGridParam({url:'${ContextPath}/evaluation/progress/indicatorGetData/'}).trigger("reloadGrid");
                   	createNextQuestionRouteEle(data[0].nextQuestionRoute);
                   }
                });
           };
       };
       
       
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
<%-- <div class="selectbar">     
<a id="calculate" href="#" class="button">显示打分进度</a>
</div>
  
<input id="process" type="hidden" value="${process}"> --%>



 




 
