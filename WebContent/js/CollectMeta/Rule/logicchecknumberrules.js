/**
 *  逻辑检查——数字规则检验js函数
 *  @author lubin 
 */

/**
 * 检验非负整数,
 * 
 * @param value
 * @param colname
 * @returns {Array}
 * @author lubin
 */
function checkNumNonNegativeInt(value, colname) {// colname位待检测的列名，value为相应的值
	var regex = /^[0-9]\d*$/;
	if (value.length == 0) {
		pass_validate = '0';
		return [ false, "此字段不允许为空" ];
	} else {
		if (regex.test(value)){
			return [ true, "" ];// 正确时的返回值，所有的检测函数都这么写
		}else{
			pass_validate = '0';
			return [ false, colname + ": 请输入非负整数。" ];// 失败时的返回值，第一个参数代表返回失败，第二个参数代表检测失败时的提示信息
		}
			
	}
}
/**
 * 检查是否非负整数
 * @param value
 * @returns
 */
function checkNonNegativeInt(value) {// colname位待检测的列名，value为相应的值
	var regex = /^[0-9]\d*$/;
	if (value.length == 0) {
		pass_validate = '0';
		return false;
	} else {
		if (regex.test(value))
			return true;// 正确时的返回值，所有的检测函数都这么写
		else{
			return false;// 失败时的返回值，第一个参数代表返回失败，第二个参数代表检测失败时的提示信息
		}
			
	}
}

function TRULE(value, colname) {// colname位待检测的列名，value为相应的值
	var regex = /^\d+$/;
	if (value.length == 0) {
		pass_validate = '0';
		return [ false, colname + " : 此字段不允许为空" ];
	} else {
		if (regex.test(value))
			return [ true, "" ];// 正确时的返回值，所有的检测函数都这么写
		else {
			pass_validate = '0';// 错误的时候为0
			return [ false, colname + " : 请输入正整数或零" ];// 失败时的返回值，第一个参数代表返回失败，第二个参数代表检测失败时的提示信息
		}
	}
}
/**
 * 检查正整数
 * 
 * @param value
 * @param colname
 * @returns {Array}
 */
function checkNumPositiveInt(value, colname) {
	var regex = /^[1-9]\d*$/;
	if (value.length == 0) {
		pass_validate = '0';
		return [ false, "此字段不允许为空" ];
	} else {
		if (regex.test(value))
			return [ true, "" ];// 正确时的返回值，所有的检测函数都这么写
		else{
			pass_validate = '0';
			return [ false, colname + ": 请输入正整数。" ];// 失败时的返回值，第一个参数代表返回失败，第二个参数代表检测失败时的提示信息
		}
	}
}
function checkNumPositiveIntOrNull(value,column){
	var regex = /^[1-9]\d*$/;
	if (value.length == 0) {
		return [true,""];
	} else {
		if (regex.test(value))
			return [ true, "" ];// 正确时的返回值，所有的检测函数都这么写
		else{
			pass_validate = '0';
			return [ false, colname + ": 请输入正整数。" ];// 失败时的返回值，第一个参数代表返回失败，第二个参数代表检测失败时的提示信息
		}
	}
}
/**
 * 检查非负小数
 * 
 * @param value
 * @param colname
 * @returns {Array}
 */
function checkNumNonnegativeNum(value, colname) {
	var regex = /^(0\.\d+|[1-9]\d*|[1-9]\d*\.\d+|0)$/;
	if (value.length == 0) {
		pass_validate = '0';// 错误的时候为0
		return [ false, "此字段不允许为空" ];
	} else {
		if (regex.test(value)){
			/*if(parseInt(value)<100){
				return [ true, "" ];// 正确时的返回值，所有的检测函数都这么写
			}else{
				pass_validate = '0';
				return [ false, colname + "输入数值请小于100！" ];
			}*/
			return [true,""];
		}
		else{
			pass_validate = '0';// 错误的时候为0
			return [ false, colname + ": 请输入有效数据。" ];// 失败时的返回值，第一个参数代表返回失败，第二个参数代表检测失败时的提示信息
		}
			
	}
}

function checkNumInColumn(paramsAndValues,value, colname) {
	var regex = /^(0\.\d+|[1-9]\d*|[1-9]\d*\.\d+|0)$/;
	var colModels= $("#"+currentJqGridId).getGridParam("colModel");
	
	//alert(100);
	if (value.length == 0) {
		pass_validate = '0';// 错误的时候为0
		return [ false, "此字段不允许为空" ];
	} else {
		if (regex.test(value)){
			if(parseInt(value)<100||parseInt(value)>=100){
				var parm1 = paramsAndValues[0].split(",")[1];// 获取第一个参数的值
				if(parm1!='undefined'){
					var valueHigh = $("input[id$='"+currentSaveRowId+"_"+parm1+"']").val();
					var columnHigh;
					$.each(colModels,function(i,item){
						if(item.name==parm1){
							columnHigh = item.label;
						}
					});
					if(value>valueHigh)
					{
						return [ false, colname + "数值应小于等于:'"+columnHigh+"'字段的值!" ];
					}
				}
				return [ true, "" ];// 正确时的返回值，所有的检测函数都这么写
			}else{
				pass_validate = '0';
				return [ false, colname + "输入数值请小于100！" ];
			}
		}
		else{
			pass_validate = '0';// 错误的时候为0
			return [ false, colname + ": 请输入有效数据。" ];// 失败时的返回值，第一个参数代表返回失败，第二个参数代表检测失败时的提示信息
		}
			
	}
}