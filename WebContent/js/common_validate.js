//验证是否是合法的身份证号码
function isIdCardNo(num) {
    var factorArr = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
    var parityBit = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
    var varArray = new Array();
    var lngProduct = 0;
    var intCheckDigit;
    var intStrLen = num.length;
    var idNumber = num;
    // initialize
    if ((intStrLen != 15) && (intStrLen != 18)) {
        return false;
    }
    // check and set value
    for (var i = 0; i < intStrLen; i++) {
        varArray[i] = idNumber.charAt(i);
        if ((varArray[i] < '0' || varArray[i] > '9') && (i != 17)) {
            return false;
        } else if (i < 17) {
            varArray[i] = varArray[i] * factorArr[i];
        }
    }
    if (intStrLen == 18) {
        //check date
        var date8 = idNumber.substring(6, 14);
        if (isDate8(date8) == false) {
            return false;
        }
        // calculate the sum of the products
        for (i = 0; i < 17; i++) {
            lngProduct = lngProduct + varArray[i];
        }
        // calculate the check digit
        intCheckDigit = parityBit[lngProduct % 11];
        // check last digit
        if (varArray[17] != intCheckDigit) {
            return false;
        }
    }
    else {        //length is 15
        //check date
        var date6 = idNumber.substring(6, 12);
        if (isDate6(date6) == false) {
            return false;
        }
    }
    return true;
}
function isDate6(sDate) {
    if (!/^[0-9]{6}$/.test(sDate)) {
        return false;
    }
    var year, month;
    year = sDate.substring(0, 4);
    month = sDate.substring(4, 6);
    if (year < 1700 || year > 2500) return false;
    if (month < 1 || month > 12) return false;
    return true;
}

function isDate8(sDate) {
    if (!/^[0-9]{8}$/.test(sDate)) {
        return false;
    }
    var year, month, day;
    year = sDate.substring(0, 4);
    month = sDate.substring(4, 6);
    day = sDate.substring(6, 8);
    var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    if (year < 1700 || year > 2500) return false;
    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
    if (month < 1 || month > 12) return false;
    if (day < 1 || day > iaMonthDays[month - 1]) return false;
    return true;
}

function isDate10(sDate) {
	var reg = /^(\d{4})-(\d{2})-(\d{2})$/;
    if (!reg.test(sDate)) {
        return false;
    }
    var year, month, day;
    year = sDate.substring(0, 4);
    month = sDate.substring(5, 7);
    day = sDate.substring(8, 10);
    console.log(year+' '+month+' '+day);
    var iaMonthDays = [31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    if (year < 1700 || year > 2500) return false;
    if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) iaMonthDays[1] = 29;
    if (month < 1 || month > 12) return false;
    if (day < 1 || day > iaMonthDays[month - 1]) return false;
    return true;
}

//统一验证
function validator(formClass){
	//添加身份证验证函数
	 jQuery.validator.addMethod("isIdCardNo", function (value, element) {
	        return this.optional(element) || isIdCardNo(value);
	    }, "身份证不合法");
	
	$(formClass).validate({
			rules: {
				password:{
						required:true,
						minlength:3
				},
				confirm_password:{
						required:true,
						equalTo: "#password"
				},
				schoolName:"required",
				email:{
						required:true,
						email:true	
				},
				name:{
						required:true
				},
				idCardNo:{
						required:true,
						isIdCardNo:true
				}
			},
			messages:{
				password:{
					required: "密码不能为空！",
					minlength: "长度不能少于3位！"
				},
				confirm_password:{
					required: "请再次输入密码！",
					equalTo: "两次密码不匹配！"
				},
				schoolName:{
					required:"院系不能为空！",
				},
				email:{
					required:"请输入电子邮箱！",
					email:"邮箱格式错误！"	
				},
				name:{
					required:"请输入联系人姓名！"
				},
				idCardNo:{
					required:"请输入身份证号！"
				}
			},
			onkeyup:false,
			errorPlacement: function(error, element) {
				error.appendTo(element.next());
			}
			
		});
};
