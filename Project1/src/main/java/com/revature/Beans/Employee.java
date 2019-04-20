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

    public Employee(int i, String job, String fName, String lName)
    {
        this.id = -1;
        this.jobId = null;
        this.firstName = null;
        this.lastName = null;
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
	@Override
	public String toString() {
		return "Employee [id=" + id + ", jobId=" + jobId + ", userName=" + userName + ", passWord=" + passWord
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", reportsTo=" + reportsTo + ", isManager="
				+ isManager + "]";
	}

    
}
