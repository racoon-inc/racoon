package run.racoon.node.execution;

import run.racoon.commons.domain.Record;
import run.racoon.commons.domain.SinkTaskData;
import run.racoon.commons.domain.SourceTaskData;
import run.racoon.commons.domain.TransformTaskData;
import run.racoon.commons.tasks.SinkTask;
import run.racoon.commons.tasks.SourceTask;
import run.racoon.commons.tasks.TransformTask;
import run.racoon.node.domain.Pipeline;
import run.racoon.node.domain.Step;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Pilot implementation used only in development process
 */
public class PipelineExecutor implements Runnable {
    private final Map<String, List<Record>> pullStore = new ConcurrentHashMap<>();

    private final Pipeline pipeline;

    public PipelineExecutor(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Override
    public void run() {
       pipeline.getSteps().parallelStream().forEach(this::processByTaskData);
    }

    private void processByTaskData(Step step) {
        var data = step.getTaskData();
        if (data instanceof SourceTaskData) {
            try (var task = (SourceTask) data.initiateTask()) {
                var records = task.pull();
                while (!records.isEmpty()) {
                    var stepIds = step.getDependsOnStepIds();
                    for (String stepId : stepIds) {
                        pullStore.computeIfAbsent(stepId, key -> new ArrayList<>()).addAll(records);
                    }
                    records = task.pull();
                }
            } catch (Exception e) {
                // TODO
            }
        } else if (data instanceof SinkTaskData) {
            try (var task = (SinkTask) data.initiateTask()) {
                var records = pullStore.remove(step.getStepId());
                while (records != null && !records.isEmpty()) {
                    task.push(records);
                    records = pullStore.get(step.getStepId());
                }
            } catch (Exception e) {
                // TODO
            }
        } else if (data instanceof TransformTaskData) {
            try (var task = (TransformTask) data.initiateTask()) {
                var records = pullStore.remove(step.getStepId());
                while (records != null && !records.isEmpty()) {
                    var stepIds = step.getDependsOnStepIds();
                    var transformations = task.transform(records);
                    for (String stepId : stepIds) {
                        pullStore.computeIfAbsent(stepId, key -> new ArrayList<>()).addAll(transformations);
                    }
                    records = pullStore.get(step.getStepId());
                }
            } catch (Exception e) {
                // TODO
            }
        }
    }
}
