<input id="discId" type="hidden" value="${discId}"/>
<input id="unitId" type="hidden" value="${unitId}"/>
<input id="collectId" type="hidden" value="${collectId}"/>
	
<div id="addExpertInfo_div" class="form">
	<form id="expertInfo_fm" class="fr_form">
<div id="unit_achievement" class="cur" >
 	<table id="unit_achievement_list"></table> 
	<div id="unit_achv_paper"></div> 
</div>
</form>
	</div>
<script type="text/javascript">
var discId = $('input#discId').val();
var unitId = $('input#unitId').val();
var collectId = $('input#collectId').val();
var entityId = 'E20130101';

function getCollectionDataByCollectId(discId, unitId, collectId) {
	$.getJSON("${ContextPath}/evaluation/progress/achvInitGrid/"+collectId, 
			function initJqTable(data) {
				$("#unit_achievement_list").jqGrid({
						url :'${ContextPath}/evaluation/progress/achvCollectionData/'
							+ collectId + '/' + unitId + '/' + discId,		
						editurl :'${ContextPath}/Collect/toCollect/JqOper/collectionEdit/'
							+ entityId + '/' + unitId + '/' + discId,
					datatype : 'json',
					mtype : 'POST',
					colModel : data.colConfigs,
					height : "100%",
					autowidth : false,
					width:window.screen.availWidth*0.87,
					shrinkToFit : true,
					pager : "#unit_achv_paper",
					pgbuttons : true,
					rowNum : 15,
					rowList : [ 15, 20, 25 ],
					viewrecords : true,
					sortname : data.defaultSortCol,
					sortorder : "asc",
					caption : data.name,
					jsonReader : { //jsonReader来跟服务器端返回的数据做对应  
						root : "rows", //包含实际数据的数组  
						page : "pageIndex", //当前页  
						total : "totalPage",//总页数  
						records : "totalCount", //查询出的记录数  
						repeatitems : false,
					},
					loadComplete : function() {
					},
					prmNames : {page : "page",rows : "rows",sort : "sidx",order : "sord"
					}
				}).navGrid('#pager_collect_tb', {
					edit : false,
					add : false,
					del : false,search:false
		});
	});
}

getCollectionDataByCollectId(discId, unitId, collectId);

</script>