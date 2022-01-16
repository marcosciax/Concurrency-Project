package com.client.controller;

import com.client.CDataService;
import com.client.Model.Message;
import com.client.NetworkService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatBoxController {

    String toUser;

    @FXML
    private Label chatToText;

    @FXML
    private VBox messageView;

    @FXML
    private TextField messageText;

    List<Message> messageList;

    public ChatBoxController(String toUser) {
        this.toUser = toUser;
    }

    @FXML
    public void initialize() throws IOException {
        chatToText.setText("Chat with " + toUser);

        messageList = CDataService.getInstance().getMessagesMap().get(toUser);
        if(messageList == null){
            messageList = new ArrayList<>();
            CDataService.getInstance().getMessagesMap().put(toUser,messageList);
        }
        renderMessages();
    }
    void renderMessages(){
        messageView.getChildren().clear();
        for(Message message : messageList){
            Label messageText;
            messageText = new Label(message.getContent());
            messageText.setPrefHeight(60);
            messageText.setMinHeight(60);
            messageText.setPrefWidth(300);

            if(message.getTo().equals(toUser)){
                messageText.setAlignment(Pos.CENTER_RIGHT);
            }
            messageView.getChildren().add(messageText);
        }
    }

    @FXML
    void onSendBtnClick(){
        String message = messageText.getText();
        if(message.isEmpty()){
            return;
        }
        NetworkService.getInstance().sendMessage(String.format("MESSAGE=%s-%s-%s",CDataService.getInstance().getUsername(),toUser,message));

        messageList.add(new Message(CDataService.getInstance().getUsername(), toUser,message));
        renderMessages();

        messageText.setText("");
    }

    public void gotNewMessage(Message message){
        messageList.add(message);
        renderMessages();
    }

}
