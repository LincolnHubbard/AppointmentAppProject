package com.hubbard.appointmentapp.model;

/**
 * This class contains all data for each User entity in the database
 * @author Lincoln Hubbard
 */
public class User {
    private final int userID;
    private final String userName;
    private final String userPass;

    /**
     * Constructs an instance of a User object
     * @param userID The ID of the user
     * @param userName The username of the current user
     * @param userPass The password associated with the username
     */
    public User( int userID, String userName, String userPass ){
        this.userID = userID;
        this.userName = userName;
        this.userPass = userPass;
    }

    /**
     * Gets the ID of the user
     * @return The ID of the user
     */
    public int getUserID(){
        return userID;
    }

    /**
     * Gets the username of the current user
     * @return The username of the current user
     */
    public String getUserName(){
        return userName;
    }

    /**
     * Gets the password associated with the username
     * @return The password associated with the username
     */
    public String getUserPass(){
        return userPass;
    }
}
