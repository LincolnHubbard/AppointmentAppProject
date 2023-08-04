package com.hubbard.appointmentapp;

import com.hubbard.appointmentapp.utility.JDBC;
import com.hubbard.appointmentapp.utility.TimeManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * <p>NOTE: Lambda expressions are included in "AppointmentViewController.java" and "InputValidation.java"</p>
 * The main entry point for the program
 * @author Lincoln Hubbard
 */
public class Main extends Application {

    /**
     * @param stage The stage to load when starting the application
     * @throws IOException If the fxml file to be loaded cannot be found
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader( Main.class.getResource( "login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();
        stage.setResizable( false );
    }

    /**
     * Main function called on program start
     * @param args The arguments entered when running application from the command line
     */
    public static void main(String[] args) {
        JDBC.openConnection();
        TimeManager.setUserTimeZone();
//        Locale.setDefault( Locale.FRENCH );
        launch();
        JDBC.closeConnection();
    }
}