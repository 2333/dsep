<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>问卷详情
	</h3>
</div>
 
<div id="detail_information" class="selectbar inner_table_holder">
	<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>问卷基本信息
	</h3>
	</div>
	<div class="selectbar inner_table_holder" >
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">问卷标题：</span>
			</td>
			<td>
				<input type="text" id="questionaire_title" value='关于在校生满意度的调查' />
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<span class="TextFont">问卷类型：</span>
			</td>
			<td>
				<select id="questionaire_type">
				<option value="society">社会民意</option>
				<option value="satisfaction">满意度调查</option>
				<option value="discipline">学科水平</option>
				<option value="student">学生质量</option>
				</select>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			 <td>
                 <span class="TextFont">问卷内容：</span>
             </td>
             <td>
                 <label id="questionaire_content">亲爱的本科生们：本……</label>
             </td>
		</tr>
	</table>
	<table class="layout_table right">
		<tr>
 			 <td>
				<a  id="edit_btn" class="button" href="#"><span class="icon icon-edit"></span>进入修改</a>
			 </td>		
		</tr>
    </table>
</div>
</div>

<div id="publish_settings" class="selectbar inner_table_holder">
	<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>发布信息设置
	</h3>
	
	</div>
	<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">开始时间：</span>
			</td>
			<td>
				<input type="text" id="start_date"/>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<span class="TextFont">结束时间：</span>
			</td>
			<td>
				<input type="text" id="end_date" />
			</td>
		</tr>
	</table>
	<table class="layout_table right" style="margin-right:60px">
		<tr>
			<td>
				<span class="TextFont">问卷状态：</span>
			</td>
			<td>
				<label>未开启</label>
			</td>
		</tr>
	</table>
	</div>
	<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">对象类型：</span>
			</td>
			<td>
				<select id="respondent_type" style="width:153px">
				<option value="internal_student">在校生</option>
				<option value="graduate_student">毕业生</option>
				<option value="professor">专家</option>
				<option value="employer_unit">用人单位</option>
				</select>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<a  id="batch_add_btn" class="button" href="#"><span class="icon icon-add"></span>批量选择</a>
			</td>
			<td>&nbsp;</td>
			<td>
				<a  id="query_btn" class="button" href="#"><span class="icon icon-search"></span>条件查询</a>
			</td>
		</tr>
	</table>
	<table class="layout_table right">
		<tr>
			<td>
				<a  id="open_btn" class="button" href="#"><span class="icon icon-open"></span>发   布</a>
			</td>
			<td>
				<a  id="close_btn" class="button" href="#"><span class="icon icon-close"></span>关   闭</a>
			</td>
		</tr>
	</table>
	</div>
	<div >
		<table id="respondent_list"></table>
		<div id="respondent_pager"></div>
	</div>

</div>

<div id="result_statistics" class="selectbar inner_table_holder">
	<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>问卷结果统计
	</h3>
	</div>
	<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">当前已收集答卷：</span>
			</td>
			<td>
				<label>42</label>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<span class="TextFont">最后更新时间时间：</span>
			</td>
			<td>
				<label>2014-08-26 10：02:24</label>
			</td>
		</tr>
	</table>
	<table class="layout_table right">
		<tr>
			<td>
				<a  id="view_btn" class="button" href="#"><span class="icon icon-search"></span>进入查看</a>
			</td>
		</tr>
	</table>
	</div>
</div>
 
<script type="text/javascript">
	$(document).ready(function(){
		 $( 'input[type=submit], a.button , button' ).button();
		 
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
		   jQuery("#start_date").datepicker({
			   yearRange:'1900:2020',
			   changeMonth: true,
	          changeYear: true,
	          showButtonPanel: true,
			   dateFormat: 'yy-mm-dd',  //日期格式，自己设置              buttonImage: 'calendar.gif',  //按钮的图片路径，自己设置              buttonImageOnly: true,  //Show an image trigger without any button.             showOn: 'both',//触发条件，both表示点击文本域和图片按钮都生效         yearRange: '1990:2008',//年份范围          clearText:'清除',//下面的就不用详细写注释了吧，呵呵，都是些文本设置         closeText:'关闭',         prevText:'前一月',         nextText:'后一月',         currentText:' ',          monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],  
		   });
		   jQuery("#end_date").datepicker({
			   yearRange:'1900:2020',
			   changeMonth: true,
	          changeYear: true,
	          showButtonPanel: true,
			   dateFormat: 'yy-mm-dd',  //日期格式，自己设置              buttonImage: 'calendar.gif',  //按钮的图片路径，自己设置              buttonImageOnly: true,  //Show an image trigger without any button.             showOn: 'both',//触发条件，both表示点击文本域和图片按钮都生效         yearRange: '1990:2008',//年份范围          clearText:'清除',//下面的就不用详细写注释了吧，呵呵，都是些文本设置         closeText:'关闭',         prevText:'前一月',         nextText:'后一月',         currentText:' ',          monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],  
		   });
		   
		   jQuery("#respondent_list").jqGrid({
   			datatype: "local",
   		   	colNames:['序号', '姓名', '性别','专业','邮箱'],
   		   	colModel:[
   		   		{name:'id',index:'id', width:120,align:"center"},
   		   		{name:'name',index:'name', width:220,align:"center"},
   		   		{name:'sex',index:'sex', width:220, align:"center"},
   		   		{name:'major',index:'major', width:340, align:"center"},		
   		   		{name:'email',index:'email', width:340, align:"center"},				
   		   	],
   		   	rowNum:10,
   		   	height:"100%",
   		   	rowList:[10,20,30],
   		   	pager: '#respondent_pager',
   		   	sortname: 'name',
   		    viewrecords: true,
   		    sortorder: "desc",
   		    caption: "调查对象汇总"
   		});
   		jQuery("#respondent_list").jqGrid('navGrid','#respondent_pager',{edit:false,add:false,del:false,search:true});
   		var mydata= [
   		             {id:"1",name:"aaa",sex:"男",major:"计算机科学技术",email:"545454545@qq.com"},
   		             {id:"2",name:"bbb",sex:"男",major:"软件工程",email:"45654545@qq.com"},
   		             {id:"3",name:"ddd",sex:"男",major:"新媒体设计",email:"543154545@qq.com"},
   		             {id:"4",name:"ccc",sex:"男",major:"中文",email:"545454545@qq.com"},
   		             {id:"5",name:"eee",sex:"男",major:"数学",email:"54424545@qq.com"},
   		             {id:"6",name:"ff",sex:"男",major:"外国语",email:"5248954545@qq.com"},
   		             {id:"7",name:"tt",sex:"女",major:"软件工程",email:"545454545@qq.com"},
   		             {id:"8",name:"kk",sex:"男",major:"计算机科学技术",email:"545454545@qq.com"},
   		             {id:"9",name:"zz",sex:"男",major:"机械工程",email:"59944545@qq.com"},
   		             {id:"10",name:"ll",sex:"男",major:"自动化",email:"4644574545@qq.com"},
   		             ];
   		for( var i=0;i<mydata.length;i++ )
   		{
   			jQuery("#respondent_list").addRowData(i+1,mydata[i]);	
   		}
});
	
</script>
