package com.revature.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.Beans.Employee;
import com.revature.Util.ConnectionUtil;

public class EmployeeDAOImpl extends DAO implements EmployeeDAO
{
	@Override
	public List<Employee> getAllEmployees(int i)  {
		List<Employee> emp = new ArrayList<Employee>();
		try(Connection con = ConnectionUtil.getConnection())
		{
			
			String sql = "SELECT * FROM EMPLOYEES WHERE EMPLOYEE_ID = ?";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next())
			{
				int empId = rs.getInt("EMPLOYEE_ID");
				String job = rs.getString("JOB_ROLE");
				String fName = rs.getString("FIRSTNAME");
				String lName = rs.getString("LASTNAME");
				
				new Employee(empId, job, fName, lName);
			}
				
		} 
			catch (SQLException e) {
			
			e.printStackTrace();
		}
		return emp;
	}
	
	@Override
	public boolean updateEmployee(Employee e) 
	{
		try
		{
			//id, job_title, first_name, last_name, reports_to, ismanager
			connection = ConnectionUtil.getConnection();
			String sql = "UPDATE EMPLOYEES SET JOB_ID = ? , FIRSTNAME = ?, LASTNAME = ?, REPORTS_TO = ?, ISMANAGER = ?"
			+"WHERE EMPLOYEE_ID = ?";
			stmt = connection.prepareStatement(sql);

			stmt.setString(1, e.getjob());
			stmt.setString(2, e.getFirstName());
			stmt.setString(3, e.getLastName());
			stmt.setInt(4, e.getManagerId());
			stmt.setBoolean(5, e.IsAManager());
			stmt.setInt(6, e.getId());

			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} 
		catch (SQLException ex)
		{
			ex.printStackTrace();
			return false;
		} 
		
	}

	@Override
	public boolean addEmployee(Employee e) 
	{
		//id, job_title, first_name, last_name
		try 
		{
			connection = ConnectionUtil.getConnection();
			String sql = "INSERT INTO EMPLOYEES (JOB_ID, FIRSTNAME, LASTNAME)"
					+"VALUES(?,?,?)";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, e.getjob());
			stmt.setString(2, e.getFirstName());
			stmt.setString(3, e.getLastName());
			
			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} 
		catch (SQLException ex)
		{
			ex.printStackTrace();
			return false;
		} 
		
		finally
		{
			closeResources();
		}
	}

	@Override
	public boolean deleteEmployee(int id) 
	{
		return false;
		// TODO Auto-generated method stub
	}



	@Override
	public boolean isManager(boolean isBoss) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Employee getEmployeeByLastName(String lName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getEmployeeById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
