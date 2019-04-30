package run.racoon.node.domain;

import java.util.List;

public class Pipeline {
    private final List<Step> steps;

    public Pipeline(List<Step> steps) {
        this.steps = steps;
    }

    public List<Step> getSteps() {
        return steps;
    }
}
