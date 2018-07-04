package com.meta.service.factory;

import com.meta.entity.MetaAttribute;
import com.meta.entity.MetaDataType;
import com.meta.service.sql.SqlDDLHelper;
import com.meta.service.sql.SqlDMLHelper;
import com.meta.service.sql.impl.SqlDDLHelperDateTimeImpl;
import com.meta.service.sql.impl.SqlDDLHelperDicImpl;
import com.meta.service.sql.impl.SqlDDLHelperDoubleImpl;
import com.meta.service.sql.impl.SqlDDLHelperFileImpl;
import com.meta.service.sql.impl.SqlDDLHelperImpl;
import com.meta.service.sql.impl.SqlDDLHelperPKImpl;
import com.meta.service.sql.impl.SqlDDLHelperPercentImpl;
import com.meta.service.sql.impl.SqlDDLHelperYearImpl;
import com.meta.service.sql.impl.SqlDDLHelperYearMonthImpl;
import com.meta.service.sql.impl.SqlDMLHelperDicImpl;
import com.meta.service.sql.impl.SqlDMLHelperFileImpl;
import com.meta.service.sql.impl.SqlDMLHelperImpl;
import com.meta.service.sql.impl.SqlDMLHelperPercentImpl;
import com.meta.service.sql.impl.SqlDMLHelperYearImpl;
import com.meta.service.sql.impl.SqlDMLHelperYearMonthImpl;

public class SqlHelperFactory {
	public static SqlDDLHelper newSqlDDLHelper(MetaAttribute attr, String versionId) {
		if(attr.getIndexNo() == 100) return new SqlDDLHelperPKImpl(attr, versionId);//主键
		switch(MetaDataType.getDataType(attr.getDataType())){
		case DATETIME:
			return new SqlDDLHelperDateTimeImpl(attr);
		case DIC:
			return new SqlDDLHelperDicImpl(attr);
		case PERCENT:
			return new SqlDDLHelperPercentImpl(attr);
		case FILE:
			return new SqlDDLHelperFileImpl(attr);
		case YEAR:
			return new SqlDDLHelperYearImpl(attr);
		case YEARMONTH:
			return new SqlDDLHelperYearMonthImpl(attr);
		case DOUBLE:
			return new SqlDDLHelperDoubleImpl(attr);
		default:
			return new SqlDDLHelperImpl(attr);
		}
	}

	public static SqlDMLHelper newSqlDMLHelper(MetaAttribute attr) {
		switch(MetaDataType.getDataType(attr.getDataType())){
		case DIC:
			return new SqlDMLHelperDicImpl(attr);
		case PERCENT:
			return new SqlDMLHelperPercentImpl(attr);
		case FILE:
			return new SqlDMLHelperFileImpl(attr);
		case YEAR:
			return new SqlDMLHelperYearImpl(attr);
		case YEARMONTH:
			return new SqlDMLHelperYearMonthImpl(attr);
		default:
			return new SqlDMLHelperImpl(attr);
		}
	}

	/*public static SqlDataHelper newSqlDataHelper(MetaAttribute attr) {
		SqlDataHelper sqlHelper = new SqlDataHelperImpl();
		return sqlHelper;
	}*/
}
