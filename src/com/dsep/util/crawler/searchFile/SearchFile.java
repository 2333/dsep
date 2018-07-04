package com.dsep.util.crawler.searchFile;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.dsep.util.crawler.spider.SiteSetting;

public class SearchFile {
	public static String html = "<html>"
			+ "<script src='"+SiteSetting.D_MYSOUQ_DEVE+"jquery-3.2.1.min.js'></script>"
			+ "<script src='"+SiteSetting.D_MYSOUQ_DEVE+"20171221.js'></script>"
			+ "<link href='"+SiteSetting.D_MYSOUQ_DEVE+"20171221.css' rel='stylesheet' type='text/css'/>"
			+ "<button type='button' id='button'>Export&Print!</button>"
			+ "<textarea id='myContent' rows='2' cols='100'></textarea>"
			+ "<table border='0' cellpadding='0' cellspacing='0' id='myTable'>"
			+ "<th>select</th>"
			+ "<th>orderId</th>"
			+ "<th>categories</td>"
			+ "<th>ean1</th>"
			+ "<th>ean2</th>"
			+ "<th>ean3</th>"
			+ "<th>ean4</th>"
			+ "<th>ean5</th>";
	public static void getFileList(String strPath) throws Exception {
        File dir = new File(strPath);
        File[] files = dir.listFiles(); // ���ļ�Ŀ¼���ļ�ȫ���������飬�����ж���
        
        for (File f : files) {
        	html += "<tr><td><div class='testDiv notChecked'></div></td>";
        	FolderStruct folderStruct = new FolderStruct();
        	File[] endNodes = f.listFiles();
        	int counter = 0;
        	for (File node : endNodes) {
        		String fileName = node.getName();
        		System.out.println("FileName:" + fileName);
        		if (fileName.endsWith("html")){ // �ж��ļ����Ƿ���.html��β
                    String strFileName = node.getName().substring(0, node.getName().indexOf(".html"));
                    
                    //html += "<td>" +strFileName + "</td>";
                    folderStruct.htmlStrFileName = "<td><span>" +strFileName + "</span></td>";
                    System.out.println("---" + strFileName);
                } else if (fileName.endsWith("html2")) {
                	
                	System.out.println("in html:" + fileName);
                } else if (fileName.endsWith("jpg")) {
                	System.out.println("node.getAbsolutePath()" + node.getAbsolutePath());
                	String[] eanAndQty = node.getName().substring(0, node.getName().indexOf(".jpg")).split("_");
                	folderStruct.htmlPicArr.add("<td class='setTop'>"
                			+ "<span>"+eanAndQty[0]+"</span><br>"
                			+ "<input required='required' type='text' value='"
                			+ PropertiesUtil.GetValueByKey(SiteSetting.myEanInfo, eanAndQty[0])
                			+"'><br>"
                			+ "<img src='"+node.getAbsolutePath()+ "'/>"
                			+ "<input type='hidden' value='" + eanAndQty[1] + "'/></td>");
                	counter++;
                } else {
                	continue;
                }
        		
        	}
        	// ���Ӷ��������Ŀ
        	html += folderStruct.htmlStrFileName;
        	// ���ӻ���������Ŀ
        	html += "<td><span>"+folderStruct.htmlPicArr.size()+"</span></td>";
        	// ���ӻ���ͼƬ��Ŀ
        	for (String str : folderStruct.htmlPicArr) {
        		html += str;
        	}
        	if (counter == 1) {
        		html += "<td></td><td></td><td></td><td></td></tr>";
        	} else if (counter == 2) {
        		html += "<td></td><td></td><td></td></tr>";
        	} else if (counter == 3) {
        		html += "<td></td><td></td></tr>";
        	} else if (counter == 4) {
        		html += "<td></td></tr>";
        	} 
        }
        html += "</table></html>";
        System.out.println(html);
        
        new File(SiteSetting.D_MYSOUQDate + "USER_SELECTED.html").delete();
        FileWriter writer = new FileWriter(new File(SiteSetting.D_MYSOUQDate + "USER_SELECTED.html"), true);
        writer.append(html);
        writer.flush();
        writer.close();
        		
        //return html;
    }
	
	public static void main(String[] args) throws Exception {
		System.out.println(SiteSetting.D_MYSOUQDate1downloadOrders);
		SearchFile.getFileList(SiteSetting.D_MYSOUQDate1downloadOrders);
	}
}

class FolderStruct {
	String htmlStrFileName;
	List<String> htmlPicArr = new ArrayList<String>();
	String htmlPrintFile;
}
