package com.dsep.util.export.briefsheet;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HtmlGenerator {
    /**   
     * Generate html string.   
     *    
     * @param template   the name of freemarker template.   
     * @param variables  the data of template.   
     * @return htmlStr   
     * @throws IOException 
     * @throws TemplateException 
     * @throws Exception   
     */    
    public String generate(String template, Map<String,Object> variables) throws IOException, TemplateException{     
        BufferedWriter writer = null;   
        try{
        	//FreekarkerConfig自定义类
        	Configuration config = FreemarkerConfig.getConfiguation();     
        	Template tp = config.getTemplate(template, "UTF-8");
        	tp.setEncoding("UTF-8");
        	StringWriter stringWriter = new StringWriter(); 
        	//writer = new BufferedWriter(stringWriter);  
        	
        	System.out.println(tp.getEncoding());
        	tp.process(variables, stringWriter); 
        	String htmlContent = stringWriter.toString();     
            return htmlContent; 
        }finally{
        	if(writer!=null)
        		writer.close();     
        }
    
    }     
    
}
