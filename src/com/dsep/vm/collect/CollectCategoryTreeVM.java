package com.dsep.vm.collect;

import java.util.List;

import com.dsep.vm.CollectionTreeVM;

public class CollectCategoryTreeVM {
	private String categoryId;
	private List<CollectionTreeVM> treeVMs;
	
	public CollectCategoryTreeVM(){
		
	}
	
	public CollectCategoryTreeVM(String categoryId,List<CollectionTreeVM> treeVMs){
		this.categoryId = categoryId;
		this.treeVMs= treeVMs;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public List<CollectionTreeVM> getTreeVMs() {
		return treeVMs;
	}

	public void setTreeVMs(List<CollectionTreeVM> treeVMs) {
		this.treeVMs = treeVMs;
	}

}
