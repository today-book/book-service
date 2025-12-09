package org.todaybook.bookservice.infrastructure.kafka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.todaybook.bookservice.infrastructure.kafka.dto.BookProduceMessage;

@Configuration
public class TestKafkaConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String server;

  @Bean
  public ProducerFactory<String, BookProduceMessage> producerFactory() {
    Map<String, Object> config = new HashMap<>();

    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    config.put("spring.json.add.type.headers", false);

    return new DefaultKafkaProducerFactory<>(
        config, new StringSerializer(), new JsonSerializer<>());
  }

  @Bean
  public KafkaTemplate<String, BookProduceMessage> kafkaTemplate(
      ProducerFactory<String, BookProduceMessage> producerFactory) {
    return new KafkaTemplate<>(producerFactory);
  }
}
