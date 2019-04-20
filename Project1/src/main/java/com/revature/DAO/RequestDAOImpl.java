package com.revature.DAO;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.Beans.Requests;
import com.revature.Util.ConnectionUtil;

public class RequestDAOImpl extends DAO implements RequestDAO
{
	public List<Requests> getAllRequests()
	{
		return getAllRequests("");
	}
	protected List<Requests> getAllRequests(String sql)
	{
		List<Requests> requests = new ArrayList<Requests>();
		return requests;
	}
		
	public List<Requests> getAllRequestsByStatusAndEmployee(int employeeId, String status)
	{
		String sql = String.format("WHERE EMPLOYEES = EMPLOYEE_ID AND R_STATUS = 'STATUS'", employeeId, status);

		return getAllRequests(sql);
	}

	public List<Requests> getAllRequestsByStatus(String status)
	{
		String sql = String.format("WHERE R_STATUS = 'STATUS'", status);

		return getAllRequests(sql);
	}

	public Requests getRequestById(int id)
	{
		String sql = String.format("WHERE REQUESTS_ID = %D", id);
	
		return  this.getAllRequests(sql).get(0);
	}

	public boolean addRequest(Requests r)
	{
		try
		{
			connection = ConnectionUtil.getConnection();
			String sql = "INSERT INTO REQUESTS (AMOUNT, REQUEST_ID, DESCRIPTN, RECIEPT) VALUES (?, ?, ?, ?)";
			stmt = connection.prepareStatement(sql);

//			stmt.setInt(1, r.getId());
			stmt.setDouble(1, r.getAmount());
			stmt.setInt(2, r.getEmployeeId());
			stmt.setString(3, r.getDescription());
			

			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		} finally
		{
			closeResources();
		}
	}

	public boolean updateRequest(int requestId, int managerId, String status)
	{
		try
		{
			connection = ConnectionUtil.getConnection();
			String sql = "UPDATE REQUESTS SET R_STATUS = ? , R_RESOLVER = ?"
					+"WHERE EMPLOYEE_ID = ?";
			stmt = connection.prepareStatement(sql);

//			status = "'"+status+"'";
			stmt.setString(1, status);
			stmt.setInt(2, managerId);
			stmt.setInt(3, requestId);

			if (stmt.executeUpdate() != 0)
				return true;
			else
				return false;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		} finally
		{
			closeResources();
		}
	}

	public boolean deleteRequestById(int id)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	List<Requests> ParseReimbursement(ResultSet rs)
	{
		List<Requests> requests = null;
		try
		{
			requests = new ArrayList<Requests>();
			while (rs.next())
			{
				int requestId = rs.getInt("REQUEST_ID");
				double amount = rs.getDouble("AMOUNT");
				int employee = rs.getInt("EMPLOYEE_ID");
				String status = rs.getString("R_STATUS");
				byte[] image = rs.getBytes("RECIEPT");
				Date date = Date.valueOf(rs.getString("DATE"));
				String desc = rs.getString("DESCRIPTN");
				int finisher = rs.getInt("RESEOLVED");
				Requests request = new Requests(requestId, amount, employee, status, image, date, desc, finisher);
				requests.add(request);
			}
		}catch(Exception exception)
		{
			exception.printStackTrace();
		}
		return requests;
	}
}
