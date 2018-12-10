package run.racoon.storage.serialization;

public interface ValueSerializer {
    byte[] serialize(Object value);
}
