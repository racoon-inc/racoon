package run.racoon.network.packet.sendable;

import run.racoon.commons.network.packet.SendablePacket;

public class AckRow extends SendablePacket {
    public AckRow(byte partition, long offset, String data) {
        writeByte(getId());
        writeByte(partition);
        writeLong(offset);
        writeString(data);
        writeByte(END_SYMBOL);
    }

    @Override
    public byte getId() {
        return 0x01;
    }

    @Override
    public int getSize() {
        return 32;
    }
}
