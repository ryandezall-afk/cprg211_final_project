package sait.salaries.models;

/**
Class: Contractor
Author: Ryan Dezall
Date: April 9, 2026

Description: 
	This class is part of the Salaries Management System, specifically designed to handle data for workers hired on a temporary, flat-rate basis. 
	Inputs for this specific class include an employee ID, name, the duration of the contract, and the total flat-rate contract amount. 
 	The processing involves utilizing Object-Oriented inheritance to manage the baseline employee data while securely encapsulating the specific contract details. 
 	The final output is a complete Contractor object that simply returns the agreed-upon flat rate as their total compensation.
 	This class overrides the calculatePay method to bypass complex math and simply output the total contract amount.
 */

public class Contractor extends Employee {
	
	// specific attributes
	private int contractDuration;
	private double totalContractAmount;
	
	// default constructor
	public Contractor() {
		
	}
	
	// user-defined constructor
	public Contractor(int employeeID, String name, int contractDuration, double totalContractAmount) {
		super(employeeID, name);
		this.contractDuration = contractDuration;
		this.totalContractAmount = totalContractAmount;
	}
	
	// getters and setters
	public int getContractDuration() {
		return contractDuration;
	}
	
	public void setContractDuration(int contractDuration) {
		this.contractDuration = contractDuration;
	}
	
	public double getTotalContractAmount() {
		return totalContractAmount;
	}
	
	public void setTotalContractAmount(double totalContractAmount) {
		this.totalContractAmount = totalContractAmount;
	}
	
	// pay calculation mandated by Payable interface
	@Override
	public double calculatePay() {
		return totalContractAmount;
	}
}
