module com.hubbard.appointmentapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
            
                            
    opens com.hubbard.appointmentapp to javafx.fxml;
    exports com.hubbard.appointmentapp;
    exports com.hubbard.appointmentapp.controller;
    exports com.hubbard.appointmentapp.model;
    exports com.hubbard.appointmentapp.dao;
    opens com.hubbard.appointmentapp.controller to javafx.fxml;
    opens com.hubbard.appointmentapp.dao to javafx.fxml;
}