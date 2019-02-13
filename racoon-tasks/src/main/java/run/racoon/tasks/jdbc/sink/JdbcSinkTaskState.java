package run.racoon.tasks.jdbc.sink;

import run.racoon.commons.domain.SinkState;

import java.util.Map;

public class JdbcSinkTaskState implements SinkState {
    private String jdbcUrl;
    private String query;
    private Map<String, Integer> positionMap;

    public JdbcSinkTaskState(String jdbcUrl, String query, Map<String, Integer> positionMap) {
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
}
