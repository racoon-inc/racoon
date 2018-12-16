package run.racoon.storage.serialization;

public interface ValueDeserializer {
    <T> T deserialize(byte[] value);
}
