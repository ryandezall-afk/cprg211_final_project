package ca.sait.salaries.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {

//Create//
    
public boolean addEmployee(String name, String type, double salary) {
String sql = "INSERT INTO employees (name, type, salary) VALUES (?, ?, ?)";

try (Connection conn = DatabaseConnection.getConnection();
PreparedStatement stmt = conn.prepareStatement(sql)) {

stmt.setString(1, name);
stmt.setString(2, type);
stmt.setDouble(3, salary);

return stmt.executeUpdate() > 0; }

catch (SQLException e) { e.printStackTrace(); return false; }
}

//READ//

public List<String> getAllEmployees() {List<String> employees = new ArrayList<>();
String sql = "SELECT * FROM employees";

try (Connection conn = DatabaseConnection.getConnection();
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(sql)) {

while (rs.next()) { String emp =

rs.getInt("id") + " | " +
rs.getString("name") + " | " +
rs.getString("type") + " | " +
rs.getDouble("salary");

employees.add(emp); } } 

catch (SQLException e) { e.printStackTrace(); }

return employees; }

//Update//

public boolean updateSalary(int id, double newSalary) {
String sql = "UPDATE employees SET salary = ? WHERE id = ?";

try (Connection conn = DatabaseConnection.getConnection();
PreparedStatement stmt = conn.prepareStatement(sql)) {

stmt.setDouble(1, newSalary);
stmt.setInt(2, id);

return stmt.executeUpdate() > 0; } 

catch (SQLException e) { e.printStackTrace();
            
return false; } }

//DELETE//

public boolean deleteEmployee(int id) {
String sql = "DELETE FROM employees WHERE id = ?";

try (Connection conn = DatabaseConnection.getConnection();
PreparedStatement stmt = conn.prepareStatement(sql)) {

stmt.setInt(1, id);

return stmt.executeUpdate() > 0; } 

catch (SQLException e) { e.printStackTrace();

return false; } }
}

