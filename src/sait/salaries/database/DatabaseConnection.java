package sait.salaries.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class: DatabaseConnection
 * Author: Ryan Dezall, John Acilo, Jabin Unger
 * Date: April 11, 2026
 *
 * Description:
 * This class manages all MariaDB connection setup for the Salaries Management
 * System. Inputs include the database server location, database name, username,
 * and password defined in this class. The processing creates the database if it
 * does not already exist and then creates the employees table used by the CRUD
 * operations in the application. The output is a reusable connection utility
 * that supports persistent storage, table initialization, and consistent access
 * to the database from the rest of the program.
 */
public final class DatabaseConnection {

    // Database configuration values. Update the username and password as needed.
    private static final String HOST = "localhost";
    private static final int PORT = 3306;
    private static final String DATABASE_NAME = "salaries_management";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "ericmariadb";

    private static final String SERVER_URL = "jdbc:mariadb://" + HOST + ":" + PORT + "/";
    private static final String DATABASE_URL = SERVER_URL + DATABASE_NAME;

    private DatabaseConnection() {
    }

    // Returns a live connection to the project database.
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }

    // Creates the database and employees table if they do not already exist.
    public static void initializeDatabase() throws SQLException {
        createDatabaseIfMissing();
        createEmployeesTableIfMissing();
    }

    // Creates the database if it does not exist yet.
    private static void createDatabaseIfMissing() throws SQLException {
        String sql = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;

        try (Connection connection = DriverManager.getConnection(SERVER_URL, USERNAME, PASSWORD);
                Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    // Creates the table that stores all employee types in one place.
    private static void createEmployeesTableIfMissing() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS employees ("
                + "employee_id INT PRIMARY KEY, "
                + "name VARCHAR(100) NOT NULL, "
                + "employee_type VARCHAR(20) NOT NULL, "
                + "base_salary DOUBLE NULL, "
                + "benefits_bonus DOUBLE NULL, "
                + "hourly_wage DOUBLE NULL, "
                + "hours_worked FLOAT NULL, "
                + "contract_duration INT NULL, "
                + "total_contract_amount DOUBLE NULL"
                + ")";

        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }
}