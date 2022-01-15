package com.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Server {

    ServerSocket serverSocket;
    Socket socket;

    PrintWriter out;
    BufferedReader in;

    List<HandleClient> clients = new ArrayList<>();

    public Server(int port) throws IOException, IOException {
        System.out.println("Wait For Connect");
        serverSocket = new ServerSocket(port);
        System.out.println(serverSocket.getInetAddress());
        this.serverSocket.setSoTimeout(0);
    }

    public Socket accept() throws IOException {
        return serverSocket.accept();
    }


    void start() throws IOException {
        int id = 0;

        while (true){
            socket = accept();
            System.out.println(socket);
            this.out = new PrintWriter(this.socket.getOutputStream(), true, StandardCharsets.UTF_8);
            this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), StandardCharsets.UTF_8));

            HandleClient client = new HandleClient(id,out,in);
            id++;
            client.start();
            clients.add(client);
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(8077);
        server.start();
    }

}
