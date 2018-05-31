package com.dsep.dao.dsepmeta.project.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dsep.dao.common.impl.DsepMetaDaoImpl;
import com.dsep.dao.dsepmeta.project.ItemFundsDao;
import com.dsep.entity.News;
import com.dsep.entity.project.ItemFunds;
import com.dsep.entity.project.ItemProvideFunds;

public class ItemFundsDaoImpl extends 
DsepMetaDaoImpl<ItemFunds, String> implements ItemFundsDao {

	private String getTableName() {
		return super.getTableName("p", "itemfunds");
	}
	@Override
	public List<ItemFunds> getAll() {
		String sql = "select * from " + getTableName();
		return super.sqlFind(sql);
	}
	@Override
	public List<ItemFunds> pageFind(String item_id,int pageIndex, int pageSize, Boolean desc,
			String orderProperName) {
		// TODO Auto-generated method stub
		String hql="from ItemFunds where item_id=?";
		Object[] values= new Object[]{item_id};
		return super.hqlPage(hql, pageIndex, pageSize, desc, orderProperName, values);
	}
	@Override
	public List<ItemFunds> getSearchData(String startDate, String endDate,String invoiceNumber,String item_id,
			int page, int pageSize) {
		// TODO Auto-generated method stub
		String hql = "from ItemFunds n where item_id=?";
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
			hql += " and n.usingTime >= ?";
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
			hql += " and n.usingTime <= ?";
			params.add(d);
		}
		if(StringUtils.isNotBlank(invoiceNumber)){
			hql+=" and n.invoiceNumber=?";
			params.add(invoiceNumber);
		}
		List<ItemFunds> funds = super.hqlPage(hql, page, pageSize, params.toArray());
		return funds;
	}
	
	@Override
	public int Count(String item_id) {
		// TODO Auto-generated method stub
		String hql = "from ItemFunds where item_id=?";
		Object[] values= new Object[]{item_id};
		List<ItemFunds> Funds = super.hqlFind(hql,values);
		return Funds.size();
	}
}
