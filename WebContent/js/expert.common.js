// 方弘宇维护的专家相关js
// 导航按钮变化，当该页全部打完时，实时显示按钮
function createNextQuestionRouteEle(param) {
	var needDelEle = $(".nextQuestionRoute");
	var targetEle = needDelEle.closest("td");
	var buttonClass = "nextQuestionRoute button ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only";
	var newEle = "";
	if ("null" == param) {
		newEle = $("<a class='"+ buttonClass 
				+ "' href='#'><span class='ui-button-text'><span class='icon icon-del'/>该页未完成</span></a>");
		
	} else {
		newEle = $("<a class='" + buttonClass + "' href='#' onclick='openLink(\"" + param
				+ "\"); return false;'><span class='ui-button-text'><span class='icon icon-right'/>下一项打分</span></a>");
	}
	newEle.appendTo(targetEle);
	needDelEle.remove();	
};

function openHomeLink(url) {
	if (url.trim() == "") return;
	$.post(url, function(data) {
		  $( "#content" ).empty();
		  $( "#content" ).append( data );
	  }, 'html');
}