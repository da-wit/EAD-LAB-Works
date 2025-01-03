package com.books.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;


@WebServlet("/register-with-dependency")
public class BookRegistrationWithDependencyInjection extends HttpServlet {
	private static final long serialVersionUID = 1L;

    
//	

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String price = request.getParameter("price");

        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // here we get the DBConnection form the applicationContext we set 
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        DBConnectionManager dbManager = context.getBean("dbConnectionManager", DBConnectionManager.class);


        try {
            // Open database connection
            dbManager.openConnection();
            Connection conn = dbManager.getConnection();

            // Prepare the SQL query to insert task details
            String query = "INSERT INTO books (title, author, price) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);

            // Set query parameters
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setString(3, price);

            // Execute the query
            int rowsAffected = stmt.executeUpdate();

            // Respond with a success message
            if (rowsAffected > 0) {
                out.println("<h2>Book registered successfully!</h2>");
                out.println("<a href =\"index.jsp\">Home</a>");
            } else {
                out.println("<h2>Book registration failed!</h2>");
            }

            // Close the statement
            stmt.close();
        } catch (Exception e) {
            // Handle exceptions and respond with an error message
            e.printStackTrace();
            out.println("<h2>An error occurred while registering the book.</h2>");
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
