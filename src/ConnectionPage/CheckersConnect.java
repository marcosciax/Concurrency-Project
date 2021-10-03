package ConnectionPage;

import Checkers.Models.BoardInfo;
import ServerNClient.GameClient;
import ServerNClient.GameServer;
import account_management.DataHandle.AllData;
import account_management.Models.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.BindException;

public class CheckersConnect {

    @FXML Label userName;
    @FXML Label gameInfo;
    @FXML Button join;
    @FXML Button make;

    public static Account player;
    public static GameClient socketClient;
    public static GameServer socketServer;

//    private Label[]

    public void initialize(){

    }

    public void join(ActionEvent actionEvent) throws IOException {
        boolean p = false;

        BoardInfo game = new BoardInfo();

        Account playerOne = null;
        Account playerTwo = player;

        for(Account account: AllData.accounts) {
            if (account.getUserName().equals("abdul"))
                playerOne = account;
            else if (account.getUserName().equals("hello"))
                playerTwo = account;
        }

        try{
            socketServer=new GameServer(8000);
        }catch (BindException e){
            p=true;
        }

        if(p){
            socketClient = new GameClient(8000);
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

    /*
    boolean p = false;

        BoardInfo game = new BoardInfo();

        Account playerOne = null;
        Account playerTwo = null;

        for(Account account: AllData.accounts) {
            if (account.getUserName().equals("abdul"))
                playerOne = account;
            else if (account.getUserName().equals("hello"))
                playerTwo = account;
        }

        try{
            socketServer=new GameServer(8000);
        }catch (BindException e){
            p=true;
        }

        if(p){
            socketClient = new GameClient(8000);
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
     */

    public void make(ActionEvent actionEvent) {
    }
}
