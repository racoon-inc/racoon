package run.racoon.node.configuration;

import com.beust.jcommander.Parameter;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import java.util.Collections;
import java.util.List;

@AutoProperty
public class CliConfiguration implements Configuration {

    @Parameter(names = "name", description = "Racoon name")
    private String name = "racoon";

    @Parameter(names = "port", description = "Node port")
    private int port = 9111;

    @Parameter(names = "peers", description = "members of racoon family (name:host:port)")
    private List<String> peers = Collections.emptyList();


    public CliConfiguration() {
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public List<String> getPeers() {
        return peers;
    }

    public void setPeers(List<String> peers) {
        this.peers = peers;
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }
}
