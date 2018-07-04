package com.dsep.util.crawler.spider;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
public class DetailPageSpider implements PageProcessor {
	public DetailPageSpider(Set<String> itemDetailKeySet, IdOrderStrSequRecorder idOrderStrSequRecorder) {
		this.itemDetailKeySet = itemDetailKeySet;
		DetailPageSpider.idOrderStrSequRecorder = idOrderStrSequRecorder;
	}
	//private Map<String, Object> nameValuePair = null;
	private String myUrl = "https://sell.souq.com/orders/getUnitListDetails";
	private Set<String> itemDetailKeySet = null;
    private Site site = Site.me().setRetryTimes(10).setSleepTime(5000).setTimeOut(5000)
    		.addHeader("accept","application/json, text/plain, */*")
    		.addHeader("Cookie", SiteSetting.cookie)
    		//.addHeader("accept-encoding","gzip, deflate, br")
    		//.addHeader("accept-language","zh-CN,zh;q=0.9")
    		//.addHeader("cache-control","no-cache")
    		//.addHeader("content-type","application/x-www-form-urlencoded;charset=UTF-8")
    		//.addHeader("origin","https://sell.souq.com")
    		//.addHeader("pragma","no-cache")
    		//.addHeader("referer","https://sell.souq.com/orders/order-management?tab=confirmed")
    		//.addHeader("x-requested-with","XMLHttpRequest")
    		
    		/*.addCookie("CARTID", "34fdbd3ff4dc7e7aab16e14b78ea5980")
    		.addCookie("PHPSESSID", "2ia2m1br63s6du2gmtijibr7dun2ngma")
    		.addCookie("PLATEFORMC", "ae")
    		.addCookie("PLATEFORML", "en")
    		.addCookie("SCAUAT", "eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIxNTQzNjQ2OSIsImZpcnN0TmFtZSI6IlRhbWlhIiwibGFzdE5hbWUiOiJ6aGFuZyIsInJvbGUiOiJTRUxMRVIiLCJzZWxsZXJJZCI6MS41NDM2NDY5RTcsImdyb3VwSWQiOjEuNTQzNjQ2OUU3LCJpc3MiOiJTT1VRIiwiaWQiOjEuNTQzNjQ2OUU3LCJleHAiOjE1MTM1OTM1MzAsInVzZXJOYW1lIjoiVGFtaWFhIiwiaWF0IjoxNTEzNTg5OTMwfQ.d51gGAoRBWwiW4dTpHEwa1JjPhheoPFYyKkurCiCvEHgBtBLkOdz4lQ0pMzTI6Y5DYuib4Xj-jnqtr-oNhqRMIsoJxIbCLtjgZjhU_MntV10A9ARVW1dCRpDHtWAxSrpU_0GSyB9x4QdDo7RO4lr6x0byQEBwpPOQGiePvn2cXaAb_Bw8RLR6RTNnCeWFNweWfW-KhPrvpm09qWsFWSAqaVeDymOD8_vYCY6f9KMzttkzV2fq257WBpJ4jyaTkGx5vzmUraCqUtdcCOz4P3dAlWGFv-gjsI1miwjeCkHWkBKnwq3rF9h0j7wWQ7bfEm8nLA3rRu33lTXKvX4C_Ec1AEei9p9B5JGCapwmqmZ3eWs0H-wD_g4mXSf_bJlyceKBeMS4mcu0N5cKVcP1rwwNsgIGI6rDuWZzZ3bjPI7zyWTZIGVhpqzOKDu7G9gQnRrYSpIqD0LCREMOb6W3g96zlWh55obbqjCkfYL2ToBX0HCFPIHvKRkB0yL-tbVItT0d-YqYzC4lvqT1bmS5c5LYfB6_xC5d_4ahf9M80UJoCFzP7MUZGs5Ik9kvpU4QsWUdCGnCt6f6Eu0Qzn43ghQL518QpyanEL54U4o0DNY0V4s0bI_4avntQKLzDWqryOgj4pNruHmX5QVUNFzvFhzf-OO6S-Sb0McOfcFf7jtNN0")
    		.addCookie("SCAURT", "c097d0d0af4c6e07d067db394baf90e4")
    		.addCookie("SCAUTT", "BEARER")
    		.addCookie("SCXAT", "658ySyQOxtzj6Zb7FLgaRJTZ2KJBpuwJmTq10rMJQ+1513508182661")
    		.addCookie("__gads", "ID")
    		.addCookie("_ga", "GA1.1.1123207084.1512262154")
    		.addCookie("_ga", "GA1.2.1123207084.1512262154")
    		.addCookie("_gid", "GA1.1.693435458.1513487231")
    		.addCookie("_gid", "GA1.2.693435458.1513487231")
    		.addCookie("ab.storage.deviceId.2e4ae497-9aed-4a69-8a2d-91cd396ab384", "%7B%22g%22%3A%22ed102d83-ffa8-435d-87fd-7b1260c424b9%22%2C%22c%22%3A1512262328829%2C%22l%22%3A1512262328829%7D")
    		.addCookie("ab.storage.deviceId.dde4157a-6ed4-4e47-a940-cdd336f179b2", "%7B%22g%22%3A%225294b992-466a-1132-0fb2-6fb29b721223%22%2C%22c%22%3A1512262398021%2C%22l%22%3A1512262398021%7D")
    		.addCookie("ab.storage.sessionId.2e4ae497-9aed-4a69-8a2d-91cd396ab384", "%7B%22g%22%3A%229d3fc8e0-2916-9d22-d59a-19d531e0a8f6%22%2C%22e%22%3A1513509976646%2C%22c%22%3A1513508097776%2C%22l%22%3A1513508176646%7D")
    		.addCookie("ab.storage.sessionId.dde4157a-6ed4-4e47-a940-cdd336f179b2", "%7B%22g%22%3A%228883f805-4643-7462-033c-717cd6f2abbc%22%2C%22e%22%3A1513591729172%2C%22c%22%3A1513589920841%2C%22l%22%3A1513589929172%7D")
    		.addCookie("ab.storage.userId.2e4ae497-9aed-4a69-8a2d-91cd396ab384", "%7B%22g%22%3A%2215436469%22%2C%22c%22%3A1512262222695%2C%22l%22%3A1512262222695%7D")
    		.addCookie("ab.storage.userId.dde4157a-6ed4-4e47-a940-cdd336f179b2", "%7B%22g%22%3A%2215436469%22%2C%22c%22%3A1513513455713%2C%22l%22%3A1513513455713%7D")
    		.addCookie("c_Ident", "15122621543630")
    		.addCookie("cmgvo", "Typed%2FBookmarkedTyped%2FBookmarkedundefined")
    		.addCookie("formisimo", "ld5mJ67sJByjkdGiZsp3YZuyPJ")
    		.addCookie("idc", "15436469")
    		.addCookie("is_logged_in", "1")
    		.addCookie("optimizelyBuckets", "%7B%7D")
    		.addCookie("optimizelyEndUserId", "oeu1512262310888r0.5619670834946131")
    		.addCookie("optimizelySegments", "%7B%22182429971%22%3A%22referral%22%2C%22182476429%22%3A%22false%22%2C%22182494213%22%3A%22gc%22%7D")
    		.addCookie("remember_key", "2160b82e3a7035898eaa2c0460be34c7")
    		.addCookie("s_campaign", "NA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3ANA%3Aae%3Aen%3ANA%3ANA%3ATab-Bookarked%3Afree")
    		.addCookie("s_cc", "true")
    		.addCookie("s_ev21", "%5B%5B%27Typed%2FBookmarked%27%2C%271512262155451%27%5D%2C%5B%27Typed%2FBookmarked%27%2C%271513487244280%27%5D%5D")
    		.addCookie("s_ev22", "%5B%5B%27Typed%2FBookmarked%253A%2520HomePage%27%2C%271512262155451%27%5D%2C%5B%27Typed%2FBookmarked%253A%2520HomePage%27%2C%271513487244281%27%5D%5D")
    		.addCookie("s_fid", "6EDC0491BEA2E2D2-0B9406464156F9EB")
    		.addCookie("s_nr_lifetime", "1513590108928-Repeat")
    		.addCookie("s_nr_quarter", "1513590108929-Repeat")
    		.addCookie("s_nr_year", "1513590108929-Repeat")
    		.addCookie("s_ppv", "AccountPage%2C68%2C68%2C745%2C1440%2C745%2C1440%2C900%2C1%2CP")
    		.addCookie("s_ppvl", "WishListPage%2C75%2C75%2C745%2C1440%2C745%2C1440%2C900%2C1%2CP")
    		.addCookie("s_source", "%5B%5BB%5D%5D")
    		.addCookie("s_sq", "souqglobalprod%3D%2526c.%2526a.%2526activitymap.%2526page%253DAccountPage%2526link%253DSell%252520with%252520Us%2526region%253DinnerWrap%2526pageIDType%253D1%2526.activitymap%2526.a%2526.c%2526pid%253DAccountPage%2526pidt%253D1%2526oid%253Dhttps%25253A%25252F%25252Fsell.souq.com%25252F%2526ot%253DA")
*/
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36");

    @Override
    public void process(Page page) {
    	/** 一个详细页面的JSON数据结构如下
    	 * [
		    {
		        "505700575": {
		            "idItem": "9131106",
		            "idUnit": 505700575,
		            "ean": "2724312005338",
		            "sku": "575.49117706.18",
		            "price": 6849,
		            "unitCondition": "new",
		            "leftInStock": 10,
		            "itemTitle": "Heavy solid wood defense baseball bat 25inch",
		            "itemImage": "item\/2015\/09\/23\/91\/31\/10\/6\/item_M_9131106_9671801.jpg",
		            "itemAttributes": "",
		            "qty": 1,
		            "itemLink": "https:\/\/uae.souq.com\/ae-en\/heavy-solid-wood-defense-baseball-bat-25inch-9131106\/i\/"
		        }
		    },
		    {
		    	"...":{
		    		"...": "...",
		    	},
		    }
    	 * */
    	/**
    	 * 另外，经过发现 详细页面的JSON数据中，"505700575"这种key来自于前一个ConfirmPage页面的POST请求中的data[x][y]
    	 * data[x][y]可能会有重复，但是详细页面的JSON数据中不会有重复，重复的qty会增加
    	 * 所以该类中有一个Set的数据结构存储详细页面的JSON数据的"505700575"key集合
    	 */
    	System.out.println(page.getRawText());
    	JSONArray jsonArr = new JSONArray(page.getRawText());

    	for(int i=0; i<jsonArr.length(); i++){
    		JSONObject myJsonObject = jsonArr.getJSONObject(i);
    		Set<String> keySet = myJsonObject.keySet();
    		for (String key : keySet) {
    			JSONObject myJsonObject2 = myJsonObject.getJSONObject(key);
    			String itemImage = myJsonObject2.getString("itemImage");
    			String ean = myJsonObject2.getString("ean");
    			int qty = myJsonObject2.getInt("qty");
    			System.out.println("itemImage:" + itemImage);
    			ItemImageSpider.downloadItemImage(itemImage, DetailPageSpider.idOrderStrSequRecorder.getIdOrderStrBySequ(i), ean, qty);
    		}
    	}

        String myUrl = "https://sell.souq.com/orders/getUnitListDetails";
        Request request = new Request(myUrl);
        //request.setExtras(nameValuePair);
        request.setMethod(HttpConstant.Method.POST);
        
        
        		
        		
        //spider.addRequest(request);
        //Spider.create(new FangHongyuSpider()).addUrl(myUrl).thread(1).run();
        //Spider.create(new ComfiredPageDetailSpider()).addRequest(request).thread(1).run();
        
    	
    }

    @Override
    public Site getSite() {
        return site;
    }

    
    
    public void start(Map<String, Object> nameValuePair, Set<String> itemDetailKeySet) {
        
    	
    	
    }
    public static IdOrderStrSequRecorder idOrderStrSequRecorder = null;
    public static void main(IdOrderStrSequRecorder idOrderStrSequRecorder) {
    	DetailPageSpider.idOrderStrSequRecorder = idOrderStrSequRecorder;
    	Request request = new Request("https://sell.souq.com/orders/getUnitListDetails");
    	Map<String, Object> nameValuePair = new HashMap<String, Object>();
    	NameValuePair[] values = new NameValuePair[2];
    	int counter = 0;
    	values[counter++] = new BasicNameValuePair("data[" + 0 + "][" + 0 + "]" , "65144100024");
    	values[counter++] = new BasicNameValuePair("token", "SXqGeeXEJQxGHWNs6LssvuliimNsZjB0balPhlq0");
    	nameValuePair.put("nameValuePair", values);
        request.setExtras(nameValuePair);
        request.setMethod(HttpConstant.Method.POST);
    	//Spider.create(new DetailPageSpider(null)).addRequest(request).thread(2).run();
    	
    	
	}

    

}



