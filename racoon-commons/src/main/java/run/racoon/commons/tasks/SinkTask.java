package run.racoon.commons.tasks;

import run.racoon.commons.domain.Record;
import run.racoon.commons.domain.SinkState;

import java.util.List;

public interface SinkTask<T extends SinkState> extends AutoCloseable {
    void configure(T state);
    void push(List<Record> records);
}
