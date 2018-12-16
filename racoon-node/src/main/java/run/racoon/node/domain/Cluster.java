package run.racoon.node.domain;

import java.util.List;

public class Cluster {
    private final List<Node> nodes;

    public Cluster(List<Node> nodes) {
        this.nodes = nodes;
    }
}
