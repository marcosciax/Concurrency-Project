package com.server;

import com.server.message.MessageHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class HandleClient extends Thread {
    PrintWriter out;
    BufferedReader in;
    int id;
    String username;



    public HandleClient(int id, PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;
        this.id = id;
    }

    public void sendMessage(String data)  {
        this.out.println(data);
    }

    public String receiveMessage() throws IOException {
        String line = this.in.readLine();
        return line;
    }

    @Override
    public void run() {
        MessageHandler messageHandler = new MessageHandler();

        while (true){
            try {
                String message = receiveMessage();
                System.out.println("from client " + id + ": " + message);
                String response = messageHandler.getResponse(message,id);
                System.out.println("send to client " + id + ": " + response);
                sendMessage(response);

            } catch (IOException e) {
                System.out.println("client " + id  + " error " + e);
                System.out.println("client " + id + " disconnected!");
                DataService.getInstance().getClients().remove(  DataService.getInstance().getClient(id) );
                break;
            }
        }
    }

   public int getClientId(){
        return id;
   }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
