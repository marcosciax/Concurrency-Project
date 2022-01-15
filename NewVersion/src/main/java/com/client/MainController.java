package com.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {
    String username;

    @FXML
    private Label welcomeText;

    @FXML
    private ListView userList;

    ObservableList<String> onlineUsers = FXCollections.observableArrayList(new ArrayList<>());

    public MainController(String username) {
        this.username = username;
    }

    @FXML
    public void initialize() throws IOException {
        welcomeText.setText("Welcome: " + username + "!");

        userList.setItems(onlineUsers);

        fetchOnlineUsers();
    }

    @FXML
    protected void onRefreshClick(){
        fetchOnlineUsers();
    }

    void fetchOnlineUsers(){

        NetworkService.getInstance().sendMessage("FETCHUSER=OK");
        String message = NetworkService.getInstance().receiveMessage();
        String[] response = message.split("\\=");

        onlineUsers.clear();
        if(response.length > 1){
            String[] users = response[1].split("-");
            for(String u : users){
                if(u.isEmpty() ){
                    continue;
                }
                onlineUsers.add(u);
            }
            userList.refresh();
        }
    }


}
