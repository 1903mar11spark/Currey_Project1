package com.revature.Beans;

public class Requests 
{
	
	private int id;
	private int amount;
	private String submitted;
	private String resolved;
	private String description;
	private Object receipt;
	private int author;
	private int resolver;
	private RequestStatus reqStatus;
	private RequestType reqType;
	
	public Requests() 
	{
		super();
	}
	
	public Requests(int id, int amount, String submitted, String resolved, String description, Object receipt, int author, int resolver, RequestStatus  reqStatus, RequestType reqType) 
	{
		this.id = id;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.receipt = receipt;
		this.author = author;
		this.resolver = resolver;
		this.reqStatus = reqStatus;
		this.reqType = reqType;
	}

	public int getId() 
	{
		return id;
	}

	public void setId(int id) 
	{
		this.id = id;
	}

	public int getAmount() 
	{
		return amount;
	}

	public void setAmount(int amount) 
	{
		this.amount = amount;
	}

	public String getSubmitted() 
	{
		return submitted;
	}

	public void setSubmitted(String submitted) 
	{
		this.submitted = submitted;
	}

	public String getResolved() 
	{
		return resolved;
	}

	public void setResolved(String resolved) 
	{
		this.resolved = resolved;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	public Object getReceipt() 
	{
		return receipt;
	}

	public void setReceipt(Object receipt) 
	{
		this.receipt = receipt;
	}

	public int getAuthor() 
	{
		return author;
	}

	public void setAuthor(int author) 
	{
		this.author = author;
	}

	public int getResolver() 
	{
		return resolver;
	}

	public void setResolver(int resolver) 
	{
		this.resolver = resolver;
	}

	public RequestStatus getReqStatus() 
	{
		return reqStatus;
	}

	public void setReqStatus(RequestStatus  reqStatus) 
	{
		this.reqStatus = reqStatus;
	}

	public RequestType  getReqType() 
	{
		return reqType;
	}

	public void setReqType(RequestType  reqType) 
	{
		this.reqType = reqType;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + author;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((receipt == null) ? 0 : receipt.hashCode());
		result = prime * result + ((reqStatus == null) ? 0 : reqStatus.hashCode());
		result = prime * result + ((reqType == null) ? 0 : reqType.hashCode());
		result = prime * result + ((resolved == null) ? 0 : resolved.hashCode());
		result = prime * result + resolver;
		result = prime * result + ((submitted == null) ? 0 : submitted.hashCode());
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
		Requests other = (Requests) obj;
		if (amount != other.amount)
			return false;
		if (author != other.author)
			return false;
		if (description == null) 
		{
			if (other.description != null)
				return false;
		} 
		else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (receipt == null) 
		{
			if (other.receipt != null)
				return false;
		} 
		else if (!receipt.equals(other.receipt))
			return false;
		if (reqStatus == null) 
		{
			if (other.reqStatus != null)
				return false;
		} 
		else if (!reqStatus.equals(other.reqStatus))
			return false;
		if (reqType == null) 
		{
			if (other.reqType != null)
				return false;
		} 
		else if (!reqType.equals(other.reqType))
			return false;
		if (resolved == null) 
		{
			if (other.resolved != null)
				return false;
		} 
		else if (!resolved.equals(other.resolved))
			return false;
		if (resolver != other.resolver)
			return false;
		if (submitted == null) 
		{
			if (other.submitted != null)
				return false;
		} 
		else if (!submitted.equals(other.submitted))
			return false;
		return true;
	}

	@Override
	public String toString() 
	{
		return "Reimbursement [id=" + id + ", amount=" + amount + ", submitted=" + submitted + ", resolved=" + resolved
				+ ", description=" + description + ", receipt=" + receipt + ", author=" + author + ", resolver="
				+ resolver + ", reimbStatus=" + reqStatus + ", reimbType=" + reqType + "]";
	}
	
}