package run.racoon.commons.network.packet;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public abstract class SendablePacket implements Packet {
    private final ByteArrayOutputStream byteArrayOutputStream;

    public SendablePacket() {
        byteArrayOutputStream = new ByteArrayOutputStream(getSize());
    }

    public void writeByte(byte value) {
        byteArrayOutputStream.write(value);
    }

    public void writeInt(int value) {
        byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();
        byteArrayOutputStream.writeBytes(bytes);
    }

    public void writeLong(long value) {
        byte[] bytes = ByteBuffer.allocate(8).putLong(value).array();
        byteArrayOutputStream.writeBytes(bytes);
    }

    public void writeDouble(double value) {
        byte[] bytes = ByteBuffer.allocate(8).putDouble(value).array();
        byteArrayOutputStream.writeBytes(bytes);
    }

    public void writeFloat(float value) {
        byte[] bytes = ByteBuffer.allocate(4).putFloat(value).array();
        byteArrayOutputStream.writeBytes(bytes);
    }

    public void writeString(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.UTF_8);
        writeInt(bytes.length);
        byteArrayOutputStream.writeBytes(bytes);
    }

    @Override
    public byte[] toBytes() {
        return byteArrayOutputStream.toByteArray();
    }

    public abstract int getSize();
}