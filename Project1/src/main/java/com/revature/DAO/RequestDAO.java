package com.revature.DAO;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.revature.Beans.Requests;
import com.revature.Beans.RequestStatus;
import com.revature.Beans.RequestType;
import com.revature.Util.ConnectionUtil;

import oracle.jdbc.internal.OracleTypes;

public class RequestDAO implements DAO<Requests> 
{
	
	
	@Override
	public List<Requests> getAll() 
	{

		List<Requests> requests = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnectionFromFile())  
		{

			CallableStatement cstmt = conn.prepareCall("{CALL GET_ALL_REQUESTS(?)}");
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();

			ResultSet rs = (ResultSet) cstmt.getObject(1);
			requests = this.mapResultSet(rs);

		} 
		catch (SQLException | IOException e) 
		{
			System.out.println(e.getMessage());
		}

		return requests;
	}
	
	@Override
	public Requests getById(int reqId) 
	{
		
		Requests requests = new Requests();

		try (Connection conn = ConnectionUtil.getConnectionFromFile())  
		{

			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM REQUESTS WHERE REQUEST_ID = ?");
			pstmt.setInt(1, reqId);

			ResultSet rs = pstmt.executeQuery();
			requests = this.mapResultSet(rs).get(0);

		} 
		catch (SQLException | IOException e) 
		{
			System.out.println(e.getMessage());
		}

		return requests;
	}
	
	public List<Requests> getByAuthor(int author) 
	{
		
		List<Requests> requestsList = new ArrayList<>();
		
		try(Connection conn = ConnectionUtil.getConnectionFromFile())  
		{
			
			String sql = "SELECT * FROM REQUESTS FULL OUTER JOIN REQUEST_STATUS "
					+ "USING (R_STATUS_ID) FULL OUTER JOIN REQUEST_TYPE "
					+ "USING (R_TYPE_ID) JOIN EMPLOYEES ON REQUESTS.R_AUTHOR = EMPLOYEES.EMPLOYEE_ID "
					+ "WHERE R_AUTHOR = ? ORDER BY REQUEST_ID DESC";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, author);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Requests temp = new Requests();
				temp.setId(rs.getInt("REQUEST_ID"));
				temp.setAmount(rs.getInt("AMOUNT"));
				temp.setSubmitted(rs.getString("SUBMITTED"));
				temp.setResolved(rs.getString("RESEOLVED"));
				temp.setDescription(rs.getString("DESCRIPTN"));
				temp.setReceipt(rs.getObject("RECIEPT"));
				temp.setAuthor(rs.getInt("R_AUTHOR"));
				temp.setResolver(rs.getInt("R_RESOLVER"));
				temp.setReqStatus(new RequestStatus(rs.getInt("R_STATUS_ID")));
				temp.setReqType(new RequestType(rs.getInt("R_TYPE_ID")));
				requestsList.add(temp);
			}
			
		} 
		catch (SQLException | IOException e) 
		{
			e.printStackTrace();
		}
		
		return requestsList;
	}
	
	@Override
	public Requests add(Requests newRequest) 
	{
		
		try(Connection conn = ConnectionUtil.getConnectionFromFile())  
		{
			
			conn.setAutoCommit(false);
			
			String sql = "INSERT INTO REQUESTS VALUES (0, ?, ?, null, ?, null, ?, null, 1, ?)";
			
			String[] keys = new String[1];
			keys[0] = "REQUEST_ID";
			
			PreparedStatement pstmt = conn.prepareStatement(sql, keys);
			pstmt.setInt(1, newRequest.getAmount());
			pstmt.setString(2, newRequest.getSubmitted());
			pstmt.setString(3, newRequest.getDescription());
			pstmt.setInt(4, newRequest.getAuthor());
			pstmt.setInt(5, newRequest.getReqType().getReqTypeId());
			
			int rowsInserted = pstmt.executeUpdate();
			ResultSet rs = pstmt.getGeneratedKeys();
			
			if(rowsInserted != 0) {
				
				while(rs.next()) {
					newRequest.setId(rs.getInt(1));
				}
				
				conn.commit();
				
			}
					
		} catch (SQLException | IOException e) 
		{
			e.printStackTrace();
		}
		
		if(newRequest.getId() == 0) return null;
		
		return newRequest;
	}
	
	@Override
	public Requests update(Requests updatedRequests) 
	{
		
		try(Connection conn = ConnectionUtil.getConnectionFromFile())  
		{
			
			conn.setAutoCommit(false);
			
			String sql = "UPDATE REQUESTS SET AMOUNT = ?, RESEOLVED = ?, DESCRIPTN = ?, "
					+ "R_RESOLVER = ?, R_STATUS_ID = ?, R_TYPE_ID = ? WHERE REQUEST_ID = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, updatedRequests.getAmount());
			pstmt.setString(2, updatedRequests.getResolved());
			pstmt.setString(3, updatedRequests.getDescription());
			pstmt.setInt(4, updatedRequests.getResolver());
			pstmt.setInt(5, updatedRequests.getReqStatus().getReqStatusId());
			pstmt.setInt(6, updatedRequests.getReqType().getReqTypeId());
			pstmt.setInt(7, updatedRequests.getId());
			
			if(pstmt.executeUpdate() != 0) 
			{
				conn.commit();
				return updatedRequests;
			}
			
		} catch (SQLException | IOException e) 
		{
			System.out.println(e.getMessage());
		}
		
		return null;
	}
	
	@Override
	public boolean delete(int reimbId) 
	{
		return false;
	}
	
	private List<Requests> mapResultSet(ResultSet rs) throws SQLException 
	{
		
		List<Requests> requests = new ArrayList<>();
		
		while(rs.next()) 
		{
			Requests requests1 = new Requests();
			requests1.setId(rs.getInt("REQUEST_ID"));
			requests1.setAmount(rs.getInt("AMOUNT"));
			requests1.setSubmitted(rs.getString("SUBMITTED"));
			requests1.setResolved(rs.getString("RESEOLVED"));
			requests1.setDescription(rs.getString("DESCRIPTN"));
			requests1.setReceipt(rs.getObject("RECIEPT"));
			requests1.setAuthor(rs.getInt("R_AUTHOR"));
			requests1.setResolver(rs.getInt("R_RESOLVER"));
			requests1.setReqStatus(new RequestStatus(rs.getInt("R_STATUS_ID")));
			requests1.setReqType(new RequestType(rs.getInt("R_TYPE_ID")));
			//requests.add(requests);
		}
		
		return requests;
	}
}