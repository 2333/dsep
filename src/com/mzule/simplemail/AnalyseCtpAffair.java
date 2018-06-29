package com.mzule.simplemail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.itextpdf.text.log.SysoCounter;


public class AnalyseCtpAffair {
	static long baoxiaoTotalTime = 0L;
	static int baoxiaoCount = 0;
	private static Integer updateContractStampSubRefrence(Map<Long, String> map) {
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		Integer i = 0;
		try {

			con = getConn();// 获取连接
			if(null == con){return null;}
			
			Set<Long> keys = map.keySet();
			Iterator<Long> iter = keys.iterator();
			
			while (iter.hasNext()) {
				Long key = iter.next();
				String name = map.get(key);
				name = name.replaceAll("\\s*", "");
				name = name.replaceAll("\\\\", "");
				name = name.replaceAll("/", "");
				name = name.replaceAll("\"", "");
				name = name.replaceAll("\\?", "");
				name = name.replaceAll("<", "");
				name = name.replaceAll(">", "");
				name = name.replaceAll("\\|", "");
				name = name.replaceAll("\\:", "");
				name = name.replaceAll("\\*", "");
				String sql = "update formmain_4532 set field0066=? where ID =?";// 预编译语句，“？”代表参数
				//System.out.println(sql);
				//System.out.println("ID=" + key + ", name:" + name);
				pre = con.prepareStatement(sql);// 实例化预编译语句
				pre.setString(1, name);
				pre.setLong(2, key);
				
				//pre = con.prepareStatement(sql);// 实例化预编译语句
				i = pre.executeUpdate();
			}
			

		} catch (Exception e) {
			//Logger.logStackTrace(e.getStackTrace());
			e.printStackTrace();
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();

			} catch (Exception e) {
				Logger.logStackTrace(e.getStackTrace());
				e.printStackTrace();
			}
		}
		return i;
	}
	
	private static Connection getConn() {
		try {
			return DBUtil.getConnection();
		} catch (SQLException e) {
			Logger.logStackTrace(e.getStackTrace());
			e.printStackTrace();
		}
		return null;

	}
	
	/**
	 * 对应流程信息
	 * 
	 * @param file
	 * @return
	 */
	public static List<CTPInfo> getCtpAffairInfo(String memberId) {
		Map<Long, String> map = new HashMap<Long, String>();
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		try {

			con = getConn();// 获取连接
			if(null == con){return null;}
			String sql = "select c.member_id a1, "
					+ " c.node_policy a2, c.subject a3, c.create_date a4, c.complete_time a5, "
					+ " c.receive_time a6, c.body_type a7 from V5XUSER.ctp_affair c"
					+ " where c.member_id='"+memberId+"'"
					+ " and c.receive_time<to_date( '2017-9-20', 'YYYY-MM-DD')"
					+ " and c.complete_time is null "
					+ " and c.state=3"
					+ " order by c.receive_time desc, c.form_app_id desc";
			
			//System.out.println("sql");
			//System.out.println(sql);
			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			//System.out.println(result.getFetchSize());
			int c = 0;
			String processId = "";
			Long time = 0L;
			List<CTPInfo> list = new ArrayList<CTPInfo>();
			while (result.next()) {
				String a1 = result.getString("a1");
				String a2 = result.getString("a2");
				String a3 = result.getString("a3");
				String a4 = result.getString("a4");
				String a5 = result.getString("a5");
				String a6 = result.getString("a6");
				String a7 = result.getString("a7");
				//System.out.println(a1);
				//System.out.println(a2);
				//System.out.println(a3);
				//System.out.println(a4.substring(0, 10));
				//System.out.println(a5);
				//System.out.println(a6.substring(0, 10));
				//System.out.println(a7);
				if (a7 == null) continue;
				CTPInfo info = new CTPInfo();
				info.node_policy = policyConverter(a2);
				if (a7.equals("OfficeWord")) {
					info.node_policy = "公文·" + info.node_policy;
				} else {
					info.node_policy = "流程·" + info.node_policy;
				}
				info.subject = a3;
				info.create_time = a4.substring(0, 10);
				info.receive_time = a6.substring(0, 10);
				list.add(info);
			}
			
			
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			//Logger.logStackTrace(e.getStackTrace());
		} finally {
			try {
				// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
				// 注意关闭的顺序，最后使用的最先关闭
				if (result != null)
					result.close();
				if (pre != null)
					pre.close();
				if (con != null)
					con.close();
			} catch (Exception e) {
				e.printStackTrace();
				//Logger.logStackTrace(e.getStackTrace());
			}
		}
		return null;

	}
	
	private static String policyConverter(String policy) {
		
			if(policy.equals("vouch"))
		{ return "核定";}
			if(policy.equals("newsaudit"))
		{ return "新闻审核";}
			if(policy.equals("shenhe"))
		{ return "审核";}
			if(policy.equals("formaudit"))
		{ return "表单审核";}
			if(policy.equals("zhihui"))
		{ return "知会";}
			if(policy.equals("chengban"))
		{ return "承办";}
			if(policy.equals("fengfa"))
		{ return "封发";}
			if(policy.equals("qianfa"))
		{ return "签发";}
			if(policy.equals("fuhe"))
		{ return "复核";}
			if(policy.equals("pishi"))
		{ return "批示";}
			if(policy.equals("approve"))
		{ return "办理";}
			if(policy.equals("inform"))
		{ return "阅知";}
			if(policy.equals("niban"))
		{ return "拟办";}
			if(policy.equals("shenpi"))
		{ return "审批";}
			if(policy.equals("collaboration"))
		{ return "协同";}
			if(policy.equals("wenshuguanli"))
		{ return "文书管理";}
			if(policy.equals("huiqian"))
		{ return "会签";}
			
			if(policy.equals("yuedu"))
		{ return "阅读";}
			if(policy.equals("banli"))
		{ return "办理";}
			if(policy.equals("read"))
		{ return "阅读";}
			if(policy.equals("bulletionaudit"))
		{ return "公告审核";}
			return policy;
	}
	
	private static Date formatDate(String dateStr) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
		//System.out.println(dateStr);
	    Date d1 = null;
		try {
			d1 = (Date) df.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //System.out.println(d1);
		//System.out.println(dateStr);
		return d1;
	}

	public static void main(String[] args) {
		
		AnalyseCtpAffair.getCtpAffairInfo("444148961629194534");
		
		
		
		
		
	}
	
	private static String compareDate(String startDate, String endDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String ret = null;
	    try
	    {
	      Date d1 = (Date) df.parse(startDate);
	      Date d2 = (Date) df.parse(endDate);
	      long diff = d2.getTime() - d1.getTime();//这样得到的差值是微秒级别
	      long days = diff / (1000 * 60 * 60 * 24);
	      long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
	      long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
	      //System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
	      ret = days+"天"+hours+"小时"+minutes+"分";
	    }catch (Exception e)
	    {
	    }
		return ret;
	}
	
	private static Long compareDateAndGetNum(String startDate, String endDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		long diff = 0L;
	    try
	    {
	      Date d1 = (Date) df.parse(startDate);
	      Date d2 = (Date) df.parse(endDate);
	      diff = d2.getTime() - d1.getTime();//这样得到的差值是微秒级别
	    }catch (Exception e)
	    {
	    }
		return diff;
	}
	
	
}

class CTPInfo {
	String node_policy;
	String subject;
	String create_time;
	String receive_time;
}

