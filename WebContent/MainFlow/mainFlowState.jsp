<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<table class="layout_table right">
		<tr>		
			<td>
			    <span class="TextFont">当前状态：</span>
			</td>
             <td>
                 <label id ='main_state' class="TextFont"></label>
             </td>
		</tr>
</table>
<script src="${ContextPath}/js/jquery.common.js"></script>
<script type="text/javascript">
/**
 * 获取主流程状态
 * @param unitId
 * @param dicsId
 */
	function mainState()
	{
			$.commonRequest({
				url:'${ContextPath}/MainFlow/getCurrentState',
				dataType:'text',
				success:function(data){
					//data=convertStatus(data);
					console.log(data);
					$("#main_state").html(data);
					}
				});
			
	}
</script>