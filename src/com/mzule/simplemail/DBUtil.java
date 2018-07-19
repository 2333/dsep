package com.mzule.simplemail;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;

public class DBUtil {
    //连接池对象
    private static BasicDataSource ds;
    //加载参数
    static{
        //创建连接池
		ds = new BasicDataSource();
		//设置参数
		ds.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		ds.setUrl("jdbc:oracle:" + "thin:@10.5.17.50:1521:rdb1");
		ds.setUsername("v5xuser");
		ds.setPassword("catr654321");
		ds.setInitialSize(1);
		ds.setMaxActive(10);
    }
    /*
     * 以上就是将配置文件里的参数全部读取出来，接下来就是要
     * 写两个方法，一个是用来创建连接的，一个关闭连接
     * */
    public static Connection getConnection() throws SQLException{
        return ds.getConnection();
    }
    
    public static void close(Connection conn){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭连接失败",e);
            }
        }
    }
}