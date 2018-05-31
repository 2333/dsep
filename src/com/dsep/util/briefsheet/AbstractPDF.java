package com.dsep.util.briefsheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

public abstract class AbstractPDF{
	protected String path;
	protected String name;
	protected String templateName;
	protected Map<String, Object> params;
	public abstract Map<String, String> generate(String htmlString) throws Exception;
	
	public AbstractPDF(String path) {
		super();
		this.path = path;
	}
	public AbstractPDF(String path, String templateName) {
		super();
		this.path = path;
		this.templateName = templateName;
	}	
	protected File loadFile() throws FileNotFoundException{
		File outputDir = new File(this.path);
		if (!outputDir.exists()) {  
	            outputDir.mkdirs();  
	    } 			
		File outputFile = new File(this.path, this.name);
		return outputFile;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getTemplateName() {
		return templateName;
	}

	public Map<String, Object> getParams() {
		return params;
	}
}
