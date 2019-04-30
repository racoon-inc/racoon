package run.racoon.tasks.jdbc.source;

import run.racoon.commons.domain.SourceTaskData;
import run.racoon.commons.tasks.Task;

public class JdbcSourceTaskTaskData implements SourceTaskData {
    private String jdbcUrl;
    private String query;
    private int batchSize;

    public JdbcSourceTaskTaskData(String jdbcUrl, String query, int batchSize) {
        this.jdbcUrl = jdbcUrl;
        this.query = query;
        this.batchSize = batchSize;
    }

    public String getJdbcUrl() {
        return this.jdbcUrl;
    }

    public String getQuery() {
        return this.query;
    }

    public int getBatchSize() {
        return this.batchSize;
    }

    @Override
    public Task initiateTask() {
        var task = new JdbcSourceTask();
        task.start(this, null);
        return task;
    }
}
