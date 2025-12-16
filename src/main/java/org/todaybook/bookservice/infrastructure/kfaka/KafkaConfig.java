package org.todaybook.bookservice.infrastructure.kfaka;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.config.SaslConfigs;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.todaybook.bookservice.infrastructure.kfaka.dto.BookConsumeMessage;
import org.todaybook.bookservice.infrastructure.kfaka.dto.CustomKafkaProperties;

@Slf4j
@EnableKafka
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(CustomKafkaProperties.class)
public class KafkaConfig {

  private final CustomKafkaProperties properties;

  @Bean
  public ConsumerFactory<String, BookConsumeMessage> consumerFactory() {
    Map<String, Object> config = new HashMap<>();

    config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, properties.bootstrapServers());
    config.put(ConsumerConfig.GROUP_ID_CONFIG, "book-service-001");
    config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    // 보안 설정
    config.put(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, properties.securityProtocol());
    config.put(SaslConfigs.SASL_MECHANISM, properties.saslMechanism());
    config.put(SaslConfigs.SASL_JAAS_CONFIG, properties.jaasConfig());
    // 만약 jaasConfig 주입이 귀찮다면 아래처럼 직접 넣어도 되지만 권장하지 않음
    // config.put(SaslConfigs.SASL_JAAS_CONFIG,
    // "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"admin\"
    // password=\"Qwer!234\";");

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

    DefaultErrorHandler errorHandler = new DefaultErrorHandler();
    errorHandler.addNotRetryableExceptions(SerializationException.class);
    factory.setCommonErrorHandler(errorHandler);

    return factory;
  }
}
