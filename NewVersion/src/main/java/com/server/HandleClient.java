package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class HandleClient extends Thread {
    PrintWriter out;
    BufferedReader in;
    int id;

    public HandleClient(int id,PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;
        this.id = id;
    }

    public void sendMessage(String data) throws IOException {
        this.out.println(data);
    }

    public String receiveMessage() throws IOException {
        String line = this.in.readLine();
        return line;
    }

    @Override
    public void run() {
        while (true){
            try {
                String message = receiveMessage();
                System.out.println("from client: " + message);
                sendMessage("Oke!");

            } catch (IOException e) {
                System.out.println("client " + id  + " error " + e);
                break;
            }
        }
    }
}
