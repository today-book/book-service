package org.todaybook.bookservice.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.todaybook.bookservice.config.KafkaContainerConfig;
import org.todaybook.bookservice.config.PostgresContainerConfig;
import org.todaybook.bookservice.presentation.dto.BookListResponse;

@SpringBootTest
@ActiveProfiles("test")
@Sql("classpath:sql/book-data.sql")
@Import({PostgresContainerConfig.class})
public class BookServiceTests extends KafkaContainerConfig {

  @Autowired private BookService bookService;

  @Test
  @DisplayName("도서 조회 테스트: found/failure 도서 반환")
  void test1() {
    List<UUID> ids =
        List.of(
            UUID.fromString("00000000-0000-0000-0000-000000000000"),
            UUID.fromString("11111111-1111-1111-1111-111111111111"),
            UUID.fromString("22222222-2222-2222-2222-222222222222"));

    BookListResponse result = bookService.getBooksByIds(ids);

    assertEquals(2, result.found().size());
    assertEquals(1, result.failure().size());
  }
}
