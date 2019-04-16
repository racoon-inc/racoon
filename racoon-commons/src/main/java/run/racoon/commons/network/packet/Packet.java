package run.racoon.commons.network.packet;

public interface Packet {
    byte END_SYMBOL = 0x00;

    byte getId();

    byte[] toBytes();
}
