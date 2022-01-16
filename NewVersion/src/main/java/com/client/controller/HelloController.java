package com.client.controller;

import com.client.CDataService;
import com.client.NetworkService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private TextField usernameText;

    @FXML
    private Button connectBtn;

    @FXML
    public void initialize() throws IOException {

    }

    @FXML
    protected void onHelloButtonClick() throws IOException {
        System.out.println("connect to sever");

        String username = usernameText.getText();
        connectBtn.setDisable(true);

        NetworkService.getInstance().sendMessage("LOGIN=" + username);
        String message =  NetworkService.getInstance().receiveMessage();
        System.out.println("server: " + message);

        if(message.isEmpty()){
            return;
        }

        if(message.contains("OK")){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/com/main.fxml"));
                 fxmlLoader.setController(new MainController(username));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                Stage stage = new Stage();
                stage.setTitle("Main");
                stage.setScene(scene);
                stage.show();

                CDataService.getInstance().setUsername(username);

                Stage thisStage = (Stage) usernameText.getScene().getWindow();
                thisStage.close();

            } catch (IOException e) {
                System.out.println(e);
                throw e;
            }
        }
        connectBtn.setDisable(false);
    }

}