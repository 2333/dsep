	<table class="textCenter">
		<caption>表4  出版著作</caption>
		<thead>
		<tr class="simSun wuHao">
			<td><span>序号</span></td>
			<td width="5.59cm"><span>著作名称</span></td>
			<td width="2.09cm"><span>著作类型</span></td>
			<td width="3.59cm"><span>ISBN号</span></td>
			<td width="3.09cm"><span>出版社</span></td>
			<td width="2.09cm"><span>出版类型</span></td>
			<td width="1.59cm"><span>出版年月</span></td>
			<td width="1.00cm"><span>本次印数</span></td>
			<td width="2.09cm"><span>第一作者</span></td>
			<td width="1.09cm"><span>本人排序</span></td>
			<td width="2.09cm"><span>本人角色</span></td>
		</tr>	
		</thead>
		<tbody>
			<!--freemarker start-->
			<#list XCBZZ as XCBZZData>
			<tr class="simSun wuHao">
				<td>${XCBZZData_index + 1}</td>
				<td width="5.59cm">${XCBZZData.ZZMC}</td>
				<td width="2.09cm">${XCBZZData.ZZLX}</td>
				<td width="3.59cm">${XCBZZData.ISBNH}</td>
				<td width="3.09cm">${XCBZZData.CBS}</td>
				<td width="2.09cm">${XCBZZData.CBLX}</td>
				<td width="1.59cm">${XCBZZData.CBNY}</td>
				<td width="1.00cm">${XCBZZData.BCYS}</td>
				<td width="2.09cm">${XCBZZData.DIZZ}</td>
				<td width="1.09cm">${XCBZZData.BRPX}</td>
				<td width="2.09cm">${XCBZZData.BRJS}</td>
			</tr>
			</#list>
			<!--freemarker end-->			
		</tbody>
	</table>
<!-- 	<div class="shuoMing">
		<p style="margin-top:6.0pt;margin-right:0cm;margin-bottom:0cm;margin-left:31.5pt;margin-bottom:.0001pt;text-indent:-31.5pt;line-height:13.0pt">
			<span>说明:<span>1."著作类型"栏中，通过下拉列表选择"专著、编著、译著、国家级规划教材、其他教材"；</span></span>
		</p>
		<p style="margin-left:31.5pt;line-height:13.0pt">
			<span>2."本人角色"，通过下拉列表选择"主编、副主编、编委、编著、作者"；"本人角色"如为"主编、副主编、编委"则采集字段"第一作者""本人排序"变灰，不可填写；</span>
		</p>
		<p style="margin-left:31.5pt;line-height:13.0pt">
			<span>3.“出版类型”栏中，通过下拉列表选择“首次出版、再版（不含重印）、重印”。</span>
		</p>
	</div> -->
	<table class="shuoMingTable">
		<tr>
			<td>说明：</td>
			<td>1."著作类型"栏中，通过下拉列表选择"专著、编著、译著、国家级规划教材、其他教材"；</td>
		</tr>	
		<tr>
			<td></td>
			<td>2."本人角色"，通过下拉列表选择"主编、副主编、编委、编著、作者"；"本人角色"如为"主编、副主编、编委"则采集字段"第一作者""本人排序"变灰，不可填写；</td>
		</tr>
		<tr>
			<td></td>
			<td>3.“出版类型”栏中，通过下拉列表选择“首次出版、再版（不含重印）、重印”。</td>
		</tr>		
	</table>	