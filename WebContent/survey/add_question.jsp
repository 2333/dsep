<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="add_div" class="form">
	<form id="expertInfo_fm" class="fr_form">
		<div id = paper_above   style="margin-left:170px">
		
				<input id="paper_name" type="text" style="vertical-align:middle;width:460px;height:30px; "
				 value="请填写题目"
   		
   				 onfocus="this.style.background='#E1F4EE';if (value =='请填写题目'){value =''}  "
   				 onblur="this.style.background='#FFFFFF';if (value ==''){value='请填写题目'} ">
				 </input>
		</div>
		<div  style=" margin-top:10px;margin-left:100px" >
		
				选项1：<input  type="text"  style="vertical-align:middle;width:460px;height:20px;text-align:center">
				 </input>
				 <a   id="addBt1" class="button" href="#" role="button" aria-disabled="false" ">
						<span class="icon icon-add"style="width:15px"></span> 
				</a>
		</div>
		<div   style=" margin-top:10px;margin-left:100px" >
		
				选项2：<input  type="text" style="vertical-align:middle;width:460px;height:20px;text-align:center ">
				 </input>
				  <a   id="addBt1" class="button" href="#" role="button" aria-disabled="false" ">
						<span class="icon icon-add"style="width:15px"></span> 
				</a>
				 
		</div>
	</form>
</div>
<script type="text/javascript">


function add(){
	var addques = document.getElementById("add_div");
	var div=document.createElement("div");
	var aa=$("#addques div");
	var t=aa.length;
	var input="<input type="text" style="vertical-align:middle;width:460px;height:20px;text-align:center "></input>";
	div.innerHTML+="选项"+t+input;
	
}

</script>