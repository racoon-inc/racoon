package run.racoon.storage;

import run.racoon.storage.configuration.Configuration;
import run.racoon.storage.serialization.ValueDeserializer;
import run.racoon.storage.serialization.ValueSerializer;

class RacoonStorage implements Storage {

    private final ValueSerializer serializer;
    private final ValueDeserializer deserializer;

    public RacoonStorage(ValueSerializer serializer,
                         ValueDeserializer deserializer, Configuration storageConfiguration) {
        this.serializer = serializer;
        this.deserializer = deserializer;
    }

    @Override
    public void put(String key, Object value) {
        var serializedValue = serializer.serialize(value);
        // TODO: write to file?
    }

    @Override
    public <T> T get(String key) {
        var serializedValue = new byte[]{}; // TODO: read from file?
        return deserializer.deserialize(serializedValue);
    }

    @Override
    public boolean hasKey(String key) {
        // TODO: check file?
        return false;
    }
}
