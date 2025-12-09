package org.todaybook.bookservice.infrastructure.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.todaybook.bookservice.infrastructure.kafka.dto.BookProduceMessage;

@Service
public class KafkaMessageProducer {

  @Autowired private KafkaTemplate<String, BookProduceMessage> kafkaTemplate;

  public void send(BookProduceMessage message) {
    kafkaTemplate.send("book.parsed", message);
  }
}
