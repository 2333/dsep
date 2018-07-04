package com.dsep.domain.dsepmeta.jqcol.dicrule;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

public class JqGridColDicRule {
	private String type;
	private List<String> values;
	
	public JqGridColDicRule(){
		
	}
	public JqGridColDicRule(String type,List<String> values){
		this.type = type;
		this.values = values;
	}
	public JqGridColDicRule(String type,String []values){
		this.type= type;
		this.values= new ArrayList<String>(0);
		for(String value:values){
			this.values.add(value);
		}
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof JqGridColDicRule){
			JqGridColDicRule dicRule = (JqGridColDicRule)obj;
			return this.type.equals(dicRule.getType())&&
					this.values.containsAll(dicRule.getValues());
		}
		return super.equals(obj);
	}
	
}
