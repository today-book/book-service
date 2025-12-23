package org.todaybook.bookservice.infrastructure.kfaka;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.util.backoff.FixedBackOff;
import org.todaybook.bookservice.infrastructure.kfaka.dto.BookConsumeMessage;

@Slf4j
@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

  private final KafkaTemplate<String, Object> kafkaTemplate;

  private final KafkaProperties properties;

  @Bean
  public ConsumerFactory<String, BookConsumeMessage> consumerFactory() {
    Map<String, Object> config = properties.buildConsumerProperties();

    config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);
    config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ErrorHandlingDeserializer.class);

    config.put(ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS, StringDeserializer.class);
    config.put(ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS, JsonDeserializer.class);

    config.put(JsonDeserializer.VALUE_DEFAULT_TYPE, BookConsumeMessage.class.getName());
    config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

    return new DefaultKafkaConsumerFactory<>(config);
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

    // DLQ 전송 담당
    DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate);

    // retry 3회 + backoff 1초
    DefaultErrorHandler errorHandler =
        new DefaultErrorHandler(recoverer, new FixedBackOff(1000L, 3));

    // 재시도하면 의미 없는 예외들
    errorHandler.addNotRetryableExceptions(
        SerializationException.class, IllegalArgumentException.class);

    factory.setCommonErrorHandler(errorHandler);

    return factory;
  }
}
