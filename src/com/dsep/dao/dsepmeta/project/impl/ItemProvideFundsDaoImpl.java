package com.dsep.dao.dsepmeta.project.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.project.ItemProvideFundsDao;
import com.dsep.entity.project.ItemProvideFunds;

public class ItemProvideFundsDaoImpl extends 
DsepMetaDaoImpl<ItemProvideFunds, String> implements ItemProvideFundsDao {

	private String getTableName() {
		return super.getTableName("p", "itemprovidefunds");
	}
	@Override
	public List<ItemProvideFunds> getAll() {
		String sql = "select * from " + getTableName();
		return super.sqlFind(sql);
	}
	@Override
	public List<ItemProvideFunds> pageFind(String item_id,int pageIndex, int pageSize, Boolean desc,
			String orderProperName) {
		// TODO Auto-generated method stub
		String hql="from ItemProvideFunds where item_id=?";
		Object[] values= new Object[]{item_id};
		return super.hqlPage(hql, pageIndex, pageSize, desc, orderProperName, values);
	}
	@Override
	public List<ItemProvideFunds> getSearchData(String startDate, String endDate,String item_id,
			int page, int pageSize) {
		// TODO Auto-generated method stub
		String hql = "from ItemProvideFunds n where item_id=?";
		List<Object> params = new ArrayList<Object>(0);
		params.add(item_id);
		if(StringUtils.isNotBlank(startDate)){
			Date d = null;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				d = df.parse(startDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hql += " and n.provideTime >= ?";
			params.add(d);
		}
		
		if(StringUtils.isNotBlank(endDate)){
			Date d = null;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			try {
				d = df.parse(endDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hql += " and n.provideTime <= ?";
			params.add(d);
		}
		List<ItemProvideFunds> provideFunds = super.hqlPage(hql, page, pageSize, params.toArray());
		return provideFunds;
	}
	@Override
	public int Count(String item_id) {
		// TODO Auto-generated method stub
		String hql = "from ItemProvideFunds where item_id=?";
		Object[] values= new Object[]{item_id};
		List<ItemProvideFunds> provideFunds = super.hqlFind(hql,values);
		return provideFunds.size();
	}
}
