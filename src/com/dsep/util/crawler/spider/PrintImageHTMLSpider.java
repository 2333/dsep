package com.dsep.util.crawler.spider;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class PrintImageHTMLSpider implements PageProcessor {
    private Site site = Site.me().setRetryTimes(10).setSleepTime(5000).setTimeOut(5000)
    		.addHeader("accept","application/json, text/plain, */*")
    		.addHeader("Cookie", SiteSetting.cookie)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");

    @Override
    public void process(Page page) {

        //if(page.getUrl().regex(URL_answer).match()){
    	//System.out.println(page.getHtml());
    	//System.out.println("==========================");
    	String txt = page.getRawText();
    	//System.out.println(txt);
    	try  
        {  
            FileOutputStream f=new FileOutputStream(SiteSetting.D_MYSOUQDate1downloadOrders+PrintImageHTMLSpider.orderIdStr+"/" +PrintImageHTMLSpider.orderIdStr+".html");  
            byte [] b=txt.getBytes();  
            f.write(b);  
            f.flush();  
            f.close();  
        }  
        catch (Exception e)  
        {  
            System.out.println("not find that file");  
        }  
        
        	
            

    }

    @Override
    public Site getSite() {
        return site;
    }

    public static String orderIdStr = null;
    public static String awb = null;
    
    	
    public static void getPrintImageHTML(String awb, String orderIdStr) {
    	PrintImageHTMLSpider.orderIdStr = orderIdStr;
    	PrintImageHTMLSpider.awb = awb;
        Request request = new Request( "https://sell.souq.com/orders/printAwb");
    	
        Map<String, Object> nameValuePair = new HashMap<String, Object>();
    	NameValuePair[] values = new NameValuePair[2];
    	int counter = 0;
    	values[counter++] = new BasicNameValuePair("awbs[0]" , PrintImageHTMLSpider.awb);
    	values[counter++] = new BasicNameValuePair("token", SiteSetting.token);
    	nameValuePair.put("nameValuePair", values);
        request.setExtras(nameValuePair);
        request.setMethod(HttpConstant.Method.POST);
    	Spider.create(new PrintImageHTMLSpider()).addRequest(request).thread(1).run();
    }

    

}



