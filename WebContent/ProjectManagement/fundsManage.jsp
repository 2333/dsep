<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="funding_div">
   	<div class="selectbar inner_table_holder">
   		<table class="layout_table left">
   			<tr>
				<td>
					<select id="funds_select">
					    <option value="funds_provide">下批经费</option>
						<option value="funds_using">使用经费</option>
					</select>
				</td>
			</tr>
   		</table>
   	</div>  	
    <div id="operateDiv" class="selectbar inner_table_holder">
		<div class="con_header">
			<h3>
				<span  class="icon icon-web"></span>
				<span id="funds_span">下批经费</span>
			</h3>
		</div>
    	<table id="useTable" class="layout_table left" hidden="true">
    		<tr>
				<td >
					<span class="TextFont">发票号码：</span>
				</td>
				<td >
					<input id="invoiceNumber" type='text' value=""/>
				</td>
    			<td>
					<span class="TextFont">日期：</span>
				</td>
				<td >
					<input id="u-startDate" name="startDate" type="text" value="" />—<input id="u-endDate" name="endDate" type="text" value="" />
				</td>
				<td><a  id="u-search_btn" class="button" href="#"><span class="icon icon-search"></span>查询</a>
				</td>
				<td>&nbsp;&nbsp;</td>
				<td hidden="true"><a id="useStatistics" class="button" href="#"><span class="icon icon-web"></span>统计</a>
				</td>
			</tr>
    	</table>
    	
    	<table id="provideTable" class="layout_table left">
    		<tr>
    			<td>
					<span class="TextFont">日期：</span>
				</td>
				<td >
					<input id="p-startDate" name="startDate" type="text" value="" />—<input id="p-endDate" name="endDate" type="text" value="" />
				</td>
				<td><a  id="p-search_btn" class="button" href="#"><span class="icon icon-search"></span>查询</a>
				</td>
			</tr>
    	</table>
    	<table class="layout_table right">
    		<tr>
				<td><a id="AddFunds_btn" class="button" href="#"><span class="icon icon-add"></span>添加</a>
				</td>
				<td hidden="true"><input id="itemId" name="itemId" type="text" value="${itemId}"/>
				</td>
			</tr>
    	</table>
	</div>
	<div id="funds_provide_div" >
	    <table id="funds_provide_tb"></table>
		<div id="funds_provide_pager"></div>
	</div>
	<div id="funds_using_div" hidden="true">
	    <table id="funds_use_tb"></table>
		<div id="funds_use_pager"></div>
	</div>
</div>
<div id="del_confirm"></div>
<script>
	var useurl = "";
	var prourl = "";
	var itemId = $("#itemId").val();
	var selected_val="";
	$(document).ready(function(){
		useurl = "pproject_itemFundsList";
		prourl = "pproject_itemProvideFundsList";
		var user_type=$("#user_type").val();
		if(user_type == 2){
			$("#AddFunds_btn").hide();
			//useurl = "punit_itemFundsList";
			//prourl = "punit_itemProvideFundsList";
		}
		console.log("itemId="+itemId);
		$("#funds_select").change(function(){
			selected_val = $("#funds_select option:selected").val(); 
			var span=document.getElementById("funds_span");
			if(selected_val == "funds_using"){
				span.innerHTML="使用经费";
				$("#funds_using_div").show();
				$("#funds_provide_div").hide();
				$("#useTable").show();
				$("#provideTable").hide();
				 //经费使用 
			    $('#funds_use_tb').jqGrid({
					url:'${ContextPath}/project/'+useurl+'?itemId='+itemId, 
			     	datatype: 'json',
			     	mtype:'GET',
			     	colNames:['经费id','使用时间','使用目的','经办人','审批人','使用金额','发票号码','查看详情',''],
					colModel:[
								{name:'id',index:'id',width:30,align:"center",hidden:true},
								{name:'usingTime',index:'usingTime',width:60,align:'center',formatter:"date",formatoptions: {srcformat:'u',newformat:'Y-m-d'}},
						        {name:'usingAim',index:'usingAim',width:40,align:'center'},
					            {name:'operator',index:'operator',width:40,align:'center'},
					            {name:'checkPeople',index:'checkPeople',width:40,align:'center'},
					            {name:'consumption',index:'consumption',width:40,align:'center'},
					            {name:'invoiceNumber',index:'invoiceNumber',width:60,align:'center'},
					            {name:'detail',index:'detail',width:60,align:'center'},
					            {name:'modify',index:'modify',width:60,align:'center'},
							],
					height:'100%',
					width:$("#content").width(),
					autowidth:false,
					shrinkToFit:true,
					pager: '#funds_use_pager',
					rowNum:20,
					rownumbers:true,
					rowList:[20,30,40],
					viewrecords: true,
					sortname: 'usingTime',
					sortorder: 'asc',
					gridComplete: function(){
				    	var ids = jQuery("#funds_use_tb").jqGrid('getDataIDs');
						for(var i=0;i < ids.length;i++){
							var singleDetail="<a href='#' onclick=showDetails('"+ids[i]+"')>使用详情</a>";
							var modifyInfo ="<a href='#' onclick=deleteInfo('"+ids[i]+"')>删除</a>";
							jQuery("#funds_use_tb").jqGrid('setRowData',ids[i],{detail :singleDetail});
							jQuery("#funds_use_tb").jqGrid('setRowData',ids[i],{modify :modifyInfo});
						}
				    },
					caption: "经费使用表",
					} ).navGrid('#funds_use_tb',{edit:false,add:false,del:false,search:true});
			}
			else if(selected_val == "funds_provide"){
				//span.innerHTML="下批经费";
				$("#funds_provide_div").show();
				$('#funds_using_div').hide();
				$("#useTable").hide();
				$("#provideTable").show();
			}
		});
		
		
		$('#funds_provide_tb').jqGrid({
			url:'${ContextPath}/project/'+prourl+'?itemId='+itemId,
	     	datatype: 'json',
	     	mtype:'GET',
	     	colNames:['经费id','下批时间','下批金额（万元）'],
			colModel:[
						{name:'id',index:'id',width:30,align:"center",hidden:true},
						{name:'provideTime',index:'provideTime',width:60,align:'center',formatter:"date",formatoptions: {srcformat:'u',newformat:'Y-m-d'}},
				        {name:'provideAmount',index:'provideAmount',width:40,align:'center'},
					],
			height:'100%',
			width:$("#content").width(),
			autowidth:false,
			shrinkToFit:true,
			pager: '#funds_provide_pager',
			rowNum:20,
			rownumbers:true,
			rowList:[20,30,40],
			viewrecords: true,
			sortname: 'provideTime',
			sortorder: 'asc',
			gridComplete: function(){
		    	var ids = jQuery("#funds_provide_tb").jqGrid('getDataIDs');
				for(var i=0;i < ids.length;i++){
					var modifyInfo ="<a href='#' onclick=deleteInfo('"+ids[i]+"')>删除</a>";
					jQuery("#funds_provide_tb").jqGrid('setRowData',ids[i],{modify :modifyInfo});
				}
		    },
			caption: "经费下批表",
			}).navGrid('#funds_provide_tb',{edit:false,add:false,del:false,search:true});
	});
	$.datepicker.regional['zh-CN'] = {  
		      clearText: '清除',  
		      clearStatus: '清除已选日期',  
		      closeText: '关闭',  
		      closeStatus: '不改变当前选择',  
		      prevText: '<上月',  
		      prevStatus: '显示上月',  
		      prevBigText: '<<',  
		      prevBigStatus: '显示上一年',  
		      nextText: '下月>',  
		      nextStatus: '显示下月',  
		      nextBigText: '>>',  
		      nextBigStatus: '显示下一年',  
		      currentText: '今天',  
		      currentStatus: '显示本月',  
		      monthNames: ['一月','二月','三月','四月','五月','六月', '七月','八月','九月','十月','十一月','十二月'],  
		      monthNamesShort: ['一','二','三','四','五','六', '七','八','九','十','十一','十二'],  
		      monthStatus: '选择月份',  
		      yearStatus: '选择年份',  
		      weekHeader: '周',  
		      weekStatus: '年内周次',  
		      dayNames: ['星期日','星期一','星期二','星期三','星期四','星期五','星期六'],  
		      dayNamesShort: ['周日','周一','周二','周三','周四','周五','周六'],  
		      dayNamesMin: ['日','一','二','三','四','五','六'],  
		      dayStatus: '设置 DD 为一周起始',  
		      dateStatus: '选择 m月 d日, DD',  
		      dateFormat: 'yy-mm-dd-hh-mm',  
		      firstDay: 1,  
		      initStatus: '请选择日期',  
		      isRTL: false  
		    };
	 $.datepicker.setDefaults($.datepicker.regional['zh-CN']);
	
	 jQuery("#u-startDate").datepicker({
		   yearRange:'1900:2020',
		   changeMonth: true,
	     changeYear: true,
	     showButtonPanel: true,
		   dateFormat: 'yy-mm-dd',  //日期格式，自己设置              buttonImage: 'calendar.gif',  //按钮的图片路径，自己设置              buttonImageOnly: true,  //Show an image trigger without any button.             showOn: 'both',//触发条件，both表示点击文本域和图片按钮都生效         yearRange: '1990:2008',//年份范围          clearText:'清除',//下面的就不用详细写注释了吧，呵呵，都是些文本设置         closeText:'关闭',         prevText:'前一月',         nextText:'后一月',         currentText:' ',          monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],  
	 });
	jQuery("#u-startDate").click(function(){
		$("#ui-datepicker-div").css({"z-index":"2000"});
	});
	jQuery("#u-endDate").datepicker({
		   yearRange:'1900:2020',
		   changeMonth: true,
	  changeYear: true,
	  showButtonPanel: true,
		   dateFormat: 'yy-mm-dd',  //日期格式，自己设置              buttonImage: 'calendar.gif',  //按钮的图片路径，自己设置              buttonImageOnly: true,  //Show an image trigger without any button.             showOn: 'both',//触发条件，both表示点击文本域和图片按钮都生效         yearRange: '1990:2008',//年份范围          clearText:'清除',//下面的就不用详细写注释了吧，呵呵，都是些文本设置         closeText:'关闭',         prevText:'前一月',         nextText:'后一月',         currentText:' ',          monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],  
	});
	jQuery("#u-endDate").click(function(){
		$("#ui-datepicker-div").css({"z-index":"2000"});
	});
	 jQuery("#p-startDate").datepicker({
		   yearRange:'1900:2020',
		   changeMonth: true,
	     changeYear: true,
	     showButtonPanel: true,
		   dateFormat: 'yy-mm-dd',  //日期格式，自己设置              buttonImage: 'calendar.gif',  //按钮的图片路径，自己设置              buttonImageOnly: true,  //Show an image trigger without any button.             showOn: 'both',//触发条件，both表示点击文本域和图片按钮都生效         yearRange: '1990:2008',//年份范围          clearText:'清除',//下面的就不用详细写注释了吧，呵呵，都是些文本设置         closeText:'关闭',         prevText:'前一月',         nextText:'后一月',         currentText:' ',          monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],  
	 });
	jQuery("#p-startDate").click(function(){
		$("#ui-datepicker-div").css({"z-index":"2000"});
	});
	jQuery("#p-endDate").datepicker({
		   yearRange:'1900:2020',
		   changeMonth: true,
	  changeYear: true,
	  showButtonPanel: true,
		   dateFormat: 'yy-mm-dd',  //日期格式，自己设置              buttonImage: 'calendar.gif',  //按钮的图片路径，自己设置              buttonImageOnly: true,  //Show an image trigger without any button.             showOn: 'both',//触发条件，both表示点击文本域和图片按钮都生效         yearRange: '1990:2008',//年份范围          clearText:'清除',//下面的就不用详细写注释了吧，呵呵，都是些文本设置         closeText:'关闭',         prevText:'前一月',         nextText:'后一月',         currentText:' ',          monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],  
	});
	jQuery("#p-endDate").click(function(){
		$("#ui-datepicker-div").css({"z-index":"2000"});
	});
	$('#u-search_btn').click(function(){
		jQuery("#funds_use_tb").setGridParam({
			url:"${ContextPath}/project/pproject_serachFunds?"
			 	+"&startDate="+$("#u-startDate").val()
			 	+"&endDate="+$("#u-endDate").val()
			 	+"&invoiceNumber="+$("#invoiceNumber").val()
			 	+"&item_id="+itemId,
			sortorder: "desc",
			page:1,
		}).trigger("reloadGrid");

		event.preventDefault();
	});
	
	$('#p-search_btn').click(function(){
		jQuery("#funds_provide_tb").setGridParam({
			url:"${ContextPath}/project/pproject_serachProvideFunds?"
			 	+"&startDate="+$("#p-startDate").val()
			 	+"&endDate="+$("#p-endDate").val()
			 	+"&item_id="+itemId,
			sortorder: "desc",
			page:1,
		}).trigger("reloadGrid");
		event.preventDefault();
	})
	$("#AddFunds_btn").click(function(){
		if(selected_val=="funds_using"){
			addUsingFundsDialog("${ContextPath}/project/pproject_addUsingFunds?item_id="+itemId);
		}
		else if(selected_val="funds_provide"){
			addProvideFundsDialog("${ContextPath}/project/pproject_addProvideFunds?item_id="+itemId)
		}
	});
	function addUsingFundsDialog(url){
		$.post(url,function(data){
			$("#dialog").empty();
			$("#dialog").append(data);
			$("#dialog").dialog({
				title:"经费使用情况",
	  		    height:'774',
	  			width:'730',
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  		    resizable:false,
	  			show:'fade',
	  		    autoOpen:true,
	  		    buttons:{
	  		    	"确定":function(){
	  		    		var fundsInfo = $("#fm_addFundsUsing").serialize();
  		    			$.ajax({
    	  	      			type:'POST',
    	  	      			url:'${ContextPath}/project/pproject_saveUsingFunds',
    	  	      			data:fundsInfo,
    	  	      			success: function(data){
	    	  	      			if(data == true){
	    	  	      				$("#funds_use_tb").setGridParam({url:'${ContextPath}/project/pproject_itemFundsList?itemId='+itemId}).trigger("reloadGrid");
	    	  	      		
   	  	      	   					$("#dialog").dialog("close");
   	  	      	   					alert_dialog("保存成功");
   	  	      	   				}
   	  	      	   				else{
		   	  	      	   			$("#dialog").dialog("close");
			  	      				alert_dialog("数据不完整或操作失败！");	
   	  	      	   				}
    	  	      			}
	  		    		});
	  		    	},
	  		    	"取消":function(){
	  		    		$("#dialog").dialog("close");
	  		    	}
	  		    }});
		},'html');
	}
	
	function addProvideFundsDialog(url){
		$.post(url,function(data){
			$("#dialog").empty();
			$("#dialog").append(data);
			$("#dialog").dialog({
				title:"下批经费",
	  		    height:'180',
	  			width:'680',
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  		    resizable:false,
	  			show:'fade',
	  		    autoOpen:true,
	  		    buttons:{
	  		    	"确定":function(){
	  		    		var provideInfo = $("#fm_addFundsProvide").serialize();
	  		    		console.log(provideInfo);
  		    			$.ajax({
    	  	      			type:'POST',
    	  	      			url:'${ContextPath}/project/pproject_providedFundsSave',
    	  	      			data:provideInfo,
    	  	      			success: function(data){
	    	  	      			if(data == true){
	    	  	      				$("#funds_provide_tb").setGridParam({url:'${ContextPath}/project/pproject_itemProvideFundsList?itemId='+itemId}).trigger("reloadGrid");		
   	  	      	   					$("#dialog").dialog("close");
   	  	      	   					alert_dialog("保存成功");
   	  	      	   				}
   	  	      	   				else{
		   	  	      	   			$("#dialog").dialog("close");
			  	      				alert_dialog("数据不完整或操作失败！");	
   	  	      	   				}
    	  	      			}
	  		    		});
	  		    	},
	  		    	"取消":function(){
	  		    		$("#dialog").dialog("close");
	  		    	}
	  		    }});
		},'html');
	}
	function deleteInfo(id){
		$('#del_confirm').empty().append("<p>你确定要删除此条数据吗？</p>");
		$('#del_confirm').dialog({
	  			position:'center',
	  			modal:true,
	  			draggable:true,
	  		    hide:'fade',
	  			show:'fade',
	  		    autoOpen:true,
	  		    buttons:{  
					"确定":function(){
						var rowData = $('#funds_use_tb').jqGrid("getRowData", id);
				 		var funds_id = rowData['id'];
				 		$.post("${ContextPath}/project/pproject_deleteFunds",{fundsId:funds_id},function(data){
				 			if(data){
				 				//alert(data);
				 				$("#funds_use_tb").trigger("reloadGrid");
				 			}
				 			else{
				 				alert("删除失败!");
				 			}
				 		},'json');
						$("#del_confirm").dialog("close");
					},
	 	           "取消":function(){
	 	            	$("#del_confirm").dialog("close");
	 	            }
	  		    }
		});
	}
	
	function showDetails(id){
		 console.log(id);
		 $.post('${ContextPath}/project/pproject_fundsDetail',{fundsId:id}, function(data){
		 		$('#dialog').empty();
		 		$('#dialog').append( data );
			  	$('#dialog').dialog({
			  		title:'使用经费',
		 	  		height:'750',
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
		 	 	          }
		 	  		 }
			  }); 
		  }, 'html');
		  event.preventDefault();
	}
</script>