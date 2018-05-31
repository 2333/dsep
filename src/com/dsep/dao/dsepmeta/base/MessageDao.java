package com.dsep.dao.dsepmeta.base;

import java.util.HashMap;

import com.dsep.dao.common.DsepMetaDao;
import com.dsep.entity.MessageEntity;

public interface MessageDao extends DsepMetaDao<MessageEntity, String> {
	public String getStatusByIdentifier(String _identifier);

	public void updateColumnBySql(String _messageId,
			HashMap<String, Object> columns);
}
