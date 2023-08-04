package com.hubbard.appointmentapp.controller;

import com.hubbard.appointmentapp.Main;
import com.hubbard.appointmentapp.dao.UserDAO;
import com.hubbard.appointmentapp.utility.FileManager;
import com.hubbard.appointmentapp.utility.TimeManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Handles all functionality for the "Login" screen
 * @author Lincoln Hubbard
 */
public class LoginController implements Initializable {
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField userField;
    @FXML
    private Button loginButton;
    @FXML
    private Label zoneField;
    @FXML
    private Label locationLabel;
    @FXML
    private Label passLabel;
    @FXML
    private Label userLabel;
    @FXML
    private Button exitButton;
    @FXML
    private Label loginLabel;

    /**
     * Checks the database for valid username/password combo and displays message on log-in fail or success.
     * Also changes messages based on the user system's default language
     * @throws IOException if there is an error retrieving user input
     */
    public void loginPressed() throws IOException{
        Alert loginStatus;
        String loginGoodMsg;
        String loginBadMsg;
        String userInput = userField.getText();
        String passInput =  passwordField.getText();
        System.out.println(userInput);
        System.out.println(passInput);
        if (Locale.getDefault().getLanguage().equals( "fr" )){
            loginGoodMsg = "Connexion réussie";
            loginBadMsg = "Échec de la connexion";
        }else {
            loginGoodMsg = "Login Successful";
            loginBadMsg = "Login Failed";
        }

        if(UserDAO.checkPass( userInput, passInput )){
            System.out.println("Opening main screen");
            loginStatus = new Alert( Alert.AlertType.INFORMATION, loginGoodMsg, ButtonType.OK );
            FileManager.printLogAttempt( userInput, passInput, true );
            loginStatus.showAndWait();
            switchToMain();
        }else {
            loginStatus = new Alert( Alert.AlertType.ERROR, loginBadMsg );
            FileManager.printLogAttempt( userInput, passInput, false );
            loginStatus.showAndWait();
        }
    }

    /**
     * Closes this window and opens the main menu for the application
     * @throws IOException if the fxml file cannot be found
     */
    public void switchToMain() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader( Main.class.getResource( "main.fxml"));
        Stage stage = new Stage();
        Stage stage1 = (Stage) loginButton.getScene().getWindow();
        Scene scene = new Scene( fxmlLoader.load());
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();
        stage.setResizable( false );
        stage1.close();
    }

    /**
     * Translates all labels on log-in form and also displays the user's time zone
     * @param url inherited from super
     * @param resourceBundle inherited from super
     */
    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ){
        ResourceBundle rb = ResourceBundle.getBundle( "Nat", Locale.getDefault()); //Comment out to test translation
//        ResourceBundle rb = ResourceBundle.getBundle( "Nat", Locale.FRANCE); Uncomment to test translation
        changeLabels( rb );
        zoneField.setText( TimeManager.getUserTimeZone().toString());
    }

    /**
     * Changes all labels on the "log-in" form based on if user's system is set to French/English
     * @param rb the resource bundle to be used
     */
    public void changeLabels(ResourceBundle rb){
        if (rb.getLocale().getLanguage().equals( "fr" )) {
            loginButton.setText( rb.getString( "Log-in" ) );
            exitButton.setText( rb.getString( "Exit" ) );
            locationLabel.setText( rb.getString( "Location" ) );
            userLabel.setText( rb.getString( "Username" ) );
            passLabel.setText( rb.getString( "Password" ) );
            loginLabel.setText( rb.getString( "Loginform" ) );
        }
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
}

