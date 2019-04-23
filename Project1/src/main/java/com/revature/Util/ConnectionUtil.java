package com.revature.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class ConnectionUtil {
	
	public static Connection getConnectionFromFile() throws SQLException, IOException {
		Properties prop = new Properties();
		InputStream in = ConnectionUtil.class.getClassLoader().getResourceAsStream("Connection.prop");
		prop.load(in);
		try {
			   Class.forName("oracle.jdbc.driver.OracleDriver");
			}
			catch(ClassNotFoundException ex) {
			   System.out.println("Error: unable to load driver class!");
			}
		return DriverManager.getConnection(prop.getProperty("url"), prop.getProperty("username"),
				prop.getProperty("password"));
	}
}	
	
	







//private static ConnectionUtil cu = new ConnectionUtil();
//	
//	private ConnectionUtil() {
//		super();
//	}
//	
//	public static ConnectionUtil getInstance() {
//		return cu;
//	}
//	
//	private static final String DB_DRIVER_CLASS="driver.class.name";
//	private static final String DB_USERNAME="db.username";
//	private static final String DB_PASSWORD="db.password";
//	private static final String DB_URL ="db.url";
//	
//	private static Connection connection = null;
//	private static Properties properties = null;
//	//this should not be static, if static will only create connection when class first loads
//	{
//		try {
//			properties = new Properties();
//			properties.load(new FileInputStream("src/main/java/Connection.prop"));
//			Class.forName(properties.getProperty(DB_DRIVER_CLASS));
//			connection = DriverManager.getConnection(properties.getProperty(DB_URL),properties.getProperty(DB_USERNAME) , properties.getProperty(DB_PASSWORD) );
//		} catch (ClassNotFoundException | SQLException | IOException e) {
//			e.printStackTrace();
//		}
//	}
//	
//	public Connection getConnection(){
//		
//			try {
//				properties = new Properties();
//				properties.load(new FileInputStream("src/main/java/Connection.prop"));
//				Class.forName(properties.getProperty(DB_DRIVER_CLASS));
//				connection = DriverManager.getConnection(properties.getProperty(DB_URL),properties.getProperty(DB_USERNAME) , properties.getProperty(DB_PASSWORD) );
//			} catch (ClassNotFoundException | SQLException | IOException e) {
//				e.printStackTrace();
//			}
//		
//		
//		return connection;
//	}

