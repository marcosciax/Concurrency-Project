package ServerNClient;

import java.io.IOException;
import java.net.*;

public class GameClient extends Thread{
    private InetAddress ipAddress;
    private DatagramSocket socket;

    public GameClient(String ipAdress) throws UnknownHostException, SocketException {
        this.socket = new DatagramSocket();
        this.ipAddress=InetAddress.getByName(ipAdress);
    }

    public void run(){
        while(true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data,data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Server > " + new String(packet.getData()));

        }
    }

    public void sendData(byte[] data) throws IOException {
        DatagramPacket packet = new DatagramPacket(data,data.length,ipAddress,1331);
        socket.send(packet);
    }

}
