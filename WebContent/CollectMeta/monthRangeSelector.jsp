<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<div id="monthRange_dv" class="table">
	<form id="monthRange_fm">
		<div>
			<table id="monthRange" style="width: 100%; text-align: center;">
				<tr>
					<td>起始年月</td>
					<td><select id="startYear">
							<option value="2005">2005</option>
							<option value="2006">2006</option>
							<option value="2007">2007</option>
							<option value="2008">2008</option>
							<option value="2009">2009</option>
							<option value="2010">2010</option>
							<option value="2011">2011</option>
							<option value="2012">2012</option>
							<option value="2013">2013</option>
							<option value="2014">2014</option>
							<option value="2015">2015</option>
							<option value="2016">2016</option>
					</select> <span>年</span></td>
					<td><select id="startMonth">
							<option value="01">01</option>
							<option value="02">02</option>
							<option value="03">03</option>
							<option value="04">04</option>
							<option value="05">05</option>
							<option value="06">06</option>
							<option value="07">07</option>
							<option value="08">08</option>
							<option value="09">09</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
					</select> <span>月</span></td>
				</tr>
				<tr>
					<td>结束年月</td>
					<td><select id="endYear">
							<option value="2005">2005</option>
							<option value="2006">2006</option>
							<option value="2007">2007</option>
							<option value="2008">2008</option>
							<option value="2009">2009</option>
							<option value="2010">2010</option>
							<option value="2011">2011</option>
							<option value="2012">2012</option>
							<option value="2013">2013</option>
							<option value="2014">2014</option>
							<option value="2015">2015</option>
							<option value="2016">2016</option>
					</select> <span>年</span></td>
					<td><select id="endMonth">
							<option value="01">01</option>
							<option value="02">02</option>
							<option value="03">03</option>
							<option value="04">04</option>
							<option value="05">05</option>
							<option value="06">06</option>
							<option value="07">07</option>
							<option value="08">08</option>
							<option value="09">09</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
					</select> <span>月</span></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<style>
#monthRange_dv tr {
	line-height: 40px;
	height: 40px;
}

td select {
	cursor: pointer;
}
</style>
<script type="text/javascript">
function showMonthRangeSelector(){
	$("#monthRange_dv").dialog({
		title:'选择年月区间',
		height:'200',
		width:'300',
		modal:true,
		draggable:true,
		hide:'fade',
		show:'fade',
		autoOpen:true,
		close:function(){},
		buttons:{
			'确认':function(){
				$(this).dialog("close");
			},
			'取消':function(){
				$(this).dialog("close");
			}
		}
	});
}
</script>