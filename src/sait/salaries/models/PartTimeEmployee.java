package sait.salaries.models;

/**
 * Class: PartTimeEmployee
 * Author: Ryan Dezall
 * Date: April 11, 2026
 *
 * Description:
 * This class stores and processes hourly payroll information for part-time
 * employees. Inputs include the employee ID, employee name, hourly wage, and
 * hours worked. The processing uses inheritance from Employee and calculates
 * total pay by multiplying hours worked by the hourly wage. The output is a
 * complete part-time employee object that can be validated, displayed in the
 * CLI, and stored or updated through the connected database layer.
 */
public class PartTimeEmployee extends Employee {

    // Attributes specific to part-time employees.
    private double hourlyWage;
    private float hoursWorked;

    // Default constructor.
    public PartTimeEmployee() {
    }

    // User-defined constructor.
    public PartTimeEmployee(int employeeID, String name, double hourlyWage, float hoursWorked) {
        super(employeeID, name);
        this.hourlyWage = hourlyWage;
        this.hoursWorked = hoursWorked;
    }

    // Getters and setters.
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
    public String getEmployeeType() {
        return "PART_TIME";
    }

    // Pay calculation mandated by Payable.
    @Override
    public double calculatePay() {
        return hoursWorked * hourlyWage;
    }

    @Override
    public String toString() {
        return String.format(
                "%s | Hourly Wage: $%.2f | Hours Worked: %.2f | Total Pay: $%.2f",
                getBasicDetails(), hourlyWage, hoursWorked, calculatePay());
    }
}