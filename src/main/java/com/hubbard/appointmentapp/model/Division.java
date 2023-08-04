package com.hubbard.appointmentapp.model;

/**
 * This class contains all data for each Division entity in the database (State, Province, Region, etc.)
 * @author Lincoln Hubbard
 */
public class Division {
    private final int divisionId;
    private final String divisionName;
    private final int divisionCountryId;


    /**
     * Constructs a Division object
     * @param divisionId The ID of the division
     * @param divisionName The name of the division
     * @param divisionCountryId The ID of the country that the division corresponds to
     */
    public Division( int divisionId, String divisionName, int divisionCountryId ){
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.divisionCountryId = divisionCountryId;
    }

    /**
     * Gets the ID of the division
     * @return The ID of the division
     */
    public int getDivisionId(){
        return divisionId;
    }

    /**
     * Gets the name of the division
     * @return The name of the division
     */
    public String getDivisionName(){
        return divisionName;
    }

    /**
     * This method is overridden so that the division name displays when populating GUI elements
     * @return The name of the division
     */
    @Override
    public String toString(){
        return divisionName;
    }


    /**
     * Gets the ID of the country that the division corresponds to
     * @return The ID of the country that the division corresponds to
     */
    public int getDivisionCountryId(){
        return divisionCountryId;
    }
}
