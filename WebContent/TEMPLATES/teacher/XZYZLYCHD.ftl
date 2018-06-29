	<table class="textCenter">
		<caption>表8  策划、举办或参加重要展览、演出活动</caption>
		<thead>
		<tr class="simSun wuHao">
			<td><span>序号</span></td>
			<td width="5.50cm"><span>展演作品/节目名称</span></td>
			<td width="3.50cm"><span>参演活动名称</span></td>
			<td width="2.00cm"><span>展演时间</span></td>
			<td width="2.00cm"><span>展览地点</span></td>
			<td width="11.19cm"><span>相关说明（每项不超过100字）（如：本单位主要获奖人及其担任的角色等</span></td>
		</tr>	
		</thead>
		<tbody>
			<!--freemarker start-->
			<#list XZYZLYCHD as XZYZLYCHDData>
			<tr class="simSun wuHao">
				<td>${XZYZLYCHDData_index + 1}</td>
				<td width="5.50cm">${(XZYZLYCHDData.ZYZP_JMMC)?html}</td>
				<td width="3.50cm">${(XZYZLYCHDData.CYHDMC)?html}</td>
				<td width="2.00cm">${(XZYZLYCHDData.ZYSJ)?html}</td>
				<td width="2.00cm">${(XZYZLYCHDData.ZYDD)?html}</td>
				<td width="11.19cm">${(XZYZLYCHDData.XGSM)?html}</td>
			</tr>
			</#list>
			<!--freemarker end-->			
		</tbody>
	</table>
