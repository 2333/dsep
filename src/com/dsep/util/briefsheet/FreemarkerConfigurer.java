package com.dsep.util.briefsheet;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.dsep.util.export.briefsheet.ResourceLoader;

import freemarker.template.Configuration;

public class FreemarkerConfigurer {
	private static Configuration config = null;
	/**
	 * 获取配置信息，如果没有定义，则设置配置信息
	 * @return
	 */
	public static synchronized Configuration getConfiguation() {
		if (config == null) {
			setConfiguation();
		}
		return config;
	}
	/**
	 * 私有函数，设置freemerker的配置信息，包括模板根目录，字符集信息
	 */
	private static void setConfiguation() {
		config = new Configuration();
		String path = PathProvider.getTemplateRootPath();
		System.out.println("path="+path);
		try {
			config.setDirectoryForTemplateLoading(new File(path));
			config.setLocale(Locale.CHINA);  
            config.setDefaultEncoding("utf-8");  
            config.setEncoding(Locale.CHINA, "utf-8");  
            System.out.println("in set config");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
