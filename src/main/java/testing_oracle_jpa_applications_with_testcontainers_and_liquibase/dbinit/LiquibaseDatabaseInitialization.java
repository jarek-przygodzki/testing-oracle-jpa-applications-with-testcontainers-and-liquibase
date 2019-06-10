package testing_oracle_jpa_applications_with_testcontainers_and_liquibase.dbinit;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

public class LiquibaseDatabaseInitialization {

    private boolean dropFirst;

    public void setDropFirst(boolean dropFirst) {
        this.dropFirst = dropFirst;
    }

    public boolean isDropFirst() {
        return dropFirst;
    }

    private static Liquibase createLiquibase(Connection connection) throws DatabaseException {
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor();
        String changeLogFile = "db/changelog/changelog-master.xml";
        Database db = DatabaseFactory.getInstance()
                                     .findCorrectDatabaseImplementation(
                                             new JdbcConnection(connection));
        return new Liquibase(changeLogFile, resourceAccessor, db);
    }

    public void initDatabaseSchema(DataSource dataSource) throws SQLException, LiquibaseException {
        try (Connection connection = dataSource.getConnection()) {
            initDatabaseSchema(connection);
        }

    }

    public void initDatabaseSchema(Connection connection)
            throws DatabaseException, LiquibaseException {
        Liquibase l = createLiquibase(connection);
        if (isDropFirst()) {
            l.dropAll();
        }
        l.update(new Contexts());
    }
}
