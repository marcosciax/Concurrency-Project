package ConnectionPage;

import Checkers.Models.BoardInfo;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.BindException;

public class Connect {

    public static Account player;
    private static GameClient socketClient;
    private static GameServer socketServer;
    public static int port;

    public void checkers(ActionEvent actionEvent) throws IOException {

        TextInputDialog td = new TextInputDialog();
        td.showAndWait();

        port = Integer.parseInt(td.getEditor().getText());

        boolean p = false;

        BoardInfo game = new BoardInfo();

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
            BoardInfo.setSocketClient(socketClient);
            game.setPlayerTwo(playerTwo);
            Account finalPlayerTwo = playerTwo;
            Runnable t = () -> {
                while (true) {
                    try {
                        socketClient.sendData(finalPlayerTwo);
                        game.setPlayerOne((Account) socketClient.   readData());
                        break;
                    } catch (IOException | ClassNotFoundException k) {
                        k.printStackTrace();
                    }
                }
            };
            t.run();
        }

        if(!p) {
            game.setPlayerOne(playerOne);
            socketServer.start();
            BoardInfo.setSocketServer(socketServer);
            Account finalPlayerOne = playerOne;
            Runnable t = () -> {
                while (true) {
                    try {
                        if(socketServer.getClient()!=null) {
                            socketServer.sendData(finalPlayerOne);
                            game.setPlayerTwo((Account) socketServer.readData());
                            break;
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.run();
        }

        game.startGame();
    }

    public void ttt(ActionEvent actionEvent) throws IOException {
        TextInputDialog td = new TextInputDialog();
        td.showAndWait();

        port = Integer.parseInt(td.getEditor().getText());

        boolean p = false;

//        BoardInfo game = new BoardInfo();
        GameInfo game = new GameInfo();

        Account playerOne = null;
        Account playerTwo = null;

        try{
            playerOne=player;
            socketServer=new GameServer(port);
        }catch (BindException e){
            p=true;
            playerTwo=player;
        }

        if(p){
            socketClient = new GameClient(port);
            socketClient.start();
//            BoardInfo.setSocketClient(socketClient);
            GameInfo.setSocketClient(socketClient);
//            System.out.println(socketClient.readData());
            game.setPlayerTwo(playerTwo);
            Account finalPlayerTwo = playerTwo;
            Runnable t = () -> {
                while (true) {
                    try {
//                          socketServer.sendData("Hello");
                        socketClient.sendData(finalPlayerTwo);
                        game.setPlayerOne((Account) socketClient.readData());
                        break;
                    } catch (IOException | ClassNotFoundException k) {
                        k.printStackTrace();
                    }
                }
            };
            t.run();
        }

        if(!p) {
            game.setPlayerOne(playerOne);
            socketServer.start();
//            BoardInfo.setSocketServer(socketServer);
            GameInfo.setSocketServer(socketServer);
            Account finalPlayerOne = playerOne;
            Runnable t = () -> {
                while (true) {
                    try {
                        if(socketServer.getClient()!=null) {
                            System.out.println("in here");
                            socketServer.sendData(finalPlayerOne);
                            game.setPlayerTwo((Account) socketServer.readData());
//                            socketServer.sendData("Hello");
                            break;
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.run();
        }

        game.startGame();
    }

    public void chess(ActionEvent actionEvent) {

    }

    public void report(ActionEvent actionEvent) {

    }


    public static GameClient getSocketClient() {
        return socketClient;
    }

    public static GameServer getSocketServer() {
        return socketServer;
    }
}
