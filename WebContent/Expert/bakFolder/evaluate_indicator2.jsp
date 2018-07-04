<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

 <div class="con_header">
	<h3>
		<span class="icon icon-web"></span>指标体系评价
	</h3>
</div>

<div class="selectbar inner_table_holder">
<table class="layout_table left">
	<tbody>
		<tr>
			<td width="115">
				<a id="save1" class="button" href="#">
					<span class="icon icon-store"></span>整体保存
				</a>
			</td>
		</tr>
	</tbody>
</table>
</div>
<div class="cur">
	<table id="treegrid"></table>
</div>
	
	
<table class="layout_table left">
	<tbody>
		<tr>
			<td width="115">
				<a id="save2" class="ui-button-text" href="#">
					<span class="icon icon-store"></span>整体保存
				</a>
			</td>
		</tr>
	</tbody>
</table>
 
<script src="${ContextPath}/js/grid.treegrid.js"></script>
   
    <style type='text/css'>
       .treeGridFont{
       font-family: Lucida Grande,Lucida Sans,Arial,sans-serif;
       font-size: 1.1em;
       font-weight: bold;
       color: blue;
       text-align: center;
       }
       
       .secondLevelStyle {
       font-family: Lucida Grande,Lucida Sans,Arial,sans-serif;
       font-size: 1.2em;
       text-align: center;
       }
       
       .firstLevelStyle {
       font-family: Lucida Grande,Lucida Sans,Arial,sans-serif;
       font-size: 1.3em;
       text-align: center;
       }
    </style>
      
    <script type="text/javascript">  
     
    	   $("input[type=submit], a.button, button").button();
    	   
            jQuery("#treegrid").jqGrid({  
            	url:'${ContextPath}/evaluation/indicatorGetData',
                treeGrid: true,  
                treeGridModel: 'adjacency', //treeGrid模式，跟json元数据有关 ,adjacency/nested  
                ExpandColumn : 'indexMapScore.name',
                ExpandColClick:true, 
               
                datatype: 'json', 
                mtype : 'GET',
                treedatatype: 'json',
                
                colNames:['指标项ID','指标项级别','指标项名称','父节点ID','权重','分数','打分','保存'],  
                colModel:[
                    {name : 'id',						index : 'id', 					   	 width : 90,	hidden:true},
                    {name : 'indexMapScore.indexLevel', index : 'indexMapScore.indexLevel',  width : 90,	align : "center",	hidden:true},
                    {name : 'indexMapScore.name',		index : 'indexMapScore.name', 	   	 width : 400},
                    {name : 'indexMapScore.parentId',  	index : 'indexMapScore.parentId',    width : 90,	align : "center",	hidden:true},
                    {name : 'indexMapScore.weight',	 	index : 'indexMapScore.weight',	 	 width : 90, 	align : "center"},
                    {name : 'indexMapScore.score',	 	index : 'indexMapScore.score',	   	 width : 90,	align : "center",	hidden:true},
                    {name : 'score',				 	index : 'score',					 width : 90},
                    {name : 'saveButton', 				index : 'saveButton', 				 width : 50} 
                    
                ],                                                          
                treeReader : {  
                    level_field: "level",  
                    parent_id_field: "parent",  
                    leaf_field: "isLeaf",  
                    load_field: "loaded",
                    expanded_field: "expanded"  
                },  
                jsonReader: {
         	       repeatitems: false,
         	       root: "rows"
         	   }, 
                caption: "指标体系权重打分",             
                page:false,
                height:"100%",
                viewrecords:true,    // 设为具体数值则会根据实际记录数出现垂直滚动条  
               

				 gridComplete : function() {
					var ids = jQuery("#treegrid").jqGrid('getDataIDs');
					var input;
					var save;								
					for ( var i = 0; i < ids.length; i++) {
						var rowData = $('#treegrid').jqGrid("getRowData",ids[i]);
						var id = rowData["id"];
						var score = rowData["indexMapScore.score"];
						var level = rowData["indexMapScore.indexLevel"];
						console.log(id + "level:" + level);
						if(level==3){
							input = "<input type='text' value='" + score +" '/>";					
							save = "<input type='button' class='saveButton' value='编辑' id='button"+ i + "'/>";
							jQuery("#treegrid").jqGrid(
									'setRowData', ids[i], {
										score : input
									});
							jQuery("#treegrid").jqGrid(
									'setRowData', ids[i], {
										saveButton : save
									});
							 //$("#"+rowData["id"]).find("td").css("font-color","#00FFFF");
							 $("#"+rowData["id"]).find("td").addClass("treeGridFont");
						}
						else if (2 == level) {
							 $("#"+rowData["id"]).find("td").addClass("secondLevelStyle");
						} else if (1 == level) {
							 $("#"+rowData["id"]).find("td").addClass("firstLevelStyle");
						}
					}
				} 
            });  
            
            $('#treegrid').on("click", ".saveButton", function() {
        		var score = $(this).closest("td").prev().children(0).val().trim();
        		var weight = $(this).closest("td").prev().prev().prev().text();
        		console.log(weight);
        		var indexItemId = $(this).closest("td").prev().prev().prev().prev().prev().prev().prev().text();     
        		if(score!=""){
        			/* if((score-0)>(weight-0)){
        				alert_dialog("打分值必须低于权重");
        				 $('#treegrid').trigger("reloadGrid");
        			} */
        			/* if((score-0)<(weight-0)){ */
        				var evalIndexItem={"score":score,"indexItemId":indexItemId};  
                        $.ajax({ 
                            type : "POST", 
                            url : "${ContextPath}/evaluation/indicator_saveSingleResult", 
                            dataType : "json",      
                            contentType : "application/json",               
                            data : JSON.stringify(evalIndexItem), 
                            success:alert_dialog("保存成功"), 
                         });       
        			}         		
        		/* } */
        		else alert_dialog("请输入打分值再保存");
                
        	});
            
            $("#calculate").click(function(){
           	   var process = $("#process").val();
           	   console.log(process);
           	   var process2 = process*100;
           	   $(".divProgressbar").progressbar({value: process2});        
              });          
            
            function wholeSave() {
            	var ids = jQuery("#treegrid").jqGrid('getDataIDs');
				var input;
				var save;								
				for ( var i = 0; i < ids.length; i++) {
					var rowData = $('#treegrid').jqGrid("getRowData",ids[i]);
					var score = rowData["indexMapScore.score"];
					console.log(i + " " + score);
				}
              	/* $.ajax({ 
        	            type : "POST", 
        	            url : "${ContextPath}/evaluation/reputation_saveMultiResults", 
        	            dataType : "json",      
        	            contentType : "application/json",               
        	            data : JSON.stringify(results), 
        	            success:function(data){ 
        	            	alert("打分已经保存");    
        	            } 
        	         }); */
        	}
        	$("#save1").click(function() {
        		wholeSave();		        
        	});   
        	$("#save2").click(function() {
        		wholeSave();		        
        	});       
       
       
    </script>  


<div class="progressbar_div">
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
      </div>  
<div class="selectbar">     
<a id="calculate" href="#" class="button">显示打分进度</a>
</div>
  
<input id="process" type="hidden" value="${process}">

 