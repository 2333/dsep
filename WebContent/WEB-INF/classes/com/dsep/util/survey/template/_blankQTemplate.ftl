<?xml version="1.0" encoding="UTF-8"?>
<question xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="questionsAndItemsSchema.xsd">
	<qId>${q.qId}</qId>
	<qType>${q.qType}</qType>
	<qStem>${q.qStem}</qStem>
	<totalNum>${q.totalNum}</totalNum>
	<minNum>${q.minNum}</minNum>
	<maxNum>${q.maxNum}</maxNum>
	<qItems>
	    <#list q.items as choice>
		<singleItem>
			<itemId>${choice.itemId}</itemId>
			<str1>${choice.str1}</str1>
			<blank>1</blank>
			<#if choice.str2??>
   			<str2>${choice.str2}</str2>
			</#if>
			<selectBoxNum>${choice.selectBoxNum}</selectBoxNum>
			<gapFillingNum>${choice.gapFillingNum}</gapFillingNum>
			<parentQRefId>${choice.parentQRefId}</parentQRefId>
		</singleItem>
		</#list>
	</qItems>
	<necessary>${q.necessary}</necessary>
	<fQRef/>
</question>
