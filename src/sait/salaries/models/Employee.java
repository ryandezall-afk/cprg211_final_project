package sait.salaries.models;

public abstract class Employee implements Payable{
	
	private int employeeID;
	private String name;
	
	public Employee() {
		
	}
	
	public Employee(int employeeID, String name) {
		this.employeeID = employeeID;
		this.name=name;
	}
	
	public int getEmployeeID() {
		return employeeID;
	}
	
	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
