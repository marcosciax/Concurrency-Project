package Packets;

import ServerNClient.GameClient;
import ServerNClient.GameServer;

public abstract class Packet {

    private byte packetId;

    public static enum PacketTypes{
        INVALID(-1), LOGIN(00), DISCONNECT(01);

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

    public abstract void writeData(GameClient client);
    public abstract void writeData(GameServer server);

    public String readData(byte[] data){
        String message = new String(data).trim();
        return message.substring(2);
    }

    public static PacketTypes lookupPacket(int id){
        for(PacketTypes packetType : PacketTypes.values())
            if(packetType.getPacketId()==id)
                return packetType;

        return PacketTypes.INVALID;
    }

    public byte getPacketId() {
        return packetId;
    }

    public void setPacketId(byte packetId) {
        this.packetId = packetId;
    }
}
