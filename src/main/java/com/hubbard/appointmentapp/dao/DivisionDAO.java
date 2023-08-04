package com.hubbard.appointmentapp.dao;

import com.hubbard.appointmentapp.model.Division;
import com.hubbard.appointmentapp.utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains methods for reading Division data from the database
 * @author Lincoln Hubbard
 */
public class DivisionDAO{
    /**
     * Gets a list of all divisions in the database
     * @return A list of all divisions in the database
     */
    public static ObservableList<Division> getAllDivisions(){
        ObservableList<Division> dList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = JDBC.getConnection().prepareStatement( sql );
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt( "Division_ID" );
                String name = rs.getString("Division");
                int countId = rs.getInt( "Country_ID" );
                Division dBuffer = new Division(id, name, countId);
                dList.add( dBuffer );
            }
            System.out.println("Division Load: SUCCESS");

        }catch (SQLException throwable){
            throwable.printStackTrace();
        }
        return dList;
    }

    /**
     * This method uses both country and division data to filter divisions when adding/updating a customer
     * @param countryID The country ID used to query the database
     * @return A list of divisions associated with the country ID
     */
    public static ObservableList<Division> filterDivisions(int countryID){
        ObservableList<Division> dList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement( sql );
            ps.setInt( 1, countryID );
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt( "Division_ID" );
                String name = rs.getString("Division");
                int countId = rs.getInt( "Country_ID" );
                Division dBuffer = new Division(id, name, countId);
                dList.add( dBuffer );
            }
            System.out.println("Division Load: SUCCESS");

        }catch (SQLException throwable){
            throwable.printStackTrace();
        }
        return dList;
    }

    /**
     * Gets a single division associated with the division ID
     * @param divId The division ID used to query the database
     * @return A single division associated with the division ID
     */
    public static Division getDivision(int divId){
        try{
            String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement( sql );
            ps.setInt( 1, divId );
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt( "Division_ID" );
                String name = rs.getString( "Division" );
                int countId = rs.getInt( "Country_ID" );
                return new Division( id, name, countId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

