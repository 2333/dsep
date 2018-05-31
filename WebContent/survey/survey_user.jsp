<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>调查对象管理
	</h3>
</div>
<div id="dialog-confirm" title="警告"></div>
<div class="selectbar inner_table_holder" >
	<table class="layout_table left">
		<tr>
			<td>
				<span class="TextFont">学校编号：</span>
			</td>
			<td>
				<input type="text" id="unit_Id" value='${unitId}' disabled/>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<span class="TextFont">学科编号：</span>
			</td>
			<td>
				<input type="text" id="disc_Id" value='${discId} ' disabled/>
			</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
				<span class="TextFont">对象类型：</span>
			</td>
			<td>
				<select id="respondent_type">
				<option value="internal_student">在校生</option>
				<option value="graduate_student">毕业生</option>
				<option value="professor">专家</option>
				<option value="employer_unit">用人单位</option>
				</select>
			</td>
		</tr>
	</table>
	<table class="layout_table right">
		<tr>
 			<td>
				<a id="query_btn" class="button" href="#"><span class="icon icon-search"></span>查询</a>
			</td>
		</tr>
    </table>
</div>

<div class="layout_holder">
	<div class="selectbar inner_table_holder">
	<table class="layout_table right">
		<tr>
			<td>
				<a id="mail_btn" class="button" href="#"><span class="icon icon-mail"></span>邮件通知</a>
			</td>
			<!--  
			<td>
				<a id="batch_add_btn" class="button" href="#"><span class="icon icon-add"></span>批量添加</a>
			</td>
			<td>
				<a id="add_btn" class="button" href="#"><span class="icon icon-add"></span>添加</a>
			</td>
			<td>
				<a  id="import_btn" class="button" href="#"><span class="icon icon-import"></span>导入</a>
			 </td>		
             <td>
				<a id="export_btn" class="button" href="#"><span class="icon icon-export"></span>导出</a>
			 </td>
			 -->
		</tr>
	</table>
	</div>
	<div class="layout_holder">
		<table id="user_list"></table>
		<div id="user_pager"></div>
	</div>
</div>

<script type="text/javascript">
      $(document).ready( function()
      {
    	  $( 'input[type=submit], a.button , button' ).button();
    	  
    	  console.log("log");
    	  jQuery("#user_list").jqGrid({
    		 	url:'${ContextPath}/survey/user_list',
           		datatype: 'json',
           		mtype:'GET',
    		   	colNames:['', '姓名', '性别','专业','邮箱'],
    		   	colModel:[
					{name:'modifyInfo',index:'modifyInfo',align:"center", width:200},
    		   		{name:'name',index:'name', width:200,align:"center"},
    		   		{name:'gender',index:'gender', width:200, align:"center"},
    		   		{name:'discId',index:'discId', width:200, align:"center"},		
    		   		{name:'email',index:'email', width:340, align:"center"},				
    		   	],
    		   	autowidth:true,
    		   	rowNum:10,
    		   	height:"100%",
    		   	rowList:[10,20,30],
    		   	pager: '#user_pager',
    		   	sortname: 'name',
    		    viewrecords: true,
    		    sortorder: "desc",
    		    gridComplete: function(){
    		    	var ids = jQuery("#user_list").jqGrid('getDataIDs');
    				for(var i=0;i < ids.length;i++){
    					var modify = "<a class='' href='#' onclick='modifyPreInfo("+ids[i]+")'>编辑</a>"+"/"+
    					              "<a class='' href='#' onclick='deletePreInfo("+ids[i]+")'>删除</a>"; 
    					jQuery("#user_list").jqGrid('setRowData',ids[i],{modifyInfo :modify});
    				}
    		    },
    		    caption: "调查对象汇总"
    		});
    		jQuery("#user_list").jqGrid('navGrid','#user_pager',{edit:true,add:true,del:true,search:true});
    		/* var mydata= [
    		             {id:"1",name:"aa",sex:"男",major:"计算机科学技术",email:"545454545@qq.com"},
    		             {id:"2",name:"朱明远",sex:"男",major:"软件工程",email:"45654545@qq.com"},
    		             {id:"3",name:"李云马",sex:"男",major:"新媒体设计",email:"543154545@qq.com"},
    		             {id:"4",name:"彭程凯",sex:"男",major:"中文",email:"545454545@qq.com"},
    		             {id:"5",name:"赵鹏程",sex:"男",major:"数学",email:"54424545@qq.com"},
    		             {id:"6",name:"沈佩",sex:"男",major:"外国语",email:"5248954545@qq.com"},
    		             {id:"7",name:"余姚霖",sex:"女",major:"软件工程",email:"545454545@qq.com"},
    		             {id:"8",name:"潘临杰",sex:"男",major:"计算机科学技术",email:"545454545@qq.com"},
    		             {id:"9",name:"钱宇祥",sex:"男",major:"机械工程",email:"59944545@qq.com"},
    		             {id:"10",name:"李波",sex:"男",major:"自动化",email:"4644574545@qq.com"},
    		             ];
    		for( var i=0;i<mydata.length;i++ )
    		{
    			jQuery("#user_list").addRowData(i+1,mydata[i]);	
    		} */
    	
    		$("#mail_btn").click(function(){
    			//清空警告框内文本并添加新的警告文本
      		    $( "#dialog-confirm" ).empty().append("<p>即将对系统中所有用户进行邮件通知，是否继续？</p>");
      		    $( "#dialog-confirm" ).dialog({
          	        height:150,
          	        buttons: {
          	            "确定": function() {
          	            	$(this).dialog("close");
          	  				$.get('${ContextPath}/survey/userMassEmailing/', function(result){
          	  			    	if ("success" == result) {
          	  			    		alert_dialog("群发成功");
          	  			    	}
          	  			  	});
    	      	        },
          	        	"取消": function() {
          	           		 $(this).dialog("close");
          	          	}
          	        }
          	  });
    		});
      });
</script>
