package com.meta.entity;

import java.io.Serializable;

import com.dsep.common.exception.BusinessException;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

/**
 * 百分比数据类型
 * @author thbin
 * @version 2014-02-26
 */
public class MetaPercentData extends MetaObject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2292022008846911191L;
	//变为3个字段：类型（char(1)，'0'-次序，'1'-百分比;缺省'0'），数量（int, 缺省1），本单位值（VARCHAR2(10)，缺省'1'）
	private String type;
	private int num;
	private String value;
	
	static public String getTypeColumn(String columnName){
		return columnName + "_TYPE";
	}
	static public String getNumColumn(String columnName){
		return columnName + "_NUM";
	}
	static public String getValueColumn(String columnName){
		//return columnName + "_VALUE";
		return columnName;
	}
	@Override
	public String toString() {
		if(type.equals("0")){
			return num + "(" + value+")";
		}else{
			return num + "[" + value+"]";			
		}
	}
	/**
	 * 构造函数
	 * @param type 数据类型，字符串形式表示，0-代表次序，1-百分比
	 * @param num 总数量， 整数类型
	 * @param value 实际取值
	 */
	public MetaPercentData(String type, int num, String value) {
		this.type = type;
		this.num = num;
		this.value = value;
	}
	public MetaPercentData(String origValue){
		//检查格式是否正确
		int startPos = -1;
		int endPos = -1;
		try{
			startPos = origValue.indexOf("(");
			if(startPos>0){
				this.type = "0";
				this.num = Integer.parseInt(origValue.substring(0, startPos));
				endPos = origValue.indexOf(")");
				if(endPos>startPos+1){
					this.value = origValue.substring(startPos+1, endPos);
				}
			}else{
				startPos = origValue.indexOf("[");
				if(startPos>0){
					this.type = "1";
					this.num = Integer.parseInt(origValue.substring(0, startPos));
					endPos = origValue.indexOf("]");
					if(endPos>startPos+1){
						this.value = origValue.substring(startPos+1, endPos);
					}
				}
				
			}
		}catch(Throwable ex){
			throw new BusinessException("百分位排位数据类型格式错误，无法转换！");
		}
	}
	/**
	 * 此百分比类型是否是单位顺序
	 * @return 
	 */
	public boolean IsSeqNo(){
		if(type.equals("0")){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 此百分比数据类型是否是单位百分比
	 * @return
	 */
	public boolean IsPercent(){
		if(type.equals("1")){
			return true;
		}else{
			return false;
		}
		
	}
	/**
	 * 检查当前的字符串格式是否符合百分比数据类型
	 * @param strData 字符串格式的百分比数据
	 * @return 
	 */
	public static boolean isLegal(String strData){
		return true;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
