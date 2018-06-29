package com.dsep.util.publiccheck.normalization;
/**
 * 比对姓名                              tested 
 * @author YuYaolin
 *
 */
public class PubComparePersonName {
	public static boolean compare(String localName,String namesStr){
		localName=localName.replace(" ", "");
		String []namesArr;
		int count=0;
		for(int i=0;i<namesStr.toCharArray().length;i++){
			if(namesStr.toCharArray()[i]==',')
				count++;
		}
		namesArr=new String[count];
		namesArr=namesStr.split(",");
		for(int i=0;i<=count;i++){
			if(localName.equals(namesArr[i]))
				return true;
		}
		return false;
		
	}
	
	public static void main(String args[]){
		String name="张三";
	    String nameStr="张三,李四,王麻子";
	    boolean result=compare(name,nameStr);
	    System.out.println(result);
	}
}
