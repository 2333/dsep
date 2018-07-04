<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>进行反馈</title>
<style type="text/css">
	#td
	{
		text-align:center;
	}
</style>
</head>
<body>
	<div id="beginFeedback_dv" class="form" >
     <form id="beginFeedback_fm" class="fr_form" method="post">  
			<table class="fr_table" style="border-collapse:collapse;">  
	             <tr>  
	                 <td>
	                  	<label>开始时间：</label>
	                 </td>
	                 <td>
	                      <input id="input_beginTime" type="text" name="beginTime" value="${currentRound.beginTime}"
	                      	onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');"/>
	                 </td>  
	                 <td>
	                  	<label>结束时间：</label>
	                 </td>
	                 <td>
	                      <input id="input_endTime" type="text" name="endTime" value="${currentRound.endTime}"
	                      	onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');"/>
	                 </td>   
	             </tr>
	             <tr>
	             	<td>
	                 	<label>反馈名称：</label> 
	                 </td>
	                 <td> 
	                     <input id="input_feedbackName" type="text" name="feedbackName" value="${currentRound.feedBackRoundName}"/>
	                 </td>
		           	<td id="td">
		           		<label>备注：</label>
	           		</td>
	           		<td>
		           		<input type="text" name="remark" value="${currentRound.remark}"/>
		           	</td>
	             </tr>  
       		</table>  
    	</form> 	
    </div>
</body>

<script type="text/javascript">	

	$(document).ready(function(){
		$(".fr_table td:nth-child(even)").addClass("fr_left");
	    $(".fr_table td:nth-child(odd)").addClass("fr_right");
	    $( "input[type=submit], a.button , button" ).button();
	    
	    var focusCount = 0;
	    
	    $("#input_beginTime").focus(function(){
	    	jQuery("#input_beginTime").datepicker({
				yearRange:'1900:2020',
			   	changeMonth: true,
		      	changeYear: true,
		      	showButtonPanel: true,
			   	dateFormat: 'yy-mm-dd',  //日期格式，自己设置
			   	/*monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']*/
	    });
	    
	    if(focusCount==0){
	    	$("#input_feedbackName").focus();
	    	focusCount++;
	    }
	});
	    
	$("#input_endTime").datepicker({
		   yearRange:'1900:2020',
		   changeMonth: true,
           changeYear: true,
           showButtonPanel: true,
		   dateFormat: 'yy-mm-dd',  //日期格式，自己设置              buttonImage: 'calendar.gif',  //按钮的图片路径，自己设置              buttonImageOnly: true,  //Show an image trigger without any button.             showOn: 'both',//触发条件，both表示点击文本域和图片按钮都生效         yearRange: '1990:2008',//年份范围          clearText:'清除',//下面的就不用详细写注释了吧，呵呵，都是些文本设置         closeText:'关闭',         prevText:'前一月',         nextText:'后一月',         currentText:' ',          monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],  
	   });
	
	
}); 			

</script>

</html>