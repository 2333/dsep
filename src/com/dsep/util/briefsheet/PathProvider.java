package com.dsep.util.briefsheet;

import java.net.URL;

import com.dsep.dao.base.StorageDao;
import com.dsep.util.Configurations;

public class PathProvider {
	private StorageDao storageDao;
	public static URL getClassResource(String resourcePath){
		ClassLoader classLoader= Thread.currentThread().getContextClassLoader();
		return classLoader.getResource(resourcePath);
	}
	public static URL getBasicResource(String resource){
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		String subRes = resource.startsWith("/") ?
						resource.substring(1) : resource;
		return classLoader.getResource("../../" + subRes);
	}
	public static String getClassPath(){
		return PathProvider.getClassResource("").getPath().replace("%20", " ");
	}
	public static String getBasicPath(){
		return PathProvider.getBasicResource("").getPath().replace("%20", " ");
	}
	public static String getTemplateRootPath(){
		String tempatesPath = Configurations.getTemplateRootPath();
		return tempatesPath;
	}
	public static String getStoreRootPath(){
		String storeRoot = getTemplateRootPath();
		return storeRoot;
	}
	public static String getImagePath() {
		// TODO Auto-generated method stub
		return getTemplateRootPath();
	}
}
