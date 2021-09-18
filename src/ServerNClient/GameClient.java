package ServerNClient;

import Checkers.Models.BoardInfo;
import Packets.Packet;
import Packets.Packet00Login;
import account_management.DataHandle.AllData;
import account_management.Models.Account;

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
//            System.out.println("Server > " + new String(packet.getData()));
            try {
                parsePacket(packet.getData(),packet.getAddress(),packet.getPort());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void parsePacket(byte[] data, InetAddress address, int port) throws IOException {
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(message.substring(0,2));
        switch (type){
            case INVALID -> {

            }
            case LOGIN -> {
                Packet00Login packet = new Packet00Login(data);
                System.out.println("["+address.getHostAddress()+ ":" +port+" ] "+packet.getUserName()+" has joined the Game");
                for(Account account : AllData.accounts){
                    if(account.getUserName().equals(packet.getUserName())) {
                        account.setIpAddress(address);
                        account.setPort(port);
//                        BoardInfo game = new BoardInfo();
//                        game.setPlayerTwo(account);
//                        game.startGame();
                        break;
                    }
                }
            }
            case DISCONNECT -> {

            }
        }

    }

    public void sendData(byte[] data) throws IOException {
        DatagramPacket packet = new DatagramPacket(data,data.length,ipAddress,1441);
        socket.send(packet);
        return;
    }

}
