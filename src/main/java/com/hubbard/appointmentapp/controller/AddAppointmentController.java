package com.hubbard.appointmentapp.controller;

import com.hubbard.appointmentapp.dao.AppointmentDAO;
import com.hubbard.appointmentapp.dao.ContactDAO;
import com.hubbard.appointmentapp.dao.CustomerDAO;
import com.hubbard.appointmentapp.dao.UserDAO;
import com.hubbard.appointmentapp.model.Contact;
import com.hubbard.appointmentapp.model.Customer;
import com.hubbard.appointmentapp.utility.InputValidation;
import com.hubbard.appointmentapp.utility.TimeManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

/**
 * Handles functionality for the "Add Appointment" view
 * @author Lincoln Hubbard
 */
public class AddAppointmentController implements Initializable, InputValidation {
    @FXML
    private Button cancelButton;
    @FXML
    private TextField titleField;
    @FXML
    private TextArea descField;
    @FXML
    private TextField locField;
    @FXML
    private TextField typeField;
    @FXML
    private ComboBox<Contact> contactComboBox;
    @FXML
    private ComboBox<Customer> custComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<LocalTime> startComboBox;
    @FXML
    private ComboBox<LocalTime> endComboBox;

    /**
     * This method closes the current window
     */
    public void cancelButtonPressed(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Populates combo boxes with customer and contact data
     * @param url Inherited from super
     * @param resourceBundle Inherited from super
     */
    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ){
        contactComboBox.setItems( ContactDAO.getAllContacts() );
        custComboBox.setItems( CustomerDAO.getAllCustomers() );
        startComboBox.setItems( TimeManager.generateTimes() );
        endComboBox.setItems( TimeManager.generateTimes() );
    }

    /**
     * Validates all input and calls method to insert appointment into the database, then closes the window
     * @throws SQLException If there is an error running this SQL query
     */
    public void saveButtonPressed() throws SQLException{
        String title = titleField.getText();
        if (!stringIsGood.test( title )){
            InputValidation.displayError( "Title cannot be empty!" );
            return;
        }
        String desc = descField.getText();
        if (!stringIsGood.test( desc )){
            InputValidation.displayError( "Description cannot be empty!" );
            return;
        }
        String loc = locField.getText();
        if (!stringIsGood.test( loc )){
            InputValidation.displayError( "Location cannot be empty!" );
            return;
        }
        String type = typeField.getText();
        if (!stringIsGood.test( type )){
            InputValidation.displayError( "Type cannot be empty!" );
            return;
        }
        LocalDate date = datePicker.getValue();
        if (datePicker.getValue() == null){
            InputValidation.displayError( "A date must be selected!" );
            return;
        }
        LocalTime startTime = startComboBox.getValue();
        LocalTime endTime = endComboBox.getValue();
        if (!InputValidation.comboBoxHasSelection( startComboBox ) || !InputValidation.comboBoxHasSelection( endComboBox )){
            InputValidation.displayError( "Start and end times must be chosen" );
            return;
        }else if (!InputValidation.timesAreGood( startTime, endTime )){
            InputValidation.displayError("End time must be at least 15 minutes after end time!" );
            return;
        }
        if (!InputValidation.comboBoxHasSelection( contactComboBox )){
            InputValidation.displayError( "A contact must be selected!" );
            return;
        }
        int conId = contactComboBox.getSelectionModel().getSelectedItem().getContactID();
        if (!InputValidation.comboBoxHasSelection( custComboBox)){
            InputValidation.displayError( "A customer must be selected!" );
            return;
        }
        int custId = custComboBox.getSelectionModel().getSelectedItem().getCustomerId();
        int userId;
        try{
            userId = UserDAO.currentUser.getUserID();
        }catch (Exception e){
            InputValidation.displayError( "CRITICAL: No account associated with this user. " +
                                          "Please contact your database administrator" );
            return;
        }

        LocalDateTime start = LocalDateTime.of( date, startTime );
        LocalDateTime end = LocalDateTime.of( date, endTime );
        if (TimeManager.apptHasTimeConflict( start, end, custId, 0 )){
            return;
        }
        start = TimeManager.localToUTC( start ).toLocalDateTime();
        end = TimeManager.localToUTC( end ).toLocalDateTime();

        try{
            AppointmentDAO.createAppointment( title, desc, loc, type, start, end, custId, userId, conId);
        }catch (SQLException e){
            e.printStackTrace();
        }
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

}

