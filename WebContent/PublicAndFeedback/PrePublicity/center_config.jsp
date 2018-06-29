<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="con_header inner_table_holder">
	<table class="layout_table left">
		<jsp:include page="../_ddl_disc_category.jsp"></jsp:include>
	</table>
	<table class="layout_table right">
		<tr>
			<td>
				<c:choose>
					<c:when test="${empty currentRound}">
						<a class="button" type="submit" href="#" id="btn_open_publicity"> <span
						class="icon icon-backup"></span>开启预公示
					</a>
					</c:when>
					<c:when test="${currentRound.status == 1}">
						<a class="button" href="#" id="btn_set_publicity"> <span
						class="icon icon-backup"></span>公示设置
						</a>
						&nbsp;
						<a class="button" href="#" id="btn_begin_publicity"> <span
						class="icon icon-backup"></span>立即公示
						</a>
				 	</c:when>
					<c:when test="${currentRound.status == 2}">
						<span class="TextFont">公示正在进行中</span>
					</c:when>
					<c:otherwise>
					 </c:otherwise>
				</c:choose> 
			</td>
		</tr> 
	</table>
</div>

<div id="dialog-confirm" title="警告">
</div>
<div class="layout_holder" id="dv_prePubTree">
	<c:if test="${!empty currentRound}">
		<jsp:include page="../TreeAndView/prepub_tree.jsp"></jsp:include>											
		<jsp:include page="../TreeAndView/prepub_view.jsp"></jsp:include> 
	</c:if>
</div>

<script src="${ContextPath}/js/common_validate.js"></script>
<script type="text/javascript">
	$("#ddl_disc_category").change(function(){
		var catId = $("#ddl_disc_category").val();
		loadTreeByCatId(catId);
	});
	
	
	//是否第一个日期大于第二个
	function isFirstOverSecond(firstDate,secondDate){
		if( firstDate > secondDate ){
			return true;
		}
		else{
			return false;
		}
	}
	
	//开启预公示
	$("#btn_open_publicity").click(function(){
		$("#dialog-confirm").empty().append("<p>确定开启预公示吗</p>");
		$("#dialog-confirm").dialog({
     	      height:150,
     	      buttons: {
     	        "确定":function(){
					$.ajax({
						type:'POST',
						url:'${ContextPath}/publicity/prepub_openPublicityRound',
						success:function(data){
							/* alert(data); */
							if( data == true ){
								alert_dialog("预公示已开启");
								$.post('${ContextPath}/publicity/prepub', function(data){
									  $( "#content" ).empty();
									  $( "#content" ).append( data );
									  documentReady();
								  }, 'html');
							}
							else{
								alert_dialog("预公示未开启,出现错误,请联系管理员");
							}
						}
					});
					$(this).dialog("close");
				},
				"取消":function(){
					$(this).dialog("close");
				}
   	      	}
		});
	});
	
	//开启公示
	$("#btn_set_publicity").click(function(){
		setPublicityDialog("${ContextPath}/publicity/prepub_beginPublicityDialog");
	});
	
	//立即开启公示批次
	$("#btn_begin_publicity").click(function(){
		$("#dialog-confirm").empty().append("<p>确定立即进行公示吗</p>");
		$("#dialog-confirm").dialog({
			 height:150,
    	     buttons: {
    	    	 "确定":function(){
    	    		 $.ajax({
    	    				url:"${ContextPath}/publicity/prepub_beginPublicity",
    	    				success: function(data){
    	    	  				if(data == "success"){
    	    	  		    		alert_dialog("开启成功");
    	    	  					$.post('${ContextPath}/publicity/prepub', function(data){
    	    							  $( "#content" ).empty();
    	    							  $( "#content" ).append( data );
    	    							  documentReady();
    	    						  }, 'html');
    	    	     			}
    	    	     			else if( data == "notset")
    	    	     			{
    	    	     				alert_dialog("请先进行公示设置");
    	    	     			}
    	    	     			else{
    	    	     				alert_dialog("出现错误");
    	    	     			}
    	    	  			}
    	    			});
    	    		 $(this).dialog("close");
    	    	 },
				"取消":function(){
					$(this).dialog("close");
				}
    	     }
		});
		
	});
	
	//公示设置的对话框
	function setPublicityDialog(url){
		$.post(url, function(data){
 		  	$("#dialog").empty();
 		  	$("#dialog").append( data );
 	 	  	$('#dialog').dialog({
	    		title:"公示设置",
 	  		    height:'230',
 	  			width:'750',
 	  			position:'center',
 	  			modal:true,
 	  			draggable:true,
 	  		    hide:'fade',
 	  			show:'fade',
 	  		    autoOpen:true,
 	  		    buttons:{  
  		    		"确定":function(){ 
  		    			if( !isDate10($("#input_endTime").val()) || !isDate10($("#input_beginTime").val())){ //日期是否合法
  		    				alert_dialog('请输入yyyy-mm-dd格式的合法日期');
  		    				return;
  		    			}
  		    			
  		    			var beginDateArray = $("#input_beginTime").val().split("-");
  		    			var endDateArray = $("#input_endTime").val().split("-");
  		    			var currentDate = new Date();
  		    			var beginSetDate = new Date(beginDateArray[0],beginDateArray[1]-1,beginDateArray[2]);
  		    			var endSetDate = new Date(endDateArray[0],endDateArray[1]-1,endDateArray[2]);
  		    			if(!isFirstOverSecond(beginSetDate,currentDate)){
  		    				alert_dialog('开始日期应大于当前日期');
  		    				return;
  		    			}
  		    			if(!isFirstOverSecond(endSetDate,currentDate)){
  		    				alert_dialog('结束日期应大于当前日期');
  		    				return;
  		    			}
  		    			if(!isFirstOverSecond(endSetDate,beginSetDate)){
  		    				alert_dialog('结束日期应大于开始日期');
  		    				return;
  		    			}
  		    			if($("#input_publicName").val() == ""){
  		    				alert_dialog('请输入公示名称');
  		    				return;
  		    			}
	    				var fmstr=$('#beginPublicity_fm').serialize();
	  	         		$.ajax({
 	  	      			type:'POST',
	 	  	      			url:'${ContextPath}/publicity/prepub_setPublicity',
	 	  	      			data:fmstr,
	  	      				success: function(data){
		  	      				if(data == true){
	  	      	   		    		alert_dialog("设置成功");
		  	      					$("#dialog").dialog("close");
	    	  	      			}
	    	  	      			else
	    	  	      			{
		    	  	      			$("#dialog").dialog("close");
	    	  	      				alert_dialog("设置失败");	
	    	  	      			}
	   	  	      			}
	  	      			});
	  	            },
	 	            "关闭":function(){
	 	            	$("#dialog").dialog("close");
 	            }
  		    }
 	 	  }); 
 	  }, 'html');
	}
	
	
	
	function documentReady(){
		$("input[type=submit], a.button , button" ).button();
		var round = "${currentRound}";
		if( round+"" == ""){
			$("#dv_prePubTree").hide();
		}
		else{
			$("#dv_prePubTree").show();
			var catId = $("#ddl_disc_category").val();
			initRoundStatus("${currentRound.status}");//位于prepub_view页面，初始化轮次状态 
			loadTreeByCatId(catId); 
			$("#collect_batch_add").hide();
			$("#collect_sort").hide();
			$("#excel_import").hide();
			$("#excel_output").hide();
		}
	}
	
	$(document).ready(function() {
		documentReady();
		
		/* jQuery("#input_endTime").datepicker({
			   yearRange:'1900:2020',
			   changeMonth: true,
	        changeYear: true,
	        showButtonPanel: true,
			   dateFormat: 'yy-mm-dd',  //日期格式，自己设置              buttonImage: 'calendar.gif',  //按钮的图片路径，自己设置              buttonImageOnly: true,  //Show an image trigger without any button.             showOn: 'both',//触发条件，both表示点击文本域和图片按钮都生效         yearRange: '1990:2008',//年份范围          clearText:'清除',//下面的就不用详细写注释了吧，呵呵，都是些文本设置         closeText:'关闭',         prevText:'前一月',         nextText:'后一月',         currentText:' ',          monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],  
		   }); */
	});
</script>