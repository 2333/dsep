package com.dsep.util.datacheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 论文抽查算法
 * @author lubin
 *
 */
public class PaperInspectionAlgorithm {

	/**
	 * 论文抽查函数
	 * @param map 学校代码和学科代码键值对，用来表示一对多
	 * @param disciplinesID 学校代码
	 * @return
	 */
	public static Map<String,List<String>> disciplineRandomInspection(Map<String,List<String>> map,List<String> disciplinesID){
		Map<String,List<String>> result = new HashMap<String,List<String>>();
		for(int i=0;i<disciplinesID.size();i++){
			String collegeID = disciplinesID.get(i);
			result.put(collegeID, randomInspection(map.get(collegeID)));
		}
		return result;
	}
	
	/**
	 * 根据学科代码返回抽取中的学科集合
	 * @param disciplinesID 学科代码
	 * @return
	 */
	public static List<String> randomInspection(List<String> disciplinesID){
		int length = disciplinesID.size();
		List<Integer> index = new ArrayList<Integer>();
		switch(length/10){
		case 0:
			index = randomFunction(length,1);
			break;
		case 1:
		case 2:
			index = randomFunction(length,2);
			break;
		case 3:
		default:
			index = randomFunction(length,3);
		}
		List<String> result = new ArrayList<String>();
		for(int n : index)
			result.add(disciplinesID.get(n));
		return result;
	}
	
	/**
	 * 随机抽取学科的核心函数
	 * @param size 所要抽取的学科总数
	 * @param number 所要抽取的学科数
	 * @return
	 */
	public static List<Integer> randomFunction(int size,int number){		
		List<Integer> list = new ArrayList<Integer>();
		int []x=new int[number+1];
		int s=size-number+1;
		int t=s/(int)(number+1);
		for(int i=0;i<number;i++){
			x[i]=t;
		    s=s-t;
		}
		x[number]=s;
		for(int i=0;i<x.length;i++){
			//将数组x拆解，即抽取范围为x[i]的随机数，并放入x[i+1]中，依次类推
			int g=(int)(Math.random()*(x[i]));
			if(i==number){
				x[number]-=g;
				x[0]+=g;
			}
			else if(i==0){
				x[0]-=g;
				x[number]+=g;
			}
			else{
				x[i]-=g;
				x[i+1]+=g;
		    }
		}
		for(int i=x.length-1;i>=0;i--){
			int g=(int)(Math.random()*(x[i]));
			if(i==0){
				x[0]-=g;
				x[number]+=g;
			}
			else if(i==0){
				x[0]-=g;
				x[number]+=g;
			}
			else{
				x[i]-=g;
				x[i-1]+=g;
			}
		}
		int z=0;
		for(int j=0;j<number;j++){
			z=z+x[j];
			list.add(z);
			z++;
		}   
		return list;
	}
}
