package com.hubbard.appointmentapp.controller;

import com.hubbard.appointmentapp.dao.AppointmentDAO;
import com.hubbard.appointmentapp.dao.ContactDAO;
import com.hubbard.appointmentapp.model.Appointment;
import com.hubbard.appointmentapp.model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles all functionality for the "Contact View" screen. One of the reports required in section A3F
 * @author Lincoln Hubbard
 */
public class ContactViewController implements Initializable {
    @FXML
    private TableView<Appointment> apptTableView;
    @FXML
    private ComboBox<Contact> contactComboBox;
    @FXML
    private Button closeButton;
    @FXML
    private TableColumn<Appointment, String> idColumn;
    @FXML
    private  TableColumn<Appointment, String> titleColumn;
    @FXML
    private  TableColumn<Appointment, String> typeColumn;
    @FXML
    private  TableColumn<Appointment, String> descColumn;
    @FXML
    private  TableColumn<Appointment, String> startColumn;
    @FXML
    private  TableColumn<Appointment, String> endColumn;
    @FXML
    private  TableColumn<Appointment, String> custIdColumn;
    private ObservableList<Appointment> apptList = FXCollections.observableArrayList();

    /**
     * Populates both the combo box and table view when this window is opened
     * @param url inherited from super
     * @param resourceBundle inherited from super
     */
    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ){
        contactComboBox.setItems( ContactDAO.getAllContacts() );
        updateTableView();
    }

    /**
     * Calls the <code>updateTableView</code> method when a selection is made in the combo box
     * @see #updateTableView()
     */
    public void contactSelection(){
        updateTableView();
    }

    /**
     * Closes the current window
     */
    public void closeButtonPressed(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * **LAMBDA EXPRESSION**
     * <p>In this lambda expression, "a" represents each appointment in the data base, which is then
     * filtered based on the condition (if the appointment contact ID matches the contact selected in the combo box).
     * This is an efficient way to implement this in a way that improves code readability and keeps this method short</p>
     * Updates the table view when a selection is made
     */
    public void updateTableView(){
        apptList = AppointmentDAO.getAllAppointments();
        if (contactComboBox.getSelectionModel().getSelectedItem() != null){
            apptList = apptList.filtered( a -> a.getContactID() == contactComboBox.getSelectionModel().getSelectedItem().getContactID());
        }

        apptTableView.setItems( apptList );
        idColumn.setCellValueFactory( new PropertyValueFactory<>( "apptId" ) );
        titleColumn.setCellValueFactory( new PropertyValueFactory<>( "appTitle" ) );
        descColumn.setCellValueFactory( new PropertyValueFactory<>( "apptDescription" ) );
        typeColumn.setCellValueFactory( new PropertyValueFactory<>( "apptType" ) );
        startColumn.setCellValueFactory( new PropertyValueFactory<>( "apptStartDateTime" ) );
        endColumn.setCellValueFactory( new PropertyValueFactory<>( "apptEndDateTime" ) );
        custIdColumn.setCellValueFactory( new PropertyValueFactory<>( "customerID" ) );
    }
}

