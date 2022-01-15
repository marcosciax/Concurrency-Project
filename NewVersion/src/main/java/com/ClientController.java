package com;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientController {
    @FXML
    private Label welcomeText;




    Socket socket;

    PrintWriter out;
    BufferedReader in;

    void init(int port) throws IOException {
        socket =  new Socket("localhost",port);
        this.out = new PrintWriter(this.socket.getOutputStream(), true, StandardCharsets.UTF_8);
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
    }

    public void sendMessage(String data) throws IOException {
        this.out.println(data);
    }

    public String receiveMessage() throws IOException {
        String line = this.in.readLine();
        return line;
    }

    @FXML
    public void initialize() throws IOException {
        init(8077);

    }
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Send message to server!");

        try {
            sendMessage("hello");
            String message = receiveMessage();
            System.out.println("server: " + message);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}