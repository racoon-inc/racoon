package run.racoon.commons.tasks;

import run.racoon.commons.domain.Record;
import run.racoon.commons.domain.SourceTaskData;

import java.util.List;

public interface SourceTask<T extends SourceTaskData> extends Task<T> {
    List<Record> pull();
}
