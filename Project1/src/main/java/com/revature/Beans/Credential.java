package com.revature.Beans;

public class Credential
{
	int empId;
	String userName, userPassword;
	String eMail;
	
	public Credential(int id, String user, String password, String email)
	{
		this.empId = id;
		this.userName = user;
		this.userPassword = password;
		this.eMail = email;
	}

	public int getEmployeeId()
	{
		return empId;
	}

	public String getUserName()
	{
		return userName;
	}

	public String getPassWord()
	{
		return userPassword;
	}

	public String getEMail()
    {
        return this.eMail;
    }
}
