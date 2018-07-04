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


public class AnalyseCtpTongyongAffair {
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
				System.out.println(sql);
				System.out.println("ID=" + key + ", name:" + name);
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
	 * 对应合同信息
	 * 
	 * @param file
	 * @return
	 */
	private static Map<Long, String> getContract() {
		Map<Long, String> map = new HashMap<Long, String>();
		Connection con = null;// 创建一个数据库连接
		PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
		ResultSet result = null;// 创建一个结果集对象
		try {

			con = getConn();// 获取连接
			if(null == con){return null;}
			// 合同归档扫描件: field0029
			// 合同盖章扫描件: field0042
			String sql = "select c.member_id member_id, c.sender_id sender_id, "
					+ "m.name member_name, c.subject subject, c.create_date create_date, "
					+ "c.receive_time receive_time, c.complete_time complete_time, "
					+ "c.templete_id, c.process_id process_id "
					+ "from ctp_affair c left join org_member m on c.member_id = m.id "
					+ "where subject like '%支出合同%' and create_date>to_date('2017-3-1', 'YYYY-MM-DD') "
					+ "and process_id is not null "
					+ "order by process_id desc, receive_time asc";
			
			
			
			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			int c = 0;
			String processId = "";
			List<Info> list = new ArrayList<Info>();
			Info info = null;
			while (result.next()) {
				String currentProcessId = result.getString("process_id");
				String completeTime = result.getString("complete_time");
				String memberId = result.getString("member_id");
				String memberName = result.getString("member_name");
				String startTime = result.getString("create_date");
				if (processId.equals(currentProcessId)) {
					info.lastProcessorId = memberId;
					info.lastProcessorName = memberName;
					if (completeTime == null) {
						info.lastProcessorCompleteTime = null;
					} else {
						info.lastCompleteTime = completeTime;
						info.lastProcessorCompleteTime = info.lastCompleteTime;
					}
				} else {
					if (info != null) {
						list.add(judgeIsProcessCompleted(info));
					}
					processId = currentProcessId;
					info = new Info();
					info.processId = processId;
					info.startTime = startTime;
					info.lastProcessorId = memberId;
					info.lastProcessorName = memberName;
					if (completeTime == null) {
						info.lastCompleteTime = startTime;
						info.lastProcessorCompleteTime = null;
					} else {
						info.lastCompleteTime = completeTime;
						info.lastProcessorCompleteTime = info.lastCompleteTime;
					}
				}
			}
			System.out.println(list.size());
			return map;
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
		return map;

	}
	
	private static Info judgeIsProcessCompleted(Info info) {
		//if (info.lastPrcessorName.equals("合同归档") && info.lastCompleteTime != null) {
		if (info.lastProcessorName.equals("合同归档")) {
			info.isComplete = true;
			info.totalProcessTime = compareDate(info.startTime, info.lastCompleteTime);
		} else {
			info.isComplete = false;
			info.totalProcessTime = compareDate(info.startTime, info.lastCompleteTime);
		} 
		System.out.println("last node:" + info.lastProcessorName);
		return info;
	}
	
	private static Date formatDate(String dateStr) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
		System.out.println(dateStr);
	    Date d1 = null;
		try {
			d1 = (Date) df.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    System.out.println(d1);
		System.out.println(dateStr);
		return d1;
	}

	public static void main(String[] args) {
		AnalyseCtpTongyongAffair.getContract();
		//SQLUpdate.getContract();
	}
	
	private static String compareDate(String startDate, String endDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String ret = null;
	    try
	    {
	      Date d1 = (Date) df.parse(startDate);
	      Date d2 = (Date) df.parse(endDate);
	      long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
	      long days = diff / (1000 * 60 * 60 * 24);
	      long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
	      long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
	      System.out.println(""+days+"天"+hours+"小时"+minutes+"分");
	      ret = days+"天"+hours+"小时"+minutes+"分";
	    }catch (Exception e)
	    {
	    }
		return ret;
	}
	
	
}

class Info2 {
	String processId;
	String subject;
	String startTime;
	String lastCompleteTime;
	String lastProcessorId;
	String lastProcessorName;
	// 可能为空，当前流程节点人未办理
	String lastProcessorCompleteTime;
	boolean isComplete;
	String totalProcessTime;
}
