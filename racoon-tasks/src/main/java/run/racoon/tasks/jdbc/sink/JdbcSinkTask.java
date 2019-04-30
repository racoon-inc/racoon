package run.racoon.tasks.jdbc.sink;

import run.racoon.commons.domain.Cell;
import run.racoon.commons.domain.Record;
import run.racoon.commons.exceptions.ConfigurationException;
import run.racoon.commons.handlers.ErrorHandler;
import run.racoon.commons.tasks.SinkTask;

import java.sql.*;
import java.util.List;

public class JdbcSinkTask implements SinkTask<JdbcSinkTaskTaskData> {
    private JdbcSinkTaskTaskData state;
    private Connection connection;
    private PreparedStatement query;
    private ErrorHandler errorHandler;

    @Override
    public void start(JdbcSinkTaskTaskData state, ErrorHandler errorHandler) {
        try {
            this.state = state;
            this.errorHandler = errorHandler;
            this.connection = DriverManager.getConnection(state.getJdbcUrl());
            this.query = this.connection.prepareStatement(state.getQuery());
        } catch (SQLException e) {
            throw new ConfigurationException("Failed creating JDBC connection", e);
        }
    }

    @Override
    public void push(List<Record> records) {
        for (Record record : records) {
            try {
                addRecordToBatch(record);
            } catch (SQLException e) {
                errorHandler.handle(e);
            }
        }

        try {
            query.executeBatch();
            query.clearBatch();
        } catch (SQLException e) {
            errorHandler.handle(e);
        }
    }

    private void addRecordToBatch(Record record) throws SQLException {
        for (Cell cell : record.getCells()) {
            String name = cell.getName();
            Object value = cell.getValue();

            Integer position = state.getPositionMap().get(name);
            query.setObject(position, value);
        }

        query.addBatch();
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            query.close();
            connection.close();
        }
    }
}
