package sait.salaries.database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import sait.salaries.exceptions.EmployeeNotFoundException;
import sait.salaries.exceptions.InvalidPayException;
import sait.salaries.models.Contractor;
import sait.salaries.models.Employee;
import sait.salaries.models.FullTimeEmployee;
import sait.salaries.models.PartTimeEmployee;

/**
 * Class: EmployeeManager
 * Author: Ryan Dezall
 * Date: April 11, 2026
 *
 * Description:
 * This class acts as the business and data-access layer for the Salaries
 * Management System. Inputs include employee objects and employee IDs provided
 * by the command-line interface. The processing validates user data, performs
 * CRUD operations using prepared SQL statements, and rebuilds the correct
 * employee subtype from database rows using inheritance and polymorphism. The
 * output is a clean set of reusable methods that connect the user interface to
 * the MariaDB database while also supporting custom exception handling.
 */
public class EmployeeManager {

    // Adds a new employee record to the database.
    public void addEmployee(Employee employee) throws SQLException, InvalidPayException {
        validateEmployee(employee);

        if (employeeExists(employee.getEmployeeID())) {
            throw new InvalidPayException("An employee with ID " + employee.getEmployeeID() + " already exists.");
        }

        String sql = "INSERT INTO employees (employee_id, name, employee_type, base_salary, benefits_bonus, "
                + "hourly_wage, hours_worked, contract_duration, total_contract_amount) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            setEmployeeParameters(statement, employee);
            statement.executeUpdate();
        }
    }

    // Returns every employee from the database.
    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<Employee>();
        String sql = "SELECT * FROM employees ORDER BY employee_id";

        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                employees.add(buildEmployeeFromResultSet(resultSet));
            }
        }

        return employees;
    }

    // Returns one employee by ID or throws a custom exception if not found.
    public Employee getEmployeeById(int employeeID) throws SQLException, EmployeeNotFoundException {
        String sql = "SELECT * FROM employees WHERE employee_id = ?";

        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, employeeID);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return buildEmployeeFromResultSet(resultSet);
                }
            }
        }

        throw new EmployeeNotFoundException("Employee with ID " + employeeID + " was not found.");
    }

    // Updates an existing employee record.
    public void updateEmployee(Employee employee)
            throws SQLException, InvalidPayException, EmployeeNotFoundException {
        validateEmployee(employee);

        if (!employeeExists(employee.getEmployeeID())) {
            throw new EmployeeNotFoundException("Employee with ID " + employee.getEmployeeID() + " was not found.");
        }

        String sql = "UPDATE employees SET name = ?, employee_type = ?, base_salary = ?, benefits_bonus = ?, "
                + "hourly_wage = ?, hours_worked = ?, contract_duration = ?, total_contract_amount = ? "
                + "WHERE employee_id = ?";

        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getEmployeeType());

            if (employee instanceof FullTimeEmployee) {
                FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employee;
                statement.setDouble(3, fullTimeEmployee.getBaseSalary());
                statement.setDouble(4, fullTimeEmployee.getBenefitsBonus());
                statement.setNull(5, Types.DOUBLE);
                statement.setNull(6, Types.FLOAT);
                statement.setNull(7, Types.INTEGER);
                statement.setNull(8, Types.DOUBLE);
            } else if (employee instanceof PartTimeEmployee) {
                PartTimeEmployee partTimeEmployee = (PartTimeEmployee) employee;
                statement.setNull(3, Types.DOUBLE);
                statement.setNull(4, Types.DOUBLE);
                statement.setDouble(5, partTimeEmployee.getHourlyWage());
                statement.setFloat(6, partTimeEmployee.getHoursWorked());
                statement.setNull(7, Types.INTEGER);
                statement.setNull(8, Types.DOUBLE);
            } else if (employee instanceof Contractor) {
                Contractor contractor = (Contractor) employee;
                statement.setNull(3, Types.DOUBLE);
                statement.setNull(4, Types.DOUBLE);
                statement.setNull(5, Types.DOUBLE);
                statement.setNull(6, Types.FLOAT);
                statement.setInt(7, contractor.getContractDuration());
                statement.setDouble(8, contractor.getTotalContractAmount());
            } else {
                throw new InvalidPayException("Unsupported employee type.");
            }

            statement.setInt(9, employee.getEmployeeID());
            statement.executeUpdate();
        }
    }

    // Deletes an employee record by ID.
    public void deleteEmployee(int employeeID) throws SQLException, EmployeeNotFoundException {
        if (!employeeExists(employeeID)) {
            throw new EmployeeNotFoundException("Employee with ID " + employeeID + " was not found.");
        }

        String sql = "DELETE FROM employees WHERE employee_id = ?";

        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, employeeID);
            statement.executeUpdate();
        }
    }

    // Checks if an employee ID already exists.
    private boolean employeeExists(int employeeID) throws SQLException {
        String sql = "SELECT COUNT(*) FROM employees WHERE employee_id = ?";

        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(sql)) {
            statement.setInt(1, employeeID);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }

        return false;
    }

    // Validates employee data before it is saved or updated.
    private void validateEmployee(Employee employee) throws InvalidPayException {
        if (employee == null) {
            throw new InvalidPayException("Employee data cannot be null.");
        }

        if (employee.getEmployeeID() <= 0) {
            throw new InvalidPayException("Employee ID must be greater than 0.");
        }

        if (employee.getName() == null || employee.getName().trim().isEmpty()) {
            throw new InvalidPayException("Employee name cannot be empty.");
        }

        if (employee instanceof FullTimeEmployee) {
            FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employee;

            if (fullTimeEmployee.getBaseSalary() < 0) {
                throw new InvalidPayException("Base salary cannot be negative.");
            }

            if (fullTimeEmployee.getBenefitsBonus() < 0) {
                throw new InvalidPayException("Benefits bonus cannot be negative.");
            }
        } else if (employee instanceof PartTimeEmployee) {
            PartTimeEmployee partTimeEmployee = (PartTimeEmployee) employee;

            if (partTimeEmployee.getHourlyWage() < 0) {
                throw new InvalidPayException("Hourly wage cannot be negative.");
            }

            if (partTimeEmployee.getHoursWorked() < 0) {
                throw new InvalidPayException("Hours worked cannot be negative.");
            }
        } else if (employee instanceof Contractor) {
            Contractor contractor = (Contractor) employee;

            if (contractor.getContractDuration() <= 0) {
                throw new InvalidPayException("Contract duration must be greater than 0.");
            }

            if (contractor.getTotalContractAmount() < 0) {
                throw new InvalidPayException("Contract amount cannot be negative.");
            }
        } else {
            throw new InvalidPayException("Unsupported employee type.");
        }
    }

    // Fills the INSERT statement using the correct subclass fields.
    private void setEmployeeParameters(PreparedStatement statement, Employee employee) throws SQLException,
            InvalidPayException {
        statement.setInt(1, employee.getEmployeeID());
        statement.setString(2, employee.getName());
        statement.setString(3, employee.getEmployeeType());

        if (employee instanceof FullTimeEmployee) {
            FullTimeEmployee fullTimeEmployee = (FullTimeEmployee) employee;
            statement.setDouble(4, fullTimeEmployee.getBaseSalary());
            statement.setDouble(5, fullTimeEmployee.getBenefitsBonus());
            statement.setNull(6, Types.DOUBLE);
            statement.setNull(7, Types.FLOAT);
            statement.setNull(8, Types.INTEGER);
            statement.setNull(9, Types.DOUBLE);
        } else if (employee instanceof PartTimeEmployee) {
            PartTimeEmployee partTimeEmployee = (PartTimeEmployee) employee;
            statement.setNull(4, Types.DOUBLE);
            statement.setNull(5, Types.DOUBLE);
            statement.setDouble(6, partTimeEmployee.getHourlyWage());
            statement.setFloat(7, partTimeEmployee.getHoursWorked());
            statement.setNull(8, Types.INTEGER);
            statement.setNull(9, Types.DOUBLE);
        } else if (employee instanceof Contractor) {
            Contractor contractor = (Contractor) employee;
            statement.setNull(4, Types.DOUBLE);
            statement.setNull(5, Types.DOUBLE);
            statement.setNull(6, Types.DOUBLE);
            statement.setNull(7, Types.FLOAT);
            statement.setInt(8, contractor.getContractDuration());
            statement.setDouble(9, contractor.getTotalContractAmount());
        } else {
            throw new InvalidPayException("Unsupported employee type.");
        }
    }

    // Converts a database row back into the correct Java subclass.
    private Employee buildEmployeeFromResultSet(ResultSet resultSet) throws SQLException {
        int employeeID = resultSet.getInt("employee_id");
        String name = resultSet.getString("name");
        String employeeType = resultSet.getString("employee_type");

        if ("FULL_TIME".equalsIgnoreCase(employeeType)) {
            double baseSalary = resultSet.getDouble("base_salary");
            double benefitsBonus = resultSet.getDouble("benefits_bonus");
            return new FullTimeEmployee(employeeID, name, baseSalary, benefitsBonus);
        }

        if ("PART_TIME".equalsIgnoreCase(employeeType)) {
            double hourlyWage = resultSet.getDouble("hourly_wage");
            float hoursWorked = resultSet.getFloat("hours_worked");
            return new PartTimeEmployee(employeeID, name, hourlyWage, hoursWorked);
        }

        if ("CONTRACTOR".equalsIgnoreCase(employeeType)) {
            int contractDuration = resultSet.getInt("contract_duration");
            double totalContractAmount = resultSet.getDouble("total_contract_amount");
            return new Contractor(employeeID, name, contractDuration, totalContractAmount);
        }

        throw new SQLException("Unknown employee type found in the database: " + employeeType);
    }
}