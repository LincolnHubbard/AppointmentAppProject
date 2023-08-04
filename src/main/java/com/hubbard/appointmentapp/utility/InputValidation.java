package com.hubbard.appointmentapp.utility;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.function.Predicate;

/**
 * This interface contains methods for validating input when adding/updating both customers and appointments
 * @author Lincoln Hubbard
 */
public interface InputValidation {

    /**
     * **LAMBDA FUNCTION**
     * <p>Making this method a lambda expression improves code readability and makes it easier to pass the return value
     * of this function into conditional checks as if it were a variable. This involves taking a string as input and
     * checking if it meets the condition (in this case, the string being not empty)</p>
     * This method checks to make sure the user actually entered an input into text fields throughout the program
     */
    Predicate<String> stringIsGood = input -> !input.isEmpty();


    /**
     * This method is called in various controller classes to display any error messages (input validation, no selected
     * item, etc.)
     * @param errorMsg The message to be displayed
     */
    static void displayError(String errorMsg){
        Alert noSelection = new Alert( Alert.AlertType.ERROR, (errorMsg),
                                       ButtonType.CLOSE);
        noSelection.showAndWait();

    }

    /**
     *
     * This method displays an error message when the user tries to schedule overlapping appointments
     *
     * @param errorId The ID of the appointment that the user is overlapping with
     * @param errorStartTime The start time of the appointment that the user is overlapping with
     * @param errorEndTime The end time of the appointment that the user is overlapping with
     */
    static void displayOverlapError( int errorId, LocalDateTime errorStartTime, LocalDateTime errorEndTime){
        String errorMsg = "Your appointment overlaps with Appointment #" + errorId + " Starting at " +  errorStartTime
                          + ", ending at " + errorEndTime;
        Alert apptOverlap = new Alert( Alert.AlertType.ERROR, errorMsg);
        apptOverlap.showAndWait();
    }

    /**
     * This method is called to validate user input of start and end times for appointments
     *
     * @param start The start time of the appointment to be scheduled
     * @param end The end time of the appointment to be scheduled
     * @return True if the end time is after and not equal to the start time
     */
    static boolean timesAreGood( LocalTime start, LocalTime end ){
        return end.isAfter(start) && !end.equals( start );
    }

    /**
     * This method is called to validate user input in all combo boxes when adding/updating customers and
     * appointments
     * @param comboBox The JavaFX ComboBox to be checked
     * @return True if the ComboBox is **not** empty
     */
    static boolean comboBoxHasSelection( ComboBox comboBox ){
        return !comboBox.getSelectionModel().isEmpty();
    }

}
