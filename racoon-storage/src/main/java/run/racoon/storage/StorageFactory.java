package run.racoon.storage;

import run.racoon.commons.storage.Storage;
import run.racoon.commons.storage.StorageProperties;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Map;

public class StorageFactory {
    private StorageFactory() {
    }

    public static Storage getStorage(Class<? extends Storage> className, Map<String, String> parameters) throws IOException {
        Storage storage;
        try {
            Constructor<? extends Storage> constructor = className.getConstructor();
            storage = constructor.newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed creating storage: ", e);
        }

        storage.configure(new StorageProperties(parameters));
        return storage;
    }
}
