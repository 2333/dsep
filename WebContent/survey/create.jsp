<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <head>

<style type="text/css">
.first_block {
	width: 150px;
	min-height: 650px;
	position: absolute;
	background-color: #dfeffc; 
	border:1px solid #aaeffc;
}
.second_block {
	margin-left: 200px;
	margin-right: 20px;
	min-height: 650px;
	border:1px solid #E3E3E3;
	height:650px;
	overflow:auto;
	background-color: #EEEFF1; 
}

.line {
	height: 80px;
}
.line h3 {
	margin: 6px 10px;
	color: #2e6e9e;
}
.table-c table{
	border-right:2px solid  #E3E3E3;
	border-bottom:1px solid  #E3E3E3;
	margin-left:85px;
} 
.table-c table td{
	border-left:2px solid  #E3E3E3;
	border-top:1px solid #E3E3E3;
} 

#paper {
	background-color: #EEEFF1; 
}

#paper_above {
	background-color: #EEEFF1; 
}
#question_content {
	background-color: #EEEFF1; 
	color:#666;
}

.qWrapper {
	background-color: #FFF; 
	padding-right:10px;
	border:1px solid #E3E3E3;
	margin:20px 10px 20px; 
	font-size:16px;
	font-family:"微软雅黑";
}

.qStem {
	margin-top:10px;
	margin-bottom:20px;
}

.qItem {
	margin-bottom:20px;
	margin-left:90px;
}

.qItemIcon {
	width:15px;
	cursor:pointer;
}

.qStemIcon {
	margin-left:5px;
	margin-bottom:5px;
	width:15px;
	cursor:pointer;
}


.QNum {
	margin-left:10px;
	display:block;
	float:left;
	width:35px;
}

.stem {
	font-size:14px;
	font-family:"微软雅黑";
	color:#000;
}

.SCQStem {
	vertical-align:middle;
	width:600px;
	height:30px;
	margin-left:30px;
	display:block;
	float:left;
}

.str1 {
	vertical-align:middle;
	width:420px;
	height:20px;
	margin-left:10px;
	float:left;
}
.SCQStr1 {
}

.MCQStem {
	vertical-align:middle;
	width:600px;
	height:30px;
	margin-left:30px;
}

.MCQStr1 {
}

.surveyTitle {
	vertical-align:middle;
	text-align:center;
	font-size:24px;
	font-family:"楷体","楷体_GB2312",Arial;
	width:860px;
	height:50px;
	text-align:center;
}

.matrixQStem {
	vertical-align:middle;
	width:350px;
	height:30px;
	margin-left:30px;
}

.matrixCalcBox {
	margin-left:85px;
	margin-top:10px;
}
.matrixTable {
	width:300px;
	
	display:block;
	margin-left:85px;
	padding-botton:20px;
}
.matrixTd {
	vertical-align:middle;
	border:1px solid  #E3E3E3;
	text-align:center;
}
.matrixStr1 {
	border:0px;
	text-align:center;
	vertical-align:middle;
	line-height:20px;
}

.blankQStem {
	vertical-align:middle;
	width:600px;
	height:30px;
	margin-left:30px;
}

.blankQTextarea {
	vertical-align:middle;
	width:500px;
	height:100px;
	float:left;
}

.mixQStem {
	vertical-align:middle;
	width:600px;
	height:30px;
	margin-left:30px;
}
.mixQStr1 {
	vertical-align:middle;
	width:500px;
	height:20px;
	margin-left:10px;
}

.hintQStem {
	vertical-align:middle;
	width:600px;
	height:30px;
	margin-left:30px;
}

.paneQStem {
	vertical-align:middle;
	text-align:center;
	font-size:16px;
	font-family:"宋体",Arial;
	font-weight:bold;
	width:600px;
	height:40px;
	display:block;
	margin:0 auto;	
}

.none {
	display:none;
}

.selectStyle {
	border:1px solid #2E6E9E; 
	width:235px;
	padding:0.4em 1em;
	margin-right:1em;
}
</style>

</head>
<input id="guid" type="hidden" value="${guid}"/>
<div id="dialog-confirm" title="提示"></div>
<div class="con_header">
	<table class="left">
		<tr>
			<td >
				<h3>
					<span class="icon icon-user" style="margin-left:10px"></span>
					编辑问卷
				</h3>
			</td>
		</tr>
	</table>
	<a id="save_paper" class="button right" href="#" onclick="savePaper(true)">
		<span class="icon icon-save"></span>保存问卷
	</a>
	<a id="pre_see" class="button right" href="#" onclick="preview()">
		<span class="icon icon-report"></span>预览问卷
	</a>
	<a id="logic" class="button right" href="#" onclick="setLogic()">
		<span class="icon icon-explanation"></span>逻辑设置
	</a>
	<a id="section" class="button right" href="#" onclick="addPane()">
		<span class="icon icon-edit"></span>板块设置
	</a>
	
	<select id="qnrType" style="border:1px solid #2E6E9E; padding:0.4em 1em;margin-right:1em" class="right">
  		<option value ="在校生调查问卷">在校生调查问卷</option>
  		<option value ="毕业生调查问卷">毕业生调查问卷</option>
  		<option value="用人单位调查问卷">用人单位调查问卷</option>
  	</select>
  <span class="right" style="font-weight:bold;color:#2E6E9E;font-size:1.1em;padding:0.4em 1em">问卷类型</span>
</div>

<div class="layout_holder">
	<div class="first_block" >
		<div class="table">
			<table id="add_ques_table"></table>
			<div  align = center style="height:30px;line-height:30px;background-color:#75ABD4;text-align:center" >
				<h3 style="color: white">
					<span></span>基本题型<span id="quetion_type"></span>
				</h3>
			</div>
			<div id="add_question" align =center>
			     <ul style="list-style-type:none;">
			     	<li style="margin-top:10px;">
						<a id="addBt1" class="button" onclick="addPane()">
							<span class="icon icon-edit"></span>板块设置
						</a>
					</li>
					
					<li style="margin-top:10px;">
						<a id="addBt2" class="button" onclick="addHint()">
							<span class="icon icon-explanation"></span>提示语句
						</a>
					</li>
					
			        <li style="margin-top:10px; ">
						<a id="addBt3" class="button" onclick="addSCQ()">
							<span class="icon icon-add"></span>单选题
						</a>
					</li>	
					<li style="margin-top:10px;">
						<a id="addBt4" class="button" onclick="addMCQ()">
							<span class="icon icon-add"></span>多选题 
						</a>
					</li>	
			
					<li style="margin-top:10px;">
						<a id="addBt5" class="button" onclick="addMatrixQ()">
							<span class="icon icon-add"></span>矩阵题
						</a>
					</li>		
					<li style="margin-top:10px;">
						<a id="addBt6" class="button" onclick="addBlankQ()">
							<span class="icon icon-add"></span>填空题
						</a>
					</li>	
					<li style="margin-top:10px;">
						<a id="addBt7" class="button" onclick="addMixQ()">
							<span class="icon icon-add"></span>选择填空
						</a>
					</li>		
					<li style="margin-top:10px;">
						<a id="addBt8" class="button" onclick= "savePaper(true)">
							<span class="icon icon-save"></span>保存问卷
						</a>
					</li>	
		    	</ul>
			</div>
		</div>
	</div>
	<div id="tabset" class="second_block"  >
		<div class="radiobutton_div" >
			<table id="result" style="width:910px;">
			</table>
		</div>
		<div id="paper_above" class="line" align=center>
		<h3>
		<input id="paper_name" type="text" class="surveyTitle"
		 	value="请填写问卷题目" onfocus="stemToggle(this);" onblur="stemToggle(this);"/>
		</h3>
		
		</div>
		<div id="paper" class="line" align=center style="height:160px">
			<div id="myEditor" style="width:800px" align=center >
				<!-- <textarea rows="7" cols="70" id="paper_intro">请在此处填写问卷说明。</textarea> -->
				<script type="text/javascript" >
	    		var editor = new baidu.editor.ui.Editor({initialFrameHeight:120,initialFrameWidth:760,initialContent:"请在此填写问卷说明",autoClearinitialContent :true, zIndex : 1});
	    		editor.render("myEditor");
			</script>
			</div>
		</div>

		<div id="question_content"></div>
		
		<div class="process_dialog">
		<span class="ui-icon ui-icon-info"
			style="float: left; margin-right: .3em;"></span> 正在保存……
		</div>

		<div style="clear: both;"></div>
	</div>
</div>

<script type="text/javascript">
	var num=0;
	function initQNumAndBlankQ() {
		$.each($(".Q"), function(i, val) {
			if ($(val).hasClass("SCQ") 
				|| $(val).hasClass("MCQ") 
				|| $(val).hasClass("matrixQ")) {
				num++;
			} else if ($(val).hasClass("blankQ")) {
				num++;
				var myTextarea = $($(val).find(".blankQTextarea")[0]);
				var str = myTextarea.val().replace(/myChangeLine/g, "\n");
				myTextarea.val(str);
			} else if ($(val).hasClass("mixQ")) {
				num++;
				$.each($(val).find(".blankQTextarea"), function(j, val2) {
					var str = $(val2).val().replace(/myChangeLine/g, "\n");
					$(val2).val(str);
				});
				
			}
		});
	}
	
	
	function myUpdate() {
		var guid = $("#guid").val().trim();
		var getQuestionContentUrl = "${ContextPath}/survey/create/updateGetContent/" + guid;
		var getPaperNameAndIntroUrl = "${ContextPath}/survey/create/updateGetPaperNameAndTitle/" + guid;
		$.post(getQuestionContentUrl, function(data) {
			  $("#question_content").empty();
			  $("#question_content").append(data);
			  $.post(getPaperNameAndIntroUrl, function(paperNameAndIntroData) {
				 var idx = paperNameAndIntroData.indexOf("|||");
				 var paperName = paperNameAndIntroData.substr(0, idx);
				 var paperIntro = paperNameAndIntroData.substr(idx+3);
				 if (paperName.trim() != "")
				 	$("#paper_name").val(paperName);
				 if (paperIntro.trim != "")
				 	editor.setContent(paperIntro);
				 initQNumAndBlankQ();
			  });
			  
		  }, 'html');
		
	};
	
	myUpdate();
	
	$("input[type=submit], a.button, button").button();
	$(".process_dialog").hide();

	var qType = {SCQ : 0, MCQ : 1, blankQ : 2, mixQ : 3, matrixQ : 4, 
			SAQ : 5, paneQ : 6, hintQ : 7, other : 8};
	
	// 将0转变为A，1转变为B
	iToAlph = function (n) {
		if ((n < 0) || (n > 24)) return "";
		return String.fromCharCode(65 + n);
	};
	
	
	var qContent = $("#question_content");
	var del = "<span class='icon icon-del qStemIcon' onclick='delQ(this)' title='删除本题'></span>";
	
	
	/*============单选题SCQ(SingleChoiceQuestion)开始，包含添加题目、添加选项和题目显隐方法=======*/
	// 添加单选题
	function addSCQ(){
		num++;
		// 'Q'class是所有题目都具有的class
		var qWrapper = $("<div class='Q SCQ qWrapper' id='q" + num + "'></div>");
		var qStem = $("<div class='qStem'></div>");
		
		var label = "<label class='QNum'>Q" + num + "<label>";
		// 'stem'class是所有题目题干都具有的class
		var input="<input type='text' class='stem SCQStem' value='" + defaultSCQStem
			+ "' onfocus='stemToggle(this);' onblur='stemToggle(this);'><br>";
		// qStem[0] means convert jQuery Obj to DOM Obj
		qStem[0].innerHTML += del;
		qStem[0].innerHTML += label;
	    qStem[0].innerHTML += input;
	   
	    qWrapper.append(qStem);
	    
	    for (var i = 0; i < 2; i++) {
	    	var qItem = $("<div class='qItem' id='opt" + (i + 1) + "'></div>");
	    	// 点击选项加号添加选项
	        var add = "<a class='button' style='cursor:pointer;display:block;float:left;' onclick=addSCQItem('"+ num 
	        		+ "')><span class='icon icon-add qItemIcon'></span><span style='float:left;' class='choice'>单选选项" + iToAlph(i) 
	        		+ "</span></a>";
	        // 'str1'class是所有题目选项都具有的class
	        var input2 = "<input type='text' class='str1 SCQStr1'>";
	        var del2 = "<span class='icon icon-del qItemIcon' onclick='delSCQItem(this)' title='删除本选项'></span><br>";
	        qItem[0].innerHTML += add;
	        qItem[0].innerHTML += input2;
	        qItem[0].innerHTML += del2;
	    	qWrapper.append(qItem[0]);
	    }
	    qContent.append(qWrapper);
	    var lct = document.getElementById('tabset');
	    lct.scrollTop = Math.max(0, lct.scrollHeight - lct.offsetHeight);
	};
	
	// 添加单选选项
	function addSCQItem(n)
	{  
		var SCQList = document.getElementById("q" + n);
		var len = SCQList.getElementsByTagName("div").length ;
		
		var qItem = $("<div class='qItem'></div>");
    	qItem.attr("id", "opt" + len);
    	// 点击选项加号添加选项
        var add = "<a class='button' style='cursor:pointer;display:block;float:left;' onclick=addSCQItem('"+ n 
        		+ "')><span class='icon icon-add qItemIcon'></span><span style='float:left;' class='choice'>单选选项" + iToAlph(len - 1) + "</span></a>";
        // 'str1'class是所有题目选项都具有的class
        var input2 = "<input type='text' class='str1 SCQStr1'>";
        var del = "<span class='icon icon-del qItemIcon' onclick='delSCQItem(this)'></span><br>";
        qItem[0].innerHTML += add;
        qItem[0].innerHTML += input2;
        qItem[0].innerHTML += del;
		SCQList.appendChild(qItem[0]);
	}
	
	function delSCQItem(that) {
		$that = $(that);
		currentQ = $that.parent().parent();
		$that.parent().remove();
		
		$.each(currentQ.find(".qItem"), function(i, val) {
			var choiceStr = "单选选项" + iToAlph(i);
			$(val).find(".choice").html(choiceStr);
		});
	}
	/*============单选题SCQ(SingleChoiceQuestion)结束，包含添加题目、添加选项和题目显隐方法=======*/
	
	/*============多选题MCQ(MultiChoiceQuestion)开始，包含添加题目、添加选项和题目显隐方法========*/
	// 添加多选题
	function addMCQ()
	{
		num++;
		// 'Q'class是所有题目都具有的class
		var qWrapper = $("<div class='Q MCQ qWrapper' id='q" + num + "'></div>");
		
		var qStem = $("<div class='qStem'></div>");
		var label = "<label class='QNum'>Q" + num + "<label>";
		// 'stem'class是所有题目题干都具有的class
		var input="<input type='text' class='stem MCQStem' value='" + defaultMCQStem
			+ "' onfocus='stemToggle(this);' onblur='stemToggle(this);'><br>";
		// qStem[0] means convert jQuery Obj to DOM Obj
		qStem[0].innerHTML += del;
		qStem[0].innerHTML += label;
	    qStem[0].innerHTML += input;
	    qWrapper.append(qStem);
	    
	    for (var i = 0; i < 2; i++) {
	    	var qItem = $("<div class='qItem' id='opt" + (i + 1) + "'></div>");
	    	// 点击选项加号添加选项
	        var add = "<a class='button' style='cursor:pointer;display:block;float:left;' onclick=addMCQItem('"+ num 
	        		+ "')><span class='icon icon-add qItemIcon'></span><span style='float:left;' class='choice'>多选选项" 
	        		+ iToAlph(i) + "</a>";
       		// 'str1'class是所有题目选项都具有的class
   	        var input2 = "<input type='text' class='str1 MCQStr1'>";
   	        var del2 = "<span class='icon icon-del qItemIcon' onclick='delMCQItem(this)' title='删除本选项'></span><br>";
   	        
	        qItem[0].innerHTML += add;
	        qItem[0].innerHTML += input2;
	        qItem[0].innerHTML += del2;
	    	qWrapper.append(qItem[0]);
	    }
	    qContent.append(qWrapper);
	    var lct = document.getElementById('tabset');
	    lct.scrollTop = Math.max(0, lct.scrollHeight - lct.offsetHeight);
	};
	
	// 添加多选选项
	function addMCQItem(n)
	{  
		var MCQList = document.getElementById("q" + n);
		var len = MCQList.getElementsByTagName("div").length ;
		
		var qItem = $("<div class='qItem'></div>");
    	qItem.attr("id", "opt" + len);
	   
    	// 点击选项加号添加选项
     	var add = "<a class='button' style='cursor:pointer;display:block;float:left;' onclick=addMCQItem('"+ n 
      		+ "')><span class='icon icon-add qItemIcon'></span><span style='float:left;' class='choice'>多选选项" 
      		+ iToAlph(len - 1) + "</a>";
		var input2 = "<input type='text' class='str1 MCQStr1'/>";
		var del2 = "<span class='icon icon-del qItemIcon' onclick='delMCQItem(this)' title='删除本选项'></span><br>";
        
		qItem[0].innerHTML += add;
	    qItem[0].innerHTML += input2;
	    qItem[0].innerHTML += del2;
		MCQList.appendChild(qItem[0]);
	}
	
	function delMCQItem(that) {
		$that = $(that);
		currentQ = $that.parent().parent();
		$that.parent().remove();
		
		$.each(currentQ.find(".qItem"), function(i, val) {
			var choiceStr = "单选选项" + iToAlph(i);
			$(val).find(".choice").html(choiceStr);
		});
	}
	/*============多选题MCQ(MultiChoiceQuestion)结束，包含添加题目、添加选项和题目显隐方法========*/
	
	/*============矩阵题matrixQ(matrixQuestion)开始，包含添加题目、添加选项和题目显隐方法=========*/
	function addMatrixQ(){
		num++;
		// 'Q'class是所有题目都具有的class
		var qWrapper = $("<div class='Q matrixQ qWrapper' id='q" + num + "'></div>");
		
		var qStem = $("<div class='qStem'></div>");
		var label = "<label class='QNum'>Q" + num + "<label>";
		
		// 'stem'class是所有题目题干都具有的class
		var input="<input type='text' class='stem matrixQStem' value='" + defaultMatrixQStem
			+ "' onfocus='stemToggle(this);' onblur='stemToggle(this);'><br>";
		qStem[0].innerHTML += del;
		qStem[0].innerHTML += label;
	    qStem[0].innerHTML += input;
	    
		// 'stem'class是所有题目题干都具有的class
		var calcBox = "<div class='matrixCalcBox'>请先设定矩阵大小：矩阵由<input type='text' value='1' size='2'>行×<input type='text' value='1' size='2'>列组成。" + 
		"<input type='button' value='确定' onclick='addMatrixQTable(this);'></div>";
		// qStem[0] means convert jQuery Obj to DOM Obj
		qStem[0].innerHTML += calcBox;
	    
	    qWrapper.append(qStem);
		
	    qContent.append(qWrapper);
	    var lct = document.getElementById('tabset');
	    lct.scrollTop = Math.max(0, lct.scrollHeight - lct.offsetHeight);
	};
	
	addMatrixQTable = function (that) {
		$that = $(that);
		var matrixCalcBox = $that.closest(".matrixCalcBox");
		// 每次新建表格时remove上一次的表格
		matrixCalcBox.parent().next().remove();
		
		// 获得表格大小的参数
		var inputs = matrixCalcBox.find("input");
		var col = parseInt($(inputs[0]).val());
		var row = parseInt($(inputs[1]).val());

		var qWrapper = $that.closest(".qWrapper");
		var tableWrapper = document.createElement("div");
	 	
		var table="<table class='matrixTable'><tbody>";
		// col+1和row+1是因为第一行是优良中差等中文选项，第一列是中文题目
		//alert((col+1) +" "+ (row+1) );
		for (var i = 1; i <= (col + 1); i++) {
			for (var j = 1; j <= (row + 1); j++) {
				// 第一行是优良中差等中文选项
				if (i == 1) {
					// 第一列要加上<tr>
					if (j == 1) {
						table+="<tr style='height:20px;'><td class='matrixTd' >&nbsp;</td>";
					} else if (j == (row + 1)) {
						// 最后一列要加上</tr>
						table+="<td class='matrixTd'><input type='text' class='matrixStr1 matrixQSubOption' value='选项" 
						+  "' onfocus='stemToggle(this);' onblur='stemToggle(this);'></td></tr>";
					} else {
						table+="<td class='matrixTd'><input type='text' class='matrixStr1 matrixQSubOption' value='选项"
						+ "' onfocus='stemToggle(this);' onblur='stemToggle(this);'></td>";
					};
				} else {
					if (j == 1) {
						table+="<td class='matrixTd' style='text-align:left;'><input type='text' class='stem matrixQSubStem' value='请填写子题目" 
						+ "' onfocus='stemToggle(this);' onblur='stemToggle(this);'></td>";
					} else if (j == (row + 1)) {
						table+="<td class='matrixTd'><input type='checkbox'></td></tr>";
					} else {
						table+="<td class='matrixTd'><input type='checkbox'></td>";
					};
				};
			};
		}
	 	table+="</tbody></table>";
	
	 	var tableMargin = "<div id='tableMargin' style='height:20px;'></div>";
	 	tableWrapper.innerHTML += table;
	 	tableWrapper.innerHTML += tableMargin;
	  	qWrapper.append(tableWrapper);
	   
	    var lct = document.getElementById('tabset');
	    lct.scrollTop = Math.max(0, lct.scrollHeight - lct.offsetHeight);
	};
	/*============矩阵题matrixQ(matrixQuestion)结束，包含添加题目、添加选项和题目显隐方法=========*/
	
	/*============填空题blankQ(blankQuestion开始，包含添加题目、验证是否存在填空空格方法===========*/
	function addBlankQ(){
		num++;
		var qWrapper = $("<div class='Q blankQ qWrapper' id='q" + num + "'></div>");

		var qStem = $("<div class='qStem'></div>");
		var label = "<label class='QNum'>Q" + num + "<label>";
		// 'stem'class是所有题目题干都具有的class
		var input="<input type='text' class='stem blankQStem' value='" + defaultBlankQStem 
			+ "' onfocus='stemToggle(this);' onblur='stemToggle(this);'><br>";
		qStem[0].innerHTML += del;
		qStem[0].innerHTML += label;
		qStem[0].innerHTML += input;
		qWrapper.append(qStem);
		
	    var qItem = $("<div class='qItem'></div>");
	    var input1 = "<textarea class='str1 blankQTextarea'>";
	    var a2 = "<a class='button' style='cursor:pointer' onclick='addBlank(this)'><span class='icon icon-add qItemIcon'></span>添加空格</a>";
	    qItem[0].innerHTML += input1;
	    qItem[0].innerHTML += a2;
		
	    qWrapper.append(qItem);
	    
	    var clearBothDiv = document.createElement("div");
	    clearBothDiv.setAttribute("style", "clear:both;margin-bottom:20px;"); 
	    qWrapper.append(clearBothDiv);
	    qContent.append(qWrapper);
	    //qContent.append(clearBothDiv);
		var lct = document.getElementById('tabset');
		lct.scrollTop=Math.max(0,lct.scrollHeight-lct.offsetHeight);
	};		
	
	var currentCaretPosition = 0;
	function addBlank(that) {
		$that = $(that);
		$textarea = $that.prev();
		var caretPos = $textarea[0].selectionStart;
	    var textAreaTxt = $textarea.val();
	    var txtToAdd = "___";
	    currentCaretPosition= caretPos + 3;
	    $textarea.val(textAreaTxt.substring(0, caretPos) + txtToAdd + textAreaTxt.substring(caretPos) );
	    setCaretPosition($textarea[0]);
	}
	
	function setCaretPosition(ctrl){//设置光标位置函数
		if(ctrl.setSelectionRange) {
			ctrl.focus();
		 	ctrl.setSelectionRange(currentCaretPosition,currentCaretPosition);
		} else if (ctrl.createTextRange) {
		    var range = ctrl.createTextRange();
		    range.collapse(true);
		    range.moveEnd('character', currentCaretPosition);
		    range.moveStart('character', currentCaretPosition);
		    range.select();
		 }
	}
	
	// 填空题没有填空，就不符合规则
	function validateBlankQs() {
		$.each($(".blankQ"), function(i, val) {
			$.each($(val).find(".str1"), function(j, val2) {
				// 如果某道填空题的选项中不含有填空(即'_')，则返回该题目的名称
				if ($(val2).val().indexOf("_") == -1) {
					return $(val).find('.blankQStem').val(); 					
				}
			});
		});
		$.each($(".mixQ"), function(i, val) {
			$.each($(val).find(".str1"), function(j, val2) {
				// 如果某道填空题的选项中不含有填空(即'_')，则返回该题目的名称
				if ($(val2).val().indexOf("_") == -1) {
					return $(val).find('.mixQStem').val(); 					
				}
			});
		});
		// 如果全部验证通过，返回true
		return true;
	}
	/*============填空题blankQ(blankQuestion结束，包含添加题目、验证是否存在填空空格方法===========*/
	
	/*============选择填空题mixQ(mixQuestion开始，包含添加题目、添加选项和题目显隐方法=============*/
	//填空选择题
    function addMixQ()
    {
    	num++;
		var qContent = $("#question_content");
		// 'Q'class是所有题目都具有的class
		var qWrapper = $("<div class='Q mixQ qWrapper' id='q" + num + "'></div>");
		
		
		var qStem = $("<div class='qStem'></div>");
		var label = "<label class='QNum'>Q" + num + "<label>";
		// 'stem'class是所有题目题干都具有的class
		var input="<input type='text' class='stem mixQStem' value='" + defaultMixQStem
			+ "' onfocus='stemToggle(this);' onblur='stemToggle(this);'><br>";
		// qStem[0] means convert jQuery Obj to DOM Obj
		qStem[0].innerHTML += del;
		qStem[0].innerHTML += label;
	    qStem[0].innerHTML += input;
	    qWrapper.append(qStem);
	    
	    for (var i = 0; i < 2; i++) {
	    	// 选择选项Item
	    	var qItem = $("<div class='qItem' id='opt" + (i + 1) + "'></div>");
	    	// 点击选项加号添加选项
	        var add = "<a class='button' style='cursor:pointer;display:block;float:left;' onclick=addMixQItem('"+ num 
	        		+ "')><span class='icon icon-add qItemIcon'></span><span style='float:left;' class='choice'>选择题选项" 
	        		+ iToAlph(i) + "</span></a>";
	     	// 'str1'class是所有题目选项都具有的class
	        var input2 = "<input type='text' class='str1 mixQStr1'>";
	        var del2 = "<span class='icon icon-del qItemIcon' onclick='delMixQItem(this)' title='删除本选项'></span><br>";
	        qItem[0].innerHTML += add;
	        qItem[0].innerHTML += input2;
	        qItem[0].innerHTML += del2;
	        qWrapper.append(qItem);
	        
	        var qItem2 = $("<div class='qItem'></div>");
	        var addBlankBox = "<a class='button' style='cursor:hand' onclick='showBlank(this)'><span class='icon icon-add qItemIcon'></span><span style='float:left;'>添加填空</span></a>";
	        var delBlankBox = "<a class='button none' style='cursor:hand' onclick='delBlank(this)'><span class='icon icon-del qItemIcon'></span>删除填空</a><br>";
	        qItem2[0].innerHTML += addBlankBox;
	        qItem2[0].innerHTML += delBlankBox;
	        var blankBox = "<textarea class='none str1 fromTextarea blankQTextarea'>";
	        var addBlank = "<a class='button none' style='cursor:hand ' onclick='addBlank(this)'><span class='icon icon-add qItemIcon'></span>添加空格</a><div style='clear: both;'></div><hr style='margin-top:10px'>";
	        qItem2[0].innerHTML += blankBox;
	        qItem2[0].innerHTML += addBlank;
	        qWrapper.append(qItem2);
	    }
	    qContent.append(qWrapper);
	    var lct = document.getElementById('tabset');
	    lct.scrollTop=Math.max(0,lct.scrollHeight-lct.offsetHeight);   			
    }
	
	function addMixQItem(n)
	{  
		var SCQList = document.getElementById("q" + n);
		var len = SCQList.getElementsByTagName("div").length ;
		len=(len+2)/3;
		var qItem = $("<div class='qItem' id='opt" + len + "'></div>");
	   
	    var add = "<a class='button' style='cursor:pointer;display:block;float:left;' onclick=addMixQItem('"+ n 
      		+ "')><span class='icon icon-add qItemIcon'></span><span style='float:left;' class='choice'>选择题选项" 
      		+ iToAlph(len-1) + "</span></a>";
				
		var input2 = "<input type='text' class='str1 mixQStr1'/>";
		var del2 = "<span class='icon icon-del qItemIcon' onclick='delMixQItem(this)' title='删除本选项'></span><br>";
		qItem[0].innerHTML += add;
		qItem[0].innerHTML += input2;
		qItem[0].innerHTML += del2;
		SCQList.appendChild(qItem[0]);
        
		var qItem2 = $("<div class='qItem'></div>");
        var addBlankBox = "<a class='button' style='cursor:hand' onclick='showBlank(this)'><span class='icon icon-add qItemIcon'></span><span style='float:left;'>添加填空</span></a>";
        var delBlankBox = "<a class='button none' style='cursor:hand' onclick='delBlank(this)'><span class='icon icon-del qItemIcon'></span>删除填空</a><br>";
        qItem2[0].innerHTML += addBlankBox;
        qItem2[0].innerHTML += delBlankBox;
        var blankBox = "<textarea class='none str1 fromTextarea blankQTextarea'>";
        var addBlank = "<a class='button none' style='cursor:hand ' onclick='addBlank(this)'><span class='icon icon-add qItemIcon'></span>添加空格</a><div style='clear: both;'></div><hr style='margin-top:10px'>";
        qItem2[0].innerHTML += blankBox;
        qItem2[0].innerHTML += addBlank;
		SCQList.appendChild(qItem2[0]);
	}
		
	function showBlank(that){
		$that = $(that);
		$that.next().removeClass("none");
		$that.next().next().next().removeClass("none");
		$that.next().next().next().next().removeClass("none");
	}
	
	function delBlank(that){
		$that = $(that);
		$that.addClass("none");
		$that.next().next().addClass("none");
		$that.next().next().next().addClass("none");
	}
	
	function delMixQItem(that) {
		$that = $(that);
		currentQ = $that.parent().parent();
		// remove掉填空
		$that.parent().next().remove();
		// remove掉选项
		$that.parent().remove();
	 	var itemNumCounter = 0;
		$.each(currentQ.find(".qItem"), function(i, val) {
			var choice = $(val).find(".choice");
			//alert(choice.length);
			if (choice.length) {
				var choiceStr = "选择题选项" + iToAlph(itemNumCounter++);
				choice.html(choiceStr);
			};
		});
	}
	/*============选择填空题mixQ(mixQuestion结束，包含添加题目、添加选项和题目显隐方法=============*/
	
	/*============提示语句Hint开始，包含添加Hint===========================================*/	
	function addHint(){
		var qWrapper = $("<div class='Q hintQ qWrapper'></div>");
		var qStem = $("<div class='qStem'></div>");
		var label = "<label class='QNum'>提示</label>";
	    var input="<input type='text' class='stem hintQStem' value='" + defaultHintQStem
		+ "' onfocus='stemToggle(this);' onblur='stemToggle(this);'><br>";
		var delHint = "<span class='icon icon-del qStemIcon' onclick='delHint(this)' title='删除提示语句'></span>";
		qStem[0].innerHTML += delHint;
		qStem[0].innerHTML += label;
		qStem[0].innerHTML += input;
	    qWrapper.append(qStem);
	    qContent.append(qWrapper);
		
		var lct = document.getElementById('tabset');
		lct.scrollTop=Math.max(0,lct.scrollHeight-lct.offsetHeight);
	};
	/*============提示语句Hint结束，包含添加Hint===========================================*/	

	/*============板块Pane开始，包含添加Pane===========================================*/	
	function addPane(){
		var qWrapper = $("<div class='Q paneQ qWrapper'></div>");
		var qStem = $("<div class='qStem'></div>");
	    var input="<input type='text' class='stem paneQStem' value='" + defaultPaneQStem
		+ "' onfocus='stemToggle(this);' onblur='stemToggle(this);'><br>";
		var delPane = "<span class='icon icon-del qStemIcon' onclick='delPane(this)' title='删除板块'></span>";
		qStem[0].innerHTML += delPane;
	    qStem[0].innerHTML += input;
	    qWrapper.append(qStem);
	    qContent.append(qWrapper);
		
		var lct = document.getElementById('tabset');
		lct.scrollTop=Math.max(0,lct.scrollHeight-lct.offsetHeight);
	}
	/*============板块Pane结束，包含添加Pane===========================================*/	
			
	
	function delQ(that) {
		// 全局变量num
		num -= 1;
		// 删除当前题目Div
		$that = $(that).parent().parent().remove();
		// 题号计数器
		var qNum = 1;
		$.each($(".Q"), function(i, val) {
			if ($(val).hasClass("SCQ") || $(val).hasClass("MCQ") 
					|| $(val).hasClass("matrixQ") || $(val).hasClass("blankQ") 
					|| $(val).hasClass("mixQ")) {
				// 重新设置题目的序号
				$(val).find(".QNum").html("Q" + qNum);
				qNum += 1;
			}
		});
	}
	
	function delPane(that) {
		// 删除当前PaneDiv
		$that = $(that).parent().parent().remove();
	}
	
	function delHint(that) {
		// 删除当前HintDiv
		$that = $(that).parent().parent().remove();
	} 
	
	// ==================设置逻辑开始=============================
	var logicArr = [];
		
	function setLogic() {
		setLogicDialog("${ContextPath}/survey/create/setLogicDialog");
	}
	
	// 对话框
	function setLogicDialog(url) {
		$.post(url, function(data) {
			$('#dialog').empty();
			$("#dialog").append(data);
			initSelect1Option();
			$('#dialog').dialog({
				title : "逻辑设置",
				autoheight : true,
				width : '1000',
				position : 'center',
				modal : true,
				draggable : true,
				hide : 'fade',
				show : 'fade',
				autoOpen : true,
				buttons : {
					"保存" : function() {
						var selects = $('#selectsAnchor').find(".selects");
						$.each(selects, function(i, val) {
							var s1 = $(val).find("select")[0];
							var s2 = $(val).find("select")[1];
							var s3 = $(val).find("select")[2];
							var ele = {"qId1" : $(s1).find("option:selected").val(), 
								       "item1": $(s2).find("option:selected").text(), 
								       "qId2" : $(s3).find("option:selected").val()};
							logicArr.push(ele);
							
						});
						$("#dialog").dialog("close");
						$('#dialog').empty();
					}
				},
			});
		}, 'html');
	};
	
	function addLogicSelects(that) {
		$("#selectsAnchor").append("<br/><div class='selects'>如果题目<select class='selectStyle s1' onchange='initSelect2Option()'></select>选择了<select class='selectStyle s2' onchange='initSelect3Option()'></select>，那么跳转到<select class='selectStyle s3' onchange='lockSelects()'></select></div>");
		initSelect1Option();
	}

	function initSelect1Option() {
		$("#addLogicSelects").hide();
		var s1 = $("#selectsAnchor").find(".selects").last().find(".s1");
		s1.empty();
		s1.append("<option></option>");
		var qArr = $(".Q");
		// 遍历题目数组
		$.each(qArr, function(i, val) {
			// 获得题目的题干，因为题干有且只有一个，stem数组取[0]
			qStem = $($(val).find(".stem")[0]).val();
			qDivId =$(val).attr('id');
			if ($(val).hasClass("SCQ") || $(val).hasClass("MCQ")) {
				s1.append("<option value='" + qDivId + "'> " + qDivId + " " + qStem + "</option>");
			}
		});
	}
	
	function initSelect2Option() {
		var s1 = $("#selectsAnchor").find(".selects").last().find(".s1");
		var s2 = $("#selectsAnchor").find(".selects").last().find(".s2");
		var qId = s1.find("option:selected").val();
		str1Arr = $("#" + qId).find(".str1");
		s2.empty();
		s2.append("<option></option>");
		$.each(str1Arr, function(i, val) {
			// 把所有选项放到选项数组中
			s2.append("<option value=''> " + $(val).val() + "</option>");
		});
	}
	
	function initSelect3Option() {
		var s1 = $("#selectsAnchor").find(".selects").last().find(".s1");
		// 将qn字符串转变为数字n
		var s1QId = parseInt(s1.find("option:selected").val().substring(1));
		
		var s3 = $("#selectsAnchor").find(".selects").last().find(".s3");
		s3.append("<option></option>");
		var qArr = $(".Q");
		var noOptionAppended = true;
		// 遍历题目数组
		$.each(qArr, function(i, val) {
			if ($(val).hasClass("hintQ") || $(val).hasClass("paneQ")) {
			} else {
				// 获得题目的题干，因为题干有且只有一个，stem数组取[0]
				qStem = $($(val).find(".stem")[0]).val();
				qDivId = parseInt($(val).attr('id').substring(1));
				if (qDivId > s1QId) {
					s3.append("<option value='q" + qDivId + "'> q" + qDivId + " " + qStem + "</option>");
					noOptionAppended = false;
				}
			}
		});
		if (noOptionAppended) {
			s3.append("<option>结束</option>");
		}
	}
	
	function lockSelects() {
		var s1 = $("#selectsAnchor").find(".selects").last().find(".s1");
		var s2 = $("#selectsAnchor").find(".selects").last().find(".s2");
		var s3 = $("#selectsAnchor").find(".selects").last().find(".s3");
		s1.attr("disabled","disabled");
		s2.attr("disabled","disabled");
		s3.attr("disabled","disabled");
		$("#addLogicSelects").show();
	}
	
	// ==================设置逻辑结束=============================
			
	/*============获取编辑的题目的参数=====================================================*/	
	var jSONArr = [];

	// save和preivew都会执行保存动作
	savePaper = function(fromSaveActionNotPreviewAction) {
		validateBlankQs();
		var guid = $("#guid").val().trim();
		var paperName = $("#paper_name").val().trim();
		var paperIntro = editor.getContent();
		
		// 获得所有题目数组
		var qArr = $(".Q");
		var qStem = "", qDivId = "", str1Arr = [], q = {};
		// 遍历题目数组
		$.each(qArr, function(i, val) {
			// 获得题目的题干，因为题干有且只有一个，stem数组取[0]
			qStem = $($(val).find(".stem")[0]).val();
			qDivId =$(val).attr('id');
			if ($(val).hasClass("SCQ")) {
				str1Vals = [];
				// 获得某个题目的选项
				str1Arr = $(val).find(".str1");
				$.each(str1Arr, function(j, val2) {
					// 把所有选项放到选项数组中
					str1Vals.push($(val2).val());
				});
				// 构造了一道题目
				q = {"qType" : qType.SCQ, "qStem" : qStem, "qSequ" : qDivId,
					 "itemNum" : str1Arr.length, "str1Vals" : str1Vals, "matrixEnd" : "",};
				jSONArr.push(q);
			} else if($(val).hasClass("MCQ")){
				str1Vals = [];
				// 获得某个题目的选项
				str1Arr = $(val).find(".str1");
				$.each(str1Arr, function(j, val2) {
					// 把所有选项放到选项数组中
					str1Vals.push($(val2).val());
				});
				q = {"qType" : qType.MCQ, "qStem" : qStem, "qSequ" : qDivId,
					 "itemNum" : str1Arr.length, "str1Vals" : str1Vals, "matrixEnd" : ""};
				jSONArr.push(q);
			} else if ($(val).hasClass("blankQ")) {
				str1Vals = [];
				// 获得某个题目的选项
				str1Arr = $(val).find(".str1");
				$.each(str1Arr, function(j, val2) {
					// 把所有选项放到选项数组中
					str1Vals.push($(val2).val());
				});
				q = {"qType" : qType.blankQ, "qStem" : qStem, "qSequ" : qDivId, "itemNum" : str1Arr.length, 
					 "str1Vals" : str1Vals, "matrixEnd" : ""};
				jSONArr.push(q);
			} else if ($(val).hasClass("matrixQ")) {
				q = {"qType" : qType.matrixQ, "qStem" : qStem, "qSequ" : qDivId, "itemNum" : 0, 
					 "str1Vals" : [], "matrixEnd" : ""};
				jSONArr.push(q);
				
				var firstTr = true;
				var str1Vals = [];
				$.each($(val).find("table tr"), function(j, val2) {
					if (firstTr) {
						$.each($(val2).find('input'), function (k, val3) {
							//alert($(val3).val());
							str1Vals.push($(val3).val());
						});
						firstTr = false;
					} else {
						qStem = $(val2).find(".stem").val();
						//alert(qStem);
						//alert(str1Vals.length);
						q = {"qType" : qType.matrixQ, "qStem" : qStem, "qSequ" : "", "itemNum" : str1Vals.length, 
							 "str1Vals" : str1Vals, "matrixEnd" : "",};
						jSONArr.push(q);
					}
				});
				// 矩阵题目的最后一个小题要加上matrixEnd:end表明这道矩阵题结束了
				jSONArr[jSONArr.length - 1].matrixEnd = "end";
			} else if($(val).hasClass("mixQ")) {
				str1Vals = [];
				// 获得某个题目的选项
				str1Arr = $(val).find(".str1");
				$.each(str1Arr, function(j, val2) {
					if ($(val2).val().trim() != "") {
						if ($(val2).hasClass("fromTextarea")) {
							// 标记该val是某个选项后面的填空
							str1Vals.push("fromTextarea" + $(val2).val());
						} else {
							// 把所有选项放到选项数组中
							str1Vals.push($(val2).val());	
						}
					}
				});
			 
				q = {"qType" : qType.mixQ, "qStem" : qStem, "qSequ" : qDivId, "itemNum" : str1Arr.length, 
					 "str1Vals" : str1Vals, "matrixEnd" : "",};
				jSONArr.push(q);
			} else if($(val).hasClass("paneQ")) {
				q = {"qType" : qType.paneQ, "qStem" : qStem, "qSequ" : qDivId, "itemNum" : 0, 
					 "str1Vals" : [], "matrixEnd" : "",};
				jSONArr.push(q);
			} else if($(val).hasClass("hintQ")) {
				q = {"qType" : qType.hintQ, "qStem" : qStem, "qSequ" : qDivId, "itemNum" : 0, 
						 "str1Vals" : [], "matrixEnd" : "",};
				jSONArr.push(q);
				};
		});
		
		// 最后一个Q封装了问卷GUID，问卷名，问卷简介(qStem等字段题文不对，为了方便)
		//var lastQ = {"qType" : qType.other, "qStem" : paperName, "qSequ" : paperIntro, "itemNum" : 0,
		//		"str1Vals" : [], "matrixEnd" : "",};
		//jSONArr.push(lastQ);
		
		// 如果没有指明题目，显然不合理
		if (jSONArr.length < 1) {
			alert_dialog("请编辑至少一道题目");
		} else {
			$(".process_dialog").show();
			$('.process_dialog').dialog({
				position : 'center',
				modal : true,
				autoOpen : true,
			});
			var qnrType = $("#qnrType").find("option:selected").text();
			var postData = {"qInfo" : jSONArr, "paperName" : paperName, 
					"paperIntro" : paperIntro, "logicArr" : logicArr, "type" : qnrType};
			$.ajax({ 
		        type : "POST", 
		        url : "${ContextPath}/survey/create/save/" + guid, 
		        dataType : "json",      
		        contentType : "application/json",               
		        data : JSON.stringify(postData), 
		        success: function(data) {
		        	postData = {};
		        	jSONArr = [];
		        	$(".process_dialog").dialog("destroy");
					$(".process_dialog").hide();
					if (data) {
						//alert(data);
						if (fromSaveActionNotPreviewAction) {
							alert_dialog("问卷已经存储");	
						} else {
							showPreviewQNR();
						}
					} else {
						alert_dialog("问卷存储失败，请联系工作人员");	
					}
		        }
		     });
		}
	};
	
	
	function preview() {
		//清空警告框内文本并添加新的警告文本
	    $( "#dialog-confirm" ).empty().append("<p>预览问卷系统会先保存问卷</p>");
	    $( "#dialog-confirm" ).dialog({
 	        height:150,
 	        buttons: {
	            "确定": function() {
	            	$(this).dialog("close");
	            	savePaper(false);
	   	        },
	        	"取消": function() {
	           		 $(this).dialog("close");
	          	}
        	}
  	  });
	};
	
	function showPreviewQNR() {
		var guid = $("#guid").val().trim();
		/* var getQuestionContentUrl = "${ContextPath}/survey/create/preview/" + guid;
		var getPaperNameAndIntroUrl = "${ContextPath}/survey/create/previewGetPaperNameAndTitle/" + guid;
		$.post(getQuestionContentUrl, function(data) {
			  $("#question_content").empty();
			  $("#question_content").append(data);
			  $.post(getPaperNameAndIntroUrl, function(paperNameAndIntroData) {
				 var idx = paperNameAndIntroData.indexOf("|||");
				 var paperName = paperNameAndIntroData.substr(0, idx);
				 var paperIntro = paperNameAndIntroData.substr(idx+3);
				 $("#paper_name").val(paperName);
				 editor.getContent(paperIntro);
			  });
			  
		  }, 'html'); */
		$.post("${ContextPath}/survey/create/preview/" + guid, function(data) {
			$( "#content" ).empty();
			var navi = "<table><tr><td><a id='questionnaire_home_btn' class='button' href='#' onclick='backHome()'><span class='icon icon-left'></span>返回主页</a></td><td><a id='questionnaire_edit_btn' class='button' href='#' onclick='updateFun2(\""
					+ guid + "\")'><span class='icon icon-edit'></span>编辑问卷</a></td></tr></table>";
			data = navi + data;
			
			var submit = "<div align='center'><a type='submit' id='saveAns' class='button' onclick='saveAnswer()' href='#'>提交</a></div>";
			//data += submit;
			$( "#content" ).append(data);
			$('input[type=submit], a.button , button').button();
			setQNum();
			jsonData = $("#content :first").val();
			jsonObj = eval(jsonData);
			event.preventDefault();
		  }, 'html');
	}
	function updateFun2(guid) {
		$.post("${ContextPath}/survey/create/update/" + guid, function(data) {
			 $( "#content" ).empty();
			  $( "#content" ).append( data );
		 	  }, 'html');
			event.preventDefault();  
	}
	
	function backHome() {
		$.post("${ContextPath}/survey/home", function(data) {
			 $( "#content" ).empty();
			  $( "#content" ).append( data );
		 	  }, 'html');
			event.preventDefault();  
	}
	
	function setQNum() {
		$.each($(".publishQStem"), function(i, val) {
			$(val).html("<span class='publishQNum'>" + (i + 1) + "</span>. " + $(val).html());			
		});
	}
	
	
	
	
	
	
	
	
		
	
	
	
	
	
	
	

function brief()
{
	num++;
	var n= num;
	var addques = document.getElementById("question_content");
	var divall = document.createElement("div");
	divall.id="q"+num;
	var div2=document.createElement("div");
	var label = "<label style='margin-left:10px' >Q"+num+"<label>";
	div2.innerHTML+=label;
    var input="<input type='text'  style='vertical-align:middle;width:350px;height:30px;margin-left:60px'  value='简答题'  ><br>";
    div2.innerHTML+=input;
    div2.setAttribute("style", "margin-bottom:20px"); 
    divall.appendChild(div2);
    var div3=document.createElement("div");
    div3.setAttribute("style", "margin-bottom:20px;margin-left:85px");  
    var textarea = "<textarea class='form-control' style='resize:none;'rows='5' cols='48'></textarea>";
	div3.innerHTML+=textarea;
	
	divall.appendChild(div3);
	divall.setAttribute("style", "margin-top:20px "); 

	divall.style.border="1px solid #E3E3E3";
	addques.appendChild(divall);
	var lct = document.getElementById('tabset');
	lct.scrollTop=Math.max(0,lct.scrollHeight-lct.offsetHeight);
};


	var defaultTitleStem        = "请填写问卷题目";
	var defaultIntroStem        = "请在此处填写问卷说明。";
	var defaultSCQStem          = "单选题（在此填写题目，如：下列哪种物质密度最大？）";
	var defaultMCQStem          = "多选题（在此填写题目，如：您从事过的职业？）";
	var defaultMatrixQStem      = "矩阵题";
	var defaultBlankQStem       = "填空题";
	var defaultMixQStem         = "填空选择题（在此填写题目，如：你是否获得过国家奖学金？）";
	var defaultHintQStem        = "在此填写提示语句";
	var defaultPaneQStem        = "在此填写板块名称";
	var defaultMatrixQSubStem   = "请填写子题目";
	var defaultMatrixQSubOption = "选项";
	
	
	
	// 单选题干提示语句的显隐
	stemToggle = function(that) {
		$that = $(that);
		if ($that.hasClass("surveyTitle")) {
			if ($that.val() == defaultTitleStem) {
				that.value = "";
			} else if ($that.val().trim() == "") {
				that.value = defaultTitleStem;
			}
		}
		if ($that.hasClass("SCQStem")) {
			if ($that.val() == defaultSCQStem) {
				that.value = "";
			} else if ($that.val().trim() == "") {
				that.value = defaultSCQStem;
			}
		} else if ($that.hasClass("MCQStem")) {
			if ($that.val() == defaultMCQStem) {
				that.value = "";
			} else if ($that.val().trim() == "") {
				that.value = defaultMCQStem;
			}
		} else if ($that.hasClass("matrixQStem")) {
			if ($that.val() == defaultMatrixQStem) {
				that.value = "";
			} else if ($that.val().trim() == "") {
				that.value = defaultMatrixQStem;
			}
		} else if ($that.hasClass("blankQStem")) {
			if ($that.val() == defaultBlankQStem) {
				that.value = "";
			} else if ($that.val().trim() == "") {
				that.value = defaultBlankQStem;
			}
		} else if ($that.hasClass("mixQStem")) {
			if ($that.val() == defaultMixQStem) {
				that.value = "";
			} else if ($that.val().trim() == "") {
				that.value = defaultMixQStem;
			}
		} else if ($that.hasClass("hintQStem")) {
			if ($that.val() == defaultHintQStem) {
				that.value = "";
			} else if ($that.val().trim() == "") {
				that.value = defaultHintQStem;
			}
		} else if ($that.hasClass("paneQStem")) {
			if ($that.val() == defaultPaneQStem) {
				that.value = "";
			} else if ($that.val().trim() == "") {
				that.value = defaultPaneQStem;
			}
		} else if ($that.hasClass("matrixQSubStem")) {
			if ($that.val() == defaultMatrixQSubStem) {
				that.value = "";
			} else if ($that.val().trim() == "") {
				that.value = defaultMatrixQSubStem;
			}
		} else if ($that.hasClass("matrixQSubOption")) {
			if ($that.val() == defaultMatrixQSubOption) {
				that.value = "";
			} else if ($that.val().trim() == "") {
				that.value = defaultMatrixQSubOption;
			}
		}
	};
	
	
	
</script>




