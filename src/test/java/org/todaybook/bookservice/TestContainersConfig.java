package org.todaybook.bookservice;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.postgresql.PostgreSQLContainer;

@TestConfiguration
public class TestContainersConfig {

  @Container
  @ServiceConnection
  public static final PostgreSQLContainer postgres =
      new PostgreSQLContainer("postgres:17")
          .withDatabaseName("testdb")
          .withUsername("root")
          .withPassword("1234");
}
