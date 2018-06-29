package com.meta.dao.impl;
import com.dsep.dao.common.impl.DaoImpl;
import com.meta.dao.MetaAttrStyleDao;
import com.meta.entity.MetaAttrStyle;

public class MetaAttrStyleDaoImpl extends DaoImpl<MetaAttrStyle, String> 
       implements MetaAttrStyleDao{

	@Override
	public String saveBySql(MetaAttrStyle attrStyle) {
		// TODO Auto-generated method stub
		String sql = "insert into META_ATTR_STYLE (" +
				"ID,ATTRID,ENTITYSTYLEID,DISPLENGTH,EDITABLE,ISHIDDEN," +
				"CONTROLTYPE,COLNO,ROWNO,COLNUMS,ROWNUMS,CSS,ALIGN)" +
				"values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		super.sqlBulkUpdate(sql, new Object[]{
			attrStyle.getId(),attrStyle.getAttrId(),attrStyle.getEntityStyleId(),
			attrStyle.getDispLength(),attrStyle.getEditable(),attrStyle.getIsHidden(),
			attrStyle.getControlType(),attrStyle.getColNo(),attrStyle.getRowNo(),
			attrStyle.getColNums(),attrStyle.getRowNums(),
			attrStyle.getCss(),attrStyle.getAlign()
		});
		return attrStyle.getId();
	}

}
