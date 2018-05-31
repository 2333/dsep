package com.dsep.domain.dsepmeta.viewconfig.views;

import java.util.ArrayList;
import java.util.List;

import com.meta.domain.MetaAttrDomain;
import com.meta.domain.MetaEntityDomain;

public class CKFormViewConfig extends JqgridViewConfig{

	private int width;
	private int height;
	private int maxWords;
	private List<String> editableColIds;
	public CKFormViewConfig(MetaEntityDomain e){
		super(e);
		setCKFormConfig(e);	

	}
	
	private void setCKFormConfig(MetaEntityDomain e){
		this.width =  e.getFormWidth();
		this.height = e.getFormHeight();
		this.maxWords = e.getMaxWords(); 
		setEditableCols(e);
	}
	@Override
	public void setVisible(String colName, String label, boolean Visible,
			int width) {
		// TODO Auto-generated method stub
		
	}
	public void setEditableCols(MetaEntityDomain e){
		this.editableColIds = new ArrayList<String>(0);
		for(MetaAttrDomain attr: e.getAttrDomains()){
			if(attr.isEditable()){
				this.editableColIds.add(attr.getName());
			}
		}
	}
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getMaxWords() {
		return maxWords;
	}

	public void setMaxWords(int maxWords) {
		this.maxWords = maxWords;
	}

	public List<String> getEditableColIds() {
		return editableColIds;
	}

	public void setEditableColIds(List<String> editableColIds) {
		this.editableColIds = editableColIds;
	}
	

}
