<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>公示设置</title>
</head>
<body>
	<div id="beginPublicity_dv" class="form" >
     <form id="beginPublicity_fm" class="fr_form" method="post">  
			<table class="fr_table" style="border-collapse:collapse;">  
	             <tr>  
	                 <td>
	                  	<label>开始时间：</label>
	                 </td>
                 	 <td>
                 	 	<div>
                 	 		<input id="input_beginTime" type="text" name="beginTime" value="${currentRound.beginTime}"
	                     	   onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');"/>
                 	 	</div>
	                      	<!-- value="${currentRound.beginTime}"  -->
	                 </td>   
	                 <td>
	                  	<label>结束时间：</label>
	                 </td>
	                 <td>
	                 	<div>
	                 		<input id="input_endTime" type="text" name="endTime"  value="${currentRound.endTime}"
	                      		onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');"/>
	                 	</div>
	                      	<!-- value="${currentRound.endTime}" onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');"/> -->
	                 </td>
	             </tr>
	             <tr>
	             	 <td>
	                 	 <label>公示名称：</label> 
	                 </td>
	                 <td colspan="7"> 
	                     <input style="width:505px" id="input_publicName" type="text" name="publicityName" value="${currentRound.publicRoundName}"/>
	                 </td>
	             </tr>
	             <tr>
		           	<td>
		           		<label>备注：</label>
	           		</td>
	           		<td colspan="7">
		           		<input style="width:505px" type="text" name="remark" value="${currentRound.remark}"/>
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
	    	$("#input_beginTime").datepicker({
			   	yearRange:'1900:2020',
			   	changeMonth: true,
		      	changeYear: true,
			   	dateFormat: 'yy-mm-dd',  //日期格式，自己设置
			   	/* monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'] */ 
			});
	    	if( focusCount == 0){
	    		$("#input_publicName").focus();
	    		focusCount++;
	    	}
	    }); 
		
	    
		$("#input_endTime").datepicker({
		   	yearRange:'1900:2020',
		   	changeMonth: true,
	      	changeYear: true,
		   	dateFormat: 'yy-mm-dd',  //日期格式，自己设置             
		   	/* monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']  */
		});
		

	});
	
	 
	 
 			
</script>

</html>

