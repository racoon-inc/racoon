package run.racoon.node.handlers;

import run.racoon.commons.storage.Storage;
import run.racoon.node.domain.Event;

public class SourceRegistrationHandler implements EventHandler {
    private final Storage storage;

    public SourceRegistrationHandler(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean applicableFor(Event event) {
        return false;
    }

    @Override
    public void handle(Event event) {

    }
}
