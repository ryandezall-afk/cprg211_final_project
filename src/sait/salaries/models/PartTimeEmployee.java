package sait.salaries.models;

/**
Class: PartTimeEmployee
Author: Ryan Dezall
Date: April 9, 2026

Description: 
	This class is part of the Salaries Management System, specifically designed to handle data and payroll calculations for hourly wage workers. 
	Inputs for this specific class include an employee ID, name, hourly wage rate, and the number of hours worked (stored as a float to allow for partial hours). 
	The processing involves utilizing inheritance to manage the standard employee data, while calculating the total pay by multiplying the hours worked by the hourly wage. 
	The final output is a complete Part-Time Employee object containing accurate compensation data ready to be outputted to the console.
 	This class overrides the calculatePay method to provide the precise mathematical logic required for hourly staff.
 */

public class PartTimeEmployee extends Employee {
	
	// specific attributes
	private double hourlyWage;
	private float hoursWorked;
	
	// default constructor
	public PartTimeEmployee() {
		
	}
	
	// user-defined constructor
	public PartTimeEmployee(int employeeID, String name, double hourlyWage, float hoursWorked) {
		super(employeeID, name);
		this.hourlyWage = hourlyWage;
		this.hoursWorked = hoursWorked;
	}
	
	// getters and setters
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
	
	// pay calculation mandated by Payable interface
	@Override
	public double calculatePay() {
		return hoursWorked * hourlyWage;
	}
}
