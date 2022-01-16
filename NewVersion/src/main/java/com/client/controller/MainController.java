package com.client.controller;

import com.client.NetworkService;
import com.client.threads.WaitForMessageThread;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class MainController {
    String username;

    @FXML
    private Label welcomeText;

    @FXML
    private ListView userList;

    @FXML
    private Button chatBtn;


    ObservableList<String> onlineUsers = FXCollections.observableArrayList(new ArrayList<>());

    public MainController(String username) {
        this.username = username;
    }

    @FXML
    public void initialize() throws IOException {
        welcomeText.setText("Welcome: " + username + "!");

        userList.setItems(onlineUsers);

        new WaitForMessageThread(this).start();

        NetworkService.getInstance().sendMessage("FETCHUSER=OK");
    }

    @FXML
    protected void onRefreshClick(){
        NetworkService.getInstance().sendMessage("FETCHUSER=OK");
    }

    public void fetchOnlineUsers(String message){
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

    @FXML
    public void onChatBtnClick(){
        String selectedUser = (String)userList.getSelectionModel().getSelectedItem();

        if(selectedUser == null || selectedUser.isEmpty()){
            return;
        }

        openMessageWindow(selectedUser);
    }

    public void openMessageWindow(String selectedUser)  {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/chatbox.fxml"));
            fxmlLoader.setController(new ChatBoxController(selectedUser));
            Scene scene = new Scene(fxmlLoader.load(), 400, 300);
            Stage stage = new Stage();
            stage.setTitle("Main");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.out.println(e);
        }

    }

}
