package com.revature.DAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.Beans.Employee;
import com.revature.Util.ConnectionUtil;

public class EmployeeDAOImpl extends DAO implements EmployeeDAO
{
	
	
	@Override
	public Employee getEmployeeById(int id) 
	{
		String sql = String.format("WHERE EMPLOYEE_ID = %d", id);
		return getAllEmployees(sql).get(0);
	}
 
	
	
	@Override
	public Employee getEmployeeByLastName(String lName) 
	{
		String ln = "'"+lName +"'"; 
		String sql = String.format("WHERE LASTNAME = %s", ln);
		return getAllEmployees(sql).get(0);
	}
	
	
	
	@Override
	public List<Employee> getAllEmployees() 
	{	
		return getAllEmployees("");
	}

	protected List<Employee> getAllEmployees(String sql) 
	{
		List<Employee> employees = new ArrayList<Employee>();
		
		try 
		{
			String base = "SELECT * FROM EMPLOYEES ";
			connection = ConnectionUtil.getConnection();
			stmt = connection.prepareStatement(base+sql); // SELECT and WHERE clauses combined
			
			ResultSet rs = stmt.executeQuery();
			//id, job_title, first_name, last_name, reports_to, ismanager
			while(rs.next())
			{
				int empId = rs.getInt("EMPLOYEE_ID");
				String job = rs.getString("JOB_ROLE");
				String fName = rs.getString("FIRSTNAME");
				String lName = rs.getString("LASTNAME");
				int myBoss = rs.getInt("REPORTS_TO");
				boolean isBoss = rs.getBoolean("ISMANAGER");
				
				Employee emp = new Employee(empId, job, fName, lName, myBoss, isBoss, lName);
				employees.add(emp);
			}
			rs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		} 
		
		finally
		{
			closeResources();
		}
		
		return employees;
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
		
		finally
		{
			closeResources();
		}
	}

	@Override
	public boolean addEmployee(Employee e) 
	{
		//id, job_title, first_name, last_name, reports_to, ismanager
		try 
		{
			connection = ConnectionUtil.getConnection();
			String sql = "INSERT INTO EMPLOYEES (JOB_ID, FIRSTNAME, LASTNAME, REPORTS_TO, ISMANAGER)"
					+"VALUES(?,?,?,?,?)";
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, e.getjob());
			stmt.setString(2, e.getFirstName());
			stmt.setString(3, e.getLastName());
			stmt.setInt(4, e.getManagerId());
			stmt.setBoolean(5, e.IsAManager());
			
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
}
