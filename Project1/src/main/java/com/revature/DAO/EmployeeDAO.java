package com.revature.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.Beans.Role;
import com.revature.Beans.Employee;
import com.revature.Util.ConnectionUtil;

public class EmployeeDAO implements DAO<Employee> {
	
	
	public Employee getByUsername(String username) {
		
		Employee user = new Employee();
		
		try(Connection conn = ConnectionUtil.getConnection()) 
		{
			String sql = "SELECT * FROM EMPLOYEES JOIN JOB_ROLES "
					+ "ON EMPLOYEES.EMPLOYEE_ID = JOB_ROLES.JOB_ROLE_ID WHERE USER_NAME = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, username);
			
//			List<Employee> users = this.mapResultSet(pstmt.executeQuery());
//			if (!users.isEmpty()) user = users.get(0);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) 
			{
				user.setId(rs.getInt("EMPLOYEE_ID"));
				user.setFirstName(rs.getString("FIRSTNAME"));
				user.setLastName(rs.getString("LASTNAME"));
				user.setUsername(rs.getString("USER_NAME"));
				user.setPassword(rs.getString("PASS_WORD"));
				//System.out.println(user);
				
			}
			
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		return user;
	}
	
	
	
	
	
	
	public Employee getByCredentials(String username, String password) {
		
		Employee user = new Employee();
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			String sql = "SELECT * FROM EMPLOYEES JOIN JOB_ROLES "
					+ "ON EMPLOYEES.EMPLOYEE_ID = JOB_ROLES.JOB_ROLE_ID WHERE USER_NAME = ?";
			PreparedStatement pstmt =  conn.prepareStatement(sql);
			
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
//			List<Employee> users = this.mapResultSet(pstmt.executeQuery());
//			if (!users.isEmpty()) user = users.get(0);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) 
			{
				user.setId(rs.getInt("EMPLOYEE_ID"));
				user.setFirstName(rs.getString("FIRSTNAME"));
				user.setLastName(rs.getString("LASTNAME"));
				user.setUsername(rs.getString("USER_NAME"));
				user.setPassword(rs.getString("PASS_WORD"));
				//System.out.println(user);
				
			}
			
		} 
		
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
				
		return user;
	}
	
	
	
	
	
	
	
	@Override
	public List<Employee> getAll() 
	{
		
		List<Employee> users = new ArrayList<>();
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM EMPLOYEES JOIN JOB_ROLES "
					+ "ON EMPLOYEES.EMPLOYEES_ID = JOB_ROLES.JOB_ROLES_ID");
			users = this.mapResultSet(rs);
			
			users.forEach(u -> u.setPassword("*********"));
			
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
		}
		
		return users;
	}
	
	
	
	
	
	
	
	@Override
	public Employee getById(int userId) {
		
		Employee user = null;;
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM EMPLOYEES JOIN JOB_ROLES "
					+ "ON EMPLOYEE.JOB_ROLE_ID = JOB_ROLES.JOB_ROLES_ID WHERE EMPLOYEE_ID = ?");
			pstmt.setInt(1, userId);
			
			ResultSet rs = pstmt.executeQuery();
			List<Employee> users = this.mapResultSet(rs);
			
			if (!users.isEmpty()) {
				user = users.get(0);
				user.setPassword("*********");
			}
			
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
		}
		
		return user;
	}
	
	
	
	
	
	
	
	
	
	@Override
	public Employee add(Employee newEmployee) {
		
		Role newRole = new Role();
		newRole.setRoleId(2);
		newRole.setRoleName("EMPLOYEE");
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			conn.setAutoCommit(false);
			
			String sql = "INSERT INTO EMPLOYEES VALUES (0, ?, ?, ?, ?, ?, 2)";
			
			String[] keys = new String[1];
			keys[0] = "EMPLOYEE_ID";
			
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setString(1, newEmployee.getUsername());
			pstmt.setString(2, newEmployee.getPassword());
			pstmt.setString(3, newEmployee.getFirstName());
			pstmt.setString(4, newEmployee.getLastName());
			pstmt.setString(5, newEmployee.getEmail());
			
			int rowsInserted = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if(rowsInserted != 0) {
				
				while(rs.next()) {
					newEmployee.setId(rs.getInt(1));
					newEmployee.setRole(newRole);
				}
				
				conn.commit();
				
			}
					
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
		}
		
		if(newEmployee.getId() == 0) return null;
		
		return newEmployee;
	}
	
	
	
	
	
	
	
	
	
	@Override
	public Employee update(Employee  updatedEmployee ) {
		
		try(Connection conn = ConnectionUtil.getConnection()) {
			
			conn.setAutoCommit(false);
			
			String sql = "UPDATE ers_users SET ers_password = ?, user_first_name = ?, user_last_name = ?, "
					+ "user_email = ? WHERE ers_users_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, updatedEmployee.getPassword());
			pstmt.setString(2, updatedEmployee.getFirstName());
			pstmt.setString(3, updatedEmployee.getLastName());
			pstmt.setString(4, updatedEmployee.getEmail());
			pstmt.setInt(4, updatedEmployee.getId());
			
			if(pstmt.executeUpdate() != 0) {
				conn.commit();
				return updatedEmployee;
			}
			
		} catch (SQLException e) {
			//System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	@Override
	public boolean delete(int userId) 
	{
		return false;
	}
	
	private List<Employee> mapResultSet(ResultSet rs) throws SQLException {
		
		List<Employee> users = new ArrayList<>();
		
		while(rs.next()) {
			Employee user = new Employee();
			user.setId(rs.getInt("ers_users_id"));
			user.setUsername(rs.getString("ers_username"));
			user.setPassword(rs.getString("ers_password"));
			user.setFirstName(rs.getString("user_first_name"));
			user.setLastName(rs.getString("user_last_name"));
			user.setEmail(rs.getString("user_email"));
			user.setRole(new Role(rs.getInt("user_role_id")));
			users.add(user);
		}
		
		return users;
	}

	
}