package run.racoon.tasks.jdbc.sink;

import run.racoon.commons.domain.SinkTaskData;
import run.racoon.commons.tasks.Task;

import java.util.Map;

public class JdbcSinkTaskTaskData implements SinkTaskData {
    private String jdbcUrl;
    private String query;
    private Map<String, Integer> positionMap;

    public JdbcSinkTaskTaskData(String jdbcUrl, String query, Map<String, Integer> positionMap) {
        this.jdbcUrl = jdbcUrl;
        this.query = query;
        this.positionMap = positionMap;
    }

    public String getJdbcUrl() {
        return this.jdbcUrl;
    }

    public String getQuery() {
        return this.query;
    }

    public Map<String, Integer> getPositionMap() {
        return positionMap;
    }


    // TODO: rethink task initialization
    @Override
    public Task initiateTask() {
        var task = new JdbcSinkTask();
        task.start(this, null);
        return task;
    }
}
