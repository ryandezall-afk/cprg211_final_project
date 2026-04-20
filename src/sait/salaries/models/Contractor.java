package sait.salaries.models;

/**
 * Class: Contractor
 * Author: Ryan Dezall
 * Date: April 11, 2026
 *
 * Description:
 * This class stores and processes payroll information for contractors who are
 * paid through a flat contract amount. Inputs include the employee ID, name,
 * contract duration, and total contract amount. The processing uses the shared
 * Employee base class while applying contractor-specific logic for pay, which
 * is simply the contract amount itself. The output is a contractor object that
 * supports polymorphism, CRUD operations, and clean display within the salary
 * management system.
 */
public class Contractor extends Employee {

    // Attributes specific to contractors.
    private int contractDuration;
    private double totalContractAmount;

    // Default constructor.
    public Contractor() {
    }

    // User-defined constructor.
    public Contractor(int employeeID, String name, int contractDuration, double totalContractAmount) {
        super(employeeID, name);
        this.contractDuration = contractDuration;
        this.totalContractAmount = totalContractAmount;
    }

    // Getters and setters.
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
    public String getEmployeeType() {
        return "CONTRACTOR";
    }

    // Pay calculation mandated by Payable.
    @Override
    public double calculatePay() {
        return totalContractAmount;
    }

    @Override
    public String toString() {
        return String.format(
                "%s | Contract Duration: %d months | Contract Amount: $%.2f | Total Pay: $%.2f",
                getBasicDetails(), contractDuration, totalContractAmount, calculatePay());
    }
}