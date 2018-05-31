<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header">
	<h3>
		<span class="icon icon-publiccontrast"></span>学科报告生成
	</h3>
</div>

 <div class="tabs">
	<ul>
        <li><a href="#dsep_report" >学科报告</a></li>
        <li><a href="#add_report" >补充报告</a></li>
        <li><a href="#department_report" >单位报告</a></li>
        <li><a href="#no_attend_report" >未参评报告</a></li>
    </ul>
    <div id="dsep_report">
    	<div class="con_header">
             <a id="calculate" class="button" href="#"><span class="icon icon-report"></span>开始生成</a>
             <a id="viewresult" href="#" class="button"><span class="icon icon-see"></span>查看进度</a>
			 <a id="itemDel" class="button" href="#"><span class="icon icon-see"></span>查看错误日志</a>
			 <a id="report_public" class="button" href="#"><span class="icon icon-download"></span>发布报告</a>	
        </div>
        <div id="report_rule" class="report_generate" >
        	 	<table class="fr_table">
        	 		<tr>
						<td class="fr_right">
							<label for="xks" class="TextFont">学校:</label>
						</td>
						<td class="fr_left">
						    <select name="select1" id="select1" style="width:160px;" > 
								<option value="1">全部</option> 
								<option value="2">高校</option> 
								<option value="3">科研院所</option> 
							</select> 
						</td>
						<td class="fr_right">
							<label for="xks" class="TextFont">学科:</label>
						</td>
						<td class="fr_left">
						    <select name="select2" id="select2" style="width:160px;" > 
								<option value="1">全部</option> 
								<option value="2">计算机学</option> 
								<option value="3">软件工程</option> 
							</select> 
						</td>
						<td class="fr_right">
							<label for="xks" class="TextFont">学科分类:</label>
						</td>
						<td class="fr_left">
						    <select name="select3" id="select3" style="width:160px;" > 
								<option value="1">管理学门类</option> 
								<option value="2">应用学门类</option> 
								<option value="3">医学门类</option> 
							</select> 
						</td>
				  	</tr> 
				  	<tr>
						<td class="fr_right">
							<label for="xks" class="TextFont">授权类别:</label>
						</td>
						<td class="fr_left">
						    <select name="select4" id="select4"  style="width:160px;"> 
								<option value="1">博士授权</option> 
								<option value="2">硕士授权</option> 
							</select> 
						</td>
						<td class="fr_right">
							<label for="xks" class="TextFont">重点学科:</label>
						</td>
						<td class="fr_left">
						   <select name="select5" id="select5"  style="width:160px;"> 
								<option value="1">是</option> 
								<option value="2">否</option> 
							</select> 
						</td>
						<td class="fr_right">
							<label for="xks" class="TextFont">上轮参评:</label>
						</td>
						<td class="fr_left">
						    <select name="select5" id="select5"  style="width:160px;"> 
								<option value="1">是</option> 
								<option value="2">否</option> 
							</select>  
						</td>
				  	</tr> 
				  	<tr>
					<td class="fr_right">
						<label for="xks" class="TextFont">本轮参评:</label> 
					</td>
					<td class="fr_left">
					    <select name="select7" id="select7" style="width:160px;"> 
							<option value="1">全部</option> 
							<option value="1">是</option> 
							<option value="2">否</option> 
						</select>
					</td>
					<td class="fr_right">
						<label for="xks" class="TextFont">需要报告:</label>
					</td>
					<td class="fr_left">
					    <select name="select8" id="select8" style="width:160px;"> 
							<option value="1">全部</option> 
							<option value="1">是</option> 
							<option value="2">否</option> 
						</select> 
					</td>
					<td class="fr_right">
						<label for="xks" class="TextFont">是否有数据:</label>
					</td>
					<td class="fr_left">
                        <select name="select9" id="select9" style="width:160px;"> 
							<option value="1">全部</option> 
							<option value="1">是</option> 
							<option value="2">否</option> 
						</select> 
					</td>
				</tr>
				 <tr>
					<td class="fr_right">
						<input type="checkbox" name="checkbox1"> 
					    
					</td>
					<td class="fr_left">
					<label for="xks" class="TextFont">上次未生成</label>
					</td>
					<td class="fr_right">
						<input type="checkbox" name="checkbox1"> 
						</td>
						<td class="fr_left">
					    <label for="xks" class="TextFont">仅显示需“单位报告”高校</label>
					</td>
					<td class="fr_right">
						<input type="checkbox" name="checkbox1"> 
						</td>
						<td class="fr_left">
					    <label for="xks" class="TextFont">仅显示需“未参评报告”学科</label>
					</td>
				</tr> 
        	 	</table>
        <!-- 	</form> -->
        </div>
       <div style="text-align:center;">
		    <a id="search" class="button" href="#"><span class="icon icon-search"></span>筛选学校</a>
		</div>
	    <div  id="search_report" class="table" >
          	<table id="check_list_1"></table>
	      	<div id="pager1"></div>
     	</div>  
    </div>
    <div id="department_report" class="selectbar">
    
    </div>
     <div id="add_report" class="selectbar">
     
    </div> 
    <div id="no_attend_report" class="selectbar">
    
    </div>
    <div class="progressbar_div">
           <table class="fr_table">
                 <tr>
                     <td>
                         <label for="computingtime" class="TextFont">计算开始时间:</label>
                     </td>
                     <td>
                         <label class="TextFont">2013-12-18 16:55:02</label>
                     </td>
                 </tr>
                 <tr>
                     <td>
                         <label for="computingtime" class="TextFont">已用时间:</label>
                     </td>
                     <td>
                         <label class="TextFont">14:15</label>
                     </td>
                 </tr>
                 <tr>
                     <td>
                         <label for="computingtime" class="TextFont">预计完成时间:</label>
                     </td>
                     <td>
                         <label class="TextFont">2013-12-19 17:10:34</label>
                     </td>
                 </tr>
                 <tr>
                     <td>
                         <label for="progressname" class="TextFont">计算进度:</label>
                     </td>
                     <td>
                         <label for="calculateprogress" class="TextFont">20/100</label>
                     </td>
                 </tr>
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
 </div>
 

<script type="text/javascript">
$("#calculate").click(function(){
	   $("#calculate").hide();
	   $(".divProgressbar").progressbar({value: 0});
    updateProgressbarValue();
    function updateProgressbarValue(){
 	   var newValue = $(".divProgressbar").progressbar("option", "value") + 1;
 	   $(".progress").html(newValue+"%");
        $(".divProgressbar").progressbar("option", "value", newValue);
        if(newValue < 100) 
     	   setTimeout(updateProgressbarValue, 1000);
        if(newValue==100)
     	 {
     	   $(".progressbar_div").dialog("close");
     	   $("#calculate").show();
     	 }
    }
}); 

$("#viewresult").click(function(){
	$(".progressbar_div").show();
	    	   $('.progressbar_div').dialog({
		  		    title:"进度查看",
		  		    open:function(event,ui){
						$(".ui-dialog-titlebar-close", $(this).parent()).hide();
					},
		  		    height:'80%',
		  			width:'90%',
		  			position:'center',
		  			modal:true,
		  			draggable:false,
		  		    autoOpen:true,
		  		    buttons:{
		  		    	"关闭":function(){
		  		    		 $(".progressbar_div").dialog("close");
		  		    	}
		  		    }
	    	   });  
	       });
 $(function() {
	    $(".progressbar_div").hide();
	    $(".tabs").tabs();
	});

 $(document).ready(function(){
		$( "input[type=submit], a.button , button" ).button();
		$("#search_report").hide();
	});
 
   $("#search").click(function(){
    	   $("#search_report").show();
    });
   
/*    $("#report_download").click(function(){
	   var reUrl="${ContextPath}/reportManagement/toDownLoadReport";
	 	$.post(reUrl, function(data){
		  	$( "#content" ).empty();
		  	$( "#content" ).append( data );
	  	}, 'html');
		event.preventDefault();
	}); */
    
    
     $(function(){
    	 $("#check_list_1").jqGrid({
    		datatype: "local",
    		colNames:['学校','一级专业','学科分类','授权类别','重点学科','上轮参评','本轮参评','需要报告'],
    			colModel:[
    				{name:'collegeName',index:'collegeName',width:40,align:'center'},
    				{name:'yjzy',index:'yjzy',width:20,align:'center'},
    				{name:'xkfl',index:'xkfl',width:20,align:'center'},
    				{name:'sqlb',index:'sqlb',width:20,align:'center'},
    				{name:'zdxk',index:'zdxk',width:10,align:'center'},
    				{name:'slcp',index:'slcp',width:10,align:'center'},
    				{name:'blcp',index:'blcp',width:10,align:'center'},
    				{name:'xybg',index:'xybg',width:10,align:'center'}
    			],
    			height:"100%",
    			pager: '#pager1',
    			autowidth:true,
    			rowNum:10,
    			rowList:[10,20,30],
    			viewrecords: true,
    			sortorder: "desc",
    			multiselect: true,
    			multiboxonly: true,
    			caption: "学科刷选"
    		}).navGrid('#pager1',{refresh:false, search:false, edit:false,add:false,del:false});
    		var mydata = [
    				{collegeName:"中国人民大学",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"},
    				{collegeName:"清华大学",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"},
    				{collegeName:"北京大学",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"},
    				{collegeName:"北京航空航天大学",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"},
    				{collegeName:"华中师范大学",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"},
    				{collegeName:"华中农业大学",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"},
    				{collegeName:"湖北大学",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"},
    				{collegeName:"河北大学",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"},
    				{collegeName:"北京邮电大学",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"},
    				{collegeName:"北京科技大学",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"},
    				{collegeName:"武汉科技大学",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"},
    				{collegeName:"兰州大学",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"},
    				{collegeName:"北京理工大学",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"},
    				{collegeName:"北京林业大学",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"},
    				{collegeName:"北京外国语学院",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"},
    				{collegeName:"北京电影学院",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"},
    				{collegeName:"武汉大学",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"},
    				{collegeName:"华中科技大学",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"},
    				{collegeName:"郑州大学",yjzy:"工商管理",xkfl:"管理学门类",sqlb:"博士授权",zdxk:"是",slcp:"是",blcp:"是",xybg:"是"}
   
    				];
    		for(var i=0;i<=mydata.length;i++)
    			jQuery("#check_list_1").jqGrid('addRowData',i+1,mydata[i]);
    		$("#check_list_1").setGridWidth($("#content").width());
    		
       });

 </script>