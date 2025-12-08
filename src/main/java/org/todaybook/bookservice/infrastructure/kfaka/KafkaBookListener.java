package org.todaybook.bookservice.infrastructure.kfaka;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.todaybook.bookservice.application.dto.BookMapper;
import org.todaybook.bookservice.application.service.BookService;
import org.todaybook.bookservice.infrastructure.kfaka.dto.BookConsumeMessage;
import org.todaybook.bookservice.presentation.dto.BookRegisterRequest;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaBookListener {

  private final BookService bookService;

  @KafkaListener(topics = "book.parsed", containerFactory = "batchKafkaListenerContainerFactory")
  public void parsed(List<BookConsumeMessage> messages) {
    log.debug("[TODAY-BOOK] Kafka 메세지 수신: {}", messages);
    List<BookRegisterRequest> request = messages.stream().map(BookMapper::toRequest).toList();
    bookService.register(request);
  }
}
