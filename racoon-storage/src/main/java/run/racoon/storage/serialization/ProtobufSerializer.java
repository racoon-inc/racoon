package run.racoon.storage.serialization;

import run.racoon.commons.storage.serialization.ValueSerializer;

public class ProtobufSerializer implements ValueSerializer {
    @Override
    public byte[] serialize(Object value) {
        return new byte[0];
    }
}
