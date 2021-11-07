package ServerNClient;

import account_management.Controller.LoginController;

import java.io.*;
import java.net.*;

public class GameClient extends Thread {
    private Socket socket;

    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public GameClient(int port) throws IOException {
        InetAddress ip = InetAddress.getLocalHost();
        String hostname = ip.getHostName();
        System.out.println(LoginController.adressToCopy);
        this.socket = new Socket(LoginController.adressToCopy,port);
    }

    public void run() {
        while (true) {
            byte[] data = new byte[1024];

            if (inputStream == null) {
                try {
                    outputStream = new ObjectOutputStream(socket.getOutputStream());
                    inputStream = new ObjectInputStream(socket.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }

    public void sendData(Object data) throws IOException {
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream.writeObject(data);
    }

    public Object readData() throws IOException, ClassNotFoundException {
        inputStream = new ObjectInputStream(socket.getInputStream());
        return inputStream.readObject();
    }
}
