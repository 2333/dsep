package com.dsep.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TextProcess {
	/**
	 * 根据相对路径获取绝对路径
	 * @param path 相对路径
	 * @return
	 * @throws IOException
	 */
	static String getAbsolutePath(String path) throws IOException{
		File directory = new File("");
		String rootPath = directory.getCanonicalPath();
		rootPath += path;
		return rootPath;
	}
	
	/**
	 * 根据相对路径以流格式获取html页面或jsp页面的内容
	 * @param path 相对路径,如'/WebContent'表示WebContent根目录
	 * @return
	 * @throws IOException
	 */
	public static String getContentByRelativePath(String path) throws IOException {
		return getContentByAbsolutePath(getAbsolutePath(path));
	}
	
	/**
	 * 根据绝对路径以流格式获取html页面或jsp页面的内容
	 * @param path 相对路径,如'/WebContent'表示WebContent根目录
	 * @return
	 * @throws IOException
	 */
	public static String getContentByAbsolutePath(String path) throws IOException {
		InputStreamReader isReader = null;
		BufferedReader bufReader = null;
		StringBuffer buf = new StringBuffer();
		File file = new File(path);
		isReader = new InputStreamReader(new FileInputStream(file), "utf-8");
		bufReader = new BufferedReader(isReader, 1);
		String data;
		while ((data = bufReader.readLine()) != null) {
			buf.append(data);
		}
		// TODO 关闭流
		isReader.close();
		bufReader.close();
		return buf.toString();
	}
}