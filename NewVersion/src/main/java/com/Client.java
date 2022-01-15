package com;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Client extends Application {

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

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

        init(8077);
    }

    public static void main(String[] args) {
        launch();
    }
}