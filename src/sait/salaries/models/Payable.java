package sait.salaries.models;
/**
 Interface: Payable
 Author: Ryan Dezall
 Date: April 9, 2026
 
 Description: 
 	This interface is a core component of the Salaries Management System, 
 	designed to enforce payroll calculation rules. Inputs for this system include employee 
 	details and specific payment metrics such as annual salaries, hourly wages, or contract flat rates. 
 	The processing involves utilizing Object-Oriented principles, specifically polymorphism, to allow 
 	different employee types to calculate their pay uniquely. The final output is the accurately calculated 
 	monetary compensation for each specific worker. Specifically, this interface acts as a contract mandating that any implementing class must provide the mathematical logic for the calculatePay method.
 */

public interface Payable {

	// Method signature required for all implementing classes
	double calculatePay();
}
