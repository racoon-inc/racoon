package run.racoon.commons.tasks;

import run.racoon.commons.domain.Record;
import run.racoon.commons.domain.TransformTaskData;

import java.util.List;

public interface TransformTask<T extends TransformTaskData> extends Task<T> {
    List<Record> transform(List<Record> records);
}
