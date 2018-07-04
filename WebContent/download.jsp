<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.File" %>
<%@ page import="java.io.*" %>
<%@ page import="java.net.*" %>
<%@ page import="com.dsep.util.FTPHelper" %>
<%@ page import="com.dsep.util.Base64Util" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>download</title>
</head>
<body>
<%
	//供导出button使用的下载函数，返回数据后set下载窗口的值
	String filename = request.getParameter("filename"); 
	String filepath = request.getParameter("filepath");
	
	//base64解密
	filepath = filepath.replace(' ', '+');//处理加号变成空格的问题
	filepath = Base64Util.decode(filepath);
	
	response.setContentType("application/octet-stream");
 	response.setHeader("Content-disposition","attachment; filename=" + new String(filename.getBytes("utf-8"),"iso8859-1")); 	 
	
 	OutputStream output = null;
	output = response.getOutputStream();
	
	if(filepath.startsWith("ftp://"))//如果是ftp,则用FTP下载
	{
		FTPHelper ftp = new FTPHelper();
        String serverFile = ftp.parseFTPAddress(filepath+filename);
       	ftp.get(serverFile, output);
        output.flush();	
        
	}else{
		FileInputStream fis = null;
		File f = new File(filepath+filename);

		try{
			fis = new FileInputStream(f);
			byte[] b = new byte[(int)f.length()];
			int i = 0;
			while((i = fis.read(b)) >0){
				output.write(b, 0, i);
			}
			output.flush();
		}catch(Exception e){
			e.printStackTrace();
	 	}
		finally{
			if(fis != null){
				fis.close();
	 	  		fis = null;
	 	  	}			
	 	}
	}
	if(output != null){
		output.close();
		output = null;
		out.clear();
		out = pageContext.pushBody();
	}
	
	
	//下面两句的编码转换很重要
	//filename =  new String(filename.getBytes("iso8859-1"),"utf-8"); 
	//response.setHeader("Content-Disposition","attachment;filename = \""+URLEncoder.encode(filename, "utf-8")+"\""); 
%>
</body>
</html>