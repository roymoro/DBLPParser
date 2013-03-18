package com.database;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class DB {
	public static String driver = "com.mysql.jdbc.Driver";
	public static String url = "jdbc:mysql://127.0.0.1:3306/dblp";
	public static String user = "root";
	public static String password = "wangmian";

	public static Connection SQlConnection() {
		Connection conn=null;
		try {
			// 加载驱动程序
			Class.forName(driver);

			// 连续数据库
			conn = (Connection) DriverManager.getConnection(url,
					user, password);

			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");

			// statement用来执行SQL语句
			
			
		
			

		} catch (ClassNotFoundException e) {

			System.out.println("Sorry,can`t find the Driver!");
			e.printStackTrace();

		} catch (SQLException e) {

			e.printStackTrace();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return conn;
	}
}
