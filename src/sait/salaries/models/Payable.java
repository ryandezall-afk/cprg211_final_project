package sait.salaries.models;

/**
 * Interface: Payable
 * Author: Ryan Dezall
 * Date: April 11, 2026
 *
 * Description:
 * This interface is used in the Salaries Management System to enforce a common
 * payroll contract for every employee type in the application. Inputs include
 * employee-specific payment details such as annual salary, benefits bonus,
 * hourly wage, hours worked, or a contract amount. The processing allows each
 * subclass to calculate pay differently while still being handled through one
 * shared type. The output is a calculated payment value that can be displayed
 * in the CLI and stored consistently through the application.
 */
public interface Payable {

    // Method required for all payable objects.
    double calculatePay();
}