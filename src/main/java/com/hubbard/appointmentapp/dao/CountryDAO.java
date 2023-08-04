package com.hubbard.appointmentapp.dao;

import com.hubbard.appointmentapp.model.Country;
import com.hubbard.appointmentapp.utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains methods for reading Country data from the database
 */
public class CountryDAO {

    /**
     * Gets the list of all countries in the database
     * @return The list of all countries in the database
     * @throws SQLException If there is an error running this SQL query
     */
    public static ObservableList<Country> getAllCountries() throws SQLException{
        ObservableList<Country> cList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement( sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                int countryID = rs.getInt( "Country_ID" );
                String countryName = rs.getString("Country");
                Country c = new Country(countryID, countryName);
                cList.add( c );
            }
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

        return cList;
    }

    /**
     * Gets a single country associated with the ID passed into this method
     * @param id The country ID used to query the database
     * @return A single country associated with the ID passed into this method
     */
    public static Country getCountryById(int id){
        try{
            String sql = "SELECT * FROM countries WHERE Country_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement( sql);
            ps.setInt( 1, id );
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                int countryID = rs.getInt( "Country_ID" );
                String countryName = rs.getString("Country");
                return new Country(countryID, countryName);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

}
