package com.dsep.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.dsep.vm.JqgridVM;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class FileOperate {    
		 // 上传后放在哪个位置
	private static final String UPLOADDIR = "import";
	 //导出excel存放的位置
	private static final String OUTPUTDIR = "output";

	/**
	 * 根据文件路径查询文件是否存在
	 * @param filePath 文件全路径
	 * @return
	 * @throws Exception 
	 */
	public static boolean ifFileExist(String filePath) throws Exception{
		
		if(filePath.startsWith("ftp://"))//如果是ftp
		{
			FTPHelper ftp = new FTPHelper();
	        String serverFile = ftp.parseFTPAddress(filePath);
	        if(ftp.isExist(serverFile))
	        	return true;
	        
		}else{//本地文件
			File f = new File(filePath);
			if(f.exists())
				return true;
		}
		
		return false;
	}
	
	/**
	 * 上传证明文件，为了避免重名，修改文件名，加入上传的单位和时间
	 * @param file
	 * @param user
	 * @return
	 */
	public static String uploadFile(MultipartFile file,String userString){
		// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中
		String realPath = "D:/proofFile/objection/";
		String formerFileName = file.getOriginalFilename();
		String newFileName = getProcessedFileName(formerFileName, userString);
		// 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
		try {
			File newFile = new File(realPath,newFileName);
			InputStream is = file.getInputStream();
			FileUtils.copyInputStreamToFile(is,newFile);
		} catch (IOException e) {
			return null;
		}
		String filePath = realPath + newFileName;
		return filePath;
		
		/*String realPath = "D:/proofFile/objection/";
		// 这里不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉，我是看它的源码才知道的
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(),
					new File(realPath, file.getOriginalFilename()));
		} catch (IOException e) {
			return null;
		}
		String filePath = realPath + file.getOriginalFilename();
		return filePath;*/
	}
	
	/**
	 * 上传文件公共方法
	 * @param file 需要上传的文件
	 * @param path 上传文件的路径
	 * @param fileName 文件名（经过处理后的）
	 * @return
	 */
	public static Boolean upload(MultipartFile file, String path, String fileName){
		try {
			File newFile = new File(path, fileName);
			InputStream is = file.getInputStream();
			FileUtils.copyInputStreamToFile(is,newFile);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * 删除附件
	 * @param path 文件全路径，类似D:/A/B.xml
	 */
	public static void delete(String path){
		if(path != null){
			File file = new File(path);
			FileUtils.deleteQuietly(file);
		}
	}
	
	/**
	 * 工具函数，从request中获取MultipartFile对象
	 * @param request
	 * @return
	 */
	public static MultipartFile getFile(HttpServletRequest request){
		
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		commonsMultipartResolver.setDefaultEncoding("utf-8");
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Iterator<String> iter = multipartRequest.getFileNames();

		while (iter.hasNext()) {

			MultipartFile file = multipartRequest.getFile((String) iter.next());
			if (file != null) {
				return file;
			}
		}
		return null;
	}
	
	/**
	 * 获取处理后的文件名
	 * @param formerFileName 原文件名，包含扩展名
	 * @param userString 需要在原文件名后添加的后缀字符串
	 * @return
	 */
	private static String getProcessedFileName(String formerFileName,String userString){
		String[] fileArray = formerFileName.split("\\.");
		String formerSimpleFileName = "";//不带后缀名的原文件名
		String suffix = fileArray[fileArray.length-1];//原文件的后缀名
		for(int i=0;i < fileArray.length-1;i++){
			formerSimpleFileName = fileArray[i];
		}
		return formerSimpleFileName+"_"+userString+"."+suffix;
	}

	/**
	 * 导出excel数据的功能函数
	 * @param excelTitle
	 * @param excelDatalist
	 * @param sheetName
	 * @param rootPath
	 * @param storeFolder
	 * @return 返回字符串为将传递给js回调函数生成下载链接提供文件下载功能
	 */
	 public static Map<String, String> exportExcel(List<List<String>> excelTitle,List<List<String[]>> excelDatalist,List<String> sheetName, String rootPath,String storeFolder){
    	 /*Map<String, String> exportedExcelPathMap = null;
		 try {
			 SimpleDateFormat df = new SimpleDateFormat("MMdd_HHmm");
			 rootPath = URLDecoder.decode(rootPath + storeFolder +File.separator + OUTPUTDIR + File.separator, "UTF-8");

			 String fileName = URLDecoder.decode(df.format(new Date()) +".xls", "UTF-8");
			 File outputDir = new File(rootPath);
			 if (!outputDir.exists()) {  
		            outputDir.mkdirs();  
		     }  
		     WritableWorkbook wwb;  
		     File outputFile = new File(rootPath + fileName);
	         wwb = Workbook.createWorkbook(outputFile);
	         for(int sheetNumber = 0; sheetNumber < sheetName.size(); ++sheetNumber){
		         WritableSheet sheet = wwb.createSheet(sheetName.get(sheetNumber), sheetNumber);   
		         Label label;   
		         WritableFont font1 = new WritableFont(WritableFont.TAHOMA, 11); 
		         WritableCellFormat format1 = new WritableCellFormat(font1); 
		         format1.setAlignment(jxl.format.Alignment.CENTRE);
		         
	             
		         for(int i=0;i<excelTitle.get(sheetNumber).size();i++){
			         label = new Label(i,0,excelTitle.get(sheetNumber).get(i));   
					 sheet.addCell(label);
					 sheet.setColumnView(i,20,format1);
		         }   
		         for(int i=0;i<excelDatalist.get(sheetNumber).size();i++){
		        
		        	 for(int j=0;j<excelDatalist.get(sheetNumber).get(i).length;j++){
		        		 if(excelDatalist.get(sheetNumber).get(i)[j]==null)continue;
		        		 //if(!isNumber(excelDatalist.get(sheetNumber).get(i)[j])){
		        			 label = new Label(j,i+1,excelDatalist.get(sheetNumber).get(i)[j]);
		        			 sheet.addCell(label); 
		        		 //}
		        		 //else{
		        			 //jxl.write.Number number = new jxl.write.Number(j,i+1,Double.parseDouble(excelDatalist.get(sheetNumber).get(i)[j])); 
		        			 //sheet.addCell(number); 
		        		 //}	  	  
		        	 }   	                  
		         }     
	         }
	         wwb.write();   
	    	 wwb.close();  
	    	 exportedExcelPathMap = new HashMap<String, String>();
	    	 exportedExcelPathMap.put("filepath", rootPath);
	    	 exportedExcelPathMap.put("filename", fileName);
			 return exportedExcelPathMap;
		 } catch (UnsupportedEncodingException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 } catch (RowsExceededException e) {
			 e.printStackTrace();
		 } catch (WriteException e) {
			 e.printStackTrace();
		 } 
		 finally{

		 }
		return exportedExcelPathMap;*/
		 return exportExcel(null, excelTitle, excelDatalist, sheetName, rootPath, storeFolder);
	 }
	 /**
	  * 
	  * @param entityName
	  * @param excelTitle
	  * @param excelDatalist
	  * @param sheetName
	  * @param rootPath
	  * @param storeFolder
	  * @return
	  */
	 public static Map<String, String> exportExcel(String entityName,List<List<String>> excelTitle,List<List<String[]>> excelDatalist,List<String> sheetName, String rootPath,String storeFolder){
    	 Map<String, String> exportedExcelPathMap = null;
		 try {
			 SimpleDateFormat df = new SimpleDateFormat("MMdd_HHmm");
			 rootPath = URLDecoder.decode(rootPath + storeFolder +File.separator + OUTPUTDIR + File.separator, "UTF-8");
			 String fileName = null;
			 if(StringUtils.isNotBlank(entityName)){
				 fileName = URLDecoder.decode(entityName+"_"+df.format(new Date()) +".xls", "UTF-8");
			 }else{
				 fileName = URLDecoder.decode(df.format(new Date()) +".xls", "UTF-8"); 
			 }
			 File outputDir = new File(rootPath);
			 if (!outputDir.exists()) {  
		            outputDir.mkdirs();  
		     }  
		     WritableWorkbook wwb;  
		     File outputFile = new File(rootPath + fileName);
	         wwb = Workbook.createWorkbook(outputFile);
	         for(int sheetNumber = 0; sheetNumber < sheetName.size(); ++sheetNumber){
		         WritableSheet sheet = wwb.createSheet(sheetName.get(sheetNumber), sheetNumber);   
		         Label label;   
		         WritableFont font1 = new WritableFont(WritableFont.TAHOMA, 11); 
		         WritableCellFormat format1 = new WritableCellFormat(font1); 
		         format1.setAlignment(jxl.format.Alignment.CENTRE);
		         
	             
		         for(int i=0;i<excelTitle.get(sheetNumber).size();i++){
			         label = new Label(i,0,excelTitle.get(sheetNumber).get(i));   
					 sheet.addCell(label);
					 sheet.setColumnView(i,20,format1);
		         }   
		         System.out.println();
		         int tt = 0;
		         for(int i=0;i<excelDatalist.get(sheetNumber).size();i++){
		        	 String[] row = excelDatalist.get(sheetNumber).get(i);
		        	 int rowLength = row.length;
		        	 for (int j = 0; j < rowLength; j++) {
		        		 System.out.println(tt++);
		        		 if(row[j]==null)continue;
		        		 //if(!isNumber(excelDatalist.get(sheetNumber).get(i)[j])){
		        			 label = new Label(j,i+1,row[j]);
		        			 sheet.addCell(label); 
		        		 //}
		        		 //else{
		        			 //jxl.write.Number number = new jxl.write.Number(j,i+1,Double.parseDouble(excelDatalist.get(sheetNumber).get(i)[j])); 
		        			 //sheet.addCell(number); 
		        		 //}	  	  
		        	 }   	                  
		         }
		         System.out.println();
	         }
	         wwb.write();   
	    	 wwb.close();  
	    	 exportedExcelPathMap = new HashMap<String, String>();
	    	 exportedExcelPathMap.put("filepath", rootPath);
	    	 exportedExcelPathMap.put("filename", fileName);
			 return exportedExcelPathMap;
		 } catch (UnsupportedEncodingException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 } catch (RowsExceededException e) {
			 e.printStackTrace();
		 } catch (WriteException e) {
			 e.printStackTrace();
		 } 
		 finally{

		 }
		return exportedExcelPathMap;
	 }
	 public static Map<String, String> exportExcel(List<List<String>> excelTitle,List<List<String[]>> excelDatalist,List<String> sheetName,String sheetTitle, String rootPath,String storeFolder){
    	 Map<String, String> exportedExcelPathMap = null;
		 try {
			 SimpleDateFormat df = new SimpleDateFormat("MMdd_HHmm");
			 rootPath = URLDecoder.decode(rootPath + storeFolder +File.separator + OUTPUTDIR + File.separator, "UTF-8");

			 String fileName = URLDecoder.decode(df.format(new Date()) +".xls", "UTF-8");
			 File outputDir = new File(rootPath);
			 if (!outputDir.exists()) {  
		            outputDir.mkdirs();  
		     }  
		     WritableWorkbook wwb;  
		     File outputFile = new File(rootPath + fileName);
	         wwb = Workbook.createWorkbook(outputFile);
	         for(int sheetNumber = 0; sheetNumber < sheetName.size(); ++sheetNumber){
		         WritableSheet sheet = wwb.createSheet(sheetName.get(sheetNumber), sheetNumber);   
		         Label label;   
		         WritableFont font1 = new WritableFont(WritableFont.TAHOMA, 11); 
		         WritableCellFormat format1 = new WritableCellFormat(font1); 
		         format1.setAlignment(jxl.format.Alignment.CENTRE);
		         int cols=excelTitle.get(sheetNumber).size();
	             sheet.mergeCells(0, 0, cols-1, 0);
	             label = new Label(0,0,sheetTitle);
	             sheet.addCell(label);
		         for(int i=0;i<excelTitle.get(sheetNumber).size();i++){
			         label = new Label(i,1,excelTitle.get(sheetNumber).get(i));   
					 sheet.addCell(label);
					 sheet.setColumnView(i,20,format1);
		         }   
		         for(int i=0;i<excelDatalist.get(sheetNumber).size();i++){
		        
		        	 for(int j=0;j<excelDatalist.get(sheetNumber).get(i).length;j++){
		        		 if(excelDatalist.get(sheetNumber).get(i)[j]==null)continue;
		        		 //if(!isNumber(excelDatalist.get(sheetNumber).get(i)[j])){
		        			 label = new Label(j,i+2,excelDatalist.get(sheetNumber).get(i)[j]);
		        			 sheet.addCell(label); 
		        		 //}
		        		 //else{
		        			 //jxl.write.Number number = new jxl.write.Number(j,i+1,Double.parseDouble(excelDatalist.get(sheetNumber).get(i)[j])); 
		        			 //sheet.addCell(number); 
		        		 //}	  	  
		        	 }   	                  
		         }     
	         }
	         wwb.write();   
	    	 wwb.close();  
	    	 exportedExcelPathMap = new HashMap<String, String>();
	    	 exportedExcelPathMap.put("filepath", rootPath);
	    	 exportedExcelPathMap.put("filename", fileName);
			 return exportedExcelPathMap;
		 } catch (UnsupportedEncodingException e) {
			 e.printStackTrace();
		 } catch (IOException e) {
			 e.printStackTrace();
		 } catch (RowsExceededException e) {
			 e.printStackTrace();
		 } catch (WriteException e) {
			 e.printStackTrace();
		 } 
		 finally{

		 }
		return exportedExcelPathMap;
	 }
	
	public static JqgridVM importExcel(String tableName,List<String> titleValues,List<String> titleNames,MultipartFile importFile,String storePath) throws Exception {
        //MultipartFile是对当前上传的文件的封装，当要同时上传多个文件时，可以给定多个MultipartFile参数
        if (!importFile.isEmpty()) {
        	File excelFile = null;
            try {
        		storePath = URLDecoder.decode(storePath+ "excel"+File.separator + UPLOADDIR + File.separator, "UTF-8");
        		File storeDir = new File(storePath);
      			if (!storeDir.exists()) {  
 		            storeDir.mkdirs();  
 		        }  
      			//上传的文件的原始文件名
                String fileName = importFile.getOriginalFilename();  
                //重命名上传的文件后存储于服务器，防止冲突，便于处理，规则可以调整
                String storeName = rename(fileName); 
                //File storeFile = new File(storePath+storeName);
                //将上传的文件保存到服务器
                //importFile.transferTo(storeFile);
                // 读取文件流并保持在指定路径  
                InputStream inputStream = importFile.getInputStream();  
                OutputStream outputStream = new FileOutputStream(storePath+storeName);  
                byte[] buffer = importFile.getBytes();  
                int bytesum = 0;  
                int byteread = 0;  
                while ((byteread = inputStream.read(buffer)) != -1) {  
                    bytesum += byteread;  
                    outputStream.write(buffer, 0, byteread);  
                    outputStream.flush();  
                }  
                outputStream.close();  
                inputStream.close();  
                //读取excel表格进行数据封装
                excelFile = new File(storePath+storeName);
                JqgridVM importedjJqgridVM =  readExcel(tableName,titleValues, titleNames,excelFile);
                return importedjJqgridVM;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            finally{
            	excelFile.delete();
            }

           return (JqgridVM)new Object();
       } else {
           return (JqgridVM)new Object();
       }
	}
	private static String rename(String originalName){
		String storeName=null;
        Long now = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));  
        Long random = (long) (Math.random() * now);  
        storeName = now + "" + random;  
        if (originalName.indexOf(".") != -1) {  
        	storeName += originalName.substring(originalName.lastIndexOf("."));  
        }  

		return storeName;
	}
	/**
	 * 判断一行是否为空
	 * @param cells
	 * @param colsNum
	 * @return 如果空返回true
	 */
	private static boolean isExcelEmptyRow(Cell[]cells,int colsNum){
		boolean result = true;
		for(int i = 0;i<colsNum;i++){
			if(!StringDealUtil.removeBeforeAndAfterBlank(cells[i].getContents()).equals("")){
				result=false;
				break;
			}
		}
		return result;
	}
	 public static JqgridVM readExcel(String tableName,List<String> titleValues,List<String> titleNames,File excelFile) throws Exception{
		 //对excel表格内容进行封装
		 List<Map<String, String>> importDataList = new ArrayList<Map<String,String>>();
		 // 创建工作簿对象
		 //只能读取xls格式的excel表格，xlsx会出现异常：jxl.read.biff.BiffException: Unable to recognize OLE stream
		 Workbook workBook = Workbook.getWorkbook(excelFile);
		 // 得到工作簿所有的工作表对象
		 Sheet[] sheets = workBook.getSheets();
		 if(!sheets[0].getName().equals(tableName))
			 throw new Exception("对不起！表格内容与当前不符，您可以点击重新上传或下载对应模板");
		 for(int i=0;i<sheets[0].getRow(0).length&&i<titleNames.size();i++){
			 Cell[] cells = sheets[0].getRow(0);
			 if(!titleNames.get(i).equals(StringDealUtil.removeBeforeAndAfterBlank(cells[i].getContents()))) {
				 throw new Exception("对不起！上传的数据不合要求，请您检查是否曾对模板原内容进行过改动");
			 }
				 
		 }
		 // 遍历所有行
		 for (int i = 1; i < sheets[0].getRows(); i++) {
			 if(isEmptyRow(sheets[0].getRow(i))||isExcelEmptyRow(sheets[0].getRow(i),titleValues.size()))
				 continue;
			 //存储所有行的数据的map，用于存储每一行的各列内容
			 HashMap<String, String> map = new HashMap<String, String>();
			 // 得到所有列，在输出列中的内容
			 Cell[] cells = sheets[0].getRow(i);
			 for (int j = 0; (j < sheets[0].getRow(0).length) && (j < titleValues.size()); j++) {
				 
				 if ((cells.length-1) < j || cells[j].getContents().equals(""))
					 map.put(titleValues.get(j), "");
				 else{
					 map.put(titleValues.get(j), StringDealUtil.removeBeforeAndAfterBlank((cells[j].getContents())));
				 }
			 }
			 importDataList.add(map);
		 }
		 
		 //封装成前台需要的Jqgrid数据
		 JqgridVM importJqgridVM = new JqgridVM(1,1,sheets[0].getRows()-1,importDataList);
		 return importJqgridVM;
	 }
	 
	 public static Boolean isEmptyRow(Cell[] cells){
		 boolean flag = true;
		 int i = 0;
		 while(flag && (i < cells.length)){
			 if(!cells[i].getContents().trim().equals(""))
				 flag = false;
			 i++;
		 }
		 return flag;
	 }

    	public static boolean isInteger(String value) {
    		try {
    			Integer.parseInt(value);
    			return true;
    		} catch (NumberFormatException e) {
    			return false;
    		}
    	}

    	public static boolean isDouble(String value) {
    		try {
    			Double.parseDouble(value);
    			if (value.contains("."))
    		    return true;
    		    return false;
    		} catch (NumberFormatException e) {
    			return false;
    		}
    	}
    	public static boolean isNumber(String value) {
    		return isInteger(value) || isDouble(value);
    	}  

    	/***
    	 * 生成excel导入文件的模板
    	 * @param sheetName 模板文件的文件名和sheet名
    	 * @param colData   列数据，包括下拉框选项
    	 * @param filePath  文件存储目录
    	 * @return
    	 */
    	public static Map<String, String> createExcelTemplate(String sheetName, LinkedHashMap<String, 
    			List<Object>> colData,String filePath,List<String> sampleDatas){
    		HashMap<String, String> resault = new HashMap<String, String>();
    		int MAX_COL = 100;
    		try {
    			 filePath = URLDecoder.decode(filePath+ "excel"+File.separator, "UTF-8");
    			 String fileName = URLDecoder.decode(sheetName +".xls", "UTF-8");
    			 File outputDir = new File(filePath);
    			 if (!outputDir.exists()) {  
    		            outputDir.mkdirs();  
    		     }  
    		     WritableWorkbook wwb;  
    		     File outputFile = new File(filePath + fileName);
    		     if(!outputFile.exists()){
	    	         wwb=Workbook.createWorkbook(outputFile);
	    	         WritableSheet sheet = wwb.createSheet(sheetName, 0);   
	    	         Label label;   
	    	         WritableFont font1 = new WritableFont(WritableFont.TAHOMA, 11); 
	    	         WritableCellFormat format1 = new WritableCellFormat(font1); 
	    	         format1.setAlignment(jxl.format.Alignment.CENTRE);
	    	         //向模板中写入数据
	    	         Iterator<Map.Entry<String, List<Object>>> iter = colData.entrySet().iterator(); 
	    	         int i=0;
	    	         List<Object> selectList;
	    	         while (iter.hasNext()) { 
	    	             Map.Entry<String,List<Object>> entry = (Map.Entry<String,List<Object>>) iter.next();
	    		         label = new Label(i,0,entry.getKey().trim());
	    		         selectList = entry.getValue();
	    		         if(selectList != null){
	    		        	 WritableCellFeatures wcf = new WritableCellFeatures();
	    		        	 wcf.setDataValidationList(selectList);
	    		        	 label.setCellFeatures(wcf);
	    		         }
	    				 sheet.addCell(label);
	    				 sheet.setColumnView(i,20,format1);
	    				 if(selectList != null)sheet.applySharedDataValidation(label, 0, MAX_COL);
	    	             i++;
	    	         } 
	    	         i=0;
	    	         for(String sample:sampleDatas){
	    	        	 label = new Label(i,1,sample);
	    	        	 sheet.addCell(label);
	    	        	 i++;
	    	         }
	    	         wwb.write();   
	    	    	 wwb.close();   
    		     }
    		     resault.put("filePath", filePath);
    		     resault.put("fileName", fileName);
    			 return resault;
    		 } catch (UnsupportedEncodingException e) {
    			 e.printStackTrace();
    		 } catch (IOException e) {
    			 e.printStackTrace();
    		 } catch (RowsExceededException e) {
    			 e.printStackTrace();
    		 } catch (WriteException e) {
    			 e.printStackTrace();
    		 } 
    		 finally{

    		 }
    		return resault;
    	}
    	//读取excel表格测试
    	public static void main(String[] args) throws Exception {
    		   //readExcel("");
    		  }
 } 
