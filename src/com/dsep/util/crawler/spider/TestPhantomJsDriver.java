package com.dsep.util.crawler.spider;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * PhantomJs��һ������webkit�ں˵���ͷ���������û��UI���棬��������һ���������ֻ�����ڵĵ������ҳ����Ϊ��ز�����Ҫ�������ʵ��;
 * ��Ϊ�������ÿ����ȡ������һ�ιȸ��������ʵ�ֲ���,�������ϻ���һ��Ӱ��,������������ʮ�����������ֱ���ڴ�ج��,
 * ���ѡ��phantomJs���滻chromeDriver
 * PhantomJs�ڱ��ؿ���ʱ�򻹺ã����Ҫ���𵽷��������ͱ�������linux�汾��PhantomJs,���window��������
 * @author zhuangj
 * @date 2017/11/14
 */
public class TestPhantomJsDriver {


    public static PhantomJSDriver getPhantomJSDriver(String phantomJSDriverExePath){
        //���ñ�Ҫ����
        DesiredCapabilities dcaps = new DesiredCapabilities();
        //ssl֤��֧��
        dcaps.setCapability("acceptSslCerts", true);
        //����֧��
        dcaps.setCapability("takesScreenshot", false);
        //css����֧��
        dcaps.setCapability("cssSelectorsEnabled", true);
        //js֧��
        dcaps.setJavascriptEnabled(true);
        //����֧��
        dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomJSDriverExePath + "phantomjs.exe");

        PhantomJSDriver driver = new PhantomJSDriver(dcaps);
        return  driver;
    }

    public static void main(String[] args) {
        /*WebDriver driver=getPhantomJSDriver();
        driver.get("http://www.baidu.com");
        System.out.println(driver.getCurrentUrl());*/
    	WebDriver driver = getPhantomJSDriver(null);
        // ����������� Baidu
        //driver.get("https://uae.souq.com/ae-en/login.php");
        // ���������Ҳ����ʵ��
        driver.navigate().to("https://uae.souq.com/ae-en/login.php/");
        // ��ȡ ��ҳ�� title
        System.out.println(" Page title is: " +driver.getTitle());
        // ͨ�� id �ҵ� input �� DOM
        WebElement element =driver.findElement(By.id("email"));
        // ����ؼ���
        //element.sendKeys("tamia-2017@outlook.com");
        element.sendKeys("betterbell2017@gmail.com");
        
        // ͨ�� id �ҵ� input �� DOM
        WebElement password =driver.findElement(By.id("password"));
        // ����ؼ���
        //password.sendKeys("jsby2017");
        password.sendKeys("zwhtboy22");
        // ͨ�� id �ҵ� input �� DOM
        WebElement siteLogin =driver.findElement(By.id("siteLogin"));
        // �ύ input ���ڵ� form
        siteLogin.submit();
        // ͨ���ж� title ���ݵȴ�����ҳ�������ϣ������
        /*new WebDriverWait(driver, 10).until(new ExpectedCondition() {
            @Override
            public Object apply(Object input) {
                return ((WebDriver)input).getTitle().toLowerCase().startsWith("������ש");
            }
        });
        // ��ʾ�������ҳ��� title
        System.out.println(" Page title is: " +driver.getTitle());*/
        
        
        Set<Cookie> cookies = driver.manage().getCookies();  
        
        System.out.println("Cookie.size = " + cookies.size());  
        
        for (Cookie c : cookies) {
        	//System.out.println(c.toString());
        }
        System.out.println("=====================================");
        driver.navigate().to("https://sell.souq.com/orders/order-management?tab=confirmed");
        
        cookies = driver.manage().getCookies();  
        
        System.out.println("Cookie.size = " + cookies.size());  
        
        String str = "";
        for (Cookie c : cookies) {
        	str += c.toString();
        }
        System.out.println(str);
        
        
        
        System.out.println("===========================================");
       
        driver.navigate().to("https://sell.souq.com/orders/getOrders?isFbs=false&interval=1&orderStatus=ALL_SHIPMENT&sortKey=orderDate&sortOrder=desc&page=0&size=20&filters%5Bstatus%5D=ALL_SHIPMENT");
        
        cookies = driver.manage().getCookies();  
        
        System.out.println("Cookie.size = " + cookies.size());  
        
         str = "";
        for (Cookie c : cookies) {
        	str += c.toString();
        }
        System.out.println(str);
    }
    
    public static String getCookieStr(String phantomJSDriverExePath) {
    	/*WebDriver driver=getPhantomJSDriver();
        driver.get("http://www.baidu.com");
        System.out.println(driver.getCurrentUrl());*/
    	//SiteSetting.jtb.append("׼����¼����...\n\r");
    	/*new Thread(new Runnable(){
    		public void run() {
    		try {
    			SiteSetting.jtb.append("׼����¼��������Լ��Ҫ2���ӣ����Ժ�...\n\r");
    			SiteSetting.jtb.paintImmediately(SiteSetting.jtb.getBounds());
    		Thread.sleep(1000); 
    		} catch (InterruptedException e) {
    		e.printStackTrace();
    		}
    		}
    	}).start();*/
    	WebDriver driver = getPhantomJSDriver(phantomJSDriverExePath);
        // ����������� Baidu
        //driver.get("https://uae.souq.com/ae-en/login.php");
        // ���������Ҳ����ʵ��
        driver.navigate().to("https://uae.souq.com/ae-en/login.php/");
        // ��ȡ ��ҳ�� title
        System.out.println(" Page title is: " +driver.getTitle());
        // ͨ�� id �ҵ� input �� DOM
        WebElement element =driver.findElement(By.id("email"));
        // ����ؼ���
        //element.sendKeys("tamia-2017@outlook.com");
        if (null == SiteSetting.username || SiteSetting.username.equals("")) {
        	System.out.println("SiteSetting.username is not SET!!!");
        	SiteSetting.username = "betterbell2017@gmail.com";
        }
        element.sendKeys(SiteSetting.username);
        
        
        
        // ͨ�� id �ҵ� input �� DOM
        WebElement password =driver.findElement(By.id("password"));
        // ����ؼ���
        //password.sendKeys("jsby2017");
        if (null == SiteSetting.password || SiteSetting.password.equals("")) {
        	System.out.println("SiteSetting.password is not SET!!!");
        	SiteSetting.username = "zwhtboy22";
        }
        password.sendKeys(SiteSetting.password);
        // ͨ�� id �ҵ� input �� DOM
        WebElement siteLogin =driver.findElement(By.id("siteLogin"));
        // �ύ input ���ڵ� form
        siteLogin.submit();
        // ͨ���ж� title ���ݵȴ�����ҳ�������ϣ������
        /*new WebDriverWait(driver, 10).until(new ExpectedCondition() {
            @Override
            public Object apply(Object input) {
                return ((WebDriver)input).getTitle().toLowerCase().startsWith("������ש");
            }
        });
        // ��ʾ�������ҳ��� title
        System.out.println(" Page title is: " +driver.getTitle());*/
        
        
        /*Set<Cookie> cookies = driver.manage().getCookies();  
        
        System.out.println("Cookie.size = " + cookies.size());  
        
        for (Cookie c : cookies) {
        	//System.out.println(c.toString());
        }
        System.out.println("=====================================");
        driver.navigate().to("https://sell.souq.com/orders/order-management?tab=confirmed");
        
        cookies = driver.manage().getCookies();  
        
        System.out.println("Cookie.size = " + cookies.size());  
        
        String str = "";
        for (Cookie c : cookies) {
        	str += c.toString();
        }
        System.out.println(str);
        
        
        
        System.out.println("===========================================");*/
        //SiteSetting.jtb.append("�Ѿ���¼����!\n\r");
        new Thread(new Runnable(){
    		public void run() {
    		try {
    			SiteSetting.jtb.append("�Ѿ���¼����!\n\r");
    			SiteSetting.jtb.paintImmediately(SiteSetting.jtb.getBounds());
    		Thread.sleep(1000); 
    		} catch (InterruptedException e) {
    		e.printStackTrace();
    		}
    		}
    	}).start();
        driver.navigate().to("https://sell.souq.com/orders/getOrders?isFbs=false&interval=&orderStatus=SHIPMENT_STATUS_CONFIRMED&sortKey=orderDate&sortOrder=desc&page=0&size=20");
        
        Set<Cookie> cookies = driver.manage().getCookies();  
        
        System.out.println("Cookie.size = " + cookies.size());  
        
        String str = "";
        for (Cookie c : cookies) {
        	str += c.toString();
        }
        System.out.println(str);
        //SiteSetting.jtb.append("�Ѿ�������ȡҳ�棬׼����ȡ...\n\r");
        new Thread(new Runnable(){
    		public void run() {
    		try {
    			System.out.println("�Ѿ�������ȡҳ�棬׼����ȡ...\n\r");
    			SiteSetting.jtb.append("�Ѿ�������ȡҳ�棬׼����ȡ...\n\r");
    			SiteSetting.jtb.paintImmediately(SiteSetting.jtb.getBounds());
    		Thread.sleep(1000); 
    		} catch (InterruptedException e) {
    		e.printStackTrace();
    		}
    		}
    	}).start();
        return str;
    }
}