package run.racoon.commons.network.packet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public abstract class ReceivablePacket extends Packet {
    private final byte[] bytes;
    private final ByteArrayInputStream byteArrayInputStream;

    public ReceivablePacket(byte[] bytes) {
        this.bytes = bytes;
        this.byteArrayInputStream = new ByteArrayInputStream(bytes);
    }

    public byte readByte() {
        return (byte) byteArrayInputStream.read();
    }

    public int readInt() throws IOException {
        ByteBuffer wrapped = ByteBuffer.wrap(byteArrayInputStream.readNBytes(4));
        return wrapped.getInt();
    }

    public long readLong() throws IOException {
        ByteBuffer wrapped = ByteBuffer.wrap(byteArrayInputStream.readNBytes(8));
        return wrapped.getLong();
    }

    public double readDouble() throws IOException {
        ByteBuffer wrapped = ByteBuffer.wrap(byteArrayInputStream.readNBytes(8));
        return wrapped.getDouble();
    }

    public float readFloat() throws IOException {
        ByteBuffer wrapped = ByteBuffer.wrap(byteArrayInputStream.readNBytes(4));
        return wrapped.getFloat();
    }

    public String readString() throws IOException {
        byte[] bytes = byteArrayInputStream.readNBytes(readInt());
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public byte[] toBytes() {
        return bytes;
    }
}
