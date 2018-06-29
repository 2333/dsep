	<table class="textCenter">
		<caption>表1  科研项目</caption>
		<thead>
		<tr class="simSun wuHao">
			<td><span>序号</span></td>
			<td width="5.09cm"><span>项目名称</span></td>
			<td width="5.09cm"><span>项目编号</span></td>
			<td width="3.09cm"><span>项目类别</span></td>
			<td width="2.09cm"><span>项目下达单位</span></td>
			<td width="2.09cm"><span>项目承担单位</span></td>
			<td width="2.09cm"><span>项目负责（主持）人</span></td>
			<td width="1.29cm"><span>项目下达年月</span></td>
			<td width="1.29cm"><span>项目开始年月</span></td>
			<td width="1.29cm"><span>项目结束年月</span></td>
			<td width="1.49cm"><span>合同经费（万）</span></td>
		</tr>	
		</thead>
		<tbody>
			<!--freemarker start-->
			<#list XKYXM as XKYXMData>
			<tr class="simSun wuHao">
				<td>${XKYXMData_index + 1}</td>
				<td width="5.09cm">${XKYXMData.XMMC}</td>
				<td width="5.09cm">${XKYXMData.XMBH}</td>
				<td width="3.09cm">${XKYXMData.XMLB}</td>
				<td width="2.09cm">${XKYXMData.XMXDDW}</td>
				<td width="2.09cm">${XKYXMData.XMCDDWMC}</td>
				<td width="2.09cm">${XKYXMData.XMFZRXM}</td>
				<td width="1.29cm">${XKYXMData.XMXDNY}</td>
				<td width="1.29cm">${XKYXMData.XMKSNY}</td>
				<td width="1.29cm">${XKYXMData.XMJSNY}</td>
				<td width="1.49cm">${XKYXMData.HTJF}</td>
			</tr>
			</#list>
			<!--freemarker end-->			
		</tbody>
	</table>
<!-- 	<div class="shuoMing">
		<p style="margin-top:6.0pt;margin-right:0cm;margin-bottom:0cm;margin-left:31.5pt;margin-bottom:.0001pt;text-indent:-31.5pt;line-height:13.0pt">
			<span>说明:<span>1."项目下达单位"、"项目类别"采用下拉列表形式，见附件三(项目下达单位与合同签署单位一致)；</span></span>
		</p>
		<p style="margin-left:31.5pt;line-height:13.0pt">
			<span>2."项目承担单位"需按顺序填写所有单位名称，通过勾选方式实现，勾选项分"本单位""其他单位"，其中"其他单位"可填写单位名称,例如</span><br/>
			<span>"&#9633;本单位   &#9633;其他单位_____"；此项涉及多个单位名称，填写栏后面设置”+“键，可通过点击”+“方式添加多条单位记录；</span>
		</p>
		<p style="margin-left:31.5pt;line-height:13.0pt">
			<span>3."项目负责（主持）人"需填写项目负责人姓名，通过勾选方式实现，勾选项分为"本人""他人"，其中"他人"可填写他人姓名，</span>
		</p>
		<p style="margin-left:31.5pt;line-height:13.0pt">
			<span>例如"&#9633;本人  &#x025A1;他人_____"。</span>
		</p>
	</div> -->
	<table class="shuoMingTable">
		<tr>
			<td>说明：</td>
			<td>1."项目下达单位"、"项目类别"采用下拉列表形式，见附件三(项目下达单位与合同签署单位一致)；</td>
		</tr>	
		<tr>
			<td></td>
			<td>2."项目承担单位"需按顺序填写所有单位名称，通过勾选方式实现，勾选项分"本单位""其他单位"，其中"其他单位"可填写单位名称,例如"&#9633;本单位   &#9633;其他单位_____"；此项涉及多个单位名称，填写栏后面设置”+“键，可通过点击”+“方式添加多条单位记录；</td>
		</tr>		
		<tr>
			<td></td>
			<td>3."项目负责（主持）人"需填写项目负责人姓名，通过勾选方式实现，勾选项分为"本人""他人"，其中"他人"可填写他人姓名，例如"&#9633;本人  &#x025A1;他人_____"。</td>
		</tr>			
	</table>