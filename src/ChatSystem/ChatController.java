package ChatSystem;

import ServerNClient.GameClient;
import ServerNClient.GameServer;
import account_management.Models.Account;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class ChatController {

    @FXML
    private GridPane pane;
    @FXML
    private TextField message;

    private static Account playerOne;
    private static Account playerTwo;
    private static GameServer server;
    private static GameClient client;


    public void initialize(){

        pane.setMaxWidth(1000);
        pane.setPrefWidth(1000);
    
        Thread receiveMessages = new Thread(()->{
            if(client!=null){
                while(true){
                    String messageReceived = null;
                    try {
                        messageReceived = (String) client.readData();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    String finalMessageReceived = messageReceived;
                    Platform.runLater(()->{
                        Label label = new Label(finalMessageReceived);
                        label.setPrefHeight(50);
                        label.setMinHeight(50);
                        label.setMaxHeight(50);
                        label.setMinWidth(1000);
                        label.setPadding(new Insets(10,10,10,10));
                        if(pane.getChildren().size()==0)
                            pane.addRow(0,label);
                        else
                            pane.addRow(GridPane.getRowIndex(pane.getChildren().get(pane.getChildren().size()-1))+1,label);
                    });
                }
            }
            if(server!=null){
                while(true){
                    String messageReceived = null;
                    try {
                        messageReceived = (String) server.readData();
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    String finalMessageReceived = messageReceived;
                    Platform.runLater(()->{
                        Label label = new Label(finalMessageReceived);
                        label.setPrefHeight(50);
                        label.setMinHeight(50);
                        label.setMaxHeight(50);
                        label.setMinWidth(1000);
                        label.setPadding(new Insets(10,10,10,10));
                        if(pane.getChildren().size()==0)
                            pane.addRow(0,label);
                        else
                            pane.addRow(GridPane.getRowIndex(pane.getChildren().get(pane.getChildren().size()-1))+1,label);
                    });
                }
            }
        });
        receiveMessages.start();

    }

    public void sendMessage(ActionEvent actionEvent) throws IOException {

        Label label = null;
        if(client!=null){
            label = new Label(playerTwo.getUserName() + " : " + message.getText());
            client.sendData(playerTwo.getUserName() + " : " + message.getText());
        }
        if(server!=null){
            label = new Label(playerOne.getUserName() + " : " + message.getText());
            server.sendData(playerOne.getUserName() + " : " + message.getText());
        }

        label.setPrefHeight(50);
        label.setMinHeight(50);
        label.setMinWidth(1000);
        label.setPadding(new Insets(10,10,10,10));
        if(pane.getChildren().size()==0)
            pane.addRow(0,label);
        else
            pane.addRow(GridPane.getRowIndex(pane.getChildren().get(pane.getChildren().size()-1))+1,label);

    }

    public static GameClient getClient() {
        return client;
    }

    public static GameServer getServer() {
        return server;
    }

    public static void setClient(GameClient client) {
        ChatController.client = client;
    }

    public static void setServer(GameServer server) {
        ChatController.server = server;
    }

    public static void setPlayerOne(Account playerOne) {
        ChatController.playerOne = playerOne;
    }

    public static void setPlayerTwo(Account playerTwo) {
        ChatController.playerTwo = playerTwo;
    }
}
