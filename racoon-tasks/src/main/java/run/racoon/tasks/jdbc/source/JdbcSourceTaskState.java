package run.racoon.tasks.jdbc.source;

import run.racoon.commons.domain.SourceState;

public class JdbcSourceTaskState implements SourceState {
    private String jdbcUrl;
    private String query;
    private int batchSize;

    public JdbcSourceTaskState(String jdbcUrl, String query, int batchSize) {
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
}
