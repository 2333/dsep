<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header">
	<h3>
		<span class="icon icon-download"></span>学科报告下载
	</h3>
</div>

 <div class="tabs">
	<ul>
        <li><a href="#dsep_report" >学科报告下载</a></li>
        <li><a href="#department_report" >单位报告下载</a></li>
    </ul>
    <div id="dsep_report" class="selectbar">
    	<div class="con_header">
        	<table style="float:left">
				<tr>
					<td>
						<a  id="calculate" class="button" href="#">
			    		<span class="icon icon-download"></span>报告下载</a>
			    		<a  id="itemDel" class="button" href="#">
			    		<span class="icon icon-download"></span>打包下载</a>
					</td>
				</tr>
			</table>
        </div>
			<div  id="reports" class="table" >
          		<table id="check_list_1"></table>
	      		<div id="pager1"></div>
     		</div>  

    </div>
    

    
    <div id="department_report" class="selectbar">
    
    </div>
 </div>
 

<script type="text/javascript">
 
 $(function() {
	    $(".tabs").tabs();
	});
 
 $(document).ready(function(){
		$( "input[type=submit], a.button , button" ).button();
	});
 

     $(function(){
    	 $("#check_list_1").jqGrid({
    		datatype: "local",
    		colNames:['学校','一级专业','学科分类','授权类别','重点学科','上轮参评','本轮参评','需要报告'],
    			colModel:[
    				{name:'collegeName',index:'collegeName',width:30,align:'center'},
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
    			caption: "学科报告下载"
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