package pl.edziennik;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class ContainerEnvironment {


    @Container
    public static PostgreSQLContainer<PostgresTestContainer> container = PostgresTestContainer.getInstance();

}
