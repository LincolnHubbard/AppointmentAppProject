package com.hubbard.appointmentapp.controller;

import com.hubbard.appointmentapp.dao.CountryDAO;
import com.hubbard.appointmentapp.dao.CustomerDAO;
import com.hubbard.appointmentapp.dao.DivisionDAO;
import com.hubbard.appointmentapp.model.Country;
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
 * Handles functionality for the "Add Customer" view
 * @author Lincoln Hubbard
 */
public class AddCustomerController implements Initializable, InputValidation {

    @FXML
    private TextField postalField;
    @FXML
    private ComboBox<Country> countryComboBox;
    @FXML
    private ComboBox<Division> fldComboBox;
    @FXML
    private TextField nameField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneField;
    @FXML
    private Button cancelButton;

    /**
     * This method closes the current window
     */
    public void cancelButtonPressed(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Validates all input and calls method to insert customer into the database, then closes the window.
     */
    public void saveButtonPressed(){
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

        System.out.println("Creating customer with name " + name);
        try{
            CustomerDAO.insertCustomer( name, addr, post, phone, divId  );
        } catch (SQLException e) {
            throw new RuntimeException( e );
        }
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();

    }

    /**
     * Populates combo boxes and disables the division combo box until a country is selected first
     * @param url Inherited from super
     * @param resourceBundle Inherited from super
     */
    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ){
        try {
            countryComboBox.setItems( CountryDAO.getAllCountries() );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        fldComboBox.setDisable( true );
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
        fldComboBox.setItems( DivisionDAO.filterDivisions( countryId) );
    }

    /**
     * This method is called when the user interacts with the country combo box and checks if the user
     * selected a country or not
     */
    public void countrySelectionMade(){
        if (countryComboBox.getSelectionModel().getSelectedItem() != null){
            updateDivComboBox();
        }
    }
}
