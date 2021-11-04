package ServerNClient;

import java.io.*;
import java.net.*;

public class GameServer extends Thread{
    private final ServerSocket socket;
    private Socket client;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public GameServer(int port) throws IOException {
        this.socket = new ServerSocket();
        socket.bind(new InetSocketAddress("0.0.0.0", port));
        System.out.println(socket.getInetAddress());
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