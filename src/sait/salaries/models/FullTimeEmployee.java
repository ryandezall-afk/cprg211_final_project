package sait.salaries.models;

/**
 * Class: FullTimeEmployee
 * Author: Ryan Dezall
 * Date: April 11, 2026
 *
 * Description:
 * This class stores and processes salary information for full-time employees.
 * Inputs include the employee ID, employee name, base salary, and benefits
 * bonus. The processing uses inheritance from Employee and implements the
 * required payroll calculation by adding the base salary and bonus together.
 * The output is a complete full-time employee object that can be displayed,
 * validated, saved to MariaDB, and used in CRUD operations through the system.
 */
public class FullTimeEmployee extends Employee {

    // Attributes specific to full-time employees.
    private double benefitsBonus;
    private double baseSalary;

    // Default constructor.
    public FullTimeEmployee() {
        super();
    }

    // User-defined constructor.
    public FullTimeEmployee(int employeeID, String name, double baseSalary, double benefitsBonus) {
        super(employeeID, name);
        this.baseSalary = baseSalary;
        this.benefitsBonus = benefitsBonus;
    }

    // Getters and setters.
    public double getBenefitsBonus() {
        return benefitsBonus;
    }

    public void setBenefitsBonus(double benefitsBonus) {
        this.benefitsBonus = benefitsBonus;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    @Override
    public String getEmployeeType() {
        return "FULL_TIME";
    }

    // Pay calculation mandated by Payable.
    @Override
    public double calculatePay() {
        return baseSalary + benefitsBonus;
    }

    @Override
    public String toString() {
        return String.format(
                "%s | Base Salary: $%.2f | Benefits Bonus: $%.2f | Total Pay: $%.2f",
                getBasicDetails(), baseSalary, benefitsBonus, calculatePay());
    }
}