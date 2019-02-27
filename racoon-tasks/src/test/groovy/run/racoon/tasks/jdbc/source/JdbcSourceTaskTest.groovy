package run.racoon.tasks.jdbc.source

import org.junit.Rule
import org.zapodot.junit.db.EmbeddedDatabaseRule
import run.racoon.tasks.jdbc.JdbcTestUtil
import spock.lang.Specification

class JdbcSourceTaskTest extends Specification {

    @Rule
    EmbeddedDatabaseRule dbRule = JdbcTestUtil.createEmbeddedDatabaseRule("""
                CREATE TABLE coons (
                  first_name VARCHAR,
                  last_name VARCHAR,
                  age INTEGER
                );
                INSERT INTO coons (first_name, last_name, age) VALUES
                ('Racoon', 'Coon', 2),
                ('Coony', 'McRacoon', 4),
                ('McCoon', 'Racoonator', 6);
            """)

    def "Should retrieve batched records"() {
        given:
        def state = new JdbcSourceTaskState(
                dbRule.getConnectionJdbcUrl(),
                "SELECT first_name, last_name, age FROM coons",
                2
        )
        def errorHandler = { Throwable error -> error.printStackTrace() }
        def task = new JdbcSourceTask()

        when:
        task.start(state, errorHandler)

        then:
        noExceptionThrown()

        when:
        def batch = task.pull()

        then:
        batch.size() == 2
        byName(batch[0], 'FIRST_NAME') == 'Racoon'
        byName(batch[0], 'LAST_NAME') == 'Coon'
        byName(batch[0], 'AGE') == 2

        when:
        batch = task.pull()

        then:
        batch.size() == 1
        byName(batch[0], 'FIRST_NAME') == 'McCoon'

        when:
        batch = task.pull()

        then:
        batch == []
    }

    def byName(record, name){
        record.getByName(name)
    }

}
