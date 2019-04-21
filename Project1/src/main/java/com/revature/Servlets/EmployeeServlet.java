//idk what im doing anymore
package com.revature.Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.revature.Beans.Principal;
import com.revature.Beans.Employee;
import com.revature.Services.EmployeeService;

public class EmployeeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private final EmployeeService userService = new EmployeeService();

    
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        Principal principal = (Principal) req.getAttribute("principal");

        String requestURI = req.getRequestURI();
        ObjectMapper mapper = new ObjectMapper();

        try 
        {
            PrintWriter out = resp.getWriter();
            if(principal == null) 
            {
            	System.out.println("No principal attribute found on request");
                resp.setStatus(401);
                return;
            }

           
            if(requestURI.equals("/employee") || requestURI.equals("/employee/")) 
            {
                if (!principal.getRole().equalsIgnoreCase("manager")) 
                {
                    Employee user = userService.getById(Integer.parseInt(principal.getId()));
                    String userJSON = mapper.writeValueAsString(user);
                    resp.setStatus(200);
                    out.write(userJSON);
                } 
                
                else 
                {
                    List<Employee> users = userService.getAll();
                    String usersJSON = mapper.writeValueAsString(users);
                    resp.setStatus(200);
                    out.write(usersJSON);
                }
            } 
            
            else if (requestURI.contains("users/")) 
            {   
                String[] fragments = requestURI.split("/");
              
                String userId = fragments[3];
                    
                if (!principal.getRole().equalsIgnoreCase("manager") && !principal.getId().equalsIgnoreCase(userId)) 
                {
                	System.out.println("Unauthorized access attempt made from origin: " + req.getLocalAddr());
                    resp.setStatus(401);
                    return;
                }

                Employee user = userService.getById(Integer.parseInt(userId));
                String userJSON = mapper.writeValueAsString(user);
                resp.setStatus(200);
                out.write(userJSON);
            } 

        } 
        
        catch (NumberFormatException nfe) 
        {
        		System.out.println(nfe.getMessage());
                resp.setStatus(400);
        } 
        
        catch (Exception e) 
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            resp.setStatus(500);
        }

    }

    

    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException 
    {
    	System.out.println("Request received by UserServlet.doPost()");
        Employee newUser = null;
        ObjectMapper mapper = new ObjectMapper();
        

        try 
        {
            newUser = mapper.readValue(req.getInputStream(), Employee.class);
        } 
        catch (MismatchedInputException mie) 
        {
        	System.out.println(mie.getMessage());
            resp.setStatus(400);
            return;
        } 
        catch (Exception e) 
        {
        	System.out.println(e.getMessage());
            resp.setStatus(500);
            return;
        }

            

        EmployeeService userService = new EmployeeService();
        List<Employee> users = userService.getAll();
        for(Employee user: users) 
        {
            if(user.getUsername().equals(newUser.getUsername())) 
            {
                resp.setStatus(409);
            }
        }       
        
        newUser = userService.add(newUser);

        

        try 
        {
            String userJson = mapper.writeValueAsString(newUser);
            PrintWriter out = resp.getWriter();
            out.write(userJson);
        } 
        catch (Exception e) 
        {
        	System.out.println(e.getMessage());
            resp.setStatus(500);
        }
    }
}
