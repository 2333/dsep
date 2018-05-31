<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<label class = "info_label2">数据状态：</label>
<input type="text" id="collect_state" for="collect_state" class="info_input">
<script type="text/javascript">
/**
 * 获取某学校某学科的成果状态
 * @param unitId
 * @param dicsId
 */
	function collectState(unitId,dicsId)
	{
		if(unitId!=""&&discId!=""){
			$.commonRequest({
				url:'${ContextPath}/Collect/toCollect/collectState/'+unitId+'/'+dicsId,
				dataType:'text',
				success:function(data){
					data=convertStatus(data,domainId);
					var state = data;
					console.log(state);
					if((state.indexOf("正在修改")!=-1) || (state.indexOf("退回至")!=-1)){
						console.log(state);
						$("#submit2Unit").show();//标签在/DataCollect/discipline_collect.jsp中
					}else{
						$("#submit2Unit").hide();
						document.getElementById("reproduceBrief").style.visibility = "hidden";
						document.getElementById("produceBrief").style.visibility = "hidden";
					}
					$("#collect_state").val(data);
				}
			});
		}	
	}
	function setCollectState(state){
		$("#collect_state").val(data);
	}
</script>