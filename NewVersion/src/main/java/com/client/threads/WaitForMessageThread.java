package com.client.threads;

import com.client.CDataService;
import com.client.Model.Message;
import com.client.Model.TicTacToeRoom;
import com.client.controller.ChatBoxController;
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

    public WaitForMessageThread(MainController mainController) {
        this.mainController = mainController;
        chatBoxMap = new HashMap<>();
        ticTacMap = new HashMap<>();
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
                        controller.enemyHavePlay(row,col,val);
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

    public void setTicTacMap(HashMap<String, TicTacToeController> ticTacMap) {
        this.ticTacMap = ticTacMap;
    }
}
