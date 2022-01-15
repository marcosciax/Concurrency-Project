package com.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientController {
    @FXML
    private TextField usernameText;

    @FXML
    private Button connectBtn;

    @FXML
    public void initialize() throws IOException {

    }
    @FXML
    protected void onHelloButtonClick() {
        System.out.println("connect to sever");

        String username = usernameText.getText();
        connectBtn.setDisable(true);

        NetworkService.getInstance().sendMessage("LOGIN=" + username);
        String message =  NetworkService.getInstance().receiveMessage();
        System.out.println("server: " + message);

        connectBtn.setDisable(false);
    }

}