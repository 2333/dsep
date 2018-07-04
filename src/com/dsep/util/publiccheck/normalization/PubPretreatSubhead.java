package com.dsep.util.publiccheck.normalization;

/**
 * 处理副标题
 * @author YuYaolin
 *
 */
public class PubPretreatSubhead {

	 /**
	  * 处理一个副标题  tested
	  * @param head
	  * @return  返回的标题形式为head%%标题内容&&&&&sub%%副标题内容   返回
	  */
	 public static String pretreat(String head){
		 head=head.replace(" ", "");
		 if(isBlank(head)){
			 return null;
		 }
		 
		 String result = "";
		 
		 if(identHeadType(head)!=0&&identEditionType(head)==0){         //包含副标题，二不包含版本
			 if(head.contains("-")){
				 head=head.replace(" ", "");
				 
				 int index1=head.indexOf("-");
				 int index2=head.lastIndexOf("-");
				 
				 String mainhead=head.substring(0,index1);
				 String subhead=head.substring(index2+1, head.length());
				 result="main%%"+mainhead+"$$$$$"+"sub%%"+subhead;
			 }
			 
			 else if(head.contains("——")){
				 head=head.replace(" ", "");
				 
				 int index1=head.indexOf("——");
				 int index2=head.lastIndexOf("——");
				 
				 String mainhead=head.substring(0,index1);
				 String subhead=head.substring(index2+1, head.length());
				 
				 result="main%%"+mainhead+"$$$$$"+"sub%%"+subhead;
			 }
		 }
		 
		 else if(identHeadType(head)==0&&identEditionType(head)!=0){
			 if(head.contains("(")&&head.contains(")")){
				 String edition=head.substring(head.indexOf("(")+1,head.indexOf(""));
				 result="main%%"+head.substring(0,head.indexOf("("))+"$$$$$"+"sub%%"+edition;
			 }
		 }
		 else if(identHeadType(head)!=0&&identEditionType(head)!=0){ 
			 	//既有副标题又有版本de比对
			 int indexSub1;
			 int indexSub2;
			 if(head.contains("-"))
				 indexSub1=head.lastIndexOf("-");
			 else /*if(head.contains("——"))*/
				 indexSub1=head.lastIndexOf("——");
			 indexSub2=head.indexOf("(");
			 String subhead=head.substring(indexSub1+1, indexSub2);
			 String edition=head.substring(head.indexOf("(")+1,head.indexOf(""));
			
		 }
		 else{
			 result=head;
		 }
		 return result;
	 }
	 
	 /**
	  * 判断一个标题是不是有副标题
	  * @param head
	  * @return  0表示没有副标题
	  */
	 private static int identHeadType(String head){
		 if(head.contains("-"))
			 return 1;
		 else if(head.contains("——"))
			 return 1;
		 else 
			 return 0;
	 }
	 
	 /**
	  * 判断一个标题中是否标注了版本
	  * @param head
	  * @return
	  */
	 private static int identEditionType(String head){

		 if(head.indexOf("版")==head.length()-1)
			 return 1;
		 else
			 return 0;
	 }
	 
	 private static boolean isBlank(String head){
		 if(head.equals(""))
			 return true;
		 else if(head.contains("看不清"))
			 return true;
		 else
			 return false;
	 } 
	 public static void main(String[]args){
		 String head1="测试主标题---副标题测试";
		 String result1=pretreat(head1);
		 System.out.println(result1);
		 String head2="测试主标题---副标题测试(第二版)";
		 String result2=pretreat(head2);
		 System.out.println(result2);
	 }
}
