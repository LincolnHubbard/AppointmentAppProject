package com.hubbard.appointmentapp.model;

/**
 * This class contains all data for each Customer entity in the database
 * @author Lincoln Hubbard
 */
public class Customer {
    private final int customerId;
    private final String customerName;
    private final String customerAddress;
    private final String customerPostalCode;
    private final String customerPhone;
    private final int customerDivisionId;
    private String countryName;


    /**
     * Constructs a Customer object
     * @param customerId The ID of the customer
     * @param customerName The name of the customer
     * @param customerAddress The address of the customer
     * @param customerPostalCode The postal code (zip code) of the customer
     * @param customerPhone The phone number of the customer
     * @param customerDivisionId The first level division ID of the customer
     */
    public Customer( int customerId, String customerName, String customerAddress, String customerPostalCode,
                     String customerPhone, int customerDivisionId){
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.customerDivisionId = customerDivisionId;
    }

    /**
     * Gets the ID of the customer
     * @return The ID of the customer
     */
    public int getCustomerId(){
        return customerId;
    }

    /**
     * Gets the name of the customer
     * @return The name of the customer
     */
    public String getCustomerName(){
        return customerName;
    }

    /**
     * Gets the address of the customer
     * @return The address of the customer
     */
    public String getCustomerAddress(){
        return customerAddress;
    }

    /**
     * Gets the postal code (zip code) of the customer
     * @return The postal code (zip code) of the customer
     */
    public String getCustomerPostalCode(){
        return customerPostalCode;
    }

    /**
     * Gets the phone number of the customer
     * @return The phone number of the customer
     */
    public String getCustomerPhone(){
        return customerPhone;
    }

    /**
     * Gets the first level division ID of the customer
     * @return The first level division ID of the customer
     */
    public int getCustomerDivisionId(){
        return customerDivisionId;
    }

    /**
     * Gets the name of the country that the customer's first level division corresponds to
     * @param countryName The name of the country that the customer's first level division corresponds to
     */
    public void setCountryName(String countryName){
        this.countryName = countryName;
    }

    /**
     * Gets the name of the country that the customer's first level division corresponds to
     * @return The name of the country that the customer's first level division corresponds to
     */
    public String getCountryName(){
        return countryName;
    }

    /**
     * This method is overridden to display the customer's name when populating GUI elements
     * @return The name of the customer
     */
    @Override
    public String toString(){
        return customerName;
    }
}

