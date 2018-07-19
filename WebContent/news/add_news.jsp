<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="con_header">
	<h3>
		<span class="icon icon-user"></span>新建公告
	</h3>
</div>
<div class="selectbar inner_table_holder">
	<form id="fm_addNews" class="fr_form" method="post">  
		<table class="layout_table left">
			<tr class="hidden">
	             <td>
	             	<input type="hidden" id="sessionid" value="${pageContext.session.id}" />
	             </td>
            </tr>  
			<tr>
				<td>
				    <span class="TextFont">重要级别：</span>
				</td>
				<td style="width:75px;">
					<select id="importantLevel" name="importantLevel">
			      		<option value="0">普通</option>
			      		<option value="1">重要</option>
			      		<option value="2">其他</option>
	                </select>
				</td>
				<td style="width:75px;">
				    <span class="TextFont">公告类型：</span>
				</td>
				<td style="width:75px;">
					<select id="type" name="type">
			      		<option value="0">通知</option>
			      		<option value="1">报表</option>
			      		<option value="2">宣传</option>
			      		<option value="3">账单</option>
			      		<option value="4">其他</option>
	                </select>
				</td>
			</tr>
			<tr>
				<td>
				    <span class="TextFont">公告标题：</span>
				</td>
				<td colspan="4">
					<input id="title" name="title" type="text" value="" size="90"/>
				</td>
			</tr>
			<tr>
				<td>
				    <span class="TextFont">公告内容：</span>
				</td>
				<td colspan="4">
					<textarea name="content" id="myContent"></textarea> 
				</td>
			</tr>
			<tr>
				<td>
				    <span class="TextFont">公告附件：</span>
				</td>
				<td colspan="4">
					<input id="file_upload" name="file_upload" type="file">
					<div id="fileQueue"></div>
					<a class="button" id="upload">上传</a>| 
         			<a class="button" id="cancel">取消上传</a>	|
         			<a class="button" id="delete">删除附件</a>	
         			<b>您上传的附件：</b><a id="file_detail" style="color:Green;font-weight:bold;"></a><a id="file_no" style="display:none"></a>
				</td>
			</tr>
		</table>
	</form>
</div>
<div class="selectbar inner_table_holder">
	<table class="layout_table left">
		<tr>
			<td>
				<a  id="add_btn" class="button" href="#"><span class="icon icon-add"></span>确定</a>
			</td>
			<td>
				<a  id="return_btn" class="button" href="#"><span class="icon icon-cancel"></span>取消</a>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">
	  
	var editor = new baidu.editor.ui.Editor({initialFrameHeight:400,initialFrameWidth:660,autoClearinitialContent :true, });
	editor.render("myContent");
	  
      $(document).ready(function(){
    	  
    	 	$('input[type=submit], a.button , button').button();
    	 	
    	 	$("#file_upload").uploadify({  
                'swf'            : '${ContextPath}/js/uploadify/uploadify.swf',  
                'uploader'       : '${ContextPath}/news/newsupload?jsessionid=' + $("#sessionid").val(),//后台处理的请求    
                'queueID'        : 'fileQueue',
                'queueSizeLimit' : 1,  
                'auto'           : false,
                'buttonText'     : '附件',
                'onUploadSuccess':function(file,data,response){
                	$("#file_detail").html(file.name);
                	$("#file_no").html(data);
                }
            });  
    	 	
    	 	$('#add_btn').click(function(){
    			$.post("${ContextPath}/news/newssaveadd", $("#fm_addNews").serialize()+"&attachmentId="+$("#file_no").text() , function(data){
    				if(data){
    					alert_dialog("保存成功！");
    					$('#return_btn').click();
    				}
    				else{
    					alert_dialog("保存失败，信息不完整或服务器错误！");
    				}
    			}, 'json');
    		});
    	 	
    	 	$('#return_btn').click(function(){
    			$.post('${ContextPath}/news/news', function(data){
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
    	 		$.post('${ContextPath}/news/newsfiledelete',{id:$("#file_no").text()} ,function(data){
    	 			if(data){
    	 				alert_dialog("附件已删除！");
    	 				$("#file_detail").text("");
    	 				$("#file_no").text("");
    	 			}else{
    	 				alert_dialog("尚无附件或附件删除失败！");	
    	 			}
  			  	}, 'json');
    		});
    	 
      });
      
     
</script>