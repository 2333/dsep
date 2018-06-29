package com.meta.domain.search;

public enum SearchOperator {
	EQUAL("eq"),
	NOT_EQUAL("ne"),
	LESS("lt"),
	LESS_EQUAL("le"),
	GREATER("gt"),
	GREATER_EQUAL("ge"),
	IS_NULL("nu"),
	IS_NOT_NULL("nn"),
	IS_IN("in"),
	IS_NOT_IN("ni"),
	BEGIN_WITH("bw"),
	NOT_BEGIN_WITH("bn"),
	END_WITH("ew"),
	NOT_END_WITH("en"),
	CONTAIN("cn"),
	NOT_CONTAIN("nc"),
	ERROR("");
	
	final private String operator;
	private SearchOperator(String operator){
		this.operator = operator;
	}
	@Override
	public String toString() {
		String result = "";
		switch(this){
		case EQUAL:
			result = " = ";
			break;
		case NOT_EQUAL:
			result = " <> ";
			break;
		case LESS:
			result = " < ";
			break;
		case LESS_EQUAL:
			result = " <= ";
			break;
		case GREATER:
			result = " > ";
			break;
		case GREATER_EQUAL:
			result = " >= ";
			break;
		case IS_NULL:
			result = " is null ";
			break;
		case IS_NOT_NULL:
			result = " is not null ";
			break;
		case IS_IN:
			result = " in ";
			break;
		case IS_NOT_IN:
			result = " not in ";
			break;
		case BEGIN_WITH:
			result = " like ";
			break;
		case NOT_BEGIN_WITH:
			result = " not like ";
			break;
		case END_WITH:
			result = " like ";
			break;
		case NOT_END_WITH:
			result = " not like ";
			break;
		case CONTAIN:
			result = " like ";
			break;
		case NOT_CONTAIN:
			result = " not like ";
			break;
		default:
			result =" ";
			break;
		}
		return result;
	}
	public String getOperator() {
		return operator;
	}
	static public SearchOperator getSearchOperator(String op){
		for(SearchOperator operator: SearchOperator.values()){
			if(operator.getOperator().equals(op)) return operator;
		}
		return SearchOperator.ERROR;
	}
}
