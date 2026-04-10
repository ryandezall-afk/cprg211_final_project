package sait.salaries.models;

/**
Class: Employee
Author: Ryan Dezall
Date: April 9, 2026

Description: 
	This abstract class is part of the Salaries Management System, designed to handle shared employee data and establish a class hierarchy. 
	Inputs for this system include universal employee details such as identification numbers and full names. 
	The processing involves utilizing Object-Oriented inheritance to pass these shared attributes down to specific child classes, preventing redundant code. 
	The final output is structured employee data that is properly encapsulated and ready to be stored in the MariaDB database.
	As an abstract class implementing Payable, it forces all child classes to define their own specific pay calculations.
 */

public abstract class Employee implements Payable{
	
	// Encapsulate shared attributes
	private int employeeID;
	private String name;
	
	// Default constructor
	public Employee() {
		
	}
	
	//User-defined constructor
	public Employee(int employeeID, String name) {
		this.employeeID = employeeID;
		this.name=name;
	}
	
	// getters and setters
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
