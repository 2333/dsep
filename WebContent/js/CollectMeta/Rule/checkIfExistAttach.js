function checkIfExistAttach(value, colname){
	if(value!='undefined'){
		return [ true, "" ];
	}else{
		pass_validate = '0';
		return [ false , "请上传附件！"];
	}
	
}