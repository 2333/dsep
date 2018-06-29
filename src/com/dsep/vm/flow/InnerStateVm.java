package com.dsep.vm.flow;

import com.meta.entity.MetaInnerStateDetail;

public class InnerStateVm {
	
	private MetaInnerStateDetail detail;

	public InnerStateVm(){
		
	}
	public InnerStateVm(MetaInnerStateDetail inStateDetail){
		this.detail = inStateDetail;
	}
	public MetaInnerStateDetail getDetail() {
		return detail;
	}
	public void setDetail(MetaInnerStateDetail detail) {
		this.detail = detail;
	}
	
}
