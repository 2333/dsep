package com.dsep.util.export.briefsheet;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import freemarker.template.Configuration;

import com.dsep.util.briefsheet.PathProvider;
import com.dsep.util.export.briefsheet.ResourceLoader;
public class FreemarkerConfig {
	private static Configuration config = null;

	/**
	 * 获取 FreemarkerConfiguration
	 * 
	 * @Title: getConfiguation
	 * @Description:
	 * @return
	 * @author lihengjun 修改时间： 2013年11月11日 下午5:27:32 修改内容：新建
	 */
	public static synchronized Configuration getConfiguation() {
		if (config == null) {
			setConfiguation();
		}
		return config;
	}
	/**
	 * 设置 配置
	 * @Title: setConfiguation
	 * @Description: 
	 * @author lihengjun
	 * 修改时间： 2013年11月5日 下午3:25:42
	 * 修改内容：新建
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
