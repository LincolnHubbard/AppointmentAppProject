package com.hubbard.appointmentapp.utility;

import com.hubbard.appointmentapp.dao.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.time.*;

/**
 * This class contains all functions for setting timezones and converting DateTime values
 * @author Lincoln Hubbard
 */
public abstract class TimeManager {
    private static ZoneId userTimeZone;
    private final static ZoneId utcTimeZone = ZoneId.of( "UTC" );

    /**
     * Sets the userTimeZone variable to that of the user's default time zone on their system
     */
    public static void setUserTimeZone(){

        userTimeZone = ZoneId.systemDefault();
//       userTimeZone = ZoneId.of( "America/Denver" ); Used to pass in different time zones when checking conversion
    }

    /**
     * Gets the user's time zone
     * @return The current user's time zone
     */
    public static ZoneId getUserTimeZone(){
        return userTimeZone;
    }


    /**
     * This method generates appointment times from 8AM-10PM EST (business hours) AND converts them to the user's
     * time zone
     * @return A list of available appointment times used to populate combo boxes
     */
    public static ObservableList<LocalTime> generateTimes(){
        LocalTime openHour = LocalTime.of( 8,0 );
        LocalDateTime openHourDT = LocalDateTime.of( LocalDate.now(), openHour );
        ZonedDateTime openZDT = ZonedDateTime.of( openHourDT, ZoneId.of( "America/New_York" ) );
        ZonedDateTime userOpen = openZDT.withZoneSameInstant( userTimeZone );
        LocalTime time = LocalTime.from( userOpen );

        ObservableList<LocalTime> validTimes = FXCollections.observableArrayList();
        for(int i = 0; i < 57; i ++){
            LocalTime timeBuffer = time;
            timeBuffer = timeBuffer.plusMinutes( 15 * i);
            validTimes.add( timeBuffer );
        }
        return validTimes;
    }

    /**
     * Converts the user's local time to UTC
     * @param local A DateTime in the user's time zone
     * @return The DateTime converted to UTC
     */
    public static ZonedDateTime localToUTC(LocalDateTime local){
        ZonedDateTime utcTime = ZonedDateTime.of( local, userTimeZone);
        return utcTime.withZoneSameInstant( utcTimeZone );
    }

    /**
     * Converts the UTC time to the user's local time zone
     * @param utc A DateTime in the UTC time zone
     * @return The DateTime converted to the user's time zone
     */
    public static LocalDateTime utcToLocal(LocalDateTime utc){
        ZonedDateTime utcTime = ZonedDateTime.of( utc, utcTimeZone);
        ZonedDateTime localTime = utcTime.withZoneSameInstant( userTimeZone);
        return localTime.toLocalDateTime();
    }

    /**
     * This method checks if the appointment the user is scheduling overlaps with any other appointments for that
     * customer
     * @param inputStart The start time of the appointment to be scheduled
     * @param inputEnd The end time of the appointment to be scheduled
     * @param custId The customer of the appointment to be scheduled
     * @param apptId The ID of the appointment to be scheduled
     * @return True if the appointment does conflict with other appointments
     * @throws SQLException In case of error connecting to the database
     */
    public static boolean apptHasTimeConflict( LocalDateTime inputStart, LocalDateTime inputEnd, int custId, int apptId) throws SQLException{
        if (!AppointmentDAO.containsCustId( custId )){
            return false; //The customer cannot have overlapping appointments if they have none scheduled
        }
        ObservableList<AppointmentDAO.AppointmentTimes> appointmentTimes = AppointmentDAO.getApptTimes( custId, apptId );

        LocalDate inputDate = LocalDate.from( inputStart );
        boolean overlapDates = false;
        for (AppointmentDAO.AppointmentTimes d : appointmentTimes){

            LocalDate apptDate = LocalDate.from( d.getStart() );
            if (inputDate.equals( apptDate )) {
                overlapDates = true;
                break;
            }
        }

        if (!overlapDates){
            return false; //Appointments cannot overlap if they are not on the same date
        }
        for (AppointmentDAO.AppointmentTimes t : appointmentTimes){
            if (inputStart.equals( t.getStart() )){
                InputValidation.displayOverlapError( t.getApptId(), t.getStart(), t.getEnd() );
                return true;
            }
            if (inputStart.isBefore( t.getStart() ) && inputEnd.isAfter( t.getStart() )){
                InputValidation.displayOverlapError( t.getApptId(), t.getStart(), t.getEnd() );
                return true;
            }
            if(inputStart.isAfter( t.getStart() ) && inputStart.isBefore( t.getEnd() )){
                InputValidation.displayOverlapError( t.getApptId(), t.getStart(), t.getEnd() );
                return true;
            }
        }
        return false;
    }

}

