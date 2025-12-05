package org.todaybook.bookservice.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.postgresql.PostgreSQLContainer;

@TestConfiguration
public class TestContainersConfig {

  @Bean
  public PostgreSQLContainer postgreSQLContainer() {
    PostgreSQLContainer postgres =
        new PostgreSQLContainer("postgres:17")
            .withDatabaseName("testdb")
            .withUsername("root")
            .withPassword("1234");
    postgres.start();
    return postgres;
  }

  @Bean
  public DataSource dataSource(PostgreSQLContainer postgres) {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(postgres.getJdbcUrl());
    dataSource.setUsername(postgres.getUsername());
    dataSource.setPassword(postgres.getPassword());
    return dataSource;
  }
}
