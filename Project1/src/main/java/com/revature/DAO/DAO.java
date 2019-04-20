package com.revature.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class DAO 
{
	protected static Connection connection = null;
	protected static PreparedStatement stmt = null;
	
	protected static void closeResources()
	{
		try
		{
			if (stmt != null)
				stmt.close();
		} catch (SQLException e)
		{
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}

		try
		{
			if (connection != null)
				connection.close();
		} catch (SQLException e)
		{
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}

}