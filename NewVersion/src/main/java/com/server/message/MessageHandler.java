package com.server.message;

import com.client.Model.ChessRoom;
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

            TicTacToeRoom playingRoom = DataService.getInstance().getTicTacToePlayingRoom(fromClient.getUsername(),toClient.getUsername());

            for (TicTacToeRoom r : DataService.getInstance().getTicTacToesRooms()){
                System.out.println(r);
            }

            // find no playing room
            if(playingRoom == null){
                toClient.sendMessage(request);
                response = "STATUS=ok";
            }else{
                // continue playing room
                response = String.format("TICTACLOADGAME="+
                          playingRoom.getEnemy(fromClient.getUsername()) +"-"+ playingRoom.getId()+"-" +
                           playingRoom.getPlayFirst()+"-"+playingRoom.getTicTacToe().getStrs() );
            }

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
            if(room.getTicTacToe().findWinner() != 'P'){
                room.setStatus("ENDED");
            }

            toClient.sendMessage(request);

            response = "STATUS=ok";
        }



        //////////////////////// chess
        else if(command.equals("CHESSREQUEST")){
            String[] usersStr = data.split("-");
            HandleClient fromClient = DataService.getInstance().getClient(usersStr[0]);
            HandleClient toClient = DataService.getInstance().getClient(usersStr[1]);

            ChessRoom playingRoom = DataService.getInstance().getTChessPlayingRoom(fromClient.getUsername(),toClient.getUsername());

            // find no playing room
            if(playingRoom == null){
                toClient.sendMessage(request);
                response = "STATUS=ok";
            }else{
               //  continue playing room
                response = String.format("CHESSLOADGAME="+
                        playingRoom.getEnemy(fromClient.getUsername()) +"-"+ playingRoom.getId()+"-" +
                        playingRoom.getPlayFirst()+"-"+ playingRoom.getWaitFor() +"-"+playingRoom.getChess().getStrs() );
            }

        }
        else if(command.equals("CHESSACCEPT")){
            String[] params = data.split("-");
            HandleClient fromClient = DataService.getInstance().getClient(params[0]);
            HandleClient toClient = DataService.getInstance().getClient(params[1]);

            ChessRoom chessRoom = new ChessRoom(fromClient.getUsername(),toClient.getUsername());
            String playFirst = chessRoom.getPlayFirst();

            DataService.getInstance().getChessRooms().add(chessRoom);

            fromClient.sendMessage("CHESSSTART="+toClient.getUsername()+"-"+chessRoom.getId()+"-" + playFirst);
            toClient.sendMessage("CHESSSTART="+fromClient.getUsername()+"-"+chessRoom.getId()+"-" + playFirst);


            response = "STATUS=ok";
        }
        else if(command.equals("CHESSPLAY")){
            String[] params = data.split("-");
            HandleClient fromClient = DataService.getInstance().getClient(params[0]);
            HandleClient toClient = DataService.getInstance().getClient(params[1]);

            ChessRoom room = DataService.getInstance().getChessRoom(Integer.valueOf(params[2]));
            int row = Integer.valueOf(params[3]);
            int col = Integer.valueOf(params[4]);
            int toRow = Integer.valueOf(params[5]);
            int toCol = Integer.valueOf(params[6]);

            // save data to room
            room.setWaitFor(toClient.getUsername());
            room.getChess().move(row,col,toRow,toCol);
            if(room.getChess().findWinner() != 'D'){
                room.setStatus("ENDED");
            }

            toClient.sendMessage(request);

            response = "STATUS=ok";
        }

        return response;
    }


}
