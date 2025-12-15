package org.todaybook.bookservice.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.postgresql.PostgreSQLContainer;

@TestConfiguration
public class PostgresContainerConfig {

  @Bean
  @ServiceConnection
  @SuppressWarnings("resource")
  public PostgreSQLContainer postgresContainer() {
    return new PostgreSQLContainer("postgres:17").withDatabaseName("test-postgres");
  }
}
