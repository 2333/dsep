		<#list qList as q>
			<#if "0" == q.qType>
				<div class="Q SCQ qWrapper" id="q${q_index?number+1}" >
					<div class="qStem">
					<span class="icon icon-del qStemIcon" onclick="delQ(this)" title="删除本题"></span>
					<label class="QNum">Q${q_index?number+1}</label>
					<input class="stem SCQStem" value="${q.qStem}" 
						onfocus="stemToggle(this);" onblur="stemToggle(this);" type="text"><br/>
					</div>
				<#list q.items as choice>
					<div class="qItem" id="opt${choice_index?number+1}">
						<a class="button" style="cursor: pointer;display:block;float:left;" onclick="addSCQItem('${q_index?number+1}')">
							<span class="icon icon-add qItemIcon"></span><span style="float:left;" class="choice">单选选项<#if 0 == choice_index>A<#elseif 1 == choice_index>B<#elseif 2 == choice_index>C<#elseif 3 == choice_index>D<#elseif 4 == choice_index>E<#elseif 5 == choice_index>F<#elseif 6 == choice_index>G<#elseif 7 == choice_index>H</#if></span>
						</a>
						<input class="str1 SCQStr1" type="text" value="${choice.str1}">
						<span class="icon icon-del qItemIcon" onclick="delSCQItem(this)" title="删除本选项"></span><br>
					</div>
				</#list>	
				</div>
			<#elseif "1" == q.qType>
				<div class="Q MCQ qWrapper" id="q${q_index?number+1}" >
					<div class="qStem">
					<span class="icon icon-del qStemIcon" onclick="delQ(this)" title="删除本题"></span>
					<label class="QNum">Q${q_index?number+1}</label>
					<input class="stem MCQStem" value="${q.qStem}" 
						onfocus="stemToggle(this);" onblur="stemToggle(this);" type="text"><br/>
					</div>
				<#list q.items as choice>
					<div class="qItem" id="opt${choice_index?number+1}">
						<a class="button" style="cursor: pointer;display:block;float:left;" onclick="addMCQItem('${q_index?number+1}')">
							<span class="icon icon-add qItemIcon"></span><span style="float:left;" class="choice">多选选项<#if 0 == choice_index>A<#elseif 1 == choice_index>B<#elseif 2 == choice_index>C<#elseif 3 == choice_index>D<#elseif 4 == choice_index>E<#elseif 5 == choice_index>F<#elseif 6 == choice_index>G<#elseif 7 == choice_index>H</#if></span>
						</a>
						<input class="str1 MCQStr1" type="text" value="${choice.str1}">
						<span class="icon icon-del qItemIcon" onclick="delSCQItem(this)" title="删除本选项"></span><br>
					</div>
				</#list>
				</div>	
			<#elseif "2" == q.qType>
				<div class="Q blankQ qWrapper" id="q${q_index?number+1}" >
					<div class="qStem">
						<span class="icon icon-del qStemIcon" onclick="delQ(this)" title="删除本题"></span>
						<label class="QNum">Q${q_index?number+1}</label>
						<input class="stem blankQStem" value="${q.qStem}" 
							onfocus="stemToggle(this);" onblur="stemToggle(this);" type="text"><br/>
					</div>
					<div class="qItem">
						<textarea class="str1 blankQTextarea">${q.items[0].rawData}</textarea>
						<a class="button" style="cursor: pointer" onclick="addBlank(this)">
						<span class="icon icon-add qItemIcon"></span>添加空格</a>
					</div>
					<div style="clear: both; margin-bottom: 20px;"></div>
				</div>
			<#elseif "3" == q.qType>
				<div class="Q mixQ qWrapper" id="q${q_index?number+1}" >
					<div class="qStem">
						<span class="icon icon-del qStemIcon" onclick="delQ(this)" title="删除本题"></span>
						<label class="QNum">Q${q_index?number+1}</label>
						<input class="stem mixQStem" value="${q.qStem}"
							onfocus="stemToggle(this);" onblur="stemToggle(this);" type="text"><br/>
					</div>
					
					<#--mix题目的选择选项计数器-->
					<#assign counter=1>
					<#list q.items as choice>
					<#--只处理选择部分的选项，填空部分是在该if中判断的-->
					<#if choice.gapFillingNum == 0>
						<div class="qItem" id="opt${counter}">
							<a class="button" style="cursor: pointer;display:block;float:left;" onclick="addMixQItem('${q_index?number+1}')">
								<span class="icon icon-add qItemIcon"></span><span style='float:left;' class='choice'>选择题选项<#if 1 == counter>A<#elseif 2 == counter>B<#elseif 3 == counter>C<#elseif 4 == counter>D<#elseif 5 == counter>E<#elseif 6 == counter>F<#elseif 7 == counter>G<#elseif 8 == counter>H</#if></span>
								<#--mix题目的选择选项计数器自增1-->			
								<#assign counter=counter+1 />
							</a>
							<input class="str1 mixQStr1" type="text" value="${choice.str1}">
							<span class="icon icon-del qItemIcon" onclick="delMixQItem(this)" title="删除本选项"></span><br/>
						</div>
						<#--判断选择选项的下一个是否是填空选项（gapFillingNum是1表示是填空）-->
						<#if choice_has_next && q.items[choice_index + 1].gapFillingNum == 1>
							<div class='qItem'>
								<a class='button' style='cursor:hand' onclick='showBlank(this)'><span class='icon icon-add qItemIcon'></span><span style='float:left;'>添加填空</span></a>
								<a class='button none' style='cursor:hand' onclick='delBlank(this)'><span class='icon icon-del qItemIcon'></span>删除填空</a><br>
								<textarea class='str1 fromTextarea blankQTextarea'>${choice.rawData}</textarea>
								<a class='button none' style='cursor:hand ' onclick='addBlank(this)'><span class='icon icon-add qItemIcon'></span>添加空格</a><div style='clear: both;'></div><hr style='margin-top:10px'>
							</div>
						<#else>
							<div class='qItem'>
								<a class='button' style='cursor:hand' onclick='showBlank(this)'><span class='icon icon-add qItemIcon'></span><span style='float:left;'>添加填空</span></a>
								<a class='button none' style='cursor:hand' onclick='delBlank(this)'><span class='icon icon-del qItemIcon'></span>删除填空</a><br>
								<textarea class='none str1 fromTextarea blankQTextarea'></textarea>
								<a class='button none' style='cursor:hand ' onclick='addBlank(this)'><span class='icon icon-add qItemIcon'></span>添加空格</a><div style='clear: both;'></div><hr style='margin-top:10px'>
							</div>
						</#if>
					</#if>
				</#list>	
				</div>
			<#elseif "4" == q.qType>
				<div class="Q matrixQ qWrapper" id="q${q_index?number+1}" >
					<div class="qStem">
						<span class="icon icon-del qStemIcon" onclick="delQ(this)" title="删除本题"></span>
						<label class="QNum">Q${q_index?number+1}</label>
						<input class="stem matrixQStem" value="${q.qStem}"
							onfocus="stemToggle(this);" onblur="stemToggle(this);" type="text"><br/>
						<div class="matrixCalcBox">
							请先设定矩阵大小：矩阵由<input value="" size="2" type="text">行×<input
								value="" size="2" type="text">列组成。<input value="确定"
								onclick="addMatrixQTable(this);" type="button">
						</div>
					</div>
					<div>
						<table class="matrixTable">
							<tbody>
								<#assign firstTime=0/>
								<#list q.subQuestions as subQ>
									<#if 0 == firstTime>
									<tr style="height: 20px;">
										<td class="matrixTd">&nbsp;</td>
										<#list subQ.items as choice>
											<td class="matrixTd"><input class="matrixStr1" value="${choice.str1}"
											type="text"></td>
										</#list>
									</tr>
									<#assign firstTime=1/>
									</#if>
									<tr>
										<td class="matrixTd" style="text-align: left;">
										<input class="stem" value="${subQ.qStem}" type="text"></td>
										<#list subQ.items as choice>
											<td class="matrixTd"><input type="checkbox"></td>
										</#list>
									</tr>
								</#list>
							</tbody>
						</table>
						<div id="tableMargin" style="height:20px;"></div>
					</div>
				</div>
			<#elseif "6" == q.qType>
				<div class="Q paneQ qWrapper">
					<div class="qStem">
						<span class="icon icon-del qStemIcon" onclick="delPane(this)" title="删除板块"></span>
						<label class="QNum"></label>
						<input class="stem paneQStem" value="${q.qStem}"
							onfocus="stemToggle(this);" onblur="stemToggle(this);" type="text"><br/>
					</div>
				</div>
			<#elseif "7" == q.qType>
				<div class="Q hintQ qWrapper">
					<div class="qStem">
						<span class="icon icon-del qStemIcon" onclick="delHint(this)" title="删除提示语句"></span>
						<label class="QNum">提示</label>
						<input class="stem hintQStem" value="${q.qStem}"
							onfocus="stemToggle(this);" onblur="stemToggle(this);" type="text"><br/>
					</div>
				</div>
			</#if>
		</#list>