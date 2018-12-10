package run.racoon.node;

import run.racoon.node.domain.Event;
import run.racoon.node.handlers.EventHandler;

import java.util.List;

class Listener {
    private final int port;
    private final List<EventHandler> handlers;

    public Listener(int port, List<EventHandler> handlers) {
        this.port = port;
        this.handlers = handlers;
    }

    public void start() {
        // TODO: start listener on port `port`?
        // TODO: infinite loop read events
        Event event = null;
        handlers.stream()
                .filter(h -> h.applicableFor(event))
                .forEach(h -> h.handle(event));
    }

}
