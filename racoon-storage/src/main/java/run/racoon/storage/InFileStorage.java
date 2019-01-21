package run.racoon.storage;

import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;
import run.racoon.commons.storage.Storage;
import run.racoon.commons.storage.StorageProperties;

import java.io.File;
import java.io.IOException;

public class InFileStorage implements Storage {

    private ChronicleMap<String, Object> map;

    public void configure(StorageProperties config) throws IOException {
        String storagePath = config.get("path");
        var file = new File(storagePath);
        map = ChronicleMapBuilder.of(String.class, Object.class)
                .createPersistedTo(file);
    }

    @Override
    public void put(String key, Object value) {
        map.put(key, value);
    }

    @Override
    public <T> T get(String key) {
        return (T) map.get(key);
    }

    @Override
    public boolean hasKey(String key) {
        return map.containsKey(key);
    }

    @Override
    public void close() {
        this.map.close();
    }

}
