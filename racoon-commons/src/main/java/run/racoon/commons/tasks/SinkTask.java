package run.racoon.commons.tasks;

import run.racoon.commons.domain.Record;
import run.racoon.commons.domain.SinkState;
import run.racoon.commons.handlers.ErrorHandler;

import java.util.List;

public interface SinkTask<T extends SinkState> extends AutoCloseable {
    void start(T state, ErrorHandler errorHandler);
    void push(List<Record> records);
}
