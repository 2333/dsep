package com.dsep.dao.admin;

import java.util.List;
import java.util.Map;

public interface SqlHelperDao{
	
	/**
	 * 更新或保存记录数
	 * @param sql
	 * @return
	 */
	public int excuteUpdateOrSave(String sql);
	
	/**
	 * 查询记录
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> excuteSelect(String sql);
}
