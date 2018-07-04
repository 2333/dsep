package com.dsep.domain.dsepmeta.logicrule;

import java.util.Map;

import com.meta.domain.MetaAttrDomain;

/**
 * 属性校验接口
 * @author lubin
 *
 */
public interface MetaAttrCheck {
	/**
	 * 校验函数
	 * @param metaAttr 获取参数值、参数个数等信息
	 * @param rowData行数据
 	 * @return  元属性校验错误信息
	 */
	public abstract String check(MetaAttrDomain metaAttr, Map<String, String> rowData);

}
