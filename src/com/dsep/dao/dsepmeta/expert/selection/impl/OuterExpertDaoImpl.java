package com.dsep.dao.dsepmeta.expert.selection.impl;

import java.util.List;

import com.dsep.dao.common.impl.ExpDaoImpl;
import com.dsep.dao.dsepmeta.expert.selection.OuterExpertDao;
import com.dsep.domain.dsepmeta.expert.OuterExpert;
import com.dsep.entity.expert.Expert;

public class OuterExpertDaoImpl extends
		ExpDaoImpl<OuterExpert> implements
		OuterExpertDao {

	@Override
	public List<OuterExpert> getAll() throws InstantiationException,
			IllegalAccessException {
		String sql = "select" + getUsefulColumns()
				+ " from xx_zj order by YJXKM,XXDM";
		return getAllBySql(sql);
	}

	@Override
	public List<OuterExpert> getByDisc(String discId,
			List<String> selectedExpertNumbers) throws InstantiationException,
			IllegalAccessException {
		String sql = "select" + getUsefulColumns()
				+ " from xx_zj where (YJXKM = '" + discId + "' or YJXKM2 = '"
				+ discId + "') "
				+ excludeExpertSelectedSQL(selectedExpertNumbers)
				+ " order by XXDM";
		return getAllBySql(sql);
	}

	@Override
	public List<OuterExpert> getExperts(List<Expert> experts)
			throws InstantiationException, IllegalAccessException {
		return null;
	}

	@Override
	public List<OuterExpert> getExpertsByName(String name,
			List<String> selectedExpertNumbers) throws InstantiationException,
			IllegalAccessException {
		String sql = "select distinct ZJBH,ZJXM,XXDM,YJXKM,YJXKM2,CSNY,XZZW,BGDH,DZXX,ZJFL,BGDH from xx_zj where ZJXM like '%"
				+ name + "%'" + excludeExpertSelectedSQL(selectedExpertNumbers);
		sql += " order by YJXKM,XXDM";
		return getAllBySql(sql);
	}

	@Override
	public OuterExpert getExpertsByExpertNumber(String expertNumber)
			throws InstantiationException, IllegalAccessException {
		String sql = "select ZJBH,ZJXM,XXDM,YJXKM,YJXKM2,CSNY,XZZW,BGDH,DZXX,ZJFL,BGDH from xx_zj where ZJBH = '"
				+ expertNumber + "'";
		List<OuterExpert> list = getAllBySql(sql);
		if (list.size() > 0)
			return list.get(0);
		else
			return null;
	}

	@Override
	public List<OuterExpert> getExpertsQueryByDisAndUnit(
			String discId, String unitId, List<String> selectedExpertNumbers)
			throws InstantiationException, IllegalAccessException {
		String sql = "select distinct ZJBH,ZJXM,XXDM,YJXKM,YJXKM2,CSNY,XZZW,BGDH,DZXX,ZJFL,BGDH from xx_zj where ";
		if (discId != null && unitId != null) {
			sql += " XXDM='" + unitId + "' and (YJXKM='" + discId + "' or YJXKM2='" + discId + "')";
		} else if (discId != null && unitId == null) {
			sql += " YJXKM='" + discId + "' or YJXKM2='" + discId + "'"; 
		} else if (discId == null && unitId != null) {
			sql += " XXDM='" +unitId + "'";
		}
		sql += excludeExpertSelectedSQL(selectedExpertNumbers);
		sql += " order by YJXKM,XXDM";
		return getAllBySql(sql);
	}

	// ZJBH<>'1234' or ZJBH<>'2345'
	private String excludeExpertSelectedSQL(List<String> expertZJBHs) {
		// 如果
		if (null == expertZJBHs) {
			return "";
		} else {
			if (0 == expertZJBHs.size()) {
				return "";
			}
			String sql = " and (";
			for (String ZJBH : expertZJBHs) {
				// 此处不要随意加空格，因为倒数第二行sql.substring(0, sql.length()
				// -2);会去除最后一个and，如果有空格，可能去除不准确
				sql += " ZJBH<>'" + ZJBH + "' and";
			}
			sql = sql.substring(0, sql.length() - 3);
			sql += ")";
			return sql;
		}
	}

	private String getUsefulColumns() {
		/**
		 * ZJBH:专家编号,ZJXM:专家姓名,XXDM:学校代码 YJXKM:一级学科码,YJXKM2:一级学科码2,CSNY:出生年月
		 * XZZW:行政职务,BGDH:办公电话,YDDH:移动电话 DZXX:电子信箱,ZJFL:专家分类
		 */
		/*
		 * return " ZJBH,ZJXM,XXDM,YJXKM,YJXKM2,CSNY,XZZW,BGDH,YDDH,DZXX,ZJFL ";
		 */
		/**
		 * ZJBH:专家编号,ZJXM:专家姓名,XXDM:学校代码 YJXKM:一级学科码, YJXKM2:一级学科码2,CSNY:出生年月
		 * XZZW:行政职务,BGDH:办公电话,YDDH:移动电话 DZXX:电子信箱,ZJFL:专家分类 __________
		 * XBM:性别码，SFZH:身份证号,SZBM:所在部门， TXDZ:通讯地址，YZBM:邮政编码，ZZDH:住宅电话，CZDH:传真电话，
		 * ZYJSZWM:专业技术职务码，DSLBM:导师类别码，RDSNY:任导师年月，ZJLBM:专家类别码， ZGXWM:最高学位码，
		 * ZGXWM:最高学位码，HXWNY:获学位年月，EJXKM:二级学科码、YJFX1：研究方向1， YJFX2：研究方向2，
		 * YJFX3：研究方向3，YJFX4：研究方向4
		 * BZ:备注，MZM:民族码，ZZMMM:政治面貌码，EJXKM2：二级学科码2，WGYZM:外国语中码，WYSLCDM:外语熟练程度，
		 * SFJZDS:是否在外单位担任兼职导师，兼职单位名称，
		 * ZDBSSS:指导博士数，ZSBSSS:招收博士数，ZDSSSS:指导硕士数，ZSSSSS:招收硕士数，
		 * ZDBSSS2:指导博士数2，ZSBSSS2：招收博士数2，ZDSSSS:指导硕士数2，ZSSSSS:招收硕士数2 SHJZ:社会兼职
		 */
		return " ZJBH,ZJXM,XXDM,YJXKM,YJXKM2,CSNY,XZZW,BGDH,YDDH,DZXX,ZJFL,"
				+ " XBM,SFZH,SZBM,TXDZ,YZBM,ZZDH,CZDH,ZYJSZWM,"
				+ "DSLBM,RDSNY,ZJLBM,ZGXWM,ZGXWM,HXWNY,EJXKM,"
				+ "YJFX1,YJFX2,YJFX3,YJFX4,BZ,MZM,ZZMMM,EJXKM2,"
				+ "WGYZM,WYSLCDM,SFJZDS,JZDWMC,ZDBSSS,ZSBSSS,ZDSSSS,ZSSSSS,"
				+ "ZDBSSS2,ZSBSSS2,ZDSSSS,ZSSSSS,SHJZ";
	}

	@Override
	public List<OuterExpert> getExpertsByExpertNumbers(
			List<String> expertsNumbers) throws InstantiationException,
			IllegalAccessException {
		String sql = "select " + getUsefulColumns() + " from xx_zj where ";
		String conditions = "";
		for (String expertNumber : expertsNumbers) {
			conditions += " ZJBH ='" + expertNumber + "' or";
		}
		conditions = conditions.substring(0, conditions.length() - 2);
		sql = sql + conditions;
		return getAllBySql(sql);
	}

	private String createQueryPageSQL(String tableName, String orderProperName,
			Boolean asc, int startRow, int endRow) {
		// String sql = null;= "select * from (select rownum as r,t.* from "
		/*
		 * +" (select %s.* from %s order by %s %s) t "
		 * +" where rownum<=?) where r>?";
		 */
		String orderType = "asc";
		if (asc) {
			orderType = "asc";
		} else {
			orderType = "desc";
		}
		StringBuilder sql = new StringBuilder(
				"select * from (select rownum as r,t.* from ");
		sql.append(" (select " + tableName + ".* from " + tableName);
		sql.append(" order by " + orderProperName + " " + orderType + ") t");
		sql.append(" where rownum <= " + endRow + ") where r > " + startRow);

		/*
		 * sql = String.format("select * from (select rownum as r,t.* from "
		 * +" (select %s.* from %s order by %s %s) t "
		 * +" where rownum<=?) where r>?",
		 * tableName,tableName,orderProperName,orderType);
		 */
		return sql.toString();
	}

	@Override
	public List<OuterExpert> getPageFromOtherDBs(Integer pageIndex,
			Integer pageSize, String orderProperName, Boolean asc)
			throws InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		int startRow = ((pageIndex) - 1) * pageSize;
		int endRow = startRow + Integer.valueOf(pageSize);
		String sql = createQueryPageSQL("xx_zj", "YJXKM", true, startRow,
				endRow);
		System.out.println(sql);
		return super.getAllBySql(sql);
	}

	@Override
	public int getExpertInfoCount() {
		// TODO Auto-generated method stub
		String sql = " select count(*) from xx_zj ";
		return super.getCountBySql(sql);
	}
	
	/*新增，仅供重构测试*/
	@Override
	public List<OuterExpert> getByDisc2(String discId,
			List<String> selectedExpertNumbers, int level) throws InstantiationException,
			IllegalAccessException {
		String sql = "select" + getUsefulColumns()
				+ " from xx_zj where ";
		sql += (level==1)?" YJXKM=":" YJXKM2=";
		sql += "'" + discId + "'";
		return getAllBySql(sql);
	}
	
	@Override
	public List<OuterExpert> getAllByCondition() throws InstantiationException,
	IllegalAccessException{
		String sql = "select " + getUsefulColumns() + " from xx_zj t"
				+ " where t.YJXKM2 is not null order by YJXKM2,XXDM ";
		return getAllBySql(sql);
	}
	/*新增，仅供重构测试*/

}
