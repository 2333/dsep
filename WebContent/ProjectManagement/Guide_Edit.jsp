<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>编辑指南
	</h3>
</div>
<div class="selectbar inner_table_holder">
	<form id="fm_editGuides" class="fr_form" method="post">  
		<table class="layout_table left">
			<tr class="hidden">
	             <td >ID：</td>  
	             <td >
	                <input id="projectId" type="text" name="id" value="${project.project.id}" readonly/>  
	             </td>
	             <td>
	             	<input type="hidden" id="sessionid" value="${pageContext.session.id}" />
	             </td>
            </tr>  
			<tr>
				<td>
				    <span class="TextFont">项目类别：</span>
				</td>
				<td style="width:150px;">
					<select id="guide_type" name="guide_type">
						<option value="国防类纵向项目">国防类纵向项目</option>
						<option value="民口类纵向项目">民口类纵向项目</option>
						<option value="基础类纵向项目">基础类纵向项目</option>
						<option value="人文社科纵向项目">人文社科纵向项目</option>
					</select>
				</td>
				<td style="width:80px;">
				    <span class="TextFont">结束时间：</span>
				</td>
				<td>
					<input type="text" id="end_date"  name="end_date" value="${project.endDate}"/>
				</td>
				<td>&nbsp;&nbsp;</td>
				<td>
				    <span class="TextFont">项目名称：</span>
				</td>
				<td colspan="4">
					<input id="guide_name" name="guide_name" type="text" size="50" value="${project.project.projectName}"/>
				</td>
			</tr>
			</table>
			<table class="layout_table left" style="margin-top:8px;">
			<tr>
				<td>
				    <span class="TextFont">项目简介：</span>
				</td>
				<td colspan="4">
					<textarea name="guide_content" id="guide_content" ></textarea>
					<input type="hidden" value="${project.project.projectIntro}" id="projectIntro_value"> 
				</td>
			</tr>
			</table>
			<table class="layout_table left" style="margin-top:8px;">
			<tr>
				<td>
				    <span class="TextFont">申报条件：</span>
				</td>
				<td colspan="4">
					<textarea name="apply_conditions" id="apply_conditions" ></textarea> 
					<input type="hidden" value="${project.project.projectRestrict}" id="apply_conditions_value"> 
				</td>
			</tr>
			</table>
			<table class="layout_table left" style="margin-top:8px;">
			<tr>
				<td>
				    <span class="TextFont">答辩事项：</span>
				</td>
				<td colspan="4">
					<textarea name="defense_matters" id="defense_matters"></textarea>
					<input type="hidden" value="${project.project.projectDetail}" id="defense_matters_value"> 
				</td>
			</tr>
			<tr >
				<td>
				    <span class="TextFont">附件：</span>
				</td>
				<td colspan="4">
					<input id="file_upload" name="file_upload" type="file">
					<div id="fileQueue"></div>
					<a class="button" id="upload">上传</a>| 
         			<a class="button" id="cancel">取消上传</a>	|
         			<a class="button" id="delete">删除附件</a>	
         			<b>您上传的附件：</b><a id="file_detail" style="color:Green;font-weight:bold;">${project.project.attachment.name}</a><a id="file_no" style="display:none">${project.project.attachment.id}</a>
				</td>
			</tr>
		</table>
	</form>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<a  id="save_btn" class="button" href="#"><span class="icon icon-store"></span>保存</a>
			</td>
			<td>
				<a  id="return_btn" class="button" href="#"><span class="icon icon-cancel"></span>取消</a>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
	  
	var editor1 = new baidu.editor.ui.Editor({initialFrameHeight:100,initialFrameWidth:890,autoClearinitialContent :true, });
	editor1.addListener( 'ready', function( editor1 ) {
		setEditor1Contents(); //编辑器加载完成后，让编辑器拿到焦点
	 } );
	function setEditor1Contents() {
		var projectIntro_value  = $("#projectIntro_value").val();
		editor1.setContent(projectIntro_value);
	}
	editor1.render("guide_content");
	
	
	var editor2 = new baidu.editor.ui.Editor({initialFrameHeight:100,initialFrameWidth:890,autoClearinitialContent :true, });
	editor2.addListener( 'ready', function( editor2 ) {
		setEditor2Contents(); //编辑器加载完成后，让编辑器拿到焦点
	 } );
	function setEditor2Contents() {
		var apply_conditions_value  = $("#apply_conditions_value").val();
		editor2.setContent(apply_conditions_value);
	}
	editor2.render("apply_conditions");
	
	
	var editor3 = new baidu.editor.ui.Editor({initialFrameHeight:100,initialFrameWidth:890,autoClearinitialContent :true, });
	editor3.addListener( 'ready', function( editor3 ) {
		setEditor3Contents(); //编辑器加载完成后，让编辑器拿到焦点
	 } );
	function setEditor3Contents() {
		var defense_matters_value  = $("#defense_matters_value").val();
		editor3.setContent(defense_matters_value);
	}
	editor3.render("defense_matters");
	
	function checkNotNull()
	{
		if( !$('#end_date').val() )
		{
			alert_dialog("请输入项目结束时间!");
			return false;
		}
		if( !$('#guide_name').val())
		{
			alert_dialog("请输入项目名称!");
			return false;
		}
		return true;
	}
	
	
	
    $(document).ready(function(){
    	  
    	 	$('input[type=submit], a.button , button').button();
    	 	var guide_type = "${project.project.projectType}";
    	 	var guide_select = document.getElementById("guide_type");
    	 	var options = guide_select.options;
    	 	for(var i=0;i<options.length;i++)
    	 	{
    	 		if(guide_type == options[i].value )
    	 		{
    	 			options[i].selected=true;
    	 			break;
    	 		}
    	 	}
    	 	
    	 	$("#file_upload").uploadify({  
                'swf'            : '${ContextPath}/js/uploadify/uploadify.swf',  
                'uploader'       : '${ContextPath}/project/punit_fileUpload?jsessionid=' + $("#sessionid").val(),//后台处理的请求   
                'queueID'        : 'fileQueue',
                'queueSizeLimit' : 1,  
                'auto'           : false,
                'buttonText'     : '附件',
                'onUploadSuccess':function(file,data,response){
                	$("#file_detail").html(file.name);
                	$("#file_no").html(data);
                	
                }
            });  
    	 	
    	 	$('#save_btn').click(function(){
    	 		if( checkNotNull() )
    	 		{
    				$.post("${ContextPath}/project/punit_saveEdit", $("#fm_editGuides").serialize()+"&attachmentId="+$("#file_no").text() , function(data){
    				if(data){
    					alert_dialog("保存成功！");
    					$('#return_btn').click();
    				}
    				else{
    					alert_dialog("保存失败，信息不完整或服务器错误！");
    				}
    				}, 'json');
    	 		}
    		});
    	 	
    	 	$('#return_btn').click(function(){
    	 		$.post("${ContextPath}/project/punit", function(data){
					  $( "#content" ).empty();
					  $( "#content" ).append( data );
				 	  }, 'html');
    	 		event.preventDefault();
    		});
    	 	
    	 	$('#upload').click(function(){
    	 		$('#file_upload').uploadify('upload');
    		});
    	 	
    	 	$('#cancel').click(function(){
    	 		$('#file_upload').uploadify('cancel');
    		});
    	 	
    	 	$('#delete').click(function(){
    	 		$.post('${ContextPath}/project/punit_fileDelete',{id:$("#file_no").text()} ,function(data){
    	 			if(data){
    	 				$("#file_detail").text("");
    	 				$("#file_no").text("");
    	 			}else{
    	 				alert_dialog("尚无附件或附件删除失败！");	
    	 			}
  			  	}, 'json');
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
  	
  		   jQuery("#end_date").datepicker({
  			   yearRange:'1900:2020',
  			   changeMonth: true,
  	           changeYear: true,
  	           showButtonPanel: true,
  			   dateFormat: 'yy-mm-dd',  //日期格式，自己设置              buttonImage: 'calendar.gif',  //按钮的图片路径，自己设置              buttonImageOnly: true,  //Show an image trigger without any button.             showOn: 'both',//触发条件，both表示点击文本域和图片按钮都生效         yearRange: '1990:2008',//年份范围          clearText:'清除',//下面的就不用详细写注释了吧，呵呵，都是些文本设置         closeText:'关闭',         prevText:'前一月',         nextText:'后一月',         currentText:' ',          monthNames:['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],  
  		   });
  		 jQuery("#end_date").click(function(){
  			$("#ui-datepicker-div").css({"z-index":"2000"});
  		 });
  		   
      });
      
     
</script>