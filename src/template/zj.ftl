		<div>
		  <table>
			<thead>
			  <tr style="height:21.8pt">
				<td width="17.87cm" colspan="6">
					<p>
						<b><span class="simSun xiaoSi" >I-1专家</span></b> 
					</p>
				</td>
			  </tr>
			  <tr style="height:27.35pt">
				<td width="1.16cm" >
					<p  class="textCenter">
						<b><span class="simSun">序号</span></b>
					</p>
				</td>
				<td width="3.95cm">
					<p  class="textCenter">
						<b><span class="simSun">专家类别</span></b>
					</p>
				</td>
				<td width="2.39cm">
					<p  class="textCenter">
						<b><span class="simSun">专家姓名</span></b>
					</p>
				</td>
				<td width="2.95cm">
					<p  class="textCenter">
						<b><span class="simSun">出生年月</span></b>
					</p>
				</td>
				<td width="2.98cm">
					<p  class="textCenter">
						<b><span class="simSun">获批年月</span></b>
					</p>
				</td>
				<td width="3.39cm">
					<p  class="textCenter">
						<b><span class="simSun">备注</span></b>
					</p>
				</td>
			  </tr>
			</thead>
			<tbody>
			  <!--freemarker start-->
			  <#list ZJ as zjData>
			  <tr style="height:19.85pt">
				<td width="1.16cm">
					<p  class="textCenter">
						<span  class="simSun">${zjData_index + 1}</span>
					</p>
				</td>
				<td width="3.95cm">
					<p  class="textCenter">
						<span class="simSun">${zjData.ZJ_TYPE}</span>
					</p>
				</td>
				<td width="2.39cm" >
					<p  class="textCenter">
						<span  class="simSun">${zjData.ZJ_NAME}</span>
					</p>
				</td>
				<td width="2.95cm">
					<p  class="textCenter">
						<span  class="simSun">${zjData.BIRTHDATE}</span>
					</p>
				</td>
				<td width="2.98cm">
					<p  class="textCenter">
						<span  class="simSun">${zjData.HIRE_DATE}</span>
					</p>
				</td>
				<td width="3.93cm">
					<p  class="textCenter">
						<span class="simSun">${zjData.MEMO}</span>
					</p>
				</td>
			  </tr>
			  </#list>
			  <!--freemarker end-->
			</tbody>
		  </table>
		</div>
		<p style="margin-top:6.0pt;margin-right:0cm;margin-bottom:0cm;margin-left:31.5pt;margin-bottom:.0001pt;text-indent:-31.5pt;line-height:13.0pt">
			<span class="kaiTi">说明：<span >1.“专家类别”栏中，限填“中国科学院/工程院院士、千人计划入选者、军队科技领军人才培养对象、ACM Fellow、IEEE Fellow、长江学者特聘/讲座教授、国家杰青基金获得者、973首席科学家、国家级教学名师、教育部高校青年教师奖获得者、教育部跨世纪人才、百千万人才工程国家级人选、中科院百人计划入选者、教育部新世纪人才”。同一专家有多种称谓时，请按以上顺序选择一种，不重复填写。</span></span>
		</p>
		<p style="margin-left:31.5pt;line-height:13.0pt">
			<span  class="kaiTi">2.“千人计划入选者”不包含“短期千人”和“青年千人”；国家杰青基金获得者应依据其“申请学科”进行填报（需在“备注”栏中注明申请时的学科代码），原“杰青B类”不计入内。</span>
		</p>
		<p style="margin-top:2.4pt;margin-right:0cm;margin-bottom:0cm; margin-left:31.5pt;margin-bottom:.0001pt;line-height:13.0pt">
			<span  class="kaiTi">3.长江学者应依据其设岗学科进行填报。“聘期”内的长江学者，若人事关系不在聘任单位，应由聘任单位填写（需在“备注”栏注明人事关系所在单位名称：不在“聘期”内的长江学者，由人事关系所在单位填写。</span>
		</p>
		<p style="margin-left:31.5pt;line-height:13.0pt">
			<span  class="kaiTi">4.院士需在“备注”栏中注明所在学部。</span></p>
		<p style="text-indent:31.5pt;line-height:13.0pt">
			<span  class="kaiTi">5.2011年12月31日前已故专家不再计入师资队伍。</span>
		</p>