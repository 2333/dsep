package com.dsep.util.publiccheck;

/**
 * 公共库比对的工具函数， resultType为1，表示不在公共库中，resultType为2，表示本条数据完全匹配，resultType为3，本条数据存在异议
 * @author yuyaolin
 * 
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.dsep.util.datacheck.CheckSimByCompositive;

public class CheckPublicLibrary {

	// resultType为1，表示不在公共库中
	// resultType为2，表示本条数据完全匹配
	// resultType为3，本条数据存在异议
	private static ArrayList<Map<String, String>> resultList = new ArrayList<Map<String, String>>();

	/**
	 * 本地数据链表与公共库数据链表对比
	 * 
	 * @param listOfLocal
	 *            本地数据链表
	 * @param listOfPubLab
	 *            公共库数据链表
	 * @param listKey
	 *            关键字段名称集合
	 * @return 每条数据项比对的结果链表    
	 */
	public ArrayList<Map<String, String>> check(
			LinkedList<LinkedHashMap<String, String>> listOfLocal,
			List<Map<String, String>> listOfPubLab,
			LinkedHashMap<String, String> keys) {

		// LinkedHashMap<String, String> oneLocalItem = listOfLocal.get(0);
		if (listOfLocal.size() != 0 ) {
			for (int i = 0; i < listOfLocal.size(); i++) {
				LinkedHashMap<String, String> localItem = listOfLocal.get(i);
				checkItem(localItem, listOfPubLab, keys);
			}
		}

		return this.resultList;
	}

	/**
	 * 将一条本地数据，与公共数据库比较
	 * 
	 * @param localItem
	 *            正在比对的这条本地数据
	 * @param listOfPubLab
	 *            公共库数据链表
	 * @param listKey
	 *            关键字段集合
	 * @param listKey
	 *            关键字段名称集合 resultType
	 *            为1表示不在公共库，为2表示有一条完全匹配的数据，为3表示有与之关键字段相同的相似数据
	 */
	public void checkItem(LinkedHashMap<String, String> localItem,
			List<Map<String, String>> listOfPubLab,
			LinkedHashMap<String, String> keys) {

		Map<String, String> result = new LinkedHashMap<String, String>();
		ArrayList<Integer> locationList = new ArrayList<Integer>(); // 存储所有关键字段与之相同的公共数据项ID
		String localItemID = null;

		Iterator iter = localItem.entrySet().iterator();
		Map.Entry entry1 = (Map.Entry) iter.next(); // Map的第一项存的是ID，这个不用比较，直接从第二项开始比较
		String val1 = (String) entry1.getValue();
		localItemID = val1;
		// /*===*/ System.out.println("开始定位关键字段：");
		// 遍历一遍公共库，找出所有与本条数据关键字段相同的数据
		for (int i = 0; i < listOfPubLab.size(); i++) { // 第一个key是ID
			// /*===*/ System.out.println(i); // 用来对这条记录进行标记的,不用比对，从第二个Map开始比对
			Map<String, String> pubItem = listOfPubLab.get(i);
			if (checkKeyField(localItem, pubItem, keys)) {
				locationList.add(i); // 将关键字段与本条数据相同的公共数据的位置存进
				// /*===*/ System.out.println("定位关键字段相同的序号："+i);
			}
		}
		// for(int i=0;i<locationList.size();i++){
		// System.out.println("定位关键字段相同的序号："+i);
		// }
		// ///////////////////////
		// 比对非关键字段
		Set<String> setkey = keys.keySet();

		if (locationList.size() == 0) { // 没有在公共库中找到关键字段相同的数据

			result.put("localItemID", localItemID);
			result.put("resultType", "1"); // resultType为1，表示不在公共库中
			this.resultList.add(result);

		} else if (locationList.size() == 1) { // 在公共库中找到了一条关键字段相同的数据

			int index = locationList.get(0);
			result = checkNotKeyField(localItem, listOfPubLab.get(index),
					setkey);
			this.resultList.add(result);

		} else if (locationList.size() > 1) { // 在公共库中找到了多条关键字段相同的数据

			ArrayList<Map<String, String>> list1 = new ArrayList<Map<String, String>>();
			boolean isFindCompleteMatch = false;
			for (int i = 0; i < locationList.size(); i++) {
				int index = locationList.get(i);
				result = checkNotKeyField(localItem, listOfPubLab.get(index),
						setkey);
				if (result.get("resultType") == "2") { // 如果有一条数据完全配对，忽略其余的数据
					isFindCompleteMatch = true;
					this.resultList.add(result);
					return;
				}
				list1.add(result);
			}
			if (isFindCompleteMatch == false) { // 如果没有数据完全配对，就把所有的比对结果都返回
				this.resultList.addAll(list1);
			}
		} else {
			// 抛出异常！
		}

	}

	/**
	 * 对于关键字段相等的本地数据项和公共库数据项，对比其非关键字段，返回比对结果
	 * 
	 * @param localItem
	 *            一条本地数据
	 * @param pubItem
	 *            一条公用库数据
	 * @param listKey
	 *            关键字段名称集合
	 * @return 返回比对结果，封装在一个Map中
	 */
	public Map<String, String> checkNotKeyField(
			LinkedHashMap<String, String> localItem, // 检查其余的非关键字段
			Map<String, String> pubItem, Set<String> setKey) {

		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
		Iterator iter = localItem.entrySet().iterator();
		String localItemID;

		Map.Entry entry1 = (Map.Entry) iter.next(); // Map的第一项存的是ID，这个不用比较，直接从第二项开始比较
		String val1 = (String) entry1.getValue();
		localItemID = val1;

		while (iter.hasNext()) { // 遍历所有非关键字段
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			if (!(setKey.contains(key)) && !(val.equals(pubItem.get(key)))) {// 本字段不是关键字段,且与公共库的值不相同
				result.put("localItemID", localItemID);
				result.put("resultType", "3"); // resultType为3，本条数据存在异议
												// 在这里可以把数据不一致的具体情况表示出来
				result.put("pubLabItemID", pubItem.get("ID")); // 所给的PubLabItem中数据的存储公共数据的ID的key是什么？？？
				return result;
			}
		}
		if (result.size() == 0) {
			result.put("localItemID", localItemID);
			result.put("resultType", "2"); // resultType为2，表示本条数据完全匹配
			result.put("pubLabItemID", pubItem.get("ID")); // 所给的PubLabItem中数据的存储公共数据的ID的key是什么？？？
		}
		return result;
	}

	/**
	 * 将一条本地数据，对比一条公共库数据，查看关键字段是否相等
	 * 
	 * @param localItem
	 *            一条本地数据
	 * @param pubItem
	 *            一条公共库数据项
	 * @param listKey
	 *            关键字段名称集合
	 * @return 如果关键字段相同，返回true，否则返回false
	 */
	public boolean checkKeyField(LinkedHashMap<String, String> localItem, // 检查关键字段是否相等
			Map<String, String> pubItem, LinkedHashMap<String, String> keys) {

		// Iterator iter = localItem.entrySet().iterator();

		// System.out.println("进入到比对关键字段函数");
		Iterator iter = keys.entrySet().iterator();
		// System.out.println("进入到比对关键字段函数");
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = (String) entry.getKey();
			String keyType = (String) entry.getValue();

			String localVal = localItem.get(key);
			String pubVal = pubItem.get(key);

			if (localVal == null || pubVal == null)
				return false;
			// 不同数据类型的关键字段采用给不同的对比方法
			if (keyType.equals("0")) { // 0表示此关键字是名字等短类型
				if (!localVal.equals(pubVal))
					return false;
			} else if (keyType.equals("1")) { // 1表示关键字是项目名称等长字符串类型
				if (CheckSimByCompositive.checkSimOfTwoString(localVal, pubVal) <= 0.95)
					return false;
			} else {
				// 抛出异常！！
			}
		}

		return true;
	}

	/*
	 * public static void main(String args[]){ CheckPublicLibrary
	 * checkPublicLibrary = new CheckPublicLibrary();
	 * 
	 * LinkedList<LinkedHashMap<String, String>> listOfLocal = new
	 * LinkedList<LinkedHashMap<String, String>>(); List<Map<String, String>>
	 * listOfPubLab = new LinkedList<Map<String, String>>();
	 * LinkedHashMap<String,String> keys = new LinkedHashMap<String,String>();
	 * LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
	 * LinkedHashMap<String, String> map1 = new LinkedHashMap<String, String>();
	 * LinkedHashMap<String, String> map11 = new LinkedHashMap<String,
	 * String>(); map.put("ID", "12311");map.put("Name", "张三");map.put("Year",
	 * "1988");map.put("Rank", "1"); map1.put("ID", "12312");map1.put("Name",
	 * "张四");map1.put("Year", "1988");map1.put("Rank", "1"); map11.put("ID",
	 * "12313");map11.put("Name", "张五");map11.put("Year",
	 * "1979");map11.put("Rank", "2"); listOfLocal.add(map);
	 * listOfLocal.add(map1); listOfLocal.add(map11); Map<String,String> map2
	 * =new LinkedHashMap<String, String>(); Map<String,String> map3 =new
	 * LinkedHashMap<String, String>(); Map<String,String> map33 =new
	 * LinkedHashMap<String, String>(); Map<String,String> map24 =new
	 * LinkedHashMap<String, String>(); map2.put("ID", "12321");map2.put("Name",
	 * "张三");map2.put("Year", "1988");map2.put("Rank", "1"); map3.put("ID",
	 * "12322");map3.put("Name", "张三");map3.put("Year", "1956");map3.put("Rank",
	 * "1"); map33.put("ID", "12323");map33.put("Name", "张五");map33.put("Year",
	 * "1956");map33.put("Rank", "1"); map24.put("ID",
	 * "12324");map24.put("Name", "张五");map24.put("Year",
	 * "1979");map24.put("Rank", "1");
	 * 
	 * listOfPubLab.add(map2); listOfPubLab.add(map3); listOfPubLab.add(map33);
	 * listOfPubLab.add(map24); keys.put("Name", "0");
	 * checkPublicLibrary.check(listOfLocal, listOfPubLab, keys); //
	 * checkPublicLibrary.checkItem(map, listOfPubLab, keys);
	 * System.out.println(resultList.size()); for(int
	 * i=0;i<resultList.size();i++){ Map result=resultList.get(i); Iterator
	 * iter=result.entrySet().iterator(); while (iter.hasNext()){ Map.Entry
	 * entry = (Map.Entry) iter.next(); String key = (String) entry.getKey();
	 * String keyType = (String) entry.getValue();
	 * System.out.println(key+" : "+keyType);
	 * 
	 * } System.out.println("---------------------"); } }
	 */
}
