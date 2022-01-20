package com.server.message;

import com.client.Model.TicTacToeRoom;
import com.server.DataService;
import com.server.HandleClient;

public class MessageHandler {
    String username;

    public String getResponse(String request,int clientId){
        String response = "";
        String[] requests = request.split("=");
        String command = requests[0];
        String data = requests[1];


        if(command.equals("FETCHUSER")){ // fetch user

            response = "USERS=";

            for(HandleClient c : DataService.getInstance().getClients()){
                if(c.getClientId() != clientId){
                    response += c.getUsername() + "-";
                }
            }
        }
        else if(command.equals("LOGIN")){
            response = "LOGIN=OK";
            username = data;
            if(username.isEmpty()){
                response = "LOGIN=NG";
            }
            else{
                HandleClient client = DataService.getInstance().getClient(clientId);
                client.setUsername(username);
            }
        }
        else if(command.equals("MESSAGE")){

            String[] message = data.split("-");

//            HandleClient fromClient = DataService.getInstance().getClient(message[0]);
            HandleClient toClient = DataService.getInstance().getClient(message[1]);

            toClient.sendMessage(request);
            response = "STATUS=ok";
        }
        else if(command.equals("TICTACREQUEST")){
            String[] usersStr = data.split("-");
            HandleClient fromClient = DataService.getInstance().getClient(usersStr[0]);
            HandleClient toClient = DataService.getInstance().getClient(usersStr[1]);

            toClient.sendMessage(request);

            response = "STATUS=ok";
        }
        else if(command.equals("TICTACACCEPT")){
            String[] params = data.split("-");
            HandleClient fromClient = DataService.getInstance().getClient(params[0]);
            HandleClient toClient = DataService.getInstance().getClient(params[1]);

            TicTacToeRoom ticTacToeRoom = new TicTacToeRoom(fromClient.getUsername(),toClient.getUsername());
            String playFirst = ticTacToeRoom.getPlayFirst();

            DataService.getInstance().getTicTacToesRooms().add(ticTacToeRoom);

            fromClient.sendMessage("TICTACSTART="+toClient.getUsername()+"-"+ticTacToeRoom.getId()+"-" + playFirst);
            toClient.sendMessage("TICTACSTART="+fromClient.getUsername()+"-"+ticTacToeRoom.getId()+"-" + playFirst);


            response = "STATUS=ok";
        }
        else if(command.equals("TICTACPLAY")){
            String[] params = data.split("-");
            HandleClient fromClient = DataService.getInstance().getClient(params[0]);
            HandleClient toClient = DataService.getInstance().getClient(params[1]);

            TicTacToeRoom room = DataService.getInstance().getTicTacToeRoom(Integer.valueOf(params[2]));
            int row = Integer.valueOf(params[3]);
            int col = Integer.valueOf(params[4]);
            char val = params[5].charAt(0);

            // save data to room

            room.getTicTacToe().add(row,col,val);

            toClient.sendMessage(request);

            response = "STATUS=ok";
        }

        return response;
    }


}