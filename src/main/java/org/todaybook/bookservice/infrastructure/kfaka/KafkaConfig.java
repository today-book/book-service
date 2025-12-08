package org.todaybook.bookservice.infrastructure.kfaka;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.todaybook.bookservice.infrastructure.kfaka.dto.BookConsumeMessage;

@EnableKafka
@Configuration
public class KafkaConfig {

  @Value("${spring.kafka.bootstrap-servers}")
  private String server;

  @Bean
  public ConsumerFactory<String, BookConsumeMessage> consumerFactory() {
    Map<String, Object> config = new HashMap<>();

    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
    config.put(ConsumerConfig.GROUP_ID_CONFIG, "book-service");
    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
    config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

    config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 50);
    config.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 300);
    config.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1);

    return new DefaultKafkaConsumerFactory<>(
        config, new StringDeserializer(), new JsonDeserializer<>(BookConsumeMessage.class));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, BookConsumeMessage>
      kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, BookConsumeMessage> factory =
        new ConcurrentKafkaListenerContainerFactory<>();

    factory.setConsumerFactory(consumerFactory());

    return factory;
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, BookConsumeMessage>
      batchKafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, BookConsumeMessage> factory =
        new ConcurrentKafkaListenerContainerFactory<>();

    factory.setConsumerFactory(consumerFactory());

    factory.setBatchListener(true);

    return factory;
  }
}
