package ServerNClient;

import Checkers.Models.BoardInfo;
import Packets.Packet;
import Packets.Packet00Login;
import account_management.DataHandle.AllData;
import account_management.Models.Account;
import javafx.beans.binding.ObjectExpression;

import javax.management.monitor.Monitor;
import javax.xml.crypto.Data;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class GameServer extends Thread{
    private final ServerSocket socket;
    private Socket client;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public GameServer(int port) throws IOException {
        this.socket = new ServerSocket(port);
    }

    public void run() {

        while(true){

            if(client==null){
                try {
                    client = socket.accept();
                    outputStream = new ObjectOutputStream(client.getOutputStream());
                    inputStream = new ObjectInputStream(client.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    public void sendData(Object data) throws IOException {
        outputStream = new ObjectOutputStream(client.getOutputStream());
        outputStream.writeObject(data);
        outputStream.flush();
    }

    public Object readData() throws IOException, ClassNotFoundException {
        inputStream = new ObjectInputStream(client.getInputStream());
        return inputStream.readObject();
    }

    public Socket getClient() {
        return client;
    }
}