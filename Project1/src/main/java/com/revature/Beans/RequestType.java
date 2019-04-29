package com.revature.Beans;

public class RequestType {

	private int reqTypeId;
	private String reqTypeName;
	
	public RequestType() 
	{
		super();
	}
	
	public RequestType(int reqTypeId) {
		this.reqTypeId = reqTypeId;
		
		switch(reqTypeId) {
		case 1:
			this.reqTypeName = "lodging"; break;
		case 2:
			this.reqTypeName = "travel"; break;
		case 3:
			this.reqTypeName = "food"; break;
		case 4:
			this.reqTypeName = "other"; break;
		default:
			this.reqTypeName = null;
		}
	}
	
	public RequestType(String reqTypeName) 
	{
		super();
		this.reqTypeName = reqTypeName;
		
		switch(reqTypeName) 
		{
		case "lodging":
			this.reqTypeId = 1; break;
		case "travel":
			this.reqTypeId = 2; break;
		case "food":
			this.reqTypeId = 3; break;
		case "other":
			this.reqTypeId = 4; break;
		default:
			this.reqTypeId = 4;
		}
	}
	
	public RequestType(int reqTypeId, String reqTypeName) 
	{
		super();
		this.reqTypeId = reqTypeId;
		this.reqTypeName = reqTypeName;
	}

	public int getReqTypeId() 
	{
		return reqTypeId;
	}

	public void setReqTypeId(int reqTypeId) 
	{
		this.reqTypeId = reqTypeId;
	}

	public String getReqTypeName() 
	{
		return reqTypeName;
	}

	public void setReqTypeName(String reqTypeName) 
	{
		this.reqTypeName = reqTypeName;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + reqTypeId;
		result = prime * result + ((reqTypeName == null) ? 0 : reqTypeName.hashCode());
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
		RequestType other = (RequestType) obj;
		if (reqTypeId != other.reqTypeId)
			return false;
		if (reqTypeName == null) {
			if (other.reqTypeName != null)
				return false;
		} else if (!reqTypeName.equals(other.reqTypeName))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "ReimbursementType [reimbTypeId=" + reqTypeId + ", reimbTypeName=" + reqTypeName + "]";
	}
	
}
