<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="con_header">
	<h3>
		<span class="icon icon-web"></span>公示信息-选择查看单位
	</h3>
</div>
<div class=".layout_holder" style="padding-top:10px">
	<div class="left">
		<table id="tb_unit" class="layout_table">
			<tr id="tr_join_unit">
				<jsp:include page="../_lb_join_unit.jsp"></jsp:include>
			</tr>
			<tr>
				<td>
				</td>
				<td align="right" style="padding-right:10px">
					<!-- <a class="button" href="#" id="btn_reset_unit"> 
					<span class="icon icon-undo"></span>重置学校</a> -->
				</td>
			</tr>
		</table>
	</div>
	<div class="left">
		<table id="tb_disc" class="layout_table">
			<tr id="tr_join_disc">
				<jsp:include page="../_lb_join_discipline.jsp"></jsp:include>
			</tr>
			<tr>
				<td>
				</td>
				<td align="right" style="padding-right:10px">
					<!-- <a class="button" href="#" id="btn_reset_discipline"> 
					<span class="icon icon-undo"></span>重置学科</a> -->
				</td>
			</tr>
		</table>
	</div>
	<div class="left">
		<table id="tb_message" class="layout_table">
			<tr>
				<td>
					<span class="TextFont">已选学校编号:</span>&nbsp;
				</td>
				<td>
					<!-- background-color:#F2F2F2; 此背景色表示文本框不可更改-->
					<input id="tb_choosenUnit" style="width:200px"  type="text"  />&nbsp;
				</td>
				<td>
					<!-- <a class="button" href="#" id="btn_cancelUnit"> 
					<span class="icon icon-cancel"></span>清除</a> -->
					<article id="art_unit" class="TextFont" style="color:red;">此编号无法查看</article>
				</td>
			</tr>
			<tr id="tr_choosenDiscipline">
				<td>
					<span id="sp_choosenDiscipline" class="TextFont">已选${textConfiguration.disc}编号:</span>&nbsp; 
				</td>
				<td>
					<!-- background-color:#F2F2F2; 此背景色表示文本框不可更改-->
					<input id="tb_choosenDiscipline" style="width:200px" type="text"  />
				</td>
				<td>
					<article id="art_discipline" class="TextFont" style="color:red;">此编号无法查看</article>
				</td>
			</tr>
			<tr>
				<td>
					
				</td>
				<td align="right">
					<a class="button" href="#" id="btn_reset"> 
						<span class="icon icon-undo"></span>重置</a>
					<a class="button" href="#" id="btn_viewPublicity"> 
					<span class="icon icon-search"></span>查看</a>
				</td>
			</tr>
		</table>
	</div>
</div>	
	
<script type="text/javascript">

	var unitMap;
	var discMap;
	$(document).ready(function() {
		$("input[type=submit], a.button , button").button();
		$("#art_unit").hide();
		$("#art_discipline").hide();
		if("${user.userType}"+"" == "3"){//学科用户
			$("#tb_choosenDiscipline").val("${user.discId}");
			$("#tb_disc").hide();
			$("#tr_choosenDiscipline").hide();
			$("#btn_reset").hide();
		}
		else{//学校用户和中心用户
			$("#tb_choosenDiscipline").val("${formerViewDisciplineId}");
			$("#tb_choosenUnit").val("${formerViewUnitId}");
			$("#tb_disc").show();
			$("#tb_unit").show();
			$("#btn_reset").show();
		}
		unitMap = eval("(${joinUnitMap})").root;
		discMap = eval("(${joinDisciplineMap})").root;
		setLbDiscipline("${joinDisciplineMap}");
		setLbUnit("${joinUnitMap}");
	});
	
	function setLbDiscipline(data){
		data = eval("("+data+")");
		$("#lb_join_discipline").empty();
		discMap = data.root;
		if(!$.isEmptyObject(data)){
			$.each(data.root,function(i,item){
				$("#lb_join_discipline").append('<option value="'+item.name+'">'+item.value+'</option>');
			});
		}
		else{
			$("#lb_join_discipline").append('<option value="">暂无参评${textConfiguration.disc}</option>');
		}
	}
	
	function setLbUnit(data){
		data = eval("("+data+")");
		$("#lb_join_unit").empty();
		unitMap = data.root;
		if(!$.isEmptyObject(data)){
			$.each(data.root,function(i,item){
				$("#lb_join_unit").append('<option value="'+item.name+'">'+item.value+'</option>');
			});
		}
		else{
			$("#lb_join_unit").append('<option value="">暂无参评学校</option>');
		}
	}
	
    
	//选中列表框的某一学科
    $("#lb_join_discipline").click(function(){
    	var discId = $("#lb_join_discipline").val();
    	discChange(discId);
    });
    
	
    //选中列表框的某一学校
    $("#lb_join_unit").click(function(){
    	var unitId = $("#lb_join_unit").val();
    	unitChange(unitId);
    });
    
    //重置页面上所有信息
    $("#btn_reset").click(function(){
    	resetMessage();
    	resetDisc();
    	resetUnit();
    	$("#art_discipline").hide();
    	$("#art_unit").hide();
    });
    
    //重置已选学校和已选学科
    function resetMessage(){
    	if("${user.userType}"+"" == "3"){ //学科用户
	    	$("#tb_choosenUnit").val("");
    	}
    	else{
    		$("#tb_choosenDiscipline").val("");
	    	$("#tb_choosenUnit").val("");
    	}
    }
    
  	//重新设置学校列表框
    function resetUnit(){
    	$.ajax({ 
    		url:'${ContextPath}/publicity/viewPub_resetUnit',
    		type:"POST",
    		success:function(data){
    			setLbUnit(data);
    			$("#lb_join_discipline").attr("selected","");
    		}
    	});
    }
    
  	//重新设置学科列表框
    function resetDisc(){
    	$.ajax({ 
    		url:'${ContextPath}/publicity/viewPub_resetDisc',
    		type:"POST",
    		success:function(data){
    			setLbDiscipline(data);
    			$("#lb_join_unit").attr("selected","");
    		}
    	});
    }
    
    //查看学科发生变化时改变学科列表框
    function unitChange(unitId){
    	if( unitId != "" && unitId != null){
    		$("#tb_choosenUnit").val(unitId);
	    	$.ajax({
	    		url:'${ContextPath}/publicity/viewPub_changeUnit',
	    		type:"POST",
	    		data:"unitId="+unitId,
	    		success:function(data){
	    			setLbDiscipline(data);
	    		},
	    		error:function(data){
	    			alert_dialog('出现错误');
	    		}
	    	});
    	}
    }
    
    //查看学科发生变化时改变学校列表框
    function discChange(discId){
    	if( discId != "" && discId != null){
	    	$("#tb_choosenDiscipline").val(discId);
    		$.ajax({
	    		url:'${ContextPath}/publicity/viewPub_changeDiscipline',
	    		type:"POST",
	    		data:"discId="+discId,
	    		success:function(data){
	    			console.log(data);
	    			setLbUnit(data);
	    		}
    		});
    	}
    }
    
    //判断输入的学校Id是否在map中
    function isInUnitMap(unitId){
    	var result = false;
    	$.each(unitMap,function(i,item){ 
    		 if( item.name == unitId){
    			 result = true;
    		 }
    	});
    	return result;
    }
    
    //判断输入的学科Id是否在map中
    function isInDiscMap(discId){
    	var result = false;
    	$.each(discMap,function(i,item){ 
   		 	if( item.name == discId){
   		 		result = true;
   		 	}
    	});
    	return result;
    }
    
    //是否能够查看公示数据
    function isViewPublicity(){
    	if($("#tb_choosenDiscipline").is(":visible")){
    		if($("#tb_choosenDiscipline").val() == "" || $("#tb_choosenUnit").val() == ""){
    			return false;
    		}
    		else{
    			return true;
    		}
    	}
    	else if( $("#tb_choosenUnit").val() == ""){
    		return false;
    	}
    	else{
    		return true;
    	}
    }
    
    //所填的学校和学科是否有效，无效则无法查看公示信息
    function isLegalDepartment(){
    	if($("#tb_choosenDiscipline").is(":visible")){
    		if( isInDiscMap($("#tb_choosenDiscipline").val()) || isInUnitMap($("#tb_choosenUnit").val())) {	
    			return true;
    		}
    		else{
    			return false;
    		}
    	}
    	else if( isInUnitMap($("#tb_choosenUnit").val()) ){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
    //查看公示数据
    $("#btn_viewPublicity").click(function(){
    	if(isViewPublicity() && isLegalDepartment()){
    		$.ajax({
    			url:"${ContextPath}/publicity/viewPub_viewPublicity",
    			type:"POST",
    			data:"unitId="+$("#tb_choosenUnit").val()+"&discId="+$("#tb_choosenDiscipline").val(),
    			success:function(data){
    				 $( "#content" ).empty();
    				 $( "#content" ).append(data);
    			}
    		});
    	}
    	else if( !isViewPublicity() ){
    		alert_dialog('请先选择查看单位');
    	}
    	else if( !isLegalDepartment()){
    		alert_dialog('请先选择有效的查看单位');
    	}
    });
    
    //查看学校的文本框的键盘事件，如果输入的学校编号不合法则提示警告信息
    $("#tb_choosenUnit").keyup(function(){
    	if( $("#tb_choosenUnit").val() == "" ){
    		$("#art_unit").hide();
    		resetDisc();	
    	}
    	else if( !isInUnitMap($("#tb_choosenUnit").val()) ){
    		$("#art_unit").show();
    	}
    	else{
    		$("#art_unit").hide();
    		unitChange($("#tb_choosenUnit").val());
    	}
    });
    
  	//查看学科的文本框的键盘事件，如果输入的学科编号不合法则提示警告信息
    $("#tb_choosenDiscipline").keyup(function(){
    	if( $("#tb_choosenDiscipline").val() == "" ){
    		$("#art_discipline").hide();
    		resetUnit();	
    	}
    	else if( !isInDiscMap($("#tb_choosenDiscipline").val()) ){
    		$("#art_discipline").show();
    	}
    	else{
    		$("#art_discipline").hide();
    		discChange($("#tb_choosenDiscipline").val());
    	}
    });

</script>