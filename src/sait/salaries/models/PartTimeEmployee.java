package sait.salaries.models;

public class PartTimeEmployee extends Employee {
	
	private double hourlyWage;
	private float hoursWorked;
	
	public PartTimeEmployee() {
		
	}
	
	public PartTimeEmployee(int employeeID, String name, double hourlyWage, float hoursWorked) {
		super(employeeID, name);
		this.hourlyWage = hourlyWage;
		this.hoursWorked = hoursWorked;
	}
	
	public double getHourlyWage() {
		return hourlyWage;
	}
	
	public void setHourlyWage(double hourlyWage) {
		this.hourlyWage = hourlyWage;
	}
	
	public float getHoursWorked() {
		return hoursWorked;
	}
	
	public void setHoursWorked(float hoursWorked) {
		this.hoursWorked = hoursWorked;
	}
	
	@Override
	public double calculatePay() {
		return hoursWorked * hourlyWage;
	}
}
