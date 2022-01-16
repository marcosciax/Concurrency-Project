package com.server.message;

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
            response = "RECIEVEMESSAGE=ok";
        }

        return response;
    }


}
