package run.racoon.commons.network.packet;

public abstract class Packet {
    public static final byte END_SYMBOL = 0x00;

    public abstract byte getId();

    public abstract byte[] toBytes();
}
