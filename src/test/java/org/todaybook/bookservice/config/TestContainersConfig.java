package org.todaybook.bookservice.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.postgresql.PostgreSQLContainer;

@TestConfiguration
public class TestContainersConfig {

  @Bean
  @ServiceConnection
  public PostgreSQLContainer postgreSQLContainer() {
    PostgreSQLContainer postgres =
        new PostgreSQLContainer("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("root")
            .withPassword("1234");
    postgres.start();
    return postgres;
  }
}
