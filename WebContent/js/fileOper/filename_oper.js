
//根据文件路径和分隔符获取文件名
function getFileName(filePath,splitString){
	var fileArray = filePath.split(splitString);
	return fileArray[fileArray.length-1];
}

//根据文件全路径获取文件所在文件夹的绝对路径
function getFilePath(filePath,splitString){
	var lastIndex = filePath.lastIndexOf(splitString);
	var path = filePath.substring(0,lastIndex+1);
	return path;
}