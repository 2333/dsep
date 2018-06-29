<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>公告管理
	</h3>
</div>

<div class="layout_holder">
	<div class="selectbar inner_table_holder">
		<form id="fm_search" method="post">
			<table class="layout_table">
				<tr>
					<td>
					    <span class="TextFont">级别：</span>
					</td>
					<td >
						<select id="importantLevel" name="importantLevel">
				      		<option value="-">全部</option>
				      		<option value="0">普通</option>
				      		<option value="1">重要</option>
				      		<option value="2">其他</option>
			               </select>
					</td>
					<td class="left_space">
					    <span class="TextFont">类型：</span>
					</td>
					<td >
						<select id="type" name="type">
							<option value="-">全部</option>
				      		<option value="0">通知</option>
				      		<option value="1">报表</option>
				      		<option value="2">宣传</option>
				      		<option value="3">账单</option>
				      		<option value="4">其他</option>
			               </select>
					</td>
					<td class="left_space">
					    <span class="TextFont">标题：</span>
					</td>
					<td >
						<input id="title" name="title" type="text" value="" size="20"/>
					</td>
					<td class="left_space">
					    <span class="TextFont">发布人：</span>
					</td>
					<td >
						<input id="publisher" name="publisher" type="text" value="" size="10"/>
					</td>
					<td class="left_space">
					    <span class="TextFont">日期：</span>
					</td>
					<td >
						<input id="fromDate" name="fromDate" type="text" value="" size="10"/>—<input id="toDate" name="toDate" type="text" value="" size="10"/>
					</td>
					<td class="left_space">
						<a  id="search_btn" class="button" href="#"><span class="icon icon-search"></span>查询公告</a>
					</td>
					<td class="left_space">
						<a  id="add_btn" class="button" href="#"><span class="icon icon-add"></span>新建公告</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div class="layout_holder">
		<table id="news_list"></table>
		<div id="news_pager"></div>
	</div>
</div>
<div class="hidden" id="userType">${userSession.userType}</div>

<div id="dialog-confirm" title="警告">
</div>

<script type="text/javascript">
      $(document).ready(function(){
    	    
    	 	$('input[type=submit], a.button , button').button();
    	 	$("#fromDate,#toDate").datepicker({dateFormat: 'yy-mm-dd'});
    	 	if(!($("#userType").text() == "1"|| $("#userType").text() == "0")){
    	 		$("#add_btn").remove();
    	 	}
    	  	jQuery("#news_list").jqGrid({
    	  		url: '${ContextPath}/news/newslist',
                datatype: 'json',
                mtype: 'GET',
    		   	colNames:['ID','级别', '类型','标题','发布人','日期',''],
    		   	colModel:[
					{name:'id',index:'id', width:50,align:"center", hidden:true},
    		   		{name:'importantLevelName',index:'importantLevel', width:50,align:"center",cellattr: function(rowId, val, rawObject, cm, rdata){
						 if(val=="普通"){
					        	return "style='color:green'";
					    	}else{
					    		return "style='color:red'";
					   		}
    		   		}
					},
    		   		{name:'newsTypeName',index:'type', width:50,align:"center"},
    		   		{name:'news.title',index:'title', width:400, align:"center"},
    		   		{name:'news.publisher',index:'publisher', width:150, align:"center"},		
    		   		{name:'news.date',index:'date', width:150, align:"center",formatter:"date",formatoptions: {srcformat:'u',newformat:'Y-m-d'}},
    		   		{name:'modifyInfo',index:'modifyInfo',align:"center", width:100}
    		   	],
    		   	rowNum:20,
    		   	height:"100%",
    		   	autowidth:true,
    		   	rowList:[20,30],
    		   	pager: '#news_pager',
    		   	sortname: 'date',
    		    viewrecords: true,
    		    sortorder: "desc",
    		    gridComplete: function(){
    		    	var ids = jQuery("#news_list").jqGrid('getDataIDs');
    		    	
    				for(var i=0;i < ids.length;i++){
    					var modify ="<a href='#' onclick=checkNews('"+ids[i]+"')>查看</a>";
    					if($("#userType").text() == "1"||$("#userType").text() == "0"){
    						modify += "-<a href='#' onclick=editNews('"+ids[i]+"')>编辑</a>";
    							   /* +"-<a href onclick=deleteNews('"+ids[i]+"')>删除</a>";  */	
    					}
    					jQuery("#news_list").jqGrid('setRowData',ids[i],{modifyInfo :modify});
    				}
    		    },
    		    caption: "公告列表",
    		    jsonReader: {    //jsonReader来跟服务器端返回的数据做对应  
    	            root: 'rows',  //包含实际数据的数组  
    	            page: 'pageIndex',  //当前页  
    	            total: 'totalPage',//总页数  
    	            records:'totalCount', //查询出的记录数  
    	            repeatitems : false      
    	   		}
    		}).jqGrid('navGrid','#news_pager',{edit:false,add:false,del:false,search:true});
    		
    		//打开新建公告界面
    		$('#add_btn').click(function(){
    			$.post('${ContextPath}/news/newsadd', function(data){
    				  $( "#content" ).empty();
    				  $( "#content" ).append( data );
    			  }, 'html');
    			event.preventDefault();
    		});
    		
    		//查询公告
    		$('#search_btn').click(function(){
    			jQuery("#news_list").setGridParam({
    				url:"${ContextPath}/news/newssearch?"
    					+"importantLevel="+$("#importantLevel").val()
    					+"&type="+$("#type").val()
    				 	+"&title="+$("#title").val()
    				 	+"&publisher="+$("#publisher").val()
    				 	+"&fromDate="+$("#fromDate").val()
    				 	+"&toDate="+$("#toDate").val(),
    				sortorder: "desc",
    				sortname:'date',
    				page:1,
    			}).trigger("reloadGrid");
    			event.preventDefault();
    		});
      });
      
      //跳转编辑页面
      function editNews(id){
    	  $.post('${ContextPath}/news/newsedit', {newsId:id}, function(data){
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		  }, 'html');
    	  event.preventDefault();
      }
      
      //查看公告详情
      function checkNews(id){
    	  /* $.post('${ContextPath}/news/newsdetail', {newsId:id}, function(data){
			  $( "#content" ).empty();
			  $( "#content" ).append( data );
		  }, 'html');
    	  event.preventDefault(); */
    	  
    	  $.post('${ContextPath}/news/newsdetail',{newsId:id}, function(data){
	   		  $('#dialog').empty();
			  $('#dialog').append( data );
		 	  $('#dialog').dialog({
	   	  		    title:"公告详情",
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
      
      //删除新闻
      /*
      function deleteNews(id){
    	  $( "#dialog-confirm" ).empty().append("<p>你确定删除该公告吗？</p>");
  		  $( "#dialog-confirm" ).dialog({
      	      height:150,
      	      modal:true,
      	      buttons: {
      	        "确定": function() {
      	        	$( this ).dialog( "close" );
      	        	$.post('${ContextPath}/news/newsdelete?newsId='+ id, function(data){
      	    			  if(data == true){
      	    				  alert_dialog("删除成功！");
      	    				  $("#news_list").setGridParam({url:'${ContextPath}/news/newslist'}).trigger("reloadGrid");
      	    			  }
      	    			  else if(data == false)
      	    			  {
      	    				  alert_dialog("删除失败！");
      	   				  }
      	    			  else{
      	    				  alert_dialog(data);
      	    			  }
      	    		});
      	        },
      	        "取消": function() {
      	            $( this ).dialog( "close" );
      	          }
      	        }
      	  });
  		  event.preventDefault();
      }
      */
</script>
