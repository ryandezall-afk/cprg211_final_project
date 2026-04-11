package sait.salaries.application;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import sait.salaries.database.DatabaseConnection;
import sait.salaries.database.EmployeeManager;
import sait.salaries.exceptions.EmployeeNotFoundException;
import sait.salaries.exceptions.InvalidPayException;
import sait.salaries.models.Contractor;
import sait.salaries.models.Employee;
import sait.salaries.models.FullTimeEmployee;
import sait.salaries.models.PartTimeEmployee;

/**
 * Class: Main
 * Author: Ryan Dezall, Jabin Unger, John Eric Acilo
 * Date: April 11, 2026
 *
 * Description:
 * This class provides the command-line user interface for the Salaries
 * Management System final project. Inputs come from the user through menu
 * selections and keyboard entry for employee details. The processing connects
 * the CLI to the EmployeeManager so the program can perform full CRUD
 * operations, validate user input, handle custom exceptions, and store all
 * employee data in MariaDB. The output is an interactive terminal-based system
 * that demonstrates inheritance, interfaces, abstract classes, exceptions, and
 * database integration as required by the project instructions.
 */
public class Main {

    private static final Scanner SCANNER = new Scanner(System.in);
    private static final EmployeeManager EMPLOYEE_MANAGER = new EmployeeManager();

    public static void main(String[] args) {
        try {
            DatabaseConnection.initializeDatabase();
            runApplication();
        } catch (SQLException exception) {
            System.out.println("Unable to start the application because the database could not be initialized.");
            System.out.println("Technical details: " + exception.getMessage());
        } finally {
            SCANNER.close();
        }
    }

    // Runs the main menu loop.
    private static void runApplication() {
        boolean running = true;

        System.out.println("============================================");
        System.out.println("   Salaries Management System - Final App   ");
        System.out.println("============================================");

        while (running) {
            displayMenu();
            int choice = readMenuChoice();

            switch (choice) {
            case 1:
                addEmployee();
                break;
            case 2:
                displayAllEmployees();
                break;
            case 3:
                searchEmployeeById();
                break;
            case 4:
                updateEmployee();
                break;
            case 5:
                deleteEmployee();
                break;
            case 6:
                running = false;
                System.out.println("Application closed. Goodbye.");
                break;
            default:
                System.out.println("Invalid menu choice.");
                break;
            }
        }
    }

    // Displays the application menu.
    private static void displayMenu() {
        System.out.println();
        System.out.println("1. Add employee");
        System.out.println("2. Display all employees");
        System.out.println("3. Search employee by ID");
        System.out.println("4. Update employee");
        System.out.println("5. Delete employee");
        System.out.println("6. Exit");
    }

    // Adds a new employee using typed user input.
    private static void addEmployee() {
        try {
            Employee employee = buildEmployeeFromInput(false, 0);
            EMPLOYEE_MANAGER.addEmployee(employee);
            System.out.println("Employee added successfully.");
        } catch (InvalidPayException exception) {
            System.out.println("Validation error: " + exception.getMessage());
        } catch (SQLException exception) {
            System.out.println("Database error while adding employee: " + exception.getMessage());
        }
    }

    // Displays all employees in the database.
    private static void displayAllEmployees() {
        try {
            List<Employee> employees = EMPLOYEE_MANAGER.getAllEmployees();

            if (employees.isEmpty()) {
                System.out.println("No employees found in the system.");
                return;
            }

            System.out.println();
            System.out.println("------------- Employee Records -------------");
            for (Employee employee : employees) {
                System.out.println(employee);
            }
            System.out.println("--------------------------------------------");
        } catch (SQLException exception) {
            System.out.println("Database error while reading employees: " + exception.getMessage());
        }
    }

    // Displays one employee by ID.
    private static void searchEmployeeById() {
        int employeeID = readPositiveInt("Enter employee ID to search: ");

        try {
            Employee employee = EMPLOYEE_MANAGER.getEmployeeById(employeeID);
            System.out.println("Employee found:");
            System.out.println(employee);
        } catch (EmployeeNotFoundException exception) {
            System.out.println(exception.getMessage());
        } catch (SQLException exception) {
            System.out.println("Database error while searching employee: " + exception.getMessage());
        }
    }

    // Updates an existing employee. The employee type can also be changed.
    private static void updateEmployee() {
        int employeeID = readPositiveInt("Enter employee ID to update: ");

        try {
            Employee existingEmployee = EMPLOYEE_MANAGER.getEmployeeById(employeeID);
            System.out.println("Current record:");
            System.out.println(existingEmployee);
            System.out.println();
            System.out.println("Enter the new information for this employee.");

            Employee updatedEmployee = buildEmployeeFromInput(true, employeeID);
            EMPLOYEE_MANAGER.updateEmployee(updatedEmployee);
            System.out.println("Employee updated successfully.");
        } catch (EmployeeNotFoundException exception) {
            System.out.println(exception.getMessage());
        } catch (InvalidPayException exception) {
            System.out.println("Validation error: " + exception.getMessage());
        } catch (SQLException exception) {
            System.out.println("Database error while updating employee: " + exception.getMessage());
        }
    }

    // Deletes an employee by ID.
    private static void deleteEmployee() {
        int employeeID = readPositiveInt("Enter employee ID to delete: ");

        try {
            EMPLOYEE_MANAGER.deleteEmployee(employeeID);
            System.out.println("Employee deleted successfully.");
        } catch (EmployeeNotFoundException exception) {
            System.out.println(exception.getMessage());
        } catch (SQLException exception) {
            System.out.println("Database error while deleting employee: " + exception.getMessage());
        }
    }

    // Builds the correct employee subtype from CLI input.
    private static Employee buildEmployeeFromInput(boolean keepExistingId, int existingId) {
        int employeeID = keepExistingId ? existingId : readPositiveInt("Enter employee ID: ");
        String name = readNonEmptyString("Enter employee name: ");

        System.out.println("Select employee type:");
        System.out.println("1. Full-Time Employee");
        System.out.println("2. Part-Time Employee");
        System.out.println("3. Contractor");

        int typeChoice = readChoiceBetween("Enter employee type: ", 1, 3);

        switch (typeChoice) {
        case 1:
            double baseSalary = readNonNegativeDouble("Enter base salary: ");
            double benefitsBonus = readNonNegativeDouble("Enter benefits bonus: ");
            return new FullTimeEmployee(employeeID, name, baseSalary, benefitsBonus);
        case 2:
            double hourlyWage = readNonNegativeDouble("Enter hourly wage: ");
            float hoursWorked = readNonNegativeFloat("Enter hours worked: ");
            return new PartTimeEmployee(employeeID, name, hourlyWage, hoursWorked);
        case 3:
            int contractDuration = readPositiveInt("Enter contract duration in months: ");
            double contractAmount = readNonNegativeDouble("Enter total contract amount: ");
            return new Contractor(employeeID, name, contractDuration, contractAmount);
        default:
            return null;
        }
    }

    // Reads and validates the main menu choice.
    private static int readMenuChoice() {
        return readChoiceBetween("Choose an option: ", 1, 6);
    }

    // Reads a menu or range-based integer choice.
    private static int readChoiceBetween(String prompt, int minimum, int maximum) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value >= minimum && value <= maximum) {
                    return value;
                }
                System.out.println("Please enter a number between " + minimum + " and " + maximum + ".");
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input. Please enter a valid whole number.");
            }
        }
    }

    // Reads a positive integer.
    private static int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();

            try {
                int value = Integer.parseInt(input);
                if (value > 0) {
                    return value;
                }
                System.out.println("Please enter a number greater than 0.");
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input. Please enter a valid whole number.");
            }
        }
    }

    // Reads a non-negative double.
    private static double readNonNegativeDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();

            try {
                double value = Double.parseDouble(input);
                if (value >= 0) {
                    return value;
                }
                System.out.println("Please enter a number that is 0 or greater.");
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    // Reads a non-negative float.
    private static float readNonNegativeFloat(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = SCANNER.nextLine().trim();

            try {
                float value = Float.parseFloat(input);
                if (value >= 0) {
                    return value;
                }
                System.out.println("Please enter a number that is 0 or greater.");
            } catch (NumberFormatException exception) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    // Reads a required text field.
    private static String readNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = SCANNER.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("This field cannot be empty.");
        }
    }
}