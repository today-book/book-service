package org.todaybook.bookservice.config;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;

@Testcontainers
public abstract class KafkaContainerConfig {

  @Container public static KafkaContainer KAFKA = new KafkaContainer("apache/kafka");

  @DynamicPropertySource
  static void properties(DynamicPropertyRegistry registry) {
    String uri = KAFKA.getBootstrapServers();
    registry.add("spring.kafka.bootstrap-servers", () -> uri);

    registry.add("spring.kafka.properties.security.protocol", () -> "PLAINTEXT");
    registry.add("spring.kafka.properties.sasl.mechanism", () -> "");
    registry.add("spring.kafka.properties.sasl.jaas.config", () -> "");

    System.out.println("Kafka Container start: " + uri);
  }
}
