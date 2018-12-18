package run.racoon.commons.storage.serialization;

public interface ValueDeserializer {
    <T> T deserialize(byte[] value);
}
