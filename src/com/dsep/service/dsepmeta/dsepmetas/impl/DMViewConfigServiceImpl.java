package com.dsep.service.dsepmeta.dsepmetas.impl;

import com.dsep.domain.dsepmeta.viewconfig.ViewConfig;
import com.dsep.service.dsepmeta.MetaOper;
import com.dsep.service.dsepmeta.dsepmetas.DMViewConfigService;
import com.meta.domain.MetaEntityDomain;

public class DMViewConfigServiceImpl extends MetaOper implements DMViewConfigService {

	@Override
	public ViewConfig getViewConfig(String entityId, String style) {
		
		MetaEntityDomain entityDomain = super.metaEntityService.getEntityDomain(entityId, style);
		
		if (entityDomain == null)
			throw new NullPointerException("找不到该实体：实体ID" + entityId);

		return ViewConfig.produce(entityDomain);
	}

	@Override
	public ViewConfig getViewConfig(String entityId) {
		return getViewConfig(entityId, "C");
	}

	@Override
	public ViewConfig getViewConfigByCategory(String entityId, String style) {
		// TODO Auto-generated method stub
		return getViewConfig(entityId, style);
	}
	
	
}
