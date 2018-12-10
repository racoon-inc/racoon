package run.racoon.storage;

public interface Storage {
    void put(String key, Object value);
    <T> T get(String key);
    boolean hasKey(String key);
}
