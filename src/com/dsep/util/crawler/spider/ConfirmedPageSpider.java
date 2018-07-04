package com.dsep.util.crawler.spider;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;
/**
 */
public class ConfirmedPageSpider implements PageProcessor {
    private Site site = Site.me().setRetryTimes(10).setSleepTime(5000).setTimeOut(5000)
    		.addHeader("accept","application/json, text/plain, */*")
    		.addHeader("accept-encoding","gzip, deflate, br")
    		.addHeader("accept-language","zh-CN,zh;q=0.9")
    		.addHeader("cache-control","no-cache")
    		.addHeader("content-type","application/x-www-form-urlencoded;charset=UTF-8")
    		.addHeader("origin","https://sell.souq.com")
    		.addHeader("pragma","no-cache")
    		.addHeader("referer","https://sell.souq.com/orders/order-management?tab=confirmed")
    		.addHeader("x-requested-with","XMLHttpRequest")
    		.addHeader("Cookie", SiteSetting.cookie)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");

    // 用于在ConfirmPageDetailSpider中遍历详细信息的JSON用
    private static Set<String> itemDetailKeySet = new HashSet<String>();
    
    // 用于记录本page所有idOrderStr的顺序信息
    private static IdOrderStrSequRecorder idOrderStrSequRecorder = null;
    
    public static IdOrderStrSequRecorder getIdOrderStrSequRecorder() {
    	return idOrderStrSequRecorder;
    }
    
    @Override
    public void process(Page page) {
    	String url = page.getUrl().get();
    	//if (url.contains("getUnitListDetails")) {
    		
    	//} else {
    	System.out.println(page.getRawText());
    	JSONObject myJsonObject1 = new JSONObject(page.getRawText());
    	JSONArray dataArr = myJsonObject1.getJSONArray("data");
    	System.out.println("dataArr length is:" + dataArr.length());
    	//NameValuePair[] values = new NameValuePair[21];
    	ArrayList<NameValuePair> values = new ArrayList<NameValuePair>();
    	idOrderStrSequRecorder = new IdOrderStrSequRecorder();
        int counter = 0;
    	for(int i=0; i<dataArr.length(); i++){
    		System.out.println(dataArr.length() + ":" + i);
    		JSONObject myJsonObject2 = dataArr.getJSONObject(i);
    		if (i == 16) {
    			System.out.println();
    		}
    		String orderIdStr = myJsonObject2.getString("orderIdStr");
    		final String  orderIdStrForPrint = orderIdStr;
    		System.out.println("orderIdStr:" + orderIdStr);
    		idOrderStrSequRecorder.add(orderIdStr);
    		new File(SiteSetting.D_MYSOUQDate1downloadOrders + orderIdStr).mkdirs();
    		String awb = myJsonObject2.getString("awb");
    		System.out.println("awb:" + awb);
    		try {
    			//new File(SiteSetting.D2017malaoDate + orderIdStr + "/" + orderIdStr + ".orderIdStr").createNewFile();
    			//SiteSetting.jtb.append("正在爬取订单" + orderIdStr + "的打印页...\n\r");
    			new Thread(new Runnable(){
    	    		public void run() {
    	    		try {
    	    			SiteSetting.jtb.append("正在爬取订单" + orderIdStrForPrint + "的打印页...");
    	    			SiteSetting.jtb.paintImmediately(SiteSetting.jtb.getBounds());
    	    			SiteSetting.logger.error("正在爬取订单" + orderIdStrForPrint + "的打印页...");
    	    		Thread.sleep(1000); 
    	    		} catch (InterruptedException e) {
    	    		e.printStackTrace();
    	    		}
    	    		}
    	    	}).start();
    			new File(SiteSetting.D_MYSOUQDate1downloadOrders + orderIdStr + "/" + orderIdStr + ".html").createNewFile();
				PrintImageHTMLSpider.getPrintImageHTML(awb, orderIdStr);
				
				new Thread(new Runnable(){
    	    		public void run() {
    	    		try {
    	    			
    	    			if (SiteSetting.totalCounter % 11 == 0) {
    	    				SiteSetting.jtb.removeAll();
    	    				SiteSetting.jtb.setText("");
    	    				SiteSetting.jtb.paintImmediately(SiteSetting.jtb.getBounds());
    	    			}
    	    			SiteSetting.jtb.append("爬取完毕!");
    	    			SiteSetting.logger.error("爬取完毕!");
    	    			SiteSetting.jtb.append("第" + ++SiteSetting.totalCounter + "个/总" + SiteSetting.totalElements + "个\n\r");
    	    			SiteSetting.logger.error( "第" + SiteSetting.totalCounter + "个/总" + SiteSetting.totalElements + "个\n\r" );
    	    			
    	    			SiteSetting.jtb.paintImmediately(SiteSetting.jtb.getBounds());
    	    		Thread.sleep(1000); 
    	    		} catch (InterruptedException e) {
    	    		e.printStackTrace();
    	    		}
    	    		}
    	    	}).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		Integer confirmedDate = myJsonObject2.getInt("confirmedDate");
    		System.out.println("confirmedDate:" + confirmedDate);
    		Integer quantity = myJsonObject2.getInt("quantity");
    		System.out.println("quantity:" + quantity);
    		JSONArray shipmentOrderUnitsArr = myJsonObject2.getJSONArray("shipmentOrderUnits");
    		for(int j=0; j<shipmentOrderUnitsArr.length(); j++){
    			JSONObject myJsonObject3 = shipmentOrderUnitsArr.getJSONObject(j);
    			Long idUnit = myJsonObject3.getLong("idUnit");
    			//idUnit.get(key)
    			values.add(new BasicNameValuePair("data[" + i + "][" + j + "]" , idUnit.toString()));
    			itemDetailKeySet.add(idUnit.toString());
    			System.out.println("data[" + i + "][" + j + "]" + "=" + idUnit);
    		}
    	}
    	
    	NameValuePair[] values2 = new NameValuePair[values.size() + 1];
    	//System.out.println(counter + 1);
    	for (int i = 0; i < values.size(); i++) {
    		values2[i] = values.get(i);
    		//System.out.println(i);
    	}
    	
    	// 是SCXAT的VALUE的+号之前的
    	values2[values.size()] = new BasicNameValuePair("token", SiteSetting.token);

        this.nameValuePair.put("nameValuePair", values2);
        
        
        //Spider.create(new DetailPageSpider(nameValuePair)).addRequest(request).thread(2).run();
    	
    	
        
        
        
    	//}
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        
    }
    
    private static String url = "https://sell.souq.com/orders/getOrders?isFbs=false&interval=&orderStatus=SHIPMENT_STATUS_CONFIRMED&sortKey=orderDate&sortOrder=desc&";;
    
    
    
    private static Map<String, Object> nameValuePair = new HashMap<String, Object>();
    
    public static Map<String, Object> getNameValuePair() {
    	return nameValuePair;
    }
    
    public static Set<String> getItemDetailKeySet() {
    	return itemDetailKeySet;
    }
    
    //"https://sell.souq.com/orders/getUnitListDetails"
    public static Request getRequest(String nextUrl) {
    	Request request = new Request(nextUrl);
        request.setExtras(nameValuePair);
        
        for (String str : nameValuePair.keySet()) System.out.println(str);
        request.setMethod(HttpConstant.Method.POST);
        return request;
    }
}


/**
 * REQUEST的构造如下：
 * GET
 * https://sell.souq.com/orders/getOrders?isFbs=false&interval=&orderStatus=SHIPMENT_STATUS_CONFIRMED&sortKey=orderDate&sortOrder=desc&page=0&size=10&filters%5Bstatus%5D=SHIPMENT_STATUS_CONFIRMED
 * 不需要参数（URL中自带）
 * 不需要HEADER值
 * COOKIE:
 * c_Ident=15122621543630; __gads=ID=c7e107b43dcc2259:T=1512262159:S=ALNI_MZnfGHNFgkCy7PWkHvPONMQ18080Q; ab.storage.userId.2e4ae497-9aed-4a69-8a2d-91cd396ab384=%7B%22g%22%3A%2215436469%22%2C%22c%22%3A1512262222695%2C%22l%22%3A1512262222695%7D; optimizelyEndUserId=oeu1512262310888r0.5619670834946131; ab.storage.deviceId.2e4ae497-9aed-4a69-8a2d-91cd396ab384=%7B%22g%22%3A%22ed102d83-ffa8-435d-87fd-7b1260c424b9%22%2C%22c%22%3A1512262328829%2C%22l%22%3A1512262328829%7D; ab.storage.deviceId.dde4157a-6ed4-4e47-a940-cdd336f179b2=%7B%22g%22%3A%225294b992-466a-1132-0fb2-6fb29b721223%22%2C%22c%22%3A1512262398021%2C%22l%22%3A1512262398021%7D; idc=15436469; is_logged_in=1; s_ev21=%5B%5B%27Typed%2FBookmarked%27%2C%271512262155451%27%5D%2C%5B%27Typed%2FBookmarked%27%2C%271513487244280%27%5D%5D; s_ev22=%5B%5B%27Typed%2FBookmarked%253A%2520HomePage%27%2C%271512262155451%27%5D%2C%5B%27Typed%2FBookmarked%253A%2520HomePage%27%2C%271513487244281%27%5D%5D; cmgvo=Typed%2FBookmarkedTyped%2FBookmarkedundefined; ab.storage.sessionId.2e4ae497-9aed-4a69-8a2d-91cd396ab384=%7B%22g%22%3A%229d3fc8e0-2916-9d22-d59a-19d531e0a8f6%22%2C%22e%22%3A1513509976646%2C%22c%22%3A1513508097776%2C%22l%22%3A1513508176646%7D; s_ppvl=WishListPage%2C75%2C75%2C745%2C1440%2C745%2C1440%2C900%2C1%2CP; _ga=GA1.2.1123207084.1512262154; formisimo=ld5mJ67sJByjkdGiZsp3YZuyPJ; ab.storage.userId.dde4157a-6ed4-4e47-a940-cdd336f179b2=%7B%22g%22%3A%2215436469%22%2C%22c%22%3A1513513455713%2C%22l%22%3A1513513455713%7D; s_campaign=NA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3Aae%3Aen%3ANA%3ANA%3ATab-Bookarked%3Afree; SCXAT=O88RvB2gqYnB7zcNYMfELtcPo647VNbUNibJjdcs73M+1513605362937; optimizelySegments=%7B%22182429971%22%3A%22referral%22%2C%22182476429%22%3A%22false%22%2C%22182494213%22%3A%22gc%22%7D; optimizelyBuckets=%7B%7D; _ga=GA1.1.1123207084.1512262154; _gid=GA1.1.693435458.1513487231; s_cc=true; _gat=1; SCAUAT=eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIxNTQzNjQ2OSIsImZpcnN0TmFtZSI6IlRhbWlhIiwibGFzdE5hbWUiOiJ6aGFuZyIsInJvbGUiOiJTRUxMRVIiLCJzZWxsZXJJZCI6MS41NDM2NDY5RTcsImdyb3VwSWQiOjEuNTQzNjQ2OUU3LCJpc3MiOiJTT1VRIiwiaWQiOjEuNTQzNjQ2OUU3LCJleHAiOjE1MTM2MTIxNjQsInVzZXJOYW1lIjoiVGFtaWFhIiwiaWF0IjoxNTEzNjA4NTY0fQ.swcFrql-gjDZ6YPdOKnv-Hbx1WUCab0h_Ebs9bxVsuC403uh3mvi0za5ds7_L_soc3ckIIrnkn8TEd-7VbTuV_X1okxAQdLMvSQi3uVHKzYWIHRweD5Z0PCOGHHEV1PThgURhE6IDxRrj4Wdr_EkrK12AJrxhGE7cM4PMDaD9q9YznjF4So6xyvbRzLP5Wmmjwc6SDfZgGqbFcr-d86BUXSADnKpNkixyLvfEAbFfoP5H0iVDeGxA375x1gVsRO0O6vwb2KY8N2p4y5nRnoX_k_JvIeSdZTckciUJsNn2pBV7_8MeCgMVZZPG15_ltLJrw2hLiYbNcaLjYXcWRTy6yOb9aGQnTsir_zjGwxuLp5-c_SrX_HnPaFZQ_snhgarePGb4qgnYjir5-vYs1Rut7c3vnGql2RMRomU7CK4SJjAYUEVihjm7tLaRk17dcxZBSKR3W9fBVLOvzrpVdHQQEv89H1bQF0sN3gBxmKuHGtWbSt4p5ZbUe9IZi6S5_5PFhG3IRu30vTSjrbZAJtbtQ0rg9Y5fPTkCePmVfnVe8iDCveuqiZ9a7pyC5NJ89HjGMUAAvRCz0_58leoaue7EFt8HHAXMabjRK8-phJ6y3DD2Oz7k5y3RQ-N9DPR2Kk3ytEDxW36OQbQJmxFNLgTth-lckvHA4gv0JDLasnJPXk; SCAURT=606af8af2416753e3b9df7f2d1ff8787; SCAUTT=BEARER; PHPSESSID=m1npaqmdj4u1fc6ar67bpnj94a2nf6pv; remember_key=41931b06ec7df9692b6c1853ca675377; ab.storage.sessionId.dde4157a-6ed4-4e47-a940-cdd336f179b2=%7B%22g%22%3A%22c9a85ac6-564b-6433-6613-c3c600f8e21f%22%2C%22e%22%3A1513610363632%2C%22c%22%3A1513608563633%2C%22l%22%3A1513608563633%7D; s_sq=souqglobalprod%3D%2526c.%2526a.%2526activitymap.%2526page%253DAccountPage%2526link%253DSell%252520with%252520Us%2526region%253DinnerWrap%2526pageIDType%253D1%2526.activitymap%2526.a%2526.c%2526pid%253DAccountPage%2526pidt%253D1%2526oid%253Dhttps%25253A%25252F%25252Fsell.souq.com%25252F%2526ot%253DA; PLATEFORMC=ae; PLATEFORML=en; CARTID=34fdbd3ff4dc7e7aab16e14b78ea5980; s_ppv=AccountPage%2C68%2C68%2C745%2C1440%2C745%2C1440%2C900%2C1%2CP; s_fid=6EDC0491BEA2E2D2-0B9406464156F9EB; s_source=%5B%5BB%5D%5D; s_nr_lifetime=1513608572023-Repeat; s_nr_year=1513608572023-Repeat; s_nr_quarter=1513608572024-Repeat
 * 
 * 
 * RESPONSE的JSON结构如下：
 * {
    "data": [
        {
            "labelsService": null,
            "shipmentId": 47994600,
            "shipmentOrderUnits": [
                {
                    "orderUnitId": 73340635,
                    "price": 4500,
                    "idUnit": 65144100024,
                    "idSeller": 15436469,
                    "unitStatus": "SHIPMENT_STATUS_CONFIRMED",
                    "unitStatusLabel": "Confirmed Order",
                    "cancelReason": null,
                    "cancellationDate": null,
                    "returnInitiatedDate": null,
                    "returnReceivedDate": null,
                    "returnInitiatedReason": null,
                    "returnReceivedReason": null,
                    "returnType": 0,
                    "commisionFee": 900,
                    "idItem": 12009138,
                    "paymentStatus": "Pending",
                    "currency": "AED",
                    "countryID": 1,
                    "sku": "24.29133086.18"
                }
            ],
            "orderId": 35190937,
            "orderIdStr": "1067577863659",
            "orderDate": 1513507709000,
            "confirmedDate": 1513583540000,
            "shippedDate": null,
            "deliveryDate": null,
            "awb": "5890039372374",
            "returnAwb": null,
            "carrierCode": "QPU",
            "location": "ae",
            "dateInserted": 1513507715000,
            "acceptanceDate": 1513607737000,
            "idDeliveryAddress": 45292126,
            "shippingFee": 0,
            "note": null,
            "deliveryTime": "c",
            "quantity": 1,
            "totalHandlingTime": null,
            "shipmentSource": null,
            "shipmentDestination": null,
            "shipmentSourceAsStr": null,
            "shipmentDestinationAsStr": null,
            "paidBySeller": null,
            "paymentMethod": "fort",
            "isPaid": null,
            "paymentReleaseDate": null,
            "packedDate": null,
            "codFee": 0,
            "courier": "QExpressUno",
            "itemCollectionType": null,
            "totalHandlingTimeLabel": "3 Business days",
            "shipmentStatusLabel": "SHIPMENT_STATUS_CONFIRMED",
            "printedAwb": false,
            "shipmentPaymentStatus": {
                "Refunded": 0,
                "Released": 0,
                "DeliveredTotal": 1,
                "Blocked in buyer account": 0,
                "Pending": 1,
                "Total": 1
            },
            "shipmentStatus": [
                "SHIPMENT_STATUS_CONFIRMED"
            ],
            "fbs": false,
            "quantityByStatus": {
                "SHIPMENT_STATUS_CONFIRMED": 1
            },
            "quantityByUnitId": {
                "65144100024": 1
            },
            "grandTotal": 3600,
            "canceledGrandTotal": 0,
            "returnsGrandTotal": 0,
            "returnsSubTotal": 0,
            "canceledSubTotal": 0,
            "initiatedDate": null,
            "receivedDate": null,
            "returnInitiatedReason": null,
            "returnReceivedReason": null,
            "cancellationDate": null,
            "orderAge": "1 day, 4 hours, 3 minutes and 18 seconds",
            "unitSKUs": "24.29133086.18",
            "shippedAge": "NA",
            "deliveryAge": "NA",
            "paymentFees": 0,
            "subTotal": 4500,
            "commisionFees": 900,
            "returnType": null
        },
        {
            ...
        },
    ],
    "page": {
        "size": 10,
        "totalElements": 30,
        "totalPages": 3,
        "number": 0
    }
}
 */


