package com.hubbard.appointmentapp.dao;

import com.hubbard.appointmentapp.model.Contact;
import com.hubbard.appointmentapp.utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class contains methods for reading Contact data from the database
 * @author Lincoln Hubbard
 */
public class ContactDAO {

    /**
     * Gets a list of all contacts in the database
     * @return A list of all contacts in the database
     */
    public static ObservableList<Contact> getAllContacts(){
        ObservableList<Contact> custList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM contacts";
            PreparedStatement ps = JDBC.getConnection().prepareStatement( sql );
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt( "Contact_ID" );
                String name = rs.getString("Contact_Name");
                String email = rs.getString( "Email" );
                Contact cBuffer = new Contact(id, name, email);
                custList.add( cBuffer );
            }
            System.out.println("Contact Load: SUCCESS");

        }catch (SQLException throwable){
            throwable.printStackTrace();
        }
        return custList;
    }

    /**
     * Gets the contact associated with the contact ID passed into this method
     * @param conId The contact ID used to query the database
     * @return The contact associated with the contact ID passed into this method
     */
    public static Contact getContact(int conId){
        try{
            String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement( sql );
            ps.setInt( 1, conId );
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                int id = rs.getInt( "Contact_ID" );
                String name = rs.getString("Contact_Name");
                String email = rs.getString( "Email" );
                return new Contact(id, name, email);
            }
        }catch (SQLException throwable){
            throwable.printStackTrace();
        }
        return null;
    }
}

