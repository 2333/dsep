package com.dsep.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.xfire.client.Client;

public class WebServiceUtil {
	/**
	 * 根据服务类型获得Web服务的地址
	 * @param serviceType "report,报告"
	 * @return
	 */
	private static String getWebAddress()
	{
		return Configurations.getWinReportUrl();
	}
	/**
	 * 调用webservice服务
	 * @param serviceType，服务类型，"report,为学科报告"
	 * @param serviceName，服务名称，"Generate，为生成报告"
	 * @param args, 该web服务需要的阐述
	 * @return web服务的返回值
	 */
	public static Object[] invoke(String serviceName, Object[] args) {
		try {
			Client c=new Client(new URL(getWebAddress()));
			Object[] result=c.invoke(serviceName, args);
			return result;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
