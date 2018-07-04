package com.dsep.util.crawler.spider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.HttpConstant;
/**
 */
public class ItemImageSpider {//implements PageProcessor {
	/*private Site site = Site.me().setRetryTimes(10).setSleepTime(5000).setTimeOut(5000)
    		.addHeader("accept","application/json, text/plain, * /*")
    		.addHeader("Cookie", SiteSetting.cookie)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");

    @Override
    public void process(Page page) {

        //if(page.getUrl().regex(URL_answer).match()){
    	if (true) {
        	//System.out.println(page.getHtml());
        	System.out.println("==========================");
        	System.out.println(page.getRawText());
        	JSONObject myJsonObject1 = new JSONObject(page.getRawText());
        	JSONArray dataArr = myJsonObject1.getJSONArray("data");
        	Map<String, Object> nameValuePair = new HashMap<String, Object>();
            NameValuePair[] values = new NameValuePair[47];
            int counter = 0;
        	for(int i=0; i<dataArr.length(); i++){
        		JSONObject myJsonObject2 = dataArr.getJSONObject(i);
        		JSONArray shipmentOrderUnitsArr = myJsonObject2.getJSONArray("shipmentOrderUnits");
        		for(int j=0; j<shipmentOrderUnitsArr.length(); j++){
        			JSONObject myJsonObject3 = shipmentOrderUnitsArr.getJSONObject(j);
        			Long idUnit = myJsonObject3.getLong("idUnit");
        			//idUnit.get(key)
        			values[counter++] = new BasicNameValuePair("data[" + i + "][" + j + "]" , idUnit.toString());
        			System.out.println("data[" + i + "][" + j + "]" + "=" + idUnit);
        		}
        	}
        	values[counter++] = new BasicNameValuePair("token", SiteSetting.token);

            nameValuePair.put("nameValuePair", values);
            String myUrl = "https://sell.souq.com/orders/getUnitListDetails";
            Request request = new Request(myUrl);
            request.setExtras(nameValuePair);
            request.setMethod(HttpConstant.Method.POST);
            //spider.addRequest(request);
            //Spider.create(new FangHongyuSpider()).addUrl(myUrl).thread(1).run();
            Spider.create(new ItemImageSpider()).addRequest(request).thread(1).run();
            
        	FileWriter fw = null;
			try {
				fw = new FileWriter("E:\\demo1.txt",false);
				fw.write(page.getRawText());
				fw.flush();
				fw.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
        	
            
        }

    }

    @Override
    public Site getSite() {
        return site;
    }*/
    
    public static InputStream inStream = null;  
    public static void downloadItemImage(String link, String location, String ean, int qty) {
    	try {  
            URL url = new URL(SiteSetting.imgDownloadUrl + link);  
            URLConnection con = url.openConnection();  
            inStream = con.getInputStream();  
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
            byte[] buf = new byte[1024];  
            int len = 0;  
            while((len = inStream.read(buf)) != -1){  
                outStream.write(buf,0,len);  
            }  
            inStream.close();  
            outStream.close();  
            File file = new File(SiteSetting.D_MYSOUQDate1downloadOrders + location+"/" + ean+ "_" + qty + ".jpg");//图片下载地址  
            FileOutputStream op = new FileOutputStream(file);  
            op.write(outStream.toByteArray());  
            op.close();  
        } catch (MalformedURLException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }

    public static void main(String[] args) {
    	
    	//https://sell.souq.com/orders/getOrders?size=20&page=0&sortKey=orderDate&sortOrder=desc&isFbs=false&filters%5Bstatus%5D=SHIPMENT_STATUS_CONFIRMED&orderStatus=SHIPMENT_STATUS_CONFIRMED";
        //https://sell.souq.com/orders/getOrders?size=20&page=0&sortKey=orderDate&sortOrder=desc&isFbs=false&filters%5Bstatus%5D=SHIPMENT_STATUS_CONFIRMED&orderStatus=SHIPMENT_STATUS_CONFIRMED";
    	//https://sell.souq.com/orders/getOrders?isFbs=false&interval=&orderStatus=SHIPMENT_STATUS_CONFIRMED&sortKey=orderDate&sortOrder=desc&page=1&size=20
        
        //String answerUrl =  "https://sell.souq.com/orders/getOrders?isFbs=false&interval=&orderStatus=SHIPMENT_STATUS_CONFIRMED&sortKey=orderDate&sortOrder=desc&page=1&size=20";
        //Spider.create(new ItemImageSpider()).addUrl(answerUrl).thread(1).run();
    	//ItemImageSpider.downloadItemImage("/item/2017/02/23/22/08/84/61/item_M_22088461_29139563.jpg");
    }

    

}



