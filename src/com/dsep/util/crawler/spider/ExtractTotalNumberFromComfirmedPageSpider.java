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

public class ExtractTotalNumberFromComfirmedPageSpider implements PageProcessor {
	private Site site = Site.me().setRetryTimes(10).setSleepTime(5000).setTimeOut(120000)
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

    
    @Override
    public void process(Page page) {
    	String url = page.getUrl().get();
    	System.out.println(page.getRawText());
    	JSONObject myJsonObject1 = new JSONObject(page.getRawText());
    	JSONObject pageInfo = myJsonObject1.getJSONObject("page");
    	SiteSetting.size = pageInfo.getInt("size");
    	SiteSetting.totalElements = pageInfo.getInt("totalElements");
    	SiteSetting.totalPages = pageInfo.getInt("totalPages");
    	SiteSetting.number = pageInfo.getInt("number");
    	
    	
    }

    @Override
    public Site getSite() {
        return site;
    }
}
