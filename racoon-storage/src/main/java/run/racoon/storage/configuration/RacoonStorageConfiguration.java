package run.racoon.storage.configuration;

import java.util.List;
import java.util.Objects;

public class RacoonStorageConfiguration implements Configuration {
    public static final String NODES_PROPERTY = "nodes";

    private final List<DataNode> dataNodes;

    public RacoonStorageConfiguration(List<DataNode> dataNodes) {
        this.dataNodes = Objects.requireNonNull(dataNodes, "Data nodes cannot be null");
    }

    @Override
    public <T> T getByKey(String key) {
        throw new NullPointerException("Key not found: " + key);
    }

    @Override
    public <T> List<T> getListByKey(String key) {
        if (NODES_PROPERTY.equals(key)) {
            return (List<T>) dataNodes;
        } else {
            throw new NullPointerException("Key not found: " + key);
        }
    }


    public static class DataNode {

    }
}
