package run.racoon.node.configuration;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import run.racoon.commons.storage.Storage;

import javax.validation.constraints.NotBlank;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@AutoProperty
public class Configuration {

    @NotBlank
    @Config(name = "name", description = "Racoon name")
    private String name;

    @Config(name = "port", description = "Node port")
    private Integer port;

    @Config(name = "peers", description = "members of racoon family (name:host:port)")
    private List<String> peers = Collections.emptyList();

    @Config(name = "storage", description = "Storage configuration")
    private StorageConfiguration storageConfiguration;


    public Configuration() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public List<String> getPeers() {
        return peers;
    }

    public void setPeers(List<String> peers) {
        this.peers = peers;
    }

    public StorageConfiguration getStorageConfiguration() {
        return storageConfiguration;
    }

    public void setStorageConfiguration(StorageConfiguration storageConfiguration) {
        this.storageConfiguration = storageConfiguration;
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }

    @AutoProperty
    public static class StorageConfiguration {
        @Config(name = "class", description = "Storage class name")
        private Class<? extends Storage> engineClass;

        @Config(name = "parameters", description = "Storage engine specific parameters")
        private Map<String, String> parameters;

        public Class<? extends Storage> getEngineClass() {
            return engineClass;
        }

        public void setEngineClass(Class<? extends Storage> engineClass) {
            this.engineClass = engineClass;
        }

        public Map<String, String> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, String> parameters) {
            this.parameters = parameters;
        }

        @Override
        public String toString() {
            return Pojomatic.toString(this);
        }
    }
}
