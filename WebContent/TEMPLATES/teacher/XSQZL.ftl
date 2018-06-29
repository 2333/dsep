	<table class="textCenter">
		<caption>表3  授权专利</caption>
		<thead>
		<tr class="simSun wuHao">
			<td><span>序号</span></td>
			<td width="5.50cm"><span>专利名称</span></td>
			<td width="3.50cm"><span>专利号</span></td>
			<td width="2.72cm"><span>专利授权国家（地区）</span></td>
			<td width="2.42cm"><span>专利类别</span></td>
			<td width="1.22cm"><span>专利授权（颁证）年月</span></td>
			<td width="2.42cm"><span>专利权人</span></td>
			<td width="2.42cm"><span>第一发明人</span></td>
			<td width="2.59cm"><span>专利转让/应用单位</span></td>
			<td width="2.29cm"><span>专利应用情况</span></td>
		</tr>	
		</thead>
		<tbody>
			<!--freemarker start-->
			<#list XSQZL as XSQZLData>
			<tr class="simSun wuHao">
				<td>${XSQZLData_index + 1}</td>
				<td width="5.50cm">${(XSQZLData.ZLMC)?html}</td>
				<td width="3.50cm">${(XSQZLData.ZLH)?html}</td>
				<td width="2.72cm">${(XSQZLData.ZLSQGJ)?html}</td>
				<td width="2.42cm">${(XSQZLData.ZLLX)?html}</td>
				<td width="1.22cm">${(XSQZLData.ZLSQNY)?html}</td>
				<td width="2.42cm">${(XSQZLData.ZLQRXM)?html}</td>
				<td width="2.42cm">${(XSQZLData.DYFMR)?html}</td>
				<td width="2.59cm">${(XSQZLData.ZLZR)?html}</td>
				<td width="2.29cm">${(XSQZLData.ZLYYQKSM)?html}</td>
			</tr>
			</#list>
			<!--freemarker end-->			
		</tbody>
	</table>
<!-- 	<div class="shuoMing">
		<p style="margin-top:6.0pt;margin-right:0cm;margin-bottom:0cm;margin-left:31.5pt;margin-bottom:.0001pt;text-indent:-31.5pt;line-height:13.0pt">
			<span>说明:<span>1.“专利类别”栏中，通过下拉列表选择“发明专利”、“外观专利”、“实用新型专利”、“国防专利”、 “其他”；</span></span>
		</p>
		<p style="margin-left:31.5pt;line-height:13.0pt">
			<span>2.“专利权人”栏中，采用勾选方式，勾选项为“本人""他人"，其中"他人"可填写他人姓名，例如"&#9633;本人   &#9633;他人_____",此数据采集项涉及多个专利权人，填写栏后面设置"+"键，可通过点击"+"方式添加多条专利权人记录。</span>
		</p>
	</div> -->
	<table class="shuoMingTable">
		<tr>
			<td>说明：</td>
			<td>1.“专利类别”栏中，通过下拉列表选择“发明专利”、“外观专利”、“实用新型专利”、“国防专利”、 “其他”；</td>
		</tr>	
		<tr>
			<td></td>
			<td>2.“专利权人”栏中，采用勾选方式，勾选项为“本人""他人"，其中"他人"可填写他人姓名，例如"&#9633;本人   &#9633;他人_____",此数据采集项涉及多个专利权人，填写栏后面设置"+"键，可通过点击"+"方式添加多条专利权人记录。</td>
		</tr>			
	</table>	