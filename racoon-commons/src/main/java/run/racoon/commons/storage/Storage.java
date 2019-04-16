package run.racoon.commons.storage;

import java.io.IOException;
import java.util.Map;

public interface Storage extends AutoCloseable {
    void configure(StorageProperties parameters) throws IOException;
    void put(String key, Object value);
    <T> T get(String key);
    boolean hasKey(String key);
}
