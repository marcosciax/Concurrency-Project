package TicTacToe.Controllers;

import ChatSystem.ChatController;
import Checkers.Models.BoardInfo;
import ServerNClient.GameClient;
import ServerNClient.GameServer;
import TicTacToe.GameInfo;
import account_management.Models.Account;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.lang.constant.Constable;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GameController {

    private boolean isTurn = false; //false is for P1 , true is for P2
    static int gameMode; // 1 for single player
    public static Account playerOne;
    public static Account playerTwo;
    private final String playerOneIdentity = "X";
    private final String playerTwoIdentity = "O";

    private int i = 0;

    @FXML
    private Label Label1x1;
    @FXML
    private Label Label2x1;
    @FXML
    private Label Label3x1;
    @FXML
    private Label Label1x2;
    @FXML
    private Label Label2x2;
    @FXML
    private Label Label3x2;
    @FXML
    private Label Label1x3;
    @FXML
    private Label Label2x3;
    @FXML
    private Label Label3x3;
    @FXML
    private Label winner;
    @FXML
    private Button reset;

    public void setPlayers(Account playerone, Account playertwo){
        playerOne = playerone;
        playerTwo = playertwo;
    }

    public void back(javafx.event.ActionEvent actionEvent) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("/Interface/StartingWindow.fxml"));
//        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
//        Scene scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }

    public void restart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/TicTacToe/Controllers/gameWindow.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize() {

        int port = 4002;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(GameInfo.getSocketServer()!=null){
                    try {
                        ChatController.setServer(new GameServer(port));
                        ChatController.getServer().start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ChatController.setClient(null);
                }
                else if(GameInfo.getSocketClient()!=null) {
                    ChatController.setServer(null);
                    try {
                        ChatController.setClient(new GameClient(port));
                        ChatController.getClient().start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

        Thread receive = new Thread(new Runnable() {
            @Override
            public void run() {
                if (GameInfo.getSocketClient() != null) {
                    while (true) {
                        try {
                            DataToBeSent data = (DataToBeSent) GameInfo.getSocketClient().readData();
                            Platform.runLater(() -> {
                                switch (data.message) {
                                    case "1x1" -> Label1x1.setText(data.type);
                                    case "1x2" -> Label1x2.setText(data.type);
                                    case "1x3" -> Label1x3.setText(data.type);
                                    case "2x1" -> Label2x1.setText(data.type);
                                    case "2x2" -> Label2x2.setText(data.type);
                                    case "2x3" -> Label2x3.setText(data.type);
                                    case "3x1" -> Label3x1.setText(data.type);
                                    case "3x2" -> Label3x2.setText(data.type);
                                    case "3x3" -> Label3x3.setText(data.type);
                                }
                            });
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (GameInfo.getSocketServer() != null) {
                    while (true) {
                        try {
                            DataToBeSent data = (DataToBeSent) GameInfo.getSocketServer().readData();
                            Platform.runLater(() -> {
                                switch (data.message) {
                                    case "1x1" -> Label1x1.setText(data.type);
                                    case "1x2" -> Label1x2.setText(data.type);
                                    case "1x3" -> Label1x3.setText(data.type);
                                    case "2x1" -> Label2x1.setText(data.type);
                                    case "2x2" -> Label2x2.setText(data.type);
                                    case "2x3" -> Label2x3.setText(data.type);
                                    case "3x1" -> Label3x1.setText(data.type);
                                    case "3x2" -> Label3x2.setText(data.type);
                                    case "3x3" -> Label3x3.setText(data.type);
                                }
                            });
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        receive.start();

        reset.setDisable(true);

    }

    public void makeMove(MouseEvent mouseEvent) throws IOException {

        Alert alert;

        String messageIndex = null;
        Label l =  (Label) mouseEvent.getSource();

        if (Label1x1.equals(l))
            messageIndex="1x1";
        else if(Label1x2.equals(l))
            messageIndex="1x2";
        else if(Label1x3.equals(l))
            messageIndex="1x3";
        else if(Label2x1.equals(l))
            messageIndex="2x1";
        else if(Label2x2.equals(l))
            messageIndex="2x2";
        else if(Label2x3.equals(l))
            messageIndex="2x3";
        else if(Label3x1.equals(l))
            messageIndex="3x1";
        else if(Label3x2.equals(l))
            messageIndex="3x2";
        else if(Label3x3.equals(l))
            messageIndex="3x3";


        if(l.getText().equals("X")||l.getText().equals("O")){
            alert = new Alert(Alert.AlertType.WARNING,"The box is Already Filled");
            alert.show();
        }else {
            if (!isTurn)
                l.setText(playerOneIdentity);
            else
                l.setText(playerTwoIdentity);
            isTurn = !isTurn;
        }

        DataToBeSent data = new DataToBeSent();
        data.message = messageIndex;

        if(isTurn)
            data.type=playerOneIdentity;
        else
            data.type=playerTwoIdentity;

        if(GameInfo.getSocketClient()!=null){
            GameInfo.getSocketClient().sendData(data);
        }else if(GameInfo.getSocketServer()!=null){
            GameInfo.getSocketServer().sendData(data);
        }

        if(gameMode==1&&i!=16){
            Random random =  new Random();
            boolean isRepeat = false;
            Label finalPos = null;
            do {
                isRepeat=false;
                int pos = random.nextInt(9);
                switch (pos) {
                    case 0 -> {
                        isRepeat=true;
                    }
                    case 1 -> {
                        if (Label1x1.getText().equals("X") || Label1x1.getText().equals("O")){
                            isRepeat=true;
                        }
                        finalPos=Label1x1;
                    }
                    case 2 -> {
                        if (Label1x2.getText().equals("X") || Label1x2.getText().equals("O")){
                            isRepeat=true;
                        }
                        finalPos=Label1x2;
                    }
                    case 3 -> {
                        if (Label1x3.getText().equals("X") || Label1x3.getText().equals("O")){
                            isRepeat=true;
                        }
                        finalPos=Label1x3;
                    }
                    case 4 -> {
                        if (Label2x1.getText().equals("X") || Label2x1.getText().equals("O")){
                            isRepeat=true;
                        }
                        finalPos=Label2x1;
                    }
                    case 5 -> {
                        if (Label2x2.getText().equals("X") || Label2x2.getText().equals("O")){
                            isRepeat=true;
                        }
                        finalPos=Label2x2;
                    }
                    case 6 -> {
                        if (Label2x3.getText().equals("X") || Label2x3.getText().equals("O")){
                            isRepeat=true;
                        }
                        finalPos=Label2x3;
                    }
                    case 7 -> {
                        if (Label3x1.getText().equals("X") || Label3x1.getText().equals("O")){
                            isRepeat=true;
                        }
                        finalPos=Label3x1;
                    }
                    case 8 -> {
                        if (Label3x2.getText().equals("X") || Label3x2.getText().equals("O")){
                            isRepeat=true;
                        }
                        finalPos=Label3x2;
                    }
                    case 9 -> {
                        if (Label3x3.getText().equals("X") || Label3x3.getText().equals("O")){
                            isRepeat=true;
                        }
                        finalPos=Label3x3;
                    }
                }
            }while (isRepeat);
            assert finalPos != null;
            finalPos.setText("O");
            isTurn = !isTurn;
            i += 2;
        }

        if (checkStatus().equals(true)) {
            winner.setText(playerOne.getUserName()+" has won the Game");
            reset.setDisable(false);
        } else if (checkStatus().equals(false)) {
            winner.setText(playerTwo.getUserName()+" has won the Game");
            reset.setDisable(false);
        }

        if (i == 18) {
            alert = new Alert(Alert.AlertType.INFORMATION, "Match is Draw");
            alert.show();
            reset.setDisable(false);
        }

    }

    public Constable checkStatus () {
        if (Label1x1.getText().equals(Label1x2.getText()) && Label1x1.getText().equals(Label1x3.getText()) && Label1x1.getText().equals("X"))
            return true;
        if (Label2x1.getText().equals(Label2x2.getText()) && Label2x1.getText().equals(Label2x3.getText()) && Label2x1.getText().equals("X"))
            return true;
        if (Label3x1.getText().equals(Label3x2.getText()) && Label3x1.getText().equals(Label3x3.getText()) && Label3x1.getText().equals("X"))
            return true;

        if (Label1x1.getText().equals(Label2x1.getText()) && Label1x1.getText().equals(Label3x1.getText()) && Label1x1.getText().equals("X"))
            return true;
        if (Label1x2.getText().equals(Label2x2.getText()) && Label1x2.getText().equals(Label3x2.getText()) && Label1x2.getText().equals("X"))
            return true;
        if (Label1x3.getText().equals(Label2x3.getText()) && Label1x3.getText().equals(Label3x3.getText()) && Label1x3.getText().equals("X"))
            return true;

        if (Label1x1.getText().equals(Label2x2.getText()) && Label1x1.getText().equals(Label3x3.getText()) && Label1x1.getText().equals("X"))
            return true;
        if (Label1x3.getText().equals(Label2x2.getText()) && Label1x3.getText().equals(Label3x1.getText()) && Label1x3.getText().equals("X"))
            return true;

        if (Label1x1.getText().equals(Label1x2.getText()) && Label1x1.getText().equals(Label1x3.getText()) && Label1x1.getText().equals("O"))
            return false;
        if (Label2x1.getText().equals(Label2x2.getText()) && Label2x1.getText().equals(Label2x3.getText()) && Label2x1.getText().equals("O"))
            return false;
        if (Label3x1.getText().equals(Label3x2.getText()) && Label3x1.getText().equals(Label3x3.getText()) && Label3x1.getText().equals("O"))
            return false;

        if (Label1x1.getText().equals(Label2x1.getText()) && Label1x1.getText().equals(Label3x1.getText()) && Label1x1.getText().equals("O"))
            return false;
        if (Label1x2.getText().equals(Label2x2.getText()) && Label1x2.getText().equals(Label3x2.getText()) && Label1x2.getText().equals("O"))
            return false;
        if (Label1x3.getText().equals(Label2x3.getText()) && Label1x3.getText().equals(Label3x3.getText()) && Label1x3.getText().equals("O"))
            return false;

        if (Label1x1.getText().equals(Label2x2.getText()) && Label1x1.getText().equals(Label3x3.getText()) && Label1x1.getText().equals("O"))
            return false;
        if (Label1x3.getText().equals(Label2x2.getText()) && Label1x3.getText().equals(Label3x1.getText()) && Label1x3.getText().equals("O"))
            return false;

        i++;
        return "NuN";
    }

}
