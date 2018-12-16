package run.racoon.commons.storage;

public interface Storage extends AutoCloseable {
    void put(String key, Object value);
    <T> T get(String key);
    boolean hasKey(String key);
}
