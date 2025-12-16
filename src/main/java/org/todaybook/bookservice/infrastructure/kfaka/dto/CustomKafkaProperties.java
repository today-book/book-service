package org.todaybook.bookservice.infrastructure.kfaka.dto;

import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka")
public record CustomKafkaProperties(String bootstrapServers, Map<String, String> properties) {
  public String securityProtocol() {
    return properties.getOrDefault("security.protocol", "PLAINTEXT");
  }

  public String saslMechanism() {
    return properties.getOrDefault("sasl.mechanism", null);
  }

  public String jaasConfig() {
    return properties.getOrDefault("sasl.jaas.config", null);
  }

  public String get(String key) {
    return properties.get(key);
  }

  public boolean has(String key) {
    return properties.containsKey(key);
  }
}
