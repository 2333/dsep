/**
 * 计算日期相差的天数
 * @param sDate1
 * @param sDate2
 * @returns
 */
function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式        
   var  aDate,  oDate1,  oDate2,  iDays;        
   aDate  =  sDate1.split("-");        
   oDate1  =  new  Date(aDate[0]  +  '-'  +  aDate[1]  +  '-'  +  aDate[2]);    //转换为12-18-2006格式        
   aDate  =  sDate2.split("-");        
   oDate2  =  new  Date(aDate[0]  +  '-'  +  aDate[1]  +  '-'  +  aDate[2]);        
   iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24);    //把相差的毫秒数转换为天数       
   return  iDays + 1;       
}   
/**
 * 比较两个函数的大小
 * @param date1
 * @param date2
 * @returns {Boolean} date1小于date2返回true
 */
function compareTwoDate(date1,date2,type){
	var value1 = new Date(Date.parse(date1.replace(/-/g,   "/")));
	var value2 = new Date(Date.parse(date2.replace(/-/g,   "/")));
	var result = true;
	if(type=='#'){
		if(value1<value2){
			result = false;
		}
	}else{
		if(value1>value2){
			result = false;
		}
	}
	return result;
}
/**
 * 检查本日期列小于某个列的日期，且小于多少日（参数1为指定列，参数2小于的天数,参数3指定的列的中文名，参数4较大小的类型#前大于后，@后大于前）
 */
function checkDateOneClo(paramsAndValues,value,column){
	var result = checkGeneralDate(value,column);
	var message = [true,""];
	if(!result[0]){
		return result;
	}else{
		var rightCol = true;//是否指定列输入日期正确
		var parm1 = paramsAndValues[0].split(",")[1];// 获取第一个参数的值(指定列名)
		var parm2 = paramsAndValues[1].split(",")[1];// 获取第二个参数的值(天数)
		var parm3 = paramsAndValues[2].split(",")[1];// 获取第三个参数的值(指定列的中文名)
		var parm4 = paramsAndValues[3].split(",")[1];// 获取第四个参数的值说明大于还是小于（#大于；@小于）
		var dateCol = $("input[id$='"+currentSaveRowId+"_"+parm1+"']").val();
		if(!checkDateFormat(dateCol)){
			rightCol = false;
		}
		if(rightCol){
			if(compareTwoDate(value,dateCol,parm4)){
				var diffValue = DateDiff(value,dateCol);
				console.log(diffValue);
				if(diffValue<parm2){
					message = [];
					message.push(false);
					message.push(column+"必须与"+parm3+"相差"+parm2+"天！");
				}
			}else{
				message = [];
				message.push(false);
				if(parm4=='#')
					message.push(column+"字段必须大于："+dateCol);
				else{
					message.push(column+"字段必须小于："+dateCol);
				}
			}
		}
	}
	if(!rightCol){
		return [true,""];
	}else{
		pass_validate = '0';
		return message;
	}
}


/**
 * 检查日期在两个时间之间
 * @param paramsAndValues
 * @param value
 * @param column
 * @returns
 */
function checkDateBetweenTwoDates(paramsAndValues,value,column){
	var result = checkGeneralDate(value,column);
	if(!result[0]){
		return result;
	}else{
		var parm1 = paramsAndValues[0].split(",")[1];// 获取第一个参数的值
		var parm2 = paramsAndValues[1].split(",")[1];// 获取第二个参数的值
		var valueLow = new Date(Date.parse(parm1.replace(/-/g,   "/")));
		var valueHigh = new Date(Date.parse(parm2.replace(/-/g,   "/")));
		value = new Date(Date.parse(value.replace(/-/g,   "/")));
		if(valueLow<=value&&value<=valueHigh){
			return [true,""];
		}else{
			pass_validate = '0';
			return [false, column+" 字段 ："+"必须大于等于日期： "+parm1+" , 并且小于等于日期: "+parm2];
		}
	}
}
/**
 *检查年月在两个值之间 
 */
function checkYearMonthBetweenTwoDates(paramsAndValues,value,column){
	if( value.length==0 ){
		pass_validate = '0';
		return [ false,"年月字段不允许为空!"];
	}else
	{
		var regex = /^(([1-9]\d{3})|(0\d{2}[1-9]))(0[1-9]|1[0-2])$/;
		if( ! regex.test(value) ){
			pass_validate = '0';
			return [ false,"年月字段格式有误！"];
		}
		else
		{
			var valueLow = paramsAndValues[0].split(",")[1];// 获取第一个参数的值
			var valueHigh = paramsAndValues[1].split(",")[1];// 获取第二个参数的值
			if(valueLow<=value&&value<=valueHigh){
				return [true,""];
			}else{
				pass_validate = '0';
				return [false, column+" 字段 ："+"必须大于等于年月： "+valueLow+" , 并且小于等于年月: "+valueHigh];
			}
		}
	}
}


/**
 *  逻辑检查——时间规则检验js函数
 *  @author lubin 
 *  @author YangJunLin
 */
function checkDateBetweenTwoColumnValues(paramsAndValues,value,column){
	var result = checkGeneralDate(value,column);
	if(!result[0]){
		return result;
	}else{
		var valueLow='';//较小列的值
		var minValue='';//最小值
		var valueHigh='';//较大列的值
		var maxValue='';//最大值
		var columnLow='';//较小的列名
		var columnHigh='';//较大的列名
		var parm1 = paramsAndValues[0].split(",")[1];// 获取第一个参数的值
		var parm2 = paramsAndValues[1].split(",")[1];// 获取第二个参数的值
		var parm3 = paramsAndValues[2].split(",")[1];// 获取第三个参数的值
		var parm4 = paramsAndValues[3].split(",")[1];// 获取第四个参数的值
		var colModels= $("#"+currentJqGridId).getGridParam("colModel");
		if(parm1!='undefined'){
			
			valueLow = $("input[id$='"+currentSaveRowId+"_"+parm1+"']").val();
			//alert("input[id$='"+currentSaveRowId+"_"+parm1+"']");
			console.log("valueLow:"+valueLow);
			valueLow = new Date(Date.parse(valueLow.replace(/-/g,   "/")));
			$.each(colModels,function(i,item){
				if(item.name==parm1){
					columnLow = item.label;
				}
			});
		}
		if(parm2!='undefined'){
			valueHigh = $("input[id$='"+currentSaveRowId+"_"+parm2+"']").val();
			valueHigh = new Date(Date.parse(valueHigh.replace(/-/g,   "/")));
			$.each(colModels,function(i,item){
				if(item.name==parm2){
					columnHigh = item.label;
				}
			});
		}
		if(parm3!=0){
			minValue = new Date(Date.parse(parm3.replace(/-/g,   "/")));
		}
		if(parm4!=0){
			maxValue = new Date(Date.parse(parm4.replace(/-/g,"/")));
		}
		value = new Date(Date.parse(value.replace(/-/g,"/")));
		if(minValue!=''&&minValue>value){
			pass_validate = '0';
			return [false, column +" 字段必须大于日期：  "+parm3];
		}else if(maxValue!=''&&maxValue<value){
			pass_validate = '0';
			return [false, column +" 字段必须小于日期：  "+parm4];
		}else{
			if(valueLow!=''&&valueHigh!=''&&(valueLow<value)&&(value<valueHigh)){
				return [ true, "" ];
			}else if(valueLow!=''&&valueHigh==''&&(valueLow<value)){
				return [ true, "" ];
			}else if(valueLow==''&&valueHigh!=''&&(value<valueHigh)){
				return [ true, "" ];
			}else{
				var result = column;
				if(columnLow!=''){
					result+=' 应大于  "'+columnLow+'" 字段的值  ';
				}
				if(columnHigh!=''){
					result+=' 应小于  "'+ columnHigh+ '" 字段的值 ';
				}
				pass_validate = '0';
				return [false,result];
			}
		}
	}
}
//*检查格式
function checkGeneralDate(value,column){
	
	var regex = /(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)/;
	if (value.length == 0) {
		pass_validate = '0';
		return [ false, column +" ： 字段不允许为空!" ];
	}else{
		if(regex.test(value)){
			return [true,""];
		}else{
			return [ false, column +" ： 字段日期格式错误！" ];
		}
	}
}
//*检查格式
function checkDateFormat(value){
	
	var regex = /(([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29)/;
	if (value.length == 0) {
		return false;
	}else{
		if(regex.test(value)){
			return true;
		}else{
			return false;
		}
	}
}
/**
 * 检查年月在两个字段的值之间
 */
function checkYearMonthBetweenTwoColumnValues(paramsAndValues,value,column){
	//regex = /^(19)\d{2}(0[1-9]|1[0-2])$/;
	regex = /^(([1-9]\d{3})|(0\d{2}[1-9]))(0[1-9]|1[0-2])$/;
	console.log(paramsAndValues);
	if (value.length == 0) {
		pass_validate = '0';
		return [ false, "此字段不允许为空!" ];
	}else{
		var valueLow='';
		var minValue='';//最小值
		var valueHigh='';
		var maxValue='';//最大值
		var columnLow='';
		var columnHigh='';
		var parm1 = paramsAndValues[0].split(",")[1];// 获取第一个参数的值
		var parm2 = paramsAndValues[1].split(",")[1];// 获取第二个参数的值
		var parm3 = paramsAndValues[2].split(",")[1];// 获取第三个参数的值
		var parm4 = paramsAndValues[3].split(",")[1];// 获取第四个参数的值
		var colModels= $("#"+currentJqGridId).getGridParam("colModel");
		if(parm1!='undefined'){
			valueLow = $("input[id$='"+currentSaveRowId+"_"+parm1+"']").val();
			$.each(colModels,function(i,item){
				if(item.name==parm1){
					columnLow = item.label;
				}
			});
		}
		if(parm2!='undefined'){
			valueHigh = $("input[id$='"+currentSaveRowId+"_"+parm2+"']").val();
			$.each(colModels,function(i,item){
				if(item.name==parm2){
					columnHigh = item.label;
				}
			});
		}
		if(parm3!=0){
			minValue= parm3;
		}
		if(parm4!=0){
			maxValue = parm4;
		}
		value=parseInt(value);
		if(!regex.test(value)){
			pass_validate = '0';
			return [false,column+' ：此字段格式错误！ '];
		}else if(minValue!=''&&minValue>value){
			pass_validate = '0';
			return [false, column +" 字段必须大于日期：  "+parm3];
		}else if(maxValue!=''&&maxValue<value){
			pass_validate = '0';
			return [false, column +" 字段必须小于日期：  "+parm4];
		}else{
			if(valueLow!=''&&valueHigh!=''&&(valueLow<value)&&(value<valueHigh)){
				return [ true, "" ];
			}else if(valueLow!=''&&valueHigh==''&&(valueLow<value)){
				return [ true, "" ];
			}else if(valueLow==''&&valueHigh!=''&&(value<valueHigh)){
				return [ true, "" ];
			}else{
				var result = column;
				if(columnLow!=''){
					result+=' 应大于  "'+columnLow+'" 字段的值  ';
				}
				if(columnHigh!=''){
					result+=' 应小于  "'+ columnHigh+ '" 字段的值 ';
				}
				pass_validate = '0';
				return [false,result];
			}
		}
	}
	return [true,""];
}
/**
 * 检查日期符合出生年月规则,检验出生年月格式及合理性.格式为YYYYMM其中年龄应该小于150岁大于18岁。
 * 
 * @param value
 * @param colname
 * @returns {Array}
 * @author YangJunLin
 */
function checkBornYearAndMonth(value, colname) {
	if (value.length == 0) {
		pass_validate = '0';
		return [ false, "此字段不允许为空!" ];
	} else {
		regex = /^(19)\d{2}(0[1-9]|1[0-2])$/;
		var year = parseInt(value.substring(0, 4));
		var date = new Date();
		var currentYear = date.getFullYear();
		var trueYear = currentYear - year;
		if ((18 <= trueYear) && (150 >= trueYear) && regex.test(value)) {
			return [ true, "" ];
		} else {
			pass_validate = '0';
			return [ false, colname + ": 年龄应大于18岁小于150岁,并且出生年月格式要正确。" ];
		}
	}
}

/**
 * @param paramsAndValues
 * @param value
 * @param colname
 * @returns {Array}
 * @author YangJunLin
 */
function checkYearOfBeforeNow(value, colname) {
	if (value.length == 0) {
		pass_validate = '0';
		return [ false, "此字段不允许为空!" ];
	} else {
		regex = /^(19|20)\d{2}$/;
		var year = parseInt(value);
		var date = new Date();
		var currentYear = date.getFullYear();
		var trueYear = currentYear - year;
		if ((0 <= trueYear) && regex.test(value)) {
			return [ true, "" ];
		} else {
			pass_validate = '0';
			return [ false, colname + ": 应当小于当前年份。" ];
		}
	}
}
function checkYearAndMonthOfBeforeNowOrNull(value, colname){
	if (value.length == 0) {
		return [true,""];
	} else {
		regex = /^(19|20)\d{2}$/;
		var year = parseInt(value);
		var date = new Date();
		var currentYear = date.getFullYear();
		var trueYear = currentYear - year;
		if ((0 <= trueYear) && regex.test(value)) {
			return [ true, "" ];
		} else {
			pass_validate = '0';
			return [ false, colname + ": 应当小于当前年份。" ];
		}
	}
}
/**
 * @param paramsAndValues
 * @param value
 * @param colname
 * @returns {Array}
 * @author YangJunLin
 */
function checkYearAndMonthOfBeforeNow( value, colname) {
	if (value.length == 0) {
		pass_validate = '0';
		return [ false, "此字段不允许为空!" ];
	} else {
		var year = parseInt(value.substring(0, 4));
		var month = parseInt(value.substring(5, 7));
		var date = new Date();
		var currentYear = date.getFullYear();
		var currentMonth = date.getMonth() + 1;
		regex = /^(19|20)\d{2}(0[1-9]|1[0-2])$/;
		if ( !regex.test(value) )
		{
			return [ false, "日期格式不对！"];
		}
		else if ( year < currentYear || ((year == currentYear) && (month <= currentMonth))) { // 时间要小于当前时间，年份和月份都要小于。
			return [ true, "" ];
		} else {
			pass_validate = '0';
			return [ false, year+" : "+month+" : "+currentYear+" : "+currentMonth+" : "+colname + ": 需合理且小于当前日期。" ];
		}
	}
}

/**
 * 检查一般年月规则,时间格式为YYYYMM，且不能超过当前的日期。
 * 
 * @param value
 * @param colname
 * @returns {Array}
 * @author YangJunLin
 */
function checkDateGeneralYearMonth(value, colname) {
	if (value.length == 0){
		pass_validate = '0';
		return [ false, colname+" : 字段不允许为空!" ];
	}else {
		var year = parseInt(value.substring(0, 4));
		var month = parseInt(value.substring(4, 6));
		var date = new Date();
		var currentYear = date.getFullYear();
		var currentMonth = date.getMonth() + 1;
		//regex = /^(1[89]|20)\d{2}(0[1-9]|1[0-2])$/;
		regex = /^(([1-9]\d{3})|(0\d{2}[1-9]))(0[1-9]|1[0-2])$/;
		if ((regex.test(value) && ((year < currentYear) || ((year == currentYear) && (month <= currentMonth))))) { // 时间要小于当前时间，年份和月份都要小于。
			return [ true, "" ];
		} else {
			pass_validate = '0';
			return [ false, colname+" : 请您输入合理的日期，保证格式正确，且小于当前日期！" ];
		}
	}
}

/**
 * 检查一般年月日，对应规则ID为D7.格式应当为YYYYMMDD。
 * 
 * @param value
 * @param colname
 * @returns {Array}
 * @author YangJunLin
 */
function checkDateGeneralYearMonthDay(value, colname) {
	if (value.length == 0){
		pass_validate = '0';
		return [ false, "此字段不允许为空!" ];
	}else {
		regex = /^(1[89]|20)\d{2}(0[1-9]|1[0-2])(0[1-9]|1[0-9]|2[0-9]|3[0-1])$/;
		if (regex.test(value)) {
			return [ true, "" ];
		} else {
			pass_validate = '0';
			return [ false, "请您输入合理的日期!" ];
		}
	}
}

/**
 * 项目或其它结束时间规则，参数为开始年月，结束时间必须大于开始时间。格式（YYYYMM）,对应规则为D3.
 * 
 * @param startDate
 * @param endDate
 * @param colname
 * @returns {Array}
 * @author YangJunLin
 */
function checkDateFinishYearMonth(startDate, endDate, colname) {
	if ((startDate.length == 0) || (endDate.length == 0)){
		pass_validate = '0';
		return [ false, "字段不允许为空!" ];
	}	
	else {
		var startTime = Integer.parseInt(startDate);
		var endTime = Integer.parseInt(endDate);
		var time = endTime - startTime;
		if (time >= 0) {
			return [ true, "" ];
		} else {
			pass_validate = '0';
			return [ false, "请您输入合理的日期，结束日期必须大于开始日期！" ];
		}
	}
}

/**
 * 检查结束年月日 项目或其它结束时间规则，参数为开始年月日，结束时间必须大于开始时间且开始或结束时间在评估范围之内（3年之内）。格式（YYYYMMDD）
 * 
 * @param startDate
 * @param endDate
 * @param colname
 * @returns {Array}
 * @author YangJunLin
 */
function checkDateFinishYearMonthDay(startDate, endDate, colname) {
	if ((startDate.length == 0) || (endDate.length == 0)){
		pass_validate = '0';
		return [ false, "字段不允许为空!" ];
	}	
	else {
		var startYear = Integer.parseInt(startDate.substring(0, 4));
		var startTime = Integer.parseInt(startDate);
		var endYear = Integer.parseInt(endDate.substring(0, 4));
		var endTime = Integer.parseInt(endDate);
		var time = endTime - startTime;
		var date = new Date();
		var currentYear = date.getFullYear();
		var startPeriod = Math.abs(currentYear - startYear);
		var endPeriod = Math.abs(currentYear - endYear);
		if ((time > 0) && (startPeriod <= 3.0 || endPeriod <= 3.0)) {
			return [ true, "" ];
		} else {
			pass_validate = '0';
			return [ false, "请您输入合理的日期，结束时间必须大于开始时间且开始或结束时间在评估范围之内（3年之内）！" ];
		}
	}
}

/**
 * 检查年度规则（评估有效年度内）,时间必须在有效范围之内。格式（YYYY）,
 * 
 * @param paramsAndValues
 * @param value
 * @param colname
 * @returns {Array}
 * @author YangJunLin
 */
function checkDateYear(paramsAndValues, value, colname) {
	var parm1 = paramsAndValues[0].split(",")[1];// 获取第一个参数的值
	var parm2 = paramsAndValues[1].split(",")[1];// 获取第二个参数的值
	if ((value.length == 0)) {
		pass_validate = '0';
		return [ false, colname + "不允许为空。" ];
	} else {
		if ((parm1 <= value) && (value <= parm2)) {
			return [ true, "" ];
		} else {
			pass_validate = '0';
			return [ false, colname + "：有效年份范围为" + parm1 + "-" + parm2 + "。" ];
		}
	}
}

/**
 * @param paramsAndValues
 * @param value
 * @param colname
 * @returns {Array}
 * @author YangJunLin
 */
function checkGeneralYear(value, colname) {
	if ((value.length == 0)) {
		pass_validate = '0';
		return [ false, colname + "不允许为空。" ];
	} else {
		regex = /^(2[01])\d{2}$/;
		if (regex.test(value)) {
			return [ true, "" ];
		} else {
			pass_validate = '0';
			return [ false, colname + ": 日期格式不正确,日期必须大于1999，小于2200" ];
		}
	}
}

/**
 * 检查一般的年月，格式是否合格。
 * @param paramsAndValues
 * @param value
 * @param colname
 * @returns {Array}
 */
function checkGeneralYearAndMonth(value, colname) {
	if ((value.length == 0)) {
		pass_validate = '0';
		return [ false, colname + "不允许为空。" ];
	} else {
		regex = /^(2[01])\d{2}(0[1-9]|1[0-2])$/;
		if (regex.test(value)) {
			return [ true, "" ];
		} else {
			pass_validate = '0';
			return [ false, colname + ": 日期格式不正确。" ];
		}
	}
}

/**
 * 检查年度规则（评估有效年度内）。格式（YYYYMM）,
 * 
 * @param year
 * @param period
 * @param colname
 * @returns {Array}
 * @author YangJunLin
 */
function CheckDateYearMonth(paramsAndValues, value, colname) {
	var parm1 = paramsAndValues[0].split(",")[1];// 获取第一个参数的值
	var parm2 = paramsAndValues[1].split(",")[1];// 获取第二个参数的值
	regex = /^(20)\d{2}(0[1-9]|1[0-2])$/;
	if ((value.length == 0)) {
		pass_validate = '0';
		return [ false, colname + "不允许为空。" ];
	} else {
		if ((parm1 <= value) && (value <= parm2) && (regex.test(value))) {
			return [ true, "" ];
		} else {
			pass_validate = '0';
			return [ false, colname + "：有效年月范围为" + parm1 + "-" + parm2 + "。" ];
		}
	}
}

/**
 * 资助年限,格式YYYYMM-YYYYMM，起始年月不得超过系统当前时间。
 * 
 * @param value
 * @param colname
 * @returns {Array}
 * @author YangJunLin
 */
function checkDatePeriod(value, colname) {
	if (value.length == 0){
		pass_validate = '0';
		return [ false, "此字段不允许为空!" ];
	}	
	else {
		var regex = /^(1[89]|20)\d{2}(0[1-9]|1[0-2])-(1[89]|20)\d{2}(0[1-9]|1[0-2])$/;
		var startTime = Integer.parseInt(value.substring(0, 6));
		var endTime = Integer.parseInt(value.substring(7, 13));
		if (startTime >= endTime) {
			pass_validate = '0';
			return [ false, "请您输入合理的日期,格式YYYYMM-YYYYMM，起始年月不得超过系统当前时间且要小于结束时间!" ];
		}
		var year = Integer.parseInt(str.substring(0, 4));
		var month = Integer.parseInt(str.substring(4, 6));
		var date = new Date();
		var currentYear = date.getFullYear();
		var currentMonth = date.getMonth() + 1;
		if ((startTime < endTime)
				&& (regex.test(value) && ((year < currentYear) || ((year == currentYear) && (month <= currentMonth))))) { // 时间要小于当前时间，年份和月份都要小于。
			return [ true, "" ];
		} else {
			pass_validate = '0';
			return [ false, "请您输入合理的日期,格式YYYYMM-YYYYMM，起始年月不得超过系统当前时间且要小于结束时间!" ];
		}
	}
}
