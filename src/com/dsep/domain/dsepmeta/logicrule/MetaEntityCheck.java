package com.dsep.domain.dsepmeta.logicrule;

import java.util.List;
import java.util.Map;

import com.meta.domain.MetaEntityDomain;

/**
 * 实体校验接口
 * 
 * @author
 * 
 */
public interface MetaEntityCheck {

	public abstract String entityCheck(MetaEntityDomain entity,
			String categoryId, int count, List<Map<String, String>> datas);

}
