package run.racoon.commons.tasks;

import run.racoon.commons.handlers.ErrorHandler;

public interface Task<T> extends AutoCloseable {
    void start(T state, ErrorHandler errorHandler);
}
