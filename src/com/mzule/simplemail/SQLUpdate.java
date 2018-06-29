package com.mzule.simplemail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class SQLUpdate {
	//public static String readFilePath = "H:/contarctFile/readFilePath";
	//public static String renameFilePath = "H:/contarctFile/renameFilePath";
	//public static Integer totalFile = 0;
	
	/**
	 * 更新归档文件关联信息
	 * @param contract
	 * @return
	 */
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
			String sql = "select ID,field0006 from formmain_4532";
			pre = con.prepareStatement(sql);// 实例化预编译语句
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			int c = 0;
			while (result.next()) {
				Long ID = result.getLong("ID");
				String name = result.getString("field0006");
				System.out.println(++c + "ID:" + ID + ",name:" + name);
				map.put(ID, name);
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			Logger.logStackTrace(e.getStackTrace());
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
				Logger.logStackTrace(e.getStackTrace());
			}
		}
		return map;

	}

	public static void main(String[] args) {
		//SQLUpdate.updateContractStampSubRefrence(SQLUpdate.getContract());
		SQLUpdate.getContract();
	}

}
