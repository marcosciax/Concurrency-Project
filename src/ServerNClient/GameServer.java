package ServerNClient;

import Checkers.Models.BoardInfo;
import Packets.Packet;
import Packets.Packet00Login;
import account_management.DataHandle.AllData;
import account_management.Models.Account;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class GameServer extends Thread{
    private DatagramSocket socket;
    private ArrayList<Account> connectedAccounts = new ArrayList<>();

    public GameServer() throws SocketException {
        this.socket = new DatagramSocket(1441);
    }

    public void run() {
        while(true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data,data.length);
            try {
                socket.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            parsePacket(packet.getData(),packet.getAddress(),packet.getPort());
//            System.out.println("Client ["+packet.getAddress().getHostAddress() + "] : " + packet.getPort() + " > " + new String(packet.getData()));
//            String message = new String(packet.getData()).trim();
//            if(message.trim().equalsIgnoreCase("ping")){
//                try {
//                    sendData("Pong".getBytes(),packet.getAddress(),packet.getPort());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }

        }
    }

    public void parsePacket(byte[] data, InetAddress address, int port){
        String message = new String(data).trim();
        Packet.PacketTypes type = Packet.lookupPacket(message.substring(0,2));
        switch (type){
            case INVALID -> {

            }
            case LOGIN -> {
                Packet00Login packet = new Packet00Login(data);
                System.out.println("["+address.getHostAddress()+ ":" +port+" ] "+packet.getUserName()+" has connected");
                for(Account account : AllData.accounts){
                    if(account.getUserName().equals(packet.getUserName())) {
                        account.setIpAddress(address);
                        account.setPort(port);
                        this.connectedAccounts.add(account);
                        BoardInfo game = new BoardInfo();
                        game.setPlayerOne(account);
                        game.startGame();
                        break;
                    }
                }
            }
            case DISCONNECT -> {

            }
        }
    }

    public void sendData(byte[] data, InetAddress ipAddress , int port) throws IOException {
        DatagramPacket packet = new DatagramPacket(data,data.length,ipAddress,port);
        socket.send(packet);
    }

    public void sendDataToAllClients(byte[] data) throws IOException {
        for(Account account : connectedAccounts)
            sendData(data,account.getIpAddress(),account.getPort());
    }
}