<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="yy_mm_dv" class="table">
	<form id="yy_mm_fm">
		<div>
			<table id="date" style="width: 100%; text-align: center;">
			</table>
		</div>
	</form>
</div>
<style>
#date {
	text-align: center;
}

.td {
	cursor: pointer;
}
</style>
<script type="text/javascript">
var year;
var month=1;
var elemId;
var oriYM;
var isOpen;
/**
 * 设置时间
 *elem是元素
 */
function setYearMonth(elem){
		year=$("#year").find("option:selected").val();
		if(Number(month)<10)
		{
			elem.val(year+'0'+month);	
		}else{
			elem.val(year+month);
		}
		
}
/**
 * 回复数据
 */
function restoreYM(elem){
	elem.val(oriYM);
}
/**
 * 初始化dialog
 */
function initYMDialog(elem,x,y)
{
	oriYM=elem.val();//保存原始时间
	initDateTable();
	isOpen = true;
	elemId=elem;
	$("#delYear").click(function(){
		DelYear();
	});
	$("#addYear").click(function(){
		AddYear();
	});
	$("#yy_mm_dv").dialog({
  		    title:"请选择年月",
  		    height:'200',
  			width:'20%',
  			position:[x,y],
  			model:true,
  			draggable:false,
  		    hide:'fade',
  			show:'fade',
  		    autoOpen:true,
  		    close:function(){
  		    	
  		    },
  		    buttons:{
  		    	/* "确认":function(){
  		    		setYearMonth(elem);
  		    		isOpen= false;
  		    		$(this).dialog("close");
  		    	},
  		    	"取消":function(){
  		    		restoreYM(elem);
  		    		isOpen= false;
  		    		$(this).dialog("close");
  		    	} */
  		    }
	});	
	
}
/**
 * 初始化年月表
 */
function initDateTable(){
	$("#date").empty();
	   var str='';
	   str+='<tr>';
	   str+='<td colspan="4"><select id="year"><select><span>年</span></td>';
	   //str+='<td><a href="javascript:void(0)" id="addYear">> ></a></td>';
	   str+='</tr>';
	   for(var i=0;i<3;i++){
	      str+='<tr>';
	      str+='<td onclick="ChangeMonth(this)" class="td">'+Number(1+i*4)+'月</td>';
	      str+='<td onclick="ChangeMonth(this)" class="td">'+Number(2+i*4)+'月</td>';
	      str+='<td onclick="ChangeMonth(this)" class="td">'+Number(3+i*4)+'月</td>';
	      str+='<td onclick="ChangeMonth(this)" class="td">'+Number(4+i*4)+'月</td>';
	      str+='</tr>';
	   }
	   str=str.replace('<td onclick="ChangeMonth(this)" class="td">','<td onclick="ChangeMonth(this)" class="td" style="color:red">');
	   $("#date").append(str);
	   initSelect();
	}
	function AddYear(){
	   
	}
	function DelYear(){
		   
	}
	/**
	*点击月的响应事件
	*/
	function ChangeMonth(obj){
	   $("#date td").css('color','black');
	   obj.style.color="red";
	   var mt= obj.innerHTML;
	   month= mt.substring(0,mt.length-1);
	   //console.log(month);
	   setYearMonth(elemId);
	   $("#yy_mm_dv").dialog("close");
	}
	/**
	*初始化select
	*/
	function initSelect()
	{
		$("#year").empty();
		var str="";
		var currentYear= new Date().getFullYear();
		for(var i= 1940;i<2050;i++)
		{	
			if(Number(currentYear)==Number(i))
			{
				str+='<option selected="selected" value="'+i+'">'+i+'</option>';
			}else{
				str+='<option value="'+i+'">'+i+'</option>';
			}
			
		}
		$("#year").append(str);
		$("#year").click(function(){
			setYearMonth(elemId);
		});
	}
</script>