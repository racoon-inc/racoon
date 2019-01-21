package run.racoon.network.packet.receivable;

import run.racoon.commons.network.packet.ReceivablePacket;

import java.io.IOException;

public class AckRow extends ReceivablePacket {
    private final byte partition;
    private final long offset;
    private final String data;

    public AckRow(byte[] bytes) throws IOException {
        super(bytes);

        readByte(); //read id

        this.partition = readByte();
        this.offset = readLong();
        this.data = readString();

        readByte(); //read end byte
    }

    @Override
    public byte getId() {
        return 0x01;
    }

    @Override
    public String toString() {
        return "AckRow{" +
                "partition=" + partition +
                ", offset=" + offset +
                ", data='" + data + '\'' +
                '}';
    }
}
