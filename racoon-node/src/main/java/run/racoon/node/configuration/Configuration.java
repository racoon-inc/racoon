package run.racoon.node.configuration;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.util.Collections;
import java.util.List;

@AutoProperty
public class Configuration {

    @Config(name = "name", description = "Racoon name")
    private String name;

    @Config(name = "port", description = "Node port")
    private Integer port;

    @Config(name = "peers", description = "members of racoon family (name:host:port)")
    private List<String> peers = Collections.emptyList();

    @Config(name = "storage", description = "Storage configuration")
    private Storage storage;


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

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }

    @AutoProperty
    public static class Storage {
        @Config(name = "path", description = "path to storage dir")
        private String path;

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        @Override
        public String toString() {
            return Pojomatic.toString(this);
        }
    }
}
