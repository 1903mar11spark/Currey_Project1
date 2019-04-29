package com.revature.Beans;

public class RequestStatus 
{

	private int reqStatusId;
	private String reqStatusName;
	
	public RequestStatus() 
	{
		super();
	}
	
	public RequestStatus(int reqStatusId)
{
		this.reqStatusId = reqStatusId;
		
		switch(reqStatusId) 
		{
		case 1:
			this.reqStatusName = "pending"; break;
		case 2:
			this.reqStatusName = "approved"; break;
		case 3:
			this.reqStatusName = "denied"; break;
		default:
			this.reqStatusName = null;
		}
	}
	
	public RequestStatus(String reqStatusName) 
	{
		super();
		this.reqStatusName = reqStatusName;
		
		switch(reqStatusName) 
		{
		case "pending":
			this.reqStatusId = 1; break;
		case "approved":
			this.reqStatusId = 2; break;
		case "denied":
			this.reqStatusId = 3; break;
		default:
			this.reqStatusId = 1;
		}
	}
	
	public RequestStatus(int reqStatusId, String reqStatusName) 
	{
		super();
		this.reqStatusId = reqStatusId;
		this.reqStatusName = reqStatusName;
	}

	public int getReqStatusId() 
	{
		return reqStatusId;
	}

	public void setReqStatusId(int reqStatusId) 
	{
		this.reqStatusId = reqStatusId;
	}

	public String getReqStatusName() {
		return reqStatusName;
	}

	public void setReqStatusName(String reqStatusName) {
		this.reqStatusName = reqStatusName;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + reqStatusId;
		result = prime * result + ((reqStatusName == null) ? 0 : reqStatusName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestStatus other = (RequestStatus) obj;
		if (reqStatusId != other.reqStatusId)
			return false;
		if (reqStatusName == null) 
		{
			if (other.reqStatusName != null)
				return false;
		} 
		else if (!reqStatusName.equals(other.reqStatusName))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "RequestStatus [reqStatusId=" + reqStatusId + ", reqStatusName=" + reqStatusName + "]";
	}
	
}

	
