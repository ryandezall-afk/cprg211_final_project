package sait.salaries.exceptions;

/**
 * Class: EmployeeNotFoundException
 * Author: Ryan Dezall, Jabin Unger, John Eric Acilo
 * Date: April 11, 2026
 *
 * Description:
 * This custom exception is used when the application attempts to find, update,
 * or delete an employee record that does not exist in the system. Inputs for
 * this exception are usually an employee ID or a search action that returns no
 * matching result. The processing allows the application to stop the current
 * operation safely and return a meaningful message to the user instead of
 * crashing. The output is cleaner error handling for CLI and database-based
 * operations throughout the project.
 */
public class EmployeeNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}