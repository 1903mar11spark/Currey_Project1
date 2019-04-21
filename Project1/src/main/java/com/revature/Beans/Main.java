package com.revature.Beans;

import com.revature.DAO.EmployeeDAO;

public class Main 
{
	 public static void main(String[] args) 
	 {
		 EmployeeDAO edaoimpl = new EmployeeDAO();
		 
		 //System.out.println(edaoimpl.getByUsername("shawkurr"));
		 
		 System.out.println(edaoimpl.getByCredentials("shawkurr", "12345"));
		 //System.out.println(edaoimpl.getById(1));
		 
}
}
