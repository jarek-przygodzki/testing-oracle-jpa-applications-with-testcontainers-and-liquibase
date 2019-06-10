package testing_oracle_jpa_applications_with_testcontainers_and_liquibase.repository;

import java.sql.Connection;
import java.sql.DriverManager;

import org.testcontainers.containers.JdbcDatabaseContainer;

import testing_oracle_jpa_applications_with_testcontainers_and_liquibase.dbinit.LiquibaseDatabaseInitialization;

public class DbTestSupport {

    public static void initDatabaseSchema(JdbcDatabaseContainer<?> db) throws Exception {
        try (Connection connection = DriverManager.getConnection(db.getJdbcUrl(), db.getUsername(),
                db.getPassword())) {
            LiquibaseDatabaseInitialization dbInit = new LiquibaseDatabaseInitialization();
            dbInit.setDropFirst(true);
            dbInit.initDatabaseSchema(connection);
        }
    }
}
