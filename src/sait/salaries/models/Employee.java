package sait.salaries.models;

/**
 * Class: Employee
 * Author: Ryan Dezall
 * Date: April 11, 2026
 *
 * Description:
 * This abstract class represents the shared foundation for all employee types
 * in the Salaries Management System. Inputs include common employee details
 * such as the employee ID and employee name. The processing uses inheritance
 * so these shared values do not need to be duplicated in every subclass, while
 * also forcing subclasses to provide their own employee type and pay logic.
 * The output is a clean and reusable base model that supports polymorphism,
 * database storage, validation, and command-line display.
 */
public abstract class Employee implements Payable {

    // Shared employee attributes.
    private int employeeID;
    private String name;

    // Default constructor.
    public Employee() {
    }

    // User-defined constructor.
    public Employee(int employeeID, String name) {
        this.employeeID = employeeID;
        this.name = name;
    }

    // Getters and setters.
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

    // Forces subclasses to identify their type for display and database storage.
    public abstract String getEmployeeType();

    // Shared formatting helper used by subclasses.
    protected String getBasicDetails() {
        return String.format("ID: %d | Name: %s | Type: %s", employeeID, name, getEmployeeType());
    }
}