package run.racoon.storage;

import run.racoon.commons.storage.Storage;
import run.racoon.commons.storage.configuration.Configuration;
import run.racoon.storage.serialization.ProtobufDeserializer;
import run.racoon.storage.serialization.ProtobufSerializer;

public class StorageFactory {
    private StorageFactory() {
    }

    public static Storage getStorage(Configuration storageConfiguration) {
        var serializer = new ProtobufSerializer();
        var deserializer = new ProtobufDeserializer();

        return new RacoonStorage(serializer, deserializer, storageConfiguration);
    }
}
