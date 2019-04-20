package com.revature.Beans;

import com.revature.DAO.EmployeeDAOImpl;

public class Main 
{
	 public static void main(String[] args) 
	 {
		 EmployeeDAOImpl edaoimpl = new EmployeeDAOImpl();
		 System.out.println(edaoimpl.getAllEmployees(1));
}
}
