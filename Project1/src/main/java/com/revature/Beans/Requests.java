package com.revature.Beans;

import java.sql.Date;
import java.util.Base64;

public class Requests 
{
	
/*
REQUEST_ID INTEGER PRIMARY KEY,
AMOUNT        NUMBER,
SUBMITTED     VARCHAR2 (20),
RESEOLVED      VARCHAR2 (20),
DESCRIPTN   VARCHAR2 (250),
RECIEPT       BLOB,
R_AUTHOR        NUMBER,
R_RESOLVER      NUMBER,
R_STATUS_ID     NUMBER,
R_TYPE_ID       NUMBER
*/
	
	
	public enum Status
	{
		pending, accepted, rejected, invalid
	}

	int reqid;
	double amount;
	int employeeId;
	Status status;
	Date date;
	String description;
	String imageString;
	int finalizedBy;
	
	public Requests(int id, double money, int empId, String stat, byte[] imgData, Date date, String desc, int finisher)
	{
		this.reqid = id;
		this.amount = money;
		this.employeeId = empId;
		this.status = stat != null? Status.valueOf(stat.toLowerCase()): null;
		this.date = date;
		this.description = desc;
		this.finalizedBy = finisher;
	}
	public Requests(double money, int empId, String desc)
	{
		this.amount = money;
		this.employeeId = empId;
		this.description = desc;
	}
	
	@Override
	public String toString()
	{
		return "Requests [id=" + reqid + ", amount=" + amount + ", employeeId=" + employeeId + ", status=" + status
				+", date=" + date + ", description=" + description +"]";
	}
	public int getId()
	{
		return reqid;
	}

	public double getAmount()
	{
		return amount;
	}

	public int getEmployeeId()
	{
		return employeeId;
	}

	public String getStatus()
	{
		return status.toString();
	}
	public int getFinisher()
	{
		return finalizedBy;
	}
	public Date getDate() 
	{
		return date;
	}
	public String getDescription() 
	{
		return description;
	}
	public void setStatus(Status status) 
	{
		this.status = status;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}

	public void setDate(Date date) 
	{
		this.date = date;
	}
}
