package com.hubbard.appointmentapp.controller;

import com.hubbard.appointmentapp.dao.AppointmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Handles functionality of the "Appointment Stats" view, containing the one of the reports required in A3F
 * @author Lincoln Hubbard
 */
public class AppointmentStatViewController implements Initializable {
    @FXML
    private TableView<AppointmentDAO.AppointmentReport> statTableView;
    @FXML
    private TableColumn<AppointmentDAO.AppointmentReport, String> monthColumn;
    @FXML
    private TableColumn<AppointmentDAO.AppointmentReport, String> typeColumn;
    @FXML
    private TableColumn<AppointmentDAO.AppointmentReport, String> amountColumn;
    @FXML
    private Button closeButton;
    private ObservableList<AppointmentDAO.AppointmentReport> reportList = FXCollections.observableArrayList();

    /**
     * Populates the table view when this window is opened
     * @param url Inherited from super
     * @param resourceBundle Inherited from super
     */
    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ){
        updateTableView();
    }

    /**
     * This method closes the current window
     */
    public void closeButtonPressed(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    /**
     * This method updates the table view with data from a SQL query ran on the database
     */
    public void updateTableView(){
        reportList = AppointmentDAO.generateApptReports();

        statTableView.setItems( reportList );
        monthColumn.setCellValueFactory( new PropertyValueFactory<>( "month" ) );
        typeColumn.setCellValueFactory( new PropertyValueFactory<>( "type" ) );
        amountColumn.setCellValueFactory( new PropertyValueFactory<>( "amount" ) );
        statTableView.getSortOrder().add( monthColumn );
    }
}

