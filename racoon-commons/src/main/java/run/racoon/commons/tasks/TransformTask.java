package run.racoon.commons.tasks;

import run.racoon.commons.domain.Record;
import run.racoon.commons.domain.TransformerState;

import java.util.List;

public interface TransformTask<T extends TransformerState> {
    void configure(T state);
    List<Record> transform(List<Record> records);
}
