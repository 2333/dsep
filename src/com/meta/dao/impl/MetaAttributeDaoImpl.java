package com.meta.dao.impl;
import com.dsep.dao.common.impl.DaoImpl;
import com.meta.dao.MetaAttributeDao;
import com.meta.entity.MetaAttribute;

public class MetaAttributeDaoImpl extends DaoImpl<MetaAttribute, String> 
       implements MetaAttributeDao{

	@Override
	public String saveBySql(MetaAttribute attribute) {
		// TODO Auto-generated method stub
		String sql = "insert into META_ATTRIBUTE " +
				"(ID,ENTITYID,NAME,CHSNAME,DATATYPE,DATALENGTH,ISNULL," +
				"DEFAULTVALUE,SEQNO,INDEXNO,DICID,DATARULE,CHECKRULE,REMARK) " +
				"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		super.sqlBulkUpdate(sql, new Object[]{attribute.getId(),attribute.getEntityId(),
				attribute.getName(),attribute.getChsName(),attribute.getDataType(),attribute.getDataLength(),
				attribute.getIsNull(),attribute.getDefaultValue(),attribute.getSeqNo(),
				attribute.getIndexNo(),attribute.getDicId(),attribute.getDataRule(),
				attribute.getCheckRule(),attribute.getRemark()});
		/*String sql = "insert into META_ATTRIBUTE " +
				"(ID,ENTITYID,NAME,CHSNAME,DATATYPE,DATALENGTH,ISNULL," +
				"DEFAULTVALUE,SEQNO,INDEXNO) " +
				"values(?,?,?,?,?,?,?,?,?,?)";
		super.sqlBulkUpdate(sql, new Object[]{attribute.getId(),attribute.getEntityId(),
				attribute.getName(),attribute.getChsName(),attribute.getDataType(),attribute.getDataLength(),
				attribute.getIsNull(),attribute.getDefaultValue(),attribute.getSeqNo(),
				attribute.getIndexNo()});*/
		return attribute.getId();
	}

}
