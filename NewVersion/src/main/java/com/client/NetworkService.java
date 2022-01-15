package com.client;

import com.server.DataService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class NetworkService {
    Socket socket;

    PrintWriter out;
    BufferedReader in;

    private static NetworkService _instance = null;

    public static NetworkService getInstance()
    {
        if (_instance == null)
            _instance = new NetworkService();

        return _instance;
    }

    NetworkService(){
        try {
            init(8077);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void init(int port) throws IOException {
        socket =  new Socket("localhost",port);
        this.out = new PrintWriter(this.socket.getOutputStream(), true, StandardCharsets.UTF_8);
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));
    }

    public void sendMessage(String data) {
        this.out.println(data);
    }

    public String receiveMessage() {
        String line = null;
        try {
            line = this.in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

}
