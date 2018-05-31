<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<div class="TE-box2">
	<table class="TE-table" >
		<tbody>
			<tr>
				<td align="left" width="80px">问卷名称:
				</td>
				<td align="left">
					<input name="name_input" id="id_input" 
						type="text" style="width:450px;">
				</td>
			</tr>
					
			<tr>
				<td style="height: 10px;">
				</td>
			</tr>
                	
			<tr>
				<td align="left" valign="top" width="80px">问卷说明:
				</td>
				<td align="left" valign="top">
					<div id="myEditor"></div>
				</td>
			</tr>
			
			<tr>
				<td style="height: 10px;">
				</td>
			</tr>
			
            <tr>
            	<td style="width:80px;">
            	
				</td>
            	<td align="center"><a id="submit-info" class="button" href="#">确认</a></td>
            </tr>
		</tbody>
	</table>
</div>

<style>


</style>

<script type="text/javascript" >
    var editor = new baidu.editor.ui.Editor({initialFrameHeight:120,initialFrameWidth:455,initialContent:"请在此填写问卷说明",autoClearinitialContent :true, });
    editor.render("myEditor");
    
    $(document).ready(function(){
    	$( 'input[type=submit], a.button , button' ).button();
    	
		$("#submit-info").click(function(){
   			 $.post("${ContextPath}/survey/qedit", function(data){
  			  $( "#content").empty();
  			  $( "#content").append( data );
  		 	  }, 'html');
  			event.preventDefault();   
   		});
    });
</script>