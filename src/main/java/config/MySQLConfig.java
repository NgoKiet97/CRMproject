package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLConfig {

	public static Connection getConnection() {
		Connection connection = null; 
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/crm_app", "root", "123456");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error get connection: " + e.getMessage());
		}
		return connection;
	}
}
