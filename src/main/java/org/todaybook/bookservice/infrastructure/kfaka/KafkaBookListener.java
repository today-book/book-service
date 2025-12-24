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
    if (messages.isEmpty()) {
      log.warn("[TODAY-BOOK] Kafka 메시지 수신 - empty batch");
      return;
    }

    log.info("[TODAY-BOOK] Kafka 메시지 수신 - size={}", messages.size());

    try {
      List<BookRegisterRequest> request = messages.stream().map(BookMapper::toRequest).toList();

      bookService.register(request);

      log.info("[TODAY-BOOK] Kafka 메시지 처리 완료 - size={}", messages.size());
    } catch (Exception e) {
      log.error("[TODAY-BOOK] Kafka 메시지 처리 실패 - size={}", messages.size(), e);
      throw e;
    }
  }
}
