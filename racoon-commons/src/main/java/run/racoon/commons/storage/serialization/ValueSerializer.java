package run.racoon.commons.storage.serialization;

public interface ValueSerializer {
    byte[] serialize(Object value);
}
