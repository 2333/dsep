package com.dsep.domain.dsepmeta.viewconfig.views;

import java.util.ArrayList;
import java.util.List;

import com.dsep.domain.dsepmeta.viewconfig.ViewConfig;
import com.meta.domain.MetaAttrDomain;
import com.meta.domain.MetaEntityDomain;

public class FileFormViewConfig extends ViewConfig{

	private List<String> editableColIds;
	public FileFormViewConfig(){
		
	}
	public FileFormViewConfig(MetaEntityDomain e){
		super(e);
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
	public List<String> getEditableColIds() {
		return editableColIds;
	}
	public void setEditableColIds(List<String> editableColIds) {
		this.editableColIds = editableColIds;
	}
	
}
