package run.racoon.commons.storage;

import java.util.Map;
import java.util.Objects;

public class StorageProperties {
    private final Map<String, String> properties;

    public StorageProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String get(String key) {
        return Objects.requireNonNull(properties.get(key), "Missing " + key + " config");
    }
}
