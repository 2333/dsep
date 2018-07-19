	<table class="textCenter">
		<caption>表2  发表论文</caption>
		<thead>
		<tr class="simSun wuHao">
			<td><span>序号</span></td>
			<td width="5.09cm"><span>论文题目</span></td>
			<td width="3.09cm"><span>发表期刊（会议）名称</span></td>
			<td width="2.09cm"><span>ISSN号</span></td>
			<td width="2.09cm"><span>发表年月</span></td>
			<td width="2.09cm"><span>论文收录类型</span></td>
			<td width="2.29cm"><span>期刊影响因子（发表当年）</span></td>
			<td width="1.59cm"><span>论文第一作者</span></td>
			<td width="2.09cm"><span>通讯作者</span></td>
			<td width="1.59cm"><span>本人排序</span></td>
			<td width="2.09cm"><span>作者署名情况</span></td>
		</tr>	
		</thead>
		<tbody>
			<!--freemarker start-->
			<#list XFBLW as XFBLWData>
			<tr class="simSun wuHao">
				<td>${XFBLWData_index + 1}</td>
				<td width="5.09cm">${XFBLWData.LWTM}</td>
				<td width="3.09cm">${XFBLWData.FBQKMC}</td>
				<td width="2.09cm">${XFBLWData.ISSN_NUMBER}</td>
				<td width="2.09cm">${XFBLWData.FBNY}</td>
				<td width="2.09cm">${XFBLWData.LWSLLX}</td>
				<td width="2.29cm">${XFBLWData.QKYXYZ}</td>
				<td width="1.59cm">${XFBLWData.LWDYZZ}</td>
				<td width="2.09cm">${XFBLWData.TXZZ}</td>
				<td width="1.59cm">${XFBLWData.BRPX}</td>
				<td width="2.09cm">${XFBLWData.ZZSMQK}</td>
			</tr>
			</#list>
			<!--freemarker end-->			
		</tbody>
	</table>
<!-- 	<div class="shuoMing">
		<p style="margin-top:6.0pt;margin-right:0cm;margin-bottom:0cm;margin-left:31.5pt;margin-bottom:.0001pt;text-indent:-31.5pt;line-height:13.0pt">
			<span>说明:<span>1.“作者署名情况”栏中，通过下拉列表方式选择“第一作者、通讯作者、第一作者及通讯作者、不区分排名先后、共同第一作者、其他”；</span></span>
		</p>
		<p style="margin-left:31.5pt;line-height:13.0pt">
			<span>2.“论文收录类型”栏中，通过下拉列表方式选择“SSCI、A&amp;HCI、SCI、EI、Medline|、ISTP、CSSCI、CSCD、其他”。</span>
		</p>
	</div> -->
	<table class="shuoMingTable">
		<tr>
			<td>说明：</td>
			<td>1.“作者署名情况”栏中，通过下拉列表方式选择“第一作者、通讯作者、第一作者及通讯作者、不区分排名先后、共同第一作者、其他”；</td>
		</tr>	
		<tr>
			<td></td>
			<td>2.“论文收录类型”栏中，通过下拉列表方式选择“SSCI、A&amp;HCI、SCI、EI、Medline|、ISTP、CSSCI、CSCD、其他”。</td>
		</tr>		
	</table>		