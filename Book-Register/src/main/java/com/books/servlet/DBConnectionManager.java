package com.books.servlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DBConnectionManager {
    private Connection connection;
    private String url = "jdbc:mysql://localhost:3306/bookstoredb";
    private String username = "root";                         
    private String password = "admin";                     
    /**
     * Opens the database connection.
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the JDBC driver class is not found
     */
    public void openConnection() throws SQLException, ClassNotFoundException {
        // Load the JDBC driver (optional in some JDBC versions)
        Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure you have the MySQL JDBC driver in your dependencies

        // Establish the connection
        connection = DriverManager.getConnection(url, username, password);
        System.out.println("Database connection established successfully.");
    }

    /**
     * Closes the database connection if it is open.
     * @throws SQLException if a database access error occurs
     */
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Database connection closed successfully.");
        }
    }
}
