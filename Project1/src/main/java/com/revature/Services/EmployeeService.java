package com.revature.Services;

import java.util.List;

import com.revature.Beans.Employee;
import com.revature.DAO.EmployeeDAO;

public class EmployeeService 
{	
	private EmployeeDAO employeeDao = new EmployeeDAO();
	
	public List<Employee> getAll() 
	{
		return employeeDao.getAll();
	}

	public Employee getById(int userId) 
	{
		return employeeDao.getById(userId);
	}

	public Employee getByUsername(String username) 
	{
		return employeeDao.getByUsername(username);
	}

	
	//GET BY USERNAME & PASSWORD
	public Employee getByCredentials(String username, String password) 
	{
		Employee user = null;
		
		// Verify that neither of the credentials are empty string
		if (!username.equals("") && !password.equals("")) 
		{
			user = employeeDao.getByCredentials(username, password);
			if(user != null) 
			{
				return user;
			}
		}
		System.out.println("Username and/or password is empty!");
		return null;
	}
	
	
	
	//ADD
	public Employee add(Employee newUser) 
	{
		// Verify that there are no empty fields
		if (newUser.getUsername().equals("") 
				|| newUser.getPassword().equals("") 
				|| newUser.getFirstName().equals("")
				|| newUser.getLastName().equals("") 
				|| newUser.getEmail().equals(""))
		{
			System.out.println("New User had empty fields!");
			return null;
		}

		return employeeDao.add(newUser);
	}
	
	
	
	
	
	//UPDATE
	public Employee update(Employee updatedUser) 
	{

		// Verify that there are no empty fields
		if (updatedUser.getUsername().equals("") 
				|| updatedUser.getPassword().equals("")
				|| updatedUser.getFirstName().equals("") 
				|| updatedUser.getLastName().equals("") 
				|| updatedUser.getEmail().equals("")) 
		{
			System.out.println("Updated User had empty fields!");
			return null;
		}
		
		
		// Attempt to persist the user to the dataset
		Employee persistedUser = employeeDao.update(updatedUser);
		
	
		// If the update attempt was successful, 
		//update the currentUser of AppState, and return the updatedUser
		if (persistedUser != null) 
		{
			return updatedUser;
		}
		// If the update attempt was unsuccessful, return null
		return null;
	}
	
	
	
	public boolean delete(int userId) 
	{
		return false;
	}
	
}