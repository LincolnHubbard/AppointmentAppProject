package com.hubbard.appointmentapp.controller;

import com.hubbard.appointmentapp.Main;
import com.hubbard.appointmentapp.dao.AppointmentDAO;
import com.hubbard.appointmentapp.dao.UserDAO;
import com.hubbard.appointmentapp.model.Appointment;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Handles all functionality for the main menu of the application
 * @author Lincoln Hubbard
 */
public class MainController implements Initializable {
    @FXML
    private Label idLabel;
    @FXML
    private Label notifyLabel;

    /**
     * Displays the user's ID and checks if any appointments are scheduled to start in 15 minutes or less
     * @param url inherited from super
     * @param resourceBundle inherited from super
     */
    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ){
        idLabel.setText( String.valueOf( UserDAO.currentUser.getUserID() ) );
        checkUpcomingAppt();
    }

    /**
     * Confirms with user before exiting application
     */
    public void exitButtonPressed(){
        Alert confirmExit = new Alert( Alert.AlertType.CONFIRMATION, "Are you sure you want to exit");
        Optional<ButtonType> result = confirmExit.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK){
            javafx.application.Platform.exit();
        }
    }

    /**
     * Opens the "Appointment View" screen
     * @throws IOException if the fxml file cannot be found
     */
    public void apptButtonPressed() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader( Main.class.getResource( "appointmentview.fxml") );
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Appointment View");
        stage.setScene(new Scene( root));
        stage.initModality( Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }

    /**
     * Opens the "Customer View" screen
     * @throws IOException if the fxml file cannot be found
     */
    public void custButtonPressed() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader( Main.class.getResource( "customerview.fxml") );
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Customer View");
        stage.setScene(new Scene( root));
        stage.initModality( Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }

    /**
     * Checks all appointments to see if any are starting in 15 minutes ore less, from the time the user logs into
     * application
     */
    public void checkUpcomingAppt(){
        boolean apptIsSoon = false;
        ObservableList<Appointment> apptList = AppointmentDAO.getAllAppointments();
        LocalTime userTime = LocalTime.now(); //Comment out this line to test method
//        LocalTime userTime = LocalTime.of( 4, 55 ); Uncomment to hardcode in user's local time to test method
        LocalDateTime userDT = LocalDateTime.of( LocalDate.now(), userTime );
        System.out.println(userTime);
        for (Appointment a : apptList){
            LocalDateTime apptTime =  a.getApptStartDateTime();
            long deltaTime = ChronoUnit.MINUTES.between( userDT, apptTime );
            if (deltaTime <= 15 && deltaTime > 0){
                apptIsSoon = true;
                String notifyMsg = "Appointment ID: " + a.getApptId() + " Starts in " + deltaTime + " minutes!";
                notifyLabel.setText( notifyMsg );
            }
        }
        if (!apptIsSoon){
            String notifyMsg = "No upcoming appointments";
            notifyLabel.setText( notifyMsg );
        }
    }
}