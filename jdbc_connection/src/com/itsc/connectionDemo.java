package com.itsc;

import java.sql.*;
public class connectionDemo {
public static void main(String[] args) {
try {
String driver = "com.mysql.cj.jdbc.Driver";
String url = "jdbc:mysql://localhost:3306/student";
String username = "root"; 
String password = "admin"; 
Class.forName(driver); 
Connection connection = DriverManager.getConnection(url,
username, password);
System.out.println("Established Connection");
}catch (Exception e) {
e.printStackTrace();
}
}
}