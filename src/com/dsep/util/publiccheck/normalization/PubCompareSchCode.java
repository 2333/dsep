package com.dsep.util.publiccheck.normalization;
/**
 * 比对学校代码      tested
 * @author YuYaolin
 *
 */
public class PubCompareSchCode {

	public static boolean compare(String local_unitID,String codes){
		boolean result=false;
		String [] codeArr=null;
		local_unitID=local_unitID.replace(" ", "");
		if(codes==null)
			return true;
		codeArr=codes.split("#");
		for(int i=0;i<codeArr.length;i++){
			if(local_unitID.equals(codeArr[i])){
				result=true;
				break;
			}
		}
		return result;
	}
	/*public static void main(String[] args){
		String localValue="10007";
		String pubValue="10006#10010#10003#20312#";
		boolean result=compare(localValue,pubValue);
		System.out.println(result);
	}*/
}
