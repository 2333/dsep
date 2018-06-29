package com.dsep.util.export.briefsheet;

import java.io.File;
import java.net.URL;
//util class to get resource
public class ResourceLoader {
	public static String CLASS_PATH_PREFIX ="classpath:";

	  /**
	   * classpath中获取资源
	   * @Title: getResource
	   * @Description: classpath中获取资源
	   * @param resource
	   * @return
	   */
	   public static URL getResource(String resource) {
	    ClassLoader classLoader = null;
	    classLoader = Thread.currentThread().getContextClassLoader();
	    return classLoader.getResource(resource);
	  } 
	  public static String getBasicPath(){
		  URL url = getBasicResour();
		  if(url==null)
			  return null;
		  return url.getPath().replaceAll("%20", " ");
	  }
	  public static URL getBasicResour(){
		  ClassLoader classLoader = null;
		    classLoader = Thread.currentThread().getContextClassLoader();
		    return classLoader.getResource("../../");
	  }
	  
	  /**
	   *  classpath 中搜索路径
	   * @Title: getPath
	   * @Description: 
	   * @param resource
	   * @return
	   */
	  public static String getPath(String resource){
		  if(resource!=null){
			  if(resource.startsWith(CLASS_PATH_PREFIX)){
				  resource = getPath("")+resource.replaceAll(CLASS_PATH_PREFIX, "");
			  }
		  }
		  
		  URL url = getResource(resource);
		  if(url==null)
			  return null;
		  return url.getPath().replaceAll("%20", " ");
	  }
	  /**
	   * 
	   * @Title: getPath
	   * @Description: 
	   * @param resource
	   * @param clazz
	   * @return
	   */
	  public static String getPath(String resource,Class clazz){
		  URL url = getResource(resource, clazz);
		  if(url==null)
			  return null;
		  return url.getPath().replaceAll("%20", " ");
	  }
	  
	  /**
	   * 指定class中获取资源
	   * @Title: getResource
	   * @Description: 指定class中获取资源
	   * @param resource
	   * @param clazz
	   * @return
	   */
	  public static URL getResource(String resource,Class clazz){
		  return clazz.getResource(resource);
	  }
	  
		/**
		 * If running under JDK 1.2 load the specified class using the
		 * <code>Thread</code> <code>contextClassLoader</code> if that fails try
		 * Class.forname. Under JDK 1.1 only Class.forName is used.
		 * 
		 */
		public static Class loadClass(String clazz) throws ClassNotFoundException {
			return Class.forName(clazz);
		}
		
		public static String getCommonURL(){
			ClassLoader classLoader = null;
			classLoader = Thread.currentThread().getContextClassLoader();
			File file = new File(classLoader.getResource("").toString());
			String url = file.getParent();
			file.delete();
			url = url.substring(6);
			File file2 = new File(url);
			String url2 = file2.getParent();
			file2.delete();
			File file3 = new File(url2);
			String url3 = file3.getParent();
			file3.delete();
			url3 = url3.replace("\\", "/");
			System.out.println(url3);
			return url3;
		}
}
