package ConnectionPage;

import ChatSystem.ChatController;
import Checkers.Models.BoardInfo;
import ChessGame.template.ChessApplication;
import ChessGame.template.ChessBoard;
import ChessGame.template.CustomControl;
import ServerNClient.GameClient;
import ServerNClient.GameServer;
import TicTacToe.GameInfo;
import account_management.DataHandle.AllData;
import account_management.Models.Account;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.BindException;
import java.sql.SQLException;
import java.util.Random;

public class Connect {

    public static Account player;
    public static Account playerOne;
    public static Account playerTwo;
    GameClient socketClient;
    GameServer socketServer;
    GameClient chatClient;
    GameServer chatServer;
    GameServer CheckersServer;
    GameClient CheckersClient;
    GameServer ChessServer;
    GameClient ChessClient;
    GameServer ticTacToeServer;
    GameClient ticTacToeClient;
    BoardInfo game = new BoardInfo();
    GameInfo ticTacToeGame = new GameInfo();

    public Connect() throws IOException {
        int port = 7777;
        int chatPort = 9001;
        int chessPort = 2000;
        int checkersPort = 2001;
        int ticTacToePort = 2002;

        socketServer = null;
        boolean p =false;

        Account playerOne = null;
        Account playerTwo = null;

        try{
            socketServer=new GameServer(port);
            playerOne=player;
        }catch (BindException e){
            p=true;
            playerTwo=player;
        }

        if(p){
            socketClient = new GameClient(port);
            socketClient.start();
            Connect.playerTwo=playerTwo;
            Account finalPlayerTwo = playerTwo;
            Runnable t = () -> {
                while (true) {
                    try {
                        socketClient.sendData(finalPlayerTwo);
                        Connect.playerOne = ((Account) socketClient.readData());
                        break;
                    } catch (IOException | ClassNotFoundException k) {
                        k.printStackTrace();
                    }
                }
            };
            t.run();
        }

        if(!p) {
            Connect.playerOne = (playerOne);
            socketServer.start();
            Account finalPlayerOne = playerOne;
            GameServer finalSocketServer = socketServer;
            Runnable t = () -> {
                while (true) {
                    try {
                        if(finalSocketServer.getClient()!=null) {
                            finalSocketServer.sendData(finalPlayerOne);
                            Connect.playerTwo = ((Account) finalSocketServer.readData());
                            break;
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.run();
        }


        if(!p){
            chatServer=new GameServer(chatPort);
            System.out.println("Server Here");
            chatServer.start();
        }
        if (p){
            chatClient=new GameClient(chatPort);
            System.out.println("Client Here");
            chatClient.start();
        }

        ChatController.setClient(chatClient);
        ChatController.setServer(chatServer);
        ChatController.setPlayerOne(Connect.playerOne);
        ChatController.setPlayerTwo(Connect.playerTwo);

        if(!p){
            CheckersServer=new GameServer(checkersPort);
            CheckersServer.start();
        }
        if(p){
            CheckersClient=new GameClient(checkersPort);
            CheckersClient.start();
        }

        BoardInfo.setSocketClient(CheckersClient);
        BoardInfo.setSocketServer(CheckersServer);
        game.setPlayerTwo(Connect.playerTwo);
        game.setPlayerOne(Connect.playerOne);

        if(!p){
            ChessServer=new GameServer(chessPort);
            ChessServer.start();
        }
        if(p){
            ChessClient=new GameClient(chessPort);
            ChessClient.start();
        }

        ChessBoard.playerOne= Connect.playerOne;
        ChessBoard.playerTwo=Connect.playerTwo;
        ChessBoard.gameClient = ChessClient;
        ChessBoard.gameServer=ChessServer;

        if(!p){
            ticTacToeServer=new GameServer(ticTacToePort);
            ticTacToeServer.start();
        }
        if(p){
            ticTacToeClient=new GameClient(ticTacToePort);
            ticTacToeClient.start();
        }

        GameInfo.setSocketServer(ticTacToeServer);
        GameInfo.setSocketClient(ticTacToeClient);
        ticTacToeGame.setPlayerOne(Connect.playerOne);
        ticTacToeGame.setPlayerTwo(Connect.playerTwo);
    }

    public void checkers(ActionEvent actionEvent) throws IOException {
        game.startGame();
    }

    public void ttt(ActionEvent actionEvent) throws IOException {
        ticTacToeGame.startGame();
    }

    public void chess(ActionEvent actionEvent) throws SQLException, IOException {
        Platform.runLater(() -> {
            StackPane sp_mainlayout = new StackPane();
            CustomControl cc_custom = new CustomControl();
            sp_mainlayout.getChildren().add(cc_custom);

            Stage primaryStage = new Stage();
            primaryStage.setTitle("Chess game");
            primaryStage.setScene(new Scene(sp_mainlayout, 600, 700));
            primaryStage.setMinWidth(300);
            primaryStage.setMinHeight(300);
            primaryStage.show();
        });

    }

    public void chat(ActionEvent actionEvent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/ChatSystem/ChatWindow.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Stage stage = new Stage();
                stage.setTitle("Chat : " + player.getUserName());
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        });
    }
}
