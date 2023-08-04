package com.hubbard.appointmentapp.utility;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * This class contains all methods for opening, maintaining and closing connections to the database
 * @author Lincoln Hubbard (With help from professor Malcolm Wabara)
 */

public abstract class JDBC {
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; // Password
    private static Connection connection;  // Connection Interface

    /**
     * Connects the application to the database
     */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection( jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Gets the active connection to the database
     * @return The active connection to the database
     */
    public static Connection getConnection(){
        return connection;
    }

    /**
     * Closes the connection to the database. In a real application, the connection should be closed
     * whenever a user is not currently loading or saving data to and from the database
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
