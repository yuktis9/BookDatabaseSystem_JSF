package com.bookdata.db;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnection {

	String url = "jdbc:mysql://localhost:3306/books";
    String url1 = "jdbc:mysql://cs99.bradley.edu:3306/s_dmukati";
    //?"+ "user=s_dmukati&password=JY753SMz";
    String userName = "root";
    String password = "";
    public Connection createDBConnection() {
 	
            System.out.println("-------- MySQL JDBC Connection Initiating ------------");
 
            try {
            	Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
            	System.out.println("MySQL JDBC Driver Missing");
            	e.printStackTrace();
            	return null;
            }
 
            System.out.println("MySQL JDBC Driver Registered!");
            Connection connection = null;
 
            try {
            	connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/books?"
        		  + "user="+userName+"&password="+password);
 
            } catch (SQLException e) {
               	System.out.println("Connection Failed! Check output console");
               	e.printStackTrace();
               	return null;
            }
 
            if (connection != null) {
            	System.out.println("Connection succesfully Established !");
            } else {
                 System.out.println("Failed to make connection!");
            }
            return connection;
    }
}
