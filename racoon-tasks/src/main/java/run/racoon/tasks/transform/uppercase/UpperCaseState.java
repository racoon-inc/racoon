package run.racoon.tasks.transform.uppercase;

import run.racoon.commons.domain.TransformTaskData;
import run.racoon.commons.tasks.Task;

import java.util.List;

public class UpperCaseState implements TransformTaskData {
    private final List<String> fieldNames;

    public UpperCaseState(List<String> fieldNames) {
        this.fieldNames = fieldNames;
    }

    public List<String> getFieldNames() {
        return fieldNames;
    }

    @Override
    public Task initiateTask() {
        var task = new UpperCaseTransformTask();
        task.start(this, null);
        return task;
    }
}
