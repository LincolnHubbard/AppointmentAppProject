package com.hubbard.appointmentapp.dao;

import com.hubbard.appointmentapp.model.User;
import com.hubbard.appointmentapp.utility.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains methods for reading User data from the database and keeping track of which user is logged
 * into the application
 * @author Lincoln Hubbard
 */
public class UserDAO {
    /**
     * The user currently logged into the application. This is public so all controller classes may access this instance
     */
    public static User currentUser;

    /**
     * Checks if the username and password are entered correctly and are in the database
     * @param userInput The username entered when log-in is attempted
     * @param passInput The password entered when log-in is attempted
     * @return True if a matching username AND password are found in the database
     */
    public static boolean checkPass( String userInput, String passInput ){
        String passReturn = null;
        try{
            String sql = " SELECT * FROM users WHERE User_Name = '" + userInput + "'";
            PreparedStatement ps = JDBC.getConnection().prepareStatement( sql);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                passReturn= rs.getString( "Password" );
            }
            if (passReturn == null){
                return false;
            } else if(passReturn.equals( passInput )){
                System.out.println("Log-in Success");
                createUserSession( userInput );
                return true;
            } else {
                System.out.println("Log-in Fail");
                return false;
            }
        } catch (SQLException e) {
//   e.printStackTrace(); Catch this exception but ignore it. We know if this throws the user or pass is wrong or blank
        }
        return false;
    }

    /**
     * Creates the user's current login session
     * @param userName The username of the user logged into the application
     */
    public static void createUserSession( String userName){
        try {
            String sql = " SELECT * FROM users WHERE User_Name = '" + userName + "'";
            PreparedStatement ps = JDBC.getConnection().prepareStatement( sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt( "User_ID" );
                String name = rs.getString( "User_Name" );
                String pass = rs.getString( "Password" );
                currentUser = new User( id, name, pass );
                System.out.println( "User initialized" );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

