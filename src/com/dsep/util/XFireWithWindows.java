package com.dsep.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.codehaus.xfire.client.Client;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class XFireWithWindows {
	public static String generatorReport(String[] args) {
		// TODO Auto-generated method stub
		try {
			Client c=new Client(new URL("http://192.168.3.75:8089/WebService1.asmx?wsdl"));
			Object[] o=c.invoke("Generate", args);
			//Client c=new Client(new URL("http://61.159.228.48/Service.asmx?wsdl"));			
			//Object[] o=c.invoke("getName", new String[]{"364523428"});
			System.out.println((String)o[0]);
			return((String)o[0]);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	public static String generatorWinReport(String unitId,String discId,
			String reportType,String introUrl){
		try{
			//Client client = new Client(new URL(Configurations.getWinReportUrl()));
			Client client = new Client(new URL("http://192.168.3.112:81/ReportWS.asmx?wsdl"));
			Object[] result = client.invoke("Generate", new String[]{unitId,discId,reportType,introUrl});
			return result[0].toString();
		}catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
