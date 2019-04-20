package com.revature.DAO;

import java.util.List;

import com.revature.Beans.Requests;

public interface RequestDAO 
{
	public List<Requests> getAllRequests();
	public List<Requests> getAllRequestsByStatusAndEmployee(int employeeId, String status);
	public List<Requests> getAllRequestsByStatus(String status);
	public Requests getRequestById(int id);
	
	public boolean addRequest(Requests r);
	public boolean updateRequest(int requestId, int managerId, String status);
	public boolean deleteRequestById(int id);
	
}
