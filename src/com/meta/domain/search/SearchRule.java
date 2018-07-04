package com.meta.domain.search;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class SearchRule {
	/**
	 * 数据库字段名，如果是hql则为实体类的属性名
	 */
	private String field;//字段名称
	/**
	 * 操作符号，与jgGrid对应
	 */
	private String op;//比较符号（大于、等于等）
	/**
	 * 查询条件值
	 */
	private String data;//数据
	/**
	 * 数据类型integer, number, year,  yearmonth, datetime, text
	 */
	private String type;
	
	public SearchRule(){		
	}
	/*public SearchRule(String field, String op, String data, String type){
		this.field = field;
		this.op = op;
		this.data = data;
		this.type = type;
	}*/

	public SearchRule(String field, SearchOperator op, String data, SearchType type){
		this.field = field;
		this.op = op.getOperator();
		this.data = data;
		this.type = type.toString();
	}
	/**
	 * 构造适合in的查询规则
	 * @param field 字段名
	 * @param data in条件中的数据项，注意Object的类型？
	 * @param 数据类型
	 */
	public SearchRule(String field, Object[] data, SearchType type){
		this.field = field;
		this.op =SearchOperator.IS_IN.getOperator();
		this.type = type.toString();
		if(type==SearchType.ERROR){
			this.type = SearchType.STRING.toString();
		}
		StringBuilder value = new StringBuilder("");
		if(data.length==0){
			value.append("''");
		}else{
			boolean first = true;
			for(Object v:data){
				if(!first) value.append(",");
				value.append(v.toString());
				first = false;
			}
		}
		this.data=value.toString();
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getOp() {
		return op;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public SearchOperator getOperator(){ 
		return SearchOperator.getSearchOperator(op);
	}
	
	public void setOp(String op) {
		this.op = op;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	@Override
	public String toString() {		
		return field + getOperator().toString() + convertData();
	}
		
	/**
	 *是否需要补充单引号 
	 * @return
	 */
	private String needQuote(){
		if(type.equals("integer")||type.equals("number")||type.equals("datetime")){
			return "";
		}else{
			return "'";
		}
	}
	
	private boolean needToDate(){
		if(type.equals("datetime")||type.equals("date")){
			return true;
		}else{
			return false;
		}
	}
	private String dealDate(String data){
		return "to_date('"+data+"', 'yyyy-mm-dd hh24:mi:ss')";
	}
	
	private String convertData(){
		String sqlData = data;
		if(needToDate() ){
			sqlData = dealDate(data);
		}
		String result = needQuote() + sqlData + needQuote();
		switch(getOperator()){
		case BEGIN_WITH:
		case NOT_BEGIN_WITH:
			result = needQuote() + sqlData + "%" + needQuote();
			break;
		case CONTAIN:
		case NOT_CONTAIN:
			result = needQuote() + "%" + sqlData + "%" + needQuote();
			break;
		case END_WITH:
		case NOT_END_WITH:
			result = needQuote() + "%" + sqlData + needQuote();
			break;
		case IS_IN:
		case IS_NOT_IN:
			String[] values = data.split(",");
			StringBuilder re= new StringBuilder(" (");
			boolean isFirst = true;
			for(String value:values){
				if(!isFirst) re.append(", ");
				if(StringUtils.isNotBlank(value)){
					if(needToDate()) value = dealDate(value);
					re.append(needQuote()  + value + needQuote());
					isFirst = false;
				}
			}
			re.append(") ");
			result = re.toString();
			break;
		case IS_NULL:
		case IS_NOT_NULL:
			result = "";
		default:
			break;
		}
		return result;
	}
	

}
