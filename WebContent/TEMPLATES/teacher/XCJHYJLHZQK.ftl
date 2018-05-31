	<table class="textCenter">
		<caption>表7  参加国际、国内学术会议的交流合作情况</caption>
		<thead>
		<tr class="simSun wuHao">
			<td><span>序号</span></td>
			<td width="3.00cm"><span>会议名称</span></td>
			<td width="6.00cm"><span>会议报告名称</span></td>
			<td width="2.50cm"><span>出镜时间</span></td>
			<td width="2.50cm"><span>入境时间</span></td>
			<td width="2.80cm"><span>会议地点（国家地区）</span></td>
			<td width="3.00cm"><span>主办单位</span></td>
			<td width="2.50cm"><span>报告形式</span></td>
		</tr>	
		</thead>
		<tbody>
			<!--freemarker start-->
			<#list XCJHYJLHZQK as XCJHYJLHZQKData>
			<tr class="simSun wuHao">
				<td>${XCJHYJLHZQKData_index + 1}</td>
				<td width="3.00cm">${(XCJHYJLHZQKData.HYMC)?html}</td>
				<td width="6.00cm">${(XCJHYJLHZQKData.HYBGMC)?html}</td>
				<td width="2.50cm">${(XCJHYJLHZQKData.CJSJ)}</td>
				<td width="2.50cm">${(XCJHYJLHZQKData.RJSJ)}</td>
				<td width="3.00cm">${(XCJHYJLHZQKData.HYDD)}</td>
				<td width="2.80cm">${(XCJHYJLHZQKData.ZBDW)?html}</td>
				<td width="2.50cm">${(XCJHYJLHZQKData.BGXS)?html}</td>
			</tr>
			</#list>
			<!--freemarker end-->			
		</tbody>
	</table>
<!-- 	<div class="shuoMing">
		<p style="margin-top:6.0pt;margin-right:0cm;margin-bottom:0cm;margin-left:31.5pt;margin-bottom:.0001pt;text-indent:-31.5pt;line-height:13.0pt">
			<span>说明:<span>1."交流形式"，采用下拉列表选择"出席人员、交流论文、特邀报告、主办会议、其他"。</span></span>
		</p>
	</div> -->
	<table class="shuoMingTable">
		<tr>
			<td>说明：</td>
			<td>1."交流形式"，采用下拉列表选择"出席人员、交流论文、特邀报告、主办会议、其他"。</td>
		</tr>	
	</table>	