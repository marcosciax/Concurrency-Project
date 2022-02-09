package com.client.controller;

import com.client.NetworkService;
import com.client.threads.WaitForMessageThread;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class MainController {
    String username;

    @FXML
    private Label welcomeText;
    @FXML
    private Label messageText;

    @FXML
    private ListView userList;

    @FXML
    private Button chatBtn;


    ObservableList<String> onlineUsers = FXCollections.observableArrayList(new ArrayList<>());
    WaitForMessageThread waitForMessageThread;

    public MainController(String username) {
        this.username = username;
    }

    @FXML
    public void initialize() throws IOException {
        welcomeText.setText("Welcome: " + username + "!");

        userList.setItems(onlineUsers);

        waitForMessageThread = new WaitForMessageThread(this);
        waitForMessageThread.start();

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
        if(waitForMessageThread.getChatBoxMap().get(selectedUser) != null){
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/chatbox.fxml"));
            ChatBoxController chatBoxController = new ChatBoxController(selectedUser);
            fxmlLoader.setController(chatBoxController);
            Scene scene = new Scene(fxmlLoader.load(), 400, 600);
            Stage stage = new Stage();
            stage.setTitle("Chatbox");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    //System.out.println("Stage is closing");
                    waitForMessageThread.getChatBoxMap().remove(selectedUser);
                }
            });

            waitForMessageThread.getChatBoxMap().put(selectedUser,chatBoxController);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }

    }


    @FXML
    void onTicTacBtnClick(){
        String selectedUser = (String)userList.getSelectionModel().getSelectedItem();

        if(selectedUser == null || selectedUser.isEmpty()){
            return;
        }
        NetworkService.getInstance().sendMessage("TICTACREQUEST="+username+"-"+selectedUser);
        messageText.setText("Sent request play-tictactoe to " + selectedUser + "!");

    }



    public void ticTacToeRequest(String fromUser){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Request");
        alert.setHeaderText(fromUser + " want to play Tic Tac Toe with you!");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null  || option.get() == ButtonType.CANCEL) {
        } else if (option.get() == ButtonType.OK) {
            NetworkService.getInstance().sendMessage("TICTACACCEPT="+fromUser+"-"+username);
        }
        messageText.setText("Accept request TicTacToe from " + fromUser + "!");
    }

    public void openTicTacWindow(String selectedUser,String playFirst,int roomId)  {
        if(waitForMessageThread.getTicTacMap().get(selectedUser) != null){
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/tictactoe.fxml"));
            TicTacToeController ticTacToeController = new TicTacToeController(selectedUser,playFirst,roomId);
            fxmlLoader.setController(ticTacToeController);
            Scene scene = new Scene(fxmlLoader.load(), 450, 450);
            Stage stage = new Stage();
            stage.setTitle("Tic tac toe");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    //System.out.println("Stage is closing");
                    waitForMessageThread.getTicTacMap().remove(selectedUser);
                }
            });

            waitForMessageThread.getTicTacMap().put(selectedUser,ticTacToeController);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }

    }

//////////////////////chess

    @FXML
    void onChessBtnClicked(){
        String selectedUser = (String)userList.getSelectionModel().getSelectedItem();

        if(selectedUser == null || selectedUser.isEmpty()){
            return;
        }
        NetworkService.getInstance().sendMessage("CHESSREQUEST="+username+"-"+selectedUser);
        messageText.setText("Sent request play-chess to " + selectedUser + "!");
    }

    public void chessRequest(String fromUser){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Request");
        alert.setHeaderText(fromUser + " want to play Chess with you!");

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == null  || option.get() == ButtonType.CANCEL) {
        } else if (option.get() == ButtonType.OK) {
            NetworkService.getInstance().sendMessage("CHESSACCEPT="+fromUser+"-"+username);
        }
        messageText.setText("Accept request Chess from " + fromUser + "!");
    }

    public void openChessWindow(String selectedUser,String playFirst,int roomId)  {
        if(waitForMessageThread.getChessMap().get(selectedUser) != null){
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/chess.fxml"));
            ChessController chessController = new ChessController(selectedUser,playFirst,roomId);
            fxmlLoader.setController(chessController);
            Scene scene = new Scene(fxmlLoader.load(), 800, 700);
            Stage stage = new Stage();
            stage.setTitle("Chess");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.setScene(scene);
            stage.show();

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                public void handle(WindowEvent we) {
                    //System.out.println("Stage is closing");
                    waitForMessageThread.getChessMap().remove(selectedUser);
                }
            });

            waitForMessageThread.getChessMap().put(selectedUser,chessController);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }

    }

}
