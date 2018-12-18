package run.racoon.storage;

import net.openhft.chronicle.hash.serialization.BytesReader;
import net.openhft.chronicle.hash.serialization.BytesWriter;
import net.openhft.chronicle.map.ChronicleMap;
import net.openhft.chronicle.map.ChronicleMapBuilder;
import run.racoon.commons.storage.Storage;
import run.racoon.commons.storage.configuration.Configuration;
import run.racoon.commons.storage.serialization.ValueDeserializer;
import run.racoon.commons.storage.serialization.ValueSerializer;

class RacoonStorage implements Storage {

    private final ChronicleMap<String, Object> map;

    public RacoonStorage(ValueSerializer serializer,
                         ValueDeserializer deserializer,
                         Configuration config) {

        // TODO: - Implement replication on config.nodes (only available on ChronicleMap enterprise)
        //       - configure in file storage
        map = ChronicleMapBuilder.of(String.class, Object.class)
                .valueMarshallers(bytesReader(deserializer), bytesWriter(serializer))
                .create();
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

    private BytesReader<Object> bytesReader(ValueDeserializer deserializer) {
        return (in, using) -> deserializer.deserialize(in.toByteArray());
    }

    private BytesWriter<Object> bytesWriter(ValueSerializer serializer) {
        return (out, toWrite) -> out.write(serializer.serialize(toWrite));
    }

}
