package run.racoon.storage.configuration;

import run.racoon.commons.storage.configuration.Configuration;

import java.util.List;
import java.util.Objects;

public class RacoonStorageConfiguration implements Configuration {
    public static final String NODES_PROPERTY = "nodes";
    public static final String NODE_IDENTIFIER = "identifier";

    private final List<DataNode> dataNodes;
    private final String nodeIdentifier;

    public RacoonStorageConfiguration(List<DataNode> dataNodes, String nodeIdentifier) {
        this.dataNodes = Objects.requireNonNull(dataNodes, "Data nodes cannot be null");
        this.nodeIdentifier = nodeIdentifier;
    }

    @Override
    public <T> T getByKey(String key) {
        switch (key) {
            case NODE_IDENTIFIER:
                return (T) nodeIdentifier;
            default:
                throw new NullPointerException("Key not found: " + key);
        }
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
