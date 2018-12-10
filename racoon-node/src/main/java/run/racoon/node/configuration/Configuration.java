package run.racoon.node.configuration;

import java.util.List;

public interface Configuration {
    String getName();

    int getPort();

    List<String> getPeers();
}
