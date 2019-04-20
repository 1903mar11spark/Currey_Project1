package com.revature.DAO;

import java.util.List;


import com.revature.Beans.Employee;

public interface EmployeeDAO
{
	public Employee getEmployeeByLastName(String lName);
	public List<Employee> getAllEmployees(int id);
	public boolean updateEmployee(Employee employee);
	public boolean addEmployee(Employee employee);
	public boolean deleteEmployee(int id);
	public boolean isManager(boolean isBoss);
	Employee getEmployeeById(int id);
}