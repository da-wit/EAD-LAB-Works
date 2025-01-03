package com.books.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteTaskServlet
 */
@WebServlet("/deleteBook")
public class DeleteBookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection manager instance
    private DBConnectionManager dbManager = new DBConnectionManager();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve task ID from the request
        String taskId = request.getParameter("book_id");

        // Prepare response writer to send feedback to the client
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Open database connection
            dbManager.openConnection();
            Connection conn = dbManager.getConnection();

            // Prepare the SQL query to delete the task by its ID
            String query = "DELETE FROM books WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);

            // Set the task ID as a parameter for the query
            stmt.setInt(1, Integer.parseInt(taskId));

            // Execute the query
            int rowsAffected = stmt.executeUpdate();

            // Respond with a success or failure message
            if (rowsAffected > 0) {
                out.println("<h2>Book deleted successfully!</h2>");
                out.println("<a href =\"index.jsp\">Home</a>");
            } else {
                out.println("<h2>No Book found with the given ID!</h2>");
            }

            // Close the statement
            stmt.close();
        } catch (Exception e) {
            // Handle exceptions and respond with an error message
            e.printStackTrace();
            out.println("<h2>An error occurred while deleting the book.</h2>");
        } finally {
            try {
                // Close the database connection
                dbManager.closeConnection();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}