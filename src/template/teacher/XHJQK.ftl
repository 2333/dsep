	<table class="textCenter">
		<caption>表6  获奖情况</caption>
		<thead>
		<tr class="simSun wuHao">
			<td><span>序号</span></td>
			<td width="5.52cm"><span>获奖项目名称</span></td>
			<td width="3.82cm"><span>获奖证书编号</span></td>
			<td width="2.32cm"><span>奖励类别</span></td>
			<td width="2.32cm"><span>设奖单位</span></td>
			<td width="1.52cm"><span>获奖等级</span></td>
			<td width="1.52cm"><span>获奖年度</span></td>
			<td width="2.32cm"><span>获奖单位</span></td>
			<td width="2.02cm"><span>第一获奖人</span></td>
			<td width="1.52cm"><span>本人排序</span></td>
		</tr>	
		</thead>
		<tbody>
			<!--freemarker start-->
			<#list XHJQK as XHJQKData>
			<tr class="simSun wuHao">
				<td>${XHJQKData_index + 1}</td>
				<td width="5.52cm">${XHJQKData.HJXMMC}</td>
				<td width="3.82cm">${XHJQKData.HJZSBH}</td>
				<td width="2.32cm">${XHJQKData.JLLB}</td>
				<td width="2.32cm">${XHJQKData.SJDW}</td>
				<td width="1.52cm">${XHJQKData.HJDJ}</td>
				<td width="1.52cm">${XHJQKData.HJND}</td>
				<td width="2.32cm">${XHJQKData.HJDW}</td>
				<td width="2.02cm">${XHJQKData.DYHJR}</td>
				<td width="1.52cm">${XHJQKData.BRPX}</td>
			</tr>
			</#list>
			<!--freemarker end-->			
		</tbody>
	</table>
<!-- 	<div class="shuoMing">
		<p style="margin-top:6.0pt;margin-right:0cm;margin-bottom:0cm;margin-left:31.5pt;margin-bottom:.0001pt;text-indent:-31.5pt;line-height:13.0pt">
			<span>说明:<span>1."奖励类别""获奖等级"，采用下拉列表选择，见附件四。</span></span>
		</p>
		<p style="margin-left:31.5pt;line-height:13.0pt">
			<span>2.“获奖年度”应与获奖证书名称或内容的年月表述一致，名称、内容中没有年度表述的以证书编号中的年度信息为准，以上均无的以证书落款年度为准；</span>
		</p>
		<p style="margin-left:31.5pt;line-height:13.0pt">
			<span>3.“获奖单位”，需按顺序填写所有获奖单位名称，通过勾选形式实现，勾选项有"本单位/其他单位"，例如"&#9633;本单位   &#9633;其他单位_____"；此项涉及多个单位名称，填写栏后面设置"+"键，可通过点击"+"方式添加多条单位记录；</span>
		</p>
		<p style="margin-left:31.5pt;line-height:13.0pt">
			<span>4.对于艺术创作设计奖中的获奖作品/节目名称，填写到"获奖项目名称"中。</span>
		</p>
	</div> -->
	<table class="shuoMingTable">
		<tr>
			<td>说明：</td>
			<td>1."奖励类别""获奖等级"，采用下拉列表选择，见附件四。</td>
		</tr>
		<tr>
			<td></td>
			<td>2.“获奖年度”应与获奖证书名称或内容的年月表述一致，名称、内容中没有年度表述的以证书编号中的年度信息为准，以上均无的以证书落款年度为准；</td>
		</tr>
		<tr>
			<td></td>
			<td>3.“获奖单位”，需按顺序填写所有获奖单位名称，通过勾选形式实现，勾选项有"本单位/其他单位"，例如"&#9633;本单位   &#9633;其他单位_____"；此项涉及多个单位名称，填写栏后面设置"+"键，可通过点击"+"方式添加多条单位记录；</td>
		</tr>
		<tr>
			<td></td>
			<td>4.对于艺术创作设计奖中的获奖作品/节目名称，填写到"获奖项目名称"中。</td>
		</tr>		
	</table>