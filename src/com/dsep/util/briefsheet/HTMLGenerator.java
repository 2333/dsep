package com.dsep.util.briefsheet;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Locale;

import com.dsep.util.export.briefsheet.FreemarkerConfig;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HTMLGenerator {
	public static String generateHTMLString(String templateFileName,  Object dataModel){
		String htmlContent = null;       	    
       	try{
       		StringWriter stringWriter = new StringWriter();
       		BufferedWriter writer = new BufferedWriter(stringWriter);
        	Configuration config = FreemarkerConfigurer.getConfiguation();  
       		Template tp = config.getTemplate(templateFileName, Locale.CHINA, "UTF-8");
       		tp.setEncoding("UTF-8");
       		System.out.println(tp.getEncoding());
       		tp.process(dataModel, writer);
       		writer.flush(); 
       		htmlContent = stringWriter.toString();     
       		return htmlContent; 
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			
		}
		return htmlContent; 
	}
}
