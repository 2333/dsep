<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="inner_table_holder">
	<form id="fm_addFundsUsing" class="fr_form" method="post">  
		<table class="layout_table left"> 
			<tr>
				<td>
				    <span class="TextFont">使用目的：</span>
				</td>
				<td colspan="4">
					<input id="usingAim" name="usingAim" type="text" value="" size="85%"/>
				</td>
			</tr>
			<tr>
				<td>
					<span class="TextFont">使用金额：</span>
				</td>
				<td width="305px">
					<input id="amount" name="amount" type="text" value="" size='5'/>（万元）
				</td>
 			    <td>
					<span class="TextFont">使用时间：</span>
				</td>
				<td>
					<input id="using_Time" name="using_Time" type="text"/>
				</td> 
			</tr>
			<tr>
				<td>
					<span class="TextFont">经办人：</span>
				</td>
				<td>
					<input id="operator" name="operator" type="text" value="" />
				</td>
				
			    <td>
					<span class="TextFont">审批人：</span>
				</td>
				<td>
					<input id="checkPeople" name="checkPeople" type="text"/>
				</td>
			</tr>
			
			<tr>				
			    <td>
					<span class="TextFont">发票号码：</span>
				</td>
				<td>
					<input id="invoiceNumber" name="invoiceNumber" type="text" value=""/>
				</td>
				<td class="hidden">
					<input id="itemId" name="itemId" type="text" value="${item_id}"/>
				</td>
			</tr>
			<tr>
				<td>
				    <span class="TextFont">使用详情：</span>
				</td>
				<td colspan="4">
					<textarea name="detail" id="mContent"></textarea> 
				</td>
			</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	var editor = new baidu.editor.ui.Editor({initialFrameHeight:350,initialFrameWidth:540,autoClearinitialContent :true, });
	editor.render("mContent");
	
	$(document).ready(function(){
		$( "input[type=submit], a.button , button" )
		  .button()
		  .click(function( event ) {
			event.preventDefault();
		});
		$(".layout_table td:nth-child(even)").addClass("fr_left");
		$(".layout_table td:nth-child(odd)").addClass("fr_right");
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

	   jQuery("#using_Time").datepicker({
		   yearRange:'1900:2020',
		   changeMonth: true,
           changeYear: true,
           showButtonPanel: true,
		   dateFormat: 'yy-mm-dd',  //日期格式，自己设置              buttonImage: 'calendar.gif',  //按钮的图片路径，自己设置              buttonImageOnly: true,  //Show an image trigger without any button.             showOn: 'both',//触发条件，both表示点击文本域和图片按钮都生效         yearRange: '1990:2008',//年份范围          clearText:'清除',//下面的就不用详细写注释了吧，呵呵，都是些文本设置         closeText:'关闭',         prevText:'前一月',         nextText:'后一月',         currentText:' ',          monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],  
	   });
	 jQuery("#using_Time").click(function(){
		$("#ui-datepicker-div").css({"z-index":"2000"});
	 });
</script>