package run.racoon.tasks.jdbc.source;

import run.racoon.commons.domain.Record;
import run.racoon.commons.exceptions.ConfigurationException;
import run.racoon.commons.handlers.ErrorHandler;
import run.racoon.commons.tasks.SourceTask;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcSourceTask implements SourceTask<JdbcSourceTaskState> {

    private JdbcSourceTaskState state;
    private Connection connection;
    private PreparedStatement query;
    private ResultSet resultSet;
    private ResultSetMetaData meta;
    private ErrorHandler errorHandler;

    @Override
    public void start(JdbcSourceTaskState state, ErrorHandler errorHandler) {
        try {
            this.state = state;
            this.errorHandler = errorHandler;
            this.connection = DriverManager.getConnection(state.getJdbcUrl());
            this.query = this.connection.prepareStatement(state.getQuery());
            this.query.setFetchSize(state.getBatchSize());
            this.resultSet = query.executeQuery();
            this.meta = resultSet.getMetaData();
        } catch (SQLException e) {
            throw new ConfigurationException("Failed creating JDBC connection", e);
        }
    }

    @Override
    public List<Record> pull() {
        var results = new ArrayList<Record>();
        var recordIndex = 0;
        do {
            recordIndex++;
            try {
                if (!resultSet.next()) {
                    break;
                }
                var builder = new Record.Builder();
                for (int i = 1; i <= meta.getColumnCount(); i++) {
                    builder.cell(meta.getColumnName(i), resultSet.getObject(i));
                }
                results.add(builder.build());

            } catch (SQLException e) {
                errorHandler.handle(e);
            }

        } while (state.getBatchSize() != recordIndex);

        return results;
    }

    @Override
    public void close() throws Exception {
        if (connection != null) {
            resultSet.close();
            query.close();
            connection.close();
        }
    }
}
