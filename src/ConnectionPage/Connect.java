package ConnectionPage;

import Checkers.Models.BoardInfo;
import ChessGame.template.ChessBoard;
import ChessGame.template.CustomControl;
import ServerNClient.GameClient;
import ServerNClient.GameServer;
import TicTacToe.GameInfo;
import account_management.DataHandle.AllData;
import account_management.Models.Account;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.BindException;
import java.net.PasswordAuthentication;
import java.sql.SQLException;
import java.util.Properties;

public class Connect {

    public static Account player;
    private static GameClient socketClient;
    private static GameServer socketServer;
    public static int port;
    private boolean firstTime=true;

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
            GameInfo.setSocketClient(socketClient);
            game.setPlayerTwo(playerTwo);
            Account finalPlayerTwo = playerTwo;
            Runnable t = () -> {
                while (true) {
                    try {
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
            GameInfo.setSocketServer(socketServer);
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

    public void chess(ActionEvent actionEvent) throws SQLException, IOException {
        TextInputDialog td = new TextInputDialog();
        td.showAndWait();

        int port = Integer.parseInt(td.getEditor().getText());
        boolean p = false;

        GameServer socketServer = null;
        GameClient socketClient;

        Account playerOne = null;
        Account playerTwo = null;

        for(Account account: AllData.accounts) {
            if (account.getUserName().equals("abdul"))
                playerOne = account;
            else if (account.getUserName().equals("hello"))
                playerTwo = account;
        }

        try{
            ChessBoard.playerOne= Connect.player;
            socketServer=new GameServer(port);
        }catch (BindException e){
            p=true;
            ChessBoard.playerTwo=Connect.player;
        }

        if(p){
            socketClient = new GameClient(port);
            socketClient.start();
            ChessBoard.gameClient = socketClient;
            ChessBoard.playerTwo=playerTwo;
            Account finalPlayerTwo = playerTwo;
            Runnable t = () -> {
                while (true) {
                    try {
                        socketClient.sendData(finalPlayerTwo);
                        ChessBoard.playerOne = (Account) socketClient.readData();
                        break;
                    } catch (IOException | ClassNotFoundException k) {
                        k.printStackTrace();
                    }
                }
            };
            t.run();
        }

        if(!p) {
            ChessBoard.playerOne=playerOne;
            socketServer.start();
            ChessBoard.gameServer=socketServer;
            Account finalPlayerOne = playerOne;
            GameServer finalSocketServer = socketServer;
            Runnable t = () -> {
                while (true) {
                    try {
                        if(finalSocketServer.getClient()!=null) {
                            System.out.println("in here");
                            finalSocketServer.sendData(finalPlayerOne);
                            ChessBoard.playerTwo = (Account) finalSocketServer.readData();
                            break;
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.run();
        }


        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                StackPane sp_mainlayout = new StackPane();
                CustomControl cc_custom = new CustomControl();
                sp_mainlayout.getChildren().add(cc_custom);

                Stage primaryStage = new Stage();
                primaryStage.setTitle("Chess game");
                primaryStage.setScene(new Scene(sp_mainlayout, 600, 700));
                primaryStage.setMinWidth(300);
                primaryStage.setMinHeight(300);
                primaryStage.show();
            }
        });
    }

    public void report(ActionEvent actionEvent) {
        final String username = "abdulmannankhan1000@gmail.com";
        final String password = "03105784747";

        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        javax.mail.PasswordAuthentication passwordAuthentication = new javax.mail.PasswordAuthentication(username, password);
                        return passwordAuthentication;
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("abdulmannankhan1000@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("mannanbadakhan@domain.com"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler,"
                    + "\n\n No spam to my email, please!");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    public static GameClient getSocketClient() {
        return socketClient;
    }

    public static GameServer getSocketServer() {
        return socketServer;
    }
}
