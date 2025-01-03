package com.books.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Servlet to display all tasks from the database.
 */
@WebServlet("/display-books")
public class DisplayBooksServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection manager instance
    private DBConnectionManager dbManager = new DBConnectionManager();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Open database connection
            dbManager.openConnection();
            Connection conn = dbManager.getConnection();

            // Fetch tasks from the database
            String query = "SELECT * FROM books";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            // Start HTML table
            out.println("<html><head><title>books</title></head><body>");
            out.println("<h1>Books List</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Title</th><th>Author</th><th>Price</th></tr>");

            // Iterate over the result set and add rows to the table
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String price = rs.getString("price");

                out.println("<tr>");
                out.println("<td>" + id + "</td>");
                out.println("<td>" + title + "</td>");
                out.println("<td>" + author + "</td>");
                out.println("<td>" + price + "</td>");
                out.println("</tr>");
            }

            // End HTML table and close the connection
            out.println("</table>");
            out.println("<a href =\"index.jsp\">Home</a>");
            out.println("</body></html>");

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h2>An error occurred while retrieving tasks.</h2>");
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