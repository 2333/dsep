<?xml version="1.0" encoding="UTF-8"?>
<question xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="questionsAndItemsSchema.xsd">
	<qId>${q.qId}</qId>
	<qType>${q.qType}</qType>
	<qStem>${q.qStem}</qStem>
	
	<#list q.subQuestions as subQ>
		<subQuestion>
			<qId>${subQ.qId}</qId>
			<qType>${subQ.qType}</qType>
			<qStem>${subQ.qStem}</qStem>
			<totalNum>${subQ.totalNum}</totalNum>
			<minNum>${subQ.minNum}</minNum>
			<maxNum>${subQ.maxNum}</maxNum>
			<qItems>
	   			<#list subQ.items as choice>
					<singleItem>
						<itemId>${choice.itemId}</itemId>
						<str1>${choice.str1}</str1>
						<#if choice.str2??>
   						<str2>${choice.str2}</str2>
						</#if>
						<selectBoxNum>${choice.selectBoxNum}</selectBoxNum>
						<#if 0 == choice_index><value>A</value>
						<#elseif 1 == choice_index><value>B</value>
						<#elseif 2 == choice_index><value>C</value>
						<#elseif 3 == choice_index><value>D</value>
						<#elseif 4 == choice_index><value>E</value>
						<#elseif 5 == choice_index><value>F</value>
						<#elseif 6 == choice_index><value>G</value>
						<#else><value>H</value>
						</#if>
						<gapFillingNum>${choice.gapFillingNum}</gapFillingNum>
						<parentQRefId>${choice.parentQRefId}</parentQRefId>
					</singleItem>
				</#list>
			</qItems>
			<necessary>${subQ.necessary}</necessary>
			<fQRef>${subQ.fQRef}</fQRef>
		</subQuestion>
	</#list>
</question>
