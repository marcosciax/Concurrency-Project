package Checkers.Models;

import Checkers.Controller.BoardController;
import ServerNClient.GameClient;
import ServerNClient.GameServer;
import account_management.Models.Account;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class BoardInfo {

    private int boardId;
    private Account playerOne;
    private Account playerTwo;
    private static GameServer socketServer;
    private static GameClient socketClient;

    public BoardInfo(){
        setBoardId();
    }

    public Account getPlayerOne() {
        return playerOne;
    }

    public Account getPlayerTwo() {
        return playerTwo;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId() {
        Random random = new Random();
        this.boardId = random.nextInt();
    }

    public void setPlayerTwo(Account playerTwo) {
        this.playerTwo = playerTwo;
        BoardController.playerTwo=playerTwo;
    }

    public void setPlayerOne(Account playerOne) {
        this.playerOne = playerOne;
        BoardController.playerOne=playerOne;
    }

    public void startGame(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/Checkers/Interface/board.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    public static GameServer getSocketServer() {
        return socketServer;
    }

    public static GameClient getSocketClient() {
        return socketClient;
    }

    public static void setSocketClient(GameClient socketClient) {
        BoardInfo.socketClient = socketClient;
    }

    public static void setSocketServer(GameServer socketServer) {
        BoardInfo.socketServer = socketServer;
    }
}
