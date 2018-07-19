$("#download_brief_report_btn").click(function(){
	var unitId = $("#unit_Id").val();
	var discId = $("#disc_Id").val();
	if(unitId==''||discId==''){
		alert_dialog('学校代码和学科代码不能为空！');
	}
	var url = contextPath + '/Collect/toCollect/export/briefsheet/'+unitId+"/"+discId; 
	outputJS(url);
	event.preventDefault();
});
function downloadBriefSheet(unitId, discId){
	var url = contextPath + '/Collect/toCollect/export/briefsheet/'+unitId+"/"+discId; 
	outputJS(url);	
}