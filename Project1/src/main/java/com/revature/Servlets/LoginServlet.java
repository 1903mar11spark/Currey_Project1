package com.revature.Servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.revature.Beans.Employee;
import com.revature.DAO.EmployeeDAO;
import com.revature.Services.EmployeeService;

public class LoginServlet extends HttpServlet 
{

	private static final long serialVersionUID = 1L;
       

    public LoginServlet() {
        super();
    
    }

	
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	//System.out.println("line 26");
    	request.getRequestDispatcher("Login.HTML").forward(request, response);
    	
    	//doPost(request,response);
	}

	
    
    
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		//check whether session exists, and create one if not
		//overloaded version takes a boolean parameter, 
		//if false returns null when there is none
		
		HttpSession session = request.getSession();
		//response.setContentType("text/html");
		
//		Employee empl = null;
//		EmployeeDAO emplDAO = new EmployeeDAO();
		
		
		
		
		//System.out.println(request.getParameter("user"));
		//grab credentials from request
		EmployeeService es = new EmployeeService();
		System.out.println(request);
		String user = request.getParameter("user");
		System.out.println(user);
		String pass = request.getParameter("password");
		System.out.println(pass);
		es.getByCredentials(user, pass);
		
		
		
		
		if (es.getByCredentials(request.getParameter("user"), request.getParameter("password"))!= null) 
		{
			//set user information as session attributes (not request attributes!)
			session.setAttribute("userId", es.getById(0));
			session.setAttribute("username", es.getByUsername(user));
//			session.setAttribute("firstname", es.getByFirstname());
//			session.setAttribute("lastname", es.getLastname());
//			session.setAttribute("email", es.getEmail());
			session.setAttribute("problem", null);
			
			
			//redirect user to profile page if authenticated 
			//System.out.println("line 69");
			response.sendRedirect("EmployeeHomePage.html");
		} 
		
		else 
		{
			//session.setAttribute("problem", "invalid credentials");
			//otherwise redirect to login page
			response.sendRedirect("login");
		}
	}

}
