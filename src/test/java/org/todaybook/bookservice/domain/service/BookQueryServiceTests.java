package org.todaybook.bookservice.domain.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.todaybook.bookservice.config.TestContainersConfig;
import org.todaybook.bookservice.domain.Book;
import org.todaybook.bookservice.domain.BookId;

@SpringBootTest
@ActiveProfiles("test")
@Sql("classpath:sql/book-data.sql")
@Import({TestContainersConfig.class})
class BookQueryServiceTests {

  @Autowired private BookQueryService queryService;

  @Test
  @DisplayName("도서 조회 테스트: 여러 ID 중 존재하는 도서만 반환")
  void test1() {
    List<BookId> ids =
        List.of(
            BookId.of(UUID.fromString("00000000-0000-0000-0000-000000000000")),
            BookId.of(UUID.fromString("11111111-1111-1111-1111-111111111111")),
            BookId.of(UUID.fromString("22222222-2222-2222-2222-222222222222")));

    List<Book> books = queryService.getBooksByIds(ids);

    assertEquals(2, books.size());
  }
}
