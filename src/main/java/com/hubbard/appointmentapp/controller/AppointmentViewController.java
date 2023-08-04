package com.hubbard.appointmentapp.controller;

import com.hubbard.appointmentapp.Main;
import com.hubbard.appointmentapp.dao.AppointmentDAO;
import com.hubbard.appointmentapp.model.Appointment;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Handles all functionality for the "Appointment View" screen
 * @author Lincoln Hubbard
 */
public class AppointmentViewController implements Initializable {
    @FXML
    private Label apptRemainingLabel;
    @FXML
    private RadioButton monthRadioButton;
    @FXML
    private RadioButton weekRadioButton;
    @FXML
    private ToggleGroup filterVIew;
    @FXML
    private TableView<Appointment> apptTableView;
    @FXML
    private TableColumn<Appointment, String> idColumn;
    @FXML
    private TableColumn<Appointment, String> titleColumn;
    @FXML
    private TableColumn<Appointment, String> descColumn;
    @FXML
    private TableColumn<Appointment, String> locColumn;
    @FXML
    private TableColumn<Appointment, String> contactColumn;
    @FXML
    private TableColumn<Appointment, String> typeColumn;
    @FXML
    private TableColumn<Appointment, String> startColumn;
    @FXML
    private TableColumn<Appointment, String> endColumn;
    @FXML
    private TableColumn<Appointment, String> custIdColumn;
    @FXML
    private TableColumn<Appointment, String> userIdColumn;
    @FXML
    private Button cancelButton;
    private ObservableList<Appointment> apptList = FXCollections.observableArrayList();

    /**
     * Is called when the "All Appointments" radio button is selected
     */
    public void allSelected( ){
        updateTableView();
    }

    /**
     * Is called when the "By Month" radio button is selected
     */
    public void monthSelected(){
        updateTableView();
    }

    /**
     * Is called when the "By Week" radio button is selected
     */
    public void weekSelected( ){
        updateTableView();
    }

    /**
     * Populates the table view with appointment data when this window is opened
     * @param url  Inherited from super
     * @param resourceBundle Inherited from super
     */
    @Override
    public void initialize( URL url, ResourceBundle resourceBundle ){
        updateTableView();
    }

    /**
     * Opens the "Add Appointment" view
     * @throws IOException If the fxml file cannot be located
     */
    public void addButtonPressed() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader( Main.class.getResource( "addappointment.fxml") );
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Appointment");
        stage.setScene(new Scene( root));
        stage.initModality( Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        updateTableView();
    }

    /**
     * Opens the "Update Appointment" view, only if an appointment is selected
     * @throws IOException If the fxml file cannot be located
     */
    public void updateButtonPressed() throws IOException{
        if (apptTableView.getSelectionModel().getSelectedItem() == null){
            Alert noSelection = new Alert( Alert.AlertType.ERROR, "No appointment selected", ButtonType.CLOSE);
            noSelection.showAndWait();
            return;
        }
        AppointmentDAO.selectedAppointment = AppointmentDAO.getAppointment(
                apptTableView.getSelectionModel().getSelectedItem().getApptId() );
        FXMLLoader fxmlLoader = new FXMLLoader( Main.class.getResource( "updateappointment.fxml") );
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Update Appointment");
        stage.setScene(new Scene( root));
        stage.initModality( Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        updateTableView();
    }

    /**
     * Checks if an appointment is selected, then confirms deletion with user before deleting appointment
     */
    public void deleteButtonPressed(){
        if (apptTableView.getSelectionModel().getSelectedItem() == null) {
            Alert noSelection = new Alert( Alert.AlertType.ERROR, "No appointment selected", ButtonType.CLOSE);
            noSelection.showAndWait();
        }else{
            Alert confirmDelete = new Alert( Alert.AlertType.CONFIRMATION, "Are you sure you want to delete appointment?" );
            Optional<ButtonType> result = confirmDelete.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                try{
                    AppointmentDAO.deleteAppointment( apptTableView.getSelectionModel().getSelectedItem().getApptId() );
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                System.out.println("Deleting appointment with ID: " +
                                   apptTableView.getSelectionModel().getSelectedItem().getApptId());
                Alert notifyDelete = new Alert( Alert.AlertType.INFORMATION,
                                                "Appointment #" + apptTableView.getSelectionModel().getSelectedItem().getApptId() + " of type: " +
                                                apptTableView.getSelectionModel().getSelectedItem().getApptType() +" deleted!" );
                notifyDelete.showAndWait();
                updateTableView();
            }
        }
    }

    /**
     * Closes the current window
     */
    public void cancelButtonPressed(){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /**
     * ***LAMBDA EXPRESSION**
     * <p>Utilizing a lambda function in the conditional branch in this method improves code readability and conciseness
     * by taking each appointment in the list as input "a" and filtering it based on if it matches the provided timeframe</p>
     *
     * Updates the table view and filters result based on the radio button selected. Also changes the label notifying
     * the user of appointments remaining today, 1 of 3 reports required in A3F
     */
    public void updateTableView(){
        apptList = AppointmentDAO.getAllAppointments();
        if (filterVIew.getSelectedToggle() == monthRadioButton){
            apptList = apptList.filtered( a -> a.getApptStartDateTime().getMonth() == LocalDate.now().getMonth()
                                               && a.getApptStartDateTime().getYear() == LocalDate.now().getYear());
        }else if (filterVIew.getSelectedToggle() == weekRadioButton){
            apptList = apptList.filtered( a -> ChronoUnit.WEEKS.between( a.getApptStartDateTime(), LocalDateTime.now()) == 0);
        }

        apptTableView.setItems( apptList );
        idColumn.setCellValueFactory( new PropertyValueFactory<>( "apptId" ) );
        titleColumn.setCellValueFactory( new PropertyValueFactory<>( "appTitle" ) );
        descColumn.setCellValueFactory( new PropertyValueFactory<>( "apptDescription" ) );
        locColumn.setCellValueFactory( new PropertyValueFactory<>( "apptLocation" ) );
        contactColumn.setCellValueFactory( new PropertyValueFactory<>( "contactID" ) );
        typeColumn.setCellValueFactory( new PropertyValueFactory<>( "apptType" ) );
        startColumn.setCellValueFactory( new PropertyValueFactory<>( "apptStartDateTime" ) );
        endColumn.setCellValueFactory( new PropertyValueFactory<>( "apptEndDateTime" ) );
        custIdColumn.setCellValueFactory( new PropertyValueFactory<>( "customerID" ) );
        userIdColumn.setCellValueFactory( new PropertyValueFactory<>( "userID" ) );
        apptRemainingLabel.setText( String.valueOf( AppointmentDAO.getRemainingAppts( apptList ) ) );
    }


    /**
     * Opens the "Contact View" screen showing a schedule for each contact. 1 of 3 reports required in section A3F
     * @throws IOException If the fxml file cannot be found
     */
    public void contactViewPressed() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader( Main.class.getResource( "contactview.fxml") );
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Appointment");
        stage.setScene(new Scene( root));
        stage.initModality( Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        updateTableView();
    }

    /**
     * Opens the "Appointment Stats" screen showing a schedule for each contact. 1 of 3 reports required in section A3F
     * @throws IOException If the fxml file cannot be found
     */
    public void apptStatsPressed() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader( Main.class.getResource( "appointmentstatview.fxml") );
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Add Appointment");
        stage.setScene(new Scene( root));
        stage.initModality( Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
        updateTableView();
    }
}

