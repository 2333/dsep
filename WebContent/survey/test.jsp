<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<div id="add_div" class="form">
<form>
	<%-- <jsp:include page="abc.jsp"/> --%>
</form>
</div>
<input type="button" id="test" value="aaaa"/>
<script type="text/javascript">
var guid = "217FF50AB7264D65AA5B6B034AE70FD3";
$.post("${ContextPath}/survey/create/preview/" + guid, function(data) {
	$( "#content" ).empty();
	var navi = "<table><tr><td><a id='questionnaire_home_btn' class='button' href='#' onclick='backHome()'><span class='icon icon-left'></span>返回主页</a></td><td><a id='questionnaire_edit_btn' class='button' href='#' onclick='updateFun2(\""
			+ guid + "\")'><span class='icon icon-edit'></span>编辑问卷</a></td></tr></table>";
	data = navi + data;
	
	var submit = "<div align='center'><a type='submit' id='saveAns' class='button' onclick='saveAnswer()' href='#'>提交</a></div>";
	//data += submit;
	$( "#content" ).append(data);
	$('input[type=submit], a.button , button').button();
	//setQNum();
	jsonData = $("#content :first").val();
	jsonObj = eval(jsonData);
	event.preventDefault();
	test();
  }, 'html');
  

function test() {
	alert(1);
	var answer = [
	{ "itemId":"F4E909E850C4414993F6403D5A5BCB95" , "value":"C" },
	{ "itemId":"1BC36969EB2B443F811FAD7667270A71" , "value":"A" },
	{ "itemId":"1BC36969EB2B443F811FAD7667270A71" , "value":"D" }];
	// 双重each赋值语句
	$.each($(".value"), function(i,val) {
		var $val = $(val);
		if ("radio" == $val.attr("type")) {
			var itemId = $val.attr("itemId");
			// 遍历json的循环
			$(answer).each(function(){
				alert(this.itemId + " " + itemId);
				if (this.itemId == itemId) {
					$val.prop('checked', true);
				}
			}); 
			
		}
		//alert($(val).prop("tagName"));
		//alert();
		/* var $val = $(val);
		alert($val.name);
		//var val=$('input:radio[name="sex"]:checked').val();
	     alert($val.attr("checked")); */
		 //alert(val + " " + i + " " + val.name + " " + val.value);
	     //if ($(val).prop("checked"))
	     //	 alert(val.value);
	     //alert($(val).attr("checked"));
	}); 
	
	
	/* $.ajax({
    	url:url, 
        type: "POST", 
        data: JSON.stringify(x), 
        success: function(data){
                  
        }, 
        dataType: "json",
        contentType: "application/json"
     }); */
}

</script>