package com.dsep.util.publiccheck.normalization;

import java.util.LinkedList;
import java.util.List;

//英文名称的情况还没有考虑
/**
 * 预处理完成人姓名  tested
 * 本函数可以处理由中文逗号，英文逗号,中文分号；英文分号;顿号、空格六种特殊字符分隔的中文人名，本函数还可以识别被空格的两字人名
 * @author YuYaolin
 *
 */
public class PubPretreatPersonName {

	/**
	 * 预处理名字字符串，将拆分的名字以List的形式返回
	 * @param str
	 * @return
	 */
	public static String pretreat(String str){
		
		List<String> names = new LinkedList<String>(); //返回拆分结果
		String namesStr="";
		StringBuffer name = new StringBuffer();      //缓存一个名字	
		List<String> sbs= new LinkedList<String>();
		str=str.trim();
		
		if(isBlank(str))
			return null;
		
		for(int i=0;i<str.toCharArray().length;i++){
			
			char cc=str.toCharArray()[i];
			
			if(!isSplitCode(cc)){                      //如果该字符不是特殊字符		
				name.append(cc);
				
				if(i==str.toCharArray().length-1){
				//	names.add(name.toString());
					namesStr=namesStr+name.toString();
				}
				
			}else{
				if(name.length()>1){		
				//	names.add(name.toString());
					namesStr=namesStr+name.toString()+",";
					name.delete(0, name.length());
					
				}else if(name.length()==1){                //空格的ASC码是32，
					String singleWord = "";
					singleWord = name.toString();
					if(sbs.size()==0&&cc==32){               //空格的ASC码是32，把名字形式为X Y的第二个字添加进去
						sbs.add(singleWord);
						name.delete(0, name.length());
					}
					else if(sbs.size()==1){
						
						String strname="";
						strname+=sbs.get(0).toString();
						strname=strname+singleWord;
						name.delete(0, name.length());
				//		names.add(strname); //把用空格分隔的两字名字添加进去
						namesStr=namesStr+strname+",";
						sbs.clear();
						name.delete(0, name.length());
					}
					else ;
				}
			}
		}
		
		return namesStr;
	}
	
	/**
	 * 定义哪些字符是特殊字符，特殊字符就是用来分隔名字的字符
	 * @param c
	 * @return
	 */
	public static boolean isSplitCode(char c){
		
		List<Character> specialCodes = new LinkedList<Character>();
		
		specialCodes.add(',');          //英文逗号
		specialCodes.add('，');         //中文逗号
		specialCodes.add(';');          //英文分号
		specialCodes.add('；');          //中文分号
		specialCodes.add('、');          //顿号
		specialCodes.add(' ');           //空格
		
		if(specialCodes.contains((Character)c))
			return true;
		else
			return false;
	}
	private static boolean isBlank(String head){
		
		if(head.equals(""))
			 return true;
		 else if(head.contains("看不清"))
			 return true;
		 else
			 return false;
	 }
	/***********以下为测试代码******************************/
	/*public static void main(String [] args){
		List<String> nameList=new LinkedList<String>();		
		String nameStr1 = "马  融  张喜莲  刘  璇   薛  征  魏小维  李新民  董承超  刘琳琳  晋文蔓  王亚雷";
		System.out.println(nameStr1);
		System.out.println(nameStr1.length());
		String result1=pretreat(nameStr1);
		System.out.println(result1);
		
		int count=0;
		for(int i=0;i<result1.toCharArray().length;i++){
			if(result1.toCharArray()[i]==',')
				count++;
		}
		String resultArr[]=new String[count];
		resultArr=result1.split(",");
		for(int i=0;i<resultArr.length;i++){
			System.out.println(i+":  "+resultArr[i]);
		}
		System.out.println("*************************");
		String nameStr2 = "黄烽、张江林、朱剑、杨春花、郭军华、邓小虎、王炎焱、赵征、赵伟、李晓兰 ";
		System.out.println(nameStr2);
		System.out.println(nameStr2.length());
		String result2=pretreat(nameStr2);
		System.out.println(result2);
		System.out.println("*************************");
		nameList.add(nameStr2);
		String nameStr3 = "尹  江，马  恢，张希近，杜  珍，温利军，左庆华，齐海英，王晓明，杜培兵，高永龙  ";
		System.out.println(nameStr3);
		System.out.println(nameStr3.length());
		String result3=pretreat(nameStr3);
		System.out.println(result3);
		System.out.println("*************************");
		
		List<List<String>> resulta = new LinkedList<List<String>>();
//		resulta=pretreat(nameList);
		
		for(int k=0;k<resulta.size();k++){
			List<String> result = resulta.get(k);
			System.out.println(result.size());
			for(int i=0;i<result.size();i++){
				System.out.println(i+ " "+result.get(i));
			}
		}
		
	}*/
	/***********以上为测试代码******************************/
}
