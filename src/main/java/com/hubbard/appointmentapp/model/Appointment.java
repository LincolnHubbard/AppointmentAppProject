package com.hubbard.appointmentapp.model;

import java.time.LocalDateTime;

/**
 * This class represents all data values for adding and updating appointments
 * @author Lincoln Hubbard
 */

public class Appointment {
    private final int apptId;
    private final String appTitle;
    private final String apptDescription;
    private final String apptLocation;
    private final String apptType;
    private final LocalDateTime apptStartDateTime;
    private final LocalDateTime apptEndDateTime;
    private final int customerID;
    private final int userID;
    private final int contactID;

    /**
     * Constructs an instance of an Appointment object
     * @param apptId The appointment's ID
     * @param appTitle The appointment's title
     * @param apptDescription The appointment's description
     * @param apptLocation The appointment's location
     * @param apptType The type of appointment
     * @param apptStartDateTime The appointment's start date and time
     * @param apptEndDateTime The appointment's end date and time
     * @param customerID The customer assigned to the appointment
     * @param userID The user who last modified the appointment
     * @param contactID The contact assigned to the appointment
     */
    public Appointment( int apptId, String appTitle, String apptDescription, String apptLocation, String apptType,
                        LocalDateTime apptStartDateTime, LocalDateTime apptEndDateTime, int customerID, int userID,
                        int contactID ){
        this.apptId = apptId;
        this.appTitle = appTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.apptStartDateTime = apptStartDateTime;
        this.apptEndDateTime = apptEndDateTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    /**
     * Gets the appointment's ID
     * @return The appointment's ID
     */
    public int getApptId(){
        return apptId;
    }

    /**
     * Gets the appointment's title
     * @return The appointment's title
     */
    public String getAppTitle(){
        return appTitle;
    }

    /**
     * Gets the appointment's description
     * @return The appointment's description
     */
    public String getApptDescription(){
        return apptDescription;
    }

    /**
     * Gets the appointment's location
     * @return The appointment's location
     */
    public String getApptLocation(){
        return apptLocation;
    }

    /**
     * Gets the type of appointment
     * @return The type of appointment
     */
    public String getApptType(){
        return apptType;
    }

    /**
     * Gets the appointment's start date and time
     * @return The appointment's start date and time
     */
    public LocalDateTime getApptStartDateTime(){
        return apptStartDateTime;
    }

    /**
     * Gets the appointment's end date and time
     * @return The appointment's end date and time
     */
    public LocalDateTime getApptEndDateTime(){
        return apptEndDateTime;
    }

    /**
     * Gets the customer assigned to the appointment
     * @return The customer assigned to the appointment
     */
    public int getCustomerID(){
        return customerID;
    }

    /**
     * Gets the user who last modified the appointment
     * @return The user who last modified the appointment
     */
    public int getUserID(){
        return userID;
    }

    /**
     * Gets the contact assigned to the appointment
     * @return The contact assigned to the appointment
     */
    public int getContactID(){
        return contactID;
    }
}
