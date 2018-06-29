package com.dsep.util.crawler.searchFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.dsep.util.crawler.spider.SiteSetting;

public class PrintGoodsConvertor {
private static String HTML_Header = SiteSetting.D_MYSOUQ + "HTML_Header.html";
	
	public static void init2() throws Exception {
		new File(SiteSetting.D_MYSOUQDate + "GOODS.html").delete();
		new File(SiteSetting.D_MYSOUQDate + "GOODS.html").createNewFile();
	}
	
	
	public static void convert2(String orderListFolderPath) throws Exception {
		init2();
        //��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�
        FileWriter writer = new FileWriter(new File(SiteSetting.D_MYSOUQDate + "GOODS.html"), true);
        initHeader(new File(HTML_Header), writer);
		//orderListFolderPath = SiteSetting.D2017malaoDate2selectedOrders;
		File currentRootFolder = new File(orderListFolderPath);
		File[] orderList = currentRootFolder.listFiles();
		int counter = 1;
		
		
		for (File order : orderList) {
			File[] orderInfos = null;
			if (order.isDirectory()) {
				orderInfos = order.listFiles();
			} else {
				continue;
			}
			 
			String goodsInfo = "";
			for (File info : orderInfos) {
				if (info.getName().endsWith(".html")) {
					goodsInfo = extractGoodsInfo(info);
				} 
			}
			
			writer.write(goodsInfo);
			
			String itemInfo = "";
			for (File info : orderInfos) {
				if (info.getName().endsWith(".jpg")) {
					itemInfo += "<tr style=\"vertical-align: top;\">";
					itemInfo += "<td><img src='" + info.getAbsolutePath() + "' class='img-size-small ' alt='``' title='``' /></td>";
					itemInfo += "<td style='font-size: 12px;'><strong>" + info.getName().substring(0, info.getName().indexOf(".jpg")) + "</strong></td>";
					itemInfo += "<td></td><td></td><td></td><td></td></tr></tbody></table>";
				}
			}
			System.out.println(itemInfo);
			writer.write(itemInfo);
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
		//�رն�ȡ��
		br.close();
		reader.close();
	}
	
	
	
	private static String extractGoodsInfo(File html) throws Exception {
		System.out.println(html.getName());
		FileReader reader = new FileReader(html);
		BufferedReader br = new BufferedReader(reader);
		String line = "";
		String returnStr = "";
		boolean hasAlreadyFindDivClassPage = false; 
		int counter = 1;
		while ((line = br.readLine()) != null) {
			counter++;
			if (line.contains("class=\"page-last\"")) {
				System.out.println("find page-last in line:" + counter++);
				returnStr += line;
				System.out.println("find 1st");
				while ((line = br.readLine()) != null) {
					if (line.contains("vertical-align: top;\"")) {
						System.out.println("find 2ed");
						return returnStr;
					}
					returnStr += line;
				}
			}
			//System.out.println(br.readLine());
		}
		//�رն�ȡ��
		br.close();
		reader.close();
		return returnStr;
	}
	
	public static void main(String[] args) throws Exception {
		//PrintHtmlConvertor.convert1(SiteSetting.D2017malaoDate2selectedOrders);
		//PrintHtmlConvertor.convert2(SiteSetting.D_MYSOUQDate2selectedOrders);
	}
}
