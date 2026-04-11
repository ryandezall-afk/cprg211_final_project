package sait.salaries.exceptions;

/**
 * Class: InvalidPayException
 * Author: Ryan Dezall, John Eric Acilo, Jabin Unger
 * Date: April 11, 2026
 *
 * Description:
 * This custom exception is used when salary-related input is invalid and should
 * not be accepted by the application. Inputs that can trigger this exception
 * include negative salary values, empty names, invalid IDs, negative hours, or
 * invalid contractor payment information. The processing provides a dedicated
 * validation path so the application can reject bad data before it reaches the
 * database. The output is safer data entry, better feedback to the user, and
 * improved protection against corrupted salary records.
 */
public class InvalidPayException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidPayException(String message) {
        super(message);
    }
}