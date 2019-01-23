package run.racoon.commons.tasks;

import run.racoon.commons.domain.Record;
import run.racoon.commons.domain.SourceState;
import run.racoon.commons.handlers.ErrorHandler;

import java.util.List;

public interface SourceTask<T extends SourceState> extends AutoCloseable {
    void start(T state, ErrorHandler handler);
    List<Record> pull();
}
