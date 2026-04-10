package sait.salaries.models;

/**
Class: FullTimeEmployee
Author: Ryan Dezall
Date: April 9, 2026
 
Description: 
	This class is part of the Salaries Management System, specifically designed to handle data and calculations for salaried workers. 
 	Inputs for this specific class include an employee ID, name, base salary, and a benefits bonus amount. 
 	The processing involves utilizing inheritance to send the ID and name to the parent class, while calculating the total pay by adding the salary and bonus together. 
 	The final output is a complete Full-Time Employee object with an accurately calculated total compensation ready for the user interface.
 	This class overrides the calculatePay method to provide the specific mathematical logic for full-time staff.
 */

public class FullTimeEmployee extends Employee {
	
	// specific attributes
	private double benefitsBonus;
	private double baseSalary;
	
	// default constructor
	public FullTimeEmployee() {
		super();
	}
	
	// user-defined constructor
	public FullTimeEmployee(int employeeID, String name, double baseSalary, double benefitsBonus) {
		super(employeeID, name);
		this.baseSalary = baseSalary;
		this.benefitsBonus = benefitsBonus;
	}
	
	// getters and setter
	public double getBenefitsBonus() {
		return benefitsBonus;
	}
	
	public void setBenefitsBonus (double benefitsBonus) {
		this.benefitsBonus = benefitsBonus;
	}
	
	public double getBaseSalary() {
		return baseSalary;
	}
	
	public void setBaseSalary(double baseSalary) {
		this.baseSalary = baseSalary;
	}
	
	// pay calculation mandated by Payable interface
	@Override
	public double calculatePay() {
		return baseSalary + benefitsBonus;
	}
}
