<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>学院信息维护
	</h3>
</div>

<div class="selectbar">
	<table>
		<tr>
			<!-- <td><span class="TextFont">学科编号：</span></td>
			<td><input type="text" name="teacherName" /></td>
			<td><span class="TextFont">学院名称：</span></td>
			<td><input type="text" name="teacherIdentifyNumber" /></td> -->
			<td><a class="button" href="#" id = "addCollege_btn"><span
				class="icon icon-add"></span>添加学院</a></td>
			<td><a class="button" href="#" id = "editCollege_btn"><span
				class="icon icon-edit"></span>编辑学院</a></td>
			<td style="text-align:right;"><a class="button" href="#" id = "deleteCollege_btn"><span
				class="icon icon-del"></span>删除学院</a></td>
		</tr>
	</table>
</div>

<div class="selectbar layout_holder">
	<table id="collegeList_tb"></table>
	<div id="pager"></div>
</div>


<script type="text/javascript">
	$(document).ready(function(){
		
		$("#collegeList_tb").jqGrid({
			datatype: "local",
			colNames:['学院编号','学院名称'],
			colModel:[
          		{name:'collegeId',index:'collegeId',align:'center', width:100},
          		{name:'collegeName',index:'collegeId',align:'center',width:100}
          	],
          	height:"100%",
			autowidth:true,
			pager: '#pager',
			rowNum:10, 
			rowList:[10,20,30],
			viewrecords: true,
			sortorder: "desc",
			caption: "学院列表"
		});
		
		var myData = [
           {collegeId:"01",collegeName:"材料科学与工程学院"},
           {collegeId:"02",collegeName:"电子信息学院"},
           {collegeId:"06",collegeName:"计算机学院"},
           {collegeId:"08",collegeName:"经管学院"},
           {collegeId:"21",collegeName:"软件学院"}
           ];
		
		for(var i=0; i < myData.length; i++)
			$("#collegeList_tb").jqGrid('addRowData',i+1,myData[i]);
		
		$( "input[type=submit], a.button , button" ).button();	//jquery ui - > buttons
		$(".fr_table td:nth-child(even)").addClass("fr_left");	//even - > left
		$(".fr_table td:nth-child(odd)").addClass("fr_right");	//odd  - > right
		
		//点击“添加学院”按钮，弹出“添加学院”对话框
		$("#addCollege_btn").click(function(event){
			addCollegeDialog("${ContextPath}/DSEP/infoMaintain/toAddCollege");
			event.preventDefault();
		});
		//点击“编辑学院”按钮，弹出“编辑学院”对话框
		$("#editCollege_btn").click(function(event){
			editCollegeDialog("${ContextPath}/DSEP/infoMaintain/toEditCollege");
			event.preventDefault();
		});
		//添加学院.表单显示函数
		function addCollegeDialog(url){
			$.post(url,function(data){
				$("#dialog").empty();
				$("#dialog").append(data);
				$("#dialog").dialog({
					title:"添加学院",
		  		    height:'360',
		  			width:'647.2',
		  			position:'center',
		  			modal:true,
		  			draggable:true,
		  		    hide:'fade',
		  			show:'fade',
		  		    autoOpen:true,
		  		    buttons:{
		  		    	"保存":function(){
		  		    		//JSON
    	  	                var collegeInfo=$('#collegeAdd_fr').serialize();
    	  	         		$.ajax({
	    	  	      			type:'POST',
	    	  	      			url:'${ContextPath}/infomaintain/saveAddCollege',
	    	  	      			data:collegeInfo,
	    	  	      			success: function(data){
		    	  	      			if(data){
	    	  	      	   				$("#collegeList_tb").setGridParam({url:'${ContextPath}/infomaintain/collegeList'}).trigger("reloadGrid");
		  	      	   		    		$("#dialog").dialog("close");
		    	  	      			}
		    	  	      			else
		    	  	      			{
			    	  	      			$("#dialog").dialog("close");
		    	  	      				alert("保存失败！");	
		    	  	      			}
	    	  	      			}
    	  	      			});
		  		    		$("#dialog").empty();
		  		    	},
		  		    	"取消":function(){
		  		    		$("#dialog").dialog("close");
		  		    		$("#dialog").empty();
		  		    	}
		  		    }
				});
			},'html');
		}
		//编辑学院.表单显示函数
		function editCollegeDialog(theUrl){
			$.post(theUrl,function(data){
				$("#dialog").empty();
				$("#dialog").append(data);
				$("#dialog").dialog({
					title:"学院信息",
		  		    height:'700',
		  			width:'900',
		  			position:'center',
		  			modal:true,
		  			draggable:true,
		  		    hide:'fade',
		  			show:'fade',
		  		    autoOpen:true,
		  		    buttons:{
		  		    	"保存":function(){
		  		    		$("#dialog").dialog("close");
		  		    		$("#dialog").empty();
		  		    	},
		  		    	"关闭":function(){
		  		    		$("#dialog").dialog("close");
		  		    		$("#dialog").empty();
		  		    	}
		  		    }
				});
			},'html');
		}
		
	});

</script>