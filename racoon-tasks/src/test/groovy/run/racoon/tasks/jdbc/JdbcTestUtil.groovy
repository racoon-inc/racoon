package run.racoon.tasks.jdbc

import org.zapodot.junit.db.CompatibilityMode
import org.zapodot.junit.db.EmbeddedDatabaseRule

class JdbcTestUtil {
    static createEmbeddedDatabaseRule(String initSql) {
        return EmbeddedDatabaseRule
            .builder()
            .withMode(CompatibilityMode.PostgreSQL)
            .withInitialSql(initSql)
            .build()
    }
}
