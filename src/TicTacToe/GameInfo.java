package TicTacToe;

import Checkers.Controller.BoardController;
import ServerNClient.GameClient;
import ServerNClient.GameServer;
import TicTacToe.Controllers.GameController;
import account_management.Models.Account;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

public class GameInfo {

    private int boardId;
    private static Account playerOne;
    private static Account playerTwo;
    private static GameServer socketServer;
    private static GameClient socketClient;

    public GameInfo(){
        setBoardId();
    }

    public static Account getPlayerOne() {
        return playerOne;
    }

    public static Account getPlayerTwo() {
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
        GameController.playerTwo=playerTwo;
    }

    public void setPlayerOne(Account playerOne) {
        this.playerOne = playerOne;
        GameController.playerOne=playerOne;
    }

    public void startGame(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/TicTacToe/Interface/gameWindow.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setTitle("Tic Tac Toe");
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
        GameInfo.socketClient = socketClient;
    }

    public static void setSocketServer(GameServer socketServer) {
        GameInfo.socketServer = socketServer;
    }
}
