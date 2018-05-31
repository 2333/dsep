		<div >
		  <table>
			<thead>
			  <tr style="height:21.8pt">
				<td width="636" colspan="6">
					<p>
						<b><span class="simSun" style="font-size:12.0pt;">I-2团队</span></b>
					</p>
				</td>
			  </tr>
			  <tr>
				<td width="44">
					<p  class="textCenter">
						<b><span class="simSun">序号</span></b>
					</p>
				</td>
				<td width="183">
					<p  class="textCenter">
						<b><span class="simSun">团队类型</span></b>
					</p>
				</td>
				<td width="142">
					<p  class="textCenter">
						<b><span class="simSun">学术带头人姓名</span></b>
					</p>
				</td>
				<td width="156">
					<p  class="textCenter">
						<b><span class="simSun">带头人出生年月</span></b>
					</p>
				</td>
				<td width="146">
					<p  class="textCenter">
						<b><span class="simSun">资助期限</span></b>
					</p>
				</td>
			  </tr>
			</thead>
			<tbody>
			  <!--freemarker start-->
			  <#list ZJ as zjData>
			  <tr style="height:19.85pt">
				<td width="44" style="height:19.85pt">
					<p  class="textCenter">
						<span  class="simSun">${zjData_index + 1}</span>
					</p>
				</td>
				<td width="149" style="height:19.85pt">
					<p  class="textCenter">
						<span class="simSun">${zjData.ZJ_TYPE}</span>
					</p>
				</td>
				<td width="90" style="height:19.85pt">
					<p  class="textCenter">
						<span  class="simSun">${zjData.ZJ_NAME}</span>
					</p>
				</td>
				<td width="112" style="height:19.85pt">
					<p  class="textCenter">
						<span  class="simSun">${zjData.BIRTHDATE}</span>
					</p>
				</td>
				<td width="112" style="height:19.85pt">
					<p  class="textCenter">
						<span  class="simSun">${zjData.HIRE_DATE}</span>
					</p>
				</td>
			  </tr>
			  </#list>
			  <!--freemarker end-->
			</tbody>
		  </table>
		</div>
		<p style="margin-top:6.0pt;margin-right:0cm;margin-bottom:0cm;margin-left:31.5pt;margin-bottom:.0001pt;text-indent:-31.5pt;line-height:13.0pt">
			<span class="kaiTi">说明:<span >1.“团队类别”栏中，限填“国家自然基金委创新群体、教育部创新团队”。</span></span>
		</p>
		<p style="margin-left:31.5pt;line-height:13.0pt">
			<span  class="kaiTi">2.“专家、团队”等人员信息不能多学科重复填写，对于跨学科人员，应填写在其主要从事的一级学科。</span>
		</p>