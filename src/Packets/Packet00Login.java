package Packets;

import ServerNClient.GameClient;
import ServerNClient.GameServer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Packet00Login extends Packet{

    private String userName;

    public Packet00Login(byte[] data) {
        super(00);
        this.userName = readData(data);
    }

    public Packet00Login(String userName) {
        super(00);
        this.userName = userName;
    }

    @Override
    public void writeData(GameClient client) throws IOException {
        System.out.println("in write data (client)");
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) throws IOException {
        server.sendDataToAllClients(getData());
    }

    @Override
    public byte[] getData() {
        return ("00" + this.userName).getBytes();
    }

    public String getUserName() {
        return userName;
    }
}
