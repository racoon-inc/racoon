package run.racoon.node.handlers;

import run.racoon.node.domain.Event;
import run.racoon.storage.Storage;

public class StartPipelineHandler implements EventHandler{
    private final Storage storage;

    public StartPipelineHandler(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean applicableFor(Event event) {
        return false;
    }

    @Override
    public void handle(Event event) {
        // TODO: start socket pipeline?
    }
}
