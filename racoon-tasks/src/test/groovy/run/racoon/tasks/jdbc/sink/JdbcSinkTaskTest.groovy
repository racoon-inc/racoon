package run.racoon.tasks.jdbc.sink

import org.junit.Rule
import org.zapodot.junit.db.EmbeddedDatabaseRule
import run.racoon.commons.domain.Record
import run.racoon.tasks.jdbc.JdbcTestUtil
import spock.lang.Specification

class JdbcSinkTaskTest extends Specification {

    @Rule
    EmbeddedDatabaseRule dbRule = JdbcTestUtil.createEmbeddedDatabaseRule("""
                CREATE TABLE coons (
                  first_name VARCHAR,
                  last_name VARCHAR,
                  age INTEGER
                );
            """)

    def "Should sink two messages"() {
        given:
        def state = new JdbcSinkTaskState(
                dbRule.getConnectionJdbcUrl(),
                "INSERT INTO coons (first_name, last_name, age) VALUES (?, ?, ?);",
                [first_name: 1, last_name: 2, age: 3]
        )
        def errorHandler = { Throwable error -> error.printStackTrace() }
        def task = new JdbcSinkTask()

        when:
        task.start(state, errorHandler)

        then:
        noExceptionThrown()

        when:
        def records = [
            Record.Builder.newBuilder()
                    .cell("first_name", "Racoon")
                    .cell("last_name", "Coon")
                    .cell("age", 21)
                    .build(),
            Record.Builder.newBuilder()
                    .cell("first_name", "Racoon2")
                    .cell("last_name", "Coon2")
                    .cell("age", 22)
                    .build()]
        task.push(records)

        then:
        def connection = dbRule.getConnection()
        def statement = connection.createStatement()
        def resultSet = statement.executeQuery("SELECT * FROM coons")

        resultSet.next()
        resultSet.getString(1) == 'Racoon'
        resultSet.getString(2) == 'Coon'
        resultSet.getInt(3) == 21

        resultSet.next()
        resultSet.getString(1) == 'Racoon2'
        resultSet.getString(2) == 'Coon2'
        resultSet.getInt(3) == 22

        !resultSet.next()

        resultSet.close()
        statement.close()
        connection.close()
    }
}
