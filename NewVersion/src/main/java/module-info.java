module com.example.mul {
    requires javafx.controls;
    requires javafx.fxml;


    opens com to javafx.fxml;
    exports com.client;
    opens com.client to javafx.fxml;
    exports com.client.controller;
    opens com.client.controller to javafx.fxml;

}