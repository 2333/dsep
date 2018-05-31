/**
 *  逻辑检查——字符串规则检验js函数
 *  @author lubin 
 */ 

/**
 * 检查字符串是否为空
 * @param value
 * @param colname
 * @returns {Array}
 */
function checkStringNotNull(value, colname){
	if(value.length == 0)
	{
		pass_validate = '0';// 错误的时候为0
		return [false,"此字段不允许为空"];
	}	
	else
		return [true,""];
}

/**
 * 检查字符串是否包含中文
 * @param value
 * @param colname
 * @returns {Array}
 */
function checkStringNotChinese(value,colname){
	regex = /^[A-Za-z0-9.\\\/-]+$/;
	if(value.length == 0)
	{
		pass_validate = '0';// 错误的时候为0
		return [false,"此字段不允许为空"];
	}	
	else{
		if(regex.test(value))
			return [true,""];
		else{
			pass_validate = '0';// 错误的时候为0
			return [false,colname+":请输入合理数据。"];
		}
			
	}
}

/**
 * 检验字符串格式满足ISBN号正确格式
 * @param value
 * @param colname
 * @returns {Array}
 */
function checkStringISBN(value, colname){
	var regex13 = /^\d{3}-\d-\d{5}-\d{3}-\d$/;
	var regex10 = /^\d-\d{5}-\d{3}-\d$/;
	
	console.log(value);
	if(value.length == 0){
		pass_validate = '0';// 错误的时候为0
		return [false,colname+" : 此字段不允许为空"];
	}
		
	else{
		if(regex13.test(value)){
			return [true,""];
		}else if(regex10.test(value)){
			return [true,""];
		}
		else{
			pass_validate = '0';// 错误的时候为0
			return [false,"请输入正确ISBN号!(说明：10位：4-88888-913-9; 13位： 978-4-88888-913-1)"];
		}
			
	}
}
/**
 * 检查ISSN编号
 * @param value
 * @param column
 * @returns {Array}
 */
function checkStringISSN(value,column){
	var regex= /^\d{4}-\d{4}$/;
	if(value.length == 0){
		pass_validate = '0';// 错误的时候为0
		return [false,colname+" : 此字段不允许为空"];
	}
		
	else{
		if(regex.test(value))
			return [true,""];
		else{
			pass_validate = '0';// 错误的时候为0
			return [false,"请输入正确ISSN号!(格式：1234-5678)"];
		}
			
	}
}
/**
 * 检查邮箱格式
 * @param value
 * @param column
 * @returns {Array}
 */
function checkStringEmail(value,column){
	//var regex = /^[w-.]+@[w-.]+(.w+)+$/;
	//var regex = /^[A-Za-zd]+([-_.][A-Za-zd]+)*@([A-Za-zd]+[-.])+[A-Za-zd]{2,5}$/;
	var regex = /\w+[@]{1}\w+[.]\w+/;
	if($.trim(value)==""){
		pass_validate = '0';// 错误的时候为0
		return [false, column +" 字段不能为空！"];
	}else{
		if(regex.test(value)){
			return [true, ""];
		}else{
			pass_validate = '0';// 错误的时候为0
			return [false,column +" 字段格式不正确！"];
		}
	}
}
/**
 * 检查手机号、座机号、小灵通号
 * @param value
 * @param column
 */
function checkStringPhone(value,column){
	var regex = /^(1[3,5,8,7]{1}[\d]{9})|(((400)-(\d{3})-(\d{4}))|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{3,7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)$/;
	if($.trim(value)==""){
		pass_validate = '0';// 错误的时候为0
		return [false, column +" 字段不能为空！"];
	}else{
		if(regex.test(value)){
			return [true ,""];
		}else{
			pass_validate = '0';// 错误的时候为0
			return [false,column +" 字段格式不正确！"];
		}
	}
}
/**
 * 检查是否是电话类型或者是邮箱类型
 * @param value
 * @param column
 * @returns
 */
function checkStringPhoneOrEmail(value,column){
	var result = checkStringPhone(value,column);
	if(result[0]){
		return result;
	}else{
		result = checkStringEmail(value,column);
		if(result[0]){
			return result;
		}else{
			return [false, column+ " 字段格式不正确！"];
		}
	}
}
function checkStringPercent(value,column){
	//var regex = /\d+\.?\d+?%/;
	var regex1 = /^(0\.\d+|[1-9]\d*|[1-9]\d*\.\d+|0)$/;
	if($.trim(value)==""){
		pass_validate = '0';// 错误的时候为0
		return [false, column +" 字段不能为空！"];
	}else if(!regex1.test(value.substr(0,value.length-1))){
		pass_validate = '0';// 错误的时候为0
		return [false, column +" 字段请输入非负值！"];
	}else if(value.substr(value.length-1,1)!='%'){
		pass_validate = '0';// 错误的时候为0
		return [false,column +" 字段格式不正确！（正确格式：20%） "];
	}else{
		if(parseInt(value.substr(0,value.length-1))<=100){
			return [true ,""];
		}else{
			pass_validate = '0';// 错误的时候为0
			return [false, column +" 字段应小于等于100%！"];
		}
	}
}