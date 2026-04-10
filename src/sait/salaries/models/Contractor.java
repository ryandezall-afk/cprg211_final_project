package sait.salaries.models;

public class Contractor extends Employee {
	
	private int contractDuration;
	private double totalContractAmount;
	
	public Contractor() {
		
	}
	
	public Contractor(int employeeID, String name, int contractDuration, double totalContractAmount) {
		super(employeeID, name);
		this.contractDuration = contractDuration;
		this.totalContractAmount = totalContractAmount;
	}
	
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
	
	@Override
	public double calculatePay() {
		return totalContractAmount;
	}
}
