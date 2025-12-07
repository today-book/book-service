package org.todaybook.bookservice.infrastructure.kfaka;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.todaybook.bookservice.application.dto.BookMapper;
import org.todaybook.bookservice.application.service.BookService;
import org.todaybook.bookservice.infrastructure.kfaka.dto.BookConsumeMessage;
import org.todaybook.bookservice.presentation.dto.BookRegisterRequest;

@Component
@RequiredArgsConstructor
public class KafkaBookListener {

  private final BookService bookService;

  @KafkaListener(topics = "book.parsed", containerFactory = "batchKafkaListenerContainerFactory")
  public void parsed(
      List<BookConsumeMessage> messages, @Header(KafkaHeaders.RECEIVED_KEY) String key) {
    List<BookRegisterRequest> request = messages.stream().map(BookMapper::toRequest).toList();
    bookService.register(request);
  }
}
