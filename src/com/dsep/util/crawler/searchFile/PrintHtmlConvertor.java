package com.dsep.util.crawler.searchFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

import com.dsep.util.crawler.spider.SiteSetting;

public class PrintHtmlConvertor {
	private static String HTML_Header = SiteSetting.D_MYSOUQ_DEVE + "HTML_Header.html";
	
	public static void init1() throws Exception {
		new File(SiteSetting.D_MYSOUQDate + "PRINT.html").delete();
		new File(SiteSetting.D_MYSOUQDate + "PRINT.html").createNewFile();
		
		
	}
	private static int counter = 1;
	public static void convert1(String orderListFolderPath) throws Exception {
		init1();
        //打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
        FileWriter writer = new FileWriter(new File(SiteSetting.D_MYSOUQDate + "PRINT.html"), true);
        initHeader(new File(HTML_Header), writer);
		//orderListFolderPath = SiteSetting.D2017malaoDate2selectedOrders;
		File currentRootFolder = new File(orderListFolderPath);
		File[] orderList = currentRootFolder.listFiles();
		LinkedList<OrderInfo> orderInfoList = new LinkedList<OrderInfo>();
		for (File f : orderList) {
			orderInfoList.add(new OrderInfo(f.getAbsolutePath()));
		}
		Collections.sort(orderInfoList, new OrderInfoComparator());
		int counter = 1;
		for (OrderInfo orderInfo : orderInfoList) {
			File order = new File(orderInfo.path);
			File[] orderInfos = null;
			if (order.isDirectory()) {
				orderInfos = order.listFiles();
			} else {
				continue;
			}
			 
			String goodsInfo = "<span style=\"font-size:30px; font-weight: bold;\">";
			String printInfo = "";
			for (File info : orderInfos) {
				if (info.getName().endsWith(".html")) {
					printInfo = extractPrintInfo(info);
				} else if (info.getName().endsWith(".jpg")) {
					goodsInfo += info.getName().substring(0, info.getName().indexOf(".jpg")) + " ";
				}
			}
			//System.out.println(printInfo);
			goodsInfo += "</span>";
			writer.write(goodsInfo);
			writer.write(printInfo);
			if (counter++ % 2 == 0) {
				writer.write("<div class='breakByFangHongyu'></div>");
			}
		}
		writer.flush();
		writer.close();
		
	}
	
	
	
	
	private static void initHeader(File HTML_Header, FileWriter writer) throws Exception {
		FileReader reader = new FileReader(HTML_Header);
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		while ((line = br.readLine()) != null) {
			writer.append(line);
		}
		//关闭读取流
		br.close();
		reader.close();
	}
	private static String extractPrintInfo(File html) throws Exception {
		System.out.println(html.getName());
		FileReader reader = new FileReader(html);
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		String returnStr = "";
		boolean hasAlreadyFindDivClassPage = false; 
		while ((line = br.readLine()) != null) {
			if (line.contains("div class=\"page\" s")) {
				if (counter++ % 2 == 1) {
					returnStr += line;
				} else {
					line = line.replace("page", "page2");
					returnStr += line;
				}
				
				System.out.println("find 1st");
				while ((line = br.readLine()) != null) {
					if (line.contains("page-last")) {
						System.out.println("find 2ed");
						br.close();
						reader.close();
						return returnStr;
					}
					returnStr += line;
				}
			}
			//System.out.println(br.readLine());
		}
		//关闭读取流
		br.close();
		reader.close();
		return returnStr;
	}
	
	
	
	
	public static void main(String[] args) throws Exception {
		PrintHtmlConvertor.convert1(SiteSetting.D_MYSOUQDate2selectedOrders);
		//PrintHtmlConvertor.convert2(SiteSetting.D2017malaoDate2selectedOrders);
	}
}

class OrderInfo {
	String path;
	String orderId;
	String firstPicName;
	
	public OrderInfo(String orderInfoPath) {
		path = orderInfoPath;
		File[] files = new File(orderInfoPath).listFiles();
		
		LinkedList<String> fileList = new LinkedList<String>();
		for (File f : files) {
			if (f.getName().endsWith("jpg")) {
				fileList.add(f.getName());
			} else if (f.getName().endsWith(".html")) {
				orderId = f.getName().substring(0, f.getName().indexOf(".html"));
			}
		}
		if (fileList.size() == 1) {
			System.out.println("has only 1 element:" + fileList.get(0));
			firstPicName = fileList.get(0);
		} else {
			for(String s : fileList) {
				System.out.println(s);
			}
			
			 Collections.sort(fileList, new PicComparator());
			 firstPicName = fileList.get(0);
			 System.out.println("the first element is:" + firstPicName);
		}
	}
}

// 同一个订单下，所有jpg图片按字母顺序排序
class PicComparator implements Comparator<String> {
	@Override
	  public int compare(String o1, String o2 ) {

	          return o1.compareTo(o2);

	  }

	}

class OrderInfoComparator implements Comparator<OrderInfo> {

	 

	  @Override

	  public int compare(OrderInfo o1, OrderInfo o2 ) {

	          return o1.firstPicName.compareTo(o2.firstPicName);

	  }

	}