package com.hubbard.appointmentapp.model;

/**
 * This class contains all data for each Country entity in the database
 * @author Lincoln Hubbard
 */
public class Country {
    private final int countryId;
    private final String countryName;

    /**
     * Constructs a Country object
     * @param countryId The ID of the country
     * @param countryName The name of the country
     */
    public Country( int countryId, String countryName ){
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**
     * Gets the ID of the country
     * @return The ID of the country
     */
    public int getCountryId(){
        return countryId;
    }

    /**
     * Gets the name of the country
     * @return The name of the country
     */
    public String getCountryName(){
        return countryName;
    }

    /**
     * This method is overridden so that the country name displays when populating GUI elements
     * @return The name of the country
     */
    @Override
    public String toString(){
        return countryName;
    }
}
