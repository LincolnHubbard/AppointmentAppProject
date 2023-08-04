package com.hubbard.appointmentapp.controller;

import com.hubbard.appointmentapp.dao.CountryDAO;
import com.hubbard.appointmentapp.dao.CustomerDAO;
import com.hubbard.appointmentapp.dao.DivisionDAO;
import com.hubbard.appointmentapp.model.Country;
import com.hubbard.appointmentapp.model.Customer;
import com.hubbard.appointmentapp.model.Division;
import com.hubbard.appointmentapp.utility.InputValidation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Handles all functionality for updating customers
 */
public class UpdateCustomerController implements Initializable, InputValidation {
    @FXML
    private TextField idField;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private ComboBox<Country> countryComboBox;
    @FXML
    private TextField postalField;
    @FXML
    private ComboBox<Division> fldComboBox;

    /**
     * Closes the current window
     */
    public void cancelButtonPressed(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Updates the division combo box when a country is selected. Does not need to check if selection is null since
     * a value will be loaded in when the window opens
     */
    public void countrySelectionMade(){
        updateDivComboBox();
    }

    /**
     * Validates all input and calls method to insert customer into the database, then closes the window.
     */
    public void saveButtonPressed(){
        int custId = Integer.parseInt( idField.getText() );
        String name = nameField.getText();
        if (!stringIsGood.test( name )){
            InputValidation.displayError( "Name cannot be empty!" );
            return;
        }
        String addr = addressField.getText();
        if (!stringIsGood.test( addr )){
            InputValidation.displayError( "Address cannot be empty!" );
            return;
        }
        String post = postalField.getText();
        if (!stringIsGood.test( post )){
            InputValidation.displayError( "Postal code cannot be empty!" );
            return;
        }
        String phone = phoneField.getText();
        if (!stringIsGood.test( phone )){
            InputValidation.displayError( "Phone number cannot be empty!" );
            return;
        }
        if (!InputValidation.comboBoxHasSelection( fldComboBox )){
            InputValidation.displayError( "Country and State/Province/Region cannot be empty!" );
            return;
        }
        int divId = fldComboBox.getSelectionModel().getSelectedItem().getDivisionId();

        try {
            CustomerDAO.updateCustomer( custId, name, addr, post, phone, divId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Populates combo boxes and loads customer data from the database
     * @param url Inherited from super
     * @param resourceBundle Inherited from super
     */
    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ){
        try {
            countryComboBox.setItems( CountryDAO.getAllCountries() );
            fldComboBox.setItems( DivisionDAO.getAllDivisions() );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        loadCustomerValues();

    }

    /**
     * Loads all customer values from the database and populates fields + combo boxes
     */
    public void loadCustomerValues( ){
        Customer custBuffer = CustomerDAO.selectedCustomer;
        CustomerDAO.selectedCustomer = null;
        idField.setText( String.valueOf( custBuffer.getCustomerId() ) );
        nameField.setText( custBuffer.getCustomerName() );
        addressField.setText( custBuffer.getCustomerAddress() );
        phoneField.setText( custBuffer.getCustomerPhone() );
        postalField.setText( custBuffer.getCustomerPostalCode() );
        for (Division d: fldComboBox.getItems()){
            if (d.getDivisionId() == custBuffer.getCustomerDivisionId()){
                fldComboBox.setValue( d );
            }
        }
        for(Country c : countryComboBox.getItems()){
            if(c.getCountryId() == fldComboBox.getSelectionModel().getSelectedItem().getDivisionCountryId()){
                countryComboBox.setValue( c );
            }
        }
    }

    /**
     * Repopulates the division combo box based on the country the user selects. Arrows in switch statement are
     * NOT lambda expressions
     */
    public void updateDivComboBox(){
        fldComboBox.getSelectionModel().clearSelection();
        int countryId = countryComboBox.getSelectionModel().getSelectedItem().getCountryId();
        fldComboBox.setDisable( false );
        switch( countryId ) {
            case 1 -> fldComboBox.setPromptText( "State" );
            case 2 -> fldComboBox.setPromptText( "Region" );
            case 3 -> fldComboBox.setPromptText( "Province" );
        }
        fldComboBox.setItems( DivisionDAO.filterDivisions(countryId) );
    }
}

