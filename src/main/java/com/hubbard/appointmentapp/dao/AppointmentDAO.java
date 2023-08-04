package com.hubbard.appointmentapp.dao;

import com.hubbard.appointmentapp.model.Appointment;
import com.hubbard.appointmentapp.utility.JDBC;
import com.hubbard.appointmentapp.utility.TimeManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This class contains all methods for creating, reading, updating, and deleting Appointment data to and from the
 * database
 * @author Lincoln Hubbard
 */
public class AppointmentDAO{
    /**
     * The appointment selected by the user to be updated. This is public so that all controllers can access the instance
     */
    public static Appointment selectedAppointment;

    /**
     * This class is used as a "struct" and contains start and end times for each appointment
     */
    public static class AppointmentTimes{
        private final LocalDateTime start;
        private final LocalDateTime end;
        private final int apptId;

        /**
         *
         * @param start The start date and time for the appointment
         * @param end The end date and time for the appointment
         * @param apptId The ID of the appointment
         */
        public AppointmentTimes( LocalDateTime start, LocalDateTime end, int apptId ){
            this.start = start;
            this.end = end;
            this.apptId = apptId;
        }

        /**
         *
         * @return The start date and time for the appointment
         */
        public LocalDateTime getStart(){return start;}

        /**
         *
         * @return The end date and time for the appointment
         */
        public LocalDateTime getEnd(){return end;}

        /**
         *
         * @return The ID of the appointment
         */
        public int getApptId(){return apptId;}
    }

    /**
     * This class is also used like a "struct" and contains the appointment month, type, and number of occurrences of
     * said type
     */
    public static class AppointmentReport{
        private final String month;
        private final String type;
        private final int amount;

        /**
         *
         * @param month The month the appointment is scheduled for
         * @param type The type of this appointment
         * @param amount The number of occurrences of this appointment type (in this month)
         */
        public AppointmentReport( String month, String type, int amount ){
            this.month = month;
            this.type = type;
            this.amount = amount;
        }

        /**
         *
         * @return The month the appointment is scheduled for
         */
        public String getMonth(){return month;}

        /**
         *
         * @return The type of this appointment
         */
        public String getType(){return type;}

        /**
         *
         * @return The number of occurrences of this appointment type (in this month)
         */
        public int getAmount(){return amount;}
    }

    /**
     * Gets a list of every appointment in the database
     * @return A list of every appointment in the database
     */
    public static ObservableList<Appointment> getAllAppointments(){
        ObservableList<Appointment> aList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = JDBC.getConnection().prepareStatement( sql );
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int id = rs.getInt( "Appointment_ID" );
                String title = rs.getString( "Title" );
                String desc = rs.getString("Description");
                String loc = rs.getString( "Location" );
                String type = rs.getString( "Type" );
                Timestamp startts = rs.getTimestamp( "Start" );
                Timestamp endts = rs.getTimestamp( "End" );
                LocalDateTime start = TimeManager.utcToLocal( startts.toLocalDateTime() );
                LocalDateTime end = TimeManager.utcToLocal( endts.toLocalDateTime() );
                int custId = rs.getInt( "Customer_ID" );
                int userId = rs.getInt("User_ID");
                int conId = rs.getInt( "Contact_ID" );
                Appointment aBuffer = new Appointment( id, title, desc, loc, type, start, end, custId, userId, conId);
                aList.add( aBuffer );
            }
            System.out.println("Appointment Load: SUCCESS");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return aList;
    }

    /**
     *  Gets single Appointment entity corresponding to the ID that is passed into this method
     * @param apptId The appointment ID used to query the database
     * @return A single Appointment entity corresponding to the ID that is passed into this method
     */
    public static Appointment getAppointment(int apptId){
        try{
            String sql = "SELECT * FROM appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement( sql );
            ps.setInt( 1, apptId );
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                int id = rs.getInt( "Appointment_ID" );
                String title = rs.getString( "Title" );
                String desc = rs.getString("Description");
                String loc = rs.getString( "Location" );
                String type = rs.getString( "Type" );
                Timestamp startts = rs.getTimestamp( "Start" );
                LocalDateTime start = TimeManager.utcToLocal( startts.toLocalDateTime() );
                Timestamp endts = rs.getTimestamp( "End" );
                LocalDateTime end = TimeManager.utcToLocal( endts.toLocalDateTime() );
                int custId = rs.getInt( "Customer_ID" );
                int userId = rs.getInt("User_ID");
                int conId = rs.getInt( "Contact_ID" );
                return new Appointment( id, title, desc, loc, type, start, end, custId, userId, conId);
            }
            System.out.println("Appointment Load: SUCCESS");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    /**
     * Gets a list of all appointment times in the database
     * @param custId The customer ID used to query the database
     * @param apptId The appointment ID used to query the database (Used to exclude this ID from the query)
     * @return A list of all appointment times in the database
     */
    public static ObservableList<AppointmentTimes> getApptTimes(int custId, int apptId){
        ObservableList<AppointmentTimes> tList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT Start, End, Appointment_ID FROM appointments WHERE Customer_ID = ? AND Appointment_ID != ?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement( sql );
            ps.setInt( 1, custId );
            ps.setInt( 2, apptId );
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                LocalDateTime start = rs.getTimestamp( "Start" ).toLocalDateTime();
                start = TimeManager.utcToLocal( start );
                LocalDateTime end = rs.getTimestamp( "End" ).toLocalDateTime();
                end = TimeManager.utcToLocal( end );
                int returnApptId = rs.getInt( "Appointment_ID" );
                AppointmentTimes timeBuffer = new AppointmentTimes(start, end, returnApptId);
                tList.add( timeBuffer );
            }
            System.out.println("Appointment Load: SUCCESS");
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return tList;
    }

    /**
     * Inserts an appointment into the database
     * @param title The title of the appointment to enter into the database
     * @param desc The description of the appointment to enter into the database
     * @param loc The location of the appointment to enter into the database
     * @param type The type of appointment to enter into the database
     * @param start The start date and time of the appointment to enter into the database
     * @param end The end date and time of the appointment to enter into the database
     * @param custId The customer ID associated with this appointment
     * @param userId The ID of the user that created this appointment
     * @param conId The contact ID associated with this appointment
     * @return The number of rows in the database affected by this method
     * @throws SQLException If there is an error running this SQL query in the database
     */
    public static int createAppointment(String title, String desc, String loc, String type, LocalDateTime start,
                                        LocalDateTime end, int custId, int userId, int conId) throws SQLException{
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID," +
                     "Contact_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.getConnection().prepareStatement( sql );
        ps.setString( 1, title );
        ps.setString( 2,desc );
        ps.setString( 3, loc );
        ps.setString( 4, type );
        ps.setTimestamp( 5, Timestamp.valueOf( start ) );
        ps.setTimestamp( 6, Timestamp.valueOf( end ) );
        ps.setInt( 7, custId );
        ps.setInt( 8, userId );
        ps.setInt( 9, conId );
        System.out.println("Appointment " + title + " Created: SUCCESS");
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Updates the selected appointment in the database
     * @param title The title of the appointment to update the database
     * @param apptId The id of the appointment to be updated
     * @param desc The description of the appointment to update the database
     * @param loc The location of the appointment to update the database
     * @param type The type of appointment to update the database
     * @param start The start date and time of the appointment to update the database
     * @param end The end date and time of the appointment to update the database
     * @param custId The customer ID associated with this appointment
     * @param userId The ID of the user that updated this appointment
     * @param conId The contact ID associated with this appointment
     * @return The number of rows in the database affected by this method
     * @throws SQLException If there is an error running this SQL query in the database
     */
    public static int updateAppointment(int apptId, String title, String desc, String loc, String type, LocalDateTime start,
                                        LocalDateTime end, int custId, int userId, int conId) throws SQLException{
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?," +
                     "Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement( sql );
        ps.setString( 1, title );
        ps.setString( 2,desc );
        ps.setString( 3, loc );
        ps.setString( 4, type );
        ps.setTimestamp( 5, Timestamp.valueOf( start ) );
        ps.setTimestamp( 6, Timestamp.valueOf( end ) );
        ps.setInt( 7, custId );
        ps.setInt( 8, userId );
        ps.setInt( 9, conId );
        ps.setInt( 10, apptId );
        System.out.println("Appointment " + title + " Updated: SUCCESS");
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * Removes selected appointment from the database
     * @param apptId The ID of the appointment to be deleted
     * @return The number of rows in the database affected by this method (Should always be 1)
     * @throws SQLException If there is an error running this SQL query in the database
     */
    public static int deleteAppointment(int apptId) throws SQLException{
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement( sql );
        ps.setInt( 1, apptId );
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
     * This method is used to check if this customer has any scheduled appointments
     * @param custId The customer ID to query this database
     * @return True if any appointments are associated with this customer ID
     * @throws SQLException If there is an error running this SQL query in the database
     */
    public static boolean containsCustId(int custId) throws SQLException{
        ObservableList<String> custAppts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement( sql );
        ps.setInt( 1, custId );
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String title = rs.getString( "Title" );
            custAppts.add(title);
        }
        return !custAppts.isEmpty();
    }

    /**
     * Gets a list of all appointment months, types, and the amount that each type occurs
     * @return A list of all appointment months, types, and the amount that each type occurs
     */
    public static ObservableList<AppointmentReport> generateApptReports() {
        ObservableList<AppointmentReport> reportList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT month, apptType, amount FROM" +
                         "(SELECT DISTINCT MONTHNAME(appointments.Start) AS month, type as apptType FROM appointments) as table1" +
                         "LEFT JOIN" +
                         "(SELECT COUNT(type) as amount, type FROM appointments as table2 GROUP BY type) as table2 ON apptType = Type";
            PreparedStatement ps = JDBC.getConnection().prepareStatement( sql );
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                String month = rs.getString( "month" );
                String type = rs.getString( "apptType" );
                int amount = rs.getInt( "amount" );
                AppointmentReport rBuffer = new AppointmentReport( month, type, amount );
                reportList.add( rBuffer );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reportList;
    }

    /**
     * Gets the number of appointments that are still scheduled for today that have not started yet
     * @param apptList The list of appointments to check
     * @return The number of appointments that are still scheduled for today that have not started yet
     */
    public static int getRemainingAppts(ObservableList<Appointment> apptList){
        int count = 0;
        for (Appointment a : apptList){
            if (LocalDateTime.now().isBefore( a.getApptStartDateTime() ) &&
                LocalDate.now().equals( a.getApptStartDateTime().toLocalDate() )){
                count += 1;
            }
        }
        return count;
    }
}

