package com.client.threads;

import com.client.CDataService;
import com.client.Model.Chess;
import com.client.Model.Message;
import com.client.Model.TicTacToe;
import com.client.Model.TicTacToeRoom;
import com.client.controller.ChatBoxController;
import com.client.controller.ChessController;
import com.client.controller.MainController;
import com.client.NetworkService;
import com.client.controller.TicTacToeController;
import com.server.DataService;
import com.server.HandleClient;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WaitForMessageThread extends Thread {
    MainController mainController;

    HashMap<String,ChatBoxController> chatBoxMap;
    HashMap<String, TicTacToeController> ticTacMap;
    HashMap<String, ChessController> chessMap;

    public WaitForMessageThread(MainController mainController) {
        this.mainController = mainController;
        chatBoxMap = new HashMap<>();
        ticTacMap = new HashMap<>();
        chessMap = new HashMap<>();
    }

    @Override
    public void run() {

        while (true){
            String message = NetworkService.getInstance().receiveMessage();
            System.out.println("Server: " + message);

            String[] responses = message.split("=");
            if(responses.length < 2){
                continue;
            }
            String command = responses[0];
            String data = responses[1];

            if(command.equals("USERS")){
                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        mainController.fetchOnlineUsers(message);
                    }
                });
            }
            else if(command.equals("MESSAGE")){
                String[] messageData = data.split("-");
                Message mess = new Message(messageData[0],messageData[1],messageData[2]);

                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        String fromUser = messageData[0];
                        if( chatBoxMap.get(fromUser) != null ){
                            chatBoxMap.get(fromUser).gotNewMessage(mess);
                        }else{
                            List<Message> messageList = CDataService.getInstance().getMessagesMap().get(fromUser);
                            if(messageList == null){
                                messageList = new ArrayList<>();
                                CDataService.getInstance().getMessagesMap().put(fromUser,messageList);
                            }
                            messageList.add(mess);

                            mainController.openMessageWindow(fromUser);
                        }
                    }
                });
            }
            else if(command.equals("TICTACREQUEST")){
                String[] messageData = data.split("-");

                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        String fromUser = messageData[0];
                        mainController.ticTacToeRequest(fromUser);
                    }
                });

            }
            else if(command.equals("TICTACSTART")){
                String[] messageData = data.split("-");

                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        String vsUser = messageData[0];
                        int roomId = Integer.valueOf(messageData[1]);
                        String playFirst = messageData[2];
                        mainController.openTicTacWindow(vsUser,playFirst,roomId);
                    }
                });
            }
            else if(command.equals("TICTACPLAY")){
                String[] params = data.split("-");
                String fromUser = params[0];

                TicTacToeRoom room = DataService.getInstance().getTicTacToeRoom(Integer.valueOf(params[2]));
                int row = Integer.valueOf(params[3]);
                int col = Integer.valueOf(params[4]);
                char val = params[5].charAt(0);


                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        TicTacToeController controller = ticTacMap.get(fromUser);
                        if(controller != null){
                            controller.enemyHavePlay(row,col,val);
                        }
                    }
                });
            }
            else if(command.equals("TICTACLOADGAME")){
                String[] messageData = data.split("-");

                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        String vsUser = messageData[0];
                        int roomId = Integer.valueOf(messageData[1]);
                        String playFirst = messageData[2];
                        String ticTacData = messageData[3];
                        mainController.openTicTacWindow(vsUser,playFirst,roomId);
                        ticTacMap.get(vsUser).loadGame(new TicTacToe(ticTacData));
                    }
                });
            }

            /////////////////////////////chess
            else if(command.equals("CHESSREQUEST")){
                String[] messageData = data.split("-");

                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        String fromUser = messageData[0];
                        mainController.chessRequest(fromUser);
                    }
                });

            }
            else if(command.equals("CHESSSTART")){
                String[] messageData = data.split("-");

                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        String vsUser = messageData[0];
                        int roomId = Integer.valueOf(messageData[1]);
                        String playFirst = messageData[2];
                        mainController.openChessWindow(vsUser,playFirst,roomId);
                    }
                });
            }
            else if(command.equals("CHESSPLAY")){
                String[] params = data.split("-");
                String fromUser = params[0];

                //TicTacToeRoom room = DataService.getInstance().getTicTacToeRoom(Integer.valueOf(params[2]));
                int row = Integer.valueOf(params[3]);
                int col = Integer.valueOf(params[4]);
                int toRow = Integer.valueOf(params[5]);
                int toCol = Integer.valueOf(params[6]);


                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        ChessController controller = chessMap.get(fromUser);
                        if(controller != null){
                            controller.enemyHavePlay(row,col,toRow,toCol);
                        }
                    }
                });
            }
            else if(command.equals("CHESSLOADGAME")){
                String[] messageData = data.split("-");

                Platform.runLater(new Runnable(){
                    @Override
                    public void run() {
                        String vsUser = messageData[0];
                        int roomId = Integer.valueOf(messageData[1]);
                        String playFirst = messageData[2];
                        String waitFor = messageData[3];
                        String chessData = messageData[4];
                        mainController.openChessWindow(vsUser,playFirst,roomId);
                        chessMap.get(vsUser).loadGame(new Chess(chessData),playFirst,waitFor);
                    }
                });
            }
        }
    }

    public HashMap<String, ChatBoxController> getChatBoxMap() {
        return chatBoxMap;
    }

    public void setChatBoxMap(HashMap<String, ChatBoxController> chatBoxMap) {
        this.chatBoxMap = chatBoxMap;
    }

    public HashMap<String, TicTacToeController> getTicTacMap() {
        return ticTacMap;
    }

    public HashMap<String, ChessController> getChessMap() {
        return chessMap;
    }

    public void setTicTacMap(HashMap<String, TicTacToeController> ticTacMap) {
        this.ticTacMap = ticTacMap;
    }
}
