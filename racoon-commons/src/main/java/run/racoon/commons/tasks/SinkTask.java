package run.racoon.commons.tasks;

import run.racoon.commons.domain.Record;
import run.racoon.commons.domain.SinkTaskData;

import java.util.List;

public interface SinkTask<T extends SinkTaskData> extends Task<T> {
    void push(List<Record> records);
}
