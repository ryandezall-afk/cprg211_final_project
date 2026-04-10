package sait.salaries.models;

public class FullTimeEmployee extends Employee {
	
	private double benefitsBonus;
	private double baseSalary;
	
	public FullTimeEmployee() {
		super();
	}
	
	public FullTimeEmployee(int employeeID, String name, double baseSalary, double benefitsBonus) {
		super(employeeID, name);
		this.baseSalary = baseSalary;
		this.benefitsBonus = benefitsBonus;
	}
	
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
	
	@Override
	public double calculatePay() {
		return baseSalary + benefitsBonus;
	}
}
