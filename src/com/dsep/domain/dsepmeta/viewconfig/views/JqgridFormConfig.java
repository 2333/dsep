package com.dsep.domain.dsepmeta.viewconfig.views;

import com.meta.domain.MetaEntityDomain;

public class JqgridFormConfig extends JqgridViewConfig{
	private int maxNum;

	public JqgridFormConfig(MetaEntityDomain e)
	{
		super(e);
		this.maxNum = 1;
	}
	
	public int getMaxNum() {
		return maxNum;
	}


}
