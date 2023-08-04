package com.hubbard.appointmentapp.model;

/**
 * This class represents the data values for all contacts
 * @author Lincoln Hubbard
 */
public class Contact {
    private final int contactID;
    private final String contactName;
    private final String contactEmail;


    /**
     * Constructs an instance of a Contact object
     * @param contactID The contact's ID in the database
     * @param contactName The contact's name
     * @param contactEmail The contact's email address
     */
    public Contact( int contactID, String contactName, String contactEmail ){
        this.contactID = contactID;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Gets the contact's ID in the database
     * @return The contact's ID in the database
     */
    public int getContactID(){
        return contactID;
    }

    /**
     * Gets the contact's name
     * @return The contact's name
     */
    public String getContactName(){
        return contactName;
    }

    /**
     * Gets the contact's email address
     * @return The contact's email address
     */
    public String getContactEmail(){
        return contactEmail;
    }

    /**
     * This method is overwritten so that the contact's name is displayed when populating combo boxes with contacts
     * @return The contact's name
     */
    @Override
    public String toString(){
        return contactName;
    }
}
