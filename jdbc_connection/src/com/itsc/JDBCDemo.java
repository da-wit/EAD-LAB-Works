package com.itsc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBCDemo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/student_db";
        String username = "root";
        String password = "admin";
        
        try (Connection connection = DriverManager.getConnection(url, username, password);
             Statement statement = connection.createStatement()) {
            
            statement.execute("CREATE TABLE IF NOT EXISTS students (id INT PRIMARY KEY, firstname VARCHAR(255), lastname VARCHAR(255), grade INT)");

            insertSampleData(connection);

            retrieveData(connection);

            updateStudentName(connection, 1, "UpdatedFirstName");

            deleteStudent(connection, 2);

            calculateAverageGrade(connection);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertSampleData(Connection connection) {
        String insertQuery = "INSERT INTO students (id, firstname, lastname, grade) VALUES (?, ?, ?, ?)";
        
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setInt(1, 1);
            insertStatement.setString(2, "John");
            insertStatement.setString(3, "Doe");
            insertStatement.setInt(4, 90);
            insertStatement.executeUpdate();
            
            for (int i = 2; i <= 10; i++) {
                insertStatement.setInt(1, i);
                insertStatement.setString(2, "StudentFirstName" + i);
                insertStatement.setString(3, "StudentLastName" + i);
                insertStatement.setInt(4, 70 + i);
                insertStatement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void retrieveData(Connection connection) {
        String selectQuery = "SELECT * FROM students LIMIT 5";
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {
            
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                int grade = resultSet.getInt("grade");
                System.out.println("ID: " + id + ", Name: " + firstname + " " + lastname + ", Grade: " + grade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateStudentName(Connection connection, int id, String newFirstName) {
        String updateQuery = "UPDATE students SET firstname = ? WHERE id = ?";
        
        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setString(1, newFirstName);
            updateStatement.setInt(2, id);
            updateStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteStudent(Connection connection, int id) {
        String deleteQuery = "DELETE FROM students WHERE id = ?";
        
        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void calculateAverageGrade(Connection connection) {
        String avgQuery = "SELECT AVG(grade) AS average_grade FROM students";
        
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(avgQuery)) {
            
            if (resultSet.next()) {
                double averageGrade = resultSet.getDouble("average_grade");
                System.out.println("Average Grade: " + averageGrade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
