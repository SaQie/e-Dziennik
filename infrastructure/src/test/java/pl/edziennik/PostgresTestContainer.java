package pl.edziennik;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresTestContainer extends PostgreSQLContainer<PostgresTestContainer> {

    private static final String POSTGRESQL_IMAGE_VERSION = "postgres:14.4";
    private static final String DATABASE_NAME = "diary_db";
    private static PostgreSQLContainer<PostgresTestContainer> container;

    public PostgresTestContainer() {
        super(POSTGRESQL_IMAGE_VERSION);
    }

    public static PostgreSQLContainer<PostgresTestContainer> getInstance() {
        if (container == null) {
            container = new <PostgresTestContainer>PostgresTestContainer()
                    .withDatabaseName(DATABASE_NAME);
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }
}
