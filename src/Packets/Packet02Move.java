package Packets;

import ServerNClient.GameClient;
import ServerNClient.GameServer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Packet02Move extends Packet{

    private String userName;

    public Packet02Move(String data) {
        super(00);
        this.userName = readData(data);
    }


    @Override
    public void writeData(GameClient client) throws IOException {
        System.out.println("in write data (client)");
        client.sendData(getData());
    }

    @Override
    public void writeData(GameServer server) throws IOException {
        server.sendData(getData());
    }

    @Override
    public Object getData() {
        return ("02" + this.userName).getBytes();
    }

    public String getUserName() {
        return userName;
    }
}
