package com.dsep.util.crawler.spider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;

import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;

import com.dsep.util.crawler.searchFile.DeleteDirectory;

public class Starter {
	private String token = null;
	
	
	
	private static int pageNumber = 0;
	private static int lastPageSize = 0;
	private static int pageSize = 10;
	private static int totalNumber = 0;
	
	
	public static boolean begin() {
		DeleteDirectory.deleteDir(new File(SiteSetting.D_MYSOUQDate));
		System.out.println(CollectionUtils.class.getProtectionDomain().getCodeSource().getLocation());

		ExtractTotalNumberFromComfirmedPageSpider findTotalNumberSpider = new ExtractTotalNumberFromComfirmedPageSpider();
		Spider.create(findTotalNumberSpider)
		.addUrl("https://sell.souq.com/orders/getOrders?isFbs=false&interval=&orderStatus=SHIPMENT_STATUS_CONFIRMED&sortKey=orderDate&sortOrder=desc&page=0&size=10").thread(1).run();
		
		
		System.out.println(SiteSetting.token);
		List<String> pageUrls = new ArrayList<String>();
		
		
		for (int page=0; page < SiteSetting.totalPages; page++) {
			String currentUrl = "https://sell.souq.com/orders/getOrders?isFbs=false&interval=&orderStatus=SHIPMENT_STATUS_CONFIRMED&sortKey=orderDate&sortOrder=desc&"
					+ "page=" + page + "&size=" + SiteSetting.size;
			pageUrls.add(currentUrl);
			System.out.println("currentUrl:" + currentUrl);
		}
		
		
		for (String url : pageUrls) {
			synchronized (url) {
				ConfirmedPageSpider spider = new ConfirmedPageSpider();
				Spider.create(spider).addUrl(url).thread(1).run();
				Request request = spider.getRequest("https://sell.souq.com/orders/getUnitListDetails");
				Set<String> itemDetailKeySet = spider.getItemDetailKeySet();
				IdOrderStrSequRecorder idOrderStrSequRecorder = spider.getIdOrderStrSequRecorder();
				Spider.create(new DetailPageSpider(itemDetailKeySet, idOrderStrSequRecorder)).thread(1).addRequest(request).run();
				System.out.println("next totalNumber is:" + totalNumber);
			
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		DeleteDirectory.deleteDir(new File(SiteSetting.D_MYSOUQDate));
		System.out.println(SiteSetting.token);
		totalNumber = Integer.parseInt("0");
		int page = 0;
		while (totalNumber > 0) {
			ConfirmedPageSpider spider = new ConfirmedPageSpider();
			//Spider.create(spider)
			//	.addUrl("https://sell.souq.com/orders/getOrders?isFbs=false&interval=&orderStatus=SHIPMENT_STATUS_CONFIRMED&sortKey=orderDate&sortOrder=desc&"
			//	+ "page=" + (page++) + "&size=" + pageSize).thread(1).run();
			int toalNumberTemp = totalNumber;
			totalNumber -= pageSize;
			// last page
			if (totalNumber < 0) {
				Spider.create(spider)
				.addUrl("https://sell.souq.com/orders/getOrders?isFbs=false&interval=&orderStatus=SHIPMENT_STATUS_CONFIRMED&sortKey=orderDate&sortOrder=desc&"
				+ "page=" + (page++) + "&size=" + toalNumberTemp).thread(1).run();
			} else {
				Spider.create(spider)
				.addUrl("https://sell.souq.com/orders/getOrders?isFbs=false&interval=&orderStatus=SHIPMENT_STATUS_CONFIRMED&sortKey=orderDate&sortOrder=desc&"
				+ "page=" + (page++) + "&size=" + pageSize).thread(1).run();
			}
			Request request = spider.getRequest("https://sell.souq.com/orders/getUnitListDetails");
			Set<String> itemDetailKeySet = spider.getItemDetailKeySet();
			IdOrderStrSequRecorder idOrderStrSequRecorder = spider.getIdOrderStrSequRecorder();
			Spider.create(new DetailPageSpider(itemDetailKeySet, idOrderStrSequRecorder)).thread(1).addRequest(request).run();
			System.out.println(totalNumber);
			
		}
	}
}
