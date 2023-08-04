package com.hubbard.appointmentapp.controller;

import com.hubbard.appointmentapp.Main;
import com.hubbard.appointmentapp.dao.AppointmentDAO;
import com.hubbard.appointmentapp.dao.CustomerDAO;
import com.hubbard.appointmentapp.model.Country;
import com.hubbard.appointmentapp.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Handles all functionality for the "Customer View" screen
 * @author Lincoln Hubbard
 */
public class CustomerViewController implements Initializable {

    @FXML
    private Button cancelButton;
    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, String> idColumn;
    @FXML
    private TableColumn<Customer, String> nameColumn;
    @FXML
    private TableColumn<Customer, String> addrColumn;
    @FXML
    private TableColumn<Customer, String> phoneColumn;
    @FXML
    private TableColumn<Country, String> countryColumn;
    @FXML
    private TableColumn<Customer, String> postColumn;
    @FXML
    private TableColumn<Customer, String> divColumn;


    /**
     * Opens the "Add Customer" view
     * @throws IOException if the fxml file cannot be found
     */
    public void addButtonPressed() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader( Main.class.getResource( "addcustomer.fxml") );
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Customer");
        stage.setScene(new Scene( root));
        stage.initModality( Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        updateTableView();
    }

    /**
     * Opens the "Update Customer" View
     * @throws IOException if the fxml file cannot be found
     */
    public void updateButtonPressed() throws IOException{
        if (customerTableView.getSelectionModel().getSelectedItem() == null){
            Alert noSelection = new Alert( Alert.AlertType.ERROR, "No Customer selected", ButtonType.CLOSE);
            noSelection.showAndWait();
            return;
        }
        CustomerDAO.selectedCustomer = CustomerDAO.getCustomer(
                customerTableView.getSelectionModel().getSelectedItem().getCustomerId() );
        FXMLLoader fxmlLoader = new FXMLLoader( Main.class.getResource( "updatecustomer.fxml") );
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Customer");
        stage.setScene(new Scene( root));
        stage.initModality( Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        updateTableView();
    }

    /**
     * First checks if the customer has scheduled appointments, then deletes after confirming with user
     * @throws SQLException if there is an error running the SQL queries
     */
    public void deleteButtonPressed() throws SQLException{

        if (customerTableView.getSelectionModel().getSelectedItem() == null)
        {
            Alert noSelection = new Alert( Alert.AlertType.ERROR, "No Customer selected", ButtonType.CLOSE);
            noSelection.showAndWait();
        }else{
            if (AppointmentDAO.containsCustId( customerTableView.getSelectionModel().getSelectedItem().getCustomerId())){
                Alert cannotDelete = new Alert( Alert.AlertType.ERROR, "Customer has scheduled appointments!", ButtonType.CLOSE);
                cannotDelete.showAndWait();
            }else {
                Alert confirmDelete = new Alert( Alert.AlertType.CONFIRMATION, "Are you sure you want to delete customer?" );
                Optional<ButtonType> result = confirmDelete.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    try{
                        CustomerDAO.deleteCustomer( customerTableView.getSelectionModel().getSelectedItem().getCustomerId());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Deleting customer with ID: " +
                                       customerTableView.getSelectionModel().getSelectedItem().getCustomerId());
                    updateTableView();

                }
            }
        }
    }

    /**
     * Closes the current window
     */
    public void cancelButtonPressed( ){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * Populates the table view with customer data once the window is opened
     * @param url inherited from super
     * @param resourceBundle inherited from super
     */
    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ){
        updateTableView();
    }

    /**
     * Retrieves all customer data from the database and populates the table view with said data
     */
    public void updateTableView(){
        customerList = CustomerDAO.getAllCustomers();

        customerTableView.setItems( customerList );
        idColumn.setCellValueFactory(new PropertyValueFactory<>( "customerId" ) );
        nameColumn.setCellValueFactory( new PropertyValueFactory<>("customerName"  ) );
        addrColumn.setCellValueFactory( new PropertyValueFactory<>("customerAddress"  ) );
        postColumn.setCellValueFactory( new PropertyValueFactory<>("customerPostalCode"  ) );
        phoneColumn.setCellValueFactory( new PropertyValueFactory<>("customerPhone"  ) );
        divColumn.setCellValueFactory( new PropertyValueFactory<>("customerDivisionId"  ) );
        countryColumn.setCellValueFactory( new PropertyValueFactory<>( "countryName" ) );
    }
}

