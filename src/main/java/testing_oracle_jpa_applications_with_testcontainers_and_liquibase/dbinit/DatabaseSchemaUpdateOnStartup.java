package testing_oracle_jpa_applications_with_testcontainers_and_liquibase.dbinit;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.sql.DataSource;

import liquibase.exception.LiquibaseException;

@ApplicationScoped
public class DatabaseSchemaUpdateOnStartup {
    @Resource(mappedName = "java:/jdbc/AppDs")
    private DataSource dataSource;

    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {
        LiquibaseDatabaseInitialization dbInit = new LiquibaseDatabaseInitialization();
        try {
            dbInit.initDatabaseSchema(dataSource);
        } catch (SQLException | LiquibaseException e) {
            throw new RuntimeException("Failed to initialize database", e);
        }
    }

}
