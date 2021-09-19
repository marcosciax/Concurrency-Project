package Packets;

import ServerNClient.GameClient;
import ServerNClient.GameServer;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Pack;

import java.io.IOException;
import java.io.ObjectOutputStream;

public abstract class Packet {

    private byte packetId;

    public static enum PacketTypes{
        INVALID(-1), LOGIN(00), DISCONNECT(01), MOVE(02);

        private int packetId;
        private PacketTypes(int packetId){
            this.packetId=packetId;
        }

        public int getPacketId() {
            return packetId;
        }
    }

    public Packet(int packetId){
        this.packetId=(byte)packetId;
    }

    public abstract void writeData(GameClient client) throws IOException;
    public abstract void writeData(GameServer server) throws IOException;

    public String readData(String data){
        String message = new String(data).trim();
        return data.substring(2);
    }

    public static PacketTypes lookupPacket(String packetId){
        try{
            return lookupPacket(Integer.parseInt(packetId));
        }catch (NumberFormatException e){
            return PacketTypes.INVALID;
        }
    }
    public static PacketTypes lookupPacket(int id){
        for(PacketTypes packetType : PacketTypes.values())
            if(packetType.getPacketId()==id)
                return packetType;

        return PacketTypes.INVALID;
    }

    public abstract Object getData();

    public byte getPacketId() {
        return packetId;
    }

    public void setPacketId(byte packetId) {
        this.packetId = packetId;
    }
}
