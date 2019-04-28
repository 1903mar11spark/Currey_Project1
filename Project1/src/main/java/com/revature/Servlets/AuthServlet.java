package com.revature.Servlets;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.Beans.Employee;
import com.revature.Services.EmployeeService;


public class AuthServlet extends HttpServlet 
{

	private static final long serialVersionUID = 1L;
	
	private final EmployeeService userService = new EmployeeService();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException 
	{
		ObjectMapper mapper = new ObjectMapper();
		String[] credentials = null;
//		System.out.println(req.getInputStream());
//		System.out.println(mapper.readValue(req.getInputStream(), String[].class));
		try 
		{
			credentials = mapper.readValue(req.getInputStream(), String[].class);
			
		} 
		catch (MismatchedInputException mie) 
		{
			System.out.println(mie.getMessage());
			resp.setStatus(400);
			return;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println(e.getMessage());
			resp.setStatus(500);
			return;
		}
		
		if(credentials != null && credentials.length != 2) {
			System.out.println("Invalid request, unexpected number of credential values");
			resp.setStatus(400);
			return;
		}
		
		Employee user = userService.getByCredentials(credentials[0], credentials[1]);
		
		if(user == null) {
			resp.setStatus(401);
			return;
		}
		
		resp.setStatus(200);
	}
}