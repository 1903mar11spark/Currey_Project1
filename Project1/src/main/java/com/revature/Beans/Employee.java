package com.revature.Beans;

public class Employee
{
	
    
    int id;
    String jobId;
    String userName;
    String passWord;
    String firstName, lastName;
    int reportsTo;
    boolean isManager;
	

    static int count = 0;

    public Employee()
    {
        this.id = -1;
        this.jobId = null;
        this.firstName = null;
        this.lastName = null;
        this.reportsTo = -1;
        
    }
    public Employee(int id, String job,  String fName, String lName, int manId, boolean isBoss, String Email)
    {
        this.id = id;
        this.jobId = job;
        this.firstName = fName;
        this.lastName = lName;
        this.reportsTo = manId;
        this.isManager = isBoss;
        System.out.println(this.toString());
    }
   
    
    public void setId(int num)
    {
    	this.id = num;
    }

    public int getId()
    {
        return this.id;
    }
    public int getManagerId()
    {
        return this.reportsTo;
    }

    public String getjob()
    {
        return this.jobId;
    }
    public String getFirstName()
    {
        return this.firstName;
    }
    public String getLastName()
    {
        return this.lastName;
    }
    public boolean IsAManager()
    {
        return this.isManager;
    }
}
