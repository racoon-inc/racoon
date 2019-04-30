package run.racoon.node.domain;

import run.racoon.commons.domain.TaskData;

import java.util.List;

public class Step {
    private final String stepId;
    private final List<String> dependsOnStepIds;
    private final TaskData taskData;

    public Step(String stepId, List<String> dependsOnStepIds, TaskData taskData) {
        this.stepId = stepId;
        this.dependsOnStepIds = dependsOnStepIds;
        this.taskData = taskData;
    }

    public String getStepId() {
        return stepId;
    }

    public List<String> getDependsOnStepIds() {
        return dependsOnStepIds;
    }

    public TaskData getTaskData() {
        return taskData;
    }
}
