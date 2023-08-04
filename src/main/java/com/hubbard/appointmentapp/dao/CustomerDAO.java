package com.hubbard.appointmentapp.dao;

import com.hubbard.appointmentapp.model.Customer;
import com.hubbard.appointmentapp.utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * This class contains all methods for creating, reading, updating, and deleting Customer data to and from the
 * database
 * @author Lincoln Hubbard
 */
public class CustomerDAO {
    /**
     * The customer selected by the user to be updated. This is public so that all controllers can access the instance
     */
    public static Customer selectedCustomer;

    /**
     * Gets a list of every customer in the database
     * @return A list of every customer in the database
     */
    public static ObservableList<Customer> getAllCustomers(){
        ObservableList<Customer> custList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = JDBC.getConnection().prepareStatement( sql );
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt( "Customer_ID" );
                String name = rs.getString( "Customer_Name" );
                String address = rs.getString( "Address" );
                String post = rs.getString("Postal_Code");
                String phone = rs.getString( "Phone" );
                int divId = rs.getInt("Division_ID");
                Customer custBuffer = new Customer(id, name, address, post, phone, divId);
                custBuffer.setCountryName( String.valueOf(
                        CountryDAO.getCountryById( Objects.requireNonNull( DivisionDAO.getDivision( divId ) ).getDivisionCountryId() ) ) );
                custList.add( custBuffer );
            }
            System.out.println("Customer Load: SUCCESS");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return custList;
    }

    /**
     * Gets a single customer associated with the customer ID
     * @param customerId The customer ID used to query the database
     * @return A single customer associated with the customer ID
     */
    public static Customer getCustomer( int customerId){
        try{
            String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement( sql );
            ps.setInt( 1, customerId );
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                int id = rs.getInt( "Customer_ID" );
                String name = rs.getString( "Customer_Name" );
                String address = rs.getString( "Address" );
                String post = rs.getString("Postal_Code");
                String phone = rs.getString( "Phone" );
                int divId = rs.getInt("Division_ID");
                return new Customer( id, name, address, post, phone, divId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Inserts a customer into the database
     * @param name The name of the customer to enter into the database
     * @param address The address of the customer to enter into the database
     * @param post The postal code of the customer to enter into the database
     * @param phone The phone number of the customer to enter into the database
     * @param divID The division ID of the customer to enter into the database
     * @return The number of rows in the database affected by this method
     * @throws SQLException If there is an error running this SQL query in the database
     */
    public static int insertCustomer(String name, String address, String post, String phone, int divID) throws SQLException{

        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID)" +
                     " VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement( sql );
        ps.setString( 1, name );
        ps.setString( 2, address );
        ps.setString( 3, post );
        ps.setString(4, phone);
        ps.setInt( 5, divID );
        System.out.println("Customer " + name + " added: SUCCESS");
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Updates the selected customer in the database
     * @param custId The ID of the customer to be updated
     * @param name The name of the customer to update in the database
     * @param address The address of the customer to update in the database
     * @param post The postal code of the customer to update in the database
     * @param phone The phone number of the customer to update in the database
     * @param divID The division ID of the customer to update in the database
     * @return The number of rows in the database affected by this method
     * @throws SQLException If there is an error running this SQL query in the database
     */
    public static int updateCustomer(int custId, String name, String address, String post, String phone, int divID)
            throws SQLException{
        String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?," +
                     " Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement( sql );
        ps.setString( 1, name );
        ps.setString( 2, address );
        ps.setString( 3, post );
        ps.setString( 4,phone );
        ps.setInt( 5, divID );
        ps.setInt( 6, custId );
        System.out.println("Customer " + name + " updated: SUCCESS");
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Deletes the selected customer from the database
     * @see AppointmentDAO#containsCustId(int) This method should ALWAYS be called first
     * @param custID The ID of the customer to be removed
     * @return The number of rows in the database affected by this method
     * @throws SQLException If there is an error running this SQL query in the database
     */
    public static int deleteCustomer(int custID) throws SQLException{
        String sql = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement( sql );
        ps.setInt( 1, custID );
        System.out.println( "Customer Deleted: Success");
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}

