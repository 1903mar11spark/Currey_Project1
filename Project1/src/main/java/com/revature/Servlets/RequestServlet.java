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
import com.revature.Beans.Requests;
import com.revature.Services.RequestService;


public class RequestServlet extends HttpServlet 
{
	
    private static final long serialVersionUID = 1L;
  
    
    private final RequestService reqService = new RequestService();
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
        resp.setContentType("application/json");
        Principal principal = (Principal) req.getAttribute("principal");
        
        String requestURI = req.getRequestURI();
        ObjectMapper mapper = new ObjectMapper();
        
        try 
        {
            resp.getWriter();
            
            if(principal == null) 
            {
            	System.out.println("No principal attribute found on request");
                resp.setStatus(401);
                return;
            }
            
            if(requestURI.equals("/Project1/requests") || requestURI.equals("/Project1/requests/"))
            {
                
                if (!principal.getRole().equalsIgnoreCase("manager")) 
                {
                    reqService.getByAuthor(Integer.parseInt(principal.getId()));
                    resp.setStatus(200);
                 
                } 
                else 
                {
                    List<Requests> req1 = reqService.getAll();
                    mapper.writeValueAsString(req1);
                    resp.setStatus(200);
                }   
            } 
        } 
        catch (NumberFormatException nfe) 
        {
                System.out.println(nfe.getMessage());
                resp.setStatus(400);
        } catch (Exception e) 
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
            resp.setStatus(500);
        }
    }
    
    
    


	@Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException 
    {
    	System.out.println("Request received by ReimbursementServlet.doPost()");
        Requests someRequest = null;
        
        resp.setContentType("application/json");
        Principal principal = (Principal) req.getAttribute("principal");
        
        ObjectMapper mapper = new ObjectMapper();
        
        try 
        {
        	someRequest = mapper.readValue(req.getInputStream(), Requests.class);
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
        
        if(someRequest.getReqStatus().getReqStatusName().equals("pending")) 
        {
            
        	someRequest.setAuthor(Integer.parseInt(principal.getId()));
        	
        	someRequest = reqService.add(someRequest);
        } 
        else 
        {
        	
        	someRequest.setResolver(Integer.parseInt(principal.getId()));
        	
        	someRequest = reqService.update(someRequest);
        }
        
        try 
        {
            String reimJson = mapper.writeValueAsString(someRequest);
            PrintWriter out = resp.getWriter();
            out.write(reimJson);
        } 
        catch (Exception e) {
        	System.out.println(e.getMessage());
            resp.setStatus(500);
        }
    }
    
}
