package run.racoon.network.packet.receivable;

import run.racoon.commons.network.packet.ReceivablePacket;

import java.io.IOException;

public class Row extends ReceivablePacket {
    private final byte partition;
    private final long offset;

    public Row(byte[] bytes) throws IOException {
        super(bytes);

        readByte(); //read id

        this.partition = readByte();
        this.offset = readLong();
    }

    @Override
    public byte getId() {
        return 0x01;
    }
}
