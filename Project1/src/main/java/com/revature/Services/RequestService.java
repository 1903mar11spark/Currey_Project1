package com.revature.Services;

import java.util.List;

import com.revature.DAO.RequestDAO;
import com.revature.Beans.Requests;

public class RequestService 
{
	private RequestDAO reqDao = new RequestDAO();
	
	public List<Requests> getAll()
	{
		return reqDao.getAll();
	}
	
	public Requests getById(int reimbId) 
	{
		return reqDao.getById(reimbId);
	}
	
	public List<Requests> getByAuthor(int reqAuthor) 
	{
		return reqDao.getByAuthor(reqAuthor);
	}
	
	public Requests add(Requests newRequests) 
	{
		//check if all fields are not empty, except for receipt
		if(newRequests.getAmount() == 0 
				|| newRequests.getSubmitted().equals("") 
				|| newRequests.getDescription().equals("") 
				|| newRequests.getAuthor() == 0
				|| newRequests.getReqStatus().equals(null) 
				|| newRequests.getReqType().equals(null)) {
			System.out.println("New Reimbursement has empty fields!");
			return null;
		}
		return reqDao.add(newRequests);
	}
	
	public Requests update(Requests updatedRequests) 
	{
		
		//check if all fields are not empty, except for receipt
		if(updatedRequests.getAmount() == 0 || updatedRequests.getSubmitted().equals("") 
				|| updatedRequests.getResolved().equals("") 
				|| updatedRequests.getDescription().equals("") 
				|| updatedRequests.getAuthor() == 0
				|| updatedRequests.getResolver() == 0 
				|| updatedRequests.getReqStatus().equals(null) 
				|| updatedRequests.getReqType().equals(null)) 
		{
			System.out.println("Updated Reimbursement has empty fields!");
			return null;
		}
			
		
		
		//make sure finance manager cannot approve/deny their own requests
		if(updatedRequests.getResolver() == updatedRequests.getAuthor())
		{
			System.out.println("Finance Manager unauthorized to approve/deny own reimbursement request!");
			return null;
		}
		
		
		
		//Attempt to update updatedRequests on to database
		Requests persistedReimbursement = reqDao.update(updatedRequests);
		
		//Verify if update was successful
		if(persistedReimbursement != null) 
		{
			return updatedRequests;
		}
		
		//if update was unsuccessful return null 
		System.out.println("Reimbursement update failed");
		return null;
	}
	
	public boolean delete(int Requests) 
	{
		return false;
	}
	
}