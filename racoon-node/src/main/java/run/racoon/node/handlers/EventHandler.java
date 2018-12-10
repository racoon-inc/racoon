package run.racoon.node.handlers;

import run.racoon.node.domain.Event;

public interface EventHandler {
    boolean applicableFor(Event event);
    void handle(Event event);
}
