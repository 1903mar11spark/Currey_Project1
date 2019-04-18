package com.revature.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.Beans.Credential;
import com.revature.Util.ConnectionUtil;

public class CredentialDAO extends DAO
{
	public Credential getCred(String user, String password, String email)
	{
		Credential cred = null;;
		try
		{
			connection = ConnectionUtil.getConnection();
			String sql = "SELECT * FROM EMPLOYEE WHERE USER_NAME = ? AND PASS_WORD = ?";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, user);
			stmt.setString(2, password);

			ResultSet rs = stmt.executeQuery();
			
			rs.next();
			int id = rs.getInt("EMPLOYEE_ID");
			cred = new Credential(id, user, password, email);
		} catch (SQLException e)
		{
			e.printStackTrace();
		} finally
		{
			closeResources();
		}
		return cred;
	}
	
	public int tryLogin(String user, String password)
	{
		int id = -1;
		try
		{
			connection = ConnectionUtil .getConnection();
			String sql = "SELECT EMPLOYEE_ID FROM EMPLOYEES WHERE USER_NAME = ? AND PASS_WORD = ?";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, user);
			stmt.setString(2, password);

			ResultSet rs = stmt.executeQuery();
			if( rs.next())
			{
				id = rs.getInt(1);
			}
			
		} 
		
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		
		finally
		{
			closeResources();
		}
		return id;
	}
}

