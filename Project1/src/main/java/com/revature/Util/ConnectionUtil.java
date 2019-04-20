package com.revature.Util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.revature.DAO.EmployeeDAO;
import com.revature.DAO.EmployeeDAOImpl;

public class ConnectionUtil {
	private static final String DB_DRIVER_CLASS="driver.class.name";
	private static final String DB_USERNAME="db.username";
	private static final String DB_PASSWORD="db.password";
	private static final String DB_URL ="db.url";
	
	private static Connection connection = null;
	private static Properties properties = null;
	//this should not be static, if static will only create connection when class first loads
	{
		try {
			properties = new Properties();
			properties.load(new FileInputStream("src/main/resources/connection.prop"));
			Class.forName(properties.getProperty(DB_DRIVER_CLASS));
			connection = DriverManager.getConnection(properties.getProperty(DB_URL),properties.getProperty(DB_USERNAME) , properties.getProperty(DB_PASSWORD) );
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection(){
		
			try {
				properties = new Properties();
				properties.load(new FileInputStream("src/main/Java/Connection.prop"));
				Class.forName(properties.getProperty(DB_DRIVER_CLASS));
				connection = DriverManager.getConnection(properties.getProperty(DB_URL),properties.getProperty(DB_USERNAME) , properties.getProperty(DB_PASSWORD) );
			} catch (ClassNotFoundException | SQLException | IOException e) {
				e.printStackTrace();
			}
		
		
		return connection;
	}
	
	
//	public static RequestsDAO getReimburseDAO()
//	{
//		return new ReimbursementDAOImpl();
//	}

	public static EmployeeDAO getEmployeeDAO()
	{
		return new EmployeeDAOImpl();
	}
}