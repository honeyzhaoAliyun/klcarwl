package com.klcarwl.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 
 * Title: klcar Platform
 * 
 * Author: zhaoguoqing
 * 
 * Date: 2014-7-18
 * 
 * Description: 获取JDBC数据源
 * 
 */
public class JDBCDataSource {
	
	public static Connection getConnection(){
		
		Connection connection = null;
		try {
        	Properties prop = new Properties(); 
        	InputStream fis =JDBCDataSource.class.getClassLoader().getResourceAsStream("/config/jdbc.properties");//jdbc.properties放在src目录  
        	prop.load(fis);
        	String driver = prop.getProperty("jdbc.driverClassName"); 
        	String url = prop.getProperty("jdbc.url"); 
        	String username = prop.getProperty("jdbc.username"); 
        	String password = prop.getProperty("jdbc.password"); 
        	Class.forName(driver);
			connection = DriverManager.getConnection(url, username, password);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	public static void main(String[] args) {
		getConnection();
	}
	
	public static void closeAll(ResultSet rs, PreparedStatement pstm, Connection conn) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstm != null) {
			try {
				pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
